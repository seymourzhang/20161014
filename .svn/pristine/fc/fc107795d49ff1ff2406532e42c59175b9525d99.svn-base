/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.device.bbfar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.mtc.app.biz.DeviceBbfReqManager;
import com.mtc.device.bbfar.common.BBFarUtil;
import com.mtc.device.yincomm.common.StringHexUtil;
import com.mtc.entity.app.SBBbfar;


/**
 * @ClassName: BBFarDealData
 * @Description: 
 * @Date 2015年12月22日 上午11:07:44
 * @Author Yin Guo Xiang
 * 
 */
public class BBFarDealData implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(BBFarDealData.class);
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat df = new SimpleDateFormat(pattern);
	
	private DeviceBbfReqManager tDeviceBbfReqManager = null;
	
	private Socket socket = null;
	
	public BBFarDealData(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			String socketTaskName = df.format(new Date()) ;
			mLogger.info("BBFarDealData("+socketTaskName+") start ,线程号====" + Thread.currentThread());
			
			int dataCount = 1;
			InputStream in = socket.getInputStream();
			ByteArrayOutputStream bo = null;
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length=in.read(buffer))!=-1) {
				bo = new ByteArrayOutputStream();
				bo.write(buffer, 0, length);
				long longtime = System.currentTimeMillis();
				byte[] res = bo.toByteArray();
   
				mLogger.info(df.format(new Date(longtime))+"接收帧"+dataCount+"："+ StringHexUtil.bytes2HexString(res));
				
				byte[] response = dealDatas(res);
				
				if(response != null){
					mLogger.info(df.format(new Date(longtime))+"确认帧"+dataCount+"："+ StringHexUtil.bytes2HexString(response));
					OutputStream tOutputStream = socket.getOutputStream();
					tOutputStream.write(response);
					tOutputStream.flush();
				}
				
				mLogger.info("");
				dataCount++;
			}
			mLogger.info("BBFarDealData("+socketTaskName+") end   ,线程号====" + Thread.currentThread());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean checkData(String receiveStr,int crcCheckValue){
		boolean res = true;
		int crcIndex = receiveStr.indexOf("CRC=");
		receiveStr = receiveStr.substring(0, crcIndex);
		
		String HexStr = StringHexUtil.bytes2HexString(BBFarUtil.transformHexStr(receiveStr));
		int realCrcValue = BBFarUtil.getCRCValue(HexStr);
		if(crcCheckValue != realCrcValue){
			mLogger.error("Error：参数CRC值为：" + crcCheckValue);
			mLogger.error("Error：计算CRC值为：" + realCrcValue);
			res = false;
		}
		return res;
	}
	
	private HashMap<String,String> genParaMap(String sourceStr){
		HashMap<String,String> tempHM = null;
		try {
			tempHM = new HashMap<String,String>();
			String[] paraArray = sourceStr.split("&");
			if(paraArray != null && paraArray.length > 0){
				for(String singlePara : paraArray){
					String[] keyValues = singlePara.split("=");
					if(keyValues.length == 1){
						tempHM.put(keyValues[0], "");
					}else{
						tempHM.put(keyValues[0], keyValues[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempHM;
	}
	
	private byte [] dealDatas(byte[] datas){
		byte [] returnData = null;
		try {
//			String res = "6B657969643D663532383030383430323834633426494D45493D3836303131313032303934323334322649434349443D38393836303242333133313635303036333439302674313D32332E312674323D302E302674333D302E302674343D302E302674353D302E302674363D302E302668313D2668323D26703D312673746174696F6E3D396238323B313831393B3436303B3030264353513D3239264442473D343533363B343533363B343533363B3130303030264352433D3433393035";
//			datas = StringHexUtil.hexString2Bytes(res);
			
			if(datas == null){
				mLogger.error("Error：数据为空。");
				return returnData;
			}
			String receiveStr = BBFarUtil.parseFromHexStr(datas);
			mLogger.info("解析后：" + receiveStr);
			
			HashMap<String,String> paraMap = genParaMap(receiveStr);
			if(paraMap == null || paraMap.size() == 0){
				mLogger.error("传输数据错误。。。");
				return returnData;
			}
			if(!paraMap.containsKey("CRC")){
				mLogger.error("传输数据无CRC校验码");
				return returnData;
			}
			
			int crcValue = Integer.parseInt(paraMap.get("CRC").toString());
			
			if(!checkData(receiveStr,crcValue)){
				mLogger.error("CRC校验失败。");
				return returnData;
			}
			
			SBBbfar tSBBbfar = transObject(paraMap);
			if(tSBBbfar != null){
				try {
					HashMap<String, Object> mParas = new HashMap<String, Object>();
					mParas.put("SBBbfar", tSBBbfar);
					tDeviceBbfReqManager.saveSbbbfar(mParas);
					tDeviceBbfReqManager.dealBBFarData(tSBBbfar);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			returnData = genResponseByte(paraMap.get("keyid").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	private SBBbfar transObject(HashMap<String,String> paraMap){
		SBBbfar tSBBbfar = null;
		
		if(paraMap != null && paraMap.size()>0){
			tSBBbfar = new SBBbfar();
			Iterator<Entry<String, String>> iter = paraMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
				if(entry.getKey().equals("keyid")){
					tSBBbfar.setKeyid(entry.getValue());
				}else if(entry.getKey().equals("IMEI")){
					tSBBbfar.setBak1(entry.getValue());
				}else if(entry.getKey().equals("ICCID")){
					tSBBbfar.setBak2(entry.getValue());
				}else if(entry.getKey().equals("t1")){
					tSBBbfar.setT1(entry.getValue());
				}else if(entry.getKey().equals("t2")){
					tSBBbfar.setT2(entry.getValue());
				}else if(entry.getKey().equals("t3")){
					tSBBbfar.setT3(entry.getValue());
				}else if(entry.getKey().equals("t4")){
					tSBBbfar.setT4(entry.getValue());
				}else if(entry.getKey().equals("t5")){
					tSBBbfar.setT5(entry.getValue());
				}else if(entry.getKey().equals("t6")){
					tSBBbfar.setT6(entry.getValue());
				}else if(entry.getKey().equals("h1")){
					tSBBbfar.setH1(entry.getValue());
				}else if(entry.getKey().equals("h2")){
					tSBBbfar.setH2(entry.getValue());
				}else if(entry.getKey().equals("p")){
					tSBBbfar.setP(entry.getValue());
				}else if(entry.getKey().equals("station")){
					tSBBbfar.setStation(entry.getValue());
				}else if(entry.getKey().equals("CSQ")){
					tSBBbfar.setCsq(entry.getValue());
				}else if(entry.getKey().equals("DBG")){
					tSBBbfar.setDbg(entry.getValue());
				}else if(entry.getKey().equals("CRC")){
					tSBBbfar.setCrc(entry.getValue());
				}
			}
			tSBBbfar.setDateTime(new Date());
		}
		
		return tSBBbfar;
	}
	
	public static void main(String []dd){
		BBFarDealData tBBFarDealData =new BBFarDealData(null);
		tBBFarDealData.dealDatas(null);
	}
	
	private byte[] genResponseByte(String keyId){
		String responseStr = "RecvOK" + keyId;
		return BBFarUtil.transformHexStr(responseStr);
	}

	public DeviceBbfReqManager gettDeviceBbfReqManager() {
		return tDeviceBbfReqManager;
	}

	public void settDeviceBbfReqManager(DeviceBbfReqManager tDeviceBbfReqManager) {
		this.tDeviceBbfReqManager = tDeviceBbfReqManager;
	}
}

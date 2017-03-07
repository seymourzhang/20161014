/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.device.yincomm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.mtc.app.biz.SBYincommManager;
import com.mtc.common.util.PubFun;
import com.mtc.device.yincomm.common.ByteNumUtil;
import com.mtc.device.yincomm.common.CRC16_Modbus;
import com.mtc.device.yincomm.common.StringHexUtil;
import com.mtc.device.yincomm.common.WirelessYTConstants;
import com.mtc.entity.app.SBYincomm;
import com.mtc.entity.app.SBYincommMain;
import com.mtc.entity.app.SBYincommSub;


/**
 * @ClassName: YINCommSocketTask
 * @Description: 
 * @Date 2015年12月22日 上午11:07:44
 * @Author Yin Guo Xiang
 * 
 */
public class WirelessYTTask implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(WirelessYTTask.class);
	
	private SBYincommManager mSBYincommManager;
	
	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	
	private static SimpleDateFormat df = new SimpleDateFormat(pattern);
	
	private Socket socket = null;
	
	private SBYincommMain mSBYincommMain ;
	private SBYincommSub tSBYincommSub ;
	private List<SBYincommSub> tSBYincommSubList ;
	private String sourceCodeStr ;
    private String responseCodeStr ;
	
	public WirelessYTTask(Socket socket){
		this.socket = socket;
	}
	
	@Override
	public void run() {
        try {
            String socketTaskName = df.format(new Date()) ;
            mLogger.info("WirelessYTTask("+socketTaskName+") start ,线程号====" + Thread.currentThread());

            int dataCount = 1;
            InputStream in = socket.getInputStream();
            ByteArrayOutputStream bo ;
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
            mLogger.info("WirelessYTTask("+socketTaskName+") end   ,线程号====" + Thread.currentThread());
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
	
	private byte [] dealDatas(byte[] datas){
        byte [] returnData = null;
		/*
		String res = "5B3A295D"   // 起始符：4字节 ([:)])
				   + "840B"       // 帧类型：2字节 (b[15]:需确认 b[14]:确认帧 b[13]:保留  b[12-10]:版本号 b[9-8]:加密类型 b[7-0]:消息类型)
				   + "0040"       // 帧长度：2字节 (帧头+帧负荷长度)
				   + "000000001F260064"   // 序列号：8字节 (上行帧时填充 MagicMote 的唯一序列号。下行帧不填数据。)
				   + "0002"      // 帧序号：2字节 (帧号用于标识帧的唯一性。除确认帧外，每发送一帧数据，帧号应加 1。确认帧的帧号应该填入需确认帧的帧号。)
				   + "5C2A"      // 校验码：2字节 (包含整帧（帧头+帧负荷）crc 校验和，计算时本字段填充为 0。源码请参考"crc 计算"这一章节。)
				   + "05E6"      // 采集器序列号(2+8) 1510
				   + "0003108EF03A3E3E"
                    2 + 2  16比特short

					   + "05E7"      // 传感器编号(2+2) 1511
					   + "0001"
					   + "03E8"      // 温度(2+4) 1000
					   + "41B4CCCD"
					   + "07E1"      // 传感器状态(2+4)  2017
					   + "00000001"

				    2 + 2  16比特short

				   + "07D3"          // 供电状态(2+4)  2003
				   + "0004"
				   + "07D4"          // 电池电压  2004
				   + "41B4CCCD"
				   + "0640"          // 时间戳  1600
				   + "56695154";

				   消息类型：254
				   帧内容前4个字节，设置服务器参数，5
				   字符串：127.0.0.1:9876
				   0

                    4个字节，0-成功，其他不成功。

                   消息类型：253
				   帧内容前4个字节，设置服务器参数，5
				   字符串：127.0.0.1:9876
                   0
			*/
		/*
		String res = "5B3A295D880B00E60003108EF06F9DF40007EE0A05E60003108EF06F9DF407E2FFCC05E7000103E8419B333307E10000000007E2FFE205E7000203E84196666607E10000000007E2FFE205E7000303E84198000007E10000000007E2FFE205E7000403E8419CCCCD07E10000000007E2FFE205E7000503E8419B333307E10000000007E2FFE205E7000603E84199999A07E10000000007E2FFE205E7000103F241D4CCCD07E10000000007E2FFE205E700010410443CD33307E10000000007E2FFE205E700010456424C000007E10000000007E2FFE207D3000507D440851FC90640585251F1";
//		res = "5B3A295D8805005B0003108EF06F9DF4000436F605E60003108EF06F9DF407E00000008D07D80456312E3107D70F56303033523030314330315350374107E3045343383007E4143839383630306631303931366637303030303839";
		datas = StringHexUtil.hexString2Bytes(res);
        */
        if(datas == null){
            mLogger.error("Error：数据为空。");
            return returnData;
        }
        mLogger.info("数据长度   = " + datas.length);
        if (datas.length<20) {
            mLogger.error("Error：数据长度小于20。");
            return returnData;
        }

        int datas_index = 0;
        // 起始符(4字节)
        if (datas[datas_index++] != WirelessYTConstants.HEADBYTE[0]
                || datas[datas_index++] != WirelessYTConstants.HEADBYTE[1]
                || datas[datas_index++] != WirelessYTConstants.HEADBYTE[2]
                || datas[datas_index++] != WirelessYTConstants.HEADBYTE[3]) {
            mLogger.error("Error：起始符有误。");
            return returnData;
        }
        // 帧类型(2字节)
        String messageStr1 = StringHexUtil.b2BS(datas[datas_index++]);
        boolean needreturn = messageStr1.substring(0, 1).equals("1");   // 是否需要应答帧
        String versionNo = messageStr1.substring(3, 6);
        String aesType = messageStr1.substring(6, 8);   //0-无加密  1-AES_128  2-AES_196   3-AES_256
        byte messageType = datas[datas_index++]; // 1-主动上报数据   2-设备信息查询   3-时间同步

        // 数据长度(2字节)
        byte [] temp1 = {(byte)0,(byte)0,datas[datas_index++],datas[datas_index++]};
        if(ByteNumUtil.bytesToInt(temp1) != datas.length){
            mLogger.error("Error：数据标识长度与实际长度不符。");
            return returnData;
        }

        // 设备序列号(8字节)
        byte [] temp2 = {datas[datas_index++],datas[datas_index++],datas[datas_index++],datas[datas_index++],
                datas[datas_index++],datas[datas_index++],datas[datas_index++],datas[datas_index++]};
        String deviceSN = StringHexUtil.bytes2HexString(temp2);
        long deviceSN_long = ByteNumUtil.bytes2Long(temp2);
        mLogger.info("序列号：" + deviceSN + "(" + deviceSN_long + ")");

        // 帧号(2字节)
        byte [] temp3 = {(byte)0,(byte)0,datas[datas_index++],datas[datas_index++]};
        String dataSN = StringHexUtil.bytes2HexString(temp3);
        mLogger.info("帧号："+ dataSN);

        //  生成返回数据
        if(needreturn){
            returnData = genResponseByte(messageType,temp3);
        }

        // 数据CRC校验码(2字节)
        byte [] tempCRC = {(byte)0,(byte)0,datas[datas_index++],datas[datas_index++]};

        byte [] temp4 = datas.clone();
        temp4[18] = 0;
        temp4[19] = 0;
        byte[] crcCal = CRC16_Modbus.getSendBuf(StringHexUtil.bytes2HexString(temp4));
        if(crcCal[0] != tempCRC[2] || crcCal[1] != tempCRC[3]){
            mLogger.error("Error：CRC验证失败。");
            return returnData;
        }

        // 初始化各变量信息
        initSomeVariable();

        sourceCodeStr = StringHexUtil.bytes2HexString(datas) ;  // 接收帧原始内容
        responseCodeStr = StringHexUtil.bytes2HexString(returnData) ;  // 确认帧内容

        int loopId = 1;
        int IdBit = 0;
        Date dataDate = null;
        int initLoopIndex = datas_index ;

        while((initLoopIndex + IdBit)<datas.length){
            byte [] temp5 = {(byte)0,(byte)0,datas[initLoopIndex+IdBit],datas[initLoopIndex+IdBit+1]};

            int tId = ByteNumUtil.bytesToInt(temp5);

            String tName = ""; // 名称
            String tValueType = WirelessYTConstants.VALUE_TYPE_NULL; // 值类型
            int tValueLength = 0; // 值占用的字节数
            String precision = ""; // 精确数
            if(tId == WirelessYTConstants.ID_TEMPERATURE_1000){
                tName = "温度";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_OUTSIDE_TEMPERATURE_1005){
                tName = "室外温度";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_HUMIDITY_1010){
                tName = "湿度";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_OUTSIDE_HUMIDITY_1015){
                tName = "室外湿度";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_NH4_1020){
                tName = "氨气";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_H2S_1030){
                tName = "硫化氢";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_CO2_1040){
                tName = "二氧化碳";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_AIR_PRESSURE_1050){
                tName = "气压";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.1";
            }else if(tId == WirelessYTConstants.ID_INTERFACESN_1511){
                tName = "接口编号";
                tValueType = WirelessYTConstants.VALUE_TYPE_INT;
                tValueLength = 2;
            }else if(tId == WirelessYTConstants.ID_ILUMINATION_1110){
                tName = "光照";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.01";
            }else if(tId == WirelessYTConstants.ID_SENSOR_STATUS_2017){
                tName = "传感器状态";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING_2;
                tValueLength = 4;
            }else if(tId == WirelessYTConstants.ID_MMSN_1510){
                tName = "MagicMote序列号";
                tValueType = WirelessYTConstants.VALUE_TYPE_INT;
                tValueLength = 8;
            }else if(tId == WirelessYTConstants.ID_TIME_1600){
                tName = "时间戳";
                tValueType = WirelessYTConstants.VALUE_TYPE_DATETIME;
                tValueLength = 4;
            }else if(tId == WirelessYTConstants.ID_MM_POWER_STATUS_2003){
                tName = "MagicMote供电状态";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING_2;
                tValueLength = 2;
            }else if(tId == WirelessYTConstants.ID_VOLTAGE_2004){
                tName = "电池电压";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.01";
            }else if(tId == WirelessYTConstants.ID_RUNTIME_2006){
                tName = "运行时间";
                tValueType = WirelessYTConstants.VALUE_TYPE_INT;
                tValueLength = 4;
            }else if(tId == WirelessYTConstants.ID_SOFTWARE_VERSION_2007){
                tName = "软件版本";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING;
                tValueLength = 0;
            }else if(tId == WirelessYTConstants.ID_HARDWARE_VERSION_2008){
                tName = "硬件版本";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING;
                tValueLength = 0;
            }else if(tId == WirelessYTConstants.ID_ELEC_QUANTITY_60003){
                tName = "总有功电量";
                tValueType = WirelessYTConstants.VALUE_TYPE_FLOAT;
                tValueLength = 4;
                precision = "0.01";
            }else if(tId == WirelessYTConstants.ID_MM_PROPERTY_2016){
                tName = "Magicmote属性";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING_2;
                tValueLength = 4;
            }else if(tId == WirelessYTConstants.ID_12_CS_60004){
                tName = "12路电流监测";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING_2;
                tValueLength = 4;
            }else if(tId == WirelessYTConstants.ID_SIGNAL_STRENGTH_2018){
                tName = "信号强度";
                tValueType = WirelessYTConstants.VALUE_TYPE_SHORT;
                tValueLength = 2;
            }else if(tId == WirelessYTConstants.ID_MM_PROPERTY_2019){
                tName = "产品信息";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING;
                tValueLength = 0;
            }else if(tId == WirelessYTConstants.ID_SIM_ICCID_2020){
                tName = "SIM_ICCID";
                tValueType = WirelessYTConstants.VALUE_TYPE_STRING;
                tValueLength = 0;
            }else{
                mLogger.error("Error：发现未知的数据ID类型。ID="+tId);
                return returnData;
            }
            IdBit += 2;

            if(tValueLength == 0){
                if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_STRING)){
                    byte [] tTempStrLength = new byte[4];
                    tTempStrLength[0] = (byte)0;
                    tTempStrLength[1] = (byte)0;
                    tTempStrLength[2] = (byte)0;
                    tTempStrLength[3] = datas[initLoopIndex+IdBit];
                    IdBit += 1;
                    tValueLength = ByteNumUtil.bytesToInt(tTempStrLength);
                }else{
                    mLogger.error("ID="+tId+" Error：数据长度是0，但是类型不是字符串。");
                    return returnData;
                }
            }

            byte [] tValueBytes = new byte[tValueLength];
            for(int in = 0; in < tValueBytes.length; in ++){
                tValueBytes[in] = datas[initLoopIndex+IdBit+in] ;
            }
            String tRealVal = "";
            if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_INT)){
                if(tValueLength == 2){
                    byte [] intBytes = {(byte)0,(byte)0,tValueBytes[0],tValueBytes[1]};
                    tRealVal += ByteNumUtil.bytesToInt(intBytes);
                }else if(tValueLength == 4){
                    tRealVal += ByteNumUtil.bytesToInt(tValueBytes);
                }else if(tValueLength == 8){
                    tRealVal += ByteNumUtil.bytes2Long(tValueBytes);
                }else{
                    mLogger.error("Error：Value类型是Int,但是长度异常。");
                    return returnData;
                }
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_SHORT)){
                if(tValueLength == 2){
                    tRealVal += ByteNumUtil.bytesToShort(tValueBytes);
                }else{
                    mLogger.error("Error：Value类型是Short,但是长度异常");
                    return returnData;
                }
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_FLOAT)){
                if(tValueLength == 4){
                    tRealVal += Float.intBitsToFloat(ByteNumUtil.bytesToInt(tValueBytes));
                }else{
                    mLogger.error("Error：Value类型是Float,但是长度异常");
                    return returnData;
                }
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_STRING)){
                tRealVal += StringHexUtil.bytes2String(tValueBytes);
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_DATETIME)){
                tRealVal += ByteNumUtil.bytesToInt(tValueBytes);
                dataDate = new Date(Long.parseLong(tRealVal)*1000);
                mLogger.info("dataDate ：" + dataDate);
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_STRING_2)){
                tRealVal = StringHexUtil.bytes2BinaryString(tValueBytes);
            }else if(tValueType.equals(WirelessYTConstants.VALUE_TYPE_NULL)){
                tRealVal = StringHexUtil.bytes2HexString(tValueBytes);
            }

            mLogger.info("(" + StringHexUtil.b2HS(temp5[2]) + StringHexUtil.b2HS(temp5[3]) + ")");
            mLogger.info(tId + "-" + tName + "-" + tRealVal );
            mLogger.info("(" + StringHexUtil.bytes2HexString(tValueBytes)+ "," + tValueLength + "字节,"+tValueType + ")");
            mLogger.info("");

            // 对数据进行处理
            operateData(tId, tRealVal);

            IdBit += tValueLength;
            if((initLoopIndex + IdBit)==datas.length){
                mLogger.info("Success");
                break;
            }else if((initLoopIndex + IdBit)>datas.length){
                mLogger.error("Error：数据循环异常结束。");
                break;
            }

            loopId ++;
            if(loopId >100){
                mLogger.error("Error：数据循环超过100次，发生异常。");
                return returnData;
            }
        }

        // 数据提交
        commitData(String.valueOf(deviceSN_long), messageType, dataSN );

        return returnData;
    }
	public static void main(String []dd){
		WirelessYTTask tYINCommSocketTask =new WirelessYTTask(null);
		tYINCommSocketTask.dealDatas(null);
	}
	
	private byte[] genResponseByte(byte messageType,byte[] frameSN){
        byte [] ResponseByte ;
        if(messageType == 3){
            ResponseByte = new byte[24];
        }else{
            ResponseByte = new byte[20];
        }

        // 起始符 4字节
        ResponseByte[0] = WirelessYTConstants.HEADBYTE[0];
        ResponseByte[1] = WirelessYTConstants.HEADBYTE[1];
        ResponseByte[2] = WirelessYTConstants.HEADBYTE[2];
        ResponseByte[3] = WirelessYTConstants.HEADBYTE[3];

        // 帧类型，2字节
        ResponseByte[4] = 0x44; // 0100 0100
        ResponseByte[5] = messageType;

        // 帧长度，2字节
        byte[] len = ByteNumUtil.intToBytes(ResponseByte.length);
        ResponseByte[6] = len[2];
        ResponseByte[7] = len[3];
        // 序列号  4字节
        ResponseByte[8] = 0;
        ResponseByte[9] = 0;
        ResponseByte[10] = 0;
        ResponseByte[11] = 0;
        ResponseByte[12] = 0;
        ResponseByte[13] = 0;
        ResponseByte[14] = 0;
        ResponseByte[15] = 0;

        // 帧序号  2字节
        ResponseByte[16] = frameSN[2];
        ResponseByte[17] = frameSN[3];
        // CRC 2字节
        ResponseByte[18] = 0;
        ResponseByte[19] = 0;

        if(messageType == 3){
            int curTimes =  (int)((new Date()).getTime()/1000);
            byte[] curTimeByte = ByteNumUtil.intToBytes(curTimes);

            ResponseByte[20] = curTimeByte[0];
            ResponseByte[21] = curTimeByte[1];
            ResponseByte[22] = curTimeByte[2];
            ResponseByte[23] = curTimeByte[3];
        }

        byte[] crcAck = CRC16_Modbus.getSendBuf(StringHexUtil.bytes2HexString(ResponseByte));
        ResponseByte[18] = crcAck[0];
        ResponseByte[19] = crcAck[1];
        return ResponseByte;
    }
	
	private void initSomeVariable(){
		mSBYincommMain = new SBYincommMain();
		tSBYincommSub = null;
		tSBYincommSubList = new ArrayList<SBYincommSub>();
        sourceCodeStr = null ;
        responseCodeStr = null ;
    }

    private void operateData(int tId, String tRealVal){
    	try {
			if(tId== WirelessYTConstants.ID_MMSN_1510){
				// 1510 Magicmote设备唯一的序列号
				mSBYincommMain.setMmSn(tRealVal);
			}else if(tId== WirelessYTConstants.ID_MM_POWER_STATUS_2003){
				// 2003 Magicmote供电状态
				mSBYincommMain.setPowerStatus(tRealVal);
			}else if(tId== WirelessYTConstants.ID_VOLTAGE_2004){
				// 2004 电池电压
				mSBYincommMain.setVoltage(PubFun.getBigDecimalData(tRealVal));
			}else if(tId== WirelessYTConstants.ID_TIME_1600){
				// 1600 时间戳
				mSBYincommMain.setYtdataTime(new Date(Long.valueOf(tRealVal)*1000));
			}else if(tId== WirelessYTConstants.ID_SOFTWARE_VERSION_2007){
				// 2007 软件版本
				mSBYincommMain.setSoftwareVersion(tRealVal);
			}else if(tId== WirelessYTConstants.ID_HARDWARE_VERSION_2008){
				// 2008 硬件版本
				mSBYincommMain.setHardwareVersion(tRealVal);
			}else if(tId== WirelessYTConstants.ID_MM_PROPERTY_2016){
				// 2016 MM属性
				mSBYincommMain.setProperty(tRealVal);
			}else if(tId== WirelessYTConstants.ID_RUNTIME_2006){
				// 2006 MM运行时间
				mSBYincommMain.setRunTime(Integer.parseInt(tRealVal));
			}else if(tId == WirelessYTConstants.ID_MM_PROPERTY_2019){
                // 2019 产品信息
				mSBYincommMain.setDeviceInfo(tRealVal);
            }else if(tId == WirelessYTConstants.ID_SIM_ICCID_2020){
                // 2020  SIM_iccid
            	mSBYincommMain.setSimIccid(tRealVal);
            }else{
				// 1511 接口编号
				if(tId == WirelessYTConstants.ID_INTERFACESN_1511){
					tSBYincommSub = new SBYincommSub();
					tSBYincommSub.setMmSn(mSBYincommMain.getMmSn());
					tSBYincommSub.setSensorSn(Integer.parseInt(tRealVal));
			   	// 2017 传感器状态            		
				}else if(tId == WirelessYTConstants.ID_SENSOR_STATUS_2017){
					tSBYincommSub.setSensorStatus(tRealVal);
				// 2018 信号强度 
				}else if(tId == WirelessYTConstants.ID_SIGNAL_STRENGTH_2018){
                    if(tSBYincommSubList.size() == 0 && tSBYincommSub == null){
                    	mSBYincommMain.setSignalStrength(Integer.valueOf(tRealVal));
                    }else{
                    	tSBYincommSub.setSignalStrength(Integer.valueOf(tRealVal));
                    	if(tSBYincommSub != null 
    							&& !PubFun.isNull(tSBYincommSub.getSensorCode())
    							&& !PubFun.isNull(tSBYincommSub.getMmSn())
    							&& tSBYincommSub.getSensorSn() != 0
    							){
    						tSBYincommSubList.add(tSBYincommSub);
    					}
                    }
                }else if(tId == WirelessYTConstants.ID_TEMPERATURE_1000
						|| tId == WirelessYTConstants.ID_HUMIDITY_1010
						|| tId == WirelessYTConstants.ID_CO2_1040
						|| tId == WirelessYTConstants.ID_ILUMINATION_1110
						|| tId == WirelessYTConstants.ID_OUTSIDE_TEMPERATURE_1005
						|| tId == WirelessYTConstants.ID_AIR_PRESSURE_1050){
					tSBYincommSub.setSensorCode(String.valueOf(tId));
					tSBYincommSub.setValueType(2);
					tSBYincommSub.setSpecialFlag("N");
					tSBYincommSub.setValueNum(PubFun.getBigDecimalData(tRealVal));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			tSBYincommSubList = null;
		}
    }

    private void commitData(String MM_SN, int messageType, String dataSN){
    	try {
    		if(StringUtils.isEmpty(mSBYincommMain.getMmSn())){
    			mSBYincommMain.setMmSn(MM_SN);
            }
    		
			SBYincomm mSBYincomm = new SBYincomm();
			mSBYincomm.setMakeTime(new Date());
			mSBYincomm.setYtdataTime(mSBYincommMain.getYtdataTime());
			mSBYincomm.setFrameType(String.valueOf(messageType));
			mSBYincomm.setMmSn(mSBYincommMain.getMmSn());
			mSBYincomm.setFrameSn(dataSN);
			mSBYincomm.setReceiveData(sourceCodeStr);
			mSBYincomm.setConfirmData(responseCodeStr);
			mSBYincomm.setProterty(mSBYincommMain.getProperty());
			mSBYincomm.setPowerStatus(mSBYincommMain.getPowerStatus());
			mSBYincomm.setVoltage(mSBYincommMain.getVoltage());
			mSBYincomm.setRunTime(mSBYincommMain.getRunTime());
			mSBYincomm.setHardwareVersion(mSBYincommMain.getHardwareVersion());
			mSBYincomm.setSoftwareVersion(mSBYincommMain.getSoftwareVersion());
			mSBYincomm.setSignalStrength(mSBYincommMain.getSignalStrength());
			mSBYincomm.setDeviceInfo(mSBYincommMain.getDeviceInfo());
			mSBYincomm.setSimIccid(mSBYincommMain.getSimIccid());
			
			if(tSBYincommSubList != null){
				for(int i = 0; i < tSBYincommSubList.size(); i++){
					if(tSBYincommSubList.get(i).getSensorCode().equals("1000")){
						if(tSBYincommSubList.get(i).getSensorSn() == 1){
							mSBYincomm.setTemp1(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 2){
							mSBYincomm.setTemp2(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 3){
							mSBYincomm.setTemp3(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 4){
							mSBYincomm.setTemp4(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 5){
							mSBYincomm.setTemp5(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 6){
							mSBYincomm.setTemp6(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 7){
							mSBYincomm.setTemp7(tSBYincommSubList.get(i).getValueNum());
						}
					}else if(tSBYincommSubList.get(i).getSensorCode().equals("1010")){
						if(tSBYincommSubList.get(i).getSensorSn() == 1){
							mSBYincomm.setHumi1(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 2){
							mSBYincomm.setHumi2(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 3){
							mSBYincomm.setHumi3(tSBYincommSubList.get(i).getValueNum());
						}
					}else if(tSBYincommSubList.get(i).getSensorCode().equals("1040")){
						if(tSBYincommSubList.get(i).getSensorSn() == 1){
							mSBYincomm.setCo21(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 2){
							mSBYincomm.setCo22(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 3){
							mSBYincomm.setCo23(tSBYincommSubList.get(i).getValueNum());
						}
					}else if(tSBYincommSubList.get(i).getSensorCode().equals("1110")){
						if(tSBYincommSubList.get(i).getSensorSn() == 1){
							mSBYincomm.setLux1(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 2){
							mSBYincomm.setLux2(tSBYincommSubList.get(i).getValueNum());
						}else if(tSBYincommSubList.get(i).getSensorSn() == 3){
							mSBYincomm.setLux3(tSBYincommSubList.get(i).getValueNum());
						}
					}
				}
			}
			HashMap<String,Object> tPara = new HashMap<String,Object>();
			tPara.put("SBYincomm", mSBYincomm);
			tPara.put("SBYincommMain", mSBYincommMain);
			tPara.put("SBYincommSubList", tSBYincommSubList);
			
			mSBYincommManager.dealSave(tPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public SBYincommManager getSBYincommManager() {
		return mSBYincommManager;
	}

	public void setSBYincommManager(SBYincommManager mSBYincommManager) {
		this.mSBYincommManager = mSBYincommManager;
	}
}

/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.device.bbfar;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mtc.app.biz.DeviceBbfReqManager;
import com.mtc.device.bbfar.common.BBFarConstants;

/**
 * @ClassName: BBFarService
 * @Description: 
 * @Date 2015年12月22日 上午11:00:28
 * @Author Yin Guo Xiang
 * 
 */
public class BBFarService implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(BBFarService.class);
	
	private static BBFarService mBBFarService = null;
	
	private static ServerSocket mServerSocket = null;
	
	private DeviceBbfReqManager tDeviceBbfReqManager = null;
	
	private Socket Socket = null;  
	
	private String serverState = BBFarConstants.STOP_02;
	
	public static void main(String[] args) {
		BBFarService tBBFarService = new BBFarService();
		tBBFarService.run();
	}
	
	@Override
	public void run() {
		try {
			if(tDeviceBbfReqManager == null){
				mLogger.error("DeviceBbfReqManager 注入失败，笔笔发服务启动失败。");
				return;
			}
			
			if(mServerSocket == null || mServerSocket.isClosed()){
				mServerSocket = new ServerSocket(BBFarConstants.PORTNO);
				
				if(mServerSocket == null){
					mLogger.error("笔笔发服务启动失败。");
					return;
				}
				
				mLogger.info("笔笔发服务启动成功。端口号：" + BBFarConstants.PORTNO);
				serverState = BBFarConstants.RUN_01;
				
				while (true)  
				{
				    try  
				    {
						Socket = mServerSocket.accept();
				    	
				    	BBFarDealData tSocketTask = new BBFarDealData(Socket);  
				    	tSocketTask.settDeviceBbfReqManager(tDeviceBbfReqManager);
				    	
				    	Thread tThread = new Thread(tSocketTask);
				    	tThread.start();
				    	
				        Thread.sleep(2000);  
				    }
				    catch (Exception e)  
				    {
				        e.printStackTrace();  
				    }
				}
			}else{
				serverState = BBFarConstants.RUN_01;
				mLogger.info("笔笔发服务已经启动，禁止重复启用。");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(Socket != null){
				try {
					Socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(mServerSocket != null){
				try {
					mServerSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			serverState = BBFarConstants.STOP_02;
		}
	}
	
	public static BBFarService getInstance(){
		if(mBBFarService == null){
			mBBFarService = new BBFarService();
		}
		return mBBFarService ;
	}
	
	private BBFarService()  
    {
    }
	
	public String getServerState() {
		return serverState;
	}

	public void setServerState(String serverState) {
		this.serverState = serverState;
	}

	public DeviceBbfReqManager gettDeviceBbfReqManager() {
		return tDeviceBbfReqManager;
	}

	public void settDeviceBbfReqManager(DeviceBbfReqManager tDeviceBbfReqManager) {
		this.tDeviceBbfReqManager = tDeviceBbfReqManager;
	}
}

/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.device.yincomm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.mtc.app.biz.SBYincommManager;
import com.mtc.device.yincomm.common.WirelessYTConstants;

/**
 * @ClassName: YINCommService
 * @Description: 
 * @Date 2015年12月22日 上午11:00:28
 * @Author Yin Guo Xiang
 * 
 */
public class YINCommService implements Runnable {
	
	private static Logger mLogger =Logger.getLogger(YINCommService.class);
	
	private static YINCommService mYINCommService = null;
	
	private SBYincommManager mSBYincommManager;
	
	private static ServerSocket mServerSocket = null;
	
	private Socket Socket = null;  
	
	private String serverState = WirelessYTConstants.STOP_02;
	
	@Override
	public void run() {
		try {
			if(mServerSocket == null || mServerSocket.isClosed()){
				mServerSocket = new ServerSocket(WirelessYTConstants.PORTNO);
				
				if(mServerSocket == null){
					mLogger.info("引通服务启动失败。");
					return;
				}
				
				mLogger.info("引通服务启动成功。端口号：" + WirelessYTConstants.PORTNO);
				serverState = WirelessYTConstants.RUN_01;
				
				while (true)  
				{
				    try  
				    {
						Socket = mServerSocket.accept();
				    	
				    	WirelessYTTask tSocketTask = new WirelessYTTask(Socket);  
				    	tSocketTask.setSBYincommManager(mSBYincommManager);
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
				serverState = WirelessYTConstants.RUN_01;
				mLogger.info("引通服务已经启动。");
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
			serverState = WirelessYTConstants.STOP_02;
		}
	}
	
	public static YINCommService getInstance(){
		if(mYINCommService == null){
			mYINCommService = new YINCommService();
		}
		return mYINCommService ;
	}
	
	private YINCommService()  
    {
    }
	
	public String getServerState() {
		return serverState;
	}

	public void setServerState(String serverState) {
		this.serverState = serverState;
	}

	public SBYincommManager getSBYincommManager() {
		return mSBYincommManager;
	}

	public void setSBYincommManager(SBYincommManager mSBYincommManager) {
		this.mSBYincommManager = mSBYincommManager;
	}
}

/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mtc.app.biz.DeviceBbfReqManager;
import com.mtc.app.biz.SBYincommManager;
import com.mtc.common.util.IPUtil;
import com.mtc.device.bbfar.BBFarService;
import com.mtc.device.yincomm.YINCommService;

/**
 * @ClassName: StartupListener
 * @Description: 
 * @Date 2015年12月22日 下午1:56:45
 * @Author Yin Guo Xiang
 * 
 */
public class StartupListener implements ServletContextListener{
	
	private static Logger mLogger =Logger.getLogger(StartupListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()); //获取spring上下文！  
		SBYincommManager tSBYincommManager = (SBYincommManager)app.getBean("SBYincommManager");
		DeviceBbfReqManager tDeviceBbfReqManager = (DeviceBbfReqManager)app.getBean("deviceBbfReqManager");
		
		startYincommService(tSBYincommManager);
		startBBFarService(tDeviceBbfReqManager);
	}
	
	/**
	 *  启动笔笔发服务
	 */
	private void startBBFarService(DeviceBbfReqManager tDeviceBbfReqManager){
		mLogger.info("=====start startBBFarService()");
		try {
			BBFarService mBBFarService = BBFarService.getInstance();
			mBBFarService.settDeviceBbfReqManager(tDeviceBbfReqManager);
			
			new Thread(mBBFarService).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====end startBBFarService()");
	}
	
	/**
	 *  启动引通服务
	 */
	private void startYincommService(SBYincommManager tSBYincommManager){
		mLogger.info("=====start startYincommService()");
		try {
			YINCommService mYINCommService = YINCommService.getInstance();
			mYINCommService.setSBYincommManager(tSBYincommManager);
			
			new Thread(mYINCommService).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====end startYincommService()");
	}
}

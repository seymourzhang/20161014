/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.mtc.app.service.MySQLSPService;
import com.mtc.common.util.IPUtil;

/**
 * @ClassName: YINComTimerTask
 * @Description: 
 * @Date 2015年12月22日 下午5:19:37
 * @Author Yin Guo Xiang
 * 
 */

public class TransYINCommDataJob{
	
	private static Logger mLogger =Logger.getLogger(TransYINCommDataJob.class);
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	private static int counter = 0;
	
	@Scheduled(cron="0/60 * * * * ?") 
	public void Service() {
		if(!IPUtil.needRunTask()){
			return ;
		}
		counter ++ ;
		mLogger.info("=====Now 开始  第 " + counter +" 次 TransYINCommDataJob.Service");
		try {
          String resl = tMySQLSPService.exec_s_p_TransYINCommData();
          mLogger.info("=====exec_s_p_TransYINCommData 执行结果：" + resl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now 结束  第 " + counter +" 次 TransYINCommDataJob.Service");
	}
	
	
}

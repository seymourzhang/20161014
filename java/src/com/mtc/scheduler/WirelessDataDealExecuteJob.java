/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.scheduler;

import com.mtc.app.service.MySQLSPService;
import com.mtc.common.util.IPUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: AlarmDealExecuteJob
 * @Description: 
 * @Date 2015年11月30日 上午11:24:32
 * @Author Yin Guo Xiang
 * 
 */

public class WirelessDataDealExecuteJob {
	private static Logger mLogger =Logger.getLogger(WirelessDataDealExecuteJob.class);
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	private static int counter = 0;
	
	public void Service() {
		if(!IPUtil.needRunTask()){
			return ;
		}
		counter ++ ;
		mLogger.info("=====Now 开始  第 " + counter +" 次 WirelessDataDealExecuteJob.Service");
		try {

            String resl = tMySQLSPService.exec_s_p_dealWirelessData();
            mLogger.info("=====执行结果：" + resl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mLogger.info("=====Now 结束  第 " + counter +" 次 WirelessDataDealExecuteJob.Service");
	}
}

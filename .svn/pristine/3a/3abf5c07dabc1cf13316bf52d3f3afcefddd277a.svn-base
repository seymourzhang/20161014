/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBYincommDataService;
import com.mtc.entity.app.SBYincomm;
import com.mtc.entity.app.SBYincommMain;
import com.mtc.entity.app.SBYincommSub;

/**
 * @ClassName: SBYincommManager
 * @Description: 
 * @Date 2015年12月22日 下午3:39:44
 * @Author Yin Guo Xiang
 * 
 */
@Component
public class SBYincommManager {
	
	private static Logger mLogger =Logger.getLogger(SBYincommManager.class);
	
	@Autowired
	private SBYincommDataService tSBYincommDataService;
	
	public void dealSave(HashMap<String,Object> tPara){
		SBYincommMain tSBYincommMain = (SBYincommMain)tPara.get("SBYincommMain");
		SBYincomm tSBYincomm = (SBYincomm)tPara.get("SBYincomm");
		
		tSBYincommDataService.insertOriginalData(tSBYincomm);
		tSBYincommMain.setMakeTime(new Date());
		tSBYincommDataService.updateMainData(tSBYincommMain);
		
		ArrayList<SBYincommSub> tSBYincommSubList = (ArrayList<SBYincommSub>)tPara.get("SBYincommSubList");
		
		if(tSBYincommSubList != null && tSBYincommSubList.size() > 0){
			for(SBYincommSub tSBYincommSub:tSBYincommSubList){
				tSBYincommSub.setYtdataTime(tSBYincommMain.getYtdataTime());
				tSBYincommSub.setMakeTime(tSBYincommMain.getMakeTime());
				tSBYincommDataService.deleteSub(tSBYincommSub);
				tSBYincommDataService.insetSub(tSBYincommSub);
			}
		}
	}
}

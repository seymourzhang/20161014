/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBCallAlarmService;
import com.mtc.app.service.SBCallDetailService;
import com.mtc.app.service.SBCallMainService;
import com.mtc.app.service.SBRemindAlarmcodeService;
import com.mtc.app.service.SBRemindAlarmerService;
import com.mtc.app.service.SBRemindSettingService;
import com.mtc.app.service.SBRemindSwitchService;
import com.mtc.entity.app.SBCallAlarm;
import com.mtc.entity.app.SBCallDetail;
import com.mtc.entity.app.SBCallMain;
import com.mtc.entity.app.SBRemindAlarmcode;
import com.mtc.entity.app.SBRemindSetting;
import com.mtc.entity.app.SBRemindSwitch;
import com.mtc.entity.app.SBReminder;


@Component
public class RemindReqManager {
	
	private static Logger mLogger =Logger.getLogger(RemindReqManager.class);
	
	@Autowired
	private SBRemindSettingService remindSettingService;

	@Autowired
	private SBRemindAlarmcodeService alarmcodeService;
	
	@Autowired
	private SBRemindAlarmerService alarmerService;
	
	@Autowired
	private SBRemindSwitchService alarmSwitchService;
	
	@Autowired
	private SBCallMainService callMainService;
	
	@Autowired
	private SBCallAlarmService alarmService;
	
	@Autowired
	private SBCallDetailService detailService;
	
	
	public int insert(SBRemindSetting record){
		return remindSettingService.insert(record);
	}
	
	public int updateByPrimaryKey(SBRemindSetting record){
		return remindSettingService.updateByPrimaryKey(record);
	}
	
	
	public int insertBatch(List<SBReminder> list){
		return alarmerService.insertBatch(list);
	}
	
	
	public int updateByPrimaryKey(SBReminder record){
		return alarmerService.updateByPrimaryKey(record);
	}
	
	
	public int insert(SBRemindSwitch record){
		return alarmSwitchService.insert(record);
	}
	

	public int updateByPrimaryKey(SBRemindSwitch record){
		return alarmSwitchService.updateByPrimaryKey(record);
	}
	
	public int insertCodeBatch(List<SBRemindAlarmcode> list){
		return alarmcodeService.insertBatch(list);
	}
	
	
	public SBCallMain selectCallMainByParams(int farmId,int houseId,String tempId){
		return callMainService.selectCallMainByParams( farmId, houseId, tempId);
	} 
	
	
	public int insert(SBCallMain callMain){
		return callMainService.insert(callMain);
	}
	
	
	public List<SBCallAlarm> getSBCallAlarmByMainId(int mainId){
		return alarmService.getSBCallAlarmByMainId(mainId);
	}
	
	public List<SBCallMain> getNeedCallMainInfo() {
		// TODO Auto-generated method stub
		return callMainService.getNeedCallMainInfo();
	}


	public List<SBCallDetail> getSBCallDetail(int mainId) {
		// TODO Auto-generated method stub
		return detailService.getSBCallDetail(mainId);
	}

	public int updateSBCallMain(SBCallMain main) {
		// TODO Auto-generated method stub
		return callMainService.updateSBCallMain(main);
	}

	public int updateSBCallDetail(SBCallDetail detail) {
		// TODO Auto-generated method stub
		return detailService.updateSBCallDetail(detail);
	}

	public int updateSBCallMainCallTimes(SBCallMain main) {
		// TODO Auto-generated method stub
		return callMainService.updateSBCallMainCallTimes(main);
	}
	
	public int saveCallMainAndOthers(SBCallMain main,List<SBCallAlarm> callAlarms,List<SBCallDetail> details){
		this.insert(main);
		int mainId = main.getId();
		mLogger.info("new mainId:" + mainId);
		if(mainId > 0){
			for(SBCallAlarm alarm : callAlarms){
				alarm.setMainId(mainId);
			}
			if(!callAlarms.isEmpty()){
				alarmService.insertCallAlarmBatch(callAlarms);
			}
			for(SBCallDetail alarm : details){
				alarm.setMainId(mainId);
			}
			if(!details.isEmpty()){
				detailService.insertCallDetails(details);
			}
		}
		return mainId;
	}
	
	
	public int updateSBCallMainAndDetail(SBCallMain main,SBCallDetail detail) {
		// TODO Auto-generated method stub
		int n = callMainService.updateSBCallMain(main);
		int m = detailService.updateSBCallDetail(detail);
		return m + n;
	}
	
	public int updateSBCallMainCallTimesAndDetail(SBCallMain main,SBCallDetail detail) {
		// TODO Auto-generated method stub
		int n = callMainService.updateSBCallMainCallTimes(main);
		int m = detailService.updateSBCallDetail(detail);
		return m + n;
	}
	
	public int saveFarmRemind(SBRemindSetting alarmSetting,
			List<SBRemindAlarmcode> codeSettings,
			List<SBReminder> alarmerList,
			List<SBRemindSwitch> alarmEnableds){
		
		int m1 = this.insert(alarmSetting);
		int m2 = 0;
		if(!codeSettings.isEmpty()){
			m2 = this.insertCodeBatch(codeSettings);
		}
		int m3 = 0;
		if(!alarmerList.isEmpty()){
			m3 = this.insertBatch(alarmerList);
		}
		
		int m4 = 0;
		if(!alarmEnableds.isEmpty()){
			m4 = this.insertBatchSwitch(alarmEnableds);
		}
		
		return m1 + m2 + m3 + m4;
	}
	
	public int updateFarmRemind(SBRemindSetting alarmSetting,int houseId,
			List<SBRemindAlarmcode> codeSettings,
			List<SBReminder> alarmerList,
			List<SBRemindSwitch> alarmEnableds){
		
		int farmId = alarmSetting.getFarmId();
		
		int m1 = this.insert(alarmSetting);
		
		// 删除旧的报警设置记录
		int m = alarmcodeService.deleteByFarmHouseType(farmId,houseId,alarmSetting.getRemindMethod());
		mLogger.info("delete alarm code :" + m);
		int m2 = this.insertCodeBatch(codeSettings);
		
		// 删除旧的报警人
		m = alarmerService.deleteByFarmHouseType(farmId,houseId,alarmSetting.getRemindMethod());
		mLogger.info("delete alarmer  :" + m);
		
		int m3 = 0;
		if(alarmerList.size()>0){
			m3 = this.insertBatch(alarmerList);
		}
		// 删除旧的报警开关
		m = alarmSwitchService.deleteByFarmHouseType(farmId,houseId,alarmSetting.getRemindMethod());
		mLogger.info("delete farm switch  :" + m);
		int m4 = this.insertBatchSwitch(alarmEnableds);
		
		return m1 + m2 + m3 + m4;
	}

	public int insertBatchSwitch(List<SBRemindSwitch> alarmEnableds) {
		// TODO Auto-generated method stub
		return alarmSwitchService.insertBatch(alarmEnableds);
	}

	public List<SBCallDetail> getSBCallDetailByCallResult(int mainId,
			String callResult) {
		// TODO Auto-generated method stub
		return detailService.getSBCallDetailByCallResult(mainId,callResult);
	}
	
	
}


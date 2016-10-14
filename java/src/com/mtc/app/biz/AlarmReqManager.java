/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.SBHouseAlarmService;
import com.mtc.app.service.SBHouseProbeService;
import com.mtc.app.service.SBTempSettingSubService;
import com.mtc.app.service.SBTempSettingService;
import com.mtc.entity.app.SBHouseAlarm;
import com.mtc.entity.app.SBTempSetting;
import com.mtc.entity.app.SBTempSettingSub;
import com.mtc.entity.app.SBHouseProbe;

/**
 * @ClassName: AlarmReqManager
 * @Description: 
 * @Date 2015-11-30 下午2:35:39
 * @Author Shao Yao Yu
 * 
 */
@Component
public class AlarmReqManager {
	
	
	private static Logger mLogger =Logger.getLogger(AlarmReqManager.class);
	@Autowired
	private SBTempSettingService  mSBTempSettingService;
	@Autowired
	private SBTempSettingSubService  tSBTempSettingSubService;
	@Autowired
	private SBHouseAlarmService  tSBHouseAlarmService;
	@Autowired
	private SBHouseProbeService  tSBHouseProbeService;
	
	public int dealSave(HashMap<String,Object> tPara) throws Exception{
		SBTempSetting tSBTempSetting = (SBTempSetting)tPara.get("SBTempSetting");
		SBHouseProbe tSBHouseProbe = (SBHouseProbe)tPara.get("SBHouseProbe");
		JSONObject effAlarmProbe =(JSONObject)tPara.get("effAlarmProbe");
		String        tempLeft1 = effAlarmProbe.getString("tempLeft1");
		String        tempLeft2 = effAlarmProbe.getString("tempLeft2");
		String        tempMiddle1 = effAlarmProbe.getString("tempMiddle1");
		String        tempMiddle2 = effAlarmProbe.getString("tempMiddle2");
		String        tempRight1 = effAlarmProbe.getString("tempRight1");
		String        tempRight2 = effAlarmProbe.getString("tempRight2");
		
		if(tempLeft1.equals("true")){
			tSBHouseProbe.setProbeCode("tempLeft1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempLeft1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if(tempLeft2.equals("true")){
			tSBHouseProbe.setProbeCode("tempLeft2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempLeft2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if(tempMiddle1.equals("true")){
			tSBHouseProbe.setProbeCode("tempMiddle1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempMiddle1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if(tempMiddle2.equals("true")){
			tSBHouseProbe.setProbeCode("tempMiddle2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempMiddle2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if(tempRight1.equals("true")){
			tSBHouseProbe.setProbeCode("tempRight1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempRight1");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		if(tempRight2.equals("true")){
			tSBHouseProbe.setProbeCode("tempRight2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
			tSBHouseProbeService.insertSBHouseProbe(tSBHouseProbe);
		}else{
			tSBHouseProbe.setProbeCode("tempRight2");
			tSBHouseProbeService.deleteByPrimaryKeySBHouseProbe(tSBHouseProbe);
		}
		
		int farm_id = tSBTempSetting.getFarmId();
		int house_id = tSBTempSetting.getHouseId();
		
	    SBTempSetting mSBTempSetting = mSBTempSettingService.selectByFarmIdKeyandHouseId(farm_id, house_id);
	    Integer uidNum = null;
	    if(mSBTempSetting==null){
	    	 mSBTempSettingService.insert(tSBTempSetting);
	    	 uidNum = tSBTempSetting.getId();
	    }else{
	    	 uidNum = mSBTempSetting.getId();
	    	 mSBTempSetting.setModifyPerson(tSBTempSetting.getModifyPerson());
	    	 mSBTempSetting.setModifyDate(tSBTempSetting.getModifyDate());
	    	 mSBTempSetting.setModifyTime(tSBTempSetting.getCreateTime());
	    	 mSBTempSettingService.updateByPrimaryKey(mSBTempSetting);
	    }
	   List<SBTempSettingSub>  tSBTempSettingSub = (List<SBTempSettingSub>) tPara.get("SBTempSettingSub");
	   for (SBTempSettingSub sbTempSettingSub : tSBTempSettingSub) {
		   sbTempSettingSub.setUidNum(uidNum);
		   int UidNum = sbTempSettingSub.getUidNum();
		   int dayAge = sbTempSettingSub.getDayAge();
		   tSBTempSettingSubService.deleteByPrimaryKey(UidNum, dayAge);
		   tSBTempSettingSubService.insert(sbTempSettingSub);
	   }
	   SBHouseAlarm tSBHouseAlarm = (SBHouseAlarm)tPara.get("SBHouseAlarm");
	   int  FarmId=tSBHouseAlarm.getFarmId();
	   int  HouseId=tSBHouseAlarm.getHouseId();
	   SBHouseAlarm mSBHouseAlarm = tSBHouseAlarmService.selectByPrimaryKey(FarmId,HouseId);
	   
	   if(mSBHouseAlarm==null){
		   tSBHouseAlarmService.insert(tSBHouseAlarm);
	   }else{
		   mSBHouseAlarm.setAlarmDelay(tSBHouseAlarm.getAlarmDelay());
		   mSBHouseAlarm.setTempCpsation(tSBHouseAlarm.getTempCpsation());
		   mSBHouseAlarm.setTempCordon(tSBHouseAlarm.getTempCordon());
		   mSBHouseAlarm.setAlarmProbe(tSBHouseAlarm.getAlarmProbe());
		   mSBHouseAlarm.setAlarmWay(tSBHouseAlarm.getAlarmWay());
		   mSBHouseAlarm.setPointAlarm(tSBHouseAlarm.getPointAlarm());
		   mSBHouseAlarm.setModifyDate(tSBHouseAlarm.getModifyDate());
		   mSBHouseAlarm.setModifyPerson(tSBHouseAlarm.getModifyPerson());
		   mSBHouseAlarm.setModifyTime(tSBHouseAlarm.getModifyTime());
		   tSBHouseAlarmService.updateByPrimaryKey(mSBHouseAlarm);
	   }
	   return uidNum ;
	}
}

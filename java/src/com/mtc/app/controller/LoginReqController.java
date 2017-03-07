/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtc.app.biz.LoginReqManager;
import com.mtc.entity.app.*;
import com.mtc.mapper.app.SLUserImeiMapper;
import com.mtc.mapper.app.SLUserLogonMapper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBDeviHouseService;
import com.mtc.app.service.SBUserFarmService;
import com.mtc.app.service.SDFarmService;
import com.mtc.app.service.SDHouseService;
import com.mtc.app.service.SDUserHousesService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.app.service.SDUserRolesService;
import com.mtc.app.service.SDUserService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: LoginReqController
 * @Description: 
 * @Date 2015年11月25日 上午11:11:42
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("/sys/login")
public class LoginReqController {
	
	private static Logger mLogger =Logger.getLogger(LoginReqController.class);  
	@Autowired
	private SDUserService mSDUserService;
	@Autowired
	private SDUserRolesService mSDUserRolesService ;
	@Autowired
	private SDUserHousesService mSDUserHousesService ;
	@Autowired
	private SDHouseService mSDHouseService ;
	@Autowired
	private SDFarmService mSDFarmService ;
	@Autowired
	private SBUserFarmService mSBUserFarmService ;
	@Autowired
	private SBDeviHouseService mSBDeviHouseService ;
	@Autowired
	private BaseQueryService mBaseQueryService;
	
	@Autowired
	private SDUserOperationService operationService;

	@Autowired
	private LoginReqManager loginReqManager;
	
	@RequestMapping("/logIn")
	public void logIn(HttpServletRequest request, HttpServletResponse response){
		mLogger.info("=====Now start executing LoginReqController.logIn");
		
		JSONObject resJson = new JSONObject();
		String dealRes = null ;
		HashMap<String,Object> tParas = new HashMap<>();
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("logIn.para=" + paraStr);
	        
			JSONObject jsonObject = new JSONObject(paraStr);
			
			mLogger.info("jsonObject=" + jsonObject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/

			JSONObject tUserJson = jsonObject.getJSONObject("params");
			
			if(!tUserJson.has("userCode") 
					  || !tUserJson.has("pw")){
				resJson.put("ErrorMsg","登录失败，数据格式错误。");
			}else{
				SDUser mSDUser = mSDUserService.selectByUserCode(tUserJson.optString("userCode")); 
				if(mSDUser != null){
					if(mSDUser.getUserPassword().equals(tUserJson.optString("pw"))){
						JSONObject userInfo = genUserInfo(mSDUser);
						operationService.insert(SDUserOperationService.MENU_LOGIN, SDUserOperationService.OPERATION_SELECT, userInfo.optInt("id"));

						String AndroidImei = tUserJson.optString("AndroidImei");
						String DeviceModel = tUserJson.optString("model");
						String DeviceSysVersion = tUserJson.optString("sysVersion");
						String DevicePlatForm = tUserJson.optString("platForm");
						
						if(AndroidImei == null || AndroidImei.equals("") || AndroidImei.equals("null")){
							
						}else{
							SLUserImei slUserImei = new SLUserImei();
							slUserImei.setImeiNo(AndroidImei);
							slUserImei.setUserId(userInfo.optInt("id"));
							slUserImei.setUserCode(mSDUser.getUserCode());
							slUserImei.setUuid(tUserJson.optString("uuid"));
							slUserImei.setModel(tUserJson.optString("model"));
							slUserImei.setSysVersion(tUserJson.optString("sysVersion"));
							slUserImei.setPlatform(tUserJson.optString("platForm"));
							slUserImei.setCreatePerson(userInfo.optInt("id"));
							slUserImei.setCreateDate(new Date());
							slUserImei.setCreateTime(new Date());
							tParas.put("slUserImei", slUserImei);
						}

						//登陆日志
						SLUserLogon lSLUserLogon = new SLUserLogon();
						lSLUserLogon.setUserCode(tUserJson.optString("userCode"));
						lSLUserLogon.setUserId(userInfo.optInt("id"));
						lSLUserLogon.setCreatePerson(userInfo.optInt("id"));
						lSLUserLogon.setMobileInfo(
												(PubFun.isNull(DeviceModel)?"null":DeviceModel) + 
												"|" + (PubFun.isNull(DeviceSysVersion)?"null":DeviceSysVersion) + 
												"|" + (PubFun.isNull(DevicePlatForm)?"null":DevicePlatForm)
														);
						lSLUserLogon.setCreateDate(new Date());
						lSLUserLogon.setCreateTime(new Date());
						tParas.put("userLogon", lSLUserLogon);
						loginReqManager.dealSave(tParas);

						// 农场信息
						SBUserFarm mSBUserFarm = mSBUserFarmService.selectByUserId(mSDUser.getId());
						JSONObject farminfo = new JSONObject();
						if(mSBUserFarm != null){
							// 返回 农场信息
							SDFarm tSDFarm =mSDFarmService.selectByPrimaryKey(mSBUserFarm.getFarmId());
							farminfo.put("id", tSDFarm.getId());
							farminfo.put("name", tSDFarm.getFarmNameChs());
							farminfo.put("address1", tSDFarm.getFarmAdd1());
							farminfo.put("address2", tSDFarm.getFarmAdd2());
							farminfo.put("address3", tSDFarm.getFarmAdd3());
							farminfo.put("address4", tSDFarm.getFarmAdd4());
							farminfo.put("address5", tSDFarm.getFarmAdd5());
							farminfo.put("feedtype", tSDFarm.getFeedMethod());
							farminfo.put("cageInfo_layer", tSDFarm.getLayers());
							farminfo.put("cageInfo_row", tSDFarm.getRows());
							farminfo.put("corporation", tSDFarm.getCorporation());
							farminfo.put("corporation2", tSDFarm.getCorporation2());
							farminfo.put("house_length", tSDFarm.gethLength());
							farminfo.put("house_width", tSDFarm.gethWidth());
							farminfo.put("house_height", tSDFarm.gethHeight());
							farminfo.put("feedarea", tSDFarm.getFeedDensity());
							farminfo.put("businessModle", tSDFarm.getMemo1());
							
							int maxHouseId = mBaseQueryService.selectIntergerByAny("SELECT COUNT(1) FROM s_d_house a WHERE a.farm_id = " + tSDFarm.getId());
							farminfo.put("house_Maxid", maxHouseId);
							
							int farmBreedBatchId = 0;
							String farmBreedStatus = "00";
							String farmBreedBatchCode = "-";
							List<HashMap<String,Object>> farmBreedInfo  = mBaseQueryService.selectMapByAny("SELECT id,batch_status,batch_code FROM s_b_farm_breed WHERE id = s_f_getFarmBreedId("+tSDFarm.getId()+") ");
							if(farmBreedInfo != null && farmBreedInfo.size()>0){
								farmBreedBatchId = (int)farmBreedInfo.get(0).get("id");
								farmBreedStatus = farmBreedInfo.get(0).get("batch_status").toString();
								farmBreedBatchCode = farmBreedInfo.get(0).get("batch_code").toString();
							}
							farminfo.put("farmBreedBatchId", farmBreedBatchId);
							farminfo.put("farmBreedBatchCode", farmBreedBatchCode);
							farminfo.put("farmBreedStatus", farmBreedStatus);
							resJson.put("farminfo", farminfo);
							
							String tempSQL = "select hd.house_id AS houseId, s_f_getHouseName(hd.house_id) AS houseName, ifnull(c.id, 0) AS houseBreedId, ifnull(c.batch_status,'00') as HouseBreedStatus from "
												+ "( SELECT * FROM s_user_house_view WHERE user_id = "+mSDUser.getId()+" ) hd "
												+ " LEFT JOIN s_b_house_breed c ON c.house_id = hd.house_id AND c.farm_breed_id = " + farmBreedBatchId
												+ " order by hd.house_id " ;
							mLogger.info("=========LoginReqController.logIn.SQL = " + tempSQL);
							List<HashMap<String, Object>> tHousesList = mBaseQueryService.selectMapByAny(tempSQL);
							
							JSONArray tJSONArray = new JSONArray();
							JSONObject houseUserJson = null;
							if(tHousesList != null && tHousesList.size() > 0){
								for(HashMap<String, Object> temp:tHousesList){
									houseUserJson = new JSONObject();
									houseUserJson.put("HouseId", temp.get("houseId"));
									houseUserJson.put("HouseName", temp.get("houseName"));
									houseUserJson.put("HouseBreedBatchId", temp.get("houseBreedId"));
									houseUserJson.put("HouseBreedStatus", temp.get("HouseBreedStatus"));
									tJSONArray.put(houseUserJson);
								}
							}
							userInfo.put("houses", tJSONArray);
							
							// 返回 栋舍信息
							JSONArray houseJsons = new JSONArray();
							
							List<SDHouse> mSDHouses = mSDHouseService.selectHousesByFarmId(tSDFarm.getId());
							
							if(mSDHouses != null && mSDHouses.size() > 0){
								JSONObject houseJson = null;
								for(int i = 0;i<mSDHouses.size();i++){
									if(mSDHouses.get(i).getFreezeStatus().equals("1")){
										continue;
									}
									houseJson = new JSONObject();
									houseJson.put("id", mSDHouses.get(i).getId());
									houseJson.put("houseName", mSDHouses.get(i).getHouseName());
									houseJson.put("h_length", mSDHouses.get(i).getHouseLength());
									houseJson.put("h_width", mSDHouses.get(i).getHouseWidth());
									houseJson.put("h_height", mSDHouses.get(i).getHouseHeight());
									houseJson.put("feedarea", mSDHouses.get(i).getFeedDensity());
									
									SBDeviHouse tSBDeviHouse = mSBDeviHouseService.selectByHouseId(mSDHouses.get(i).getId());  
									String tempDeviceId = "";
									if(tSBDeviHouse!=null){
										tempDeviceId = tSBDeviHouse.getDeviceCode();
									}
									houseJson.put("mtc_device_id", tempDeviceId);
									houseJsons.put(houseJson) ;
								}
							}
							resJson.put("houseinfos", houseJsons);
							
							// 返回员工信息
							JSONObject [] employeeJsons = null;
							List<SDUser> empolyees = mSDUserService.selectByFarmer(mSDUser.getId()); 
							if(empolyees != null && empolyees.size() > 0){
								employeeJsons = new JSONObject[empolyees.size()] ;
								for(int i = 0;i<empolyees.size();i++){
									employeeJsons[i] = genUserInfo(empolyees.get(i));
								}
							}
							resJson.put("userinfos", employeeJsons);
							
							JSONObject authority = new JSONObject();
							// 1:'老板',2:'场长',5:'副场长',6：'统计员',3:'技术员',4:'饲养员',99:'只读用户'
							authority.put("role",userInfo.optInt("role"));

							String sql = "select module_code, value from s_b_auth_mapping where auth_id = " + userInfo.optInt("author_id");
							List<HashMap<String,Object>> lpd = mBaseQueryService.selectMapByAny(sql);
							mLogger.info("=========LoginReqController.authorId_sql = " + sql);
							for (HashMap<String, Object> map : lpd) {
								authority.put(map.get("module_code").toString(), map.get("value"));
							}

							// 1:'老板',2:'场长'
							/*if(userInfo.optInt("role") == 2 || userInfo.optInt("role") == 1){
								authority.put("basicInfo","All"); //信息维护
								authority.put("FarmBreed","All"); //入雏出栏
								authority.put("FarmSettle","All"); //结算与报告查看
								authority.put("AlarmSetting","All");//报警设置
							}
							// 5:'副场长'
							if(userInfo.optInt("role") == 5){
								authority.put("basicInfo","Read"); //信息维护
								authority.put("FarmBreed","All"); //入雏出栏
								authority.put("FarmSettle","All"); //结算与报告查看
								authority.put("AlarmSetting","All");//报警设置
							}
							// 6：'统计员'
							if(userInfo.optInt("role") == 6){
								authority.put("basicInfo","Read"); //信息维护
								authority.put("FarmBreed","All"); //入雏出栏
								authority.put("FarmSettle","No"); //结算与报告查看
								authority.put("AlarmSetting","Read");//报警设置
							}
							// 3:'技术员'
							if(userInfo.optInt("role") == 3){
								authority.put("basicInfo","Read"); //信息维护
								authority.put("FarmBreed","All"); //入雏出栏
								authority.put("FarmSettle","No"); //结算与报告查看
								authority.put("AlarmSetting","All");//报警设置
							}
							// 4:'饲养员'
							if(userInfo.optInt("role") == 4){
								authority.put("basicInfo","Read"); //信息维护
								authority.put("FarmBreed","Read"); //入雏出栏
								authority.put("FarmSettle","No"); //结算与报告查看
								authority.put("AlarmSetting","Read");//报警设置
							}
							// 99:'只读用户'
							if(userInfo.optInt("role") == 99){
								authority.put("basicInfo","Read"); //信息维护
								authority.put("FarmBreed","Read"); //入雏出栏
								authority.put("FarmSettle","Read"); //结算与报告查看
								authority.put("AlarmSetting","Read");//报警设置
								
								authority.put("TaskDeal","Read"); //任务提醒处理
								authority.put("TaskSetting","Read");//任务设置
								authority.put("MonitorDeal","Read"); //环控处理
								authority.put("DailyInput","Read"); //日报填制
							}else{
								authority.put("TaskDeal","Read"); //任务提醒处理
								authority.put("TaskSetting","Read");//任务设置
								authority.put("MonitorDeal","All"); //环控处理
								authority.put("DailyInput","All"); //日报填制
							}*/
							resJson.put("Authority", authority);
						}
						// 返回 用户信息
						resJson.put("userinfo", userInfo);
						resJson.put("LoginResult", "Success");
					}else{
						resJson.put("ErrorMsg","用户密码错误");
					}
				}else{
					resJson.put("ErrorMsg","没有找到该用户");
				}
			}
			/** 业务处理结束 **/
			dealRes = Constants.RESULT_SUCCESS ;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", PubFun.isNull(e.getMessage())?"空指针错误":e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing LoginReqController.logIn");
	}
	
	private JSONObject genUserInfo(SDUser tSDUser) throws JSONException{
		JSONObject userInfo = null;
		if(tSDUser != null){
			userInfo = new JSONObject();
			userInfo.put("id", tSDUser.getId());
			userInfo.put("name", tSDUser.getUserRealName());
			userInfo.put("tele", tSDUser.getUserMobile1());
			List<SBUserHouse> SBUserHouses = mSDUserHousesService.selectByUserId(tSDUser.getId());
			int [] houseIds = null;
			if(SBUserHouses != null && SBUserHouses.size()>0){
				houseIds = new int[SBUserHouses.size()];
				for(int i = 0;i<SBUserHouses.size();i++){
					houseIds[i] = SBUserHouses.get(i).getHouseId();
				}
			}
			userInfo.put("houses", houseIds);
			SBUserRole tSBUserRole = mSDUserRolesService.selectByUserId(tSDUser.getId());
			if(tSBUserRole != null){
				userInfo.put("role", tSBUserRole.getRoleId());
				userInfo.put("author_id", tSBUserRole.getAuthorId());
			}else{
				userInfo = null;
			}
		}
		return userInfo;
	}
}

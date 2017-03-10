/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtc.app.service.SDUserOperationService;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: RepDailyFeedReqController
 * @Description:
 * @Date 2016年2月23日 下午12:06:09
 * @Author Yin Guo Xiang
 * 
 */
@Controller
@RequestMapping("/rep/DailyFeed")
public class RepDailyFeedReqController {
	private static Logger mLogger = Logger.getLogger(RepDailyFeedReqController.class);

	@Autowired
	private BaseQueryService mBaseQueryService;
	@Autowired
	private SDUserOperationService dSDUserOperationService;

	@RequestMapping("/DFRRep")
	public void DFRRep(HttpServletRequest request,
			HttpServletResponse response) {
		mLogger.info("=======Now start executing RepDailyFeedReqController.DFRRep");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		long startReqTime = System.currentTimeMillis();
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			/** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int userid = jsonobject.optInt("id_spa");
			 String CompareType = params.optString("CompareType");
			 if(CompareType == null || CompareType.equals("")){
				 CompareType = "01";
			 }
			 // 栋舍对比
			 dSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_DAILYFOOD_CURVE, SDUserOperationService.OPERATION_SELECT, userid);
			 
			int farm_id = params.optInt("FarmId");
			
			JSONArray DCDatas = new JSONArray();
			int DataSize = 0;
			// 保存标准数据
			DCDatas.put(0, "null");
			
			if(CompareType.equals("01")){
				 int FarmBreedId = params.optInt("FarmBreedId");
				 String sql = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag, hb.house_id ,s_f_getHouseName(hb.house_id) AS housename, bd.age ,IFNULL(ROUND(bd.cur_feed * 2000/(bd.cur_amount + bd.ytd_amount),0),0) AS DFR "
				 +"  FROM s_b_breed_detail AS bd LEFT JOIN s_b_house_breed AS hb ON bd.house_breed_id = hb.id "
						 +" WHERE hb.farm_breed_id = "+FarmBreedId+" AND (CASE WHEN hb.market_date IS NOT NULL THEN bd.growth_date <= hb.market_date ELSE bd.growth_date IS NOT NULL END  ) GROUP BY bd.house_breed_id , bd.age  ORDER BY bd.house_breed_id,  bd.age "; 
				 mLogger.info("RepDailyFeedReqController.DFRRep.SQL=" + sql);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 if(toutcome.size()!=0){
					 Object house_id = toutcome.get(0).get("house_id");
					 int i=0;
					 Object housename = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!house_id.equals(toutcome.get(i).get("house_id"))||i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("HouseId", house_id);
							 tJSONObject.put("housename", housename);
							 tJSONObject.put("HouseDatas", HouseDa);
							 if(DataSize < HouseDa.size()){
								 DataSize = HouseDa.size(); 
							 }
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 house_id =  outcome.get("house_id");					
						 housename =  outcome.get("housename");
						 Object DFR =  outcome.get("DFR");
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							HouseDa.add(DFR);
						 }
						 i++;
					 }
				 }
				 resJson.put("FarmBreedId", FarmBreedId);			
			 // 批次对比	 
			 }else if(CompareType.equals("02")){
				 int HouseId = params.optInt("HouseId");
				 String sql = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag, hb.farm_breed_id , (SELECT batch_code from s_b_farm_breed where id = hb.farm_breed_id) AS batch_code , bd.age ,IFNULL(ROUND(bd.cur_feed * 2000/(bd.cur_amount + bd.ytd_amount),0),0) AS DFR "
				 +" FROM s_b_breed_detail AS bd LEFT JOIN s_b_house_breed AS hb ON bd.house_breed_id = hb.id "
						 +" WHERE hb.house_id = "+HouseId+" AND (CASE WHEN hb.market_date IS NOT NULL THEN bd.growth_date <= hb.market_date ELSE bd.growth_date IS NOT NULL END  ) GROUP BY bd.house_breed_id ,"
				 +" bd.age  ORDER BY bd.house_breed_id,  bd.age "; 

				 mLogger.info("RepDailyFeedReqController.DFRRep.SQL=" + sql);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 if(toutcome.size()!=0){
					 Object farm_breed_id = toutcome.get(0).get("farm_breed_id");
					 int i=0;
					 Object batch_code = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!farm_breed_id.equals(toutcome.get(i).get("farm_breed_id")) || i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("FarmBreedId", farm_breed_id);
							 tJSONObject.put("FBBatchCode", batch_code);
							 tJSONObject.put("HouseDatas", HouseDa);
							 if(DataSize < HouseDa.size()){
								 DataSize = HouseDa.size(); 
							 }
							 if(DCDatas.length()>5){
								 break;
							 }
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 farm_breed_id =  outcome.get("farm_breed_id");					
						 batch_code =  outcome.get("batch_code");
						 Object DFR =  outcome.get("DFR");	
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							HouseDa.add(DFR);
						 }
						 i++;
					 }
				 }
				 resJson.put("HouseId", HouseId);	
			 }
			List<HashMap<String,Object>> standIntakeList = null;
			String standIntakeSQL = "SELECT age,daily_intake from s_b_chicken_standar where farm_id = "+farm_id+" and feed_type = 'mixed' ";
			standIntakeList = mBaseQueryService.selectMapByAny(standIntakeSQL);

			ArrayList<Object> staLists =new ArrayList<Object>();
			if(standIntakeList != null && standIntakeList.size() > 0){
				 JSONObject standarObject = new JSONObject();
				 if (CompareType.equals("01")) {
					standarObject.put("HouseId", -1);
					standarObject.put("housename", "标准");
				 }else if (CompareType.equals("02")) {
					standarObject.put("FarmBreedId", -1);
					standarObject.put("FBBatchCode", "标准");
				 }
				 staLists =new ArrayList<Object>();
				 for(HashMap<String, Object> singleData : standIntakeList){
					 if(staLists.size() >= 42){
						 if(staLists.size() >= DataSize){
							 break;
						 }
					 }
					 staLists.add(singleData.get("daily_intake"));
				 }
				 standarObject.put("HouseDatas", staLists);
				 DCDatas.put(0,standarObject);
			}else{
				JSONObject standar = new JSONObject();
				standar.put("HouseDatas", staLists);
				DCDatas.put(0, standar);
			}
			
			resJson.put("Result", "Success");
			resJson.put("DCDatas", DCDatas);
			/** 业务处理结束 **/
			dealRes = Constants.RESULT_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL;
		}
		long endReqTime = System.currentTimeMillis();
		if(endReqTime - startReqTime < 1500){
			try {
				Thread.sleep(1500 - endReqTime + startReqTime);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		mLogger.info("=====Now end executing RepDailyFeedReqController.DFRRep");
	}
}

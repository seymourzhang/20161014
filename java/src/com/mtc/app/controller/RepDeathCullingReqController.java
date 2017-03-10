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

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: RepDeathCullingReqController
 * @Description: 
 * @Date 2016-1-4 下午6:00:55
 * @Author Shao Yao Yu
 * 死淘率曲线
 */
@Controller
@RequestMapping("/rep/DCRate")
public class RepDeathCullingReqController {
		
	private static Logger mLogger =Logger.getLogger(RepDeathCullingReqController.class); 
		
	@Autowired
	private BaseQueryService mBaseQueryService;
	@Autowired
	private SDUserOperationService sSDUserOperationService;
	 
	@RequestMapping("/DCRateReq")
	public void DCRateReq(HttpServletRequest request,HttpServletResponse response){
	   mLogger.info("=======Now start executing RepDeathCullingReqController.DCRateReq");
	   JSONObject resJson = new JSONObject();
	   String dealRes = null;
	   long startReqTime = System.currentTimeMillis();
	   try {
		     String paraStr = PubFun.getRequestPara(request);
		     mLogger.info("updateFarm.para="+paraStr);
			 JSONObject jsonobject = new JSONObject(paraStr);
			 mLogger.info("jsonObject=" + jsonobject.toString());
			 /** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int userid = jsonobject.optInt("id_spa");
			 String CompareType = params.optString("CompareType");
			 if(CompareType == null || CompareType.equals("")){
				 CompareType = "01";
			 }
		 
			 int farm_id = params.optInt("FarmId");
		 
			 sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_DAILYDC_CURVE, SDUserOperationService.OPERATION_SELECT, userid);
			 // 栋舍对比
			 if(CompareType.equals("01")){
				 int FarmBreedId = params.optInt("FarmBreedId");
				 String sql = " SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag,"
				 		+ " hb.house_id,s_f_getHouseName(hb.house_id) AS housename, age, "
				 		+ " ROUND(bd.cur_cd / bd.ytd_amount * 1000, 1) AS atu_cd_rate "
				 		+ " FROM s_b_house_breed hb INNER JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
				 		+ " and hb.farm_breed_id = "+FarmBreedId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END) "
		 				+ " GROUP BY bd.age,hb.house_id "
		 				+ " UNION ALL"
		 				+ " SELECT (CASE WHEN bd.growth_date > CURDATE() THEN 'N' ELSE 'Y' END) AS showFlag,"
		 				+ " 0 AS house_id,'全场' AS housename, age,"
		 				+ " ROUND(SUM(bd.cur_cd)/SUM(bd.ytd_amount) * 1000,1) AS atu_cd_rate "
		 				+ " FROM s_b_house_breed hb INNER JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
		 				+ " and hb.farm_breed_id = "+FarmBreedId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END)"
 						+ " GROUP BY age ORDER BY house_id,age "; 
				 mLogger.info("=========RepDeathCullingReqController.DCRateReq.SQL=" + sql);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 JSONArray DCDatas = new JSONArray();
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
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 house_id =  outcome.get("house_id");					
						 housename =  outcome.get("housename");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");	
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 i++;
					 }
				 }
				 resJson.put("DCDatas", DCDatas);
				 resJson.put("FarmBreedId", FarmBreedId);			
			 // 批次对比	 
			 }else if(CompareType.equals("02")){
				 String HouseId = params.optString("HouseId");
				 String SQL =null;
				  SQL = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag,"
				  		+ " hb.farm_breed_id,(SELECT batch_code from s_b_farm_breed where id = hb.farm_breed_id) AS batch_code,"
			 			+ " ROUND(bd.cur_cd/bd.ytd_amount * 1000, 1) AS atu_cd_rate "
				 		+ " FROM s_b_house_breed hb inner JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
				 		+ " and hb.house_id = "+HouseId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END)"
		 				+ " GROUP BY bd.age,hb.farm_breed_id ORDER BY hb.farm_breed_id,bd.age";  
			     mLogger.info("===========RepDeathCullingReqController.DCRateReq.SQL=" + SQL);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(SQL);
				 JSONArray DCDatas = new JSONArray();
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
							 DCDatas.put(tJSONObject);
							 if(DCDatas.length()>5){
								 break;
							 }
							 HouseDa = new ArrayList<Object>();
						 }
						 farm_breed_id =  outcome.get("farm_breed_id");					
						 batch_code =  outcome.get("batch_code");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");	
						 
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 i++;
					 }
				 }
				 resJson.put("DCDatas", DCDatas);
				 resJson.put("HouseId", HouseId);	
			 }
			 resJson.put("Result", "Success");
			 /** 业务处理结束 **/
			 dealRes = Constants.RESULT_SUCCESS ;
	   } catch (Exception e) {
			e.printStackTrace();
			try {
				resJson = new JSONObject();
				resJson.put("Exception", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			dealRes = Constants.RESULT_FAIL ;
		}
		long endReqTime = System.currentTimeMillis();
		if(endReqTime - startReqTime < 1500){
			try {
				Thread.sleep(1500 - endReqTime + startReqTime);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing RepDeathCullingReqController.DCRateReq");
	}
	
	@RequestMapping("/accDCRateReq")
	public void accDCRateReq(HttpServletRequest request,HttpServletResponse response){
	   mLogger.info("=======Now start executing RepDeathCullingReqController.accDCRateReq");
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
		   if (CompareType == null || CompareType.equals("")) {
			   CompareType = "01";
		   }

		   int farm_id = params.optInt("FarmId");
		   sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_LIFE, SDUserOperationService.OPERATION_SELECT, userid);

		   JSONArray DCDatas = new JSONArray();
		   int DataSize = 0;
		   // 保存标准数据
		   DCDatas.put(0, "null");

		   // 栋舍对比
		   if (CompareType.equals("01")) {
			   int FarmBreedId = params.optInt("FarmBreedId");
			   String sql = " SELECT (CASE WHEN bd.growth_date > curdate() THEN 'N' ELSE 'Y' END) AS showFlag,"
					   + " hb.house_id,s_f_getHouseName(hb.house_id) AS housename, age, "
					   + " ROUND(bd.acc_cd/hb.place_num * 100, 1) AS acc_cd_rate "
					   + " FROM s_b_house_breed hb inner JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id and hb.farm_breed_id = " + FarmBreedId
					   + " AND (CASE WHEN hb.market_date IS NULL THEN 1 = 1 ELSE bd.growth_date <= hb.market_date END) GROUP BY bd.age,hb.house_id "
					   + " UNION ALL "
					   + " SELECT (CASE WHEN bd.growth_date > curdate() THEN 'N' ELSE 'Y' END) AS showFlag,"
					   + " 0 AS house_id,'全场' AS housename, age,"
					   + " ROUND(SUM(bd.acc_cd) / SUM(hb.place_num) * 100, 2) AS acc_cd_rate "
					   + " FROM s_b_house_breed hb inner JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
					   + " and hb.farm_breed_id = " + FarmBreedId + " AND (CASE WHEN hb.market_date IS NULL THEN 1 = 1 ELSE bd.growth_date <= hb.market_date END) GROUP BY age ORDER BY house_id,age";
			   mLogger.info("=========RepDeathCullingReqController.accDCRateReq.SQL=" + sql);
			   List<HashMap<String, Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
			   if (toutcome.size() != 0) {
				   Object house_id = toutcome.get(0).get("house_id");
				   int i = 0;
				   Object housename = null;
				   ArrayList<Object> HouseDa = new ArrayList<Object>();
				   for (HashMap<String, Object> outcome : toutcome) {
					   if (!house_id.equals(toutcome.get(i).get("house_id")) || i + 1 == toutcome.size()) {
						   JSONObject tJSONObject = new JSONObject();
						   tJSONObject.put("HouseId", house_id);
						   tJSONObject.put("housename", housename);
						   tJSONObject.put("HouseDatas", HouseDa);
						   if (DataSize < HouseDa.size()) {
							   DataSize = HouseDa.size();
						   }
						   DCDatas.put(tJSONObject);
						   HouseDa = new ArrayList<Object>();
					   }
					   house_id = outcome.get("house_id");
					   housename = outcome.get("housename");
					   Object atu_cd_rate = outcome.get("acc_cd_rate");
					   String showFlag = outcome.get("showFlag").toString();
					   if (showFlag.equals("Y")) {
						   HouseDa.add(atu_cd_rate);
					   }
					   i++;
				   }
			   }
			   resJson.put("FarmBreedId", FarmBreedId);
			   // 批次对比
		   } else if (CompareType.equals("02")) {
			   String HouseId = params.optString("HouseId");
			   String SQL = null;
			   SQL = "SELECT (CASE WHEN bd.growth_date > curdate() THEN 'N' ELSE 'Y' END) AS showFlag,"
					   + "hb.farm_breed_id,(SELECT batch_code FROM s_b_farm_breed WHERE id = hb.farm_breed_id) AS batch_code,"
					   + " bd.age,ROUND(bd.acc_cd / hb.place_num * 100, 2) AS acc_cd_rate "
					   + " FROM s_b_house_breed hb INNER join s_b_breed_detail bd ON bd.house_breed_id = hb.id "
					   + " and hb.house_id = " + HouseId
					   + " AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END) "
					   + " GROUP BY bd.age,hb.farm_breed_id ORDER BY hb.farm_breed_id,bd.age";
			   mLogger.info("===========RepDeathCullingReqController.accDCRateReq.SQL=" + SQL);
			   List<HashMap<String, Object>> toutcome = mBaseQueryService.selectMapByAny(SQL);
			   if (toutcome.size() != 0) {
				   Object farm_breed_id = toutcome.get(0).get("farm_breed_id");
				   int i = 0;
				   Object batch_code = null;
				   ArrayList<Object> HouseDa = new ArrayList<Object>();
				   for (HashMap<String, Object> outcome : toutcome) {
					   if (!farm_breed_id.equals(toutcome.get(i).get("farm_breed_id")) || i + 1 == toutcome.size()) {
						   JSONObject tJSONObject = new JSONObject();
						   tJSONObject.put("FarmBreedId", farm_breed_id);
						   tJSONObject.put("FBBatchCode", batch_code);
						   tJSONObject.put("HouseDatas", HouseDa);
						   if (DataSize < HouseDa.size()) {
							   DataSize = HouseDa.size();
						   }
						   if (DCDatas.length() > 5) {
							   break;
						   }
						   DCDatas.put(tJSONObject);
						   HouseDa = new ArrayList<Object>();
					   }
					   farm_breed_id = outcome.get("farm_breed_id");
					   batch_code = outcome.get("batch_code");
					   Object atu_cd_rate = outcome.get("acc_cd_rate");
					   String showFlag = outcome.get("showFlag").toString();
					   if (showFlag.equals("Y")) {
						   HouseDa.add(atu_cd_rate);
					   }
					   i++;
				   }
			   }
			   resJson.put("HouseId", HouseId);
		   }

		   List<HashMap<String, Object>> standIntakeList = null;
		   String standIntakeSQL = "SELECT age,cum_motality from s_b_chicken_standar where farm_id = " + farm_id + " and feed_type = 'mixed' and cum_motality <> 0  ORDER BY age ";
		   standIntakeList = mBaseQueryService.selectMapByAny(standIntakeSQL);

		   JSONObject standarObject = new JSONObject();
		   if (CompareType.equals("01")) {
			   standarObject.put("HouseId", -1);
			   standarObject.put("housename", "标准");
		   } else if (CompareType.equals("02")) {
			   standarObject.put("FarmBreedId", -1);
			   standarObject.put("FBBatchCode", "标准");
		   }
		   ArrayList<Object> cum_motality_datas = new ArrayList<Object>();
		   if (standIntakeList != null && standIntakeList.size() > 0) {
			   for (HashMap<String, Object> singleData : standIntakeList) {
				   if (cum_motality_datas.size() >= 42) {
					   if (cum_motality_datas.size() >= DataSize) {
						   break;
					   }
				   }
				   cum_motality_datas.add(singleData.get("cum_motality"));
			   }
		   }
		   standarObject.put("HouseDatas", cum_motality_datas);
		   DCDatas.put(0, standarObject);
		   resJson.put("DCDatas", DCDatas);
		   resJson.put("Result", "Success");
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
			dealRes = Constants.RESULT_FAIL ;
		}
		long endReqTime = System.currentTimeMillis();
		if(endReqTime - startReqTime < 1500){
			try {
				Thread.sleep(1500 - endReqTime + startReqTime);
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		DealSuccOrFail.dealApp(request,response,dealRes,resJson);
		mLogger.info("=====Now end executing RepDeathCullingReqController.accDCRateReq");
	}
}

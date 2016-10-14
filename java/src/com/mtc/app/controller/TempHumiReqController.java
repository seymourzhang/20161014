/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mtc.app.service.SDUserOperationService;
import com.mtc.entity.app.SDUserOperation;
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
 * @ClassName: RepTempReqController
 * @Description:
 * @Date 2016-1-6 下午5:40:42
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/rep/TempHumi")
public class TempHumiReqController {

	private static Logger mLogger = Logger.getLogger(TempHumiReqController.class);
	@Autowired
	private BaseQueryService mBaseQueryService;
	@Autowired
	private SDUserOperationService sSDUserOperationService;

	@RequestMapping("/TempHumiReq")
	public void TempHumiReq(HttpServletRequest request,HttpServletResponse response) {
		mLogger.info("=======Now start executing TempHumiReqController.TempHumiReq");
		JSONObject resJson = new JSONObject();
		String dealRes = null;
		long startReqTime = System.currentTimeMillis();
		try {
			String paraStr = PubFun.getRequestPara(request);
			mLogger.info("updateFarm.para=" + paraStr);
			JSONObject jsonobject = new JSONObject(paraStr);
			int userId = jsonobject.optInt("id_spa");
			mLogger.info("jsonObject=" + jsonobject.toString());
			String tErrorContent = "Null";
			/** 业务处理开始，查询、增加、修改、或删除 **/
			JSONObject   params = jsonobject.getJSONObject("params");
			int   FarmBreedId = params.optInt("FarmBreedId");
			int   HouseId = params.optInt("HouseId");

			sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_TEMPHUM, SDUserOperationService.OPERATION_SELECT, userId);
			mLogger.info("==========温湿度操作信息：查询，导入完毕");
			String tSQL = "SELECT DATE_FORMAT(place_date, '%Y-%m-%d') FROM s_b_house_breed where farm_breed_id = "+FarmBreedId+" and house_id = " + HouseId;
			String placeDate = mBaseQueryService.selectStringByAny(tSQL);
			String endDate = null;
			if(!PubFun.isNull(placeDate)){
				endDate = PubFun.addDate(placeDate, 45);
			}
			
			String SQL = " SELECT (CASE WHEN a.growth_date > CURDATE() THEN 'N' ELSE 'Y' END)  AS dataflag, "
					+ "b.house_id, DATE_FORMAT(a.growth_date, '%Y-%m-%d') AS growthDate, "
					+ "a.age   AS age, tData2.avgtemp_max, tData2.avgtemp_min, tData2.tartemp, tData2.humi "
					+ "FROM s_b_breed_detail a "
					+ "LEFT JOIN s_b_house_breed b ON b.id = a.house_breed_id "
					+ "LEFT JOIN (SELECT tData.timeId, ROUND(MAX(tData.inside_avg_temp), 1) AS avgtemp_max, "
							+ "ROUND(MIN(tData.inside_avg_temp), 1) AS avgtemp_min, ROUND(AVG(tData.inside_set_temp),1) AS tartemp,  "
							+ "ROUND(AVG(tData.inside_humidity),1) AS humi FROM "
									+ "(SELECT  DATE_FORMAT(collect_datetime, '%Y-%m-%d') AS timeId,  a.*  "
									+ "FROM s_b_monitor_hist a WHERE  a.house_id = "+HouseId+" "
									+ "and date_format(a.collect_datetime,'%Y-%m-%d') BETWEEN '"+placeDate+"' and '"+endDate+"' "
							+ ") tData GROUP BY tData.timeId ORDER BY tData.timeId) AS tData2 "
					+ "ON tData2.timeId = DATE_FORMAT(a.growth_date, '%Y-%m-%d') "
					+ "WHERE  a.age <= 45 AND b.house_id = "+HouseId+" AND b.farm_breed_id ="+FarmBreedId ;
			mLogger.info("=========TempHumiReqController.TempHumiReq.SQL: "+SQL);
			List<HashMap<String,Object>> listMap = mBaseQueryService.selectMapByAny(SQL);
			JSONArray THDatas = new JSONArray();
			JSONArray xAxis = new JSONArray();
		    if(listMap.size()>0){
		    	JSONObject njsonObject = new JSONObject();
				for (HashMap<String, Object> hashMap : listMap) {
				    njsonObject = new JSONObject();
					Object  MinTemp	= hashMap.get("avgtemp_min");
					Object  MaxTemp = hashMap.get("avgtemp_max");
					Object	TarTemp = hashMap.get("tartemp");
					Object	Humi = hashMap.get("humi");
					Object	age = hashMap.get("age");
					String  dataflag = (String) hashMap.get("dataflag");
					if(MinTemp==null){
						njsonObject.put("MinTemp", 0);
					}else{
						njsonObject.put("MinTemp", MinTemp);
					}
                    if(MaxTemp==null){
                    	njsonObject.put("MaxTemp", 0);
					}else{
						njsonObject.put("MaxTemp", MaxTemp);
					}
                    if(TarTemp==null){
                    	njsonObject.put("TarTemp", 0);
                    }else{
                    	njsonObject.put("TarTemp", TarTemp);
                    }
					if(Humi==null){
						njsonObject.put("Humi", 0);
					}else{
						njsonObject.put("Humi", Humi);
					}
					if(dataflag.equals("Y")){
						THDatas.put(njsonObject);
					}
					xAxis.put(age);
				}
		    }
		    resJson.put("xAxis", xAxis);
			resJson.put("THDatas", THDatas);
		    resJson.put("FarmBreedId", FarmBreedId);
			resJson.put("HouseId", HouseId);
			resJson.put("Result", "Success");
			dealRes = Constants.RESULT_SUCCESS;
			/** 业务处理结束 **/
		} catch (Exception e) {
			e.printStackTrace();
				resJson = new JSONObject();
				try {
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
		mLogger.info("=====Now end executing TempHumiReqController.TempHumiReq");
	}
}

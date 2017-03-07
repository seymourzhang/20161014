/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
 * @ClassName: RepTempDiffReqController
 * @Description:
 * @Date 2016-1-6 下午5:40:42
 * @Author Shao Yao Yu
 * 
 */
@Controller
@RequestMapping("/rep/TempDiffCurve")
public class RepTempDiffReqController {

    private static Logger mLogger = Logger.getLogger(RepTempDiffReqController.class);

    @Autowired
    private BaseQueryService mBaseQueryService;
    @Autowired
    private SDUserOperationService sSDUserOperationService;

	@RequestMapping("/TempDiffCurveReq")
	public void TempDiffCurveReq(HttpServletRequest request,HttpServletResponse response) {
		mLogger.info("=======Now start executing RepTempDiffReqController.TempDiffCurveReq");
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

			 /*业务处理开始，查询、增加、修改、或删除*/
			JSONObject params = jsonobject.optJSONObject("params");
			int FarmBreedId = params.optInt("FarmBreedId");
			int HouseId = params.optInt("HouseId");
			String DataType = params.optString("DataType");
			String ReqFlag = params.optString("ReqFlag");
			String DataRange = params.optString("DataRange");
			String data_date = "null" ;
			String data_age = "null";
			JSONArray TempDatas = new JSONArray();
			List<HashMap<String, Object>> listMap = null;
			String tSQL = "";
			boolean flag = true;
			sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_TEMPDIFF, SDUserOperationService.OPERATION_SELECT, userId);

			if (DataType.equals("01")) {

			    String fSQL = "SELECT place_date FROM s_b_house_breed where house_id = " + HouseId + " and farm_breed_id = " + FarmBreedId;
                String place = mBaseQueryService.selectStringByAny(fSQL);
                
				tSQL = "SELECT (CASE when a.growth_date > curdate() then 'N' else 'Y' end) as dataflag,'Null'as data_age,'Null'as data_date,b.house_id,concat(date_format(a.growth_date,'%m-%d'),'(',a.age,')') as x_axis," +
						"tData2.point_temp_diff "
					 + "FROM s_b_breed_detail a "
					 + "left join s_b_house_breed b on b.id = a.house_breed_id "
					 + "left join( SELECT tData.timeId,"
						           + "truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff "
								   + "from ( SELECT date_format(collect_datetime, '%Y-%m-%d') AS timeId, a.* "
								   			+ "FROM s_b_monitor_hist a WHERE 1 = 1 "
								   			+ "AND a.house_id = " + HouseId + " " ;
                if (place == null || place.equals("")){
                	tSQL = tSQL + " and 1<>1 ";
                }else{
                	tSQL = tSQL + "AND a.collect_datetime BETWEEN '" + place + "' AND date_add('" + place + "', INTERVAL 60 DAY) " ;
                }
                tSQL = tSQL + ") tData group by tData.timeId order by tData.timeId "
						+ ") as tData2 on tData2.timeId = date_format(a.growth_date,'%Y-%m-%d') "
						+ "where 1=1 "
						+ "and a.age <= 45 "
						+ "and b.house_id = " + HouseId + " "
						+ "and b.farm_breed_id =" + FarmBreedId + " "
						+ "and exists(SELECT 1 from s_b_house_breed sbh where sbh.id = a.house_breed_id and a.growth_date <= ifnull(b.market_date,now())) ";

			}else if (DataType.equals("02")) {
				if (ReqFlag.equals("N")) {
					DataRange = "NULL";
					String tDateSql = "SELECT s_f_getRecentAgeDateByHouseId("+HouseId+", '"+DataRange+"',"+FarmBreedId+") ";
					DataRange = mBaseQueryService.selectStringByAny(tDateSql);
					if (DataRange == null) {
						tErrorContent = "暂无入雏信息！";
						flag = false;
					}
				}else{
					if (DataRange.length() != 10) {
						tErrorContent = "请传入正确的日期参数（YYYY-MM-DD）。";
					}
				}
				if (flag) {
					tSQL = "SELECT (CASE WHEN concat(tData3.data_date,' ',CODE) > date_format(adddate(now(), INTERVAL 30 MINUTE), '%Y-%m-%d %H:%i') THEN 'N' ELSE 'Y' END) AS dataflag, "
							+ "CODE AS x_axis," + HouseId + " AS house_id,"
							+ "tData3.data_date as data_date,"
							+ "concat('(日龄：',s_f_getDayAgeByHouseId(" + HouseId + ",tData3.data_date),')')  AS data_age,"
							+ " tData2.point_temp_diff "
							+ " FROM s_b_constants sc "
							+ "	LEFT JOIN("
							+ " SELECT case when tData.timeId = '00:00' then '24:00' else tData.timeId end as timeId,"
							+ "tData.house_id, tData.date_age,"
							+ "truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff "
							+ " FROM ( SELECT  (CASE WHEN DATE_FORMAT(collect_datetime, '%i') BETWEEN '00' AND '30' THEN CONCAT(DATE_FORMAT(collect_datetime, '%H'),':30') ELSE CONCAT(DATE_FORMAT(adddate(collect_datetime,INTERVAL 1 HOUR), '%H'),':00') END) AS timeId,a.* "
							+ " FROM s_b_monitor_hist a "
							+ " WHERE  a.house_id = " + HouseId
							+ " and DATE_FORMAT(a.collect_datetime, '%Y-%m-%d') = '" + DataRange + "' "
							+ ") tData GROUP BY tData.timeId ORDER BY tData.timeId"
							+ ") AS tData2 ON tData2.timeId = sc.code "
							+ "LEFT JOIN (select '" + DataRange + "' as data_date) AS tData3 on 1=1"
							+ " WHERE codetype = 'HalfHour' ";
				}
			} else if (DataType.equals("03")) {
				String DataRangeStart = "";
				String DataRangeEnd = "";
				if (ReqFlag.equals("N")) {
					String tarTime = "";
					if(DataRange.equals(PubFun.getCurrentDate())){
						String tCurTime = PubFun.getCurrentTime();
						if(tCurTime.substring(3, 5).compareTo("30")>0){
							tarTime = tCurTime.substring(0,2) + ":30";
						}else{
							tarTime = tCurTime.substring(0,2) + ":00";
						}
					}else{
						tarTime = "00:00";
					}
					DataRangeStart = DataRange + " " + tarTime ;
					
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					Date date = formatter.parse(DataRangeStart);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, 30);
					DataRangeEnd = formatter.format(calendar.getTime());
					
				}else{
					DataRangeEnd = DataRange ;
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm");
					Date date = formatter.parse(DataRangeEnd);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, -30);
					DataRangeStart = formatter.format(calendar.getTime());
					
					date = formatter.parse(DataRangeEnd);
					DataRangeEnd = formatter.format(date);
				}
				if (DataRangeStart.length() != 16 || DataRangeEnd.length() != 16) {
					tErrorContent = "DataRange日期参数有误";
				}
				
				data_date = DataRangeStart.substring(0,10);
				String tHourValue = DataRangeStart.substring(11, 13);
				String codeType = "";
				if (DataRangeStart.endsWith("00")) {
					codeType = "PerMinute1";
				} else {
					codeType = "PerMinute2";
				}
				
				tSQL = "SELECT 'Y' as dataflag ,CONCAT('"+ tHourValue+ ":', CASE when tData.timeId = '00' then '60' else tData.timeId end) AS x_axis,"
						+ "'Null'as data_date,concat('(日龄：',tData.date_age,')')  AS data_age,tData.house_id,"
						+ "truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff "
						+ "FROM (SELECT DATE_FORMAT(adddate(a.collect_datetime,INTERVAL 1 MINUTE), '%i') AS timeId,a.* "
							+ "FROM s_b_monitor_hist a WHERE 1=1 "
								+ "and a.house_id = " + HouseId + " "
								+ "AND a.collect_datetime >= STR_TO_DATE('"+ DataRangeStart+ "', '%Y-%m-%d %H:%i' ) "
								+ "AND a.collect_datetime  < STR_TO_DATE( '"+ DataRangeEnd+ "',  '%Y-%m-%d %H:%i' ) "
							 + ") tData GROUP BY tData.timeId "
						+ "UNION ALL "
							+ "SELECT  'N' as dataflag , CONCAT('"+ tHourValue+ ":', sc.code) AS x_axis,"
								+ "NULL,NULL, NULL,  NULL FROM s_b_constants sc  WHERE 1=1 "
								+ "AND sc.codetype = '"+ codeType+ "' "
								+ "AND CODE > (SELECT right(concat('0', ifnull(DATE_FORMAT(MAX(sbh.collect_datetime), '%i') + 1,0)), 2) FROM s_b_monitor_hist sbh  WHERE 1=1 "
								+ "AND sbh.house_id = " + HouseId + " "
								+ "AND sbh.collect_datetime BETWEEN "
											+ "STR_TO_DATE('"+ DataRangeStart+ "', '%Y-%m-%d %H:%i' ) AND "
											+ "STR_TO_DATE('"+ DataRangeEnd + "','%Y-%m-%d %H:%i' )) "
						+ " ORDER BY x_axis ";
			}else{
				tErrorContent = "DataType参数有误";
			}
			mLogger.info("DataType=" + DataType + " DataRange="+ DataRange);
			mLogger.info("==========RepTempDiffReqController.TempDiffCurveReq.sql=" + tSQL);
			
			if(tErrorContent.equals("Null")){
				listMap = mBaseQueryService.selectMapByAny(tSQL);
				if (listMap.size() > 0) {
					JSONArray point_temp_diffArray = new JSONArray();
					JSONArray xAxis = new JSONArray();
					for (HashMap<String, Object> hashMap : listMap) {
						Object x_axis = hashMap.get("x_axis");
						if (x_axis == null) {
							x_axis = 0;
						}
						
						if(x_axis.toString().endsWith("60")){
							int tHor = Integer.parseInt(x_axis.toString().substring(0, 2)) + 1;
							x_axis = PubFun.fillLeftChar(tHor, '0', 2) + ":00";
						}
						
						Object point_temp_diff = hashMap.get("point_temp_diff");
						if (point_temp_diff == null) {
							point_temp_diff = 0;
						}
						xAxis.put(x_axis);
						if(hashMap.get("dataflag").equals("N")){
							continue;
						}
						point_temp_diffArray.put(point_temp_diff.toString());
						if(DataType.equals("02")){
							data_date = hashMap.get("data_date")!=null?hashMap.get("data_date").toString():"Null";
						}
						if(hashMap.get("data_age") != null){
							data_age = hashMap.get("data_age").toString();
						}
					}

					resJson.put("xAxis", xAxis);
					JSONObject tJSONObject = new JSONObject();
					tJSONObject.put("TempAreaCode", "pointTempDiff");
					tJSONObject.put("TempAreaName", "点温差");
					tJSONObject.put("TempDiffCurve", point_temp_diffArray);
					TempDatas.put(tJSONObject);
						
					resJson.put("TempDatas", TempDatas);
					resJson.put("HouseId", HouseId);
					resJson.put("DataDate", data_date);
					resJson.put("data_age", data_age);
					resJson.put("FarmBreedId", FarmBreedId);
					resJson.put("Result", "Success");
					dealRes = Constants.RESULT_SUCCESS;
				} else {
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请求参数错误");
					dealRes = Constants.RESULT_SUCCESS;
				}
			}else{
				resJson.put("Result", "Fail");
				resJson.put("ErrorMsg", tErrorContent);
				dealRes = Constants.RESULT_SUCCESS;
			}
			 /*业务处理结束*/
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
		mLogger.info("=====Now end executing RepTempDiffReqController.TempDiffCurveReq");
	}

    @RequestMapping("/TempDiffCurveReq_v2")
    public void TempDiffCurveReq_v2(HttpServletRequest request, HttpServletResponse response) {
        mLogger.info("=====Now start executing RepTempDiffReqController.TempDiffCurveReq");
        JSONObject resJson = new JSONObject();
        String dealRes = "";
        long startReqTime = System.currentTimeMillis();
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("updateFarm.para=" + paraStr);
            JSONObject jsonobject = new JSONObject(paraStr);
            int userId = jsonobject.optInt("id_spa");
            mLogger.info("jsonObject=" + jsonobject.toString());

            String tErrorContent = "Null";

			/* 业务处理开始，查询、增加、修改、或删除 */

            JSONObject params = jsonobject.optJSONObject("params");
            int FarmBreedId = params.optInt("FarmBreedId");
            String DataType = params.optString("DataType");
            String AgeFlag = params.optString("AgeFlag");
            String AgeRange = params.optString("AgeRange");
            String TimeFlag = params.optString("TimeFlag");
            String TimeRange = params.optString("TimeRange");
            String data_date = "null";
            String data_age = "null";
            JSONArray TempDatas = new JSONArray();
            List<HashMap<String, Object>> listMap = null;
            String tSQL = "";
            boolean flag = true;
            sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_TEMPDIFF, SDUserOperationService.OPERATION_SELECT, userId);

            String sql = "select house_id, s_f_getHouseName(house_id) as house_name from s_b_house_breed where farm_breed_id = " + FarmBreedId;

            List<HashMap<String, Object>> lpd = mBaseQueryService.selectMapByAny(sql);

            if (DataType.equals("02")) {
                if (AgeFlag.equals("N")) {
                    AgeRange = "NULL";
                    String tDateSql = "SELECT b.age\n" +
                            "FROM s_b_breed_detail b\n" +
                            "  LEFT JOIN (\n" +
                            "              SELECT\n" +
                            "                date_format(\n" +
                            "                    least(date_add(min(a.place_date), INTERVAL 50 DAY), ifnull(a.market_date, curdate()), curdate()),\n" +
                            "                    '%Y-%m-%d') tDate,\n" +
                            "                a.id\n" +
                            "              FROM s_b_house_breed a\n" +
                            "              WHERE 1 = 1 AND farm_breed_id = " + FarmBreedId + ") tDate2 ON tDate2.tDate = b.growth_date\n" +
                            "WHERE b.house_breed_id = tDate2.id";
                    AgeRange = mBaseQueryService.selectStringByAny(tDateSql);
                    if (AgeRange == null || AgeRange.equals("")){
                        tErrorContent = "暂无批次信息！";
                    }
                }
            }
            if (tErrorContent.equals("Null")) {
                for (HashMap<String, Object> stringObjectHashMap : lpd) {
                    int HouseId = Integer.parseInt(stringObjectHashMap.get("house_id").toString());
                    String HouseName = stringObjectHashMap.get("house_name").toString();
                    if (DataType.equals("01")) {
                        String fSQL = "SELECT place_date FROM s_b_house_breed where house_id = " + HouseId + " and farm_breed_id = " + FarmBreedId;
                        String place = mBaseQueryService.selectStringByAny(fSQL);

                        tSQL = "SELECT (CASE when a.growth_date > curdate() then 'N' else 'Y' end) as dataflag,'Null'as data_age,'Null'as data_date,b.house_id,a.age as x_axis," +
                                "tData2.point_temp_diff "
                                + "FROM s_b_breed_detail a "
                                + "left join s_b_house_breed b on b.id = a.house_breed_id "
                                + "left join( SELECT tData.timeId,"
                                + "truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff "
                                + "from ( SELECT date_format(collect_datetime, '%Y-%m-%d') AS timeId, a.* "
                                + "FROM s_b_monitor_hist a WHERE 1 = 1 "
                                + "AND a.house_id = " + HouseId + " ";
                        if (place == null || place.equals("")) {
                            tSQL = tSQL + " and 1<>1 ";
                        } else {
                            tSQL = tSQL + "AND a.collect_datetime BETWEEN '" + place + "' AND date_add('" + place + "', INTERVAL 50 DAY) ";
                        }
                        tSQL = tSQL + ") tData group by tData.timeId order by tData.timeId "
                                + ") as tData2 on tData2.timeId = date_format(a.growth_date,'%Y-%m-%d') "
                                + "where 1=1 "
                                + "and a.age <= 50 "
                                + "and b.house_id = " + HouseId + " "
                                + "and b.farm_breed_id =" + FarmBreedId + " "
                                + "and exists(SELECT 1 from s_b_house_breed sbh where sbh.id = a.house_breed_id and a.growth_date <= ifnull(b.market_date,now())) ";

                    } else if (DataType.equals("02")) {
                        tSQL = "SELECT\n" +
                                "  (CASE WHEN concat(tData2.collect_datetime, ' ', CODE) > date_format(adddate(now(), INTERVAL 30 MINUTE), '%Y-%m-%d %H:%i')\n" +
                                "    THEN 'N'\n" +
                                "   ELSE 'Y' END)                                                     AS dataflag,\n" +
                                "  CODE                                                               AS x_axis,\n" +
                                "  " + HouseId + "                                                    AS house_id,\n" +
                                "  tData2.collect_datetime                                            AS data_date,\n" +
                                "  tData2.date_age                                                    AS data_age,\n" +
                                "  tData2.point_temp_diff\n" +
                                "FROM s_b_constants sc\n" +
                                "  LEFT JOIN (\n" +
                                "              SELECT\n" +
                                "                CASE WHEN tData.timeId = '00:00'\n" +
                                "                  THEN '24:00'\n" +
                                "                ELSE tData.timeId END                   AS timeId,\n" +
                                "                tData.house_id,\n" +
                                "                tData.age                                AS date_age,\n" +
                                "                tData.collect_datetime,\n" +
                                "                truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff\n" +
                                "              FROM (SELECT\n" +
                                "                      b.age,\n" +
                                "                      (CASE WHEN DATE_FORMAT(collect_datetime, '%i') BETWEEN '00' AND '30'\n" +
                                "                        THEN CONCAT(DATE_FORMAT(collect_datetime, '%H'), ':30')\n" +
                                "                       ELSE CONCAT(DATE_FORMAT(adddate(collect_datetime, INTERVAL 1 HOUR), '%H'), ':00') END) AS timeId,\n" +
                                "                      a.*\n" +
                                "                    FROM s_b_breed_detail b\n" +
                                "                    LEFT JOIN s_b_monitor_hist a ON date_format(b.growth_date, '%Y-%m-%d') = date_format(a.collect_datetime, '%Y-%m-%d')\n" +
                                "                    LEFT JOIN s_b_house_breed c  ON c.id = b.house_breed_id\n" +
                                "                    WHERE a.house_id = " + HouseId + "\n" +
                                "                          AND b.age = " + AgeRange + " AND c.farm_breed_id = " + FarmBreedId + "\n" +
                                "                   ) tData\n" +
                                "              GROUP BY tData.timeId\n" +
                                "              ORDER BY tData.timeId\n" +
                                "            ) AS tData2 ON tData2.timeId = sc.code\n" +
                                "WHERE codetype = 'HalfHour'";
                    } else if (DataType.equals("03")) {
                        String DataRangeStart = "";
                        String DataRangeEnd = "";
                        String speDate = "";
                        if (AgeFlag.equals("Y") && AgeRange != "") {
                            String sqlTime = "SELECT b.growth_date\n" +
                                    "FROM s_b_house_breed a LEFT JOIN s_b_breed_detail b ON a.id = b.house_breed_id\n" +
                                    "where a.house_id = " + HouseId + " and b.age = " + AgeRange + " and a.farm_breed_id = " + FarmBreedId;
                            speDate = mBaseQueryService.selectStringByAny(sqlTime);
                            if (speDate == null || speDate.equals("")) {
                                tErrorContent = "暂无批次信息！";
                            }
                        } else {
                            tErrorContent = "参数错误，请联系管理员！";
                        }
                        if (tErrorContent.equals("Null")) {
                            if (TimeFlag.equals("N")) {
                                String tarTime = "";
                                if (speDate.equals(PubFun.getCurrentDate())) {
                                    String tCurTime = PubFun.getCurrentTime();
                                    if (tCurTime.substring(3, 5).compareTo("30") > 0) {
                                        tarTime = tCurTime.substring(0, 2) + ":30";
                                    } else {
                                        tarTime = tCurTime.substring(0, 2) + ":00";
                                    }
                                } else {
                                    tarTime = "00:00";
                                }
                                DataRangeStart = speDate + " " + tarTime;

                                SimpleDateFormat formatter = new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm");
                                Date date = formatter.parse(DataRangeStart);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.MINUTE, 30);
                                DataRangeEnd = formatter.format(calendar.getTime());
                            } else {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                DataRangeEnd = format.format(format.parse(speDate)) + " " + TimeRange;
                                SimpleDateFormat formatter = new SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm");
                                Date date = formatter.parse(DataRangeEnd);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                calendar.add(Calendar.MINUTE, -30);
                                DataRangeStart = formatter.format(calendar.getTime());

                                date = formatter.parse(DataRangeEnd);
                                DataRangeEnd = formatter.format(date);
                            }

                            String tHourValue = DataRangeStart.substring(11, 13);
                            String codeType = "";
                            if (DataRangeStart.endsWith("00")) {
                                codeType = "PerMinute1";
                            } else {
                                codeType = "PerMinute2";
                            }

                            tSQL = "SELECT 'Y' as dataflag ,CONCAT('" + tHourValue + ":', CASE when tData.timeId = '00' then '60' else tData.timeId end) AS x_axis,"
                                    + "'Null'as data_date,tData.date_age  AS data_age,tData.house_id,"
                                    + "truncate(AVG(tData.point_temp_diff), 1) AS point_temp_diff "
                                    + "FROM (SELECT DATE_FORMAT(adddate(a.collect_datetime,INTERVAL 1 MINUTE), '%i') AS timeId,a.*,b.age "
                                    + "FROM s_b_monitor_hist a "
                                    + "  LEFT JOIN s_b_breed_detail b on date_format(b.growth_date, '%Y-%m-%d') = date_format(a.collect_datetime, '%Y-%m-%d')"
                                    + "  LEFT JOIN s_b_house_breed c on c.id = b.house_breed_id "
                                    + "WHERE 1=1 "
                                    + "and a.house_id = " + HouseId + " "
                                    + "and c.farm_breed_id = " + FarmBreedId + " "
                                    + "AND a.collect_datetime >= STR_TO_DATE('" + DataRangeStart + "', '%Y-%m-%d %H:%i' ) "
                                    + "AND a.collect_datetime  < STR_TO_DATE( '" + DataRangeEnd + "',  '%Y-%m-%d %H:%i' ) "
                                    + ") tData GROUP BY tData.timeId "
                                    + "UNION ALL "
                                    + "SELECT  'N' as dataflag , CONCAT('" + tHourValue + ":', sc.code) AS x_axis,"
                                    + "NULL,NULL,NULL,NULL FROM s_b_constants sc  WHERE 1=1 "
                                    + "AND sc.codetype = '" + codeType + "' "
                                    + "AND CODE > (SELECT right(concat('0', ifnull(DATE_FORMAT(MAX(sbh.collect_datetime), '%i') + 1,0)), 2) FROM s_b_monitor_hist sbh  WHERE 1=1 "
                                    + "AND sbh.house_id = " + HouseId + " "
                                    + "AND sbh.collect_datetime BETWEEN "
                                    + "STR_TO_DATE('" + DataRangeStart + "', '%Y-%m-%d %H:%i' ) AND "
                                    + "STR_TO_DATE('" + DataRangeEnd + "','%Y-%m-%d %H:%i' )) "
                                    + " ORDER BY x_axis ";
                        }
                    } else {
                        tErrorContent = "DataType参数有误";
                    }
//                mLogger.info("DataType=" + DataType + " TimaRange=" + TimaRange);
                    mLogger.info("==========RepTempDiffReqController.TempDiffCurveReq.sql=" + tSQL);

                    if (tErrorContent.equals("Null")) {
                        listMap = mBaseQueryService.selectMapByAny(tSQL);
                        if (listMap.size() > 0) {
                            JSONArray point_temp_diffArray = new JSONArray();
                            JSONArray xAxis = new JSONArray();
                            for (HashMap<String, Object> hashMap : listMap) {
                                Object x_axis = hashMap.get("x_axis");
                                if (x_axis == null) {
                                    x_axis = 0;
                                }

                                if (x_axis.toString().endsWith("60")) {
                                    int tHor = Integer.parseInt(x_axis.toString().substring(0, 1)) + 1;
                                    x_axis = PubFun.fillLeftChar(tHor, '0', 1) + ":00";
                                }

                                Object point_temp_diff = hashMap.get("point_temp_diff");
                                if (point_temp_diff == null) {
                                    point_temp_diff = 0;
                                }
                                xAxis.put(x_axis);
                                if (hashMap.get("dataflag").equals("N")) {
                                    continue;
                                }
                                point_temp_diffArray.put(point_temp_diff.toString());
                                if (DataType.equals("02")) {
                                    data_date = hashMap.get("data_date") != null ? hashMap.get("data_date").toString() : "Null";
                                }
                            }

                            resJson.put("xAxis", xAxis);
                            JSONObject tJSONObject = new JSONObject();
//						tJSONObject.put("TempAreaCode", "pointTempDiff");
                            tJSONObject.put("TempAreaName", HouseName + "栋");
                            tJSONObject.put("TempDiffCurve", point_temp_diffArray);
                            tJSONObject.put("HouseId", HouseId);
                            TempDatas.put(tJSONObject);
                            resJson.put("Result", "Success");
                        } else {
                            resJson.put("Result", "Fail");
                            resJson.put("ErrorMsg", "请求参数错误");
                        }
                    } else {
                        resJson.put("Result", "Fail");
                        resJson.put("ErrorMsg", tErrorContent);
                    }
                }
            } else {
                resJson.put("Result", "Fail");
                resJson.put("ErrorMsg", tErrorContent);
            }
            resJson.put("FarmBreedId", FarmBreedId);
            resJson.put("DataDate", data_date);
            resJson.put("data_age", AgeRange);
            resJson.put("TempDatas", TempDatas);
            dealRes = Constants.RESULT_SUCCESS;
		/* 业务处理结束 */

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
        if (endReqTime - startReqTime < 1500) {
            try {
                Thread.sleep(1500 - endReqTime + startReqTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("=====Now end executing RepTempDiffReqController.TempDiffCurveReq");
    }
}

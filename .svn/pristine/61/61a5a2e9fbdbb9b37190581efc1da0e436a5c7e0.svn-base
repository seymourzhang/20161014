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
 * @ClassName: RepFCRReqController
 * @Description:
 * @Date 2016年4月8日 下午12:06:09
 * @Author 邵耀雨
 * 
 */
@Controller
@RequestMapping("/farmManage")
public class RepProSUMReqController {
	private static Logger mLogger = Logger.getLogger(RepProSUMReqController.class);

	@Autowired
	private BaseQueryService tBaseQueryService;
	@Autowired
	private SDUserOperationService sSDUserOperationService;

	/**
	 * 生产指标汇总（批次报告）
	 * @param request
	 * @param response
	 */
	@RequestMapping("/productionSumReport")
    public void getProductionSumReport(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======Now start FarmManageReqController.getProductionSumReport");
        JSONObject resJson = new JSONObject();
        String dealRes = null ;
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("saveUser.para=" + paraStr);

            JSONObject jsonObject = new JSONObject(paraStr);
            JSONObject tJSONObject = jsonObject.getJSONObject("params");

            mLogger.info("jsonObject=" + jsonObject.toString());
            //** 业务处理开始，查询、增加、修改、或删除 **//
            sSDUserOperationService.insert(SDUserOperationService.MENU_DATAANALYSIS_SUMREQ, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

            int BreedBatchId = tJSONObject.optInt("BreedBatchId");
            int houseId = tJSONObject.optInt("HouseId");
            int farmId = tJSONObject.optInt("FarmId");
            String compareType = tJSONObject.getString("compareType");
            JSONArray jsonArray = new JSONArray();
            String SQLdata = " ";
            
            // 批次
            if(compareType.equals("01")){
            	SQLdata = "SELECT " +
                        " truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0)                   AS feed_days," +
                        " truncate(ifnull(avg(a.moveout_num),0),0)                                                    AS market_num," +
                        " truncate(ifnull(avg(a.moveout_num)/avg(a.place_num), 0) * 100, 1)                AS market_rate," +
                        " truncate(ifnull(avg(a.moveout_weight), 0), 2)                                    AS body_weight_avg," +
                        " truncate(ifnull(avg(a.moveout_sumweight), 0), 2)                                 AS moveout_sumweight," +
                        " ifnull(avg(a.market_feed_weight), 0)                                                  AS acc_feed," +
                        " truncate(ifnull(avg(a.market_feed_weight)/avg(a.moveout_sumweight), 0), 2)              AS fcr," +
                        " truncate(ifnull(avg(a.place_num), 0), 0)                                                       AS place_num , " + 
                        " h.house_name as name, h.id as orderid " +
                        " FROM s_b_house_breed a ,s_d_house h  " +
                        " WHERE 1=1 " + 
                        " and a.farm_breed_id = " + BreedBatchId + 
                        " AND a.farm_id = h.farm_id " + 
                        " AND a.batch_status = '02' AND a.house_id = h.id GROUP BY h.id " + 
                        " union " + 
                        " SELECT " + 
                        " truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0) AS feed_days," + 
                        " truncate(ifnull(sum(a.moveout_num), 0), 0)                                      AS market_num," + 
                        " truncate(ifnull(sum(a.moveout_num)/sum(a.place_num),0) * 100, 1)   AS market_rate," + 
                        " truncate(ifnull(avg(a.moveout_weight), 0), 2)                      AS body_weight_avg," + 
                        " truncate(ifnull(sum(a.moveout_sumweight), 0), 2)                   AS moveout_sumweight," + 
                        " ifnull(sum(a.market_feed_weight), 0)                                    AS acc_feed," + 
                        " truncate(ifnull(sum(a.market_feed_weight) / sum(a.moveout_sumweight), 0), 2) AS fcr," + 
                        " truncate(ifnull(sum(a.place_num), 0), 0)                                        AS place_num," + 
                        " '全场'                                                       AS name," + 
                        " 999999 as orderid " + 
                        " FROM s_b_house_breed a, s_d_house h " + 
                        " WHERE 1=1 " +
                        " and a.farm_id = " + farmId + 
                        " and a.farm_breed_id = " + BreedBatchId + 
                        " AND a.farm_id = h.farm_id AND a.house_id = h.id " + 
                        " and a.batch_status = '02' " + 
                        " ORDER BY orderid ";
                
            }else{
            	if(houseId == 0){
            		SQLdata = " SELECT" + 
            				" truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0)           AS feed_days," + 
            				" truncate(ifnull(sum(a.moveout_num), 0), 0)                                   AS market_num," + 
            				" truncate(ifnull(sum(a.moveout_num) / sum(a.place_num), 0) * 100, 1)          AS market_rate," + 
            				" truncate(ifnull(avg(a.moveout_weight), 0), 2)                                AS body_weight_avg," + 
            				" truncate(ifnull(sum(a.moveout_sumweight), 0), 2)                             AS moveout_sumweight," + 
            				" ifnull(sum(a.market_feed_weight), 0)                                         AS acc_feed," + 
            				" truncate(ifnull(sum(a.market_feed_weight) / sum(a.moveout_sumweight), 0), 2) AS fcr," + 
            				" truncate(ifnull(sum(a.place_num), 0), 0)                                     AS place_num," + 
            				" b.batch_code                                                                 AS name" + 
            				" FROM s_b_house_breed a, s_b_farm_breed b" + 
            				" WHERE 1 = 1" + 
            				" AND a.farm_id = " + farmId + " AND a.farm_id = b.farm_id AND a.farm_breed_id = b.id" + 
            				" GROUP BY b.batch_code" + 
            				" ORDER BY b.id " ;
            	}else{
            		SQLdata = "SELECT " + 
                			" truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0)  AS feed_days, " +
                			" truncate(ifnull(avg(a.moveout_num), 0), 0)                          AS market_num, " +
                			" truncate(ifnull(avg(a.moveout_num) / avg(a.place_num), 0) * 100, 1) AS market_rate, " +
                			" truncate(ifnull(avg(a.moveout_weight), 0), 2)                       AS body_weight_avg, " +
                			" truncate(ifnull(avg(a.moveout_sumweight), 0), 2)                    AS moveout_sumweight, " +
                			" ifnull(avg(a.market_feed_weight), 0)                                AS acc_feed, " +
                			" truncate(ifnull(avg(a.market_feed_weight)/avg(a.moveout_sumweight), 0), 2)  AS fcr, " +
                			" truncate(ifnull(avg(a.place_num), 0), 0)                            AS place_num, " +
                			" b.batch_code                                                        AS name " +
                			" FROM s_b_house_breed a, s_b_farm_breed b " +
                			" WHERE 1 = 1 " +
                			" AND a.house_id = " + houseId + " AND a.farm_id = b.farm_id AND a.farm_breed_id = b.id " +
                			" GROUP BY b.batch_code " +
                			" ORDER BY b.id " ; 
            	}
            }
            
            mLogger.info("FarmManageReqController.getProductionSumReport.SQLdata = " + SQLdata);
            List<HashMap<String, Object>> data = tBaseQueryService.selectMapByAny(SQLdata);
            for (HashMap<String, Object> cc : data) {
                String EuropIndexValue = BreedBatchReqController.getEuropIndexValue(
                		PubFun.getDoubleData(cc.get("moveout_sumweight").toString()), 
                		PubFun.getIntegerData(cc.get("market_num").toString()),
                        PubFun.getIntegerData(cc.get("place_num").toString()), 
                        PubFun.getDoubleData(cc.get("acc_feed").toString()), 
                        PubFun.getIntegerData(cc.get("feed_days").toString()));
                
                	JSONObject OverViewJson1 = new JSONObject();
                
                    OverViewJson1.put("feed_days",  !"0".equals(cc.get("feed_days") ) ? cc.get("feed_days") : "-");
                    OverViewJson1.put("place_num", !"0".equals(cc.get("place_num") ) ? cc.get("place_num") : "-");
                    OverViewJson1.put("market_num", !"0".equals(cc.get("market_num") ) ? cc.get("market_num") : "-");
                    OverViewJson1.put("market_rate", !"0.0".equals(cc.get("market_rate") ) ? cc.get("market_rate").toString() + "%" : "-");
                    OverViewJson1.put("body_weight_avg", !"0.00".equals(cc.get("body_weight_avg") ) ? cc.get("body_weight_avg").toString() : "-");
                    OverViewJson1.put("fcr", !"0.0".equals(cc.get("fcr") ) ? cc.get("fcr").toString() : "-");
                    OverViewJson1.put("europ", !"0".equals(EuropIndexValue) ? EuropIndexValue.toString() : "-");
                    OverViewJson1.put("name", cc.get("name"));
                    jsonArray.put(OverViewJson1);

            }
        
            resJson.put("OverView", jsonArray);

            resJson.put("BreedBatchId", BreedBatchId);
            resJson.put("HouseId", houseId);
            resJson.put("Result", "Success");
            resJson.put("compareType", compareType);
            dealRes = Constants.RESULT_SUCCESS;
            resJson.put("ErrorMsg", "");
                
        }catch(Exception e) {
            e.printStackTrace();
            try {
                resJson = new JSONObject();
                resJson.put("Result", "Fail");
                resJson.put("ErrorMsg", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            dealRes = Constants.RESULT_FAIL;
        }
        DealSuccOrFail.dealApp(request,response,dealRes,resJson);
        mLogger.info("======Now end FarmManageReqController.getProductionSumReport");
    }
}

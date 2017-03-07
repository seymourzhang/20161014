package com.mtc.app.controller;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Seymour on 2016/10/31.
 */
@Controller
@RequestMapping("/report")
public class WeekReportReqController {
    private static Logger mLogger = Logger.getLogger(DataInputReqController.class);

    @Autowired
    private BaseQueryService baseQueryService;
    @Autowired
	private SDUserOperationService operationService;
    
    @RequestMapping("/queryWeekReport")
    public void queryWeekReport(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("=====Now start executing WeekReportReqController.queryWeekReport");
        JSONObject resJson = new JSONObject();
        String dealRes = null ;
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("saveUser.para=" + paraStr);

            JSONObject jsonObject = new JSONObject(paraStr);
            int userId = jsonObject.optInt("id_spa");
            operationService.insert(SDUserOperationService.MENU_PRODUCTION_WEEKLY_REPORT, SDUserOperationService.OPERATION_SELECT, userId);
            JSONObject tJSONObject = jsonObject.optJSONObject("params");
            int FarmBreedId = tJSONObject.optInt("FarmBreedId");
            int HouseId = tJSONObject.optInt("HouseId");

            mLogger.info("jsonObject=" + jsonObject.toString());

            JSONArray reportInfo = new JSONArray();


            //** 业务处理开始，查询、增加、修改、或删除 **//*

            String SQL = "SELECT " +
                    "  e.house_breed_id                                            AS house_breed_id, " +
                    "  '累计'                                                        AS weekAge, " +
                    "  sum(e.cur_cd)  AS dc_act_num, " +
                    "  truncate(ifnull(sum(e.cur_cd) / d.place_num * 100,0), 2)            AS dc_act_rate, " +
                    "  truncate(ifnull((select max(c.cum_motality) from s_b_chicken_standar c where c.farm_id = d.farm_id and c.age <= max(e.age)),0), 2) AS dc_stan_rate, " +
                    "  truncate(ifnull(sum(e.cur_feed), 0),0)                      AS intake_act_sum, " +
                    "  truncate(ifnull(SUM(e.cur_feed) / min(e.cur_amount) / max(e.age) * 1000,0), 0) AS intake_act_sig, " +
                    "  (select truncate(ifnull(avg(c.daily_intake), 0),0) from s_b_chicken_standar c where c.farm_id = d.farm_id and c.age <= max(e.age) ) AS intake_stan_sig, " +
                    "  truncate(ifnull(sum(e.num_bak1), 0),0)                        AS water_act_sum, " +
                    "  truncate(ifnull(sum(e.num_bak1) / sum(e.cur_feed) , 0), 2)   AS ratio_water_feed, " +
                    "  truncate(ifnull(max(e.cur_weight), 0), 0)  AS body_weight_actual, " +
                    "  truncate(ifnull((select max(c.body_weight) from s_b_chicken_standar c where c.farm_id = d.farm_id and c.age <= max(e.age)),0), 0) AS body_weight_standard, " +
                    "  truncate(ifnull(max(e.acc_feed) / (max(e.cur_weight) * min(e.cur_amount)/1000), 0), 2) AS ratio_intake_body " +
                    "FROM s_b_house_breed d " +
                    "  LEFT JOIN s_b_breed_detail e ON d.id = e.house_breed_id " +
                    "WHERE d.farm_breed_id = " + FarmBreedId + " AND d.house_id = " + HouseId + " and e.growth_date <= curdate() " +
                    "group by e.house_breed_id " +
                    "union all " +
                    "SELECT " +
                    "  b.house_breed_id                                            AS house_breed_id, " +
                    "  ceil(if(b.age = 0, 1, b.age) / 7)                           AS weekAge, " +
                    "  sum(b.cur_cd)  AS dc_act_num, " +
                    "  truncate(ifnull(SUM(b.cur_cd) / (sum(b.cur_cd) + min(b.cur_amount)) * 100,0),2) AS dc_act_rate, " +
                    "  (select truncate(ifnull(sum(c.daily_motality), 0),2) from s_b_chicken_standar c where c.farm_id = a.farm_id and ceil(if(c.age = 0, 1, c.age) / 7) = ceil(if(b.age = 0, 1, b.age) / 7)) AS dc_stan_rate, " +
                    "  truncate(ifnull(sum(b.cur_feed), 0),0)                      AS intake_act_sum, " +
                    "  truncate(ifnull(SUM(b.cur_feed) / (sum(b.cur_cd) + min(b.cur_amount)) / 7 * 1000,0),0) AS intake_act_sig, " +
                    "  (select truncate(ifnull(avg(c.daily_intake), 0),0) from s_b_chicken_standar c where c.farm_id = a.farm_id and ceil(if(c.age = 0, 1, c.age) / 7) = ceil(if(b.age = 0, 1, b.age) / 7)) AS intake_stan_sig, " +
                    "  truncate(ifnull(sum(b.num_bak1), 0),0)                        AS water_act_sum, " +
                    "  truncate(ifnull(sum(b.num_bak1) / sum(b.cur_feed) , 0), 2)   AS ratio_water_feed, " +
                    "  truncate(ifnull(avg(case when b.cur_weight = 0 then null else b.cur_weight end), 0), 0)  AS body_weight_actual, " +
                    "  (select truncate(ifnull(max(c.body_weight), 0),0) from s_b_chicken_standar c where c.farm_id = a.farm_id and ceil(if(c.age = 0, 1, c.age) / 7) = ceil(if(b.age = 0, 1, b.age) / 7)) AS body_weight_standard, " +
                    "  truncate(ifnull(max(b.acc_feed) / (avg(case when b.cur_weight = 0 then null else b.cur_weight end) * min(b.cur_amount)/1000), 0), 2) AS ratio_intake_body " +
                    "FROM s_b_house_breed a " +
                    "  LEFT JOIN s_b_breed_detail b ON a.id = b.house_breed_id " +
                    "  LEFT JOIN s_b_chicken_standar c ON a.farm_id = c.farm_id AND b.age = c.age " +
                    "WHERE a.farm_breed_id = " + FarmBreedId + " AND a.house_id = " + HouseId + " and b.growth_date <= curdate() " +
                    "GROUP BY ceil(if(b.age = 0, 1, b.age) / 7),b.house_breed_id ";
            mLogger.info("@@@@@@@@@@@@@@@@@@ WeekReportReqController.queryWeekReport.SQL=" + SQL);
            List<HashMap<String,Object>> tDatas = baseQueryService.selectMapByAny(SQL);
            if (tDatas.size() == 0){
                resJson.put("Result", "Fail");
                resJson.put("ErrorMsg", "暂无数据！");
                resJson.put("reportInfo", reportInfo);
                dealRes = Constants.RESULT_SUCCESS;
            } else {
                double dcNum = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    JSONObject info = new JSONObject();
                    info.put("age", tData.get("weekAge"));
                    info.put("dc_act_num", tData.get("dc_act_num").toString());
                    info.put("dc_act_rate", tData.get("dc_act_rate").toString());
                    info.put("dc_stan_rate", tData.get("dc_stan_rate").toString());
                    info.put("intake_act_sum", tData.get("intake_act_sum").toString());
                    info.put("intake_act_sig", tData.get("intake_act_sig").toString());
                    info.put("intake_stan_sig", tData.get("intake_stan_sig").toString());
                    info.put("water_act_sum", tData.get("water_act_sum").toString());
                    info.put("ratio_water_feed", tData.get("ratio_water_feed").toString());
                    info.put("body_weight_actual", tData.get("body_weight_actual").toString());
                    info.put("body_weight_standard", tData.get("body_weight_standard").toString());
                    info.put("ratio_intake_body", tData.get("ratio_intake_body").toString());
                    reportInfo.put(info);
                }
                resJson.put("reportInfo", reportInfo);
                resJson.put("HouseBreedId", tDatas.get(0).get("house_breed_id"));
                resJson.put("HouseId", HouseId);
                resJson.put("Result", "Success");
                resJson.put("ErrorMsg", "");
                dealRes = Constants.RESULT_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resJson = new JSONObject();
                resJson.put("ErrorMsg", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            dealRes = Constants.RESULT_FAIL ;
        }
        DealSuccOrFail.dealApp(request,response,dealRes,resJson);

        mLogger.info("=====Now end executing WeekReportReqController.queryWeekReport");
    }
}

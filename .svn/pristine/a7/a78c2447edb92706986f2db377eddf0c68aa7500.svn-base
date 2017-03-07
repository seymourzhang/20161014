package com.mtc.app.controller;

import com.mtc.app.biz.BroilerStandardReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBChickenStandarService;
import com.mtc.app.service.SBFarmStandarService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBChickenStandar;
import com.mtc.entity.app.SBFarmStandar;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.round;

/**
 * Created by Ants on 2016/6/12.
 */
@Controller
@RequestMapping("/standard")
public class FarmStandardReqController {
    private static Logger mLogger = Logger.getLogger(FarmStandardReqController.class);

    @Autowired
	private BaseQueryService tBaseQueryService;

    @Autowired
    private SBChickenStandarService sSBChickenStandarService;

    @Autowired
    private SBFarmStandarService sSBFarmStandarService;

    @Autowired
    private BroilerStandardReqManager bStandardReqManager;

    @Autowired
	private SDUserOperationService operationService;

    @RequestMapping("/detailQuery")
    public void detailQuery(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======== Now start FarmStandardReqController.detailQuery");
        JSONObject resJson = new JSONObject();
        JSONArray DetailData = new JSONArray();
        String dealRes = null;
        try{
            String params = PubFun.getRequestPara(request);
            JSONObject jsonObject = new JSONObject(params);
            JSONObject mJsonObject = jsonObject.getJSONObject("params");
            String standardName = mJsonObject.optString("StandardName");
            int farmId = mJsonObject.optInt("FarmId");
            int userId = jsonObject.optInt("id_spa");
            operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_STANDARD_QUERY,SDUserOperationService.OPERATION_SELECT,userId);

            String SQL = "SELECT a.age, a.breed_name, ifnull(truncate(round(sum(CASE WHEN breed_name IN ('10005', '10004', '20001', '10003', '10002', '10001') THEN cum_motality ELSE NULL END), 2),2),0) AS acc_cdreate1, " +
                    "ifnull(sum(CASE WHEN feed_type = 'mixed' THEN body_weight ELSE NULL END),0) AS body_weight1, " +
                    "ifnull(sum(CASE WHEN feed_type = 'mixed' AND a.body_weight <> 0 AND (SELECT b.body_weight FROM s_b_chicken_standar b WHERE b.farm_id = a.farm_id AND b.breed_name = a.breed_name AND b.feed_type = a.feed_type AND" +
                    " b.age = a.age - 1) <> 0 THEN " +
                    "a.body_weight - " +
                    "(SELECT b.body_weight FROM s_b_chicken_standar b WHERE b.farm_id = a.farm_id AND b.breed_name = a.breed_name AND b.feed_type = a.feed_type AND b.age = a.age - 1) " +
                    "ELSE NULL END),0) AS daily_bddiff1, " +
                    "ifnull(sum(CASE WHEN feed_type = 'mixed' THEN daily_intake ELSE NULL END),0) AS daily_feed1, " +
                    "ifnull(sum(CASE WHEN feed_type = 'mixed' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) " +
                    "ELSE NULL END),0) AS acc_feed1, " +
                    "ifnull(truncate(round(sum(CASE WHEN feed_type = 'mixed' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) / a.body_weight " +
                    "ELSE NULL END), 3),3),0) AS FCR1, " +
                    "ifnull(truncate(round(sum(CASE WHEN breed_name IN ('10005', '10004', '20001') THEN cum_motality ELSE NULL END), 2),2),0) AS acc_cdreate2, " +
                    "ifnull(sum(CASE WHEN feed_type = 'cock' THEN body_weight ELSE NULL END),0) AS body_weight2, " +
                    "ifnull(sum(CASE WHEN feed_type = 'cock' THEN a.body_weight - " +
                    "(SELECT b.body_weight FROM s_b_chicken_standar b WHERE b.farm_id = a.farm_id AND b.breed_name = a.breed_name AND b.feed_type = a.feed_type AND b.age = a.age - 1) " +
                    "ELSE NULL END),0) AS daily_bddiff2, " +
                    "ifnull(sum(CASE WHEN feed_type = 'cock' THEN daily_intake ELSE NULL END),0) AS daily_feed2, " +
                    "ifnull(sum(CASE WHEN feed_type = 'cock' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) " +
                    "ELSE NULL END), 0) AS acc_feed2, " +
                    "ifnull( truncate(round(sum(CASE WHEN feed_type = 'cock' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) / a.body_weight " +
                    "ELSE NULL END), 3),3),0) AS FCR2, " +
                    "ifnull(truncate(round(sum(CASE WHEN breed_name IN ('10005', '10004', '20001') THEN cum_motality ELSE NULL END), 2),2),0) AS acc_cdreate3, " +
                    "ifnull(sum(CASE WHEN feed_type = 'hen' THEN body_weight ELSE NULL END),0) AS body_weight3, " +
                    "ifnull(sum(CASE WHEN feed_type = 'hen' THEN a.body_weight - " +
                    "(SELECT b.body_weight FROM s_b_chicken_standar b WHERE b.farm_id = a.farm_id AND b.breed_name = a.breed_name AND b.feed_type = a.feed_type AND b.age = a.age - 1) " +
                    "ELSE NULL END),0) AS daily_bddiff3, " +
                    "ifnull(sum(CASE WHEN feed_type = 'hen' THEN daily_intake ELSE NULL END),0) AS daily_feed3, " +
                    "ifnull(sum(CASE WHEN feed_type = 'hen' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) " +
                    "ELSE NULL END),0) AS acc_feed3, " +
                    "ifnull(truncate(round(sum(CASE WHEN feed_type = 'hen' THEN " +
                    "(SELECT sum(c.daily_intake) FROM s_b_chicken_standar c WHERE c.farm_id = a.farm_id AND c.breed_name = a.breed_name AND c.feed_type = a.feed_type AND c.age BETWEEN 0 AND a.age GROUP BY c.breed_name) / a.body_weight " +
                    "ELSE NULL END), 3),3),0) AS FCR3 " +
                    "FROM s_b_chicken_standar a ";
            if(standardName.equals("20001")) {
                SQL += "WHERE  a.farm_id = " + farmId + " GROUP BY a.age";
            } else {
                SQL += "WHERE a.breed_name = " + standardName + " AND a.farm_id = 0 GROUP BY a.age";
            }
            mLogger.info("=========FarmStandardReqController.detailQuery.SQL=" + SQL);
            List<HashMap<String,Object>> details = tBaseQueryService.selectMapByAny(SQL);
            String flagBehind = null;
            for(HashMap<String,Object> aa : details) {
                 flagBehind = (String)aa.get("breed_name");
            }

            if(details.size() != 0) {
                if (standardName.equals("10001") || standardName.equals("10002") || standardName.equals("10003")) {
                    DetailData = new JSONArray();
                    for (int i = 0; i < details.size(); ++i) {
                        JSONObject za = new JSONObject();
                        Object growthAge = details.get(i).get("age");
                        String accCdreate1 = details.get(i).get("acc_cdreate1").toString();
                        String bodyWeight1 = details.get(i).get("body_weight1").toString();
                        String dailyBddiff1 = details.get(i).get("daily_bddiff1").toString();
                        String dailyFeed1 = details.get(i).get("daily_feed1").toString();
                        String acc_feed1 = details.get(i).get("acc_feed1").toString();
                        String FCR1 = details.get(i).get("FCR1").toString();
//                        String accCdreate2 = details.get(i).get("acc_cdreate2").toString();
                        String bodyWeight2 = details.get(i).get("body_weight2").toString();
                        String dailyBddiff2 = details.get(i).get("daily_bddiff2").toString();
                        String dailyFeed2 = details.get(i).get("daily_feed2").toString();
                        String acc_feed2 = details.get(i).get("acc_feed2").toString();
                        String FCR2 = details.get(i).get("FCR2").toString();
//                        String accCdreate3 = details.get(i).get("acc_cdreate3").toString();
                        String bodyWeight3 = details.get(i).get("body_weight3").toString();
                        String dailyBddiff3 = details.get(i).get("daily_bddiff3").toString();
                        String dailyFeed3 = details.get(i).get("daily_feed3").toString();
                        String acc_feed3 = details.get(i).get("acc_feed3").toString();
                        String FCR3 = details.get(i).get("FCR3").toString();

                        za.put("growth_age", growthAge);
//                        za.put("acc_cdreate1", accCdreate1);
                        za.put("body_weight1", round(Double.parseDouble(bodyWeight1)) == 0 ? "-" : round(Double.parseDouble(bodyWeight1)));
                        za.put("daily_bddiff1", round(Double.parseDouble(bodyWeight1)) == 0 ? "-" : round(Double.parseDouble(dailyBddiff1)));
                        za.put("daily_feed1", round(Double.parseDouble(dailyFeed1)));
                        za.put("acc_feed1", round(Double.parseDouble(acc_feed1)));
                        za.put("FCR1", FCR1);
//                        za.put("acc_cdreate2", accCdreate2);
                        za.put("body_weight2", round(Double.parseDouble(bodyWeight2)) == 0 ? "-" : round(Double.parseDouble(bodyWeight2)));
                        za.put("daily_bddiff2", round(Double.parseDouble(bodyWeight2)) == 0 ? "-" : round(Double.parseDouble(dailyBddiff2)));
                        za.put("daily_feed2", round(Double.parseDouble(dailyFeed2)));
                        za.put("acc_feed2", round(Double.parseDouble(acc_feed2)));
                        za.put("FCR2", FCR2);
//                        za.put("acc_cdreate3", accCdreate3);
                        za.put("body_weight3", round(Double.parseDouble(bodyWeight3)) == 0 ? "-" : round(Double.parseDouble(bodyWeight3)));
                        za.put("daily_bddiff3", round(Double.parseDouble(bodyWeight3)) == 0 ? "-" : round(Double.parseDouble(dailyBddiff3)));
                        za.put("daily_feed3", round(Double.parseDouble(dailyFeed3)));
                        za.put("acc_feed3", round(Double.parseDouble(acc_feed3)));
                        za.put("FCR3", FCR3);
                        DetailData.put(za);
                    }
                    resJson.put("Result", "Succ");
                    dealRes = Constants.RESULT_SUCCESS;
                } else if (standardName.equals("10004") || standardName.equals("10005") || standardName.equals("20001")) {
                    DetailData = new JSONArray();
                    for (int i = 0; i < details.size(); ++i) {
                        JSONObject za = new JSONObject();
                        Object growthAge = details.get(i).get("age");
                        String accCdreate1 = details.get(i).get("acc_cdreate1").toString();
                        String bodyWeight1 = details.get(i).get("body_weight1").toString();
                        String dailyBddiff1 = details.get(i).get("daily_bddiff1").toString();
                        String dailyFeed1 = details.get(i).get("daily_feed1").toString();
                        String acc_feed1 = details.get(i).get("acc_feed1").toString();
                        String FCR1 = details.get(i).get("FCR1").toString();

                        za.put("growth_age", growthAge);
                        za.put("acc_cdreate1", accCdreate1);
                        za.put("body_weight1", round(Double.parseDouble(bodyWeight1)) == 0 ? "-" : round(Double.parseDouble(bodyWeight1)));
                        za.put("daily_bddiff1", (round(Double.parseDouble(bodyWeight1)) == 0) || (round(Double.parseDouble(dailyBddiff1)) == 0) ? "-" : round(Double.parseDouble(dailyBddiff1)));
                        za.put("daily_feed1", round(Double.parseDouble(dailyFeed1)));
                        za.put("acc_feed1", round(Double.parseDouble(acc_feed1)));
                        za.put("FCR1", FCR1);
                        DetailData.put(za);
                    }
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("Result", "Succ");
                }
            } else {
                resJson.put("Result", "Fail");
                resJson.put("ErrorMsg", "暂无数据，请先设定标准。");
                dealRes = Constants.RESULT_SUCCESS;
            }
            resJson.put("DetailData", DetailData);
            resJson.put("BreedName", standardName);
        } catch (Exception e){
            dealRes = Constants.RESULT_FAIL;
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now end FarmStandardReqController.detailQuery");
    }

    @RequestMapping("/SettingQuery")
    public void SettingQuery(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======== Now start FarmStandardReqController.SettingQuery");
        JSONObject resJson = new JSONObject();
        JSONArray DetailData = new JSONArray();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject jsonObject = new JSONObject(params);
            JSONObject mJsonObject = jsonObject.getJSONObject("params");
//            String standardName = mJsonObject.optString("StandardName");
            int farmId = mJsonObject.optInt("FarmId");
            int userId = jsonObject.optInt("id_spa");
            operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTING_STANDARD,SDUserOperationService.OPERATION_SELECT,userId);


           String SQL = "SELECT fs.breed_name, ifnull(fs.mortality_flag, 'N')  AS mortality_flag, ifnull(fs.daily_motality, 'N')  AS daily_motality, " +
                   "ifnull(fs.cum_motality, 0)   AS cum_motality, ifnull(fs.daily_intake, 0) AS daily_intake, ifnull(fs.body_weight, 0) AS body_weight, " +
                   "cum_datas.* " +
                   "FROM s_b_farm_standar AS fs " +
                   "LEFT JOIN ( " +
                   "select " +
                   "ifnull(sum(CASE cs.age WHEN 7 THEN cs.cum_motality ELSE NULL END), 0) AS week1, " +
                   "ifnull(sum(CASE cs.age WHEN 14 THEN cs.cum_motality ELSE NULL END), 0) AS week2, " +
                   "ifnull(sum(CASE cs.age WHEN 21 THEN cs.cum_motality ELSE NULL END), 0) AS week3, " +
                   "ifnull(sum(CASE cs.age WHEN 28 THEN cs.cum_motality ELSE NULL END), 0) AS week4, " +
                   "ifnull(sum(CASE cs.age WHEN 35 THEN cs.cum_motality ELSE NULL END), 0) AS week5, " +
                   "ifnull(sum(CASE cs.age WHEN 42 THEN cs.cum_motality ELSE NULL END), 0) AS week6, " +
                   "ifnull(sum(CASE cs.age WHEN 49 THEN cs.cum_motality ELSE NULL END), 0) AS week7 " +
                   "from s_b_chicken_standar AS cs where 1=1 " +
                   "and cs.farm_id = " + farmId + " AND cs.feed_type = 'mixed'" +
                   ") as cum_datas on 1=1 WHERE fs.farm_id = " + farmId + "";
            mLogger.info("=========FarmStandardReqController.SettingQuery.SQL=" + SQL);
            List<HashMap<String, Object>> details = tBaseQueryService.selectMapByAny(SQL);
                DetailData = new JSONArray();
                if(details.size() == 0){
                    resJson.put("Result", "Fail");
                    resJson.put("ErrorMsg", "暂无数据，请先设定标准。");
                    dealRes = Constants.RESULT_SUCCESS;
                } else {
                    for (int i = 0; i < details.size(); ++i) {
                        JSONObject za = new JSONObject();
                        JSONObject zb = new JSONObject();
                        JSONObject zc = new JSONObject();
                        Object growthAge = details.get(i).get("age");
                        String breedName = details.get(i).get("breed_name").toString();
                        String week1 = details.get(i).get("week1").toString();
                        String week2 = details.get(i).get("week2").toString();
                        String week3 = details.get(i).get("week3").toString();
                        String week4 = details.get(i).get("week4").toString();
                        String week5 = details.get(i).get("week5").toString();
                        String week6 = details.get(i).get("week6").toString();
                        String week7 = details.get(i).get("week7").toString();
                        String mortalityFlag = details.get(i).get("mortality_flag").toString();
                        String dailyMotality = details.get(i).get("daily_motality").toString();
                        String cumMotality = details.get(i).get("cum_motality").toString();
                        String dailyIntake = details.get(i).get("daily_intake").toString();
                        String bodyWeight = details.get(i).get("body_weight").toString();

                        zb.put("Max_Mortality", cumMotality);
                        zb.put("Max_feed", dailyIntake);
                        zb.put("Min_body_weight", bodyWeight);

                        zc.put("week1", week1);
                        zc.put("week2", week2);
                        zc.put("week3", week3);
                        zc.put("week4", week4);
                        zc.put("week5", week5);
                        zc.put("week6", week6);
                        zc.put("week7", week7);

                        za.put("growth_age", growthAge);
                        za.put("farm_standard", breedName);
                        za.put("need_mortality", mortalityFlag);
                        za.put("need_manage_alert", dailyMotality);
                        za.put("manage_alert", zc);
                        za.put("cum_mortality", zb);

                        DetailData.put(za);
                    }
                    resJson.put("DetailData", DetailData);
                    resJson.put("Result", "Succ");
                    dealRes = Constants.RESULT_SUCCESS;
                }
        } catch (Exception e) {
            dealRes = Constants.RESULT_FAIL;
//            resJson.put("Result", "Fail");
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now end FarmStandardReqController.SettingQuery");
    }

    @RequestMapping("/SettingSave")
    public void SettingSave(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======== Now start FarmStandardReqController.SettingSave");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(params);
            JSONObject paramContents = op.getJSONObject("params");
            JSONObject settingData = paramContents.optJSONObject("SettingData");

            int userId = op.optInt("id_spa");

            int farmId = settingData.getInt("Farm_Id");
            String farmStandard = settingData.optString("farm_standard");
            String needMortality = settingData.optString("need_mortality");
            String needManageAlert = settingData.optString("need_manage_alert");

            JSONObject cumMortality = settingData.optJSONObject("cum_mortality");

            String week1 = cumMortality.optString("week1");
            String week2 = cumMortality.optString("week2");
            String week3 = cumMortality.optString("week3");
            String week4 = cumMortality.optString("week4");
            String week5 = cumMortality.optString("week5");
            String week6 = cumMortality.optString("week6");
            String week7 = cumMortality.optString("week7");

            JSONObject manageAlert = settingData.optJSONObject("manage_alert");
            BigDecimal maxMortality = new BigDecimal(manageAlert.optString("Max_Mortality"));
            BigDecimal maxFeed = new BigDecimal(manageAlert.optString("Max_feed"));
            BigDecimal minBodyWeight = new BigDecimal(manageAlert.optString("Min_body_weight"));

            HashMap<String,Object> dealSave = new HashMap<String,Object>();
           String SQL = "SELECT fs.breed_name, ifnull(fs.mortality_flag, 'N')  AS mortality_flag, ifnull(fs.daily_motality, 'N')  AS daily_motality, " +
                   "ifnull(fs.cum_motality, 0)   AS cum_motality, ifnull(fs.daily_intake, 0) AS daily_intake, ifnull(fs.body_weight, 0) AS body_weight, " +
                   "cum_datas.* " +
                   "FROM s_b_farm_standar AS fs " +
                   "LEFT JOIN ( " +
                   "select " +
                   "ifnull(sum(CASE cs.age WHEN 7 THEN cs.cum_motality ELSE NULL END), 0) AS week1, " +
                   "ifnull(sum(CASE cs.age WHEN 14 THEN cs.cum_motality ELSE NULL END), 0) AS week2, " +
                   "ifnull(sum(CASE cs.age WHEN 21 THEN cs.cum_motality ELSE NULL END), 0) AS week3, " +
                   "ifnull(sum(CASE cs.age WHEN 28 THEN cs.cum_motality ELSE NULL END), 0) AS week4, " +
                   "ifnull(sum(CASE cs.age WHEN 35 THEN cs.cum_motality ELSE NULL END), 0) AS week5, " +
                   "ifnull(sum(CASE cs.age WHEN 42 THEN cs.cum_motality ELSE NULL END), 0) AS week6, " +
                   "ifnull(sum(CASE cs.age WHEN 49 THEN cs.cum_motality ELSE NULL END), 0) AS week7 " +
                   "from s_b_chicken_standar AS cs where 1=1 " +
                   "and cs.farm_id = " + farmId + " AND cs.feed_type = 'mixed'" +
                   ") as cum_datas on 1=1 WHERE fs.farm_id = " + farmId + "";
            List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQL);
            if(tDatas.isEmpty()) {
                /**新增操作*/
            	operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTING_STANDARD,SDUserOperationService.OPERATION_ADD,userId);

                try {
                    /**准备对应信息，改变farmId，进行插入操作.进行第7、14、21、28、35、42、49周数据的修改*/
                    List<SBChickenStandar> tt = sSBChickenStandarService.selectForInsert(farmStandard);
                    for (int i = 0; i < tt.size(); ++i) {
                    	if(needMortality.equals("Y")) {
                            if (tt.get(i).getAge() == 7) {
                                tt.get(i).setCumMotality(new BigDecimal(week1));
                            } else if (tt.get(i).getAge() == 14) {
                                tt.get(i).setCumMotality(new BigDecimal(week2));
                            } else if (tt.get(i).getAge() == 21) {
                                tt.get(i).setCumMotality(new BigDecimal(week3));
                            } else if (tt.get(i).getAge() == 28) {
                                tt.get(i).setCumMotality(new BigDecimal(week4));
                            } else if (tt.get(i).getAge() == 35) {
                                tt.get(i).setCumMotality(new BigDecimal(week5));
                            } else if (tt.get(i).getAge() == 42) {
                                tt.get(i).setCumMotality(new BigDecimal(week6));
                            } else if (tt.get(i).getAge() == 49) {
                                tt.get(i).setCumMotality(new BigDecimal(week7));
                            }
                    	}
                    	tt.get(i).setId(null);
                        tt.get(i).setFarmId(farmId);
                        tt.get(i).setBreedName(farmStandard);
                        tt.get(i).setCreatePerson(userId);
                        tt.get(i).setModifyPerson(userId);
                        tt.get(i).setCreateDate(new Date());
                        tt.get(i).setCreateTime(new Date());
                        tt.get(i).setModifyDate(new Date());
                        tt.get(i).setModifyTime(new Date());
                    }
                    dealSave.put("ckStandard", tt);

                    SBFarmStandar sSBFarmStandar = new SBFarmStandar();
                    /*更新s_b_farm_standar表*/
                    sSBFarmStandar.setFarmId(farmId);
                    sSBFarmStandar.setBreedName(farmStandard);
                    sSBFarmStandar.setFeedType("mixed");
                    sSBFarmStandar.setMortalityFlag(needMortality);
                    sSBFarmStandar.setDailyMotality(needManageAlert);
                    if(needManageAlert.equals("Y")) {
                        sSBFarmStandar.setCumMotality(maxMortality);
                        sSBFarmStandar.setDailyIntake(maxFeed);
                        sSBFarmStandar.setBodyWeight(minBodyWeight);
                    }else{
                    	sSBFarmStandar.setCumMotality(new BigDecimal(0));
                        sSBFarmStandar.setDailyIntake(new BigDecimal(0));
                        sSBFarmStandar.setBodyWeight(new BigDecimal(0));
                    }
                    sSBFarmStandar.setBak1(null);
                    sSBFarmStandar.setBak2(null);
                    sSBFarmStandar.setBak3(null);
                    sSBFarmStandar.setCreatePerson(userId);
                    sSBFarmStandar.setCreateDate(new Date());
                    sSBFarmStandar.setCreateTime(new Date());
                    sSBFarmStandar.setModifyPerson(userId);
                    sSBFarmStandar.setModifyDate(new Date());
                    sSBFarmStandar.setModifyTime(new Date());
                    dealSave.put("faStandard", sSBFarmStandar);
                    bStandardReqManager.dealStandardSave(dealSave);
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("Result","Succ");
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {
                /**修改操作*/
            	operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTING_STANDARD,SDUserOperationService.OPERATION_UPDATE,userId);

            	HashMap<String,Object> dealUpdate = new HashMap<String,Object>();
                String standardName1 = null;
                for(HashMap<String,Object> ss : tDatas){
                     standardName1 = (String)ss.get("breed_name");
                }

                if(farmStandard.equals(standardName1)){
                    dealUpdate.put("flag", farmStandard.equals(standardName1));
                    List<SBChickenStandar> tt = sSBChickenStandarService.selectForOwner(farmId);
                    for (int j = 0; j < tt.size(); ++j) {
                    	if(needMortality.equals("Y")) {
                            if (tt.get(j).getAge() == 7) {
                                tt.get(j).setCumMotality(new BigDecimal(week1));
                            } else if (tt.get(j).getAge() == 14) {
                                tt.get(j).setCumMotality(new BigDecimal(week2));
                            } else if (tt.get(j).getAge() == 21) {
                                tt.get(j).setCumMotality(new BigDecimal(week3));
                            } else if (tt.get(j).getAge() == 28) {
                                tt.get(j).setCumMotality(new BigDecimal(week4));
                            } else if (tt.get(j).getAge() == 35) {
                                tt.get(j).setCumMotality(new BigDecimal(week5));
                            } else if (tt.get(j).getAge() == 42) {
                                tt.get(j).setCumMotality(new BigDecimal(week6));
                            } else if (tt.get(j).getAge() == 49) {
                                tt.get(j).setCumMotality(new BigDecimal(week7));
                            }
                    	}
                        tt.get(j).setModifyPerson(userId);
                        tt.get(j).setModifyDate(new Date());
                        tt.get(j).setModifyTime(new Date());
                    }
                    dealUpdate.put("ckStandard", tt);

                    SBFarmStandar sdatas = sSBFarmStandarService.select(farmId);
                    sdatas.setMortalityFlag(needMortality);
                    sdatas.setDailyMotality(needManageAlert);
                    if (needManageAlert.equals("Y")) {
                    	sdatas.setCumMotality(maxMortality);
                        sdatas.setDailyIntake(maxFeed);
                        sdatas.setBodyWeight(minBodyWeight);
                    }
                    sdatas.setModifyDate(new Date());
                    sdatas.setModifyTime(new Date());
                    dealUpdate.put("faStandard", sdatas);
                    bStandardReqManager.dealStandardUpdate(dealUpdate);

                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("Result","Succ");
                }else{
                    dealUpdate.put("flag", farmStandard.equals(standardName1));
                    List<SBChickenStandar> tt = sSBChickenStandarService.selectForInsert(farmStandard);
                    /**先delete*/
                    String SQLForDel = "DELETE FROM s_b_chicken_standar WHERE farm_id = " + farmId;
                    dealUpdate.put("delSql", SQLForDel);
                    /**后insert*/

                    for (int i = 0; i < tt.size(); ++i) {
                    	if(needMortality.equals("Y")) {
                            if (tt.get(i).getAge() == 7) {
                                tt.get(i).setCumMotality(new BigDecimal(week1));
                            } else if (tt.get(i).getAge() == 14) {
                                tt.get(i).setCumMotality(new BigDecimal(week2));
                            } else if (tt.get(i).getAge() == 21) {
                                tt.get(i).setCumMotality(new BigDecimal(week3));
                            } else if (tt.get(i).getAge() == 28) {
                                tt.get(i).setCumMotality(new BigDecimal(week4));
                            } else if (tt.get(i).getAge() == 35) {
                                tt.get(i).setCumMotality(new BigDecimal(week5));
                            } else if (tt.get(i).getAge() == 42) {
                                tt.get(i).setCumMotality(new BigDecimal(week6));
                            } else if (tt.get(i).getAge() == 49) {
                                tt.get(i).setCumMotality(new BigDecimal(week7));
                            }
                    	}
                        tt.get(i).setFarmId(farmId);
                        tt.get(i).setBreedName(farmStandard);
                        tt.get(i).setCreatePerson(userId);
                        tt.get(i).setCreateDate(new Date());
                        tt.get(i).setCreateTime(new Date());
                        tt.get(i).setModifyPerson(userId);
                        tt.get(i).setModifyDate(new Date());
                        tt.get(i).setModifyTime(new Date());
                    }
                    dealUpdate.put("ckStandard", tt);

                    SBFarmStandar sbFarmStandar = sSBFarmStandarService.select(farmId);
                    /*更新s_b_farm_standar表*/
                    sbFarmStandar.setBreedName(farmStandard);
                    sbFarmStandar.setMortalityFlag(needMortality);
                    sbFarmStandar.setDailyMotality(needManageAlert);
                    if(needManageAlert.equals("Y")) {
                        sbFarmStandar.setCumMotality(maxMortality);
                        sbFarmStandar.setDailyIntake(maxFeed);
                        sbFarmStandar.setBodyWeight(minBodyWeight);
                    }else{
                    	sbFarmStandar.setCumMotality(new BigDecimal(0));
                        sbFarmStandar.setDailyIntake(new BigDecimal(0));
                        sbFarmStandar.setBodyWeight(new BigDecimal(0));
                    }
                    sbFarmStandar.setModifyDate(new Date());
                    sbFarmStandar.setModifyTime(new Date());
                    dealUpdate.put("faStandard", sbFarmStandar);

                    bStandardReqManager.dealStandardUpdate(dealUpdate);
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("Result","Succ");
                }
              }
            } catch (Exception e){
                e.printStackTrace();
            }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now end FarmStandardReqController.SettingSave");
    }

    @RequestMapping("/editSave")
    public void editSave(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======== Now start FarmStandardReqController.editSave");
        String paraStr = PubFun.getRequestPara(request);
		mLogger.info("updateFarm.para=" + paraStr);
		String dealRes = Constants.RESULT_FAIL;
		JSONObject resJson = new JSONObject();

		try {

			JSONObject jsonobject = new JSONObject(paraStr);
			mLogger.info("jsonObject=" + jsonobject.toString());
			JSONObject params = jsonobject.optJSONObject("params");
			int userId = jsonobject.optInt("id_spa");
			int farmId = params.getInt("FarmId");

			operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_STANDARD_DIY,SDUserOperationService.OPERATION_UPDATE,userId);


			JSONArray dataInput = params.getJSONArray("DetailData");
			// 需要修改的记录
			List<SBChickenStandar> list = new ArrayList<>();

			for(int i = 0; i < dataInput.length(); i++){
				JSONObject temp = dataInput.getJSONObject(i);
				SBChickenStandar eggsell = new SBChickenStandar();

				eggsell.setFarmId(farmId);

				eggsell.setAge(temp.getInt("growth_age"));
				// 累计死淘 cum_motality
				try{
					eggsell.setCumMotality(new BigDecimal(temp.getDouble("acc_cdreate1")));
				}catch(Exception e){
					eggsell.setCumMotality(new BigDecimal(0));
				}

				// 日采食
				eggsell.setDailyIntake(new BigDecimal(temp.getDouble("daily_feed1")));
				// 体重
				eggsell.setBodyWeight(new BigDecimal(temp.getDouble("body_weight1")));

				eggsell.setModifyDate(new Date(System.currentTimeMillis()));
				eggsell.setModifyPerson(userId);
				eggsell.setModifyTime(eggsell.getModifyDate());

				int res = bStandardReqManager.updateStandardDIY(eggsell);

				//list.add(eggsell);
				mLogger.info("update standard info success,update rows count:" + res);
			}


			//int res = sSBChickenStandarService.updateStandardByBatch(list);
			//mLogger.info("update standard info success,update rows count:" + res);

			dealRes = Constants.RESULT_SUCCESS;
			resJson.put("FarmId", farmId);
			resJson.put("Result", "Success");
			//DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
			try {
				resJson.put("Result", "Fail");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//DealSuccOrFail.dealApp(request, response, dealRes, resJson);
		}
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now start FarmStandardReqController.editSave");
    }

    @RequestMapping("/SettingQuery_v2")
    public void SettingQuery_v2(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======== Now start FarmStandardReqController.SettingQuery");
        JSONObject resJson = new JSONObject();
        JSONArray DetailData = new JSONArray();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject jsonObject = new JSONObject(params);
            JSONObject mJsonObject = jsonObject.getJSONObject("params");
//            String standardName = mJsonObject.optString("StandardName");
            int farmId = mJsonObject.optInt("FarmId");
            int userId = jsonObject.optInt("id_spa");
            operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTING_STANDARD,SDUserOperationService.OPERATION_SELECT,userId);


            String SQL = "SELECT fs.breed_name, ifnull(fs.mortality_flag, 'N')  AS mortality_flag, ifnull(fs.daily_motality, 'N')  AS daily_motality, " +
                    "ifnull(fs.cum_motality, 0)   AS cum_motality, ifnull(fs.daily_intake, 0) AS daily_intake, ifnull(fs.body_weight, 0) AS body_weight, " +
                    "ifnull(fs.bak1, 2) AS mortality_type " +
                    "FROM s_b_farm_standar AS fs " +
                    "WHERE fs.farm_id = " + farmId + "";
            mLogger.info("=========FarmStandardReqController.SettingQuery_v2.SQL=" + SQL);

            String sql = "select age, ifnull(cum_motality,0) as cum_motality, ifnull(bak1,0) as bak1 from s_b_chicken_standar where farm_id = " + farmId +" order by age ";
            List<HashMap<String, Object>> details = tBaseQueryService.selectMapByAny(SQL);
            List<HashMap<String, Object>> standard = tBaseQueryService.selectMapByAny(sql);
            DetailData = new JSONArray();
            JSONObject za = new JSONObject();
            JSONObject zb = new JSONObject();
            JSONObject zc = new JSONObject();
            if(details.size() == 0){
                resJson.put("Result", "Fail");
                resJson.put("ErrorMsg", "暂无数据，请先设定标准。");
                dealRes = Constants.RESULT_SUCCESS;
            } else {
                for (int i = 0; i < details.size(); ++i) {
                    JSONArray array = new JSONArray();
                    Object growthAge = details.get(i).get("age");
                    String breedName = details.get(i).get("breed_name").toString();
                    String mortalityFlag = details.get(i).get("mortality_flag").toString();
                    BigDecimal mortalityType = PubFun.getBigDecimalData(details.get(i).get("mortality_type").toString());
                    if (2 == mortalityType.intValue()) {
                        for (HashMap<String, Object> map : standard) {
                            zc = new JSONObject();
                            if ("7".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("14".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("21".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("28".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("35".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("42".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }else if ("49".equals(map.get("age").toString())) {
                                zc.put("cum_rate", map.get("cum_motality"));
                                zc.put("weekAge", PubFun.getIntegerData(map.get("age").toString())/7);
                                zc.put("cum_alert", map.get("bak1"));
                                array.put(zc);
                            }
                        }
                    }else if (1 == mortalityType.intValue()){
                        for (HashMap<String, Object> map : standard) {
                            zc = new JSONObject();
                            zc.put("dayAge", map.get("age"));
                            zc.put("cum_rate", map.get("cum_motality"));
                            zc.put("cum_alert", map.get("bak1"));
                            array.put(zc);
                        }
                    }
                    za.put("farm_standard", breedName);
                    za.put("mortality_type", mortalityType.intValue());
                    za.put("cum_mortality", array);

                }
                resJson.put("SettingData", za);
                resJson.put("Result", "Succ");
                dealRes = Constants.RESULT_SUCCESS;
            }
        } catch (Exception e) {
            dealRes = Constants.RESULT_FAIL;
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now end FarmStandardReqController.SettingQuery");
    }

    @RequestMapping("/SettingSave_v2")
    public void SettingSave_v2(HttpServletRequest request, HttpServletResponse response) {
        mLogger.info("======== Now start FarmStandardReqController.SettingSave");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(params);
            JSONObject paramContents = op.getJSONObject("params");
            JSONObject settingData = paramContents.optJSONObject("SettingData");

            int userId = op.optInt("id_spa");

            int farmId = settingData.getInt("Farm_Id");
            String farmStandard = settingData.optString("farm_standard");
            String needMortality = settingData.optString("need_mortality");

            String mortalitytype = settingData.optString("mortality_type");
            JSONArray cumMortality = settingData.optJSONArray("cum_mortality");

            HashMap<String, Object> dealSave = new HashMap<String, Object>();
            /**新增操作*/
            operationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTING_STANDARD, SDUserOperationService.OPERATION_ADD, userId);

            /**准备对应信息，改变farmId，进行插入操作。进行数据的修改，然后再插入*/
            List<SBChickenStandar> tt = sSBChickenStandarService.selectForInsert(farmStandard);
            
        	for (int i = 0; i < tt.size(); ++i) {
        		if (needMortality.equals("Y")) {
        			BigDecimal cumRate = new BigDecimal(0);
            		BigDecimal cumAlert = new BigDecimal(0);
            		int dayAgeTemp = tt.get(i).getAge();
            		int dayNum = tt.get(i).getAge() % 7;
            		
        			if("2".equals(mortalitytype)){
                		for (int week = 0; week < cumMortality.length(); ++week) {
                			if((dayAgeTemp-1)/7 == week ){
                				BigDecimal weekBeginRate = week == 0?new BigDecimal(0) : PubFun.getBigDecimalData(cumMortality.getJSONObject(week-1).optString("cum_rate")) ;
                				BigDecimal weekEndRate = PubFun.getBigDecimalData(cumMortality.getJSONObject(week).optString("cum_rate")) ;
                				
                				BigDecimal weekBeginAlert = week == 0?new BigDecimal(0) : PubFun.getBigDecimalData(cumMortality.getJSONObject(week-1).optString("cum_alert")) ;
                				BigDecimal weekEndAlert = PubFun.getBigDecimalData(cumMortality.getJSONObject(week).optString("cum_alert")) ;
                				
                				if(dayNum == 0 && dayAgeTemp != 0){
                					cumRate = weekEndRate ;
                					cumAlert = weekEndAlert ;
                				}else{
                					cumRate = weekBeginRate.add((weekEndRate.subtract(weekBeginRate)).divide(new BigDecimal(7),2,4).multiply(new BigDecimal(dayNum))) ;
                					cumAlert = weekBeginAlert.add((weekEndAlert.subtract(weekBeginAlert)).divide(new BigDecimal(7),2,4).multiply(new BigDecimal(dayNum))) ;
                				}
                			}
                		}
        			}else if("1".equals(mortalitytype)){
        				for (int week = 0; week < cumMortality.length(); ++week) {
        					int dayAge = cumMortality.getJSONObject(week).optInt("dayAge");
        					if (tt.get(i).getAge() == dayAge) {
                                cumRate = PubFun.getBigDecimalData(cumMortality.getJSONObject(week).optString("cum_rate"));
                                cumAlert = PubFun.getBigDecimalData(cumMortality.getJSONObject(week).optString("cum_alert"));
                            }
                		}
        			}
        			if(PubFun.isEqual(cumRate, new BigDecimal(0)) && i > 0){
            			cumRate = tt.get(i-1).getCumMotality();
            		}
            		tt.get(i).setCumMotality(cumRate);
                    tt.get(i).setDailyMotality(i == 0 ? cumRate : cumRate.subtract(tt.get(i-1).getCumMotality()));
                    tt.get(i).setBak1(cumAlert);
                    tt.get(i).setBak2(i == 0 ? cumAlert : cumAlert.subtract(tt.get(i-1).getBak1()));
        		}
                tt.get(i).setId(null);
                tt.get(i).setFarmId(farmId);
                tt.get(i).setBreedName(farmStandard);
                tt.get(i).setCreatePerson(userId);
                tt.get(i).setModifyPerson(userId);
                tt.get(i).setCreateDate(new Date());
                tt.get(i).setCreateTime(new Date());
                tt.get(i).setModifyDate(new Date());
                tt.get(i).setModifyTime(new Date());
            }
            dealSave.put("ckStandard", tt);

            SBFarmStandar sSBFarmStandar = new SBFarmStandar();
            /*更新s_b_farm_standar表*/
            sSBFarmStandar.setFarmId(farmId);
            sSBFarmStandar.setBreedName(farmStandard);
            sSBFarmStandar.setFeedType("mixed");
            sSBFarmStandar.setMortalityFlag(needMortality);
            if (needMortality.equals("Y")) {
            	sSBFarmStandar.setBak1(new BigDecimal(mortalitytype));
            }
            sSBFarmStandar.setDailyMotality("N");
            sSBFarmStandar.setCreatePerson(userId);
            sSBFarmStandar.setCreateDate(new Date());
            sSBFarmStandar.setCreateTime(new Date());
            sSBFarmStandar.setModifyPerson(userId);
            sSBFarmStandar.setModifyDate(new Date());
            sSBFarmStandar.setModifyTime(new Date());
            dealSave.put("faStandard", sSBFarmStandar);
            
            String delSQL1 = "DELETE FROM s_b_chicken_standar WHERE farm_id = " + farmId;
            dealSave.put("delSql1", delSQL1);
            String delSQL2 = "DELETE FROM s_b_farm_standar WHERE farm_id = " + farmId;
            dealSave.put("delSql2", delSQL2);
            
            bStandardReqManager.dealStandardSave_v2(dealSave);
            
            dealRes = Constants.RESULT_SUCCESS;
            resJson.put("Result", "Succ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("======== Now end FarmStandardReqController.SettingSave");
    }
}

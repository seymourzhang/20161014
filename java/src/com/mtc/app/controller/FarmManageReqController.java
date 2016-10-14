package com.mtc.app.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.apache.tools.ant.types.Commandline;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.biz.BreedBatchReqManager;
import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.MySQLSPService;
import com.mtc.app.service.SBBreedDetailService;
import com.mtc.app.service.SBFarmBreedService;
import com.mtc.app.service.SBFarmSettleService;
import com.mtc.app.service.SBHouseBreedService;
import com.mtc.app.service.SDUserOperationService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;
import com.mtc.entity.app.SBBreedDetail;
import com.mtc.entity.app.SBFarmBreed;
import com.mtc.entity.app.SBFarmSettle;
import com.mtc.entity.app.SBHouseBreed;
import com.mtc.mapper.app.SBBreedDetailMapperCustom;
import org.springframework.web.servlet.View;

/**
 * Created by Ants on 2016/6/29.
 */
@Controller
@RequestMapping("/farmManage")
public class FarmManageReqController {
     private static Logger mLogger = Logger.getLogger(FarmStandardReqController.class);

    @Autowired
    private BaseQueryService tBaseQueryService;

    @Autowired
    private BreedBatchReqManager tBreedBatchReqManager;

    @Autowired
    private SBFarmBreedService tSBFarmBreedService;

    @Autowired
    private SBHouseBreedService tSBHouseBreedService;

    @Autowired
    private SBBreedDetailMapperCustom tSBBreedDetailMapperCustom;

    @Autowired
    private SBBreedDetailService tSBBreedDetailService;
    
    @Autowired
	private SDUserOperationService sSDUserOperationService;
    
    @Autowired
	private SBFarmSettleService tSBFarmSettleService;

    @Autowired
    private SDUserOperationService uSDUserOperationService;
    
    @Autowired
	private MySQLSPService tMySQLSPService;

    @RequestMapping("/placeChickDeal")
    public void placeChickDeal(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("============now start FarmManageReqController.placeChickDeal");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
        	String tResult = "Success";
        	String errorMsg = "";
        	
            String params = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(params);
            JSONObject paramContents = op.getJSONObject("params");
            JSONObject batchInfo = paramContents.optJSONObject("batchInfo");
            JSONArray houseInfoArray = paramContents.optJSONArray("houseInfo");
            
            int userId = op.optInt("id_spa");
            int FarmId = batchInfo.optInt("farmId");
            int farmBreedId = batchInfo.optInt("farmBreedId");
            String batchCode = batchInfo.optString("batchCode");
            String batchDate = batchInfo.optString("batchDate");
            int plansBreedDays = batchInfo.optInt("planBreedDays");
            int placeNumSum = batchInfo.optInt("placeNumSum");
            String planMarket = batchInfo.optString("planMarketDate");
            String chickVendor = batchInfo.optString("chickVendor");
            BigDecimal chickPrice = new BigDecimal(batchInfo.optDouble("chickPrice"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date planMarketDate = new Date();
            Date batchDateFormat = new Date();
            try {
                planMarketDate = sdf.parse(planMarket);
                batchDateFormat = sdf.parse(batchDate);
            }catch(ParseException pe) {
            	mLogger.info("planMarket="+planMarket);
            	mLogger.info("batchDate="+batchDate);
            	tResult = "Fail";
            	errorMsg = "日期格式有误，请联系管理员！";
            }
            SBFarmBreed ySBFarmBreed = new SBFarmBreed();
            
            if(!tResult.equals("Fail")){
            	Date date = new Date();
                int houseBreedId = 0;
                HashMap<String, Object> mParas = new HashMap<>();
                List<SBHouseBreed> lSBHouseBreed = new ArrayList<>();
                List<SBHouseBreed> lSBHouseBreedUp = new ArrayList<>();
                List<SBBreedDetail> lSBBreedDetailUp = new ArrayList<>();
                //  farmBreedId 是 0 代表 批次新增
                if (farmBreedId == 0) {
                    mParas.put("farmBreedFlag", true);

                    //操作记录
                    uSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PLACE_CHICK, SDUserOperationService.OPERATION_ADD, userId);

                    String SQLCheck1 = "SELECT count(1) FROM s_b_farm_breed WHERE farm_id = " + FarmId + " AND batch_status = '01'";
                    Integer checkInfo1 = tBaseQueryService.selectIntergerByAny(SQLCheck1);
                    if (checkInfo1 > 0) {
                    	tResult = "Fail";
                    	errorMsg = "该农场有批次尚未结算，不允许新建入雏批次。";
                    }else {
                    	String SQLCheck2 = "SELECT count(1) FROM s_b_farm_breed WHERE farm_id = " + FarmId + " AND date(market_date) >= '" + batchDate + "'";
                        Integer checkInfo2 = tBaseQueryService.selectIntergerByAny(SQLCheck2);
                        if(checkInfo2 > 0){
	                    	tResult = "Fail";
	                    	errorMsg = "最早入雏日期大于上批次的预计出栏日期。";
                    	}else{
                    		// 生成农场养殖表  
                            SBFarmBreed tSBFarmBreed = new SBFarmBreed();
                            tSBFarmBreed.setFarmId(FarmId);
                            tSBFarmBreed.setBatchCode(batchCode);
                            tSBFarmBreed.setBatchStatus("01");
                            tSBFarmBreed.setPlaceDate(batchDateFormat);
                            tSBFarmBreed.setPlaceNum(placeNumSum);
                            tSBFarmBreed.setBreedDays(plansBreedDays);
                            tSBFarmBreed.setMarketDate(planMarketDate);
                            tSBFarmBreed.setDocVendors(chickVendor);
                            tSBFarmBreed.setChickPrice(chickPrice);
                            tSBFarmBreed.setSettleNum(0);
                            tSBFarmBreed.setSettleWeight(new BigDecimal(0));
                            tSBFarmBreed.setSettleMoney(new BigDecimal(0));
                            tSBFarmBreed.setSettlePrice(new BigDecimal(0));
                            tSBFarmBreed.setModifyDate(date);
                            tSBFarmBreed.setModifyPerson(userId);
                            tSBFarmBreed.setModifyTime(date);
                            tSBFarmBreed.setCreateDate(date);
                            tSBFarmBreed.setCreatePerson(userId);
                            tSBFarmBreed.setCreateTime(date);
                            mParas.put("SBFarmBreed", tSBFarmBreed);
                            // 生成栋舍养殖表和养殖详情表
                            for (int i = 0; i < houseInfoArray.length(); ++i) {
                                JSONObject hi = (JSONObject) houseInfoArray.get(i);
                                int houseId = hi.optInt("houseId");
                                int placeNum = hi.optInt("placeNum");
                                Date placeDate = new Date();
                                Date tempDate = new Date();
                                try {
                                    placeDate = sdf.parse(hi.optString("placeDate"));
                                    tempDate = placeDate;
                                } catch (ParseException pe) {
                                	tResult = "Fail";
        	                    	errorMsg = "日期格式有误，请联系管理员！";
        	                    	break;
                                }
                                SBHouseBreed tSBHouseBreed = new SBHouseBreed();
                                tSBHouseBreed.setFarmId(FarmId);
                                tSBHouseBreed.setHouseId(houseId);
                                tSBHouseBreed.setPlaceNum(placeNum);
                                tSBHouseBreed.setPlaceDate(placeDate);
                                tSBHouseBreed.setBatchStatus("01");
//                                tSBHouseBreed.setMarketDate(planMarketDate);
                                tSBHouseBreed.setMoveoutSumweight(new BigDecimal(0));
                                tSBHouseBreed.setMarketFeedWeight(new BigDecimal(0));
                                tSBHouseBreed.setCreatePerson(userId);
                                tSBHouseBreed.setCreateDate(new Date());
                                tSBHouseBreed.setCreateTime(new Date());
                                tSBHouseBreed.setModifyPerson(userId);
                                tSBHouseBreed.setModifyDate(new Date());
                                tSBHouseBreed.setModifyTime(new Date());
                                lSBHouseBreed.add(tSBHouseBreed);
                            }
                            if(!tResult.equals("Fail")){
                            	mParas.put("planDayNum", plansBreedDays);
                                mParas.put("SBHouseBreedList", lSBHouseBreed);
                                List<SBHouseBreed> resHouseBreedList = tBreedBatchReqManager.dealSave(mParas);
                                
                                for (int i = 0; i < resHouseBreedList.size(); ++i) {
	                                HashMap tHashMap = new HashMap();
	                                tHashMap.put("in_buzType", "0");
	                                tHashMap.put("in_houseBreedId", resHouseBreedList.get(i).getId());
	                                tMySQLSPService.exec_s_p_createTargetMonitor(tHashMap);
                                }
                            }
                    	}
                    }
                }else{
                	//  farmBreedId 不是 0 代表 批次修改（有可能包含栋舍新入雏以及入雏修改）
                    String sqlCheck1 = "SELECT count(1) FROM s_b_farm_breed WHERE id = " + farmBreedId + "";
                    Integer checkInfo1 = tBaseQueryService.selectIntergerByAny(sqlCheck1);
                    //操作记录--修改
                    uSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PLACE_CHICK, SDUserOperationService.OPERATION_UPDATE, userId);

                    if (checkInfo1 == 0) {
                    	tResult = "Fail";
                    	errorMsg = "该入雏批次在数据库中不存在，请勿修改。";
                   }else{
                        SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
                        tSBFarmBreed.setPlaceNum(placeNumSum);
                        tSBFarmBreed.setDocVendors(chickVendor);
                        tSBFarmBreed.setChickPrice(chickPrice);
                        tSBFarmBreed.setModifyPerson(userId);
                        tSBFarmBreed.setModifyDate(new Date());
                        tSBFarmBreed.setModifyTime(new Date());
                        mParas.put("SBFarmBreed", tSBFarmBreed);
                        
                        for (int i = 0; i < houseInfoArray.length(); ++i) {
                            JSONObject hi = (JSONObject) houseInfoArray.get(i);
                            int houseId = hi.optInt("houseId");
                            int placeNum = hi.optInt("placeNum");
                            String houseName = hi.optString("houseName");
                            String placeDateStr = hi.optString("placeDate");
                            Date placeDate = new Date();
                            Date tempDate = new Date();
                            try {
                                placeDate = sdf.parse(placeDateStr);
                                tempDate = placeDate;
                            } catch (ParseException pe) {
                            	tResult = "Fail";
                            	errorMsg = "日期格式有误，请联系管理员！";
                            	break;
                            }
                            SBHouseBreed sSBHouseBreed = tSBHouseBreedService.selectByFarmBreedId(farmBreedId, houseId);
                            // 已经入雏的栋舍做修改操作
                            if (sSBHouseBreed != null) {
                                if (!sdf.format(sSBHouseBreed.getPlaceDate()).equals(placeDateStr)) {
                                	tResult = "Fail";
                                	errorMsg = houseName + "栋已经入雏，入雏日期不允许修改。";
                                	break;
                                }else{
                                    Integer oldPlaceNum = sSBHouseBreed.getPlaceNum();
                                    houseBreedId = sSBHouseBreed.getId();
                                    if ("02".equals(sSBHouseBreed.getBatchStatus())) {
                                    	// 如果该栋舍已经出栏，则不作操作
                                    	continue;
                                    }
                                    
                                    if (oldPlaceNum == placeNum) {
                                    	// 如果该栋舍入雏数量未改，则不作操作
                                    	continue;
                                    }
                                    
                                    List<SBBreedDetail> mSBBreedDetail = tSBBreedDetailMapperCustom.selectByhouseBreedId(houseBreedId);
                                    for (SBBreedDetail sbBreedDetail : mSBBreedDetail) {
                                        int dd1 = sbBreedDetail.getCurAmount() + (placeNum - oldPlaceNum);
                                        int dd2 = sbBreedDetail.getYtdAmount() + (placeNum - oldPlaceNum);
                                        sbBreedDetail.setCurAmount(dd1);
                                        sbBreedDetail.setYtdAmount(dd2);
                                        sbBreedDetail.setModifyPerson(userId);
                                        sbBreedDetail.setModifyDate(new Date());
                                        sbBreedDetail.setModifyTime(new Date());
                                        lSBBreedDetailUp.add(sbBreedDetail);
                                    }
                                    sSBHouseBreed.setPlaceNum(placeNum);
                                    sSBHouseBreed.setModifyDate(new Date());
                                    sSBHouseBreed.setModifyPerson(userId);
                                    sSBHouseBreed.setModifyTime(new Date());
                                    lSBHouseBreedUp.add(sSBHouseBreed);
                                }
                            }else{
                                // 未入雏的栋舍新增
                                if (placeDate.before(batchDateFormat)) {
                                	tResult = "Fail";
                                	errorMsg = "新入雏栋舍的入雏日期必须大于批次日期。";
                                	break;
                                }else{
                                    mParas.put("farmBreedFlag", false);
                                    SBHouseBreed tSBHouseBreed = new SBHouseBreed();
                                    tSBHouseBreed.setFarmId(FarmId);
                                    tSBHouseBreed.setHouseId(houseId);
                                    tSBHouseBreed.setPlaceNum(placeNum);
                                    tSBHouseBreed.setPlaceDate(placeDate);
                                    tSBHouseBreed.setBatchStatus("01");
                                    tSBHouseBreed.setMarketFeedWeight(new BigDecimal(0));
                                    tSBHouseBreed.setMoveoutSumweight(new BigDecimal(0));
                                    tSBHouseBreed.setCreatePerson(userId);
                                    tSBHouseBreed.setCreateDate(new Date());
                                    tSBHouseBreed.setCreateTime(new Date());
                                    tSBHouseBreed.setModifyPerson(userId);
                                    tSBHouseBreed.setModifyDate(new Date());
                                    tSBHouseBreed.setModifyTime(new Date());
                                    lSBHouseBreed.add(tSBHouseBreed);
                                }
                            }
                        }
                        
                        if(!tResult.equals("Fail")){
                        	mParas.put("SBBreedDetailList", lSBBreedDetailUp);
                            mParas.put("SBHouseBreedUp", lSBHouseBreedUp);
                            mParas.put("SBHouseBreedInsert", lSBHouseBreed);
                            mParas.put("planDayNum", plansBreedDays);
                            
                            List<SBHouseBreed> resHouseBreedList = tBreedBatchReqManager.dealUpdate(mParas);
                            
                            for (int i = 0; i < resHouseBreedList.size(); ++i) {
                                HashMap tHashMap = new HashMap();
                                tHashMap.put("in_buzType", "0");
                                tHashMap.put("in_houseBreedId", resHouseBreedList.get(i).getId());
                                tMySQLSPService.exec_s_p_createTargetMonitor(tHashMap);
                            }
                        }
                    }
                }
            }
            resJson = new JSONObject();
            resJson.put("FarmId", FarmId);
            resJson.put("BreedBatchId", ySBFarmBreed.getId());
            resJson.put("Result", tResult);
            resJson.put("ErrorMsg",errorMsg);
            dealRes = Constants.RESULT_SUCCESS;
        } catch (Exception E) {
            E.printStackTrace();
            try {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", E.getMessage());
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
        DealSuccOrFail.dealApp(request,response,dealRes,resJson);
        mLogger.info("============now end FarmManageReqController.placeChickDeal");
    }

    @RequestMapping("/placeChickQuery")
    public void placeChickQuery(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("==========Now start FarmManageReqController.placeChickQuery");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(params);
            int userId = op.optInt("id_spa");
            JSONObject paramContents = op.getJSONObject("params");
            int farmId = paramContents.optInt("farmId");
            int farmBreedId = paramContents.optInt("farmBreedId");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONObject tempJson1 = new JSONObject();
            JSONArray array = new JSONArray();
            //操作记录
            uSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_PLACE_CHICK, SDUserOperationService.OPERATION_SELECT, userId);

            if (farmBreedId != 0) {
                String SQL = "SELECT batch_code, place_date, breed_days, place_num, market_date, doc_vendors, chick_price FROM s_b_farm_breed WHERE id = " + farmBreedId + "";
                mLogger.info("@@@@@@@@@ FarmManageReqController.placeChickQuery.SQL " + SQL);
                List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQL);
                if (tDatas.size() != 0) {
                    for (HashMap<String, Object> tData : tDatas) {
                        String batchCode = tData.get("batch_code").toString();
                        String batchDate = tData.get("place_date").toString();
                        Object planBreedDays = tData.get("breed_days");
                        Object placeNumSum = tData.get("place_num");
                        String planMarketDate = tData.get("market_date").toString();
                        String chickVendor = tData.get("doc_vendors").toString();
                        Object chickPrice = tData.get("chick_price");

                        tempJson1.put("batchCode", batchCode);
                        tempJson1.put("planBreedDays", planBreedDays);
                        tempJson1.put("batchDate", sdf.format(sdf.parse(batchDate)));
                        tempJson1.put("placeNumSum", placeNumSum);
                        tempJson1.put("planMarketDate", sdf.format(sdf.parse(planMarketDate)));
                        tempJson1.put("chickVendor", chickVendor);
                        tempJson1.put("chickPrice", chickPrice);
                    }
                }else{
                    resJson.put("Result", "Fail");
                    resJson.put("ErrorMsg", "不存在该批次信息");
                    dealRes = Constants.RESULT_SUCCESS;
                }
                String SQL1 = "SELECT house_id, place_num, s_f_getHouseName(house_id) AS house_name, place_date" +
                        " FROM s_b_house_breed" +
                        " WHERE farm_breed_id = " + farmBreedId + " AND farm_id = " + farmId + "" +
                        " UNION ALL" +
                        " SELECT id AS house_id, NULL AS place_num, house_name, NULL AS place_date" +
                        " FROM s_d_house a" +
                        " WHERE freeze_status = 0 AND farm_id = " + farmId + " AND NOT exists(SELECT 1" +
                        " FROM s_b_house_breed b" +
                        " WHERE farm_breed_id = " + farmBreedId + " AND farm_id = "  + farmId + " AND b.house_id = a.id)" +
                        " ORDER BY house_id";
                mLogger.info("@@@@@@@@@ FarmManageReqController.placeChickQuery.SQL " + SQL1);
                List<HashMap<String, Object>> hDatas = tBaseQueryService.selectMapByAny(SQL1);
                if (hDatas.size() != 0) {
                    for (HashMap<String, Object> hData : hDatas) {
                        Object houseId = hData.get("house_id");
                        Object houseName = hData.get("house_name");
                        Object placeNum = hData.get("place_num");
                        Object placeDate = hData.get("place_date");

                        JSONObject tempJson = new JSONObject();
                        tempJson.put("houseId", houseId == null ? "" : houseId);
                        tempJson.put("houseName", houseName == null ? "" : houseName);
                        tempJson.put("placeNum", placeNum == null ? "" : placeNum);
                        tempJson.put("placeDate", placeDate == null ? "" : sdf.format(sdf.parse(placeDate.toString())));
                        array.put(tempJson);
                    }
                    resJson.put("batchInfo", tempJson1);
                    resJson.put("houseInfo", array);
                    resJson.put("Result", "Success");
                    dealRes = Constants.RESULT_SUCCESS;
                } else {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", "尚未入雏");
                }
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "暂无入雏信息！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("==========Now end FarmManageReqController.placeChickQuery");
    }

    @RequestMapping("/settleChickQuery")
    public void settleChickQuery(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("==========Now start FarmManageReqController.settleChickQuery");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
            String params = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(params);
            int userId = op.optInt("id_spa");
            JSONObject paramContents = op.getJSONObject("params");
            int farmId = paramContents.optInt("farmId");
            int farmBreedId = paramContents.optInt("farmBreedId");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JSONArray array = new JSONArray();
            //操作记录
            uSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTLE_CHICK, SDUserOperationService.OPERATION_SELECT, userId);

            if (farmBreedId != 0) {
                String SQL1 = "SELECT h.house_id, s_f_getHouseName(h.house_id) AS house_name,  h.id AS house_breed_id, h.batch_status, h.moveout_num," +
                        " h.market_date, h.moveout_weight, h.moveout_sumweight, h.market_feed_weight" +
                        " FROM s_b_house_breed h WHERE farm_breed_id = " + farmBreedId + "  AND farm_id = " + farmId + "" +
                        " ORDER BY house_id";
                mLogger.info("@@@@@@@@@ FarmManageReqController.settleChickQuery.SQL " + SQL1);
                List<HashMap<String, Object>> hDatas = tBaseQueryService.selectMapByAny(SQL1);
                if (hDatas.size() != 0) {
                    for (HashMap<String, Object> hData : hDatas) {
                        Object houseId = hData.get("house_id");
                        Object houseName = hData.get("house_name");
                        Object houseBreedId = hData.get("house_breed_id");
                        Object batchStatus = hData.get("batch_status");
                        Object moveoutNum = hData.get("moveout_num");
                        Object marketDate = hData.get("market_date");
                        Object moveoutWeight = hData.get("moveout_weight");
                        Object moveoutSumWeight = hData.get("moveout_sumweight");
                        Object marketFeedWeight = hData.get("market_feed_weight");

                        JSONObject tempJson = new JSONObject();
                        tempJson.put("houseId", houseId == null ? "" : houseId);
                        tempJson.put("houseName", houseName == null ? "" : houseName);
                        tempJson.put("houseBreedId", houseBreedId == null ? "" : houseBreedId);
                        tempJson.put("breedStatus", batchStatus == null ? "" : batchStatus);
                        tempJson.put("marketNum", moveoutNum == null ? "" : moveoutNum);
                        tempJson.put("marketDate", marketDate == null ? "" : sdf.format(sdf.parse(marketDate.toString())));
                        tempJson.put("marketWeight", moveoutSumWeight == null ? "" : moveoutSumWeight);
                        tempJson.put("marketAvgWeight", moveoutWeight == null ? "" : moveoutWeight);
                        array.put(tempJson);
                    }
                    resJson.put("settleHouse", array);
                    SBFarmBreed sbFarmBreed = tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
                    JSONObject tempJson = new JSONObject();
                    if (sbFarmBreed != null) {
                        tempJson.put("farmBreedId", farmBreedId);
                        tempJson.put("BHName", sbFarmBreed.getShName() == null ? "" : sbFarmBreed.getShName());
                        tempJson.put("SaleSumWeight", sbFarmBreed.getSettleWeight() == null ? 0 : sbFarmBreed.getSettleWeight());
                        tempJson.put("SaleSumMoney", sbFarmBreed.getSettleMoney() == null ? 0 : sbFarmBreed.getSettleMoney());
                        tempJson.put("SalePrice", sbFarmBreed.getSettlePrice() == null ? 0 : sbFarmBreed.getSettlePrice());
                        tempJson.put("SaleSumNum", sbFarmBreed.getSettleNum() == null ? 0 : sbFarmBreed.getSettleNum());
                        resJson.put("SettleFarm", tempJson);
                    }else {
                        tempJson.put("farmBreedId", "");
                        tempJson.put("BHName", "");
                        tempJson.put("SaleSumWeight", "");
                        tempJson.put("SaleSumMoney", "");
                        tempJson.put("SalePrice", "");
                        tempJson.put("SaleSumNum", "");
                        resJson.put("SettleFarm", tempJson);
                    }
                    resJson.put("Result", "Success");
                    resJson.put("ErrorMsg", "");
                    dealRes = Constants.RESULT_SUCCESS;
                } else {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", "尚未入雏");
                }
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "暂无入雏信息！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("==========Now end FarmManageReqController.settleChickQuery");
    }

    @RequestMapping("/settleChickDeal")
    public void settleChickDeal(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("=======Now start FarmManageReqController.settleChickDeal");
        JSONObject resJson = new JSONObject();
        String dealRes = "";
        HashMap<String,Object> tPara = new HashMap<>();
        try {
            String parameters = PubFun.getRequestPara(request);
            JSONObject op = new JSONObject(parameters);
            JSONObject contains = op.getJSONObject("params");
            int userId = op.optInt("id_spa");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //操作记录
            uSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_SETTLE_CHICK, SDUserOperationService.OPERATION_SELECT, userId);

            if ("house".equals(contains.optString("settleFlag"))) {
                int houseBreedId = contains.optInt("houseBreedId");
                int houseId = contains.optInt("houseId");
                String houseName = contains.optString("houseName");
                int marketNum = contains.optInt("marketNum");
                String marketDate = contains.optString("marketDate");
                BigDecimal marketSumWeight = new BigDecimal(contains.optDouble("marketWeight"));
                BigDecimal marketAvgWeight = new BigDecimal(contains.optDouble("marketAvgWeight"));
                if(marketNum == 0 || PubFun.isEqual(marketSumWeight, new BigDecimal(0))
                		|| PubFun.isEqual(marketAvgWeight, new BigDecimal(0))){
                	resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", houseName + "您输入的出栏数据不全，请重新输入！");
                }
                SBHouseBreed tdata = tSBHouseBreedService.selectByPrimaryKey(houseBreedId);
                if (sdf.parse(marketDate).before(tdata.getPlaceDate())) {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", houseName + "您输入的出栏日期在入雏日前之前，请重新输入！");
                } else if ("02".equals(tdata.getBatchStatus())) {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", houseName + "栋已出栏！");
                } else {
                    //校验,输入的出栏日期之后是否有日报数据：是，不允许出栏。
                    String SQL = "SELECT count(1) FROM s_b_breed_detail WHERE house_breed_id = " + houseBreedId + " AND growth_date > '" + marketDate + "' AND (cur_cd <> 0 OR cur_feed <> 0 OR cur_weight <> 0 OR num_bak1 <> 0)";
                    Integer tag = tBaseQueryService.selectIntergerByAny(SQL);
                    if (tag > 0) {
                        resJson.put("Result", "Fail");
                        dealRes = Constants.RESULT_SUCCESS;
                        resJson.put("ErrorMsg", "您输入的出栏日期之后有日报数据，不允许出栏。");
                    } else {
                        String Sql = "SELECT max(age) as age, sum(cur_feed) as sum_feed FROM s_b_breed_detail WHERE house_breed_id = " + houseBreedId + " AND growth_date <= '" + marketDate + "'";
                        List<HashMap<String,Object>> tData = tBaseQueryService.selectMapByAny(Sql);
                        int age = 0;
                        BigDecimal sumFeed = new BigDecimal(0);
                        for (HashMap<String, Object> a : tData) {
                            age = Integer.parseInt(a.get("age").toString());
                            sumFeed = new BigDecimal(a.get("sum_feed").toString());
                        }
                        SBBreedDetail breedDetail = tSBBreedDetailService.selectByPrimaryKey(houseBreedId, age);
                        int originalNum = tdata.getPlaceNum();
                        int dValue = originalNum - marketNum - (breedDetail.getAccCd()-breedDetail.getCurCd());
                        if (dValue < 0) {
                            resJson.put("Result", "Fail");
                            dealRes = Constants.RESULT_SUCCESS;
                            resJson.put("ErrorMsg", "您输入的出栏数有误（超过初始入雏加累计死淘数），请重新填写。");
                        } else {
                            SBHouseBreed sbHouseBreed = tSBHouseBreedService.selectByPrimaryKey(houseBreedId);
                            int farmBreedId = sbHouseBreed.getFarmBreedId();

                            SBFarmBreed sbFarmBreed = tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
                            BigDecimal SaleSumMoney = sbFarmBreed.getSettleMoney();
                            BigDecimal SalePrice = SaleSumMoney.divide(sbFarmBreed.getSettleWeight().add(marketSumWeight), 2, 4);
                            sbFarmBreed.setSettleNum(sbFarmBreed.getSettleNum() + marketNum);
                            sbFarmBreed.setSettleWeight(sbFarmBreed.getSettleWeight().add(marketSumWeight));
                            sbFarmBreed.setSettlePrice(SalePrice);
                            sbFarmBreed.setModifyPerson(userId);
                            sbFarmBreed.setModifyDate(new Date());
                            sbFarmBreed.setModifyTime(new Date());
                            tPara.put("SBFarmBreed", sbFarmBreed);

                            sbHouseBreed.setMoveoutNum(marketNum);
                            sbHouseBreed.setMarketDate(breedDetail.getGrowthDate());
                            sbHouseBreed.setMoveoutWeight(marketSumWeight.divide(new BigDecimal(marketNum),2,4));
                            sbHouseBreed.setMoveoutSumweight(marketSumWeight);
                            sbHouseBreed.setMarketFeedWeight(sumFeed);
                            sbHouseBreed.setBatchStatus("02");
                            sbHouseBreed.setModifyPerson(userId);
                            sbHouseBreed.setModifyDate(new Date());
                            sbHouseBreed.setModifyTime(new Date());
                            tPara.put("SBHouseBreed", sbHouseBreed);

                            breedDetail.setCullingPm(dValue);
                            breedDetail.setAccCd(dValue + (breedDetail.getAccCd()-breedDetail.getCurCd()));
                            breedDetail.setCurCd(dValue);
                            breedDetail.setCurWeight(marketAvgWeight.multiply(new BigDecimal(1000)));
                            breedDetail.setCurAmount(marketNum);
                            breedDetail.setModifyPerson(userId);
                            breedDetail.setModifyDate(new Date());
                            breedDetail.setModifyTime(new Date());
                            tPara.put("SBBreedDetail", breedDetail);
                            tBreedBatchReqManager.settleDealSave(tPara);

                            resJson.put("houseId", sbHouseBreed.getHouseId());
                            resJson.put("houseBreedId", breedDetail.getHouseBreedId());
                            resJson.put("Result", "Success");
                            resJson.put("ErrorMsg", "");
                            dealRes = Constants.RESULT_SUCCESS;
                        }
                    }
                }
            }else if ("farm".equals(contains.optString("settleFlag"))) {
                int farmBreedId = contains.optInt("farmBreedId");
                String BHName = contains.optString("BHName");
                BigDecimal SaleSumMoney = new BigDecimal(contains.optDouble("SaleSumMoney"));
                String sql = "SELECT ifnull(sum(moveout_num), 0)       AS moveout_sumnum, ifnull(sum(moveout_sumweight), 0) AS moveout_sumweight FROM s_b_house_breed WHERE batch_status = '02' AND farm_breed_id = " + farmBreedId + "";
                List<HashMap<String,Object>> tdata = tBaseQueryService.selectMapByAny(sql);
                BigDecimal moveoutSumweight = new BigDecimal(0);
                int moveoutSumnum = 0;
                for (HashMap<String, Object> ss : tdata) {
                    moveoutSumweight = new BigDecimal(ss.get("moveout_sumweight").toString());
                    moveoutSumnum = Integer.parseInt(ss.get("moveout_sumnum").toString());
                }
                SBFarmBreed sbFarmBreed = tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
                if ((moveoutSumweight != null && PubFun.isEqual(moveoutSumweight,new BigDecimal(0.00)))
                		|| moveoutSumnum == 0 ) {
                    resJson.put("Result", "Fail");
                    resJson.put("ErrorMsg", "暂无出栏信息！");
                    dealRes = Constants.RESULT_SUCCESS;
                } else {
                    sbFarmBreed.setShName(BHName);
                    sbFarmBreed.setSettleMoney(SaleSumMoney);
                    sbFarmBreed.setSettlePrice(SaleSumMoney.divide(moveoutSumweight, 2, 4));
                    sbFarmBreed.setSettleNum(moveoutSumnum);
                    sbFarmBreed.setSettleWeight(moveoutSumweight);
                    sbFarmBreed.setModifyPerson(userId);
                    sbFarmBreed.setModifyDate(new Date());
                    sbFarmBreed.setModifyTime(new Date());
                    tSBFarmBreedService.updateByPrimaryKey(sbFarmBreed);

                    resJson.put("Result", "Success");
                    resJson.put("ErrorMsg", "");
                    dealRes = Constants.RESULT_SUCCESS;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("=======Now end FarmManageReqController.settleChickDeal");
    }
    
    public static void main(String[] args)   {
		BigDecimal bg1 = null;
		BigDecimal bg2 = null;
		System.out.println(PubFun.isEqual(bg1, bg2));
		
//		// create 3 BigDecimal objects
//        BigDecimal bg1, bg2, bg3;
//
//        bg1 = new BigDecimal("235.00");
//        bg2 = new BigDecimal("235.00");
//        bg3 = new BigDecimal("235");
//
//        // create 2 boolean objects
//        Boolean b1,b2;
//
//        // assign the result of equals method to b1, b2
//        b1 = bg1.equals(bg2);
//        b2 = bg1.equals(bg3);
//
//        String str1 = bg1 + " equals " + bg2 + " is " +b1;
//        String str2 = bg1 + " equals " + bg3 + " is " +b2;
//
//	// print b1, b2 values
//        System.out.println( str1 );
//        System.out.println( str2 );
	}
    
    @RequestMapping("/settleBatchQuery")
	public void settleBatchQuery(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing FarmManageReqController.settleBatchQuery");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				//操作记录
				sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_BALANCE, SDUserOperationService.OPERATION_SELECT, id_spa);
				
				String tSQL2 = "SELECT ifnull(sum(a.cur_feed), 0) AS acc_feed, (SELECT c.batch_status FROM s_b_farm_breed c WHERE c.id = "+BreedBatchId+") AS status "
							 + " FROM s_b_breed_detail a where 1=1 "
							 + " and exists(SELECT 1 from s_b_house_breed b where a.house_breed_id = b.id and b.farm_breed_id = "+BreedBatchId+") " ;
				List<HashMap<String,Object>> list2 = tBaseQueryService.selectMapByAny(tSQL2);
				double acc_feed = 0;
				String batchStatus = "00";
				if(list2 != null && list2.size()>0){
					acc_feed = ((BigDecimal)list2.get(0).get("acc_feed")).doubleValue();
					batchStatus = (String)list2.get(0).get("status");
				}
				
				JSONObject tFeedMsg = new JSONObject();
				tFeedMsg.put("VenderName", "");// 饲料结算--厂家
				tFeedMsg.put("sys_amount", acc_feed );// 饲料结算--生产日报耗料
				
				JSONObject tFeedInfo_1 = new JSONObject();
				tFeedInfo_1.put("FeedCode", "2001");
				tFeedInfo_1.put("FeedName", "1号料");
				tFeedInfo_1.put("Weight", 0);// 1号料--重量
				tFeedInfo_1.put("Price_p", 0);// 1号料--单价
				tFeedInfo_1.put("Price_sum", 0);// 1号料--总金额
				
				JSONObject tFeedInfo_2_1 = new JSONObject();
				tFeedInfo_2_1.put("FeedCode", "2002");
				tFeedInfo_2_1.put("FeedName", "2号料(1)");
				tFeedInfo_2_1.put("Weight", 0);// 2号料--重量
				tFeedInfo_2_1.put("Price_p", 0);// 2号料--单价
				tFeedInfo_2_1.put("Price_sum", 0);// 2号料--总金额
				
				JSONObject tFeedInfo_2_2 = new JSONObject();
				tFeedInfo_2_2.put("FeedCode", "2013");
				tFeedInfo_2_2.put("FeedName", "2号料(2)");
				tFeedInfo_2_2.put("Weight", 0);// 3号料--重量
				tFeedInfo_2_2.put("Price_p", 0);// 3号料--单价
				tFeedInfo_2_2.put("Price_sum", 0);// 3号料--总金额
				
				JSONObject tFeedInfo_3 = new JSONObject();
				tFeedInfo_3.put("FeedCode", "2003");
				tFeedInfo_3.put("FeedName", "3号料");
				tFeedInfo_3.put("Weight", 0);// 3号料--重量
				tFeedInfo_3.put("Price_p", 0);// 3号料--单价
				tFeedInfo_3.put("Price_sum", 0);// 3号料--总金额

                JSONObject tFeedInfo_4 = new JSONObject();
				tFeedInfo_4.put("FeedCode", "2014");
				tFeedInfo_4.put("FeedName", "其他料");
				tFeedInfo_4.put("Weight", 0);// 其他料--重量
				tFeedInfo_4.put("Price_p", 0);// 其他料--单价
				tFeedInfo_4.put("Price_sum", 0);// 其他料--总金额
				
				JSONObject tOtherMsg = new JSONObject();
//				tOtherMsg.put("ChickenManure", 0);// 农场费用--鸡粪收入
//				tOtherMsg.put("OtherFee_IC", 0);// 农场费用--其他收入
				tOtherMsg.put("VaccineFee", 0);// 农场费用--药品疫苗
				tOtherMsg.put("ManualFee", 0);// 农场费用--人工费用
				tOtherMsg.put("FuelFee", 0);// 农场费用--燃料费用
				tOtherMsg.put("UtilityFee", 0);// 农场费用--水电费
				tOtherMsg.put("PaddingFee", 0);// 农场费用--垫料费
				tOtherMsg.put("CatcherFee", 0);// 农场费用--抓鸡费
                tOtherMsg.put("MaintainFee",0);//农场费用--维修费
                tOtherMsg.put("QuarantineFee",0);//农场费用--检疫费
                tOtherMsg.put("ServiceFee", 0);//农场费用--服务费
				tOtherMsg.put("RentFee", 0);// 农场费用--租金
                tOtherMsg.put("DeprFee", 0);//农场费用--折旧
                tOtherMsg.put("InterestFee",0);//农场费用--利息
				tOtherMsg.put("OtherFee", 0);// 农场费用--其它费用
                tOtherMsg.put("ChickenManure", 0);//农场收入--鸡粪收入

				List<SBFarmSettle> tSBFarmSettles = tSBFarmSettleService.selectSettleByFarm(BreedBatchId);
				if(tSBFarmSettles != null && tSBFarmSettles.size()>0){
					for(SBFarmSettle mSBFarmSettle:tSBFarmSettles) {
                        // 2001-1号料
                        if (mSBFarmSettle.getFeeCode().equals("2001")) {
                            tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
                            tFeedInfo_1.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
                            tFeedInfo_1.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
                            tFeedInfo_1.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 2002-2号料（1）
                        if (mSBFarmSettle.getFeeCode().equals("2002")) {
                            tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
                            tFeedInfo_2_1.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
                            tFeedInfo_2_1.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
                            tFeedInfo_2_1.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 2013-2号料（2）
                        if (mSBFarmSettle.getFeeCode().equals("2013")) {
                            tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
                            tFeedInfo_2_2.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
                            tFeedInfo_2_2.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
                            tFeedInfo_2_2.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 2003-3号料
                        if (mSBFarmSettle.getFeeCode().equals("2003")) {
                            tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
                            tFeedInfo_3.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
                            tFeedInfo_3.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
                            tFeedInfo_3.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 2003-其他料
                        if (mSBFarmSettle.getFeeCode().equals("2014")) {
                            tFeedMsg.put("VenderName", mSBFarmSettle.getCompanyName());
                            tFeedInfo_4.put("Weight", getDoubleValue(mSBFarmSettle.getWeight()));
                            tFeedInfo_4.put("Price_p", getDoubleValue(mSBFarmSettle.getPricePu()));
                            tFeedInfo_4.put("Price_sum", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        
                        // 3001-药品疫苗
                        if (mSBFarmSettle.getFeeCode().equals("3001")) {
                            tOtherMsg.put("VaccineFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3008-抓鸡费
                        if (mSBFarmSettle.getFeeCode().equals("3008")) {
                            tOtherMsg.put("CatcherFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3007-垫料费
                        if (mSBFarmSettle.getFeeCode().equals("3007")) {
                            tOtherMsg.put("PaddingFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3003-人工费
                        if (mSBFarmSettle.getFeeCode().equals("3003")) {
                            tOtherMsg.put("ManualFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3002-燃料费
                        if (mSBFarmSettle.getFeeCode().equals("3002")) {
                            tOtherMsg.put("FuelFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3006-水电费
                        if (mSBFarmSettle.getFeeCode().equals("3006")) {
                            tOtherMsg.put("UtilityFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 30013-维修费
                        if (mSBFarmSettle.getFeeCode().equals("3013")) {
                            tOtherMsg.put("MaintainFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3012-检疫费
                        if (mSBFarmSettle.getFeeCode().equals("3012")) {
                            tOtherMsg.put("QuarantineFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3014-服务费
                        if (mSBFarmSettle.getFeeCode().equals("3014")) {
                            tOtherMsg.put("ServiceFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3011-租金
                        if (mSBFarmSettle.getFeeCode().equals("3011")) {
                            tOtherMsg.put("RentFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3005-折旧费
                        if (mSBFarmSettle.getFeeCode().equals("3005")) {
                            tOtherMsg.put("DeprFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3010-利息
                        if (mSBFarmSettle.getFeeCode().equals("3010")) {
                            tOtherMsg.put("InterestFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        //4002-鸡粪收入
                        if (mSBFarmSettle.getFeeCode().equals("4002")) {
                            tOtherMsg.put("ChickenManure", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                        // 3004-杂费
                        if (mSBFarmSettle.getFeeCode().equals("3004")) {
                            tOtherMsg.put("OtherFee", getDoubleValue(mSBFarmSettle.getMoneySum()));
                        }
                    }
				}
				
				JSONArray tJSONArray = new JSONArray();
				tJSONArray.put(tFeedInfo_1);
				tJSONArray.put(tFeedInfo_2_1);
				tJSONArray.put(tFeedInfo_2_2);
				tJSONArray.put(tFeedInfo_3);
                tJSONArray.put(tFeedInfo_4);
				tFeedMsg.put("FeedInfo", tJSONArray);
				resJson.put("FeedMsg", tFeedMsg);
				resJson.put("OtherMsg", tOtherMsg);
                resJson.put("OtherFeeInputType", "02");
				resJson.put("BatchStatus", batchStatus);
				resJson.put("Result", "Success");
				
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
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
			mLogger.info("=====Now end executing FarmManageReqController.settleBatchQuery");
	}
	
	@RequestMapping("/settleBatchSave")
	public void settleBatchSave(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing FarmManageReqController.settleBatchSave");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
                int  id_spa = jsonObject.getInt("id_spa");
				JSONObject tJSONObject = jsonObject.getJSONObject("params");
				int BreedBatchId = tJSONObject.optInt("BreedBatchId");
				int FarmId = tJSONObject.optInt("FarmId");
                JSONObject FeedMsg = tJSONObject.getJSONObject("FeedMsg");
                    String VenderName = FeedMsg.optString("VenderName");
                    JSONArray FeedInfo = FeedMsg.getJSONArray("FeedInfo");
                    BigDecimal sys_amount = new BigDecimal(FeedMsg.optInt("sys_amount"));
                String OtherFeeInputType = tJSONObject.optString("OtherFeeInputType");
                JSONObject OtherMsg = tJSONObject.getJSONObject("OtherMsg");
                    /*BigDecimal VaccineFee = new BigDecimal(OtherMsg.optDouble("VaccineFee"));       //药品疫苗费
                    BigDecimal CatcherFee = new BigDecimal(OtherMsg.optDouble("CatcherFee"));       //抓鸡费
                    BigDecimal PaddingFee = new BigDecimal(OtherMsg.optDouble("PaddingFee"));       //垫料费
                    BigDecimal ManualFee = new BigDecimal(OtherMsg.optDouble("ManualFee"));         //人工费
                    BigDecimal FuelFee = new BigDecimal(OtherMsg.optDouble("FuelFee"));             //燃料费
                    BigDecimal UtilityFee = new BigDecimal(OtherMsg.optDouble("UtilityFee"));       //水电费
                    BigDecimal MaintainFee = new BigDecimal(OtherMsg.optDouble("MaintainFee"));     //维修费
                    BigDecimal QuarantineFee = new BigDecimal(OtherMsg.optDouble("QuarantineFee")); //检疫费
                    BigDecimal RentFee = new BigDecimal(OtherMsg.optDouble("RentFee"));             //租金
                    BigDecimal InterestFee = new BigDecimal(OtherMsg.optDouble("InterestFee"));     //利息
                    BigDecimal OtherFee = new BigDecimal(OtherMsg.optDouble("OtherFee"));           //其他费用*/
				Date curDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sSDUserOperationService.insert(SDUserOperationService.MENU_FARMMANAGEMENT_BALANCE, SDUserOperationService.OPERATION_UPDATE, id_spa);

                SBFarmBreed sbFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
                if (sbFarmBreed == null) {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", "暂无入雏信息！");
                }else if ("02".equals(sbFarmBreed.getBatchStatus())) {
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", "该农场批次已经结算确认，不允许修改。");
                }else {
                    List<SBFarmSettle> tList = new ArrayList<SBFarmSettle>();
//					JSONObject  ChickMsgJson = tJSONObject.getJSONObject("ChickMsg");
                    // 鸡苗结算
                    SBFarmSettle tChickSBFarmSettle = new SBFarmSettle();
                    tChickSBFarmSettle.setFarmId(FarmId);
                    tChickSBFarmSettle.setFarmBreedId(BreedBatchId);
                    tChickSBFarmSettle.setFeeType("E");
                    tChickSBFarmSettle.setFeeCode("1001");
                    tChickSBFarmSettle.setFeeName("鸡苗结算");
                    tChickSBFarmSettle.setCompanyCode("");
                    tChickSBFarmSettle.setCompanyName(sbFarmBreed.getDocVendors());
                    tChickSBFarmSettle.setPricePu(sbFarmBreed.getChickPrice());
                    tChickSBFarmSettle.setQuentity(sbFarmBreed.getPlaceNum());
                    tChickSBFarmSettle.setMoneySum(sbFarmBreed.getChickPrice().multiply(new BigDecimal(sbFarmBreed.getPlaceNum())));
                    tChickSBFarmSettle.setCreatePerson(id_spa);
                    tChickSBFarmSettle.setCreateDate(curDate);
                    tChickSBFarmSettle.setCreateTime(curDate);
                    tChickSBFarmSettle.setModifyPerson(id_spa);
                    tChickSBFarmSettle.setModifyDate(curDate);
                    tChickSBFarmSettle.setModifyTime(curDate);
                    tList.add(tChickSBFarmSettle);
                    // 毛鸡结算
                    /*String SQL = "SELECT ifnull((moveout_num * moveout_weight),0) AS moveout_weight, ifnull(moveout_num,0) as moveout_num FROM s_b_house_breed WHERE farm_breed_id = " + BreedBatchId + "";
                    List<HashMap<String, Object>> tdata = tBaseQueryService.selectMapByAny(SQL);
                    int moveoutNum = 0;
                    BigDecimal moveoutWeight = new BigDecimal(0);
                    BigDecimal totalWeight = new BigDecimal(0);
                    int totalNum = 0;
                    for (HashMap<String, Object> aa : tdata) {
                        moveoutNum = Integer.parseInt(aa.get("moveout_num").toString());
                        moveoutWeight = new BigDecimal(aa.get("moveout_weight").toString());
                        totalWeight = totalWeight.add(moveoutWeight);
                        totalNum = totalNum + moveoutNum;
                    }*/
                    BigDecimal sumChickenWeight = new BigDecimal(0) ;
                    if (sbFarmBreed.getSettleNum() != 0 && !sbFarmBreed.getSettleWeight().equals(new BigDecimal(0))) {
                        SBFarmSettle tOutputMsgJSON = new SBFarmSettle();
                        tOutputMsgJSON.setFarmId(FarmId);
                        tOutputMsgJSON.setFarmBreedId(BreedBatchId);
                        tOutputMsgJSON.setFeeType("I");
                        tOutputMsgJSON.setFeeCode("4001");
                        tOutputMsgJSON.setFeeName("毛鸡结算");
                        tOutputMsgJSON.setCompanyCode("");
                        tOutputMsgJSON.setCompanyName(sbFarmBreed.getShName());
                        tOutputMsgJSON.setPricePu(sbFarmBreed.getSettlePrice());
                        tOutputMsgJSON.setQuentity(sbFarmBreed.getSettleNum());
                        tOutputMsgJSON.setWeight(sbFarmBreed.getSettleWeight());
                        tOutputMsgJSON.setMoneySum(sbFarmBreed.getSettleMoney());
                        tOutputMsgJSON.setCreatePerson(id_spa);
                        tOutputMsgJSON.setCreateDate(curDate);
                        tOutputMsgJSON.setCreateTime(curDate);
                        tOutputMsgJSON.setModifyPerson(id_spa);
                        tOutputMsgJSON.setModifyDate(curDate);
                        tOutputMsgJSON.setModifyTime(curDate);
                        tList.add(tOutputMsgJSON);
                        
                        sumChickenWeight = sbFarmBreed.getSettleWeight() ;
                    }
                    
                    BigDecimal sumFeedInfoWeight = new BigDecimal(0) ;
                    // 饲料结算
                    for (int i = 0; i < FeedInfo.length(); i++) {
                        JSONObject tObject = (JSONObject) FeedInfo.get(i);
                        SBFarmSettle tFeedSBFarmSettle = new SBFarmSettle();
                        tFeedSBFarmSettle.setFarmId(FarmId);
                        tFeedSBFarmSettle.setFarmBreedId(BreedBatchId);
                        tFeedSBFarmSettle.setFeeType("E");
                        tFeedSBFarmSettle.setCompanyCode("");
                        tFeedSBFarmSettle.setCompanyName(FeedMsg.get("VenderName").toString());
                        tFeedSBFarmSettle.setCreatePerson(id_spa);
                        tFeedSBFarmSettle.setCreateDate(curDate);
                        tFeedSBFarmSettle.setCreateTime(curDate);
                        tFeedSBFarmSettle.setModifyPerson(id_spa);
                        tFeedSBFarmSettle.setModifyDate(curDate);
                        tFeedSBFarmSettle.setModifyTime(curDate);
                        tFeedSBFarmSettle.setFeeCode(tObject.getString("FeedCode"));
                        tFeedSBFarmSettle.setFeeName(tObject.getString("FeedName"));
                        tFeedSBFarmSettle.setPricePu(new BigDecimal(tObject.optString("Price_p")));
                        tFeedSBFarmSettle.setWeight(new BigDecimal(tObject.optString("Weight")));
                        tFeedSBFarmSettle.setMoneySum(new BigDecimal(tObject.optString("Price_sum")));
                        tList.add(tFeedSBFarmSettle);
                        
                        sumFeedInfoWeight = sumFeedInfoWeight.add(new BigDecimal(tObject.optString("Weight")));
                    }
                    
                    if (sumChickenWeight.compareTo(new BigDecimal(0)) != 0
                    		&& sumFeedInfoWeight.compareTo(new BigDecimal(0)) != 0
                    		&& (sumFeedInfoWeight.divide(sumChickenWeight,1,4).compareTo(new BigDecimal(2.5)) > 0 
    	                    		|| sumFeedInfoWeight.divide(sumChickenWeight,1,4).compareTo(new BigDecimal(1.5)) < 0 )
	                    	){
                        resJson.put("Result", "Fail");
                        dealRes = Constants.RESULT_SUCCESS;
                        resJson.put("ErrorMsg", "请确保饲料录入总重量与出栏毛鸡总重量比率在1.5与2.5之间！");
                    }else{
                    	// 农场费用登记
                        if (OtherMsg.optString("VaccineFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3001");
                            tOtherMsgSettle.setFeeName("药品疫苗");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("VaccineFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("CatcherFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3008");
                            tOtherMsgSettle.setFeeName("抓鸡费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("CatcherFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("PaddingFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3007");
                            tOtherMsgSettle.setFeeName("垫料费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("PaddingFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("ChickenManure") != null) {
                            SBFarmSettle tOutputMsgJSON = new SBFarmSettle();
                            tOutputMsgJSON.setFarmId(FarmId);
                            tOutputMsgJSON.setFarmBreedId(BreedBatchId);
                            tOutputMsgJSON.setFeeType("I");
                            tOutputMsgJSON.setFeeCode("4002");
                            tOutputMsgJSON.setFeeName("鸡粪收入");
                            tOutputMsgJSON.setCompanyCode("");
                            tOutputMsgJSON.setMoneySum(new BigDecimal(OtherMsg.optString("ChickenManure")));
                            tOutputMsgJSON.setCreatePerson(id_spa);
                            tOutputMsgJSON.setCreateDate(curDate);
                            tOutputMsgJSON.setCreateTime(curDate);
                            tOutputMsgJSON.setModifyPerson(id_spa);
                            tOutputMsgJSON.setModifyDate(curDate);
                            tOutputMsgJSON.setModifyTime(curDate);
                            tList.add(tOutputMsgJSON);
                        }
                        if (OtherMsg.optString("ManualFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3003");
                            tOtherMsgSettle.setFeeName("人工费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("ManualFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("FuelFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3002");
                            tOtherMsgSettle.setFeeName("燃料费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("FuelFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("UtilityFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3006");
                            tOtherMsgSettle.setFeeName("水电费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("UtilityFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("MaintainFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3013");
                            tOtherMsgSettle.setFeeName("维修费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("MaintainFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("QuarantineFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3012");
                            tOtherMsgSettle.setFeeName("检疫费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("QuarantineFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("ServiceFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3014");
                            tOtherMsgSettle.setFeeName("服务费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("ServiceFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("RentFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3011");
                            tOtherMsgSettle.setFeeName("租金");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("RentFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("DeprFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3005");
                            tOtherMsgSettle.setFeeName("折旧费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("DeprFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("InterestFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3010");
                            tOtherMsgSettle.setFeeName("利息");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("InterestFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        if (OtherMsg.optString("OtherFee") != null) {
                            SBFarmSettle tOtherMsgSettle = new SBFarmSettle();
                            tOtherMsgSettle.setFarmId(FarmId);
                            tOtherMsgSettle.setFarmBreedId(BreedBatchId);
                            tOtherMsgSettle.setFeeType("E");
                            tOtherMsgSettle.setCreatePerson(id_spa);
                            tOtherMsgSettle.setCreateDate(curDate);
                            tOtherMsgSettle.setCreateTime(curDate);
                            tOtherMsgSettle.setModifyPerson(id_spa);
                            tOtherMsgSettle.setModifyDate(curDate);
                            tOtherMsgSettle.setModifyTime(curDate);
                            tOtherMsgSettle.setFeeCode("3004");
                            tOtherMsgSettle.setFeeName("杂费");
                            tOtherMsgSettle.setMoneySum(PubFun.getBigDecimalData(OtherMsg.optString("OtherFee")));
                            tList.add(tOtherMsgSettle);
                        }
                        HashMap<String,Object> tPara =  new HashMap<String, Object>();
    					tPara.put("tList", tList);
    					tBreedBatchReqManager.intterSBFarmSettle(tPara);
                        resJson.put("Result", "Success");
                        dealRes = Constants.RESULT_SUCCESS;
                    }
                }
				//** 业务处理结束 **//*
			} catch (Exception e){
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
			mLogger.info("=====Now end executing FarmManageReqController.settleBatchSave");
	}

	@RequestMapping("/settleBatchConfirm")
	public void settleBatchConfirm(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing FarmManageReqController.settleBatchConfirm");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
			try {
				String paraStr = PubFun.getRequestPara(request);
				mLogger.info("paraStr=" + paraStr);
				JSONObject jsonObject = new JSONObject(paraStr);
				mLogger.info("jsonObject=" + jsonObject.toString());
				//** 业务处理开始，查询、增加、修改、或删除 **//*
				JSONObject  tJSONObject = jsonObject.getJSONObject("params");
				int  id_spa = jsonObject.getInt("id_spa");
				int BreedBatchId = tJSONObject.getInt("BreedBatchId");
				String tSQL = "SELECT count(1) from s_b_house_breed where farm_breed_id = "+BreedBatchId+" and batch_status = '01'";
				int dd = tBaseQueryService.selectIntergerByAny(tSQL);
                if(dd != 0){
					resJson.put("Result", "Fail");
					resJson.put("ErrorMsg", "请确保所有栋舍已经出栏，再进行批次结算。");
				}else{
					SBFarmBreed tSBFarmBreed = tSBFarmBreedService.selectByPrimaryKey(BreedBatchId);
					if(tSBFarmBreed!=null){
						if(tSBFarmBreed.getBatchStatus().equals("02")){
							resJson.put("Result", "Fail");
							resJson.put("ErrorMsg", "该批次已经被结算，请勿重复操作。");
						}else if(tSBFarmBreed.getSettlePrice().compareTo(new BigDecimal(0))==0){
							resJson.put("Result", "Fail");
							resJson.put("ErrorMsg", "请在出栏确认界面输入毛鸡结算金额。");
						}else if(tSBFarmBreed.getChickPrice().compareTo(new BigDecimal(0))==0){
							resJson.put("Result", "Fail");
							resJson.put("ErrorMsg", "请在入雏确认界面输入鸡苗金额。");
						}else{
							tSBFarmBreed.setModifyPerson(id_spa);
							tSBFarmBreed.setBatchStatus("02");
							Date tdate = new Date();
							tSBFarmBreed.setSettleDate(tdate);
							HashMap<String, Object> mpate = new HashMap<String, Object>();
							mpate.put("SBFarmBreed", tSBFarmBreed);
							tBreedBatchReqManager.settleBatch(mpate);
							resJson.put("Result", "Success");
						}
					}else{
						resJson.put("Result", "Fail");
					    resJson.put("ErrorMsg", "系统中不存在该饲养批次。");
					}
				}
				dealRes = Constants.RESULT_SUCCESS;
				//** 业务处理结束 **//*
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
			mLogger.info("=====Now end executing CodeDataReqController.settleBatchConfirm");
	
	}

    @RequestMapping("/getProfitRep")
    public void getProfitRep(HttpServletRequest request, HttpServletResponse response){
        mLogger.info("======Now start FarmManageReqController.getProfitRep");
        JSONObject resJson = new JSONObject();
        String dealRes = null ;
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("saveUser.para=" + paraStr);

            JSONObject jsonObject = new JSONObject(paraStr);
            JSONObject tJSONObject = jsonObject.getJSONObject("params");

            mLogger.info("jsonObject=" + jsonObject.toString());
            //** 业务处理开始，查询、增加、修改、或删除 **//
            sSDUserOperationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

            int FarmId = tJSONObject.optInt("FarmId");
            int BreedBatchId = tJSONObject.optInt("BreedBatchId");
            JSONArray jsonArray = new JSONArray();
            JSONObject OverViewJson1 = new JSONObject();
            JSONObject OverViewJson2 = new JSONObject();
            JSONObject OverViewJson3 = new JSONObject();
            JSONObject OverViewJson4 = new JSONObject();
            JSONObject OverViewJson5 = new JSONObject();
            JSONObject OverViewJson6 = new JSONObject();
            String SQLoverview = "select id as farmBreedId,if(id=" + BreedBatchId + ",'this','last') as tFlag " +
                    " from s_b_farm_breed where farm_id = " + FarmId + "" +
                    " ORDER BY batch_code desc LIMIT 2";
            mLogger.info("@@@@@@@@@@@@FarmManageReqController.getProfitRep.SQLoverview = " + SQLoverview);
            List<HashMap<String,Object>> flag = tBaseQueryService.selectMapByAny(SQLoverview);
            if (flag.size() == 0) {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "尚未存在批次！");
            } else {
                for (HashMap<String, Object> aa : flag) {
                    String SQLdata = "SELECT" +
                            " truncate(ifnull(avg(datediff(market_date, place_date)), 0), 0)                   AS feed_days," +
                            " ifnull(sum(a.moveout_num), 0)                                                    AS market_num," +
                            " truncate(ifnull(sum(a.moveout_num) / sum(CASE WHEN a.batch_status = '02'" +
                            " THEN a.place_num ELSE 0 END), 0) * 100, 1)                                       AS market_rate," +
                            " truncate(ifnull(avg(a.moveout_weight), 0), 2)                                    AS body_weight_avg," +
                            " ifnull(sum(a.moveout_sumweight),0)                                               AS moveout_sumweight," +
                            /*" ifnull(sum(a.market_feed_weight), 0)                                             AS acc_feed," +
                            " truncate(ifnull(sum(a.market_feed_weight) / sum(a.moveout_sumweight) , 0), 1)    AS fcr," +*/
                            " ifnull((select sum(b.weight) from s_b_farm_settle b where b.farm_breed_id = " + aa.get("farmBreedId") + " and b.fee_code in ('2001','2002','2013','2003') ),0)  AS acc_feed," +
                            " truncate(ifnull((select sum(c.weight) from s_b_farm_settle c where c.farm_breed_id = " + aa.get("farmBreedId") + " and c.fee_code in ('2001','2002','2013','2003')) / sum(a.moveout_sumweight), 0), 1) AS fcr," +
                            " ifnull(sum(a.place_num),0)                                                       AS place_num" +
                            " FROM s_b_house_breed a" +
                            " WHERE farm_breed_id = " + aa.get("farmBreedId") + "";
                    mLogger.info("@@@@@@@FarmManageReqController.getProfitRep.SQLdata = " + SQLdata);
                    List<HashMap<String, Object>> data = tBaseQueryService.selectMapByAny(SQLdata);
                    for (HashMap<String, Object> cc : data) {
                        String EuropIndexValue = BreedBatchReqController.getEuropIndexValue(PubFun.getDoubleData(cc.get("moveout_sumweight").toString()), PubFun.getIntegerData(cc.get("market_num").toString()),
                                PubFun.getIntegerData(cc.get("place_num").toString()), PubFun.getDoubleData(cc.get("acc_feed").toString()), PubFun.getIntegerData(cc.get("feed_days").toString()));
                        if (flag.size() == 1) {
                            OverViewJson1.put("ItemName", "饲养天数");
                            OverViewJson1.put("value_this", ("this".equals(aa.get("tFlag"))  &&  !"0".equals(cc.get("feed_days") )) ? cc.get("feed_days") : "-");
                            OverViewJson1.put("value_last", ("last".equals(aa.get("tFlag"))  &&  !"0".equals(cc.get("feed_days") )) ? cc.get("feed_days") : "-");
                            OverViewJson2.put("ItemName", "出栏数");
                            OverViewJson2.put("value_this", ("this".equals(aa.get("tFlag"))  &&  !"0".equals(cc.get("market_num") )) ? cc.get("market_num") : "-");
                            OverViewJson2.put("value_last", ("last".equals(aa.get("tFlag"))  &&  !"0".equals(cc.get("market_num") )) ? cc.get("market_num") : "-");
                            OverViewJson3.put("ItemName", "成活率");
                            OverViewJson3.put("value_this", ("this".equals(aa.get("tFlag"))  &&  !"0.0".equals(cc.get("market_rate") )) ? cc.get("market_rate").toString() + "%" : "-");
                            OverViewJson3.put("value_last", ("last".equals(aa.get("tFlag"))  &&  !"0.0".equals(cc.get("market_rate") )) ? cc.get("market_rate").toString() + "%" : "-");
                            OverViewJson4.put("ItemName", "只均重");
                            OverViewJson4.put("value_this", ("this".equals(aa.get("tFlag"))  &&  !"0.00".equals(cc.get("body_weight_avg") )) ? cc.get("body_weight_avg").toString() : "-");
                            OverViewJson4.put("value_last", ("last".equals(aa.get("tFlag"))  &&  !"0.00".equals(cc.get("body_weight_avg") )) ? cc.get("body_weight_avg").toString() : "-");
                            OverViewJson5.put("ItemName", "料肉比");
                            OverViewJson5.put("value_this", ("this".equals(aa.get("tFlag"))  &&  !"0.0".equals(cc.get("fcr") )) ? cc.get("fcr").toString() : "-");
                            OverViewJson5.put("value_last", ("last".equals(aa.get("tFlag"))  &&  !"0.0".equals(cc.get("fcr") )) ? cc.get("fcr").toString() : "-");
                            OverViewJson6.put("ItemName", "欧指");
                            boolean aaa = "this".equals(aa.get("tFlag"));
                            OverViewJson6.put("value_this", ("this".equals(aa.get("tFlag")) && !"0".equals(EuropIndexValue)) ? EuropIndexValue.toString() : "-");
                            OverViewJson6.put("value_last", ("last".equals(aa.get("tFlag")) && !"0".equals(EuropIndexValue)) ? EuropIndexValue.toString() : "-");
                        } else if (flag.size() == 2) {
                            OverViewJson1.put("ItemName", "饲养天数");
                            OverViewJson2.put("ItemName", "出栏数");
                            OverViewJson3.put("ItemName", "成活率");
                            OverViewJson4.put("ItemName", "只均重");
                            OverViewJson5.put("ItemName", "料肉比");
                            OverViewJson6.put("ItemName", "欧指");
                            if ("this".equals(aa.get("tFlag"))) {
                                OverViewJson1.put("value_this", "0".equals(cc.get("feed_days").toString())? "-" : cc.get("feed_days"));
                                OverViewJson2.put("value_this", "0".equals(cc.get("market_num").toString())? "-" : cc.get("market_num"));
                                OverViewJson3.put("value_this", "0.0".equals(cc.get("market_rate").toString())? "-" : cc.get("market_rate").toString() + "%");
                                OverViewJson4.put("value_this", "0.00".equals(cc.get("body_weight_avg").toString())? "-" : cc.get("body_weight_avg").toString());
                                OverViewJson5.put("value_this", "0.0".equals(cc.get("fcr").toString())? "-" : cc.get("fcr").toString());
                                OverViewJson6.put("value_this", "0".equals(EuropIndexValue) ? "-" : EuropIndexValue);
                            } else if ("last".equals(aa.get("tFlag"))) {
                                OverViewJson1.put("value_last", "0".equals(cc.get("feed_days").toString())? "-" : cc.get("feed_days"));
                                OverViewJson2.put("value_last", "0".equals(cc.get("market_num").toString())? "-" : cc.get("market_num"));
                                OverViewJson3.put("value_last", "0.0".equals(cc.get("market_rate").toString())? "-" : cc.get("market_rate").toString() + "%");
                                OverViewJson4.put("value_last", "0.00".equals(cc.get("body_weight_avg").toString())? "-" : cc.get("body_weight_avg").toString());
                                OverViewJson5.put("value_last", "0.0".equals(cc.get("fcr").toString())? "-" : cc.get("fcr").toString());
                                OverViewJson6.put("value_last", "0".equals(EuropIndexValue) ? "-" : EuropIndexValue);
                            }
                        } else if (flag.size() == 0) {
                            OverViewJson1.put("ItemName", "饲养天数");
                            OverViewJson1.put("value_this", "-");
                            OverViewJson1.put("value_last", "-");
                            OverViewJson2.put("ItemName", "出栏数");
                            OverViewJson2.put("value_this", "-");
                            OverViewJson2.put("value_last", "-");
                            OverViewJson3.put("ItemName", "成活率");
                            OverViewJson3.put("value_this", "-");
                            OverViewJson3.put("value_last", "-");
                            OverViewJson4.put("ItemName", "只均重");
                            OverViewJson4.put("value_this", "-");
                            OverViewJson4.put("value_last", "-");
                            OverViewJson5.put("ItemName", "料肉比");
                            OverViewJson5.put("value_this", "-");
                            OverViewJson5.put("value_last", "-");
                            OverViewJson6.put("ItemName", "欧指");
                            OverViewJson6.put("value_this", "-");
                            OverViewJson6.put("value_last", "-");
                        }
                    }
                }
                jsonArray.put(OverViewJson1);
                jsonArray.put(OverViewJson2);
                jsonArray.put(OverViewJson3);
                jsonArray.put(OverViewJson4);
                jsonArray.put(OverViewJson5);
                jsonArray.put(OverViewJson6);
                resJson.put("OverView", jsonArray);

                String SQL = "SELECT ifnull(b.fee_code, 'null') AS fee_code, b.fee_name," +
                        " ifnull(sum(CASE WHEN a.tFlag = 'this' THEN b.quentity ELSE 0 END), 0) AS this_amount," +
                        " ifnull(sum(CASE WHEN a.tFlag = 'last' THEN b.quentity ELSE 0 END), 0) AS last_amount," +
                        " ifnull(sum(CASE WHEN a.tFlag = 'this' THEN b.weight ELSE 0 END), 0) AS this_weight," +
                        " ifnull(sum(CASE WHEN a.tFlag = 'last' THEN b.weight ELSE 0 END), 0) AS last_weight," +
                        " sum(CASE WHEN a.tFlag = 'this' THEN b.money_sum ELSE 0 END) AS this_money," +
                        " sum(CASE WHEN a.tFlag = 'last' THEN b.money_sum ELSE 0 END) AS last_money" +
                        " FROM ( SELECT" +
                        " id                                        AS farmBreedId," +
                        " if(id = " + BreedBatchId + ", 'this', 'last') AS tFlag" +
                        " FROM s_b_farm_breed WHERE farm_id = " + FarmId + " ORDER BY batch_code DESC LIMIT 2) a" +
                        " LEFT JOIN s_b_farm_settle b ON b.farm_breed_id = a.farmBreedId" +
                        " WHERE fee_code IS NOT NULL" +
                        " GROUP BY b.fee_code, b.fee_name" +
                        " ORDER BY b.fee_code";
                List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQL);
                mLogger.info("@@@@@@@@@@@@ FarmManageReqController.getProfitRep.SQL:" + SQL);
                if (tDatas != null && tDatas.size() > 1) {
                    int this_amount = 0;
                    int last_amount = 0;
                    double this_weight = 0;
                    double last_weight = 0;
                    for (HashMap<String, Object> tData : tDatas) {
                        if (tData.get("fee_code").equals("4001")) {
                            this_amount = ((BigDecimal) tData.get("this_amount")).intValue();
                            last_amount = ((BigDecimal) tData.get("last_amount")).intValue();
                            this_weight = ((BigDecimal) tData.get("this_weight")).doubleValue();
                            last_weight = ((BigDecimal) tData.get("last_weight")).doubleValue();
                            break;
                        }
                    }
                    JSONArray tInComeArray = new JSONArray();
                    JSONArray tExpenseArray = new JSONArray();
                    JSONObject tProfitJson = new JSONObject();

                    JSONObject tChickenJson = new JSONObject();
                    JSONObject tOtherIncomeJson = new JSONObject();
                    JSONObject tChickenManure = new JSONObject();

                    JSONObject tChickJson = new JSONObject();
                    JSONObject tFeedJson = new JSONObject();
                    JSONObject tMedicineJson = new JSONObject();
                    JSONObject tManualJson = new JSONObject();
                    JSONObject tUtilityJson = new JSONObject();
                    JSONObject tPaddingJson = new JSONObject();
                    JSONObject tCatcherJson = new JSONObject();
                    JSONObject tDepreciationJson = new JSONObject();
                    JSONObject tFuelJson = new JSONObject();
                    JSONObject tOtherExpenseJson = new JSONObject();
                    JSONObject tExpenAllJson = new JSONObject();
                    JSONObject MaintainFeeJson = new JSONObject();   //维修费
                    JSONObject QuarantineFeeJson = new JSONObject(); //检疫费
                    JSONObject ServiceFeeJson = new JSONObject();   //服务费
                    JSONObject RentFeeJson = new JSONObject();     //租金
                    JSONObject DeprFeeJson = new JSONObject();         //折旧费
                    JSONObject InterestFeeJson = new JSONObject(); //利息
                    JSONObject tExpenseJson = new JSONObject();  //其他费用
                    // 饲料合计
                    double money_feed_sum_this = 0;
                    double money_feed_sum_last = 0;
                    // 支出合计
                    double money_all_e_sum_this = 0;
                    double money_all_e_sum_last = 0;
                    // 收入合计
                    double money_all_i_sum_this = 0;
                    double money_all_i_sum_last = 0;
                    for (HashMap<String, Object> tRow : tDatas) {
                        if (tRow.get("fee_code").equals("4001")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_i_sum_this += money_sum_this;
                            money_all_i_sum_last += money_sum_last;
                            tChickenJson.put("ItemName", "毛鸡");
                            tChickenJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tChickenJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tChickenJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tChickenJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tChickenJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tChickenJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("4002")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_i_sum_this += money_sum_this;
                            money_all_i_sum_last += money_sum_last;
                            tChickenManure.put("ItemName", "鸡粪");
                            tChickenManure.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tChickenManure.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tChickenManure.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tChickenManure.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tChickenManure.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tChickenManure.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("1001")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tChickJson.put("ItemName", "鸡苗");
                            tChickJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tChickJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tChickJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tChickJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tChickJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tChickJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("2001")
                                || tRow.get("fee_code").equals("2002")
                                || tRow.get("fee_code").equals("2013")
                                || tRow.get("fee_code").equals("2003")
                                || tRow.get("fee_code").equals("2014")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_feed_sum_this += money_sum_this;
                            money_feed_sum_last += money_sum_last;
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                        }
                        if (tRow.get("fee_code").equals("3001")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tMedicineJson.put("ItemName", "药费");
                            tMedicineJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tMedicineJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tMedicineJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tMedicineJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tMedicineJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tMedicineJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3002")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tFuelJson.put("ItemName", "燃料");
                            tFuelJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tFuelJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tFuelJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tFuelJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tFuelJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tFuelJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3003")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tManualJson.put("ItemName", "人工");
                            tManualJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tManualJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tManualJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tManualJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tManualJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tManualJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3006")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tUtilityJson.put("ItemName", "水电");
                            tUtilityJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tUtilityJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tUtilityJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tUtilityJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tUtilityJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tUtilityJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3007")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tPaddingJson.put("ItemName", "垫料");
                            tPaddingJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tPaddingJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tPaddingJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tPaddingJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tPaddingJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tPaddingJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3008")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tCatcherJson.put("ItemName", "抓鸡");
                            tCatcherJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tCatcherJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tCatcherJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tCatcherJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tCatcherJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tCatcherJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3013")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            MaintainFeeJson.put("ItemName", "维修");
                            MaintainFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            MaintainFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            MaintainFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            MaintainFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            MaintainFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            MaintainFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3012")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            QuarantineFeeJson.put("ItemName", "检疫");
                            QuarantineFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            QuarantineFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            QuarantineFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            QuarantineFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            QuarantineFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            QuarantineFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3014")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            ServiceFeeJson.put("ItemName", "服务");
                            ServiceFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            ServiceFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            ServiceFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            ServiceFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            ServiceFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            ServiceFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3011")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            RentFeeJson.put("ItemName", "租金");
                            RentFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            RentFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            RentFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            RentFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            RentFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            RentFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3005")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            DeprFeeJson.put("ItemName", "折旧");
                            DeprFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            DeprFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            DeprFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            DeprFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            DeprFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            DeprFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3010")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            InterestFeeJson.put("ItemName", "利息");
                            InterestFeeJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            InterestFeeJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            InterestFeeJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            InterestFeeJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            InterestFeeJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            InterestFeeJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        if (tRow.get("fee_code").equals("3004")) {
                            double money_sum_this = ((BigDecimal) tRow.get("this_money")).doubleValue();
                            double money_sum_last = ((BigDecimal) tRow.get("last_money")).doubleValue();
//                            money_other_e_sum_this += money_sum_this;
//                            money_other_e_sum_last += money_sum_last;
                            money_all_e_sum_this += money_sum_this;
                            money_all_e_sum_last += money_sum_last;
                            tExpenseJson.put("ItemName", "杂费");
                            tExpenseJson.put("PricSum_this", money_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_this / 10000, 2));
                            tExpenseJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_sum_this / this_weight, 2));
                            tExpenseJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_sum_this / this_amount, 2));
                            tExpenseJson.put("SaleChicken_last", money_sum_last == 0.0 ? "-" : PubFun.formatDoubleNum(money_sum_last / 10000, 2));
                            tExpenseJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_sum_last / last_weight, 2));
                            tExpenseJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_sum_last / last_amount, 2));
                        }
                        double profit_this = money_all_i_sum_this - money_all_e_sum_this;
                        double profit_last = money_all_i_sum_last - money_all_e_sum_last;

                        this.mLogger.info("盈利报告：本批次盈利=" + profit_this + ",数量=" + this_amount + ",重量=" + this_weight);
                        this.mLogger.info("盈利报告：上批次盈利=" + profit_last + ",数量=" + last_amount + ",重量=" + last_weight);

                        tProfitJson.put("ItemName", "盈(亏)");
                        tProfitJson.put("PricSum_this", (money_all_i_sum_this == 0.0) ? "-" : PubFun.formatDoubleNum(profit_this / 10000, 2));
                        tProfitJson.put("PricePKilo_this", PubFun.formatDoubleNum(profit_this / this_weight, 2));
                        tProfitJson.put("PricePUnit_this", PubFun.formatDoubleNum(profit_this / this_amount, 2));
                        tProfitJson.put("SaleChicken_last", (money_all_i_sum_last == 0.0) ? "-" : PubFun.formatDoubleNum(profit_last / 10000, 2));
                        tProfitJson.put("PricePKilo_last", PubFun.formatDoubleNum(profit_last / last_weight, 2));
                        tProfitJson.put("PricePUnit_last", PubFun.formatDoubleNum(profit_last / last_amount, 2));
                    }
                    tInComeArray.put(tChickenJson);// 毛鸡

					if (tChickenManure.length() != 0  &&
                            (!tChickenManure.optString("PricSum_this").equals("-")
							|| !tChickenManure.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tChickenManure);// 鸡粪
                    }
					
                    tInComeArray.put(tChickJson); // 鸡苗
                    
                    tFeedJson.put("ItemName", "饲料");
                    tFeedJson.put("PricSum_this", money_feed_sum_this == 0.0 ? "-" : PubFun.formatDoubleNum(money_feed_sum_this / 10000, 2));
                    tFeedJson.put("PricePKilo_this", PubFun.formatDoubleNum(money_feed_sum_this / this_weight, 2));
                    tFeedJson.put("PricePUnit_this", PubFun.formatDoubleNum(money_feed_sum_this / this_amount, 2));
                    tFeedJson.put("SaleChicken_last", money_feed_sum_last == 0.0  ? "-" : PubFun.formatDoubleNum(money_feed_sum_last / 10000, 2));
                    tFeedJson.put("PricePKilo_last", PubFun.formatDoubleNum(money_feed_sum_last / last_weight, 2));
                    tFeedJson.put("PricePUnit_last", PubFun.formatDoubleNum(money_feed_sum_last / last_amount, 2));
                    
                    if (tFeedJson.length() != 0 &&
                            (!tFeedJson.optString("PricSum_this").equals("-")
							|| !tFeedJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tFeedJson); // 饲料
                    }
                    
                    if (tMedicineJson.length() != 0  &&
                            (!tMedicineJson.optString("PricSum_this").equals("-")
							|| !tMedicineJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tMedicineJson);// 疫苗
                    }
                    if (tCatcherJson.length() != 0 &&
                            (!tCatcherJson.optString("PricSum_this").equals("-")
							|| !tCatcherJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tCatcherJson);// 抓鸡
                    }
                    if (tPaddingJson.length() != 0 &&
                            (!tPaddingJson.optString("PricSum_this").equals("-")
							|| !tPaddingJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tPaddingJson);// 垫料
                    }
                    if (tManualJson.length() != 0 &&
                            (!tManualJson.optString("PricSum_this").equals("-")
							|| !tManualJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tManualJson);// 人工
                    }
                    if (tFuelJson.length() != 0 &&
                            (!tFuelJson.optString("PricSum_this").equals("-")
							|| !tFuelJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tFuelJson);// 燃料
                    }
                    if (tUtilityJson.length() != 0 &&
                            (!tUtilityJson.optString("PricSum_this").equals("-")
							|| !tUtilityJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tUtilityJson);// 水电
                    }
                    if (MaintainFeeJson.length() != 0 &&
                            (!MaintainFeeJson.optString("PricSum_this").equals("-")
							|| !MaintainFeeJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(MaintainFeeJson);//维修费用
                    }
                    if (QuarantineFeeJson.length() != 0 &&
                            (!QuarantineFeeJson.optString("PricSum_this").equals("-")
							|| !QuarantineFeeJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(QuarantineFeeJson);//检疫费
                    }
                    if (ServiceFeeJson.length() != 0 &&
                            (!ServiceFeeJson.optString("PricSum_this").equals("-")
							|| !ServiceFeeJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(ServiceFeeJson);//检疫费
                    }
                    if (RentFeeJson.length() != 0 &&
                            (!RentFeeJson.optString("PricSum_this").equals("-")
							|| !RentFeeJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(RentFeeJson);//租金
                    }
                     if (DeprFeeJson.length() != 0 &&
                             (!DeprFeeJson.optString("PricSum_this").equals("-")
							|| !DeprFeeJson.optString("SaleChicken_last").equals("-"))) {
                         tInComeArray.put(DeprFeeJson);//折旧费
                     }
                    if (InterestFeeJson.length() != 0 &&
                            (!InterestFeeJson.optString("PricSum_this").equals("-")
							|| !InterestFeeJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(InterestFeeJson);//利息
                    }
                    if (tExpenseJson.length() != 0 &&
                            (!tExpenseJson.optString("PricSum_this").equals("-")
							|| !tExpenseJson.optString("SaleChicken_last").equals("-"))) {
                        tInComeArray.put(tExpenseJson);//其他费用
                    }
                    if (tProfitJson.length() != 0 &&
                            (!tProfitJson.optString("PricSum_this").equals("-")
							|| !tProfitJson.optString("SaleChicken_last").equals("-"))){
                        tInComeArray.put(tProfitJson);
                    }
                    resJson.put("feeDetail", tInComeArray);

                    resJson.put("Result", "Success");
                    dealRes = Constants.RESULT_SUCCESS;
                }else{
                    resJson.put("Result", "Fail");
                    dealRes = Constants.RESULT_SUCCESS;
                    resJson.put("ErrorMsg", "暂无信息！");
                }
            }
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
        mLogger.info("======Now end FarmManageReqController.getProfitRep");
    }

     @RequestMapping("/settleChickUpdate")
	public void settleChickUpdate(HttpServletRequest request,HttpServletResponse response){
		 mLogger.info("=====Now start executing FarmManageReqController.settleChickUpdate");
			JSONObject resJson = new JSONObject();
			String dealRes = null ;
            HashMap<String,Object> tPara = new HashMap<>();
			try {
                String paraStr = PubFun.getRequestPara(request);
                mLogger.info("paraStr=" + paraStr);
                JSONObject jsonObject = new JSONObject(paraStr);
                mLogger.info("jsonObject=" + jsonObject.toString());
                //** 业务处理开始，查询、增加、修改、或删除 **//*
                JSONObject tJSONObject = jsonObject.getJSONObject("params");
                int id_spa = jsonObject.optInt("id_spa");
                int farmBreedId = tJSONObject.optInt("farmBreedId");
                int houseId = tJSONObject.optInt("houseId");
                String houseName = tJSONObject.optString("houseName");
                int houseBreedId = tJSONObject.optInt("houseBreedId");
                int marketNum = tJSONObject.optInt("marketNum");
                BigDecimal marketWeight = new BigDecimal(tJSONObject.optDouble("marketWeight"));
                SBHouseBreed sbHouseBreed = tSBHouseBreedService.selectByPrimaryKey(houseBreedId);
                if (sbHouseBreed == null) {
                    resJson.put("Result", "Fail");
                    resJson.put("ErrorMsg", "系统中不存在" + houseName + "栋信息。");
                    dealRes = Constants.RESULT_SUCCESS;
                } else if ("01".equals(sbHouseBreed.getBatchStatus())) {
                    resJson.put("Result", "Fail");
                    resJson.put("ErrorMsg", houseName + "栋,尚未出栏不允许修改。");
                    dealRes = Constants.RESULT_SUCCESS;
                } else {
                    SBFarmBreed sbFarmBreed = tSBFarmBreedService.selectByPrimaryKey(farmBreedId);
                    sbFarmBreed.setSettleWeight(sbFarmBreed.getSettleWeight().subtract(sbHouseBreed.getMoveoutSumweight()).add(marketWeight));
                    sbFarmBreed.setSettleNum(sbFarmBreed.getSettleNum() - sbHouseBreed.getMoveoutNum() + marketNum);
                    sbFarmBreed.setSettlePrice(sbFarmBreed.getSettleMoney().divide(sbFarmBreed.getSettleWeight(), 2, 4));
                    sbFarmBreed.setModifyPerson(id_spa);
                    sbFarmBreed.setModifyDate(new Date());
                    sbFarmBreed.setModifyTime(new Date());
                    tPara.put("SBFarmBreed", sbFarmBreed);

                    sbHouseBreed.setMoveoutNum(marketNum);
                    sbHouseBreed.setMoveoutSumweight(marketWeight);
                    sbHouseBreed.setMoveoutWeight(marketWeight.divide(new BigDecimal(marketNum), 2, 4));
                    sbHouseBreed.setModifyPerson(id_spa);
                    sbHouseBreed.setModifyDate(new Date());
                    sbHouseBreed.setModifyTime(new Date());
                    tPara.put("SBHouseBreed", sbHouseBreed);
                    //获取出栏日龄
                    String sql = "SELECT max(age) FROM s_b_breed_detail WHERE house_breed_id = " + houseBreedId + "";
                    int marketAge = tBaseQueryService.selectIntergerByAny(sql);

                    SBBreedDetail sbBreedDetail = tSBBreedDetailService.selectByPrimaryKey(houseBreedId, marketAge);
                    if (sbBreedDetail == null) {
                        resJson.put("Result", "Fail");
                        resJson.put("ErrorMsg", houseName + "栋,养殖详情信息不存在，请联系管理员。");
                        dealRes = Constants.RESULT_SUCCESS;
                    } else {
                        int originalNum = sbHouseBreed.getPlaceNum();
                        int dValue = originalNum - marketNum - (sbBreedDetail.getAccCd()-sbBreedDetail.getCurCd());
                        if (dValue < 0) {
                            resJson.put("Result", "Fail");
                            resJson.put("ErrorMsg", "您修改出栏数大于之前存栏数！");
                            dealRes = Constants.RESULT_SUCCESS;
                        } else {
                            sbBreedDetail.setAccCd(dValue + (sbBreedDetail.getAccCd()-sbBreedDetail.getCurCd()));
                            sbBreedDetail.setCurCd(dValue);
                            sbBreedDetail.setCullingPm(dValue);
                            sbBreedDetail.setCurWeight(marketWeight.divide(new BigDecimal(marketNum), 2, 4).multiply(new BigDecimal(1000)));
                            sbBreedDetail.setCurAmount(marketNum);
                            sbBreedDetail.setModifyPerson(id_spa);
                            sbBreedDetail.setModifyDate(new Date());
                            sbBreedDetail.setModifyTime(new Date());

                            resJson.put("Result", "Success");
                            resJson.put("ErrorMsg", "");
                            dealRes = Constants.RESULT_SUCCESS;
                            
                            tPara.put("SBBreedDetail", sbBreedDetail);
                            tBreedBatchReqManager.settleDealUpdate(tPara);
                        }
                    }
                }
                //** 业务处理结束 **//*
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    resJson = new JSONObject();
                    resJson.put("ErrorMsg", e.getMessage());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                dealRes = Constants.RESULT_FAIL;
            }
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing CodeDataReqController.settleChickUpdate");
	}

    @RequestMapping("/getBenefitRep")
    public void getBenefitRep(HttpServletRequest request, HttpServletResponse response) {
        mLogger.info("=====Now start executing FarmManageReqController.getBenefitRep");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        HashMap<String, Object> tPara = new HashMap<>();
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("paraStr=" + paraStr);
            JSONObject jsonObject = new JSONObject(paraStr);
            mLogger.info("jsonObject=" + jsonObject.toString());
            //** 业务处理开始，查询、增加、修改、或删除 **//*
            JSONObject tJSONObject = jsonObject.getJSONObject("params");
            int FarmId = tJSONObject.optInt("FarmId");
            String ViewUnit = tJSONObject.optString("ViewUnit");
            String SQLre = "SELECT" +
                    " b.farmBreedId, " +
                    " truncate(ifnull(avg(datediff(market_date, place_date)), 0), 0)               AS feed_days," +
                    " ifnull(sum(a.moveout_num), 0)                                                AS market_num," +
                    " truncate(ifnull(sum(a.moveout_num) / sum(CASE WHEN a.batch_status = '02'" +
                    " THEN a.place_num ELSE 0 END), 0) * 100, 2)                                   AS market_rate," +
                    " truncate(ifnull(avg(a.moveout_weight), 0), 2)                                AS body_weight_avg," +
                    " ifnull(sum(a.moveout_sumweight), 0)                                          AS moveout_sumweight," +
                    " ifnull((select sum(aa.weight) from s_b_farm_settle aa where aa.farm_breed_id = b.farmBreedId and aa.fee_code in ('2001','2002','2013','2003')), 0)    AS acc_feed," +
                    " truncate(ifnull((select sum(aa.weight) from s_b_farm_settle aa where aa.farm_breed_id = b.farmBreedId and aa.fee_code in ('2001','2002','2013','2003')) / sum(a.moveout_sumweight), 0), 2) AS fcr," +
                    " ifnull(sum(a.place_num), 0)                                                  AS place_num," +
                    " b.batch_code" +
                    " FROM s_b_house_breed a" +
                    " JOIN (SELECT id AS farmBreedId, fb.batch_code FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02' ORDER BY fb.batch_code DESC LIMIT 5) b ON a.farm_breed_id = b.farmBreedId" +
                    " GROUP BY a.farm_breed_id " +
                    " ORDER BY b.batch_code DESC";
            mLogger.info("@@@@@@@@@@@ FarmManageReqController.getBenefitRep.SQLre =" + SQLre);
            List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQLre);
            JSONArray overViews = new JSONArray();
            JSONObject overView1 = new JSONObject();
            JSONObject overView2 = new JSONObject();
            JSONObject overView3 = new JSONObject();
            JSONObject overView4 = new JSONObject();
            JSONObject overView5 = new JSONObject();
            JSONObject overView6 = new JSONObject();
            JSONObject overView7 = new JSONObject();
            overView1.put("ItemName", "批次");
            overView1.put("index1", "-");
            overView1.put("index2", "-");
            overView1.put("index3", "-");
            overView1.put("index4", "-");
            overView1.put("index5", "-");

            overView2.put("ItemName", "天数");
            overView2.put("index1", "-");
            overView2.put("index2", "-");
            overView2.put("index3", "-");
            overView2.put("index4", "-");
            overView2.put("index5", "-");

            overView3.put("ItemName", "出栏");
            overView3.put("index1", "-");
            overView3.put("index2", "-");
            overView3.put("index3", "-");
            overView3.put("index4", "-");
            overView3.put("index5", "-");

            overView4.put("ItemName", "成活%");
            overView4.put("index1", "-");
            overView4.put("index2", "-");
            overView4.put("index3", "-");
            overView4.put("index4", "-");
            overView4.put("index5", "-");

            overView5.put("ItemName", "均重");
            overView5.put("index1", "-");
            overView5.put("index2", "-");
            overView5.put("index3", "-");
            overView5.put("index4", "-");
            overView5.put("index5", "-");

            overView6.put("ItemName", "料/肉");
            overView6.put("index1", "-");
            overView6.put("index2", "-");
            overView6.put("index3", "-");
            overView6.put("index4", "-");
            overView6.put("index5", "-");

            overView7.put("ItemName", "欧指");
            overView7.put("index1", "-");
            overView7.put("index2", "-");
            overView7.put("index3", "-");
            overView7.put("index4", "-");
            overView7.put("index5", "-");

            if (tDatas.size() != 0) {
                int i = 0;
                for (HashMap<String, Object> data : tDatas) {
                    ++i;
                    overView1.put("index" + i, data.get("batch_code"));
                    overView2.put("index" + i, data.get("feed_days"));
                    overView3.put("index" + i, data.get("market_num"));
                    overView4.put("index" + i, data.get("market_rate"));
                    overView5.put("index" + i, data.get("body_weight_avg"));
                    overView6.put("index" + i, data.get("fcr"));

                    String EuropIndexValue = BreedBatchReqController.getEuropIndexValue(PubFun.getDoubleData(data.get("moveout_sumweight").toString()), PubFun.getIntegerData(data.get("market_num").toString()),
                            PubFun.getIntegerData(data.get("place_num").toString()), PubFun.getDoubleData(data.get("acc_feed").toString()), PubFun.getIntegerData(data.get("feed_days").toString()));

                    overView7.put("index" + i, EuropIndexValue);
                }
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "尚不存在批次信息！");
                DealSuccOrFail.dealApp(request, response, dealRes, resJson);
                return;
            }
            overViews.put(overView1);
            overViews.put(overView2);
            overViews.put(overView3);
            overViews.put(overView4);
            overViews.put(overView5);
            overViews.put(overView6);
            overViews.put(overView7);

            String SQLfee = "SELECT" +
                    " ifnull(b.fee_code,'null') as fee_code,b.fee_name,a.farmBreedId,a.batch_code,b.weight,truncate(b.money_sum, 2) as money_sum,truncate(b.price_pu, 2) as prive_pu,b.company_name" +
                    " FROM (SELECT id AS farmBreedId, fb.batch_code FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02' ORDER BY fb.batch_code DESC LIMIT 5) a" +
                    " LEFT JOIN s_b_farm_settle b ON a.farmBreedId = b.farm_breed_id" +
                    " ORDER BY a.batch_code desc, b.fee_code";
            mLogger.info("@@@@@@@@@@@ FarmManageReqController.getBenefitRep.SQLfee =" + SQLfee);
            List<HashMap<String, Object>> fDatas = tBaseQueryService.selectMapByAny(SQLfee);

            JSONArray otherFees = new JSONArray();
            JSONObject otherFee1 = new JSONObject();
            JSONObject otherFee2 = new JSONObject();
            JSONObject otherFee3 = new JSONObject();
            JSONObject otherFee4 = new JSONObject();
            JSONObject otherFee5 = new JSONObject();
            JSONObject otherFee6 = new JSONObject();
            JSONObject otherFee7 = new JSONObject();

            otherFee1.put("ItemName", "毛鸡");
            otherFee1.put("index1", "-");
            otherFee1.put("index2", "-");
            otherFee1.put("index3", "-");
            otherFee1.put("index4", "-");
            otherFee1.put("index5", "-");

           /* otherFee2.put("ItemName", "鸡粪收入");
            otherFee2.put("index1", "-");
            otherFee2.put("index2", "-");
            otherFee2.put("index3", "-");
            otherFee2.put("index4", "-");
            otherFee2.put("index5", "-");*/

            otherFee2.put("ItemName", "鸡苗");
            otherFee2.put("index1", "-");
            otherFee2.put("index2", "-");
            otherFee2.put("index3", "-");
            otherFee2.put("index4", "-");
            otherFee2.put("index5", "-");

            otherFee3.put("ItemName", "饲料");
            otherFee3.put("index1", "-");
            otherFee3.put("index2", "-");
            otherFee3.put("index3", "-");
            otherFee3.put("index4", "-");
            otherFee3.put("index5", "-");

            otherFee4.put("ItemName", "药费");
            otherFee4.put("index1", "-");
            otherFee4.put("index2", "-");
            otherFee4.put("index3", "-");
            otherFee4.put("index4", "-");
            otherFee4.put("index5", "-");

            otherFee5.put("ItemName", "人工");
            otherFee5.put("index1", "-");
            otherFee5.put("index2", "-");
            otherFee5.put("index3", "-");
            otherFee5.put("index4", "-");
            otherFee5.put("index5", "-");

            otherFee6.put("ItemName", "其它");
            otherFee6.put("index1", "-");
            otherFee6.put("index2", "-");
            otherFee6.put("index3", "-");
            otherFee6.put("index4", "-");
            otherFee6.put("index5", "-");

            otherFee7.put("ItemName", "盈亏");
            otherFee7.put("index1", "-");
            otherFee7.put("index2", "-");
            otherFee7.put("index3", "-");
            otherFee7.put("index4", "-");
            otherFee7.put("index5", "-");

            if (fDatas.size() != 0) {
                int o = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    ++o;
                    double money_chick_sum = 0;     //毛鸡收入
                    double money_chick_sum_all = 0; //毛鸡收入和
//                    double money_manure_sum = 0;    //鸡粪收入
                    double money_feed_sum = 0;      //饲料费用
                    double money_chicken_sum = 0;   //鸡苗费用
                    double money_medicine_sum = 0;  //药品疫苗
                    double money_manual_sum = 0;    //人工费
                    double money_feed_sum_all = 0;
                    double money_other_sum = 0;     //其他费用
                    double money_other_sum_all = 0;
                    double money_in_sum_all = 0;
                    double money_out_sum_all = 0;
                    for (int j = 0; j < fDatas.size(); ++j) {
                        if (tData.get("batch_code").equals(fDatas.get(j).get("batch_code")) && tData.get("farmBreedId").equals(fDatas.get(j).get("farmBreedId"))) {
                            if ("4001".equals(fDatas.get(j).get("fee_code"))) {
                                money_chick_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                money_chick_sum_all += money_chick_sum;
                                money_in_sum_all += money_chick_sum;
                            }
                            if ("2001".equals(fDatas.get(j).get("fee_code"))
                                    || "2002".equals(fDatas.get(j).get("fee_code"))
                                    || "2013".equals(fDatas.get(j).get("fee_code"))
                                    || "2003".equals(fDatas.get(j).get("fee_code"))
                                    || "2014".equals(fDatas.get(j).get("fee_code"))) {
                                money_feed_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                money_out_sum_all += money_feed_sum;
                                money_feed_sum_all += money_feed_sum;
                            }
                            if ("1001".equals(fDatas.get(j).get("fee_code"))) {
                                money_chicken_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                money_out_sum_all += money_chicken_sum;
                            }
                            if ("3001".equals(fDatas.get(j).get("fee_code"))) {
                                money_medicine_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                money_out_sum_all += money_medicine_sum;
                            }
                            if ("3003".equals(fDatas.get(j).get("fee_code"))) {
                                money_manual_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                money_out_sum_all += money_manual_sum;
                            }
                            if ("3002".equals(fDatas.get(j).get("fee_code")) ||
                                    "4002".equals(fDatas.get(j).get("fee_code")) ||
                                    "3004".equals(fDatas.get(j).get("fee_code")) ||
                                    "3005".equals(fDatas.get(j).get("fee_code")) ||
                                    "3006".equals(fDatas.get(j).get("fee_code")) ||
                                    "3007".equals(fDatas.get(j).get("fee_code")) ||
                                    "3008".equals(fDatas.get(j).get("fee_code")) ||
                                    "3009".equals(fDatas.get(j).get("fee_code")) ||
                                    "3010".equals(fDatas.get(j).get("fee_code")) ||
                                    "3011".equals(fDatas.get(j).get("fee_code")) ||
                                    "3012".equals(fDatas.get(j).get("fee_code")) ||
                                    "3013".equals(fDatas.get(j).get("fee_code")) ||
                                    "3014".equals(fDatas.get(j).get("fee_code"))) {
                                money_other_sum = PubFun.getDoubleData(fDatas.get(j).get("money_sum").toString());
                                if("4002".equals(fDatas.get(j).get("fee_code"))){
                                	money_out_sum_all -= money_other_sum;
                                    money_other_sum_all -= money_other_sum;
                                }else{
                                	money_out_sum_all += money_other_sum;
                                    money_other_sum_all += money_other_sum;	
                                }
                            }
                        }
                    }
                    BigDecimal tempMarketNum = new BigDecimal(tData.get("market_num").toString());
                    BigDecimal tempMarketWeight = new BigDecimal(tData.get("moveout_sumweight").toString());
                    for (int j = 0; j < fDatas.size(); ++j) {
                        if (tData.get("batch_code").equals(fDatas.get(j).get("batch_code")) && tData.get("farmBreedId").equals(fDatas.get(j).get("farmBreedId"))) {
                            if ("Money".equals(ViewUnit)) {
                            	String moneyType = "";
                            	if(fDatas.get(j).get("money_sum") != null){
                            		moneyType = new BigDecimal(fDatas.get(j).get("money_sum").toString()).divide(new BigDecimal(10000), 2, 4).toString();
                            	}else{
                            		moneyType = "-";
                            	}
                                if ("4001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee1.put("index" + o, new BigDecimal(money_chick_sum_all).divide(new BigDecimal(10000), 2, 4).toString());
                                }
                                if ("1001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee2.put("index" + o, moneyType);
                                }
                                otherFee3.put("index" + o, new BigDecimal(money_feed_sum_all).divide(new BigDecimal(10000), 2, 4).toString());
                                if ("3001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee4.put("index" + o, moneyType);
                                }
                                if ("3003".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee5.put("index" + o, moneyType);
                                }
                                otherFee6.put("index" + o, new BigDecimal(money_other_sum_all).divide(new BigDecimal(10000), 2, 4).toString());
                            } else if ("quentity".equals(ViewUnit)) {
                            	String quentityType = "";
                            	if(fDatas.get(j).get("money_sum") != null){
                            		quentityType = PubFun.nh_divide(new BigDecimal(fDatas.get(j).get("money_sum").toString()), tempMarketNum,2, 4);
                            	}else{
                            		quentityType = "-";
                            	}
                                if ("4001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee1.put("index" + o,PubFun.nh_divide(new BigDecimal(money_chick_sum_all), tempMarketNum, 2, 4));
                                }
                                if ("1001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee2.put("index" + o, quentityType);
                                }
                                otherFee3.put("index" + o, PubFun.nh_divide(new BigDecimal(money_feed_sum_all), tempMarketNum, 2, 4));
                                if ("3001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee4.put("index" + o, quentityType);
                                }
                                if ("3003".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee5.put("index" + o, quentityType);
                                }
                                otherFee6.put("index" + o, PubFun.nh_divide(new BigDecimal(money_other_sum_all), tempMarketNum,2, 4));
                            } else if ("weight".equals(ViewUnit)) {
                            	String weightType = "";
                            	if(fDatas.get(j).get("money_sum") != null){
                            		weightType = PubFun.nh_divide(new BigDecimal(fDatas.get(j).get("money_sum").toString()), tempMarketWeight, 2, 4);
                            	}else{
                            		weightType = "-";
                            	}
                                if ("4001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee1.put("index" + o, PubFun.nh_divide(new BigDecimal(money_chick_sum_all), tempMarketWeight, 2, 4));
                                }
                                if ("1001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee2.put("index" + o, weightType);
                                }
                                otherFee3.put("index" + o, PubFun.nh_divide(new BigDecimal(money_feed_sum_all), tempMarketWeight, 2, 4));
                                if ("3001".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee4.put("index" + o, weightType);
                                }
                                if ("3003".equals(fDatas.get(j).get("fee_code").toString())) {
                                    otherFee5.put("index" + o, weightType);
                                }
                                otherFee6.put("index" + o, PubFun.nh_divide(new BigDecimal(money_other_sum_all), tempMarketWeight, 2, 4));
                            }
                        }
                    }
                    if ("Money".equals(ViewUnit)) {
                        otherFee7.put("index" + o, PubFun.nh_divide(new BigDecimal(money_in_sum_all - money_out_sum_all),new BigDecimal(10000),2,4));
                    } else if ("quentity".equals(ViewUnit)) {
                        if (!tData.get("market_num").equals(0)) {
                            otherFee7.put("index" + o, PubFun.nh_divide(new BigDecimal(money_in_sum_all - money_out_sum_all), tempMarketNum,2, 4));
                        }
                    } else if ("weight".equals(ViewUnit)) {
                        if (!tData.get("market_num").equals(0)) {
                            otherFee7.put("index" + o, PubFun.nh_divide(new BigDecimal(money_in_sum_all - money_out_sum_all), tempMarketWeight, 2, 4));
                        }
                    }
                }
                otherFees.put(otherFee1);
                otherFees.put(otherFee2);
                otherFees.put(otherFee3);
                otherFees.put(otherFee4);
                otherFees.put(otherFee5);
                otherFees.put(otherFee6);
                otherFees.put(otherFee7);
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "尚不存在批次信息！");
                DealSuccOrFail.dealApp(request, response, dealRes, resJson);
                return;
            }
            dealRes = Constants.RESULT_SUCCESS;
            resJson.put("otherFees", otherFees);
            resJson.put("OverView", overViews);
            resJson.put("Result", "Success");
            resJson.put("ErrorMsg", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("=====Now end executing FarmManageReqController.getBenefitRep");
    }

    @RequestMapping("/getCostsRep")
    public void getCostsRep(HttpServletRequest request, HttpServletResponse response) {
        mLogger.info("=====Now start executing FarmManageReqController.getCostsRep");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        HashMap<String, Object> tPara = new HashMap<>();
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("paraStr=" + paraStr);
            JSONObject jsonObject = new JSONObject(paraStr);
            mLogger.info("jsonObject=" + jsonObject.toString());
            //** 业务处理开始，查询、增加、修改、或删除 **//*
            JSONObject tJSONObject = jsonObject.getJSONObject("params");
            int FarmId = tJSONObject.optInt("FarmId");
            String ViewUnit = tJSONObject.optString("ViewUnit");

            JSONArray otherFees = new JSONArray();
            JSONObject otherFee1 = new JSONObject();

            otherFee1.put("ItemName", "批次");
            otherFee1.put("index1", "-");
            otherFee1.put("index2", "-");
            otherFee1.put("index3", "-");
            otherFee1.put("index4", "-");
            otherFee1.put("index5", "-");

            String SQLre = "SELECT b.batch_code,ifnull(sum(a.moveout_num), 0) as market_num,ifnull(sum(a.moveout_sumweight), 0) AS moveout_sumweight FROM s_b_house_breed a" +
                    " JOIN (SELECT id AS farmBreedId, fb.batch_code FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02' ORDER BY fb.batch_code DESC LIMIT 5) b ON a.farm_breed_id = b.farmBreedId" +
                    " GROUP BY a.farm_breed_id " +
                    " ORDER BY b.batch_code DESC";
            mLogger.info("@@@@@@@@@@@ FarmManageReqController.getBenefitRep.SQLre =" + SQLre);
            List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQLre);
            if (tDatas.size() != 0) {
                int a = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    ++a;
                    otherFee1.put("index" + a, tData.get("batch_code"));
                }
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "尚不存在批次信息！");
                DealSuccOrFail.dealApp(request, response, dealRes, resJson);
                return;
            }

            String SQLfee = "SELECT" +
                    " ifnull(b.fee_code,'null') as fee_code,b.fee_name,a.farmBreedId,a.batch_code,b.weight,ifnull(b.money_sum,0) as money_sum,b.price_pu,b.company_name" +
                    " FROM (SELECT id AS farmBreedId, fb.batch_code FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02' ORDER BY fb.batch_code DESC LIMIT 5) a" +
                    " LEFT JOIN s_b_farm_settle b ON a.farmBreedId = b.farm_breed_id" +
                    " ORDER BY a.batch_code desc, b.fee_code";
            mLogger.info("@@@@@@@@@@@ FarmManageReqController.getCostsRep.SQLfee =" + SQLfee);
            List<HashMap<String, Object>> fDatas = tBaseQueryService.selectMapByAny(SQLfee);

            JSONObject otherFee2 = new JSONObject();
            JSONObject otherFee3 = new JSONObject();
            JSONObject otherFee4 = new JSONObject();
            JSONObject otherFee5 = new JSONObject();
            JSONObject otherFee6 = new JSONObject();
            JSONObject otherFee7 = new JSONObject();
            JSONObject otherFee8 = new JSONObject();
            JSONObject otherFee9 = new JSONObject();
            JSONObject otherFee10 = new JSONObject();
            JSONObject otherFee11 = new JSONObject();
            JSONObject otherFee12 = new JSONObject();
            JSONObject otherFee13 = new JSONObject();
            JSONObject otherFee14 = new JSONObject();
            JSONObject otherFee15 = new JSONObject();
            JSONObject otherFee16 = new JSONObject();
            JSONObject otherFee17 = new JSONObject();

            otherFee2.put("ItemName", "鸡苗");
            otherFee2.put("index1", "-");
            otherFee2.put("index2", "-");
            otherFee2.put("index3", "-");
            otherFee2.put("index4", "-");
            otherFee2.put("index5", "-");

            otherFee3.put("ItemName", "饲料");
            otherFee3.put("index1", "-");
            otherFee3.put("index2", "-");
            otherFee3.put("index3", "-");
            otherFee3.put("index4", "-");
            otherFee3.put("index5", "-");

            otherFee4.put("ItemName", "药费");
            otherFee4.put("index1", "-");
            otherFee4.put("index2", "-");
            otherFee4.put("index3", "-");
            otherFee4.put("index4", "-");
            otherFee4.put("index5", "-");

            otherFee5.put("ItemName", "抓鸡");
            otherFee5.put("index1", "-");
            otherFee5.put("index2", "-");
            otherFee5.put("index3", "-");
            otherFee5.put("index4", "-");
            otherFee5.put("index5", "-");

            otherFee6.put("ItemName", "垫料");
            otherFee6.put("index1", "-");
            otherFee6.put("index2", "-");
            otherFee6.put("index3", "-");
            otherFee6.put("index4", "-");
            otherFee6.put("index5", "-");

            otherFee7.put("ItemName", "人工");
            otherFee7.put("index1", "-");
            otherFee7.put("index2", "-");
            otherFee7.put("index3", "-");
            otherFee7.put("index4", "-");
            otherFee7.put("index5", "-");

            otherFee8.put("ItemName", "燃料");
            otherFee8.put("index1", "-");
            otherFee8.put("index2", "-");
            otherFee8.put("index3", "-");
            otherFee8.put("index4", "-");
            otherFee8.put("index5", "-");

            otherFee9.put("ItemName", "水电");
            otherFee9.put("index1", "-");
            otherFee9.put("index2", "-");
            otherFee9.put("index3", "-");
            otherFee9.put("index4", "-");
            otherFee9.put("index5", "-");

            otherFee10.put("ItemName", "维修");
            otherFee10.put("index1", "-");
            otherFee10.put("index2", "-");
            otherFee10.put("index3", "-");
            otherFee10.put("index4", "-");
            otherFee10.put("index5", "-");

            otherFee11.put("ItemName", "检疫");
            otherFee11.put("index1", "-");
            otherFee11.put("index2", "-");
            otherFee11.put("index3", "-");
            otherFee11.put("index4", "-");
            otherFee11.put("index5", "-");

            otherFee12.put("ItemName", "服务费");
            otherFee12.put("index1", "-");
            otherFee12.put("index2", "-");
            otherFee12.put("index3", "-");
            otherFee12.put("index4", "-");
            otherFee12.put("index5", "-");

            otherFee13.put("ItemName", "租金");
            otherFee13.put("index1", "-");
            otherFee13.put("index2", "-");
            otherFee13.put("index3", "-");
            otherFee13.put("index4", "-");
            otherFee13.put("index5", "-");

            otherFee14.put("ItemName", "折旧费");
            otherFee14.put("index1", "-");
            otherFee14.put("index2", "-");
            otherFee14.put("index3", "-");
            otherFee14.put("index4", "-");
            otherFee14.put("index5", "-");

            otherFee15.put("ItemName", "利息");
            otherFee15.put("index1", "-");
            otherFee15.put("index2", "-");
            otherFee15.put("index3", "-");
            otherFee15.put("index4", "-");
            otherFee15.put("index5", "-");

            otherFee16.put("ItemName", "杂费");
            otherFee16.put("index1", "-");
            otherFee16.put("index2", "-");
            otherFee16.put("index3", "-");
            otherFee16.put("index4", "-");
            otherFee16.put("index5", "-");

            otherFee17.put("ItemName", "鸡粪收入");
            otherFee17.put("index1", "-");
            otherFee17.put("index2", "-");
            otherFee17.put("index3", "-");
            otherFee17.put("index4", "-");
            otherFee17.put("index5", "-");

            if (fDatas.size() != 0) {
                int b = 0;
                double money_feed_sum = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    ++b;
                    money_feed_sum = 0;
                    BigDecimal moveoutSumweight = new BigDecimal(tData.get("moveout_sumweight").toString());
                    BigDecimal marketNum = new BigDecimal(tData.get("market_num").toString());
                    for (HashMap<String, Object> fData : fDatas) {
                        if ("Money".equals(ViewUnit)) {
                            String moneyType = PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : new BigDecimal(fData.get("money_sum").toString()).divide(new BigDecimal(10000), 2, 4).toString();
                            if (tData.get("batch_code").equals(fData.get("batch_code"))) {
                                //鸡苗
                                if ("1001".equals(fData.get("fee_code"))) {
                                    otherFee2.put("index" + b, moneyType);
                                }
                                //饲料费
                                if ("2001".equals(fData.get("fee_code"))
                                        || "2002".equals(fData.get("fee_code"))
                                        || "2013".equals(fData.get("fee_code"))
                                        || "2003".equals(fData.get("fee_code"))
                                        || "2014".equals(fData.get("fee_code"))) {
                                    money_feed_sum += PubFun.getDoubleData(fData.get("money_sum").toString());
                                    otherFee3.put("index" + b, PubFun.isEqual(new BigDecimal(money_feed_sum), new BigDecimal(0)) ? "-" : new BigDecimal(money_feed_sum).divide(new BigDecimal(10000), 2, 4).toString());
                                }
                                //药费
                                if ("3001".equals(fData.get("fee_code"))) {
                                    otherFee4.put("index" + b, moneyType);
                                }
                                //抓鸡费
                                if ("3008".equals(fData.get("fee_code"))) {
                                    otherFee5.put("index" + b, moneyType);
                                }
                                //垫料费
                                if ("3007".equals(fData.get("fee_code"))) {
                                    otherFee6.put("index" + b, moneyType);
                                }
                                //人工费
                                if ("3003".equals(fData.get("fee_code"))) {
                                    otherFee7.put("index" + b, moneyType);
                                }
                                //燃料费
                                if ("3002".equals(fData.get("fee_code"))) {
                                    otherFee8.put("index" + b, moneyType);
                                }
                                //水电费
                                if ("3006".equals(fData.get("fee_code"))) {
                                    otherFee9.put("index" + b, moneyType);
                                }
                                //维修费
                                if ("3013".equals(fData.get("fee_code"))) {
                                    otherFee10.put("index" + b, moneyType);
                                }
                                //检疫费
                                if ("3012".equals(fData.get("fee_code"))) {
                                    otherFee11.put("index" + b, moneyType);
                                }
                                //服务费
                                if ("3014".equals(fData.get("fee_code"))) {
                                    otherFee12.put("index" + b, moneyType);
                                }
                                //租金
                                if ("3011".equals(fData.get("fee_code"))) {
                                    otherFee13.put("index" + b, moneyType);
                                }
                                //折旧费
                                if ("3005".equals(fData.get("fee_code"))) {
                                    otherFee14.put("index" + b, moneyType);
                                }
                                //利息
                                if ("3010".equals(fData.get("fee_code"))) {
                                    otherFee15.put("index" + b, moneyType);
                                }
                                //杂费
                                if ("3004".equals(fData.get("fee_code"))) {
                                    otherFee16.put("index" + b, moneyType);
                                }
                                //鸡粪收入
                                if ("4002".equals(fData.get("fee_code"))) {
                                    otherFee17.put("index" + b, PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : new BigDecimal(fData.get("money_sum").toString()).divide(new BigDecimal(10000), 2, 4).multiply(new BigDecimal(-1)).toString());
                                }
                            }
                        } else if ("quentity".equals(ViewUnit)) {
                            String quentityType = PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), marketNum, 2, 4);
                            if (tData.get("batch_code").equals(fData.get("batch_code"))) {
                                //鸡苗
                                if ("1001".equals(fData.get("fee_code"))) {
                                    otherFee2.put("index" + b, quentityType);
                                }
                                //饲料费
                                if ("2001".equals(fData.get("fee_code"))
                                        || "2002".equals(fData.get("fee_code"))
                                        || "2013".equals(fData.get("fee_code"))
                                        || "2003".equals(fData.get("fee_code"))
                                        || "2014".equals(fData.get("fee_code"))) {
                                    money_feed_sum += PubFun.getDoubleData(fData.get("money_sum").toString());
                                    otherFee3.put("index" + b, PubFun.isEqual(new BigDecimal(money_feed_sum), new BigDecimal(0)) ? "-" : PubFun.nh_divide(new BigDecimal(money_feed_sum), marketNum, 2, 4));
                                }
                                //药费
                                if ("3001".equals(fData.get("fee_code"))) {
                                    otherFee4.put("index" + b, quentityType);
                                }
                                //抓鸡费
                                if ("3008".equals(fData.get("fee_code"))) {
                                    otherFee5.put("index" + b, quentityType);
                                }
                                //垫料费
                                if ("3007".equals(fData.get("fee_code"))) {
                                    otherFee6.put("index" + b, quentityType);
                                }
                                //人工费
                                if ("3003".equals(fData.get("fee_code"))) {
                                    otherFee7.put("index" + b, quentityType);
                                }
                                //燃料费
                                if ("3002".equals(fData.get("fee_code"))) {
                                    otherFee8.put("index" + b, quentityType);
                                }
                                //水电费
                                if ("3006".equals(fData.get("fee_code"))) {
                                    otherFee9.put("index" + b, quentityType);
                                }
                                //维修费
                                if ("3013".equals(fData.get("fee_code"))) {
                                    otherFee10.put("index" + b, quentityType);
                                }
                                //检疫费
                                if ("3012".equals(fData.get("fee_code"))) {
                                    otherFee11.put("index" + b, quentityType);
                                }
                                //服务费
                                if ("3014".equals(fData.get("fee_code"))) {
                                    otherFee12.put("index" + b, quentityType);
                                }
                                //租金
                                if ("3011".equals(fData.get("fee_code"))) {
                                    otherFee13.put("index" + b, quentityType);
                                }
                                //折旧费
                                if ("3005".equals(fData.get("fee_code"))) {
                                    otherFee14.put("index" + b, quentityType);
                                }
                                //利息
                                if ("3010".equals(fData.get("fee_code"))) {
                                    otherFee15.put("index" + b, quentityType);
                                }
                                //杂费
                                if ("3004".equals(fData.get("fee_code"))) {
                                    otherFee16.put("index" + b, quentityType);
                                }
                                //鸡粪收入
                                if ("4002".equals(fData.get("fee_code"))) {
                                    otherFee17.put("index" + b, PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : 
                                    	("-".equals(PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), marketNum, 2, 4)) ? "-" : 
                                    		new BigDecimal(PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), marketNum, 2, 4)).multiply(new BigDecimal(-1)).toString()));
                                }
                            }
                        } else if ("weight".equals(ViewUnit)) {
                            String weightType = PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), moveoutSumweight, 2, 4);
                            if (tData.get("batch_code").equals(fData.get("batch_code"))) {
                                //鸡苗
                                if ("1001".equals(fData.get("fee_code"))) {
                                    otherFee2.put("index" + b, weightType);
                                }
                                //饲料费
                                if ("2001".equals(fData.get("fee_code"))
                                        || "2002".equals(fData.get("fee_code"))
                                        || "2013".equals(fData.get("fee_code"))
                                        || "2003".equals(fData.get("fee_code"))
                                        || "2014".equals(fData.get("fee_code"))) {
                                    money_feed_sum += PubFun.getDoubleData(fData.get("money_sum").toString());
                                    otherFee3.put("index" + b, PubFun.isEqual(new BigDecimal(money_feed_sum), new BigDecimal(0)) ? "-" : PubFun.nh_divide(new BigDecimal(money_feed_sum), moveoutSumweight, 2, 4));
                                }
                                //药费
                                if ("3001".equals(fData.get("fee_code"))) {
                                    otherFee4.put("index" + b, weightType);
                                }
                                //抓鸡费
                                if ("3008".equals(fData.get("fee_code"))) {
                                    otherFee5.put("index" + b, weightType);
                                }
                                //垫料费
                                if ("3007".equals(fData.get("fee_code"))) {
                                    otherFee6.put("index" + b, weightType);
                                }
                                //人工费
                                if ("3003".equals(fData.get("fee_code"))) {
                                    otherFee7.put("index" + b, weightType);
                                }
                                //燃料费
                                if ("3002".equals(fData.get("fee_code"))) {
                                    otherFee8.put("index" + b, weightType);
                                }
                                //水电费
                                if ("3006".equals(fData.get("fee_code"))) {
                                    otherFee9.put("index" + b, weightType);
                                }
                                //维修费
                                if ("3013".equals(fData.get("fee_code"))) {
                                    otherFee10.put("index" + b, weightType);
                                }
                                //检疫费
                                if ("3012".equals(fData.get("fee_code"))) {
                                    otherFee11.put("index" + b, weightType);
                                }
                                //服务费
                                if ("3014".equals(fData.get("fee_code"))) {
                                    otherFee12.put("index" + b, weightType);
                                }
                                //租金
                                if ("3011".equals(fData.get("fee_code"))) {
                                    otherFee13.put("index" + b, weightType);
                                }
                                //折旧费
                                if ("3005".equals(fData.get("fee_code"))) {
                                    otherFee14.put("index" + b, weightType);
                                }
                                //利息
                                if ("3010".equals(fData.get("fee_code"))) {
                                    otherFee15.put("index" + b, weightType);
                                }
                                //杂费
                                if ("3004".equals(fData.get("fee_code"))) {
                                    otherFee16.put("index" + b, weightType);
                                }
                                //鸡粪收入
                                if ("4002".equals(fData.get("fee_code"))) {
                                    otherFee17.put("index" + b, PubFun.isEqual(new BigDecimal(fData.get("money_sum").toString()), new BigDecimal(0)) ? "-" : 
                                    	("-".equals(PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), moveoutSumweight, 2, 4)) ? "-" : 
                                    		new BigDecimal(PubFun.nh_divide(new BigDecimal(fData.get("money_sum").toString()), moveoutSumweight, 2, 4)).multiply(new BigDecimal(-1)).toString()));
                                }
                            }
                        }
                    }
                }
                otherFees.put(otherFee1);
                otherFees.put(otherFee2);
                otherFees.put(otherFee3);
                otherFees.put(otherFee4);
                otherFees.put(otherFee5);
                otherFees.put(otherFee6);
                otherFees.put(otherFee7);
                otherFees.put(otherFee8);
                otherFees.put(otherFee9);
                otherFees.put(otherFee10);
                otherFees.put(otherFee11);
                otherFees.put(otherFee12);
                otherFees.put(otherFee13);
                otherFees.put(otherFee14);
                otherFees.put(otherFee15);
                otherFees.put(otherFee16);
                otherFees.put(otherFee17);
            } else {
                resJson.put("Result", "Fail");
                dealRes = Constants.RESULT_SUCCESS;
                resJson.put("ErrorMsg", "尚不存在批次信息！");
                DealSuccOrFail.dealApp(request, response, dealRes, resJson);
                return;
            }
            dealRes = Constants.RESULT_SUCCESS;
            resJson.put("otherFees", otherFees);
            resJson.put("Result", "Success");
            resJson.put("ErrorMsg", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("=====Now end executing FarmManageReqController.getCostsRep");
    }

    @RequestMapping("/getSettlePriceRep")
    public void getSettlePriceRep(HttpServletRequest request, HttpServletResponse response) {
        mLogger.info("=====Now start executing FarmManageReqController.getSettlePriceRep");
        JSONObject resJson = new JSONObject();
        String dealRes = null;
        try {
            String paraStr = PubFun.getRequestPara(request);
            mLogger.info("paraStr=" + paraStr);
            JSONObject jsonObject = new JSONObject(paraStr);
            mLogger.info("jsonObject=" + jsonObject.toString());
            //** 业务处理开始，查询、增加、修改、或删除 **//*
            JSONObject tJSONObject = jsonObject.getJSONObject("params");
            int FarmId = tJSONObject.optInt("FarmId");

            JSONObject codeFee1 = new JSONObject();
            JSONArray chickFees = new JSONArray();
            JSONObject chickFee1 = new JSONObject();
            JSONObject chickFee2 = new JSONObject();
            codeFee1.put("ItemName", "批次");
            codeFee1.put("index1", "-");
            codeFee1.put("index2", "-");
            codeFee1.put("index3", "-");
            codeFee1.put("index4", "-");
            codeFee1.put("index5", "-");

            chickFee1.put("ItemName", "雏源厂家");
            chickFee1.put("index1", "-");
            chickFee1.put("index2", "-");
            chickFee1.put("index3", "-");
            chickFee1.put("index4", "-");
            chickFee1.put("index5", "-");

            chickFee2.put("ItemName", "鸡苗价格");
            chickFee2.put("index1", "-");
            chickFee2.put("index2", "-");
            chickFee2.put("index3", "-");
            chickFee2.put("index4", "-");
            chickFee2.put("index5", "-");

            String SQLcode = "SELECT a.farm_breed_id,a.company_name as company_name,truncate(ifnull(a.price_pu, 0), 2) AS price_pu,b.batch_code" +
                    " FROM s_b_farm_settle a JOIN (SELECT" +
                    " id AS farmBreedId," +
                    " fb.batch_code" +
                    " FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02'" +
                    " ORDER BY fb.batch_code DESC LIMIT 5) b ON a.farm_breed_id = b.farmBreedId and a.fee_code = '1001' " +
                    " GROUP BY a.farm_breed_id ORDER BY b.batch_code DESC";
            mLogger.info("@@@@@@@@@@@FarmManageReqController.getSettlePriceRep.SQLother =" + SQLcode);
            List<HashMap<String, Object>> tDatas = tBaseQueryService.selectMapByAny(SQLcode);
            if (tDatas.size() != 0) {
                int i = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    ++i;
                    codeFee1.put("index" + i, tData.get("batch_code"));
                    chickFee1.put("index" + i, tData.get("company_name"));
                    chickFee2.put("index" + i, tData.get("price_pu").toString());
                }
            }
            chickFees.put(chickFee1);
            chickFees.put(chickFee2);

            JSONArray FeedPrices = new JSONArray();
            JSONObject feedPrice1 = new JSONObject();
            JSONObject feedPrice2 = new JSONObject();
            JSONObject feedPrice3 = new JSONObject();
            JSONObject feedPrice4 = new JSONObject();
            JSONObject feedPrice5 = new JSONObject();
            feedPrice1.put("ItemName", "饲料厂家");
            feedPrice1.put("index1", "-");
            feedPrice1.put("index2", "-");
            feedPrice1.put("index3", "-");
            feedPrice1.put("index4", "-");
            feedPrice1.put("index5", "-");

            feedPrice2.put("ItemName", "1号料");
            feedPrice2.put("index1", "-");
            feedPrice2.put("index2", "-");
            feedPrice2.put("index3", "-");
            feedPrice2.put("index4", "-");
            feedPrice2.put("index5", "-");

            feedPrice3.put("ItemName", "2号(1)料");
            feedPrice3.put("index1", "-");
            feedPrice3.put("index2", "-");
            feedPrice3.put("index3", "-");
            feedPrice3.put("index4", "-");
            feedPrice3.put("index5", "-");

            feedPrice4.put("ItemName", "2号(2)料");
            feedPrice4.put("index1", "-");
            feedPrice4.put("index2", "-");
            feedPrice4.put("index3", "-");
            feedPrice4.put("index4", "-");
            feedPrice4.put("index5", "-");

            feedPrice5.put("ItemName", "3号料");
            feedPrice5.put("index1", "-");
            feedPrice5.put("index2", "-");
            feedPrice5.put("index3", "-");
            feedPrice5.put("index4", "-");
            feedPrice5.put("index5", "-");

            JSONArray chickenPrices = new JSONArray();
            JSONObject chickenPrice1 = new JSONObject();
            JSONObject chickenPrice2 = new JSONObject();
            chickenPrice1.put("ItemName", "屠宰厂家");
            chickenPrice1.put("index1", "-");
            chickenPrice1.put("index2", "-");
            chickenPrice1.put("index3", "-");
            chickenPrice1.put("index4", "-");
            chickenPrice1.put("index5", "-");

            chickenPrice2.put("ItemName", "毛鸡价格");
            chickenPrice2.put("index1", "-");
            chickenPrice2.put("index2", "-");
            chickenPrice2.put("index3", "-");
            chickenPrice2.put("index4", "-");
            chickenPrice2.put("index5", "-");
            String SQLfeed = "SELECT ifnull(b.fee_code,'null') as fee_code,b.fee_name,a.farmBreedId,a.batch_code,b.weight,b.money_sum,truncate(ifnull(b.price_pu, 0), 2) as price_pu,b.company_name" +
                    " FROM (SELECT id AS farmBreedId, fb.batch_code FROM s_b_farm_breed fb WHERE fb.farm_id = " + FarmId + " AND fb.batch_status = '02' ORDER BY fb.batch_code DESC LIMIT 5) a" +
                    " LEFT JOIN s_b_farm_settle b ON a.farmBreedId = b.farm_breed_id and (b.fee_code = '1001' or b.fee_code like '2%' or b.fee_code = '4001') " +
                    " ORDER BY a.batch_code DESC,b.fee_code";
            mLogger.info("@@@@@@@@@@@FarmManageReqController.getSettlePriceRep.SQLother =" + SQLfeed);
            List<HashMap<String, Object>> fDatas = tBaseQueryService.selectMapByAny(SQLfeed);
            if (fDatas.size() != 0) {
                int f = 0;
                for (HashMap<String, Object> tData : tDatas) {
                    ++f;
                    for (HashMap<String, Object> fData : fDatas) {
                        if (tData.get("batch_code").equals(fData.get("batch_code"))) {
                        	String price_pu = fData.get("price_pu")!= null?fData.get("price_pu").toString():"";
                        	if("0.00".equals(price_pu)){
                        		price_pu = "-";
                        	}
                            if ("2001".equals(fData.get("fee_code"))) {
                                feedPrice1.put("index" + f, fData.get("company_name"));
                                feedPrice2.put("index" + f, price_pu);
                            }
                            if ("2002".equals(fData.get("fee_code"))) {
                                feedPrice3.put("index" + f, price_pu);
                            }
                            if ("2013".equals(fData.get("fee_code"))) {
                                feedPrice4.put("index" + f, price_pu);
                            }
                            if ("2003".equals(fData.get("fee_code"))) {
                                feedPrice5.put("index" + f, price_pu);
                            }
                            if ("4001".equals(fData.get("fee_code"))) {
                                chickenPrice1.put("index" + f, fData.get("company_name"));
                                chickenPrice2.put("index" + f, price_pu);
                            }
                        }
                    }
                }
                FeedPrices.put(feedPrice1);
                FeedPrices.put(feedPrice2);
                FeedPrices.put(feedPrice3);
                FeedPrices.put(feedPrice4);
                FeedPrices.put(feedPrice5);

                chickenPrices.put(chickenPrice1);
                chickenPrices.put(chickenPrice2);
            }

            dealRes = Constants.RESULT_SUCCESS;
            resJson.put("BatchDatas", codeFee1);
            resJson.put("ChickPrice", chickFees);
            resJson.put("FeedPrice", FeedPrices);
            resJson.put("ChickenPrice", chickenPrices);
            resJson.put("Result", "Success");
            resJson.put("ErrorMsg", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        DealSuccOrFail.dealApp(request, response, dealRes, resJson);
        mLogger.info("=====Now end executing FarmManageReqController.getSettlePriceRep");
    }

	private double getDoubleValue(BigDecimal tBigDecimal){
		if(tBigDecimal == null){
			return 0;
		}else{
			return tBigDecimal.doubleValue();
		}
	}
	/**
	 * 获取一个
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
            sSDUserOperationService.insert(SDUserOperationService.MENU_PRODUCTION_DAY, SDUserOperationService.OPERATION_SELECT, jsonObject.optInt("id_spa"));

            int BreedBatchId = tJSONObject.optInt("BreedBatchId");
            int houseId = tJSONObject.optInt("HouseId");
            String compareType = tJSONObject.getString("compareType");
            JSONArray jsonArray = new JSONArray();
            String SQLdata = " ";
            
            // 批次
            if(compareType.equals("01")){
            	SQLdata = "SELECT " +
                        " truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0)                   AS feed_days," +
                        " ifnull(sum(a.moveout_num), 0)                                                    AS market_num," +
                        " truncate(ifnull(sum(a.moveout_num) / sum(CASE WHEN a.batch_status = '02'" +
                        " THEN a.place_num ELSE 0 END), 0) * 100, 1)                                       AS market_rate," +
                        " truncate(ifnull(avg(a.moveout_weight), 0), 2)                                    AS body_weight_avg," +
                        " truncate(ifnull(sum(a.moveout_sumweight), 0), 2)                                 AS moveout_sumweight," +
                        " ifnull(a.market_feed_weight, 0)                                                  AS acc_feed," +
                        " truncate(ifnull(a.market_feed_weight / a.moveout_sumweight , 0), 1)              AS fcr," +
                        " ifnull(sum(a.place_num),0)                                                       AS place_num , h.house_name as name " +
                        " FROM s_b_house_breed a ,s_d_house h  " +
                        " WHERE a.farm_breed_id = " + BreedBatchId + " AND a.farm_id = h.farm_id AND a.house_id = h.id GROUP BY h.id ORDER BY h.id; ";
                
            }else{
            	SQLdata = "SELECT " +
                        " truncate(ifnull(avg(datediff(a.market_date, a.place_date)), 0), 0)                   AS feed_days," +
                        " ifnull(sum(a.moveout_num), 0)                                                    AS market_num," +
                        " truncate(ifnull(sum(a.moveout_num) / sum(CASE WHEN a.batch_status = '02'" +
                        " THEN a.place_num ELSE 0 END), 0) * 100, 1)                                       AS market_rate," +
                        " truncate(ifnull(avg(a.moveout_weight), 0), 2)                                    AS body_weight_avg," +
                        " truncate(ifnull(sum(a.moveout_sumweight), 0), 2)                                 AS moveout_sumweight, " +
                        " ifnull(a.market_feed_weight, 0)                                                  AS acc_feed," +
                        " truncate(ifnull(a.market_feed_weight / a.moveout_sumweight , 0), 1)              AS fcr," +
                        " ifnull(sum(a.place_num),0)                                                       AS place_num , b.batch_code as name " +
                        " FROM s_b_house_breed a ,s_b_farm_breed b   " +
                        " WHERE a.house_id = "+houseId+" AND a.farm_id = b.farm_id AND a.farm_breed_id = b.id GROUP BY b.batch_code ORDER BY b.id; ";
                
            }
            
            mLogger.info("FarmManageReqController.getProductionSumReport.SQLdata = " + SQLdata);
            List<HashMap<String, Object>> data = tBaseQueryService.selectMapByAny(SQLdata);
            for (HashMap<String, Object> cc : data) {
                String EuropIndexValue = BreedBatchReqController.getEuropIndexValue(PubFun.getDoubleData(cc.get("moveout_sumweight").toString()), PubFun.getIntegerData(cc.get("market_num").toString()),
                        PubFun.getIntegerData(cc.get("place_num").toString()), PubFun.getDoubleData(cc.get("acc_feed").toString()), PubFun.getIntegerData(cc.get("feed_days").toString()));
                
                	JSONObject OverViewJson1 = new JSONObject();
                
                    OverViewJson1.put("feed_days",  !"0".equals(cc.get("feed_days") ) ? cc.get("feed_days") : "-");
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

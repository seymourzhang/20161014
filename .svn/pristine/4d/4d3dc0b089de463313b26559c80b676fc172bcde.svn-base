/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.biz;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mtc.app.service.*;
import com.mtc.common.util.PubFun;
import com.mtc.entity.app.SBBreedDetail;
import com.mtc.entity.app.SBHouseBreed;

import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.entity.app.SBFarmBreed;
import com.mtc.entity.app.SBFarmSettle;

/**
 * @ClassName: BreedBatchReqManager
 * @Description: 
 * @Date 2015-12-4 下午2:29:24
 * @Author Shao Yao Yu
 * 
 */
@Component
public class BreedBatchReqManager {
	
	private static Logger mLogger =Logger.getLogger(BreedBatchReqManager.class);
	
	@Autowired
	private SBFarmBreedService tSBFarmBreedService;

	@Autowired
	private SBHouseBreedService sSBHouseBreedService;

	@Autowired
	private SBBreedDetailService sSBBreedDetailService;
	
	@Autowired
	private SBFarmSettleService tSBFarmSettleService;
	
	@Autowired
	private BaseQueryService tBaseQueryService;
	
	@Autowired
	private MySQLSPService tMySQLSPService;
	
	public SBFarmBreed dealSave_old(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		List<SBHouseBreed> tSBHouseBreed = (List<SBHouseBreed>)tPara.get("SBHouseBreedList");
		boolean flag = (boolean)tPara.get("farmBreedFlag");
		int farmBreedId = 0;
		if (flag) {
			tSBFarmBreedService.insertSBFarmBreed(tSBFarmBreed);
		}
		return tSBFarmBreed;
	}
	
	public List<SBHouseBreed> dealSave(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		List<SBHouseBreed> tSBHouseBreed = (List<SBHouseBreed>)tPara.get("SBHouseBreedList");
		boolean flag = (boolean)tPara.get("farmBreedFlag");
		int farmBreedId = 0;
		if (flag) {
			tSBFarmBreedService.insertSBFarmBreed(tSBFarmBreed);
		}
		
		farmBreedId = tSBFarmBreed.getId();
		List<SBBreedDetail> lSBBreedDetail = null;
		if(tSBHouseBreed != null && tSBHouseBreed.size() > 0){
			for (int i = 0; i < tSBHouseBreed.size(); ++i) {
			    tSBHouseBreed.get(i).setFarmBreedId(farmBreedId);
				sSBHouseBreedService.insert(tSBHouseBreed.get(i));
				int houseBreedId = tSBHouseBreed.get(i).getId();
				Date tempDate = tSBHouseBreed.get(i).getPlaceDate();
				lSBBreedDetail = new ArrayList<>();
				for (int j = 0; j < tSBFarmBreed.getBreedDays() + 10; j++){
	                tempDate = PubFun.addDate(tSBHouseBreed.get(i).getPlaceDate(), j);
	                SBBreedDetail tSBBreedDetail = new SBBreedDetail();
	                tSBBreedDetail.setHouseBreedId(houseBreedId);
	                tSBBreedDetail.setAge(j);
	                tSBBreedDetail.setGrowthDate(tempDate);
	                tSBBreedDetail.setDeathAm(0);
	                tSBBreedDetail.setDeathPm(0);
	                tSBBreedDetail.setCullingAm(0);
	                tSBBreedDetail.setCullingPm(0);
	                tSBBreedDetail.setCurCd(0);
	                tSBBreedDetail.setAccCd(0);
	                tSBBreedDetail.setCurFeed(new BigDecimal(0));
	                tSBBreedDetail.setAccFeed(new BigDecimal(0));
	                tSBBreedDetail.setCurWeight(new BigDecimal(0));
	                tSBBreedDetail.setCurAmount(tSBHouseBreed.get(i).getPlaceNum());
	                tSBBreedDetail.setYtdAmount(tSBHouseBreed.get(i).getPlaceNum());
	                tSBBreedDetail.setNumBak1(new BigDecimal(0));
	                tSBBreedDetail.setNumBak2(new BigDecimal(0));
	                tSBBreedDetail.setCreatePerson(tSBHouseBreed.get(i).getCreatePerson());
	                tSBBreedDetail.setCreateDate(new Date());
	                tSBBreedDetail.setCreateTime(new Date());
	                tSBBreedDetail.setModifyPerson(tSBHouseBreed.get(i).getModifyPerson());
	                tSBBreedDetail.setModifyDate(new Date());
	                tSBBreedDetail.setModifyTime(new Date());
	                lSBBreedDetail.add(tSBBreedDetail);
	            }
				if (lSBBreedDetail != null && lSBBreedDetail.size() != 0) {
					sSBBreedDetailService.insertBatch(lSBBreedDetail);
				}
				
			}
		}
		mLogger.info("新增入雏：" + tSBFarmBreed.getId());
		return tSBHouseBreed;
	}
	public List<SBHouseBreed> dealUpdate(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");

		tSBFarmBreedService.updateByPrimaryKey(tSBFarmBreed);

		List<SBHouseBreed> tSBHouseBreed = (List<SBHouseBreed>)tPara.get("SBHouseBreedUp");
		if (tSBHouseBreed != null) {
			for (SBHouseBreed sbHouseBreed : tSBHouseBreed) {
				sSBHouseBreedService.updateByPrimaryKey(sbHouseBreed);
			}
		}

		List<SBBreedDetail> lSBBreedDetails = (List<SBBreedDetail>)tPara.get("SBBreedDetailList");
		if (lSBBreedDetails != null) {
			for (SBBreedDetail lSBBreedDetail : lSBBreedDetails) {
				sSBBreedDetailService.updateByPrimaryKey(lSBBreedDetail);
			}
		}
		int nums = (Integer)tPara.get("planDayNum");
		List<SBHouseBreed> liSBHouseBreed = (List<SBHouseBreed>)tPara.get("SBHouseBreedInsert");
		if (liSBHouseBreed != null) {
			for (int i = 0; i < liSBHouseBreed.size(); ++i) {
				liSBHouseBreed.get(i).setFarmBreedId(tSBFarmBreed.getId());
				sSBHouseBreedService.insert(liSBHouseBreed.get(i));
				int houseBreedId = liSBHouseBreed.get(i).getId();
				
				List<SBBreedDetail> lSBBreedDetail = new ArrayList<>();
				Date tempDate = liSBHouseBreed.get(i).getPlaceDate();
				
				for (int j = 0; j < tSBFarmBreed.getBreedDays() + 10; j++){
	                tempDate = PubFun.addDate(liSBHouseBreed.get(i).getPlaceDate(), j);
	                SBBreedDetail tSBBreedDetail = new SBBreedDetail();
	                tSBBreedDetail.setHouseBreedId(houseBreedId);
	                tSBBreedDetail.setAge(j);
	                tSBBreedDetail.setGrowthDate(tempDate);
	                tSBBreedDetail.setDeathAm(0);
	                tSBBreedDetail.setDeathPm(0);
	                tSBBreedDetail.setCullingAm(0);
	                tSBBreedDetail.setCullingPm(0);
	                tSBBreedDetail.setCurCd(0);
	                tSBBreedDetail.setAccCd(0);
	                tSBBreedDetail.setCurFeed(new BigDecimal(0));
	                tSBBreedDetail.setAccFeed(new BigDecimal(0));
	                tSBBreedDetail.setCurWeight(new BigDecimal(0));
	                tSBBreedDetail.setCurAmount(liSBHouseBreed.get(i).getPlaceNum());
	                tSBBreedDetail.setYtdAmount(liSBHouseBreed.get(i).getPlaceNum());
	                tSBBreedDetail.setNumBak1(new BigDecimal(0));
	                tSBBreedDetail.setNumBak2(new BigDecimal(0));
	                tSBBreedDetail.setCreatePerson(liSBHouseBreed.get(i).getCreatePerson());
	                tSBBreedDetail.setCreateDate(new Date());
	                tSBBreedDetail.setCreateTime(new Date());
	                tSBBreedDetail.setModifyPerson(liSBHouseBreed.get(i).getModifyPerson());
	                tSBBreedDetail.setModifyDate(new Date());
	                tSBBreedDetail.setModifyTime(new Date());
	                lSBBreedDetail.add(tSBBreedDetail);
	            }
				
				if (lSBBreedDetail != null && lSBBreedDetail.size() != 0) {
					sSBBreedDetailService.insertBatch(lSBBreedDetail);
				}
			}
		}
		mLogger.info("更新入雏：" + tSBFarmBreed.getId());
		return liSBHouseBreed;
	}

	public SBBreedDetail settleDealSave(HashMap<String,Object> tPara){
		SBBreedDetail SBBreedDetail = (SBBreedDetail)tPara.get("SBBreedDetail");
		sSBBreedDetailService.updateByPrimaryKey(SBBreedDetail);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String SQL = "DELETE FROM s_b_breed_detail WHERE house_breed_id = " + SBBreedDetail.getHouseBreedId() + " AND growth_date > '" + sdf.format(SBBreedDetail.getGrowthDate()) + "'";
		tBaseQueryService.deleteByAny(SQL);

		SBHouseBreed SBHouseBreed = (SBHouseBreed)tPara.get("SBHouseBreed");
		sSBHouseBreedService.updateByPrimaryKey(SBHouseBreed);

		SBFarmBreed sbFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		tSBFarmBreedService.updateByPrimaryKey(sbFarmBreed);

		mLogger.info("出栏确认：" + SBBreedDetail.getHouseBreedId());
		return SBBreedDetail;
	}

	public void settleDealUpdate(HashMap<String,Object> tPara){
		SBFarmBreed sbFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		tSBFarmBreedService.updateByPrimaryKey(sbFarmBreed);

		SBHouseBreed sbHouseBreed = (SBHouseBreed)tPara.get("SBHouseBreed");
		sSBHouseBreedService.updateByPrimaryKey(sbHouseBreed);

		SBBreedDetail sbBreedDetail = (SBBreedDetail)tPara.get("SBBreedDetail");
		sSBBreedDetailService.updateByPrimaryKey(sbBreedDetail);

		mLogger.info("出栏修改：" + sbBreedDetail.getHouseBreedId());
	}

	public SBFarmBreed settleBatch(HashMap<String,Object> tPara){
		SBFarmBreed tSBFarmBreed = (SBFarmBreed)tPara.get("SBFarmBreed");
		tSBFarmBreedService.updateByPrimaryKey(tSBFarmBreed);
		
		String sql = "UPDATE s_b_farm_task a set a.task_status = 'N',a.bak1 = '"+tSBFarmBreed.getId()+"' where a.task_type = '05' and a.task_status = 'Y' and a.farm_id = " + tSBFarmBreed.getFarmId() ;	
		tBaseQueryService.updateIntergerByAny(sql);
		
		mLogger.info("保存批次：" + tSBFarmBreed.getId());		
		return tSBFarmBreed;
	}
	public int intterSBFarmSettle(HashMap<String,Object> tPara){
		List<SBFarmSettle> tList = (List<SBFarmSettle>) tPara.get("tList");
		int FarmBreedId = tList.get(0).getFarmBreedId();
		String SQL ="DELETE  FROM  s_b_farm_settle  WHERE farm_breed_id ="+FarmBreedId;
		tBaseQueryService.deleteByAny(SQL);
		mLogger.info("保存批次：" + tList.get(0).getId());
		return tSBFarmSettleService.Listinsert(tList);
	}
	
}

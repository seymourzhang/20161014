/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.mtc.mapper.app.SBChickenStandarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBChickenStandar;
import com.mtc.mapper.app.SBChickenStandarMapperCustom;

/**
 * @ClassName: SBChickenStandarService
 * @Description: 
 * @Date 2016年2月19日 下午1:10:39
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBChickenStandarService {
	@Autowired
	private SBChickenStandarMapperCustom tSBChickenStandarMapperCustom ;

	@Autowired
	private SBChickenStandarMapper mSBChickenStandarMapper;

	public int updateByPrimaryKey(SBChickenStandar sSBChickenStandar){
		return mSBChickenStandarMapper.updateByPrimaryKey(sSBChickenStandar);
	}
	
	public List<HashMap<String, Object>> select(int farmId, String breedName){
		return tSBChickenStandarMapperCustom.select(farmId,breedName);
	}

	public List<SBChickenStandar> selectForInsert(String breedName){
		return tSBChickenStandarMapperCustom.selectForInsert(breedName);
	}

	public void insertByFarmIdAndBreedName(List<SBChickenStandar> sSBChickenStandar){
		tSBChickenStandarMapperCustom.insertByFarmIdAndBreedName(sSBChickenStandar);
	}

	public void updateStandarByBreedAndFarmId(SBChickenStandar sSBChickenStandar){
		tSBChickenStandarMapperCustom.updateStandarByBreedAndFarmId(sSBChickenStandar);
	}

	public List<SBChickenStandar> selectByFarmId(int farmId){
		return tSBChickenStandarMapperCustom.selectByFarmId(farmId);
	}


	public int updateStandardDIY(SBChickenStandar eggsell) {
		// TODO Auto-generated method stub
		return tSBChickenStandarMapperCustom.updateStandardDIY(eggsell);
	}

	public List<SBChickenStandar> selectForOwner(int farmId){
		return tSBChickenStandarMapperCustom.selectForOwner(farmId);
	}
}

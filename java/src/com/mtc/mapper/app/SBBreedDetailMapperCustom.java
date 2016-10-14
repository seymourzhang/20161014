package com.mtc.mapper.app;

import java.util.Date;
import java.util.List;

import com.mtc.entity.app.SBBreedDetail;

public interface SBBreedDetailMapperCustom {

	List<SBBreedDetail> selectByhouseBreedId(int houseBreedId);
    
	SBBreedDetail selectByhouseIdDate(int houseBreedId,Date date);
	
	int insertBatch(List<SBBreedDetail> item);

	int updateBatch(List<SBBreedDetail> item);
}
/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBYincomm;
import com.mtc.entity.app.SBYincommMain;
import com.mtc.entity.app.SBYincommSub;
import com.mtc.mapper.app.SBYincommMainMapper;
import com.mtc.mapper.app.SBYincommMainMapperCustom;
import com.mtc.mapper.app.SBYincommMapper;
import com.mtc.mapper.app.SBYincommSubMapper;

/**
 * @ClassName: YincommDataService
 * @Description: 
 * @Date 2015年12月22日 下午2:40:53
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBYincommDataService {
	
	@Autowired
	private SBYincommMapper tSBYincommMapper ;
	
	@Autowired
	private SBYincommMainMapper tSBYincommMainMapper ;
	
	@Autowired
	private SBYincommMainMapperCustom tSBYincommMainMapperCustom;
	
	@Autowired
	private SBYincommSubMapper tSBYincommSubMapper ;
	
	public int insertOriginalData(SBYincomm tSBYincomm){
		return tSBYincommMapper.insert(tSBYincomm);
	}
	
	public int updateMainData(SBYincommMain tSBYincommMain){
		return tSBYincommMainMapperCustom.updateBymmSN(tSBYincommMain);
	}
	
	public int deleteSub(SBYincommSub tSBYincommSub){
		return tSBYincommSubMapper.deleteByPrimaryKey(tSBYincommSub.getMmSn(),
									tSBYincommSub.getSensorCode(),
									tSBYincommSub.getSensorSn());
	}
	public int insetSub(SBYincommSub tSBYincommSub){
		return tSBYincommSubMapper.insert(tSBYincommSub);
	}
}

/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtc.entity.app.SBRotemNetData;
import com.mtc.mapper.app.SBRotemNetDataMapper;

/**
 * @ClassName: SBRotemNetDataService
 * @Description: 
 * @Date 2017年1月5日 下午8:20:32
 * @Author Yin Guo Xiang
 * 
 */
@Service
public class SBRotemNetDataService {
	@Autowired
	private SBRotemNetDataMapper tSBRotemNetDataMapper;
	
	public int insertRotemNetData(SBRotemNetData record){
		return tSBRotemNetDataMapper.insert(record);
	}
}

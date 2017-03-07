package com.mtc.app.service;

import com.mtc.entity.app.SBBizMessage;
import com.mtc.mapper.app.SBBizMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Seymour on 2017/2/14.
 */
@Service
public class SBBizMessageService {
    @Autowired
    private SBBizMessageMapper sbBizMessageMapper;

    public int update(SBBizMessage record){
        return sbBizMessageMapper.updateByPrimaryKey(record);
    }
    public int insert(SBBizMessage record){
        return sbBizMessageMapper.insert(record);
    }
    public SBBizMessage selectByPrimaryKey(Integer id) {
        return sbBizMessageMapper.selectByPrimaryKey(id);
    }
    public int delete(Integer id) {
        return sbBizMessageMapper.deleteByPrimaryKey(id);
    }
}

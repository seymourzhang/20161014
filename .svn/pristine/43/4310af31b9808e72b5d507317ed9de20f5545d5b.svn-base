package com.mtc.app.service;

import com.mtc.entity.app.SBFarmStandar;
import com.mtc.mapper.app.SBFarmStandarMapperCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ants on 2016/6/14.
 */
@Service
public class SBFarmStandarService {
    @Autowired
    private SBFarmStandarMapperCustom sSBFarmStandarMapperCustom;

    public void insert(SBFarmStandar sSBFarmStandar){
        sSBFarmStandarMapperCustom.insert(sSBFarmStandar);
    }

    public void update(SBFarmStandar sSBFarmStandar){
        sSBFarmStandarMapperCustom.update(sSBFarmStandar);
    }

    public SBFarmStandar select(int farmId){
        return sSBFarmStandarMapperCustom.select(farmId);
    }
}

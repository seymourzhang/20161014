package com.mtc.app.biz;

import java.util.HashMap;
import java.util.List;

import com.mtc.mapper.app.SBChickenStandarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mtc.app.service.BaseQueryService;
import com.mtc.app.service.SBChickenStandarService;
import com.mtc.app.service.SBFarmStandarService;
import com.mtc.entity.app.SBChickenStandar;
import com.mtc.entity.app.SBFarmStandar;

/**
 * Created by Ants on 2016/6/17.
 */
@Component
public class BroilerStandardReqManager {

    @Autowired
	private BaseQueryService tBaseQueryService;

    @Autowired
    private SBChickenStandarService sSBChickenStandarService;

    @Autowired
    private SBChickenStandarService mSBChickenStandarService;

    @Autowired
    private SBFarmStandarService sSBFarmStandarService;

    public void dealStandardSave(HashMap<String,Object> tParas){
        List<SBChickenStandar> CKStandard = (List<SBChickenStandar>)tParas.get("ckStandard");

        SBFarmStandar FAStandard = (SBFarmStandar)tParas.get("faStandard");

        sSBChickenStandarService.insertByFarmIdAndBreedName(CKStandard);

        sSBFarmStandarService.insert(FAStandard);

    }
    
    public void dealStandardSave_v2(HashMap<String,Object> tParas){
    	String delSql1 = (String)tParas.get("delSql1");
    	String delSql2 = (String)tParas.get("delSql2");
    	SBFarmStandar FAStandard = (SBFarmStandar)tParas.get("faStandard");
        List<SBChickenStandar> CKStandard = (List<SBChickenStandar>)tParas.get("ckStandard");

        tBaseQueryService.deleteByAny(delSql1);
        tBaseQueryService.deleteByAny(delSql2);

        sSBChickenStandarService.insertByFarmIdAndBreedName(CKStandard);
        sSBFarmStandarService.insert(FAStandard);
    }

    public void dealStandardUpdate(HashMap<String,Object> tParas){
        String delSql = (String)tParas.get("delSql");

        List<SBChickenStandar> CKStandard = (List<SBChickenStandar>)tParas.get("ckStandard");

        SBFarmStandar FAStandard = (SBFarmStandar)tParas.get("faStandard");

        int i = 0;

        boolean flag = (boolean)tParas.get("flag");
        if(!flag) {
            tBaseQueryService.deleteByAny(delSql);
            sSBChickenStandarService.insertByFarmIdAndBreedName(CKStandard);
        }else {
            for (SBChickenStandar cs : CKStandard) {
               i = mSBChickenStandarService.updateByPrimaryKey(cs);
            }
        }

        sSBFarmStandarService.update(FAStandard);
    }
    
    public int updateStandardDIY(SBChickenStandar eggsell) {
		// TODO Auto-generated method stub
		return sSBChickenStandarService.updateStandardDIY(eggsell);
	}
}

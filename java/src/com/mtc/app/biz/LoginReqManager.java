package com.mtc.app.biz;

import com.mtc.app.service.SDUserService;
import com.mtc.app.service.SLUserImeiService;
import com.mtc.app.service.SLUserLogonService;
import com.mtc.entity.app.SDUser;
import com.mtc.entity.app.SLUserImei;
import com.mtc.entity.app.SLUserLogon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Ants on 2016/9/19.
 */
@Component
public class LoginReqManager {
    @Autowired
    private SLUserLogonService slUserLogonService;

    @Autowired
    private SLUserImeiService slUserImeiService;

    public void dealSave(HashMap<String,Object> tParams) {
        SLUserImei slUserImei = (SLUserImei) tParams.get("slUserImei");
        if (slUserImei != null) {
            slUserImeiService.delete(slUserImei.getImeiNo());
            slUserImeiService.insert(slUserImei);
        }

        SLUserLogon SLUserLogon = (SLUserLogon) tParams.get("userLogon");
        slUserLogonService.insert(SLUserLogon);
    }
}

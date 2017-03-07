// 判断农场、栋舍、员工是否有信息(该方法针对老板)
var biz_common_judgeRegistInfo = function(ionicPopup, state, page) {
    if (sparraw_user.userinfo.role == 1) {
        if (!sparraw_user.farminfo || sparraw_user.farminfo == 0) {
            var confirmPopup = ionicPopup.confirm({
                title: '提醒',
                template: '使用此功能前,请先创建农场信息。',
                buttons: [{
                    text: '取消',
                    onTap: function() {
                        console.log("留在本页");
                    }
                },
                {
                    text: '确定',
                    type: 'button-positive',
                    onTap: function() {
                        state.go("farmRegistered");
                    }
                }]
            });

        } else if (!sparraw_user.houseinfos || sparraw_user.houseinfos.length == 0) {
            var confirmPopup = ionicPopup.confirm({
                title: '提醒',
                template: '使用此功能前,请先创建栋舍信息。',
                buttons: [{
                    text: '取消',
                    onTap: function() {
                        console.log("留在本页");
                    }
                },
                {
                    text: '确定',
                    type: 'button-positive',
                    onTap: function() {
                        state.go("buildingTable");
                    }
                }]
            });
        } else if (!sparraw_user.userinfos || sparraw_user.userinfos.length == 0) {
            var confirmPopup = ionicPopup.confirm({
                title: '提醒',
                template: '使用此功能前,请先创建员工信息。',
                buttons: [{
                    text: '取消',
                    onTap: function() {
                        console.log("留在本页");
                    }
                },
                {
                    text: '确定',
                    type: 'button-positive',
                    onTap: function() {
                        state.go("employeesTable");
                    }
                }]
            });
            return;
        } else {
            console.log("都有啦~");
            state.go(page);
        };
    } else {
        state.go(page);
    };
};

// 重新获取该用户最新数据
var biz_common_getLatestData = function(state, nextPage) {

    Common_setDeviceImei();

    var params = {
        "userCode": sparraw_user.profile.account,
        "pw": sparraw_user.profile.password,
        "AndroidImei": Common_MOBILE_IMEI,
        "uuid": Common_MOBILE_UUID,
        "model": Common_MOBILE_MODELNAME,
        "sysVersion": Common_MOBILE_VERSION,
        "platForm": Common_MOBILE_PLATFORM
    };

    console.log(params);

    Sparraw.ajaxPost('sys/login/logIn.action', params,
    function(data) {
        if (data.ResponseDetail.LoginResult == 'Success') {

            sparraw_user.userinfo = data.ResponseDetail.userinfo;
            sparraw_user.farminfo = data.ResponseDetail.farminfo;
            sparraw_user.houseinfos = data.ResponseDetail.houseinfos;
            sparraw_user.userinfos = data.ResponseDetail.userinfos;
            sparraw_user.Authority = data.ResponseDetail.Authority;
            sparraw_user.profile = {
                'id_spa': data.ResponseDetail.userinfo.id,
                'secret': 'mtc_secret',
                'user_State': false,
                'account': sparraw_user.profile.account,
                'password': sparraw_user.profile.password
            };
            persistentData.dataEntryReceiveHouse = "";
        } else {
            Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
        };

        if(state && nextPage && nextPage != ""){
            state.go(nextPage);
        }
    });
}

// 将用户信息存于缓存中
function biz_common_saveUserInfo(userInfo){
    if(userInfo != null){
        localStorage.userInfo = userInfo; //设置一个键值
        var loginListStr = "{}";
        if(localStorage.loginList){
            loginListStr = localStorage.loginList;
        }
        var loginListObj = JSON.parse(loginListStr);
        var loginKey = JSON.parse(userInfo).userCode ;

        if(loginListObj.hasOwnProperty(loginKey)){
            delete loginListObj[loginKey];  
        }
        loginListObj[loginKey] = userInfo;

        //获取key并且倒序
        if (Common_getJsonSize(loginListObj) >= 10) {
            var listIndex = 0;
            var tempArr = [];
            var tempArr2 = [];
            for (var key in loginListObj){
                tempArr.push(key);
            }
            tempArr = tempArr.reverse();

            //获取大于10的key
            for (var i = 0; i < tempArr.length; i++) {
                listIndex ++;
                if (listIndex >= 10) {
                    tempArr2.push(tempArr[i]);
                }
            }

            //删除
            for (var i = 0; i < tempArr2.length; i++) {
                console.log(loginListObj[tempArr2[i]]);
                delete loginListObj[tempArr2[i]];
            }

        }else{

        }
        localStorage.loginList = JSON.stringify(loginListObj);
    }
}

// 缓存中获取用户信息
function biz_common_getUserInfo(){
    if(localStorage.userInfo){
        return JSON.parse(localStorage.userInfo) ;
    }else{
        return null;
    }
}

// 用户退出登录
function biz_common_userLogout(){
    selectBackPage.NeedLogin = false;
    sparraw_user.userinfo    = {}  ;
    sparraw_user.farminfo    = {}  ;
    sparraw_user.houseinfos  = []  ;
    sparraw_user.userinfos   = []  ;
    sparraw_user.profile = {
                'id_spa'      :  ''            ,
                'secret'      :  'mtc_secret'  ,
                'user_State'  :  true 
              };
    persistentData.dataEntryReceiveHouse = "";
    Alarm_clearTask();
}

// 模块正在建设中提示
function biz_common_pointDevelop() {
    app_alert('模块正在建设中...', '提示');
    return;
};

//设定标准设置默认值
function biz_common_getStandardWeekData(dateArr, valueArr) {
    var getArr = [];
    for (var i = 0; i < dateArr.length; i++) {
        getArr.push({
            "weekAge": i + 1,
            "cum_rate": (valueArr[i]).toFixed(2),
            "cum_alert": (valueArr[i] + 0.2).toFixed(2)
        });
    }
    return getArr;
}

//设定标准设置默认值
function biz_common_getStandardDayData(dateArr, valueArr) {
    var getArr = [];
    for (var i = 0; i < dateArr.length; i++) {
        getArr.push({
            "dayAge": i,
            "cum_rate": (valueArr[i]).toFixed(2),
            "cum_alert": (valueArr[i] + 0.2).toFixed(2)
        });
    }
    return getArr;
}

function biz_common_judgePassword(cancleCallBack){
    if(sparraw_user.profile.password == "123456") {
        app_confirm('您的密码是初始密码，是否立即进行修改？', '提示', null, function (button) {
            // 1-取消  2-确定
            if (button == 2) {
                Sparraw.goToStateURL("passwordModify");
            }else{
                cancleCallBack ? cancleCallBack() : biz_common_judgeDataInput();
            }
        });
    }else{
        cancleCallBack ? cancleCallBack() : biz_common_judgeDataInput();
    };
}

function biz_common_judgeDataInput(cancleCallBack){
    var params = {
        "operation":"needAlarm",
        "FarmBreedId":sparraw_user.farminfo && sparraw_user.farminfo.farmBreedBatchId ? sparraw_user.farminfo.farmBreedBatchId:0
    };
    Sparraw.ajaxPost('message/DataInputAlarm.action', params, function(data){
        if (data.ResponseDetail.Result == "Success") {
            if (data.ResponseDetail.NeedAlarm == 'Y') {
                var showList = null;
                showList = data.ResponseDetail.AlarmHouseList.toString();

                showList = showList.replace(/(,)/g,"栋,");
                var buttonLabels = ['稍后填','立即填'];

                app_confirm('截止到昨天'+ showList +'栋，尚有生产记录未填写。',null,buttonLabels,function(buttonIndex){
                    // 1-取消  2-确定
                    if(buttonIndex == 2){
                        Sparraw.goToStateURL("collection");
                    }else if(buttonIndex == 1){
                        cancleCallBack && cancleCallBack();
                    }
                });

            }else{
                cancleCallBack && cancleCallBack();
            }
        }else {
             Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
        }
    });
}
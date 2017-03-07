var API_Host = '';

var Sparraw = function() {

    var API_Lock = false;

    /* To be inited or changed in ctrl */    
    var $scope = null;
    var $state = null;

    var $ionicLoading = null;
    var $http = null;
    var $timeout = null;
    var $cordovaFileTransfer = null;
    var $cordovaFileOpener2 = null;
    var $ionicDatePicker = null;
    /* End */

    // 跳转页面
    var goToStateURL = function(url) {
        $state.go(url);
    };

    // 提示框
    var myNotice = function(msg, timeout) {
        $ionicLoading.show({
            template: msg
        });
        $timeout(function() {
            $ionicLoading.hide();
        },
        timeout || 1500);
        return false;
    }

    // Post请求
    var myRemote = function(target, params, done, fail, timeout) {
        if (!Common_isOnLine()) {
            return myNotice("请检查您的网络状态！");
        }

        var lock = target;
        if (API_Lock == lock) {
            console.log('Http Locked:' + API_Lock);
            return;
        }
        API_Lock = lock;
        
        console.log("请求参数：" + JSON.stringify(params));
        console.log("请求地址(POST)：" + target);

        if (target == 'null') {
            API_Lock = false;
            done && done('null');
        } else {
            // 如果 target 在 数组 CONFIG_actionURLArrayOfhideLoadIng 中
            if (!Common_existINArray(target,CONFIG_actionURLArrayOfhideLoadIng)) {
                $ionicLoading.show();
            }

            if (!timeout) {
                timeout = 20000;
            }

            $http.post(API_Host + target, JSON.stringify(params), {
                'timeout': timeout
            }).success(function(data) {
                if (!Common_existINArray(target,CONFIG_actionURLArrayOfhideLoadIng)) {
                    $ionicLoading.hide();
                }

                API_Lock = false;
                if (data && data.ResponseStatus == 1) {
                    console.log("返回成功：" + JSON.stringify(data));
                    done && done(data);
                } else {
                    console.log("返回失败：" + JSON.stringify(data));
                    $ionicLoading.hide();
                    fail ? fail(data) : myNotice(data ? JSON.stringify(data) : '发生错误');
                }
            }).error(function(data) {
                if (!Common_existINArray(target,CONFIG_actionURLArrayOfhideLoadIng)) {
                    $ionicLoading.hide();
                }

                console.log("网络错误：" + JSON.stringify(data));
                API_Lock = false;

                fail ? fail(data) : myNotice(data ? JSON.stringify(data) : '网络错误');
            });
        }
    }

    // Post请求
    var ajaxPost = function(target, params, done, fail, timeout) {
        myRemote(target, {
            'id_spa': sparraw_user.profile.id_spa,
            'secret': sparraw_user.profile.secret,
            'params': params
        },
        done, fail, timeout);
    }

    // Get请求
    var ajaxGet = function(target, done, fail,timeout){
        if (!Common_isOnLine()) {
            return myNotice("请检查您的网络状态！");
        }

        var lock = target;
        if (API_Lock == lock) {
            console.log('Http Locked:' + API_Lock);
            return;
        }
        API_Lock = lock;
        
        console.log("请求地址(GET)：" + target);

        if (target == 'null') {
            API_Lock = false;
            done && done();
        } else {
            if (!timeout) {
                timeout = 20000;
            }

            $http.get( API_Host + target)
            .success(function(data) {
                API_Lock = false;
                done && done(data);
            }).error(function(data) {
                API_Lock = false;
                fail ? fail(data) : myNotice(data ? JSON.stringify(data) : '网络错误');
            });
        }
    }

    // 单个Controller初始化
    var intoMyController = function(scope, state) {
        $scope = scope;
        $state = state;
        $scope.myConfig = myConfig;
        $scope.tempVar  = tempVar;
    }

    // Controller基础初始化
    var inintBaseController = function(ionicLoading,http,timeout,cordovaFileTransfer,cordovaFileOpener2,ionicDatePicker) {
        $ionicLoading = ionicLoading;
        $http = http;
        $timeout = timeout;
        $cordovaFileTransfer = cordovaFileTransfer;
        $cordovaFileOpener2 = cordovaFileOpener2;
        $ionicDatePicker = ionicDatePicker;
    }

    var getIonicLoading = function(){
        return $ionicLoading;
    }

    var getHttp = function(){
        return $http;
    }
    var getTimeout = function(){
        return $timeout;
    }
    var getCordovaFileTransfer = function(){
        return $cordovaFileTransfer;
    }
    var getCordovaFileOpener2 = function(){
        return $cordovaFileOpener2;
    }
    
    var evalSetDateStr = null ;
    var evalsetDateCallBackStr = null ;

    var datePickerObj = {
        callback: function (val) {
            console.log('DatePicker Value is : ' + val + '===' + new Date(val));
            evalSetDateStr = evalSetDateStr.replace("$DateStr$",new Date(val).Format("yyyy-MM-dd"));
            evalSetDateStr?eval(evalSetDateStr):null;
            evalsetDateCallBackStr?eval(evalsetDateCallBackStr):null;
        }
    };

    var openDatePicker = function(ng_modal_name,callback_str){
        evalSetDateStr = "$scope.$ngModalName$ = '$DateStr$' ;" ;
        evalSetDateStr = evalSetDateStr.replace("$ngModalName$",ng_modal_name);

        evalsetDateCallBackStr = callback_str ;

        $ionicDatePicker.openDatePicker(datePickerObj);
    }

    var sparraw = {}

    sparraw.myNotice = myNotice;
    //  POST请求
    sparraw.ajaxPost = ajaxPost;
    //  Get请求
    sparraw.ajaxGet = ajaxGet;

    sparraw.inintBaseController = inintBaseController;
    sparraw.intoMyController = intoMyController;
    sparraw.goToStateURL = goToStateURL;

    sparraw.getIonicLoading = getIonicLoading;
    sparraw.getHttp = getHttp;
    sparraw.getTimeout = getTimeout;
    sparraw.getCordovaFileTransfer = getCordovaFileTransfer;
    sparraw.getCordovaFileOpener2 = getCordovaFileOpener2;
    sparraw.openDatePicker = openDatePicker;
    
    return sparraw;
}();
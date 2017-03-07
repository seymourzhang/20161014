var Common_MOBILE_IMEI = 'null';
var Common_MOBILE_UUID = 'null';
var Common_MOBILE_MODELNAME = 'null';
var Common_MOBILE_VERSION = 'null';
var Common_MOBILE_PLATFORM = 'null';

// 移动设备参数初始化
function Common_setDeviceImei(){
    try{
        window.plugins.imeiplugin.getImei(setImei);
        function setImei(imei) {
            Common_MOBILE_IMEI = imei;
            console.log("My Android IMEI :" + Common_MOBILE_IMEI);
        }
        function onDeviceReady() {
                Common_MOBILE_UUID = device.uuid;
                Common_MOBILE_MODELNAME = device.model;
                Common_MOBILE_VERSION = device.version;
                Common_MOBILE_PLATFORM = device.platform;
        }
        document.addEventListener("deviceready", onDeviceReady, false);
    }catch(exception){
        console.log(exception);
    }
}

/**
* description :自动补零函数
* paras:num-需要补零的数字，fill-结果字符串长度
* 
*/
function Common_padNumber(num, fill) {
    var len = ('' + num).length;
    return (Array(
        fill > len ? fill - len + 1 || 0 : 0
    ).join(0) + num);
}

/**
* description :判断是否是数字
* paras:s
*/
function Common_isNum(s)
{
    if (s!=null && s!="")
    {
        return !isNaN(s);
    }
    return false;
}

/**
* description :获取音乐的路径
* paras:s
*/
function Common_getMediaURL(path) {

    var ua = navigator.userAgent.toLowerCase(); 
    if (/iphone|ipad|ipod/.test(ua)) {
        if(device.platform.toLowerCase() === "android") {
            return "file:///android_asset/www/" + path; 
        }else{
            return path;
        }
    } else if (/android/.test(ua)) {
        if(device.platform.toLowerCase() === "android") {
            return "file:///android_asset/www/" + path; 
        }else{
            return path;
        }
    }else{
        console.log("________________非手机平台无效。");
    }
}

function Common_isOnLine_Wifi(){
    if(!Common_isAndroid() && !Common_isIOS()){
        return true ;
    }else{
        return navigator && navigator.connection && navigator.connection.type==Connection.WIFI;
    }
}
function Common_isOnLine(){
    if(!Common_isAndroid() && !Common_isIOS()){
        return navigator && navigator.onLine ;
    }else{
        return navigator && navigator.connection && navigator.connection.type!=Connection.NONE;
    }
}

function app_alert(message,title,buttonName,callback){
    if(title == null){
        title = "提示";
    }
    if(buttonName == null){
        buttonName = "确 定";
    }
    try{
        navigator.notification.alert(
            message, 
            callback, 
            title,  
            buttonName 
        );
    }catch(e){
        console.log(e);
        alert(message);
    }
}
function app_confirm(message,title,buttonLabels,onConfirm){
    /*
    navigator.notification.confirm(
        'You are the winner!', // message
         onConfirm,            // callback to invoke with index of button pressed
        'Game Over',           // title
        ['Restart','Exit']     // buttonLabels
    );
    */
    if(title == null){
        title = "提示";
    }
    if(buttonLabels == null){
        buttonLabels = ['取 消','确 定'];
    }
    try{
        navigator.notification.confirm(
            message, 
            onConfirm, 
            title,  
            buttonLabels 
        );
    }catch(e){
        //console.log(e);
        var r = confirm(message);
        if (r==true){
            onConfirm && onConfirm(2);
        }else{
            onConfirm && onConfirm(1);
        }
    }
}
function app_prompt(message,title,buttonLabels,onPrompt,defaultText){
    /*
    navigator.notification.prompt(
        'Please enter your name',  // message
        onPrompt,                  // callback to invoke
        'Registration',            // title
        ['Ok','Exit'],             // buttonLabels
        'Jane Doe'                 // defaultText
    );
    */
    if(title == null){
        title = "提示";
    }
    if(buttonLabels == null){
        buttonLabels = ['取 消','确 定'];
    }
    try{
        navigator.notification.prompt(
            message, 
            onPrompt, 
            title,  
            buttonLabels,
            defaultText
        );
    }catch(e){
        console.log(e);
    }
}

function Common_NulltoZero(Obj){
    if (Obj === "" ||!Obj) {
        return 0;
    }else{
        return Obj;
    };
}

function Common_isNull(Obj){
    if (Obj === "" ||!Obj) {
        return app_alert("尚有内容未填写...");
    }else{
        return true;
    };
}

function Common_judgeDevice(){
    var ua = navigator.userAgent.toLowerCase(); 
    if (/iphone|ipad|ipod/.test(ua)) {
        return "iphone";    
    } else if (/android/.test(ua)) {
        return "android";   
    }
    return 0;
}


function Common_vibrate(paraArray){
    navigator.vibrate(paraArray);
}

function Common_wakeLock(){
    try{
        window.powerManagement.acquire(function() {
            // app_alert('Wakelock acquired');
        }, function() {
            app_alert('Failed to acquire wakelock');
        });
    }catch(e){
        console.log(e);
    }
}

function Common_wakeLockDim(){
    try{
        window.powerManagement.dim(function() {
        //    app_alert('Wakelock dim acquired');
        }, function() {
            app_alert('Failed to acquire dim wakelock');
        });
    }catch(e){
        //console.log(e);
    }
}

function Common_releaseWakeLock(){
    try{
        window.powerManagement.release(function() {
            //app_alert('Wakelock released');
        }, function() {
            app_alert('Failed to release wakelock');
        });
    }catch(e){
        console.log(e);
    }
}

function Common_setReleaseOnPause(){
    try{
        window.powerManagement.setReleaseOnPause(false, function() {
            //app_alert('Set successfully');
        }, function() {
            app_alert('Failed to set');
        });
    }catch(e){
        console.log(e);
    }
}

// 获取 json 的长度
function Common_getJsonSize(o){  
   var n, count = 0;  
   for(n in o){
      if(o.hasOwnProperty(n)){
         count++;
      }
   }
   return count;  
}  

function Common_appUpGrade(appVersion){
    
    var $ionicLoading = Sparraw.getIonicLoading();
    var $cordovaFileTransfer = Sparraw.getCordovaFileTransfer();
    var $cordovaFileOpener2 = Sparraw.getCordovaFileOpener2();
    var $timeout = Sparraw.getTimeout();

    var apkName = CONFIG_apkNamePrefix + "_" + appVersion + ".apk"; 

    var url = API_Host + apkName; //可以从服务端获取更新APP的路径

    // var targetPath = "/storage/emulated/0/" + apkName ;  //APP下载存放的路径，可以使用cordova file插件进行相关配置
    var targetPath = cordova.file.externalRootDirectory + apkName;

    var trustHosts = true;
    var options = {};

    $cordovaFileTransfer.download(url, targetPath, options, trustHosts).then(function (result) {
        
        //打开下载下来的APP
        $cordovaFileOpener2.open(targetPath, 'application/vnd.android.package-archive'
        ).then(function () {
                // 成功
        }, function (err) {
            // 错误
        });
        
    }, function (err) {
        app_alert('更新失败,失败原因：' + JSON.stringify(err));
    }, function (progress) {
        //进度，这里使用文字显示下载百分比
        $timeout(function () {
            var downloadProgress = (progress.loaded / progress.total) * 100;
            $ionicLoading.show({
                template: "已经下载：" + Math.floor(downloadProgress) + "%"
            });
            if (downloadProgress > 99) {
                $ionicLoading.hide();
            }
        })
    });
}


// 检查更新
function Common_checkForUpdate(cancleCallBack){
    if(Common_isAndroid()){
        Sparraw.ajaxGet("checkVersion.jsp",function(data){
            data = JSON.stringify(data);
            var tVersion = data.substring(data.indexOf("$")+1,data.indexOf("$",data.indexOf("$")+1));
            console.log("tVersion:" + tVersion);
            if(CONFIG_App_Version != tVersion){
                app_confirm('服务器最新版本是：'+tVersion+'，请下载更新，更新过程中您的数据信息将不会丢失。',null,null,function(buttonIndex){
                    // 1-取消 2-确定
                    if(buttonIndex == 1){
                        
                    }else if(buttonIndex == 2){
                        Common_appUpGrade(tVersion);
                    }
                }); 
            }else{
                cancleCallBack && cancleCallBack();
            }
        },function(data){
            app_alert("检查更新网络错误，请稍后再试。");
        });
    }else{
        cancleCallBack && cancleCallBack();
    }
}

function Common_barScan(succCallBack,failCallBack){
   try{
        cordova.plugins.barcodeScanner.scan(
          function (result) {
            // alert("We got a barcode\n" +
           //          "Result: " + result.text + "\n" +
           //          "Format: " + result.format + "\n" +
           //          "Cancelled: " + result.cancelled);
            succCallBack && succCallBack(result);
          }, 
          function (error) {
            // alert("Scanning failed: " + error);
            failCallBack && failCallBack(error);
          }
        );
    }catch(e){
        console.log(e);
    }
};

function Common_isAndroid(){
    if(Common_judgeDevice() == 'android'){
        return true;
    }else{
        return false;
    }
}


function Common_isIOS(){
    if(Common_judgeDevice() == 'iphone'){
        return true;
    }else{
        return false;
    }
}

var Common_initJpushUI = function() {
    try {
        window.plugins.jPushPlugin.init();
        Common_JPush_getRegistrationID();

        if(Common_isIOS()){
          window.plugins.jPushPlugin.setDebugModeFromIos();
          window.plugins.jPushPlugin.setApplicationIconBadgeNumber(0);
        }else if(Common_isAndroid()){
          window.plugins.jPushPlugin.setDebugMode(false);
          window.plugins.jPushPlugin.setStatisticsOpen(true);
        }
        var onTagsWithAlias = function(event) {
          try {
             console.log("onTagsWithAlias");    
             var result = "result code:"+event.resultCode + " ";
             result += "tags:" + event.tags + " ";
             result += "alias:" + event.alias + " ";
          } catch(exception) {
              console.log(exception);
          }
        }
        document.addEventListener("jpush.setTagsWithAlias", onTagsWithAlias, false);

        if (Common_isIOS()) {
            window.plugins.jPushPlugin.setDebugModeFromIos();
            window.plugins.jPushPlugin.setApplicationIconBadgeNumber(0);
        } else if(Common_isAndroid()){
            window.plugins.jPushPlugin.setDebugMode(true);
            window.plugins.jPushPlugin.setStatisticsOpen(true);
        }
    } catch (exception) {
        console.log(exception);
    }
};
var JpushTags = ["mtc_tag_0"];
var JpushAlias = "mtc_alias_0";
var Common_JPush_setTagsWithAlias = function(tags,alias) {
    try {
        JpushTags = tags;
        JpushAlias = alias ;
        window.plugins.jPushPlugin.setTagsWithAlias(JpushTags, JpushAlias);
    } catch (exception) {
        //console.log(exception);
    }
};

var Common_JPush_getRegistrationID = function() {
    window.plugins.jPushPlugin.getRegistrationID(Common_JPush_onGetRegistrationID);
};

var Common_JPush_onGetRegistrationID = function(data) {
    try {
        if (data.length == 0) {
            var t1 = window.setTimeout(Common_JPush_getRegistrationID, 1000);
        }else{
            Common_JPush_setTagsWithAlias(JpushTags, JpushAlias);
        }
    } catch (exception) {
        console.log(exception);
    }
};

// 向本地存储存放数据
function Common_saveObjectToLocalStorage(key,obj) {
    try{
        if(key != null && obj != null){
            localStorage[key] = (obj); //设置一个键值 
        }
    }catch(e){
        throw e;
    }
    
}
// 从本地存储获取数据
function Common_getObjectFromLocalStorage(key){
    try{
        if(key){
            var obj = localStorage[key];
            return obj;
        }else{
            return null;
        }
    }catch(e){
        throw e;
    }
}

// 向本地存储删除数据
function Common_deleteObjectFromLocalStorage(key){
    try{
        if(key != null){
            delete localStorage[key];
        }
    }catch(e){
        throw e;
    }
}

//  从 JSon-source 中得到 key 的 数组
function Common_getKeyArrayFromJson(source) {
    var result = [],
    key,
    _length = 0;
    for (key in source) {
        if (source.hasOwnProperty(key)) {
            result[_length++] = key;
        }
    }
    return result;
}

//  从 JSon-source 中得到 key 的 数组
function Common_GetValueArrayFromJson(source) {
    var result = [],
    key,
    _length = 0;
    for (key in source) {
        if (source.hasOwnProperty(key)) {
            result[_length++] = source[key];
        }
    }
    return result;
}

// 锁定屏幕方向
// portrait-保持竖屏;landscape-横屏
function app_lockOrientation(orientation) {

    console.log("请求屏幕方向" + orientation);
    console.log("当前屏幕方向" + screen.orientation);
    try {
        screen.lockOrientation(orientation);
    } catch(e) {
        console.log(e);
    }
}

function setLandscape(setiOS, setAndroid) {
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        console.log("ios");
        if (setiOS == false) {

        } else {
            setTimeout(function() {
                app_lockOrientation('landscape'); //进入时横屏
            },
            1000);
        }
    } else if (/android/.test(ua)) {
        console.log("android");
        if (setAndroid == false) {

    } else {
            setTimeout(function() {
                app_lockOrientation('landscape'); //进入时横屏
            },
            1000);
        }
    }
}

function setPortrait(setiOS, setAndroid) {
    var ua = navigator.userAgent.toLowerCase();
    if (/iphone|ipad|ipod/.test(ua)) {
        console.log("ios");
        if (setiOS == false) {
    } else {
            setTimeout(function() {
                app_lockOrientation('portrait'); //进入时竖屏
            },
            1000);
        }
    } else if (/android/.test(ua)) {
        console.log("android");
        if (setAndroid == false) {

    } else {
            setTimeout(function() {
                app_lockOrientation('portrait'); //进入时竖屏
            },
            1000);
        }
    } else {
        console.log("________________非手机平台旋转无效。");
    }
}

//删除数组重复内容
function Common_deleteRepeat(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem]) {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

// 日期的天数加减
// date格式为：yyyy-mm-dd
function Common_addDateByDays(date,days){ 
    var d=new Date(date); 
    d.setDate(d.getDate()+days); 
    var month=d.getMonth()+1; 
    var day = d.getDate(); 
    if(month<10){ 
        month = "0"+month; 
    } 
    if(day<10){ 
        day = "0"+day; 
    } 
    var val = d.getFullYear()+"-"+month+"-"+day; 
    return val; 
}

// 两个数乘积
function Common_multiply(arg1, arg2) {
    var m = 0,
    s1 = arg1.toString(),
    s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch(e) {
        // m += s1.length;
    }
    try {
        m += s2.split(".")[1].length;
    } catch(e) {
        //m += s2.length;
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// arg 是否在 tArray 中存在
function Common_existINArray(arg, tArray) {
    if(arg && tArray && tArray instanceof Array){
        return tArray.indexOf(arg) >= 0;
    }else{
        return false;
    }
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")   ==> 2006-7-2 8:9:4.18


Date.prototype.Format = function (fmt) { //author: meizz
  var o = {
    "M+": this.getMonth() + 1, //月份
    "d+": this.getDate(), //日
    "h+": this.getHours(), //小时
    "m+": this.getMinutes(), //分
    "s+": this.getSeconds(), //秒
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
    "S": this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
}
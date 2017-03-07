 var AppMode = 'Production';  // 生产模式
// var AppMode = 'Local';  // 本地模式
// var AppMode = 'Development';  // 测试开发模式

var App_Version = '1.6.9';

var ANDROID_IMEI = '';
var UUID = '';
var MODELNAME = '';
var VERSION = '';
var PLATFORM = '';

var Production_API_Host = 'http://broiler.agnet.com.cn/sweb/';  // 生产服务器地址
var Development_API_Host = 'http://116.228.141.210:8089/test_web/';  // 开发服务器地址
var Local_API_Host = 'http://192.168.10.115:8088/sweb/';  // 本地调试地址

//http://192.168.10.114:8080/sweb/farmManage/getBenefitRep.action

var myConfig = {
  'device'          : 'android',
  'address1'        : {"110000":"北京","120000":"天津","130000":"河北","140000":"山西","150000":"内蒙古","210000":"辽宁","220000":"吉林","230000":"黑龙江","310000":"上海","320000":"江苏","330000":"浙江","340000":"安徽","350000":"福建","360000":"江西","370000":"山东","410000":"河南","420000":"湖北","430000":"湖南","440000":"广东","450000":"广西","460000":"海南","500000":"重庆","510000":"四川","520000":"贵州","530000":"云南","540000":"西藏","610000":"陕西","620000":"甘肃","630000":"青海","640000":"宁夏","650000":"新疆"},
  'address2'        : {},
  'address3'        : {},
  'address4'        : {},
  'corporation'     : {'01':'森宝','02':'嘉吉','03':'泰森','04':'暂无合作公司'},
  'role'            : {1:'老板',2:'场长',5:'副场长',6:'统计员',3:'技术员',4:'饲养员'},

  'feedtype'        : {1:'地面平养',2:'网上平养',3:'立体笼养',4:'其他'},  
  'businessModel'   : {1:'合同养殖',2:'市场化养殖'},
  'breeding'        : {1:'白羽肉鸡',2:'黄羽肉鸡',3:'肉杂鸡',4:'蛋鸡育成',5:'种鸡育成'},

  //报警设置
  'Delay'           : {0:'请选择',1:'一分钟',2:'两分钟',3:'三分钟',4:'四分钟',5:'五分钟',6:'六分钟'},
  'alarmProbe'      : {0:'请选择',1:'独立温区报警',2:'平均温度报警'},
  

  //新建批次
  'doc_vendors'     : {1:'厂家1',2:'厂家2',3:'厂家3'},
  //'breed'           : {1:'白羽鸡',2:'黄羽鸡',3:'蛋鸡'},

  //日报填制
  'buildingNumber'  : {1:'测试数据1',2:'测试数据2',3:'测试数据3'},


  //"needAlarm"       : {1:'N',2:'Y'},
  /*'All':'全部','pointTemp':'温差点报警','frontTemp':'前区报警','middleTemp':'中区报警','backTemp':'后区报警','avgTemp':'平均温度报警'*/

  //报警日志-筛选
  'alarmLogType'    : {"All":'全部',"pointTemp":'点温差报警',"frontTemp":'前区报警',"middleTemp":'中区报警',"backTemp":'后区报警',"avgTemp":'平均温度报警',"powerStatus":'断电报警'},
  'logStart'        : {0:'0',1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10',11:'11',12:'12',13:'13',14:'14',15:'15',16:'16',17:'17',18:'18',19:'19',20:'20',21:'21',22:'22',23:'23',24:'24',25:'25',26:'26',27:'27',28:'28',29:'29',30:'30',31:'31',32:'32',33:'33',34:'34',35:'35',36:'36',37:'37',38:'38',39:'39',40:'40',41:'41',42:'42',43:'43',44:'44',45:'45',46:'46',47:'47',48:'48',49:'49',50:'50'},
  'logEnd'          : {0:'0',1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10',11:'11',12:'12',13:'13',14:'14',15:'15',16:'16',17:'17',18:'18',19:'19',20:'20',21:'21',22:'22',23:'23',24:'24',25:'25',26:'26',27:'27',28:'28',29:'29',30:'30',31:'31',32:'32',33:'33',34:'34',35:'35',36:'36',37:'37',38:'38',39:'39',40:'40',41:'41',42:'42',43:'43',44:'44',45:'45',46:'46',47:'47',48:'48',49:'49',50:'50'},


  //死淘(成活)率
  'CompareType'     : {'01':'批次','02':'栋舍'},

  //费用输入方式
  'costType'     : {"0":'请选择',"01":'总数',"02":'明细'},


  //生产标准
  // 'prodStan'        : {0:'请选择',1:'科宝(2015)',2:'罗斯(2014)',3:'AA+(2014)',4:'正大笼养',5:'正大平养'},
  'prodStan'        : {"10000":'请选择',"10001":'科宝(2015)',"10002":'AA+(2014)',"10003":'罗斯(2014)',"10004":'正大笼养',"10005":'正大平养'},

  //多批盈利查询
  'inquireUnits'    : {"Money":'万元',"quentity":'元/只',"weight":'元/公斤'},

  //任务是否有效
  'taskStatus'      : {'Y':'启用','N':'停用'},
  //任务日龄
  'taskDate'        : {'0':'入雏当天','-1':'入雏前1天','-2':'入雏前2天','-3':'入雏前3天','-4':'入雏前4天','-5':'入雏前5天','-6':'入雏前6天','-7':'入雏前7天','-8':'入雏前8天','-9':'入雏前9天','-10':'入雏前10天','-11':'入雏前11天','-12':'入雏前12天','-13':'入雏前13天','-14':'入雏前14天','-15':'入雏前15天','-16':'入雏前16天','-17':'入雏前17天','-18':'入雏前18天','-19':'入雏前19天','-20':'入雏前20天'},

  //任务时间段
  'taskTimeSlot'    : {'0':'默认','1':'入雏前','2':'第1周','3':'第2周','4':'第3周','5':'第4周','6':'第5周','7':'第6周','8':'第7周','9':'第8周','10':'第9周','11':'第10周'},

  //任务完成状态
  'taskCompleteStatus'      : {'01':'完成','02':'延后','03':'取消'}
};

//持久数据
var persistentData = {
  //数据录入所选的栋舍
  'dataEntryReceiveHouse' :  '',
  'standardType'          :  '',
  'switchRemind'          :  true,
};

//选择跳转的页面
var selectBackPage = {
  'profitReportBack' : '',
  'firstTime':'',
  'NeedLogin':true,
  'reportingBack':'',
};





var DeviInfo = {
  'ScreenHeight' : document.documentElement.clientHeight  ,
  'ScreenWidth'  : document.documentElement.clientWidth   ,
  'DeviceType'   : navigator.userAgent.toLowerCase()
};

var tempVar = {
  'houseTemp' : {
          // 'id'              :''   , 
          // 'houseName'       :''   , //栋编号
          // 'h_length'        :''   ,
          // 'h_width'         :''   ,
          // 'h_height'        :''   ,
          // 'feedarea'        :''   , // 养殖面积
          // 'mtc_device_id'   :''
  },
  'userTemp' : {
          // 'id'     : ''          ,
          // 'name'   : ''          ,
          // 'role'   : {}          ,// 角色信息:01-厂长、02-饲养员、03-技术员、04-农场老板、05-管理员、06-维修员
          // 'tele'   : ''          ,
          // 'pw1'    : ''          ,
          // 'pw2'    : ''          ,
          // 'pw'     : ''          ,
          // 'houses_authority':[]  ,
          // 'houses' : ''     
  },
  'AlarmSetting' : {
            'HouseId'          :  ''  ,  //栋舍号
            'Delay'            :  ''  ,  //报警延迟
            'tempCpsation'     :  ''  ,  //温度补偿
            'tempCpsationVal'  :  ''  ,  //补偿数值
            'alarmProbe'       :  ''  ,  //报警探头
            'pointAlarm'       :  ''  ,  //点温差报警
            'tempSettings'        :  [
                    {"dayAge":1   ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,  //dayAge:int型,tarTemp/minTemp/maxTemp：number型
                    {"dayAge":7   ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,
                    {"dayAge":14  ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,
                    {"dayAge":21  ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,
                    {"dayAge":28  ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,
                    {"dayAge":35  ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }  ,
                    {"dayAge":42  ,   "tarTemp":""  ,  "minTemp":""  ,  "maxTemp":""  }
            ],
            "effAlarmProbe":  {
                            "tempLeft1":true,
                            "tempLeft2":true,
                            "tempMiddle1":true,
                            "tempMiddle2":true,
                            "tempRight1":true,
                            "tempRight2":true
                           }
  },
  'newBatchData'  : {
            "BatchCode"    :  ''  ,  //varchar型，批次编号
            "place_date"   :  ''  ,  //varchar型，入雏日期
            "place_num"    :  ''  ,  //int型，入雏数量
            "breed_days"   :  ''  ,  //int型，预计饲养天数
            "market_date"  :  ''  ,  //varchar型，预计出栏日
            "doc_vendors"  :  ''  ,  //varchar型，雏源厂家
            "breed"        :  ''     //varchar型,品种
  },
  'batchClearingData'  : {
            'companyName'       :  ''  ,
            'clearingQuantity'  :  ''  ,
            'giveQuantity'      :  ''  ,
            'clearingPrice'     :  ''  
            
  },
/*  'docPlaceData'  : {
            'HouseId'    :  ''  ,  //栋舍id
            'PlaceNum'   :  ''  ,  //入雏数量
            'PlaceDate'  :  ''     //入雏日期
  },*/
  /*'dailyReportData'  : {

            //查询信息
            "CurDayAge"        :  9        ,  //int型，当前日龄
            "cur_amount"       :  20000    ,  //int型，存栏数量
            "std_cd_rate"      :  "99%"    ,  //varchar型，警戒死淘率
            "original_amount"  :  20000    ,  //int型，入雏数量
            "atu_cd_rate"      :  "98%"    ,  //varchar型，死淘率



            //保存信息
            'buildingNumber'  :  ''  ,
            'dataInput'       :  [{
                     "day_age"       :  1     ,  //int类型，对应日龄
                     "death_am"      :  25    ,  //int类型，上午死亡率
                     "death_pm"      :  25    ,  //int类型，下午死亡率
                     "culling_am"    :  25    ,  //int类型，上午淘汰量
                     "culling_pm"    :  25    ,  //int类型，下午淘汰量
                     "daily_feed"    :  2.05  ,  //number类型，用料
                     "daily_weight"  :  3.25     //number类型，均重
            },{},{}]  
  },*/



  /*'envMonitoringData'  : {
            "ResponseDetail":[{
                  "houseName"         :  '001'    ,  //栋舍名称
                  "dayAge"            :  '8'      ,  //日龄
                  "out_temp"          :  '22.3℃'  ,  //室外温度
                  "temp1"             :  '26.6℃'  ,  //前区温度
                  "temp2"             :  '25.7℃'  ,  //中区温度
                  "temp3"             :  '27.8℃'  ,  //后区温度
                  "tar_temp"          :  '38'     ,  //目标温度
                  "avg_temp"          :  '25.7'   ,  //平均温度
                  "H_temp"            :  '28.2'   ,  //高报温度
                  "L_temp"            :  '21.5'   ,  //低报温度
                  "point_temp"        :  '2'      ,  //点温差
                  "humi"              :  '67.03%' ,  //湿度
                  "power_status"      :  '否'     ,  //断电报警（0）


                  "temp_in1_alarm"    :  'N'  ,  //前区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in2_alarm"    :  'N'  ,  //中区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in3_alarm"    :  'N'  ,  //后区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_avg_alarm"    :  'N'  ,  //平均温度：高报警（H）,正常（N）,低报警（L)
                  "point_temp_alarm"  :  'N'  ,  //点温差：报警（H）

                 },{
                  "houseName"         :  '002'   ,  //栋舍名称
                  "dayAge"            :  '18'    ,  //日龄
                  "out_temp"          :  '10℃'   ,  //室外温度
                  "temp1"             :  '2.6℃'  ,  //前区温度
                  "temp2"             :  '5.7℃'  ,  //中区温度
                  "temp3"             :  '7.8℃'  ,  //后区温度
                  "tar_temp"          :  '8'     ,  //目标温度
                  "avg_temp"          :  '5.7'   ,  //平均温度
                  "H_temp"            :  '11.2'  ,  //高报温度
                  "L_temp"            :  '33.5'  ,  //低报温度
                  "point_temp"        :  '2'     ,  //点温差
                  "humi"              :  '7.03%' ,  //湿度
                  "power_status"      :  '是'    ,  //断电报警（0）


                  "temp_in1_alarm"    :  'N'  ,  //前区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in2_alarm"    :  'N'  ,  //中区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in3_alarm"    :  'N'  ,  //后区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_avg_alarm"    :  'N'  ,  //平均温度：高报警（H）,正常（N）,低报警（L)
                  "point_temp_alarm"  :  'N'  ,  //点温差：报警（H）
                 },{
                  "houseName"         :  '002'   ,   //栋舍名称
                  "dayAge"            :  '18'    ,   //日龄
                  "out_temp"          :  '10℃'   ,   //室外温度
                  "temp1"             :  '2.6℃'  ,   //前区温度
                  "temp2"             :  '5.7℃'  ,   //中区温度
                  "temp3"             :  '7.8℃'  ,   //后区温度
                  "tar_temp"          :  '8'     ,   //目标温度
                  "avg_temp"          :  '5.7'   ,   //平均温度
                  "H_temp"            :  '11.2'  ,   //高报温度
                  "L_temp"            :  '33.5'  ,   //低报温度
                  "point_temp"        :  '2'     ,   //点温差
                  "humi"              :  '7.03%' ,   //湿度
                  "power_status"      :  '是'    ,   //断电报警（0）


                  "temp_in1_alarm"    :  'N'  ,  //前区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in2_alarm"    :  'N'  ,  //中区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_in3_alarm"    :  'N'  ,  //后区温度：高报警（H）,正常（N）,低报警（L）
                  "temp_avg_alarm"    :  'N'  ,  //平均温度：高报警（H）,正常（N）,低报警（L)
                  "point_temp_alarm"  :  'N'  ,  //点温差：报警（H）
                 }]       
  },*/
  /*'alarmStatisticsData'  :  {
          "houseData":[{
                "houseName"    :  '002'                 ,  //栋舍名称
                "dayAge"       :  '12'                  ,  //日龄
                "avg"          :  {'H':'23','L':'14'}   ,  //平均
                "temp1"        :  {'H':'15','L':'19'}   ,  //前区
                "temp2"        :  {'H':'19','L':'35'}   ,  //中区
                "temp3"        :  {'H':'29','L':'81'}   ,  //后区
                "point_temp"   :  '12'                  ,  //点温差
                "power"        :  '10'                  ,  //断电报警（0）
          },{
                "houseName"    :  '009'                 ,  //栋舍名称
                "dayAge"       :  '22'                  ,  //日龄
                "avg"          :  {'H':'123','L':'4'}   ,  //平均
                "temp1"        :  {'H':'15','L':'9'}    ,  //前区
                "temp2"        :  {'H':'129','L':'15'}  ,  //中区
                "temp3"        :  {'H':'291','L':'81'}  ,  //后区
                "point_temp"   :  '112'                 ,  //点温差
                "power"        :  '110'                 ,  //断电报警（0）
          }]
  },*/
  /*'alarmLogData'  :  {
          "houseName"    :  '009'                 ,  //栋舍名称
          "dayAge"       :  '22'                  ,  //日龄
          "warningData"  :[{
                  "tableDayAge"  :  '5'  ,
                  "tableTime"    :  '10:28'  ,
                  "tableType"    :  '点温差报警'  ,
                  "tableTarget"  :  '29'  ,
                  "tableActual"  :  '33'  ,
                  "tableState"   :  '完'  
          }]
  }*/

};

var API_Host = '';

var Sparraw = function(){

  var API_Lock = false;
  
  /* To be inited or changed in ctrl */
  var BackManner    = null;

  var $ionicNavBarDelegate = null;
  var $ionicLoading = null;
  var $http         = null;
  var $state        = null;
  var $scope        = null;
  var $menusScope   = null;
  var $timeout      = null;
  /* End */

  var backward = function(){
    switch(BackManner){
      case 'exit' :navigator.app.exitApp();break;
      case 'xxxx' :navigator.app.backHistory();break;
      case 'back' :$ionicNavBarDelegate.back();break;
      case 'wait' :$state.go
      ('menus.job-main');break;
      default     :break;
    }
  }

  var isOnline = function(){
      return isOnLine();
  };
  //var myLogger = function(i){return console.log(JSON.stringify(i));};
  var myLogger = function(){for(var i=0;i<arguments.length;i++){console.log(JSON.stringify(arguments[i]));}};

  var LocCache = function(){
    var data = {};
    var conn = {};
    conn.save = function(key, val){
      try{
        key = ('&'==key.substring(0,1)) ? key : '~'+key;
        data[key] = {'ttl' : Date.now(), 'val' : val};
        localStorage[key] = JSON.stringify(data[key]);
        return data[key];
      }catch(e){
        return false;
      }
    }
    conn.load = function(key, ttl){
      try{
        key = ('&'==key.substring(0,1)) ? key : '~'+key;
        data[key] = JSON.parse(localStorage[key]);
        return (data[key] && (data[key].ttl > Date.now() - (ttl || 60*60*24*365)*1000)) ? data[key].val : false;
      }catch(e){
        return false;
      }
    }
    conn.clear = function(prefix){
      prefix = prefix || '~';
      Object.keys(localStorage).forEach(function(key){if(key.substring(0,1)==prefix){localStorage.removeItem(key);}});
    }
    return conn;
  }();

  var myNotice = function(msg, timeout, prev, post){
    $ionicLoading.show({template:msg});
    $timeout(function(){prev && prev();$ionicLoading.hide();post && post();}, timeout || 1500);
    return false;
  }

  var myRemote = function(target, params, done, fail, timeout, prev, post){
    if(!isOnline()){
      // return myLogger('Connection.NONE');
    }
    var lock = target;  //var lock = API_Host+target + ":" + JSON.stringify(params);
    if(API_Lock==lock){
      return myLogger('Http Locked:'+API_Lock);
    }
    API_Lock = lock;
    console.log("请求参数："+JSON.stringify(params));

    console.log("请求地址："+target);
    if(target == 'null'){
      API_Lock = false;
      done && done('null');
    }else{
      //开始  needAlarm.action
      if (target == "envCtrl/needAlarm.action") {//报警查询以及天气查询不显示弹窗
        
      }else{
        $ionicLoading.show();
      };


      if (!timeout) {
        timeout = 20000;
      }

      //sys/alarm/saveSettingBatch.action
      
      /*$http.post(API_Host+target, JSON.stringify(params), {'timeout' : 20000
      }).success(function(data){*/
      $http.post(API_Host+target, JSON.stringify(params), {'timeout' : timeout
      }).success(function(data){
        //结束
        if (target == "envCtrl/needAlarm.action") {
          
        }else{
          $ionicLoading.hide();
        };

        API_Lock = false;
        if(data && data.ResponseStatus == 1){
          console.log("返回成功："+JSON.stringify(data));
          done && done(data);
        }else{
          console.log("返回失败："+JSON.stringify(data));
          $ionicLoading.hide();
          fail ? fail(data) : myNotice(data ? JSON.stringify(data) : '发生错误');
        }
      }).error(function(data){
        //结束
        if (target == "envCtrl/needAlarm.action") {
          
        }else{
          $ionicLoading.hide();
        };       

        console.log("网络错误："+JSON.stringify(data));
        API_Lock = false;
        $ionicLoading.hide();
        fail ? fail(data) : myNotice(data ? JSON.stringify(data) : '网络错误');
      });
    }


  /*  
*/
    //console.log("URL:" + API_Host+target);
    //console.log("data:" + JSON.stringify(params));

    

  }

  var ajaxPost = function(target, params, done, fail,timeout){
    myRemote(target, {'id_spa' : sparraw_user.profile.id_spa, 'secret' : sparraw_user.profile.secret,'params' : params}, done, fail,timeout);
  }

  var setBackManner     = function(type){BackManner = type;}

  var setMyIonicNavBarDelegate = function(obj){$ionicNavBarDelegate = obj;}
  var setMyIonicLoading = function(obj){$ionicLoading = obj;}
  var setMyHttp         = function(obj){$http         = obj;}
  var setMyTimeout      = function(obj){$timeout      = obj;}
  var setMenusScope     = function(obj){$menusScope   = obj;}

  var intoMyController  = function(scope, state){
    $scope = scope;
    $state = state;
    $scope.myConfig = myConfig;
    $scope.tempVar  = tempVar;
    $scope.back = function(){backward();};
    $scope.$on("$destroy", function(){exitMyController();});
  }
  var exitMyController  = function(){
    $scope = null;
    $state = null;
  }

  var sparraw = {}

 



  //判断农场、栋舍、员工是否有信息(该方法针对老板)
  var getInfoStatus = function(ionicPopup,state,page){
    if (sparraw_user.userinfo.role == 1) {
        if (!sparraw_user.farminfo || sparraw_user.farminfo == 0) {
        var confirmPopup = ionicPopup.confirm({
                 title: '提醒',
                 template: '使用此功能前,请先创建农场信息。',
                 buttons: [
                   { text: '取消',
                     onTap: function() {
                      console.log("留在本页");
                     }
                  },
                   { text: '确定',
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
               buttons: [
                   { text: '取消',
                     onTap: function() {
                      console.log("留在本页");
                     }
                  },
                   { text: '确定',
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
               buttons: [
                   { text: '取消',
                     onTap: function() {
                      console.log("留在本页");
                     }
                  },
                   { text: '确定',
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
    }else{
        state.go(page);
    };

  }



/*************线性图表数据****************/
  /*
  xData       //x轴数据                                数据样式 = ['1','2','3','4','5'];
  yData       //y轴数据（总数据，包含几条以及所有点的数据）  数据样式:testData = [
                                                                ['10','20','30','40','50'],
                                                                ['50','40','30','20','10'],
                                                                ['60','54','32','12','32']
                                                              ];
  yName       //y轴各条数据的名字                        数据样式:testData = ["数据一","数据二","数据三"];
  yColor      //y轴各条数据的颜色                        数据样式:testData = ['red','blue','#EEEE00'];
  hiddenPara  //需要隐藏的线                            数据样式:testData = ["数据一", false , "数据二", false];
  clickFun    //获取被点击的参数
  yUnit       //Y轴计量单位       
  yLeftRange Y轴左边值区间,类型为数组，包含两个值，最小值，最大值 demo:[0,100]
  yRightRange Y轴右边值区间,类型为数组，包含两个值，最小值，最大值 demo : [[0,100]]
  */
  var getLineChart = function(xShaftSumData,yShaftSumData,yName,yColor,hiddenPara,yUnit,yUnit1,clickFun,yLeftRange,yRightRange){

        var myChart ;
        var option ;

        var xData ;
        var yData;

        var tTitleName;
        var tLegend;
        var yName;
        var serialsName;
        var allYData = [];
        var hiddenData;
        var json = {};
        var hiddenPara;
        var yUnit;
        var yUnit1;
		
		var ylMin,ylMax,yRMax,yRMin;
		if(yLeftRange != undefined){
			if(yLeftRange[0] != undefined){
				ylMin = yLeftRange[0];
			}
			if(yLeftRange[1] != undefined){
				ylMax = yLeftRange[1];
			}
		}
		if(yRightRange != undefined){
			if(yRightRange[0] != undefined){
				yRMin = yRightRange[0];
			}
			if(yRightRange[1] != undefined){
				yRMax = yRightRange[1];
			}
		}




        xData = xShaftSumData;//X轴数据
        yData = yShaftSumData;//Y轴数据


        

        tTitleName = "曲线图";
        tLegend = yName;//Y轴名字


        for (var i = 0; i < yName.length; i++) {
          serialsName = yName[i];
          if (yName[i] == "标准") {
          allYData[i] = {
                  name:yName[i],
                  type:'bar',
                  data:yData[i],
                  itemStyle: {
                      normal: {
                          color:"#E3E3E3",
                      }
                  }
            }
          }else{
            allYData[i] = {
                  name:yName[i],
                  type:'line',
                  yAxisIndex: 0,
                  smooth:true,//是否折线
                  symbolSize:0,//点得大小
                  data:yData[i],
                    itemStyle: {
                    normal: {
                        lineStyle: {
                          color:yColor[i],
                          width:1
                        }
                    }
                }
          }
          }

          
        };
        

        /*for (var i = 0; i < yName.length; i++) {
          serialsName = yName[i];

          allYData[i] = {
                  name:yName[i],
                  type:'line',
                  yAxisIndex: 0,
                  smooth:true,//是否折线
                  symbolSize:0,//点得大小
                  data:yData[i],
                    itemStyle: {
                    normal: {
                        lineStyle: {
                          color:yColor[i],
                          width:1
                        }
                    }
                }
          }
        };*/
        
        //隐藏哪些数据
        for (var i=0;i<hiddenPara.length;i+=2) {
            json[hiddenPara[i]] = hiddenPara[i + 1];

        }
        hiddenData = {
              data:tLegend,
              selected:json
              
        };


        require.config({
            paths: {
                echarts: 'js/echarts-2.2.7'
            }
        });
        //显示多少（什么）数据
        require(
            [
                'echarts',
                'echarts/chart/line',
                'echarts/chart/bar'
            ],
            function (ec) {
                myChart = ec.init(document.getElementById('main'));
                option = {
              tooltip : {
                          trigger: 'axis',
                          textStyle:{
                            fontSize:13
                        },
                          backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色

                          formatter: clickFun
                      },
                      legend: hiddenData,
                      /*legend:{
                        data:tLegend,
                        selected: {
                            "室外温度" :false

                          
                        }
                      },*/
                      grid://表对应上下左右的大小
                      {
                          x:60,
                          y:30,
                          x2:20,
                          y2:30

                      },
                      xAxis : [
                          {

                              type : 'category',
                              data : xData,
                              nameLocation:'start',
                              show :true,//是否显示x轴
                              axisTick : true,
                              axisLabel:{
                                //margin:10 //文字与x轴的距离
                              },
                              splitLine:true,
                              axisLine:{
                                lineStyle:{//x轴风格
                                  color: '#000',
                                  width:0.5
                                }
                              }
                          }
                      ],
                      yAxis : [
                          {   name:yUnit1,
                             
                              type : 'value',
                              nameTextStyle:{
                                fontSize:13
                              },
                              axisLabel : {
                                    formatter: '{value}' + yUnit//左边的数据
                                },
                              scale: true,
                              axisLine:{
                                lineStyle:{
                                  color: '#000',
                                  width:1
                                }
                              },
							  min:ylMin,
							  max:ylMax
                          },
                          {
                              type : 'value',
                              nameTextStyle:{
                                fontSize:16
                              },
                              axisLabel : {
                                    show:false,//是否显示
                                    formatter: '{value}'//右边的数据
                                },
                              splitLine : false,
                              scale: true,
                              axisLine:{
                                lineStyle:{
                                  color: '#fff',
                                  width:1
                                }
                              },
							  min:yRMin,
							  max:yRMax
                          }
                      ],
                      series : allYData
                  };


                /*myChart.on('click',clickFun);
                myChart.on(option);*/


                myChart.setOption(option);
                // window.onresize = myChart.resize;
                window.onresize = function(){};
            }
        );
  }
/*****************************/



/************获取服务器最新数据**********/

 var getLatestData = function(state,nextPage){

  

    /*if (sparraw_user.profile.account == undefined && sparraw_user.profile.password == undefined  ) {

    }else{
      
    };
    console.log(sparraw_user);
    console.log(params);*/
    setDeviceImei();
    var params = {
          "userCode"  :  sparraw_user.profile.account,
          "pw"        :  sparraw_user.profile.password,
          "AndroidImei": ANDROID_IMEI,
          "uuid":UUID,
          "model":MODELNAME,
          "sysVersion":VERSION,
          "platForm":PLATFORM
      };

    console.log(params);

    Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
        if (data.ResponseDetail.LoginResult == 'Success') {

          sparraw_user.userinfo   = data.ResponseDetail.userinfo;
          sparraw_user.farminfo   = data.ResponseDetail.farminfo;
          sparraw_user.houseinfos = data.ResponseDetail.houseinfos;
          sparraw_user.userinfos  = data.ResponseDetail.userinfos;
          sparraw_user.Authority  = data.ResponseDetail.Authority;
          sparraw_user.profile = {
                        'id_spa':data.ResponseDetail.userinfo.id,
                        'secret':'mtc_secret',
                        'user_State': false ,
                        'account':sparraw_user.profile.account,
                        'password':sparraw_user.profile.password
                      } ;
          persistentData.dataEntryReceiveHouse = "";
       }else{
          Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
       };

       if (state == undefined && nextPage == undefined) {

       }else{
        state.go(nextPage);
       };
    });

 }











/*****************************/



  //提醒建设中
  var pointDevelop = function() {
    app_alert('模块正在建设中...','提示');
    return; 
  };

  //获取当前年月日
  var getNowFormatDate = function(){
      var date = new Date();
      var seperator1 = "-";
      var seperator2 = ":";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
          month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate;
      TempDate = currentdate;
      return currentdate;
  };



/*
*   以下程序为报警之后的处理
*
*/
  var tskId = null;
  var tLogId = 0;
  var tNotificationID = null;
  var alarmStatus = false;

  //获取当前年月日
  var beginAlarmTask = function(roleId){
      var isAndroid = navigator.userAgent.indexOf('Android') > -1; //

      if(alarmStatus){
          console.log('警报任务已经开启');
      }else{
          app_wakeLockDim();
          console.log("用户角色:"+roleId); 
          var postAlarmReq = function(){
              tLogId ++ ;
              var params = {
                     "operation":"needAlarm",
                      "uuid":isAndroid?device.uuid:'null',
                      "model":isAndroid?device.model:'null',
                      "version":isAndroid?device.version:'null',
                      "platform":isAndroid?device.platform:'null' 
              };
              ajaxPost('envCtrl/needAlarm.action', params, function(data){
                  if (data.ResponseDetail.AlarmStatus == 'Y') {
                      sparraw_user.needAlarm = "Y";
                      // 推送消息
                      APP_Notification.cancleNotification(tNotificationID);
                      tNotificationID = APP_Notification.addOneNotification("alarm","智慧鸡场警报","环控数据异常，请点击查看");
                      
                          // 播放音乐
                          var src = "sounds/4611.wav";
                          APP_Media.stopAudio();
                          APP_Media.playAudio(getMediaURL(src));  
                      
                      console.log("已经报警:"+tLogId); 
                   }else {
                      sparraw_user.needAlarm = "N";
                      if(tNotificationID != null){
                         APP_Notification.cancleNotification(tNotificationID);
                      }
                      console.log("数据正常:"+tLogId); 
                   };
               });
          };

          postAlarmReq();
          tskId = setInterval(postAlarmReq,30000);
          alarmStatus = true;
      }
      console.log("任务开始:"+tskId);
  };
  var clearTask = function(){
    if(tskId){
        APP_Notification.cancleNotification(tNotificationID);
        console.log("已经解除:"+tskId);
        // alert("已经解除:"+tskId);         
        clearInterval(tskId);
    }
    app_releaseWakeLock();
    alarmStatus = false;
  }

  sparraw.backward = backward;
  sparraw.isOnline = isOnline;
  sparraw.myLogger = myLogger;
  sparraw.LocCache = LocCache;
  sparraw.myNotice = myNotice;
  sparraw.myRemote = myRemote;
  sparraw.ajaxPost = ajaxPost;


  sparraw.getInfoStatus     =  getInfoStatus;
  sparraw.getNowFormatDate  =  getNowFormatDate;
  //sparraw.getLineChart      =  getLineChart;
  //sparraw.pointDevelop      =  pointDevelop;

  sparraw.setBackManner  = setBackManner;
  
  sparraw.setMyIonicNavBarDelegate = setMyIonicNavBarDelegate;
  sparraw.setMyIonicLoading        = setMyIonicLoading;

  sparraw.setMyHttp        = setMyHttp;
  sparraw.setMyTimeout     = setMyTimeout;
  sparraw.setMenusScope    = setMenusScope;

  sparraw.intoMyController = intoMyController;

  sparraw.beginAlarmTask = beginAlarmTask;
  sparraw.clearTask = clearTask;
  sparraw.getLatestData = getLatestData;





  return sparraw;
}();



var sparraw_user = 
{                                             //user_State用来判断是否注册完成，true注册中，false注册完成
'profile':{'id_spa':0,'secret':'mtc_secret','user_State': true ,'account':'','password':''},

'weather':{
    "KeyInfo":{
        "WeatherCode1"    :  ""    ,//天气地址Code1
        "WeatherCode2"    :  ""    ,//天气地址Code2
        "WeatherCode3"    :  ""    ,//天气地址Code3
        "WeatherName1"    :  ""    ,//天气地址Name1
        "WeatherName2"    :  ""    ,//天气地址Name2
        "WeatherName3"    :  ""    ,//天气地址Name3
        "WeatherDate"     :  ""    // 天气预报的日期
    },
    "weatherinfo":[{
        "day_temp"      :  "-"  ,//白天温度
        "night_temp"    :  "-"  ,//晚上温度
        "day_desc"      :  ""  ,//白天天气
        "night_desc"    :  ""  ,//晚上天气
        "day_wind"      :  ""  ,//白天风向
        "night_desc"    :  ""  ,//晚上风向
        "day_speed"     :  ""  ,//白天风速
        "night_speed"   :  ""  ,//晚上风速
        "day_desc_png"  :  "" //图标
    },{
        "day_temp"      :  "-"  ,//白天温度
        "night_temp"    :  "-"  ,//晚上温度
        "day_desc"      :  ""  ,//白天天气
        "night_desc"    :  ""  ,//晚上天气
        "day_wind"      :  ""  ,//白天风向
        "night_desc"    :  ""  ,//晚上风向
        "day_speed"     :  ""  ,//白天风速
        "night_speed"   :  ""  ,//晚上风速
        "day_desc_png"  :  "" //图标
    },{
        "day_temp"      :  "-"  ,//白天温度
        "night_temp"    :  "-"  ,//晚上温度
        "day_desc"      :  ""  ,//白天天气
        "night_desc"    :  ""  ,//晚上天气
        "day_wind"      :  ""  ,//白天风向
        "night_desc"    :  ""  ,//晚上风向
        "day_speed"     :  ""  ,//白天风速
        "night_speed"   :  ""  ,//晚上风速
        "day_desc_png"  :  "" //图标
    }]},

"userinfo":{
          "id":"",
          "name": "",
          "role":"",
          "tele": "",
          "houses":[{
                "HouseId":'',
                "HouseName":"",
                "HouseBreedBatchId":'',
                "HouseBreedStatus":''
              }]
          },
"houseinfos":[{
           "id":"",
           "houseName":"",
           "h_length":"",
           "h_width":"",
           "h_height":"",
           "feedarea":"",
           "mtc_device_id":""
          }],
 "farminfo":{
           "id":"",
           "name":"",
           "address1":"", 
           "address2":"",
           "address3":"",
           "address4":"",
           "address5":"",
           "feedtype":"",
           "feedBreeds":"",
           "businessModle":"",
           "cageInfo_layer":"",
           "cageInfo_row":"",
           "corporation":"",
           "house_length":"",
           "house_width":"",
           "house_height":"",
           "feedarea":"",
           "house_Maxid":0,
           "farmBreedBatchId":0,
           "farmBreedBatchCode":'',
           "farmBreedStatus":""
          },
"userinfos":[{
        "id":"",
        "name": "",
        "tele": "",
        "role":"",
        "houses":['','']
        }],
"LoginResult":"",
 "Authority": {
    "HouseBreed": "",
    "basicInfo": "",
    "DailyInput": "",//生产日报
    "AlarmSetting": "",
    "role": 0,
    "MonitorDeal": "",
    "FarmBreed": "",//批次管理
    "TaskSetting": "",
    "TaskDeal": ""
}
,
"needAlarm":"N"
};



function userLogout(){
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
    Sparraw.clearTask();
}
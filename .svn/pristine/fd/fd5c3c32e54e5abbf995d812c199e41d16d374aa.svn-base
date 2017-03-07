// Ionic Starter App

angular.module('myApp', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav','ui.grid.validate','ui.grid.grouping','ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize',
         'myApp.controllers','myApp.services','ionic-datepicker',
         'myApp.UseRegistered','myApp.farmRegistered','myApp.BuildingTable','myApp.updateBuildingInfo','myApp.Addbuilding',
         'myApp.EmployeesTable','myApp.updateEmployeesInfo','myApp.AddEmployees','myApp.passwordModify','myApp.functionIntroduce','myApp.tips','myApp.contactUs','myApp.about','myApp.home','myApp.envMonitoring','myApp.alarmSettings','myApp.voiceSettings','myApp.tempChart','myApp.reportTempHumi','myApp.alarmLogDelay','myApp.alarmStatistics','myApp.alarmLog','myApp.infoSafeguard','myApp.chickenAssistList','myApp.apparentTempCalc','myApp.chickenWechat','myApp.simulateCalc','myApp.comparisonRes','myApp.prodPerfStanTable','myApp.fixedStandard','myApp.myStandard','myApp.setStandard','myApp.dataAnalyseTable','myApp.cumuCullDeathCurve','myApp.dayCullDeathCurve','myApp.dayFoodCurve','myApp.dayWaterCurve','myApp.weekWeight','myApp.tempDiffCurve','myApp.dateWeight','myApp.productionSumReport','myApp.prodReco','myApp.dailyDay','myApp.dailyCumu','myApp.collection','myApp.newBatchManage','myApp.docPlaceAffirm','myApp.breedAffirm','myApp.batchClear','myApp.benefitsReport','myApp.costReport','myApp.priceClear','myApp.newProfitReport','myApp.landingProtocol','myApp.landingPage','myApp.forgotPassword','myApp.disclaimer','myApp.taskRemind','myApp.taskSet','myApp.taskTable','myApp.addTask','myApp.updateTask','myApp.dailyTable','myApp.weekly','myApp.userList','myApp.ventCompute','myApp.ventConclusion'
         ],function($httpProvider) {
  // Use x-www-form-urlencoded Content-Type
  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
 
  /**
   * The workhorse; converts an object to x-www-form-urlencoded serialization.
   * @param {Object} obj
   * @return {String}
   */ 
  var param = function(obj) {
    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
      
    for(name in obj) {
      value = obj[name];
        
      if(value instanceof Array) {
        for(i=0; i<value.length; ++i) {
          subValue = value[i];
          fullSubName = name + '[' + i + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value instanceof Object) {
        for(subName in value) {
          subValue = value[subName];
          fullSubName = name + '[' + subName + ']';
          innerObj = {};
          innerObj[fullSubName] = subValue;
          query += param(innerObj) + '&';
        }
      }
      else if(value !== undefined && value !== null)
        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
    }
      
    return query.length ? query.substr(0, query.length - 1) : query;
  };
 
  // Override $http service's default transformRequest
  $httpProvider.defaults.transformRequest = [function(data) {
    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
  }];
})



/*.run(function($ionicPlatform,$rootScope) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
*/
.run(['$ionicPlatform','$rootScope', '$location', '$timeout', '$ionicHistory','$cordovaToast','$state', 
    function ($ionicPlatform,$rootScope, $location, $timeout, $ionicHistory,$cordovaToast,$state) {
    $ionicPlatform.ready(function () {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
        //cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        cordova.plugins.Keyboard.hideKeyboardAccessoryBar(false);
      }
      if (window.StatusBar) {
        // org.apache.cordova.statusbar required
        StatusBar.styleLightContent();
      }
      // 设置 设备的 Common_MOBILE_IMEI、Common_MOBILE_UUID、Common_MOBILE_MODELNAME、Common_MOBILE_VERSION、Common_MOBILE_PLATFORM
      Common_setDeviceImei();

      if (Common_judgeDevice()=="iphone") {
        cordova.plugins.Keyboard.disableScroll(true);
      }else{

      };
      // 保持竖屏
      app_lockOrientation('portrait');

      Common_initJpushUI();
    });

    //主页面显示退出提示框
    $ionicPlatform.registerBackButtonAction(function (e) {
        //判断处于哪个页面时双击退出
        if ($location.path() == '/homeurl' ) {
              app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        biz_common_userLogout();
                        $state.go("landingPage");
                   }
              });
        }else if ($location.path().indexOf('/landingPageurl') == 0 || $location.path() == '/landingProtocolurl') {
              // 按钮两次，退出App
              if ($rootScope.backButtonPressedOnceToExit) {
                  ionic.Platform.exitApp();
              } else {
                  $rootScope.backButtonPressedOnceToExit = true;
                  $cordovaToast.showShortCenter('再按一次退出系统');
                  setTimeout(function () {
                      $rootScope.backButtonPressedOnceToExit = false;
                  }, 1500);
              }
        }else if($location.path() == '/farmRegisteredurl' 
                      || $location.path() == '/buildingTable' 
                      || $location.path() == '/employeesTable'){
              $cordovaToast.showShortCenter('请点击左上角进行返回。');
        }else if($ionicHistory.backView()){
              if($location.path().indexOf('/report') == 0 || $location.path().indexOf('/productionDailyurl') == 0 ){
                  app_lockOrientation('portrait');
              }
              $ionicHistory.goBack();
        }else {
              // 按钮两次，退出App
              if ($rootScope.backButtonPressedOnceToExit) {
                  ionic.Platform.exitApp();
              } else {
                  $rootScope.backButtonPressedOnceToExit = true;
                  $cordovaToast.showShortCenter('再按一次退出系统');
                  setTimeout(function () {
                      $rootScope.backButtonPressedOnceToExit = false;
                  }, 1500);
              }
        }
        e.preventDefault();
        return false;
    }, 101);
}])

//cache:true-使用缓存 false-不使用缓存
.config(function($stateProvider,$urlRouterProvider,$ionicConfigProvider) {
  //禁止手势返回出现白屏
  $ionicConfigProvider.views.swipeBackEnabled(false);
  $stateProvider












































/*2016.10.27*/
/*注册模块*/
// 用户注册
.state('useRegistered', {
      url:'/useRegisteredurl',
      templateUrl: "module/basicInfo/registration/useRegistered.html",
      controller: 'UseRegisteredCtrl'
    })
// 农场注册
.state('farmRegistered', {
      url:'/farmRegisteredurl',
      cache:false,
      templateUrl: "module/basicInfo/farmInfo/farmRegistered.html",
      controller: 'farmRegisteredCtrl'
    })
// 栋舍列表
.state('buildingTable', {
      url:'/buildingTable',
      cache:false,
      templateUrl: "module/basicInfo/houseInfo/buildingTable.html",
      controller: 'BuildingTableCtrl'
    })
// 栋舍修改
.state('updateBuildingInfo', {
      url:'/buildingTable/buildingInfo/:buildingID',
      templateUrl: "module/basicInfo/houseInfo/updateBuildingInfo.html",
      controller: 'updateBuildingInfoCtrl'
    })
// 栋舍添加
.state('addbuilding', {
      url:'/addbuildingurl',
      cache:false,
      templateUrl: "module/basicInfo/houseInfo/addbuilding.html",
      controller: 'AddbuildingCtrl'
    })
// 员工列表
.state('employeesTable', {
      url:'/employeesTable',
      cache:false,
      templateUrl: "module/basicInfo/userInfo/employeesTable.html",
      controller: 'EmployeesTableCtrl'
    })
// 员工修改
.state('updateEmployeesInfo', {
      url:'/employeesTable/employeesInfo/:employeesID',
      templateUrl: "module/basicInfo/userInfo/updateEmployeesInfo.html",
      controller: 'updateEmployeesInfoCtrl'
    })
// 员工添加
.state('addEmployees', {
      url:'/addEmployeesurl',
      cache:false,
      templateUrl: "module/basicInfo/userInfo/addEmployees.html",
      controller: 'AddEmployeesCtrl'
    })

/*登陆模块*/
// 登陆协议
.state('landingProtocol', {
      url:'/landingProtocolurl',
      cache:false,
      templateUrl: "module/landing/landingProtocol/landingProtocol.html",
      controller: 'landingProtocolCtrl'
    })
// 用户登录
.state('landingPage', {
      url:'/landingPageurl/:firstTime',
      cache:false,
      templateUrl: "module/landing/landingPage/landingPage.html",
      controller: 'landingPageCtrl'
    })
// 忘记密码
.state('forgotPassword', {
      url:'/forgotPasswordurl',
      templateUrl: "module/landing/forgotPassword/forgotPassword.html",
      controller: 'forgotPasswordCtrl'
    })
// 免责声明
.state('disclaimer', {
      url:'/disclaimerurl',
      templateUrl: "module/landing/disclaimer/disclaimer.html",
      controller: 'disclaimerCtrl'
    })
/*默认用户名列表*/
.state('userList', {
      url:'/userListurl',
      cache:false,
      templateUrl: "module/landing/userList/userList.html",
      controller: 'userListCtrl'
    })

/*首页*/
.state('home', {
      url:'homeurl',
      cache:false,
      templateUrl: "module/home/home.html",
      controller: 'homeCtrl'
    })
// 补录界面
.state('collection', {
      url:'/collectionurl',
      cache:false,
      templateUrl: "module/home/collection/collection.html",
      controller: 'collectionCtrl'
})

/*信息维护模块*/
// 信息维护
.state('infoSafeguard', {
      url:'/infoSafeguard',
      cache:false,
      templateUrl: "module/basicInfo/info/infoSafeguard.html",
      controller: 'infoSafeguardCtrl'
    })
/*关于模块*/
// 关于页面
.state('about', {
      url:'/abouturl',
      cache:false,
      templateUrl: "module/about/about.html",
      controller: 'aboutCtrl'
    })
// 密码修改
.state('passwordModify', {
      url:'/passwordModifyurl',
      cache:false,
      templateUrl: "module/about/passwordModify/passwordModify.html",
      controller: 'passwordModifyCtrl'
    })
// 功能介绍
.state('functionIntroduce', {
      url:'/functionIntroduceurl',
      templateUrl: "module/about/functionIntroduce/functionIntroduce.html",
      controller: 'functionIntroduceCtrl'
    })
// 使用小贴士
.state('tips', {
      url:'/tipsurl',
      templateUrl: "module/about/tips/tips.html",
      controller: 'tipsCtrl'
    })
// 联系我们
.state('contactUs', {
      url:'/contactUsurl',
      templateUrl: "module/about/contactUs/contactUs.html",
      controller: 'contactUsCtrl'
    })
/*环控模块*/
//环境监测
.state('envMonitoring', {
      url:'/envMonitoring',
      cache:false,
      templateUrl: "module/envMonitor/envMonitoring.html",
      controller: 'envMonitoringCtrl'
    })
// 报警设置
.state('alarmSettings', {
      url:'alarmSettings',
      cache:false,
      templateUrl: "module/envMonitor/alarmSettings/alarmSettings.html",
      controller: 'alarmSettingsCtrl'
    })
// 语音设置
.state('voiceSettings', {
      url:'voiceSettings',
      cache:false,
      templateUrl: "module/envMonitor/voiceSettings/voiceSettings.html",
      controller: 'voiceSettingsCtrl'
    })
// 环控温度表
.state('tempChart', {
      url:'/report/tempChart/:receiveHouseId/:area',
      cache:false,
      templateUrl: "module/envMonitor/tempChart/tempChart.html",
      controller: 'tempChartCtrl'
    })
// 温湿度综合报表
.state('reportTempHumi', {
      url:'/report/reportTempHumiurl/:judgeEnter',
      cache:false,
      templateUrl: "module/envMonitor/reportTempHumi/reportTempHumi.html",
      controller: 'reportTempHumiCtrl'
    })
//报警延迟处理
.state('alarmLogDelay', {
      url:'/envMonitoring/alarmLogDelay/:receiveHouseId/:dayAge',
      cache:false,
      templateUrl: "module/envMonitor/alarmLogDelay/alarmLogDelay.html",
      controller: 'alarmLogDelayCtrl'
    })
// 环控报警统计
.state('alarmStatistics', {
      url:'/alarmStatistics',
      templateUrl: "module/envMonitor/alarmStatistics/alarmStatistics.html",
      cache:false,
      controller: 'alarmStatisticsCtrl'
    })
// 报警日志
.state('alarmLog', {
      url:'/alarmStatistics/alarmLog/:receiveHouseId/:buildingDayAge',
      cache:false,
      templateUrl: "module/envMonitor/alarmLog/alarmLog.html",
      controller: 'alarmLogCtrl'
    })

/*养鸡助手模块*/
//养鸡助手列表
.state('chickenAssistList', {
      url:'/chickenAssistList',
      cache:false,
      templateUrl: "module/chickenAssists/chickenAssistList.html",
      controller: 'chickenAssistListCtrl'
    })
// 体感温度计算器
.state('apparentTempCalc', {
      url:'/apparentTempCalc',
      cache:false,
      templateUrl: "module/chickenAssists/apparentTempCalc/apparentTempCalc.html",
      controller: 'apparentTempCalcCtrl'
    })
// 鸡场微信
.state('chickenWechat', {
      url:'/chickenWechat',
      cache:false,
      templateUrl: "module/chickenAssists/chickenWechat/chickenWechat.html",
      controller: 'chickenWechatCtrl'
    })
// 模拟算账
.state('simulateCalc', {
      url:'/simulateCalc',
      cache:false,
      templateUrl: "module/chickenAssists/simulateCalc/simulateCalc.html",
      controller: 'simulateCalcCtrl'
    })
//最小通风计算器
.state('ventCompute', {
      url:'/ventCompute',
      cache:false,
      templateUrl: "module/chickenAssists/ventCompute/ventCompute.html",
      controller: 'ventComputeCtrl'
    })
//最小通风计算器结果
.state('ventConclusion', {
      url:'/ventConclusion',
      cache:false,
      templateUrl: "module/chickenAssists/ventConclusion/ventConclusion.html",
      controller: 'ventConclusionCtrl'
    })

// 数据对比
.state('comparisonRes', {
      url:'/comparisonRes',
      cache:false,
      templateUrl: "module/chickenAssists/comparisonRes/comparisonRes.html",
      controller: 'comparisonResCtrl'
    })  
//生产性能标准
.state('prodPerfStanTable', {
     url:'/prodPerfStanTableurl',
     cache:false,
     templateUrl: "module/chickenAssists/prodPerfStanTable/prodPerfStanTable.html",
     controller: 'prodPerfStanTableCtrl'
})
//固定标准
.state('fixedStandard', {
     url:'/fixedStandardurl',
     cache:false,
     templateUrl: "module/chickenAssists/fixedStandard/fixedStandard.html",
     controller: 'fixedStandardCtrl'
})
//我的标准
.state('myStandard', {
     url:'/myStandard',
     cache:false,
     templateUrl: "module/basicInfo/myStandard/myStandard.html",
     controller: 'myStandardCtrl'
})
//设定标准
.state('setStandard', {
     url:'/setStandard',
     cache:false,
     templateUrl: "module/basicInfo/setStandard/setStandard.html",
     controller: 'setStandardCtrl'
})








/*任务提醒模块*/
//任务提醒
.state('taskRemind', {
      url:'/module/task/taskRemind.html',
      cache:false,
      templateUrl: "module/chickenAssists/taskRemind/taskRemind.html",
      controller: 'taskRemindCtrl'
    })
//任务设定
.state('taskSet', {
      url:'/module/task/taskSet.html',
      cache:false,
      templateUrl: "module/chickenAssists/taskSet/taskSet.html",
      controller: 'taskSetCtrl'
    })
//任务列表
.state('taskTable', {
      url:'/module/task/taskTable/:TaskType',
      cache:false,
      templateUrl: "module/chickenAssists/taskTable/taskTable.html",
      controller: 'taskTableCtrl'
    })
//新增任务
.state('addTask', {
      url:'/module/task/addTask/:TaskType',
      cache:false,
      templateUrl: "module/chickenAssists/addTask/addTask.html",
      controller: 'addTaskCtrl'
    })
//查看任务
.state('updateTask', {
      url:'/module/task/updateTask/:TaskType/:receiveTskID',
      cache:false,
      templateUrl: "module/chickenAssists/updateTask/updateTask.html",
      controller: 'updateTaskCtrl'
    })









/*数据分析模块*/
// 数据分析
.state('dataAnalyseTable', {
      url:'/dataAnalyseTable',
      cache:false,
      templateUrl: "module/dataAnalyses/dataAnalyseTable.html",
      controller: 'dataAnalyseTableCtrl'
    })
//成活率
.state('cumuCullDeathCurve', {
      url:'/report/cumuCullDeathCurveurl',
      cache:false,
      templateUrl: "module/dataAnalyses/cumuCullDeathCurve/cumuCullDeathCurve.html",
      controller: 'cumuCullDeathCurveCtrl'
    })
//日死淘率
.state('dayCullDeathCurve', {
      url:'/report/dayCullDeathCurve',
      cache:false,
      templateUrl: "module/dataAnalyses/dayCullDeathCurve/dayCullDeathCurve.html",
      controller: 'dayCullDeathCurveCtrl'
    })
// 日采食率
.state('dayFoodCurve', {
      url:'/report/dayFoodCurve',
      cache:false,
      templateUrl: "module/dataAnalyses/dayFoodCurve/dayFoodCurve.html",
      controller: 'dayFoodCurveCtrl'
    })
// 日饮水率
.state('dayWaterCurve', {
      url:'/report/dayWaterCurve',
      cache:false,
      templateUrl: "module/dataAnalyses/dayWaterCurve/dayWaterCurve.html",
      controller: 'dayWaterCurveCtrl'
    })
// 日体重
.state('dateWeight', {
      url:'/dateWeight',
      cache:false,
      templateUrl: "module/dataAnalyses/dateWeight/dateWeight.html",
      controller: 'dateWeightCtrl'
    })
// 周体重
.state('weekWeight', {
      url:'/weekWeight',
      cache:false,
      templateUrl: "module/dataAnalyses/weekWeight/weekWeight.html",
      controller: 'weekWeightCtrl'
    })
//点温差
.state('tempDiffCurve', {
      url:'/TempDiffCurve',
      cache:false,
      templateUrl: "module/dataAnalyses/tempDiffCurve/tempDiffCurve.html",
      controller: 'tempDiffCurveCtrl'
    })
// 生产指标汇总报表
.state('productionSumReport', {
      url:'/productionSumReport',
      cache:false,
      templateUrl: "module/prodReport/productionSumReport/productionSumReport.html",
      controller: 'productionSumReportCtrl'
    })

/*生产报告模块*/

//生产报告
.state('dailyTable', {
      url:'/dailyTableurl',
      cache:false,
      templateUrl: "module/prodReport/dailyTable/dailyTable.html",
      controller: 'dailyTableCtrl'
    })
// 生产记录
.state('prodReco', {
      url:'/prodRecourl',
      cache:false,
      templateUrl: "module/prodReport/prodReco/prodReco.html",
      controller: 'prodRecoCtrl'
    })
// 生产日报(当天)
.state('dailyDay', {
      url:'/dailyDayurl',
      cache:false,
      templateUrl: "module/prodReport/dailyDay/dailyDay.html",
      controller: 'dailyDayCtrl'
    })

// 生产日报(累计)
.state('dailyCumu', {
      url:'/dailyCumuurl',
      cache:false,
      templateUrl: "module/prodReport/dailyCumu/dailyCumu.html",
      controller: 'dailyCumuCtrl'
    })
// 生产周报
.state('weekly', {
      url:'/weeklyurl',
      cache:false,
      templateUrl: "module/prodReport/weekly/weekly.html",
      controller: 'weeklyCtrl'
    })


/*批次管理模块*/
// 批次管理
.state('newBatchManage', {
      url:'/newBatchManageurl',
      cache:false,
      templateUrl: "module/newBatchManages/newBatchManage.html",
      controller: 'newBatchManageCtrl'
    })
//入雏确认
.state('docPlaceAffirm', {
  url:'/docPlaceAffirm',
  cache:false,
  templateUrl: "module/newBatchManages/docPlaceAffirm/docPlaceAffirm.html",
  controller: 'docPlaceAffirmCtrl'
})
//出栏确认
.state('breedAffirm', {
  url:'/breedAffirm',
  cache:false,
  templateUrl: "module/newBatchManages/breedAffirm/breedAffirm.html",
  controller: 'breedAffirmCtrl'
})
//批次结算
.state('batchClear', {
      url:'/batchClearurl',
      cache:false,
      templateUrl: "module/newBatchManages/batchClear/batchClear.html",
      controller: 'batchClearCtrl'
    })
//盈利报告
.state('newProfitReport', {
      url:'/newProfitReporturl',
      cache:false,
      templateUrl: "module/newBatchManages/newProfitReport/newProfitReport.html",
      controller: 'newProfitReportCtrl'
    })
//效益报告
.state('benefitsReport', {
      url:'/benefitsReporturl',
      cache:false,
      templateUrl: "module/newBatchManages/benefitsReport/benefitsReport.html",
      controller: 'benefitsReportCtrl'
    })
//成本报告
.state('costReport', {
      url:'/costReporturl',
      cache:false,
      templateUrl: "module/newBatchManages/costReport/costReport.html",
      controller: 'costReportCtrl'
    })
//结算价格
.state('priceClear', {
      url:'/priceClearurl',
      cache:false,
      templateUrl: "module/newBatchManages/priceClear/priceClear.html",
      controller: 'priceClearCtrl'
    })











;
})

.config(function (ionicDatePickerProvider) {
    var datePickerObj = {
      inputDate: new Date(),
      titleLabel: null,        
      mondayFirst: false,
      weeksList: ["日", "一", "二", "三", "四", "五", "六"],
      monthsList:["01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月"],
      setLabel: '确认',
      closeLabel: '取消',
      from: new Date(2010, 1, 1),// Optional，
      to: new Date(2020, 11, 31),// Optional， 
      inputDate: new Date(),     // Optional，
      mondayFirst: false,        // Optional，
      templateType: 'popup',     // Optional，modal or popup，Default value is modal
      dateFormat: 'yyyy-MM-dd',  // Optional，Defaults to dd-MM-yyyy.
      showTodayButton: true,     // Optional，是否选中今天按钮，默认值：false.
      todayLabel: '今天',
      closeOnSelect: false,      // Optional，关闭是否选中日期，默认值：false.
      disabledDates: [],
      disableWeekdays: []        // Optional，星期几被disabled,数组形式，0(Sunday) to 6(Saturday)
    };
    ionicDatePickerProvider.configDatePicker(datePickerObj);
  })
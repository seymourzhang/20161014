// Ionic Starter App

angular.module('myApp', ['ionic','myApp.controllers','myApp.services','ionic-datepicker','ngCordova','ngTouch', 'ui.grid', 'ui.grid.pinning','ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection', 'ui.grid.resizeColumns','ui.grid.autoResize'],function($httpProvider) {
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
      }
      if (window.StatusBar) {
        // org.apache.cordova.statusbar required
        StatusBar.styleLightContent();
      }
      // 设置 设备的Imei号UUID、MODELNAME、VERSION、PLATFORM
      setDeviceImei();

      if (judgeDevice()=="iphone") {
        cordova.plugins.Keyboard.disableScroll(true);
      }else{

      };
      // 保持竖屏
      app_lockOrientation('portrait');

      initJpushUI();
    });


    
    //主页面显示退出提示框
    $ionicPlatform.registerBackButtonAction(function (e) {
        //判断处于哪个页面时双击退出
        if ($location.path() == '/homeurl' ) {
              app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        userLogout();
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
              // $cordovaToast.showShortCenter('请点击左上角进行返回。');
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
// 用户注册
.state('useRegistered', {
      url:'/useRegisteredurl',
      templateUrl: "module/registered/useRegistered.html",
      controller: 'UseRegisteredCtrl'
    })
// 农场注册
.state('farmRegistered', {
      url:'/farmRegisteredurl',
      cache:false,
      templateUrl: "module/registered/farmRegistered.html",
      controller: 'FarmRegisteredCtrl'
    })
// 栋舍列表
.state('buildingTable', {
      url:'/buildingTable',
      cache:false,
      templateUrl: "module/registered/buildingTable.html",
      controller: 'BuildingTableCtrl'
    })
// 栋舍修改
.state('updateBuildingInfo', {
      url:'/buildingTable/buildingInfo/:buildingID',
      templateUrl: "module/registered/updateBuildingInfo.html",
      controller: 'updateBuildingInfoCtrl'
    })
// 栋舍添加
.state('addbuilding', {
      url:'/addbuildingurl',
      cache:false,
      templateUrl: "module/registered/addbuilding.html",
      controller: 'AddbuildingCtrl'
    })
// 员工列表
.state('employeesTable', {
      url:'/employeesTable',
      cache:false,
      templateUrl: "module/registered/employeesTable.html",
      controller: 'EmployeesTableCtrl'
    })
// 员工修改
.state('updateEmployeesInfo', {
      url:'/employeesTable/employeesInfo/:employeesID',
      templateUrl: "module/registered/updateEmployeesInfo.html",
      controller: 'updateEmployeesInfoCtrl'
    })
// 员工添加
.state('addEmployees', {
      url:'/addEmployeesurl',
      cache:false,
      templateUrl: "module/registered/addEmployees.html",
      controller: 'AddEmployeesCtrl'
    })

// 登陆协议
.state('landingProtocol', {
      url:'/landingProtocolurl',
      cache:false,
      templateUrl: "module/landing/landingProtocol.html",
      controller: 'landingProtocolCtrl'
    })
// 用户登录
.state('landingPage', {
      url:'/landingPageurl/:firstTime',
      cache:false,
      templateUrl: "module/landing/landingPage.html",
      controller: 'landingPageCtrl'
    })
// 忘记密码
.state('forgotPassword', {
      url:'/forgotPasswordurl',
      templateUrl: "module/landing/forgotPassword.html",
      controller: 'forgotPasswordCtrl'
    })
// 免责声明
.state('disclaimer', {
      url:'/disclaimerurl',
      templateUrl: "module/landing/disclaimer.html",
      controller: 'disclaimerCtrl'
    })



// 主页面
.state('home', {
      url:'homeurl',
      cache:false,
      templateUrl: "module/home/home.html",
      controller: 'homeCtrl'
    })
// 信息维护
.state('infoSafeguard', {
      url:'/infoSafeguard',
      cache:false,
      templateUrl: "module/home/infoSafeguard.html",
      controller: 'infoSafeguardCtrl'
    })
// 个人信息修改
.state('modifyUserInfo', {
      url:'/modifyUserInfourl',
      cache:false,
      templateUrl: "module/home/modifyUserInfo.html",
      controller: 'modifyUserInfoCtrl'
    })
// 关于页面
.state('about', {
      url:'/abouturl',
      templateUrl: "module/home/about.html",
      controller: 'aboutCtrl'
    })
//功能介绍
.state('functionIntroduce', {
      url:'/functionIntroduceurl',
      templateUrl: "module/home/functionIntroduce.html",
      controller: 'functionIntroduceCtrl'
    })
//联系我们
.state('contactUs', {
      url:'/contactUsurl',
      templateUrl: "module/home/contactUs.html",
      controller: 'contactUsCtrl'
    })
//意见反馈
.state('feedBack', {
      url:'/feedBackurl',
      templateUrl: "module/home/feedBack.html",
      controller: 'feedBackCtrl'
    })
// 权限控制
.state('accessControl', {
      url:'/accessControlurl',
      templateUrl: "module/home/accessControl.html",
      controller: 'accessControlCtrl'
    })




//盈利报告
.state('profitReport', {
      url:'/profitReporturl',
      cache:false,
      templateUrl: "module/batchManage/profitReport.html",
      controller: 'profitReportCtrl'
    })
//多批盈利查询
.state('moreBatchProfit', {
      url:'/moreBatchProfiturl',
      cache:false,
      templateUrl: "module/batchManage/moreBatchProfit.html",
      controller: 'moreBatchProfitCtrl'
    })
//多批结算查询
.state('moreBatchClearing', {
      url:'/moreBatchClearingurl',
      cache:false,
      templateUrl: "module/batchManage/moreBatchClearing.html",
      controller: 'moreBatchClearingCtrl'
    })
//生产性能标准
.state('prodPerfStanTable', {
     url:'/prodPerfStanTableurl',
     cache:false,
     templateUrl: "module/batchManage/prodPerfStanTable.html",
     controller: 'prodPerfStanTableCtrl'
})
//固定标准
.state('fixedStandard', {
     url:'/fixedStandardurl',
     cache:false,
     templateUrl: "module/batchManage/fixedStandard.html",
     controller: 'fixedStandardCtrl'
})
//我的标准
.state('myStandard', {
     url:'/myStandard',
     cache:false,
     templateUrl: "module/batchManage/myStandard.html",
     controller: 'myStandardCtrl'
})
//设定标准
.state('setStandard', {
     url:'/setStandard',
     cache:false,
     templateUrl: "module/batchManage/setStandard.html",
     controller: 'setStandardCtrl'
})

//日报管理
.state('dailyTable', {
      url:'/dailyTableurl',
      templateUrl: "module/dataInput/dailyTable.html",
      controller: 'dailyTableCtrl'
    })

// 日报统计
.state('dailyStatistical', {
      url:'/dailyStatisticalurl/:receiveHouseId',
      cache:false,
      templateUrl: "module/dataInput/dailyStatistical.html",
      controller: 'dailyStatisticalCtrl'
    })

// 生产日报(当天)
.state('dailyDay', {
      url:'/dailyDayurl',
      cache:false,
      templateUrl: "module/dataInput/newPage/dailyDay.html",
      controller: 'dailyDayCtrl'
    })

// 生产日报(累计)
.state('dailyCumu', {
      url:'/dailyCumuurl',
      cache:false,
      templateUrl: "module/dataInput/newPage/dailyCumu.html",
      controller: 'dailyCumuCtrl'
    })
// 生产周报
.state('weekly', {
      url:'/weeklyurl',
      cache:false,
      templateUrl: "module/dataInput/newPage/weekly.html",
      controller: 'weeklyCtrl'
    })

// 入雏管理
.state('docPlace', {
      url:'/dailyReporturl/docPlaceurl/:HouseId',
      cache:false,
      templateUrl: "module/dataInput/docPlace.html",
      controller: 'docPlaceCtrl'
    })
// 环控管理表
.state('envMonitoringTable', {
      url:'/envMonitoringTableurl',
      templateUrl: "module/envCtrl/envMonitoringTable.html",
      controller: 'envMonitoringTableCtrl'
    })
// 环控监测
.state('envMonitoring', {
      url:'/envMonitoring',
      cache:false,
      templateUrl: "module/envCtrl/envMonitoring.html",
      controller: 'envMonitoringCtrl'
    })
//报警延迟处理
.state('alarmLogDelay', {
      url:'/envMonitoring/alarmLogDelay/:receiveHouseId/:dayAge',
      cache:false,
      templateUrl: "module/envCtrl/alarmLogDelay.html",
      controller: 'alarmLogDelayCtrl'
    })
// 环控温度表
.state('tempChart', {
      url:'/report/tempChart/:receiveHouseId/:area',
      cache:false,
      templateUrl: "module/report/tempChart.html",
      controller: 'tempChartCtrl'
    })

// 环控报警统计
.state('alarmStatistics', {
      url:'/alarmStatistics',
      templateUrl: "module/envCtrl/alarmStatistics.html",
      cache:false,
      controller: 'alarmStatisticsCtrl'
    })
// 报警日志
.state('alarmLog', {
      url:'/alarmStatistics/alarmLog/:receiveHouseId/:buildingDayAge',
      cache:false,
      templateUrl: "module/envCtrl/alarmLog.html",
      controller: 'alarmLogCtrl'
    })
// 报警设置
.state('alarmSettings', {
      url:'alarmSettings',
      cache:false,
      templateUrl: "module/envCtrl/alarmSettings.html",
      controller: 'alarmSettingsCtrl'
    })
// 语音设置
.state('voiceSettings', {
      url:'voiceSettings',
      cache:false,
      templateUrl: "module/envCtrl/voiceSettings.html",
      controller: 'voiceSettingsCtrl'
    })
// 数据分析
.state('dataAnalyseTable', {
      url:'/dataAnalyseTable',
      cache:false,
      templateUrl: "module/dataAnalyse/dataAnalyseTable.html",
      controller: 'dataAnalyseTableCtrl'
    })
// 温度K线图
.state('tempKMap', {
      url:'report/tempKMap',
      templateUrl: "module/dataAnalyse/tempKMap.html",
      controller: 'tempKMapCtrl'
    })

// 温湿度综合报表
.state('reportTempHumi', {
      url:'/report/reportTempHumiurl/:judgeEnter',
      cache:false,
      templateUrl: "module/report/reportTempHumi.html",
      controller: 'reportTempHumiCtrl'
    })


//死淘(成活率)率图
.state('cullDeathRate', {
      url:'/report/cullDeathRateurl',
      cache:false,
      templateUrl: "module/report/cullDeathRate.html",
      controller: 'cullDeathRateCtrl'
    })

// 均重和饲料转换
.state('reportWeightFeedRate', {
      url:'/report/reportWeightFeed',
      cache:false,
      templateUrl: "module/report/reportWeightFeed.html",
      controller: 'reportWeightFeedCtrl'
    })
// 日采食曲线
.state('reportDailyFeedRate', {
      url:'/report/reportDailyFeed',
      cache:false,
      templateUrl: "module/report/reportDailyFeed.html",
      controller: 'reportDailyFeedCtrl'
    })
//生产指标汇总
.state('productionQuota', {
      url:'/report/productionQuota',
      cache:false,
      templateUrl: "module/report/productionQuota.html",
      controller: 'productionQuotaCtrl'
    })


//任务提醒
.state('taskRemind', {
      url:'/module/task/taskRemind.html',
      cache:false,
      templateUrl: "module/task/taskRemind.html",
      controller: 'taskRemindCtrl'
    })
//任务设定
.state('taskSet', {
      url:'/module/task/taskSet.html',
      cache:false,
      templateUrl: "module/task/taskSet.html",
      controller: 'taskSetCtrl'
    })
//任务列表
.state('taskTable', {
      url:'/module/task/taskTable/:TaskType',
      cache:false,
      templateUrl: "module/task/taskTable.html",
      controller: 'taskTableCtrl'
    })
//新增任务
.state('addTask', {
      url:'/module/task/addTask/:TaskType',
      cache:false,
      templateUrl: "module/task/addTask.html",
      controller: 'addTaskCtrl'
    })
//查看任务
.state('updateTask', {
      url:'/module/task/updateTask/:TaskType/:receiveTskID',
      cache:false,
      templateUrl: "module/task/updateTask.html",
      controller: 'updateTaskCtrl'
    })



//测试界面
.state('test', {
      url:'/testurl',
      templateUrl: "module/test.html",
      controller: 'testCtrl'
    })
// 测试报警
.state('alarmTest', {
      url:'/alarmTesturl',
      cache:false,
      templateUrl: "module/home/alarmTest.html",
      controller: 'alarmTestCtrl'
    })
// 体感温度计算器
.state('apparentTempCalc', {
      url:'/apparentTempCalc',
      cache:false,
      templateUrl: "module/envCtrl/apparentTempCalc.html",
      controller: 'apparentTempCalcCtrl'
    })







/*-------newPage-------*/

// 批次管理
.state('newBatchManage', {
      url:'/newBatchManageurl',
      cache:false,
      templateUrl: "module/batchManage/newPage/newBatchManage.html",
      controller: 'newBatchManageCtrl'
    })
//入雏确认
.state('docPlaceAffirm', {
  url:'/docPlaceAffirm',
  cache:false,
  templateUrl: "module/batchManage/newPage/docPlaceAffirm.html",
  controller: 'docPlaceAffirmCtrl'
})
//出栏确认
.state('breedAffirm', {
  url:'/breedAffirm',
  cache:false,
  templateUrl: "module/batchManage/newPage/breedAffirm.html",
  controller: 'breedAffirmCtrl'
})
//批次结算
.state('batchClear', {
      url:'/batchClearurl',
      cache:false,
      templateUrl: "module/batchManage/newPage/batchClear.html",
      controller: 'batchClearCtrl'
    })
//价格结算
.state('priceClear', {
      url:'/priceClearurl',
      cache:false,
      templateUrl: "module/batchManage/newPage/priceClear.html",
      controller: 'priceClearCtrl'
    })
//盈利报告
.state('newProfitReport', {
      url:'/newProfitReporturl',
      cache:false,
      templateUrl: "module/batchManage/newPage/newProfitReport.html",
      controller: 'newProfitReportCtrl'
    })
//效益报告
.state('benefitsReport', {
      url:'/benefitsReporturl',
      cache:false,
      templateUrl: "module/batchManage/newPage/benefitsReport.html",
      controller: 'benefitsReportCtrl'
    })

//效益报告
.state('costReport', {
      url:'/costReporturl',
      cache:false,
      templateUrl: "module/batchManage/newPage/costReport.html",
      controller: 'costReportCtrl'
    })

// 生产记录
.state('prodReco', {
      url:'/prodRecourl',
      cache:false,
      templateUrl: "module/dataInput/newPage/prodReco.html",
      controller: 'prodRecoCtrl'
    })

// 补录界面dailyReport
.state('collection', {
      url:'/collectionurl',
      cache:false,
      templateUrl: "module/dataInput/newPage/collection.html",
      controller: 'collectionCtrl'
})








// 日采食率
.state('dayFoodCurve', {
      url:'/report/dayFoodCurve',
      cache:false,
      templateUrl: "module/report/newPage/dayFoodCurve.html",
      controller: 'dayFoodCurveCtrl'
    })

// 日饮水率
.state('dayWaterCurve', {
      url:'/report/dayWaterCurve',
      cache:false,
      templateUrl: "module/report/newPage/dayWaterCurve.html",
      controller: 'dayWaterCurveCtrl'
    })

//日死淘率
.state('dayCullDeathCurve', {
      url:'/report/dayCullDeathCurve',
      cache:false,
      templateUrl: "module/report/newPage/dayCullDeathCurve.html",
      controller: 'dayCullDeathCurveCtrl'
    })

//累计死淘率
.state('cumuCullDeathCurve', {
      url:'/report/cumuCullDeathCurveurl',
      cache:false,
      templateUrl: "module/report/newPage/cumuCullDeathCurve.html",
      controller: 'cumuCullDeathCurveCtrl'
    })
//养鸡助手列表
.state('chickenAssistList', {
      url:'/chickenAssistList',
      cache:false,
      templateUrl: "module/chickenAssist/chickenAssistList.html",
      controller: 'chickenAssistListCtrl'
    })
// 周体重
.state('weekWeight', {
      url:'/weekWeight',
      cache:false,
      templateUrl: "module/report/newPage/weekWeight.html",
      controller: 'weekWeightCtrl'
    })

// 生产指标汇总报表
.state('productionSumReport', {
      url:'/productionSumReport',
      cache:false,
      templateUrl: "module/report/newPage/productionSumReport.html",
      controller: 'productionSumReportCtrl'
    })

// 鸡场微信
.state('chickenWechat', {
      url:'/chickenWechat',
      cache:false,
      templateUrl: "module/chickenAssist/chickenWechat.html",
      controller: 'chickenWechatCtrl'
    })
// 模拟算账
.state('simulateCalc', {
      url:'/simulateCalc',
      cache:false,
      templateUrl: "module/simulateCalc/simulateCalc.html",
      controller: 'simulateCalcCtrl'
    })
// 数据对比
.state('comparisonRes', {
      url:'/comparisonRes',
      cache:false,
      templateUrl: "module/simulateCalc/comparisonRes.html",
      controller: 'comparisonResCtrl'
    })	
;

})
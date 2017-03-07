angular.module('myApp.dataAnalyseTable', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
// 数据分析
.controller("dataAnalyseTableCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();
		}
	,1000);
    

    $scope.pointDevelop = function(reportId) {
    	if(reportId == 'wsdzh'){
    		biz_common_judgeRegistInfo($ionicPopup,$state,"reportTempHumi");
    		
    	}else if(reportId == 'wdkx'){
    		
    		biz_common_judgeRegistInfo($ionicPopup,$state,"tempChart");
    	}else if(reportId == 'rstl'){
    		
    		biz_common_judgeRegistInfo($ionicPopup,$state,"cullDeathRate");
    	}else if(reportId == 'chl'){
    		
    		biz_common_judgeRegistInfo($ionicPopup,$state,"survivalRateDay");
    	}else if(reportId == 'jzsl'){
    		
    		biz_common_judgeRegistInfo($ionicPopup,$state,"reportWeightFeedRate");
    	}else if(reportId == 'rcsqx'){
    		
    		biz_common_judgeRegistInfo($ionicPopup,$state,"reportDailyFeedRate");
    	}else if(reportId == 'prodQuota'){
    		//biz_common_judgeRegistInfo($ionicPopup,$state,"productionQuota");
    		$state.go("productionQuota");
    	}else{
    		biz_common_pointDevelop();
    	}
	};




})
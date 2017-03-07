angular.module('myApp.chickenAssistList', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//养鸡助手列表
.controller("chickenAssistListCtrl",function($scope, $state,$ionicPopup,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.goApparentTempCalc = function(){
		$state.go("apparentTempCalc");
	};

	$scope.goProdPerfStanTable = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"prodPerfStanTable");
	};
	
	$scope.goChickenWechat = function(){
		$state.go("chickenWechat");
	};

	$scope.goSyncProject = function(){
		//$state.go("prodPerfStanTable");
		app_alert("历史数据达到5批次后，才能使用同行对标功能")
	};
	
	$scope.goSimulateCalc = function(){
		$state.go("simulateCalc");
	};

	$scope.goVentCompute = function(){
		$state.go("ventCompute");
	}

	$scope.goTaskRemind = function(){
		biz_common_pointDevelop();
	};
})
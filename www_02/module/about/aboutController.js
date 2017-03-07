angular.module('myApp.about', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
.controller("aboutCtrl",function($scope, $state, $http, AppData,$cordovaFileTransfer,$ionicLoading,$timeout,$cordovaFileOpener2) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.App_Version = CONFIG_App_Version;

	if (/android/.test(DeviInfo.DeviceType)){
		$scope.UpdateBtn = false;//显示	
	}else{
		$scope.UpdateBtn = true;//隐藏
	};

	$scope.dlAPK = function(){
		Common_checkForUpdate(function(){
     			Sparraw.myNotice("当前已是最新版本。");
     	});
	}
	$scope.queryVersion = function(){
		console.log("downLoad apk");
	};

	$scope.pointDevelop = function() {
		
		biz_common_pointDevelop();
		return;	
	};
})
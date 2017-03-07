 angular.module('myApp.landingProtocol', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 // 登陆协议
.controller("landingProtocolCtrl",function($scope, $state, $http, AppData) {
	$scope.goLandingPage = function(){
		$state.go("landingPage");
	}
	$scope.closeApp = function(){
		console.log("关闭app");
		var ua = navigator.userAgent.toLowerCase(); 
		if (/iphone|ipad|ipod/.test(ua)) {
			//return document.location = "objc://printLog::/";
		} else if (/android/.test(ua)) {
		 	ionic.Platform.exitApp();
		}else{
		 	Sparraw.myNotice("请在手机端点击。");
		}
	}
})
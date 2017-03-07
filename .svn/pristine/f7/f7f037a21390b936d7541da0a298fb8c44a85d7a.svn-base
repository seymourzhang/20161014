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




	$scope.setLinkView = function(){
		$scope.showUrl = 'https://mp.weixin.qq.com/s?__biz=MzAwMjU4ODMyNg==&mid=509958431&idx=1&sn=18dbc9b90212e8dd316d599acf642df3&chksm=0159d2d6362e5bc01ac1eedb54e3fbdd72c62432e0a7428fc68dd57491cad4259ae5dd68ef72&mpshare=1&scene=1&srcid=0214WUivbiXQlG5AyTO61XfL&key=e79f30ad82098b75578fa7ed2d171781d8b3135bcc3179da17388060c88ca292facad2a9a60730f75adfe99449d0d5f208d3c0f2f96753333bfe1e9d3af0881db63786f525b54df1aff8cb8f5fc031aa&ascene=0&uin=MTMzODE0OTAwMA%3D%3D&devicetype=iMac+MacBookPro11%2C3+OSX+OSX+10.12+build(16A323)&version=11020201&pass_ticket=suVzW7ZTmwfnzCXbQTHtK0gqLeeEL9SUQ83PCu%2FPnIVI%2BtuhJIwtpd1W%2B7JK%2F0Bg';
		cordova.ThemeableBrowser.open($scope.showUrl, '_blank', {
		    statusbar: {
		        color: '#3dcb64'
		    },
		    toolbar: {
		        height: 44,
		        color: '#3dcb64'
		    },
		    title: {
		        color: '#FFFFFF',
		        showPageTitle: true
		    },
		    backButton: {
		        wwwImage: 'img/newFolder/public/BackArrow.png',
		        wwwImagePressed: 'img/newFolder/public/BackArrow.png',
		        wwwImageDensity: 2,
		        align: 'left',
		        event: 'backPressed'
		    },
    		backButtonCanClose: true
		}).addEventListener('loadstart', function(event) { 
			console.log("载入开始");
			console.log(event);
		}).addEventListener('loadstop', function(event) { 
			console.log("载入结束");
			console.log(event);
		}).addEventListener('loaderror', function(event) { 
			console.log("载入错误");
		}).addEventListener('backPressed', function(e) {
			console.log("返回");
		});
	}





})
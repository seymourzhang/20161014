angular.module('myApp.home', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 主页面
.controller("homeCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout,AppData) {

	setPortrait(true,true);//竖屏
	
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.setData = function(){

		$scope.homeData = {
			"deviceNum"   :  "" ,
			"messageNum"  :  "" ,
			"HouseNum"    :  "" ,
			"FeedDays"    :  "" 
		};
		$scope.helpDiv = false;

		$scope.devicesNum = [];
		if ($scope.sparraw_user_temp.houseinfos != undefined) {
			for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
				if ($scope.sparraw_user_temp.houseinfos[i].mtc_device_id != "") {
					$scope.devicesNum.push($scope.sparraw_user_temp.houseinfos[i]);
				}
			}
		}
		

		$scope.weatherSrc1 = "img/newFolder/home/weather/05.png";
		$scope.weatherSrc2 = "img/newFolder/home/weather/05.png";
		$scope.weatherSrc3 = "img/newFolder/home/weather/05.png";
		$scope.callPoliceSrc = "img/newFolder/home/callPolice.png";



		if(Common_isIOS()){
			document.getElementById("annoRemind").style.top = 35 + 'px';
	    	document.getElementById("messageSvg").style.top = 35 + 'px';
	    	document.getElementById("aboutSvg").style.top = 35 + 'px';
	    }else{

	    }






		var myDate = new Date();
		var dateStr = "";
		var TempMonth = (new Date()).getMonth()+1;
		dateStr = myDate.getFullYear() + "年" + TempMonth + "月" + myDate.getDate() + "日";

		var addCode1 = "";
		var addCode2 = "";
		var addCode3 = "";

		if($scope.sparraw_user_temp.hasOwnProperty("farminfo")){
			addCode1 = $scope.sparraw_user_temp.farminfo.address1;
			addCode2 = $scope.sparraw_user_temp.farminfo.address2;
			addCode3 = $scope.sparraw_user_temp.farminfo.address3;
		}

		var curWData = addCode1 + "_" + addCode2 + "_" + addCode3 + "_" + new Date().Format("yyyyMMdd");

		var tempWData = $scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 + "_" + 
						$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 + "_" + 
						$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 + "_" + 
						$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate;

		if(curWData != tempWData){
			var params = {
						      'add1code'    : addCode1 ,
						      'add2code'    : addCode2 ,
						      'add3code'    : addCode3                         
			};
			Sparraw.ajaxPost('sys/home/reqWeather.action', params, function(data){
					if(data.ResponseDetail.hasOwnProperty("weatherinfo")){
						$scope.homeData = {};
						$scope.homeData.weatherinfo = data.ResponseDetail.weatherinfo;
						var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
						var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
						var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
						var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
						if (judgeRain >= 0) {
							console.log("雨天");
							$scope.weatherSrc1 = "img/newFolder/home/weather/10.png";
						}else if (judgeCloudy >= 0) {
							console.log("多云");
							$scope.weatherSrc1 = "img/newFolder/home/weather/07.png";
						}else if (judgeSnow >= 0) {
							console.log("下雪");
							$scope.weatherSrc1 = "img/newFolder/home/weather/14.png";
						}else if (judgeShade >= 0) {
							console.log("阴");
							$scope.weatherSrc1 = "img/newFolder/home/weather/05.png";
						}else{
							console.log("晴天");
							$scope.weatherSrc1 = "img/newFolder/home/weather/00.png";
						};

						var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
						var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
						var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
						var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
						if (judgeRain1 >= 0) {
							console.log("雨天");
							$scope.weatherSrc2 = "img/newFolder/home/weather/10.png";
						}else if (judgeCloudy1 >= 0) {
							console.log("多云");
							$scope.weatherSrc2 = "img/newFolder/home/weather/07.png";
						}else if (judgeSnow1 >= 0) {
							console.log("下雪");
							$scope.weatherSrc2 = "img/newFolder/home/weather/14.png";
						}else if (judgeShade1 >= 0) {
							console.log("阴");
							$scope.weatherSrc2 = "img/newFolder/home/weather/05.png";
						}else{
							console.log("晴天");
							$scope.weatherSrc2 = "img/newFolder/home/weather/00.png";
						};

						$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 = addCode1;
						$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 = addCode2;
						$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 = addCode3;
						if(data.ResponseDetail.cityinfo){
							$scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 = data.ResponseDetail.cityinfo.cityname1;
							$scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 = data.ResponseDetail.cityinfo.cityname2;
							$scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 = data.ResponseDetail.cityinfo.cityname3;
							$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate = data.ResponseDetail.cityinfo.date?data.ResponseDetail.cityinfo.date.substr(0,8):"";
							$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;
						}else{
							console.log("用户暂未设置城市信息.")
						}
						$scope.sparraw_user_temp.weather.weatherinfo = data.ResponseDetail.weatherinfo;
					}
				    sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
				});
		}else{
			console.log("读取缓存天气");
			$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;

			//$scope.homeData = {};
			$scope.homeData.weatherinfo = $scope.sparraw_user_temp.weather.weatherinfo;
			var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
			var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
			var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
			var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
			if (judgeRain >= 0) {
				console.log("雨天");
				$scope.weatherSrc1 = "img/newFolder/home/weather/10.png";
			}else if (judgeCloudy >= 0) {
				console.log("多云");
				$scope.weatherSrc1 = "img/newFolder/home/weather/07.png";
			}else if (judgeSnow >= 0) {
				console.log("下雪");
				$scope.weatherSrc1 = "img/newFolder/home/weather/14.png";
			}else if (judgeShade >= 0) {
				console.log("阴");
				$scope.weatherSrc1 = "img/newFolder/home/weather/05.png";
			}else{
				console.log("晴天");
				$scope.weatherSrc1 = "img/newFolder/home/weather/00.png";
			};

			var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
			var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
			var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
			var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
			if (judgeRain1 >= 0) {
				console.log("雨天");
				$scope.weatherSrc2 = "img/newFolder/home/weather/10.png";
			}else if (judgeCloudy1 >= 0) {
				console.log("多云");
				$scope.weatherSrc2 = "img/newFolder/home/weather/07.png";
			}else if (judgeSnow1 >= 0) {
				console.log("下雪");
				$scope.weatherSrc2 = "img/newFolder/home/weather/14.png";
			}else if (judgeShade1 >= 0) {
				console.log("阴");
				$scope.weatherSrc2 = "img/newFolder/home/weather/05.png";
			}else{
				console.log("晴天");
				$scope.weatherSrc2 = "img/newFolder/home/weather/00.png";
			};
		}

		var dayNames = new Array("周日","周一","周二","周三","周四","周五","周六");  
		Stamp = new Date();  
		$scope.nowDate = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";
		
		if ($scope.sparraw_user_temp.farminfo) {
			$scope.getHomeInfo();
		}
		
	}


	$scope.getHomeInfo = function(){
		var params = {
			"FarmId"       :  $scope.sparraw_user_temp.farminfo.id     ,
			"UserId"       :  $scope.sparraw_user_temp.profile.id_spa     
		};
		Sparraw.ajaxPost('sys/home/reqOverview.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.homeData.deviceNum   =  data.ResponseDetail.OverView.deviceNum   ;
				$scope.homeData.messageNum  =  data.ResponseDetail.OverView.messageNum  ;
				$scope.homeData.HouseNum    =  data.ResponseDetail.OverView.HouseNum    ;
				$scope.homeData.FeedDays    =  data.ResponseDetail.OverView.FeedDays    ;
				//公告通知
				if (data.ResponseDetail.OverView.messageNum != 0) {
					$scope.annoRemindDiv = true;
				}else{
					$scope.annoRemindDiv = false;
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}



	$scope.openHelp = function(){//1.显示指引。2.检查更新。3.判断是否是试用账户。4.判断密码。5.判断日报。
		//判断是否显示指引
		var farmGuideShow = Common_getObjectFromLocalStorage("farmGuide");
		console.log("这是：" + farmGuideShow);
		if (!farmGuideShow) {
			$scope.helpDiv = true;
			return;
		}else{
			$scope.helpDiv = false;
			if (persistentData.onlyShowOnce && !$scope.helpDiv) {//试用账户只提示只显示一次
				persistentData.onlyShowOnce = false;
				Common_checkForUpdate($scope.judgTrial());
			}else{
				Common_checkForUpdate();
			}
			
		}
	}

	$scope.judgTrial = function(){
		if ($scope.sparraw_user_temp.profile.account == "13420161102") {
           		app_confirm('您现在使用的是“试用账户”该账户无任何操作权限',null,['去注册','先试用'],function(buttonIndex){
               	// 1-取消 2-确定
               	if(buttonIndex == 1){
					$state.go("useRegistered");
               	}else if(buttonIndex == 2){
               		
               	}
            }); 
        }else{
        	biz_common_judgePassword();
        }
	};

	$scope.cancelFun = function(){
		$scope.helpDiv = false;
		Common_saveObjectToLocalStorage('farmGuide','read');
		console.log("移除帮助页面");
	};

	$scope.goNext = function(){
		console.log("跳转");
		$scope.helpDiv = false;
		Common_saveObjectToLocalStorage('farmGuide','read');
		$state.go("myView");
	};
	

	$scope.goAbout = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"about");
	};

	$scope.goAlarmLogDelay = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"alarmLogDelay");
	};
	$scope.goEnvMonitoring = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"envMonitoring");
	};
	$scope.goAlarmStatistics = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"alarmStatistics");
	};
	$scope.goProdReco = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"prodReco");
	};
	$scope.goDocPlaceAffirm = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"docPlaceAffirm");
	};
	$scope.goBreedAffirm = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"breedAffirm");
	};
	$scope.goBatchClear = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"batchClear");
	};
	$scope.goDailyDay = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"dailyDay");
	};
	$scope.goWarnTabIndex = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"warnTabIndex");
	};
	$scope.goEnvAnalyIndex = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"envAnalyIndex");
	};
	
	$scope.go = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"");
	};


	$scope.pointDevelop = function() {
		biz_common_pointDevelop();
		return;	         
	};

	$scope.setData();
	Alarm_beginAlarmTask($scope.sparraw_user_temp.userinfo.role);
	$scope.judgeAlarm = function(){
		// 该标志在 nh_alarm.js中定义为全局变量
		if (nh_alarm_nowNeedAlarm == "N") {
			$scope.callPoliceSrc = "img/newFolder/home/callPolice.png";
		}else{
			$scope.callPoliceSrc = "img/newFolder/home/warning.png";
		};
	}

	$scope.goCostReport = function(){
		if ($scope.sparraw_user_temp.Authority.Financial === "Read") {
			$state.go("costReport");
		}else{
			return app_alert("该用户无此操作权限。");
		};
	};

	$scope.goBenefitsReport = function(){
		if ($scope.sparraw_user_temp.Authority.Financial === "Read") {
			$state.go("benefitsReport");
		}else{
			return app_alert("该用户无此操作权限。");
		};
	}



	$scope.setLinkView = function(){
		$scope.showUrl = 'https://mp.weixin.qq.com/s?__biz=MzAwMjU4ODMyNg==&mid=509958313&idx=1&sn=52e51cc59f80e4105791da3b66d91310&chksm=0159d360362e5a7620656671587f8f758a136bb3578284d4ac35c106a89bd429e47db05a306a&mpshare=1&scene=1&srcid=02244x8fhMDDaLqxlI2DPqeU&key=494a5736f574d32dc07ecaa218188a27cd53c8ccf354933f980c933e557abbc6ab3e5625887f70716cac587dec930d808372ba1be2e617130bbde213feb8ea42c315592fdffe080079bcf493f007b19e&ascene=0&uin=MTMzODE0OTAwMA%3D%3D&devicetype=iMac+MacBookPro11%2C3+OSX+OSX+10.12+build(16A323)&version=11020201&pass_ticket=AXJc62kSmNN4cCxdWblyRWOXpsuYCnxbgMpBRl%2FlRsZx46dQQcB3qwPP9Yb8ZijG';
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



	$scope.goClassroom = function(){
		$scope.showUrl = 'https://mp.weixin.qq.com/s?__biz=MzAwMjU4ODMyNg==&mid=509958604&idx=1&sn=29bc29e6420aac1fb6b3b521a7502d24&chksm=0159d205362e5b13c35e5f1a02cd180469962b0e937bbf227dfeb60c1d8de0a0806d133cdf2b&mpshare=1&scene=1&srcid=03090eRKQI2Cl5Dh3YUx9NvL&key=81058a66e1c3783b14a4c97365ce32337b1e2ff7f2a6a609eb9061ebb87dc0faffabc9b74cfe53035c05ffad97171df5063b5f1bee70ae442f12d95fdd5bc89f8f27f4d206d3aee2277ba658348f1ee0&ascene=0&uin=MTEzMzI0NzA0MA%3D%3D&devicetype=iMac+MacBookPro10%2C1+OSX+OSX+10.12.4+build(16E183b)&version=12020010&nettype=WIFI&fontScale=100&pass_ticket=8P22JObIT8EGmKCyMyhIMMeaSuYOzVVTCQiiiAOBKnPaexzARIWjwPj3YWDGuYNf';
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


	setTimeout(function () {
		//显示指引
		$scope.openHelp();
		//以下是测试代码
		//$state.go("envAnalyIndex");
	},1000);

	


	
})
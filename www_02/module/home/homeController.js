angular.module('myApp.home', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 主页面
.controller("homeCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout, AppData) {

	setPortrait(true,true);//竖屏
	
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}
	var dayNames = new Array("周日","周一","周二","周三","周四","周五","周六");  
	Stamp = new Date();  
	$scope.nowDate = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";

	$scope.goDailyTable = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"dailyTable");
	}

	$scope.gobatchManage = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"newBatchManage");
	}
	$scope.goEnvMonitoring = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"envMonitoring");
	}
	$scope.godataAnalyseTable = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"dataAnalyseTable");
	}
	$scope.goTaskRemind = function(){
		$state.go("taskRemind");
	}
	$scope.goChickenAssistList = function(){
		$state.go("chickenAssistList");
	}
	$scope.goProdReco = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"prodReco");
	}

	$scope.pointDevelop = function() {
		biz_common_pointDevelop();
		return;	
	};

	$scope.weatherSrc1 = "img/newFolder/home/weather/05.png";
	$scope.weatherSrc2 = "img/newFolder/home/weather/05.png";
	$scope.weatherSrc3 = "img/newFolder/home/weather/05.png";






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
	console.log("当前天气请求参数：" + curWData);
	console.log("缓存天气存在参数：" + tempWData);
	if(curWData != tempWData){
		console.log("加载天气信息");
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
						//document.getElementById('weatherImg1').setAttribute('src', 'img/newFolder/home/weather/10.png');
						$scope.weatherSrc1 = "img/newFolder/home/weather/10.png";
					}else if (judgeCloudy >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/newFolder/home/weather/07.png');
						$scope.weatherSrc1 = "img/newFolder/home/weather/07.png";
					}else if (judgeSnow >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/newFolder/home/weather/14.png');
						$scope.weatherSrc1 = "img/newFolder/home/weather/14.png";
					}else if (judgeShade >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/newFolder/home/weather/05.png');
						$scope.weatherSrc1 = "img/newFolder/home/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/newFolder/home/weather/00.png');
						$scope.weatherSrc1 = "img/newFolder/home/weather/00.png";
					};

					var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
					var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
					var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
					var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
					if (judgeRain1 >= 0) {
						console.log("雨天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/newFolder/home/weather/10.png');
						$scope.weatherSrc2 = "img/newFolder/home/weather/10.png";
					}else if (judgeCloudy1 >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/newFolder/home/weather/07.png');
						$scope.weatherSrc2 = "img/newFolder/home/weather/07.png";
					}else if (judgeSnow1 >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/newFolder/home/weather/14.png');
						$scope.weatherSrc2 = "img/newFolder/home/weather/14.png";
					}else if (judgeShade1 >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/newFolder/home/weather/05.png');
						$scope.weatherSrc2 = "img/newFolder/home/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/newFolder/home/weather/00.png');
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

		$scope.homeData = {};
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
	
	Alarm_beginAlarmTask($scope.sparraw_user_temp.userinfo.role);
	
	$scope.judgeAlarm = function(){
		// 该标志在 nh_alarm.js中定义为全局变量
		if (nh_alarm_nowNeedAlarm == "N") {
			document.getElementById('AlarmImg').setAttribute('src', 'img/newFolder/home/envMonitoring.png');
		}else{
			document.getElementById('AlarmImg').setAttribute('src', 'img/newFolder/home/envWarning.png');
		};
	}

	setTimeout(function () {
		if (persistentData.switchRemind) {
			persistentData.switchRemind = false;
			Common_checkForUpdate(biz_common_judgePassword);
		}
	},1000);
})
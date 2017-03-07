angular.module('myApp.envMonitoring', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//环境监测
.controller("envMonitoringCtrl",function($scope, $state, $http, $ionicLoading,$ionicScrollDelegate, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();

			setTimeout(
				function (){
					$scope.setData();
					$scope.queryDatatt();
				}
			,1000);
		}
	,1000);

	



    $scope.setData = function(){
    	//数据源
	    $scope.envMonitoringData = {
	    	"operation"   :  "realTimeData",
	    	"celsius"     :  "℃"           ,
	    	"MonitorData" :  [{
	    		"houseName"         :  "-"  , //-栋舍名称
				"dayAge"            :  "-"  , //-日龄
				"out_temp"          :  "-"  , //-室外温度
				"tempLeft1"         :  "-"  , //-前区温度
				"tempLeft2"         :  "-"  ,
				"tempMiddle1"       :  "-"  , //-中区温度
				"tempMiddle2"       :  "-"  ,
				"tempRight1"        :  "-"  , //-后区温度
				"tempRight2"	    :  "-"  ,
				"house_id"           :  ""   ,

				"tar_temp"          :  "-"  , //-目标温度
				"avg_temp"          :  "-"  , //-平均温度
				"H_temp"            :  "-"  , //-高报温度
				"L_temp"            :  "-"  , //-低报温度
				"point_temp"        :  "-"  , //-点温差
				"humi"              :  "-"  , //-湿度
				"data_time"         :  "-"  , //数据时间
				"temp_in1_alarm"    :  "N"  , //-前区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in2_alarm"    :  "N"  , //-中区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in3_alarm"    :  "N"  , //-后区温度：高报警（H）,正常（N）,低报警（L）
				"temp_avg_alarm"    :  "N"  , //-平均温度：高报警（H）,正常（N）,低报警（L)
				"point_temp_alarm"  :  "0"  , //-1-点温差报警；0-正常
				"power_status"      :  "-"  ,  
				"power_status_alarm" :  "0"    //1-断电报警；0-不报
	    	}]
	    }
	    $scope.transferHouseId = "";//跨页面传值使用
	    $scope.area = {
	    	"area1"  :  "Outdoor"  ,  //室外
	    	"area2"  :  "Front"    ,  //前区
	    	"area3"  :  "Middle"   ,  //中区
	    	"area4"  :  "Behind"   ,  //后区
	    	"area5"  :  "all"         //所有区域
	    }//判断从哪里跳转至报表界面
    }

    


    //判断是否是从环控监视进入报 警 设 置
    $scope.goAlarmSettings = function(){
		$state.go("alarmSettings");
    }


	$scope.queryDatatt = function(){
		var params = {
			"operation"   :  "realTimeData"
		};
		Sparraw.ajaxPost('envCtrl/monitoring.action', params, function(data){
			console.log(data);
			console.log("---------------------------");
			if (data.ResponseDetail.Result == "Success") {
				$scope.envMonitoringData.MonitorData = data.ResponseDetail.MonitorData;
				if ($scope.envMonitoringData.MonitorData == "" || !data.ResponseDetail.MonitorData) {
					$scope.dataNullText = true;
				}else{
					$scope.dataNullText = false;
					document.getElementById('dataNullTextId').innerHTML = "请至少绑定一个农汇通设备。";
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			$scope.$broadcast('scroll.refreshComplete');
		});
	};
	
	

	$scope.refreshFun = function(){
		$scope.queryDatatt();
	}

	$scope.goHome = function(){
		//biz_common_judgeRegistInfo($ionicPopup,$state,"envMonitoringTable");
		$state.go("home");
	}

	


	//控制区域颜色
	$scope.judgeEnter = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{background:'red'}";
		}else{
			return "{background:'#a5d5a9'}";
		};
	}

	//字体颜色
	$scope.labelColor = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{color:'#fff'}";
		}else{
			return "{color:'#5c5c5c'}";
		};
	}




    //判断温度状态
    //前区温度
    $scope.frontStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //中区温度
    $scope.middleStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //后区温度
    $scope.afterStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //平均温度
    $scope.retStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#f4f4f4'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //判断温差点
    $scope.temperatureStyle = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }
    //判断断电情况
    $scope.judgeCurrent = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }

})
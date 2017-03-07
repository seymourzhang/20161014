angular.module('myApp.alarmStatistics', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 环控报警统计
.controller("alarmStatisticsCtrl",function($scope, $state, $http, AppData) {

	setPortrait(true,true);//竖屏

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//数据源
	$scope.transferHouseId  =  ""  ;
	$scope.buildingDayAge   =  ""  ;
	$scope.alarmStatisticsData = {
		"houseData":[{
                "houseName"         :  ""                                             ,  //栋舍名称
                "dayAge"            :  ""                                             ,  //日龄
                "temp_avg_alarm"    :  {'temp_avg_alarm_H':"",'temp_avg_alarm_l':""}  ,  //平均
                "temp_in1_alarm"    :  {'temp_in1_alarm_H':"",'temp_in1_alarm_L':""}  ,  //前区
                "temp_in2_alarm"    :  {'temp_in2_alarm_H':"",'temp_in2_alarm_l':""}  ,  //中区
                "temp_in3_alarm"    :  {'temp_in3_alarm_H':"",'temp_in3_alarm_L':""}  ,  //后区
                "point_temp_alarm"  :  ""                                             ,  //点温差
                "power_status"      :  ""                                             ,  //断电报警（0）
          }]
	}
	//监控报警
	var params = {
		"FarmId"  :  $scope.sparraw_user_temp.farminfo.id
	};
	Sparraw.ajaxPost('sys/alarm/queryAlarmData.action', params, function(data){
		console.log(data);
		if (data.ResponseDetail.Result == "Success") {
			
			$scope.alarmStatisticsData.houseData = data.ResponseDetail.AlarmData;

		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});

	$scope.goAlarmLog = function(item){		
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseName==item.houseName){
				$scope.transferHouseId = $scope.sparraw_user_temp.userinfo.houses[i].HouseId;
				$scope.buildingDayAge  = $scope.alarmStatisticsData.houseData[i].dayAge;
			}
		}
	}
})

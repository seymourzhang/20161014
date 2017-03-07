angular.module('myApp.alarmLog', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 报警日志
.controller("alarmLogCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $stateParams, AppData) {

	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	$scope.setScreenStateFun = function(){
		setLandscape(true,true);//横屏
		var MAXHEIGHT = document.documentElement.clientWidth;

		var MINHEIGHT = MAXHEIGHT - 190;
		document.getElementById('tableDiv').style.minHeight = MINHEIGHT + 'px';
		document.getElementById('tableDiv').minHeight = MINHEIGHT + 'px';

		var MAX_HEIGHT = MAXHEIGHT - 240;
		document.getElementById('tableDiv').style.maxheight = MAX_HEIGHT + 'px';
		document.getElementById('tableDiv').maxheight = MAX_HEIGHT + 'px';
	}

	$scope.setData = function(){
		//数据源
	    $scope.alarmLogData = {	
	    	//"alarmCode"       :  ""  ,  //筛选栏的报警类型
	    	"AlarmCategory"       :  ""  ,  //筛选栏的报警类型
	        "AgeBegin"            :  ""  ,  //开始日龄
	        "AgeEnd"              :  ""  ,	//结束日龄
	        /*查询条件：
			"AlarmCategory":"All"-全部
					        "frontTemp"-前区温度报警
					        "middleTemp"-中区温度报警
					        "backTemp"-后区温度报警
					        "pointTemp"-点温差报警
					        "avgTemp"-平均温度报警
					        "powerStatus"-断电报警
			"AgeBegin"和"AgeEnd" 查询起始和截止日龄*/
			//一条日志的信息
	        "AlarmLog":[{
		            "aDayAge"         :  ""  ,  //日龄
		            "aDate"           :  ""  ,	 
		            "aTime"           :  ""  ,  //时间
		            "alarmID"         :  ""  ,	 
		            "alarmName"       :  ""  ,	 //显示的报警类型
		            "realValue"       :  ""  ,	 //实际温度
		            "targetValue"     :  ""  ,  //目标温度
		            "process_status"  :  ""  ,  //日志状态 01-待处理；02-处理中；03-已结束
		            "values"          :  ""  ,  //实际/目标
		            "process_status"  :  ""  ,  //响应状态
		            "deal_person"     :  ""  ,  //响应人员
		            "deal_time"       :  ""  ,  //响应时间
		            "is_normal"       :  ""  ,  //是否消除
		            "last_time"       :  ""     //持续时间
		            }]

	    };
	}
	$scope.houseName      = "" ;
	$scope.buildingDayAge = "" ;
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseId==$stateParams.receiveHouseId){
				$scope.houseName = $scope.sparraw_user_temp.userinfo.houses[i].HouseName;
			}
		}   
    $scope.buildingDayAge = $stateParams.buildingDayAge
	
    $scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};

	$scope.queryLog = function(){
		/*校验信息*/
	   	var required = [$scope.alarmLogData.AlarmCategory,$scope.alarmLogData.AgeBegin,$scope.alarmLogData.AgeEnd];
	   	if (parseInt(required[1]) > parseInt(required[2])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };
	    for(i in required){if(required[i]==''){return Sparraw.myNotice('尚有内容未填写...');}}
	    
		var params = {
			"HouseId"        :  parseInt($stateParams.receiveHouseId)                    ,
			"AlarmCategory"  :  $scope.alarmLogData.AlarmCategory                        ,
			"AgeBegin"       :  $scope.alarmLogData.AgeBegin                             ,
			"AgeEnd"         :  $scope.alarmLogData.AgeEnd
		};

		Sparraw.ajaxPost('sys/alarm/queryAlarmLog.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice("暂无报警信息。");
					$scope.alarmLogData.AlarmLog = [];
				}else{
					$scope.alarmLogData.AlarmLog = data.ResponseDetail.AlarmData;
				};

		});
	}
	$scope.cancel = function(){
		console.log("取消啦");
		$ionicSideMenuDelegate.toggleRight();
	}
	$scope.setScreenStateFun();
    setTimeout(function() {
    	$scope.setData();
    	//设置默认值自动查询   
		$scope.alarmLogData.AlarmCategory  =  "All"  ;
	    $scope.alarmLogData.AgeBegin       =  "0"    ;
	    $scope.alarmLogData.AgeEnd         =  "50"   ;
	    setTimeout(function() {
	    	$scope.queryLog();
	    }, 500);
    }, 1500);
})
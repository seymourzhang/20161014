angular.module('myApp.taskRemind', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//任务提醒
.controller("taskRemindCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $ionicPopup, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.taskRemindData = {
		"selectHouse" :  ""     ,
		"UnCompleteTaskNum":  "0"  ,//未完成任务个数
		"delayCount"	   :  "0"  ,//延迟任务个数
		"cancleCount"      :  "0"  ,//完成任务个数
		"AgeBegin"            :  ""  ,  //开始日龄
        "AgeEnd"              :  ""  ,	//结束日龄
        "TaskTimeSlot"        :  "0"  ,	//任务时间段
		"TskInfo"  :[{
			"TskGrpName":"",
			"curAgeFlag":"",
			"TaskDetail":[{
				"TskSN":"",
				"TaskName":"",
				"dealStatus":""
			}]
		}]
	};


	
	//默认选择第一栋
	$scope.taskRemindData.selectHouse = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]);
	//设置默认值自动查询
    $scope.taskRemindData.AgeBegin   =  "0"    ;
    $scope.taskRemindData.AgeEnd     =  "0"    ;
	$scope.judgeHouse = function(IsFirstTime){
		//判断农场状态
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01") {
			$scope.sparraw_user_temp.farminfo.farmBreedBatchId = 0;
		}else{

		};

		var required = [$scope.taskRemindData.AgeBegin,$scope.taskRemindData.AgeEnd];
	   	if (parseInt(required[0]) > parseInt(required[1])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };

	    if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {

			switch($scope.taskRemindData.TaskTimeSlot){
				case "0":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "0";
				  break;
				case "1":
					$scope.taskRemindData.AgeBegin="-1";
				  	$scope.taskRemindData.AgeEnd = "-1";
				  break;
				case "2":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "7";
				  break;
				case "3":
					$scope.taskRemindData.AgeBegin="8";
				  	$scope.taskRemindData.AgeEnd = "14";
				  break;
				case "4":
					$scope.taskRemindData.AgeBegin="15";
					$scope.taskRemindData.AgeEnd = "21";
				  break;
				case "5":
					$scope.taskRemindData.AgeBegin="22";
				  	$scope.taskRemindData.AgeEnd = "28";
				  break;
				case "6":
					$scope.taskRemindData.AgeBegin="29";
				  	$scope.taskRemindData.AgeEnd = "35";
				  break;
				case "7":
					$scope.taskRemindData.AgeBegin="36";
				  	$scope.taskRemindData.AgeEnd = "42";
				  break;
				case "8":
					$scope.taskRemindData.AgeBegin="43";
					$scope.taskRemindData.AgeEnd = "49";
				  break;
				case "9":
					$scope.taskRemindData.AgeBegin="50";
					$scope.taskRemindData.AgeEnd = "56";
				  break;
				case "10":
					$scope.taskRemindData.AgeBegin="57";
					$scope.taskRemindData.AgeEnd = "63";
				  break;
				default:
					$scope.taskRemindData.AgeBegin="64";
					$scope.taskRemindData.AgeEnd = "71";
					
	};
		};



		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"AgeBegin"     :  $scope.taskRemindData.AgeBegin                        ,
   			"AgeEnd"       :  $scope.taskRemindData.AgeEnd
		};
		Sparraw.ajaxPost('tsk/ActualTask/query.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskRemindData.TskInfo = data.ResponseDetail.TskInfo;
				$scope.taskRemindData.UnCompleteTaskNum = data.ResponseDetail.UnCompleteTaskNum;
				$scope.taskRemindData.delayCount = data.ResponseDetail.delayCount;
				$scope.taskRemindData.cancleCount = data.ResponseDetail.cancleCount;
			}else if (data.ResponseDetail.Result == "Fail"){
				$scope.taskRemindData.TskInfo = [];
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {
				$ionicSideMenuDelegate.toggleRight();
			};
		});
	}
	$scope.judgeHouse("firstTime");
	
	$scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};
	
	$scope.cancel = function(){
		$ionicSideMenuDelegate.toggleRight();
	}

	//判断日龄
	$scope.judgeDaysAge = function(item){
		if (item.curAgeFlag == "Y") {
			return "{background:'#71C671'}";
		}else{
			return "{background:'#AAAAAA'}";
		}
	};

	//判断任务状态
	$scope.judgeStateUpdateColor = function(sku){
		if (sku.UpdateFlag=='N' || $scope.sparraw_user_temp.Authority.TaskDeal == 'Read') {

		}else{
			if (sku.dealStatus == "01" || sku.dealStatus == "03") {
				return "{color:'#666666'}";
			}else if (sku.dealStatus == "02" || sku.dealStatus == "00"){
				return "{color:'red'}";
			}else {
				return "{color:'#2f7fff'}";
			}
		};
	}
	//判断权限
	if ($scope.sparraw_user_temp.Authority.TaskSetting === "All") {
		$scope.setBtn = false;
	}else{
		$scope.setBtn = true;
	};
	//任务状态选择
	$scope.judgeTaskState = function(choice,sku){
		console.log($scope.sparraw_user_temp.Authority);

		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"TskSN"        :  sku.TskSN                                                    ,
   			"TaskName"     :  sku.TaskName,
   			"dealStatus"   :  choice
		};
		Sparraw.ajaxPost('tsk/ActualTask/deal.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.judgeHouse();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});


	};
})
angular.module('myApp.addTask', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//新增任务
.controller("addTaskCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.TaskType = $stateParams.TaskType;

	$scope.addTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  ""                                    ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）默认启用
	}

	switch($scope.TaskType){
				case "01":
				  	$scope.addTaskData.showTaskType = "入雏前准备";
				  	$scope.addTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.addTaskData.showTaskType = "入雏后日常工作";
				  	$scope.addTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.addTaskData.showTaskType = "光照程序";
				  	$scope.addTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.addTaskData.showTaskType = "免疫用药程序";
				  	$scope.addTaskData.TaskType = "04";
				  break;
				default:
					$scope.addTaskData.showTaskType = "其他临时任务";
					$scope.addTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.addTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.addTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};

	

	$scope.save = function(){
		$scope.addTaskData.AgeInfos = $scope.addTaskData.AgeInfos.trim();
		//判断是否为空
		if ((Common_isNull($scope.addTaskData.TaskName)) 
			&& (Common_isNull($scope.addTaskData.AgeInfos)) 
			&& (Common_isNull($scope.addTaskData.TaskStatus))) {
		}else{
			return;
		};

		var tempAgeInfos = "";
		if($scope.addTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.addTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!Common_isNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.addTaskData.AgeInfos = tempAgeInfos.substring(1);

		var params = {
			"FarmId"  :    $scope.addTaskData.FarmId      ,
			"TaskName":    $scope.addTaskData.TaskName    ,
          	"TaskType":    $scope.addTaskData.TaskType    ,
          	"AgeInfos":    $scope.addTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.addTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/addTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("添加任务成功！");
				persistentData.overallTaskId     =  data.ResponseDetail.TskId    ;
				persistentData.overallTaskState  =  $scope.addTaskData.TaskType  ;
				console.log(persistentData.overallTaskState);
				$state.go("updateTask");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}

})
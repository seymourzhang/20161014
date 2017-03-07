angular.module('myApp.updateTask', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//查看任务
.controller("updateTaskCtrl",function($scope, $state, $http,  $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.saveBtn = true;
	$scope.cancelBtn = true;
	$scope.sheerDiv = false;
	$scope.backBtn = false;
	$scope.visible = false;

	$scope.updateTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  $stateParams.receiveTskID             ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）
	}

	//判断是从新建任务进入的，还是从任务列表进入的
	if (persistentData.overallTaskId === "") {
		$scope.TaskType = $stateParams.TaskType;
	}else{
		$scope.updateTaskData.TskID  =  persistentData.overallTaskId     ;
		$scope.TaskType              =  persistentData.overallTaskState  ;
	};

	

	$scope.startEdit = function(){
		$scope.saveBtn = false;
		$scope.cancelBtn = false;
		$scope.sheerDiv = true;
		$scope.backBtn = true;
		$scope.visible = true;
	};
	$scope.cancelEvent = function(){
		$scope.saveBtn = true;
		$scope.cancelBtn = true;
		$scope.sheerDiv = false;
		$scope.backBtn = false;
		$scope.visible = false;
	};

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	switch($scope.TaskType){
				case "01":
				  	$scope.updateTaskData.showTaskType = "入雏前准备";
				  	$scope.updateTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.updateTaskData.showTaskType = "入雏后日常工作";
				  	$scope.updateTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.updateTaskData.showTaskType = "光照程序";
				  	$scope.updateTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.updateTaskData.showTaskType = "免疫用药程序";
				  	$scope.updateTaskData.TaskType = "04";
				  break;
				default:
					$scope.updateTaskData.showTaskType = "其他临时任务";
					$scope.updateTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.updateTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.updateTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};


	$scope.inquire = function(){
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TskId"   :    $scope.updateTaskData.TskID    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskDetail.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.updateTaskData.TaskName = data.ResponseDetail.TaskName;
				$scope.updateTaskData.AgeInfos = data.ResponseDetail.AgeInfos;
				$scope.updateTaskData.TaskStatus = data.ResponseDetail.TaskStatus;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();

	$scope.save = function(){
		$scope.updateTaskData.AgeInfos = $scope.updateTaskData.AgeInfos.trim();
		//判断是否为空
		if ((Common_isNull($scope.updateTaskData.TaskName)) 
			&& (Common_isNull($scope.updateTaskData.AgeInfos)) 
			&& (Common_isNull($scope.updateTaskData.TaskStatus))) {
			
		}else{
			return;
		};
		var tempAgeInfos = "";
		if($scope.updateTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.updateTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!Common_isNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.updateTaskData.AgeInfos = tempAgeInfos.substring(1);
		
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TaskName":    $scope.updateTaskData.TaskName    ,
			"TskId"   :    $scope.updateTaskData.TskID       ,
          	"TaskType":    $scope.updateTaskData.TaskType    ,
          	"AgeInfos":    $scope.updateTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.updateTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/updateTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("修改任务成功！");
				$scope.cancelEvent();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}
})
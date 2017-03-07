angular.module('myApp.taskTable', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//任务列表
.controller("taskTableCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//用来判断从哪个入口进入以及标题的显示
	$scope.TaskType = $stateParams.TaskType;
	
	
	$scope.taskTableData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别
		"TaskType"      :  ""                                    ,//用来判断从服务器获取什么类型的任务（任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务）
		"conveyTskID"   :  ""                                    ,

		"TaskDetail"    : [{
			"TskID"         :  ""                                    ,//任务id
			"TaskName"      :  ""                                    ,//任务名称
			"ageInfos"      :  ""                                    ,//任务日期
			"TaskStatus"    :  ""                                     //任务状态Y有效、 N无效（任务状态是指饲养员是否可见Y可见、N隐藏）
		}]
		
	}
	//清空该值
	persistentData.overallTaskId     =  ""  ;
	persistentData.overallTaskState  =  ""  ;



	switch($scope.TaskType){
				case "01":
				  	$scope.taskTableData.showTaskType = "入雏前准备";
				  	$scope.taskTableData.TaskType     = "01";
				  break;
				case "02":
				  	$scope.taskTableData.showTaskType = "入雏后日常工作";
				  	$scope.taskTableData.TaskType     = "02";
				  break;
				case "03":
				  	$scope.taskTableData.showTaskType = "光照程序";
				  	$scope.taskTableData.TaskType     = "03";
				  break;
				case "04":
				  	$scope.taskTableData.showTaskType = "免疫用药程序";
				  	$scope.taskTableData.TaskType     = "04";
				  break;
				default:
					$scope.taskTableData.showTaskType = "其他临时任务";
					$scope.taskTableData.TaskType     = "05";
	};



	$scope.inquire = function(){
		var params = {
			"FarmId"     :    $scope.taskTableData.FarmId      ,
			"TaskType"   :    $scope.taskTableData.TaskType    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskList.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskTableData.TaskDetail = data.ResponseDetail.TaskDetail;
			}else if (data.ResponseDetail.Result == "Fail") {
				if ($scope.taskTableData.TaskDetail.length == 1) {
					$scope.taskTable = true;
				};
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.obtainTskId = function(item){
		$scope.taskTableData.conveyTskID = item.TskID;
	}



})
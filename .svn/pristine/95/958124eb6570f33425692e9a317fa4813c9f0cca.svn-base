angular.module('myApp.newProfitReport', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//盈利报告 
.controller("newProfitReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.profitData = {
		"FarmId"          :  $scope.sparraw_user_temp.farminfo.id,
		"BreedBatchId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"OverView":[],
        "feeDetail":[]
	}
	$scope.profitLoss = "";




	$scope.inquire = function(){

		var params = {
			"FarmId"        :  $scope.profitData.FarmId,
       		"BreedBatchId"  :  $scope.profitData.BreedBatchId
		};
		Sparraw.ajaxPost('farmManage/getProfitRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.profitData.OverView = data.ResponseDetail.OverView;
				$scope.profitData.feeDetail = data.ResponseDetail.feeDetail;

			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.inquire();


	$scope.judgeTitle = function(item){
		if (item.ItemName == "盈(亏)") {
			return "{'font-weight': 'bold'}";
		}else{

		}
	}



	console.log(selectBackPage.profitReportBack);
	$scope.backFun = function(){
		if (selectBackPage.profitReportBack == 'batchClearPage') {
			$state.go("batchClear");
			selectBackPage.profitReportBack = '';
		}else{
			$state.go("newBatchManage");
		}
	}


})
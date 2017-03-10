 angular.module('myApp.newBatchManage', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//批次管理
.controller("newBatchManageCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.batchStatus = {
		"docPlace":"",
		"such":"",
		"settlement":"",
		"batchCode":$scope.sparraw_user_temp.farminfo.farmBreedBatchCode
	}

	var stateA = [];//00
	var stateB = [];//01
	var stateC = [];//02
	for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
		if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "00") {
			stateA.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
		}

		if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "01") {
			stateB.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
		}

		if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "02") {
			stateC.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
		}
	}

	//判断批次结算
	if ($scope.sparraw_user_temp.farminfo.farmBreedStatus == "02") {
		$scope.batchStatus.settlement = "未结算";
		$scope.batchStatus.docPlace = "未入雏";
		$scope.batchStatus.such = "未出栏";
		$scope.batchStatus.batchCode = "";
	}else{
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus == "02") {
			$scope.batchStatus.settlement = "已结算";
		}else{
			$scope.batchStatus.settlement = "未结算";
		}

		//判断入雏状态
		if (stateA.length == $scope.sparraw_user_temp.userinfo.houses.length) {
			$scope.batchStatus.docPlace = "未入雏";
		}else if (stateA.length == 0) {
			$scope.batchStatus.docPlace = "全部入雏";
		}else if (stateA.length < $scope.sparraw_user_temp.userinfo.houses.length && stateA.length != 0) {
			$scope.batchStatus.docPlace = "部分入雏";
		}
		//判断出栏状态
		console.log("未入雏" + stateA.length);//未入雏
		console.log("已入雏" + stateB.length);//已入雏
		console.log("已出栏" + stateC.length);//已出栏

		if (stateC.length == 0) {
			$scope.batchStatus.such = "未出栏";
		}else if (stateB.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "部分出栏";
		}else if (stateB.length == 0 && stateC.length != 0) {
			$scope.batchStatus.such = "全部出栏";
		}


		/*if (stateA.length == 0 && stateC.length == 0 ||
			stateA.length != 0 && stateB.length != 0 ||
			stateB.length == 0 && stateC.length == 0) {
			$scope.batchStatus.such = "未出栏";
		}else if (stateB.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "部分出栏";
		}else if (stateA.length == 0 && stateB.length == 0  || stateA.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "全部出栏";
		}*/
	}
	

	$scope.goBreedAffirm = function(){
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01" || 
			$scope.sparraw_user_temp.farminfo.farmBreedStatus == "" || 
			!$scope.sparraw_user_temp.farminfo.farmBreedStatus) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			$state.go("breedAffirm");
		}
	}

	

	$scope.goNewProfitReport = function(){
		$state.go("newProfitReport");
	}

	$scope.goMoreBatchClearing = function(){
		$state.go("moreBatchClearing");
	}

	$scope.goMoreBatchProfit = function(){
		$state.go("moreBatchProfit");
	}


	$scope.goBatchClear = function(){



		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01" || 
			$scope.sparraw_user_temp.farminfo.farmBreedStatus == "" || 
			!$scope.sparraw_user_temp.farminfo.farmBreedStatus) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			$state.go("batchClear");
		}
	}


	$scope.goBenefitsReport = function(){

		$state.go("benefitsReport");
	}

	$scope.goCostReport = function(){

		$state.go("costReport");
	}

	$scope.goPriceClear = function(){

		$state.go("priceClear");
	}

})
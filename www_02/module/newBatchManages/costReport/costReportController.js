angular.module('myApp.costReport', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//成本报告 
.controller("costReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.costRepoData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,

				"amountCost":[],

				"otherFees": [],

		        "chickShitMoney":[]
	}



	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.costRepoData.FarmId        ,
			"ViewUnit"       :    $scope.costRepoData.transferUnit    
		};
		Sparraw.ajaxPost('farmManage/getCostsRep.action', params, function(data){
			var index1 = [];
			var index2 = [];
			var index3 = [];
			var index4 = [];
			var index5 = [];

			var index1Sun = 0;
			var index2Sun = 0;
			var index3Sun = 0;
			var index4Sun = 0;
			var index5Sun = 0;

			if (data.ResponseDetail.Result == "Success") {
				$scope.costRepoData.otherFees = data.ResponseDetail.otherFees;
				for (var i = 0; i < $scope.costRepoData.otherFees.length; i++) {
					if ($scope.costRepoData.otherFees[i].ItemName == "批次") {
						$scope.costRepoData.batchTitle = $scope.costRepoData.otherFees[i];
					}else{

					}
				}

				Array.prototype.elremove = function(m){  
			　　      if(isNaN(m)||m>this.length){return false;}  
			　　      this.splice(m,1);  
			　   } 
			　   $scope.costRepoData.otherFees.elremove(0);
				for (var i = 0; i < $scope.costRepoData.otherFees.length; i++) {
					if ($scope.costRepoData.otherFees[i].index1 != "-") {
						index1.push($scope.costRepoData.otherFees[i].index1);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index2 != "-") {
						index2.push($scope.costRepoData.otherFees[i].index2);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index3 != "-") {
						index3.push($scope.costRepoData.otherFees[i].index3);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index4 != "-") {
						index4.push($scope.costRepoData.otherFees[i].index4);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index5 != "-") {
						index5.push($scope.costRepoData.otherFees[i].index5);
					}else{

					}


					if ($scope.costRepoData.otherFees[i].ItemName == "鸡粪收入") {
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index1);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index2);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index3);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index4);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index5);
					}else{

					}
				}

				for (var i = 0; i < index1.length; i++) {

					index1Sun += Number(index1[i]);
				}
				for (var i = 0; i < index2.length; i++) {
					index2Sun += Number(index2[i]);
				}
				for (var i = 0; i < index3.length; i++) {
					index3Sun += Number(index3[i]);
				}
				for (var i = 0; i < index4.length; i++) {
					index4Sun += Number(index4[i]);
				}
				for (var i = 0; i < index5.length; i++) {
					index5Sun += Number(index5[i]);
				}


				$scope.costRepoData.amountCost = [
						Number(index1Sun).toFixed(2),
						Number(index2Sun).toFixed(2),
						Number(index3Sun).toFixed(2),
						Number(index4Sun).toFixed(2),
						Number(index5Sun).toFixed(2)
				];


				for (var i = 0; i < $scope.costRepoData.amountCost.length; i++) {
					if ($scope.costRepoData.amountCost[i] == 0.00) {
						$scope.costRepoData.amountCost[i] = "-";
					}
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.inquireMultiProfit = function(){
		$scope.inquire();
	}


	
})
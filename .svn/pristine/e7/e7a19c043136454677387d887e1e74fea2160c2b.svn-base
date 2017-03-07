angular.module('myApp.priceClear', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//价格结算
.controller("priceClearCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicBackdrop,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.priceClearData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"BatchDatas":{},
		        "ChickPrice":[],
		        "ChickenPrice":[],
		        "FeedPrice":[]
    }
	
	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.priceClearData.FarmId        
		};
		Sparraw.ajaxPost('farmManage/getSettlePriceRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.priceClearData.BatchDatas = data.ResponseDetail.BatchDatas;
				$scope.priceClearData.FeedPrice = data.ResponseDetail.FeedPrice;
				$scope.priceClearData.ChickPrice = data.ResponseDetail.ChickPrice;
				$scope.priceClearData.ChickenPrice = data.ResponseDetail.ChickenPrice;
				console.log(data.ResponseDetail.ChickPrice);
				console.log(data.ResponseDetail.ChickenPrice);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


})
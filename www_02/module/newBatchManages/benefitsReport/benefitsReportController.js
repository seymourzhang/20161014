angular.module('myApp.benefitsReport', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//效益报告 
.controller("benefitsReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.beneRepoData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,

				"OverView": [{
		            "ItemName"  : "批次号"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "天数"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "出栏"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "存活%"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "均重"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "料/肉"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "欧指"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        }],
				"otherFees": [{
		            "ItemName"  : "毛鸡"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "鸡苗"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "饲料"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "药费"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "杂费"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "盈亏"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        }]
	}
	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.beneRepoData.FarmId        ,
			"ViewUnit"       :    $scope.beneRepoData.transferUnit    
		};
		Sparraw.ajaxPost('farmManage/getBenefitRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.beneRepoData.OverView = data.ResponseDetail.OverView;
				$scope.beneRepoData.otherFees = data.ResponseDetail.otherFees;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();
	$scope.inquireMultiProfit = function(){
		$scope.inquire();
	}
	$scope.judgeBackground = function(item){
		if (item.ItemName == "批次") {
			return "{background:'#44CA65',color:'#FFF',border:'solid 1px #FFF'}";
		}else{
			return "{background:'#FFF',color:'black'}";
		}
	}
})
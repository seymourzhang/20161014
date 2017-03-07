angular.module('myApp.productionSumReport', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//生产指标汇总报表
.controller("productionSumReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	
	setLandscape(true,true);

	$scope.setData = function(){
		$scope.cullDeathRateData = {
			"xData"       :  [] ,
			"yData"       :  [] ,
			"yName"       :  [] ,
			"yColor"      :  [] ,
			"hiddenPara"  :  [] ,
			"selectedBatch"  :  [] ,
			"selectedBatchKey"  :  [] ,
			"farmBatch"  :  [] ,
			"BatchDate"  :  [] ,
			"farmBatch"  :  [] ,
			"selectUnit" :  "" ,
			"compareType":  "" ,
			"selectedHouse": 0,
			"containBatchHouse":[]
	    };

		//设置栋舍号列表信息
		for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		


		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0].id);

		$scope.profitData = {
			"OverView":[]
		}

		setTimeout(function() {
			$scope.GainBatch();
		}, 1000);
	}

    


	$scope.GainBatch = function(){
		$scope.batchDiv = true;
		$scope.housesDiv = false;
		var params = {
			"FarmId"     : $scope.sparraw_user_temp.farminfo.id
		};
		Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
	            $scope.cullDeathRateData.farmBatch = data.ResponseDetail.FarmBreedIdArray;
	            var Length = 0;
				for(var key in $scope.cullDeathRateData.farmBatch){
					Length++;
				    $scope.cullDeathRateData.selectedBatch = key;
				    $scope.cullDeathRateData.selectedBatchKey = $scope.cullDeathRateData.selectedBatch;	
				}
				$scope.inquire();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};
	
	
	$scope.setData();
	
    //切换筛选方式
    $scope.changesCompareType = function(){
		if ($scope.cullDeathRateData.compareType == '01') {
			//批次
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        document.getElementById("headName").innerHTML  = "栋舍号";
		}else{
			//栋舍
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			document.getElementById("headName").innerHTML  = "批次号";
		};
		
		$scope.inquire()

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	$scope.inquire()
    }
    //切换栋舍
    $scope.changesHousesId = function(){
		$scope.inquire()
    }
	
	


	$scope.inquire = function(){
		var params = {
			"compareType":$scope.cullDeathRateData.compareType,
       		"BreedBatchId"  :  $scope.cullDeathRateData.selectedBatch,
       		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id,
			"HouseId"  :  $scope.cullDeathRateData.selectedHouse
		};
		Sparraw.ajaxPost('farmManage/productionSumReport.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.profitData.OverView = data.ResponseDetail.OverView;
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};
})


angular.module('myApp.dayWaterCurve', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])

.controller('dayWaterCurveCtrl', function($scope, $state) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	setLandscape(true,true);

    $scope.initData = function(){
		document.getElementById('mainContent').style.top = persistentData.tabHeight + 'px';
		if (DeviInfo.ScreenHeight > DeviInfo.ScreenWidth) {
			document.getElementById('mainContent').style.height = (DeviInfo.ScreenWidth - persistentData.tabHeight) + 'px';
		}else{
			document.getElementById('mainContent').style.height = (DeviInfo.ScreenHeight - persistentData.tabHeight) + 'px';
		}

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
			"selectedHouse": "",
			"containBatchHouse":[]
	    };


		setTimeout(function() {
			$scope.GainBatch();
		}, 1500);
	};



	$scope.GainBatch = function(){
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
	               return $scope.changes2();
			}else{
				    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		//设置栋舍号列表信息
		for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		console.log($scope.cullDeathRateData.containBatchHouse);
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	
	
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 42) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
										            }
			                                        return    res;
							                     }
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
     	}else{
     		console.log("栋舍号对比");
     		$scope.selectedHouseId = JSON.parse($scope.cullDeathRateData.selectedHouse);
     		var params = { 
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
								ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 42) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
										            }
			                                        return    res;
							                     }
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
     	};
	}
    $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             document.getElementById("chang").innerHTML = '日采食';
             document.getElementById("cullSurTi").innerHTML = '日饮水曲线';
             return $scope.changes2();
        }else if (dd=='日采食'){
        	 document.getElementById("chang").innerHTML = '日饮水';
        	 document.getElementById("cullSurTi").innerHTML = '日采食曲线';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        $scope.changes2();
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			$scope.changes2();
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
        $scope.changes2();
    }
    //切换栋舍
    $scope.changesHousesId = function(){
        $scope.changes2();
    }
  	//日饮水
	$scope.changes2 = function(){
		console.log("日饮水");
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 42) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
										            }
			                                        return    res;
							                     }
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}else{
			console.log("栋舍号对比");
			$scope.selectedHouseId = JSON.parse($scope.cullDeathRateData.selectedHouse);
			var params = { 
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 42) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '';
										            }
			                                        return    res;
							                     }
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}
	}

	$scope.initData();
})
 angular.module('myApp.cumuCullDeathCurve', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//成活率
.controller("cumuCullDeathCurveCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setLandscape(true,true);   

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

	document.getElementById('cullDeath_DIV').style.height = (screen.width - 75) + 'px';


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
	               return $scope.changes1();
			}else{
				    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		//设置栋舍号列表信息
		for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
			$scope.sparraw_user_temp.houseinfos[i].houseName += "栋";
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		/*var  selectBatch = {
			"id":"01",
			"mtc_device_id":"f528009e0292d922",
			"houseName":"全场平均",
			"feedarea":"99"
		};
		$scope.cullDeathRateData.containBatchHouse.push(selectBatch);*/
		
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	$scope.GainBatch();

	$scope.changesDieToLife = function(datas){
		for (var i = 0; i < datas.length; i++) {
			datas[i] = (100 - datas[i]);
		}
		return datas;
	}
	//死淘率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率");
		var yLeftRange = [90,100];//undefined;//[0,15];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('rep/DCRate/accDCRateReq.action', params, function(data){
				console.log("批次号对比---");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								var datas = $scope.changesDieToLife(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] = datas;// data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];

							$scope.cullDeathRateData.selectUnit = '%';


							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value) + '％';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});

			console.log("!!!!!!!!!!!!!!!!");
			console.log($scope.cullDeathRateData.selectedBatch);
			console.log("!!!!!!!!!!!!!!!!");
     	}else{
     		console.log("栋舍号对比_____");
     		$scope.selectedHouseId = JSON.parse($scope.cullDeathRateData.selectedHouse);
     		var params = { 
	        	'CompareType' : "02",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch ,
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('rep/DCRate/accDCRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								var datas = $scope.changesDieToLife(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] = datas;// data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value) + '％';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};

						//$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.selectedHouse);
							console.log("-----------");
							console.log($scope.cullDeathRateData.selectedHouse);
							console.log("-----------");
			});
     	};
	}
    $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             document.getElementById("chang").innerHTML = '日死淘率';
             document.getElementById("cullSurTi").innerHTML = '成活率报表';
             return $scope.changes2();
        }else if (dd=='日死淘率'){
        	 document.getElementById("chang").innerHTML = '成活率';
        	 document.getElementById("cullSurTi").innerHTML = '日死淘率报表';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
  	//成活率率
	$scope.changes2 = function(){
		console.log("成活率");
		var yLeftRange = [85,100];//undefined;//[0,15];
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
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
							console.log("yName:" + $scope.cullDeathRateData.yName);
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 42) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
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
							$scope.cullDeathRateData.selectUnit = '%';
							Echart_initLine01($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );

						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}
	}
})
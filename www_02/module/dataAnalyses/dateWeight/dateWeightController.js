angular.module('myApp.dateWeight', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
// 日体重
.controller("dateWeightCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	setLandscape(true,true);

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }
	$scope.tempChartData = {
		"batchTable"        :  ""                           ,//批次表
		"selectedHouseId"   :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId),//选中的栋舍id
		"selectedBatch"     :  ""                           ,//选中批次的key
		"selectedBatchVal"  :  ""                           //选中批次的val
	}
   //获取批次信息
	var params = {
		"FarmId"   :   $scope.sparraw_user_temp.farminfo.id
	};

    

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
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		console.log($scope.cullDeathRateData.containBatchHouse);
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	$scope.GainBatch();
	
	$scope.changeDatas = function(datas){
		console.log("datas:" + datas);
		var bigDatas = [];
		// 将元数据中大雨0的数据筛选出来
		for (var i = 0; i < datas.length; i++) {
			if(datas[i] > 0){
				var obj = new Object();
				obj.age = i;
				obj.value = datas[i];
				bigDatas.push(obj);
			}
		}
		
		if(bigDatas.length == 0){
			return datas;
		}
		// 遍历大于0的数据
		for (var i = 0; i < datas.length; i++) {
			var j = i + 1;
			var obj = bigDatas[i];
			if(j < bigDatas.length){
				var next = bigDatas[j];
				var temp = (next.value - obj.value )/(next.age - obj.age);
				
				for(var k = obj.age + 1; k < next.age; k++){
					datas[k] = obj.value + parseInt(temp*(k - obj.age));
				}
				
			}
		}
		var newdatas = [];
		// 最近的0值全部删除
		for(var i = 0 ; i <= bigDatas[bigDatas.length - 1].age; i++){
			newdatas.push(datas[i]);
		}
		console.log("newdatas:" + newdatas);
		return newdatas;
	}
	
	
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
				"FarmId"     : $scope.sparraw_user_temp.farminfo.id,
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('/rep/weekWeight/DFRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								var datas = $scope.changeDatas(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] =  datas;//data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 45) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
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
												 '单位：克 ',
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
				"FarmId"     : $scope.sparraw_user_temp.farminfo.id,
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('/rep/weekWeight/DFRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								var datas = $scope.changeDatas(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] =  datas;//data.ResponseDetail.DCDatas[i].HouseDatas;
								//$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
								ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 45) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
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
												 '单位：克',
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
    	
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='周体重'){
	             return $scope.changes1();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='周体重'){
	             return $scope.changes1();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='周体重'){
             return $scope.changes1();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='周体重'){
             return $scope.changes1();
        }
    }
	
})
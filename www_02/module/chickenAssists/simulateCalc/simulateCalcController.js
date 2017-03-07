angular.module('myApp.simulateCalc', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 模拟算账
.controller("simulateCalcCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	var userId = ($scope.sparraw_user_temp.profile.id_spa);
	
	$scope.loseFoucus = function(){
		//console.log("-----")
		/*var val = $scope.simulateData.simulateDataTemp.fcr;
		if(val < 1 ){
			//$scope.simulateData.simulateDataTemp.fcr = val;
			app_alert('料肉比不小于1.');
			return;
		}*/
	}
	
	$scope.forageTotal = function(type){
		
		if (isNaN($scope.simulateData.simulateDataTemp.survival)) {
			$scope.simulateData.simulateDataTemp.survival = 0;
		}else{
			if($scope.simulateData.simulateDataTemp.survival > 100 || $scope.simulateData.simulateDataTemp.survival < 0){
				$scope.simulateData.simulateDataTemp.survival = 0;
				$scope.simulateData.simulateDataTemp.outputChickens = 0;
				app_alert('成活率超出范围:0~100');
				return;

			}else{
				//$scope.simulateData.simulateDataTemp.outputChickens = parseInt($scope.simulateData.simulateDataTemp.inputChickens*$scope.simulateData.simulateDataTemp.survival/100);
			}
		}

		if (isNaN($scope.simulateData.simulateDataTemp.manualMoney)) {
			$scope.simulateData.simulateDataTemp.manualMoney = 0;
		}else{
			
		}

		if (isNaN($scope.simulateData.simulateDataTemp.othersMoney)) {
			$scope.simulateData.simulateDataTemp.othersMoney = 0;
		}else{
			
		}

		
		if ($scope.simulateData.simulateDataTemp.outputChickens > $scope.simulateData.simulateDataTemp.inputChickens) {
			Sparraw.myNotice("出栏数不允许大于进鸡数。");
		}else{
			
		}

		
		
		var val = $scope.simulateData.simulateDataTemp.fcr;
		if(val < 1 ){
			//$scope.simulateData.simulateDataTemp.fcr = val;
			//app_alert('料肉比不小于1.');
			//return;
		}
		//存活率
		//$scope.simulateData.simulateDataTemp.survival = Number(($scope.simulateData.simulateDataTemp.outputChickens / $scope.simulateData.simulateDataTemp.inputChickens) * 100).toFixed(0);

		// 毛鸡价钱 + 鸡shit钱
		var saleMoney = $scope.simulateData.simulateDataTemp.outputChickens*($scope.simulateData.simulateDataTemp.weight*$scope.simulateData.simulateDataTemp.chickPrice) + $scope.simulateData.simulateDataTemp.chickShitMoney;
		
		var costMoney = $scope.simulateData.simulateDataTemp.inputChickens*$scope.simulateData.simulateDataTemp.smallChickPrice + $scope.simulateData.simulateDataTemp.weight*$scope.simulateData.simulateDataTemp.fcr*$scope.simulateData.simulateDataTemp.outputChickens*$scope.simulateData.simulateDataTemp.feedPrice + ($scope.simulateData.simulateDataTemp.medicineMoney + $scope.simulateData.simulateDataTemp.catchChickMoney + $scope.simulateData.simulateDataTemp.paddingMoney + $scope.simulateData.simulateDataTemp.manualMoney + $scope.simulateData.simulateDataTemp.heatingMoney + $scope.simulateData.simulateDataTemp.utilityMoney + $scope.simulateData.simulateDataTemp.maintainMoney + $scope.simulateData.simulateDataTemp.quarantineMoney + $scope.simulateData.simulateDataTemp.rentMoney + $scope.simulateData.simulateDataTemp.interestMoney + $scope.simulateData.simulateDataTemp.othersMoney);
		
	}



	
	var simulateTypes = {1:'基准数据',2:'模拟数据'};
	var simulateType = 1;
	
	Common_deleteObjectFromLocalStorage("simulateDataStand");
	Common_deleteObjectFromLocalStorage("simulateDataDIY");
	
	var simulateDataStand = Common_getObjectFromLocalStorage("simulateDataStand_" + userId);
	//console.log("simulateDataStand:" + JSON.stringify(simulateDataStand))
	if(simulateDataStand == undefined || simulateDataStand == null){
		simulateDataStand = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
		Common_saveObjectToLocalStorage("simulateDataStand",JSON.stringify(simulateDataStand))
	}else{
		simulateDataStand = JSON.parse(simulateDataStand);
	}
	
	var simulateDataDIY = Common_getObjectFromLocalStorage("simulateDataDIY_" + userId);
	if(simulateDataDIY == undefined || simulateDataDIY == null){
		simulateDataDIY = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
		Common_saveObjectToLocalStorage("simulateDataDIY",JSON.stringify(simulateDataDIY))
	}else{
		simulateDataDIY = JSON.parse(simulateDataDIY);
	}

	var simulateDataProperty = {"inputChickens":"进鸡数","feedDays":"饲养天数","outputChickens":"出栏数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛    鸡","smallChickPrice":"鸡    苗","feedPrice":"饲    料","medicineMoney":"药费","manualMoney":"人工费","othersMoney":"其他费用","makeMoney":"盈 / 亏","ouzhi":"欧指"};
	//{"inputChickens":"进鸡数","outputChickens":"出栏数","feedDays":"饲养天数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛鸡价","smallChickPrice":"鸡苗价","feedPrice":"饲料价","chickShitMoney":"鸡粪收入","medicineMoney":"药品疫苗","catchChickMoney":"抓鸡费","paddingMoney":"垫料费","manualMoney":"人工费","heatingMoney":"取暖费","utilityMoney":"水电费","maintainMoney":"维修费","quarantineMoney":"检疫费","rentMoney":"租金","interestMoney":"利息","othersMoney":"杂费","makeMoney":"盈/亏"};
		
	Common_saveObjectToLocalStorage("simulateDataProperty_" + userId,JSON.stringify(simulateDataProperty))
	
	
	$scope.simulateData = {
		"simulateTypes"      :  simulateTypes ,
		"simulateType"       :  simulateType ,
		"simulateDataTemp":simulateDataStand,// 保存的模拟数据
		
    };
	
	$scope.selectGuige = function(){
		if($scope.simulateData.simulateType == 1){
			if ((!simulateDataDIY.inputChickens || simulateDataDIY.inputChickens == 0 || simulateDataDIY.inputChickens == "") &&
				(!simulateDataDIY.feedDays ||simulateDataDIY.feedDays == 0 ||  simulateDataDIY.feedDays == "") &&
				(!simulateDataDIY.survival ||simulateDataDIY.survival == 0 ||  simulateDataDIY.survival == "") &&
				(!simulateDataDIY.weight ||simulateDataDIY.weight == 0 ||  simulateDataDIY.weight == "") &&
				(!simulateDataDIY.fcr ||simulateDataDIY.fcr == 0 ||  simulateDataDIY.fcr == "")) {

			}else{
				$scope.simulateData.simulateDataTemp = simulateDataDIY;
			}


			
			$scope.simulateData.simulateType = 2;
			document.getElementById("mainTitle").innerHTML = "模拟算账(方案二)";
			document.getElementById("changer").innerHTML = "方案一";
		}else if($scope.simulateData.simulateType == 2){
			$scope.simulateData.simulateType = 1;
			$scope.simulateData.simulateDataTemp = simulateDataStand;
			document.getElementById("mainTitle").innerHTML = "模拟算账(方案一)";
			document.getElementById("changer").innerHTML = "方案二";
		}
		$scope.forageTotal();
		//console.log($scope.simulateData.simulateType);
	}
	
	$scope.save = function(){
		if($scope.simulateData.simulateType == 1){
			simulateDataStand = $scope.simulateData.simulateDataTemp;
			Common_saveObjectToLocalStorage("simulateDataStand_" + userId,JSON.stringify(simulateDataStand));
			
		}else if($scope.simulateData.simulateType == 2){
			simulateDataDIY = $scope.simulateData.simulateDataTemp;
			
			Common_saveObjectToLocalStorage("simulateDataDIY_" + userId,JSON.stringify(simulateDataDIY))
		}
		//console.log($scope.simulateData.simulateType);
		//console.log(JSON.stringify($scope.simulateData.simulateDataTemp));
		
		//Sparraw.myNotice("保存成功");
		
	}
	
	
	$scope.doComp = function(){

		$scope.save();
		$state.go("comparisonRes");
	}
	
	$scope.forageTotal();
})

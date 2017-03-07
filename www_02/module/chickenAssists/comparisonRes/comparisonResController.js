angular.module('myApp.comparisonRes', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 数据对比
.controller("comparisonResCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var userId = ($scope.sparraw_user_temp.profile.id_spa);
	var simulateDataStand = Common_getObjectFromLocalStorage("simulateDataStand_" + userId);
	var simulateDataDIY = Common_getObjectFromLocalStorage("simulateDataDIY_" + userId);
	var simulateDataProperty = Common_getObjectFromLocalStorage("simulateDataProperty_" + userId);

	$scope.transferUnit = "Money";
	$scope.ViewUnit = "万元";
	
	if(simulateDataProperty == undefined || simulateDataProperty == null){
		simulateDataProperty = {"feedDays":"饲养天数","outputChickens":"出栏数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛    鸡","smallChickPrice":"鸡    苗","feedPrice":"饲    料","medicineMoney":"药费","manualMoney":"人工费","othersMoney":"其他费用","makeMoney":"盈/亏","ouzhi":"欧指"};
		
		Common_saveObjectToLocalStorage("simulateDataProperty",JSON.stringify(simulateDataProperty))
	}else{
		simulateDataProperty = JSON.parse(simulateDataProperty);
	}
	
	if(simulateDataStand == undefined || simulateDataStand == null){
		simulateDataStand = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
	}else{
		simulateDataStand = JSON.parse(simulateDataStand);
	}
	
	if(simulateDataDIY == undefined || simulateDataDIY == null){
		simulateDataDIY = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
	}else{
		simulateDataDIY = JSON.parse(simulateDataDIY);
	}
	
	//基准
	//出栏数量
	simulateDataStand.outputChickens = (simulateDataStand.inputChickens * (simulateDataStand.survival * 0.01)).toFixed(0);
	//毛鸡总价 出栏数量*只均重*毛鸡价
	simulateDataStand.chickPrice = simulateDataStand.outputChickens * simulateDataStand.weight * simulateDataStand.chickPrice;
	//鸡苗总价
	simulateDataStand.smallChickPrice *= simulateDataStand.inputChickens;
	//饲料总价
	simulateDataStand.feedPrice = (simulateDataStand.fcr * (simulateDataStand.outputChickens * simulateDataStand.weight) * simulateDataStand.feedPrice).toFixed(0);
	//盈亏（毛鸡总价-鸡苗总价-饲料总价-药费-人工费-其他费用）
	simulateDataStand.makeMoney = (simulateDataStand.chickPrice  - simulateDataStand.smallChickPrice - simulateDataStand.feedPrice - simulateDataStand.medicineMoney - simulateDataStand.manualMoney - simulateDataStand.othersMoney).toFixed(0);
	if (!simulateDataStand.makeMoney || isNaN(simulateDataStand.makeMoney)) {
		simulateDataStand.makeMoney = 0;
	}
	//((体重(只均重)x成活率(0.0X))/(料肉比x出栏日龄))*10000
	simulateDataStand.ouzhi = parseFloat(((simulateDataStand.weight * (simulateDataStand.survival * 0.01))/(simulateDataStand.fcr * simulateDataStand.feedDays))*10000).toFixed(2);
	if (!simulateDataStand.ouzhi || isNaN(simulateDataStand.ouzhi)) {
		simulateDataStand.ouzhi = 0;
	}

	if (!simulateDataStand.survival || isNaN(simulateDataStand.survival)) {
		simulateDataStand.survival = 0;
	}
	

	//模拟
	//出栏数量
	simulateDataDIY.outputChickens = (simulateDataDIY.inputChickens * (simulateDataDIY.survival * 0.01)).toFixed(0);
	//毛鸡总价
	simulateDataDIY.chickPrice = simulateDataDIY.outputChickens * simulateDataDIY.weight * simulateDataDIY.chickPrice;
	//鸡苗总价
	simulateDataDIY.smallChickPrice *= simulateDataDIY.inputChickens;
	//饲料总价
	simulateDataDIY.feedPrice = (simulateDataDIY.fcr * (simulateDataDIY.outputChickens * simulateDataDIY.weight) * simulateDataDIY.feedPrice).toFixed(0);
	//盈亏
	simulateDataDIY.makeMoney = (simulateDataDIY.chickPrice  - simulateDataDIY.smallChickPrice - simulateDataDIY.feedPrice - simulateDataDIY.medicineMoney - simulateDataDIY.manualMoney - simulateDataDIY.othersMoney).toFixed(0);
	if (!simulateDataDIY.makeMoney || isNaN(simulateDataDIY.makeMoney)) {
		simulateDataDIY.makeMoney = 0;
	}
	//((体重x成活率)/(料肉比x出栏日龄))*10000
	simulateDataDIY.ouzhi = parseFloat(((simulateDataDIY.weight * (simulateDataDIY.survival * 0.01))/(simulateDataDIY.fcr * simulateDataDIY.feedDays))*10000).toFixed(2);
	if (!simulateDataDIY.ouzhi || isNaN(simulateDataDIY.ouzhi)) {
		simulateDataDIY.ouzhi = 0;
	}

	if (!simulateDataDIY.survival || isNaN(simulateDataDIY.survival)) {
		simulateDataDIY.survival = 0;
	}


	
	



	

	

	
	


	$scope.upDateView = function(){

		var views = [];
		for(var name in simulateDataProperty){
			var obj = new Object();
			obj.itemName = simulateDataProperty[name];


			if(simulateDataStand[name] == "" || !simulateDataStand[name]){
				obj.value_stand = 0;
			}else{
				obj.value_stand = simulateDataStand[name];
			}
			
			
			if(simulateDataDIY[name] == ""  || !simulateDataDIY[name]){
				obj.value_diy = 0;
			}else{
				obj.value_diy = simulateDataDIY[name]
			}
			
			views.push(obj);
		}
		console.log(simulateDataStand);

		$scope.profitData = {
			"OverView"       :  views
	    };
	}
	
	
	$scope.doBack = function(){
		$state.go("simulateCalc");
	}

	var tempDiyChickPrice = simulateDataDIY.chickPrice;
	var tempDiySmallChickPrice = simulateDataDIY.smallChickPrice;
	var tempDiyFeedPrice = simulateDataDIY.feedPrice;
	var tempDiyMedicineMoney = simulateDataDIY.medicineMoney;
	var tempDiyManualMoney = simulateDataDIY.manualMoney;
	var tempDiyOthersMoney = simulateDataDIY.othersMoney;
	var tempDiyMakeMoney = simulateDataDIY.makeMoney;


	var tempSimChickPrice = simulateDataStand.chickPrice
	var tempSimSmallChickPrice = simulateDataStand.smallChickPrice
	var tempSimFeedPrice = simulateDataStand.feedPrice
	var tempSimMedicineMoney = simulateDataStand.medicineMoney
	var tempSimManualMoney = simulateDataStand.manualMoney
	var tempSimOthersMoney = simulateDataStand.othersMoney
	var tempSimMakeMoney = simulateDataStand.makeMoney



	$scope.inquireMultiProfit = function(){
		if ($scope.transferUnit == "Money") {
	    	$scope.ViewUnit = "万元";

	    	simulateDataStand.chickPrice = (tempSimChickPrice * 0.0001).toFixed(2);
			simulateDataStand.smallChickPrice = (tempSimSmallChickPrice * 0.0001).toFixed(2);
			simulateDataStand.feedPrice = (tempSimFeedPrice * 0.0001).toFixed(2);
			simulateDataStand.medicineMoney = (tempSimMedicineMoney * 0.0001).toFixed(2);
			simulateDataStand.manualMoney = (tempSimManualMoney * 0.0001).toFixed(2);
			simulateDataStand.othersMoney = (tempSimOthersMoney * 0.0001).toFixed(2);
			simulateDataStand.makeMoney = (tempSimMakeMoney * 0.0001).toFixed(2);


			simulateDataDIY.chickPrice = (tempDiyChickPrice * 0.0001).toFixed(2);
			simulateDataDIY.smallChickPrice = (tempDiySmallChickPrice * 0.0001).toFixed(2);
			simulateDataDIY.feedPrice = (tempDiyFeedPrice * 0.0001).toFixed(2);
			simulateDataDIY.medicineMoney = (tempDiyMedicineMoney * 0.0001).toFixed(2);
			simulateDataDIY.manualMoney = (tempDiyManualMoney * 0.0001).toFixed(2);
			simulateDataDIY.othersMoney = (tempDiyOthersMoney * 0.0001).toFixed(2);
			simulateDataDIY.makeMoney = (tempDiyMakeMoney * 0.0001).toFixed(2);

	    }else if ($scope.transferUnit == "quentity") {
	    	$scope.ViewUnit = "只";
	    	simulateDataStand.chickPrice = parseFloat(tempSimChickPrice / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.smallChickPrice = parseFloat(tempSimSmallChickPrice / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.feedPrice = parseFloat(tempSimFeedPrice / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.medicineMoney = parseFloat(tempSimMedicineMoney / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.manualMoney = parseFloat(tempSimManualMoney / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.othersMoney = parseFloat(tempSimOthersMoney / simulateDataStand.outputChickens).toFixed(2);
			simulateDataStand.makeMoney = parseFloat(tempSimMakeMoney / simulateDataStand.outputChickens).toFixed(2);

			simulateDataDIY.chickPrice = (tempDiyChickPrice / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.smallChickPrice = (tempDiySmallChickPrice / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.feedPrice = (tempDiyFeedPrice / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.medicineMoney = (tempDiyMedicineMoney / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.manualMoney = (tempDiyManualMoney / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.othersMoney = (tempDiyOthersMoney / simulateDataDIY.outputChickens).toFixed(2);
			simulateDataDIY.makeMoney = (tempDiyMakeMoney / simulateDataDIY.outputChickens).toFixed(2);


	    }else if ($scope.transferUnit == "weight") {
	    	$scope.ViewUnit = "公斤";
	    	simulateDataStand.chickPrice = parseFloat(tempSimChickPrice / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.smallChickPrice = parseFloat(tempSimSmallChickPrice / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.feedPrice = parseFloat(tempSimFeedPrice / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.medicineMoney = parseFloat(tempSimMedicineMoney / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.manualMoney = parseFloat(tempSimManualMoney / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.othersMoney = parseFloat(tempSimOthersMoney / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);
			simulateDataStand.makeMoney = parseFloat(tempSimMakeMoney / (simulateDataStand.outputChickens * simulateDataStand.weight)).toFixed(2);


			simulateDataDIY.chickPrice = (tempDiyChickPrice / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.smallChickPrice = (tempDiySmallChickPrice / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.feedPrice = (tempDiyFeedPrice / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.medicineMoney = (tempDiyMedicineMoney / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.manualMoney = (tempDiyManualMoney / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.othersMoney = (tempDiyOthersMoney / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
			simulateDataDIY.makeMoney = (tempDiyMakeMoney / (simulateDataDIY.outputChickens * simulateDataDIY.weight)).toFixed(2);
	    };

	    if (isNaN(simulateDataStand.chickPrice)) {
	    	simulateDataStand.chickPrice = 0;
	    }
	    if (isNaN(simulateDataStand.smallChickPrice)) {
	    	simulateDataStand.smallChickPrice = 0;
	    }
	    if (isNaN(simulateDataStand.feedPrice)) {
	    	simulateDataStand.feedPrice = 0;
	    }
	    if (isNaN(simulateDataStand.medicineMoney)) {
	    	simulateDataStand.medicineMoney = 0;
	    }
	    if (isNaN(simulateDataStand.manualMoney)) {
	    	simulateDataStand.manualMoney = 0;
	    }
	    if (isNaN(simulateDataStand.othersMoney)) {
	    	simulateDataStand.othersMoney = 0;
	    }
	    if (isNaN(simulateDataStand.makeMoney)) {
	    	simulateDataStand.makeMoney = 0;
	    }


	    if (isNaN(simulateDataDIY.chickPrice)) {
	    	simulateDataDIY.chickPrice = 0;
	    }
	    if (isNaN(simulateDataDIY.smallChickPrice)) {
	    	simulateDataDIY.smallChickPrice = 0;
	    }
	    if (isNaN(simulateDataDIY.feedPrice)) {
	    	simulateDataDIY.feedPrice = 0;
	    }
	    if (isNaN(simulateDataDIY.medicineMoney)) {
	    	simulateDataDIY.medicineMoney = 0;
	    }
	    if (isNaN(simulateDataDIY.manualMoney)) {
	    	simulateDataDIY.manualMoney = 0;
	    }
	    if (isNaN(simulateDataDIY.othersMoney)) {
	    	simulateDataDIY.othersMoney = 0;
	    }
	    if (isNaN(simulateDataDIY.makeMoney)) {
	    	simulateDataDIY.makeMoney = 0;
	    }


	    $scope.upDateView();
	}
	$scope.inquireMultiProfit();




	
	
})

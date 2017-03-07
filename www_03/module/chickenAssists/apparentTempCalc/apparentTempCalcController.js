angular.module('myApp.apparentTempCalc', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 体感温度计算器
.controller("apparentTempCalcCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	
	$scope.calcData = {
		"wind"       :  0 ,// 风速
		"RH"       :  0 ,// 相对湿度
		"envTemp"       :  0 ,// 温度
		"apparentTemp"      :  0
    };
	
	// 基础标准数据
	var baseData = [{'temp':35,'RH':30,'wind':0,'feelsLike':35},
					{'temp':35,'RH':50,'wind':0,'feelsLike':35},
					{'temp':35,'RH':70,'wind':0,'feelsLike':38.3},
					{'temp':35,'RH':80,'wind':0,'feelsLike':40},
					{'temp':32.2,'RH':30,'wind':0,'feelsLike':32.2},
					{'temp':32.2,'RH':50,'wind':0,'feelsLike':32.2},
					{'temp':32.2,'RH':70,'wind':0,'feelsLike':35.5},
					{'temp':32.2,'RH':80,'wind':0,'feelsLike':37.2},
					{'temp':29.4,'RH':30,'wind':0,'feelsLike':29.4},
					{'temp':29.4,'RH':50,'wind':0,'feelsLike':29.4},
					{'temp':29.4,'RH':70,'wind':0,'feelsLike':31.6},
					{'temp':29.4,'RH':80,'wind':0,'feelsLike':33.3},
					{'temp':26.6,'RH':30,'wind':0,'feelsLike':26.6},
					{'temp':26.6,'RH':50,'wind':0,'feelsLike':26.6},
					{'temp':26.6,'RH':70,'wind':0,'feelsLike':28.3},
					{'temp':26.6,'RH':80,'wind':0,'feelsLike':29.4},
					{'temp':23.9,'RH':30,'wind':0,'feelsLike':23.9},
					{'temp':23.9,'RH':50,'wind':0,'feelsLike':23.9},
					{'temp':23.9,'RH':70,'wind':0,'feelsLike':25.5},
					{'temp':23.9,'RH':80,'wind':0,'feelsLike':26.3},
					{'temp':21.1,'RH':30,'wind':0,'feelsLike':21.1},
					{'temp':21.1,'RH':50,'wind':0,'feelsLike':21.1},
					{'temp':21.1,'RH':70,'wind':0,'feelsLike':23.3},
					{'temp':21.1,'RH':80,'wind':0,'feelsLike':24.4},
					{'temp':35,'RH':30,'wind':0.5,'feelsLike':31.6},
					{'temp':35,'RH':50,'wind':0.5,'feelsLike':32.2},
					{'temp':35,'RH':70,'wind':0.5,'feelsLike':35.2},
					{'temp':35,'RH':80,'wind':0.5,'feelsLike':36.7},
					{'temp':32.2,'RH':30,'wind':0.5,'feelsLike':28.8},
					{'temp':32.2,'RH':50,'wind':0.5,'feelsLike':29.4},
					{'temp':32.2,'RH':70,'wind':0.5,'feelsLike':32.7},
					{'temp':32.2,'RH':80,'wind':0.5,'feelsLike':34.2},
					{'temp':29.4,'RH':30,'wind':0.5,'feelsLike':26},
					{'temp':29.4,'RH':50,'wind':0.5,'feelsLike':26.6},
					{'temp':29.4,'RH':70,'wind':0.5,'feelsLike':30},
					{'temp':29.4,'RH':80,'wind':0.5,'feelsLike':31.6},
					{'temp':26.6,'RH':30,'wind':0.5,'feelsLike':23.6},
					{'temp':26.6,'RH':50,'wind':0.5,'feelsLike':24.4},
					{'temp':26.6,'RH':70,'wind':0.5,'feelsLike':26.1},
					{'temp':26.6,'RH':80,'wind':0.5,'feelsLike':27.2},
					{'temp':23.9,'RH':30,'wind':0.5,'feelsLike':22.2},
					{'temp':23.9,'RH':50,'wind':0.5,'feelsLike':22.8},
					{'temp':23.9,'RH':70,'wind':0.5,'feelsLike':24.4},
					{'temp':23.9,'RH':80,'wind':0.5,'feelsLike':25.2},
					{'temp':21.1,'RH':30,'wind':0.5,'feelsLike':18.4},
					{'temp':21.1,'RH':50,'wind':0.5,'feelsLike':18.9},
					{'temp':21.1,'RH':70,'wind':0.5,'feelsLike':20.5},
					{'temp':21.1,'RH':80,'wind':0.5,'feelsLike':21.3},
					{'temp':35,'RH':30,'wind':1.1,'feelsLike':26.1},
					{'temp':35,'RH':50,'wind':1.1,'feelsLike':26.6},
					{'temp':35,'RH':70,'wind':1.1,'feelsLike':30.5},
					{'temp':35,'RH':80,'wind':1.1,'feelsLike':32},
					{'temp':32.2,'RH':30,'wind':1.1,'feelsLike':24.9},
					{'temp':32.2,'RH':50,'wind':1.1,'feelsLike':25.5},
					{'temp':32.2,'RH':70,'wind':1.1,'feelsLike':28.8},
					{'temp':32.2,'RH':80,'wind':1.1,'feelsLike':30},
					{'temp':29.4,'RH':30,'wind':1.1,'feelsLike':23.8},
					{'temp':29.4,'RH':50,'wind':1.1,'feelsLike':24.4},
					{'temp':29.4,'RH':70,'wind':1.1,'feelsLike':27.2},
					{'temp':29.4,'RH':80,'wind':1.1,'feelsLike':28.8},
					{'temp':26.6,'RH':30,'wind':1.1,'feelsLike':21.3},
					{'temp':26.6,'RH':50,'wind':1.1,'feelsLike':22.2},
					{'temp':26.6,'RH':70,'wind':1.1,'feelsLike':24.4},
					{'temp':26.6,'RH':80,'wind':1.1,'feelsLike':25.5},
					{'temp':23.9,'RH':30,'wind':1.1,'feelsLike':20.2},
					{'temp':23.9,'RH':50,'wind':1.1,'feelsLike':21.1},
					{'temp':23.9,'RH':70,'wind':1.1,'feelsLike':23.3},
					{'temp':23.9,'RH':80,'wind':1.1,'feelsLike':24.1},
					{'temp':21.1,'RH':30,'wind':1.1,'feelsLike':17.6},
					{'temp':21.1,'RH':50,'wind':1.1,'feelsLike':18.3},
					{'temp':21.1,'RH':70,'wind':1.1,'feelsLike':19.4},
					{'temp':21.1,'RH':80,'wind':1.1,'feelsLike':20},
					{'temp':35,'RH':30,'wind':1.52,'feelsLike':23.8},
					{'temp':35,'RH':50,'wind':1.52,'feelsLike':24.4},
					{'temp':35,'RH':70,'wind':1.52,'feelsLike':28.8},
					{'temp':35,'RH':80,'wind':1.52,'feelsLike':30},
					{'temp':32.2,'RH':30,'wind':1.52,'feelsLike':23.2},
					{'temp':32.2,'RH':50,'wind':1.52,'feelsLike':23.8},
					{'temp':32.2,'RH':70,'wind':1.52,'feelsLike':27.2},
					{'temp':32.2,'RH':80,'wind':1.52,'feelsLike':28.4},
					{'temp':29.4,'RH':30,'wind':1.52,'feelsLike':22.1},
					{'temp':29.4,'RH':50,'wind':1.52,'feelsLike':22.7},
					{'temp':29.4,'RH':70,'wind':1.52,'feelsLike':25.5},
					{'temp':29.4,'RH':80,'wind':1.52,'feelsLike':26.7},
					{'temp':26.6,'RH':30,'wind':1.52,'feelsLike':20.5},
					{'temp':26.6,'RH':50,'wind':1.52,'feelsLike':21.1},
					{'temp':26.6,'RH':70,'wind':1.52,'feelsLike':23.3},
					{'temp':26.6,'RH':80,'wind':1.52,'feelsLike':24},
					{'temp':23.9,'RH':30,'wind':1.52,'feelsLike':19.4},
					{'temp':23.9,'RH':50,'wind':1.52,'feelsLike':20},
					{'temp':23.9,'RH':70,'wind':1.52,'feelsLike':22.2},
					{'temp':23.9,'RH':80,'wind':1.52,'feelsLike':22.8},
					{'temp':21.1,'RH':30,'wind':1.52,'feelsLike':17.3},
					{'temp':21.1,'RH':50,'wind':1.52,'feelsLike':17.7},
					{'temp':21.1,'RH':70,'wind':1.52,'feelsLike':18.8},
					{'temp':21.1,'RH':80,'wind':1.52,'feelsLike':19.5},
					{'temp':35,'RH':30,'wind':2.03,'feelsLike':22.7},
					{'temp':35,'RH':50,'wind':2.03,'feelsLike':23.3},
					{'temp':35,'RH':70,'wind':2.03,'feelsLike':26.1},
					{'temp':35,'RH':80,'wind':2.03,'feelsLike':27.2},
					{'temp':32.2,'RH':30,'wind':2.03,'feelsLike':22.1},
					{'temp':32.2,'RH':50,'wind':2.03,'feelsLike':22.7},
					{'temp':32.2,'RH':70,'wind':2.03,'feelsLike':25.5},
					{'temp':32.2,'RH':80,'wind':2.03,'feelsLike':26.5},
					{'temp':29.4,'RH':30,'wind':2.03,'feelsLike':20.5},
					{'temp':29.4,'RH':50,'wind':2.03,'feelsLike':21.1},
					{'temp':29.4,'RH':70,'wind':2.03,'feelsLike':24.4},
					{'temp':29.4,'RH':80,'wind':2.03,'feelsLike':25.2},
					{'temp':26.6,'RH':30,'wind':2.03,'feelsLike':18.3},
					{'temp':26.6,'RH':50,'wind':2.03,'feelsLike':18.9},
					{'temp':26.6,'RH':70,'wind':2.03,'feelsLike':20.5},
					{'temp':26.6,'RH':80,'wind':2.03,'feelsLike':21.1},
					{'temp':23.9,'RH':30,'wind':2.03,'feelsLike':17.1},
					{'temp':23.9,'RH':50,'wind':2.03,'feelsLike':17.7},
					{'temp':23.9,'RH':70,'wind':2.03,'feelsLike':20},
					{'temp':23.9,'RH':80,'wind':2.03,'feelsLike':20.8},
					{'temp':21.1,'RH':30,'wind':2.03,'feelsLike':16.1},
					{'temp':21.1,'RH':50,'wind':2.03,'feelsLike':16.6},
					{'temp':21.1,'RH':70,'wind':2.03,'feelsLike':18.3},
					{'temp':21.1,'RH':80,'wind':2.03,'feelsLike':19},
					{'temp':35,'RH':30,'wind':2.54,'feelsLike':21.6},
					{'temp':35,'RH':50,'wind':2.54,'feelsLike':22.2},
					{'temp':35,'RH':70,'wind':2.54,'feelsLike':24.4},
					{'temp':35,'RH':80,'wind':2.54,'feelsLike':25.2},
					{'temp':32.2,'RH':30,'wind':2.54,'feelsLike':20.5},
					{'temp':32.2,'RH':50,'wind':2.54,'feelsLike':21.1},
					{'temp':32.2,'RH':70,'wind':2.54,'feelsLike':23.3},
					{'temp':32.2,'RH':80,'wind':2.54,'feelsLike':24},
					{'temp':29.4,'RH':30,'wind':2.54,'feelsLike':19.4},
					{'temp':29.4,'RH':50,'wind':2.54,'feelsLike':20},
					{'temp':29.4,'RH':70,'wind':2.54,'feelsLike':23.3},
					{'temp':29.4,'RH':80,'wind':2.54,'feelsLike':23.9},
					{'temp':26.6,'RH':30,'wind':2.54,'feelsLike':17.7},
					{'temp':26.6,'RH':50,'wind':2.54,'feelsLike':18.3},
					{'temp':26.6,'RH':70,'wind':2.54,'feelsLike':19.4},
					{'temp':26.6,'RH':80,'wind':2.54,'feelsLike':20},
					{'temp':23.9,'RH':30,'wind':2.54,'feelsLike':16},
					{'temp':23.9,'RH':50,'wind':2.54,'feelsLike':16.6},
					{'temp':23.9,'RH':70,'wind':2.54,'feelsLike':18.8},
					{'temp':23.9,'RH':80,'wind':2.54,'feelsLike':19.9},
					{'temp':21.1,'RH':30,'wind':2.54,'feelsLike':15.5},
					{'temp':21.1,'RH':50,'wind':2.54,'feelsLike':16.1},
					{'temp':21.1,'RH':70,'wind':2.54,'feelsLike':17.2},
					{'temp':21.1,'RH':80,'wind':2.54,'feelsLike':18}
					];
	
	
	$scope.save = function(){
		var wind = $scope.calcData.wind.toFixed(2);
		var RH = $scope.calcData.RH.toFixed(1);
		var envTemp = $scope.calcData.envTemp.toFixed(1);
		var apparentTemp = 0;
		
		console.log("envTemp:" + $scope.calcData.envTemp);
		console.log("RH:" + $scope.calcData.RH);
		console.log("wind:" + $scope.calcData.wind);
		
		var filter = true;
		if(filter){
			if(envTemp > 35 || envTemp < 21.1  ){
				app_alert("环境温度已超出范围(21.1-35)！")
				return;
			}
			
			if( RH > 80 || RH < 30  ){
				app_alert("相对湿度已超出范围(30-80)！")
				return;
			}
			
			if(wind > 2.54 || wind < 0 ){
				app_alert("风速已超出范围(0-2.54)！")
				return;
			}
		}
		
		
		
		// 目标温度
		var targetTemp = 35;
		// 目标温度区间差值
		var targetTempDis = 2.8;
		// 温度区间最小值
		var targetMinTemp = 0;
		// 温度区间最大值
		var targetMaxTemp = 0;
		if(envTemp > 32.2 && envTemp <= 35){
			targetTemp = 35;
			targetTempDis = 2.8;
		}
		if(envTemp > 29.4 && envTemp <= 32.2){
			targetTemp = 32.2;
			targetTempDis = 2.8;
		}
		if(envTemp > 26.6 && envTemp <= 29.4){
			targetTemp = 29.4;
			targetTempDis = 2.8;
		}
		if(envTemp > 23.9 && envTemp <= 26.6){
			targetTemp = 26.6;
			targetTempDis = 2.7;
		}
		if(envTemp >= 21.1 && envTemp <= 23.9){
			targetTemp = 23.9;
			targetTempDis = 2.8;
		}
		
		targetMinTemp = (targetTemp - targetTempDis).toFixed(1);
		targetMaxTemp = targetTemp;

		// 环境温度与目标温度的差距
		var intervalTemp = (targetTemp - envTemp).toFixed(1);
		console.log("targetMinTemp:" + targetMinTemp)
		console.log("targetMaxTemp:" + targetMaxTemp)
		console.log("intervalTemp:" + intervalTemp)
		
		// 目标湿度
		var targetRH = 30;
		// 目标湿度区间差值
		var targetRHDis = 20;
		// 湿度区间最小值
		var targetMinRH = 0;
		// 湿度区间最大值
		var targetMaxRH = 0;
		
		if(RH >= 30 && RH <= 50){
			targetRH = 50;
			targetRHDis = 20;
		}
		if(RH > 50 && RH <= 70){
			targetRH = 70;
			targetRHDis = 20;
		}
		if(RH > 70 && RH <= 80){
			targetRH = 80;
			targetRHDis = 10;
		}
		
		targetMinRH = (targetRH - targetRHDis).toFixed(1);
		targetMaxRH = targetRH;
		
		// 环境湿度与目标湿度的差距
		var intervalRH = (targetRH - RH).toFixed(1);
		console.log("targetMinRH:" + targetMinRH)
		console.log("targetMaxRH:" + targetMaxRH)
		console.log("intervalRH:" + intervalRH)
		
		// 目标风速
		var targetWind = 0;
		// 目标风速区间差值
		var targetWindDis = 0;
		// 风速区间最小值
		var targetMinWind = 0;
		// 风速区间最大值
		var targetMaxWind = 0;
		
		if(wind >= 0 && wind <= 0.5){
			targetWind = 0.5;
			targetWindDis = 0.5;
		}
		if(wind > 0.5 && wind <= 1.1){
			targetWind = 1.1;
			targetWindDis = 0.6;
		}
		if(wind > 1.1 && wind <= 1.52){
			targetWind = 1.52;
			targetWindDis = 0.42;
		}
		if(wind > 1.52 && wind <= 2.03){
			targetWind = 2.03;
			targetWindDis = 0.51;
		}
		if(wind > 2.03 && wind <= 2.54){
			targetWind = 2.54;
			targetWindDis = 0.51;
		}
		
		targetMinWind = (targetWind - targetWindDis).toFixed(2);
		targetMaxWind = targetWind;
		// 环境风速与目标风速的差距
		var intervalWind = (targetWind - wind).toFixed(2);
		console.log("targetMinWind:" + targetMinWind)
		console.log("targetMaxWind:" + targetMaxWind)
		console.log("intervalWind:" + intervalWind)
		
		// 相对湿度和风速在一定条件下，体感温度和环境温度一样
		if(RH >= 30 && RH <= 50 && wind == 0){
			apparentTemp = envTemp;
		}else{
			
			// 体感温度下限
			var feelsLikeMin = 0;
			// 体感温度上限
			var feelsLikeMax = 0;
			
			var fromIndex = 0;
			var endIndex = baseData.length;
			
			/**
			按照从温度由高到低，湿度由低到高，风速由低到高定位
			*/
			var base1 = null;
			var base2 = null;
			var base3 = null;
			var base4 = null;
			var base5 = null;
			var base6 = null;
			var base7 = null;
			var base8 = null;
					
			// 如果温度小于30,从数组后面开始查找，否则从头开始找
			if(targetTemp < 30){
				fromIndex = baseData.length;
				endIndex = 0;
				for(var index = fromIndex - 1; index >= endIndex ; index--){
					var baseInfo = baseData[index];
					//console.log(baseData[index]);
					/***
					1.找到环境温度所在区间
					2.找到环境湿度所在区间
					3.找到环境风速所在区间
					4.计算环境温度下标准相对湿度区间的值
					
					*/
					if(baseInfo.temp == targetMaxTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base1 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base2 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base3 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base4 = baseInfo;
							}
						}
					}
					
					if(baseInfo.temp == targetMinTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base5 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base6 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base7 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base8 = baseInfo;
							}
						}
						
					}
					
					if(base1 != null && base2 != null && base3 != null && base4 != null && base5 != null && base6 != null && base7 != null && base8 != null){
						break;
					}
					
				}
			}else{
				for(var index = fromIndex; index < endIndex ; index++){
					var baseInfo = baseData[index];
					//console.log(baseData[index]);
					if(baseInfo.temp == targetMaxTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base1 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base2 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base3 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base4 = baseInfo;
							}
						}
					}
					
					if(baseInfo.temp == targetMinTemp){
						if(baseInfo.RH == targetMinRH){
							if(baseInfo.wind == targetMinWind ){
								base5 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base6 = baseInfo;
							}
						}else if (baseInfo.RH == targetMaxRH){
							if(baseInfo.wind == targetMinWind ){
								base7 = baseInfo;
							}else if(baseInfo.wind == targetMaxWind ){
								base8 = baseInfo;
							}
						}
						
					}
					
					if(base1 != null && base2 != null && base3 != null && base4 != null && base5 != null && base6 != null && base7 != null && base8 != null){
						break;
					}
				}
			}
			
			// 计算环境温度对应的标准湿度风速数据
			var envData1 = new Object();
			envData1.temp = envTemp;
			envData1.RH = targetMinRH;
			envData1.wind = targetMinWind;
			envData1.feelsLike = base1.feelsLike - (base1.feelsLike - base5.feelsLike)*intervalTemp/(targetTempDis);
			
			var envData2 = new Object();
			envData2.temp = envTemp;
			envData2.RH = targetMinRH;
			envData2.wind = targetMaxWind;
			envData2.feelsLike = base2.feelsLike - (base2.feelsLike - base6.feelsLike)*intervalTemp/(targetTempDis);
			
			
			var envData3 = new Object();
			envData3.temp = envTemp;
			envData3.RH = targetMaxRH;
			envData3.wind = targetMinWind;
			envData3.feelsLike = base3.feelsLike - (base3.feelsLike - base7.feelsLike)*intervalTemp/(targetTempDis);
			
			var envData4 = new Object();
			envData4.temp = envTemp;
			envData4.RH = targetMaxRH;
			envData4.wind = targetMaxWind;
			envData4.feelsLike = base4.feelsLike - (base4.feelsLike - base8.feelsLike)*intervalTemp/(targetTempDis);
			
			// 由4个点的标准值计算环境湿度对应的标准风速值
			var envData5 = new Object();
			envData5.temp = envTemp;
			envData5.RH = RH;
			envData5.wind = targetMinWind;
			envData5.feelsLike = envData3.feelsLike - (envData3.feelsLike - envData1.feelsLike)*intervalRH/(targetRHDis);
			
			var envData6 = new Object();
			envData6.temp = envTemp;
			envData6.RH = RH;
			envData6.wind = targetMaxWind;
			envData6.feelsLike = envData4.feelsLike - (envData4.feelsLike - envData2.feelsLike)*intervalRH/(targetRHDis);
			
			var envData7 = new Object();
			envData7.temp = envTemp;
			envData7.RH = RH;
			envData7.wind = wind;
			envData7.feelsLike = envData6.feelsLike - ((envData6.feelsLike - envData5.feelsLike)*intervalWind/(targetWindDis)).toFixed(1);
			
			console.log("envData1:" + JSON.stringify(envData1))
			console.log("envData2:" + JSON.stringify(envData2))
			console.log("envData3:" + JSON.stringify(envData3))
			console.log("envData4:" + JSON.stringify(envData4))
			console.log("envData5:" + JSON.stringify(envData5))
			console.log("envData6:" + JSON.stringify(envData6))
			console.log("envData7:" + JSON.stringify(envData7))
			
			apparentTemp = envData7.feelsLike;
			
		}
		
		
		//apparentTemp = 37 - (37 - envTemp)/(0.68 - 0.0014*RH + 1/(1.76 + 1.4*Math.pow(wind,0.75))) - 0.29*envTemp*(1 - 0.01*RH);
		
		//console.log(typeof apparentTemp)
		
		$scope.calcData.apparentTemp = Math.round(apparentTemp*10)/10;
		//$scope.calcData.apparentTemp = $scope.calcData.apparentTemp.toFixed(1);
	}
	
	$scope.forageTotal = function(){
		
	}
	
	$scope.backFun = function(){
		$state.go("home");
	}
	
	

})
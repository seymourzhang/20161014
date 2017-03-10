angular.module('myApp.setStandard', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//设定标准
.controller("setStandardCtrl",function($scope, $state,  $stateParams, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setPortrait(true,true);
	$scope.setStandardData = {
		"FarmId":$scope.sparraw_user_temp.farminfo.id,//查询用FarmId
		"SettingData": {
		    "farm_standard": "10000",
		    "mortality_type": "",
		    "cum_mortality":[],//传到后台的数据
		    "need_mortality":"Y"
		},
		"":"",
		"dayData": [],//按周龄填写时49天
	    "weekData": []//按周龄填写时7天
	}
	$scope.cullDeathDiv = true;
	$scope.dateMortalityDiv = true;
	$scope.weekMortalityDiv = true;

	//选择的标准
	$scope.judgeStandard = function(){
		if ($scope.setStandardData.SettingData.farm_standard == ""||
			$scope.setStandardData.SettingData.farm_standard == 10004 || 
			$scope.setStandardData.SettingData.farm_standard == 10005 ) {
			$scope.cullDeathDiv = true;
			$scope.dateMortalityDiv = true;
			$scope.weekMortalityDiv = true;
		}else{
			$scope.cullDeathDiv = false;
			$scope.setStandardData.SettingData.mortality_type = "";
			if ($scope.setStandardData.SettingData.mortality_type == "") {
				$scope.dateMortalityDiv = true;
				$scope.weekMortalityDiv = true;
			}else{

			};
			if ($scope.setStandardData.SettingData.mortality_type == 1) {
				$scope.dateMortalityDiv = false;
				$scope.weekMortalityDiv = true;
			}else if ($scope.setStandardData.SettingData.mortality_type == 2) {
				$scope.dateMortalityDiv = true;
				$scope.weekMortalityDiv = false;
			}else{
				$scope.dateMortalityDiv = true;
				$scope.weekMortalityDiv = true;
			};
		}     
	}

	//判断标准是日龄还是周龄
	$scope.judgeMortalityType = function(sku){
			$scope.setStandardData.dayData = [];
			$scope.setStandardData.weekData = [];
			//判断展示的div
			if ($scope.cullDeathDiv) {
				$scope.dateMortalityDiv = true;
				$scope.weekMortalityDiv = true;
			}else{
				if (sku == "1" || $scope.setStandardData.SettingData.mortality_type == 1) {
					$scope.dateMortalityDiv = false;
					$scope.weekMortalityDiv = true;
				}else if (sku == "2" || $scope.setStandardData.SettingData.mortality_type == 2) {
					$scope.dateMortalityDiv = true;
					$scope.weekMortalityDiv = false;
				}else{
					$scope.dateMortalityDiv = true;
					$scope.weekMortalityDiv = true;
				}
			}

			if ($scope.setStandardData.SettingData.mortality_type == "1") {
				var dateArr = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49];
				var valueArr = [0.06,0.14,0.22,0.30,0.38,0.46,0.54,0.60,0.65,0.71,0.77,0.83,0.89,0.95,1.00,1.07,1.14,1.21,1.28,1.35,1.42,1.50,1.57,1.64,1.71,1.78,1.85,1.92,2.00,2.07,2.15,2.23,2.31,2.39,2.47,2.55,2.70,2.85,3.00,3.15,3.30,3.45,3.60,3.75,3.90,4.05,4.20,4.35,4.50,4.65];
				$scope.setStandardData.dayData = biz_common_getStandardDayData(dateArr,valueArr);
			}else{
				var dateArr = [1,2,3,4,5,6,7];
				var valueArr = [0.60,1.00,1.50,2.00,2.55,3.6,4.5];
				$scope.setStandardData.weekData = biz_common_getStandardWeekData(dateArr,valueArr);
			}

			if ($scope.setStandardData.SettingData.cum_mortality.length == 50) {
				$scope.setStandardData.dayData = $scope.setStandardData.SettingData.cum_mortality;
			}else if ($scope.setStandardData.SettingData.cum_mortality.length == 7) {
				$scope.setStandardData.weekData = $scope.setStandardData.SettingData.cum_mortality;
			}

			// app_confirm('是否使用系统推荐数据?','提示',null,function(buttonIndex){
	  //          if(buttonIndex == 2){
			// 	    //判断展示的默认数据
			// 		if ($scope.setStandardData.SettingData.mortality_type == "1") {
			// 			var dateArr = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49];
			// 			var valueArr = [0.06,0.14,0.22,0.30,0.38,0.46,0.54,0.60,0.65,0.71,0.77,0.83,0.89,0.95,1.00,1.07,1.14,1.21,1.28,1.35,1.42,1.50,1.57,1.64,1.71,1.78,1.85,1.92,2.00,2.07,2.15,2.23,2.31,2.39,2.47,2.55,2.70,2.85,3.00,3.15,3.30,3.45,3.60,3.75,3.90,4.05,4.20,4.35,4.50];
			// 			$scope.setStandardData.dayData = biz_common_getStandardDayData(dateArr,valueArr);
			// 		}else{
			// 			var dateArr = [1,2,3,4,5,6,7];
			// 			var valueArr = [0.60,1.00,1.50,2.00,2.55,3.6,4.5];
			// 			$scope.setStandardData.weekData = biz_common_getStandardWeekData(dateArr,valueArr);
			// 		}
	  //          }else{
	  //          		if ($scope.setStandardData.SettingData.mortality_type == "1") {
			// 			for (var i = 0; i <= 49; i++) {
			// 				$scope.setStandardData.dayData.push({
			// 					"dayAge": i,
			// 			        "cum_rate": 0,
			// 			        "cum_alert": 0
			// 				})
			// 			}
			// 		}else{
			// 			for (var i = 1; i <= 7; i++) {
			// 				$scope.setStandardData.weekData.push({
			// 					"weekAge": i,
			// 			        "cum_rate": 0,
			// 			        "cum_alert": 0
			// 				})
			// 			}
			// 		}
			// 		if ($scope.setStandardData.SettingData.cum_mortality.length == 50) {
			// 			$scope.setStandardData.dayData = $scope.setStandardData.SettingData.cum_mortality;
			// 		}else if ($scope.setStandardData.SettingData.cum_mortality.length == 7) {
			// 			$scope.setStandardData.weekData = $scope.setStandardData.SettingData.cum_mortality;
			// 		}
	  //          }
	  //          alert(3);
	  //       });
	}

	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.setStandardData.FarmId   
		};
		Sparraw.ajaxPost('standard/SettingQuery_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				$scope.setStandardData.SettingData = data.ResponseDetail.SettingData;
				if ($scope.setStandardData.SettingData.farm_standard == ""||
					$scope.setStandardData.SettingData.farm_standard == 10004 || 
					$scope.setStandardData.SettingData.farm_standard == 10005 ) {
					$scope.cullDeathDiv = true;
				}else{
					$scope.cullDeathDiv = false;
					if ($scope.setStandardData.SettingData.cum_mortality.length == 50) {
						$scope.setStandardData.dayData = $scope.setStandardData.SettingData.cum_mortality;
					}else if ($scope.setStandardData.SettingData.cum_mortality.length == 7) {
						$scope.setStandardData.weekData = $scope.setStandardData.SettingData.cum_mortality;
					}

					//判断展示的div
					if ($scope.cullDeathDiv) {
						$scope.dateMortalityDiv = true;
						$scope.weekMortalityDiv = true;
					}else{
						if ($scope.setStandardData.SettingData.mortality_type == 1) {
							$scope.dateMortalityDiv = false;
							$scope.weekMortalityDiv = true;
						}else if ($scope.setStandardData.SettingData.mortality_type == 2) {
							$scope.dateMortalityDiv = true;
							$scope.weekMortalityDiv = false;
						}else{
							$scope.dateMortalityDiv = true;
							$scope.weekMortalityDiv = true;
						}
					}

				}

			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.inquire();

	$scope.save = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		if ($scope.setStandardData.SettingData.farm_standard == "") {
			return app_alert("请输入正确的数据");
		}else{

		}
		//将所选标准的值放入到传输的对象
		if ($scope.setStandardData.SettingData.farm_standard == 10004 || 
			$scope.setStandardData.SettingData.farm_standard == 10005 ) {
			$scope.setStandardData.SettingData.need_mortality = "N";
			delete $scope.setStandardData.SettingData.mortality_type;
			delete $scope.setStandardData.SettingData.cum_mortality;
		}else{
			$scope.setStandardData.SettingData.need_mortality = "Y";
			if ($scope.setStandardData.SettingData.mortality_type == "1") {//判断是周龄还是日龄
				for (var i = 0; i < $scope.setStandardData.dayData.length; i++) {
					if ($scope.setStandardData.dayData[i].cum_rate == 0 || 
						isNaN($scope.setStandardData.dayData[i].cum_rate)) {
						console.log($scope.setStandardData.dayData[i].cum_rate);
						return app_alert("请输入正确的数据");
					}else{

					}
				}
				$scope.setStandardData.SettingData.cum_mortality = $scope.setStandardData.dayData;
			}else if ($scope.setStandardData.SettingData.mortality_type == "2") {
				for (var i = 0; i < $scope.setStandardData.weekData.length; i++) {
					if ($scope.setStandardData.weekData[i].cum_rate == 0 || 
						isNaN($scope.setStandardData.weekData[i].cum_rate)) {
						console.log($scope.setStandardData.weekData[i].cum_rate);
						return app_alert("请输入正确的数据");
					}else{

					}
				}
				$scope.setStandardData.SettingData.cum_mortality = $scope.setStandardData.weekData;
			}else{
				return app_alert("请输入正确的数据");
			}
			//去小数
			var TempCumAlertArr = [];
			var TempCumRateArr = [];
			for (var i = 0; i < $scope.setStandardData.SettingData.cum_mortality.length; i++) {
				TempCumAlertArr.push(parseFloat($scope.setStandardData.SettingData.cum_mortality[i].cum_alert).toFixed(2));
				TempCumRateArr.push(parseFloat($scope.setStandardData.SettingData.cum_mortality[i].cum_rate).toFixed(2));
			}
			for (var i = 0; i < $scope.setStandardData.SettingData.cum_mortality.length; i++) {
				$scope.setStandardData.SettingData.cum_mortality[i].cum_alert = TempCumAlertArr[i];
				$scope.setStandardData.SettingData.cum_mortality[i].cum_rate = TempCumRateArr[i];
			}
			TempCumAlertArr = [];
			TempCumRateArr = [];
		}

		$scope.setStandardData.SettingData.Farm_Id = $scope.sparraw_user_temp.farminfo.id;
		var params = {
			"SettingData":$scope.setStandardData.SettingData
		};
		Sparraw.ajaxPost('standard/SettingSave_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				Sparraw.myNotice("保存成功");
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}
})
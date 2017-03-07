 angular.module('myApp.prodReco', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//生产记录
.controller("prodRecoCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setPortrait(true,true);

	$scope.setData = function(){
		$scope.prodRecoData = {
			"FarmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			
			"selectAgeArr":[],
			"DataInfo":[]
		}
	}

	$scope.inquire = function(){
		var params = {
			"FarmBreedId"     : $scope.prodRecoData.FarmBreedId
		};
		Sparraw.ajaxPost('dataInput/queryDataInput_v3.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
	            $scope.prodRecoData.DataInfo = data.ResponseDetail.DataInfo;
	            var TempArr = [];
				for (var i = 0; i < $scope.prodRecoData.DataInfo.length; i++) {
					TempArr.push($scope.prodRecoData.DataInfo[i].CurDayAge);
				}
				for (var i = 0; i < TempArr.length; i++) {
					console.log(TempArr[i]);
					var TempArr2 = [];
					for (var j = 0; j <= TempArr[i]; j++) {
						TempArr2.push({
							"key":j,
							"value":j + "日"
						});
					}
					$scope.prodRecoData.selectAgeArr.push(TempArr2);
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.judgeSaveColor = function(item){
		if (item.HouseBreedStatus == "01") {
			return "{background:'#46AE58'}";
		}else{
			return "{background:'#AEAEAE'}";
		};
	}

	$scope.saveFun = function(item){
		if (item.HouseBreedStatus != "01") {
			if (item.HouseBreedStatus == "00") {
				return app_alert("该栋舍未入雏，无法保存。");
			}else if (item.HouseBreedStatus == "02") {
				return app_alert("该栋舍已出栏，无法保存。");
			}
		}else{

		}


		if ($scope.sparraw_user_temp.Authority.DailyInput === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		var params = {
			"HouseBreedId":item.HouseBreedId,
	        "HouseId":item.HouseId,
	        "dataInput":{
	             "day_age":item.dataInput.day_age,
	             "death_num":item.dataInput.death_num,
	             "culling_num":item.dataInput.culling_num,
	             "daily_feed":item.dataInput.daily_feed,
	             "daily_water":item.dataInput.daily_water,
	             "daily_weight":item.dataInput.daily_weight
	        }
		};
		

		if (item.culling_num == "" || !item.culling_num) {item.culling_num = 0};
		if (item.daily_feed == "" || !item.daily_feed) {item.daily_feed = 0};
		if (item.daily_water == "" || !item.daily_water) {item.daily_water = 0};
		if (item.daily_weight == "" || !item.daily_weight) {item.daily_weight = 0};
		if (item.death_num == "" || !item.death_num) {item.death_num = 0};

		
		Sparraw.ajaxPost('dataInput/saveDataInput_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("保存成功！");
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}


	$scope.judgeHouse = function(item){
		var params = {
			"HouseBreedId":item.HouseBreedId,
			"HouseId":item.HouseId,
			"Age":item.dataInput.day_age
		};

		console.log(params);
		console.log("-----------------------------");
		Sparraw.ajaxPost('dataInput/queryDataInput_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				console.log(data);
				console.log(data.ResponseDetail.CurDayAge);

				//item.dataInput.day_age = data.ResponseDetail.CurDayAge;
	           	item.dataInput.death_num = data.ResponseDetail.dataInput.death_num;
	            item.dataInput.culling_num = data.ResponseDetail.dataInput.culling_num;
	            item.dataInput.daily_feed = data.ResponseDetail.dataInput.daily_feed;
	            item.dataInput.daily_water = data.ResponseDetail.dataInput.daily_water;
	            item.dataInput.daily_weight = data.ResponseDetail.dataInput.daily_weight;
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}

	$scope.goDailyDay = function(){
		selectBackPage.reportingBack = "prodReco";
		$state.go("dailyDay");
	}


	setTimeout(function() {
		$scope.setData();
		setTimeout(function() {
			$scope.inquire();
		}, 1500);
	}, 500);


})
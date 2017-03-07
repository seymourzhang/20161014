 angular.module('myApp.breedAffirm', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//出栏确认
.controller("breedAffirmCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.breeAffiData = {
		"farmId":$scope.sparraw_user_temp.farminfo.id,//int型，农场id√
		"farmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId,


		"settleHouse":[],
		"SettleFarm":[],
		"judgeSaveState":false
    }

    $scope.clickDateInput = function(item){
    	if (item.breedStatus == "02") {
    		return app_alert("已出栏栋舍无法修改日期");
    	}
    	for (var i = 0; i < $scope.breeAffiData.settleHouse.length; i++) {
    		if ($scope.breeAffiData.settleHouse[i].houseId == item.houseId) {
    			Sparraw.openDatePicker("breeAffiData.settleHouse[" + i + "].marketDate","$scope.setShowDateCallBack();");
    		}
    	}
    };

    $scope.setShowDateCallBack = function(){

    };


    $scope.getMarketAvg = function(item){

    	var TempAvgWeight = parseFloat(item.marketWeight / item.marketNum);
    	item.marketAvgWeight = TempAvgWeight.toFixed(2);

    	if (!Common_isNum(item.marketAvgWeight) || !isFinite(item.marketAvgWeight)) {
			item.marketAvgWeight = 0;
		}else{

		}
    }


    $scope.inquire = function(){

    	var params = {};
    	if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "02") {
    		params = {
				"farmId"       :  $scope.breeAffiData.farmId,
	       		"farmBreedId"  :  $scope.breeAffiData.farmBreedId
			};
    	}else{
    		params = {
				"farmId"       :  $scope.breeAffiData.farmId,
	       		"farmBreedId"  :  0
			};
    	}


    	Sparraw.ajaxPost('farmManage/settleChickQuery.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					$scope.breeAffiData.settleHouse = data.ResponseDetail.settleHouse;
					$scope.breeAffiData.SettleFarm = data.ResponseDetail.SettleFarm;
    				if ($scope.breeAffiData.SettleFarm.SaleSumNum == 0) {$scope.breeAffiData.SettleFarm.SaleSumNum = "";};
    				if ($scope.breeAffiData.SettleFarm.SaleSumWeight == 0) {$scope.breeAffiData.SettleFarm.SaleSumWeight = "";};
    				if ($scope.breeAffiData.SettleFarm.SalePrice == 0) {$scope.breeAffiData.SettleFarm.SalePrice = "";};
				}else if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
		});

    }

    $scope.inquire();


    $scope.judgeEnter = function(item){
    	if (item.breedStatus == "02") {
    		return "{'background':'#AEAEAE'}";
    	}else{

    	}
    }

    $scope.judgeModify = function(item){
    	if (item.breedStatus == "01") {
    		return "{'background':'#AEAEAE'}";
    	}else{

    	}
    }

    $scope.judgeSave = function(){

    	var stateA = [];//00
		var stateB = [];//01
		var stateC = [];//02
		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "00") {
				stateA.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "01") {
				stateB.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "02") {
				stateC.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}
			//console.log($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
		}
		//判断能否保存
		if (stateC.length != 0) {
			document.getElementById('SaveBtn').style.background = "#46AE58";
		}else{
			if ($scope.breeAffiData.judgeSaveState) {
				document.getElementById('SaveBtn').style.background = "#46AE58";
			}else{
				document.getElementById('SaveBtn').style.background = "#AEAEAE";
			}
		};
    }


    $scope.judgeDateOptional = function(item){
    	if (item.breedStatus == "02") {
    		return "{'color':'#AEAEAE'}";
    	}else{

    	}
    }



    $scope.breeAffiFun = function(item){

    	if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};


    	if (item.breedStatus == "02") {
    		return Sparraw.myNotice(item.houseName + "栋已出栏");
    	}else{

    	}
    	if (item.marketDate == "" || !item.marketDate) {return app_alert("请正确选择日期。");};
    	if (item.marketNum == "" || !item.marketNum) {return app_alert("请正确填写出栏数。");};
    	if (item.marketWeight == "" || !item.marketWeight) {return app_alert("请正确填写毛鸡重量。");};

    	if (item.marketAvgWeight >= 0.5 && item.marketAvgWeight <= 3.5) {

		}else{
			return app_alert("只均重不得低于0.5且不能大于3.5");
		}

    	app_confirm('1.出栏后环控报警将关闭。\n2.出栏确认后出栏日期将无法修改。',null,null,function(buttonIndex){
			if(buttonIndex == 1){

			}else if(buttonIndex == 2){


				var params = {
					"settleFlag":"house",
					"farmBreedId":$scope.breeAffiData.farmBreedId,
					"houseId":item.houseId,
					"houseName":item.houseName,
					"houseBreedId":item.houseBreedId,
					"marketNum":item.marketNum,
					"marketWeight":item.marketWeight,
					"marketAvgWeight":item.marketAvgWeight,
					"marketDate":item.marketDate,
				};

				Sparraw.ajaxPost('farmManage/settleChickDeal.action', params, function(data){
					if (data.ResponseDetail.Result == "Success") {
						Sparraw.myNotice("出栏成功！");
						//重新获取服务器最新数据
    					biz_common_getLatestData(null,null);
    					$scope.breeAffiData.judgeSaveState = true;
    					$scope.judgeSave();
    					$scope.inquire();
					}else if (data.ResponseDetail.Result == "Fail") {
						app_alert(data.ResponseDetail.ErrorMsg);
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
				});

			}
		});	
    }



    $scope.modifyFun = function(item){
    	if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};


    	if (item.breedStatus == "01") {
    		return app_alert("出栏后即可进行修改。");
    	}else{

    	}

    	if (item.marketDate == "" || !item.marketDate) {return app_alert("请正确选择日期。");};
    	if (item.marketNum == "" || !item.marketNum) {return app_alert("请正确填写出栏数。");};
    	if (item.marketWeight == "" || !item.marketWeight) {return app_alert("请正确填写毛鸡重量。");};
    	if (item.marketAvgWeight >= 0.5 && item.marketAvgWeight <= 3.5) {

		}else{
			return app_alert("只均重不得低于0.5且不能大于3.5");
		}

    	var params = {
			"farmBreedId":$scope.breeAffiData.farmBreedId  ,
			"houseId":item.houseId                         ,
			"houseName":item.houseName                     ,
			"houseBreedId":item.houseBreedId               ,
			"marketNum":item.marketNum                     ,
			"marketWeight":item.marketWeight
		};

		Sparraw.ajaxPost('farmManage/settleChickUpdate.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.inquire();
				Sparraw.myNotice("修改成功！");
				$scope.breeAffiData.judgeSaveState = true;
			}else if (data.ResponseDetail.Result == "Fail") {
				app_alert(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    $scope.saveClearing = function(){
    	if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};


    	var stateA = [];//00
		var stateB = [];//01
		var stateC = [];//02
		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "00") {
				stateA.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "01") {
				stateB.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "02") {
				stateC.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}
		}



		//判断能否保存
		if (stateC.length != 0) {

		}else{
			if ($scope.breeAffiData.judgeSaveState) {

			}else{
				return app_alert("出栏后才能进行保存。");
			}
		}

		if ($scope.breeAffiData.SettleFarm.SalePrice >= 5 && $scope.breeAffiData.SettleFarm.SalePrice <= 15) {

		}else{
			return app_alert("结算单价不得低于5且不能大于15");
		}


    	if ($scope.breeAffiData.SettleFarm.BHName == "" || !$scope.breeAffiData.SettleFarm.BHName) {return app_alert("请正确填写屠宰厂家。");};
    	if ($scope.breeAffiData.SettleFarm.SaleSumMoney == "" || !$scope.breeAffiData.SettleFarm.SaleSumMoney) {return app_alert("请正确填写结算总额。");};

    	var params = {
			"settleFlag":"farm",
			"farmBreedId":$scope.breeAffiData.farmBreedId,
			"BHName":$scope.breeAffiData.SettleFarm.BHName,
			"SaleSumMoney":$scope.breeAffiData.SettleFarm.SaleSumMoney
		};
		Sparraw.ajaxPost('farmManage/settleChickDeal.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				//重新获取服务器最新数据
				biz_common_getLatestData(null,null);
				Sparraw.myNotice("保存成功！");
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    
    $scope.judgeSalePrice = function(){
    	var TempAvgWeight = parseFloat($scope.breeAffiData.SettleFarm.SaleSumMoney / $scope.breeAffiData.SettleFarm.SaleSumWeight);
    	$scope.breeAffiData.SettleFarm.SalePrice = TempAvgWeight.toFixed(2);

    	if (!Common_isNum($scope.breeAffiData.SettleFarm.SalePrice) || !isFinite($scope.breeAffiData.SettleFarm.SalePrice)) {
			$scope.breeAffiData.SettleFarm.SalePrice = 0;
		}else{

		}
    }

})






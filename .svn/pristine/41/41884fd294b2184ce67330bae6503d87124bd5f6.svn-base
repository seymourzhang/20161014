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
		"initial":"",//初始标准
		"judgeFirstTime":true,
		"SettingData":{
			"Farm_Id":$scope.sparraw_user_temp.farminfo.id,//保存用FarmId
            "farm_standard":"",//保存标准类型
            "need_mortality":"N",//varchar型，是否需要录入死淘
            "need_manage_alert":"N",//varchar型，是否需要管理报警
            "cum_mortality":{//累计死淘率的数值
                "week1":"",
                "week2":"",
                "week3":"",
                "week4":"",
                "week5":"",
                "week6":"",
                "week7":""
             },
            "manage_alert":{//管理报警的数值
                "Max_Mortality":"",
                "Max_feed":"",
                "Min_body_weight":""
            }
        }    
	}

	$scope.cullDeathDiv = true;
	$scope.cullDeathDataDiv = true;
	$scope.alarmDiv = true;




	$scope.queryStandard = function(){
		if ($scope.setStandardData.SettingData.farm_standard == ""||
			$scope.setStandardData.SettingData.farm_standard == 10004 || 
			$scope.setStandardData.SettingData.farm_standard == 10005 ) {
			$scope.setStandardData.SettingData.need_mortality = "N";
			$scope.cullDeathDiv = true;
			$scope.cullDeathDataDiv = true;
		}else{

			if ($scope.setStandardData.SettingData.need_mortality == "N") {
				$scope.setStandardData.SettingData.need_mortality = "N";
				$scope.cullDeathDiv = false;
				$scope.cullDeathDataDiv = true;
			}else{
				$scope.setStandardData.SettingData.need_mortality = "Y";
				$scope.cullDeathDiv = false;
				$scope.cullDeathDataDiv = false;
			}
			
		} 
	}


	//选择的标准
	$scope.judgeStandard = function(){
		$scope.queryStandard();
	}





	//判断是否设定死淘率标准
	$scope.judgeCullDeath = function(sku){
		if (sku == "N" || $scope.setStandardData.SettingData.need_mortality == "N") {
			$scope.cullDeathDataDiv = true;
			$scope.setStandardData.SettingData.need_mortality = "N";
		}else{
			$scope.cullDeathDataDiv = false;
			$scope.setStandardData.SettingData.need_mortality = "Y";
		}
	}

	//判断是否设定报警标准
	$scope.judgeAlarm = function(sku){
		if (sku == "N" || $scope.setStandardData.SettingData.need_manage_alert == "N") {
			$scope.alarmDiv = true;
			$scope.setStandardData.SettingData.need_manage_alert = "N";
		}else{
			$scope.alarmDiv = false;
			$scope.setStandardData.SettingData.need_manage_alert = "Y";
		}
	}




	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.setStandardData.FarmId   
		};
		Sparraw.ajaxPost('standard/SettingQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				for (var i = 0; i < data.ResponseDetail.DetailData.length; i++) {
					$scope.setStandardData.SettingData.cum_mortality = data.ResponseDetail.DetailData[i].manage_alert;
					$scope.setStandardData.SettingData.manage_alert = data.ResponseDetail.DetailData[i].cum_mortality;
					$scope.setStandardData.SettingData.need_mortality = data.ResponseDetail.DetailData[i].need_mortality;
					$scope.setStandardData.SettingData.need_manage_alert = data.ResponseDetail.DetailData[i].need_manage_alert;
					$scope.setStandardData.SettingData.farm_standard = data.ResponseDetail.DetailData[i].farm_standard;
					$scope.setStandardData.initial = $scope.setStandardData.SettingData.farm_standard;
				}


				$scope.setStandardData.SettingData.cum_mortality.week1 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week1);
				$scope.setStandardData.SettingData.cum_mortality.week2 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week2);
				$scope.setStandardData.SettingData.cum_mortality.week3 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week3);
				$scope.setStandardData.SettingData.cum_mortality.week4 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week4);
				$scope.setStandardData.SettingData.cum_mortality.week5 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week5);
				$scope.setStandardData.SettingData.cum_mortality.week6 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week6);
				$scope.setStandardData.SettingData.cum_mortality.week7 = parseFloat($scope.setStandardData.SettingData.cum_mortality.week7);
				
				$scope.setStandardData.SettingData.manage_alert.Max_Mortality = parseFloat($scope.setStandardData.SettingData.manage_alert.Max_Mortality);
				$scope.setStandardData.SettingData.manage_alert.Max_feed = parseFloat($scope.setStandardData.SettingData.manage_alert.Max_feed);
				$scope.setStandardData.SettingData.manage_alert.Min_body_weight = parseFloat($scope.setStandardData.SettingData.manage_alert.Min_body_weight);

				$scope.queryStandard();
				$scope.judgeCullDeath();
				$scope.judgeAlarm();
				console.log($scope.setStandardData.SettingData);
			}else if (data.ResponseDetail.Result == "Fail") {
				$scope.setStandardData.judgeFirstTime = false;
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}
	$scope.inquire();


	$scope.save = function(){

		$scope.setStandardData.SettingData.cum_mortality.week1 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week1);
		$scope.setStandardData.SettingData.cum_mortality.week2 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week2);
		$scope.setStandardData.SettingData.cum_mortality.week3 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week3);
		$scope.setStandardData.SettingData.cum_mortality.week4 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week4);
		$scope.setStandardData.SettingData.cum_mortality.week5 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week5);
		$scope.setStandardData.SettingData.cum_mortality.week6 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week6);
		$scope.setStandardData.SettingData.cum_mortality.week7 = Common_NulltoZero($scope.setStandardData.SettingData.cum_mortality.week7);
		$scope.setStandardData.SettingData.manage_alert.Max_Mortality = Common_NulltoZero($scope.setStandardData.SettingData.manage_alert.Max_Mortality);
		$scope.setStandardData.SettingData.manage_alert.Max_feed = Common_NulltoZero($scope.setStandardData.SettingData.manage_alert.Max_feed);
		$scope.setStandardData.SettingData.manage_alert.Min_body_weight = Common_NulltoZero($scope.setStandardData.SettingData.manage_alert.Min_body_weight);


		var TempProdKeyArr = Common_getKeyArrayFromJson(myConfig.prodStan);
		var TempProdValueArr = Common_GetValueArrayFromJson(myConfig.prodStan);

		var TempInitialName = "";
		var TempstandardName = "";
		for (var i = 0; i < TempProdKeyArr.length; i++) {
			if ($scope.setStandardData.initial == TempProdKeyArr[i]) {
				TempInitialName = TempProdValueArr[i];
			}else if ($scope.setStandardData.SettingData.farm_standard == TempProdKeyArr[i]) {
				TempstandardName = TempProdValueArr[i];
			}
		}

		if (!$scope.setStandardData.judgeFirstTime) {//判断是否为第一次
			var params = {
				"SettingData"  :  $scope.setStandardData.SettingData
			};
			Sparraw.ajaxPost('standard/SettingSave.action', params, function(data){
				if (data.ResponseDetail.Result == "Succ") {
					Sparraw.myNotice("保存成功");
				}else if (data.ResponseDetail.Result == "Fail") {

				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
		}else{
			if ($scope.setStandardData.SettingData.farm_standard != $scope.setStandardData.initial) {
				app_confirm(TempInitialName + "标准已改为" + TempstandardName + "标准，之前数据将被覆盖，请确认。",'提示',null,function(buttonIndex){
		           	if(buttonIndex == 2){
		              	var params = {
							"SettingData"  :  $scope.setStandardData.SettingData
						};
						Sparraw.ajaxPost('standard/SettingSave.action', params, function(data){
							if (data.ResponseDetail.Result == "Succ") {
								Sparraw.myNotice("保存成功");
							}else if (data.ResponseDetail.Result == "Fail") {

							}else{
								Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
							};
						});
		           	}else{
		           		
		           	}
			    });

			}else{
				var params = {
					"SettingData"  :  $scope.setStandardData.SettingData
				};
				Sparraw.ajaxPost('standard/SettingSave.action', params, function(data){
					if (data.ResponseDetail.Result == "Succ") {
						Sparraw.myNotice("保存成功");
					}else if (data.ResponseDetail.Result == "Fail") {

					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
				});
			}
		}	
	}
})

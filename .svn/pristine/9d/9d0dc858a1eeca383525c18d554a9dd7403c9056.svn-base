 angular.module('myApp.collection', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 // 补录界面
.controller("collectionCtrl",function($scope, $state, $http, $ionicPopup, $stateParams, $ionicScrollDelegate, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setPortrait(true,true);
	//默认搜索栋舍
	if (persistentData.dataEntryReceiveHouse == "") {
		persistentData.dataEntryReceiveHouse = $scope.sparraw_user_temp.userinfo.houses[0];
	}else{

	};
	
	//数据源
	$scope.dailyReportData = {
		"HouseBreedId"       :  ""  ,  //栋舍饲养批次Id
        "HouseId"            :  ""  ,  //栋舍Id
        "HouseName"          :  ""  ,  //栋舍名字
        "CurDayAge"          :  ""  ,  //当前日龄
        "cur_amount"         :  ""  ,  //存栏数量
        "std_cd_rate"        :  ""  ,  //警戒死淘率
        "original_amount"    :  ""  ,  //入雏数量
        "atu_cd_rate"        :  ""  ,  //死淘率
        //3月29日增加
        "culling_acc"        :  ""  ,  	//累计死淘数量
        "acc_cd_rate"        :  ""  ,	//累计死淘率
        "acc_feed"           :  ""  ,	//累计饲料消耗
        "acc_water"          :  ""  ,	//累计耗水

        "dataInput":[
            {
             "day_age"       :  ""  ,  //对应日龄
             "culling_all"   :  ""  ,  //总死淘数量
             "culling_acc"   :  ""  ,  //累计死淘数量
             "acc_cd_rate"   :  ""  ,  //累计死淘率
             "daily_feed"    :  ""  ,  //日饲料消耗量
             "acc_feed"      :  ""  ,  //累计饲料消耗
             "daily_weight"  :  ""  ,  //均重
             //3月29日增加
             "death_pm"      :  ""  ,  //当日死亡量
             "culling_pm"    :  ""  ,  //当日淘汰量
             "daily_water"   :  ""     //日均耗水

            }],
        "intoYoungBtnStatus"       :  false  ,
        "slaughterBtnStatus"       :  false  ,
        "slaughterDate"      :  "",  //出栏当天时间
        "selectHouseId"        :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId) //选择的栋舍
    }

    if (document.documentElement.clientWidth == 320) {
		
		//document.getElementById('slaughterButton').style.left = 14 + "rem";

	}else{
		
	}






    $scope.judgeHouse = function(item){

    	if (!item || item == "") {
    		item = $scope.sparraw_user_temp.userinfo.houses[0];
    	}else{
    		item = JSON.parse(item);
    	}
    	persistentData.dataEntryReceiveHouse = item;

    	console.log(item);
    	persistentData.dailySelectHouse = JSON.stringify(item);

    	switch (item.HouseBreedStatus){
		  case "00"://未入雏
		  		$scope.dailyReportData.HouseBreedId  =  ""  ;
	    		$scope.dailyReportData.HouseId       =  ""  ;
	    		$scope.dailyReportData.CurDayAge     =  ""  ;
	    		$scope.dailyReportData.culling_acc =  "" ;
				$scope.dailyReportData.acc_cd_rate =  "" ;
				$scope.dailyReportData.acc_feed    =  "" ;
				$scope.dailyReportData.acc_water   =  "" ;
				$scope.dailyReportData.original_amount = "-" ;
				$scope.dailyReportData.cur_amount = "-" ;
	    		$scope.dailyReportData.dataInput     =  []  ;

	    		Sparraw.myNotice("暂无数据，请先入雏");
	    		$scope.saveBtn = false;
	    		//document.getElementById('slaughterButton').style.background = "#ECECEC";

		    break;
		  case "01"://已入雏 未出栏
		  		//查询日报填制信息
				var params = {
							"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
							"HouseId"       :  persistentData.dataEntryReceiveHouse.HouseId      
				};
				Sparraw.ajaxPost('dataInput/queryDR.action', params, function(data){
					if (data.ResponseDetail.Result === "Fail") {
						return app_alert("暂未查询到日报信息！");	
					};

					$scope.dailyReportData.CurDayAge  =  data.ResponseDetail.CurDayAge  ;
					$scope.dailyReportData.cur_amount =  data.ResponseDetail.cur_amount ;
					$scope.dailyReportData.original_amount = data.ResponseDetail.original_amount;
					$scope.dailyReportData.culling_acc =  data.ResponseDetail.culling_acc ;
					$scope.dailyReportData.acc_cd_rate =  data.ResponseDetail.acc_cd_rate ;
					$scope.dailyReportData.acc_feed =  data.ResponseDetail.acc_feed ;
					$scope.dailyReportData.acc_water =  data.ResponseDetail.acc_water ;
					$scope.dailyReportData.dataInput  =  data.ResponseDetail.dataInput  ;
					$scope.saveBtn = true;
					//滚动到顶部
					$ionicScrollDelegate.scrollBottom();

					//遍历出来判断是否为0，为空的话赋空
					/*for (var i = 0; i < $scope.dailyReportData.dataInput.length; i++) {
						$scope.dailyReportData.dataInput[i].acc_cd_rate = parseFloat($scope.dailyReportData.dataInput[i].acc_cd_rate);
						$scope.dailyReportData.dataInput[i].acc_cd_rate = $scope.dailyReportData.dataInput[i].acc_cd_rate.toFixed(2);
					};*/
				});
		    break;
		  case "02":// 已入雏 已出栏
		    	//查询日报填制信息
				var params = {
							"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
							"HouseId"       :  persistentData.dataEntryReceiveHouse.HouseId      
				};
				Sparraw.ajaxPost('dataInput/queryDR.action', params, function(data){
					if (data.ResponseDetail.Result === "Fail") {
						return app_alert("暂未查询到日报信息！");	
					};
					
					$scope.dailyReportData.CurDayAge  =  data.ResponseDetail.CurDayAge  ;
					$scope.dailyReportData.cur_amount =  data.ResponseDetail.cur_amount ;
					$scope.dailyReportData.original_amount = data.ResponseDetail.original_amount;
					$scope.dailyReportData.culling_acc =  data.ResponseDetail.culling_acc ;
					$scope.dailyReportData.acc_cd_rate =  data.ResponseDetail.acc_cd_rate ;
					$scope.dailyReportData.acc_feed =  data.ResponseDetail.acc_feed ;
					$scope.dailyReportData.acc_water =  data.ResponseDetail.acc_water ;
					$scope.dailyReportData.dataInput  =  data.ResponseDetail.dataInput  ;
					$scope.saveBtn = false;
					//滚动到顶部
					$ionicScrollDelegate.$getByHandle('page').anchorScroll();

					
				},function(data){
					console.log("9999999行出错了😅");
				});
		    break;
		}
	}
    //进入页面立即搜索
    $scope.judgeHouse();

    //当前日龄加深
    /*$scope.judgeDayAge = function(obj){
    	if (obj == $scope.dailyReportData.CurDayAge) {
    		return "{background:'rgba(28, 85, 33, 1)'}";
    	}else{	
    		return "{background:'rgba(255, 255, 255, 1)'}";
    	};
    }*/

    //隐藏时间未到的数据
    $scope.hiddenEmptyData = function(obj){
    	if (obj > $scope.dailyReportData.CurDayAge) {
    		return "{display:'none'}";
    	}else{
    		//每隔七天添加下划线
    		if (obj % 7 == 0 && obj != 0) {
	    		return "{'border-bottom':'solid 1px #606060'}";
	    	}else{	
	    		return "{'border-bottom':'solid 1px #D0D0D0'}";
	    	};
    	};
    }
    
    $scope.save = function(){

    	if ($scope.sparraw_user_temp.Authority.DailyInput === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		//遍历出来判断是否为空，为空的话赋0
		for (var i = 0; i < $scope.dailyReportData.dataInput.length; i++) {
			$scope.dailyReportData.dataInput[i].death_pm = Common_NulltoZero($scope.dailyReportData.dataInput[i].death_pm);
			$scope.dailyReportData.dataInput[i].culling_pm = Common_NulltoZero($scope.dailyReportData.dataInput[i].culling_pm);
			$scope.dailyReportData.dataInput[i].daily_feed = Common_NulltoZero($scope.dailyReportData.dataInput[i].daily_feed);
			$scope.dailyReportData.dataInput[i].daily_water = Common_NulltoZero($scope.dailyReportData.dataInput[i].daily_water);
			$scope.dailyReportData.dataInput[i].daily_weight = Common_NulltoZero($scope.dailyReportData.dataInput[i].daily_weight);
		};

		var params = {
				"HouseBreedId"  :  persistentData.dataEntryReceiveHouse.HouseBreedBatchId  ,
		        "HouseId"       :  persistentData.dataEntryReceiveHouse.HouseId            ,
		        "dataInput"     :  $scope.dailyReportData.dataInput
		};
    	Sparraw.ajaxPost('dataInput/saveDR.action', params, function(data){
			if (data.ResponseDetail.ErrorMsg == null) {
	   			Sparraw.myNotice("保存成功");
	   			//$scope.judgeHouse();
		    }else {
		   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		    };
	    },null,60000);
	};



	$scope.getFocus = function(){
		$scope.chooseDiv = true;
		document.getElementById('blankDiv').style.height = 3 + 'rem';
	}

	$scope.loseBlur = function(item,judgeType){
		$scope.chooseDiv = false;
		document.getElementById('blankDiv').style.height = 9 + 'rem';
	}


	
})
angular.module('myApp.farmRegistered', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 农场注册
.controller("farmRegisteredCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.querySelectData = function(para){
		var temp1;
		if(para == 'add2'){
			temp1 = $scope.sparraw_user_temp.farminfo.address1;
		}else if(para == 'add3'){
			temp1 = $scope.sparraw_user_temp.farminfo.address2;
		}else if(para == 'add4'){
			temp1 = $scope.sparraw_user_temp.farminfo.address3;
		}else{
			return;
		}
		var params = {
				"CodeType":"ChinaRegion",
				"Para1":temp1
				} ;
		Sparraw.ajaxPost('sys/code/getData.action', params, function(data){
	    	if(para == 'add2'){
				$scope.myConfig.address2 = data.ResponseDetail.ResultData;
			}else if(para == 'add3'){
				$scope.myConfig.address3 = data.ResponseDetail.ResultData;
			}else if(para == 'add4'){
				$scope.myConfig.address4 = data.ResponseDetail.ResultData;
			}
			
			console.log(data.ResponseDetail.ResultData);
	    });
	}

	$scope.inintSelectData = function(){
		var params1 = {
				"CodeType":"ChinaRegion",
				"Para1":$scope.sparraw_user_temp.farminfo.address1
				} ;
		Sparraw.ajaxPost('sys/code/getData.action', params1, function(data){
			$scope.myConfig.address2 = data.ResponseDetail.ResultData;
			var params2 = {
				"CodeType":"ChinaRegion",
				"Para1":$scope.sparraw_user_temp.farminfo.address2
				} ;
			Sparraw.ajaxPost('sys/code/getData.action', params2, function(data){
				$scope.myConfig.address3 = data.ResponseDetail.ResultData;
				var params3 = {
						"CodeType":"ChinaRegion",
						"Para1":$scope.sparraw_user_temp.farminfo.address3
						} ;
				Sparraw.ajaxPost('sys/code/getData.action', params3, function(data){
					$scope.myConfig.address4 = data.ResponseDetail.ResultData;
			    });
		    });
	    });
	};

	//true-隐藏，false-显示
	//判断是否有农场信息

	console.log($scope.sparraw_user_temp.hasOwnProperty("farminfo"));


	if ($scope.sparraw_user_temp.hasOwnProperty("farminfo")) {//有农场信息
		$scope.registerTitle   =  true   ;
		$scope.saveAddBtn      =  true   ;

		$scope.landingbackBtn  =  false  ;
		$scope.landingTitle    =  false  ;
		$scope.saveUpdateBtn   =  false  ;

		$scope.backBtn    =  false  ;
		$scope.cancelBtn  =  true   ;
		$scope.visible    =  false  ;
		$scope.saveUpdateBtn =  true   ;

		$scope.sheerDiv   =  false  ;
		$scope.inintSelectData();

		console.log("1");
	}else{//无农场信息
		$scope.registerTitle   =  false  ;
		$scope.saveAddBtn      =  false  ;

		$scope.landingbackBtn  =  true   ;
		$scope.landingTitle    =  true   ;

		$scope.saveUpdateBtn   =  true   ;
		$scope.backBtn    =  true  ;
		$scope.cancelBtn  =  true   ;
		$scope.visible    =  true  ;

		$scope.sheerDiv   =  true  ;
		console.log("0");
	};

	//通过服务器取值
	$scope.select = function(para){		
		if(para == "Region1"){
			$scope.sparraw_user_temp.farminfo.address2 = '';
			$scope.sparraw_user_temp.farminfo.address3 = '';
			$scope.sparraw_user_temp.farminfo.address4 = '';

			$scope.querySelectData('add2');
		}else if(para == "Region2"){
			$scope.sparraw_user_temp.farminfo.address3 = '';
			$scope.sparraw_user_temp.farminfo.address4 = '';
			
			$scope.querySelectData('add3');
		}else if(para == "Region3"){
			$scope.sparraw_user_temp.farminfo.address4 = '';
			
			$scope.querySelectData('add4');
		}

	}

	$scope.judgeRaise = function(){
		if(this.sparraw_user_temp.farminfo){
			if (this.sparraw_user_temp.farminfo.feedtype == 3) {
				$scope.farmingSize = true;
			}else {
				$scope.farmingSize = false;
			}
		}else{
			//$scope.farmingSize = true;
			console.log("用户暂未设置农场信息")
		}
		
	}
	
	
	$scope.judgeRaise2 = function(){
		if(this.sparraw_user_temp.farminfo){
			if (this.sparraw_user_temp.farminfo.businessModle == 1) {
				$scope.farmingSize2 = true;
			}else {
				$scope.farmingSize2 = false;
			}
		}else{
			console.log("用户暂未设置农场信息")
		}
		
	}


	$scope.judgeRaise();
	$scope.judgeRaise2();


	$scope.saveUpdate = function(){
	    
	    /* 校验信息*/
	    var required = [$scope.sparraw_user_temp.farminfo.name,$scope.sparraw_user_temp.farminfo.address1 ,$scope.sparraw_user_temp.farminfo.address2 ,$scope.sparraw_user_temp.farminfo.address3];
	   	if ($scope.sparraw_user_temp.farminfo === undefined || $scope.sparraw_user_temp.farminfo.name === "" || $scope.sparraw_user_temp.farminfo.name === undefined) {
	    	return Sparraw.myNotice('请输入农场名称。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address1 === "" || $scope.sparraw_user_temp.farminfo.address1 === undefined){
	    	return Sparraw.myNotice('请输入农场所在省。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address2 === "" || $scope.sparraw_user_temp.farminfo.address2 === undefined){
	    	return Sparraw.myNotice('请输入农场所在地级市。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address3 === "" || $scope.sparraw_user_temp.farminfo.address3 === undefined){
	    	return Sparraw.myNotice('请输入农场所在县。');
	    };


	   	var params = {
	   			"operate"   :  "UPDATE"  ,
	   			"farmInfo"  :  {
				   				"id"              :  $scope.sparraw_user_temp.farminfo.id              ,
							    "name"            :  $scope.sparraw_user_temp.farminfo.name            , 
							    "address1"        :  $scope.sparraw_user_temp.farminfo.address1        , 
							    "address2"        :  $scope.sparraw_user_temp.farminfo.address2        ,
							    "address3"        :  $scope.sparraw_user_temp.farminfo.address3        ,
							    "address4"        :  $scope.sparraw_user_temp.farminfo.address4        ,
							    // "address5"        :  $scope.sparraw_user_temp.farminfo.address5        ,
							    "feedtype"        :  $scope.sparraw_user_temp.farminfo.feedtype        , 
							    "cageInfo_layer"  :  $scope.sparraw_user_temp.farminfo.cageInfo_layer  , 
							    "cageInfo_row"    :  $scope.sparraw_user_temp.farminfo.cageInfo_row    , 
							    "businessModle"   :  $scope.sparraw_user_temp.farminfo.businessModle   ,
							     "corporation"     :  $scope.sparraw_user_temp.farminfo.corporation     ,
								 "corporation2"     :  $scope.sparraw_user_temp.farminfo.corporation2     ,
							    "feedBreeds"      :  $scope.sparraw_user_temp.farminfo.feedBreeds      ,
							    "house_length"    :  $scope.sparraw_user_temp.farminfo.house_length    , 
							    "house_width"     :  $scope.sparraw_user_temp.farminfo.house_width     ,
							    "house_height"    :  $scope.sparraw_user_temp.farminfo.house_height    ,
							    "feedarea"        :  $scope.sparraw_user_temp.farminfo.feedarea 
	   			}

			};
		    
		    Sparraw.ajaxPost('sys/farm/update.action', params, function(data){
		    	  if (data.ResponseDetail.ErrorMsg == null) {
		    		$scope.sparraw_user_temp.farminfo.id = data.ResponseDetail.farmId;

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	Sparraw.myNotice('保存成功');
			    	if (sparraw_user.profile.user_State) {
			    		$state.go("addbuilding");
			    	}else {
			    		$state.go("infoSafeguard");
			    	};
			    	
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };
		    });
	}



	//编辑事件
	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		$scope.landingbackBtn    =  true  ;
		$scope.visible    =  true  ;
		$scope.sheerDiv   =  true  ;
		$scope.cancelBtn  =  false ;
		$scope.saveUpdateBtn =  false   ;
		Sparraw.myNotice('请编辑');
	}

	//取消事件
	$scope.cancelEvent = function(){
		$scope.landingbackBtn    =  false  ;
		$scope.visible    =  false  ;
		$scope.sheerDiv   =  false  ;
		$scope.cancelBtn  =  true   ;
		$scope.saveUpdateBtn =  true   ;
  	}

  	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};




	$scope.saveAdd = function(){
	    
	    if ($scope.sparraw_user_temp.farminfo === undefined || $scope.sparraw_user_temp.farminfo.name === "" || $scope.sparraw_user_temp.farminfo.name === undefined) {
	    	return Sparraw.myNotice('请输入农场名称。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address1 === "" || $scope.sparraw_user_temp.farminfo.address1 === undefined){
	    	return Sparraw.myNotice('请输入农场所在省。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address2 === "" || $scope.sparraw_user_temp.farminfo.address2 === undefined){
	    	return Sparraw.myNotice('请输入农场所在地级市。');
	    };
	    if ($scope.sparraw_user_temp.farminfo.address3 === "" || $scope.sparraw_user_temp.farminfo.address3 === undefined){
	    	return Sparraw.myNotice('请输入农场所在县。');
	    };
	   

	   	var params = {

			  'name'            : $scope.sparraw_user_temp.farminfo.name             ,
		      'address1'        : $scope.sparraw_user_temp.farminfo.address1         ,
		      'address2'        : $scope.sparraw_user_temp.farminfo.address2         ,
		      'address3'        : $scope.sparraw_user_temp.farminfo.address3         ,
		      'address4'        : $scope.sparraw_user_temp.farminfo.address4         ,
		      // 'address5'        : $scope.sparraw_user_temp.farminfo.address5         ,
		      'feedtype'        : $scope.sparraw_user_temp.farminfo.feedtype         ,
		      'cageInfo_layer'  : $scope.sparraw_user_temp.farminfo.cageInfo_layer   ,
		      'cageInfo_row'    : $scope.sparraw_user_temp.farminfo.cageInfo_row     ,
		      'businessModle'   :  $scope.sparraw_user_temp.farminfo.businessModle   ,
		      // 'corporation'     : $scope.sparraw_user_temp.farminfo.corporation      ,
		      "feedBreeds"      :  $scope.sparraw_user_temp.farminfo.feedBreeds      ,
		      'house_length'    : $scope.sparraw_user_temp.farminfo.house_length     ,
		      'house_width'     : $scope.sparraw_user_temp.farminfo.house_width      ,
		      'house_height'    : $scope.sparraw_user_temp.farminfo.house_height     ,
		      'feedarea'        : $scope.sparraw_user_temp.farminfo.feedarea         

			};
		    
		    Sparraw.ajaxPost('sys/farm/save.action', params, function(data){


		    	  if (data.ResponseDetail.ErrorMsg == null) {
		    		$scope.sparraw_user_temp.farminfo.id = data.ResponseDetail.farmId;

		    		$scope.sparraw_user_temp.Authority = {
		    			"HouseBreed"    : "All" ,
					    "basicInfo"     : "All" ,
					    "DailyInput"    : "All" ,
					    "AlarmSetting"  : "All" ,
					    "role"          : 2     ,
					    "MonitorDeal"   : "All" ,
					    "FarmBreed"     : "All"
		    		};

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	Sparraw.myNotice('保存成功');
			    	//重新获取服务器最新数据
    				biz_common_getLatestData($state,"buildingTable");
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };

		    });
  	};


})
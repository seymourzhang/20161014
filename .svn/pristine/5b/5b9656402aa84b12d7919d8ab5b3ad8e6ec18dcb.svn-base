angular.module('myApp.controllers', ['ionic','ngCordova','ngTouch', 'ui.grid', 'ui.grid.pinning','ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection', 'ui.grid.resizeColumns','ui.grid.autoResize'])
.controller("myCtrl",function($scope,$state,$http,AppData,$ionicLoading,$timeout,$ionicNavBarDelegate){

	Sparraw.setMyIonicLoading($ionicLoading);
  	Sparraw.setMyHttp($http);
  	Sparraw.setMyTimeout($timeout);
  	Sparraw.setMyIonicNavBarDelegate($ionicNavBarDelegate);

	$state.go("landingPage");
	//landingPage
	//alarmTest
})

// 用户注册
.controller("UseRegisteredCtrl",function($scope, $state, $http, $ionicPopup, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));



	$scope.validationPhone = function(){





		
		// if($scope.sparraw_user_temp.userinfo.phone && /^1[3|4|5|8]\d{9}$/.test($scope.sparraw_user_temp.userinfo.phone)){
		if($scope.sparraw_user_temp.userinfo.phone && /^1[3|4|5|7|8]\d{9}$/.test($scope.sparraw_user_temp.userinfo.phone)){
			//对的
			console.log("输入正确");
		} else{
			//不对
			Sparraw.myNotice("请输入正确手机号码");
		};
	};



	$scope.save = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    
	    /* 校验信息*/
	    
	    var required = ['name','phone','password','confirmPassword'];
	   	for(i in required){if($scope.sparraw_user_temp.userinfo[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}
	   	//判断手机号码格式
	   	if($scope.sparraw_user_temp.userinfo.phone && /^1[3|4|5|8]\d{9}$/.test($scope.sparraw_user_temp.userinfo.phone)){
		//对的
		console.log("输入正确");
		} else{
		//不对
		return Sparraw.myNotice("请输入正确手机号码");
		};
	   	//判断密码是否相同
	   	if ($scope.sparraw_user_temp.userinfo.password != $scope.sparraw_user_temp.userinfo.confirmPassword) {return Sparraw.myNotice('两次输入密码不同,请重新输入...');};
		


	    var params = {
	      'name'           : $scope.sparraw_user_temp.userinfo.name             ,
	      'tele'           : $scope.sparraw_user_temp.userinfo.phone            ,
	      'pw'             : $scope.sparraw_user_temp.userinfo.confirmPassword  ,
	      'role'           : 1                                                  ,
	      'farmid'         : null                                               ,
	      'houses'         : null                                               ,
	    };
	    
	    
	    Sparraw.ajaxPost('sys/user/save.action', params, function(data){

	    	//判断注册是否成功
	    	if (data.ResponseDetail.ErrorMsg == null) {
			   		
			   		$scope.sparraw_user_temp.profile.id_spa = data.ResponseDetail.userId;

	    			$scope.sparraw_user_temp.userinfo.id = data.ResponseDetail.userId;

	    			sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
			   		
			   		Sparraw.myNotice('保存成功');



			   		//进行登录操作
			   		var params = {
		    	      "userCode"  :  $scope.sparraw_user_temp.userinfo.phone           ,
    				  "pw"        :  $scope.sparraw_user_temp.userinfo.confirmPassword,
    				  "AndroidImei": ANDROID_IMEI,
    				  "uuid":UUID,
    				  "model":MODELNAME,
    				  "sysVersion":VERSION,
    				  "platForm":PLATFORM
					};

					Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
					    if (data.ResponseDetail.LoginResult == 'Success') {

					   		sparraw_user.userinfo    =  data.ResponseDetail.userinfo    ;
					   		sparraw_user.farminfo    =  data.ResponseDetail.farminfo    ;
					   		sparraw_user.houseinfos  =  data.ResponseDetail.houseinfos  ;
					   		sparraw_user.userinfos   =  data.ResponseDetail.userinfos   ;
					   		sparraw_user.Authority   =  data.ResponseDetail.Authority;
					   		sparraw_user.profile = {
											   			'id_spa'      :  data.ResponseDetail.userinfo.id  ,
											   			'secret'      :  'mtc_secret'                     ,
											   			'user_State'  :  true 
											   		} ;
							Sparraw.myNotice('成功登录');
					   		$state.go("home");
					   		sparraw_user.profile.account  = $scope.sparraw_user_temp.userinfo.phone            ;
			   				sparraw_user.profile.password = $scope.sparraw_user_temp.userinfo.confirmPassword  ;
			   				console.log(sparraw_user.profile.account);
			   				console.log(sparraw_user.profile.password);
					   }else{
					   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					   };
					})

			   }else {
			   		if (data.ResponseDetail.ErrorMsg == "保存失败，该手机号已经注册，请联系管理员。") {
						$scope.prompt = function() {
						    app_alert("该用户已注册，请返回登录页面");
						    return; 
						};
						$scope.prompt();
			   		}else{
			   			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   		};
			   		
			   };
	    });
  	};


})
// 农场注册
.controller("FarmRegisteredCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
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
		if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    
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
	    if(!Sparraw.isOnline()){
	      // return Sparraw.myNotice('暂无网络连接...');
	    }
	    
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
    				Sparraw.getLatestData($state,"buildingTable");
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };

		    });
  	};


})
// 栋舍列表
.controller("BuildingTableCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }

	//true-隐藏，false-显示
	if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {//有栋舍
		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//有用户
			$scope.backBtn    =  false ;
			$scope.finishBtn  =  true ;
		}else{//没用户
			$scope.backBtn    =  true  ;
			$scope.finishBtn  =  false ;
		};

	}else{//没有栋舍
		console.log("2");
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	};
	if($scope.sparraw_user_temp.userinfos){
		if ($scope.sparraw_user_temp.userinfos.length == "1") {
			$scope.finishBtn  =  false ;
			console.log("对");
		}else{
			$scope.finishBtn  =  true ;
			console.log("错");
		};
	}else{
		console.log("用户暂未设置员工信息")
	}
	

	


	$scope.goAddbuilding = function(){
		if($scope.sparraw_user_temp.Authority){
			
		}else{
			if($scope.sparraw_user_temp.farminfo == undefined || $scope.sparraw_user_temp.farminfo == null){
				app_alert('您还未进行农场信息设置，请设置后再添加栋舍。');
				return;
			}
			console.log("用户暂未设置权限息,默认All")
			$state.go("addbuilding");
			return;
			//return app_alert("该用户无此操作权限。");
		}
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$state.go("addbuilding");
	}
	

	//判断是否有栋舍信息
	/*if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {		//没栋舍
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	}else{//有栋舍
		// $scope.finishBtn  =  true   ;
		// $scope.backBtn    =  true   ;

		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//没用户
			$scope.backBtn    =  false  ;
			$scope.finishBtn  =  true   ;
		}else{//有用户

		};

	};*/

	$scope.remove = function(item){
		Sparraw.myNotice('如需删除该栋舍，请联系我们。');
		/*var params = {
		    	'operate'   : "DELETE" ,
		    	'houseInfo' : {
				      'houseId'        : item.id                ,
				      'houseName'      : item.houseName         ,
				      'h_length'       : item.h_length       	,
				      'h_width'        : item.h_width           ,
				      'h_height'       : item.h_height          ,
				      'feedarea'       : item.feedarea       	,
				      'mtc_device_id'  : item.mtc_device_id  	
				  }
		    };

		


		Sparraw.ajaxPost('sys/house/update.action', params, function(data){


			if (data.ResponseDetail.ErrorMsg == null) {
			   	var delIndex = -1;
				for(i in $scope.sparraw_user_temp.houseinfos){
					if($scope.sparraw_user_temp.houseinfos[i].houseName==item.houseName){
						delIndex = i ;
					}
				}

				$scope.sparraw_user_temp.houseinfos.splice(delIndex,1);

				sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('删除成功');

			}else {
			   Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};


			
	    });*/
	};

	$scope.goEmployeesTable = function(para){
		console.log($scope.sparraw_user_temp.houseinfos);
		if ($scope.sparraw_user_temp.houseinfos === undefined) {
			app_alert("您还未创建栋舍。");
		}else{
			$state.go("employeesTable");
		};
	};
})

// 栋舍修改
.controller("updateBuildingInfoCtrl",function($scope, $state, $http, $stateParams, AppData,$ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	//查询
	for(i in $scope.sparraw_user_temp.houseinfos){
		if($scope.sparraw_user_temp.houseinfos[i].houseName == $stateParams.buildingID){
			$scope.tempVar.houseTemp = JSON.parse(JSON.stringify($scope.sparraw_user_temp.houseinfos[i]));

		}
	}

	$scope.backBtn    =  false  ;
	$scope.cancelBtn  =  true   ;
	$scope.visible    =  false  ;

	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$scope.visible    =!  $scope.visible   ;
		$scope.sheerDiv   =!  $scope.sheerDiv  ;
		$scope.backBtn    =   true             ;
		$scope.cancelBtn  =   false            ;
		Sparraw.myNotice('请编辑');
	}

  	$scope.dealBarCode = function(){
		barScan(function(result){
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
			app_alert("扫描的设备编号是：" + result.text);
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};

	$scope.saveUpdate = function(){

		/* 校验信息*/
	    /*var required = [''];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}*/

			var params = {
		    	'operate'   : "UPDATE" ,
		    	'houseInfo' : {
		    		  'houseId'        : $scope.tempVar.houseTemp.id                ,
				      'houseName'      : $scope.tempVar.houseTemp.houseName         ,
				      'h_length'       : $scope.tempVar.houseTemp.h_length       	,
				      'h_width'        : $scope.tempVar.houseTemp.h_width           ,
				      'h_height'       : $scope.tempVar.houseTemp.h_height          ,
				      'feedarea'       : $scope.tempVar.houseTemp.feedarea       	,
				      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  	,
				      'farmId'         : $scope.sparraw_user_temp.farminfo.id       
				  }
		    };





		    Sparraw.ajaxPost('sys/house/update.action', params, function(data){
		    	

		    	if (data.ResponseDetail.ErrorMsg == null) {
			   		var delIndex = -1;
					for(i in $scope.sparraw_user_temp.houseinfos){
						if($scope.sparraw_user_temp.houseinfos[i].houseName == $scope.tempVar.houseTemp.houseName){
							delIndex = i ;
						}
					}
					$scope.sparraw_user_temp.houseinfos.splice(delIndex,1,JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));

					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
				
			    	Sparraw.myNotice('编辑成功');

			    	$scope.tempVar.houseTemp = {};

			    	$state.go("buildingTable");
			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };




		    	
		    });

	}

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	//返回时清空填写内容 
	$scope.backTable = function(){
		if (!$scope.sheerDiv) {
			$scope.tempVar.houseTemp = {};
	    	$state.go("buildingTable");
		}else {
			Sparraw.myNotice('您还未进行保存，请保存后再返回。');
		}

  	};

  	$scope.cancelEvent = function(){
  		$scope.visible = !$scope.visible;
		$scope.sheerDiv = !$scope.sheerDiv;
		$scope.backBtn = false;
		$scope.cancelBtn = true;
  	}

})

//栋舍添加
.controller("AddbuildingCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var maxID = null;
	// 栋舍编号，自动生成。并且判断是否有信息
	if (sparraw_user.houseinfos == undefined) {
		maxID = 1 ;
		$scope.tempVar.houseTemp.houseName = padNumber(maxID,2) ;
	}else{
		maxID = $scope.sparraw_user_temp.farminfo.house_Maxid + 1 ;
		$scope.tempVar.houseTemp.houseName = padNumber(maxID,2) ;

	};

	
	//将农场信息中得数据代入到栋舍中
	$scope.tempVar.houseTemp.h_length = $scope.sparraw_user_temp.farminfo.house_length;
	$scope.tempVar.houseTemp.h_width  = $scope.sparraw_user_temp.farminfo.house_width;
	$scope.tempVar.houseTemp.h_height = $scope.sparraw_user_temp.farminfo.house_height;
	$scope.tempVar.houseTemp.feedarea = $scope.sparraw_user_temp.farminfo.feedarea;

	$scope.addHouse = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    /* 校验信息*/
	    /*var required = ['mtc_device_id'];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}*/


	    var params = {
	      'farmId'         : $scope.sparraw_user_temp.farminfo.id    ,
	      'houseName'      : $scope.tempVar.houseTemp.houseName      ,
	      'h_length'       : $scope.tempVar.houseTemp.h_length       ,
	      'h_width'        : $scope.tempVar.houseTemp.h_width        ,
	      'h_height'       : $scope.tempVar.houseTemp.h_height       ,
	      'feedarea'       : $scope.tempVar.houseTemp.feedarea       ,
	      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  
	    };



	    Sparraw.ajaxPost('sys/house/save.action', params, function(data){

	    	if (data.ResponseDetail.ErrorMsg == null) {
			   	$scope.sparraw_user_temp.farminfo.house_Maxid = maxID;

		    	$scope.tempVar.houseTemp.id = data.ResponseDetail.houseId;

		    	//判断是否有信息
		    	if (sparraw_user.houseinfos == undefined) {
		    		$scope.sparraw_user_temp.houseinfos = [];
				}else{

				};

		  		$scope.sparraw_user_temp.houseinfos.push(JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));
		    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('保存成功');
		    	
		    	$scope.tempVar.houseTemp = {};

		    	//重新获取服务器最新数据
    			Sparraw.getLatestData($state,"buildingTable");
			}else {
			    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};

	    });
  	};
  	
  	$scope.dealBarCode = function(){  		
		barScan(function(result){
			app_alert("您的设备编号是：" + result.text);
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};
})

//员工列表
.controller("EmployeesTableCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }




	if (sparraw_user.profile.user_State) {
		$scope.finishBtn  =  false  ;
		$scope.backBtn    =  true   ;

	}else {
		$scope.finishBtn  =  true   ;
		$scope.backBtn    =  false  ;

	};

	

	$scope.goAddEmployees = function(){
		if ($scope.sparraw_user_temp.Authority) {

		}else{
			
			return app_alert("您还未进行农场信息设置，请设置后再添加员工信息");
		};
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$state.go("addEmployees");
	}
	

	$scope.remove = function(item){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};	
		if ($scope.sparraw_user_temp.userinfo.tele == item.tele ) {
			Sparraw.myNotice('无法删除自己');
		}else {
             app_confirm('删除该用户之后将无法恢复，请确认！','提示',null,function(buttonIndex){
                    if(buttonIndex == 2){
                     var params = {
			    	'operate'  : "DELETE" ,
			    	'userInfo' : {
					      'id'         : item.id                                            ,
					      'name'       : item.name                                          ,
					      'tele'       : item.tele       	                                ,
					      'pw'         : "123456"                                           ,
					      'role'       : item.role                                          ,
					      'farmid'     : $scope.sparraw_user_temp.farminfo.id               ,
					      'houses'     : null  	                                            
					  }
			        };
				    Sparraw.ajaxPost('sys/user/update.action', params, function(data){
						if (data.ResponseDetail.ErrorMsg == null) {
						   		var delIndex = -1;
								for(i in $scope.sparraw_user_temp.userinfos){
									if($scope.sparraw_user_temp.userinfos[i].id==item.id){
										delIndex = i ;
									}
								}
								$scope.sparraw_user_temp.userinfos.splice(delIndex,1);
								sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
						    	Sparraw.myNotice('删除成功');
						   }else {
						   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						   };
			        });
			        }
              });
		};
	};
	$scope.saveEmployees = function(para){
		Sparraw.myNotice('保存成功');
		sparraw_user.profile.user_State = false;
		$state.go("home");
	};
})


// 员工修改
.controller("updateEmployeesInfoCtrl",function($scope, $state, $http, $stateParams,  $ionicPopup, $ionicModal, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));



	//查询
	for(i in $scope.sparraw_user_temp.userinfos){
		if($scope.sparraw_user_temp.userinfos[i].id == $stateParams.employeesID){
			$scope.tempVar.userTemp = JSON.parse(JSON.stringify($scope.sparraw_user_temp.userinfos[i]));
		}
	}

	

	$scope.backBtn    =  false  ;//返回按钮 默认显示
	$scope.visible    =  false  ;//编辑按钮 默认显示
	$scope.sheerDiv   =  false  ;//覆盖图片 默认显示
	$scope.cancelBtn  =  true   ;//保存按钮 默认隐藏

	
	//编辑事件
	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		//自己无法修改自己角色
	   	if ($scope.tempVar.userTemp.tele == $scope.sparraw_user_temp.userinfo.tele) {return app_alert('用户无法修改自己的信息');};
		$scope.backBtn    =  true  ;
		$scope.visible    =  true  ;
		$scope.sheerDiv   =  true  ;
		$scope.cancelBtn  =  false ;
		Sparraw.myNotice('请编辑');
	}

	//取消事件
	$scope.cancelEvent = function(){
		$scope.backBtn    =  false  ;
		$scope.visible    =  false  ;
		$scope.sheerDiv   =  false  ;
		$scope.cancelBtn  =  true   ;
  	}


	for (var i = 0; i < $scope.sparraw_user_temp.userinfos.length; i++) {
		if ($scope.sparraw_user_temp.userinfos[i].tele == $scope.tempVar.userTemp.tele) {

			if ($scope.sparraw_user_temp.userinfos[i].role == 4) {
				$scope.houseAffiliation = true;
			}else{
				$scope.houseAffiliation = false;
			};
		};
	};



	$scope.JudgeHouse = function(){
		if ($scope.tempVar.userTemp.role == 4) {
			$scope.houseAffiliation = true;
		}else {
			$scope.houseAffiliation = false;
		};
	}



	if ($scope.tempVar.userTemp.role == 4) {
		$scope.selectedHouse = [];//获取选取的栋舍id
		for (var x = 0; x < $scope.tempVar.userTemp.houses.length; x++) {
			$scope.selectedHouse.push($scope.tempVar.userTemp.houses[x]);
		};
	}else {
		$scope.tempVar.userTemp.houses = null;
	};
	

	$scope.testAry = [];
	$scope.testAry2 = [];

	$scope.devList = [];//将栋 舍 列 表显示在页面上
    for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
    	$scope.devList.push({"text"     :           $scope.sparraw_user_temp.houseinfos[i].houseName ,
    						 "checked"  :           false                                            ,
    						 "id"       :           $scope.sparraw_user_temp.houseinfos[i].id
    	});    	
    	$scope.testAry.push($scope.devList[i].id);
    };

    

    //对比页面所选栋舍号并且显示所选栋舍
	for(var s in $scope.testAry){
	    for(var x in $scope.selectedHouse){
	        if($scope.testAry[s] === $scope.selectedHouse[x]){
	            $scope.testAry2.push($scope.testAry[s]);
	        }
	    }
	  };

	for (var i = 0; i < $scope.devList.length; i++) {
		for (var x = 0; x < $scope.testAry2.length; x++) {
			if ($scope.testAry2[x] === $scope.devList[i].id) {
				$scope.devList[i].checked = true;
			};
		};

	};



    $scope.judgeHousChange = function(){
    	$scope.tempVar.userTemp.houses = [];
    	for (var i = 0; i < $scope.devList.length; i++) {
	    	if ($scope.devList[i].checked == true) {
	    		$scope.tempVar.userTemp.houses.push($scope.devList[i].id);
	    	}else {

	    	};
	    };
    }


	$scope.saveUpdate = function(){

	   	/* 校验信息*/
	    var required = ['name','tele','role'];
	   	if($scope.tempVar.userTemp.role==4){
	      required = required.concat(['houses']);
	    }
	   	for(i in required){if($scope.tempVar.userTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}

		    var params = {
		    	'operate'  : "UPDATE" ,
		    	'userInfo' : {
				      'id'         : $scope.tempVar.userTemp.id                    ,
				      'name'       : $scope.tempVar.userTemp.name                  ,
				      'tele'       : $scope.tempVar.userTemp.tele       	       ,
				      // 'pw'         : "123456"                                      ,
				      'role'       : $scope.tempVar.userTemp.role                  ,
				      'farmid'     : $scope.sparraw_user_temp.farminfo.id          ,
				      // 'houses'     : selectedHouse  	                           
				      'houses'     : $scope.tempVar.userTemp.houses  	                           
				  }

		    };

		    
		    Sparraw.ajaxPost('sys/user/update.action', params, function(data){

		    	if (data.ResponseDetail.ErrorMsg == null) {
			   		var delIndex = -1;
					for(i in $scope.sparraw_user_temp.userinfos){
						if($scope.sparraw_user_temp.userinfos[i].id == $scope.tempVar.userTemp.id){
							delIndex = i ;
						}
					}

					$scope.sparraw_user_temp.userinfos.splice(delIndex,1,JSON.parse(JSON.stringify($scope.tempVar.userTemp)));

					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));



			    	Sparraw.myNotice('编辑成功');

			    	$scope.tempVar.userTemp = {};

			    	$state.go("employeesTable");
				   }else {
				   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				   };



		    	
		    });

	}

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	}
	

	//返回时清空填写内容 
	$scope.backTable = function(){
		if (!$scope.sheerDiv) {
			$scope.tempVar.userTemp = {};
			$state.go("employeesTable");
		}else {

		}
  	};



  	$scope.clickPhone = function(){
  		app_confirm('手机号码暂时无法修改，如需修改手机请删除该员工后再添加。','提示',null,function(buttonIndex){
                    if(buttonIndex == 2){
                     

			        };
              });
  	}
  	


  	$ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

})

//员工添加
.controller("AddEmployeesCtrl",function($scope, $state, $http, $ionicPopup, $ionicModal, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.JudgeHouse = function(){
		if (this.tempVar.userTemp.role == 4) {
			$scope.houseAffiliation = true;
		}else {
			$scope.houseAffiliation = false;
		};
	}


    $scope.devList = [];

    for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
    	$scope.devList.push({"text"     :           $scope.sparraw_user_temp.houseinfos[i].houseName ,
    						 "checked"  :           false                                            ,
    						 "id"       :           $scope.sparraw_user_temp.houseinfos[i].id
    	});
    };





	$scope.save = function(){
	    if(!Sparraw.isOnline()){
	      //return Sparraw.myNotice('暂无网络连接...');
	    }
	    /* 校验信息*/
	   	if ($scope.tempVar.userTemp.name == "" ||
	   		$scope.tempVar.userTemp.tele == "" ||
	   		$scope.tempVar.userTemp.role == "" ||
	   		$scope.tempVar.userTemp.name == undefined ||
	   		$scope.tempVar.userTemp.tele == undefined ||
	   		$scope.tempVar.userTemp.role == undefined ) {
	   		return Sparraw.myNotice('尚有内容未填写...');
	   	};
		if($scope.tempVar.userTemp.tele && /^1[3|4|5|8]\d{9}$/.test($scope.tempVar.userTemp.tele)){
			//对的
			console.log("输入正确");
		} else{
			//不对
			return Sparraw.myNotice("请输入正确手机号码");
		};

		

		var selectedHouse = [];
		for (var i = 0; i < $scope.devList.length; i++) {
	    	if ($scope.devList[i].checked == true) {
	    		selectedHouse.push($scope.devList[i].id);
	    	}else {

	    	};
	    };
	    //判断饲养员是否选择了所属栋舍
	    if($scope.tempVar.userTemp.role==4){
	    	console.log(selectedHouse.length);
	   		if (selectedHouse.length == 0) {
	   			return Sparraw.myNotice("请选择所属栋舍");
	   		}else{

	   		};
	    }else{

	    };


	    var params = {
	      'name'           : $scope.tempVar.userTemp.name                       ,
	      'tele'           : $scope.tempVar.userTemp.tele                       ,
	      'pw'             : "123456"                                           ,
	      'role'           : $scope.tempVar.userTemp.role                       ,
	      'farmid'         : $scope.sparraw_user_temp.farminfo.id               ,
	      'houses'         : selectedHouse                                      	

	    };
	    
	    Sparraw.ajaxPost('sys/user/save.action', params, function(data){
	    	
	    	if (data.ResponseDetail.ErrorMsg == null) {
			   		$scope.tempVar.userTemp.id = data.ResponseDetail.userId;


			   		//判断是否有信息
			    	if (sparraw_user.userinfos == undefined) {
			    		$scope.sparraw_user_temp.userinfos = [];
					}else{

					};

					$scope.sparraw_user_temp.userinfos.push(JSON.parse(JSON.stringify($scope.tempVar.userTemp)));

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	//制空新建员工 列表
			    	$scope.tempVar.userTemp = {};

			    	

			    	//重新获取服务器最新数据
			    	Sparraw.getLatestData($state,'employeesTable');
			    	app_alert('保存成功，新建员工密码为123456。');

			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };


	    });

  	};



  	$ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

})

// 登陆协议
.controller("landingProtocolCtrl",function($scope, $state, $http, AppData) {
	$scope.goLandingPage = function(){
		$state.go("landingPage");
	}

	$scope.closeApp = function(){
		console.log("关闭app");
		var ua = navigator.userAgent.toLowerCase(); 
		if (/iphone|ipad|ipod/.test(ua)) {
			//return document.location = "objc://printLog::/";
		} else if (/android/.test(ua)) {
		 	ionic.Platform.exitApp();
		}else{
		 	Sparraw.myNotice("请在手机端点击。");
		}
	}
})
// 用户登录
.controller("landingPageCtrl",function($scope, $state, $http, AppData, $stateParams) {

	// 保持竖屏
	setPortrait(true,true);

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('landingPage_Underlying_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('landingPage_Underlying_DIV').style.height = DIVHEIGHT + 'px';
	};

	var userTemp = getUserInfo();
	var userCode,passWord;

	
	
	if(userTemp != null){
		userCode = userTemp.userCode;
		if(userTemp.savePW == "true"){
			passWord = userTemp.pw;	
		}else{
			passWord = "";
		}
	}else{
		userCode = "";
		passWord = "";
	}



	//跳转至协议界面
	if(userTemp == null){
		if (selectBackPage.firstTime != true) {
			selectBackPage.firstTime = true;
			$state.go("landingProtocol");
		}else{
			
		}
	}else{
		setTimeout(function() {
			$scope.automaticLogin();
		}, 500);
	}

	$scope.landing = {
		"userCode":userCode,
		"passWord":passWord
	};

	 $scope.pushNotification = { checked: true };
	 var savePWFlag = true;
	 $scope.pushNotificationChange = function() {
        if ($scope.pushNotification.checked) {
        	savePWFlag = true;
        	//console.log("保存");
        }else {
        	savePWFlag = false;
        	//console.log("不保存");
        };
      };

    $scope.Register = function(){
    	sparraw_user.profile.user_State = true;
    	$state.go("useRegistered");
    };

    $scope.tempProductionMode = AppMode;

    if($scope.tempProductionMode == 'Development'){
    	$scope.webUrl = '1';
	    var tempURL = "1" ;
	    API_Host = Development_API_Host ;
	    $scope.changeURL = function(){
	    	if(tempURL == "1"){
	    		API_Host = Production_API_Host ;
	    		tempURL = '2';
	    	}else{
	    		API_Host = Development_API_Host ;
	    		tempURL = '1';
	    	}
	    };    	
    }else if($scope.tempProductionMode == 'Production'){
    	API_Host = Production_API_Host ;
    }else if($scope.tempProductionMode == 'Local'){
    	API_Host = Local_API_Host ;
    }

    $scope.goForgotPassword = function(){
    	$state.go("forgotPassword");
    }

    var demoFlag = false;

	$scope.accountLogin = function(){

		sparraw_user.profile.account  = $scope.landing.userCode;
		sparraw_user.profile.password = $scope.landing.passWord;
		var params = {
		    	      "userCode": $scope.landing.userCode,
    				  "pw": $scope.landing.passWord,
    				  "AndroidImei": ANDROID_IMEI,
    				  "uuid":UUID,
    				  "model":MODELNAME,
    				  "sysVersion":VERSION,
    				  "platForm":PLATFORM
		};
		//校验信息
	 	if (params.userCode == null || params.pw == '') {return Sparraw.myNotice('请输入用户名/密码，或注册用户');};

		Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
		    if (data.ResponseDetail.LoginResult == 'Success') {
		    	persistentData.switchRemind = true;
		   		sparraw_user.userinfo = data.ResponseDetail.userinfo;
		   		sparraw_user.farminfo = data.ResponseDetail.farminfo;
		   		sparraw_user.houseinfos = data.ResponseDetail.houseinfos;
		   		sparraw_user.userinfos = data.ResponseDetail.userinfos;
		   		sparraw_user.Authority = data.ResponseDetail.Authority;
		   		sparraw_user.profile = {
								   			'id_spa':data.ResponseDetail.userinfo.id,
								   			'secret':'mtc_secret',
								   			'user_State': false ,
								   			'account':sparraw_user.profile.account,
								   			'password':sparraw_user.profile.password
								   		} ;
				// 缓存用户名密码
				if(!demoFlag){
					saveUserInfo(JSON.stringify({"userCode": $scope.landing.userCode,"pw": $scope.landing.passWord,"savePW":savePWFlag+""}));
				}

				try{
					var jpushTags = [];
					// 如果用户设置了栋舍信息
					if(sparraw_user.userinfo.houses){
						for (var i = 0; i < sparraw_user.userinfo.houses.length; i++) {
							jpushTags.push('mtc_tag_' + sparraw_user.userinfo.houses[i].HouseId);
						};
						// 为Jpush 设置别名和标签
						app_setTagsWithAlias(jpushTags, 'mtc_alias_' + sparraw_user.profile.id_spa);
					}else{
						console.log("用户暂未设置栋舍信息.")
					}
						
				}catch(e){
					console.log(e);
				}
		   		$state.go("home");

		   		var ua = navigator.userAgent.toLowerCase(); 
				if (/iphone|ipad|ipod/.test(ua)) {
					//return document.location = "objc://printLog::/" + sparraw_user.userinfo.id;
				} else if (/android/.test(ua)) {
				 
				}else{
				 
				}
		   }else{
		   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		   };

		});
    };
    //测试账户登陆
    $scope.demoLogin = function(){
		$scope.landing = {
			"userCode":13420160527,
			"passWord":"1"
		};
		demoFlag = true;
		$scope.accountLogin();
    };


    $scope.automaticLogin = function(){
    	if (selectBackPage.NeedLogin) {
    		if ($scope.landing.userCode != "" || !$scope.landing.userCode &&
	    		$scope.landing.passWord != "" || !$scope.landing.passWord) {
	    		$scope.accountLogin();
	    	}
    	}else{
    		
    	}
    }


    $scope.companyInformation = true;
    $scope.focus = function(){
    	$scope.companyInformation = false;
    }

    $scope.blur = function(){
    	$scope.companyInformation = true;
    }
})
// 忘记密码
.controller("forgotPasswordCtrl",function($scope, $state, $http, AppData) {
	
})
// 免责声明
.controller("disclaimerCtrl",function($scope, $state, $http, AppData) {
	
})



// 主页面
.controller("homeCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout, AppData) {

	setPortrait(true,true);//竖屏

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '45';
		document.getElementById('underlying_DIV').style.height = DIVHEIGHT + 'px';
	}
	var dayNames = new Array("周日","周一","周二","周三","周四","周五","周六");  
	Stamp = new Date();  
	$scope.nowDate = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";

	$scope.goDailyTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"dailyTable");
	}

	$scope.gobatchManage = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"newBatchManage");
	}
	$scope.goEnvMonitoring = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"envMonitoring");
	}
	$scope.godataAnalyseTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"dataAnalyseTable");
	}
	$scope.goTaskRemind = function(){
		$state.go("taskRemind");
	}
	$scope.goChickenAssistList = function(){
		$state.go("chickenAssistList");
	}

	$scope.pointDevelop = function() {
		pointDevelop();
		return;	
	};

	$scope.weatherSrc1 = "img/icon/weather/05.png";
	$scope.weatherSrc2 = "img/icon/weather/05.png";
	$scope.weatherSrc3 = "img/icon/weather/05.png";






	var myDate = new Date();
	var dateStr = "";
	var TempMonth = (new Date()).getMonth()+1;
	dateStr = myDate.getFullYear() + "年" + TempMonth + "月" + myDate.getDate() + "日";

	var addCode1 = "";
	var addCode2 = "";
	var addCode3 = "";

	if($scope.sparraw_user_temp.hasOwnProperty("farminfo")){
		addCode1 = $scope.sparraw_user_temp.farminfo.address1;
		addCode2 = $scope.sparraw_user_temp.farminfo.address2;
		addCode3 = $scope.sparraw_user_temp.farminfo.address3;
	}

	var curWData = addCode1 + "_" + addCode2 + "_" + addCode3 + "_" + getCurDate().replace(/-/g,'');

	var tempWData = $scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 + "_" + 
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate;
	console.log("当前天气请求参数：" + curWData);
	console.log("缓存天气存在参数：" + tempWData);
	if(curWData != tempWData){
		console.log("加载天气信息");
		var params = {
					      'add1code'    : addCode1 ,
					      'add2code'    : addCode2 ,
					      'add3code'    : addCode3                         
		};
		Sparraw.ajaxPost('sys/home/reqWeather.action', params, function(data){
				
				if(data.ResponseDetail.hasOwnProperty("weatherinfo")){
					$scope.homeData = {};
					$scope.homeData.weatherinfo = data.ResponseDetail.weatherinfo;
					var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
					var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
					var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
					var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
					if (judgeRain >= 0) {
						console.log("雨天");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/10.png');
						$scope.weatherSrc1 = "img/icon/weather/10.png";
					}else if (judgeCloudy >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/07.png');
						$scope.weatherSrc1 = "img/icon/weather/07.png";
					}else if (judgeSnow >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/14.png');
						$scope.weatherSrc1 = "img/icon/weather/14.png";
					}else if (judgeShade >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/05.png');
						$scope.weatherSrc1 = "img/icon/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg1').setAttribute('src', 'img/icon/weather/00.png');
						$scope.weatherSrc1 = "img/icon/weather/00.png";
					};

					var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
					var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
					var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
					var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
					if (judgeRain1 >= 0) {
						console.log("雨天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/10.png');
						$scope.weatherSrc2 = "img/icon/weather/10.png";
					}else if (judgeCloudy1 >= 0) {
						console.log("多云");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/07.png');
						$scope.weatherSrc2 = "img/icon/weather/07.png";
					}else if (judgeSnow1 >= 0) {
						console.log("下雪");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/14.png');
						$scope.weatherSrc2 = "img/icon/weather/14.png";
					}else if (judgeShade1 >= 0) {
						console.log("阴");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/05.png');
						$scope.weatherSrc2 = "img/icon/weather/05.png";
					}else{
						console.log("晴天");
						//document.getElementById('weatherImg2').setAttribute('src', 'img/icon/weather/00.png');
						$scope.weatherSrc2 = "img/icon/weather/00.png";
					};
				}

				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode1 = addCode1;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode2 = addCode2;
				$scope.sparraw_user_temp.weather.KeyInfo.WeatherCode3 = addCode3;
				if(data.ResponseDetail.cityinfo){
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 = data.ResponseDetail.cityinfo.cityname1;
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 = data.ResponseDetail.cityinfo.cityname2;
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 = data.ResponseDetail.cityinfo.cityname3;
					$scope.sparraw_user_temp.weather.KeyInfo.WeatherDate = data.ResponseDetail.cityinfo.date?data.ResponseDetail.cityinfo.date.substr(0,8):"";
					$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;
				}else{
					console.log("用户暂未设置城市信息.")
				}

				$scope.sparraw_user_temp.weather.weatherinfo = data.ResponseDetail.weatherinfo;
				
			    sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
			});
	}else{
		console.log("读取缓存天气");
		$scope.weatherAdd = $scope.sparraw_user_temp.weather.KeyInfo.WeatherName1 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName2 + "  " + $scope.sparraw_user_temp.weather.KeyInfo.WeatherName3 ;

		$scope.homeData = {};
		$scope.homeData.weatherinfo = $scope.sparraw_user_temp.weather.weatherinfo;
		var judgeRain = $scope.homeData.weatherinfo[0].day_desc.indexOf("雨");
		var judgeShade = $scope.homeData.weatherinfo[0].day_desc.indexOf("阴");
		var judgeCloudy = $scope.homeData.weatherinfo[0].day_desc.indexOf("多云");
		var judgeSnow = $scope.homeData.weatherinfo[0].day_desc.indexOf("雪");
		if (judgeRain >= 0) {
			console.log("雨天");
			$scope.weatherSrc1 = "img/icon/weather/10.png";
		}else if (judgeCloudy >= 0) {
			console.log("多云");
			$scope.weatherSrc1 = "img/icon/weather/07.png";
		}else if (judgeSnow >= 0) {
			console.log("下雪");
			$scope.weatherSrc1 = "img/icon/weather/14.png";
		}else if (judgeShade >= 0) {
			console.log("阴");
			$scope.weatherSrc1 = "img/icon/weather/05.png";
		}else{
			console.log("晴天");
			$scope.weatherSrc1 = "img/icon/weather/00.png";
		};

		var judgeRain1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雨");
		var judgeShade1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("阴");
		var judgeCloudy1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("多云");
		var judgeSnow1 = $scope.homeData.weatherinfo[1].day_desc.indexOf("雪");
		if (judgeRain1 >= 0) {
			console.log("雨天");
			$scope.weatherSrc2 = "img/icon/weather/10.png";
		}else if (judgeCloudy1 >= 0) {
			console.log("多云");
			$scope.weatherSrc2 = "img/icon/weather/07.png";
		}else if (judgeSnow1 >= 0) {
			console.log("下雪");
			$scope.weatherSrc2 = "img/icon/weather/14.png";
		}else if (judgeShade1 >= 0) {
			console.log("阴");
			$scope.weatherSrc2 = "img/icon/weather/05.png";
		}else{
			console.log("晴天");
			$scope.weatherSrc2 = "img/icon/weather/00.png";
		};
	}
	
	Sparraw.beginAlarmTask($scope.sparraw_user_temp.userinfo.role);
	
	$scope.judgeAlarm = function(){
		if (sparraw_user.needAlarm == "N") {
			document.getElementById('AlarmImg').setAttribute('src', 'img/icon/farm/home4.png');
		}else{
			document.getElementById('AlarmImg').setAttribute('src', 'img/icon/farm/home_alarm.png');
		};
	}

	
	$scope.judgeUpdate = function(){
		//判断是否要更新
		$http.get( API_Host + "checkVersion.jsp")
		.success(function(data) {
			data = JSON.stringify(data);
			var tVersion = data.substring(data.indexOf("$")+1,data.indexOf("$",data.indexOf("$")+1));
			console.log("tVersion:" + tVersion);
			if(App_Version != tVersion){
				var ua = navigator.userAgent.toLowerCase();	
				console.log("客户端:" + ua);
				if (/iphone|ipad|ipod/.test(ua)) {
					console.log("ios");
					$scope.judgePassword();
				} else if (/android/.test(ua)) {
					console.log("android");
					app_confirm('服务器最新版本是：'+tVersion+'，请下载更新，更新过程中您的数据信息将不会丢失。',null,null,function(buttonIndex){
						if(buttonIndex == 1){
							// app_alert('您选择了【取消】');
							$scope.judgePassword();
						}else if(buttonIndex == 2){
							// 当前方法在JSUtil中定义
							appUpGrade($ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout,tVersion);
						}
					});	
				}else{
					Sparraw.myNotice("请在手机端点击。");
					$scope.judgePassword();	
				}
			}else{
				$scope.judgePassword();
			}
		})
		.error(function(data) {
			app_alert("服务器获取信息失败。");
		});
	}


	$scope.judgePassword = function(){
		if($scope.sparraw_user_temp.profile.password == "123456") {
			app_confirm('您的密码是初始密码，是否立即进行修改？', '提示', null, function (button) {
				if (button == 2) {
					$state.go("modifyUserInfo");
				}else{
					$scope.judgeDataInput();
				}
			});
		}else{
			$scope.judgeDataInput();
		};
	}

	$scope.judgeDataInput = function(){
		var params = {
	      	"operation":"needAlarm",
			"FarmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId                
		};
		Sparraw.ajaxPost('message/DataInputAlarm.action', params, function(data){
	   		if (data.ResponseDetail.Result == "Success") {
	   			if (data.ResponseDetail.NeedAlarm == 'Y') {
	   				var showList = data.ResponseDetail.AlarmHouseList.toString();
	   				showList = showList.replace(/(,)/g,"栋,");
					var buttonLabels = ['稍后填','立即填'];
					app_confirm('截止到昨天'+ showList +'栋，尚有生产记录未填写。',null,buttonLabels,function(buttonIndex){
						if(buttonIndex == 1){
							
						}else if(buttonIndex == 2){
							$state.go("collection");
						}
					});

	   			}else{

	   			}
		    }else {
		   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		    };
		});
	}

	setTimeout(function () {
		if (persistentData.switchRemind) {
			persistentData.switchRemind = false;
			$scope.judgeUpdate();
		}else{

		}
	},1000);


})
// 信息维护
.controller("infoSafeguardCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	

	$scope.goFarmRegistered = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"farmRegistered");
	}

	$scope.goBuildingTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"buildingTable");
	}

	$scope.goEmployeesTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"employeesTable");
	}
	$scope.goMyStandard = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"myStandard");
	}

	$scope.checkBaseInfo = function() {
		//Sparraw.getInfoStatus($ionicPopup,$state,"");
		
		if(!$scope.sparraw_user_temp.farminfo){
			app_alert("请先完善农场信息.")
			return false;
		}
		
		if(!$scope.sparraw_user_temp.userinfo.houses){
			app_alert("请先完善栋舍信息.")
			return false;
		}
		return true;
	};
	
	$scope.pointDevelop = function() {
		pointDevelop();
		return;	
	};

     $scope.showConfirm = function() {
          app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        userLogout();
                        $state.go("landingPage");
                   }
              }); 
   };

	$scope.exit = function(){
		userLogout();
		$state.go("landingPage");
	}

})
// 个人信息修改
.controller("modifyUserInfoCtrl",function($scope, $state, $http, AppData) {


	var oldPw;
	var newPw;
	var confirmPw;
	$scope.oldPassWordLostFocus = function(oldPassWord){
		oldPw = oldPassWord;
	}

	$scope.newPassWordLostFocus = function(newPassWord){
		newPw = newPassWord;
	}

	$scope.confirmPassWordLostFocus = function(confirmPassWord){
		confirmPw = confirmPassWord;
	}

	$scope.saveUpdate = function(){

		if (oldPw == null || newPw == null || confirmPw == null) {
			Sparraw.myNotice("请输入完整密码,谢谢。");
		}else {
			if (newPw != confirmPw) {
				Sparraw.myNotice("两次密码输入不一致请确认后再进行提交,谢谢。");
			}else {
				var params = {
							      'user_id'   : sparraw_user.userinfo.id        ,
							      'old_pw'    : oldPw                           ,
							      'new_pw'    : newPw                           
				   			 };

				Sparraw.ajaxPost('sys/user/upPassword.action', params, function(data){
			   		if (data.ResponseDetail.ErrorMsg == null) {
			   			 Sparraw.myNotice("修改成功");
			   			 sparraw_user.profile.user_State = true;
			   			 $state.go("landingPage");
				    }else {
				   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				    };
				});

			};
		};
		


	}

})
// 关于页面
.controller("aboutCtrl",function($scope, $state, $http, AppData,$cordovaFileTransfer,$ionicLoading,$timeout,$cordovaFileOpener2) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.App_Version = App_Version;

	if (/android/.test(DeviInfo.DeviceType)){
		$scope.UpdateBtn = false;//显示	
	}else{
		$scope.UpdateBtn = true;//隐藏
	};

	$scope.dlAPK = function(){
		$http.get( API_Host + "checkVersion.jsp")
		.success(function(data) {
			// console.log("Success:" + data);
			data = JSON.stringify(data);
			var tVersion = data.substring(data.indexOf("$")+1,data.indexOf("$",data.indexOf("$")+1));
			console.log("tVersion:" + tVersion);
			if(App_Version != tVersion){
				var ua = navigator.userAgent.toLowerCase();	
				console.log("客户端:" + ua);
				if (/iphone|ipad|ipod/.test(ua)) {
					console.log("ios");
					Sparraw.myNotice("该功能目前只支持安卓手机。");
				} else if (/android/.test(ua)) {
					console.log("android");
					app_confirm('服务器最新版本是：'+tVersion+'，请下载更新，更新过程中您的数据信息将不会丢失。',null,null,function(buttonIndex){
						if(buttonIndex == 1){
							// app_alert('您选择了【取消】');
						}else if(buttonIndex == 2){
							// 当前方法在JSUtil中定义
							appUpGrade($ionicLoading,$cordovaFileTransfer,$cordovaFileOpener2,$timeout,tVersion);
						}
					});	
				}else{
					Sparraw.myNotice("请在手机端点击。");	
				}
			}else{
				Sparraw.myNotice("当前已是最新版本。");
			}
		})
		.error(function(data) {
			app_alert("服务器获取信息失败。");
		});
	}
	$scope.queryVersion = function(){
		console.log("downLoad apk");
	};

	$scope.pointDevelop = function() {
		//Sparraw.getInfoStatus($ionicPopup,$state,"");
		pointDevelop();
		return;	
	};
})


//功能介绍
.controller("functionIntroduceCtrl",function($scope, $state, $http, AppData) {
	
})
//联系我们
.controller("contactUsCtrl",function($scope, $state, $http, AppData) {
//	var map = new BMap.Map("allmap");
//	var point = new BMap.Point(121.4025800000	,31.1722250000);
//	map.centerAndZoom(point, 35);
//	var marker = new BMap.Marker(point);  // 创建标注
//	map.addOverlay(marker);
})
//意见反馈
.controller("feedBackCtrl",function($scope, $state, $http, AppData) {
	
})
// 权限控制
.controller("accessControlCtrl",function($scope, $state, $http, AppData) {

})






//盈利报告
.controller("profitReportCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.profitReportData = {
				"FarmId" : $scope.sparraw_user_temp.farminfo.id,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
                "Expense": [{
		            "ItemName"          : ""  ,
		            "PricePKilo_last"   : ""  ,//上批次 元/公斤
		            "PricePUnit_last"   : ""  ,//上批次 元/只
		            "SaleChicken_last"  : ""  ,//上批次 万/元
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }],
		        "InCome": [{
		            "ItemName"          : ""  ,
		            "PricePKilo_last"   : ""  ,//上批次 元/公斤
		            "PricePUnit_last"   : ""  ,//上批次 元/只
		            "SaleChicken_last"  : ""  ,//上批次 万/元
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }],
		        "Profits": {
		        	"ItemName"          : ""  ,
		            "PricePKilo"        : ""  ,//上批次 毛鸡价
		            "SaleChicken"       : ""  ,//上批次 鸡苗价
		            "PricePUnit"        : ""  ,//上批次 饲料价
		            "PricePUnit_this"   : ""  ,//本批次 元/只
		            "PricePKilo_this"   : ""  ,//本批次 元/公斤
		            "PricSum_this"      : ""   //本批次 万元
		        }
	}


	
	if ($scope.sparraw_user_temp.farminfo.farmBreedBatchId == 0) {
		app_alert("未查询到批次信息,请先创建批次。");
		return;
		// return $state.go("batchManage");
	}else{

	};


	$scope.judgeIncome = function(item){
		console.log(item.ItemName);
		if (item.ItemName == "收入") {
    		return "{'font-weight': 'bold'}";
    	}else{	
    		return;
    	};
	}

	$scope.judgeCost = function(item){
		console.log(item.ItemName);
		if (item.ItemName == "成本") {
    		return "{'font-weight': 'bold'}";
    	}else{	
    		return;
    	};
	}

	


	$scope.inquireProfitReportData = function(){
		var params = {
				"FarmId"       : $scope.profitReportData.FarmId ,
				"BreedBatchId" : $scope.profitReportData.BreedBatchId
		};
    	Sparraw.ajaxPost('breedBatch/getProfitRep.action', params, function(data){
			if (data.ResponseDetail.Result === "Fail") {
				app_alert(data.ResponseDetail.ErrorMsg);
				return $state.go("batchManage");
			}else{
				$scope.profitReportData.Expense = data.ResponseDetail.Expense;
				$scope.profitReportData.InCome = data.ResponseDetail.InCome;
				$scope.profitReportData.Profits = data.ResponseDetail.Profits;
			};

	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });
	}
	$scope.inquireProfitReportData();

})

// 多批盈利查询
.controller("moreBatchProfitCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.moreBatchProfitData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,
				"Detail": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""  
		        }],
		        "OverView": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""  
		        }],
		        "DealPrice": [{
		            "ItemName"  : ""  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""  
		        }],
				"Profits":[{
		            "ItemName"  : "盈(亏)"  ,
		            "index1"    : ""  ,
		            "index2"    : ""  ,
		            "index3"    : ""  ,
		            "index4"    : ""  ,
		            "index5"    : ""  
		        }]
	}
	$scope.Title = "";



	



    $scope.inquireMultiProfit = function(){
    	if ($scope.moreBatchProfitData.BreedBatchId === 0) {
			app_alert("未查询到批次信息,请先创建批次。");
			return;
		};

		console.log($scope.moreBatchProfitData.transferUnit);
		if ($scope.moreBatchProfitData.transferUnit == "Money") {
	    	$scope.moreBatchProfitData.ViewUnit = "万元";
	    }else if ($scope.moreBatchProfitData.transferUnit == "quentity") {
	    	$scope.moreBatchProfitData.ViewUnit = "只";
	    }else if ($scope.moreBatchProfitData.transferUnit == "weight") {
	    	$scope.moreBatchProfitData.ViewUnit = "公斤";
	    };


		var params = {
				"FarmId"       : $scope.moreBatchProfitData.FarmId ,
				"BreedBatchId" : $scope.moreBatchProfitData.BreedBatchId,
				"ViewUnit"     : $scope.moreBatchProfitData.transferUnit
		};
    	Sparraw.ajaxPost('breedBatch/batchComparation.action', params, function(data){
			$scope.moreBatchProfitData.Detail = data.ResponseDetail.Detail;
			$scope.moreBatchProfitData.OverView = data.ResponseDetail.OverView;
			$scope.moreBatchProfitData.DealPrice = data.ResponseDetail.DealPrice;
			$scope.moreBatchProfitData.Profits = data.ResponseDetail.Profit;

			for (var i = 0; i < $scope.moreBatchProfitData.OverView.length; i++) {
				if ($scope.moreBatchProfitData.OverView[i].ItemName == "批次") {
					$scope.Title = $scope.moreBatchProfitData.OverView[i];
				}
			}
			$scope.moreBatchProfitData.OverView.shift();

	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });
	    
	}
	$scope.inquireMultiProfit();


	



	$scope.judgeNeedBold = function(item){
		if (item.ItemName != "毛鸡" && item.ItemName != "其它") {
    		return "{'font-weight': 'bold'}";
    	}else{	
    		return;
    	};
	}


	$scope.judgeCost = function(item){
		if (item.ItemName == "成本" || item.ItemName == "盈(亏)") {
    		return "{'font-weight': 'bold'}";
    	}else{	
    		return;
    	};
	}

})

// 多批结算查询
.controller("moreBatchClearingCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.moreBatchClearingData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				
				
				"DealPrice":[{
		               "ItemName"  :  ""  ,
		               "index1"    :  ""  ,
		               "index2"    :  ""  ,
		               "index3"    :  ""  ,
		               "index4"    :  ""  ,
		               "index5"    :  ""
		        }]
	}


	$scope.inquireMultiProfit = function(){
    	if ($scope.moreBatchClearingData.BreedBatchId === 0) {
			app_alert("未查询到批次信息,请先创建批次。");
			return;
		};





		var params = {
				"FarmId"       : $scope.moreBatchClearingData.FarmId ,
		};
    	Sparraw.ajaxPost('breedBatch/batchSettleComparation.action', params, function(data){
			/*$scope.moreBatchProfitData.Detail = data.ResponseDetail.Detail;
			$scope.moreBatchProfitData.OverView = data.ResponseDetail.OverView;
			$scope.moreBatchProfitData.DealPrice = data.ResponseDetail.DealPrice;*/
			console.log(data);
			$scope.moreBatchClearingData.DealPrice = data.ResponseDetail.DealPrice;


	    },function(data){
	    	Sparraw.myNotice("查询失败");
	    });
	    
	}
	$scope.inquireMultiProfit();
})

//生产性能标准
.controller("prodPerfStanTableCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setPortrait(true,true);

	persistentData.standardType = "";
	$scope.listArray = ['科宝(2015)','罗斯(2014)','AA+(2014)','正大笼养','正大平养'];
	$scope.goNext = function(item){
		if (item == "我的标准") {
			return $state.go("myStandard");
		}else{
			persistentData.standardType = item;
			return $state.go("fixedStandard");
		}
	}

})

//固定标准
.controller("fixedStandardCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	/*setLandscape(true,true,function(){
		$scope.gridOptionsDiv = false;
	});
	$scope.gridOptionsDiv = true;*/


	$scope.gridOptionsDiv = false;

	$scope.fixeStanData = {
		"title"       :"",
		"StandardName":"",
   		"FarmId"      :$scope.sparraw_user_temp.farminfo.id,
   		"TableData"   :[]
	}


	$scope.fixeStanData.title = "固定标准";
	switch(persistentData.standardType){
			case "科宝(2015)":
			  	$scope.fixeStanData.title = "科宝(2015)";
			  	$scope.fixeStanData.StandardName = 10001;
			  break;
			case "AA+(2014)":
			  	$scope.fixeStanData.title = "AA+(2014)";
			  	$scope.fixeStanData.StandardName = 10002;
			  break;
			case "罗斯(2014)":
			  	$scope.fixeStanData.title = "罗斯(2014)";
			  	$scope.fixeStanData.StandardName = 10003;
			  break;
			case "正大笼养":
			  	$scope.fixeStanData.title = "正大笼养";
			  	$scope.fixeStanData.StandardName = 10004;
			  break;
			default:
				$scope.fixeStanData.title = "正大平养";
				$scope.fixeStanData.StandardName = 10005;
	};


	$scope.gridOptions = {};

	$scope.GetTable = function(){
		var TempshowTableData = {};
		var header = [];
		var firstFixed = true; //首列是否固定ture-固定，false-不固定
		var rowHeight  = '';//内容高度
		var TableData  = $scope.fixeStanData.TableData;
		var TableKey = [];

		if ($scope.fixeStanData.title == "正大平养" ||
			$scope.fixeStanData.title == "正大笼养") {
			var TableShowName = ["日龄","累计死淘(%)","体重(g)","日增重(g)","日采食(g)","累计采食(g)","累计料肉比"];
			TableKey = ["growth_age","acc_cdreate1","body_weight1","daily_bddiff1","daily_feed1","acc_feed1","FCR1"];
			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '36',
							'headerCellTemplate'  :  '<div style="width:35px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}else if (i == 1) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '81',
							'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}else{
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '81',
							'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-left:solid 0.5px #ECECEC; border-right:solid 0.5px #ECECEC;"><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}
			}
			
		}else{
			var TableShowName = ["日龄",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比"];

			var TableOneLevelTitle = ["",
										"公母混养",
										"",
										"",
										"",
										"",
										"",
										"公鸡",
										"",
										"",
										"",
										"",
										"",
										"母鸡"];

			TableKey = ["growth_age",
			            "oneLevelHeader1",
						"body_weight1",
						"daily_bddiff1",
						"daily_feed1",
						"acc_feed1",
						"FCR1",
						"oneLevelHeader2",
						"body_weight2",
						"daily_bddiff2",
						"daily_feed2",
						"acc_feed2",
						"FCR2",
						"oneLevelHeader3",
						"body_weight3",
						"daily_bddiff3",
						"daily_feed3",
						"acc_feed3",
						"FCR3"];
			

			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '50',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 1 || i == 7 || i == 13) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '1',
						'displayName'         :  '',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:435px;height:60px; background-image:url("img/icon/clear_image.png");"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:100%; background:#FBFBFB;"><p class="middle" style="position: relative;top:5px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  ''
					})
				}else if (i == 2 || i == 8 || i == 14) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '70',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 3 || i == 9 || i == 15) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '85',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 4 || i == 10 || i == 16) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '85',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 5 || i == 11 || i == 17) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '100',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 6 || i == 12 || i == 18) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '95',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle' 
					})
				}
			}
		}


		TempshowTableData = {
			'header'      :header,
			'firstFixed'  :firstFixed,
			'rowHeight'   :rowHeight,
			'TableData'   :TableData
		};
		var showTableData = TempshowTableData;
		$scope.gridOptions = {
		    rowHeight: showTableData.rowHeight,
		    enableSorting: false
		};
		$scope.gridOptions.columnDefs = [];
		for (var i = 0; i < showTableData.header.length; i++) {
		    if (i == 0  && showTableData.firstFixed == true) {
		      	$scope.gridOptions.columnDefs.push({ 
	                name                :  showTableData.header[i].name                ,  
	                displayName         :  showTableData.header[i].displayName         , 
	                width               :  showTableData.header[i].width               ,
	                headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                pinnedLeft          :  true                                        ,
	                enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
	            });
		    }else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }

		        });
		    };
		  }
	  	$scope.gridOptions.data = showTableData.TableData;
	}


	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.fixeStanData.FarmId        ,
			"StandardName"   :    $scope.fixeStanData.StandardName    
		};
		Sparraw.ajaxPost('standard/detailQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				$scope.fixeStanData.TableData = data.ResponseDetail.DetailData;
				$scope.GetTable();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	//$scope.GetTable();


})

//我的标准 
.controller("myStandardCtrl",function($scope, $state, $ionicModal, $stateParams, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(false,false);
	$scope.myStanData = {
		"FarmId"       :  $scope.sparraw_user_temp.farminfo.id,
		"StandardName" :  20001,
		"selectDetailData": "",
		"GetBreedName" :  "",
		"DetailData"   :  []
	}

	$ionicModal.fromTemplateUrl('modalWindow.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

	$scope.gridOptions = {};
	$scope.GetTable = function(){
		var TempshowTableData = {};
		var header = [];
		var firstFixed = true; //首列是否固定ture-固定，false-不固定
		var rowHeight  = '';//内容高度
		var TableData = $scope.myStanData.DetailData;
		var TableShowName = [];
		var TableKey = [];

		TableKey = ['growth_age','acc_cdreate1','body_weight1','daily_bddiff1','daily_feed1','acc_feed1','FCR1'];
		TableShowName = ["日龄","累计死淘(%)","体重(g)","日增重(g)","日采食(g)","累计采食(g)","累计料肉比"];
		for (var i = 0; i < TableKey.length; i++) {
			
			if (i == 0) {
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '36',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:35px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  true
				})
			}else if (i == 1) {
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '81',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  false
				})
			}else{
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '81',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-left:solid 0.5px #ECECEC; border-right:solid 0.5px #ECECEC;"><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  false
				})
			}
		}

		TempshowTableData = {
			'header'      :header,
			'firstFixed'  :firstFixed,
			'rowHeight'   :rowHeight,
			'TableData'   :TableData
		};

		var showTableData = TempshowTableData;
		$scope.gridOptions = {
		    rowHeight: showTableData.rowHeight,
		};
		$scope.gridOptions.columnDefs = [];
		for (var i = 0; i < showTableData.header.length; i++) {
		    if (i == 0  && showTableData.firstFixed == true) {
		      	$scope.gridOptions.columnDefs.push({ 
	                name                :  showTableData.header[i].name                ,  
	                displayName         :  showTableData.header[i].displayName         , 
	                width               :  showTableData.header[i].width               ,
	                headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                enableCellEdit      :  showTableData.header[i].enableCellEdit      ,
	                pinnedLeft          :  true                                        ,
	                enableColumnMenu    :  false                                       ,
	                cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
	            });
		    }else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            cellClass           :  showTableData.header[i].cellClass           ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableCellEdit      :  showTableData.header[i].enableCellEdit      ,
		            enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
		        });

		    };
		  }
	  	$scope.gridOptions.data = showTableData.TableData;
	  	//判断哪些数据进行过修改
		$scope.gridOptions.onRegisterApi = function(gridApi){
			$scope.gridApi = gridApi;
			//input获取焦点的时候
			gridApi.edit.on.beginCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
				for (var i = 0; i < $scope.myStanData.DetailData.length; i++) {
					if ($scope.myStanData.DetailData[i].growth_age == rowEntity.growth_age) {
						$scope.myStanData.selectDetailData = $scope.myStanData.DetailData[i]
					}
				}
				$scope.myStanData.selectDetailData.acc_cdreate1 = parseFloat($scope.myStanData.selectDetailData.acc_cdreate1);
				$scope.openFun();
			});
			//input失去焦点时调用
			//gridApi.edit.on.afterCellEdit($scope,afterCellEdit);
		};
	}



	$scope.inquire = function(){
		var params = {
			"StandardName":$scope.myStanData.StandardName,
   			"FarmId":$scope.myStanData.FarmId   
		};
		Sparraw.ajaxPost('standard/detailQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				$scope.myStanData.DetailData = data.ResponseDetail.DetailData;
				$scope.myStanData.GetBreedName = data.ResponseDetail.BreedName;
				if ($scope.myStanData.DetailData.length == 0) {
					Sparraw.myNotice("暂无数据，请先设定标准。");
				}else{

				}
				$scope.GetTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.inquire();
	$scope.GetTable();


	$scope.save = function(){

		if ($scope.myStanData.selectDetailData.acc_feed1 == "-") {
			$scope.myStanData.selectDetailData.acc_feed1 = 0;
		}
		if ($scope.myStanData.selectDetailData.body_weight1 == "-") {
			$scope.myStanData.selectDetailData.body_weight1 = 0;
		}
		if ($scope.myStanData.selectDetailData.daily_bddiff1 == "-") {
			$scope.myStanData.selectDetailData.daily_bddiff1 = 0;
		}

		var transferArr = [];
		transferArr.push($scope.myStanData.selectDetailData);
		var params = {
   			"FarmId":$scope.myStanData.FarmId,
   			"DetailData":transferArr
		};

		console.log($scope.myStanData.selectDetailData);

		Sparraw.ajaxPost('standard/editSave.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.inquire();
				$scope.modalDIV.hide();
				Sparraw.myNotice("保存成功");
			}else if (data.ResponseDetail.Result == "Fail") {

			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}
})

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
			console.log($scope.setStandardData.SettingData.cum_mortality.length);
			
			app_confirm('是否使用系统推荐数据?','提示',null,function(buttonIndex){
	           if(buttonIndex == 2){
	           	console.log($scope.setStandardData.SettingData.mortality_type);
				    //判断展示的默认数据
					if ($scope.setStandardData.SettingData.mortality_type == "1") {
						var dateArr = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49];
						var valueArr = [0.06,0.14,0.22,0.30,0.38,0.46,0.54,0.60,0.65,0.71,0.77,0.83,0.89,0.95,1.00,1.07,1.14,1.21,1.28,1.35,1.42,1.50,1.57,1.64,1.71,1.78,1.85,1.92,2.00,2.07,2.15,2.23,2.31,2.39,2.47,2.55,2.70,2.85,3.00,3.15,3.30,3.45,3.60,3.75,3.90,4.05,4.20,4.35,4.50];
						$scope.setStandardData.dayData = getDayData(dateArr,valueArr);
					}else{
						var dateArr = [1,2,3,4,5,6,7];
						var valueArr = [0.60,1.00,1.50,2.00,2.55,3.6,4.5];
						$scope.setStandardData.weekData = getWeekData(dateArr,valueArr);
					}
	           }else{
	           		if ($scope.setStandardData.SettingData.mortality_type == "1") {
						for (var i = 0; i <= 49; i++) {
							$scope.setStandardData.dayData.push({
								"dayAge": i,
						        "cum_rate": 0,
						        "cum_alert": 0
							})
						}
					}else{
						for (var i = 1; i <= 7; i++) {
							$scope.setStandardData.weekData.push({
								"weekAge": i,
						        "cum_rate": 0,
						        "cum_alert": 0
							})
						}
					}
	           }
	        });
			
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
		if ($scope.setStandardData.SettingData.mortality_type == "") {
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
			}else{
				for (var i = 0; i < $scope.setStandardData.weekData.length; i++) {
					if ($scope.setStandardData.weekData[i].cum_rate == 0 || 
						isNaN($scope.setStandardData.weekData[i].cum_rate)) {
						console.log($scope.setStandardData.weekData[i].cum_rate);
						return app_alert("请输入正确的数据");
					}else{

					}
				}
				$scope.setStandardData.SettingData.cum_mortality = $scope.setStandardData.weekData;
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

//日报管理
.controller("dailyTableCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.dailyTableData = {
		"listArray" : ["生产记录","生产日报","生产周报","批次报告"]
	}
	
	$scope.goNext = function(item){
		switch(item){
				case "生产记录":
					$state.go("prodReco");
				  	return;
				  break;
				case "生产日报":
					$state.go("dailyDay");
				  	return;
				  break;
				case "生产周报":
					$state.go("weekly");
				  	return;
				  break;
				default:
					$state.go("productionSumReport");
					return;
				};
	}

	
	
})

// 日报统计
.controller("dailyStatisticalCtrl",function($scope, $state, $http, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	//数据源
	$scope.DaystatisticalData = {
		"HouseBreedId"      :  ""  ,  //int型，栋舍饲养批次Id
		"HouseId"           :  ""  ,  //int型，栋舍Id
		"CurDayAge"         :  ""  ,  //int型，当前日龄
		"CurDate"           :  ""  ,  //varchar型，当前日期
		"cur_amount"        :  ""  ,  //int型，存栏数量
		"original_amount"   :  ""  ,  //int型，入雏数量
		"acc_feed"          :  ""  ,  //varchar型，累计采食量

		"std_acc_cd_rate"   :  ""  ,  //varchar型，警戒累计死淘率
		"std_acc_cd"        :  ""  ,  //int型，警戒累计死淘数量

		"atu_acc_cd_rate"   :  ""  ,  //varchar型，累计死淘率
		"atu_acc_cd"        :  ""  ,  //int型，累计死淘数量






		"dataInput":[{
			"day_age"          :  ""  ,  //int型，对应日龄
			"daily_feed"       :  ""  ,  //varchar型，日采食量
			"daily_weight"     :  ""  ,  //varchar型，日均重
			"atu_cd_num"       :  ""  ,  //日死淘数
			"atu_cd_rate"      :  ""  ,  //varchar型，日死淘率
			"feed_conversion"  :  ""  ,  //varchar型，料肉比
		}]
		
	}

	/*//默认设定默认栋舍
	$scope.takeHouseId;
	for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
		if ($scope.sparraw_user_temp.userinfo.houses[i].HouseId == $stateParams.receiveHouseId) {
			$scope.takeHouseId = $scope.sparraw_user_temp.userinfo.houses[i];
		}; 
	};*/


	//默认设定默认栋舍
	$scope.placeDatas = {
            'selectHouse':  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]), 
            'placeNum'   :  '', 
            'placeDate'  :  '' 
	};

	//查询日报信息
    $scope.judgeHouse = function(){
    	//先获取栋舍信息
		var housebreedid;
		var houseid;
		if (typeof($scope.placeDatas.selectHouse) == "object" && Object.prototype.toString.call($scope.placeDatas.selectHouse).toLowerCase() == "[object object]" && !$scope.placeDatas.selectHouse.length) {
    		housebreedid  =  $scope.placeDatas.selectHouse.HouseBreedBatchId  ,
			houseid       =  $scope.placeDatas.selectHouse.HouseId
    	}else{
    		tempData  =  JSON.parse($scope.placeDatas.selectHouse)  ,
			housebreedid  =  tempData.HouseBreedBatchId             ,
			houseid       =  tempData.HouseId
    	};
    	$scope.DaystatisticalData.HouseBreedId  =  housebreedid  ;
    	$scope.DaystatisticalData.HouseId       =  houseid       ;

    	
    	//查询日 报 统 计信息
    	if ($scope.DaystatisticalData.HouseBreedId == 0) {
    		$scope.DaystatisticalData.HouseBreedId     =  ""   ;
    		$scope.DaystatisticalData.CurDayAge        =  "-"  ;
    		$scope.DaystatisticalData.CurDate          =  Sparraw.getNowFormatDate()  ;//当前日期
    		$scope.DaystatisticalData.cur_amount       =  "-"  ;
    		$scope.DaystatisticalData.original_amount  =  "-"  ;
    		$scope.DaystatisticalData.acc_feed         =  "-"  ;
    		$scope.DaystatisticalData.std_acc_cd_rate  =  "-"  ;
    		$scope.DaystatisticalData.std_acc_cd       =  "-"  ;
    		$scope.DaystatisticalData.atu_acc_cd_rate  =  "-"  ;
    		$scope.DaystatisticalData.atu_acc_cd       =  "-"  ;
    		$scope.DaystatisticalData.dataInput        =  []   ;
    		Sparraw.myNotice("暂无统计信息，请先入雏");
    	}else{
    		var params = {
				//"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"FarmBreedId"   :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"HouseId"       :  $scope.DaystatisticalData.HouseId
			};
			
		Sparraw.ajaxPost('dataInput/DRShow.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
					$scope.DaystatisticalData = data.ResponseDetail;
					$scope.DaystatisticalData.dataInput = data.ResponseDetail.dataInput;
					$scope.DaystatisticalData.CurDate   =  Sparraw.getNowFormatDate();//当前日期
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
    	};
		
    }
    //自动查询
    $scope.judgeHouse();


    /*$scope.judgeDayAge = function(obj){
    	if (obj == $scope.DaystatisticalData.CurDayAge) {
    		return "{background:'#00CD66'}";
    	}else{
    		return "{background:'#fff'}";
    	};
    }
*/	
})

//生产日报(当天)
.controller("dailyDayCtrl",function($scope, $state, $http, $ionicModal,$stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setLandscape(true,true);



	document.getElementById('dailyDay_DIV').style.height = (screen.width - 75) + 'px';



	$scope.dailyDayData = {
		"farmBreedId": $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"selectHouse":$scope.sparraw_user_temp.userinfo.houses[0],
		"cur_amount":"",
        "original_amount":"",  
		"dataInfo":"",
		"title":"生产日报(当天)",
		"date":"",
		"survRate":""
	}
	var dayNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");  
	Stamp = new Date();  
	$scope.dailyDayData.date = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";
	$scope.toDayGridOptions = {};


	if (persistentData.dailySelectHouse == "" || !persistentData.dailySelectHouse) {
		
	}else{
		$scope.dailyDayData.selectHouse = JSON.parse(persistentData.dailySelectHouse);
	}


	$scope.goDailyCumu = function(){
		$state.go('dailyCumu',{ animation: 'slide-in-up'});
	}


	$scope.GetDayTable = function(){
		//表头部分
		var toDayTableKey = [];
		var toDayheader = [];
		/*var toDayTitleName = ["日龄","死亡","淘汰","死淘","死淘率","采食","日耗","标准","饮水","水料比","均重","标准"];
		toDayTableKey = ["day_age",
						 "death_num",
						 "culling_num",
						 "dc_num",
						 "dc_rate_actual",
						 "intake_sum",
						 "intake_sig",
						 "intake_standard",
						 "warter_sum",
						 "ratio_water_feed",
						 "body_weight_actual",
						 "body_weight_standard"];*/

		var toDayTitleName = ["日龄","死亡","淘汰","死淘","死淘率","标准","采食","日耗","标准","饮水","水料比"];
		toDayTableKey = ["day_age",
						 "death_num",
						 "culling_num",
						 "dc_num",
						 "dc_rate_actual",
						 "dc_rate_standard",
						 "intake_sum",
						 "intake_sig",
						 "intake_standard",
						 "warter_sum",
						 "ratio_water_feed"];

		for (var i = 0; i < toDayTableKey.length; i++) {
			if (i == 0  || i == 5 || i == 7 ||i == 9 ||i == 11) {
				
				if (i == 0) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '30',
						'headerCellTemplate'  :  '<div style="width:30px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else if (i == 5) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '90',
						'headerCellTemplate'  :  '<div style="width:90px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else if (i == 7) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '60',
						'headerCellTemplate'  :  '<div style="width:60px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else{
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '60',
						'headerCellTemplate'  :  '<div style="width:60px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}
			}else{
				toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '50',
						'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})

			}
		}


		var toDayTableData = {
		    'header' : toDayheader,
		    'firstFixed': true, //首列是否固定ture-固定，false-不固定
		    'rowHeight' : 25,//内容高度
		    'TableData' : $scope.dailyDayData.dataInfo
		}
		
		var showTableData = toDayTableData;

		$scope.toDayGridOptions = {
			rowHeight: showTableData.rowHeight,
		};
		$scope.toDayGridOptions.columnDefs = [];


		//表主体部分
		  for (var i = 0; i < showTableData.header.length; i++) {
		  	if (i == 0 || i == 4 ||i == 5 || i == 7 || i == 8 ||i == 9 ||i == 11) {
				
				
				if (i == 0 && showTableData.firstFixed == true) {
					$scope.toDayGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        pinnedLeft          :  true                                        ,
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {

		                    								if (row.entity.day_age) {
			                    								if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
														            return 'fontBoldLineLineRightStyle';
														        }else{
														          	return 'fontBoldUnderlineStyle';
														        }

			                    							}else if (row.entity.day_age == 0) {
			                    								return 'fontBoldUnderlineStyle';
			                    							}

															if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
																return 'UnderLineLineRightStyle';
															}else{
																return 'LineRightCellStyle';
															}
												        }
													});
				}else if (i == 5 || i == 8) {
					$scope.toDayGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightCustomColorStyle';
												          }else{
												          	return 'LineRightCellCustomColorStyle';
												          }
												        }
													});
				}else{
					$scope.toDayGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {

												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
													});
				}
			}else{
				$scope.toDayGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
			}

		  }
		  $scope.toDayGridOptions.data = showTableData.TableData;
	}






	$scope.inquire = function(){
		if (typeof($scope.dailyDayData.selectHouse) == "object") {
			TempHouseId = $scope.dailyDayData.selectHouse.HouseId;
		}else{
			TempHouseId = JSON.parse($scope.dailyDayData.selectHouse).HouseId;
		}

		var params = {
			"FarmBreedId":$scope.dailyDayData.farmBreedId,
			"HouseId":TempHouseId
		};

		Sparraw.ajaxPost('dataInput/curDailyRP.action', params, function(data){
			console.log("--------数据！！！--------------");
			console.log($scope.dailyDayData);
			console.log("----------------------");
			if (data.ResponseDetail.Result == "Success") {
				$scope.dailyDayData.dataInfo = data.ResponseDetail.dataInfo;
				$scope.dailyDayData.cur_amount = data.ResponseDetail.cur_amount;
				$scope.dailyDayData.original_amount = data.ResponseDetail.original_amount;

				



				if ($scope.dailyDayData.original_amount == $scope.dailyDayData.cur_amount) {
					$scope.dailyDayData.survRate = 100;
				}else{
					$scope.dailyDayData.survRate = parseFloat($scope.dailyDayData.original_amount / $scope.dailyDayData.cur_amount).toFixed(2);
					$scope.dailyDayData.survRate = 100 - $scope.dailyDayData.survRate;
					if (!app_IsNum($scope.dailyDayData.survRate) || !isFinite($scope.dailyDayData.survRate)) {
						$scope.dailyDayData.survRate = 0;
					}else{

					}
				}

				
				$scope.GetDayTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				$scope.dailyDayData.dataInfo = [];
				$scope.dailyDayData.cur_amount = "";
				$scope.dailyDayData.original_amount = "";
				$scope.GetDayTable();
				Sparraw.myNotice("暂无数据。");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				$scope.dailyDayData.dataInfo = [];
				$scope.dailyDayData.cur_amount = "";
				$scope.dailyDayData.original_amount = "";
				$scope.GetDayTable();
			};
    	});
	}

	$scope.judgeHouse = function(){
		persistentData.dailySelectHouse = $scope.dailyDayData.selectHouse;
		$scope.inquire();
	}

	$scope.goDailyTable = function(){
		if (selectBackPage.reportingBack == "prodReco") {
			$state.go('prodReco',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";	
		}else{
			$state.go('dailyTable',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";
		}
	}

	$scope.GetDayTable();
	setTimeout(
        function (){
          $scope.inquire();
        }
    ,1000);

})

//生产日报(累计)
.controller("dailyCumuCtrl",function($scope, $state, $http,$ionicModal, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setLandscape(true,true);

	document.getElementById('dailyCumu_DIV').style.height = (screen.width - 75) + 'px';

	$scope.dailyCumuData = {
		"farmBreedId": $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"selectHouse":$scope.sparraw_user_temp.userinfo.houses[0],
		"cur_amount":"",
        "original_amount":"", 
		"dataInfo":"",
		"title":"生产日报(累计)",
		"date":"",
		"survRate":""
	}
	var dayNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");  
	Stamp = new Date();  
	$scope.dailyCumuData.date = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";
	$scope.TotalGridOptions = {};


	if (persistentData.dailySelectHouse == "" || !persistentData.dailySelectHouse) {
		
	}else{
		$scope.dailyCumuData.selectHouse = JSON.parse(persistentData.dailySelectHouse);
	}


	$scope.goDailyDay = function(){
		$state.go('dailyDay',{ animation: 'slide-in-up'});
	}

	$scope.goDailyTable = function(){
		if (selectBackPage.reportingBack == "prodReco") {
			$state.go('prodReco',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";	
		}else{
			$state.go('dailyTable',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";
		}
	}

	$scope.GetTotalTable = function(){
			//累计表
			var TotalTableKey = [];
			var Totalheader = [];
			/*var TotalTitleName = ["日龄","死亡","淘汰","死淘","死淘率","标准","采食","只耗","标准","料肉比","标准","饮水","水料比"];
			TotalTableKey = ["day_age","acc_death_num","acc_culling_num","acc_dc_num","accdc_rate_act","accdc_rate_sta","acc_intake_sum","acc_intake_sig","acc_intake_sta","feed_body_act","feed_body_sta","acc_warter_sum","ratio_water_feed",];*/

			var TotalTitleName = ["日龄",
								  "死亡",
								  "淘汰",
								  "死淘",
								  "死淘率",
								  "标准",
								  "采食",
								  "只耗",
								  "标准",
								  "饮水",
								  "水料比"];
			TotalTableKey = ["day_age",
							 "acc_death_num",
							 "acc_culling_num",
							 "acc_dc_num",
							 "accdc_rate_act",
							 "accdc_rate_sta",
							 "acc_intake_sum",
							 "acc_intake_sig",
							 "acc_intake_sta",
							 "acc_warter_sum",
							 "ratio_water_feed"];

			//表头部分
			for (var i = 0; i < TotalTableKey.length; i++) {
				if (i == 0 ||i == 5 ||i == 8 ||i == 10 ||i == 12) {

					if (i == 0) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '30',
							'headerCellTemplate'  :  '<div style="width:30px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 5) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 8 ||i == 10) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 12) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else{
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '58',
							'headerCellTemplate'  :  '<div style="width:58px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}
				}else if (i == 4 || i == 9 || i == 12) {


					if (i == 4) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 9) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else{
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '68',
							'headerCellTemplate'  :  '<div style="width:68px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}

					
				}else if (i == 7) {
					Totalheader.push({
						'name'                :  TotalTableKey[i],
						'width'               :  '55',
						'headerCellTemplate'  :  '<div style="width:55px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false
					})
				}else{
					Totalheader.push({
						'name'                :  TotalTableKey[i],
						'width'               :  '55',
						'headerCellTemplate'  :  '<div style="width:55px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false
					})
				}
			}

			var TotalTableData = {
			    'header' : Totalheader,
			    'firstFixed': true, //首列是否固定ture-固定，false-不固定
			    'rowHeight' : 25,//内容高度
			    'TableData' : $scope.dailyCumuData.dataInfo
			}
			
			var showTableData = TotalTableData;

			$scope.TotalGridOptions = {
				rowHeight: showTableData.rowHeight,
			};
			//表主体部分
			$scope.TotalGridOptions.columnDefs = [];
			  for (var i = 0; i < showTableData.header.length; i++) {
			    if (i == 0 ||i == 5 ||i == 8  ||i == 12) {

					if (i == 0 && showTableData.firstFixed == true) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        pinnedLeft          :  true                                        ,
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
		                    								if (row.entity.day_age) {
			                    								if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
														            return 'fontBoldLineLineRightStyle';
														        }else{
														          	return 'fontBoldUnderlineStyle';
														        }

			                    							}else if (row.entity.day_age == 0) {
			                    								return 'fontBoldUnderlineStyle';
			                    							}

												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
													});
					}else if (i == 5 ||i == 8 ||i == 10) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightCustomColorStyle';
												          }else{
												          	return 'LineRightCellCustomColorStyle';
												          }
												        }
													});
					}else if (i == 12) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightCustomColorStyle';
												          }else{
												          	return 'LineRightCellCustomColorStyle';
												          }
												        }
													});
					}else{
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
													});
					}
				}else if (i == 4 || i == 9 || i == 12) {
					if (i == 4 || i == 9) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
					}else{
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
					}
				}else if (i == 7) {
					$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
				}else{
					$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
				}
			  }
			  $scope.TotalGridOptions.data = showTableData.TableData;
	}



	$scope.inquire = function(){
		if (typeof($scope.dailyCumuData.selectHouse) == "object") {
			TempHouseId = $scope.dailyCumuData.selectHouse.HouseId;
		}else{
			TempHouseId = JSON.parse($scope.dailyCumuData.selectHouse).HouseId;
		}

		var params = {
			"FarmBreedId":$scope.dailyCumuData.farmBreedId,
			"HouseId":TempHouseId
		};

		Sparraw.ajaxPost('dataInput/accDaiyRP.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.dailyCumuData.dataInfo = data.ResponseDetail.dataInfo;
				$scope.dailyCumuData.cur_amount = data.ResponseDetail.cur_amount;
				$scope.dailyCumuData.original_amount = data.ResponseDetail.original_amount;
				for (var i = 0; i < $scope.dailyCumuData.dataInfo.length; i++) {
					$scope.dailyCumuData.dataInfo[i].dc_rate_actual = $scope.dailyCumuData.dataInfo[i].dc_rate_actual + "%"; 
				}


				if ($scope.dailyCumuData.original_amount == $scope.dailyCumuData.cur_amount) {
					$scope.dailyCumuData.survRate = 100;
				}else{
					$scope.dailyCumuData.survRate = parseFloat($scope.dailyCumuData.original_amount / $scope.dailyCumuData.cur_amount).toFixed(2);
					$scope.dailyCumuData.survRate = 100 - $scope.dailyCumuData.survRate;
					if (!app_IsNum($scope.dailyCumuData.survRate) || !isFinite($scope.dailyCumuData.survRate)) {
						$scope.dailyCumuData.survRate = 0;
					}else{

					}
				}





				$scope.GetTotalTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				$scope.dailyCumuData.dataInfo = [];
				$scope.dailyCumuData.cur_amount = "";
				$scope.dailyCumuData.original_amount = "";
				$scope.GetTotalTable();
				Sparraw.myNotice("暂无数据。");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				$scope.dailyCumuData.dataInfo = [];
				$scope.dailyCumuData.cur_amount = "";
				$scope.dailyCumuData.original_amount = "";
				$scope.GetTotalTable();
			};
    	});
	}

	$scope.judgeHouse = function(){
		persistentData.dailySelectHouse = $scope.dailyCumuData.selectHouse;
		$scope.inquire();
	}

	


	$scope.GetTotalTable();
	setTimeout(
        function (){
          $scope.inquire();
        }
    ,1000);


})

//生产周报
.controller("weeklyCtrl",function($scope, $state, $http,$ionicModal, $stateParams, $ionicPopup, AppData) {

	$scope.goDailyTable = function(){
		$state.go('dailyTable');
	}

	$scope.setData = function() {
		$scope.weeklyData = {
	   		"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			"HouseId"      :  $scope.sparraw_user_temp.userinfo.houses[0].HouseId,
	   		"TableData"   :  [],
	   		"selectHouse" :  $scope.sparraw_user_temp.userinfo.houses[0]
		}
		$scope.judgeHouse();
	}


	$scope.getTable = function(){

			var header = [];
			var firstFixed = true; //首列是否固定true-固定，false-不固定
			var rowHeight  = '';//内容高度
			var TableShowName = ["周龄",
								 "NULLS0",
								 "数量",
								 "比率(%)",
								 "标准(%)",
								 "NULLS1",
								 "数量(千克)",
								 "日耗(克)",
								 "标准(克)",
								 "NULLS2",
								 "数量(升)",
								 "水/料",
								 "NULLS3",
								 "实际(克)",
								 "标准",
								 "NULLS4",
								 ""];

			var TableOneLevelTitle = ["周龄",
										"死淘",
										"",
										"",
										"",
										"采食",
										"",
										"",
										"",
										"饮水",
										"",
										"",
										"体重",
										"",
										"",
										"料肉比"];

			var TableKey = ["age",
							"NULLS0",
							"dc_act_num",
							"dc_act_rate",
							"dc_stan_rate",
							"NULLS1",
							"intake_act_sum",
							"intake_act_sig",
							"intake_stan_sig",
							"NULLS2",
							"water_act_sum",
							"ratio_water_feed",
							"NULLS3",
							"body_weight_actual",
							"body_weight_standard",
							"NULLS4",
							"ratio_intake_body"];



			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '70',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:70px;height:60px; background:#FBFBFB;"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:120px; background:#FBFBFB;z-index:100;"><p class="middle" style="position: relative;top:20px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'tableBackground'
					})
				}else if (i == 1 || i == 5) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '1',
						'displayName'         :  '',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:210px;height:60px; background-image:url("img/icon/clear_image.png");"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:100%; background:#FBFBFB;"><p class="middle" style="position: relative;top:5px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  ''
					})
				}else if (i == 9 || i == 12) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '1',
						'displayName'         :  '',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:140px;height:60px; background-image:url("img/icon/clear_image.png");"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:100%; background:#FBFBFB;"><p class="middle" style="position: relative;top:5px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  ''
					})
				}else if (i == 15) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '1',
						'displayName'         :  '',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:1px; width:70px;height:60px; background:#FBFBFB;"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:120px; background:#FBFBFB;z-index:100;"><p class="middle" style="position: relative;top:20px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  ''
					})
				}else{
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '70',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:70px;height:60px; background-image:url("img/icon/clear_image.png");"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:100%; background:#FBFBFB;"><p class="middle" style="position: relative;top:5px;">' + TableShowName[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}

			}


			TempshowTableData = {
				'header'      :header,
				'firstFixed'  :firstFixed,
				'rowHeight'   :rowHeight,
				'TableData'   :$scope.weeklyData.TableData
			};
			var showTableData = TempshowTableData;
			$scope.gridOptions = {
			    rowHeight: showTableData.rowHeight,
			    enableSorting: false
			};
			$scope.gridOptions.columnDefs = []; 
			for (var i = 0; i < showTableData.header.length; i++) {
		    if (i == 0  && showTableData.firstFixed == true) {
		      	$scope.gridOptions.columnDefs.push({ 
	                name                :  showTableData.header[i].name                ,  
	                displayName         :  showTableData.header[i].displayName         , 
	                width               :  showTableData.header[i].width               ,
	                headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                pinnedLeft          :  true                                        ,
	                enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												        if (row.entity.age == "累计") {
		            										return 'LineRightCellStyle';
		            									}else if (row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) % 7 == 0 && row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) != 0) {
												            return 'UnderLineLineRightStyle';
												        }else{
												          	return 'LineRightCellStyle';
												        }
												    }
	            });
		    }else if (i == 4 || i == 8 || i == 14) {
				$scope.gridOptions.columnDefs.push({ 
	                        name                :  showTableData.header[i].name                ,  
	                        displayName         :  showTableData.header[i].displayName         , 
	                        width               :  showTableData.header[i].width               ,
	                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
	                        enableColumnMenu    :  false                                       ,
	                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
											          	if (row.entity.age == "累计") {
		            										return 'LineRightCellStyle';
		            								 	}else if (row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) % 7 == 0 && row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) != 0) {
											            	return 'UnderLineLineRightCustomColorStyle';
											          	}else{
											          		return 'LineRightCellCustomColorStyle';
											          	}
											        }
												});
			}else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
		            										if (row.entity.age == "累计") {
		            											return 'LineRightCellStyle';
		            										}else if (row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) % 7 == 0 && row.entity.age.substr(1).substring(0,row.entity.age.substr(1).length-1) != 0) {
												            	return 'UnderLineLineRightStyle';
												          	}else{
												          		return 'LineRightCellStyle';
												          	}
												        }

		        });
		    };
		  }
		$scope.gridOptions.data = showTableData.TableData;
	}



	$scope.judgeHouse = function(){
    	$scope.inquire();

    	console.log($scope.weeklyData.selectHouse);


    }
	
	$scope.inquire = function(){

		var TempObj = {};
		if (Object.prototype.toString.call($scope.weeklyData.selectHouse) === "[object String]") {
			TempObj = JSON.parse($scope.weeklyData.selectHouse);
			$scope.weeklyData.selectHouse = TempObj;
		}
		var params = {
			"FarmBreedId"  :  $scope.weeklyData.FarmBreedId,
			"HouseId"      :  $scope.weeklyData.selectHouse.HouseId
		};
		console.log("----------------params:----------------");
		console.log(params);
		console.log("--------------------------------");

		Sparraw.ajaxPost('report/queryWeekReport.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.weeklyData.TableData = data.ResponseDetail.reportInfo;
				$scope.getTable();
			}else{
				$scope.weeklyData.TableData = [];
				$scope.getTable();
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		$scope.getTable();

	}
	
	setLandscape(true,true);//横屏
	$scope.gridOptions = {};
	setTimeout(function() {
		Sparraw.intoMyController($scope, $state);
		$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
		$scope.setData();
	}, 500);
	

})

// 入雏管理
.controller("docPlaceCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//默认设定默认栋舍
	console.log(persistentData.dataEntryReceiveHouse);
	$scope.takeHouse = persistentData.dataEntryReceiveHouse;
	$scope.placeDatas = {
            'selectHouse':  $scope.takeHouse.HouseId                     ,  
            'selectHouseObj': {}                                         ,//包含栋舍状态
            'placeNum'   :  ''                                           , 
            'placeDate'  :  ''
  		};



  		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
  			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseId === $scope.takeHouse.HouseId) {
  				$scope.placeDatas.selectHouseObj = $scope.sparraw_user_temp.userinfo.houses[i];
  			};
  		};

  		//console.log($scope.sparraw_user_temp.userinfo.houses);
	//查询入雏信息
    $scope.judgeHouse = function(){
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "00") {
    		Sparraw.myNotice("该栋舍尚未入雏，您可以在此添加信息。");
    		$scope.placeDatas.placeNum   = ""  ,
			$scope.placeDatas.placeDate  = ""
    	}

    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
    		var params = {
				"HouseBreedId"  :  $scope.takeHouse.HouseBreedBatchId  ,
				"HouseId"       :  $scope.takeHouse.HouseId
			};

			Sparraw.ajaxPost('dataInput/queryDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					$scope.placeDatas.placeNum   = data.ResponseDetail.PlaceNum  ,
					$scope.placeDatas.placeDate  = data.ResponseDetail.PlaceDate
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
		
    		});
    	}
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "02") {
    		Sparraw.myNotice("该批次已出栏，如需添加入雏信息，请先结算批次");
    		$scope.placeDatas.placeNum   = ""  ,
			$scope.placeDatas.placeDate  = ""
    	}
    	
    }
	$scope.judgeHouse();

	//创建预计出栏时间
	var OverDate;
	//日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();

	$scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '关闭', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2014, 5, 1), //Optional
            to: new Date(2016, 7, 1), //Optional
            callback: function (val) { //Optional
            	datePickerCallbackPopup(val);
            }
    	};
	var datePickerCallbackPopup = function (val) {
            if (typeof(val) === 'undefined') {
                console.log('未选择日期');
            } else {
                $scope.datepickerObjectPopup.inputDate = val;
                $scope.datePopup = val;
                console.log('选择的日期是 : ', val)

                //转为字符串并且删除“日”字符串，修改“年”“月”为“-”
                var selectDate = val.toLocaleDateString().replace(/(日)/g,"");
                var showSelectDate = selectDate.replace(/\/|(年)|(月)/g,"-");
                $scope.placeDatas.placeDate = showSelectDate.substring(0,10);
            }
    };


    if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
		//app_alert("已入雏，日期无法修改");
		$scope.datepickerObjectPopup=null;
	}else{

	};

	$scope.judgeCanEditor = function(){
		if ($scope.datepickerObjectPopup === null) {
			return "{'color':'rgba(186, 186, 186, 1)'}";
		};
	};

    /*$scope.testFun = function(){
    	if (true) {
    			//创建预计出栏时间
				var OverDate;
				//日期选择器
				$scope.dateModel = new Date("08-14-2015");
			    $scope.datePopup = new Date("08-14-2015");
			    var disabledDates = [
			        new Date(1437719836326),
			        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
			        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
			        new Date("08-14-2015"), //Short format
			        new Date(1439676000000) //UNIX format
			    ];
			    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
			    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
			    $scope.datepickerObject = {};
			    $scope.datepickerObject.inputDate = new Date();

				$scope.datepickerObjectPopup = {
			            titleLabel: '选择日期', //Optional
			            todayLabel: '今天', //Optional
			            closeLabel: '关闭', //Optional
			            setLabel: '选择', //Optional
			            errorMsgLabel : '请选择时间.', //Optional
			            setButtonType : 'button-assertive', //Optional
			            modalHeaderColor:'bar-positive', //Optional
			            modalFooterColor:'bar-positive', //Optional
			            templateType:'popup', //Optional
			            inputDate: $scope.datepickerObject.inputDate, //Optional
			            //mondayFirst: true, //Optional
			            sundayFirst: true, //Optional
			            disabledDates:disabledDates, //Optional
			            monthList:monthList, //Optional
			            weekDaysList: weekDaysList,
			            from: new Date(2014, 5, 1), //Optional
			            to: new Date(2016, 7, 1), //Optional
			            callback: function (val) { //Optional
			            	datePickerCallbackPopup(val);
			            }
			    	};
			    	var datePickerCallbackPopup = function (val) {
				            if (typeof(val) === 'undefined') {
				                console.log('未选择日期');
				            } else {
				                $scope.datepickerObjectPopup.inputDate = val;
				                $scope.datePopup = val;
				                console.log('选择的日期是 : ', val)

				                //转为字符串并且删除“日”字符串，修改“年”“月”为“-”
				                var selectDate = val.toLocaleDateString().replace(/(日)/g,"");
				                var showSelectDate = selectDate.replace(/\/|(年)|(月)/g,"-");
				                $scope.placeDatas.placeDate = showSelectDate.substring(0,10);
				            }
				    };
	    	}else{
	    		$scope.datepickerObjectPopup = {};
	    		console.log("0-0--0-0-0-0-0-0-0-");
	    	}
	}*/


	

    
	$scope.saveDocPlace = function(){
    	if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "00") {
			//新增
			var params = {
				"HouseId"    :  $scope.takeHouse.HouseId               ,
				"PlaceNum"   :  $scope.placeDatas.placeNum             ,
				"PlaceDate"  :  $scope.placeDatas.placeDate
			};
			Sparraw.ajaxPost('dataInput/saveDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					persistentData.dataEntryReceiveHouse.HouseBreedStatus = "01";
					//重新获取服务器最新数据
					app_alert("入雏成功!");
    				Sparraw.getLatestData($state,"dailyReport");
				}else{
					app_alert(data.ResponseDetail.Exception);
				};
			});
		}

		if ($scope.placeDatas.selectHouseObj.HouseBreedStatus === "01") {
			var params = {
				"HouseBreedId"  :  $scope.takeHouse.HouseBreedBatchId  ,
				"HouseId"       :  $scope.takeHouse.HouseId            ,
				"PlaceNum"      :  $scope.placeDatas.placeNum                                   ,
				"PlaceDate"     :  $scope.placeDatas.placeDate
			};
			Sparraw.ajaxPost('dataInput/ModifyDOC.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					Sparraw.myNotice("修改成功!");
					$state.go("dailyReport");
				}else{
					app_alert(data.ResponseDetail.Exception);
				};
			});
		};
	}




    $scope.save = function(){
		app_confirm('请确认入雏日期:' + $scope.placeDatas.placeDate +',入雏之后,将无法更改入雏日期。','提示',null,function(buttonIndex){
	                   if(buttonIndex == 2){
	                        $scope.saveDocPlace();
	                   }
	              }); 
    }











		
})



// 环控管理表
.controller("envMonitoringTableCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
})

// 环境监测
.controller("envMonitoringCtrl",function($scope, $state, $http, $ionicLoading,$ionicScrollDelegate, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();

			setTimeout(
				function (){
					$scope.setData();
					$scope.queryDatatt();
				}
			,1000);
		}
	,1000);

	



    $scope.setData = function(){
    	//数据源
	    $scope.envMonitoringData = {
	    	"operation"   :  "realTimeData",
	    	"celsius"     :  "℃"           ,
	    	"MonitorData" :  [{
	    		"houseName"         :  "-"  , //-栋舍名称
				"dayAge"            :  "-"  , //-日龄
				"out_temp"          :  "-"  , //-室外温度
				"tempLeft1"         :  "-"  , //-前区温度
				"tempLeft2"         :  "-"  ,
				"tempMiddle1"       :  "-"  , //-中区温度
				"tempMiddle2"       :  "-"  ,
				"tempRight1"        :  "-"  , //-后区温度
				"tempRight2"	    :  "-"  ,
				"house_id"           :  ""   ,

				"tar_temp"          :  "-"  , //-目标温度
				"avg_temp"          :  "-"  , //-平均温度
				"H_temp"            :  "-"  , //-高报温度
				"L_temp"            :  "-"  , //-低报温度
				"point_temp"        :  "-"  , //-点温差
				"humi"              :  "-"  , //-湿度
				"data_time"         :  "-"  , //数据时间
				"temp_in1_alarm"    :  "N"  , //-前区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in2_alarm"    :  "N"  , //-中区温度：高报警（H）,正常（N）,低报警（L）
				"temp_in3_alarm"    :  "N"  , //-后区温度：高报警（H）,正常（N）,低报警（L）
				"temp_avg_alarm"    :  "N"  , //-平均温度：高报警（H）,正常（N）,低报警（L)
				"point_temp_alarm"  :  "0"  , //-1-点温差报警；0-正常
				"power_status"      :  "-"  ,  
				"power_status_alarm" :  "0"    //1-断电报警；0-不报
	    	}]
	    }
	    $scope.transferHouseId = "";//跨页面传值使用
	    $scope.area = {
	    	"area1"  :  "Outdoor"  ,  //室外
	    	"area2"  :  "Front"    ,  //前区
	    	"area3"  :  "Middle"   ,  //中区
	    	"area4"  :  "Behind"   ,  //后区
	    	"area5"  :  "all"         //所有区域
	    }//判断从哪里跳转至报表界面
    }

    


    //判断是否是从环控监视进入报 警 设 置
    $scope.goAlarmSettings = function(){
		$state.go("alarmSettings");
    }


	$scope.queryDatatt = function(){
		var params = {
			"operation"   :  "realTimeData"
		};
		Sparraw.ajaxPost('envCtrl/monitoring.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.envMonitoringData.MonitorData = data.ResponseDetail.MonitorData;
				if ($scope.envMonitoringData.MonitorData == "" || !data.ResponseDetail.MonitorData) {
					$scope.dataNullText = true;
				}else{
					$scope.dataNullText = false;
					document.getElementById('dataNullTextId').innerHTML = "请至少绑定一个农汇通设备。";
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			$scope.$broadcast('scroll.refreshComplete');
		});
	};
	
	

	$scope.refreshFun = function(){
		$scope.queryDatatt();
	}

	$scope.goHome = function(){
		//Sparraw.getInfoStatus($ionicPopup,$state,"envMonitoringTable");
		$state.go("home");
	}

	


	//控制区域颜色
	$scope.judgeEnter = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{background:'red'}";
		}else{
			return "{background:'#a5d5a9'}";
		};
	}

	//字体颜色
	$scope.labelColor = function(item){
		if (item.temp_in1_alarm     !=  "N" || 
			item.temp_in2_alarm     !=  "N" || 
			item.temp_in3_alarm     !=  "N" ||
			item.temp_avg_alarm     !=  "N" || 
			item.point_temp_alarm   !=  0   || 
			item.power_status_alarm !=  0   ) {
			return "{color:'#fff'}";
		}else{
			return "{color:'#5c5c5c'}";
		};
	}




    //判断温度状态
    //前区温度
    $scope.frontStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //中区温度
    $scope.middleStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //后区温度
    $scope.afterStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#fff'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //平均温度
    $scope.retStyle = function(obj){
    	switch(obj){
				case "H":
				  	return "{background:'red'}";
				  break;
				case "N":
				  	return "{background:'#f4f4f4'}";
				  break;
				default:
					return "{background:'#00BFFF'}";
				};
    }
    //判断温差点
    $scope.temperatureStyle = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }
    //判断断电情况
    $scope.judgeCurrent = function(obj){
    	if (obj == "0") {
    		return "{background:'#f4f4f4'}";
    	}else{
    		return "{background:'red'}";
    	};
    }

})

//  温度曲线图
.controller("tempChartCtrl",function($scope, $state, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(true,true);
	$scope.goEnvMonitoring = function(){
		/*if ($stateParams.area == "") {//判断哪个入口进入，回到哪个页面
			$state.go("dataAnalyseTable");
		}else{
			$state.go("envMonitoring");
		};*/
		$state.go("envMonitoring");

	}

	
	console.log(screen.width)
	document.getElementById('tempChart_DIV').style.height = (screen.width -75) + 'px';
			

	$scope.tempChartData = {
		
		"batchTable"        :  ""                           ,//批次表
		"selectedHouseId"   :  JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId),//选中的栋舍id
		"selectedBatch"     :  ""                           ,//选中批次的key
		"selectedBatchVal"  :  ""                           ,//选中批次的val
		"NavTitle"          :  ""                           ,

		"chartType"         :  ["01","02","03"]             ,
		"chartTitle"        :  ""                           ,//数据展示类型"01"-日龄"02"-小时"03"-分钟
	    "ReqFlag"           :  "N"                          ,//varchar型,"Y"-指定参数；"N"-没有指定参数
	    "chartDataTime"     :  null                         ,//表图数据时间
	    "chartDataisNull"   :  0                            ,//判断表图是否已获取数据0-未获取，1-已获取


	    "xData"             :  []                           ,
	    "yData"             :  []                           ,
	    "yName"             :  []                           ,
	    "yColor"            :  []                           ,
	    "hiddenPara"        :  []                           , //隐藏哪些数据
	    /*"hiddenLine"        :  [["前区温度1",false,"前区温度2",false,"中区温度",false,"后区温度1",false,"后区温度2",false],//室外数据
								["室外温度",false,"中区温度",false,"后区温度1",false,"后区温度2",false]                  ,//前区数据
								["前区温度1",false,"前区温度2",false,"室外温度",false,"后区温度1",false,"后区温度2",false],//中区数据
								["室外温度",false,"前区温度1",false,"前区温度2",false,"中区温度",false]                  ,//后区数据
								["室外温度",false]                                                                    //展示除了室外温度，其他所有数据
								],*/                            
		"hiddenLine"        :  [["前一",false,"前二",false,"中区",false,"后一",false,"后二",false],//室外数据
								["室外",false,"中区",false,"后一",false,"后二",false]                  ,//前区数据
								["前一",false,"前二",false,"室外",false,"后一",false,"后二",false],//中区数据
								["室外",false,"前一",false,"前二",false,"中区",false]                  ,//后区数据
								["室外",false]                                                                    //展示除了室外，其他所有数据
								],
		"DataDate"          :  ""                           , //服务器返回表图数据的时间
		"selectedChartTime" :  ""                           , //用户选择的时间
		"selectUnit"        :  "℃"                          , //y轴的计量单位








		"selectHouse"       :  $stateParams.receiveHouseId  ,//从环控监视进来的栋舍id
		"selectHouseArea"   :  $stateParams.area            ,//从环控监视进来的栋舍区域(Outdoor室外、Front前区、Middle中区、Behind后区)
		
		"leftBtnName"       :  "日龄"                        ,//左边按钮名
		"rightBtnName"      :  "分钟"

	}


	//判断从哪个入口进入该页面的
	switch($scope.tempChartData.selectHouseArea){
					      case 'Outdoor' :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[0];//隐藏哪些数据
					      break;
					      case 'Front'   :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[1];
					      break;
					      case 'Middle'  :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[2];
					      break;
					      case 'Behind'  :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[3];
					      break;
					      default        :
					      		$scope.tempChartData.hiddenPara = $scope.tempChartData.hiddenLine[4];
					      break;
					    }
	
	
	//获取批次信息
	var params = {
		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id
	};
	Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){

		if (data.ResponseDetail.Result == "Success") {
			$scope.tempChartData.batchTable = data.ResponseDetail.FarmBreedIdArray;
			//获取key
			for(var key in $scope.tempChartData.batchTable){
			    $scope.tempChartData.selectedBatch = key;
			}
			//获取value  
			 for(var item in $scope.tempChartData.batchTable){  
		        if(item==key){  
		            $scope.tempChartData.selectedBatchVal = $scope.tempChartData.batchTable[item];
		        }  
		    }  
		    $scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
			$scope.tempChartData.chartDataTime = null;

			
			console.log("首次显示曲线图的地方");

			if ($scope.tempChartData.selectHouse != "") {
				$scope.tempChartData.selectedHouseId = $scope.tempChartData.selectHouse;
			}else{

			};

			$scope.DataShow(
						$scope.tempChartData.selectedBatch    ,
						$scope.tempChartData.selectedHouseId  ,
						$scope.tempChartData.chartTitle       ,
						$scope.tempChartData.ReqFlag          ,
						$scope.tempChartData.chartDataTime    ,
						function () {
							if ($scope.tempChartData.chartDataisNull == 0) {
								$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
							}else{

							};
						}
					);
		}else if (data.ResponseDetail.Result == "Fail") {
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});

	$scope.switchHouse = function(){
		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
		$scope.DataShow(
					$scope.tempChartData.selectedBatch    ,
					$scope.tempChartData.selectedHouseId  ,
					$scope.tempChartData.chartTitle       , //默认小时数据
					$scope.tempChartData.ReqFlag          ,
					$scope.tempChartData.chartDataTime
		);
		$scope.tempChartData.leftBtnName  = "日龄";
		$scope.tempChartData.rightBtnName = "分钟";
		document.getElementById('rightBtn').style.background = "#33CD5F";
		document.getElementById('leftBtn').style.background = "#33CD5F";
	}

	$scope.switchBatch = function(){
		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
		$scope.DataShow(
			$scope.tempChartData.selectedBatch    ,
			$scope.tempChartData.selectedHouseId  ,
			$scope.tempChartData.chartTitle       , //默认小时数据
			$scope.tempChartData.ReqFlag          ,
			$scope.tempChartData.chartDataTime    
			);
		$scope.tempChartData.leftBtnName  = "日龄";
		$scope.tempChartData.rightBtnName = "分钟";
		document.getElementById('rightBtn').style.background = "#33CD5F";
		document.getElementById('leftBtn').style.background = "#33CD5F";
	}

	//农场批次id//栋舍id//曲线图数据类型//是否选中参数//选择的参数时间//判断数据是否获取成功
	$scope.DataShow = function(BatchKey,HouseId,DataTypeNum,ReqFlag,DataRange,judgeDataFun){

		var oDate  = new Date();
		var oMonth = ("0" + (oDate.getMonth() + 1)).slice(-2);
		var oDay   = ("0" + (oDate.getDate() + 1)).slice(-2);
		var NowDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;
		if (DataRange >= NowDate) {//判断用户所选时间是否大于当日，如果大于当日直接取最新数据
			DataRange = null;
		}else{

		};


		if (HouseId[0] == "{") {
			HouseId = JSON.parse(HouseId).id;
		}else{

		}

		//获取图表数据
		var params = {
			"FarmBreedId"  :  BatchKey     ,  //农场批次id
			"HouseId"      :  HouseId      ,  //栋舍id
			"DataType"     :  DataTypeNum  ,  //曲线图数据类型 01-日龄曲线；02-小时曲线；03-分钟曲线。
			"ReqFlag"      :  ReqFlag      ,  //varchar型,"Y"-指定参数；"N"-没有指定参数
			"DataRange"    :  DataRange       //选择的时间
		};

		Sparraw.ajaxPost('rep/TempCurve/TempCurveReq.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					for (var i = 0; i < data.ResponseDetail.TempDatas.length; i++) {
						$scope.tempChartData.yName[i] =  data.ResponseDetail.TempDatas[i].TempAreaName;
						$scope.tempChartData.yData[i] =  data.ResponseDetail.TempDatas[i].TempCurve;
					}



					
					//目标绿色 湿度淡蓝色 高报红色
					$scope.tempChartData.xData       =  data.ResponseDetail.xAxis;
					$scope.tempChartData.DataDate    =  data.ResponseDetail.DataDate;
					$scope.tempChartData.data_age    =  data.ResponseDetail.data_age;
					//$scope.tempChartData.yColor      =  ['#FF00FF','#800080','#556B2F','#000080','#1E90FF','#FFD700',  '#DC143C','#00FFFF','#00EE76','#D1EEEE'];	
					$scope.tempChartData.yColor      =  ['#EE82EE','#FFA500','#CC9933','#CCCC00','#993333','#663366',  '#DC143C','#00FFFF','#00EE76','#D1EEEE'];	
														//前1        、前2、   中区、       后1、    后2、      室外、         高报       低报      目标        湿度
					

					if (data.ResponseDetail.DataDate != "null") {
						if (data.ResponseDetail.data_age != "null") {
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substr(4);
							data.ResponseDetail.data_age = data.ResponseDetail.data_age.substring(0,data.ResponseDetail.data_age.length-1);
							$scope.tempChartData.NavTitle =  '(日龄:' + data.ResponseDetail.data_age + ')';
						}else{
							$scope.tempChartData.NavTitle = "";
						};
					}else{
						$scope.tempChartData.NavTitle = "";
					};
					$scope.tempChartData.selectUnit = "℃";


					var allData = [];

					for (var i = 0; i < $scope.tempChartData.yData.length-1; i++) {
						for (var j = 0; j < $scope.tempChartData.yData[i].length; j++) {
							allData.push($scope.tempChartData.yData[i][j]);
						}
					}
					//判断是否要设置最大最小值
					var yLeftRange = [];
					if (Math.max.apply(null, allData) > 40 || Math.min.apply(null, allData) < 15) {
						yLeftRange = [15,40];
					}else{
						yLeftRange = undefined;
					}
					var yRightRange = undefined;

					var yRightShow = true;
					//判断是否要右边的数据
					if (DataTypeNum == "03") {
						yRightShow = true;
					}else{
						yRightShow = false;
						//删除湿度数据
						if ($scope.tempChartData.yData.length == 10) {
							$scope.tempChartData.yData.pop();
							$scope.tempChartData.yName.pop();
						}
					}
					
					getLineChart($scope.tempChartData.xData,
										 $scope.tempChartData.yData,
										 $scope.tempChartData.yName,
										 $scope.tempChartData.yColor,
										 $scope.tempChartData.hiddenPara,
										 $scope.tempChartData.selectUnit,
										 '',
										 function (params) {
											$scope.tempChartData.judgeIsEmpty = params[0].data
											for (var i = 0; i < params.length; i++) {
												if (params[i].data == undefined) {
													params[i].data = "-";
												}else{

												};
											};
					                        $scope.tempChartData.selectedChartTime = params[0].name;//将x轴的字赋到判断向上向下的选择中
					                    	var res = '时间' + ' : ' + params[0].name;//x轴的字
											var tempArray = [];
											
					                    	for (var i = 0; i < params.length; i++) {
					                            tempArray[i] = params[i];
					                    	};
					                        



					                    	//得到点温差
					                        var tempDiffArr = [];
											var tempDiffMax = "";
											var tempDiffMin = "";
											var tempDiff = "";
					                    	//判断从哪个入口进入该页面的
					                    	console.log($scope.tempChartData.selectHouseArea);
											switch($scope.tempChartData.selectHouseArea){
															      case 'Outdoor' ://室外
															      		
															      break;
															      case 'Front'   ://前区
															      		//得到点温差
																		for (var i = 0; i < 2; i++) {
																			tempDiffArr.push(tempArray[i].data);
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															      case 'Middle'  ://中区
															      		
															      break;
															      case 'Behind'  ://后区
															      		//得到点温差
																		for (var i = 0; i < 5; i++) {
																			if (i > 2 && i < 5 ) {
																				tempDiffArr.push(tempArray[i].data);
																			}
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															      default        :
															      		//得到点温差
																		for (var i = 0; i < 5; i++) {
																			tempDiffArr.push(tempArray[i].data);
																		}
																		tempDiffMax = Math.max.apply(null, tempDiffArr);
																		tempDiffMin = Math.min.apply(null, tempDiffArr);
																		tempDiff = (tempDiffMax - tempDiffMin).toFixed(1);
																		//将新添加的温差点放在第一位，因为放在最后一位会出现选择日期错误的问题。
												                        tempArray.unshift({
												                        	"seriesName":"点温差",
												                        	"data":tempDiff
												                        });
															      break;
															    }



											
											
											console.log(tempArray);

					                        for (var i = 0; i < tempArray.length; i++) {
					                        	if (i % 2 == 0) {
					                        		$scope.tempChartData.lineDataTime = tempArray[i].name;
					                        		res += '<br/>' + tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
					                        	}else{
					                        		$scope.tempChartData.lineDataTime = tempArray[i].name;
					                        		res +=  tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
					                        	}
					                        };

					                        

					                        return res;					                        
					                    },yLeftRange,yRightRange,yRightShow);

					
					//每次刷新图数据后自动清空是否点击
					$scope.tempChartData.ReqFlag = "N";
					$scope.tempChartData.chartDataTime = null;
					$scope.tempChartData.selectedChartTime = "";
					//成功
					$scope.tempChartData.chartDataisNull = 1;
					judgeDataFun&&judgeDataFun();
				}else if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				}else{

				}
					
		},function(data){
			//失败
						$scope.tempChartData.chartDataisNull = 0;
						judgeDataFun&&judgeDataFun();
		});
	}


	//向上
	$scope.upMethods = function(){
		$scope.tempChartData.ReqFlag = "Y";
		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		Sparraw.myNotice("横轴已到最大粒度。");
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];//从小时切换成日龄数据
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从分钟切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
					      		$scope.tempChartData.chartDataTime = $scope.tempChartData.DataDate;
					      		console.log($scope.tempChartData.DataDate);
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];
												}else{

												};
											});
					      break;
					    }


		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		//最大值
					      		$scope.tempChartData.leftBtnName  = "";
								$scope.tempChartData.rightBtnName = "小时";
								document.getElementById('leftBtn').style.background = "#ECECEC";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					      case '02'   :
					      		//从小时切换成日龄数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('leftBtn').style.background = "#33CD5F";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					      default     :
					      		//从分钟切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('leftBtn').style.background = "#33CD5F";
								document.getElementById('rightBtn').style.background = "#33CD5F";
					      break;
					    }

	}
	

	// "ReqFlag"           :  "N"                          ,//varchar型,"Y"-指定参数；"N"-没有指定参数
	// "chartDataisNull"   :  0                            ,//判断表图是否已获取数据0-未获取，1-已获取
	// "chartTitle"        :  ["01","02","03"]             ,//数据展示类型"01"-日龄"02"-小时"03"-分钟

	//向下
	$scope.downMethods = function(){

		if ($scope.tempChartData.selectedChartTime == "") {
			$scope.tempChartData.ReqFlag = "N";
			switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从日龄切换成小时数据
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];
												}else{

												};
					                    	});
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];//从小时切换成分钟数据
					      		$scope.tempChartData.chartDataTime = $scope.tempChartData.DataDate;
								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		Sparraw.myNotice("横轴已到最小粒度。");
					      break;
					    }
		}else{

			$scope.tempChartData.ReqFlag = "Y";
			switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];//从日龄切换成小时数据
								var getYear2 = $scope.tempChartData.selectedBatchVal.substr(0, 2);
								getYear2 = '20' + getYear2;
								var getMonthAndDay = $scope.tempChartData.lineDataTime.substr(0, 5);
								$scope.tempChartData.chartDataTime = getYear2 + '-' + getMonthAndDay;
								$scope.DataShow(
											$scope.tempChartData.selectedBatch      ,
											$scope.tempChartData.selectedHouseId    ,
											$scope.tempChartData.chartTitle         , 
											$scope.tempChartData.ReqFlag            ,
											$scope.tempChartData.chartDataTime      ,

											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[0];
												}else{

												};
					                    	});
					      break;
					      case '02'   :
					      		$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[2];//从小时切换成分钟数据


					      		var selectedTime = $scope.tempChartData.DataDate;
								selectedTime += " ";
								selectedTime += $scope.tempChartData.selectedChartTime;
								$scope.tempChartData.chartDataTime = selectedTime;

								$scope.DataShow(
											$scope.tempChartData.selectedBatch    ,
											$scope.tempChartData.selectedHouseId  ,
											$scope.tempChartData.chartTitle       , 
											$scope.tempChartData.ReqFlag          ,
											$scope.tempChartData.chartDataTime    ,
											function () {
												console.log($scope.tempChartData.chartDataisNull);
												if ($scope.tempChartData.chartDataisNull == 0) {
													$scope.tempChartData.chartTitle = $scope.tempChartData.chartType[1];
												}else{

												};
					                    	});
								
					      break;
					      default     :
					      		Sparraw.myNotice("横轴已到最小粒度。");
					      break;
					    }
		};




		switch($scope.tempChartData.chartTitle){
					      case '01' :
					      		//从日龄切换成小时数据
					      		$scope.tempChartData.leftBtnName  = "小时";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('rightBtn').style.background = "#33CD5F";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					      case '02'   :
								//从小时切换成分钟数据
					      		$scope.tempChartData.leftBtnName  = "日龄";
								$scope.tempChartData.rightBtnName = "分钟";
								document.getElementById('rightBtn').style.background = "#33CD5F";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					      default     :
					      		//选择了最小值
					      		$scope.tempChartData.leftBtnName  = "小时";
								$scope.tempChartData.rightBtnName = "";
								document.getElementById('rightBtn').style.background = "#ECECEC";
								document.getElementById('leftBtn').style.background = "#33CD5F";
					      break;
					    }




	}


})
//  报警延迟处理
.controller("alarmLogDelayCtrl",function($scope, $state, $http,  crisisServiceFactory,  $ionicPopup, $stateParams, $ionicActionSheet, AppData) {
	
	setPortrait(true,true);//竖屏

	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    //数据源
    $scope.logDelayData = {
	    'alarmMessage':[],
	    'showStatus':""
    };
	//报警查询
	$scope.alarmQuery = function(){
		var params = {
			"FarmId":$scope.sparraw_user_temp.farminfo.id
		};
		Sparraw.ajaxPost('envCtrl/AlarmDealQuery_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				console.log(data.ResponseDetail.alarmMessage);
				$scope.logDelayData.alarmMessage = data.ResponseDetail.alarmMessage;

				for (var i = 0; i < $scope.logDelayData.alarmMessage.length; i++) {
					for (var j = 0; j < $scope.logDelayData.alarmMessage[i].CurAlarmData.length; j++) {
						if ($scope.logDelayData.alarmMessage[i].CurAlarmData[j].process_status == "01") {
							$scope.logDelayData.showStatus = "未处理";
							$scope.logDelayData.alarmMessage[i].CurAlarmData[j].showStatus = $scope.logDelayData.showStatus;
						}else{
							$scope.logDelayData.showStatus = "已处理";
							console.log($scope.logDelayData.alarmMessage[i].CurAlarmData[j]);
							$scope.logDelayData.alarmMessage[i].CurAlarmData[j].showStatus = $scope.logDelayData.showStatus;
						}
					}
					
				}
				$scope.DealFunc();
			}else{
				Sparraw.myNotice("暂无报警信息");
			};
		});
	}
	$scope.alarmQuery();
	//延迟函数
	$scope.DelayFunc = function(deferTime){
		var CurAlarmData = [];
		for (var i = 0; i < $scope.logDelayData.alarmMessage.length; i++) {
			for (var j = 0; j < $scope.logDelayData.alarmMessage[i].CurAlarmData.length; j++) {
				if ($scope.logDelayData.alarmMessage[i].CurAlarmData[j].process_status == '01') {
					CurAlarmData.push({
						"alarmID"     :  $scope.logDelayData.alarmMessage[i].CurAlarmData[j].alarmID    ,
		    			"alarmCode"   :  $scope.logDelayData.alarmMessage[i].CurAlarmData[j].alarmCode  ,
		    			"houseId"     :  $scope.logDelayData.alarmMessage[i].HouseId                    ,
		    			"delayTime"   :  deferTime
		    		});
				};
			}
		}
		if (CurAlarmData.length == 0 || !CurAlarmData) {
			return Sparraw.myNotice("暂无需要处理的报警。");
		};
		var params = {
			"CurAlarmData":CurAlarmData,
		};
		Sparraw.ajaxPost('envCtrl/AlarmDealDelay_v2.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				CurAlarmData = [];
				Sparraw.myNotice("处理成功。");
			}else{

			};
			$scope.alarmQuery();//再查询新信息
		});
	}
	//处理点击事件
	$scope.DealFunc = function(){
		if ($scope.sparraw_user_temp.Authority.MonitorDeal === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		var hideSheet = $ionicActionSheet.show({
                      buttons: [
                        { text: '延迟10分钟' },
                        { text: '延迟20分钟' },
                        { text: '延迟30分钟' }
                      ],
                      cancelText: '取消',
                      cancel: function() {
                      },
                      buttonClicked: function(index) {
                      	switch(index){
					      case 0 :
					      	$scope.DelayFunc(10);		//先处理
					      	break;
					      case 1 :
					      	$scope.DelayFunc(20);
					      	break;
					      case 2 :
					      	$scope.DelayFunc(30);
					      	break;
					      default  :
					      	break;
					    }
                        return true;
                      }
                  });
	}
})


// 环控报警统计
.controller("alarmStatisticsCtrl",function($scope, $state, $http, AppData) {

	setPortrait(true,true);//竖屏

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//数据源

	$scope.transferHouseId  =  ""  ;
	$scope.buildingDayAge   =  ""  ;
	$scope.alarmStatisticsData = {
		"houseData":[{
                "houseName"         :  ""                                             ,  //栋舍名称
                "dayAge"            :  ""                                             ,  //日龄
                "temp_avg_alarm"    :  {'temp_avg_alarm_H':"",'temp_avg_alarm_l':""}  ,  //平均
                "temp_in1_alarm"    :  {'temp_in1_alarm_H':"",'temp_in1_alarm_L':""}  ,  //前区
                "temp_in2_alarm"    :  {'temp_in2_alarm_H':"",'temp_in2_alarm_l':""}  ,  //中区
                "temp_in3_alarm"    :  {'temp_in3_alarm_H':"",'temp_in3_alarm_L':""}  ,  //后区
                "point_temp_alarm"  :  ""                                             ,  //点温差
                "power_status"      :  ""                                             ,  //断电报警（0）
          }]
	}





	
	//监控报警
	var params = {
		"FarmId"  :  $scope.sparraw_user_temp.farminfo.id
	};
	Sparraw.ajaxPost('sys/alarm/queryAlarmData.action', params, function(data){
		console.log(data);
		if (data.ResponseDetail.Result == "Success") {
			
			$scope.alarmStatisticsData.houseData = data.ResponseDetail.AlarmData;

		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});

	$scope.goAlarmLog = function(item){		
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseName==item.houseName){
				$scope.transferHouseId = $scope.sparraw_user_temp.userinfo.houses[i].HouseId;
				$scope.buildingDayAge  = $scope.alarmStatisticsData.houseData[i].dayAge;
			}
		}

	}



})

// 报警日志
.controller("alarmLogCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $stateParams, AppData) {

	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	$scope.setScreenStateFun = function(){
		setLandscape(true,true);//横屏
		var MAXHEIGHT = document.documentElement.clientWidth;

		var MINHEIGHT = MAXHEIGHT - 190;
		document.getElementById('tableDiv').style.minHeight = MINHEIGHT + 'px';
		document.getElementById('tableDiv').minHeight = MINHEIGHT + 'px';

		var MAX_HEIGHT = MAXHEIGHT - 240;
		document.getElementById('tableDiv').style.maxheight = MAX_HEIGHT + 'px';
		document.getElementById('tableDiv').maxheight = MAX_HEIGHT + 'px';
	}

	$scope.setData = function(){
		//数据源
	    $scope.alarmLogData = {	
	    	//"alarmCode"       :  ""  ,  //筛选栏的报警类型
	    	"AlarmCategory"       :  ""  ,  //筛选栏的报警类型
	        "AgeBegin"            :  ""  ,  //开始日龄
	        "AgeEnd"              :  ""  ,	//结束日龄
	        /*查询条件：
			"AlarmCategory":"All"-全部
					        "frontTemp"-前区温度报警
					        "middleTemp"-中区温度报警
					        "backTemp"-后区温度报警
					        "pointTemp"-点温差报警
					        "avgTemp"-平均温度报警
					        "powerStatus"-断电报警
			"AgeBegin"和"AgeEnd" 查询起始和截止日龄*/
			//一条日志的信息
	        "AlarmLog":[{
		            "aDayAge"         :  ""  ,  //日龄
		            "aDate"           :  ""  ,	 
		            "aTime"           :  ""  ,  //时间
		            "alarmID"         :  ""  ,	 
		            "alarmName"       :  ""  ,	 //显示的报警类型
		            "realValue"       :  ""  ,	 //实际温度
		            "targetValue"     :  ""  ,  //目标温度
		            "process_status"  :  ""  ,  //日志状态 01-待处理；02-处理中；03-已结束
		            "values"          :  ""  ,  //实际/目标
		            "process_status"  :  ""  ,  //响应状态
		            "deal_person"     :  ""  ,  //响应人员
		            "deal_time"       :  ""  ,  //响应时间
		            "is_normal"       :  ""  ,  //是否消除
		            "last_time"       :  ""     //持续时间
		            }]

	    };
	}



    

	$scope.houseName      = "" ;
	$scope.buildingDayAge = "" ;
		for(i in $scope.sparraw_user_temp.userinfo.houses){
			if($scope.sparraw_user_temp.userinfo.houses[i].HouseId==$stateParams.receiveHouseId){
				$scope.houseName = $scope.sparraw_user_temp.userinfo.houses[i].HouseName;
			}
		}   
    $scope.buildingDayAge = $stateParams.buildingDayAge
	
	
	

		 		
    $scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};

	$scope.queryLog = function(){
		/*校验信息*/
	   	var required = [$scope.alarmLogData.AlarmCategory,$scope.alarmLogData.AgeBegin,$scope.alarmLogData.AgeEnd];
	   	if (parseInt(required[1]) > parseInt(required[2])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };
	    for(i in required){if(required[i]==''){return Sparraw.myNotice('尚有内容未填写...');}}
	    
		var params = {
			"HouseId"        :  parseInt($stateParams.receiveHouseId)                    ,
			"AlarmCategory"  :  $scope.alarmLogData.AlarmCategory                        ,
			"AgeBegin"       :  $scope.alarmLogData.AgeBegin                             ,
			"AgeEnd"         :  $scope.alarmLogData.AgeEnd
		};

		Sparraw.ajaxPost('sys/alarm/queryAlarmLog.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice("暂无报警信息。");
					$scope.alarmLogData.AlarmLog = [];
				}else{
					$scope.alarmLogData.AlarmLog = data.ResponseDetail.AlarmData;
				};

		});
		//$ionicSideMenuDelegate.toggleRight();
	}


	


	$scope.cancel = function(){
		console.log("取消啦");
		$ionicSideMenuDelegate.toggleRight();
	}



	$scope.setScreenStateFun();
    setTimeout(function() {
    	$scope.setData();
    	//设置默认值自动查询   
		$scope.alarmLogData.AlarmCategory  =  "All"  ;
	    $scope.alarmLogData.AgeBegin       =  "0"    ;
	    $scope.alarmLogData.AgeEnd         =  "50"   ;
	    setTimeout(function() {
	    	$scope.queryLog();
	    }, 500);
    }, 1500);
    


	
    

})

// 报警设置
.controller("alarmSettingsCtrl",function($scope, $state, $http, $ionicPopup, AppData,$ionicModal) {

	setPortrait(true,true);//进入时竖屏

	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    
	
	$scope.temperatureOffsetChange = function() {
		console.log($scope.tempVar.tempCpsation);
		if ($scope.tempVar.tempCpsation) {
			$scope.showTempCpsationVal = true;
			$scope.tempVar.AlarmSetting.tempCpsation = 1;
		}else {
			$scope.showTempCpsationVal = false;
			$scope.tempVar.AlarmSetting.tempCpsation = 0;
		};
	}
	

	$scope.testMethods = function(){
		
		houseId = $scope.tempVar.AlarmSetting.HouseId;
	
		$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = false ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = true ;
    	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = true ;

        $scope.tempVar.AlarmSetting.Delay            =  0  ;  //报 警 延 迟
        $scope.tempVar.AlarmSetting.tempCpsation     =  '0'  ;  //温度补偿
        $scope.tempVar.AlarmSetting.tempCpsationVal  =  '0'  ;  //补偿数值
        $scope.tempVar.AlarmSetting.alarmProbe       =  ''  ;  //报警探头
        $scope.tempVar.AlarmSetting.pointAlarm       =  5  ;  //点温差报警
        $scope.tempVar.AlarmSetting.tempSettings     =  [
                {"dayAge":1,   "tarTemp":"",  "minTemp":"",  "maxTemp":""  },  //dayAge:int型,tarTemp/minTemp/maxTemp：number型
                {"dayAge":7,   "tarTemp":"",  "minTemp":"",  "maxTemp":""  },
                {"dayAge":14,  "tarTemp":"",  "minTemp":"",  "maxTemp":""  },
                {"dayAge":21,  "tarTemp":"",  "minTemp":"",  "maxTemp":""  },
                {"dayAge":28,  "tarTemp":"",  "minTemp":"",  "maxTemp":""  },
                {"dayAge":35,  "tarTemp":"",  "minTemp":"",  "maxTemp":""  },
                {"dayAge":42,  "tarTemp":"",  "minTemp":"",  "maxTemp":""  }
	            ];

		//查询是否有数据记录
		var params = {
	    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id                     ,
	    		"HouseId"       :  $scope.tempVar.AlarmSetting.HouseId
			};

		Sparraw.ajaxPost('sys/alarm/querySettingData.action', params, function(data){
			//判断传送是否成功
			if (data.ResponseDetail.Result == "Success") {
				//判断第一次还是已有信息
				if (data.ResponseDetail.ResultFlag == "Y") {
					$scope.tempVar.AlarmSetting.Delay            =  JSON.stringify(data.ResponseDetail.AlarmSetting.Delay)  ;
					$scope.tempVar.AlarmSetting.tempCpsation     =  data.ResponseDetail.AlarmSetting.tempCpsation           ;
					$scope.tempVar.AlarmSetting.tempCpsationVal  =  data.ResponseDetail.AlarmSetting.tempCpsationVal        ;
					$scope.tempVar.AlarmSetting.alarmProbe       =  data.ResponseDetail.AlarmSetting.alarmProbe             ;
					$scope.tempVar.AlarmSetting.pointAlarm       =  data.ResponseDetail.AlarmSetting.pointAlarm             ;
					$scope.tempVar.AlarmSetting.tempSettings     =  data.ResponseDetail.AlarmSetting.tempSettings           ;

					
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe &&data.ResponseDetail.AlarmSetting.effAlarmProbe.tempLeft1=="true")?true:false;
					
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempLeft2=="true")?true:false;

					$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempMiddle1=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempMiddle2=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempRight1=="true")?true:false;
									
					$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = 
								(data.ResponseDetail.AlarmSetting.effAlarmProbe && data.ResponseDetail.AlarmSetting.effAlarmProbe.tempRight2=="true")?true:false;																										

				
					//判断温度补偿是否打开
					console.log($scope.tempVar.AlarmSetting.tempCpsation);
					if ($scope.tempVar.AlarmSetting.tempCpsation == 1) {
						$scope.showTempCpsationVal = true;
						$scope.tempVar.tempCpsation = true;
					}else{
						$scope.showTempCpsationVal = false;
						$scope.tempVar.tempCpsation = false;
					};				

				}else{
						//判断权限
						if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {

						}else{
							return app_alert("暂无数据,请联系场长进行设置。");
						};

						$scope.tempVar.AlarmSetting.Delay = 3;//报警延迟默认参数
						$scope.tempVar.AlarmSetting.alarmProbe = 2;//报警方式默认参数
						app_confirm('该栋舍尚未设置报警信息,是否使用默认值?','提示',null,function(buttonIndex){
		                   if(buttonIndex == 2){
							    houseId = $scope.tempVar.AlarmSetting.HouseId;
								
		                        $scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle2 = false ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1 = true ;
				            	$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2 = true ;
				            	 $scope.tempVar.AlarmSetting.Delay            =  data.ResponseDetail.AlarmSetting.Delay            ;
								 $scope.tempVar.AlarmSetting.tempCpsation     =  data.ResponseDetail.AlarmSetting.tempCpsation     ;
								 $scope.tempVar.AlarmSetting.tempCpsationVal  =  data.ResponseDetail.AlarmSetting.tempCpsationVal  ;
								 $scope.tempVar.AlarmSetting.alarmProbe       =  data.ResponseDetail.AlarmSetting.alarmProbe       ;
								 $scope.tempVar.AlarmSetting.pointAlarm       =  data.ResponseDetail.AlarmSetting.pointAlarm       ;
								 $scope.tempVar.AlarmSetting.tempSettings     =  data.ResponseDetail.AlarmSetting.tempSettings     ;
								 $scope.save();
		                   }
			            }); 
				};
			}else{
				Sparraw.myNotice("查询失败");
			};
		});
	}
	//默认调用查询
	$scope.tempVar.AlarmSetting.HouseId = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0].HouseId);
	$scope.testMethods();
	
	
	//点击保存时调用
	$scope.Clicksave = function(){
		if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$scope.save();
	}


    $scope.save = function(){
		
    	for (var i = 0; i < $scope.tempVar.AlarmSetting.tempSettings.length; i++) {
    		console.log($scope.tempVar.AlarmSetting.tempSettings[i]);
    		if ($scope.tempVar.AlarmSetting.tempSettings[i].maxTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].maxTemp) {
    			$scope.tempVar.AlarmSetting.tempSettings[i].maxTemp = 0;
    		}
    		if ($scope.tempVar.AlarmSetting.tempSettings[i].tarTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].tarTemp) {
    			$scope.tempVar.AlarmSetting.tempSettings[i].tarTemp = 0;
    		}
    		if ($scope.tempVar.AlarmSetting.tempSettings[i].minTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].minTemp) {
    			$scope.tempVar.AlarmSetting.tempSettings[i].minTemp = 0;
    		}
    	}

    	var params = {
    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id             ,
    		"HouseId"       :  $scope.tempVar.AlarmSetting.HouseId                  ,
    		"AlarmSetting"  : {
					            "Delay"           :  $scope.tempVar.AlarmSetting.Delay            ,
					            "tempCpsation"    :  $scope.tempVar.AlarmSetting.tempCpsation     ,
					            "tempCpsationVal" :  $scope.tempVar.AlarmSetting.tempCpsationVal  ,
					            "alarmProbe"      :  $scope.tempVar.AlarmSetting.alarmProbe       ,
					            "pointAlarm"      :  $scope.tempVar.AlarmSetting.pointAlarm       ,
					            "tempSettings"    :  $scope.tempVar.AlarmSetting.tempSettings     ,
					            "effAlarmProbe":  {
										        "tempLeft1"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1+"",
										        "tempLeft2"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2+"",
										        "tempMiddle1" :$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1+"",
										        "tempMiddle2" :"false",
										        "tempRight1"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1+"",
										        "tempRight2"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2+""
										       }
					          }

		};

    	Sparraw.ajaxPost('sys/alarm/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.ErrorMsg == null) {
				 houseId = $scope.tempVar.AlarmSetting.HouseId;
		    }else {
		   		 Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		    };
	    });
    }


    $scope.copyFun = function(){

    	if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

    	app_confirm("所有栋舍将更新至当前设置，复制后您可以选择任何一栋单独修改，请确认。",null,null,function(buttonIndex){
			if(buttonIndex == 1){
				
			}else if(buttonIndex == 2){
				for (var i = 0; i < $scope.tempVar.AlarmSetting.tempSettings.length; i++) {
		    		console.log($scope.tempVar.AlarmSetting.tempSettings[i]);
		    		if ($scope.tempVar.AlarmSetting.tempSettings[i].maxTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].maxTemp) {
		    			$scope.tempVar.AlarmSetting.tempSettings[i].maxTemp = 0;
		    		}
		    		if ($scope.tempVar.AlarmSetting.tempSettings[i].tarTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].tarTemp) {
		    			$scope.tempVar.AlarmSetting.tempSettings[i].tarTemp = 0;
		    		}
		    		if ($scope.tempVar.AlarmSetting.tempSettings[i].minTemp == "" || !$scope.tempVar.AlarmSetting.tempSettings[i].minTemp) {
		    			$scope.tempVar.AlarmSetting.tempSettings[i].minTemp = 0;
		    		}
		    	}

		    	var params = {
		    		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id             ,
		    		"AlarmSetting"  : {
							            "Delay"           :  $scope.tempVar.AlarmSetting.Delay            ,
							            "tempCpsation"    :  $scope.tempVar.AlarmSetting.tempCpsation     ,
							            "tempCpsationVal" :  $scope.tempVar.AlarmSetting.tempCpsationVal  ,
							            "alarmProbe"      :  $scope.tempVar.AlarmSetting.alarmProbe       ,
							            "pointAlarm"      :  $scope.tempVar.AlarmSetting.pointAlarm       ,
							            "tempSettings"    :  $scope.tempVar.AlarmSetting.tempSettings     ,
							            "effAlarmProbe":  {
												        "tempLeft1"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft1+"",
												        "tempLeft2"   :$scope.tempVar.AlarmSetting.effAlarmProbe.tempLeft2+"",
												        "tempMiddle1" :$scope.tempVar.AlarmSetting.effAlarmProbe.tempMiddle1+"",
												        "tempMiddle2" :"false",
												        "tempRight1"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight1+"",
												        "tempRight2"  :$scope.tempVar.AlarmSetting.effAlarmProbe.tempRight2+""
												       }
							          }

				};

		    	Sparraw.ajaxPost('sys/alarm/saveSettingBatch.action', params, function(data){
					if (data.ResponseDetail.Result == "Success") {
				    	houseId = $scope.tempVar.AlarmSetting.HouseId;
						Sparraw.myNotice("保存成功");
					}else if (data.ResponseDetail.Result == "Fail"){
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					}else{
						Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					};
			    },null,200000);
			}
		});	
    }


    $ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }


    /*$scope.DireUse = function(){
    	app_alert("使用说明");
    }*/


})

//语音设置
.controller("voiceSettingsCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();

			setTimeout(
				function (){
					$scope.setData();
					$scope.queryDatatt();
				}
			,500);
		}
	,1000);

    $scope.setData = function(){

    	$scope.voiceSetData = {
    		"housesVoiceInfo":[]
    		/*"housesVoiceInfo":[{
    			"houseName":"01",
                "houseId": 307,
                "status": "Y",
                "alarmers": [{
                    "userId": 476,//第一报警人
                    "userOrder": 1,
                    "userType": 0
                },
                {
                    "userId": 643,//第二报警人
                    "userOrder": 2,
                    "userType": 0
                },
                {
                    "userId": 652,//第三报警人
                    "userOrder": 3,
                    "userType": 0
                }]
            }]*/
    	}
    }






    $scope.voiceSwitch = function(item){
    	console.log(item.status);
    }

    $scope.changeResponseA = function(item){
    	console.log(item);
    }

    $scope.changeResponseB = function(item){
    	console.log(item);
    }

    $scope.changeResponseC = function(item){
    	console.log(item);
    }

    $scope.queryDatatt = function(){
    	
		var params = {
			"FarmId":$scope.sparraw_user_temp.farminfo.id,
			"RemindMethod":"0",
		 };
		Sparraw.ajaxPost('sys/farm/remind/querySettingData_v2.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.voiceSetData.housesVoiceInfo = data.ResponseDetail.houseAlarmSetting;
				for (var i = 0; i < $scope.voiceSetData.housesVoiceInfo.length; i++) {
					if ($scope.voiceSetData.housesVoiceInfo[i].status == "Y") {
						$scope.voiceSetData.housesVoiceInfo[i].status = true;
					}else{
						$scope.voiceSetData.housesVoiceInfo[i].status = false;
					}
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    


    $scope.saveFun = function(item){
    	


    	if ($scope.sparraw_user_temp.Authority.AlarmSetting === "All") {
			
		}else{
			return app_alert("该用户无此操作权限。");
		};
    	
		var tUserAlarmers = [];

		var switchState = "N";
		if (item.status && item.status != "Y") {
			switchState = "Y";

			if (item.alarmers.length < 3) {
	    		return Sparraw.myNotice("您需要选择三位报警人，用来接听语音提醒。");
	    	}else{
	    		for (var i = 0; i < item.alarmers.length; i++) {
		    		if (!item.alarmers[i] || item.alarmers[i].userId == "" || !item.alarmers[i].userId) {
		    			return Sparraw.myNotice("您需要选择三位报警人，用来接听语音提醒。");
		    		}else{

		    		}
		    	}
	    	}

	    	tUserAlarmers = [{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[0].userId,"userOrder":1,"userType":0,"bak1":"","bak2":""},
						{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[1].userId,"userOrder":2,"userType":0,"bak1":"","bak2":""},
						{"remindMethod":0,"farmId":$scope.sparraw_user_temp.farminfo.id,"houseId":item.HouseId,"id":0,"modifyPerson":0,"modifyTime":null,"userId":item.alarmers[2].userId,"userOrder":3,"userType":0,"bak1":"","bak2":""}] ;

		}else{
			switchState = "N";
		}
		var params = {
			"FarmId" :  $scope.sparraw_user_temp.farminfo.id,
			"HouseId": item.HouseId,
			"RemindMethod" :  "0",
		    "alarmers":tUserAlarmers,
			"enableds":[{"farmId":$scope.sparraw_user_temp.farminfo.id,	
						"houseId":item.HouseId,
						"remindMethod":"0",
						"status":switchState,
						"id":-1,
						"bak1":"",
						"bak2":""}],
			"farmAlarmSetting":{"bak1":"",
								"bak2":"",
								"farmId":$scope.sparraw_user_temp.farminfo.id,
								"remindMethod":0,
								"switchReleHouse":"Y",
								"alarmReleHouse":"Y",
								"personReleHouse":"Y",
								"id":-1},
			"alarmCodes":[
							{"houseId":item.HouseId,"alarmCode":"B001H"},
							{"houseId":item.HouseId,"alarmCode":"B001L"},
							{"houseId":item.HouseId,"alarmCode":"C0002"}
			]
		 };
		Sparraw.ajaxPost('sys/farm/remind/saveSettingData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.queryDatatt();
				Sparraw.myNotice(item.HouseName+"栋保存成功。");
				
				
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},function(){
			Sparraw.myNotice("提交失败");
		});
	}

})
// 数据分析
.controller("dataAnalyseTableCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    $ionicLoading.show();
    setTimeout(
		function (){
			setPortrait(true,true);//进入时竖屏
			$ionicLoading.hide();
		}
	,1000);
    

    $scope.pointDevelop = function(reportId) {
    	if(reportId == 'wsdzh'){
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportTempHumi");
    		
    	}else if(reportId == 'wdkx'){
    		
    		Sparraw.getInfoStatus($ionicPopup,$state,"tempChart");
    	}else if(reportId == 'rstl'){
    		
    		Sparraw.getInfoStatus($ionicPopup,$state,"cullDeathRate");
    	}else if(reportId == 'chl'){
    		
    		Sparraw.getInfoStatus($ionicPopup,$state,"survivalRateDay");
    	}else if(reportId == 'jzsl'){
    		
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportWeightFeedRate");
    	}else if(reportId == 'rcsqx'){
    		
    		Sparraw.getInfoStatus($ionicPopup,$state,"reportDailyFeedRate");
    	}else if(reportId == 'prodQuota'){
    		//Sparraw.getInfoStatus($ionicPopup,$state,"productionQuota");
    		$state.go("productionQuota");
    	}else{
    		pointDevelop();
    	}
	};




})
/*--------------------newPage-------------------------*/
//日采食曲线
.controller("dayFoodCurveCtrl",function($scope, $state, $http, $ionicPopup,$ionicLoading, $stateParams, AppData) {
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
	
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("日采食");
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];

							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
	        	'CompareType' : "02",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 
							console.log(data.ResponseDetail.DCDatas.FBBatchCode);

							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             document.getElementById("chang").innerHTML = '日采食';
             document.getElementById("cullSurTi").innerHTML = '日饮水曲线';
             return $scope.changes2();
        }else if (dd=='日采食'){
        	 document.getElementById("chang").innerHTML = '日饮水';
        	 document.getElementById("cullSurTi").innerHTML = '日采食曲线';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
  	//日饮水
	$scope.changes2 = function(){
		console.log("日饮水");
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
		}
	}
})

//日饮水曲线
.controller("dayWaterCurveCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
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
	               return $scope.changes2();
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
	
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             document.getElementById("chang").innerHTML = '日采食';
             document.getElementById("cullSurTi").innerHTML = '日饮水曲线';
             return $scope.changes2();
        }else if (dd=='日采食'){
        	 document.getElementById("chang").innerHTML = '日饮水';
        	 document.getElementById("cullSurTi").innerHTML = '日采食曲线';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
  	//日饮水
	$scope.changes2 = function(){
		console.log("日饮水");
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
		}
	}
	
})

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


    if ($scope.sparraw_user_temp.Authority.HouseBreed === "All") {

	}else{
		// Sparraw.myNotice("该登录用户此界面只允许查看。");
		// app_alert("该登录用户此界面只允许查看。");
	};




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
			$scope.dailyReportData.dataInput[i].death_pm = NulltoZero($scope.dailyReportData.dataInput[i].death_pm);
			$scope.dailyReportData.dataInput[i].culling_pm = NulltoZero($scope.dailyReportData.dataInput[i].culling_pm);
			$scope.dailyReportData.dataInput[i].daily_feed = NulltoZero($scope.dailyReportData.dataInput[i].daily_feed);
			$scope.dailyReportData.dataInput[i].daily_water = NulltoZero($scope.dailyReportData.dataInput[i].daily_water);
			$scope.dailyReportData.dataInput[i].daily_weight = NulltoZero($scope.dailyReportData.dataInput[i].daily_weight);
		};

		console.log(persistentData.dataEntryReceiveHouse.HouseBreedStatus);
		if (persistentData.dataEntryReceiveHouse.HouseBreedStatus === 0) {
			for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
				if ($scope.sparraw_user_temp.userinfo.houses[i].HouseId == persistentData.dataEntryReceiveHouse.HouseId) {
					persistentData.dataEntryReceiveHouse.HouseBreedStatus = $scope.sparraw_user_temp.userinfo.houses[i].HouseBreedBatchId;
				};
			};
		}else{
			Sparraw.myNotice("保存失败");
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
	    });
	}


	$scope.judgeIntoYoungBtnStatus = function(){

		if ($scope.sparraw_user_temp.Authority.HouseBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		if ($scope.dailyReportData.intoYoungBtnStatus) {
			$state.go("docPlace");
		}else{
			if (persistentData.dataEntryReceiveHouse.HouseBreedStatus === "00") {
				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus === "00") {
					app_alert("请先创建农场批次。");
				};
				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus === "02") {
					app_alert("该农场批次已经结算完成。");
				};
			};
			if (persistentData.dataEntryReceiveHouse.HouseBreedStatus === "02") {
				Sparraw.myNotice("该栋舍已出栏。");
			};
		}
	}



	$scope.slaughterRemind = function(){
		if ($scope.sparraw_user_temp.Authority.HouseBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		if ($scope.dailyReportData.slaughterBtnStatus) {
			var oDate  = new Date();
			var oMonth = ("0" + (oDate.getMonth() + 1)).slice(-2);
			var oDay   = ("0" + (oDate.getDate())).slice(-2);
			var NowDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;
			$scope.dailyReportData.slaughterDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;
			//(只有存栏数，没看到出栏数啊)
			app_confirm('当前出栏数是:' + $scope.dailyReportData.cur_amount + ',出栏日期是今天，请确认。','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                      	app_confirm('出栏后该批次数据将无法修改，请确认。','提示',null,function(buttonIndex){
		                   if(buttonIndex == 2){
		                        $scope.slaughterFun();
		                   }
		              });  
                   }
              }); 

		}else{
			console.log("灰色");
			if (persistentData.dataEntryReceiveHouse.HouseBreedStatus === "00") {

				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus === "00") {
					app_alert("请先创建农场批次。");
				};
				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus === "02") {
					app_alert("该农场批次已经结算完成。");
				};
			};
			if (persistentData.dataEntryReceiveHouse.HouseBreedStatus === "02") {
				Sparraw.myNotice("该栋舍已出栏。");
			};
		};
	};

	$scope.slaughterFun = function(){
		if (persistentData.dataEntryReceiveHouse.HouseBreedStatus == "01") {
			var oDate  = new Date();
			var oMonth = ("0" + (oDate.getMonth() + 1)).slice(-2);
			var oDay   = ("0" + (oDate.getDate())).slice(-2);
			var NowDate = oDate.getFullYear() + "-" + oMonth + "-" + oDay;

			var params = {
					"HouseBreedId"  :  persistentData.dataEntryReceiveHouse.HouseBreedStatus   ,  //栋舍批次id
			        "HouseId"       :  persistentData.dataEntryReceiveHouse.HouseId             ,  //栋舍id
			        "moveoutNum"    :  $scope.dailyReportData.cur_amount                        ,  //出栏数量
			        "moveoutWeight" :  ""                                                       ,  //出栏均重，目前可以为空
			        "marketDate"    :  NowDate							                           //今日时间
			};

	    	Sparraw.ajaxPost('dataInput/ChickSettle.action', params, function(data){
	    		persistentData.dataEntryReceiveHouse.HouseBreedStatus = "02";
	    		//重新获取服务器最新数据
	    		Sparraw.getLatestData($state,"home");
	    		Sparraw.myNotice("出栏成功");
		    },function(data){
	    		Sparraw.myNotice("出栏错误");
		    });
		}else{
			Sparraw.myNotice("出栏失败");
		};
	}



	$scope.getFocus = function(){
		$scope.chooseDiv = true;
		document.getElementById('blankDiv').style.height = 3 + 'rem';
	}

	$scope.loseBlur = function(item,judgeType){
		$scope.chooseDiv = false;
		document.getElementById('blankDiv').style.height = 9 + 'rem';
	}


	
})



//累计死淘率曲线
.controller("cumuCullDeathCurveCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setLandscape(true,true);   

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

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
			$scope.sparraw_user_temp.houseinfos[i].houseName += "栋";
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		var  selectBatch = {
			"id":"01",
			"mtc_device_id":"f528009e0292d922",
			"houseName":"全场平均",
			"feedarea":"99"
		};
		$scope.cullDeathRateData.containBatchHouse.push(selectBatch);
		console.log($scope.cullDeathRateData.containBatchHouse);
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	$scope.GainBatch();

	$scope.changesDieToLife = function(datas){
		for (var i = 0; i < datas.length; i++) {
			datas[i] = (100 - datas[i]);
		}
		return datas;
	}
	//死淘率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率");
		var yLeftRange = [90,100];//undefined;//[0,15];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('rep/DCRate/accDCRateReq.action', params, function(data){
				console.log("批次号对比---");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								var datas = $scope.changesDieToLife(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] = datas;// data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];

							$scope.cullDeathRateData.selectUnit = '%';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value) + '％';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});

			console.log("!!!!!!!!!!!!!!!!");
			console.log($scope.cullDeathRateData.selectedBatch);
			console.log("!!!!!!!!!!!!!!!!");
     	}else{
     		console.log("栋舍号对比_____");
     		$scope.selectedHouseId = JSON.parse($scope.cullDeathRateData.selectedHouse);
     		var params = { 
	        	'CompareType' : "02",
	        	'FarmId':$scope.sparraw_user_temp.farminfo.id,
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch ,
	    		'HouseId' : $scope.selectedHouseId.id
	        }
			Sparraw.ajaxPost('rep/DCRate/accDCRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								var datas = $scope.changesDieToLife(data.ResponseDetail.DCDatas[i].HouseDatas);
								$scope.cullDeathRateData.yData[i] = datas;// data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.yColor = ['#009081','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value) + '％';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};

						//$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.selectedHouse);
							console.log("-----------");
							console.log($scope.cullDeathRateData.selectedHouse);
							console.log("-----------");
			});
     	};
	}
    $scope.changes = function(){
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             document.getElementById("chang").innerHTML = '日死淘率';
             document.getElementById("cullSurTi").innerHTML = '成活率报表';
             return $scope.changes2();
        }else if (dd=='日死淘率'){
        	 document.getElementById("chang").innerHTML = '成活率';
        	 document.getElementById("cullSurTi").innerHTML = '日死淘率报表';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
  	//成活率率
	$scope.changes2 = function(){
		console.log("成活率");
		var yLeftRange = [85,100];//undefined;//[0,15];
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							console.log("yName:" + $scope.cullDeathRateData.yName);
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );

						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}
	}
})

//日死淘率曲线
.controller("dayCullDeathCurveCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setLandscape(true,true);   

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }

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
			$scope.sparraw_user_temp.houseinfos[i].houseName += "栋";
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		var  selectBatch = {
			"id":"01",
			"mtc_device_id":"f528009e0292d922",
			"houseName":"全场平均",
			"feedarea":"99"
		};
		$scope.cullDeathRateData.containBatchHouse.push(selectBatch);
		console.log($scope.cullDeathRateData.containBatchHouse);
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	$scope.GainBatch();

	//死淘率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率");
		var yLeftRange = [0,10];//undefined;//[0,15];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('rep/DCRate/DCRateReq.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							
							
							$scope.cullDeathRateData.selectUnit = '‰';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '‰';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id
	        }
	         console.log($scope.cullDeathRateData);
			Sparraw.ajaxPost('rep/DCRate/DCRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.selectUnit = '‰';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '‰';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             document.getElementById("chang").innerHTML = '日死淘率';
             document.getElementById("cullSurTi").innerHTML = '成活率报表';
             return $scope.changes2();
        }else if (dd=='日死淘率'){
        	 document.getElementById("chang").innerHTML = '成活率';
        	 document.getElementById("cullSurTi").innerHTML = '日死淘率报表';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
  	//成活率率
	$scope.changes2 = function(){
		console.log("成活率");
		var yLeftRange = [85,100];//undefined;//[0,15];
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							console.log("yName:" + $scope.cullDeathRateData.yName);
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}
	}
})

/*-------------------------------------------*/


//  温度测试K线图
.controller("tempKMapCtrl",function($scope, $state, $http, $ionicPopup, $stateParams, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    setTimeout(
		function (){
		app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	setPortrait(true,true);//出去时竖屏
		setTimeout(
			function (){
				$state.go("dataAnalyseTable");
			}
		,1000);
    }


    //数据源
	$scope.tempKMapData = {
		"selectHouse"    :  "",
		"selectedBatch"  :  "",
		"farmBatch"      :  ""	
	}




	if(navigator.userAgent.indexOf('Firefox') >= 0) {
		//火狐浏览器获取高度
		var MAXHEIGHT = document.documentElement.clientHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '44';
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}else {
		//计算出手机屏幕高度
		var MAXHEIGHT = document.body.scrollHeight;
		//将屏幕高度赋给div
		var DIVHEIGHT = MAXHEIGHT - '44';
		document.getElementById('tempChart_DIV').style.height = DIVHEIGHT + 'px';
	}


	//一共多少条线	
	var sumObj = new Array(3);
	//一共多少数据
	var miniObj = new Array(160);
	for (var i = 0; i <miniObj.length ; i++) {
		miniObj[i] = parseInt(Math.random()*(100-80)+80);
	};
	for (var i = 0; i < sumObj.length; i++) {
		//每条数据有多少个
		var start = i * 55;
		var end = start + 55;
		sumObj[i] = miniObj.slice(start,end);
	};

	//随机颜色
	var getRandomColor = function(){
	  return  '#' +    
	    (function(color){    
	    return (color +=  '0123456789abcdef'[Math.floor(Math.random()*16)])    
	      && (color.length == 6) ?  color : arguments.callee(color);    
	  })('');    
	} 

	/*K线图数据*/
	//一共多少数据
	var kSingleData = new Array(196);
	for (var i = 0; i <kSingleData.length ; i++) {
		kSingleData[i] = parseInt(Math.random()*(100-80)+80);
	};
	//一条k线的数据
	var kTotalData = new Array(49);
	for (var i = 0; i < kTotalData.length; i++) {
		var start = i * 4;
		var end = start + 4;
		kTotalData[i] = kSingleData.slice(start,end);
	};
	console.log(kTotalData);



  var myChart  ;
  var option   ;

  var xData    ;
  var yData1   ;
  var yData2   ;
  var yData3   ;
  var yData4   ;




  xData = ['00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30','24:00'];//X轴数据
  //xData = ["12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)","12-21(日龄:1)","12-22(日龄:2)","12-23(日龄:3)","12-24(日龄:4)","12-25(日龄:5)","12-26(日龄:6)"];
  yData1 = sumObj[0];
  yData2 = sumObj[1];
  yData3 = sumObj[2];
  yData4 = sumObj[1];



  var tTitleName;

  var tLegend;
  var serialsName1;
  var serialsName2;
  var serialsName3;
  var serialsName4;



  tTitleName = "温度K线图";
  tLegend = ['数据1','数据2','数据3','数据4'];//点击显示一条数据
  serialsName1 = "数据1";
  serialsName2 = "数据2";
  serialsName3 = "数据3";
  serialsName4 = "数据4";


    
  require.config({
      paths: {
          echarts: 'js/echarts-2.2.7'
      }
  });
  //显示多少（什么）数据
  require(
      [
          'echarts',
          'echarts/chart/line',
      ],
      function (ec) {
          myChart = ec.init(document.getElementById('main'));
          option = {
				tooltip : {
                    trigger: 'axis',
                    textStyle:{
                      fontSize:13
                	},
                    backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
                },
                legend: {
                    data:tLegend,
                    selected:{//显示部分数据
                    	"目标温度":false,
                    	"温度移动平均":false,
                    	"湿度":false,
                    	"数据4":false
                    }

                },
                grid://表对应上下左右的大小
                {
                    x:30,
                    y:30,
                    x2:20,
                    y2:30
                },
                xAxis : [
                    {

                        type : 'category',
                        data : xData,
                        nameLocation:'start',
                        show :true,//是否显示x轴
                        axisTick : true,
                        axisLabel:{
                        	//margin:10 //文字与x轴的距离
                        },
                        /*splitLine:{

                        },*/
                        splitLine:true,
                        axisLine:{
                        	lineStyle:{//x轴风格
                        		color: '#000',
                        		width:0.5
                        	}
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                              formatter: '{value}'//左边的数据
                          },
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#000',
                        		width:1
                        	}
                        }
                    },
                    {
                        type : 'value',
                        nameTextStyle:{
                          fontSize:16
                        },
                        axisLabel : {
                              show:false,//是否显示
                              formatter: '{value}'//右边的数据
                          },
                        splitLine : false,
                        scale: true,
                        axisLine:{
                        	lineStyle:{
                        		color: '#fff',
                        		width:1
                        	}
                        }
                    }
                ],
                series : [
                    {
	                  name:serialsName1,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,//是否折线
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName2,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData2,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:getRandomColor(),
			                        width:0.5
			                    }
			                }
			            }
                    },{
	                  name:serialsName3,

	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData3,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    },{
	                  name:serialsName4,
	                  type:'line',
	                  yAxisIndex: 0,
	                  smooth:true,
	                  axisLine : true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData4,
                        itemStyle: {
			                normal: {
			                    lineStyle: {
			                    	color:'red',
			                        width:0.5
			                    }
			            	}
                    	}
                    }
                    

                ]
            };

          myChart.on('click',function(param){
			 	console.log(param.name);
			 });



          myChart.on(option);
          myChart.setOption(option);
          // window.onresize = myChart.resize;
          window.onresize = function(){};
      }
  );
})


// 成活死淘率曲线
.controller("cullDeathRateCtrl",function($scope, $state, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
    setLandscape(true,true);   

    $scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }
    

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
			$scope.sparraw_user_temp.houseinfos[i].houseName += "栋";
			$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
		};
		var  selectBatch = {
			"id":"01",
			"mtc_device_id":"f528009e0292d922",
			"houseName":"全场平均",
			"feedarea":"99"
		};
		$scope.cullDeathRateData.containBatchHouse.push(selectBatch);
		console.log($scope.cullDeathRateData.containBatchHouse);
		$scope.cullDeathRateData.compareType = "01";
		$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0]);
		$scope.batchDiv = true;
		$scope.housesDiv = false;
	};
	
	$scope.GainBatch();

	//死淘率
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("死淘率");
		var yLeftRange = [0,10];//undefined;//[0,15];
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch        	
	        }
			Sparraw.ajaxPost('rep/DCRate/DCRateReq.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							
							
							$scope.cullDeathRateData.selectUnit = '‰';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '‰';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id
	        }
	         console.log($scope.cullDeathRateData);
			Sparraw.ajaxPost('rep/DCRate/DCRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							$scope.cullDeathRateData.selectUnit = '‰';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '‰';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             document.getElementById("chang").innerHTML = '日死淘率';
             document.getElementById("cullSurTi").innerHTML = '成活率报表';
             return $scope.changes2();
        }else if (dd=='日死淘率'){
        	 document.getElementById("chang").innerHTML = '成活率';
        	 document.getElementById("cullSurTi").innerHTML = '日死淘率报表';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='成活率'){
	             return $scope.changes1();
	        }else if (dd=='日死淘率'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='成活率'){
             return $scope.changes1();
        }else if (dd=='日死淘率'){
        	 return $scope.changes2();
        }
    }
  	//成活率率
	$scope.changes2 = function(){
		console.log("成活率");
		var yLeftRange = [85,100];//undefined;//[0,15];
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							console.log("yName:" + $scope.cullDeathRateData.yName);
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/SVRate/SurvlRateReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '%';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value + '%';
										            }
			                                        return    res;
							                     },
												 yLeftRange
						   );
						}else if(data.ResponseDetail.DCDatas.length == 0 || data.ResponseDetail.DCDatas == undefined){
							Sparraw.myNotice("暂无信息");
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
			});
		}
	}
})

// 温湿度综合报表
.controller("reportTempHumiCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(true,true);   
    /*$scope.goDataAnalyseTable = function(){
    	$state.go("dataAnalyseTable");
    }*/


    $scope.back = function(){
    	$state.go("envMonitoring");
    }

	$scope.tempChartData = {
		"batchTable"        :  ""                           ,//批次表
		// "selectedHouseId"   :  JSON.stringify($scope.sparraw_user_temp.houseinfos[0]).id,//选中的栋舍id
		"selectedHouseId"   :  $scope.sparraw_user_temp.houseinfos[0].id,//选中的栋舍id
		"selectedBatch"     :  ""                           ,//选中批次的key
		"selectedBatchVal"  :  ""                           //选中批次的val
	}
   //获取批次信息
	var params = {
		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id 
	};

	


	document.getElementById('TempHumi_DIV').style.height = (screen.width - 75) + 'px';

	Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
		if (data.ResponseDetail.Result == "Success") {
			$scope.tempChartData.batchTable = data.ResponseDetail.FarmBreedIdArray;
			//获取key
			for(var key in $scope.tempChartData.batchTable){
			    $scope.tempChartData.selectedBatch = key;
			}
			//获取value  
			for(var item in $scope.tempChartData.batchTable){  
		        if(item==key){  
		            $scope.tempChartData.selectedBatchVal = $scope.tempChartData.batchTable[item];
		        }  
		    }  
            $scope.switchBatch();
		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});
	var tFarmCode ;
	var tBatchNum;
	var myChart ;
	var option ;
	var TNata ;
	var kData ;
	var yData1 ;
	var yData2 ;
	var xData ;
	$scope.switchBatch= function(){
		var params = {
			"FarmBreedId"   : $scope.tempChartData.selectedBatch,
			"HouseId"       : $scope.tempChartData.selectedHouseId
		};

		console.log(params);
		Sparraw.ajaxPost('rep/TempHumi/TempHumiReq.action', params, function(data){
			if (data.ResponseDetail.Result == "Success"){
			 kData  = new Array(data.ResponseDetail.THDatas.length);
			 TNata;
			 yData1 =new Array(data.ResponseDetail.THDatas.length);
			 yData2 = new Array(data.ResponseDetail.THDatas.length);
			 xData  = new Array(data.ResponseDetail.THDatas.length);
				for (var i = data.ResponseDetail.THDatas.length - 1; i >= 0; i--){
					 TNata = new Array(4);
					TNata[0] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[1] = data.ResponseDetail.THDatas[i].MaxTemp;
					TNata[2] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[3] = data.ResponseDetail.THDatas[i].MaxTemp;
					kData[i] = TNata;
				   	yData1[i] = data.ResponseDetail.THDatas[i].TarTemp;
					yData2[i] = data.ResponseDetail.THDatas[i].Humi;
				}
				if(data.ResponseDetail.xAxis.length>46){
                   xData = data.ResponseDetail.xAxis;
				}else{
				   xData = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
				}

				if (Object.prototype.toString.call($scope.tempChartData.selectedHouseId) === "[object String]") {

				}else{
					$scope.tempChartData.selectedHouseId = JSON.stringify($scope.tempChartData.selectedHouseId);
				}
				

			    return $scope.goecharts();
			}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			return $scope.goecharts();
			};
		});
	}


	var  tTitleName = "温度湿度综合表";
	var  tLegend = ['平均温度(℃)','目标温度(℃)','湿度%'];
	var  serialsName1 = "平均温度(℃)";
	var  serialsName2 = '目标温度(℃)';
	var  serialsName3 = "湿度%";
    
	$scope.goecharts = function (){ require.config({
	  paths: {
	      echarts: 'js/echarts-2.2.7'
	  }
	});
	require(
	  [
	      'echarts',
	      'echarts/chart/line',   
	      'echarts/chart/k'
	  ],
	  function (ec) {
	      myChart = ec.init(document.getElementById('main'));
	      option = {
				tooltip : {
	                trigger: 'axis',

	                textStyle:{
	                  fontSize:13
	            	},
	            	backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
	                formatter: function (params) {
	                	var res = '日龄：' +params[0].name;
	                	if (params[0].value[3]!=null) {
	                		res += '<br/>  最高温度 : ' + params[0].value[1] + '℃';
	                	}else{
                            res += '<br/>  最高温度 : - ℃';
	                	};
						if (params[0].value[2]!=null) {
	                		 res += '<br/>  最低温度 : ' + params[0].value[0] + '℃';
	                	}else{
	                		 res += '<br/>  最低温度 : - ℃';
	                	};		
	                    if (params[1].value!=null) {
	                		res += '<br/>目标温度' ;
	                        res += ' : ' +params[1].value + '℃';
	                	}else{
	                		res += '<br/>目标温度' ;
	                        res += ' : - ℃';
	                	};
	                	if (params[2].value!=null) {
	                		res += '<br/>湿度' ;
	                        res += ' : ' + params[2].value + '%';
	                	}else{
	                		res += '<br/>湿度' ;
	                        res += ' : - %';
	                	};       
	                    return res;
	                }
	            },
	            grid:
	            {
	                x:50,
	                y:30,
	                x2:50,
	                y2:30,
	                borderColor:'#BBB'
	            },
	            legend: {
	                data:tLegend
	            },
	            xAxis : [
	                {

	                    type : 'category',
	                    data : xData,
	                     splitLine:{show: false},
	                    nameLocation:'start',
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            yAxis : [
	                {
	                	position:'left',
	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          formatter: '{value}°C'
	                      },
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		//color: '#000000',
	                    		width:1
	                    	}
	                    }
	                },
	                {
	                	position:'right',
 	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          show:true,
	                          formatter: '{value}.0%'
	                      },
	                    splitLine : false,
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            series : [
	                {
	                    name:serialsName1,
	                    type:'k',
	                    data:kData,
	                    itemStyle: {
			                normal: {
			                	// color:"#FF3909",
			                    lineStyle: {
			                        width:0.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName2,
	                  type:'line',
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
	                    itemStyle: {
			                normal: {
			                	color:"#33CD5F",
			                    lineStyle: {
			                        width:0.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName3,
	                  type:'line',
	                  yAxisIndex: 1,
	                  data:yData2,
	                  smooth:true,
	                   // symbol:'none',
	                   symbolSize:1,
	                    itemStyle: {
			                normal: {
			                	color:"#87CEFA",
			                    lineStyle: {
			                        width:0.5
			                    },

			                    areaStyle: {
			                        // 区域图，纵向渐变填充
			                        color : (function (){
			                            var zrColor = require('zrender/tool/color');
			                            return zrColor.getLinearGradient(
			                                0, 200, 0, 400,
			                                [[1, 'rgba(135,206,250,0.5)'],[1, 'rgba(255,255,255,0.1)']]
			                            )
			                        })()
			                    }

			                }
			            }
	                }
	            ]
	        };










	      myChart.setOption(option);
	      //window.onresize = myChart.resize;
	      window.onresize = function(){};
	  }
	);

	}


})
// 饲料转换
.controller("reportWeightFeedCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setTimeout(
		function (){
			app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	setPortrait(true,true);//出去时竖屏
		
		setTimeout(
			function (){
				$state.go("dataAnalyseTable");
			}
		,1000);
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

	
	document.getElementById('feedconvert_DIV').style.height = (screen.width - 75) + 'px';
	
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
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
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
	               return $scope.changes2();
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
    $scope.changes = function(){
             return $scope.changes2();
    }
    //切换筛选方式
    $scope.changesCompareType = function(){
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        return $scope.changes2();
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			 return $scope.changes2();
		};
	};
    //切换农场批次号
    $scope.changesFarmId = function(){
        return $scope.changes2();
    }
    //切换栋舍
    $scope.changesHousesId = function(){
        return $scope.changes2();
    }
     //料肉比转化率
	$scope.changes2 = function(){
    if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch
			}
			Sparraw.ajaxPost('rep/WeightFeed/FCRReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							}
							$scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value ;
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
			$scope.selectedHouseId = JSON.parse($scope.cullDeathRateData.selectedHouse);
			var params = { 
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id
			}
			Sparraw.ajaxPost('rep/WeightFeed/FCRReq.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++){
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
								if (i==4)
								{
								break;
								}
							}
							$scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '',
												 function (params){
							                        var res = '日龄 :' + params[0].name;
										            for (var i = 0, l = params.length; i < l; i++) {
										                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value ;
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
		}
	}
})
// 日采食曲线
.controller("reportDailyFeedCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
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
	
	//日采食
     $scope.changes1 = function(){
     	$scope.cullDeathRateData.xData=[];
     	console.log("日采食！！！");
     	if ($scope.cullDeathRateData.compareType == '01') {
     		var params = { 
	        	'CompareType' : "01",
	        	'FarmBreedId' : $scope.cullDeathRateData.selectedBatch,
	        	"FarmId"   :   $scope.sparraw_user_temp.farminfo.id        	
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
	        	'CompareType' : "02",
	    		'HouseId' : $scope.selectedHouseId.id,
	    		"FarmId"   :   $scope.sparraw_user_temp.farminfo.id
	        }
			Sparraw.ajaxPost('/rep/DailyFeed/DFRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							 $scope.cullDeathRateData.yName =  [];
							 $scope.cullDeathRateData.yData =  []; 
							 var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
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
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：克 / 天·只',
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
    	console.log("这是changes方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             document.getElementById("chang").innerHTML = '日采食';
             document.getElementById("cullSurTi").innerHTML = '日饮水曲线';
             return $scope.changes2();
        }else if (dd=='日采食'){
        	 document.getElementById("chang").innerHTML = '日饮水';
        	 document.getElementById("cullSurTi").innerHTML = '日采食曲线';
        	 return $scope.changes1();
        }
    }
    //切换筛选方式
    $scope.changesCompareType = function(){

    	var dd = document.getElementById("chang").innerHTML;
		if ($scope.cullDeathRateData.compareType == '01') {
			//栋舍号对比
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		}else{
			//批次号对比
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			if(dd=='日饮水'){
	             return $scope.changes1();
	        }else if (dd=='日采食'){
	        	 return $scope.changes2();
	        }
		};

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
    	console.log("这是changesFarmId方法");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
    //切换栋舍
    $scope.changesHousesId = function(){
    	console.log("changesHousesId");
        var dd = document.getElementById("chang").innerHTML ;
        if(dd=='日饮水'){
             return $scope.changes1();
        }else if (dd=='日采食'){
        	 return $scope.changes2();
        }
    }
  	//日饮水
	$scope.changes2 = function(){
		console.log("日饮水");
		$scope.cullDeathRateData.xData = [];
		if ($scope.cullDeathRateData.compareType == '01') {
			var params = { 
				'CompareType' : "01",
				'FarmBreedId' : $scope.cullDeathRateData.selectedBatch,
				"FarmId"   :   $scope.sparraw_user_temp.farminfo.id
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
				console.log("批次号对比");
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng =[];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].housename;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
				'CompareType' : "02",
				'HouseId' : $scope.selectedHouseId.id,
				"FarmId"   :   $scope.sparraw_user_temp.farminfo.id
			}
			Sparraw.ajaxPost('rep/DailyWater/DWRRep.action', params, function(data){
						if (data.ResponseDetail.Result == "Success" && data.ResponseDetail.DCDatas.length != 0){
							$scope.cullDeathRateData.yName =  [];
						    $scope.cullDeathRateData.yData =  [];
						    var ageLeng = [];
							for (var i = 0; i < data.ResponseDetail.DCDatas.length; i++) {
								$scope.cullDeathRateData.yName[i] =  data.ResponseDetail.DCDatas[i].FBBatchCode;
								$scope.cullDeathRateData.yData[i] =  data.ResponseDetail.DCDatas[i].HouseDatas;
							    ageLeng[i] = data.ResponseDetail.DCDatas[i].HouseDatas.length;
							}
							var Maxage = Math.max.apply(null,ageLeng);
							if (Maxage <= 46) {
                               $scope.cullDeathRateData.xData  = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
							}else{
                                for (var i = 0; i < Maxage; i++) {
                                	$scope.cullDeathRateData.xData[i] = i;
                                }
							}
							$scope.cullDeathRateData.yColor = ['#FF7F50','#87CEFA','#DA70D6','#32CD32','#6495ED','#FF69B4'];
							$scope.cullDeathRateData.selectUnit = '';
							getLineChart($scope.cullDeathRateData.xData,
												 $scope.cullDeathRateData.yData,
												 $scope.cullDeathRateData.yName,
												 $scope.cullDeathRateData.yColor,
												 $scope.cullDeathRateData.hiddenPara,
												 $scope.cullDeathRateData.selectUnit,
												 '单位：毫升 / 天·只',
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
		}
	}
	
})


//生产指标汇总
.controller("productionQuotaCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setTimeout(
		function (){
			app_lockOrientation('landscape');//进入时横屏
		}
	,500);    
    $scope.goDataAnalyseTable = function(){
    	setPortrait(true,true);//出去时竖屏
    	setTimeout(
			function (){
				$state.go("dataAnalyseTable");
			}
		,1000);
    }

    $scope.prodQuotData = {
    	// "HouseId"      :  $scope.sparraw_user_temp.userinfo.houses[0].HouseId,
    	"farmId"       :  $scope.sparraw_user_temp.farminfo.id,
    	"farmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        "batchTable"   :  "",
        "selectedBatch":  "",
        "houseTable"   :  [],
        "selectedHouse":  "",
        "compareType"  :  "",//"01"-栋舍对比；"02"-批次对比
        "Headers"      :  [],
        "TableDatas"   :  [],


        
    }
    
    $scope.gridOptions = {
	   
	};
	
    $scope.drawTable = function(){
    	$scope.gridOptions.columnDefs = [];
    	for (var i = 1; i <= $scope.prodQuotData.Headers.length; i++) {
    		if (i != 1) {
    			$scope.gridOptions.columnDefs.push({name:"Index" + i ,  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false});
    		}else{
    			$scope.gridOptions.columnDefs.push({name:"Index" + i ,  displayName: $scope.prodQuotData.Headers[i-1], width:100 ,enableColumnMenu: false,pinnedLeft:true});
    		};
    	};
		$scope.gridOptions.data = $scope.prodQuotData.TableDatas;
    }
  
    $scope.changesBatch = function(){//按批次
    	var params = {
			"FarmId"       :  $scope.prodQuotData.farmId         ,
			"CompareType"  :  $scope.prodQuotData.compareType    ,
			"FarmBreedId"  :  $scope.prodQuotData.selectedBatch  ,
			"HouseId"      :  JSON.parse($scope.prodQuotData.selectedHouse).HouseId
		};
		console.log(params);
		Sparraw.ajaxPost('rep/ProSUM/SUMReq.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.prodQuotData.Headers = data.ResponseDetail.Headers;
    			$scope.prodQuotData.TableDatas = data.ResponseDetail.TableDatas;
				$scope.drawTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }

    $scope.changesHouses = function(){//按栋舍
    	var params = {
			"FarmId"       :  $scope.prodQuotData.farmId       ,
			"CompareType"  :  $scope.prodQuotData.compareType  ,
			"FarmBreedId"  :  $scope.prodQuotData.selectedBatch  ,
			"HouseId"      :  JSON.parse($scope.prodQuotData.selectedHouse).HouseId
		};
		Sparraw.ajaxPost('rep/ProSUM/SUMReq.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.prodQuotData.Headers = data.ResponseDetail.Headers;
    			$scope.prodQuotData.TableDatas = data.ResponseDetail.TableDatas;
				$scope.drawTable();
			}else if (data.ResponseDetail.Result == "Fail"){
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }


    $scope.prodQuotData.compareType = "01";//默认批次对比
    $scope.changesCompareType = function(){
    	if ($scope.prodQuotData.compareType == "01") {
    		$scope.batchDiv = true;
			$scope.housesDiv = false;

    		return $scope.changesBatch();//选择批次号
    	}else{
    		$scope.batchDiv = false;
			$scope.housesDiv = true;

    		return $scope.changesHouses();//选择栋舍号
    	};
    }

    


    //获取批次列表与栋舍列表
    $scope.getTableData = function(){
    	for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			$scope.prodQuotData.houseTable.push($scope.sparraw_user_temp.userinfo.houses[i]);
		};
		$scope.prodQuotData.selectedHouse = JSON.stringify($scope.prodQuotData.houseTable[0]);
    	var params = {
			"FarmId"     : $scope.prodQuotData.farmId
		};
		Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
	            $scope.prodQuotData.batchTable = data.ResponseDetail.FarmBreedIdArray;
				//获取key
				for(var key in $scope.prodQuotData.batchTable){
				    $scope.prodQuotData.selectedBatch = key;
				}
				//获取value  
				 for(var item in $scope.prodQuotData.batchTable){  
			        if(item==key){  
			            $scope.prodQuotData.selectedBatchVal = $scope.prodQuotData.batchTable[item];
			        }  
			    }
			    $scope.changesCompareType();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
    }

    $scope.getTableData();
    
})

//任务提醒
.controller("taskRemindCtrl",function($scope, $state, $http, $ionicSideMenuDelegate, $ionicPopup, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.taskRemindData = {
		"selectHouse" :  ""     ,
		"UnCompleteTaskNum":  "0"  ,//未完成任务个数
		"delayCount"	   :  "0"  ,//延迟任务个数
		"cancleCount"      :  "0"  ,//完成任务个数
		"AgeBegin"            :  ""  ,  //开始日龄
        "AgeEnd"              :  ""  ,	//结束日龄
        "TaskTimeSlot"        :  "0"  ,	//任务时间段
		"TskInfo"  :[{
			"TskGrpName":"",
			"curAgeFlag":"",
			"TaskDetail":[{
				"TskSN":"",
				"TaskName":"",
				"dealStatus":""
			}]
		}]
	};


	
	//默认选择第一栋
	$scope.taskRemindData.selectHouse = JSON.stringify($scope.sparraw_user_temp.userinfo.houses[0]);
	//设置默认值自动查询
    $scope.taskRemindData.AgeBegin   =  "0"    ;
    $scope.taskRemindData.AgeEnd     =  "0"    ;
	$scope.judgeHouse = function(IsFirstTime){
		//判断农场状态
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01") {
			$scope.sparraw_user_temp.farminfo.farmBreedBatchId = 0;
		}else{

		};

		var required = [$scope.taskRemindData.AgeBegin,$scope.taskRemindData.AgeEnd];
	   	if (parseInt(required[0]) > parseInt(required[1])) {
	    	return Sparraw.myNotice('开始时间不能大于结束时间');
	    };

	    if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {

			switch($scope.taskRemindData.TaskTimeSlot){
				case "0":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "0";
				  break;
				case "1":
					$scope.taskRemindData.AgeBegin="-1";
				  	$scope.taskRemindData.AgeEnd = "-1";
				  break;
				case "2":
					$scope.taskRemindData.AgeBegin="0";
				  	$scope.taskRemindData.AgeEnd = "7";
				  break;
				case "3":
					$scope.taskRemindData.AgeBegin="8";
				  	$scope.taskRemindData.AgeEnd = "14";
				  break;
				case "4":
					$scope.taskRemindData.AgeBegin="15";
					$scope.taskRemindData.AgeEnd = "21";
				  break;
				case "5":
					$scope.taskRemindData.AgeBegin="22";
				  	$scope.taskRemindData.AgeEnd = "28";
				  break;
				case "6":
					$scope.taskRemindData.AgeBegin="29";
				  	$scope.taskRemindData.AgeEnd = "35";
				  break;
				case "7":
					$scope.taskRemindData.AgeBegin="36";
				  	$scope.taskRemindData.AgeEnd = "42";
				  break;
				case "8":
					$scope.taskRemindData.AgeBegin="43";
					$scope.taskRemindData.AgeEnd = "49";
				  break;
				case "9":
					$scope.taskRemindData.AgeBegin="50";
					$scope.taskRemindData.AgeEnd = "56";
				  break;
				case "10":
					$scope.taskRemindData.AgeBegin="57";
					$scope.taskRemindData.AgeEnd = "63";
				  break;
				default:
					$scope.taskRemindData.AgeBegin="64";
					$scope.taskRemindData.AgeEnd = "71";
					
	};
		};



		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"AgeBegin"     :  $scope.taskRemindData.AgeBegin                        ,
   			"AgeEnd"       :  $scope.taskRemindData.AgeEnd
		};
		Sparraw.ajaxPost('tsk/ActualTask/query.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskRemindData.TskInfo = data.ResponseDetail.TskInfo;
				$scope.taskRemindData.UnCompleteTaskNum = data.ResponseDetail.UnCompleteTaskNum;
				$scope.taskRemindData.delayCount = data.ResponseDetail.delayCount;
				$scope.taskRemindData.cancleCount = data.ResponseDetail.cancleCount;
			}else if (data.ResponseDetail.Result == "Fail"){
				$scope.taskRemindData.TskInfo = [];
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
			if (IsFirstTime!="firstTime"&&IsFirstTime=="sidebarSearch") {
				$ionicSideMenuDelegate.toggleRight();
			};
		});
	}
	$scope.judgeHouse("firstTime");
	
	$scope.toggleRight = function() {
    	$ionicSideMenuDelegate.toggleRight();
  	};
	
	$scope.cancel = function(){
		$ionicSideMenuDelegate.toggleRight();
	}

	//判断日龄
	$scope.judgeDaysAge = function(item){
		if (item.curAgeFlag == "Y") {
			return "{background:'#71C671'}";
		}else{
			return "{background:'#AAAAAA'}";
		}
	};

	//判断任务状态
	$scope.judgeStateUpdateColor = function(sku){
		if (sku.UpdateFlag=='N' || $scope.sparraw_user_temp.Authority.TaskDeal == 'Read') {

		}else{
			if (sku.dealStatus == "01" || sku.dealStatus == "03") {
				return "{color:'#666666'}";
			}else if (sku.dealStatus == "02" || sku.dealStatus == "00"){
				return "{color:'red'}";
			}else {
				return "{color:'#2f7fff'}";
			}
		};
	}


	//判断权限
	if ($scope.sparraw_user_temp.Authority.TaskSetting === "All") {
		$scope.setBtn = false;
	}else{
		$scope.setBtn = true;
	};



	

	//任务状态选择
	$scope.judgeTaskState = function(choice,sku){
		console.log($scope.sparraw_user_temp.Authority);

		var params = {
			"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId    ,
   			"HouseId"      :  JSON.parse($scope.taskRemindData.selectHouse).HouseId ,
   			"TskSN"        :  sku.TskSN                                                    ,
   			"TaskName"     :  sku.TaskName,
   			"dealStatus"   :  choice
		};
		Sparraw.ajaxPost('tsk/ActualTask/deal.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.judgeHouse();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});


	};
})

//任务设定
.controller("taskSetCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.TaskType = ["01","02","03","04","05"];

	$scope.goTaskTable = function(){
		$state.go("taskTable");
	}
})
//任务列表
.controller("taskTableCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	//用来判断从哪个入口进入以及标题的显示
	$scope.TaskType = $stateParams.TaskType;
	
	
	$scope.taskTableData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别
		"TaskType"      :  ""                                    ,//用来判断从服务器获取什么类型的任务（任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务）
		"conveyTskID"   :  ""                                    ,

		"TaskDetail"    : [{
			"TskID"         :  ""                                    ,//任务id
			"TaskName"      :  ""                                    ,//任务名称
			"ageInfos"      :  ""                                    ,//任务日期
			"TaskStatus"    :  ""                                     //任务状态Y有效、 N无效（任务状态是指饲养员是否可见Y可见、N隐藏）
		}]
		
	}
	//清空该值
	persistentData.overallTaskId     =  ""  ;
	persistentData.overallTaskState  =  ""  ;



	switch($scope.TaskType){
				case "01":
				  	$scope.taskTableData.showTaskType = "入雏前准备";
				  	$scope.taskTableData.TaskType     = "01";
				  break;
				case "02":
				  	$scope.taskTableData.showTaskType = "入雏后日常工作";
				  	$scope.taskTableData.TaskType     = "02";
				  break;
				case "03":
				  	$scope.taskTableData.showTaskType = "光照程序";
				  	$scope.taskTableData.TaskType     = "03";
				  break;
				case "04":
				  	$scope.taskTableData.showTaskType = "免疫用药程序";
				  	$scope.taskTableData.TaskType     = "04";
				  break;
				default:
					$scope.taskTableData.showTaskType = "其他临时任务";
					$scope.taskTableData.TaskType     = "05";
	};



	$scope.inquire = function(){
		var params = {
			"FarmId"     :    $scope.taskTableData.FarmId      ,
			"TaskType"   :    $scope.taskTableData.TaskType    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskList.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.taskTableData.TaskDetail = data.ResponseDetail.TaskDetail;
			}else if (data.ResponseDetail.Result == "Fail") {
				if ($scope.taskTableData.TaskDetail.length == 1) {
					$scope.taskTable = true;
				};
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.obtainTskId = function(item){
		$scope.taskTableData.conveyTskID = item.TskID;
	}



})
//新增任务
.controller("addTaskCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.TaskType = $stateParams.TaskType;

	$scope.addTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  ""                                    ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）默认启用
	}

	switch($scope.TaskType){
				case "01":
				  	$scope.addTaskData.showTaskType = "入雏前准备";
				  	$scope.addTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.addTaskData.showTaskType = "入雏后日常工作";
				  	$scope.addTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.addTaskData.showTaskType = "光照程序";
				  	$scope.addTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.addTaskData.showTaskType = "免疫用药程序";
				  	$scope.addTaskData.TaskType = "04";
				  break;
				default:
					$scope.addTaskData.showTaskType = "其他临时任务";
					$scope.addTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.addTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.addTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};

	

	$scope.save = function(){
		$scope.addTaskData.AgeInfos = $scope.addTaskData.AgeInfos.trim();
		//判断是否为空
		if ((isNull($scope.addTaskData.TaskName)) 
			&& (isNull($scope.addTaskData.AgeInfos)) 
			&& (isNull($scope.addTaskData.TaskStatus))) {
		}else{
			return;
		};

		var tempAgeInfos = "";
		if($scope.addTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.addTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!app_IsNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.addTaskData.AgeInfos = tempAgeInfos.substring(1);

		var params = {
			"FarmId"  :    $scope.addTaskData.FarmId      ,
			"TaskName":    $scope.addTaskData.TaskName    ,
          	"TaskType":    $scope.addTaskData.TaskType    ,
          	"AgeInfos":    $scope.addTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.addTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/addTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("添加任务成功！");
				persistentData.overallTaskId     =  data.ResponseDetail.TskId    ;
				persistentData.overallTaskState  =  $scope.addTaskData.TaskType  ;
				console.log(persistentData.overallTaskState);
				$state.go("updateTask");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}



	



})
//查看任务
.controller("updateTaskCtrl",function($scope, $state, $http,  $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.saveBtn = true;
	$scope.cancelBtn = true;
	$scope.sheerDiv = false;
	$scope.backBtn = false;
	$scope.visible = false;

	$scope.updateTaskData = {
		"FarmId"        :  $scope.sparraw_user_temp.farminfo.id  ,
		"showTaskType"  :  ""                                    ,//显示的任务类别

		"TskID"         :  $stateParams.receiveTskID             ,//任务id
		"TaskName"      :  ""                                    ,//任务名称
		"TaskType"      :  ""                                    ,//任务类别01-入雏前准备、02-入雏后日常工作、03-光照程序、04-免疫用药程序、05-其他临时任务
		"AgeInfos"      :  ""                                    ,//任务日期
		"TaskStatus"    :  "Y"                                    //任务状态Y启用、 N停用（任务状态是指饲养员是否可见Y可见、N隐藏）
	}

	//判断是从新建任务进入的，还是从任务列表进入的
	if (persistentData.overallTaskId === "") {
		$scope.TaskType = $stateParams.TaskType;
	}else{
		$scope.updateTaskData.TskID  =  persistentData.overallTaskId     ;
		$scope.TaskType              =  persistentData.overallTaskState  ;
	};

	

	$scope.startEdit = function(){
		$scope.saveBtn = false;
		$scope.cancelBtn = false;
		$scope.sheerDiv = true;
		$scope.backBtn = true;
		$scope.visible = true;
	};
	$scope.cancelEvent = function(){
		$scope.saveBtn = true;
		$scope.cancelBtn = true;
		$scope.sheerDiv = false;
		$scope.backBtn = false;
		$scope.visible = false;
	};

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	switch($scope.TaskType){
				case "01":
				  	$scope.updateTaskData.showTaskType = "入雏前准备";
				  	$scope.updateTaskData.TaskType = "01";
				  break;
				case "02":
				  	$scope.updateTaskData.showTaskType = "入雏后日常工作";
				  	$scope.updateTaskData.TaskType = "02";
				  break;
				case "03":
				  	$scope.updateTaskData.showTaskType = "光照程序";
				  	$scope.updateTaskData.TaskType = "03";
				  break;
				case "04":
				  	$scope.updateTaskData.showTaskType = "免疫用药程序";
				  	$scope.updateTaskData.TaskType = "04";
				  break;
				default:
					$scope.updateTaskData.showTaskType = "其他临时任务";
					$scope.updateTaskData.TaskType = "05";
	};

	//判断是否是入雏前的任务
	if ($scope.updateTaskData.TaskType === "01") {
		$scope.multiSelectAgeInfos = true;
		$scope.radioAgeInfos = false;
		$scope.updateTaskData.AgeInfos = "0";
	}else{
		$scope.multiSelectAgeInfos = false;
		$scope.radioAgeInfos = true;
	};


	$scope.inquire = function(){
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TskId"   :    $scope.updateTaskData.TskID    
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/queryTskDetail.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				$scope.updateTaskData.TaskName = data.ResponseDetail.TaskName;
				$scope.updateTaskData.AgeInfos = data.ResponseDetail.AgeInfos;
				$scope.updateTaskData.TaskStatus = data.ResponseDetail.TaskStatus;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();

	$scope.save = function(){
		$scope.updateTaskData.AgeInfos = $scope.updateTaskData.AgeInfos.trim();
		//判断是否为空
		if ((isNull($scope.updateTaskData.TaskName)) 
			&& (isNull($scope.updateTaskData.AgeInfos)) 
			&& (isNull($scope.updateTaskData.TaskStatus))) {
			
		}else{
			return;
		};
		var tempAgeInfos = "";
		if($scope.updateTaskData.AgeInfos != '**'){
			var ageArrayTemp = $scope.updateTaskData.AgeInfos.split('#');
			for(var t =0;t<ageArrayTemp.length;t++){
				if(!app_IsNum(ageArrayTemp[t].trim())){
					app_alert("日龄设定格式错误，请重新输入。");
					return;
				}else{
					tempAgeInfos +="#" + ageArrayTemp[t].trim();
				}
			}
		}
		$scope.updateTaskData.AgeInfos = tempAgeInfos.substring(1);
		
		var params = {
			"FarmId"  :    $scope.updateTaskData.FarmId      ,
			"TaskName":    $scope.updateTaskData.TaskName    ,
			"TskId"   :    $scope.updateTaskData.TskID       ,
          	"TaskType":    $scope.updateTaskData.TaskType    ,
          	"AgeInfos":    $scope.updateTaskData.AgeInfos    ,
          	"TaskStatus":  $scope.updateTaskData.TaskStatus
		};
		Sparraw.ajaxPost('tsk/TaskTemplate/updateTsk.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("修改任务成功！");
				$scope.cancelEvent();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}








})




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
		$state.go("chickenAssistList");
	}
	
	

})



/*-------newPage-------*/
//批次管理
.controller("newBatchManageCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.batchStatus = {
		"docPlace":"",
		"such":"",
		"settlement":"",
		"batchCode":$scope.sparraw_user_temp.farminfo.farmBreedBatchCode
	}

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

	//判断批次结算
	if ($scope.sparraw_user_temp.farminfo.farmBreedStatus == "02") {
		$scope.batchStatus.settlement = "未结算";
		$scope.batchStatus.docPlace = "未入雏";
		$scope.batchStatus.such = "未出栏";
		$scope.batchStatus.batchCode = "";
	}else{
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus == "02") {
			$scope.batchStatus.settlement = "已结算";
		}else{
			$scope.batchStatus.settlement = "未结算";
		}

		//判断入雏状态
		if (stateA.length == $scope.sparraw_user_temp.userinfo.houses.length) {
			$scope.batchStatus.docPlace = "未入雏";
		}else if (stateA.length == 0) {
			$scope.batchStatus.docPlace = "全部入雏";
		}else if (stateA.length < $scope.sparraw_user_temp.userinfo.houses.length && stateA.length != 0) {
			$scope.batchStatus.docPlace = "部分入雏";
		}
		//判断出栏状态
		console.log("未入雏" + stateA.length);//未入雏
		console.log("已入雏" + stateB.length);//已入雏
		console.log("已出栏" + stateC.length);//已出栏

		if (stateC.length == 0) {
			$scope.batchStatus.such = "未出栏";
		}else if (stateB.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "部分出栏";
		}else if (stateB.length == 0 && stateC.length != 0) {
			$scope.batchStatus.such = "全部出栏";
		}


		/*if (stateA.length == 0 && stateC.length == 0 ||
			stateA.length != 0 && stateB.length != 0 ||
			stateB.length == 0 && stateC.length == 0) {
			$scope.batchStatus.such = "未出栏";
		}else if (stateB.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "部分出栏";
		}else if (stateA.length == 0 && stateB.length == 0  || stateA.length != 0 && stateC.length != 0) {
			$scope.batchStatus.such = "全部出栏";
		}*/
	}
	

	$scope.goBreedAffirm = function(){
		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01" || 
			$scope.sparraw_user_temp.farminfo.farmBreedStatus == "" || 
			!$scope.sparraw_user_temp.farminfo.farmBreedStatus) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			$state.go("breedAffirm");
		}
	}

	

	$scope.goNewProfitReport = function(){
		$state.go("newProfitReport");
	}

	$scope.goMoreBatchClearing = function(){
		$state.go("moreBatchClearing");
	}

	$scope.goMoreBatchProfit = function(){
		$state.go("moreBatchProfit");
	}


	$scope.goBatchClear = function(){

		if ($scope.sparraw_user_temp.Authority.FarmSettle != "No") {
			
		}else{
			return app_alert("该用户无此操作权限。");
		};


		if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "01" || 
			$scope.sparraw_user_temp.farminfo.farmBreedStatus == "" || 
			!$scope.sparraw_user_temp.farminfo.farmBreedStatus) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			$state.go("batchClear");
		}
	}


	$scope.goBenefitsReport = function(){
		if ($scope.sparraw_user_temp.Authority.FarmSettle != "No") {
			
		}else{
			return app_alert("该用户无此操作权限。");
		};

		$state.go("benefitsReport");
	}

	$scope.goCostReport = function(){
		if ($scope.sparraw_user_temp.Authority.FarmSettle != "No") {
			
		}else{
			return app_alert("该用户无此操作权限。");
		};

		$state.go("costReport");
	}

	$scope.goPriceClear = function(){
		if ($scope.sparraw_user_temp.Authority.FarmSettle != "No") {
			
		}else{
			return app_alert("该用户无此操作权限。");
		};

		$state.go("priceClear");
	}

})

//入雏确认
.controller("docPlaceAffirmCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.docPlaceData = {
		"firstTime":false,//判断是否是第一次取值
        "farmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        "houseInfo":[],
        "batchInfo":{
	       "farmId":$scope.sparraw_user_temp.farminfo.id,//int型，农场id√
	       "batchCode":"",//varchar型，批次编号√
	       "batchDate":"",//varchar型，批次日期√
	       "planBreedDays":"",//number型，预计饲养天数√
	       "placeNumSum":"",//number型，总入雏数量
	       "planMarketDate":"",//varchar型，预计出栏日期，YYYY-MM-DD
	       "chickVendor":"",//varchar型，雏源厂家         
	       "chickPrice":""//number型，鸡苗单价
     	}
	}


	var temphouseInfo = [];
	var tempbatchInfo = {};
	//日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();
    $scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '清除', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2014, 5, 1), //Optional
            to: new Date(2018, 7, 1), //Optional
            callback: function (val) { //Optional
                datePickerCallbackPopup(val);
            }
    };

    var selectedItem = {};
    $scope.clickDate = function(item){
		selectedItem = item;
	}
    var datePickerCallbackPopup = function (val) {
        if (typeof(val) === 'undefined') {
            console.log('未选择日期');

            for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
        		if (selectedItem.houseId == $scope.docPlaceData.houseInfo[i].houseId) {
	                //清除入雏日期
	                $scope.docPlaceData.houseInfo[i].placeDate = "";
        		}
        	}

        } else {
        	console.log(val);
        	for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
        		if (selectedItem.houseId == $scope.docPlaceData.houseInfo[i].houseId) {
					//转为字符串并且删除“日”字符串，修改“年”“月”“/”为“-”,并且做补零处理                
	                var TempDate = new Date((val/1000+86400)*1000);
	                var selectDate = TempDate.toISOString();
	                selectDate     = selectDate.substring(0,10);
	                selectDate     = selectDate.replace(/(日)/g,"");
	                selectDate     = selectDate.replace(/(月)/g,"-");
	                selectDate     = selectDate.replace(/(年)/g,"-");
	                selectDate     = selectDate.replace(/\//g,"-");
	                if (selectDate[6] == "-") {
	                	selectDate = selectDate.replace(/(.{5})/g,'$10');
	                };
	                if (selectDate[9]) {

	                }else{
	                	selectDate = selectDate.replace(/(.{8})/g,'$10');
	                };
	                //将得到的日期放入入雏日期
	                $scope.docPlaceData.houseInfo[i].placeDate = selectDate;
	                console.log("-----------------");
	                console.log(selectDate);
	                console.log("-----------------");
        		}
        	}

        }
    };




    $scope.inquire = function(){

    	if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "02") {
    		var params = {
				"farmId":$scope.docPlaceData.batchInfo.farmId,
				"farmBreedId":$scope.docPlaceData.farmBreedId,  
			};
			Sparraw.ajaxPost('farmManage/placeChickQuery.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					$scope.docPlaceData.houseInfo = data.ResponseDetail.houseInfo;
					$scope.docPlaceData.batchInfo = data.ResponseDetail.batchInfo;

					tempbatchInfo = JSON.parse(JSON.stringify(data.ResponseDetail.batchInfo));
					temphouseInfo = JSON.parse(JSON.stringify(data.ResponseDetail.houseInfo));
					
					$scope.docPlaceData.firstTime = false;
				}else if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					$scope.docPlaceData.firstTime = true;
					for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
						$scope.docPlaceData.houseInfo.push({
							"houseId":$scope.sparraw_user_temp.userinfo.houses[i].HouseId,//int型，栋舍id
							"houseName":$scope.sparraw_user_temp.userinfo.houses[i].HouseName,
							"placeNum":"",//int型，入雏数量
							"placeDate":""//varchar型，入雏日期，YYYY-MM-DD
						})
					}

					console.log($scope.docPlaceData.houseInfo);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
    	}else{
    		Sparraw.myNotice("暂无数据请先入雏");
    		$scope.docPlaceData.firstTime = true;
				for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
					$scope.docPlaceData.houseInfo.push({
						"houseId":$scope.sparraw_user_temp.userinfo.houses[i].HouseId,//int型，栋舍id
						"houseName":$scope.sparraw_user_temp.userinfo.houses[i].HouseName,
						"placeNum":"",//int型，入雏数量
						"placeDate":""//varchar型，入雏日期，YYYY-MM-DD
					})
			}
    	}
	}
	$scope.inquire();





	$scope.save = function(){



		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		if ($scope.docPlaceData.batchInfo.planBreedDays > 29 && $scope.docPlaceData.batchInfo.planBreedDays < 131) {

		}else{
			return app_alert("预计饲养天数必须在30天至130天内。");
		}

		if ($scope.docPlaceData.batchInfo.chickPrice > 0 && $scope.docPlaceData.batchInfo.chickPrice <= 9) {

		}else{
			return app_alert("鸡苗单价必须大于0元且不能大于9元。");
		}


		if ($scope.docPlaceData.firstTime == false) {
			if (tempbatchInfo.planBreedDays != $scope.docPlaceData.batchInfo.planBreedDays) {
				return app_alert("预计饲养天数无法修改。");
			}
			var minDate = $scope.docPlaceData.batchInfo.batchDate;
			var TempPlaceDate = [];
			for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
				TempPlaceDate.push($scope.docPlaceData.houseInfo[i].placeDate);
			}
			for (var i = 0; i < TempPlaceDate.length; i++) {
				var oDate1 = new Date(minDate);
			    var oDate2 = new Date(TempPlaceDate[i]);
			    if(oDate1.getTime() > oDate2.getTime()){
			        return app_alert("新入雏栋舍的入雏日期必须大于批次日期。");
			    }else{
			    	
			    }
			}
		}else{
			var TempPlaceDate = [];
			for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
				TempPlaceDate.push($scope.docPlaceData.houseInfo[i].placeDate);
			}
			var TempPlaceDateStr = TempPlaceDate.toString();
			if (TempPlaceDate.length == TempPlaceDateStr.length+1) {
				return app_alert("请输入至少一栋的入雏信息。");
			}
		}

		

		for (var i = 0; i < temphouseInfo.length; i++) {
			if (temphouseInfo[i].placeDate != "" &&
				temphouseInfo[i].placeDate != $scope.docPlaceData.houseInfo[i].placeDate) {
				return app_alert("入雏日期无法修改。");
			}
		}


		

		if ($scope.docPlaceData.batchInfo.chickVendor == "" || !$scope.docPlaceData.batchInfo.chickVendor) {
			return app_alert("请正确输入雏源厂家。");
		}
		if ($scope.docPlaceData.batchInfo.chickPrice == "" || !$scope.docPlaceData.batchInfo.chickPrice) {
			return app_alert("请正确输入鸡苗单价。");
		}
		if ($scope.docPlaceData.batchInfo.planBreedDays == "" || !$scope.docPlaceData.batchInfo.planBreedDays) {
			return app_alert("请正确输入预计饲养天数。");
		}

		for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
			if ($scope.docPlaceData.houseInfo[i].placeDate != "" &&
				$scope.docPlaceData.houseInfo[i].placeNum == "" ||
				$scope.docPlaceData.houseInfo[i].placeDate == "" &&
				$scope.docPlaceData.houseInfo[i].placeNum != "") {
				return app_alert("请完整填写入雏栋舍信息。");
			}

			if ($scope.docPlaceData.houseInfo[i].placeNum == 0 && $scope.docPlaceData.houseInfo[i].placeDate != "") {
				return app_alert("进鸡数量不允许为0。");
			}
		}

		app_confirm('保存后栋舍入雏日期无法修改，是否要进行保存？',null,null,function(buttonIndex){
			if(buttonIndex == 1){
				// app_alert('您选择了【取消】');
			}else if(buttonIndex == 2){
				// app_alert('您选择了【确定】');
				//删除空数据
				var TempHouseInfo = [];
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					if ($scope.docPlaceData.houseInfo[i].placeDate == "" && $scope.docPlaceData.houseInfo[i].placeNum == 0) {
						//$scope.docPlaceData.houseInfo.splice(i);
					}else{
						TempHouseInfo.push($scope.docPlaceData.houseInfo[i]);
					}
				}
				$scope.docPlaceData.houseInfo = TempHouseInfo;

				//获取所有栋舍日期
				var dateArr = [];
				//获取所有栋舍入雏数
				var chickArr = [];
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					dateArr.push($scope.docPlaceData.houseInfo[i].placeDate);
					chickArr.push($scope.docPlaceData.houseInfo[i].placeNum);
				}
				//判断日期大小
				var minDate = dateArr[0];
		    	for (var i = 0; i < dateArr.length; i++) {
		    		var oDate1 = new Date(minDate);
				    var oDate2 = new Date(dateArr[i]);
				    if(oDate1.getTime() > oDate2.getTime()){
				        minDate = dateArr[i];
				    }
		    	}

		    	if ($scope.docPlaceData.firstTime == true) {
					//获取批次日期
			    	$scope.docPlaceData.batchInfo.batchDate = minDate;
			    	//获取批次编号
					$scope.docPlaceData.batchInfo.batchCode = minDate.replace(/-/g,"");
					$scope.docPlaceData.batchInfo.batchCode = $scope.docPlaceData.batchInfo.batchCode.substring(2,$scope.docPlaceData.batchInfo.batchCode.length);
					console.log($scope.docPlaceData.batchInfo.batchCode);
					//获取预计出栏日
					var TempOverDate = new Date(minDate);
					var TempDate = TempOverDate.getTime() + $scope.docPlaceData.batchInfo.planBreedDays  * 24 * 60 * 60 * 1000
					TempDate = new Date(TempDate);
					
			        var OverDate = TempDate.toLocaleDateString();
	                OverDate     = OverDate.replace(/(日)/g,"");
	                OverDate     = OverDate.replace(/(月)/g,"-");
	                OverDate     = OverDate.replace(/(年)/g,"-");
	                OverDate     = OverDate.replace(/\//g,"-");
	                if (OverDate[6] == "-") {
	                	OverDate = OverDate.replace(/(.{5})/g,'$10');
	                };
	                if (OverDate[9]) {

	                }else{
	                	OverDate = OverDate.replace(/(.{8})/g,'$10');
	                };
					$scope.docPlaceData.batchInfo.planMarketDate = OverDate;
				}else{
					
				}
				//进鸡数转换为Number
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					$scope.docPlaceData.houseInfo[i].placeNum = Number($scope.docPlaceData.houseInfo[i].placeNum);
				}
				//获取进鸡总数
				$scope.docPlaceData.batchInfo.placeNumSum = 0;
				for (var i = 0; i < chickArr.length; i++) {
					$scope.docPlaceData.batchInfo.placeNumSum += Number(chickArr[i]);
				}
				//添加农场id和批次id
				$scope.docPlaceData.batchInfo.farmId = $scope.sparraw_user_temp.farminfo.id;
				$scope.docPlaceData.batchInfo.farmBreedId = $scope.sparraw_user_temp.farminfo.farmBreedBatchId;


				console.log("-------------------------");
				console.log($scope.sparraw_user_temp.farminfo.farmBreedStatus);
				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "02") {
					var params = {
						"batchInfo"   :  $scope.docPlaceData.batchInfo,
						"houseInfo"   :  $scope.docPlaceData.houseInfo
					};
					Sparraw.ajaxPost('farmManage/placeChickDeal.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							console.log(data);
							Sparraw.myNotice("保存成功");
							//重新获取服务器最新数据
	    					Sparraw.getLatestData($state,"newBatchManage");
						}else if (data.ResponseDetail.Result == "Fail") {
							//Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
							app_alert(data.ResponseDetail.ErrorMsg);
							$scope.inquire();
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}else{

					$scope.docPlaceData.batchInfo.farmBreedId = 0;
					var params = {
						"batchInfo"   :  $scope.docPlaceData.batchInfo,
						"houseInfo"   :  $scope.docPlaceData.houseInfo
					};
					Sparraw.ajaxPost('farmManage/placeChickDeal.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							console.log(data);
							Sparraw.myNotice("保存成功");
							//重新获取服务器最新数据
	    					Sparraw.getLatestData($state,"newBatchManage");
						}else if (data.ResponseDetail.Result == "Fail") {
							//Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
							app_alert(data.ResponseDetail.ErrorMsg);
							$scope.inquire();
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}
			}
		});

		
	}

	$scope.judgeBreedDays = function(){
		if ($scope.docPlaceData.batchInfo.planBreedDays > 29 && $scope.docPlaceData.batchInfo.planBreedDays < 131) {

		}else{
			Sparraw.myNotice("预计饲养天数必须在30天至130天内。");
		}
	}

	$scope.judgeChickPrice = function(){
		if ($scope.docPlaceData.batchInfo.chickPrice > 0 && $scope.docPlaceData.batchInfo.chickPrice <= 9) {

		}else{
			Sparraw.myNotice("鸡苗单价必须大于0元且不能大于9元。");
		}
	}


})
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





    //日期选择器
	$scope.dateModel = new Date("08-14-2015");
    $scope.datePopup = new Date("08-14-2015");
    var disabledDates = [
        new Date(1437719836326),
        new Date(2015, 7, 10), //months are 0-based, this is August, 10th!
        new Date('Wednesday, August 12, 2015'), //Works with any valid Date formats like long format
        new Date("08-14-2015"), //Short format
        new Date(1439676000000) //UNIX format
    ];
    var monthList = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
    var weekDaysList = ["日", "一", "二", "三", "四", "五", "六"];
    $scope.datepickerObject = {};
    $scope.datepickerObject.inputDate = new Date();
    $scope.datepickerObjectPopup = {
            titleLabel: '选择日期', //Optional
            todayLabel: '今天', //Optional
            closeLabel: '清除', //Optional
            setLabel: '选择', //Optional
            errorMsgLabel : '请选择时间.', //Optional
            setButtonType : 'button-assertive', //Optional
            modalHeaderColor:'bar-positive', //Optional
            modalFooterColor:'bar-positive', //Optional
            templateType:'popup', //Optional
            inputDate: $scope.datepickerObject.inputDate, //Optional
            //mondayFirst: true, //Optional
            sundayFirst: true, //Optional
            disabledDates:disabledDates, //Optional
            monthList:monthList, //Optional
            weekDaysList: weekDaysList,
            from: new Date(2014, 5, 1), //Optional
            to: new Date(2018, 7, 1), //Optional
            callback: function (val) { //Optional
                datePickerCallbackPopup(val);
            }
    };


    var selectedItem = {};
    $scope.clickDate = function(item){
		selectedItem = item;
	}
    var datePickerCallbackPopup = function (val) {
        if (typeof(val) === 'undefined') {
            console.log('未选择日期');
            for (var i = 0; i < $scope.breeAffiData.settleHouse.length; i++) {
	        		if (selectedItem.houseId == $scope.breeAffiData.settleHouse[i].houseId) {
		                //清除出栏日期
		                $scope.breeAffiData.settleHouse[i].marketDate = "";
	        		}
	        	}
        } else {
        	console.log(val);

        	if (selectedItem.breedStatus == "02") {	
	    		return app_alert("不允许修改日期！");
	    	}else{
	    		for (var i = 0; i < $scope.breeAffiData.settleHouse.length; i++) {
	        		if (selectedItem.houseId == $scope.breeAffiData.settleHouse[i].houseId) {
						//转为字符串并且删除“日”字符串，修改“年”“月”“/”为“-”,并且做补零处理                
		                var TempDate = new Date((val/1000+86400)*1000);
	                	var selectDate = TempDate.toISOString();
	                	selectDate     = selectDate.substring(0,10);
		                selectDate     = selectDate.replace(/(日)/g,"");
		                selectDate     = selectDate.replace(/(月)/g,"-");
		                selectDate     = selectDate.replace(/(年)/g,"-");
		                selectDate     = selectDate.replace(/\//g,"-");
		                if (selectDate[6] == "-") {
		                	selectDate = selectDate.replace(/(.{5})/g,'$10');
		                };
		                if (selectDate[9]) {

		                }else{
		                	selectDate = selectDate.replace(/(.{8})/g,'$10');
		                };
		                //将得到的日期放入入雏日期
		                console.log(selectDate);
		                $scope.breeAffiData.settleHouse[i].marketDate = selectDate;
	        		}
	        	}	
	    	}
        }
    };


    $scope.getMarketAvg = function(item){

    	var TempAvgWeight = parseFloat(item.marketWeight / item.marketNum);
    	item.marketAvgWeight = TempAvgWeight.toFixed(2);

    	if (!app_IsNum(item.marketAvgWeight) || !isFinite(item.marketAvgWeight)) {
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
    					Sparraw.getLatestData(null,null);
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
				Sparraw.getLatestData(null,null);
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

    	if (!app_IsNum($scope.breeAffiData.SettleFarm.SalePrice) || !isFinite($scope.breeAffiData.SettleFarm.SalePrice)) {
			$scope.breeAffiData.SettleFarm.SalePrice = 0;
		}else{

		}


		/*if ($scope.breeAffiData.SettleFarm.SalePrice >= 5 && $scope.breeAffiData.SettleFarm.SalePrice <= 15) {

		}else{
			Sparraw.myNotice("结算单价不得低于5且不能大于15");
		}*/

    }

})
//批次结算
.controller("batchClearCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicBackdrop,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.batchClearData = {
		"FarmId"          :  $scope.sparraw_user_temp.farminfo.id,
		"BreedBatchId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"BatchStatus"     :  ""  ,
        "FeedMsg":{
            "VenderName"  :  ""  ,
            "FeedInfo":[{
                  "FeedCode"   :  ""  ,
                  "FeedName"   :  ""  ,
                  "Weight"     :  ""  ,
                  "Price_p"    :  ""  ,
                  "Price_sum"  :  ""  
            }],
            "sys_amount":"",

			"total_Weight"      :  0  ,  //合计的 公斤数
			"total_Price_p"     :  0  ,  //合计的 单价
			"total_Price_sum"   :  0    //合计的 总金额
        },
        "OtherMsg":{
        	"ChickenManure"  :  ""  ,   //--鸡粪收入
            "VaccineFee"     :  ""  ,   //--药品疫苗费
            "CatcherFee"     :  ""  ,   //--抓鸡费
            "PaddingFee"     :  ""  ,   // --垫料费
            "ManualFee"      :  ""  ,   //--人工费
            "FuelFee"        :  ""  ,   //--燃料费
            "UtilityFee"     :  ""  ,   //--水电费
            "MaintainFee"    :  ""  ,   //--维修费
            "QuarantineFee"  :  ""  ,   //--检疫费
            "RentFee"        :  ""  ,   //--租金
            "InterestFee"    :  ""  ,   //--利息

            "OtherFee"       :  ""  ,   //折旧费
            "DeprFee"        :  ""  ,   //杂费
			"ServiceFee"     :  ""     //服务费
        },
        "OtherFeeInputType":"0",
        "TempTotal":""
	}

	//饲料结算
	//计算饲料结算的金额
	$scope.forageTotal = function(){
		var total_Weight_temp = 0;
		var total_Price_sum_temp = 0;
		var total_Price_p_temp = 0;

		for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
			//  计算 单个饲料总金额
			$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = 
					($scope.batchClearData.FeedMsg.FeedInfo[i].Weight * $scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			if (!app_IsNum($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum)) {
				$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = 0;
			}else{
				$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = $scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum.toFixed(0); 
			}
			//  计算 合计-公斤数
			if(app_IsNum($scope.batchClearData.FeedMsg.FeedInfo[i].Weight)){
				total_Weight_temp += parseFloat($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			}
			//  计算 合计-总金额
			if(app_IsNum($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum)){
				total_Price_sum_temp += parseFloat($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);
			}
		};
		//  计算 合计-单价数
		total_Price_p_temp = total_Price_sum_temp/total_Weight_temp ;
		if(!app_IsNum(total_Price_p_temp)){
			total_Price_p_temp = 0;
		}else{
			total_Price_p_temp = Math.round(total_Price_p_temp*100)/100;  
		}
		$scope.batchClearData.FeedMsg.total_Weight = total_Weight_temp;
		$scope.batchClearData.FeedMsg.total_Price_sum = total_Price_sum_temp;
		$scope.batchClearData.FeedMsg.total_Price_p = total_Price_p_temp;
	};




	$scope.inquire = function(){
		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{

		}

		var params = {
       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId
		};
		Sparraw.ajaxPost('farmManage/settleBatchQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.batchClearData.FeedMsg.VenderName = data.ResponseDetail.FeedMsg.VenderName;
				$scope.batchClearData.FeedMsg.FeedInfo = data.ResponseDetail.FeedMsg.FeedInfo;
				$scope.batchClearData.FeedMsg.sys_amount = data.ResponseDetail.FeedMsg.sys_amount;
				$scope.batchClearData.OtherMsg = data.ResponseDetail.OtherMsg;
				$scope.batchClearData.OtherFeeInputType = data.ResponseDetail.OtherFeeInputType;



				//$scope.chooseWay();
				for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
					$scope.batchClearData.FeedMsg.FeedInfo[i].Id = i;
				}
				//计算第一个表格
				$scope.forageTotal();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.inquire();
	

	$scope.submitLimit = function(){
		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			app_alert("该用户无此操作权限。");
			return false;
		};

		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			app_alert("该农场无入雏信息，请先入雏。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[0].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[0].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[1].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[1].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[2].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[2].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[3].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[3].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[4].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[4].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}
		

		if ($scope.batchClearData.FeedMsg.FeedInfo[0].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[1].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[2].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[3].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[4].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}



		return true;
	}

	$scope.saveFun = function(){
		if ($scope.submitLimit()) {

		}else{
			return;
		}

		

		var params = {
       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId        ,
        	"FarmId"        :  $scope.batchClearData.FarmId              ,
        	"FeedMsg":{
              "VenderName"  :  $scope.batchClearData.FeedMsg.VenderName  ,
              "FeedInfo"    :  $scope.batchClearData.FeedMsg.FeedInfo    ,
              "sys_amount"  :  $scope.batchClearData.FeedMsg.sys_amount
            },
        	"OtherMsg"      :  $scope.batchClearData.OtherMsg,
        	"OtherFeeInputType" : $scope.batchClearData.OtherFeeInputType
    	}

		Sparraw.ajaxPost('farmManage/settleBatchSave.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("保存成功。");
				selectBackPage.profitReportBack = 'batchClearPage';
				$state.go("newProfitReport");
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.clearingFun = function(){
		if ($scope.submitLimit()) {

		}else{
			return;
		}

		

		//判断结算方式
		if ($scope.batchClearData.OtherFeeInputType != "01") {
			//饲料厂家不为空，饲料总量不为0，药品疫苗不为空
			if ($scope.batchClearData.FeedMsg.VenderName == "" || !$scope.batchClearData.FeedMsg.VenderName) {
				return app_alert("请输入饲料厂家。");
			}
			if ($scope.batchClearData.OtherMsg.VaccineFee == "" || !$scope.batchClearData.OtherMsg.VaccineFee) {
				return app_alert("请输入药品疫苗费。");
			}
			if ($scope.batchClearData.OtherMsg.ManualFee == "" || !$scope.batchClearData.OtherMsg.ManualFee) {
				return app_alert("请输入人工费。");
			}
		}else{
			
		}
		

		var nullArr = [];
		var allArr = [];
		for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Weight == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Weight == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			}
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Price_p == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			}
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);
			}

			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);

		}
		if (nullArr.length == allArr.length) {
			return app_alert("饲料不能为空。");
		}else{
			
		}

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



		//判断批次结算
		if (stateC.length == 0) {return app_alert("出栏后才能进行结算。");};

		
		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			app_confirm('结算后该批次数据无法修改,是否要进行结算？',null,null,function(buttonIndex){
				if(buttonIndex == 1){

				}else if(buttonIndex == 2){
					//测算
					var params = {
			       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId        ,
			        	"FarmId"        :  $scope.batchClearData.FarmId              ,
			        	"FeedMsg":{
			              "VenderName"  :  $scope.batchClearData.FeedMsg.VenderName  ,
			              "FeedInfo"    :  $scope.batchClearData.FeedMsg.FeedInfo    ,
			              "sys_amount"  :  $scope.batchClearData.FeedMsg.sys_amount
			            },
			        	"OtherMsg"      :  $scope.batchClearData.OtherMsg            ,
			        	"OtherFeeInputType" : $scope.batchClearData.OtherFeeInputType
			    	}
					Sparraw.ajaxPost('farmManage/settleBatchSave.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							//结算
							var params = {
								"BreedBatchId":$scope.batchClearData.BreedBatchId
							};
							Sparraw.ajaxPost('farmManage/settleBatchConfirm.action', params, function(data){
								if (data.ResponseDetail.Result == "Success") {
									$ionicBackdrop.release();
									Sparraw.myNotice("结算成功！");
									//重新获取服务器最新数据
			    					Sparraw.getLatestData($state,"newBatchManage");
								}else if (data.ResponseDetail.Result == "Fail") {
									$ionicBackdrop.release();
									Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
								}else{
									$ionicBackdrop.release();
									Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
								};
							});
						}else if (data.ResponseDetail.Result == "Fail") {
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}
			});	
		}
	}


	/*$scope.chooseWay = function(){
		console.log($scope.batchClearData.OtherFeeInputType);

		if ($scope.batchClearData.OtherFeeInputType == "01") {
			$scope.detailDiv = false;
			$scope.totalDiv = true;
		}else if ($scope.batchClearData.OtherFeeInputType == "02") {
			$scope.detailDiv = true;
			$scope.totalDiv = false;
		}
	}*/



	$scope.judgePrice = function(item){
		if (item.Price_p <= 5) {

		}else{
			Sparraw.myNotice("饲料单价不能大于5元。");
		}
	}
})

//价格结算
.controller("priceClearCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicBackdrop,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));




	$scope.priceClearData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				// "ViewUnit"      : "万元"                                               ,
				// "transferUnit"  : "Money"                                             ,
				"BatchDatas":{},
		        "ChickPrice":[],
		        "ChickenPrice":[],
		        "FeedPrice":[]
    }
	




	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.priceClearData.FarmId        
		};
		Sparraw.ajaxPost('farmManage/getSettlePriceRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.priceClearData.BatchDatas = data.ResponseDetail.BatchDatas;
				$scope.priceClearData.FeedPrice = data.ResponseDetail.FeedPrice;
				$scope.priceClearData.ChickPrice = data.ResponseDetail.ChickPrice;
				$scope.priceClearData.ChickenPrice = data.ResponseDetail.ChickenPrice;
				// console.log($scope.priceClearData);
				// console.log($scope.priceClearData.ChickPrice);
				// console.log($scope.priceClearData.ChickenPrice);
				console.log(data.ResponseDetail.ChickPrice);
				console.log(data.ResponseDetail.ChickenPrice);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	

/*
	$scope.judgeBackground = function(item){
		if (item.ItemName == "批次") {
			return "{background:'#44CA65',color:'#FFF',border:'solid 1px #FFF'}";
		}else{
			return "{background:'#FFF',color:'black'}";
		}
	}*/




})


//盈利报告 
.controller("newProfitReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.profitData = {
		"FarmId"          :  $scope.sparraw_user_temp.farminfo.id,
		"BreedBatchId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"OverView":[],
        "feeDetail":[]
	}
	$scope.profitLoss = "";




	$scope.inquire = function(){
		/*if ($scope.profitData.BreedBatchId == "" || !$scope.profitData.BreedBatchId) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{

		}*/

		var params = {
			"FarmId"        :  $scope.profitData.FarmId,
       		"BreedBatchId"  :  $scope.profitData.BreedBatchId
		};
		Sparraw.ajaxPost('farmManage/getProfitRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.profitData.OverView = data.ResponseDetail.OverView;
				$scope.profitData.feeDetail = data.ResponseDetail.feeDetail;

				/*for (var i = 0; i < $scope.profitData.feeDetail.length; i++) {
					if ($scope.profitData.feeDetail[i].ItemName == "盈(亏)") {
						$scope.profitLoss = $scope.profitData.feeDetail[i];
					}
				}
				$scope.profitData.feeDetail.pop();*/
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.inquire();


	$scope.judgeTitle = function(item){
		if (item.ItemName == "盈(亏)") {
			return "{'font-weight': 'bold'}";
		}else{

		}
	}



	console.log(selectBackPage.profitReportBack);
	$scope.backFun = function(){
		if (selectBackPage.profitReportBack == 'batchClearPage') {
			$state.go("batchClear");
			selectBackPage.profitReportBack = '';
		}else{
			$state.go("newBatchManage");
		}
	}


})

//效益报告 
.controller("benefitsReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.beneRepoData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,

				"OverView": [{
		            "ItemName"  : "批次号"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "天数"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "出栏"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "存活%"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "均重"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "料/肉"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "欧指"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        }],
				"otherFees": [{
		            "ItemName"  : "毛鸡"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "鸡苗"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "饲料"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "药费"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "杂费"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        },{
		            "ItemName"  : "盈亏"  ,
		            "index1"    : "-"  ,
		            "index2"    : "-"  ,
		            "index3"    : "-"  ,
		            "index4"    : "-"  ,
		            "index5"    : "-"  
		        }]
	}




	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.beneRepoData.FarmId        ,
			"ViewUnit"       :    $scope.beneRepoData.transferUnit    
		};
		Sparraw.ajaxPost('farmManage/getBenefitRep.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.beneRepoData.OverView = data.ResponseDetail.OverView;
				$scope.beneRepoData.otherFees = data.ResponseDetail.otherFees;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.inquireMultiProfit = function(){
		$scope.inquire();
	}



	$scope.judgeBackground = function(item){
		if (item.ItemName == "批次") {
			return "{background:'#44CA65',color:'#FFF',border:'solid 1px #FFF'}";
		}else{
			return "{background:'#FFF',color:'black'}";
		}
	}








})

//成本报告 
.controller("costReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	$scope.costRepoData = {
				"FarmId"        : $scope.sparraw_user_temp.farminfo.id                ,
				"BreedBatchId"  : $scope.sparraw_user_temp.farminfo.farmBreedBatchId  ,
				"ViewUnit"      : "万元"                                               ,
				"transferUnit"  : "Money"                                             ,

				"amountCost":[],

				"otherFees": [],

		        "chickShitMoney":[]
	}



	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.costRepoData.FarmId        ,
			"ViewUnit"       :    $scope.costRepoData.transferUnit    
		};
		Sparraw.ajaxPost('farmManage/getCostsRep.action', params, function(data){
			var index1 = [];
			var index2 = [];
			var index3 = [];
			var index4 = [];
			var index5 = [];

			var index1Sun = 0;
			var index2Sun = 0;
			var index3Sun = 0;
			var index4Sun = 0;
			var index5Sun = 0;

			if (data.ResponseDetail.Result == "Success") {
				$scope.costRepoData.otherFees = data.ResponseDetail.otherFees;
				for (var i = 0; i < $scope.costRepoData.otherFees.length; i++) {
					if ($scope.costRepoData.otherFees[i].ItemName == "批次") {
						$scope.costRepoData.batchTitle = $scope.costRepoData.otherFees[i];
					}else{

					}
				}

				Array.prototype.elremove = function(m){  
			　　      if(isNaN(m)||m>this.length){return false;}  
			　　      this.splice(m,1);  
			　   } 
			　   $scope.costRepoData.otherFees.elremove(0);
				for (var i = 0; i < $scope.costRepoData.otherFees.length; i++) {
					if ($scope.costRepoData.otherFees[i].index1 != "-") {
						index1.push($scope.costRepoData.otherFees[i].index1);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index2 != "-") {
						index2.push($scope.costRepoData.otherFees[i].index2);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index3 != "-") {
						index3.push($scope.costRepoData.otherFees[i].index3);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index4 != "-") {
						index4.push($scope.costRepoData.otherFees[i].index4);
					}else{

					}
					if ($scope.costRepoData.otherFees[i].index5 != "-") {
						index5.push($scope.costRepoData.otherFees[i].index5);
					}else{

					}


					if ($scope.costRepoData.otherFees[i].ItemName == "鸡粪收入") {
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index1);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index2);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index3);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index4);
						$scope.costRepoData.chickShitMoney.push($scope.costRepoData.otherFees[i].index5);
					}else{

					}
				}

				for (var i = 0; i < index1.length; i++) {

					index1Sun += Number(index1[i]);
				}
				for (var i = 0; i < index2.length; i++) {
					index2Sun += Number(index2[i]);
				}
				for (var i = 0; i < index3.length; i++) {
					index3Sun += Number(index3[i]);
				}
				for (var i = 0; i < index4.length; i++) {
					index4Sun += Number(index4[i]);
				}
				for (var i = 0; i < index5.length; i++) {
					index5Sun += Number(index5[i]);
				}


				$scope.costRepoData.amountCost = [
						Number(index1Sun).toFixed(2),
						Number(index2Sun).toFixed(2),
						Number(index3Sun).toFixed(2),
						Number(index4Sun).toFixed(2),
						Number(index5Sun).toFixed(2)
				];


				for (var i = 0; i < $scope.costRepoData.amountCost.length; i++) {
					if ($scope.costRepoData.amountCost[i] == 0.00) {
						$scope.costRepoData.amountCost[i] = "-";
					}
				}
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();


	$scope.inquireMultiProfit = function(){
		$scope.inquire();
	}


	
})

//历史价格

//历史盈利

//养鸡助手列表
.controller("chickenAssistListCtrl",function($scope, $state,$ionicPopup,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.goApparentTempCalc = function(){
		$state.go("apparentTempCalc");
	}

	$scope.goProdPerfStanTable = function(){
		Sparraw.getInfoStatus($ionicPopup,$state,"prodPerfStanTable");
	}
	
	$scope.goChickenWechat = function(){
		$state.go("chickenWechat");
	}

	$scope.goSyncProject = function(){
		//$state.go("prodPerfStanTable");
		app_alert("历史数据达到5批次后，才能使用同行对标功能")
	}
	
	$scope.goSimulateCalc = function(){
		$state.go("simulateCalc");
	}

	$scope.goTaskRemind = function(){
		pointDevelop();
	}
})

// 周体重
.controller("weekWeightCtrl",function($scope, $state,$ionicLoading, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
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
							getLineChart($scope.cullDeathRateData.xData,
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
							getLineChart($scope.cullDeathRateData.xData,
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
	
}
)



//生产指标汇总报表
.controller("productionSumReportCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	
	
	setLandscape(true,true);

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
		"selectedHouse": 0,
		"containBatchHouse":[]
    };

	//设置栋舍号列表信息
	for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
		$scope.cullDeathRateData.containBatchHouse.push($scope.sparraw_user_temp.houseinfos[i]);
	};
	console.log($scope.cullDeathRateData.containBatchHouse);
	$scope.cullDeathRateData.compareType = "01";
	$scope.cullDeathRateData.selectedHouse = JSON.stringify($scope.cullDeathRateData.containBatchHouse[0].id);
		

	$scope.GainBatch = function(){
		
		
		$scope.batchDiv = true;
		$scope.housesDiv = false;
		
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
	              // return $scope.changes1();
				  
				$scope.inquire();
				  
			}else{
				    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		
	};
	
	$scope.GainBatch();
	
	
    //切换筛选方式
    $scope.changesCompareType = function(){

		if ($scope.cullDeathRateData.compareType == '01') {
			//批次
			$scope.batchDiv = true;
			$scope.housesDiv = false;
	        document.getElementById("headName").innerHTML  = "栋舍号";
		}else{
			//栋舍
			$scope.batchDiv = false;
			$scope.housesDiv = true;
			document.getElementById("headName").innerHTML  = "批次号";
		};
		
		$scope.inquire()

	};
    //切换农场批次号
    $scope.changesFarmId = function(){
		//$scope.cullDeathRateData.compareType = '01';
    	$scope.inquire()
    }
    //切换栋舍
    $scope.changesHousesId = function(){
		//$scope.cullDeathRateData.compareType = '02';
    	//console.log($scope.cullDeathRateData.selectedHouse);
		$scope.inquire()
    }
	
	$scope.profitData = {
		"OverView":[]
	}

	
	$scope.forageTotal = function(){
		
	}
	$scope.inquire = function(){
		console.log($scope.cullDeathRateData.selectedHouse);
		var params = {
			"compareType":$scope.cullDeathRateData.compareType,
       		"BreedBatchId"  :  $scope.cullDeathRateData.selectedBatch,
			"HouseId"  :  $scope.cullDeathRateData.selectedHouse
		};
		Sparraw.ajaxPost('farmManage/productionSumReport.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.profitData.OverView = data.ResponseDetail.OverView;
				console.log($scope.profitData.OverView);
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	
	

})

// 鸡场微信
.controller("chickenWechatCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

})



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
	
	deleteObjectFromLocalStorage("simulateDataStand");
	deleteObjectFromLocalStorage("simulateDataDIY");
	
	var simulateDataStand = getObjectFromLocalStorage("simulateDataStand_" + userId);
	//console.log("simulateDataStand:" + JSON.stringify(simulateDataStand))
	if(simulateDataStand == undefined || simulateDataStand == null){
		simulateDataStand = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
		saveObjectToLocalStorage("simulateDataStand",JSON.stringify(simulateDataStand))
	}else{
		simulateDataStand = JSON.parse(simulateDataStand);
	}
	
	var simulateDataDIY = getObjectFromLocalStorage("simulateDataDIY_" + userId);
	if(simulateDataDIY == undefined || simulateDataDIY == null){
		simulateDataDIY = {"inputChickens":0,"outputChickens":0,"feedDays":0,"survival":0,"weight":0,"fcr":0,"chickPrice":0,"smallChickPrice":0,"feedPrice":0,"medicineMoney":0,"manualMoney":0,"othersMoney":0,"ouzhi":0};
		saveObjectToLocalStorage("simulateDataDIY",JSON.stringify(simulateDataDIY))
	}else{
		simulateDataDIY = JSON.parse(simulateDataDIY);
	}

	var simulateDataProperty = {"inputChickens":"进鸡数","feedDays":"饲养天数","outputChickens":"出栏数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛    鸡","smallChickPrice":"鸡    苗","feedPrice":"饲    料","medicineMoney":"药费","manualMoney":"人工费","othersMoney":"其他费用","makeMoney":"盈 / 亏","ouzhi":"欧指"};
	//{"inputChickens":"进鸡数","outputChickens":"出栏数","feedDays":"饲养天数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛鸡价","smallChickPrice":"鸡苗价","feedPrice":"饲料价","chickShitMoney":"鸡粪收入","medicineMoney":"药品疫苗","catchChickMoney":"抓鸡费","paddingMoney":"垫料费","manualMoney":"人工费","heatingMoney":"取暖费","utilityMoney":"水电费","maintainMoney":"维修费","quarantineMoney":"检疫费","rentMoney":"租金","interestMoney":"利息","othersMoney":"杂费","makeMoney":"盈/亏"};
		
	saveObjectToLocalStorage("simulateDataProperty_" + userId,JSON.stringify(simulateDataProperty))
	
	
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
			saveObjectToLocalStorage("simulateDataStand_" + userId,JSON.stringify(simulateDataStand));
			
		}else if($scope.simulateData.simulateType == 2){
			simulateDataDIY = $scope.simulateData.simulateDataTemp;
			
			saveObjectToLocalStorage("simulateDataDIY_" + userId,JSON.stringify(simulateDataDIY))
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

// 数据对比
.controller("comparisonResCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var userId = ($scope.sparraw_user_temp.profile.id_spa);
	var simulateDataStand = getObjectFromLocalStorage("simulateDataStand_" + userId);
	var simulateDataDIY = getObjectFromLocalStorage("simulateDataDIY_" + userId);
	var simulateDataProperty = getObjectFromLocalStorage("simulateDataProperty_" + userId);

	$scope.transferUnit = "Money";
	$scope.ViewUnit = "万元";
	
	if(simulateDataProperty == undefined || simulateDataProperty == null){
		simulateDataProperty = {"feedDays":"饲养天数","outputChickens":"出栏数","survival":"成活率","weight":"只均重","fcr":"料肉比","chickPrice":"毛    鸡","smallChickPrice":"鸡    苗","feedPrice":"饲    料","medicineMoney":"药费","manualMoney":"人工费","othersMoney":"其他费用","makeMoney":"盈/亏","ouzhi":"欧指"};
		
		saveObjectToLocalStorage("simulateDataProperty",JSON.stringify(simulateDataProperty))
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

// Cordova测试
.controller("alarmTestCtrl",function($scope, $state,$ionicModal,$ionicLoading) {
	/*var tNotificationID = null;
	$scope.add = function() {
		tNotificationID = APP_Notification.addOneNotification("alarm","title1","text1");
    };
	$scope.update = function() {
        APP_Notification.updateNotification(tNotificationID,'titleUpdate','textUpdate');
    };
    $scope.cancle = function() {
        APP_Notification.cancleNotification(tNotificationID);
    };
	
	var src = "sounds/4611.wav";
	$scope.playd = function() {
	   APP_Media.stopAudio();
	   var realPath = getMediaURL(src);
	   APP_Media.playAudio(realPath);
    }

	$scope.stopd = function() {
	   APP_Media.stopAudio();
    }
	
	$scope.alarm = function(seconds) {
		setTimeout(function(){
			$scope.playd();
			$scope.add();
		}, 1000*seconds);
    }
	
    $scope.deadline = function() {
		var options = {
		  date: new Date(),
		  mode: 'date'
		};
		datePicker.show(options, function(d) {
			 $scope.display = d;
		});
	}

	$scope.detectNet = function() {
		if(isOnLine_Wifi()){
			app_alert('wifi');
		}else if(isOnLine()){
			app_alert('isOnLine');
		}else{
			app_alert('isOffLine');
		}
	}

	$scope.vibrate = function(seconds) {
		setTimeout(function(){
			app_vibrate([3000,1000,3000,1000,3000]);
		}, 1000*seconds);
	};

	$scope.vibrate2 = function() {
		app_vibrate([1000]);	
	};

	$scope.alertClick = function() {
		app_alert('欢迎使用alert对话框',null,null,function(){
			alert("alert完毕。");
		});	
	};
	$scope.confirmClick = function() {
		app_confirm('需要使用Confirm吗？',null,null,function(buttonIndex){
			if(buttonIndex == 1){
				app_alert('您选择了【取消】');
			}else if(buttonIndex == 2){
				app_alert('您选择了【确定】');
			}
		});	
	};
	$scope.promptClick = function() {
		app_prompt('请输入您的银行卡密码',null,null,function(results){
			app_alert(JSON.stringify(results));
			if(results.buttonIndex == 1){
				app_alert('您选择了【取消】,值:' + results.input1);
			}else if(results.buttonIndex == 2){
				app_alert('您选择了【确定】,值:' + results.input1);
			}
		},'1234567');
	};

	Sparraw.intoMyController($scope, $state);
	$scope.times = [] ;
	$scope.results = '' ;
	var taskId = null;
	$scope.startTask = function(){
		taskId = setInterval(function(){
			var params = {
		      'operation'      : 'needAlarm'
		    };
		    
		    Sparraw.ajaxPost('envCtrl/needAlarm.action', params, function(data){ 
				$scope.results = data.ResponseDetail.AlarmStatus;
				var myDate = new Date();
				var mytime=myDate.toLocaleTimeString();
				$scope.times.push(mytime);
		    });
		}, 3000);
		
	}

	$scope.Wakelock = function(){
		window.powerManagement.acquire(function() {
		    app_alert('Wakelock acquired');
		}, function() {
		    app_alert('Failed to acquire wakelock');
		});
	};
	$scope.dim = function(){
		window.powerManagement.dim(function() {
		    app_alert('Wakelock dim acquired');
		}, function() {
		    app_alert('Failed to acquire dim wakelock');
		});
	};
	$scope.release = function(){
		window.powerManagement.release(function() {
		    app_alert('Wakelock released');
		}, function() {
		    app_alert('Failed to release wakelock');
		});
	};
	$scope.setReleaseOnPause = function(){
		window.powerManagement.setReleaseOnPause(false, function() {
		    app_alert('Set successfully');
		}, function() {
		    app_alert('Failed to set');
		});
	};
	$scope.openOldApp =function (){
		var iframe = document.createElement("iframe");
	    iframe.style.cssText = "display:none;width:0px;height:0px;";
	    document.body.appendChild(iframe);
	    iframe.src="smtc999://sparrowOld";
	    alert(tttt);
	}*/

/*
	var params = {
			"FarmId"  :    274      ,
			"DetailData":    [
			{"growth_age":47,
             "acc_cdreate1":0,
             "daily_feed1":231.0000,
             "body_weight1":3322.0000},
			 { "growth_age":48,
             "acc_cdreate1":0,
             "daily_feed1":233.0000,
             "body_weight1":3414.0000}]
		};
		Sparraw.ajaxPost('http://localhost:8080/BroilerChicken/standard/editSave.action', params, function(data){
			console.log(data);
			if (data.ResponseDetail.Result == "Success") {
				app_alert("修改任务成功！");
				//$scope.cancelEvent();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
*/

	$scope.getTable = function(){
		var showTableData = {
			'header' : [{
				'name'                :  'TEST',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据1',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST2',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据2',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST3',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据3',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST4',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据4',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST5',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据5',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST6',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据6',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST7',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据7',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST8',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据8',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST9',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据9',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			},{
				'name'                :  'TEST10',//key
				'width'               :  '70',//宽
				'displayName'         :  '数据10',//表头文字
				'headerCellTemplate'  :  '',//在表格上直接覆盖一层div(var headerStyle = '<div style="width:80px;height:30px; background:red;"></div>';)
				'cellTemplate'        :  '',//在表格上直接覆盖一层div(var statusTemplate = '<div style="width:80px;height:30px; background:blue;"></div>';)
				'headerCellClass'     :  '',//修改表格style(在css里写)
				'cellClass'           :  '' //修改表格style(在css里写)
			}],
			'firstFixed': true, //首列是否固定ture-固定，false-不固定
			'rowHeight' : '',//内容高度
			'TableData' : ''
		}




		var showTableData = showTableData;
		$scope.gridOptions = {
		    rowHeight: showTableData.rowHeight,
		};
		$scope.gridOptions.columnDefs = [];
		for (var i = 0; i < showTableData.header.length; i++) {
		    if (i == 0  && showTableData.firstFixed == true) {
		      	$scope.gridOptions.columnDefs.push({ 
	                name                :  showTableData.header[i].name                ,  
	                displayName         :  showTableData.header[i].displayName         , 
	                width               :  showTableData.header[i].width               ,
	                headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                cellClass           :  showTableData.header[i].cellClass           ,
	                headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                pinnedLeft          :  true                                        ,
	                enableCellEdit      :  false                                       ,
	                enableColumnMenu    :  false
	            });
		    }else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            cellClass           :  showTableData.header[i].cellClass           ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableCellEdit      :  false                                       ,
		            enableColumnMenu    :  false

		        });
		    };
		  }

	}


	$scope.gridOptions = {};
	$scope.getTable();
	$scope.rotatingText = "横屏";
	var tempArray = [];

	$scope.rotatingFun = function(){
		$scope.contentH = "";
		$scope.contentW = "";
		$ionicLoading.show();
		$scope.gridTest = true;
		if ($scope.rotatingText == "横屏") {
			setLandscape(true,true,
				function(){
					$scope.contentH = document.getElementById('contentId').clientHeight;
					$scope.contentW = document.getElementById('contentId').clientWidth;
					
					tempArray = [];
					$scope.gridOptions.data = [];
					for (var i = 0; i < 20; i++) {
						tempArray.push({
							'TEST':"|",//需要与header.name保持一致
							'TEST2':"|",//需要与header.name保持一致
							'TEST3':"|",
							'TEST4':"|",
							'TEST5':"|",
							'TEST6':"|",
							'TEST7':"|",
							'TEST8':"|",
							'TEST9':"|",
							'TEST10':"|"
						})
					}
					$scope.gridOptions.data = tempArray;
					$ionicLoading.hide();
					$scope.gridTest = false;
				}
			);
			$scope.rotatingText = "竖屏";
		}else{
			setPortrait(true,true,
				function(){
					$scope.contentH = document.getElementById('contentId').clientHeight;
					$scope.contentW = document.getElementById('contentId').clientWidth;
					
					tempArray = [];
					$scope.gridOptions.data = [];
					for (var i = 0; i < 20; i++) {
						tempArray.push({
							'TEST':"_____",//需要与header.name保持一致
							'TEST2':"_____",//需要与header.name保持一致
							'TEST3':"_____",
							'TEST4':"_____",
							'TEST5':"_____",
							'TEST6':"_____",
							'TEST7':"_____",
							'TEST8':"_____",
							'TEST9':"_____",
							'TEST10':"_____"
						})
					}
					$scope.gridOptions.data = tempArray;
					$ionicLoading.hide();
					$scope.gridTest = false;
				}
			);
			$scope.rotatingText = "横屏";
		}
	}


	

	





  
  /*$scope.gridOptions = {
    headerTemplate: 'module/home/header-template.html',
    category: [
    	{name: '日龄', visible: true},
    	{name: '大标题1', visible: true},
    	{name: '大标题2', visible: true}
    ],//,pinnedLeft:true
    columnDefs: [
    	{name: 'index0', displayName:'',category:'日龄', width:50, enableColumnMenu: false},
      	{name: 'index1', displayName:'小标题1',category:'大标题1', width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index2', displayName:'小标题2',category:"大标题1", width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index3', displayName:'小标题3',category:"大标题1", width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index4', displayName:'小标题4',category:'大标题2', width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index5', displayName:'小标题5',category:'大标题2', width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index6', displayName:'小标题6',category:'大标题2', width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index7', displayName:'小标题7',category:'大标题2', width: 100, enableColumnMenu: false,cellClass:'middle'},
      	{name: 'index8', displayName:'小标题8',category:'大标题2', width: 100, enableColumnMenu: false,cellClass:'middle'}
      ],
    data: [
    	{ index0: '858e', index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1'},
    	{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' },
		{ index1: 'Male', index2: 'Bob', index3: 'CEO', index4 : '14', index5:'1', index6:'1', index7:'1', index8:'1' }
    ],
    onRegisterApi: function( gridApi ) {
      $scope.gridApi = gridApi;
    }
  };*/






})
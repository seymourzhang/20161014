 angular.module('myApp.landingPage', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
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

	var userTemp = biz_common_getUserInfo();
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

    $scope.tempProductionMode = CONFIG_AppMode;

    if($scope.tempProductionMode == 'Development'){
    	$scope.webUrl = '1';
	    var tempURL = "1" ;
	    API_Host = CONFIG_Development_API_Host ;
	    $scope.changeURL = function(){
	    	if(tempURL == "1"){
	    		API_Host = CONFIG_Production_API_Host ;
	    		tempURL = '2';
	    	}else{
	    		API_Host = CONFIG_Development_API_Host ;
	    		tempURL = '1';
	    	}
	    };    	
    }else if($scope.tempProductionMode == 'Production'){
    	API_Host = CONFIG_Production_API_Host ;
    }else if($scope.tempProductionMode == 'Local'){
    	API_Host = CONFIG_Local_API_Host ;
    }

    $scope.goForgotPassword = function(){
    	$state.go("forgotPassword");
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
    				  "AndroidImei": Common_MOBILE_IMEI,
    				  "uuid":Common_MOBILE_UUID,
    				  "model":Common_MOBILE_MODELNAME,
    				  "sysVersion":Common_MOBILE_VERSION,
    				  "platForm":Common_MOBILE_PLATFORM
		};
		//校验信息
	 	if (params.userCode == null || params.pw == '') {return Sparraw.myNotice('请输入用户名/密码，或注册用户');};

		Sparraw.ajaxPost('sys/login/logIn.action', params, function(data){
		    if (data.ResponseDetail.LoginResult == 'Success') {
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
					biz_common_saveUserInfo(JSON.stringify({"userCode": $scope.landing.userCode,"pw": $scope.landing.passWord,"savePW":savePWFlag+""}));
				}
				try{
					var jpushTags = [];
					// 如果用户设置了栋舍信息
					if(sparraw_user.userinfo.houses){
						for (var i = 0; i < sparraw_user.userinfo.houses.length; i++) {
							jpushTags.push('mtc_tag_' + sparraw_user.userinfo.houses[i].HouseId);
						};
						// 为Jpush 设置别名和标签
						Common_JPush_setTagsWithAlias(jpushTags, 'mtc_alias_' + sparraw_user.profile.id_spa);
					}else{
						console.log("用户暂未设置栋舍信息.")
					}
						
				}catch(e){
					console.log(e);
				}
				persistentData.selectedUserInfo = null;
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
			"userCode":13420161102,
			"passWord":"123"
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

    $scope.switchAccount = function(){
    	console.log("切换账户");
		$state.go("userList");
    }

    $scope.passwordAlert = function(){
    	app_alert("忘记密码请联系您的场长重置密码或拨打 4006233007");
    }

    if (persistentData.selectedUserInfo) {
    	for (var i = 0; i < persistentData.transferUserArr.length; i++) {
			if (persistentData.transferUserArr[i].userCode == persistentData.selectedUserInfo) {
				$scope.landing.userCode = persistentData.transferUserArr[i].userCode;
				if (persistentData.transferUserArr[i].savePW == "true") {
					$scope.landing.passWord = persistentData.transferUserArr[i].pw
				}else{
					$scope.landing.passWord = '';
				}
			}
		}
    }

})
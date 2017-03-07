angular.module('myApp.UseRegistered', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
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
    				  "AndroidImei": Common_MOBILE_IMEI,
    				  "uuid":Common_MOBILE_UUID,
    				  "model":Common_MOBILE_MODELNAME,
    				  "sysVersion":Common_MOBILE_VERSION,
    				  "platForm":Common_MOBILE_PLATFORM
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
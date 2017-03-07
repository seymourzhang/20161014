angular.module('myApp.passwordModify', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//密码修改
.controller("passwordModifyCtrl",function($scope, $state, $http, AppData) {


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
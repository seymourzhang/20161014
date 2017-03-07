angular.module('myApp.EmployeesTable', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//员工列表
.controller("EmployeesTableCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

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

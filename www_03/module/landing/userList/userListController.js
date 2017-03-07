 angular.module('myApp.userList', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 // 默认用户名列表
.controller("userListCtrl",function($scope, $state, $http, AppData, $stateParams) {
	
	persistentData.transferUserArr = [];
	var tempOBJ = Common_getObjectFromLocalStorage("loginList");
	var loginListObj = null;
	if(tempOBJ){
		loginListObj = JSON.parse(tempOBJ);	
	}
	
	if(loginListObj){	
		for(var loginObj in loginListObj){
			persistentData.transferUserArr.push(
				JSON.parse(loginListObj[loginObj])
			)
		}
	}

	$scope.userList = persistentData.transferUserArr.reverse();

	$scope.goNext = function(selected){
		persistentData.selectedUserInfo = selected;
		$state.go("landingPage");
	}

	$scope.empty = function(){
		app_confirm('是否清空历史记录？','提示',null,function(buttonIndex){
	       if(buttonIndex == 2){
	       		Common_deleteObjectFromLocalStorage("loginList");
	       		$scope.userList = [];
			    app_alert("已清空历史记录。");
	       }
	    }); 
	}
})

//13820161031、13820160810、13817595130、13820161101、
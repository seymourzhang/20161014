angular.module('myApp.infoSafeguard', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 信息维护
.controller("infoSafeguardCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));


	

	$scope.goFarmRegistered = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"farmRegistered");
	}

	$scope.goBuildingTable = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"buildingTable");
	}

	$scope.goEmployeesTable = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"employeesTable");
	}
	$scope.goMyStandard = function(){
		biz_common_judgeRegistInfo($ionicPopup,$state,"myStandard");
	}

	$scope.checkBaseInfo = function() {
		
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
		biz_common_pointDevelop();
		return;	
	};

     $scope.showConfirm = function() {
          app_confirm('是否要退出该用户?','提示',null,function(buttonIndex){
                   if(buttonIndex == 2){
                        biz_common_userLogout();
                        $state.go("landingPage");
                   }
              }); 
   };

	$scope.exit = function(){
		biz_common_userLogout();
		$state.go("landingPage");
	}

})
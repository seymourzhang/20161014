 angular.module('myApp.dailyTable', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 //生产报告
.controller("dailyTableCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setPortrait(true,true);
	
	$scope.dailyTableData = {
		"listArray" : ["生产日报","生产周报"]
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
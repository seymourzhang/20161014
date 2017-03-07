angular.module('myApp.prodPerfStanTable', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
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
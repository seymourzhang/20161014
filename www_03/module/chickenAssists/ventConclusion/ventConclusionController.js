angular.module('myApp.ventConclusion', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//最小通风计算器结果
.controller("ventConclusionCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.Conclusion = persistentData.ventComputeData.showSecond;
	

	$scope.goVentCompute = function(){
		persistentData.ventComputeResult = "";
		$state.go("ventCompute");
	}
})
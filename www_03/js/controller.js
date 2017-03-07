angular.module('myApp.controllers', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
.controller("myCtrl",function($scope,$state,$http,AppData,$ionicLoading,$timeout,$cordovaFileTransfer,$cordovaFileOpener2,ionicDatePicker){

	Sparraw.inintBaseController($ionicLoading,$http,$timeout,$cordovaFileTransfer,$cordovaFileOpener2,ionicDatePicker);

	$state.go("landingPage");
})
angular.module('myApp.messDetails', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
.controller("messDetailsCtrl",function($scope, $state, $http, $stateParams,AppData,$cordovaFileTransfer,$ionicLoading,$timeout,$cordovaFileOpener2) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	

	$scope.getMessDetails = function(){
		$scope.TempItems   =  JSON.parse($stateParams.Item) ;
		var params = {
		       "MarkType"    :  "Single"                                 ,
		       "MarkResult"  :  "Read"                                   ,
		       "UserId"      :  $scope.sparraw_user_temp.profile.id_spa  ,
		       "MessageId"   :  JSON.parse($stateParams.Item).MessageId  ,
		};
		Sparraw.ajaxPost('sys/message/markRead.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.getMessDetails();

})
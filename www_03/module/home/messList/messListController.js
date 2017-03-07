angular.module('myApp.messList', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
.controller("messListCtrl",function($scope, $state, $http, AppData,$cordovaFileTransfer,$ionicLoading,$timeout,$cordovaFileOpener2) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.getMessList = function(){
		var params = {
			"UserId"       :  $scope.sparraw_user_temp.profile.id_spa     
		};
		Sparraw.ajaxPost('sys/message/queryList.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.TempItems   =  data.ResponseDetail.MessageArray ;
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.judgeRead = function(state){
		if (state == "01") {
			return "{fontWeight:'bold',color:'#000'}";
		}else{
			return "{fontWeight:'normal',color:'#E3E3E3'}";
		};
	};


	$scope.allRead = function(){
		var params = {
		       "MarkType"    :  "All"                                    ,
		       "MarkResult"  :  "Read"                                   ,
		       "UserId"      :  $scope.sparraw_user_temp.profile.id_spa  
		};
		Sparraw.ajaxPost('sys/message/markRead.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("处理成功");
				$scope.getMessList();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}


	$scope.getMessList();

})
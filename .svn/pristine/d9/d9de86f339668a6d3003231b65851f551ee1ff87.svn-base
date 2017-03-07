angular.module('myApp.BuildingTable', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])

// 栋舍列表
.controller("BuildingTableCtrl",function($scope, $state, $http, AppData, $ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	//true-隐藏，false-显示
	if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {//有栋舍
		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//有用户
			$scope.backBtn    =  false ;
			$scope.finishBtn  =  true ;
		}else{//没用户
			$scope.backBtn    =  true  ;
			$scope.finishBtn  =  false ;
		};

	}else{//没有栋舍
		console.log("2");
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	};
	if($scope.sparraw_user_temp.userinfos){
		if ($scope.sparraw_user_temp.userinfos.length == "1") {
			$scope.finishBtn  =  false ;
			console.log("对");
		}else{
			$scope.finishBtn  =  true ;
			console.log("错");
		};
	}else{
		console.log("用户暂未设置员工信息")
	}
	

	


	$scope.goAddbuilding = function(){
		if($scope.sparraw_user_temp.Authority){
			
		}else{
			if($scope.sparraw_user_temp.farminfo == undefined || $scope.sparraw_user_temp.farminfo == null){
				app_alert('您还未进行农场信息设置，请设置后再添加栋舍。');
				return;
			}
			console.log("用户暂未设置权限息,默认All")
			$state.go("addbuilding");
			return;
			//return app_alert("该用户无此操作权限。");
		}
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$state.go("addbuilding");
	}
	

	//判断是否有栋舍信息
	/*if ($scope.sparraw_user_temp.hasOwnProperty("houseinfos")) {		//没栋舍
		$scope.backBtn    =  true   ;
		$scope.finishBtn  =  true   ;
	}else{//有栋舍
		// $scope.finishBtn  =  true   ;
		// $scope.backBtn    =  true   ;

		if ( $scope.sparraw_user_temp.hasOwnProperty("userinfos")) {//没用户
			$scope.backBtn    =  false  ;
			$scope.finishBtn  =  true   ;
		}else{//有用户

		};

	};*/

	$scope.remove = function(item){
		Sparraw.myNotice('如需删除该栋舍，请联系我们。');
		/*var params = {
		    	'operate'   : "DELETE" ,
		    	'houseInfo' : {
				      'houseId'        : item.id                ,
				      'houseName'      : item.houseName         ,
				      'h_length'       : item.h_length       	,
				      'h_width'        : item.h_width           ,
				      'h_height'       : item.h_height          ,
				      'feedarea'       : item.feedarea       	,
				      'mtc_device_id'  : item.mtc_device_id  	
				  }
		    };

		


		Sparraw.ajaxPost('sys/house/update.action', params, function(data){


			if (data.ResponseDetail.ErrorMsg == null) {
			   	var delIndex = -1;
				for(i in $scope.sparraw_user_temp.houseinfos){
					if($scope.sparraw_user_temp.houseinfos[i].houseName==item.houseName){
						delIndex = i ;
					}
				}

				$scope.sparraw_user_temp.houseinfos.splice(delIndex,1);

				sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('删除成功');

			}else {
			   Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};


			
	    });*/
	};

	$scope.goEmployeesTable = function(para){
		console.log($scope.sparraw_user_temp.houseinfos);
		if ($scope.sparraw_user_temp.houseinfos === undefined) {
			app_alert("您还未创建栋舍。");
		}else{
			$state.go("employeesTable");
		};
	};
})

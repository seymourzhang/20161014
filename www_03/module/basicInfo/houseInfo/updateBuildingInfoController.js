angular.module('myApp.updateBuildingInfo', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])

// 栋舍修改
.controller("updateBuildingInfoCtrl",function($scope, $state, $http, $stateParams, AppData,$ionicPopup) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	//查询
	for(i in $scope.sparraw_user_temp.houseinfos){
		if($scope.sparraw_user_temp.houseinfos[i].houseName == $stateParams.buildingID){
			$scope.tempVar.houseTemp = JSON.parse(JSON.stringify($scope.sparraw_user_temp.houseinfos[i]));

		}
	}

	$scope.backBtn    =  false  ;
	$scope.cancelBtn  =  true   ;
	$scope.visible    =  false  ;

	$scope.startEdit = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		$scope.visible    =!  $scope.visible   ;
		$scope.sheerDiv   =!  $scope.sheerDiv  ;
		$scope.backBtn    =   true             ;
		$scope.cancelBtn  =   false            ;
		Sparraw.myNotice('请编辑');
	}

  	$scope.dealBarCode = function(){
		Common_barScan(function(result){
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
			app_alert("扫描的设备编号是：" + result.text);
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};

	$scope.saveUpdate = function(){

		/* 校验信息*/
	    /*var required = [''];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}*/

			var params = {
		    	'operate'   : "UPDATE" ,
		    	'houseInfo' : {
		    		  'houseId'        : $scope.tempVar.houseTemp.id                ,
				      'houseName'      : $scope.tempVar.houseTemp.houseName         ,
				      'h_length'       : $scope.tempVar.houseTemp.h_length       	,
				      'h_width'        : $scope.tempVar.houseTemp.h_width           ,
				      'h_height'       : $scope.tempVar.houseTemp.h_height          ,
				      'feedarea'       : $scope.tempVar.houseTemp.feedarea       	,
				      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  	,
				      'farmId'         : $scope.sparraw_user_temp.farminfo.id       
				  }
		    };





		    Sparraw.ajaxPost('sys/house/update.action', params, function(data){
		    	

		    	if (data.ResponseDetail.ErrorMsg == null) {
			   		var delIndex = -1;
					for(i in $scope.sparraw_user_temp.houseinfos){
						if($scope.sparraw_user_temp.houseinfos[i].houseName == $scope.tempVar.houseTemp.houseName){
							delIndex = i ;
						}
					}
					$scope.sparraw_user_temp.houseinfos.splice(delIndex,1,JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));

					sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));
				
			    	Sparraw.myNotice('编辑成功');

			    	$scope.tempVar.houseTemp = {};

			    	$state.go("buildingTable");
			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };




		    	
		    });

	}

	$scope.alert = function(){
		Sparraw.myNotice('点击编辑后即可修改');
	};


	//返回时清空填写内容 
	$scope.backTable = function(){
		if (!$scope.sheerDiv) {
			$scope.tempVar.houseTemp = {};
	    	$state.go("buildingTable");
		}else {
			Sparraw.myNotice('您还未进行保存，请保存后再返回。');
		}

  	};

  	$scope.cancelEvent = function(){
  		$scope.visible = !$scope.visible;
		$scope.sheerDiv = !$scope.sheerDiv;
		$scope.backBtn = false;
		$scope.cancelBtn = true;
  	}

})

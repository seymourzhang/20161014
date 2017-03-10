angular.module('myApp.Addbuilding', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])

//栋舍添加
.controller("AddbuildingCtrl",function($scope, $state, $http, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	var maxID = null;
	// 栋舍编号，自动生成。并且判断是否有信息
	if (sparraw_user.houseinfos == undefined) {
		maxID = 1 ;
		$scope.tempVar.houseTemp.houseName = Common_padNumber(maxID,2) ;
	}else{
		maxID = $scope.sparraw_user_temp.farminfo.house_Maxid + 1 ;
		$scope.tempVar.houseTemp.houseName = Common_padNumber(maxID,2) ;

	};

	
	//将农场信息中得数据代入到栋舍中
	$scope.tempVar.houseTemp.h_length = $scope.sparraw_user_temp.farminfo.house_length;
	$scope.tempVar.houseTemp.h_width  = $scope.sparraw_user_temp.farminfo.house_width;
	$scope.tempVar.houseTemp.h_height = $scope.sparraw_user_temp.farminfo.house_height;
	$scope.tempVar.houseTemp.feedarea = $scope.sparraw_user_temp.farminfo.feedarea;

	$scope.addHouse = function(){
	    /* 校验信息*/
	    /*var required = ['mtc_device_id'];
	   	for(i in required){if($scope.tempVar.houseTemp[required[i]]==''){return Sparraw.myNotice('尚有内容未填写...');}}*/

	   	if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};
		

	    var params = {
	      'farmId'         : $scope.sparraw_user_temp.farminfo.id    ,
	      'houseName'      : $scope.tempVar.houseTemp.houseName      ,
	      'h_length'       : $scope.tempVar.houseTemp.h_length       ,
	      'h_width'        : $scope.tempVar.houseTemp.h_width        ,
	      'h_height'       : $scope.tempVar.houseTemp.h_height       ,
	      'feedarea'       : $scope.tempVar.houseTemp.feedarea       ,
	      'mtc_device_id'  : $scope.tempVar.houseTemp.mtc_device_id  
	    };



	    Sparraw.ajaxPost('sys/house/save.action', params, function(data){

	    	if (data.ResponseDetail.ErrorMsg == null) {
			   	$scope.sparraw_user_temp.farminfo.house_Maxid = maxID;

		    	$scope.tempVar.houseTemp.id = data.ResponseDetail.houseId;

		    	//判断是否有信息
		    	if (sparraw_user.houseinfos == undefined) {
		    		$scope.sparraw_user_temp.houseinfos = [];
				}else{

				};

		  		$scope.sparraw_user_temp.houseinfos.push(JSON.parse(JSON.stringify($scope.tempVar.houseTemp)));
		    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

		    	Sparraw.myNotice('保存成功');
		    	
		    	$scope.tempVar.houseTemp = {};

		    	//重新获取服务器最新数据
    			biz_common_getLatestData($state,"buildingTable");
			}else {
			    Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};

	    });
  	};
  	
  	$scope.dealBarCode = function(){  		
		Common_barScan(function(result){
			app_alert("您的设备编号是：" + result.text);
			$scope.tempVar.houseTemp.mtc_device_id = result.text;
		},function(error){
			app_alert("调用扫描失败，请手动输入设备编号。");
		});
	};
})

angular.module('myApp.AddEmployees', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//员工添加
.controller("AddEmployeesCtrl",function($scope, $state, $http, $ionicPopup, $ionicModal, AppData) {

	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.JudgeHouse = function(){
		if (this.tempVar.userTemp.role == 4) {
			$scope.houseAffiliation = true;
		}else {
			$scope.houseAffiliation = false;
		};
	}


    $scope.devList = [];

    for (var i = 0; i < $scope.sparraw_user_temp.houseinfos.length; i++) {
    	$scope.devList.push({"text"     :           $scope.sparraw_user_temp.houseinfos[i].houseName ,
    						 "checked"  :           false                                            ,
    						 "id"       :           $scope.sparraw_user_temp.houseinfos[i].id
    	});
    };





	$scope.save = function(){
	    /* 校验信息*/
	   	if ($scope.tempVar.userTemp.name == "" ||
	   		$scope.tempVar.userTemp.tele == "" ||
	   		$scope.tempVar.userTemp.role == "" ||
	   		$scope.tempVar.userTemp.name == undefined ||
	   		$scope.tempVar.userTemp.tele == undefined ||
	   		$scope.tempVar.userTemp.role == undefined ) {
	   		return Sparraw.myNotice('尚有内容未填写...');
	   	};
		if($scope.tempVar.userTemp.tele && /^1[3|4|5|8]\d{9}$/.test($scope.tempVar.userTemp.tele)){
			//对的
			console.log("输入正确");
		} else{
			//不对
			return Sparraw.myNotice("请输入正确手机号码");
		};

		

		var selectedHouse = [];
		for (var i = 0; i < $scope.devList.length; i++) {
	    	if ($scope.devList[i].checked == true) {
	    		selectedHouse.push($scope.devList[i].id);
	    	}else {

	    	};
	    };
	    //判断饲养员是否选择了所属栋舍
	    if($scope.tempVar.userTemp.role==4){
	    	console.log(selectedHouse.length);
	   		if (selectedHouse.length == 0) {
	   			return Sparraw.myNotice("请选择所属栋舍");
	   		}else{

	   		};
	    }else{

	    };


	    var params = {
	      'name'           : $scope.tempVar.userTemp.name                       ,
	      'tele'           : $scope.tempVar.userTemp.tele                       ,
	      'pw'             : "123456"                                           ,
	      'role'           : $scope.tempVar.userTemp.role                       ,
	      'farmid'         : $scope.sparraw_user_temp.farminfo.id               ,
	      'houses'         : selectedHouse                                      	

	    };
	    
	    Sparraw.ajaxPost('sys/user/save.action', params, function(data){
	    	
	    	if (data.ResponseDetail.ErrorMsg == null) {
			   		$scope.tempVar.userTemp.id = data.ResponseDetail.userId;


			   		//判断是否有信息
			    	if (sparraw_user.userinfos == undefined) {
			    		$scope.sparraw_user_temp.userinfos = [];
					}else{

					};

					$scope.sparraw_user_temp.userinfos.push(JSON.parse(JSON.stringify($scope.tempVar.userTemp)));

			    	sparraw_user = JSON.parse(JSON.stringify($scope.sparraw_user_temp));

			    	//制空新建员工 列表
			    	$scope.tempVar.userTemp = {};

			    	

			    	//重新获取服务器最新数据
			    	biz_common_getLatestData($state,'employeesTable');
			    	app_alert('保存成功，新建员工密码为123456。');

			   }else {
			   		Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			   };


	    });

  	};



  	$ionicModal.fromTemplateUrl('useHelp.html', function(modal) {  
	    $scope.modalDIV = modal;
	}, {  
	    scope: $scope,  
	    animation: 'slide-in-up'
	});

    $scope.openFun = function(){
	  	$scope.modalDIV.show();
    }

    $scope.closeFun = function(){
    	$scope.modalDIV.hide();
    }

})
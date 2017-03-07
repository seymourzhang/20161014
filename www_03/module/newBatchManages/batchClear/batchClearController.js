 angular.module('myApp.batchClear', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//批次结算
.controller("batchClearCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicBackdrop,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.batchClearData = {
		"FarmId"          :  $scope.sparraw_user_temp.farminfo.id,
		"BreedBatchId"    :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"BatchStatus"     :  ""  ,
        "FeedMsg":{
            "VenderName"  :  ""  ,
            "FeedInfo":[{
                  "FeedCode"   :  ""  ,
                  "FeedName"   :  ""  ,
                  "Weight"     :  ""  ,
                  "Price_p"    :  ""  ,
                  "Price_sum"  :  ""  
            }],
            "sys_amount":"",

			"total_Weight"      :  0  ,  //合计的 公斤数
			"total_Price_p"     :  0  ,  //合计的 单价
			"total_Price_sum"   :  0    //合计的 总金额
        },
        "OtherMsg":{
        	"ChickenManure"  :  ""  ,   //--鸡粪收入
            "VaccineFee"     :  ""  ,   //--药品疫苗费
            "CatcherFee"     :  ""  ,   //--抓鸡费
            "PaddingFee"     :  ""  ,   // --垫料费
            "ManualFee"      :  ""  ,   //--人工费
            "FuelFee"        :  ""  ,   //--燃料费
            "UtilityFee"     :  ""  ,   //--水电费
            "MaintainFee"    :  ""  ,   //--维修费
            "QuarantineFee"  :  ""  ,   //--检疫费
            "RentFee"        :  ""  ,   //--租金
            "InterestFee"    :  ""  ,   //--利息

            "OtherFee"       :  ""  ,   //折旧费
            "DeprFee"        :  ""  ,   //杂费
			"ServiceFee"     :  ""     //服务费
        },
        "OtherFeeInputType":"0",
        "TempTotal":""
	}

	//饲料结算
	//计算饲料结算的金额
	$scope.forageTotal = function(){
		var total_Weight_temp = 0;
		var total_Price_sum_temp = 0;
		var total_Price_p_temp = 0;

		for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
			//  计算 单个饲料总金额
			$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = 
					($scope.batchClearData.FeedMsg.FeedInfo[i].Weight * $scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			if (!Common_isNum($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum)) {
				$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = 0;
			}else{
				$scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum = $scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum.toFixed(0); 
			}
			//  计算 合计-公斤数
			if(Common_isNum($scope.batchClearData.FeedMsg.FeedInfo[i].Weight)){
				total_Weight_temp += parseFloat($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			}
			//  计算 合计-总金额
			if(Common_isNum($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum)){
				total_Price_sum_temp += parseFloat($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);
			}
		};
		//  计算 合计-单价数
		total_Price_p_temp = total_Price_sum_temp/total_Weight_temp ;
		if(!Common_isNum(total_Price_p_temp)){
			total_Price_p_temp = 0;
		}else{
			total_Price_p_temp = Math.round(total_Price_p_temp*100)/100;  
		}
		$scope.batchClearData.FeedMsg.total_Weight = total_Weight_temp;
		$scope.batchClearData.FeedMsg.total_Price_sum = total_Price_sum_temp;
		$scope.batchClearData.FeedMsg.total_Price_p = total_Price_p_temp;
	};




	$scope.inquire = function(){
		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{

		}

		var params = {
       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId
		};
		Sparraw.ajaxPost('farmManage/settleBatchQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.batchClearData.FeedMsg.VenderName = data.ResponseDetail.FeedMsg.VenderName;
				$scope.batchClearData.FeedMsg.FeedInfo = data.ResponseDetail.FeedMsg.FeedInfo;
				$scope.batchClearData.FeedMsg.sys_amount = data.ResponseDetail.FeedMsg.sys_amount;
				$scope.batchClearData.OtherMsg = data.ResponseDetail.OtherMsg;
				$scope.batchClearData.OtherFeeInputType = data.ResponseDetail.OtherFeeInputType;



				//$scope.chooseWay();
				for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
					$scope.batchClearData.FeedMsg.FeedInfo[i].Id = i;
				}
				//计算第一个表格
				$scope.forageTotal();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.inquire();
	

	$scope.submitLimit = function(){
		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			app_alert("该用户无此操作权限。");
			return false;
		};

		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			app_alert("该农场无入雏信息，请先入雏。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[0].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[0].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[1].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[1].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[2].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[2].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[3].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[3].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[4].Price_p == 0 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight == 0 ||
			$scope.batchClearData.FeedMsg.FeedInfo[4].Price_p != 0 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight != 0) {
		}else{
			app_alert("公斤数与单价有一个输入则必须全部输入。");
			return false;
		}
		

		if ($scope.batchClearData.FeedMsg.FeedInfo[0].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[0].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[1].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[1].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[2].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[2].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[3].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[3].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}

		if ($scope.batchClearData.FeedMsg.FeedInfo[4].Price_p >= 5 && $scope.batchClearData.FeedMsg.FeedInfo[4].Weight != 0) {
			app_alert("饲料单价不能大于5元。");
			return false;
		}else{

		}



		return true;
	}

	$scope.saveFun = function(){
		if ($scope.submitLimit()) {

		}else{
			return;
		}

		

		var params = {
       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId        ,
        	"FarmId"        :  $scope.batchClearData.FarmId              ,
        	"FeedMsg":{
              "VenderName"  :  $scope.batchClearData.FeedMsg.VenderName  ,
              "FeedInfo"    :  $scope.batchClearData.FeedMsg.FeedInfo    ,
              "sys_amount"  :  $scope.batchClearData.FeedMsg.sys_amount
            },
        	"OtherMsg"      :  $scope.batchClearData.OtherMsg,
        	"OtherFeeInputType" : $scope.batchClearData.OtherFeeInputType
    	}

		Sparraw.ajaxPost('farmManage/settleBatchSave.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				Sparraw.myNotice("保存成功。");
				selectBackPage.profitReportBack = 'batchClearPage';
				$state.go("newProfitReport");
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.clearingFun = function(){
		if ($scope.submitLimit()) {

		}else{
			return;
		}

		

		//判断结算方式
		if ($scope.batchClearData.OtherFeeInputType != "01") {
			//饲料厂家不为空，饲料总量不为0，药品疫苗不为空
			if ($scope.batchClearData.FeedMsg.VenderName == "" || !$scope.batchClearData.FeedMsg.VenderName) {
				return app_alert("请输入饲料厂家。");
			}
			if ($scope.batchClearData.OtherMsg.VaccineFee == "" || !$scope.batchClearData.OtherMsg.VaccineFee) {
				return app_alert("请输入药品疫苗费。");
			}
			if ($scope.batchClearData.OtherMsg.ManualFee == "" || !$scope.batchClearData.OtherMsg.ManualFee) {
				return app_alert("请输入人工费。");
			}
		}else{
			
		}
		

		var nullArr = [];
		var allArr = [];
		for (var i = 0; i < $scope.batchClearData.FeedMsg.FeedInfo.length; i++) {
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Weight == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Weight == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			}
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Price_p == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			}
			if ($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum == 0 || $scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum == "") {
				nullArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);
			}

			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Weight);
			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_p);
			allArr.push($scope.batchClearData.FeedMsg.FeedInfo[i].Price_sum);

		}
		if (nullArr.length == allArr.length) {
			return app_alert("饲料不能为空。");
		}else{
			
		}

    	var stateA = [];//00
		var stateB = [];//01
		var stateC = [];//02
		for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "00") {
				stateA.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "01") {
				stateB.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}

			if ($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus == "02") {
				stateC.push($scope.sparraw_user_temp.userinfo.houses[i].HouseBreedStatus);
			}
		}



		//判断批次结算
		if (stateC.length == 0) {return app_alert("出栏后才能进行结算。");};

		
		if ($scope.batchClearData.BreedBatchId == "" || !$scope.batchClearData.BreedBatchId) {
			return app_alert("该农场无入雏信息，请先入雏。");
		}else{
			app_confirm('结算后该批次数据无法修改,是否要进行结算？',null,null,function(buttonIndex){
				if(buttonIndex == 1){

				}else if(buttonIndex == 2){
					//测算
					var params = {
			       		"BreedBatchId"  :  $scope.batchClearData.BreedBatchId        ,
			        	"FarmId"        :  $scope.batchClearData.FarmId              ,
			        	"FeedMsg":{
			              "VenderName"  :  $scope.batchClearData.FeedMsg.VenderName  ,
			              "FeedInfo"    :  $scope.batchClearData.FeedMsg.FeedInfo    ,
			              "sys_amount"  :  $scope.batchClearData.FeedMsg.sys_amount
			            },
			        	"OtherMsg"      :  $scope.batchClearData.OtherMsg            ,
			        	"OtherFeeInputType" : $scope.batchClearData.OtherFeeInputType
			    	}
					Sparraw.ajaxPost('farmManage/settleBatchSave.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							//结算
							var params = {
								"BreedBatchId":$scope.batchClearData.BreedBatchId
							};
							Sparraw.ajaxPost('farmManage/settleBatchConfirm.action', params, function(data){
								if (data.ResponseDetail.Result == "Success") {
									$ionicBackdrop.release();
									Sparraw.myNotice("结算成功！");
									//重新获取服务器最新数据
			    					biz_common_getLatestData($state,"newBatchManage");
								}else if (data.ResponseDetail.Result == "Fail") {
									$ionicBackdrop.release();
									Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
								}else{
									$ionicBackdrop.release();
									Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
								};
							});
						}else if (data.ResponseDetail.Result == "Fail") {
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}
			});	
		}
	}

	$scope.judgePrice = function(item){
		if (item.Price_p <= 5) {

		}else{
			Sparraw.myNotice("饲料单价不能大于5元。");
		}
	}
})






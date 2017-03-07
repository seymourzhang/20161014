 angular.module('myApp.docPlaceAffirm', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 //入雏确认
.controller("docPlaceAffirmCtrl",function($scope, $state,$ionicModal,$ionicLoading,$ionicScrollDelegate) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.docPlaceData = {
		"firstTime":false,//判断是否是第一次取值
        "farmBreedId":$scope.sparraw_user_temp.farminfo.farmBreedBatchId,
        "houseInfo":[],
        "batchInfo":{
	       "farmId":$scope.sparraw_user_temp.farminfo.id,//int型，农场id√
	       "batchCode":"",//varchar型，批次编号√
	       "batchDate":"",//varchar型，批次日期√
	       "planBreedDays":"",//number型，预计饲养天数√
	       "placeNumSum":"",//number型，总入雏数量
	       "planMarketDate":"",//varchar型，预计出栏日期，YYYY-MM-DD
	       "chickVendor":"",//varchar型，雏源厂家         
	       "chickPrice":""//number型，鸡苗单价
     	}
	}
	var temphouseInfo = [];
	var tempbatchInfo = {};
	
    $scope.clickDateInput = function(item){
    	for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
    		if ($scope.docPlaceData.houseInfo[i].houseId == item.houseId) {
    			Sparraw.openDatePicker("docPlaceData.houseInfo[" + i + "].placeDate","$scope.setShowDateCallBack();");
    		}
    	}
      	
    };

    $scope.setShowDateCallBack = function(){
      	//$scope.inquire();
    };


    $scope.inquire = function(){

    	if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "02") {
    		var params = {
				"farmId":$scope.docPlaceData.batchInfo.farmId,
				"farmBreedId":$scope.docPlaceData.farmBreedId,  
			};
			Sparraw.ajaxPost('farmManage/placeChickQuery.action', params, function(data){
				if (data.ResponseDetail.Result == "Success") {
					$scope.docPlaceData.houseInfo = data.ResponseDetail.houseInfo;
					$scope.docPlaceData.batchInfo = data.ResponseDetail.batchInfo;

					tempbatchInfo = JSON.parse(JSON.stringify(data.ResponseDetail.batchInfo));
					temphouseInfo = JSON.parse(JSON.stringify(data.ResponseDetail.houseInfo));
					
					$scope.docPlaceData.firstTime = false;
				}else if (data.ResponseDetail.Result == "Fail") {
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
					$scope.docPlaceData.firstTime = true;
					for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
						$scope.docPlaceData.houseInfo.push({
							"houseId":$scope.sparraw_user_temp.userinfo.houses[i].HouseId,//int型，栋舍id
							"houseName":$scope.sparraw_user_temp.userinfo.houses[i].HouseName,
							"placeNum":"",//int型，入雏数量
							"placeDate":""//varchar型，入雏日期，YYYY-MM-DD
						})
					}

					console.log($scope.docPlaceData.houseInfo);
				}else{
					Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				};
			});
    	}else{
    		Sparraw.myNotice("暂无数据请先入雏");
    		$scope.docPlaceData.firstTime = true;
				for (var i = 0; i < $scope.sparraw_user_temp.userinfo.houses.length; i++) {
					$scope.docPlaceData.houseInfo.push({
						"houseId":$scope.sparraw_user_temp.userinfo.houses[i].HouseId,//int型，栋舍id
						"houseName":$scope.sparraw_user_temp.userinfo.houses[i].HouseName,
						"placeNum":"",//int型，入雏数量
						"placeDate":""//varchar型，入雏日期，YYYY-MM-DD
					})
			}
    	}
	}
	$scope.inquire();





	$scope.save = function(){



		if ($scope.sparraw_user_temp.Authority.FarmBreed === "All") {

		}else{
			return app_alert("该用户无此操作权限。");
		};

		if ($scope.docPlaceData.batchInfo.planBreedDays > 29 && $scope.docPlaceData.batchInfo.planBreedDays < 131) {

		}else{
			return app_alert("预计饲养天数必须在30天至130天内。");
		}

		if ($scope.docPlaceData.batchInfo.chickPrice > 0 && $scope.docPlaceData.batchInfo.chickPrice <= 9) {

		}else{
			return app_alert("鸡苗单价必须大于0元且不能大于9元。");
		}


		if ($scope.docPlaceData.firstTime == false) {
			if (tempbatchInfo.planBreedDays != $scope.docPlaceData.batchInfo.planBreedDays) {
				return app_alert("预计饲养天数无法修改。");
			}
			var minDate = $scope.docPlaceData.batchInfo.batchDate;
			var TempPlaceDate = [];
			for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
				TempPlaceDate.push($scope.docPlaceData.houseInfo[i].placeDate);
			}
			for (var i = 0; i < TempPlaceDate.length; i++) {
				var oDate1 = new Date(minDate);
			    var oDate2 = new Date(TempPlaceDate[i]);
			    if(oDate1.getTime() > oDate2.getTime()){
			        return app_alert("新入雏栋舍的入雏日期必须大于批次日期。");
			    }else{
			    	
			    }
			}
		}else{
			var TempPlaceDate = [];
			for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
				TempPlaceDate.push($scope.docPlaceData.houseInfo[i].placeDate);
			}
			var TempPlaceDateStr = TempPlaceDate.toString();
			if (TempPlaceDate.length == TempPlaceDateStr.length+1) {
				return app_alert("请输入至少一栋的入雏信息。");
			}
		}

		
		for (var i = 0; i < temphouseInfo.length; i++) {
			if (temphouseInfo[i].placeDate != "" &&
				temphouseInfo[i].placeDate != $scope.docPlaceData.houseInfo[i].placeDate) {
				return app_alert("入雏日期无法修改。");
			}
		}


		

		if ($scope.docPlaceData.batchInfo.chickVendor == "" || !$scope.docPlaceData.batchInfo.chickVendor) {
			return app_alert("请正确输入雏源厂家。");
		}
		if ($scope.docPlaceData.batchInfo.chickPrice == "" || !$scope.docPlaceData.batchInfo.chickPrice) {
			return app_alert("请正确输入鸡苗单价。");
		}
		if ($scope.docPlaceData.batchInfo.planBreedDays == "" || !$scope.docPlaceData.batchInfo.planBreedDays) {
			return app_alert("请正确输入预计饲养天数。");
		}

		for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
			if ($scope.docPlaceData.houseInfo[i].placeDate != "" &&
				$scope.docPlaceData.houseInfo[i].placeNum == "" ||
				$scope.docPlaceData.houseInfo[i].placeDate == "" &&
				$scope.docPlaceData.houseInfo[i].placeNum != "") {
				return app_alert("请完整填写入雏栋舍信息。");
			}

			if ($scope.docPlaceData.houseInfo[i].placeNum == 0 && $scope.docPlaceData.houseInfo[i].placeDate != "") {
				return app_alert("进鸡数量不允许为0。");
			}
		}

		app_confirm('保存后栋舍入雏日期无法修改，是否要进行保存？',null,null,function(buttonIndex){
			if(buttonIndex == 1){
				// app_alert('您选择了【取消】');
			}else if(buttonIndex == 2){
				// app_alert('您选择了【确定】');
				//删除空数据
				var TempHouseInfo = [];
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					if ($scope.docPlaceData.houseInfo[i].placeDate == "" && $scope.docPlaceData.houseInfo[i].placeNum == 0) {
						//$scope.docPlaceData.houseInfo.splice(i);
					}else{
						TempHouseInfo.push($scope.docPlaceData.houseInfo[i]);
					}
				}
				$scope.docPlaceData.houseInfo = TempHouseInfo;

				//获取所有栋舍日期
				var dateArr = [];
				//获取所有栋舍入雏数
				var chickArr = [];
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					dateArr.push($scope.docPlaceData.houseInfo[i].placeDate);
					chickArr.push($scope.docPlaceData.houseInfo[i].placeNum);
				}
				//判断日期大小
				var minDate = dateArr[0];
		    	for (var i = 0; i < dateArr.length; i++) {
		    		var oDate1 = new Date(minDate);
				    var oDate2 = new Date(dateArr[i]);
				    if(oDate1.getTime() > oDate2.getTime()){
				        minDate = dateArr[i];
				    }
		    	}

		    	if ($scope.docPlaceData.firstTime == true) {
					//获取批次日期
			    	$scope.docPlaceData.batchInfo.batchDate = minDate;
			    	//获取批次编号
					$scope.docPlaceData.batchInfo.batchCode = minDate.replace(/-/g,"");
					$scope.docPlaceData.batchInfo.batchCode = $scope.docPlaceData.batchInfo.batchCode.substring(2,$scope.docPlaceData.batchInfo.batchCode.length);
					console.log($scope.docPlaceData.batchInfo.batchCode);
					//获取预计出栏日
					var TempOverDate = new Date(minDate);
					var TempDate = TempOverDate.getTime() + $scope.docPlaceData.batchInfo.planBreedDays  * 24 * 60 * 60 * 1000
					TempDate = new Date(TempDate);
					
			        var OverDate = TempDate.toLocaleDateString();
	                OverDate     = OverDate.replace(/(日)/g,"");
	                OverDate     = OverDate.replace(/(月)/g,"-");
	                OverDate     = OverDate.replace(/(年)/g,"-");
	                OverDate     = OverDate.replace(/\//g,"-");
	                if (OverDate[6] == "-") {
	                	OverDate = OverDate.replace(/(.{5})/g,'$10');
	                };
	                if (OverDate[9]) {

	                }else{
	                	OverDate = OverDate.replace(/(.{8})/g,'$10');
	                };
					$scope.docPlaceData.batchInfo.planMarketDate = OverDate;
				}else{
					
				}
				//进鸡数转换为Number
				for (var i = 0; i < $scope.docPlaceData.houseInfo.length; i++) {
					$scope.docPlaceData.houseInfo[i].placeNum = Number($scope.docPlaceData.houseInfo[i].placeNum);
				}
				//获取进鸡总数
				$scope.docPlaceData.batchInfo.placeNumSum = 0;
				for (var i = 0; i < chickArr.length; i++) {
					$scope.docPlaceData.batchInfo.placeNumSum += Number(chickArr[i]);
				}
				//添加农场id和批次id
				$scope.docPlaceData.batchInfo.farmId = $scope.sparraw_user_temp.farminfo.id;
				$scope.docPlaceData.batchInfo.farmBreedId = $scope.sparraw_user_temp.farminfo.farmBreedBatchId;


				console.log("-------------------------");
				console.log($scope.sparraw_user_temp.farminfo.farmBreedStatus);
				if ($scope.sparraw_user_temp.farminfo.farmBreedStatus != "02") {
					var params = {
						"batchInfo"   :  $scope.docPlaceData.batchInfo,
						"houseInfo"   :  $scope.docPlaceData.houseInfo
					};
					Sparraw.ajaxPost('farmManage/placeChickDeal.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							console.log(data);
							Sparraw.myNotice("保存成功");
							//重新获取服务器最新数据
	    					biz_common_getLatestData($state,"newBatchManage");
						}else if (data.ResponseDetail.Result == "Fail") {
							//Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
							app_alert(data.ResponseDetail.ErrorMsg);
							$scope.inquire();
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}else{

					$scope.docPlaceData.batchInfo.farmBreedId = 0;
					var params = {
						"batchInfo"   :  $scope.docPlaceData.batchInfo,
						"houseInfo"   :  $scope.docPlaceData.houseInfo
					};
					Sparraw.ajaxPost('farmManage/placeChickDeal.action', params, function(data){
						if (data.ResponseDetail.Result == "Success") {
							console.log(data);
							Sparraw.myNotice("保存成功");
							//重新获取服务器最新数据
	    					biz_common_getLatestData($state,"newBatchManage");
						}else if (data.ResponseDetail.Result == "Fail") {
							//Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
							app_alert(data.ResponseDetail.ErrorMsg);
							$scope.inquire();
						}else{
							Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
						};
					});
				}
			}
		});

		
	}

	$scope.judgeBreedDays = function(){
		if ($scope.docPlaceData.batchInfo.planBreedDays > 29 && $scope.docPlaceData.batchInfo.planBreedDays < 131) {

		}else{
			Sparraw.myNotice("预计饲养天数必须在30天至130天内。");
		}
	}

	$scope.judgeChickPrice = function(){
		if ($scope.docPlaceData.batchInfo.chickPrice > 0 && $scope.docPlaceData.batchInfo.chickPrice <= 9) {

		}else{
			Sparraw.myNotice("鸡苗单价必须大于0元且不能大于9元。");
		}
	}


})
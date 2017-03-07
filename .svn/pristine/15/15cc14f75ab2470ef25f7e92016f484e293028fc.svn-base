angular.module('myApp.myStandard', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//我的标准 
.controller("myStandardCtrl",function($scope, $state, $ionicModal, $stateParams, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(false,false);
	$scope.myStanData = {
		"FarmId"       :  $scope.sparraw_user_temp.farminfo.id,
		"StandardName" :  20001,
		"selectDetailData": "",
		"GetBreedName" :  "",
		"DetailData"   :  []
	}

	$ionicModal.fromTemplateUrl('modalWindow.html', function(modal) {  
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

	$scope.gridOptions = {};
	$scope.GetTable = function(){
		var TempshowTableData = {};
		var header = [];
		var firstFixed = true; //首列是否固定ture-固定，false-不固定
		var rowHeight  = '';//内容高度
		var TableData = $scope.myStanData.DetailData;
		var TableShowName = [];
		var TableKey = [];

		TableKey = ['growth_age','acc_cdreate1','body_weight1','daily_bddiff1','daily_feed1','acc_feed1','FCR1'];
		TableShowName = ["日龄","累计死淘(%)","体重(g)","日增重(g)","日采食(g)","累计采食(g)","累计料肉比"];
		for (var i = 0; i < TableKey.length; i++) {
			
			if (i == 0) {
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '36',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:35px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  true
				})
			}else if (i == 1) {
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '81',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  false
				})
			}else{
				header.push({
					'name'                :  TableKey[i],
					'width'               :  '81',
					//'displayName'         :  TableShowName[i],
					'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-left:solid 0.5px #ECECEC; border-right:solid 0.5px #ECECEC;"><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
					'cellTemplate'        :  '',
					'headerCellClass'     :  '',
					'enableCellEdit'      :  false
				})
			}
		}

		TempshowTableData = {
			'header'      :header,
			'firstFixed'  :firstFixed,
			'rowHeight'   :rowHeight,
			'TableData'   :TableData
		};

		var showTableData = TempshowTableData;
		$scope.gridOptions = {
		    rowHeight: showTableData.rowHeight,
		};
		$scope.gridOptions.columnDefs = [];
		for (var i = 0; i < showTableData.header.length; i++) {
		    if (i == 0  && showTableData.firstFixed == true) {
		      	$scope.gridOptions.columnDefs.push({ 
	                name                :  showTableData.header[i].name                ,  
	                displayName         :  showTableData.header[i].displayName         , 
	                width               :  showTableData.header[i].width               ,
	                headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                enableCellEdit      :  showTableData.header[i].enableCellEdit      ,
	                pinnedLeft          :  true                                        ,
	                enableColumnMenu    :  false                                       ,
	                cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
	            });
		    }else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            cellClass           :  showTableData.header[i].cellClass           ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableCellEdit      :  showTableData.header[i].enableCellEdit      ,
		            enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.growth_age% 7 == 0 && row.entity.growth_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
		        });

		    };
		  }
	  	$scope.gridOptions.data = showTableData.TableData;
	  	//判断哪些数据进行过修改
		$scope.gridOptions.onRegisterApi = function(gridApi){
			$scope.gridApi = gridApi;
			//input获取焦点的时候
			gridApi.edit.on.beginCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
				for (var i = 0; i < $scope.myStanData.DetailData.length; i++) {
					if ($scope.myStanData.DetailData[i].growth_age == rowEntity.growth_age) {
						$scope.myStanData.selectDetailData = $scope.myStanData.DetailData[i]
					}
				}
				$scope.myStanData.selectDetailData.acc_cdreate1 = parseFloat($scope.myStanData.selectDetailData.acc_cdreate1);
				$scope.openFun();
			});
			//input失去焦点时调用
			//gridApi.edit.on.afterCellEdit($scope,afterCellEdit);
		};
	}



	$scope.inquire = function(){
		var params = {
			"StandardName":$scope.myStanData.StandardName,
   			"FarmId":$scope.myStanData.FarmId   
		};
		Sparraw.ajaxPost('standard/detailQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				$scope.myStanData.DetailData = data.ResponseDetail.DetailData;
				$scope.myStanData.GetBreedName = data.ResponseDetail.BreedName;
				if ($scope.myStanData.DetailData.length == 0) {
					Sparraw.myNotice("暂无数据，请先设定标准。");
				}else{

				}
				$scope.GetTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}

	$scope.inquire();
	$scope.GetTable();


	$scope.save = function(){

		if ($scope.myStanData.selectDetailData.acc_feed1 == "-") {
			$scope.myStanData.selectDetailData.acc_feed1 = 0;
		}
		if ($scope.myStanData.selectDetailData.body_weight1 == "-") {
			$scope.myStanData.selectDetailData.body_weight1 = 0;
		}
		if ($scope.myStanData.selectDetailData.daily_bddiff1 == "-") {
			$scope.myStanData.selectDetailData.daily_bddiff1 = 0;
		}

		var transferArr = [];
		transferArr.push($scope.myStanData.selectDetailData);
		var params = {
   			"FarmId":$scope.myStanData.FarmId,
   			"DetailData":transferArr
		};

		console.log($scope.myStanData.selectDetailData);

		Sparraw.ajaxPost('standard/editSave.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.inquire();
				$scope.modalDIV.hide();
				Sparraw.myNotice("保存成功");
			}else if (data.ResponseDetail.Result == "Fail") {

			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	}


	$scope.goSetStandard = function(){
		if ($scope.sparraw_user_temp.Authority.basicInfo === "All") {
			$state.go("setStandard");
		}else{
			return app_alert("该用户无此操作权限。");
		};
	}
	
})


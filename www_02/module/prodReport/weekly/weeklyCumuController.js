 angular.module('myApp.weekly', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
 //生产周报
.controller("weeklyCtrl",function($scope, $state, $http,$ionicModal, $stateParams, $ionicPopup, AppData) {

	$scope.goDailyTable = function(){
		$state.go('dailyTable');
	}
	$scope.setData = function() {
		$scope.weeklyData = {
	   		"FarmBreedId"  :  $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
			"HouseId"      :  $scope.sparraw_user_temp.userinfo.houses[0].HouseId,
	   		"TableData"   :  [],
	   		"selectHouse" :  $scope.sparraw_user_temp.userinfo.houses[0]
		}
		$scope.judgeHouse();
	}


	$scope.getTable = function(){

			var header = [];
			var firstFixed = true; //首列是否固定true-固定，false-不固定
			var rowHeight  = '';//内容高度
			var TableShowName = ["周龄",
								 "数量",
								 "比率",
								 "标准",
								 "数量",
								 "日耗",
								 "标准",
								 "数量",
								 "水/料",
								 "实际",
								 "标准",
								 "料肉比"];


		var headName1 = ["周龄",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "料肉比"];

		var headName2 = ["",
						 "",
						 "死淘",
						 "",
						 "",
						 "采食",
						 "",
						 "饮水",
						 "",
						 "体重",
						 "",
						 ""];

		var headName3 = ["",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "",
						 "饮水",
						 "",
						 "体重",
						 "",
						 ""];
		
			var TableKey = ["age",
							"dc_act_num",
							"dc_act_rate",
							"dc_stan_rate",
							"intake_act_sum",
							"intake_act_sig",
							"intake_stan_sig",
							"water_act_sum",
							"ratio_water_feed",
							"body_weight_actual",
							"body_weight_standard",
							"ratio_intake_body"];

			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '40',
						'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:40px;height:80px;background:#FFF;border-right:solid 1px #AEAEAE !important;'><p style='position:relative; width:100%; height:100%; top:15px; text-align:center;'>" + headName1[i] + "</p></div>",
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 1||i == 2||i == 3||i == 4||i == 5||i == 6) {

					if (i == 2||i == 5) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  "<div style='position:relative;top:0px;left:0px; width:50px;height:55px;background:red;'><div style='position:relative;width:100%;height:50%;top:0%;background:#FFF;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + headName2[i] + "</p></div><div style='position:relative;width:100%;height:50%;top:0%;background:#FFF;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + TableShowName[i] + "</p></div></div>",
							'cellTemplate'        :  '',
							'headerCellClass'     :  'twoLevelTitleStyle'
						})
					}else{

						if (i == 3 || i == 6) {
							header.push({
								'name'                :  TableKey[i],
								'width'               :  '50',
								'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:50px;height:80px;background:#FFF;border-right:solid 1px #AEAEAE !important;'><p style='position:relative; width:100%; height:100%; top:28px; text-align:center;'>" + TableShowName[i] + "</p></div>",
								'cellTemplate'        :  '',
								'headerCellClass'     :  'twoLevelTitleStyle'
							})
						}else{
							header.push({
								'name'                :  TableKey[i],
								'width'               :  '55',
								'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:55px;height:80px;background:#FFF;'><p style='position:relative; width:100%; height:100%; top:28px; text-align:center;'>" + TableShowName[i] + "</p></div>",
								'cellTemplate'        :  '',
								'headerCellClass'     :  'twoLevelTitleStyle'
							})
						}
					}
				}else if (i == 7 || i == 8 || i == 9 || i == 10) {

					if (i == 7) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '70',
							'headerCellTemplate'  :  "<div style='position:relative;top:0px;left:0px; width:70px;height:55px;background:#FFF;'><div style='position:relative;width:100%;height:50%;top:0%;left:50%; background:#FFF; z-index:100;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + headName2[i] + "</p></div><div style='position:relative;width:100%;height:50%;top:0%;background:#FFF;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + TableShowName[i] + "</p></div></div>",
							'cellTemplate'        :  '',
							'headerCellClass'     :  'twoLevelTitleStyle'
						})
					}else if (i == 9) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  "<div style='position:relative;top:0px;left:0px; width:50px;height:55px;background:#FFF;'><div style='position:relative;width:100%;height:50%;top:0%;left:50%; background:#FFF; z-index:100;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + headName2[i] + "</p></div><div style='position:relative;width:100%;height:50%;top:0%;background:#FFF;'><p style='position:relative; width:100%; height:100%; top:0px; text-align:center;'>" + TableShowName[i] + "</p></div></div>",
							'cellTemplate'        :  '',
							'headerCellClass'     :  'twoLevelTitleStyle'
						})
					}else{
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:50px;height:80px;background:#FFF;border-right:solid 1px #AEAEAE !important;''><p style='position:relative; width:100%; height:100%; top:28px; text-align:center;'>" + TableShowName[i] + "</p></div>",
							'cellTemplate'        :  '',
							'headerCellClass'     :  'twoLevelTitleStyle'
						})
					}
				}else if (i == 11) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '50',
						'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:50px;height:80px;background:#FFF;border-left:solid 0px #AEAEAE !important;'><p style='position:relative; width:100%; height:100%; top:15px; text-align:center;'>" + headName1[i] + "</p></div>",
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				} else{
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '50',
						'headerCellTemplate'  :  "<div style='position: relative;top:0px;left:0px; width:50px;height:80px;background:blue;'></div>",
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}
			}


			TempshowTableData = {
				'header'      :header,
				'firstFixed'  :firstFixed,
				'rowHeight'   :rowHeight,
				'TableData'   :$scope.weeklyData.TableData
			};
			var showTableData = TempshowTableData;
			$scope.gridOptions = {
			    rowHeight: showTableData.rowHeight,
			    enableSorting: false
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
	                pinnedLeft          :  true                                        ,
	                enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												        if (row.entity.age % 7 == 0 && row.entity.age != 0) {
												            return 'UnderLineLineRightStyle';
												        }else{
												          	return 'LineRightCellStyle';
												        }
												    }
	            });
		    }else if (i == 3 || i == 6 || i == 10) {
				$scope.gridOptions.columnDefs.push({ 
	                        name                :  showTableData.header[i].name                ,  
	                        displayName         :  showTableData.header[i].displayName         , 
	                        width               :  showTableData.header[i].width               ,
	                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
	                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
	                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
	                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
	                        enableColumnMenu    :  false                                       ,
	                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
											          	if (row.entity.age % 7 == 0 && row.entity.age != 0) {
											            	return 'UnderLineLineRightCustomColorStyle';
											          	}else{
											          		return 'LineRightCellCustomColorStyle';
											          	}
											        }
												});
			}else{
		      	$scope.gridOptions.columnDefs.push({ 
		            name                :  showTableData.header[i].name                ,  
		            displayName         :  showTableData.header[i].displayName         , 
		            width               :  showTableData.header[i].width               ,
		            headerCellClass     :  showTableData.header[i].headerCellClass     ,
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
		            enableColumnMenu    :  false                                       ,
		            cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
		            										if (row.entity.age % 7 == 0 && row.entity.age != 0) {
												            	return 'UnderLineLineRightStyle';
												          	}else{
												          		return 'LineRightCellStyle';
												          	}
												        }

		        });
		    };
		  }
		$scope.gridOptions.data = showTableData.TableData;
	}



	$scope.judgeHouse = function(){
    	$scope.inquire();
    }
	
	$scope.inquire = function(){
		var TempObj = {};
		if (Object.prototype.toString.call($scope.weeklyData.selectHouse) === "[object String]") {
			TempObj = JSON.parse($scope.weeklyData.selectHouse);
			$scope.weeklyData.selectHouse = TempObj;
		}
		var params = {
			"FarmBreedId"  :  $scope.weeklyData.FarmBreedId,
			"HouseId"      :  $scope.weeklyData.selectHouse.HouseId
		};
		Sparraw.ajaxPost('report/queryWeekReport.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.weeklyData.TableData = data.ResponseDetail.reportInfo;
				$scope.getTable();
			}else{
				$scope.weeklyData.TableData = [];
				$scope.getTable();
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
		$scope.getTable();
	}
	setLandscape(true,true);//横屏
	$scope.gridOptions = {};
	setTimeout(function() {
		Sparraw.intoMyController($scope, $state);
		$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
		$scope.setData();
	}, 500);
	

})
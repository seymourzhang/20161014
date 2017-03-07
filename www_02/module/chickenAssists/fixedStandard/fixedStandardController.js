angular.module('myApp.fixedStandard', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//固定标准
.controller("fixedStandardCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	$scope.gridOptionsDiv = false;

	$scope.fixeStanData = {
		"title"       :"",
		"StandardName":"",
   		"FarmId"      :$scope.sparraw_user_temp.farminfo.id,
   		"TableData"   :[]
	}


	$scope.fixeStanData.title = "固定标准";
	switch(persistentData.standardType){
			case "科宝(2015)":
			  	$scope.fixeStanData.title = "科宝(2015)";
			  	$scope.fixeStanData.StandardName = 10001;
			  break;
			case "AA+(2014)":
			  	$scope.fixeStanData.title = "AA+(2014)";
			  	$scope.fixeStanData.StandardName = 10002;
			  break;
			case "罗斯(2014)":
			  	$scope.fixeStanData.title = "罗斯(2014)";
			  	$scope.fixeStanData.StandardName = 10003;
			  break;
			case "正大笼养":
			  	$scope.fixeStanData.title = "正大笼养";
			  	$scope.fixeStanData.StandardName = 10004;
			  break;
			default:
				$scope.fixeStanData.title = "正大平养";
				$scope.fixeStanData.StandardName = 10005;
	};


	$scope.gridOptions = {};

	$scope.GetTable = function(){
		var TempshowTableData = {};
		var header = [];
		var firstFixed = true; //首列是否固定ture-固定，false-不固定
		var rowHeight  = '';//内容高度
		var TableData  = $scope.fixeStanData.TableData;
		var TableKey = [];

		if ($scope.fixeStanData.title == "正大平养" ||
			$scope.fixeStanData.title == "正大笼养") {
			var TableShowName = ["日龄","累计死淘(%)","体重(g)","日增重(g)","日采食(g)","累计采食(g)","累计料肉比"];
			TableKey = ["growth_age","acc_cdreate1","body_weight1","daily_bddiff1","daily_feed1","acc_feed1","FCR1"];
			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '36',
							'headerCellTemplate'  :  '<div style="width:35px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}else if (i == 1) {
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '81',
							'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-right:solid 0.5px rgba(174, 174, 174, 1); "><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}else{
						header.push({
							'name'                :  TableKey[i],
							'width'               :  '81',
							'headerCellTemplate'  :  '<div style="width:80px;height:45px; background:#FFF;border-left:solid 0.5px #ECECEC; border-right:solid 0.5px #ECECEC;"><p class="middle" style="position: relative; top:15px;">' + TableShowName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'cellClass'           :  'middle' 
						})
					}
			}
			
		}else{
			var TableShowName = ["日龄",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比",
								 "NULLS",
								 "体重(g)",
								 "日增重(g)",
								 "日采食(g)",
								 "累计采食(g)",
								 "累计料肉比"];

			var TableOneLevelTitle = ["",
										"公母混养",
										"",
										"",
										"",
										"",
										"",
										"公鸡",
										"",
										"",
										"",
										"",
										"",
										"母鸡"];

			TableKey = ["growth_age",
			            "oneLevelHeader1",
						"body_weight1",
						"daily_bddiff1",
						"daily_feed1",
						"acc_feed1",
						"FCR1",
						"oneLevelHeader2",
						"body_weight2",
						"daily_bddiff2",
						"daily_feed2",
						"acc_feed2",
						"FCR2",
						"oneLevelHeader3",
						"body_weight3",
						"daily_bddiff3",
						"daily_feed3",
						"acc_feed3",
						"FCR3"];
			

			for (var i = 0; i < TableKey.length; i++) {
				if (i == 0) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '50',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 1 || i == 7 || i == 13) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '1',
						'displayName'         :  '',
						'headerCellTemplate'  :  '<div style="position: relative;top:0px;left:0px; width:435px;height:60px; background-image:url("img/newFolder/public/clear_image.png");"><div style="position: relative;top:0px;left:0px; width:100%;height:50%; background:#FBFBFB; border-left:solid 1px #D4D4D4;"><div style="position: relative;top:0px;left:0px; width:100%;height:100%; background:#FBFBFB;"><p class="middle" style="position: relative;top:5px;">' + TableOneLevelTitle[i] + '</p></div></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  ''
					})
				}else if (i == 2 || i == 8 || i == 14) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '70',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 3 || i == 9 || i == 15) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '85',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 4 || i == 10 || i == 16) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '85',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 5 || i == 11 || i == 17) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '100',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle'
					})
				}else if (i == 6 || i == 12 || i == 18) {
					header.push({
						'name'                :  TableKey[i],
						'width'               :  '95',
						'displayName'         :  TableShowName[i],
						'headerCellTemplate'  :  '',
						'cellTemplate'        :  '',
						'headerCellClass'     :  'twoLevelTitleStyle' 
					})
				}
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
		            headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		            cellTemplate        :  showTableData.header[i].cellTemplate        ,
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
	}


	$scope.inquire = function(){
		var params = {
			"FarmId"         :    $scope.fixeStanData.FarmId        ,
			"StandardName"   :    $scope.fixeStanData.StandardName    
		};
		Sparraw.ajaxPost('standard/detailQuery.action', params, function(data){
			if (data.ResponseDetail.Result == "Succ") {
				$scope.fixeStanData.TableData = data.ResponseDetail.DetailData;
				$scope.GetTable();
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});

	}
	$scope.inquire();
})

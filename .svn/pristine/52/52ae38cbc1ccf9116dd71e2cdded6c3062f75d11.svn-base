 angular.module('myApp.dailyCumu', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//生产日报(累计)
.controller("dailyCumuCtrl",function($scope, $state, $http,$ionicModal, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setLandscape(true,true);

	if (DeviInfo.ScreenHeight > DeviInfo.ScreenWidth) {
		document.getElementById('dailyCumu_DIV').style.height = (DeviInfo.ScreenHeight - 75) + 'px';
	}else{
		document.getElementById('dailyCumu_DIV').style.height = (DeviInfo.ScreenWidth - 75) + 'px';
	}
	$scope.dailyCumuData = {
		"farmBreedId": $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"selectHouse":$scope.sparraw_user_temp.userinfo.houses[0],
		"cur_amount":"",
        "original_amount":"", 
		"dataInfo":"",
		"title":"生产日报(累计)",
		"date":"",
		"survRate":""
	}
	var dayNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");  
	Stamp = new Date();  
	$scope.dailyCumuData.date = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";
	$scope.TotalGridOptions = {};


	if (persistentData.dailySelectHouse == "" || !persistentData.dailySelectHouse) {
		
	}else{
		$scope.dailyCumuData.selectHouse = JSON.parse(persistentData.dailySelectHouse);
	}


	$scope.goDailyDay = function(){
		$state.go('dailyDay',{ animation: 'slide-in-up'});
	}

	$scope.goDailyTable = function(){
		if (selectBackPage.reportingBack == "prodReco") {
			$state.go('prodReco',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";	
		}else{
			$state.go('dailyTable',{ animation: 'slide-in-up'});
			selectBackPage.reportingBack = "";
		}
	}

	$scope.GetTotalTable = function(){
			//累计表
			var TotalTableKey = [];
			var Totalheader = [];
			/*var TotalTitleName = ["日龄","死亡","淘汰","死淘","死淘率","标准","采食","只耗","标准","料肉比","标准","饮水","水料比"];
			TotalTableKey = ["day_age","acc_death_num","acc_culling_num","acc_dc_num","accdc_rate_act","accdc_rate_sta","acc_intake_sum","acc_intake_sig","acc_intake_sta","feed_body_act","feed_body_sta","acc_warter_sum","ratio_water_feed",];*/

			var TotalTitleName = ["日龄",
								  "死亡",
								  "淘汰",
								  "死淘",
								  "死淘率",
								  "标准",
								  "采食",
								  "只耗",
								  "标准",
								  "饮水",
								  "水料比"];
			TotalTableKey = ["day_age",
							 "acc_death_num",
							 "acc_culling_num",
							 "acc_dc_num",
							 "accdc_rate_act",
							 "accdc_rate_sta",
							 "acc_intake_sum",
							 "acc_intake_sig",
							 "acc_intake_sta",
							 "acc_warter_sum",
							 "ratio_water_feed"];

			//表头部分
			for (var i = 0; i < TotalTableKey.length; i++) {
				if (i == 0 ||i == 5 ||i == 8 ||i == 10 ||i == 12) {

					if (i == 0) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '30',
							'headerCellTemplate'  :  '<div style="width:30px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 5) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 8 ||i == 10) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 12) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else{
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '58',
							'headerCellTemplate'  :  '<div style="width:58px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}
				}else if (i == 4 || i == 9 || i == 12) {


					if (i == 4) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '50',
							'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else if (i == 9) {
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '65',
							'headerCellTemplate'  :  '<div style="width:65px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}else{
						Totalheader.push({
							'name'                :  TotalTableKey[i],
							'width'               :  '68',
							'headerCellTemplate'  :  '<div style="width:68px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
							'cellTemplate'        :  '',
							'headerCellClass'     :  '',
							'enableCellEdit'      :  false
						})
					}

					
				}else if (i == 7) {
					Totalheader.push({
						'name'                :  TotalTableKey[i],
						'width'               :  '55',
						'headerCellTemplate'  :  '<div style="width:55px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false
					})
				}else{
					Totalheader.push({
						'name'                :  TotalTableKey[i],
						'width'               :  '55',
						'headerCellTemplate'  :  '<div style="width:55px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + TotalTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false
					})
				}
			}

			var TotalTableData = {
			    'header' : Totalheader,
			    'firstFixed': true, //首列是否固定ture-固定，false-不固定
			    'rowHeight' : 25,//内容高度
			    'TableData' : $scope.dailyCumuData.dataInfo
			}
			
			var showTableData = TotalTableData;

			$scope.TotalGridOptions = {
				rowHeight: showTableData.rowHeight,
			};
			//表主体部分
			$scope.TotalGridOptions.columnDefs = [];
			  for (var i = 0; i < showTableData.header.length; i++) {
			    if (i == 0 ||i == 5 ||i == 8  ||i == 12) {

					if (i == 0 && showTableData.firstFixed == true) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        pinnedLeft          :  true                                        ,
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
		                    								if (row.entity.day_age) {
			                    								if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
														            return 'fontBoldLineLineRightStyle';
														        }else{
														          	return 'fontBoldUnderlineStyle';
														        }

			                    							}else if (row.entity.day_age == 0) {
			                    								return 'fontBoldUnderlineStyle';
			                    							}

												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
													});
					}else if (i == 5 ||i == 8 ||i == 10) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightCustomColorStyle';
												          }else{
												          	return 'LineRightCellCustomColorStyle';
												          }
												        }
													});
					}else if (i == 12) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightCustomColorStyle';
												          }else{
												          	return 'LineRightCellCustomColorStyle';
												          }
												        }
													});
					}else{
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderLineLineRightStyle';
												          }else{
												          	return 'LineRightCellStyle';
												          }
												        }
													});
					}
				}else if (i == 4 || i == 9 || i == 12) {
					if (i == 4 || i == 9) {
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
					}else{
						$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
					}
				}else if (i == 7) {
					$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
				}else{
					$scope.TotalGridOptions.columnDefs.push({ 
		                        name                :  showTableData.header[i].name                ,  
		                        displayName         :  showTableData.header[i].displayName         , 
		                        width               :  showTableData.header[i].width               ,
		                        headerCellClass     :  showTableData.header[i].headerCellClass     ,
		                        headerCellTemplate  :  showTableData.header[i].headerCellTemplate  ,
		                        cellTemplate        :  showTableData.header[i].cellTemplate        ,
		                        enableCellEdit      :  showTableData.header[i].enableCellEdit      ,//cell是否可以编辑
		                        enableColumnMenu    :  false                                       ,
		                    	cellClass           :  function(grid, row, col, rowRenderIndex, colRenderIndex) {
												          if (row.entity.day_age% 7 == 0 && row.entity.day_age != 0) {
												            return 'UnderlineStyle';
												          }else{
												          	return 'hiddenLineCellStyle';
												          }
												        }
													});
				}
			  }
			  $scope.TotalGridOptions.data = showTableData.TableData;
	}



	$scope.inquire = function(){
		if (typeof($scope.dailyCumuData.selectHouse) == "object") {
			TempHouseId = $scope.dailyCumuData.selectHouse.HouseId;
		}else{
			TempHouseId = JSON.parse($scope.dailyCumuData.selectHouse).HouseId;
		}

		var params = {
			"FarmBreedId":$scope.dailyCumuData.farmBreedId,
			"HouseId":TempHouseId
		};

		Sparraw.ajaxPost('dataInput/accDaiyRP.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.dailyCumuData.dataInfo = data.ResponseDetail.dataInfo;
				$scope.dailyCumuData.cur_amount = data.ResponseDetail.cur_amount;
				$scope.dailyCumuData.original_amount = data.ResponseDetail.original_amount;
				for (var i = 0; i < $scope.dailyCumuData.dataInfo.length; i++) {
					$scope.dailyCumuData.dataInfo[i].dc_rate_actual = $scope.dailyCumuData.dataInfo[i].dc_rate_actual + "%"; 
				}


				if ($scope.dailyCumuData.original_amount == $scope.dailyCumuData.cur_amount) {
					$scope.dailyCumuData.survRate = 100;
				}else{
					$scope.dailyCumuData.survRate = (parseFloat($scope.dailyCumuData.cur_amount / $scope.dailyCumuData.original_amount)*100).toFixed(2);
					if (!Common_isNum($scope.dailyCumuData.survRate) || !isFinite($scope.dailyCumuData.survRate)) {
						$scope.dailyCumuData.survRate = 0;
					}else{

					}
				}





				$scope.GetTotalTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				$scope.dailyCumuData.dataInfo = [];
				$scope.dailyCumuData.cur_amount = "";
				$scope.dailyCumuData.original_amount = "";
				$scope.GetTotalTable();
				Sparraw.myNotice("暂无数据。");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				$scope.dailyCumuData.dataInfo = [];
				$scope.dailyCumuData.cur_amount = "";
				$scope.dailyCumuData.original_amount = "";
				$scope.GetTotalTable();
			};
    	});
	}

	$scope.judgeHouse = function(){
		persistentData.dailySelectHouse = $scope.dailyCumuData.selectHouse;
		$scope.inquire();
	}

	


	$scope.GetTotalTable();
	setTimeout(
        function (){
          $scope.inquire();
        }
    ,1000);


})
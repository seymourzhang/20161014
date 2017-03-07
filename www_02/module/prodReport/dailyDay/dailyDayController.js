 angular.module('myApp.dailyDay', 
        ['ionic','ngCordova','ngTouch',
         'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
         ])
//生产日报(当天)
.controller("dailyDayCtrl",function($scope, $state, $http, $ionicModal,$stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	setLandscape(true,true);
	if (screen.height > screen.width) {
		document.getElementById('dailyDay_DIV').style.height = (screen.height - 75) + 'px';
	}else{
		document.getElementById('dailyDay_DIV').style.height = (screen.width - 75) + 'px';
	}


	$scope.dailyDayData = {
		"farmBreedId": $scope.sparraw_user_temp.farminfo.farmBreedBatchId,
		"selectHouse":$scope.sparraw_user_temp.userinfo.houses[0],
		"cur_amount":"",
        "original_amount":"",  
		"dataInfo":"",
		"title":"生产日报(当天)",
		"date":"",
		"survRate":""
	}
	var dayNames = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");  
	Stamp = new Date();  
	$scope.dailyDayData.date = (Stamp.getMonth() + 1) +"月"+Stamp.getDate()+ "日"+ " " + dayNames[Stamp.getDay()] +"";
	$scope.toDayGridOptions = {};


	if (persistentData.dailySelectHouse == "" || !persistentData.dailySelectHouse) {
		
	}else{
		$scope.dailyDayData.selectHouse = JSON.parse(persistentData.dailySelectHouse);
	}


	$scope.goDailyCumu = function(){
		$state.go('dailyCumu',{ animation: 'slide-in-up'});
	}


	$scope.GetDayTable = function(){
		//表头部分
		var toDayTableKey = [];
		var toDayheader = [];
		/*var toDayTitleName = ["日龄","死亡","淘汰","死淘","死淘率","采食","日耗","标准","饮水","水料比","均重","标准"];
		toDayTableKey = ["day_age",
						 "death_num",
						 "culling_num",
						 "dc_num",
						 "dc_rate_actual",
						 "intake_sum",
						 "intake_sig",
						 "intake_standard",
						 "warter_sum",
						 "ratio_water_feed",
						 "body_weight_actual",
						 "body_weight_standard"];*/

		var toDayTitleName = ["日龄","死亡","淘汰","死淘","死淘率","标准","采食","只日耗","标准","饮水","水料比"];
		toDayTableKey = ["day_age",
							"death_num",
							"culling_num",
							"dc_num",
							"dc_rate_actual",
							"dc_rate_standard",
							"intake_sum",
							"intake_sig",
							"intake_standard",
							"warter_sum",
							"ratio_water_feed"];



		for (var i = 0; i < toDayTableKey.length; i++) {
			if (i == 0  || i == 5 || i == 7 ||i == 9 ||i == 11) {
				
				if (i == 0) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '30',
						'headerCellTemplate'  :  '<div style="width:30px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else if (i == 5) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '60',
						'headerCellTemplate'  :  '<div style="width:60px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else if (i == 7) {
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '60',
						'headerCellTemplate'  :  '<div style="width:60px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}else{
					toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '60',
						'headerCellTemplate'  :  '<div style="width:60px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC;border-right:solid 0.5px #AEAEAE; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})
				}
			}else{
				toDayheader.push({
						'name'                :  toDayTableKey[i],
						'width'               :  '50',
						'headerCellTemplate'  :  '<div style="width:50px;height:30px; background:#FFF;border-left:solid 0.5px #ECECEC; "><p class="middle" style="position: relative; top:5px;">' + toDayTitleName[i] + '</p></div>',
						'cellTemplate'        :  '',
						'headerCellClass'     :  '',
						'enableCellEdit'      :  false,
					})

			}
		}


		var toDayTableData = {
		    'header' : toDayheader,
		    'firstFixed': true, //首列是否固定ture-固定，false-不固定
		    'rowHeight' : 25,//内容高度
		    'TableData' : $scope.dailyDayData.dataInfo
		}
		
		var showTableData = toDayTableData;

		$scope.toDayGridOptions = {
			rowHeight: showTableData.rowHeight,
		};
		$scope.toDayGridOptions.columnDefs = [];


		//表主体部分
		  for (var i = 0; i < showTableData.header.length; i++) {
		  	if (i == 0 || i == 4 ||i == 5 || i == 7 || i == 8 ||i == 9 ||i == 11) {
				
				
				if (i == 0 && showTableData.firstFixed == true) {
					$scope.toDayGridOptions.columnDefs.push({ 
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
				}else if (i == 5 || i == 8) {
					$scope.toDayGridOptions.columnDefs.push({ 
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
					$scope.toDayGridOptions.columnDefs.push({ 
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
			}else{
				$scope.toDayGridOptions.columnDefs.push({ 
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
		  $scope.toDayGridOptions.data = showTableData.TableData;
	}






	$scope.inquire = function(){
		if (typeof($scope.dailyDayData.selectHouse) == "object") {
			TempHouseId = $scope.dailyDayData.selectHouse.HouseId;
		}else{
			TempHouseId = JSON.parse($scope.dailyDayData.selectHouse).HouseId;
		}

		var params = {
			"FarmBreedId":$scope.dailyDayData.farmBreedId,
			"HouseId":TempHouseId
		};

		Sparraw.ajaxPost('dataInput/curDailyRP.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.dailyDayData.dataInfo = data.ResponseDetail.dataInfo;
				$scope.dailyDayData.cur_amount = data.ResponseDetail.cur_amount;
				$scope.dailyDayData.original_amount = data.ResponseDetail.original_amount;
				if ($scope.dailyDayData.original_amount == $scope.dailyDayData.cur_amount) {
					$scope.dailyDayData.survRate = 100;
				}else{
					$scope.dailyDayData.survRate = (parseFloat($scope.dailyDayData.cur_amount / $scope.dailyDayData.original_amount)*100).toFixed(2);
					if (!Common_isNum($scope.dailyDayData.survRate) || !isFinite($scope.dailyDayData.survRate)) {
						$scope.dailyDayData.survRate = 0;
					}else{

					}
				}

				
				$scope.GetDayTable();
			}else if (data.ResponseDetail.Result == "Fail") {
				$scope.dailyDayData.dataInfo = [];
				$scope.dailyDayData.cur_amount = "";
				$scope.dailyDayData.original_amount = "";
				$scope.GetDayTable();
				Sparraw.myNotice("暂无数据。");
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
				$scope.dailyDayData.dataInfo = [];
				$scope.dailyDayData.cur_amount = "";
				$scope.dailyDayData.original_amount = "";
				$scope.GetDayTable();
			};
    	});
	}

	$scope.judgeHouse = function(){
		persistentData.dailySelectHouse = $scope.dailyDayData.selectHouse;
		$scope.inquire();
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

	$scope.GetDayTable();
	setTimeout(
        function (){
          $scope.inquire();
        }
    ,1000);

})
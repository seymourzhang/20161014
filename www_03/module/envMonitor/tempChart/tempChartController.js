angular.module('myApp.tempChart', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//  温度曲线图
.controller("tempChartCtrl",function($scope, $state, $http, $ionicSlideBoxDelegate, $stateParams, $ionicSideMenuDelegate, crisisServiceFactory, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(true,true);

	$scope.initData = function(){
		document.getElementById('tempChart_DIV').style.height = (screen.width -75) + 'px';
		$scope.chartData = {
			"selectedHouseId"    :  ""  ,
			"selectedBatchId"    :  ""  ,
			"breedIdArray"       :  []  ,
			"farmId"             :  ""  ,
			"charType"           :  ""  ,//图表类型
			"assignOn"           :  ""  ,//是否选中
			"selectedTime"       :  ""  ,//选择时间
			"turn"               :  ""  ,//转向
			"DataDate"           :  ""  ,//服务器返回的时间
			"selectedChartTime"  :  ""  ,//用户选择的时间
			"NavTitle"           :  ""  ,
			"firstTimeDate"      :  ""  
		};
		$scope.chartData.farmId = $scope.sparraw_user_temp.farminfo.id;

		setTimeout(function() {
			$scope.getBatch();
		}, 1500);
	};

	$scope.getBatch = function(){
		//获取批次信息
		var params = {
			"FarmId"   :  $scope.chartData.farmId
		};
		Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				//获取批次列表
				$scope.chartData.breedIdArray = data.ResponseDetail.FarmBreedIdArray;
				//选中默认搜索批次
				for(var key in $scope.chartData.breedIdArray){
					$scope.chartData.selectedBatchId = key;
				}
				//选中默认栋舍批次号
				$scope.chartData.selectedHouseId = $scope.sparraw_user_temp.userinfo.houses[0].HouseId                   ;
				$scope.chartData.charType = "02";
				$scope.defaultChart();
			}else if (data.ResponseDetail.Result == "Fail") {
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			}else{
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		});
	};

	$scope.switchCondition = function(Condition){
		if (Condition == "House") {
			$scope.chartData.selectedHouseId = JSON.parse($scope.chartData.selectedHouseId).id;
		}
		if ($scope.chartData.charType == "03") {
			$scope.chartData.charType = "02";
		}
		$scope.defaultChart();
	}

	$scope.defaultChart = function(){
		$scope.chartData.assignOn        = "N"   ;
		$scope.chartData.selectedTime    = ""    ;
		$scope.getChart($scope.chartData.selectedBatchId  ,
						$scope.chartData.selectedHouseId  ,
						$scope.chartData.charType         ,
						$scope.chartData.assignOn         ,
						$scope.chartData.selectedTime      );
	}

	//批次、栋舍、类型、是否选中、选择时间
	$scope.getChart = function(FarmBreedId,HouseId,DataType,ReqFlag,DataRange){
		//获取图表数据
		var params = {
			"FarmBreedId"  :  FarmBreedId     ,  //农场批次id
			"HouseId"      :  HouseId         ,  //栋舍id
			"DataType"     :  DataType        ,  //曲线图数据类型 01-日龄曲线；02-小时曲线；03-分钟曲线。
			"ReqFlag"      :  ReqFlag         ,  //varchar型,"Y"-指定参数；"N"-没有指定参数
			"DataRange"    :  DataRange          //选择的时间
		};

		Sparraw.ajaxPost('rep/TempCurve/TempCurveReq.action', params, function(data){
			if (data.ResponseDetail.Result == "Success") {
				$scope.setChartParams(data);
				Echart_initLine02(
					data.ResponseDetail.xAxis,
					$scope.tempLineConfig,
					"温度℃",
					$scope.leftRange,
					true,
					"湿度%",
					$scope.rightRange,
					$scope.touchFun
				);
				$scope.chartData.assignOn = "N";
				$scope.chartData.selectedChartTime = "";
				$scope.btnStyle();
			}else{
				Echart_initLine02(['0'],[{"yName":"0","yData":['0']}]);
				$scope.btnStyle();
				Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			};
		},$scope.timeoutHandle)
	};


	$scope.setChartParams = function(data){
		$scope.chartData.DataDate =  data.ResponseDetail.DataDate;
		//判断标题
		if ($scope.chartData.charType == "02") {
			$scope.chartData.NavTitle =  data.ResponseDetail.data_age;
			if ($scope.chartData.assignOn == "N") {
				$scope.chartData.firstTimeDate = data.ResponseDetail.DataDate;
			}
		}else if ($scope.chartData.charType == "01") {
			$scope.chartData.NavTitle =  "";
		}
		//判断左轴是否要设置最大最小值
		$scope.leftRange = undefined;
		$scope.rightRange = undefined;
		var allData = [];
		for (var i = 0; i < data.ResponseDetail.TempDatas.length; i++) {
			for (var j = 0; j < data.ResponseDetail.TempDatas[i].TempCurve.length; j++) {
				if (data.ResponseDetail.TempDatas[i].TempAreaName != "湿度" && data.ResponseDetail.TempDatas[i].TempAreaName != "室外") {
					allData.push(data.ResponseDetail.TempDatas[i].TempCurve[j]);
				}
			}
		}
		//判断温度是否要设定最小最大值
		if (Math.max.apply(null, allData) > 40 || Math.min.apply(null, allData) < 15) {
			$scope.leftRange = [15,40];
		}else{
			$scope.leftRange = null;
		}
		//隐藏线
		$scope.hiddenLine = [false,false,false,false,false,false];
		for (var i = 0; i < 6; i++) {
			switch($stateParams.area){
		      	case 'Outdoor' :
		      		$scope.hiddenLine[5] = true;
		      	break;
		      	case 'Behind'   :
		      		$scope.hiddenLine[4] = true;
		      		$scope.hiddenLine[3] = true;
		      	break;
		      	case 'Middle'  :
		      		$scope.hiddenLine[2] = true;
		      	break;
		      	case 'Front'  :
		      		$scope.hiddenLine[0] = true;
		      		$scope.hiddenLine[1] = true;
		      	break;
		      	default        :
		      		for (var i = 0; i < $scope.hiddenLine.length; i++) {
		      			if (i == 5) {
		      				$scope.hiddenLine[i] = false;
		      			}else{
		      				$scope.hiddenLine[i] = true;
		      			}
		      		}
		      	break;
		    }
		}

		$scope.tempLineConfig = [];
		for (var i = 0; i < data.ResponseDetail.TempDatas.length; i++) {
			var intoyType      = "" ;
			var intoyAxisIndex = "" ;
			if (data.ResponseDetail.TempDatas[i].TempAreaName == "湿度") {
				intoyType      = "area" ;
				intoyAxisIndex = 1      ;
			}else{
				intoyType      = "line" ;
				intoyAxisIndex = 0      ;
			}
			$scope.tempLineConfig.push({
				"yName"         :  data.ResponseDetail.TempDatas[i].TempAreaName  ,              
                "yData"         :  data.ResponseDetail.TempDatas[i].TempCurve     ,            
                "yType"         :  intoyType                                      ,
                "needSelected"  :  $scope.hiddenLine[i]                           ,                
                "yAxisIndex"    :  intoyAxisIndex                                  
			});
		}
	};

	$scope.touchFun = function(params){
		for (var i = 0; i < params.length; i++) {
			if (!params[i].data) {
				params[i].data = "-";
			}
		}
        $scope.chartData.selectedTime = params[0].name;//将x轴的字赋到判断向上向下的选择中
    	var res = '时间' + ' : ' + params[0].name;//x轴的字
		var tempArray = [];
    	for (var i = 0; i < params.length; i++) {
            tempArray[i] = params[i];
    	}
    	for (var i = 0; i < tempArray.length; i++) {
    		//格式转换
        	if (i % 2 == 0) {
        		res += '<br/>' + tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
        	}else{
        		res +=  tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
        	}
        }
        $scope.chartData.selectedChartTime = params[0].name;
        $scope.chartData.assignOn = "Y";
    	return res;
	};

	$scope.timeoutHandle = function(){
		app_alert("连接超时。");
		Echart_initLine02(['0'],[{"yName":"0","yData":['0']}]);
		$scope.btnStyle();
	}

	$scope.rotating = function(direction){
		$scope.chartData.turn = direction;

		if ($scope.chartData.charType == "01" && $scope.chartData.turn == "up") {
			return Sparraw.myNotice("横轴已到最大粒度。");
		}else{
			if ($scope.chartData.charType == "03" && $scope.chartData.turn == "down") {
				return Sparraw.myNotice("横轴已到最小粒度。");
			}
		}

		/*$scope.chartData.turn 转向
		$scope.chartData.assignOn 是否选中
		$scope.chartData.charType 曲线图类型*/
		if ($scope.chartData.turn == "up") {
			if ($scope.chartData.charType == '02') {
				$scope.chartData.assignOn = "N";
				$scope.chartData.charType = "01";
				$scope.chartData.selectedTime = "";
			}else if ($scope.chartData.charType == '03') {
				$scope.chartData.assignOn = "Y";
	      		$scope.chartData.charType = "02";
	      		$scope.chartData.selectedTime = $scope.chartData.DataDate;
			}
		}else{
			if ($scope.chartData.assignOn == "Y") {
				if ($scope.chartData.charType == '01') {

					$scope.chartData.charType = "02";
					var TempSelectYear = "";
				    var TempSelectDate = $scope.chartData.selectedTime.substr(0, 5);
				    var TempjudgeDate = "2000" + "-" + $scope.chartData.firstTimeDate.substr(5, 5);
				    var TempSelectTime = "2000" + "-" + TempSelectDate;
				    var oDate1 = new Date(TempjudgeDate);
					var oDate2 = new Date(TempSelectTime);
					if(oDate1.getTime() < oDate2.getTime()){
				        TempSelectYear = Number($scope.chartData.firstTimeDate.substr(0, 4)) - 1;
				    }else{
				    	TempSelectYear = new Date().getFullYear();
				    }
				    $scope.chartData.selectedTime = TempSelectYear + "-" + TempSelectDate;

				}else if ($scope.chartData.charType == '02') {

					$scope.chartData.charType = "03";
					var selectedTime = $scope.chartData.DataDate;
					selectedTime += " ";
					selectedTime += $scope.chartData.selectedChartTime;
		      		$scope.chartData.selectedTime = selectedTime;

				}
			}else{
				if ($scope.chartData.charType == '01') {
					$scope.chartData.charType = "02";
				}else if ($scope.chartData.charType == '02') {
					$scope.chartData.charType = "03";
			      	$scope.chartData.selectedTime = $scope.chartData.DataDate;
				}
			}
		}
		$scope.getChart($scope.chartData.selectedBatchId  ,
						$scope.chartData.selectedHouseId  ,
						$scope.chartData.charType         ,
						$scope.chartData.assignOn         ,
						$scope.chartData.selectedTime      );
	};

	$scope.btnStyle = function(){
		if ($scope.chartData.charType == "02") {
			document.getElementById('leftBtnName').innerHTML     = "日龄"     ;
			document.getElementById('rightBtnName').innerHTML    = "分钟"     ;
			document.getElementById('rightBtn').style.background = "#33CD5F" ;
			document.getElementById('leftBtn').style.background  = "#33CD5F" ;
		}else{
			if ($scope.chartData.charType == "01" && $scope.chartData.turn == "up") {
				document.getElementById('leftBtnName').innerHTML     = ""        ;
				document.getElementById('rightBtnName').innerHTML    = "小时"     ;
				document.getElementById('leftBtn').style.background  = "#ECECEC" ;
				document.getElementById('rightBtn').style.background = "#33CD5F" ;
			}else{
				if ($scope.chartData.charType == "03" && $scope.chartData.turn == "down") {
					document.getElementById('leftBtnName').innerHTML     = "小时"     ;
					document.getElementById('rightBtnName').innerHTML    = ""        ;
					document.getElementById('rightBtn').style.background = "#ECECEC" ;
					document.getElementById('leftBtn').style.background  = "#33CD5F" ;
				}
			}
		}
	}


	$scope.initData();
})
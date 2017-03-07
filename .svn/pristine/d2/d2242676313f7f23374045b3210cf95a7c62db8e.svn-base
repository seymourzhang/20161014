angular.module('myApp.reportTempHumi', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
// 温湿度综合报表
.controller("reportTempHumiCtrl",function($scope, $state, $http, $stateParams, $ionicPopup, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));
	setLandscape(true,true);   
    $scope.back = function(){
    	$state.go("envMonitoring");
    }

	$scope.tempChartData = {
		"batchTable"        :  ""                           ,//批次表
		"selectedHouseId"   :  $scope.sparraw_user_temp.houseinfos[0].id,//选中的栋舍id
		"selectedBatch"     :  ""                           ,//选中批次的key
		"selectedBatchVal"  :  ""                           //选中批次的val
	}
   //获取批次信息
	var params = {
		"FarmId"   :  $scope.sparraw_user_temp.farminfo.id 
	};

	


	document.getElementById('TempHumi_DIV').style.height = (screen.width - 75) + 'px';

	Sparraw.ajaxPost('sys/code/getFarmBreedData.action', params, function(data){
		if (data.ResponseDetail.Result == "Success") {
			$scope.tempChartData.batchTable = data.ResponseDetail.FarmBreedIdArray;
			//获取key
			for(var key in $scope.tempChartData.batchTable){
			    $scope.tempChartData.selectedBatch = key;
			}
			//获取value  
			for(var item in $scope.tempChartData.batchTable){  
		        if(item==key){  
		            $scope.tempChartData.selectedBatchVal = $scope.tempChartData.batchTable[item];
		        }  
		    }  
            $scope.switchBatch();
		}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
		};
	});
	var tFarmCode ;
	var tBatchNum;
	var myChart ;
	var option ;
	var TNata ;
	var kData ;
	var yData1 ;
	var yData2 ;
	var xData ;
	$scope.switchBatch= function(){
		var params = {
			"FarmBreedId"   : $scope.tempChartData.selectedBatch,
			"HouseId"       : $scope.tempChartData.selectedHouseId
		};

		console.log(params);
		Sparraw.ajaxPost('rep/TempHumi/TempHumiReq.action', params, function(data){
			if (data.ResponseDetail.Result == "Success"){
			 kData  = new Array(data.ResponseDetail.THDatas.length);
			 TNata;
			 yData1 =new Array(data.ResponseDetail.THDatas.length);
			 yData2 = new Array(data.ResponseDetail.THDatas.length);
			 xData  = new Array(data.ResponseDetail.THDatas.length);
				for (var i = data.ResponseDetail.THDatas.length - 1; i >= 0; i--){
					 TNata = new Array(4);
					TNata[0] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[1] = data.ResponseDetail.THDatas[i].MaxTemp;
					TNata[2] = data.ResponseDetail.THDatas[i].MinTemp;
					TNata[3] = data.ResponseDetail.THDatas[i].MaxTemp;
					kData[i] = TNata;
				   	yData1[i] = data.ResponseDetail.THDatas[i].TarTemp;
					yData2[i] = data.ResponseDetail.THDatas[i].Humi;
				}
				if(data.ResponseDetail.xAxis.length>46){
                   xData = data.ResponseDetail.xAxis;
				}else{
				   xData = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45];
				}

				if (Object.prototype.toString.call($scope.tempChartData.selectedHouseId) === "[object String]") {

				}else{
					$scope.tempChartData.selectedHouseId = JSON.stringify($scope.tempChartData.selectedHouseId);
				}
				

			    return $scope.goecharts();
			}else{
			Sparraw.myNotice(data.ResponseDetail.ErrorMsg);
			return $scope.goecharts();
			};
		});
	}


	var  tTitleName = "温度湿度综合表";
	var  tLegend = ['平均温度(℃)','目标温度(℃)','湿度%'];
	var  serialsName1 = "平均温度(℃)";
	var  serialsName2 = '目标温度(℃)';
	var  serialsName3 = "湿度%";
    
	$scope.goecharts = function (){ 

	myChart = echarts.init(document.getElementById('main'));
	      option = {
				tooltip : {
	                trigger: 'axis',

	                textStyle:{
	                  fontSize:13
	            	},
	            	backgroundColor: 'rgba(96,96,96,0.5)' ,//显示框的颜色
	                formatter: function (params) {
	                	var res = '日龄：' +params[0].name;
	                	if (params[0].value[3]!=null) {
	                		res += '<br/>  最高温度 : ' + params[0].value[1] + '℃';
	                	}else{
                            res += '<br/>  最高温度 : - ℃';
	                	};
						if (params[0].value[2]!=null) {
	                		 res += '<br/>  最低温度 : ' + params[0].value[0] + '℃';
	                	}else{
	                		 res += '<br/>  最低温度 : - ℃';
	                	};		
	                    if (params[1].value!=null) {
	                		res += '<br/>目标温度' ;
	                        res += ' : ' +params[1].value + '℃';
	                	}else{
	                		res += '<br/>目标温度' ;
	                        res += ' : - ℃';
	                	};
	                	if (params[2].value!=null) {
	                		res += '<br/>湿度' ;
	                        res += ' : ' + params[2].value + '%';
	                	}else{
	                		res += '<br/>湿度' ;
	                        res += ' : - %';
	                	};       
	                    return res;
	                }
	            },
	            grid:
	            {
	                x:50,
	                y:30,
	                x2:50,
	                y2:30,
	                borderColor:'#BBB'
	            },
	            legend: {
	                data:tLegend
	            },
	            xAxis : [
	                {

	                    type : 'category',
	                    data : xData,
	                     splitLine:{show: false},
	                    nameLocation:'start',
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            yAxis : [
	                {
	                	position:'left',
	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          formatter: '{value}°C'
	                      },
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		//color: '#000000',
	                    		width:1
	                    	}
	                    }
	                },
	                {
	                	position:'right',
 	                    type : 'value',
	                    nameTextStyle:{
	                      fontSize:16
	                    },
	                    axisLabel : {
	                          show:true,
	                          formatter: '{value}.0%'
	                      },
	                    splitLine : false,
	                    scale: true,
	                    axisLine:{
	                    	lineStyle:{
	                    		width:1
	                    	}
	                    }
	                }
	            ],
	            series : [
	                {
	                    name:serialsName1,
	                    type:'k',
	                    data:kData,
	                    itemStyle: {
			                normal: {
			                	// color:"#FF3909",
			                    /*lineStyle: {
			                        width:0.5,
			                        opacity: 0.5
			                    }*/

			                    color:"#FFF"
			                }
			            }
	                },
	                {
	                  name:serialsName2,
	                  type:'line',
	                  smooth:true,
	                  //symbol:'none',
	                  symbolSize:1,
	                  data:yData1,
	                    itemStyle: {
			                normal: {
			                	color:"#33CD5F",
			                    lineStyle: {
			                        width:0.5
			                    }
			                }
			            }
	                },
	                {
	                  name:serialsName3,
	                  type:'line',
	                  yAxisIndex: 1,
	                  data:yData2,
	                  smooth:true,
	                   // symbol:'none',
	                   symbolSize:1,
	                    itemStyle: {
			                normal: {
			                	color:"#87CEFA",
			                    lineStyle: {
			                        width:0.5
			                    },

			                    areaStyle: {
			                        // 区域图，纵向渐变填充
			                        color : '#D1EEEE'
			                    }

			                }
			            }
	                }
	            ]
	        };










	      myChart.setOption(option);
	      window.onresize = function(){};

	}


})
/*************线性图表数据****************/
/*
  xData       //x轴数据                                数据样式 = ['1','2','3','4','5'];
  yData       //y轴数据（总数据，包含几条以及所有点的数据）  数据样式:testData = [
                                                                ['10','20','30','40','50'],
                                                                ['50','40','30','20','10'],
                                                                ['60','54','32','12','32']
                                                              ];
  yName       //y轴各条数据的名字                        数据样式:testData = ["数据一","数据二","数据三"];
  yColor      //y轴各条数据的颜色                        数据样式:testData = ['red','blue','#EEEE00'];
  hiddenPara  //需要隐藏的线                            数据样式:testData = ["数据一", false , "数据二", false];
  clickFun    //获取被点击的参数
  yLeft       //Y轴计量单位    
  yRight      //   
  yLeftRange Y轴左边值区间,类型为数组，包含两个值，最小值，最大值 demo:[0,100]
  yRightRange Y轴右边值区间,类型为数组，包含两个值，最小值，最大值 demo : [[0,100]]
  */

function Echart_initLine01(xShaftSumData, yShaftSumData, yName, yColor, 
  hiddenPara, yLeft, yLeftName, clickFun, yLeftRange, yRightRange, yRightShow, showPoint
  ) {
/*
    console.log(JSON.stringify(xShaftSumData));
    console.log(JSON.stringify(yShaftSumData));
    console.log(JSON.stringify(yName));
    console.log(JSON.stringify(yColor));
    console.log(JSON.stringify(hiddenPara));
    console.log(JSON.stringify(yLeft));
    console.log(JSON.stringify(yLeftName));
    console.log(JSON.stringify(clickFun));
    console.log(JSON.stringify(yLeftRange));
    console.log(JSON.stringify(yRightRange));
    console.log(JSON.stringify(yRightShow));
    console.log(JSON.stringify(showPoint));
*/

    var myChart;
    var option;

    var xData;
    var yData;

    var tTitleName;
    var tLegend;
    var serialsName;
    var allYData = [];
    var hiddenData;
    var tHiddenJson = {};
    var yLeft;
    var yLeftName;
    var pointLeng;
    var ylMin, ylMax, yRMax, yRMin;
    if (yLeftRange != undefined) {
        if (yLeftRange[0] != undefined) {
            ylMin = yLeftRange[0];
        }
        if (yLeftRange[1] != undefined) {
            ylMax = yLeftRange[1];
        }
    }
    if (yRightRange != undefined) {
        if (yRightRange[0] != undefined) {
            yRMin = yRightRange[0];
        }
        if (yRightRange[1] != undefined) {
            yRMax = yRightRange[1];
        }
    }

    if (yRightShow == undefined) {
        yRightShow = false;
    } else {

    }

    if (showPoint == true) {
        pointLeng = 1;
    } else {
        pointLeng = 0;
    }

    //定义线的数据
    xData = xShaftSumData; //X轴数据
    yData = yShaftSumData; //Y轴数据
    tTitleName = "曲线图";
    tLegend = yName; //Y轴名字

    //隐藏哪些数据
    for (var i = 0; i < hiddenPara.length; i += 2) {
        tHiddenJson[hiddenPara[i]] = hiddenPara[i + 1];

    }
    hiddenData = {
        data: tLegend,
        selected: tHiddenJson

    };
    

    myChart = echarts.init(document.getElementById('main'));
    option = {
        tooltip: {
            trigger: 'axis',
            textStyle: {
                fontSize: 13
            },
            backgroundColor: 'rgba(96,96,96,0.5)',
            //显示框的颜色
            formatter: clickFun
        },
        legend: hiddenData,
        grid: //表对应上下左右的大小
        {
            x: 50,
            y: 30,
            x2: 50,
            y2: 30

        },
        xAxis: [{

            type: 'category',
            data: xData,
            nameLocation: 'start',
            show: true,
            //是否显示x轴
            axisTick: true,
            axisLabel: {
                //margin:10 //文字与x轴的距离
            },
            splitLine: false,
            axisLine: {
                lineStyle: { //x轴风格
                    color: '#000',
                    width: 0.5
                }
            }
        }],
        yAxis: [{
            name: yLeftName,
            position: 'left',
            type: 'value',
            nameTextStyle: {
                fontSize: 13
            },
            axisLabel: {
                formatter: '{value}' + yLeft //左边的数据
            },
            //splitLine:true,
            scale: true,
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 1
                }
            },
            min: ylMin,
            max: ylMax
        },
        {
            name: yLeftName,
            show: yRightShow,
            position: 'right',
            type: 'value',
            nameTextStyle: {
                fontSize: 13
            },
            axisLabel: {
                //formatter: '{value}%' + yLeft//右边的数据
                formatter: '{value}' + "%"
            },
            splitLine: false,
            scale: true,
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 1
                }
            },
            min: yRMin,
            max: yRMax
        }],
        series: allYData
    };

    //定义线的样式
    for (var i = 0; i < yName.length; i++) {
        serialsName = yName[i];
        if (yName[i] == "标准") {
            allYData[i] = {
                name: yName[i],
                type: 'bar',
                data: yData[i],
                itemStyle: {
                    normal: {
                        color: "#E3E3E3",
                    }
                }
            }
        } else {

            if (yName[i] == "湿度") {
                allYData[i] = {
                    name: yName[i],
                    type: 'line',
                    yAxisIndex: 1,
                    data: yData[i],
                    smooth: true,
                    //是否折线
                    symbolSize: pointLeng,
                    //点得大小
                    itemStyle: {
                        normal: {
                            color: yColor[i],
                            lineStyle: {
                                color: yColor[i],
                                width: 1
                            },

                            areaStyle: {
                                // 区域图，纵向渐变填充
                                color : '#D1EEEE'
                            }

                        }
                    }
                }

            } else {
                allYData[i] = {
                    name: yName[i],
                    type: 'line',
                    yAxisIndex: 0,
                    smooth: true,
                    //是否折线
                    symbolSize: pointLeng,
                    //点得大小
                    data: yData[i],
                    itemStyle: {
                        normal: {
                            color: yColor[i],
                            lineStyle: {
                                color: yColor[i],
                                width: 1
                            }
                        }
                    }
                }
            }

        }
    }

    myChart.setOption(option);
    // window.onresize = myChart.resize;
    window.onresize = function() {};
}







/*************线性图表数据****************/

function Echart_initLine02(
    xShaftSumData  ,    //x轴数据 必填项                       数据样式 = ['1','2','3','4','5'];
    /*  
    displayConfig 是一个JSON配置项(必填),具体定义如下：
        [
            {
                "yName":"标准",                // 数据名称                            必填项
                "yData":['10','20','30'],     // y轴数据                             必填项
                "yType":"bar",                // bar-柱状图 line-曲线图 area-区域图    非必填项（默认值是line)
                "yAxisIndex": 0,              // 0-左轴显示 1-右轴显示                 非必填项（默认值是0)
                "SymbolSize":0,               // 是否折线 0-不折线 1-折线              非必填项（默认值是0)
                "yColor":'#EEEE00',           // 曲线颜色                            非必填项
                "needSelected":true           // 是否选中，true-选中，false-隐藏       非必填项（默认值是true)
            }

        ] 
    */
    displayConfig  ,                                                                 
    leftName       ,    //左轴线名字                                                     非必填项
    leftRange      ,    //左轴线值区间,类型为数组，包含两个值，最小值，最大值 demo:[0,100]      非必填项
    rightShow      ,    //是否显示右轴                                                    非必填项
    rightName      ,    //右轴线名字                                                     非必填项
    rightRange     ,    //右轴线值区间,类型为数组，包含两个值，最小值，最大值 demo:[0,100]      非必填项
    clickFun            //触摸时触发的事件，以及显示淡灰色框样式                              非必填项
  ) {
    if (!xShaftSumData || !displayConfig ) {
        return app_alert("displayConfig数据不全，请检查。");
    }


    if (!leftName) {
        leftName = "";
    }

    if (!rightShow) {
        rightShow = false;
    }

    if (!rightName) {
        rightName = "";
    }
    
    if (!clickFun) {
        clickFun = function (params) {
            for (var i = 0; i < params.length; i++) {
                if (params[i].data == undefined) {
                    params[i].data = "-";
                };
            };

            var res = params[0].name;//x轴的字
            var tempArray = [];
            for (var i = 0; i < params.length; i++) {
                tempArray[i] = params[i];
            };

            for (var i = 0; i < tempArray.length; i++) {
                if (i % 2 == 0) {
                    res += '<br/>' + tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
                }else{
                    res +=  tempArray[i].seriesName + "：" + tempArray[i].data+'&nbsp;&nbsp;';
                }
            };
            return res;                                         
       }
    }

    //  默认颜色列表
    var defaultColorArrays = ['#EE82EE','#FFA500','#CC9933','#CCCC00','#993333','#663366',  '#DC143C','#00FFFF','#00EE76','#D1EEEE'];

    var legendData = [];  // 图例数据名称
    var selectedData = {};  // 选中的数据名称
    for (var i = 0; i < displayConfig.length; i++) {
        legendData.push(displayConfig[i].yName);
        if (displayConfig[i].needSelected != undefined) {
            selectedData[displayConfig[i].yName] = displayConfig[i].needSelected;
        }else{
            selectedData[displayConfig[i].yName] = true;
        }
    }

    // 图例数据
    var legendArray = {
        data: legendData,
        selected: selectedData
    };
    
    var ylMin, ylMax, yRMax, yRMin;  // 左右Y轴的范围值

    if (leftRange && leftRange.length != 2) {
        return app_alert("leftRange数据有误，请检查。");
    }

    if (leftRange && leftRange.length == 2) {
        if (leftRange[0] != undefined) {
            ylMin = leftRange[0];
        }
        if (leftRange[1] != undefined) {
            ylMax = leftRange[1];
        }
    }

    if (rightRange && rightRange.length != 2) {
        return app_alert("rightRange数据有误，请检查。");
    }

    if (rightRange && rightRange.length == 2) {
        if (rightRange[0] != undefined) {
            yRMin = rightRange[0];
        }
        if (rightRange[1] != undefined) {
            yRMax = rightRange[1];
        }
    }
    
    var seriesDataArray = [];
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        tooltip: {
            trigger: 'axis',
            textStyle: {
                fontSize: 13
            },
            backgroundColor: 'rgba(96,96,96,0.5)',
            //显示框的颜色
            formatter: clickFun
        },
        legend: legendArray,
        grid: //表对应上下左右的大小
        {
            x: 50,
            y: 50,
            x2: 50,
            y2: 30
        },
        xAxis: [{

            type: 'category',
            data: xShaftSumData,
            nameLocation: 'start',
            show: true,
            //是否显示x轴
            axisTick: true,
            axisLabel: {
                //margin:10 //文字与x轴的距离
            },
            splitLine: false,
            axisLine: {
                lineStyle: { //x轴风格
                    color: '#000',
                    width: 0.5
                }
            }
        }],
        yAxis: [{
            name: leftName,
            position: 'left',
            type: 'value',
            nameTextStyle: {
                fontSize: 10
            },
            axisLabel: {
                formatter: '{value}' //左边的数据
            },
            scale: true,
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 1
                }
            },
            min: ylMin,
            max: ylMax
        },{
            name: rightName,
            show: rightShow,
            position: 'right',
            type: 'value',
            nameTextStyle: {
                fontSize: 10
            },
            axisLabel: {
                formatter: '{value}' //右边的数据
            },
            scale: true,
            splitLine: false,//是否显示分割线
            axisLine: {
                lineStyle: {
                    color: '#000',
                    width: 1
                }
            },
            min: yRMin,
            max: yRMax
        }],
        series: seriesDataArray
    };
    
    for (var i = 0; i < displayConfig.length; i++) {

        //  默认值
        var mmAreaStyle = null;
        var mmYAxisIndex = 0;
        var mmType = 'line'; 
        var mmYColour = null;  // 线颜色
        var mmSymbolSize = 0;

        // 设定左轴还是右轴
        if (displayConfig[i].yAxisIndex) {
            mmYAxisIndex  = displayConfig[i].yAxisIndex;
        }

        // 设定图表形态
        if (displayConfig[i].yColor) {
            mmYColour = displayConfig[i].yColor;
        }else{
            mmYColour = defaultColorArrays[i];
        }

        //设置是否折线
        if (displayConfig[i].SymbolSize) {
            mmSymbolSize = displayConfig[i].SymbolSize;
        }

        // 设定图表形态
        if (displayConfig[i].yType) {
            // bar-柱状图 line-曲线图 area-区域图
            if(displayConfig[i].yType == 'area'){
                mmAreaStyle = { "color" : mmYColour} ;
            }else{
                mmType = displayConfig[i].yType;
            }
        }

        seriesDataArray.push({
                    name: displayConfig[i].yName,
                    type: mmType,
                    yAxisIndex: mmYAxisIndex,
                    data: displayConfig[i].yData,
                    smooth: true,
                    //是否折线
                    symbolSize: mmSymbolSize,
                    //点得大小
                    itemStyle: {
                        normal: {
                            color: mmYColour,
                            lineStyle: {
                                color: mmYColour,
                                width: 1
                            },
                            areaStyle: mmAreaStyle
                        }
                    }
        });
    }

    myChart.setOption(option);
    
    window.onresize = function() {};
}
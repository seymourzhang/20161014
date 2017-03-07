angular.module('myApp.ventCompute', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])
//最小通风计算器
.controller("ventComputeCtrl",function($scope, $state, $http, AppData) {
	Sparraw.intoMyController($scope, $state);
	$scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

	$scope.setData = function(){
		$scope.ventComputeData = persistentData.ventComputeData;

		console.log($scope.ventComputeData.ammonia);
		if ($scope.ventComputeData.ammonia == 0) {
			$scope.ventComputeData.ammonia = false;
		}else{
			$scope.ventComputeData.ammonia = true;
		}

		if ($scope.ventComputeData.humidity == 0) {
			$scope.ventComputeData.humidity = false;
		}else{
			$scope.ventComputeData.humidity = true;
		}

		if ($scope.ventComputeData.stive == 0) {
			$scope.ventComputeData.stive = false;
		}else{
			$scope.ventComputeData.stive = true;
		}
	};



	$scope.computeFun = function(){

		if ($scope.ventComputeData.ammonia) {
			$scope.ventComputeData.ammonia = 15;
		}else{
			$scope.ventComputeData.ammonia = 0;
		}
		
		if ($scope.ventComputeData.humidity) {
			$scope.ventComputeData.humidity = 15;
		}else{
			$scope.ventComputeData.humidity = 0;
		}

		if ($scope.ventComputeData.stive) {
			$scope.ventComputeData.stive = 15;
		}else{
			$scope.ventComputeData.stive = 0;
		}

		if (!$scope.ventComputeData.ventBlow) {return app_alert("缺少最小通风时控风量,请正确输入。")}
		if (!$scope.ventComputeData.fan) {return app_alert("缺少风机数量,请正确输入。")}
		if (!$scope.ventComputeData.chick) {return app_alert("缺少进鸡数,请正确输入。")}
		if (!$scope.ventComputeData.dayAge) {return app_alert("缺少日龄,请正确输入。")}

		if (isNaN($scope.ventComputeData.ventBlow)) {
            return app_alert("请输入正确的最小通风时控风量。");
        }else if (isNaN($scope.ventComputeData.fan)) {
            return app_alert("请输入正确的风机数量。");
        }else if (isNaN($scope.ventComputeData.chick)) {
            return app_alert("请输入正确的进鸡数。");
        }
        
        //风量*总鸡只数/时控风机的台数/最小通风时控风机的风量(立方米/小时)*300+(氨气+湿度-粉尘)
        var stepsA = accMul($scope.ventComputeData.dayAge,$scope.ventComputeData.chick);//风量*总鸡数
        var stepsB = accDiv(stepsA,$scope.ventComputeData.fan);//除台数
        var stepsC = accDiv(stepsB,$scope.ventComputeData.ventBlow);//除最小通风时控风机的风量(立方米/小时)
        var stepsD = accMul(stepsC,"300");//乘300
        var stepsE = accAdd(stepsD,$scope.ventComputeData.ammonia)//加上氨气
        var stepsF = accAdd(stepsE,$scope.ventComputeData.humidity)//加上湿度
        var stepsG = stepsF - $scope.ventComputeData.stive;//减去粉尘
        $scope.ventComputeData.showSecond = stepsG.toFixed(0);

        persistentData.ventComputeData = $scope.ventComputeData;
        $state.go("ventConclusion");
	}
	$scope.cleanVentComputeData = function(){
		persistentData.ventComputeData = {
			"ventBlow"    :  ""  ,
			"fan"         :  ""  ,
			"chick"       :  ""  ,
			"dayAge"      :  ""  ,
			"ammonia"     :  ""  ,
			"humidity"    :  ""  ,
			"stive"       :  ""  ,
			"showSecond"  :  "-"  
		};
		$scope.ventComputeData = persistentData.ventComputeData;
	}

	function accAdd(arg1,arg2){ //加法
		var r1,r2,m; 
		try{
			r1=arg1.toString().split(".")[1].length
		}catch(e){
			r1=0
		} 
		try{
			r2=arg2.toString().split(".")[1].length
		}catch(e){
			r2=0
		} 
		m=Math.pow(10,Math.max(r1,r2)) 
		return (arg1*m+arg2*m)/m
	}

	function accMul(arg1,arg2){ //乘法
		var m=0,
		s1=arg1.toString(),
		s2=arg2.toString(); 
		try{
			m+=s1.split(".")[1].length
		}catch(e){

		} 
		try{
			m+=s2.split(".")[1].length
		}catch(e){

		} 
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
	} 

	function accDiv(arg1,arg2){ //除法
		var t1=0,
		t2=0,r1,r2; 
		try{
			t1=arg1.toString().split(".")[1].length
		}catch(e){

	 	}
	 	try{
	 		t2=arg2.toString().split(".")[1].length
	 	}catch(e){

	 	} 
 		r1=Number(arg1.toString().replace(".","")) 
 		r2=Number(arg2.toString().replace(".","")) 
		return (r1/r2)*Math.pow(10,t2-t1);
	}



	$scope.setData();
})
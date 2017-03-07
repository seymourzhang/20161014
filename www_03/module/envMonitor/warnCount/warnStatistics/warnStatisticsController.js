angular.module('myApp.warnStatistics', 
		['ionic','ngCordova','ngTouch',
		 'ui.grid','ui.grid.pinning','ui.grid.edit','ui.grid.cellNav', 'ui.grid.validate', 'ui.grid.grouping', 'ui.grid.selection','ui.grid.resizeColumns','ui.grid.autoResize'
		 ])

.controller('warnStatisticsCtrl', function($scope, $state) {
	Sparraw.intoMyController($scope, $state);
    $scope.sparraw_user_temp = JSON.parse(JSON.stringify(sparraw_user));

    $scope.setScreenStateFun = function(){
		setLandscape(true,true);//横屏
		document.getElementById('mainContent').style.top = persistentData.tabHeight + 'px';
		if (DeviInfo.ScreenHeight > DeviInfo.ScreenWidth) {
			document.getElementById('mainContent').style.height = (DeviInfo.ScreenWidth - persistentData.tabHeight) + 'px';
		}else{
			document.getElementById('mainContent').style.height = (DeviInfo.ScreenHeight - persistentData.tabHeight) + 'px';
		}
	};

})
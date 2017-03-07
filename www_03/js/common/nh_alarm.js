/**
* description :播放报警声音的类
*/
var Alarm_Media = function(){
	var local_Media = {};
	var my_media = null;
	var isPlayed = false;
	var playAudio = function(src){
		
		if(my_media == null){
			my_media = new Media(src, onSuccess, onError);
		}
		if(!isPlayed){
			my_media.play();
		}
	};
	
	var onSuccess = function() {
		console.log("playAudio():Audio Success");
		// alert("playAudio():Audio Success");
	};
	var onError = function(error) {
		//alert('code: '    + error.code    + '\n' + 'message: ' + error.message + '\n');
	};
	
	var pauseAudio = function() {
		if (my_media) {
			my_media.pause();
		}
		// alert('pauseAudio');
	}
	var stopAudio = function() {
		if (my_media) {
			my_media.stop();
			my_media.release();
		}
		// alert('stopAudio');
	}
	
	local_Media.playAudio = playAudio;
	local_Media.pauseAudio = pauseAudio;
	local_Media.stopAudio = stopAudio;
	
	return local_Media;
}();


/**
* description :推送本地消息类
*/
var Alarm_Notification = function(){
	var tId = 0;
	var addOneNotification = function(nType,title,text){
		tId ++ ;
		try{
			cordova.plugins.notification.local.schedule({
				id: tId,
				title: title,
				text: text,
				data: {type: nType }
			});
			cordova.plugins.notification.local.on("click", function (notification) {
				var jsonData = JSON.parse(notification.data);
				if(jsonData.type == 'alarm'){
					Alarm_Media.stopAudio();
				}
			});
		}catch(e){
			//console.log(e);
		}
		return tId;
	};
	
	var updateNotification = function(id,title,text){
		try{
			cordova.plugins.notification.local.update({
				id: id,
				title: title,
				text: text
			});
		}catch(e){
			console.log(e);
		}
		return id;
	};
	
	var cancleNotification = function(id) {
		try{
			cordova.plugins.notification.local.cancel(id, function () {
				// Notification was cancelled
				//alert('cancle');
			});
		}catch(e){
			//console.log(e);
		}
	};
	var local_Notification = {};
	local_Notification.addOneNotification = addOneNotification;
	local_Notification.updateNotification = updateNotification;
	local_Notification.cancleNotification = cancleNotification;
	return local_Notification;
}();

var tskId = null;
var tLogId = 0;
var tNotificationID = null;
var tskStatus = false;

var nh_alarm_nowNeedAlarm = 'N';

/**
* description :开启推送消息任务
*/
var Alarm_beginAlarmTask = function(roleId){
  
  if(tskStatus){
      console.log('警报任务已经开启');
  }else{
      Common_wakeLockDim();
      console.log("用户角色:"+roleId); 
      var postAlarmReq = function(){
          tLogId ++ ;
          var params = {
                 "operation":"needAlarm",
                  "uuid":Common_MOBILE_UUID,
                  "model":Common_MOBILE_MODELNAME,
                  "version":Common_MOBILE_VERSION,
                  "platform":Common_MOBILE_PLATFORM 
          };
          Sparraw.ajaxPost('envCtrl/needAlarm.action', params, function(data){
              if (data.ResponseDetail.AlarmStatus == 'Y') {
                  nh_alarm_nowNeedAlarm = "Y";
                  // 推送消息
                  Alarm_Notification.cancleNotification(tNotificationID);
                  tNotificationID = Alarm_Notification.addOneNotification("alarm","智慧鸡场警报","环控数据异常，请点击查看");
                  
                      // 播放音乐
                      var src = "sounds/4611.wav";
                      Alarm_Media.stopAudio();
                      Alarm_Media.playAudio(Common_getMediaURL(src));  
                  
                  console.log("已经报警:"+tLogId); 
               }else {
                  nh_alarm_nowNeedAlarm = "N";
                  if(tNotificationID != null){
                     Alarm_Notification.cancleNotification(tNotificationID);
                  }
                  console.log("数据正常:"+tLogId); 
               };
           });
      };

      postAlarmReq();
      tskId = setInterval(postAlarmReq,30000);
      tskStatus = true;
  }
  console.log("任务开始:"+tskId);
};

/**
* description :关闭推送消息任务
*/
var Alarm_clearTask = function(){
	if(tskId){
	    Alarm_Notification.cancleNotification(tNotificationID);
	    console.log("已经解除:"+tskId);
	    // alert("已经解除:"+tskId);         
	    clearInterval(tskId);
	}
	Common_releaseWakeLock();
	tskStatus = false;
};
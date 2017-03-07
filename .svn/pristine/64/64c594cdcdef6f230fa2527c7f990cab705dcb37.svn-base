
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>智慧鸡场下载</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
	<script type="text/javascript" src="common/easyui-1.4.3/jquery.min.js"></script>
</head>
<body style="overflow: hidden;margin: 0px;">
	<div style="position:absolute; width:100%; height:100%;">  
		<img id= "dlPictureId" height='100%' width='100%'/>
	</div>
	
	<div id="dl_broiler" style="position:absolute;left:0px;top:13%; height:25%;width:35%;" >  
	</div>
	<div id="dl_layer" style="position:absolute;left:0px;top:50%; height:25%;width:35%;" >  
	</div>

	<script type="text/javascript">
		
		function dlAPK(dlUrl){
			window.location.href = dlUrl;
		}
		
		window.onload = function() {
			var userAgent = window.navigator.userAgent.toLowerCase();
			var isIOS = false;
			var isWeChatBroswer = false;
			
			if (userAgent.match(/MicroMessenger/i) == 'micromessenger') {
				isWeChatBroswer = true ;
			}
			if (/iphone|ipad|ipod/.test(userAgent)) {
				isIOS = true;
            }
			
			var dlBroiler_AndroidURL = "http://broiler.agnet.com.cn/sweb/IntelBroiler_release.apk";
			var dlBroiler_IOSURL = "https://itunes.apple.com/cn/app/zhi-hui-nong-chang-rou-ji-ban/id1096496776?l=en&mt=8";
			var dlLayer_AndroidURL = "http://egg.agnet.com.cn/eweb/IntelLayer_release.apk";
			var dlLayer_IOSURL = "https://itunes.apple.com/us/app/zhi-hui-ji-chang-chan-dan-ban/id1159296432?l=en&mt=8";
			
			if(isWeChatBroswer){
				document.getElementById("dl_broiler").style.display = "none";
				document.getElementById("dl_layer").style.display = "none";
				if(isIOS){
					document.getElementById("dlPictureId").src = "common/img/download/ios_dl.png" ;
				}else{
					document.getElementById("dlPictureId").src = "common/img/download/android_dl.png" ;
				}
			}else{
				document.getElementById("dlPictureId").src = "common/img/download/real_dl.png" ;
				if(isIOS){
					document.getElementById("dl_broiler").setAttribute("onclick","dlAPK('" + dlBroiler_IOSURL + "')");
					document.getElementById("dl_layer").setAttribute("onclick","dlAPK('" + dlLayer_IOSURL + "')");
				}else{
					document.getElementById("dl_broiler").setAttribute("onclick","dlAPK('" + dlBroiler_AndroidURL + "')");
					document.getElementById("dl_layer").setAttribute("onclick","dlAPK('" + dlLayer_AndroidURL + "')");
				}
			}
        }
	</script>
</body>
</html>
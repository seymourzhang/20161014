<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>智慧鸡场</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
</head>
<body style="overflow: hidden;margin: 0px;">
	<div id="dlDivId" style="position:absolute; width:100%; height:100%;">  
		<img id= "dlPictureId" height='100%' width='100%'/>
	</div>
	<div id="installGidue" style="position:absolute; height:50px;width:200px;" >  
	</div>

	<script type="text/javascript">
		
		function dlAPK(dlUrl){
			window.location.href = dlUrl;
		}
		
		window.onload = function() {
			var userAgent = window.navigator.userAgent.toLowerCase();
			var isIOS = false;
			var isWeChatBroswer = false;
			var isMobile = false;
			
			if (userAgent.match(/MicroMessenger/i) == 'micromessenger') {
				isWeChatBroswer = true ;
			}
			if (/iphone|ipad|ipod/.test(userAgent)) {
				isIOS = true;
            }
			if(/android|weboS|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(userAgent) ) {
				isMobile = true;
			}
			
			var installGuideURL = "https://mp.weixin.qq.com/s?__biz=MzAwMjU4ODMyNg==&mid=509958431&idx=1&sn=18dbc9b90212e8dd316d599acf642df3&chksm=0159d2d6362e5bc01ac1eedb54e3fbdd72c62432e0a7428fc68dd57491cad4259ae5dd68ef72&mpshare=1&scene=1&srcid=1227JGjS0cstx6itpfnBgoN4&key=564c3e9811aee0ab84f839eb8f4dfefdebfad680b2b9d75b9672456fd1565bce32555c0fd5058247b30e7ad16ee1f458918efe5dcab11071912fae93aa5a8bf86efdd0fe1e156a5eb14265c74dd89206&ascene=0&uin=MTEzMzI0NzA0MA%3D%3D&devicetype=iMac+MacBookPro10%2C1+OSX+OSX+10.12.2+build";
			
			if(isMobile){
				document.getElementById("dlPictureId").src = "mobile_welcome.png" ;
				document.getElementById("dlDivId").style.minWidth = "100px" ;
				document.getElementById("dlDivId").style.minHeight = "200px" ;
				
				document.getElementById("installGidue").style.left = "30%" ;
				document.getElementById("installGidue").style.top = "57%" ;
				document.getElementById("installGidue").setAttribute("onclick","dlAPK('" + installGuideURL + "')");
			}else{
				document.getElementById("dlPictureId").src = "pc_welcome.png" ;
				document.getElementById("dlDivId").style.minWidth = "600px" ;
				document.getElementById("dlDivId").style.minHeight = "400px" ;
				
				document.getElementById("installGidue").style.left = "43%" ;
				document.getElementById("installGidue").style.top = "60%" ;
				
				document.getElementById("installGidue").setAttribute("onclick","dlAPK('" + installGuideURL + "')");
			}
        }
	</script>
</body>
</html>
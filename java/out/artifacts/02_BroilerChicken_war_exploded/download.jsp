<%--
  Created by IntelliJ IDEA.
  User: Ants
  Date: 2016/7/21
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String version = "1.6.2";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
    <div class="download">
    </div>
    <div class="download_head">
   </div>
    <div class="download_broiler">
    </div>
    <div class="download_layer">
    </div>
    <div class="download_cominfo">
    </div>

    <script type="text/javascript">
        window.onload = function() {
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == 'micromessenger') {
                var p = document.getElementsByClassName('download');
//                p[0].innerHTML = window.navigator.userAgent;
                if (/iphone|ipad|ipod/.test(ua)) {
                    p[0].innerHTML = "<img src='img/check_ios.png'; style='position:absolute;top:0%;right:0%;width: 100%;height: 100%;'/>";
                }else{
                    p[0].innerHTML = "<img src='img/check_android.png'; style='position:absolute;top:0%;right:0%;width: 100%;height: 100%;'/>";
                }
            }else{
                var p = document.getElementsByClassName('download_head');
                p[0].innerHTML = "<img src='img/download_head.gif'; style='position:absolute;top:0%;right:0%;width: 100%;height: 35%;'/>";
                var q = document.getElementsByClassName('download_broiler');
                if (/iphone|ipad|ipod/.test(ua)) {
                    console.log("ios");
                    q[0].innerHTML = "<a href='https://itunes.apple.com/cn/app/zhi-hui-nong-chang-rou-ji-ban/id1096496776?l=en&mt=8'><img src='img/download_broiler.gif'; style='position:absolute;top:35%;right:0%;width:100%;height: 20%;'/> </a>";
                }else{
                    console.log("android");
                    q[0].innerHTML = "<a href='nht_broiler_pro_<%=version%>.apk'><img src='img/download_broiler.gif'; style='position:absolute;top:35%;right:0%;width:100%;height: 20%;'/> </a>";
                }
                var a = document.getElementsByClassName('download_layer');
                if (/iphone|ipad|ipod/.test(ua)) {
                    console.log("ios");
                    a[0].innerHTML = "<a href='https://itunes.apple.com/cn/app/zhi-hui-nong-chang-rou-ji-ban/id1096496776?l=en&mt=8'><img src='img/download_layer.gif'; style='position:absolute;top:55%;right:0%;width:100%;height: 20%;'/> </a>";
                }else {
                    console.log("android");
                    a[0].innerHTML = "<a href='nht_layer_pro_<%=version%>.apk'><img src='img/download_layer.gif'; style='position:absolute;top:55%;right:0%;width:100%;height: 20%;'/> </a>";
                }
                var q = document.getElementsByClassName('download_cominfo');
                q[0].innerHTML = "<img src='img/download_cominfo.gif'; style='position:absolute;top:75%;right:0%;width:100%;height: 25%;'/>";
            }
        }
        /*function isWeiXin() {
            var ua = window.navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == 'micromessenger')
                return true;
            } else {
                return false;
            }
        }*/
    </script>
</body>
</html>

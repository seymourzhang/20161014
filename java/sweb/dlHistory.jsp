<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="com.mtc.common.util.HttpRequestUtil" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath：" +  basePath);

String version = "1.6.0" ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>肉鸡历史下载</title>
</head>
<body>
<h1>智慧肉鸡-App历史版本下载</h1>
<h2>Android最新版本(<%=version%>)：<a href="nht_layer_pro_<%=version%>.apk">点击安装</a></h2>
</body>
</html>
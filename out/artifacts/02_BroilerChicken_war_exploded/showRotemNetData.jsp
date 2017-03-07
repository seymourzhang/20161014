<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<base href="<%=basePath%>">
	<title>RotemNet上传数据查询</title>
	<link rel="stylesheet" type="text/css" href="common/easyui-1.4.3/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="common/easyui-1.4.3/themes/icon.css">
	<script type="text/javascript" src="common/easyui-1.4.3/jquery.min.js"></script>
	<script type="text/javascript" src="common/easyui-1.4.3/jquery.easyui.min.js"></script>
<style type="text/css">
body {
*{
	font-size:12px;
}
body {
    font-family:verdana,helvetica,arial,sans-serif;
    padding:20px;
    font-size:12px;
    margin:0;
}
</style>
</head>
<body>
	<table id="House_table" class="easyui-datagrid" title="实时上传数据" style="width:1000px"
		data-options="singleSelect:true,
		collapsible:true,
		rownumbers:true,
		singleSelect:true,
		url:'sys/device/showRotemNetData.action', 
		method:'POST'">
		<thead>
			<tr>
				<th data-options="field:'make_time',width:130,halign:'center'">上传日期</th>
				<th data-options="field:'col_a',width:50,halign:'center'">A</th>
				<th data-options="field:'col_b',width:70,halign:'center'">B</th>
				<th data-options="field:'col_c',width:70,halign:'center'">C</th>
				<th data-options="field:'col_d',width:50,halign:'center'">D</th>
				<th data-options="field:'col_e',width:50,halign:'center'">E</th>
				<th data-options="field:'col_f',width:50,halign:'center'">F</th>
				<th data-options="field:'col_g',width:50,halign:'center'">G</th>
				<th data-options="field:'col_h',width:50,halign:'center'">H</th>
				<th data-options="field:'col_i',width:50,halign:'center'">I</th>
				<th data-options="field:'col_j',width:50,halign:'center'">J</th>
				<th data-options="field:'col_k',width:50,halign:'center'">K</th>
				<th data-options="field:'col_l',width:50,halign:'center'">L</th>
				<th data-options="field:'col_m',width:50,halign:'center'">M</th>
				<th data-options="field:'col_n',width:50,halign:'center'">N</th>
				<th data-options="field:'col_o',width:50,halign:'center'">O</th>
				<th data-options="field:'col_p',width:50,halign:'center'">P</th>
				<th data-options="field:'col_q',width:50,halign:'center'">Q</th>
				<th data-options="field:'col_r',width:50,halign:'center'">R</th>
				<th data-options="field:'col_s',width:50,halign:'center'">S</th>
				<th data-options="field:'col_t',width:50,halign:'center'">T</th>
				<th data-options="field:'col_u',width:50,halign:'center'">U</th>
				<th data-options="field:'col_v',width:50,halign:'center'">V</th>
				<th data-options="field:'col_w',width:50,halign:'center'">W</th>
				<th data-options="field:'col_x',width:50,halign:'center'">X</th>
				<th data-options="field:'col_y',width:50,halign:'center'">Y</th>
				<th data-options="field:'col_z',width:50,halign:'center'">Z</th>
				<th data-options="field:'col_aa',width:50,halign:'center'">AA</th>
				<th data-options="field:'col_ab',width:50,halign:'center'">AB</th>
				<th data-options="field:'col_ac',width:50,halign:'center'">AC</th>
				<th data-options="field:'col_ad',width:50,halign:'center'">AD</th>
				<th data-options="field:'col_ae',width:50,halign:'center'">AE</th>
				<th data-options="field:'col_af',width:50,halign:'center'">AF</th>
				<th data-options="field:'col_ag',width:50,halign:'center'">AG</th>
			</tr>
		</thead>
	</table>
</body>
</html>


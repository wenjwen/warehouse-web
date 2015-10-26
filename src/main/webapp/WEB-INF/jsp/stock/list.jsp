<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table id="stock_dg" title="出入库管理" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
	
</table>
<div id="toolbar" style="padding:5px 5px 5px 5px;">
	<label style="padding:5px 5px 5px 5px;">导入出库记录：</label><input type="file" id="excel" name="excel" accept="application/vnd.ms-excel" onchange="excelUpload();"/><br/>
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stock_dg').edatagrid('addRow',{index:0})">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stock_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stock_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stock_dg').edatagrid('cancelRow')">取消</a> | 
	<span>单号</span>
	<input id="stockNo" class="easyui-textbox" data-options="" style="line-height:26px;border:1px solid #ccc">
	<span>出入库类型</span>
	<input id="stockType" class="easyui-combobox" data-options="valueField:'id',textField:'name',data:stockTypeEntry" style="line-height:26px;border:1px solid #ccc">
	<!-- <span>日期</span>
	<input id="stockTime" class="easyui-datetimebox" style="line-height:26px;border:1px solid #ccc"/> -->
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchStock()">搜索</a><br/>
</div>

<div id="win"></div>

<script type="text/javascript">
	// 获取分类json字符串
	// categoryJson ='${categoryJson}';
</script>
</body>
</html>
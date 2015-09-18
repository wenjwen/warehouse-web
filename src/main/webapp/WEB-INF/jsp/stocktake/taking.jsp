<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table id="stocktakeItem_dg" title="盘点" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchStocktakeItem();">搜索</a> <br> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#material_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#material_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#material_dg').edatagrid('cancelRow')">取消</a>
</div>
</body>
</html>
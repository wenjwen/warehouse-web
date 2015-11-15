<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<input id="stocktakeId"  type="text" value="${stocktake.id}" style="display:none;"/>
<input id="submitted"  type="text" value="${stocktake.submitted}" style="display:none;"/>

<table id="stocktakeItem_dg" title="盘点 - - ${stocktake.name }" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
</table>
<div id="toolbar" style="padding: 5px;">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stocktakeItem_dg').edatagrid('destroyRow')">删除</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stocktakeItem_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stocktakeItem_dg').edatagrid('cancelRow')">取消</a> | 
	<span>物料名</span>
	<input id="materialId" class="easyui-combobox" data-options="valueField:'id',textField:'name'" style="width:280px;line-height:26px;border:1px solid #ccc"/>
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchStocktakeItem();">搜索</a>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
			<input type="text" id="stockIdForItems" value="${stockId }" style="display:none;">
			<input type="text" id="stockTypeForItems" value="${stockType}" style="display:none;">
			
			<table id="stockItem_dg_win"  style="width:100%;height:100%;" toolbar="#item_toolbar"
					idField="id" rownumbers="true" fitColumns="true" singleSelect="false">
			</table>
			<div id="item_toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stockItem_dg_win').edatagrid('addRow',{index:0})">新增</a>
				<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stockItem_dg_win').edatagrid('destroyRow')">删除</a> -->
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stockItem_dg_win').edatagrid('saveRow')">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stockItem_dg_win').edatagrid('cancelRow')">取消</a> 
			</div>
</body>
</html>
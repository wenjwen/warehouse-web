<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
			<input id="importStockType" type="text" value="${importStockType}" style="display:none;">
			<table id="stockItem_import_dg_win"  style="width:100%;height:93%;"
					idField="id" rownumbers="true" fitColumns="true" singleSelect="false">
			</table>
			<!-- <div id="item_toolbar">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stockItem_import_dg_win').edatagrid('addRow',{index:0})">新增</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stockItem_import_dg_win').edatagrid('destroyRow')">删除</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stockItem_import_dg_win').edatagrid('saveRow')">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stockItem_import_dg_win').edatagrid('cancelRow')">取消</a>
			</div>  -->
			<!-- 提交 -->
		<div style="width:100%;height:5%;text-align:center;">
	    	<a  class="easyui-linkbutton" style="margin:10px;width:50px;" href="javascript:void(0)" onclick="submitImportStockItem();">保存</a>
	    	<a  class="easyui-linkbutton" style="margin:10px;width:50px;" href="javascript:void(0)" onclick="javascript:$('#win').window('close');">取消</a>
	  </div>
</body>
</html>
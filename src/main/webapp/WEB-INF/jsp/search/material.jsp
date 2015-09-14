<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table id="search_material_dg" title="库存查询" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
</table>
<div id="toolbar">
	<span>物料名</span>
	<input id="id" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'/warehouse/materialEntry.json'" style="line-height:26px;border:1px solid #ccc"/>
	<span>编号</span>
	<input id="code" class="easyui-textbox"  style="line-height:26px;border:1px solid #ccc"/>
	<span>规格</span>
	<input id="size" class="easyui-textbox"  style="line-height:26px;border:1px solid #ccc"/>
	<span>分类</span>
	<input id="categoryId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'/warehouse/categoryEntry.json'" style="line-height:26px;border:1px solid #ccc"/>
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchMaterial()">搜索</a>
	<!-- 
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#material_dg').edatagrid('addRow')">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#material_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#material_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#material_dg').edatagrid('cancelRow')">取消</a>
 -->
</div>
<script type="text/javascript">
	// 获取json字符串
	// categoryJson ='${categoryJson}';
	// unitJson = '${unitJson}';
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table id="stocktake_dg" title="月度盘点" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
	<!-- <thead>
		<tr>
			<th field="ck" checkbox="true" ></th>
			<th field="name" width="80" editor="{type:'validatebox',options:{required:true}}">分类名</th>
			<th field="parentId" width="80" editor="{type:'combobox', options:{valueField:'id',textField:'name',data:categoryEntry,required:true}}">上级分类</th>
			<th field="remark" width="100" editor="{type:'textbox'}">备注</th>
		</tr>
	</thead> -->
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stocktake_dg').edatagrid('addRow','0')">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stocktake_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stocktake_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stocktake_dg').edatagrid('cancelRow')">取消</a> | 
	<span>上级分类</span>
	<input id="parentId" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'/warehouse/categoryEntry.json'" style="line-height:26px;border:1px solid #ccc">
	<span>分类名</span>
	<input id="id" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'/warehouse/categoryEntry.json'" style="line-height:26px;border:1px solid #ccc">
	<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchCategory()">搜索</a>
</div>
<script type="text/javascript">
	// 获取分类json字符串
	var categoryJson ='${categoryJson}';
</script>
</body>
</html>
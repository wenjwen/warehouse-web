<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table id="unit_dg" title="物料单位管理" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="false">
	<thead>
		<tr>
			<th field="ck" checkbox="true" ></th>
			<th field="name" width="30%" editor="{type:'validatebox',options:{required:true}}">单位名称</th>
			<!-- <th field="createTime" width="auto" >创建时间</th>
			<th field="updateTime" width="auto" >修改时间</th> -->
		</tr>
	</thead>
</table>
<div id="toolbar" style="padding: 5px;">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#unit_dg').edatagrid('addRow')">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#unit_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#unit_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#unit_dg').edatagrid('cancelRow')">取消</a>
</div>
<script type="text/javascript">
	unitJson = '${unitJson}';
</script>
</body>
</body>
</html>
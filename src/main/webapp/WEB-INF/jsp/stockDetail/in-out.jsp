<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<input type="text" id="stockType" value="${stockType }" style="display:none;">
<table id="stock_detail_in_out_dg" title="${stockTypeName }管理" style="width:100%;height:100%;" toolbar="#toolbar" 
			idField="id" autoSave="false" rownumbers="true" fitColumns="true" singleSelect="true">
		
</table>
<div id="toolbar" style="padding:5px 5px 5px 5px;">
	<div style="padding:5px 5px 5px 5px;border-bottom: 1px solid #95B8E7">
		<label style="color:red; margin-left:5px;">导入${stockTypeName }记录：</label>
		<input type="file" id="excel" name="excel" accept="application/vnd.ms-excel" onchange="sdExcelUpload(${stockType});"/>&nbsp;&nbsp;&nbsp;
		<br/>
	</div>
	<div style="padding-top:10px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stock_detail_in_out_dg').edatagrid('addRow',{index:0})">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#stock_detail_in_out_dg').edatagrid('destroyRow')">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#stock_detail_in_out_dg').edatagrid('saveRow')">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stock_detail_in_out_dg').edatagrid('cancelRow')">取消</a>
	</div>
</div>

<div id="win"></div>
</body>
</html>
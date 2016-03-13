<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<style type="text/css">
		#createfm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
	</style>
<table id="userList_dg" title="用户信息" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
</table>
<div id="toolbar" style="padding: 5px;">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:createUser();">新增</a>
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#userList_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#userList_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#userList_dg').edatagrid('cancelRow')">取消</a>
	 -->
</div>
<div id="create" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px" closed="true" buttons="#dlg-buttons">
	<div class="ftitle">用户信息</div>
	<form id="createfm" method="post">
		<div class="fitem">
			<label>用户名：</label>
			<input name="name" class="easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label>登录账号：</label>
			<input name="loginName" class="easyui-validatebox" required="true">
		</div>
		<div class="fitem">
			<label>密码：</label>
			<input name="password">
		</div>
		<div class="fitem">
			<label>重输密码：</label>
			<input name="password" class="easyui-validatebox" validType="email">
		</div>
		<div class="fitem">
			<label>手机号：</label>
			<input name="mobile" class="easyui-validatebox" validType="email">
		</div>
		<div class="fitem">
			<label>QQ：</label>
			<input name="qq" class="easyui-validatebox" validType="email">
		</div>
		<div class="fitem">
			<label>微信：</label>
			<input name="wechat" class="easyui-validatebox" validType="email">
		</div>
		<div class="fitem">
			<label>Email:</label>
			<input name="email" class="easyui-validatebox" validType="email">
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#create').dialog('close')">Cancel</a>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="width: 100%;height: 100%;text-align: center;">
		<div>
			<form id="form">
			<table style="margin-left: auto;margin-right: auto;padding-top: 10%;">
				<tr>
				<td>请输入旧密码</td><td><input id="pwd" name="pwd" type="password" class="easyui-validatebox" data-options="required:true"></td>
				</tr>
				<tr>
				<td>请输入新密码</td><td><input id="newPwd" name="newPwd" type="password" class="easyui-validatebox" data-options="required:true,validType:'length[5,20]'" ></td>
				</tr>
				<tr>
				<td>请确认新密码</td><td><input id="reNewPwd" name="reNewPwd" type="password" class="easyui-validatebox" data-options="required:true" validType="equals['#newPwd']"></td>
				</tr>
			</table>
			</form>
		</div>
		<div style="padding-top: 20px;">
			<a onclick="pwdModify();" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
			<a onclick="pwdClean();" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清空</a>
		</div>
	</div>
	<script type="text/javascript">
		function pwdModify(){
			
			if ($('#form').form('validate')){ // 如果验证通过
				var oldPwd = $('#pwd').val();
				var newPwd = $('#newPwd').val();
				
				$.ajax({
					url:'/warehouse/passwordModify',
					dataType:'json',
					type:'POST',
					data : {oldPassword:oldPwd, newPassword:newPwd},
					success:function(data){
						if(data.code == '1'){
							$.messager.alert('提示','修改成功,下次登录生效。','info');
						} 
						else if(data.code == '-1') {
							$.messager.alert('错误','旧密码不正确！','error');
						}
					}
				});
			}
		}
		
		function pwdClean(){
			$('#pwd').val('');
			$('#newPwd').val('');
			$('#reNewPwd').val('');
		}
	</script>
</body>
</html>
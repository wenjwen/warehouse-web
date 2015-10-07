<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>仓库管理系统登陆页面</title>
<meta name="description" content="登陆页面" />
<link rel="stylesheet" type="text/css" href="resource/login/style.css" />
<script type="text/javascript" src="resource/js/jquery.min.js"></script>
<script type="text/javascript" src="resource/login/placeholder.js"></script>
<style type="text/css">

</style>
</head>
<body>
<div class="main">	
	<div class="content">
		<div class="title hide">管理登录</div>
		<div style="text-align: center;color: red;"><span id="errorMsg"></span></div>
		<form name="login" action="/warehouse/main" method="post">
			<fieldset>
				<div class="input">
					<input value='admin' class="input_all name" name="userName" id="userName" placeholder="用户名" type="text" onFocus="this.className='input_all name_now';" onBlur="this.className='input_all name'"  maxLength="24" />
				</div>
				<div class="input">
					<input class="input_all password" name="password" id="password" type="password" placeholder="密码" onFocus="this.className='input_all password_now';" onBlur="this.className='input_all password'" maxLength="20" />
				</div>
				
				<div class="enter">
					<input class="button hide" name="submit" type="submit" value="登录" />
				</div>
				<div style="text-align:center;padding-top:10px;">
					<a href="/warehouse/searchonly" style="color:green;">进入只查询页面</a>
				</div>
			</fieldset>
		</form>
	</div>
</div>
<script type="text/javascript">
var code='${code}';
$(function(){
	switch(code){
		case '-1':
			$('#errorMsg').text('用户名不存在！');
			break;
		case '-2':
			$('#errorMsg').text('密码不正确！');
			break;
	};
	$('#password').focus();
});
</script>
</body>
</html>
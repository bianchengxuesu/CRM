<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>

		$(function (){
			//清空用户名和密码框
			$("#loginAct").val("");

			//用户文本框自动获得焦点
			$("#loginAct").focus();
			//提交按钮绑定事件
			$("#submitBtn").click(function () {
				login();
			})
			//为窗口绑定键盘事件
			$(window).keydown(function (event) {
				if (event==13){
					login();
				}
			})

		})

		//登录方法
		function login() {
			//先将上次的内容清空
			$("#msg").html("");
			//验证帐号密码不能为空,jquery的trim为去除左右两边的空格
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());

			if (loginAct=="" || loginPwd==""){
				$("#msg").html("帐号密码不能为空");
				//如果帐号或密码为空，就及时终止方法，不再继续运行下去
				return false;
			}

			//去后台验证登录相关操作
			$.ajax({
				url : "settings/user/login.do",
				data : {

					"loginAct" : loginAct,
					"loginPwd" : loginPwd

				},
				type : "post",
				dataType : "json",
				success : function (data) {
					/*
						data :
							{"success":true/false,
							 "msg":"具体错误信息"}
					 */
					//登录成功
					if (data.success) {
						//跳转到工作台初始页面
						window.location.href = "workbench/index.html";
					}else {
						//从data中获取登录失败的具体信息
						$("#msg").html(data.msg);
					}
				}
			})
		}
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<!-- 注意：按钮写在form表单中，默认行为是提交表单
					 		一定要将按钮的类型设置为button，
					 		按钮触发的行为我们自己手动写js代码决定
					 -->
					<button type="button" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
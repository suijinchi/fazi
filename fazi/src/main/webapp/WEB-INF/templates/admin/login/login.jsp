<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>登录</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<script>
			if(window.top !== window.self){ window.top.location = window.location;}
		</script>
	</head>

	<body class="gray-bg" style="background: url(${respath}/admin/images/login_bj.jpg) center top no-repeat;">
		<div class="middle-box text-center loginscreen" style="width: 400px;">
			<div>
				<div>
					<img src="${respath}/admin/images/ld-logo.png" width="200px" style="padding-top: 10%;margin: 0 auto;display: block;" />
				</div>
				<div style="width: 90%;background: #fff;padding: 40px 5%;margin: 0 auto;">
					<h3>欢迎使用法滋蛋糕后台管理系统</h3>
					<!--<form class="m-t">-->
						<div class="form-group">
							<input type="text" class="form-control" placeholder="用户名" required="" name="username" value="admin">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" placeholder="密码" required="" name="password" value="">
						</div>
						<a>
							<button id="submit" type="submit" class="btn btn-primary block full-width m-b">登 录</button>
						</a>
						<!--<p class="text-muted text-center">
							<a href="login.html#">
								<small>忘记密码了？</small></a> |
								<a href="">注册一个新账号</a>
						</p>-->
					<!--</form>-->
				</div>
			</div>	
		</div>

		<script type="text/javascript">
		
			$(function(){
				
				$("#submit").click(function(){
					var $submit=$("#submit");
					var username = $("input[name='username']").val();
					var password = $("input[name='password']").val();
					if(!username){
						layer.msg('<span style="color:black;">请输入用户名</span>', {
							icon : 0,
							time : 1500
						});
						return;
					}
					if(!password){
						layer.msg('<span style="color:black;">请输入密码</span>', {
							icon : 0,
							time : 1500
						});
						return;
					}
					
					$.ajax({
						url : "${base}/admin/login",
						method : "post",
						data : {
							"username":username,
							"password":password
						},
						dataType:"json",
						beforeSend:function(){
							$submit.prop("disabled",true);
						},
						success : function(data){
							if (data.type === "success") {
								window.location.href="${base}/admin/main/index";
							} else {
								layer.msg('<span style="color:black;">'+data.content+'</span>', {
									icon : 0,
									time : 1500
								});
								$submit.prop("disabled",false);
							}
						}
					});
				});
			});
		
		</script>
		
		<script language="JavaScript">
			$("body").keydown(function() {
			   document.onkeydown=function(event){
			   e = event ? event :(window.event ? window.event : null);
			   if(e.keyCode===13){
			        //执行的方法
			    	$('#submit').click();
			      }
			   }
			});
		</script>
	</body>

</html>
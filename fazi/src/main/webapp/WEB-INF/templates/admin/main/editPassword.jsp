<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />

		<title>修改密码</title>
	</head>

	<body>	
		<form action="${base}/admin/main/updatePassword" method="post" class="form form-horizontal" id="form-change-password">
			<div class="form-group" style="margin-top:20px">
				<label class="col-sm-3 control-label">用户名：</label>
				<input type="hidden" id="id" name="id" value="${admin.id }">
				<div class="col-sm-8">		
					<input type="text" class="form-control" placeholder="" readonly="readonly" value="${admin.name }">				
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 原密码：</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" placeholder="" name="oldpassword" id="oldpassword">							
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 新密码：</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" placeholder="" name="password" id="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 确认密码：</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" placeholder="" name="newpassword2" id="new-password2">						
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" type="submit" id="submit">提交</button>
				</div>
			</div>
		</form>
			
		<script type="text/javascript">
			$(function() {
				var $submit = $("#submit");
				$("#form-change-password").validate({
					rules: {
						oldpassword:{
							required: true
						},
						password: {
							required: true,
							minlength: 6,
							maxlength: 16
						},
						newpassword2: {
							required: true,
							minlength: 6,
							maxlength: 16,
							equalTo: "#password"
						}
					},
					onkeyup: false,
					focusCleanup: true,
					success: "valid",
					submitHandler:function(){
						$.ajax({
							type:"post",
							url:"${base}/admin/main/updatePassword",
							dataType:"json",
							data:$("#form-change-password").serialize(),
							beforeSend:function(){
								$submit.prop("disabled",true);
							},
							success:function(data){
								if(data.type==="success"){
									layer.msg(data.content, {
										  icon: 1,
										  time: 2000 //2秒关闭（如果不配置，默认是3秒）
										}, function(){
											parent.location.reload();
										});
								}else{
									layer.msg(data.content, {icon: 0});
									$submit.prop("disabled",false);
								}
							}
						});
						return false;
					}
				});
			});
		</script>
		<!--/请在上方写此页面业务相关的脚本-->
	</body>

</html>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>添加</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	</head>

	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;">
				
			<div class="form-group">
				<label class="col-sm-3 control-label">分销商名称：</label>
				<div class="col-sm-8">
					<input id="name" name="name" placeholder="请输入分销商名称" class="form-control" type="text">
				</div>
			</div>
		
			<%--
			<div class="form-group">
				<label class="col-sm-3 control-label">账号：</label>
				<div class="col-sm-8">
					<input id="username" name="username" placeholder="请输入账号" class="form-control" type="text">
				</div>
			</div>
		
			<div class="form-group">
				<label class="col-sm-3 control-label">密码：</label>
				<div class="col-sm-8">
					<input id="password" name="password" placeholder="请输入密码" class="form-control" type="text">
				</div>
			</div>
			--%>
		
			<div class="form-group">
				<label class="col-sm-3 control-label">管理员姓名：</label>
				<div class="col-sm-8">
					<input id="adminName" name="adminName" placeholder="请输入管理员姓名" class="form-control" type="text">
				</div>
			</div>
		
			<div class="form-group">
				<label class="col-sm-3 control-label">管理员手机号：</label>
				<div class="col-sm-8">
					<input id="adminMobile" name="adminMobile" placeholder="请输入管理员手机号" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
			
		</form>

		<script>
		$(function(){
			
			$(".i-checks").iCheck({
				checkboxClass: "icheckbox_square-green",
				radioClass: "iradio_square-green",
			})
			
			var $submit = $("#submit");
			$("#dataForm").validate({
				rules:{
					name:{
						required:true,
					},
					/*username:{
						required:true,
					},
					password:{
						required:true,
					},*/
					adminName:{
						required:true,
					},
					adminMobile:{
						required:true,
					},
				},
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(){
					$.ajax({
						type:"post",
						url:"${base}/admin/agent/save",
						dataType:"json",
						data:$("#dataForm").serialize(),
						beforeSend:function(){
							$submit.prop("disabled",true);
						},
						success:function(data){
							if(data.type=="success"){
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
			
		})
		</script>
	</body>

</html>
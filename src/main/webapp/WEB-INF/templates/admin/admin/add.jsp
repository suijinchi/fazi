<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>管理员管理-添加</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>

	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;">
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 用户名：</label>
				<div class="col-sm-8">
					<input id="username" name="username" placeholder="请输入用户名" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 初始密码：</label>
				<div class="col-sm-8">
					<input id="password" name="password" placeholder="请输入密码" class="form-control" type="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 确认密码：</label>
				<div class="col-sm-8">
					<input id="repassword" name="repassword" placeholder="请确认密码" class="form-control" type="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">姓名：</label>
				<div class="col-sm-8">
					<input id="name" name="name" placeholder="请输入姓名" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别： </label>
				<div class="col-sm-8">
					<div class="radio i-checks">
						<label>
							<input type="radio" checked="" value="1" name="sex"> <i></i> 男
						</label>
						<label>
							<input type="radio" value="0" name="sex"> <i></i> 女
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">手机号：</label>
				<div class="col-sm-8">
					<input id="phone" name="phone" placeholder="请输入手机号" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱：</label>
				<div class="col-sm-8">
					<input id="email" name="email" placeholder="@" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">角色：</label>
				<div class="col-sm-8">
					<div class="checkbox i-checks">
					 	<c:forEach items="${sysRoleList}" var="sysRole">
					 		<label><input type="checkbox" name="adminRole" value="${sysRole.id}">&nbsp;${sysRole.name}</label>
					   	</c:forEach>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea name="memo" rows="3" placeholder="请输入备注信息" class="form-control"></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</form>
		
		<script type="text/javascript">
		$(function(){
			
			$(".i-checks").iCheck({
				checkboxClass: "icheckbox_square-green",
				radioClass: "iradio_square-green"
			});
			
			var $submit = $("#submit");
			$("#dataForm").validate({
				rules:{
					username:{
						required:true
					},
					password:{
						required:true,
						maxlength:20,
						minlength:6
					},
					repassword:{
						equalTo: "#password"
					},
					email:{
						email:true
					},
					phone:{
						number:true
					}
				},
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(){
					$.ajax({
						type:"post",
						url:"${base}/admin/admin/save",
						dataType:"json",
						data:$("#dataForm").serialize(),
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
			
		})
		</script>
	</body>

</html>
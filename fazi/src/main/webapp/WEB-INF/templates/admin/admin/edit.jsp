<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>管理员管理-修改</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>

	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;">
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 用户名：</label>
				<div class="col-sm-8">
					<input id="username" name="username" value="${admin.username}" placeholder="请输入用户名" class="form-control" type="text">
					<input type="hidden" name="id" value="${admin.id}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">新密码：</label>
				<div class="col-sm-8">
					<input id="password" name="password" placeholder="请输入新密码（为空则不修改）" class="form-control" type="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">确认密码：</label>
				<div class="col-sm-8">
					<input id="repassword" name="repassword" placeholder="请确认密码" class="form-control" type="password">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">姓名：</label>
				<div class="col-sm-8">
					<input id="name" name="name" value="${admin.name}" placeholder="请输入姓名" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">性别： </label>
				<div class="col-sm-8">
					<div class="radio i-checks">
						<label>
							<input type="radio" <c:if test="${admin.sex=='1'}">checked="checked"</c:if> value="1" name="sex"> <i></i> 男
						</label>
						<label>
							<input type="radio" <c:if test="${admin.sex=='0'}">checked="checked"</c:if> value="0" name="sex"> <i></i> 女
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">手机：</label>
				<div class="col-sm-8">
					<input id="phone" name="phone" placeholder="请输入手机号" value="${admin.phone}" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">邮箱：</label>
				<div class="col-sm-8">
					<input id="email" name="email" placeholder="@" value="${admin.email}" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">角色：</label>
				<div class="col-sm-8">
					<div class="checkbox i-checks">
					 	<c:forEach items="${sysRoleList}" var="sysRole">			 		
					 		<label><input type="checkbox" name="adminRole" <c:forEach items="${checkedList }" var="check"><c:if test="${check.id==sysRole.id }">checked="checked"</c:if></c:forEach> value="${sysRole.id}">&nbsp;${sysRole.name}</label>
					   	</c:forEach>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea name="memo" rows="3" class="form-control" placeholder="请确认备注信息">${admin.memo}</textarea>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</form>

		<script>
			$(function() {
				
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
						repassword:{
							equalTo: "#password"
						}
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(){
						$.ajax({
							type:"post",
							url:"${pageContext.request.contextPath}/admin/admin/update",
							dataType:"json",
							data:$("#dataForm").serialize(),
							beforeSend:function(){
								$submit.prop("disabled",true);
							},
							success:function(data){
								if(data.type==="success"){
									console.log("编辑成功的回调1");
									layer.msg(data.content, {
										  icon: 1,
										  time: 2000 //2秒关闭（如果不配置，默认是3秒）
										}, function(){
											/* parent.location.reload(); */
											parent.location.reload();
										});
								}else{
									console.log("回调失败!");
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
	</body>

</html>
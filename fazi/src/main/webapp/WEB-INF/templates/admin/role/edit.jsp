<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>角色管理-修改</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link href="${respath}/admin/css/zTreeStyle.css" rel="stylesheet">
		<script src="${respath}/admin/js/jquery.ztree.core.min.js"></script>
		<script src="${respath}/admin/js/jquery.ztree.excheck.min.js"></script>
	</head>

	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;" method="post" onsubmit="return false" >
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 角色名称：</label>
				<div class="col-sm-8">
					<input id="name" name="name" class="form-control" type="text" value="${sysRole.name }" placeholder="请输入角色名称">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">描述：</label>
				<div class="col-sm-8">
					<input id="description" name="description" class="form-control" type="text" value="${sysRole.description }" placeholder="请输入角色描述">
				</div>
			</div>
			<div id="">
				<label class="col-sm-3 control-label">选择权限：</label>
				<div class="col-sm-8">
					<div id="menuList" class="ztree"></div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button id="submit" class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
		</form>
		
		<script>
			$(function() {
				$(".full-height-scroll").slimScroll({
					height: "100%"
				});
				 
				 var setting = {
					check: {
						enable: true
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
	         	
				 var zNodes =[
					<c:forEach items="${sysMenuList}" var="menu" varStatus="status">
						{ 	id:'${menu.id}', 
							pId:'${menu.parentId}', 
							name:"${menu.name}", 
							<c:choose><c:when test="${menu.grade==1}">open:false,</c:when><c:otherwise>open:true,</c:otherwise></c:choose>
							<c:forEach items="${checkedList}" var="checked" varStatus="status"><c:if test="${checked.id==menu.id}">checked:true,</c:if></c:forEach>
						},
					</c:forEach>
		 		];

	 			$.fn.zTree.init($("#menuList"), setting, zNodes);
				
				var $submit = $("#submit");
				
				$("#dataForm").validate({
					rules:{
						name:"required"
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(form){
						
						var zTree = $.fn.zTree.getZTreeObj("menuList");
						var list = zTree.getCheckedNodes(true);
						var ids = "";
						$.each(list,function(i,n){
							ids += n.id+",";
						});
						
						$.ajax({
							type:"post",
							url:"${base}/admin/role/update",
							dataType:"json",
							data:$("#dataForm").serialize()+"&ids="+ids+"&sysRoleId=${sysRole.id}",
							beforeSend:function(){
								$submit.prop("disabled",true);
							},
							success:function(data){
								console.log("角色编辑的回调");
								if(data.type==="success"){
									layer.msg(data.content, {
										  icon: 1,
										  time: 2000 //2秒关闭（如果不配置，默认是3秒）
										}, function(){
											//location.reload();
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
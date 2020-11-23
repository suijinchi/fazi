<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>编辑</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	</head>
	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;">
			<input type="hidden" name="id" value="${r'${'}${humpBeanName}.id}"/>
		<#list comments as data>
		<#if data_index!=0&&data_index!=1&&data_index!=2>
			<div class="form-group">
				<label class="col-sm-3 control-label">${data}：</label>
				<div class="col-sm-8">
					<input id="${columns[data_index]}" name="${columns[data_index]}" value="${r'${'}${humpBeanName}.${columns[data_index]}}" placeholder="请输入${data}" class="form-control" type="text">
				</div>
			</div>
		</#if>
		</#list>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" type="submit">提交</button>
				</div>
			</div>
			
		</form>

		<script>
			$(function() {
				$(".full-height-scroll").slimScroll({
					height: "100%"
				});
				var $submit = $("#submit");
				$("#dataForm").validate({
					rules:{
						<#list columns as data>
						<#if data_index!=0&&data_index!=1&&data_index!=2>
						${data}:{
							required:true,
						},
						</#if>
						</#list>
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(){
						$.ajax({
							type:"post",
							url:"${r'${base}'}/admin/${table}/update",
							dataType:"json",
							data:$("#dataForm").serialize(),
							beforeSend:function(){
								$submit.prop("disabled",true);
							},
							success:function(data){
								if(data.type=="success"){
									layer.msg(data.content, {
									  icon: 1,
									  time: 2000
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
	</body>

</html>
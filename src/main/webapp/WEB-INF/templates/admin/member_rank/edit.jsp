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
			<input type="hidden" name="id" value="${memberRank.id}"/>
			<div class="form-group">
				<label class="col-sm-3 control-label">等级名称：</label>
				<div class="col-sm-8">
					<input id="name" name="name" value="${memberRank.name}" placeholder="请输入等级名称" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">优惠比例：</label>
				<div class="col-sm-8">
					<input id="scale" name="scale" value="${memberRank.scale}" placeholder="请输入优惠比例" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">消费获得最低积分：</label>
				<div class="col-sm-8">
					<input id="lowAmount" name="lowAmount" value="${memberRank.lowAmount}" placeholder="消费获得最低积分" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">消费获得最高积分：</label>
				<div class="col-sm-8">
					<input id="highAmount" name="highAmount" value="${memberRank.highAmount}" placeholder="消费获得最高积分" class="form-control" type="text">
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
				$(".full-height-scroll").slimScroll({
					height: "100%"
				});
				var $submit = $("#submit");
				$("#dataForm").validate({
					rules:{
						name:{
							required:true,
						},
						isDefault:{
							required:true,
						},
						scale:{
                            required:true,
                            number:true,
                            min:0,
                            max:1
						},
                        highAmount:{
                            required:true,
                            digits:true
                        },
                        lowAmount:{
                            required:true,
                            digits:true
                        }
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(){
						$.ajax({
							type:"post",
							url:"${base}/admin/member_rank/update",
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
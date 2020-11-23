<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>管理员管理-添加</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>

	<body>
		<form class="form-horizontal" id="dataForm" style="padding-top: 30px;">
			<input id="id" name="id" value="${product.id }" class="form-control" type="hidden">
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 价格：</label>
				<div class="col-sm-5">
					<input id="price" name="price" placeholder="请输入价格" value="${productStock.price}" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i> 库存：</label>
				<div class="col-sm-5">
					<input id="stock" name="stock" placeholder="请输入库存" value="${productStock.stock}" class="form-control" type="stock">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-5 col-sm-offset-3">
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
					price:{
						number:true,
						min:0
					},
                    stock:{
                        digits:true
					}
				},
				onkeyup:false,
				focusCleanup:true,
				success:"valid",
				submitHandler:function(){
					$.ajax({
						type:"post",
						url:"${base}/admin/product/update_stock",
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
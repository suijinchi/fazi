<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>充值项-添加</title>
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
		<link href="${respath}/admin/css/public.css" rel="stylesheet">
		<link href="${respath}/admin/css/plugins/iCheck/custom.css" rel="stylesheet">
		<link href="${respath}/admin/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
		<link href="${respath}/admin/css/style.min862f.css?v=4.1.0" rel="stylesheet">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
		<script src="${respath}/admin/js/jquery.min.js"></script>
		<script src="${respath}/admin/js/jquery.form.js"></script>	

		<script src="${respath}/admin/js/bootstrap.min.js"></script>
		<script src="${respath}/admin/js/plugins/iCheck/icheck.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
		<script src="${respath}/admin/lib/laydate/laydate.js"></script>
		<script src="${respath}/admin/js/plugins/validate/jquery.validate.min.js?"></script>
		<script src="${respath}/admin/js/plugins/validate/messages_zh.min.js?"></script>

	</head>

	<body>
		<form id="dataForm" class="form-horizontal" style="padding-top: 30px;">

			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>充值名称：</label>
				<div class="col-sm-8">
					<input id="name" name="name" placeholder="最多12个字符，1个汉字2个字符" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>充值金额：</label>
				<div class="col-sm-8">
					<input id="amount" name="amount" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>赠送金额：</label>
				<div class="col-sm-8">
					<input id="giveAmount" name="giveAmount" value="0" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>排序：</label>
				<div class="col-sm-8">
					<input id="orders" name="orders" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>是否显示：</label>
				<div class="col-sm-8">
					<label class="checkbox-inline">
						<input name="isShow" type="radio" checked value="0" id="inlineCheckbox1"/>否
					</label>
					<label class="checkbox-inline">
						<input name="isShow" type="radio" checked value="1" checked id="inlineCheckbox2"/>是
					</label>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" id="submit" type="submit">提交</button>
				</div>
			</div>

		</form>
		<script>
		
			$(document).ready(function() {
				
				$(".i-checks").iCheck({
					checkboxClass: "icheckbox_square-green",
					radioClass: "iradio_square-green"
				});
				
				$('#inp-file2').change(function() {
					var a = $("#inp-file2").val();
					$("#file-show").val(a);
				});
				
				var $submit = $("#submit");
				$("#dataForm").validate({
					rules:{
						name:{
							required:true,
                            stringMaxLength:12
						},
						amount:{
                            isPrice:true,
                            required:true,
						},
                        giveAmount:{
                            isPrice:true,
                            required:true,
                        },
                        orders:{
                            required:true,
                            digits:true
						}
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(form){						
						$.ajax({
							type:"post",
							url:"${base}/admin/recharge_type/save",
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
			});
		</script>
	</body>

</html>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>商品分类管理-添加</title>
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
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>名称：</label>
				<div class="col-sm-8">
					<input id="name" name="name" class="form-control" type="text">
				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">展示图：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="indexShowUrl" value="" id="indexShowUrl" style="display: inline-block;width: 200px;line-height: 34px;height: 34px;float: left;">
					<label for="file">
						<a class="btn btn-primary upload-btn"><i class="icon iconfont">&#xe692;</i> 选择图片</a>
					</label>
					<input type="file" name="file" id="file" onchange="uploadImage();" style="display: none;">
				</div>
			</div>
			<div id="imgDiv" class="form-group">
				<label class="col-sm-2 control-label"></label>
				<div class="col-sm-8">

				</div>
			</div>

			<div class="form-group">
				<label class="col-sm-3 control-label">排序:</label>
				<div class="col-sm-8">
					<input id="orders" name="orders" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" id="submit" type="submit">提交</button>
				</div>
			</div>
		</form>
		<script>

            function uploadImage(){
                var options = {
                    url : '${base}/admin/common/upload?fileType=image',
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        console.log(data);
                        if (data.type === "success") {
                            $('#indexShowUrl').val(data.data.absolute);
                            $("#imgDiv").find("div").html("<img style='margin-right:5px' src='"+data.data.absolute+"' width='100px' />");
                        }
                    },
                    error : function(data) {
                        console.log(data);
                    }
                };
                $("#dataForm").ajaxSubmit(options);
            }

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
							required:true
						}
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(form){						
						$.ajax({
							type:"post",
							url:"${base}/admin/productCategory/save",
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
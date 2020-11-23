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
					<input type="hidden" name="specNameId" value="${specName.id}">
					<input id="name" name="name" class="form-control" type="text" value="${specName.name}">
				</div>
			</div>

			<br/>

			<div class="form-group">
				<label class="col-sm-3 control-label">规格值：</label>
				<div class="col-sm-8">
					<button onclick="addRow()" type="button" class="btn btn-success">添加规格值</button>
				</div>
			</div>

			<div id="valueDiv">
				<c:forEach items="${specValueList}" var="specValue">
					<div class="form-group">
						<label class="col-sm-3 control-label"></label>
						  <div class="col-sm-5">
								<input type="hidden" name="valueIds" value="${specValue.id}">
							  <input name="valueNames" class="form-control" type="text" value="${specValue.value}">
						  </div>
						<button onclick="removeRow(this, ${specValue.id})" type="button" class="btn">删除</button>
					</div>
				</c:forEach>
			</div>

			<br/>

			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-3">
					<button class="btn btn-primary" id="submit" type="submit">提交</button>
				</div>
			</div>
		</form>
		<script>

			function addRow() {
				var data =
           	 		'<div class="form-group">' +
					'<label class="col-sm-3 control-label"></label>' +
					'   <div class="col-sm-5">' +
					'   	<input type="hidden" name="valueIds" value="0">' +
					'   	<input name="valueNames" class="form-control" type="text">' +
					'   </div>' +
					'   	<button onclick="removeRow(this)" type="button" class="btn">删除</button>' +
					'   </div>';
				$('#valueDiv').append(data);
            }

            function removeRow(dom, specValueId) {
                $.ajax({
                    type:"post",
                    url:"${base}/admin/productSpec/deleteValue",
                    dataType:"json",
                    data:{
                        specValueId : specValueId
					},
                    success:function(data){
                        if(data.type==="success"){
                            $(dom).parent().remove();
                        }else{
                            layer.msg(data.content, {icon: 0});
                        }
                    }
                });
            }

            $(document).ready(function() {
				
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

					    var flag = true;
					    var $valueNames = $('input[name=valueNames]');
					    $.each($valueNames, function (i, n) {
							var value = $(n).val();
							if (value === '' || value === null || value === undefined) {
                                layer.msg('规格值不能为空', {icon: 0});
                                flag = false;
							}
                        });

					    if (flag) {
                            $.ajax({
                                type:"post",
                                url:"${base}/admin/productSpec/update",
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
						}

						return false;
					}
				});
				
			});
		</script>
	</body>

</html>
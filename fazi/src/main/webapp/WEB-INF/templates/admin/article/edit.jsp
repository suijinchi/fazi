<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>文章管理-添加</title>
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
		<script src="${respath}/admin/editor/kindeditor.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>

	</head>
	<body>
		<form id="dataForm" class="form-horizontal" style="padding-top: 30px;">
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>标题：</label>
				<div class="col-sm-8">
					<input id="id" name="id" value="${article.id}" class="form-control" type="hidden">
					<input id="title" name="title" value="${article.title}" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>副标题：</label>
				<div class="col-sm-8">
					<input id="subTitle" name="subTitle" value="${article.subTitle}" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>文章分类: </label>
				<div class="col-sm-8">
					<select class="form-control" name="articleCategoryId" size="1" disabled="disabled">
						<c:forEach items="${articleCategoryList}" var="articleCategory">
							<option <c:if test="${article.articleCategoryId==articleCategory.id}">selected="selected"</c:if> value="${articleCategory.id}">${articleCategory.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>展示图：</label>
				<div class="col-sm-8">
					<input name="showImageUrl" class="form-control" id="imgUrl" value="${article.showImageUrl }" readonly="readonly" style="display: inline-block;width:70%;line-height: 34px;height: 34px;float: left;">
					<label class="btn btn-primary" for="file" style="display: inline-block;line-height: 34px;height: 34px;padding: 0 10px;float: left;margin-left: 10px;">
						<i class="icon iconfont">&#xe692;</i> 选择文件</label>
					</label>
					<input type="file" name="file" id="file" onchange="uploadImage(this);" style="display: none;">
				</div>
			</div>
			<div id="imgDiv" class="form-group">
				<label class="col-sm-3 control-label"></label>
				<div class="col-sm-9">
					<img style="margin-right:5px" src="${article.showImageUrl }" width="100px"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">内容：</label>
				<div class="col-sm-9">
					<input type="hidden" id="content" name="content" value="" style="display:none" />
					<textarea id="editor" style="width:600px;height:300px;">${article.content }</textarea>
				</div>
			</div>

			<c:if test="${article.articleCategoryId == 1}">
				<div class="form-group">
					<label class="col-sm-3 control-label">标签：</label>
					<div class="col-sm-9">
						<div class="checkbox i-checks">
							<c:forEach items="${tagList}" var="tag">
								<label><input type="radio" <c:forEach items="${articleTagList }" var="articleTag"><c:if test="${articleTag.tagId==tag.id }">checked="checked"</c:if></c:forEach> value="${tag.id}" name="tagIds">&nbsp;${tag.name}</label>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>

			<div class="form-group">
				<label class="col-sm-3 control-label">设置：</label>
				<div class="col-sm-8">
					<div class="checkbox i-checks">
						<label>
							<input type="hidden" id="isPublication" name="isPublication" value="0">
							<input id="isPublicationCheck" <c:if test="${article.isPublication==1 }">checked="checked"</c:if> value="1" type="checkbox"> <i></i> 发布</label>
						<label>
							<input type="hidden" id="isTop" name="isTop" value="0">
							<input id="isTopCheck" <c:if test="${article.isTop==1 }">checked="checked"</c:if> value="1" type="checkbox"> <i></i> 置顶</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">排序:</label>
				<div class="col-sm-8">
					<input id="sort" name="sort" value="${article.sort}" class="form-control" type="text">
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
							$('#imgUrl').val(data.data.absolute);
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

                KindEditor.ready(function(K) {
                    window.editor = K.create('#editor',{
                        uploadJson : '${base}/admin/common/upload1',//服务器端的地址
                        allowImageUpload  : true//运行上传图片
                    });
                });
				
				$(".i-checks").iCheck({
					checkboxClass: "icheckbox_square-green",
					radioClass: "iradio_square-green"
				});
				
				$('#inp-file2').change(function() {
					var a = $("#inp-file2").val();
					//					alert(a);
					$("#file-show").val(a);
				});
				
				var $submit = $("#submit");
				$("#dataForm").validate({
					rules:{
                        title:{
                            required:true
                        },
                        subTitle:{
                            required:true
                        },
                        name:{
                            required:true
                        },
                        showImageUrl:{
                            required:true
                        }
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(form){

                        if ($("#isPublicationCheck").is(':checked')) {
                            $("#isPublication").val("1");
                        }

                        if ($("#isTopCheck").is(':checked')) {
                            $("#isTop").val("1");
                        }

                        $("#content").val(editor.html());

						$.ajax({
							type:"post",
							url:"${base}/admin/article/update",
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
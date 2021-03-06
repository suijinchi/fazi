<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>商品规格管理-列表</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
		<link href="${respath}/admin/css/style.min862f.css" rel="stylesheet">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
		<link href="${respath}/admin/css/public.css" rel="stylesheet">
		<script src="${respath}/admin/js/jquery.min.js"></script>
		<script src="${respath}/admin/js/bootstrap.min.js"></script>
		<script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
		<script src="${respath}/admin/lib/laydate/laydate.js"></script>
		<style type="text/css">
			.productCategory-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" action="${base}/admin/productSpec/list" method="get">
				<div class="row">
					<div class="ibox">
						<div class="ibox-content">
							<span class="text-muted small pull-right">共有数据：${page.total} 条</span>
							<form role="form" class="form-inline">
								<div class="form-group">
									<button onclick="adver_add('添加','${base}/admin/productSpec/add','800','')" type="button" class="btn btn btn-primary">
										<i class="icon iconfont">&#xe614;</i> 添加规格
									</button>
								</div>
							</form>
							<div class="productCategory-list m-t-md">
								<table class="table table-striped table-hover table-bordered">
									<thead>
										<tr class="text-c">
											<th>序号</th>
											<th>名称</th>
											<th>规格值</th>
											<th>创建时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>${status.index+1}</td>
											<td>
												${data.name}
											</td>
											<td>
												${data.specValues}
											</td>
											<td><fmt:formatDate value="${data.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>
												<a onclick="adver_add('编辑规格','${base}/admin/productSpec/edit?id=${data.id}','800','')" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe677;</i> 编辑 </a>
												<a onclick="spec_delete(${data.id})" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe693;</i> 删除 </a>
											</td>
										</tr>
										</c:forEach>	
									</tbody>
								</table>
							</div>
							<%@ include file="/WEB-INF/templates/admin/include/page.jsp" %>
						</div>
					</div>
				</div>
			</form>
		</div>

		<script>

			$(function() {

                $("#box").change(function() {
                    if ($("#box").is(":checked")) {
                        $(".boxs").prop("checked", true);
                    } else {
                        $(".boxs").prop("checked", false);
                    }
                });

				$(".full-height-scroll").slimScroll({
					height: "100%"
				});

				$("thead input:checkbox").each(function() {
					if ($(this).attr("checked") === true) {
						alert("1");
						//$(this).parents('table').children("tbody input[type='checkbox'] ")
					}
				});

			});

			function adver_add(title, url, w, h) {
				layer_show(title, url, w, h);
			}

			function spec_delete(specNameId) {
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/productSpec/delete',
						dataType: 'json',
						data:{
                            specNameId : specNameId
						},
						success: function(data) {
							if(data.type==="success"){
								layer.msg(data.content, {
									icon: 1,
									time: 2000
								}, function(){
									location.reload();
								});
							}else{
								layer.msg(data.content, {icon: 0});
							}
						}
					});
				});
			}

		</script>
	</body>

</html>
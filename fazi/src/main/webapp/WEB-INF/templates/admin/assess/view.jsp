<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>店铺详情管理</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
		<link href="${respath}/admin/css/style.min862f.css" rel="stylesheet">
		<link href="${respath}/admin/css/public.css" rel="stylesheet">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
	</head>

	<body class="gray-bg">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="tabs-container">
							<ul class="nav nav-tabs">
								<li class="active">
									<a data-toggle="tab" href="#tab-1" aria-expanded="true"> 评价详情</a>
								</li>
							</ul>
							<div class="tab-content">
								<div id="tab-1" class="tab-pane active">
									<div class="panel-body">
										<div class="col-sm-12">
											<div class="form-group">
												<label>评价内容：</label>
												${assess.content}
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<c:forEach items="${imgList}" var="data" varStatus="status" >
													<img src="${data}" width="150px" height="150px"/>
												</c:forEach>
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>评价标签：</label>
												${assess.tags}
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>商品评价：</label>
												${assess.productAssess} 星
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>服务评价：</label>
												${assess.serviceAssess} 星
											</div>
										</div>
										<div class="col-sm-12">
											<div class="form-group">
												<label>物流评价：</label>
												${assess.logisticsAssess} 星
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="m-t">
							<a onclick="window.history.go(-1)" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;返回</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="${respath}/admin/js/jquery.min.js"></script>
		<script src="${respath}/admin/js/bootstrap.min.js"></script>
		<script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
		<script src="${respath}/admin/lib/laydate/laydate.js"></script>
		<script>
			
		</script>
	</body>

</html>
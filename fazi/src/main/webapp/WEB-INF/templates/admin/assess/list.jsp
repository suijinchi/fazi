<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>评论管理-列表</title>
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
			<form role="form" class="form-inline" action="${base}/admin/assess/list" method="get">
				<div class="row">
					<div class="ibox">
						<div class="ibox-content">
							<span class="text-muted small pull-right">共有数据：${page.total} 条</span>
							<div class="form-group">
								<input type="text" id="productName" class="form-control" name="productName" value="${productName}" placeholder="请输入商品名称">
							</div>
							<div class="form-group">
								<input type="text" id="memberNickName" class="form-control" name="memberNickName" value="${memberNickName}" placeholder="请输入会员昵称">
							</div>
							<div class="form-group">
								<select class="form-control" name="status" size="1">
									<option value="">审核状态选择</option>
									<option <c:if test="${status == 0}">selected="selected"</c:if> value="0">待审核</option>
									<option <c:if test="${status == 1}">selected="selected"</c:if> value="1">已通过</option>
									<option <c:if test="${status == 2}">selected="selected"</c:if> value="2">未通过</option>
								</select>
							</div>
							<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							<div class="productCategory-list m-t-md">
								<table class="table table-striped table-hover table-bordered">
									<thead>
										<tr class="text-c">
											<%--<th style="width: 50px">--%>
												<%--<input type="checkbox" value="2" name="" id="box">--%>
											<%--</th>--%>
											<th>序号</th>
											<th>商品名称</th>
											<th>会员昵称</th>
											<th>评价内容</th>
											<th>评价时间</th>
											<th>审核状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<%--<td>--%>
												<%--<input type="checkbox" value="${data.id}" name="ids" class="boxs">--%>
											<%--</td>--%>
											<td>
												${status.index+1}
											</td>
											<td>
												${data.productName}
											</td>
											<td>
												${data.memberNickName}
											</td>
											<td>
												${data.content}
											</td>
											<td>
												<fmt:formatDate value="${data.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td>
												<c:if test="${data.status==0}">
													<span class="label label-default">待审核</span>
												</c:if>
												<c:if test="${data.status==1}">
													<span class="label label-primary">已通过</span>
												</c:if>
												<c:if test="${data.status==2}">
													<span class="label label-danger">未通过</span>
												</c:if>
											</td>
											<td>
												<a href="${base}/admin/assess/view?id=${data.id}" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe633;</i> 查看</a>
												<a onclick="assess_del(${data.id})" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe615;</i> 删除</a>
												<c:if test="${data.status==0}">
													<a onclick="complete(${data.id})" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe671;</i> 审核通过</a>
													<a onclick="reject(${data.id})" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe612;</i> 不通过</a>
												</c:if>
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

            function complete(id) {
                layer.confirm('确认要通过吗？', function(index) {
                    $.ajax({
                        type: 'POST',
                        url: '${base}/admin/assess/review',
                        dataType: 'json',
                        data:{
                            "assessId" : id,
                            "status" : 1
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

            function reject(id) {
                layer.confirm('确认不通过吗？', function(index) {
                    $.ajax({
                        type: 'POST',
                        url: '${base}/admin/assess/review',
                        dataType: 'json',
                        data:{
                            "assessId" : id,
                            "status" : 2
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

			function assess_del(id) {
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/assess/delete',
						dataType: 'json',
						data:{
						    "assessId" : id
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
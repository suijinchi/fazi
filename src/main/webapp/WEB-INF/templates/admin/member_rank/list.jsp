<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	</head>
	
	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<span class="text-muted small pull-right">共有数据：${page.total}条</span>
						<form role="form" class="form-inline" action="${base}/admin/member_rank/list" method="get">
							<div class="form-group">
								<input type="text" id="keyword" class="form-control" name="keyword" value="${keyword}" placeholder="请输入搜索条件">
							</div>
							<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							<button onclick="layer_show('添加','${base}/admin/member_rank/add','800','')" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加</button>
						</form>
						<div class="clients-list">
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead>
										<tr class="text-c">
											<th>序号</th>
											<th>等级名称</th>
											<th>是否默认</th>
											<th>消费最低积分</th>
											<th>消费最高积分</th>
											<th>优惠比例</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${page.content}" var="data" varStatus="status" >
											<tr class="text-c">
												<td>${status.index+1}</td>
												<td>${data.name}</td>
												<td>
													<c:if test="${data.isDefault==0}">否</c:if>
													<c:if test="${data.isDefault==1}">是</c:if>
												</td>
												<td>${data.lowAmount}</td>
												<td>${data.highAmount}</td>
												<td>${data.scale}</td>
												<td>
													<c:if test="${data.isDefault==0}">
													<button class="btn btn-primary" onclick="layer_show('编辑','${base}/admin/member_rank/edit?id=${data.id}','800','','')"><i class="icon iconfont">&#xe683;</i> 编辑</button>
														<button class="btn btn-danger" onclick="del(this,${data.id})"><i class="icon iconfont">&#xe606;</i> 删除</button>
													</c:if>
												</td>
											</tr>
						                </c:forEach>
									</tbody>
								</table>
							</div>							
							<%@include file="/WEB-INF/templates/admin/include/page.jsp"%>							
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>
			//时间范围
			$(function() {
				$(".full-height-scroll").slimScroll({
					height: "100%"
				})
				laydate.render({
					elem: '#startDate',
					type: 'datetime'
				});
				laydate.render({
					elem: '#endTime',
					type: 'datetime'
				});
			});
			/*删除*/
			function del(obj, id) {
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						url: 'delete',
						data:{"ids":id},
						type: 'POST',
						dataType: 'json',
						success: function(data) {
							$(obj).parents("tr").remove();
							layer.msg('已删除!', {
								icon: 1,
								time: 1000
							});
						},
						error: function(data) {
							
						},
					});
				});
			}
		</script>
	</body>

</html>
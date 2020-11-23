<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>角色管理-列表</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<style type="text/css">
			.role-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
	
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" action="${base}/admin/role/list" method="get">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<span class="text-muted small pull-right">共有数据： ${page.total} 条</span>
						<div class="form-group">
							<input type="text" id="name" class="form-control" name="name" value="${name}" placeholder="请输入角色名">
						</div>
						<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
						<button onclick="admin_add('添加角色','${base}/admin/role/add','800','')" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加角色</button>
						<div class="clients-list">
							<div class="role-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">											
											<th>序号</th>
											<th>角色名</th>
											<th>描述</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>${status.index+1}</td>
											<td>${data.name}</td>
											<td>${data.description}</td>
											<td>
												<button type="button" class="btn btn-primary" onclick="admin_edit('编辑角色','${base}/admin/role/edit?id=${data.id}','800','')"><i class="icon iconfont">&#xe683;</i> 编辑</button>
												<c:if test="${data.isSystem != 1}">
													<button type="button" class="btn btn-danger" onclick="admin_role_del(this,${data.id})"><i class="icon iconfont">&#xe606;</i>删除</button>
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
			</div>			
			</form>
		</div>

		<script>
			//时间范围
			$(function() {
				 
			});
			/*角色-增加*/
			function admin_add(title, url, w, h) {
				layer_show(title, url, w, h);
			}
			function admin_role_del(obj, id) {
				layer.confirm('角色删除须谨慎，确认要删除吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/role/delete',
						data:{"ids":id},
						dataType: 'json',
						success: function(data) {
							$(obj).parents("tr").remove();
							layer.msg('已删除!', {
								icon: 1,
								time: 1000
							});
						},
						error: function(data) {
							console.log(data.msg);
						}
					});
				});
			}
			/*角色-编辑*/
			function admin_edit(title, url, id, w, h) {
				layer_show(title, url, w, h);
			}
		</script>
	</body>

</html>
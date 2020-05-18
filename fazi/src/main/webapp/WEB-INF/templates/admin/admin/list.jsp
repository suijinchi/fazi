<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>管理员管理-列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<style type="text/css">
			.admin-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			
			<form role="form" class="form-inline" action="${base}/admin/admin/list" method="get">
			
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<span class="text-muted small pull-right">共有数据： ${page.total} 条</span>
						<%-- <div class="form-group">
							<input type="text" id="startTime" class="form-control" name="startTime" value="${startTime}" placeholder="开始日期">
						</div>
						<div class="form-group">
							<input type="text" placeholder="结束日期" id="endTime" name="endTime" value="${endTime}" class="form-control">
						</div> --%>
						<div class="form-group">
							<input type="text" id="username" class="form-control" name="username" value="${username}" placeholder="请输入登录名">
						</div>
						<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
						<button onclick="admin_add('添加管理员','${base}/admin/admin/add','800','')" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加管理员</button>
						<div class="clients-list">
							<div class="admin-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>序号</th>
											<th>登录名</th>
											<th>手机</th>
											<th>角色</th>
											<th>加入时间</th>
											<th>是否已启用</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${page.content}" var="admin" varStatus="status" >
											<tr class="text-c">
												<td>${status.index+1}</td>
												<td>${admin.username}</td>
												<td>${admin.phone}</td>
												<td>${admin.name}</td>
												<td><fmt:formatDate value="${admin.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
												<c:if test="${admin.isEnabled==1}">
												   <td class="td-status"><span class="label label-success radius">已启用</span></td>
												</c:if>
												<c:if test="${admin.isEnabled==0}">
												   <td class="td-status"><span class="label label-default radius">已禁用</span></td>
												</c:if>
												<td class="td-manage">
												   <c:if test="${admin.isEnabled==1}">													 
													 <button type="button" class="btn btn-success" onclick="admin_stop(this,${admin.id})"><i class="icon iconfont">&#xe69d;</i> 禁用</button>
												   </c:if>	
												   <c:if test="${admin.isEnabled==0}">													
													 <button type="button" class="btn btn-success" onclick="admin_start(this,${admin.id})"><i class="icon iconfont">&#xe69d;</i> 启用</button>
												   </c:if>
												   	<button type="button" class="btn btn-primary" onclick="admin_edit('编辑管理员','${base}/admin/admin/edit?id=${admin.id}','','800','')"><i class="icon iconfont">&#xe683;</i> 编辑</button>
												   	<c:if test="${admin.isSystem != 1}">
												   		<button type="button" class="btn btn-danger" onclick="admin_del(this,${admin.id})"><i class="icon iconfont">&#xe606;</i> 删除</button>
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
				$(".full-height-scroll").slimScroll({
					height: "100%"
				});
				laydate.render({
					elem: '#startTime' //指定元素
						,
					type: 'datetime'
				});
				laydate.render({
					elem: '#endTime' //指定元素
						,
					type: 'datetime'
				});
				$("thead input:checkbox").each(function() {
					if ($(this).attr("checked") === true) {
						alert("1");
					}
				});
			});
			/*管理员-增加*/
			function admin_add(title, url, w, h) {
				layer_show(title, url, w, h);
			}
			/*管理员-删除*/
			function admin_del(obj, id) {
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
							console.log(data.msg);
						}
					});
				});
			}
			/*管理员-编辑*/
			function admin_edit(title, url, id, w, h) {
				layer_show(title, url, w, h);
			}
			/*管理员-停用*/
			function admin_stop(obj, id) {
				layer.confirm('确认要停用吗？', function(index) {
					//此处请求后台程序，下方是成功后的前台处理……
					$.ajax({
						url:"adminStatus",
						data:{"sysAdminId":id},
						dateType:"json",
						type:"POST",
						success:function(data){
							layer.msg('已禁用!', {
								icon: 5,
								time: 1000
							},function(){
								location.reload(true);
							});
						}
					})
				});
			}

			/*管理员-启用*/
			function admin_start(obj, id) {
				layer.confirm('确认要启用吗？', function(index) {
					//此处请求后台程序，下方是成功后的前台处理……
					$.ajax({
						url:"adminStatus",
						data:{"sysAdminId":id},
						dateType:"json",
						type:"POST",
						success:function(data){
							layer.msg('已启用!', {
								icon: 6,
								time: 1000
							},function(){
								location.reload(true);
							});
						}
					})
				});
			}
		</script>
	</body>

</html>
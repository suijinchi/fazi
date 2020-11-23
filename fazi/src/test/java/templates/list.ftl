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
						<span class="text-muted small pull-right">共有数据：<i class="fa fa-clock-o"></i> ${r'${page.total}'}条</span>
						<form role="form" class="form-inline" action="${r'${base}'}/admin/admin/list" method="get">
							<div class="form-group">
								<input type="text" id="startDate" name="startDate" value="${r'${startDate}'}" placeholder="开始日期" class="form-control" >
							</div>
							<div class="form-group">
								<input type="text" id="endDate" name="endDate" value="${r'${endDate}'}" placeholder="结束日期" class="form-control">
							</div>
							<div class="form-group">
								<input type="text" id="keyword" class="form-control" name="keyword" value="${r'${keyword}'}" placeholder="请输入搜索条件">
							</div>
							<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							<button onclick="layer_show('添加','${r'${base}'}/admin/admin/add','800','')" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加</button>
						</form>
						<div class="clients-list">
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead>
										<tr class="text-c">
											<th>
												<div class="check-box">
													<input type="checkbox" id="checkbox">
													<label for="checkbox-1">&nbsp;</label>
												</div>
											</th>
											<th>序号</th>
											<#list comments as data>
											<#if data_index!=0&&data_index!=1&&data_index!=2>
												<th>${data}</th>
											</#if>
											<th>创建时间</th>
											</#list>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${r'${page.content}'}" var="data" varStatus="status" >
											<tr class="text-c">
												<td><input type="checkbox" name="ids"></td>
												<td>${r'${status.index+1}'}</td>
												<#list columns as data>
												<#if data!='createDate'&&data!='modifyDate'&&data!='id'>
												<td>${data}</td>
												</#if>
												</#list>
												<td><fmt:formatDate value="${r'${data.createDate}'}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>
													<button class="btn btn-primary" onclick="layer_show('编辑','${r'${base}'}/admin/${table}/edit?id=${r'${data.id}'}','','800','')"><i class="icon iconfont">&#xe683;</i> 编辑</button>
													<button class="btn btn-danger" onclick="del(this,${r'${data.id}'})"><i class="icon iconfont">&#xe606;</i> 删除</button>
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
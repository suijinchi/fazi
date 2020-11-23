<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>日志管理</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<style type="text/css">
			.logs-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<form role="form" class="form-inline" action="${base}/admin/syslog/list" method="post">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<span class="text-muted small pull-right">共有数据： ${page.total} 条</span>
						<div class="form-group">
							<input type="text" id="keyword" class="form-control" name="keyword" value="" placeholder="请输入操作内容">
						</div>
						<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
						<div class="clients-list">
							<div class="logs-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<!-- <th>
												<div class="check-box">
													<input type="checkbox" id="checkbox">
													<label for="checkbox-1">&nbsp;</label>
												</div>
											</th> -->
											<th style="width: 80px">序号</th>
											<th>操作类型</th>
											<th>操作内容</th>
											<th>操作人员</th>
											<th>ip</th>
											<th>创建时间</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<%-- <td>
												<input type="checkbox" value="${data.id}" name="ids">
											</td> --%>
											
											<td class="client-avatar">${status.index+1}</td>
											<td>${data.type}</td>
											<td>${data.operation}</td>
											<td>${data.username}</td>
											<td>${data.ip}</td>
											<td>${data.cjrq}</td>		
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
		</div>
		</form>

		<script>
		//时间范围
		$(function() {			
			$("#keyword").val("${keyword}");
		});		
		</script>
	</body>

</html>
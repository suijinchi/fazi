<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<title>积分历史</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<style type="text/css">
			.member-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" method="post" action="${base}/admin/member/list">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<span class="text-muted small pull-right">共有数据：${page.total} 条</span>
						<div class="clients-list">
							<div class="member-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>序号</th>
											<th>时间</th>
											<th style="width: 70px">积分</th>
											<%--<th style="width: 70px">剩余积分</th>--%>
											<th>备注</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>
												${status.index+1}
											</td>
											<td>
												<fmt:formatDate value="${data.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td>
												${data.point}
											</td>
											<%--
											<td>
												<c:if test="${data.surplusAmount!=null}">
													${data.surplusAmount}
												</c:if>
												<c:if test="${data.surplusAmount==null}">
													-
												</c:if>
											</td>
											--%>
											<td>
												${data.memo}
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
			$(function() {

			});
		</script>
	</body>
</html>
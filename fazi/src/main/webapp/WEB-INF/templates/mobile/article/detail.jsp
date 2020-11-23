<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>详情</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="${respath}/mobile/css/reset.css" rel="stylesheet" />
		<style>
			.artical-title {
				font-size: 15px;
				color: #000000;
			}

			.artical_time {
				line-height: 30px;
				color: #666;
				margin-bottom: 10px;
				border-bottom: 1px solid #e3e3e3;
			}
			.artical_xq{
				text-indent: 2em;
				line-height: 22px;
				word-wrap:break-word;
			}
			.artical_xq *{
				max-width: 100%;
			}

		</style>
	</head>

	<body>
		<div style="padding: 10px;font-size: 12px;">
			<h3 class="artical-title">${article.title }</h3>
			<div class="artical_time">
				<fmt:formatDate value="${article.createDate }" pattern="yyyy-MM-dd HH:mm"/>
			</div>
			<div class="artical_xq">
				${article.content }
			</div>
		</div>
	</body>

</html>
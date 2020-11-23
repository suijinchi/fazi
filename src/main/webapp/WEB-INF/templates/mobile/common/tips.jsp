<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>提示</title>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=no,user-scalable=no" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no" />
		<style type="text/css">
		.box_404 {
			width: 90%;
			margin: 0 auto;
			padding-top: 40%;
			font-size: 14px;
			color: #808080;
			line-height: 3em;
			text-align: center;
		}
		.box_404 a {
			display: inline-block;
			color: #0077B2;
			text-decoration: underline;
		}
		.box_404 i {
			font-size: 30px;
		}
		</style>
	</head>
	<body>
		<div class="box_404">
			<i class="icon iconfont">&#xe68f;</i>
			<p style="font-size:18px;margin-top:3%">${tips}~</p>
		</div>
	</body>
</html>

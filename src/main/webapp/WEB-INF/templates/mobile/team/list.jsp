<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>我的好友</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">

		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/every.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/history.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/main20171221.css?ver=${ver}">

		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<style type="text/css">
			.current {
				background-color: #c40101!important;
			}
		</style>
	</head>

	<body style="background: #eee;">
    <%--
		<div class="teamHead">
			<div class="team-category">
				<ul>
					<li class="current" level="1" style="width: 49.8%;">A级</li>
					<li level="2" style="width: 49.8%;">B级</li>
					<li level="3">C级</li>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
    --%>

		<div id="dataList" class="teamBox" style="padding-top: 10px;">
			
			<!-- <div class="item-team">
				<img src="images/photo.jpg" width="50px" height="50px" style="margin-top:0.2em;border-radius: 100%;margin-right: 10px;" />
				<div class="teamMess">
					<div class="cl clear clearfix">
						<h3 class="fl">
							<label for="" maxlength="4">温柔的晚风</label>
						</h3>
						<span class="time fr">2017-12-21</span>
					</div>
					<div class="mess">状态：<span style="color: #ff2e45;">已支付</span></div>
				</div>
			</div> -->

		</div>
		
		
		<div id="loading" class="weui-loadmore">
			<i class="weui-loading"></i>
			<span class="weui-loadmore__tips">正在加载</span>
		</div>
		<div id="noData" class="weui-loadmore weui-loadmore_line" style="display:none;">
			<span class="weui-loadmore__tips" style="background-color: #f8f8f8;">暂无数据</span>
		</div>
		
		<form id="dataForm">
			<input type="hidden" id="pageNo" name="pageNo" value="1"/>
			<input type="hidden" id="pageSize" name="pageSize" value="10"/>
			<input type="hidden" id="level" name="level" value="1"/>
		</form>
		
		<script type="text/javascript">
			$(function() {
				$(".team-category ul li").click(function(){
					$(this).addClass("current");
					$(this).siblings().removeClass("current");
					var level = $(this).attr("level");
					$("#level").val(level);
					clearAndLoadData();
				});
				
				$("#pageNo").val(1);
				$("#content").html("");
				$("#loading").show();
				$("#noData").hide();
				
				var pageNo = $("#pageNo").val();
				$.loadContent = function() {
					var pageNo = $("#pageNo").val();
					$.ajax({
						type:"post",
						url:"listJson",
						dataType:"json",
						data:{
							pageNo:pageNo,
							pageSize:"20",
							level:$("#level").val()
						},
						success:function(data){
							var pageNo = data.pageNo;
							var totalPages = data.totalPages;
							var listData = data.listData;
							if(listData.length==0){
								$("#noData").show();
								$("#loading").hide();
								loading = true;
								return;
							}
							
							var dataList = "";
							$.each(listData,function(i,n){
								dataList+=
								'<div class="item-team">'+
								'	<img src="'+n.avatarUrl+'" width="50px" height="50px" style="margin-top:0.2em;border-radius: 100%;margin-right: 10px;" />'+
								'	<div class="teamMess">'+
								'		<div class="cl clear clearfix">'+
								'			<h3 class="fl" style="max-width: 72%;overflow: hidden;height: 30px;text-overflow: ellipsis;white-space: nowrap;">'+
								'				<label for="" maxlength="4">'+n.nickname+'</label>'+
								'			</h3>'+
								'			<span class="time fr">'+n.date+'</span>'+
								'		</div>'+
								'		<div class="mess">等级：'+n.type+'</div>'+
								'	</div>'+
								'</div>';
							});
							$("#dataList").append($(dataList));
							$("#pageNo").val(pageNo+1);
							if(pageNo==totalPages){
								$("#loading").hide();
								loading = true;
							}else{
								loading = false;
							}
						}
					});
				}
				
				function clearAndLoadData(){
					$("#noData").hide();
					$("#loading").show();
					$("#pageNo").val(1);
					$("#dataList").html("");
					setTimeout(function() {
						$.loadContent();
					}, 800); //模拟延迟
				}
				
				var loading = false; //状态标记
				$(document.body).infinite().on("infinite", function() {
					if(loading) return;
					loading = true;
					setTimeout(function() {
						$("#loading").show();
						$.loadContent();
						loading = false;
					}, 800); //模拟延迟
				});
				$.loadContent();
				
			})
		</script>
	</body>

</html>
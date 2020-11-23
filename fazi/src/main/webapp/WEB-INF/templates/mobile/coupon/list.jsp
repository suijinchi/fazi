<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>我的优惠券</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<%@ include file="/WEB-INF/templates/mobile/include/common.jsp" %>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
		<link href="${respath}/mobile/css/reset.css?ver=${ver}" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/quan.css">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<style type="text/css">

		</style>
	</head>

    <body style="background: #ececec;">
		<div style="width: 100%;max-width: 460px;margin: 0 auto;">
			<div id="content" class="box-park-quan">

				<%--<div class="quan-blocks">
					<div class="item-quan-block">
						<div class="left">
							<h3>
								<span id="">￥</span>10
							</h3>
							<div class="quan-mq">
								满200元可用
							</div>
						</div>
						<div class="right">
							<div class="q-img"></div>
							<div class="q-message">
								<h4>下午茶优惠券</h4>
								<div class="quan-p">
									可用于下午茶系列
								</div>
								<div class="quan-time">
									2016-07-20至2016-12-20
								</div>
							</div>
						</div>
					</div>
				</div>--%>

			</div>
		</div>

		<form id="searchForm">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
		</form>

		<!-- 上滑 加载数据 -->
		<div id="loading" class="weui-loadmore">
			<i class="weui-loading"></i>
			<span class="weui-loadmore__tips">正在加载</span>
		</div>
		<div id="nodata" class="weui-loadmore weui-loadmore_line" style="display: none">
			<span class="weui-loadmore__tips">暂无数据</span>
		</div>
		<div id="nomore" class="weui-loadmore weui-loadmore_line weui-loadmore_dot" style="display: none">
			<span class="weui-loadmore__tips"></span>
		</div>

		<%--<div style="height: 60px;"></div>--%>

		<script type="text/javascript">

            $(function () {

                var loading = false;  //状态标记
                $(document.body).infinite().on("infinite", function() {
                    if(loading)
                        return;
                    loading = true;
                    setTimeout(function() {
                        var pageNo = $("#pageNo").val()/1+1;
                        loadData(pageNo);
                    }, 400);   //模拟延迟
                });
                function loadData(pageNo){
                    $.ajax({
                        url:"${base}/mobile/coupon/listData",
                        data:{
                            pageNo:pageNo
                        },
                        traditional:true,
                        type:"POST",
                        dataType:"json",
                        success:function(data){
                            if(data.totalPages==0){
                                $("#loading").hide();
                                $("#nomore").hide();
                                $("#nodata").show();
                                return;
                            }
                            var dataList = "";
							$.each(data.listData,function(i,n){
								dataList+=
								'<div class="quan-blocks">'+
                                '	<div class="item-quan-block">'+
                                '		<div class="left">'+
                                '			<h3><span id="">￥</span>'+n.cutMoney+'</h3>'+
                                '			<div class="quan-mq">'+n.useType+'</div>'+
                                '		</div>'+
                                '		<div class="right">'+
                                '			<div class="q-img"></div>'+
                                '			<div class="q-message">'+
                                '				<h4>'+n.name+'</h4>'+
                                '				<div class="quan-p">'+n.subname+'</div>'+
                                '				<div class="quan-time">'+n.validStartDate+"至"+n.validEndDate+'</div>'+
                                '			</div>'+
                                '		</div>'+
                                '	</div>'+
                                '</div>';
							});
                            $("#content").append(dataList);
                            $("#pageNo").val(data.pageNo);
                            if(data.pageNo==data.totalPages){
                                $("#loading").hide();
                                $("#nomore").show();
                                $(document.body).destroyInfinite();
                            }else{
                                loading = false;
                            }

                        }
                    });
                }
                loadData(1);
            });

		</script>

	</body>


</html>
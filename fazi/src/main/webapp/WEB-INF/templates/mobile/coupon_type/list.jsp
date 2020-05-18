<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>领券</title>
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
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/quan.css">

		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/common.js?ver=${ver}"></script>
		<style type="text/css">
			.box-cp-ctn{
				width: 100%;
				height: 100%;
				top: 0;
				right:0;
				z-index: 999
			}
			.box-line{
				font-size: 14px!important;
				color: #444;
			}
			.box-line-coupon{
				color: #FF4442;
				border: 1px solid #FF4442;
				border-radius: 4px;
				margin: 0 3px;
				padding: 2px;
				font-size: 12px;
			}
			.box-cp{
				width: 100%;
			}
			.box-cp .box-cp-title{
				font-size: 14px;
			}
			.box-cp .box-cp-cut{
				font-size: 0.55rem;
				color:#555;
				margin: 2px 0;
			}
			.box-cp .box-cp-valid{
				font-size: 0.55rem;
				color:#aaa;
				margin: 2px 0;
			}
			.box-cp .box-cp-get{
				color: #FF4442;
				border: 1px solid #FF4442;
				border-radius: 4px;
				margin: 0 3px;
				padding: 2px 6px;
				font-size:0.7rem;
			}
			.box-cp .box-ctn{

			}


			/*nav*/
			.jffooterNav {
				position: fixed;
				bottom: 0;
				left: 0%;
				background: #fff;
				width: 100%;
				height: 47px;
				font-size: 13px;
				padding-top: 10px;
				text-align: center;
				z-index: 999;
				box-shadow: 1px 3px 5px 4px #cacaca;
			}

			.jffooterNav .weui-flex__item {
				height: 41px;
			}

			.jffooterNav i {
				font-size: 22px;
				line-height: 22px;
				color: #a3a3a3;
			}
		</style>
	</head>

    <body>
		<div style="width: 100%;max-width: 460px;margin: 0 auto;">
			<div class="box-cp-ctn">
				<div class="box-cp">
					<div id="content" class="box-ctn"></div>
				</div>

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
		<div style="clear: both;"></div>

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

		<!--footer-->
		<div style="height: 80px;"></div>
		<div class="jffooterNav">
			<div class="weui-flex">
				<div class="weui-flex__item active">
					<a href="${base}/mobile/index">
						<i class="icon iconfont">&#xe6f5;</i>
						<div class="jfFooterName" style="color: #666;">
							首页
						</div>
					</a>
				</div>
				<div class="weui-flex__item active">
					<a href="${base}/mobile/product_category/list">
						<i class="icon iconfont">&#xe6f2;</i>
						<div class="jfFooterName" style="color: #666;">
							分类
						</div>
					</a>
				</div>
				<div class="weui-flex__item">
					<a href="${base}/mobile/cart/list">
						<i class="icon iconfont">&#xe6c2;</i>
						<div class="jfFooterName" style="color: #666;">
							购物车
						</div>
					</a>
				</div>
				<div class="weui-flex__item">
					<a href="${base}/mobile/member/index">
						<i class="icon iconfont" style="color: #666;">&#xe6f6;</i>
						<div class="jfFooterName" style="color: #666;">
							我的
						</div>
					</a>
				</div>

			</div>
		</div>

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
                        url:"${base}/mobile/coupon_type/listData",
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
                            	'<div class="weui-cell">'+
                                '    <div class="weui-cell__bd">'+
                                '		<div class="box-cp-title">'+n.name+'</div>'+
                                '		<div class="box-cp-cut">'+
                                '			订单活动商品减'+n.cutMoney+'元，'+n.useType+
                                '		</div>'+
                                '		<div class="box-cp-valid">'+
                                '			有效期:'+n.validDate+
                                '		</div>'+
                                '		<div class="box-cp-valid" style="color: red;">'+
                                '			每人限领'+n.getLimit+'张'+
                                '		</div>'+
                                '   </div>'+
                                '   <div class="weui-cell__ft">'+
                                '    	<a class="box-cp-get" onclick="getCoupon('+n.id+')">领取</a>'+
                                '   </div>'+
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
                $.wxShare({base:"${base}","title":"法滋会员福利"});
            });

            //获取优惠券
            function getCoupon(id){
                $.ajax({
                    url:"getCoupon",
                    type:"post",
                    dataType:"json",
                    data:{
                        couponTypeId:id
                    },
                    success:function(data){
                        if(data.type=="success"){
                            $.toast(data.content);
                        }else{
                            $.toast(data.content,"text");
                        }
                    },
                    error:function(){
                        $.toast("系统异常");
                    }
                });
            }

		</script>

	</body>

</html>
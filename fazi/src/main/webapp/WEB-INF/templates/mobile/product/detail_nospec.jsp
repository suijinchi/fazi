<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>${product.name}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<%@ include file="/WEB-INF/templates/mobile/include/common.jsp" %>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.min.js?ver=${ver}"></script>
		<link href="${respath}/mobile/css/reset.css?ver=${ver}" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js?ver=${ver}"></script>
        <script type="text/javascript" src="${respath}/mobile/js/ZsSuit.js?ver=${ver}"></script>
		<style type="text/css">

			.fz-product-detail *{
				max-width: 100%;
			}
			span.disabled{
				display: inline-block;
				width: 100%;
				height: 100%;
				border-style: dotted;
				border: 1px dotted #777;
			}




			.swiper-pagination-bullet-active {
				opacity: 1;
				background: #fe6901;
			}

			.swiper-container2 {
				height: 44px;
				overflow: hidden;
			}
			.weui-popup__modal .modal-content {
				height: 100%;
				padding-top: 13.2rem;
				overflow: auto;
				box-sizing: border-box;
			}
			.modal-content {
				height: 300px;
			}
		</style>
	</head>

	<body style="background: #efefef;">
		<section class="min-width">

			<!--轮播-->
			<c:if test="${swiperpics!=null}">
			<div class="fazi_banner">
				<div class="swiper-container swiper-container-banner">
					<div class="swiper-wrapper">
						<c:forEach items="${swiperpics}" var="data">
							<div class="swiper-slide"><img src="${data.imgUrl}" alt="" width="100%" /></div>
						</c:forEach>
					</div>
					<div class="swiper-pagination"></div>
				</div>
			</div>
			</c:if>

			<!--detail-->
			<div class="fzdetailBox bg-white">
				<div class="pro-title">
					<h3>${product.name}</h3>
					<span>${product.subname}</span>
				</div>
				<p class="price-label">
					<span name="skuPrice" class="top-price">￥${productStock.price}</span>
				</p>
			</div>

			<c:if test="${couponTypeList!=null&&couponTypeList.size()>0&&product.type==0}">
				<!--领取券-->
				<div class="weui-cells box-line" style="margin-top: 10px">
					<a class="weui-cell weui-cell_access" onclick="viewCoupon()">
						<div class="weui-cell__bd">
							领券<span class="box-line-coupon">优惠券</span>
						</div>
						<div class="weui-cell__ft"></div>
					</a>
				</div>
			</c:if>

			<!--商品详情-->
			<div class="box-xq-pinglun bg-white">
				<div class="weui-flex" id="pageCenter">
					<div class="weui-flex__item">
						<a href="#pageCenter" id="spXiangqing">商品详情</a>
					</div>
					<div class="weui-flex__item">
						<a href="#pageCenter" id="pinglunBtn">评论</a>
					</div>
				</div>
			</div>
			<!--这是评价-->
			<div class="fz-product-detail" style="display: none;" id="pinglunBox">
				<div class="container">
					<div class="tieLists" id="assessList"></div>
					<!-- 上滑 加载数据 -->
					<div id="loadings" class="weui-loadmore">
						<i class="weui-loadings"></i>
						<span style="background-color: none!important;background-color: transparent;" class="weui-loadmore__tips">正在加载</span>
					</div>
					<div id="nodata" class="weui-loadmore weui-loadmore_line" style="display: none">
						<span style="background-color: none!important;background-color: transparent;" class="weui-loadmore__tips">暂无数据</span>
					</div>
					<div id="nomore" class="weui-loadmore weui-loadmore_line weui-loadmore_dot" style="display: none">
						<span style="background-color: none!important;background-color: transparent;" class="weui-loadmore__tips"></span>
					</div>
					<input type="hidden" id="pageNo" name="pageNo" value="1" />
				</div>
			</div>

			<div id="fzxiangqingBox">
				<div class="fz-product-detail">
					${product.content}
				</div>


				<c:if test="${product.type==0}">
				<!--推荐商品-->
				<div class="tj_title">
					推荐商品
				</div>
				<div class="productHot" style="border-radius: 10px;overflow: hidden;">
					<div class="producthotLists haowuBox recommend-list">
						<ul>
							<c:forEach items="${recommendList}" var="rmd">
								<li>
									<a href="${base}/mobile/product/detail?skuId=${rmd.productStock.id}">
										<img src="${rmd.showUrl}" alt="">
										<h4>${rmd.name}</h4>
										<p>￥${rmd.productStock.price}</p>
									</a>
									<div class="recommend-cart" onclick="addCart('${rmd.productStock.id}')">
										<i class="icon iconfont">&#xe6c2;</i>
									</div>
								</li>
							</c:forEach>
							<div class="clear"></div>
						</ul>
					</div>
				</div>
				</c:if>

			</div>

		</section>

		<c:if test="${product.type==0}">
		<div style="height: 60px;"></div>
		<div class="fzfooterNav" id="footer">
			<div class="weui-flex">
				<div style="position: relative;width:100px;">
					<a href="${base}/mobile/cart/list">
						<i class="icon iconfont">&#xe6f3;</i>
						<label class="cart-number">0</label>
					</a>
				</div>

				<c:if test="${isSell}" >
					<div class="weui-flex__item active">
						<a class="fz-add-cart open-popup" id="addCart">
							加入购物车
						</a>
					</div>
					<div class="weui-flex__item active">
						<a class="fz-buy-now" id="buyNow">
							立即购买
						</a>
					</div>
				</c:if>
				<c:if test="${!isSell}" >
					<div class="weui-flex__item active">
						<a class="fz-buy-now" style="border-radius:15px">
							${sellDate}可购买
						</a>
					</div>
				</c:if>

			</div>
		</div>
		</c:if>


		<c:if test="${couponTypeList!=null&&couponTypeList.size()>0}">
		<!--券弹出层-->
		<div id="viewCoupon" class="box-cp-ctn" style="z-index: 999">
			<div onclick="closeCoupon()" class="box-mask"></div>
			<div class="box-cp">
				<div class="weui-cells">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<div class="box-cp-title">优惠券</div>
						</div>
						<div class="weui-cell__ft">
							<a class="box-cp-close" onclick="closeCoupon()"><i class="icon iconfont">&#xe693;</i></a>
						</div>
					</div>
					<div class="box-ctn">
						<c:forEach items="${couponTypeList}" var="data">
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<div class="box-cp-title">${data.name}</div>
								<div class="box-cp-cut">
									订单活动商品减${data.cutMoney}元，
									<c:if test="${data.useType==1}">
										无使用门槛
									</c:if>
									<c:if test="${data.useType==2}">
										满${data.byFull}元可用
									</c:if>
								</div>
								<div class="box-cp-valid">
									有效期:${data.validDate}
								</div>
							</div>
							<div class="weui-cell__ft">
								<a class="box-cp-get" onclick="getCoupon('${data.id}')">领取</a>
							</div>
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		</c:if>

	</body>
	<script type="text/javascript">

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

		//查看优惠券
        function viewCoupon(){
            $("#viewCoupon").show();
            $("body").css({
                position: 'fixed',
                top: '0',
                left: '0',
                right: '0'
            });
        }
        //关闭优惠券
        function closeCoupon(){
            $("#viewCoupon").hide();
            $('body').css({
                'position': 'relative'
            });
        }

        function addCart(skuId) {
            $.ajax({
                type:"post",
                url:"${base}/mobile/cart/addCart",
                dataType:"json",
                data:{
                    skuId:skuId
                },
                success:function(data){
                    cartCount();
                    if(data.type=="success"){
                        $.toast(data.content);
                    }else{
                        $.toast(data.content,"cancel");
                    }
                },error:function(){
                    $.toast("系统异常","cancel");
                }
            });
        }

        var index = 0;  //状态标记

		$(function() {

            $(document.body).infinite().on("infinite", function() {
                if (index === 1) {
                    return;
                }
                index = 1;
                setTimeout(function() {
                    var pageNo = $("#pageNo").val()/1+1;
                    loadData(pageNo);
                }, 800);   //模拟延迟
            });
            loadData(1);
            function loadData(pageNo){
                $.ajax({
                    url:"${base}/mobile/assess/loadAssess",
                    data:{
                        pageNo : pageNo,
                        productId : ${product.id}
                    },
                    type:"POST",
                    dataType:"json",
                    success:function(data){
                        if(data.totalPages===0){
                            $("#loadings").hide();
                            $("#nomore").hide();
                            $("#nodata").show();
                            return;
                        }
                        var dataList = '';
                        $.each(data.listData, function(i, n){

                            var imgList = '';
                            $.each(n.imgList, function(i, n){
                                imgList +=
                                    '<li class="weui-flex__item weui-uploader__file" style="height: auto;width:30%;margin-right: 2%;">'+
                                    '	<div class="fz-img" style="width: 100%;height:5.5em;background: url('+ n +') left center no-repeat;background-size: cover;"></div>'+
                                    '</li>'
                            });

                            var tagList = '';
                            $.each(n.tagList, function(i, n){
                                tagList +=
                                    '<li>'+ n +'</li>'
                            });

                            dataList +=
                                '<div class="itemTie">'+
                                '	<div class="weui-flex" style="margin:2.5% 0;border-bottom: 1px solid #ccc;padding: 2% 3%;">'+
                                '		<div style="width: 90px;">'+
                                '			<img src="'+ n.assess.avatarUrl +'" class="userPhoto">'+
                                '		</div>'+
                                '		<div style="width: 70%;">'+
                                '			<h4 class="userName">'+ n.assess.productName + ' ' + n.assess.specName +'</h4>'+
                                '			<div class="tiezi-time">'+ n.createTime +'</div>'+
                                '			<div class="user-score">'+
                                '				商品：'+ n.assess.productAssess +'星 客服：'+ n.assess.serviceAssess +'星 物流：'+ n.assess.logisticsAssess +'星'+
                                '			</div>'+
                                '		</div>'+
                                '	</div>'+
                                '	<div style="padding: 0 3%;">'+
                                '		<div class="tDescription">'+ n.assess.content +'</div>'+
                                '		<div class="weui-row">'+
                                '			<div class="weui-cell__bd">'+
                                '				<div class="weui-uploader">'+
                                '					<div class="weui-uploader__bd">'+
                                '						<ul class="weui-flex weui-uploader__files" style="padding: 10px 0;display: block;">'+ imgList +
                                '						</ul>'+
                                '					</div>'+
                                '				</div>'+
                                '			</div>'+
                                '		</div>'+
                                '		<div class="evaluate_label">'+
                                '			<div class="evaluate_list">'+
                                '				<i class="icon iconfont icon-eval">&#xe6fa;</i>'+
                                '				<ul>'+ tagList +
                                '				</ul>'+
                                '			</div>'+
                                '		</div>'+
                                '	</div>'+
                                '</div>'
                        });
                        $("#assessList").append(dataList);
                        $("#pageNo").val(data.pageNo);
                        if(data.pageNo===data.totalPages){
                            $("#loadings").hide();
                            $("#nomore").show();
                            $(document.body).destroyInfinite();
                        }else{
                            index = 0;
                        }
                    }
                });
            }


			//点击加入购物车，弹出规格选择
            $("#addCart").click(function() {
                addCart("${productStock.id}");
            })
            //点击立即购买， 弹出规格选择
            $("#buyNow").click(function() {
                addCart("${productStock.id}",function(){
                    window.location.replace("${base}/mobile/cart/list");
                    $.showLoading("跳转购物车中...");
				});
            })

            function addCart(skuId,fun) {
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/cart/addCart",
                    dataType:"json",
                    data:{
                        productId:"${product.id}",
                        skuId:skuId
                    },
                    success:function(data){
                        if(data.type=="success"){
                            $.toast(data.content,function(){
                                if(typeof fun === "function"){
                                    fun();
                                }
                            });
                        }else{
                            $.toast(data.content,"cancel");
                        }
                        cartCount();
                    },error:function(){
                        $.toast("系统异常","cancel");
                    }
                });
            }

            function cartCount(){
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/cart/count",
                    dataType:"json",
                    success:function(data){
						$(".cart-number").html(data);
                    },error:function(){
                    }
                });
            }
            cartCount();

            /*轮播*/
            var swiper = new Swiper('.swiper-container-banner', {
                pagination: '.swiper-pagination',
            });

            //商品图片限制大小
            var pImg = $(".fz-img").width();
            $(".fz-img").height(pImg);
            // 点击评论
            $("#pinglunBtn").click(function() {
                $(this).addClass("current").parents(".weui-flex__item").siblings(".weui-flex__item a").removeClass("current");
                $("#pinglunBox").show();
                $("#fzxiangqingBox").hide();
            })
            //点击详情按钮
            $("#spXiangqing").click(function() {
                $(this).addClass("current").parents(".weui-flex__item").siblings(".weui-flex__item").children("a").removeClass("current");
                $("#fzxiangqingBox").show();
                $("#pinglunBox").hide();
            })

			wx.config({
			    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			    appId: "${jsPermission.appId}", // 必填，公众号的唯一标识
			    timestamp: "${jsPermission.timestamp}", // 必填，生成签名的时间戳
			    nonceStr: "${jsPermission.noncestr}", // 必填，生成签名的随机串
			    signature: "${jsPermission.signature}",// 必填，签名，见附录1
			    jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage","hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
			});
			wx.ready(function(){
				wx.hideMenuItems({
					menuList: [ "menuItem:openWithQQBrowser", "menuItem:openWithSafari","menuItem:share:QZone","menuItem:share:qq"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
				});
				wx.onMenuShareTimeline({
				    title: '${product.name}', // 分享标题
				    link: '${link}', // 分享链接
				    imgUrl: '${imgUrl}', // 分享图标
				    success: function () {
				        // 用户确认分享后执行的回调函数
				    },
				    cancel: function () {
				        // 用户取消分享后执行的回调函数
				    }
				});
				wx.onMenuShareAppMessage({
				    title: '${product.name}', // 分享标题
				    desc: '${product.subname}', // 分享描述
				    link: '${link}', // 分享链接
                    imgUrl: '${imgUrl}', // 分享图标
				    type: 'link', // 分享类型,music、video或link，不填默认为link
				    success: function () {
				        // 用户确认分享后执行的回调函数
				    },
				    cancel: function () {
				        // 用户取消分享后执行的回调函数
				    }
				});
			});
		});

        function adjustWidth(){
            var wd = $("img[name='specImg']").width();
            $("img[name='specImg']").height(wd/2);
        }
	</script>
</html>
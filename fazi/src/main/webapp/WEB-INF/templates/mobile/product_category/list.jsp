<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>商品分类</title>
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

		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/brand_product.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">

		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
		<script type="text/javascript" src="${respath}/mobile/layer/layer.js"></script>
		<style type="text/css">
			.swiper-pagination-bullet-active {
				opacity: 1;
				background: #ff2c73!important;
			}
			.selectMessage {
				border-radius: 8px;
			}
			input[type=search] {
				font-size: 14px;
				-webkit-box-sizing: border-box;
				box-sizing: border-box;
				height: 28px;
				text-align: left;
				padding-left: 30px;
				border: 0;
				border-radius: 15px;
				background-color: #e6e9eb;
			}
		</style>
	</head>

	<body style="background: #e6e9eb;">
	<div class="productCategoryLists">
		<div class="left_Category">
			<ul>
				<c:forEach items="${productCategoryList}" var="p">
					<li onclick="loadPc('${p.id}',this)">
						<a>${p.name}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="box_right_ProductLists">
			<div class="right_ProductLists">
				<div class="item_secondCategory">
					<div class="box_products">
						<ul id="list">
							<%--<li>
								<a href="product_detail.html">
									<img src="images/c1.jpg" alt="" />
									<div class="box_pName">
										草莓红丝绒-要你旺
									</div>
								</a>
							</li>
							--%>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>
		</div>
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
					<i class="icon iconfont" style="color: #ff4442;">&#xe6f2;</i>
					<div class="jfFooterName" style="color: #ff4442;">
						分类
					</div>
				</a>
			</div>
			<div class="weui-flex__item active">
				<a href="${base}/mobile/cart/list">
					<i class="icon iconfont">&#xe6c2;</i>
					<div class="jfFooterName" style="color: #666;">
						购物车
					</div>
				</a>
			</div>
			<div class="weui-flex__item">
				<a href="${base}/mobile/member/index">
					<i class="icon iconfont">&#xe6f6;</i>
					<div class="jfFooterName" style="color: #666;">
						我的
					</div>
				</a>
			</div>
		</div>
	</div>

	</body>
	<script type="text/javascript">

        function loadPc(productCategoryId,dom){
            $(dom).siblings().children().attr("class","");
            $(dom).children().attr("class","active");
            $.ajax({
                type:"post",
                url:"listData",
                dataType:"json",
                data:{
                    productCategoryId:productCategoryId
                },
                success:function(data){
                    if(data.type=="success"){
                        var list = data.data.list;
                        var html = "";

                        if(list.length!=0){
                            $.each(list,function(i,n){
                                html+=
                                    '<li>'+
                                    '	<a href="${base}/mobile/product/detail?skuId='+n.productStock.id+'">'+
                                    '   <img src="'+n.showUrl+'" alt="" />'+
                                    '   <div class="box_pName">'+n.name+'</div>'+
                                    '   </a>'+
                                    '</li>';
                            });
						}else{
                            html = "<li style='text-align: center;width: 100%;margin-left: 0;color: #666666;'>暂无相关商品</li>";
						}
                        $("#list").html(html);
                        //图片宽高相等
                        var imgWidth = $(".box_products li img").width();
                        $(".box_products li img").height(imgWidth);

                    }
                }
            });
        }

        $(function() {
			$(".left_Category ul li:first").click();
        })
	</script>
</html>
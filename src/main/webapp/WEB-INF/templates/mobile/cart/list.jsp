<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<title>购物车</title>
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
		<style type="text/css">
			.swiper-pagination-bullet-active {
				opacity: 1;
				background: #fe6901;
			}
            .bc{
                width: 100%;
                position: fixed;
                bottom: 57px;
                background: #fff;
                left: 0;
                top:0;
            }
			/*
			.swiper-container2 {
				height: 44px;
				overflow: hidden;
			}

			.weui-toast {
				position: fixed;
				z-index: 5014;
				width: 7.6em;
				min-height: 7.6em;
				top: 180px;
				left: 50%;
				margin-left: 0;
				background: hsla(0, 0%, 7%, .7);
				text-align: center;
				border-radius: 5px;
				color: #fff;
			}*/

			.box_404 {
				padding-top: 40%;
				display: block;
				padding-bottom: 40%;
			}

			.box_404 img {
				display: block;
				margin: 0 auto;
			}

		</style>
	</head>

	<body style="background: #ececec;">
	<div class="container">

		<c:if test="${cartItemList!=null&&cartItemList.size()>0}">
		<div class="shopcartBox">
			<ul class="cart-pro-list" id="cart-object-list">

				<c:forEach items="${cartItemList}" var="cartItem">
				<li id="li${cartItem.id}" class="cart-object-goods-item scene_area">
					<div class="cart-pro-box">
						<a href="${base}/mobile/product/detail?skuId=${cartItem.productStockId}">
							<img src="${cartItem.showUrl}">
						</a>
						<div class="cart-pro-title">
							<a href="${base}/mobile/product/detail?skuId=${cartItem.productStockId}">
								<h3>
									${cartItem.name}
								</h3>
								<%--<h2>
									<span>${cartItem.subname}</span>
								</h2>--%>
							</a>
							<c:if test="${cartItem.specName!=null&&cartItem.specName!=''}">
								<span style="margin-top: 10px">规格：${cartItem.specName}</span>
							</c:if>
                            <c:if test="${cartItem.specName==null||cartItem.specName==''}">
                                <span>&nbsp;</span>
                            </c:if>

                            <span class="cart-price" data-amount="198" style="margin-top: 20px">
								￥${cartItem.price}
							</span>
							<a onclick="deleteProduct('${cartItem.id}')" class="delete-product">
								<i class="icon iconfont">&#xe66f;</i>
							</a>
						</div>
					</div>
					<div class="cart-pro-number">
						<a onclick="reducePlus('reduce','${cartItem.id}')" class="action-quantity-plus">
							<i class="icon iconfont">&#xe6f7;</i>
						</a>
						<span id="quantity${cartItem.id}" class="quantity" price="${cartItem.price}">${cartItem.quantity}</span>
						<a onclick="reducePlus('plus','${cartItem.id}')" class="action-quantity-plus">
							<i class="icon iconfont">&#xe6f8;</i>
						</a>
					</div>

                    <c:if test="${cartItem.tableware!=null&&cartItem.tableware!=''}">
                        <p class="laid-count">${cartItem.tableware}</p>
                    </c:if>

                    <c:if test="${cartItem.isBirthdayCard=='1'}">
					    <a onclick="addBirthdayCard('${cartItem.id}')" id="birthday-brand${cartItem.id}" class="birthday-brand">
							<c:if test="${cartItem.birthdayCard!=null&&cartItem.birthdayCard!=''}">
								${cartItem.birthdayCard}
							</c:if>
							<c:if test="${cartItem.birthdayCard==null||cartItem.birthdayCard==''}">
								+添加生日牌
							</c:if>
						</a>
                    </c:if>
				</li>
				</c:forEach>

			</ul>
		</div>
		</c:if>

		<c:if test="${cartItemList==null||cartItemList.size()==0}">
			<div class="box_404">
				<img src="${respath}/mobile/images/empty_03.png" alt="" width="40%" />
			</div>
		</c:if>

		<c:if test="${cartItemList!=null&&cartItemList.size()>0}">
		<!--搭配商品-->
		<div class="tj_title">
			搭配商品
		</div>
		<div class="productHot" style="border-radius: 10px;overflow: hidden;">
			<div class="producthotLists haowuBox recommend-list">
				<ul>
                    <c:forEach items="${recommendList}" var="rmd">
					<li>
						<a href="${base}/mobile/product/detail?skuId=${rmd.productStock.id}">
							<img src="${rmd.showUrl}">
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
		<div style="height: 120px;"></div>
		</c:if>

	</div>

	<c:if test="${cartItemList!=null&&cartItemList.size()>0}">
		<!--总计-->
		<div class="fz-shop-sum">
			<div class="shopSum">
				<div class="shopNumber">
					总计：<span id="sum">￥0.00</span>
				</div>
				<div class="shopYunfei">
					不含运费
				</div>
			</div>
			<a href="${base}/mobile/orders/info" class="rightXiadan">
				去下单
			</a>
		</div>

		<div id="birthdayCard" class="container" style="display: none">
			<div class="bc">

				<ul class="fz-birthdaybrand" style="width: 100%">
					<li class="custom-birthday-brand" content="">
						<label>自定义</label>
						<div>
							<input type="text" id="birthdayCardSelf" class="weui-input" value="" placeholder="最多只能填写8个汉字或16个字符">
						</div>
					</li>
					<c:forEach items="${birthdayCardList}" var="bc">
						<li class="normal-card" content="${bc.name}">
							<label for="">${bc.name}</label>
						</li>
					</c:forEach>
					<li class="normal-card active no" content="">
						<label for="">(无)</label>
					</li>
					<div style="clear: both"></div>
				</ul>
				<a id="submit" style="display: block;background:#ff7d84;width: 80%;margin: 20px auto;font-size: 14px;color: #fff;text-align: center;padding: 10px 0;border-radius: 6px;">
					确定
				</a>
			</div>
		</div>
	</c:if>

	<!--footer-->
	<div class="jffooterNav">
		<div class="weui-flex">
			<div class="weui-flex__item">
				<a href="${base}/mobile/index">
					<i class="icon iconfont">&#xe6f5;</i>
					<div class="jfFooterName" style="color: #666;">
						首页
					</div>
				</a>
			</div>
			<div class="weui-flex__item">
				<a href="${base}/mobile/product_category/list">
					<i class="icon iconfont">&#xe6f2;</i>
					<div class="jfFooterName" style="color: #666;">
						分类
					</div>
				</a>
			</div>
			<div class="weui-flex__item">
				<a href="${base}/mobile/cart/list">
					<i class="icon iconfont" style="color: #ff4442;">&#xe6c2;</i>
					<div class="jfFooterName" style="color: #ff4442;">
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

	<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
	<script type="text/javascript" src="${respath}/mobile/js/swiper.min.js"></script>
	<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
	<script type="text/javascript" src="${respath}/mobile/js/fastclick.js"></script>
	<script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
	<script type="text/javascript">
        function reducePlus(c,id,quantity) {
            if (c == "plus") {
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/cart/addQuantity",
                    dataType:"json",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.type=="success"){
                            $("#quantity"+id).html(data.data.quantity);
                        }else{
                            $.toast(data.content,"text")
                        }
                        calSumPrice();
                    }
                });
            } else {
                if(quantity==1){
                    return;
                }
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/cart/reduceQuantity",
                    dataType:"json",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.type=="success"){
                            $("#quantity"+id).html(data.data.quantity);
                        }else{
                            $.toast(data.content,"text")
                        }
                        calSumPrice();
                    }
                });
            }
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
                    if(data.type=="success"){
                        $.toast(data.content,function(){
                            window.location.reload();
                        });
                    }else{
                        $.toast(data.content,"cancel");
                    }
                },error:function(){
                    $.toast("系统异常","cancel");
                }
            });
        }

        function deleteProduct(id){
            $.ajax({
                type:"post",
                url:"${base}/mobile/cart/deleteProduct",
                dataType:"json",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.type=="success"){
                        $.toast(data.content,"text",function(){
                            var len = $(".cart-pro-list li").length;
                            if(len==0){
                                window.location.reload();
                            }
						});
                        $("#li"+id).remove();

                    }
                    calSumPrice();
                }
            });
        }

        function calSumPrice(){
            var sum = 0;
            $(".quantity").each(function(i,n){
                var price = ($(n).attr("price")/1).toFixed(2);
                var quantity = $(n).html()/1;
                sum = sum + price*quantity;
            });
            $("#sum").html("￥"+sum.toFixed(2));
        }
        calSumPrice();

        var currentCartItemId = "";
        function addBirthdayCard(id){
            currentCartItemId = id;
            $("#birthdayCard").show();
        }

        $(function(){

            $(".fz-birthdaybrand li").click(function() {

                $(this).addClass("active");
                $(this).siblings().removeClass("active");

                var content = $(this).attr("content");
				if($(this).hasClass("custom-birthday-brand")){
					return;
				}else if($(this).hasClass("no")){
                    content = "";
				}
                fillBirthdayCard(currentCartItemId,content);
            })

			$("#submit").click(function(){
				var content = $("#birthdayCardSelf").val();
				if(getByteLen(content)>16){
				    $.toast("最多输入16个字符,一个汉字算2个字符","text");
				    return false;
				}
                fillBirthdayCard(currentCartItemId,content);
                $("#birthdayCardSelf").val("");
			    return false;
			});

            function getByteLen(val) {
                var len = 0;
                for (var i = 0; i < val.length; i++) {
                    var a = val.charAt(i);
                    if (a.match(/[^\x00-\xff]/ig) != null) {
                        len += 2;
                    }
                    else {
                        len += 1;
                    }
                }
                return len;
            }

            function fillBirthdayCard(currentCartItemId,content){
                if(!content){
                    $("#birthday-brand"+currentCartItemId).html("+添加生日牌");
                }
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/cart/birthdayCard",
                    dataType:"json",
                    data:{
                        cartItemId:currentCartItemId,
                        content:content
                    },
                    success:function(data){
                        if(data.type=="success"){
                            if(!content){
                                $("#birthday-brand"+currentCartItemId).html("+添加生日牌");
                            }else{
                                $("#birthday-brand"+currentCartItemId).html(content);
                            }
                        }
                        $("#birthdayCard").hide();
                    }
                })
			}

            $(".fz-birthdaybrand li label").click(function() {
               $("#birthdayCard").hide();
            })

		});
	</script>
	</body>

</html>
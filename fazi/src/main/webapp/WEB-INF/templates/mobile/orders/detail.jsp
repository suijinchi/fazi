<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <meta name="viewport"
          content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/every.css">
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css"/>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css">
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/dingdan.css">
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/order_agree.css">
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
    <script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
    <style type="text/css">
        .cart-pro-box img {
            display: block;
            float: left;
            width: 85px !important;
            /* height: 120px; */
        }

        .cart-pro-box .cart-pro-title {
            padding-left: 100px !important;
            padding: 15px 0;
        }

        .cart-pro-box {
            height: auto;
            border-bottom: 1px solid #ececec;
        }

        .cart-pro-list li {
            margin-bottom: 0;
            padding-top: 0;
            padding-bottom: 0;
        }

        .cart-pro-box img {
            padding-top: 12px;
        }
    </style>
</head>

<body>
<section class="min-width">
    <div id="main">
        <div class="orderDetails_top">
            <h3>

                <c:if test="${order.ordersStatus ==1 && order.payStatus == 0}">待支付<span style="font-size: 14px">（剩${60-minutes}分自动取消）</span></c:if>
                <c:if test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 0 && order.shippingMethod == 0}">待自提</c:if>
                <c:if test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 0 && order.shippingMethod == 1}">待配送</c:if>
                <c:if test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 1}">待收货</c:if>
                <c:if test="${order.ordersStatus ==2 && order.payStatus == 1 && order.shippingStatus == 2}">已完成</c:if>
                <c:if test="${order.ordersStatus ==3}">已取消</c:if>
                <c:if test="${order.ordersStatus ==4}">申请退款</c:if>
                <c:if test="${order.ordersStatus ==5}">退款中</c:if>
                <c:if test="${order.ordersStatus ==6}">已退款</c:if>
            </h3>
            <div class="money">
                订单金额：￥${order.amount}
            </div>
            <e>
                <i class="icon iconfont">&#xe66b;</i>
            </e>
        </div>
        <!--显示收货地址-->
        <div id="address-finish" class="zt_finish">
            <div class="addressContent">
                <i class="i1 lt">
                    <I class="icon iconfont">&#xe664;</I>
                </i>
                <div class="box-userAddress lt">
                    <c:if test="${order.shippingMethod == 0}">

                        <c:choose>
                            <c:when test="${(order.pickUpTime!=null&&fn:trim(order.pickUpTime)!='')||(order.pickUpAddress!=null&&fn:trim(order.pickUpAddress)!='')}">
                                <div class="u-name">
                                    自提：
                                </div>
                                <div class="u-address">
                                        ${order.pickUpTime}<br/>
                                        ${order.pickUpAddress}
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div style="line-height: 54px">
                                    自提
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </c:if>
                    <c:if test="${order.shippingMethod == 1}">
                        <div class="u-name">
                                ${order.name}&nbsp;${order.mobile}
                        </div>
                        <div class="u-address">
                                ${order.address}
                        </div>
                    </c:if>
                </div>
            </div>
            <img src="${respath}/mobile/images/address_line_02.png" width="100%"/>
        </div>
        <div class="gd"></div>
        <div class="box-wuliuBox">
            <ul>
                <li>
                    <div class="wuliu" style="border-left:10px solid #fff;border-right: 10px solid #fff;<c:if
                            test="${order.ordersStatus ==1 && order.payStatus == 0}">background: #ffa1a3;</c:if>">
                        <i class="icon iconfont">&#xe646;</i>
                    </div>
                    <div class="">
                        待支付
                    </div>
                </li>
                <li>
                    <div class="wuliu" style="border-left:10px solid #fff;border-right: 10px solid #fff;<c:if
                            test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 0}">background: #ffa1a3;</c:if>">
                        <i class="icon iconfont">&#xe6fb;</i>
                    </div>
                    <div class="">
                        <c:if test="${order.shippingMethod == 0}">待自提</c:if>
                        <c:if test="${order.shippingMethod == 1}">待配送</c:if>
                    </div>
                </li>
                <li>
                    <div class="wuliu" style="border-left:10px solid #fff;border-right: 10px solid #fff;<c:if
                            test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 1}">background: #ffa1a3;</c:if>">
                        <i class="icon iconfont">&#xe648;</i>
                    </div>
                    <div class="">
                        待收货
                    </div>
                </li>
                <li>
                    <div class="wuliu" style="border-left:10px solid #fff;border-right: 10px solid #fff;<c:if
                            test="${order.ordersStatus ==2 && order.payStatus == 1 && order.shippingStatus == 2}">background: #ffa1a3;</c:if>">
                        <i class="icon iconfont">&#xe617;</i>
                    </div>
                    <div class="">
                        已完成
                    </div>
                </li>
            </ul>
        </div>
        <div class="gd"></div>
        <div class="fz-order-list">
            <div class="item-order">
                <div class="shopcartBox">
                    <ul class="cart-pro-list" id="cart-object-list">
                        <c:forEach items="${ordersItemList}" var="ordersItem">
                            <li class="cart-object-goods-item scene_area">
                                <div class="cart-pro-box">
                                    <a onclick="to_product_detail(${ordersItem.productStockId})">
                                        <img src="${ordersItem.thumbnail}" class="fz-img">
                                    </a>
                                    <div class="cart-pro-title">
                                        <a onclick="to_product_detail(${ordersItem.productStockId})">
                                            <h3>
                                                    ${ordersItem.name}
                                            </h3>
                                            <h2 style="font-weight: normal;padding: 5px 0;">
                                                <span>${ordersItem.subname}</span>
                                            </h2>
                                        </a>
                                        <span>规格:${ordersItem.productSpec} ×${ordersItem.quantity}个 </span>
                                        <c:if test="${ordersItem.birthdayCard!=null&&ordersItem.birthdayCard!=''}">
                                            <span style="font-size: 12px">生日牌:${ordersItem.birthdayCard}</span>
                                        </c:if>
                                        <span class="cart-price" style="font-size: 0.9em;">
											￥${ordersItem.price}
                                        </span>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="gd"></div>
        <div class="box-order-fapiao">
            <ul>

                <li>
                    <label class="fl">订单编号：${order.sn}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">创建时间：<fmt:formatDate value="${order.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></label>
                    <div class="clear"></div>
                </li>

                <c:if test="${order.shippingMethod == 0}">
                    <li>
                        <label class="fl">
                            自提时间：${order.pickUpTime}
                        </label>
                        <div class="clear"></div>
                    </li>
                    <li>
                        <label class="fl">
                            姓名：${order.name}
                        </label>
                        <div class="clear"></div>
                    </li>
                    <li>
                        <label class="fl">
                            手机号：${order.mobile}
                        </label>
                        <div class="clear"></div>
                    </li>

                </c:if>

                <c:if test="${order.shippingMethod == 1}">
                    <li>
                        <label class="fl">
                            配送时间：${order.shippingTime}
                        </label>
                        <div class="clear"></div>
                    </li>
                </c:if>


                <c:if test="${order.shippingStatus==2&&order.pointReward>0}">
                <li>
                    <label class="fl">
                        奖励积分：${order.pointReward}
                    </label>
                    <div class="clear"></div>
                </li>
                </c:if>

                <c:if test="${order.memo!=null&&order.memo!=''}">
                <li>
                    <label class="fl">订单留言：${order.memo}</label>
                    <div class="clear"></div>
                </li>
                </c:if>

                <c:if test="${order.ordersStatus ==4}">
                    <li>
                        <label class="fl">退款理由：${order.refundReason}</label>
                        <div class="clear"></div>
                    </li>
                </c:if>

                <div class="gd"></div>
<%--
                <li>
                    <label class="fl">付款方式：--具体显示待定--</label>
                    <div class="clear">

                    </div>
                </li>
--%>
                <li>
                    <label class="fl">商品合计：</label>
                    <label class="fr color_9">￥${productAmount}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">余额支付：</label>
                    <label class="fr color_9">￥${order.balancePay}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">积分抵扣金额(消费${order.pointPay}积分)：</label>
                    <label class="fr color_9">￥${order.pointOffset}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">优惠券抵扣：</label>
                    <label class="fr color_9">￥${order.couponPay}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">会员等级抵扣：</label>
                    <label class="fr color_9">￥${order.discountPay}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">配送费：</label>
                    <label class="fr color_9">￥${order.shippingFee}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">调整金额：</label>
                    <label class="fr color_9">￥${order.offsetAmount}</label>
                    <div class="clear"></div>
                </li>
                <li>
                    <label class="fl">总计：</label>
                    <label class="fr color_9">
                        ￥${order.amount}<c:if test="${order.amount>0&&order.balancePay>0}">(余额+微信支付)</c:if>
                    </label>
                    <div class="clear"></div>
                </li>
            </ul>
        </div>
    </div>
    <!--footer-->
    <div style="height: 80px;background: #f6f6f6;"></div>

    <div class="footerBtnBox">
        <ul>
        <c:if test="${order.ordersStatus ==1 && order.payStatus == 0}">
            <li>
                <a onclick="to_pay(${order.id})" class="color_redBox">去支付</a>
            </li>
            <li>
                <a onclick="cancel(${order.id})">取消订单</a>
            </li>
        </c:if>
        <c:if test="${order.ordersStatus ==1 && order.payStatus == 1}">
            <li><a onclick="refund(${order.id})">申请退款</a></li>
        </c:if>
        <c:if test="${order.ordersStatus ==1 && order.payStatus == 1 && order.shippingStatus == 1}">
            <li><a onclick="confirm(${order.id})">确认收货</a></li>
        </c:if>
        </ul>
    </div>

</section>

<script type="text/javascript">

    $(function () {

        //订单列表产品图片限制
        var imgW = $(".product img").width();
        $(".product img").height(imgW);

    });

    //商品详情页
    function to_product_detail(productStockId) {
        window.location = "${base}/mobile/product/detail?skuId=" + productStockId;
    }

    //订单支付
    function to_pay(orderId) {
        $.ajax({
            url : "${base}/mobile/payment/pay",
            type : 'POST',
            data : {
                orderId:orderId
            },
            dataType : "json",
            success : function(data) {
                isSubmit = false;
                if(data.type == "success"){
                    var pay_info = data.data.pay_info;
                    WeixinJSBridge.invoke('getBrandWCPayRequest',{
                        "appId" : pay_info.appId, //公众号名称，由商户传入
                        "timeStamp":pay_info.timeStamp, //时间戳，自1970 年以来的秒数
                        "nonceStr" : pay_info.nonceStr, //随机串
                        "package" : pay_info.package,
                        "signType" : "MD5", //微信签名方式:
                        "paySign" : pay_info.paySign //微信签名
                    },function(res){
                        if(res.err_msg == "get_brand_wcpay_request:ok" ) {//支付成功
                            $.toast("订单支付成功","text",function(){
                                window.location.reload();
                            });
                        }else{
                            $.toast("订单未支付","text");
                        }
                    });
                }else{
                    $.toast(data.content,"text");
                }
            },
            error : function() {
                alert("系统异常，请重新操作");
            },
        });
    }

    //订单取消
    var isCancel = false;
    function cancel(orderId) {
        if(isCancel){
            return;
        }
        isCancel = true;
        $.confirm({
            title: '提示',
            text: '确认取消订单？',
            onOK: function () {
                $.ajax({
                    url : "${base}/mobile/orders/cancel",
                    type : 'POST',
                    data : {
                        orderId:orderId
                    },
                    dataType : "json",
                    success : function(data) {
                        if(data.type == "success"){
                            $.toast(data.content,"text",function(){
                                window.location.reload();
                            });
                        }else{
                            isCancel = false;
                            $.toast(data.content,"text");
                        }
                    },
                    error : function() {
                        isCancel = false;
                        alert("系统异常，请重新操作");
                    },
                });

            },
            onCancel: function () {
                isCancel = false;
            }
        });
    }

    //订单退款
    function refund(orderId) {
        $.prompt({
            title: '申请退款',
            text: '请输入退款理由',
            input: '',
            empty: false, // 是否允许为空
            onOK: function (reason) {
                $.ajax({
                    url : "${base}/mobile/orders/refund",
                    type : 'POST',
                    dataType : "json",
                    data : {
                        orderId : orderId,
                        reason:reason
                    },
                    success : function(data) {
                        if(data.type === "success"){
                            $.toast(data.content,"text",function(){
                                window.location.reload();
                            });
                        }else{
                            alert(data.content);
                        }
                    },
                    error : function() {
                        alert("系统异常，请重新操作");
                    }
                });

            },
            onCancel: function () {
            }
        });
    }

    //确认收货
    var isConfirm = false;
    function confirm(orderId) {
        if(isConfirm){
            return;
        }
        isConfirm = true;
        $.confirm({
            title: '提示',
            text: '确认收货？',
            onOK: function () {
                $.ajax({
                    url : "${base}/mobile/orders/confirm",
                    type : 'POST',
                    dataType : "json",
                    data : {
                        orderId : orderId
                    },
                    success : function(data) {
                        if(data.type === "success"){
                            window.location.href="${base}/mobile/orders/list";
                        }else{
                            alert(data.content);
                            isConfirm = false;
                        }
                    },
                    error : function() {
                        isConfirm = false;
                        alert("系统异常，请重新操作");
                    }
                });
            },
            onCancel: function () {
                isConfirm = false;
            }
        });
    }

</script>
</body>

</html>
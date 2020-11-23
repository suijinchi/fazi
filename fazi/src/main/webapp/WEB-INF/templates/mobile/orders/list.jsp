<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>全部订单</title>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}"/>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}"/>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}"/>
    <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
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

<body style="background: #efefef;">
<section class="min-width">

    <div class="box-order-category">
        <ul>
            <li id="liall">
                <a onclick="is_all()">全部</a>
            </li>
            <li id="linopay">
                <a onclick="nopay()">待支付</a>
            </li>
            <li id="linoship">
                <a onclick="noship()">进行中</a>
            </li>
            <li id="linoaccept">
                <a onclick="noaccept()">待收货</a>
            </li>
            <li id="liiscomplete">
                <a onclick="iscomplete()">已完成</a>
            </li>
        </ul>
    </div>

    <div class="fz-order-list" style="padding-top: 40px;" id="listData">

    </div>

    <div id="loading" class="weui-loadmore">
        <i class="weui-loading"></i>
        <span style="background-color: none!important;background-color: transparent;"
              class="weui-loadmore__tips">正在加载</span>
    </div>
    <div id="nodata" class="weui-loadmore weui-loadmore_line" style="display: none">
        <span style="background-color: none!important;background-color: transparent;"
              class="weui-loadmore__tips">暂无订单</span>
    </div>
    <div id="nomore" class="weui-loadmore weui-loadmore_line weui-loadmore_dot" style="display: none">
        <span style="background-color: none!important;background-color: transparent;"
              class="weui-loadmore__tips"></span>
    </div>

    <input type="hidden" id="pageNo" value="1"/>
    <input type="hidden" id="ordersType" value="all">

    <c:if test="${type != null}">
        <input type="hidden" name="type" id="type" value="${type}">
    </c:if>
    <c:if test="${type == null}">
        <input type="hidden" name="type" id="type" value="">
    </c:if>

</section>
<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
<script type="text/javascript" src="${respath}/mobile/js/fastclick.js"></script>
<script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
<script type="text/javascript">

    var index = 0;

    $(function () {

        $('.box-order-category ul li:first-child a').addClass("cur");
        $('.box-order-category ul li a').click(function () {
            $('.box-order-category ul li a').removeClass("cur");
            $(this).addClass("cur");
        });

        $(document.body).infinite().on("infinite", function () {
            if (index === 1) {
                return;
            }
            index = 1;
            setTimeout(function () {
                var pageNo = $("#pageNo").val() / 1 + 1;
                loadData(pageNo);
            }, 800);   //模拟延迟
        });

        //待付款
        if ($("#type").val() === "nopay") {
            $('.box-order-category ul li a').removeClass("cur");
            $("#linopay a").addClass("cur");
            nopay();
        }

        //待配送
        else if ($("#type").val() === "noship") {
            $('.box-order-category ul li a').removeClass("cur");
            $("#linoshipy a").addClass("cur");
            noship();
        }

        //待收货
        else if ($("#type").val() === "noaccept") {
            $('.box-order-category ul li a').removeClass("cur");
            $("#linoaccept a").addClass("cur");
            noaccept();
        }

        //已完成
        else if ($("#type").val() === "iscomplete") {
            $('.box-order-category ul li a').removeClass("cur");
            $("#liiscomplete a").addClass("cur");
            iscomplete();
        }

        //全部
        else {
            loadData(1);
        }

    });

    //全部
    function is_all(){

        //订单状态
        $("#ordersType").val("all");
        //当前页
        $("#pageNo").val("1");
        //清空
        $("#listData").empty();

        loadData(1);
    }

    //待付款
    function nopay() {

        //订单状态
        $("#ordersType").val("nopay");
        //当前页
        $("#pageNo").val("1");
        //清空
        $("#listData").empty();

        loadData(1);
    }

    //待配送
    function noship() {

        //订单状态
        $("#ordersType").val("noship");
        //当前页
        $("#pageNo").val("1");
        //清空
        $("#listData").empty();

        loadData(1);
    }

    //待收货
    function noaccept() {

        //订单状态
        $("#ordersType").val("noaccept");
        //当前页
        $("#pageNo").val("1");
        //清空
        $("#listData").empty();

        loadData(1);
    }

    //已完成
    function iscomplete() {

        //订单状态
        $("#ordersType").val("iscomplete");
        //当前页
        $("#pageNo").val("1");
        //清空
        $("#listData").empty();

        loadData(1);
    }

    //查询
    function loadData(pageNo) {

        $("#nodata").hide();

        var ordersType = $("#ordersType").val();

        $.ajax({
            type: "post",
            url: "${base}/mobile/orders/load",
            dataType: "json",
            data: {
                pageNo: pageNo,
                ordersType: ordersType
            },
            success: function (data) {
                if (data.totalPages === 0) {
                    $("#loading").hide();
                    $("#nomore").hide();
                    $("#nodata").show();
                    return;
                }
                var dataList = '';

                var statusName = '';

                var status = '';

                $.each(data.listData, function (i, n) {

                    if (n.ordersStatus === 1 && n.payStatus === 0) {
                        status =
                            '<div class="weui-flex__item">' +
                            '	<a onclick="to_pay(' + n.orderId + ')">去支付</a>' +
                            '</div>';
                        statusName = '待支付'
                    }
                    if (n.ordersStatus === 1 && n.payStatus === 1 && n.shippingStatus === 0 && n.shippingMethod === 1) {
                        status = '';
                        statusName = '待配送'
                    }
                    if (n.ordersStatus === 1 && n.payStatus === 1 && n.shippingStatus === 0 && n.shippingMethod === 0) {
                        status = '';
                        statusName = '待提货'
                    }
                    if (n.ordersStatus === 1 && n.payStatus === 1 && n.shippingStatus === 1) {
                        status = '';
                        statusName = '待收货'
                    }
                    if (n.ordersStatus === 2 && n.payStatus === 1 && n.shippingStatus === 2) {
                        if (n.assessStatus === 0) {
                            status =
                                '<div class="weui-flex__item">' +
                                '	<a onclick="to_assess(' + n.orderId + ')">去评价</a>' +
                                '</div>'
                        } else {
                            status = '';
                        }
                        statusName = '已完成'
                    }
                    if (n.ordersStatus === 3) {
                        status = '';
                        statusName = '已取消'
                    }
                    if (n.ordersStatus === 4) {
                        status = '';
                        statusName = '申请退款'
                    }
                    if (n.ordersStatus === 5) {
                        status = '';
                        statusName = '退款中'
                    }
                    if (n.ordersStatus === 6) {
                        status = '';
                        statusName = '已退款'
                    }

                    var itemList = '';
                    $.each(n.ordersItemList, function (i, n) {
                        itemList +=
                            '<li class="cart-object-goods-item scene_area">' +
                            '	<div class="cart-pro-box">' +
                            '		<a onclick="to_product_detail(' + n.productStockId + ')">' +
                            '   		<img src="' + n.thumbnail + '" class="fz-img">' +
                            '		</a>' +
                            '		<div class="cart-pro-title">' +
                            '    		<a onclick="to_product_detail(' + n.productStockId + ')">' +
                            '				<h3>' + n.name + '</h3>' +
                            '				<h2 style="font-weight: normal;padding: 5px 0;">' +
                            '					<span>' + n.subname + '</span>' +
                            '				</h2>' +
                            '			</a>' +
                            '			<span>规格:' + n.productSpec + '×' + n.quantity + '个 ' + '</span>' +
                            '			<span class="cart-price" style="font-size: 0.9em;">' +
                            '				￥' + n.price +
                            '			</span>' +
                            '		</div>' +
                            '	</div>' +
                            '</li>'
                    });

                    dataList +=
                        '<div class="item-order">' +
                        '    <div class="weui-row order-title">' +
                        '    	<div class="weui-col-75">订单编号：' + n.sn + '</div>' +
                        '		<div class="weui-col-25" style="text-align: right">' + statusName + '</div>' +
                        '	 </div>' +
                        '	 <div class="shopcartBox">' +
                        '    	<ul class="cart-pro-list" id="cart-object-list">' + itemList +
                        '		</ul>' +
                        '		<div class="order-all">' +
                        '			<div class="weui-row">' +
                        '				<div class="weui-col-50" style="text-align: left;">' + n.createTime + '</div>' +
                        '				<div class="weui-col-50">' +
                        '					订单金额：<span style="color: #ff4442;">￥' + n.amount + ' </span>' +
                        '				</div>' +
                        '			</div>' +
                        '		</div>' +
                        '		<div class="box-order-pay">' +
                        '			<div class="weui-flex">' + status +
                        '				<div class="weui-flex__item">' +
                        '					<a onclick="to_detail(' + n.orderId + ')">查看详情</a>' +
                        '				</div>' +
                        '			</div>' +
                        '		</div>' +
                        '	</div>' +
                        '</div>'
                });

                $("#listData").append(dataList);
                $("#pageNo").val(data.pageNo);
                if (data.pageNo === data.totalPages) {
                    $("#loading").hide();
                    $("#nomore").show();
                    $(document.body).destroyInfinite();
                } else {
                    index = 0;
                }

                var pImg = $(".fz-img").width();
                $(".fz-img").height(pImg);
            }
        });
    }

    //订单详情页
    function to_detail(orderId) {
        window.location = "${base}/mobile/orders/detail?orderId=" + orderId;
    }

    //商品详情页
    function to_product_detail(productStockId) {
        window.location = "${base}/mobile/product/detail?skuId=" + productStockId;
    }

    //订单评价页
    function to_assess(orderId) {
        window.location = "${base}/mobile/assess/add?orderId=" + orderId;
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
                                window.location.replace("${base}/mobile/orders/list");
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

</script>
</body>

</html>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>我要充值</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/reset.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/myself.css?ver=${ver}">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/fastclick.js?ver=${ver}"></script>
        <script type="text/javascript" src="${respath}/mobile/js/valid.js?ver=${ver}"></script>
		<style type="text/css">

		</style>
	</head>

	<body>
	<section>
		<div class="weui-cells__title pt-5 pb-5">请选择充值金额</div>
		<div class="recharge_body">
			<ul class="weichat_select">
				<c:forEach items="${rechargeTypeList}" var="item">
					<li giveAmount="${item.giveAmount}" typeId="${item.id}">${item.name}</li>
				</c:forEach>
			</ul>
		</div>
		<div style="clear: both"></div>
		<div id="giveAmount" class="weui-cells__title pt pb-5" style="display: none">赠送<span>0.00</span>元</div>
		<input type="tel" name="amount" class="otherInp" placeholder="请输入其他充值金额" id="amount" value="" />
		<div class="weui-cells__title pt pb-5">其他充值金额(充值金额不小于${setting.minRechargeAmount}元)</div>
		<div class="mt-30">
			<input style="border: 0px" type="button" id="recharge" class="btn_confirm" value="立即充值">
		</div>
	</section>

	<script type="text/javascript">
        $(function() {
            $(".weichat_select li").click(function() {
                $(this).addClass("on").siblings().removeClass("on");
                var giveAmount = $(this).attr("giveAmount");
                if(giveAmount>0){
					$("#giveAmount").show().find("span").html(giveAmount);
				}else{
                    $("#giveAmount").hide();
				}
            })
            $("#amount").click(function() {
                $(".weichat_select li").removeClass("on");
                $("#giveAmount").hide().find("span").html("0.00");
            });

            $("#recharge").click(function(){

                var typeId = $(".weichat_select li.on").attr("typeid");
                var amount = $("#amount").val();
                if(!typeId&&!amount){
                    $.toast("请选择充值金额","text");
                    return;
                }

                if(!typeId){
                    if(!isPrice(amount)){
                        $.toast("请输入正确的充值金额","text");
                        return;
                    }
                    if(amount<("${setting.minRechargeAmount}"/1)){
                        $.toast("最小充值金额为${setting.minRechargeAmount}元","text");
                        return;
                    }
                }
                $("#recharge").prop("disabled",true);
                $.showLoading("数据加载中...");
                $.ajax({
                    type:"post",
                    data:{
                        rechargeTypeId:typeId,
                        amount:amount,
						mp:"${member.type}",
                    },
                    url:"submit",
                    success:function(data){
                        $.hideLoading();
                        if(data.type=="success"){
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
                                    $.toast("支付成功","text",function(){
                                        window.location.reload();
                                    });
                                }else{
                                    $.toast("取消支付","text");
                                }
                            });
                        }else{
                            $.toast(data.content,"text");
                        }
                        $("#recharge").prop("disabled",false);
                    }
                });

            });

        })
	</script>
	</body>


</html>
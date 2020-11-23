<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>我的二维码</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/reset.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/main20171221.css?ver=${ver}">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	</head>
	<body>

	<div class="mzdj-qrcode">
		<img src="${setting.qrcodeImgUrl}" width="100%" />
		<div class="box-words">
			<P>会员福利想不停？享不停！</P>
			<P>邀请好友福利多多</P>
		</div>
		<img src="${qrcodeUrl}" class="codeBox"/>
		<a href="javascript:;" id="zyh_share03" class="mzdj-btn" style="margin: 10% auto;">立即邀请</a>

		<div id="p_bag" style="display: none;"></div>
		<div class="box_topShare">
			<div class="box_img" style="display: none;">
				<img src="${respath}/mobile/images/share.png" width="64%" style="float: right;margin-right: 4%;"/>
			</div>
			<!--<div class="close_shareBox">知道了</div>-->
		</div>
	</div>
	<script type="text/javascript">
        $(function() {
            $("#zyh_share03").click(function() {
                $("#p_bag").fadeIn();
//					$(".close_shareBox").fadeIn();
                $(".box_img").fadeIn();
                $(".box_topShare").css("display", "block");
            })
            $("#p_bag").click(function() {
                $(".box_img").fadeOut();
                $("#p_bag").fadeOut();
            });


            $.ajax({
                type:"post",
                url:"${base}/mobile/common/jsPermission",
                dataType:"json",
                data:{
                    url:window.location.href
                },success:function(data){
                    if(data.type="success"){
                        var jsPermission = data.data.jsPermission;
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: jsPermission.appId, // 必填，公众号的唯一标识
                            timestamp: jsPermission.timestamp, // 必填，生成签名的时间戳
                            nonceStr: jsPermission.noncestr, // 必填，生成签名的随机串
                            signature: jsPermission.signature,// 必填，签名，见附录1
                            jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage","hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                        wx.ready(function(){
                            wx.hideMenuItems({
                                menuList: [ "menuItem:openWithQQBrowser", "menuItem:openWithSafari","menuItem:copyUrl","menuItem:share:QZone","menuItem:share:qq"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
                            });
                            wx.onMenuShareTimeline({
                                title: '法滋蛋糕，会员福利享不停', // 分享标题
                                link: "${link}", // 分享链接
                                imgUrl: '${member.avatarUrl}', // 分享图标
                                success: function () {
                                    // 用户确认分享后执行的回调函数
                                },
                                cancel: function () {
                                    // 用户取消分享后执行的回调函数
                                }
                            });
                            wx.onMenuShareAppMessage({
                                title: '法滋蛋糕，会员福利享不停', // 分享标题
                                desc: '吃过很多蛋糕，却依旧忘不了法滋蛋糕', // 分享描述
                                link: "${link}", // 分享链接
                                imgUrl: '${member.avatarUrl}', // 分享图标
                                type: 'link', // 分享类型,music、video或link，不填默认为link
                                success: function () {
                                    // 用户确认分享后执行的回调函数
                                },
                                cancel: function () {
                                    // 用户取消分享后执行的回调函数
                                }
                            });
                        });
                    }
                }
            })


        })
	</script>
	</body>

</html>
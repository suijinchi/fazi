if($){
    if(typeof wx=="undefined"){
        document.write("<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>");
    }
    $.wxShare = function(json){
        if(json == undefined){
            json = {};
        }
        if(json.shareUrl==undefined){
            json.shareUrl = window.location.href;
        }
        if(json.title==undefined){
            json.title = '推荐一家好店，快来瞧一瞧';
        }
        if(json.desc==undefined){
            json.desc = '法滋蛋糕|FOCUSTASTE';
        }
        $.ajax({
            type:"post",
            url:(json.base?json.base:"")+"/mobile/common/jsPermission",
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
                            title: json.title, // 分享标题
                            link: json.shareUrl, // 分享链接
                            imgUrl: data.data.domain+'/resources/mobile/images/logo.png', // 分享图标
                            type: 'link',
                            success: function () {
                                // 用户确认分享后执行的回调函数
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                            }
                        });

                        wx.onMenuShareAppMessage({
                            title: json.title, // 分享标题
                            desc: json.desc, // 分享描述
                            link: json.shareUrl, // 分享链接
                            imgUrl: data.data.domain+'/resources/mobile/images/logo.png', // 分享图标
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
    }

}
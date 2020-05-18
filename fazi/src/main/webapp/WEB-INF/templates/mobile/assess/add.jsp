<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>商品评价</title>
    <link href="${respath}/mobile/css/weui.min.css" rel="stylesheet"/>
    <link href="${respath}/mobile/css/jquery-weui.css" rel="stylesheet"/>
    <link href="${respath}/mobile/css/reset.css" rel="stylesheet"/>
    <link href="${respath}/mobile/css/pingjia.css" rel="stylesheet"/>
    <link href="${respath}/mobile/css/iconfont.css" rel="stylesheet"/>
    <script src="${respath}/mobile/js/jquery-2.1.4.js"></script>
    <script src="${respath}/mobile/js/jquery-weui.js"></script>
    <script src="${respath}/mobile/js/jquery.form.js"></script>
    <script src="${respath}/mobile/js/layer/2.4/layer.js"></script>
    <script src="${respath}/mobile/js/layer-yw.js"></script>
    <style type="text/css">
        /*.weui-cells:after {
            bottom: 0;
            border: 0 solid #d9d9d9;
        }

        .weui-cells:before {
            top: 0;
            border-top: 1px solid #fff !important;
            -webkit-transform-origin: 0 0;
            transform-origin: 0 0;
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
        }*/
    </style>
</head>

<body style="background: #fff;">
<div class="wp xiaoyu_postbox" style="margin-top: 0px;background: #fff;">

    <c:forEach items="${productStockList}" var="productStock">
        <div class="post_from">
            <div class="weui-panel">
                <div class="weui-panel__bd">
                    <div class="weui-media-box weui-media-box_small-appmsg">
                        <div class="weui-cells">
                            <a class="weui-cell weui-cell_access" href="javascript:">
                                <div class="weui-cell__hd"><img src="${productStock.imgUrl}" alt=""
                                                                style="width:40px;height:40px;margin-right:5px;display:block">
                                </div>
                                <div class="weui-cell__bd weui-cell_primary">
                                    <p style="max-width: 80%;overflow: hidden;line-height: 40px;height: 40px;overflow: hidden;">${productStock.name}</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <ul class="cl">
                <li class="bl_none area luntan-inp">
                    <textarea psid="${productStock.id}" class="pt" id="needmessage" tabindex="3" autocomplete="off"
                              name="message" cols="80" rows="3" placeholder="评价一下我们的商品吧，15字以上可获得积分~" fwin="reply"></textarea>
                </li>
            </ul>
            <div class="weui-cells weui-cells_form" style="background: #fff;">
                <div class="weui-cell">
                    <div class="weui-cell__bd">
                        <div class="weui-uploader">
                            <div class="weui-uploader__bd" style="padding: 10px 0;">
                                <ul class="weui-uploader__files" id="psid${productStock.id}"></ul>
                                <label class="weui-uploader__input-box" for="otherInput${productStock.id}"></lable>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <form id="otherForm${productStock.id}">
                <input id="otherInput${productStock.id}" class="weui-uploader__input"
                       onchange="otherImage(${productStock.id});" type="file" accept="image/*" name="file"
                       style="display: none;">
            </form>
        </div>
    </c:forEach>

    <div class="box-pingjia-label">
        <ul>
            <li class="cur">味道很好</li>
            <li class="">还会再买的</li>
            <li class="">送货准时</li>
            <li class="">贴心小礼物</li>
            <li class="">原料优质</li>
            <div class="clear">

            </div>
        </ul>
    </div>
    <img src="${respath}/mobile/images/xingji_03.png" class="xingji"/>
    <div class="box-xingji" style="padding-top: 5%;">
        <div class="item_starLists">
            <span style="float: left;font-size: 14px;color: #4c4c4c;padding-right: 3%;">商品评价</span>
            <ul id="starsUl">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="item_starLists">
            <span style="float: left;font-size: 14px;color: #4c4c4c;padding-right: 3%;">客服评价</span>
            <ul id="starsUl2">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="item_starLists">
            <span style="float: left;font-size: 14px;color: #4c4c4c;padding-right: 3%;">物流评价</span>
            <ul id="starsUl3">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <div class="clear"></div>
        </div>
        <input type="hidden" id="productStarsCounts" value="">
        <input type="hidden" id="serviceStarsCounts" value="">
        <input type="hidden" id="logisticsStarsCounts" value="">
    </div>
</div>
<button id="save" onclick="save()" class="weui-btn weui-btn_primary"
        style="width: 80%;margin: 6% auto;background: #ff7d84;font-size:16px;">提交
</button>
</body>
<script type="text/javascript">

    $(function () {

        //选择标签
        $(".box-pingjia-label ul li").click(function () {
            var classValue = $(this).attr("class");
            if (classValue === 'cur') {
                $(this).removeClass("cur");
            } else {
                $(this).addClass("cur");
            }
        });

        //给星添加点击事件
        var productStarsCounts;
        var serviceStarsCounts;
        var logisticsStarsCounts;

        $("#starsUl li").click(function () {
            for (var i = 1; i < $("#starsUl li").length; i++) {
                $("#starsUl li").eq(i).removeClass("on");
                productStarsCounts = 0;
            }
            var whatColumn = $(this).index();
            for (var i = 0; i < whatColumn + 1; i++) {
                $("#starsUl li").eq(i).addClass("on");
                productStarsCounts++;
            }
            $('#productStarsCounts').val(productStarsCounts);
        });

        $("#starsUl2 li").click(function () {
            for (var i = 1; i < $("#starsUl2 li").length; i++) {
                $("#starsUl2 li").eq(i).removeClass("on");
                serviceStarsCounts = 0;
            }
            var whatColumn = $(this).index();
            for (var i = 0; i < whatColumn + 1; i++) {
                $("#starsUl2 li").eq(i).addClass("on");
                serviceStarsCounts++;
            }
            $('#serviceStarsCounts').val(serviceStarsCounts);
        });

        $("#starsUl3 li").click(function () {
            for (var i = 1; i < $("#starsUl3 li").length; i++) {
                $("#starsUl3 li").eq(i).removeClass("on");
                logisticsStarsCounts = 0;
            }
            var whatColumn = $(this).index();
            for (var i = 0; i < whatColumn + 1; i++) {
                $("#starsUl3 li").eq(i).addClass("on");
                logisticsStarsCounts++;
            }
            $('#logisticsStarsCounts').val(logisticsStarsCounts);
        });

    });

    function save() {

        var $textarea = $('textarea');
        var content = [];
        var flag = false;
        $.each($textarea, function (i, n) {
            if ($(n).val() != null && $(n).val() != '') {
                flag = true;
            }
            var d = {
                psid: $(n).attr('psid'),
                val: $(n).val()
            };
            content.push(d);
        });
        if (flag === false) {
            layer.msg("请至少填写一个商品的文字评价！", {icon: 0});
            return;
        }
        var contentStr = JSON.stringify(content);

        var $img = $('input[name="img"]');
        var img = [];
        $.each($img, function (i, n) {
            imgStr += $(n).attr('psid') + ':' + $(n).val() + ',';
            var i = {
                psid: $(n).attr('psid'),
                val: $(n).val()
            };
            img.push(i);
        });
        var imgStr = JSON.stringify(img);

        var productStarsCounts = $('#productStarsCounts').val();
        var serviceStarsCounts = $('#serviceStarsCounts').val();
        var logisticsStarsCounts = $('#logisticsStarsCounts').val();
        if (productStarsCounts === '') {
            layer.msg("请选择商品评价星级！", {icon: 0});
            return;
        }
        if (serviceStarsCounts === '') {
            layer.msg("请选择服务评价星级！", {icon: 0});
            return;
        }
        if (logisticsStarsCounts === '') {
            layer.msg("请选择物流评价星级！", {icon: 0});
            return;
        }

        var tags = '';
        var $tags = $('.box-pingjia-label ul li');
        $.each($tags, function (i, n) {
            var classValue = $(n).attr("class");
            if (classValue === 'cur') {
                tags += $(n).html() + ',';
            }
        });

        $.ajax({
            type: "post",
            url: "${base}/mobile/assess/addAssess",
            dataType: "json",
            data: {
                contentStr: contentStr,
                imgStr: imgStr,
                productStarsCounts: productStarsCounts,
                serviceStarsCounts: serviceStarsCounts,
                logisticsStarsCounts: logisticsStarsCounts,
                tags: tags,
                orderId: ${orderId}
            },
            beforeSend: function () {
                $("#save").prop("disabled", true);
            },
            success: function (data) {
                if (data.type === "success") {
                    layer.msg(data.content, {icon: 1, time: 2000}, function () {
                        window.location.replace("${base}/mobile/orders/list?type=iscomplete");
                    });
                } else {
                    layer.msg(data.content, {icon: 0});
                    $("#save").prop("disabled", false);
                }
            }
        });

    }

    function otherImage(productStockId) {
        var options = {
            url: '${base}/mobile/common/upload?fileType=image',
            type: 'post',
            dataType: 'json',

            beforeSend: function () {
                $.showLoading("上传中...");
            },
            success: function (data) {
                console.log(data);
                if (data.type === "success") {
                    $.hideLoading();
                    $('#psid' + productStockId).append(
                        '<li style="position: relative;" class="fl weui-uploader__file">' +
                        '	<div class="weui-uploader__file" style="background-image:url(' + data.data.absolute + ');position: relative;"></div>' +
                        '	<input name="img" type="hidden" psid="' + productStockId + '" value="' + data.data.absolute + '">' +
                        '	<a onclick="removeImage(this)">' +
                        '	<label style="position: absolute;left: 0px;top: -15px;" class="del">' +
                        '	<i class="icon iconfont" style="color: #e30001;">&#xe636;</i>' +
                        '	</label>' +
                        '	</a>' +
                        '</li>'
                    )
                } else {
                    $.hideLoading();
                    alert("上传失败");
                }
            },
            error: function (data) {
                console.log(data);
                $.hideLoading();
                alert("上传失败");
            }
        };
        $("#otherForm" + productStockId).ajaxSubmit(options);
    }

    function removeImage(dom) {
        $(dom).parent().remove();
    }

</script>

</html>
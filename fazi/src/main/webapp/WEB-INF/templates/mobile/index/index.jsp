<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>法滋蛋糕|FOCUSTASTE</title>
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}" />
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/tanchuang.css?ver=${ver}">
        <style type="text/css">
            .swiper-pagination-bullet-active {
                opacity: 1;
                background: #fe6901;
            }
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
            }
            .recommend-list li{
                border-radius: 6px;
                overflow: hidden;
            }
        </style>
    </head>

    <body style="background: #efefef;">
    <section class="min-width">
        <!--search-->
        <div class="weui-search-bar" id="searchBar">
            <form class="weui-search-bar__form" action="${base}/mobile/product/list">
                <div class="weui-search-bar__box">
                    <i class="weui-icon-search"></i>
                    <input type="search" class="weui-search-bar__input" id="searchInput" name="keyword" placeholder="搜索" required="">
                    <a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
                </div>
                <label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
                    <i class="weui-icon-search"></i>
                    <span>搜索蛋糕</span>
                </label>
            </form>
            <a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
        </div>
        <!--banner轮播-->
        <div class="fazi_banner">
            <div class="swiper-container swiper-container-banner">
                <div class="swiper-wrapper">
                    <c:forEach items="${ads}" var="ad">
                        <div class="swiper-slide">
                            <a href="${ad.link}"><img src="${ad.imgUrl}" alt="" width="100%" /></a>
                        </div>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <!--法滋头条-->
        <div class="fazi-toutiao">
            <ul class="weui-row">
                <li class="weui-col-20" style="position: relative;">
                    <a href="${base}/mobile/article/list?articleCategoryId=1">
                        <img src="${respath}/mobile/images/toutiao.png" class="toutiaoWord" />
                    </a>
                </li>
                <li class="weui-col-80">
                    <div class="swiper-container2 swiper-no-swiping">
                        <div class="swiper-wrapper">
                            <c:forEach items="${headline}" var="hl" varStatus="vs">
                                <c:if test="${vs.index%2==0}">
                                    <div class ="swiper-slide" onclick="jumpArticle(this)">
                                </c:if>
                                    <%--<a href="${base}/mobile/article/detail?id=${hl.id}" class="external"><span class="reyi">${hl.tagName}</span>${hl.title}</a>--%>
                                    <a aid="${hl.id}" class="external"><span class="reyi">${hl.tagName}</span>${hl.title}</a>
                                <c:if test="${(vs.index+1)%2==0||va.index==headline.size()}">
                                    </div>
                                </c:if>
                            </c:forEach>

                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <!--nav-->
        <div class="faziNavBox">
            <div class="faziNav">
                <div class="itemNav">
                    <a href="${base}/mobile/product/list" class="color_0">
                        <img src="${respath}/mobile/images/nav1.png" class="navIcon" />
                        <div class="navName">
                            蛋糕
                        </div>
                    </a>
                </div>
                <div class="itemNav">
                    <a href="${base}/mobile/product/list?productCategoryId=5" class="color_0">
                        <img src="${respath}/mobile/images/nav2.png" class="navIcon" />
                        <div class="navName">
                            下午茶
                        </div>
                    </a>
                </div>
                <div class="itemNav">
                    <a href="${base}/mobile/orders/list" class="color_0">
                        <img src="${respath}/mobile/images/nav3.png" class="navIcon" />
                        <div class="navName">
                            我的订单
                        </div>
                    </a>
                </div>
                <div class="itemNav">
                    <a href="${base}/mobile/product/spec" class="color_0">
                        <img src="${respath}/mobile/images/nav4.png" class="navIcon" />
                        <div class="navName">
                            企业专区
                        </div>
                    </a>
                </div>
                <div class="itemNav">
                    <%--<c:if test="${setting.memberWelfareUrl!=null && setting.memberWelfareUrl!=''}">
                        <a href="${setting.memberWelfareUrl}" class="color_0">
                    </c:if>
                    <c:if test="${setting.memberWelfareUrl==null || setting.memberWelfareUrl==''}">
                        <a class="color_0">
                    </c:if>
                        <img src="${respath}/mobile/images/nav5.png" class="navIcon" />
                        <div class="navName">
                            会员福利
                        </div>
                    </a>--%>
                    <a href="${base}/mobile/coupon_type/list" class="color_0">
                        <img src="${respath}/mobile/images/nav5.png" class="navIcon" />
                        <div class="navName">
                            会员福利
                        </div>
                    </a>
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <!--new-->
        <div class="newProduct bg-white">
            <a href="${base}/mobile/product/list?tagId=1" class="newTitle weui-row">
                <div class="newIcon">
                    新品
                </div>
                <div class="">
                    查看更多
                    <i class="icon iconfont">&#xe640;</i>
                </div>
            </a>
            <div class="fazi-goods-content">
                <ul>
                    <c:forEach items="${xinpin}" var="xp">
                    <li>
                        <a href="${base}/mobile/product/detail?skuId=${xp.productStock.id}">
                            <img style="max-height: 178px" src="${xp.longShowUrl}">
                        </a>
                        <div class="bottom-price-cart">
                            <a href="${base}/mobile/product/detail?skuId=${xp.productStock.id}">
                                <h4>
                                    <span class="title">${xp.name}</span>
                                    <span class="price">${xp.productStock.price}</span>
                                    <span>元
                                        <c:if test="${xp.productStock.specName!=null&&xp.productStock.specName!=''}">/${xp.productStock.specName}</c:if>
                                    </span>
                                </h4>
                                <p>${xp.subname}</p>
                            </a>
                            <a href="${base}/mobile/product/detail?skuId=${xp.productStock.id}">
                                <button class="addtoshopcart">
                                    <i class="new-home-icon"></i>
                                </button>
                            </a>
                        </div>

                    </li>
                    </c:forEach>

                </ul>
            </div>
        </div>

<%--

        <!--全部商品-->
        <div class="newProduct bg-white" style="margin-top: 10px;">
            <a href="${base}/mobile/product/list" class="newTitle weui-row">
                <div class="newIcon">
                    全部商品
                </div>
                <div class="">
                    查看更多
                    <i class="icon iconfont">&#xe640;</i>
                </div>
            </a>
            <div class="producthotLists haowuBox recommend-list">
                <ul>
                    <c:forEach items="${allProduct}" var="ap" varStatus="status">
                    <c:if test="${status.index < 6}">
                    <li>
                        <a href="${base}/mobile/product/detail?skuId=${ap.productStock.id}">
                            <img src="${ap.showUrl}" alt="双层芝士蛋糕" class="fz-img" style="height: 179px;">
                            <h4>${ap.name}</h4>
                            <p>￥${ap.productStock.price}<c:if test="${ap.productStock.specName!=null&&ap.productStock.specName!=''}">/${ap.productStock.specName}</c:if></p>
                        </a>
                        <div class="recommend-cart">
                            <i class="icon iconfont"></i>
                        </div>
                    </li>
                    </c:if>
                    </c:forEach>

                    <div class="clear">

                    </div>
                </ul>
            </div>
        </div>
--%>

        <div class="newProduct bg-white" style="margin-top: 10px;">
            <c:forEach items="${productCategoryList}" var="pc">
                <c:if test="${pc.productList!=null&&fn:length(pc.productList)>0}">
                <a href="${base}/mobile/product/list?productCategoryId=${pc.id}" class="newTitle weui-row">
                    <img src="${pc.indexShowUrl}" style="max-width: 100%">
                </a>
                <div class="producthotLists haowuBox recommend-list">
                    <ul>
                        <c:forEach items="${pc.productList}" var="ap" varStatus="status">
                            <li>
                                <a href="${base}/mobile/product/detail?skuId=${ap.productStock.id}">
                                    <img src="${ap.showUrl}" alt="${ap.name}" class="fz-img" style="height: 179px;">
                                    <h4>${ap.name}</h4>
                                    <p>￥${ap.productStock.price}<c:if test="${ap.productStock.specName!=null&&ap.productStock.specName!=''}">/${ap.productStock.specName}</c:if></p>
                                </a>
                                <div class="recommend-cart">
                                    <i class="icon iconfont"></i>
                                </div>
                            </li>
                        </c:forEach>
                        <div class="clear"></div>
                    </ul>
                </div>
                </c:if>
            </c:forEach>
        </div>

        <!--文章-->
        <div class="newProduct bg-white" style="margin-top: 10px;">
            <a href="${base}/mobile/article/list?articleCategoryId=2" class="newTitle weui-row">
                <div class="newIcon">
                    文章
                </div>
                <div class="">
                    查看更多
                    <i class="icon iconfont">&#xe640;</i>
                </div>
            </a>
        </div>
        <div class="faziArtical bg-white">
            <div class="swiper-container" id="articalWarp">
                <div class="swiper-wrapper">
                    <c:forEach items="${beautifulArticle}" var="ba">
                    <div class="swiper-slide">
                        <div class="shequnItem">
                            <a href="${base}/mobile/article/detail?id=${ba.id}">
                                <img src="${ba.showImageUrl}" width="100%" height="80px" class="sqImg" />
                                <div class="sqTitle">${ba.title}</div>
                            </a>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </section>
    <!--footer-->
    <div style="height: 80px;">

    </div>
    <div class="jffooterNav">
        <div class="weui-flex">
            <div class="weui-flex__item active">
                <a href="${base}/mobile/index">
                    <i class="icon iconfont" style="color: #ff4442;">&#xe6f5;</i>
                    <div class="jfFooterName" style="color: #ff4442;">
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

    <!--客服-->
    <div class="btn_kefu">
        <a href="http://55815633.im.m.weimob.com">
            <i class="icon iconfont">&#xe6fd;</i>
        </a>
    </div>

    <!--首页弹窗-->
    <div class="gonggao_wrap" id="gonggao_wrap">
        <h6>${setting.popupTitle}</h6>
        <div class="ad_con">
            <img src="${setting.popupImgUrl}" class="ad_img" />
            <p>${setting.popupContent}</p>
        </div>
        <div class="ad_oper">
            <a id="sbkk" style="color: #ff7d84;">随便看看</a>
            <a href="${setting.popupLink}" style="background: #ff7d84;">${setting.popupBtnText}</a>
        </div>
    </div>
    <div id="blankWrap" style="display: none"></div>

    <script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/swiper.min.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/fastclick.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/common.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/valid.js?ver=${ver}"></script>
    <script type="text/javascript">

        function jumpArticle(dom){
            var aids = "";
            $(dom).children("a").each(function(i,n){
                var aid = $(n).attr("aid");
                aids = aids+"&aid="+aid;
            });
            window.location = "${base}/mobile/article/list?articleCategoryId=1"+aids;
        }

        $(function() {

            var ad = getCookie("ad");
            if (!ad) {
                setCookie("ad", 1, 60*30);
                if("${setting.popupSwitch}"=="1"){
                    $("#blankWrap").show();
                    //广告弹窗
                    $(".gonggao_wrap").show();
                    $("#blankWrap").click(function(){
                        $(".gonggao_wrap").hide();
                        $("#blankWrap").hide();
                    });
                }
                $("#sbkk").click(function(){
                    $(".gonggao_wrap").hide();
                    $("#blankWrap").hide();
                });
            }

            /*轮播*/
            var swiper = new Swiper('.swiper-container-banner', {
                pagination: '.swiper-pagination',
                loop: true,
                autoplay: 3000
            });
            //法滋头条
            var swiper = new Swiper('.swiper-container2', {
                direction: 'vertical',
                /*slide无法拖动，只能使用扩展API函数改变拖动*/
                autoplay: 2000
            })
            //文章
            var swiper = new Swiper('#articalWarp', {
                slidesPerView: 2.3,
                spaceBetween: 10,
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                },
            });
            /*
            //点击加入购物车
            $(".addtoshopcart").click(function() {
                $.toast("加入成功", 50000);
            });
            */
            $.wxShare({base:"${base}"});

        });
    </script>
    </body>

</html>
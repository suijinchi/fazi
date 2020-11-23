<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>企业专区</title>
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}" />
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
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
        </style>
    </head>

    <body class="bg-white">
    <section class="min-width">

        <c:if test="${list!=null&&list.size()>0}">
        <!--new-->
        <div class="newProduct bg-white" style="margin:5px auto;">
            <div class="fazi-goods-content">
                <ul>
                    <c:forEach items="${list}" var="xp">
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
        </c:if>

        <c:if test="${list==null||list.size()==0}">
        <div class="newProduct bg-white">
            <div style="height: 80px;width: 100%;text-align: center">暂无商品</div>
        </div>
        </c:if>

    </section>

    <script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/swiper.min.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/fastclick.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js?ver=${ver}"></script>
    <script type="text/javascript" src="${respath}/mobile/js/common.js?ver=${ver}"></script>
    <script type="text/javascript">

    </script>
    </body>

</html>
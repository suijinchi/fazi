<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>个人中心</title>
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css" />
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
        <style type="text/css">
            #second_header{
                width: 100%;
                height: 80px;
                padding: 20px 0 30px 0;
                position: relative;
                color: #fff;
                background: -webkit-linear-gradient(left, #ff5b5a, #ff888e);
                background: -o-linear-gradient(left, #ff5b5a, #ff888e);
                background: -moz-linear-gradient(left, #ff5b5a, #ff888e);
                background: linear-gradient(left, #ff5b5a, #ff888e);
                z-index: 1;;
            }
            .f_s_9{
                font-size: 0.9em;
            }
        </style>
    </head>

    <body style="background: #efefef;">

    <div class="weui-row" id="second_header">
        <div class="weui-col-33">
            <img src="${member.avatarUrl}" alt="" width="70px" height="70px" style="border-radius: 10px;margin: 0 auto;display:block;float: right;">
        </div>
        <div class="weui-col-66" style="margin-left: 0;">
            <div class="itemMemberName">
                ${member.nickname}
            </div>
            <div class="itemMemberNum">
                <c:if test="${member.mobile==null}">暂无手机号</c:if>
                <c:if test="${member.mobile!=null}">${member.mobile}</c:if>
            </div>
            <div class="fz-rank">${memberRank.name}</div>
            <a href="${base}/mobile/sign/index" style="position: absolute;right:0;bottom:45px;background: #ffa910;color: #fff;border-radius:10px 0 0 10px;display: inline-block;text-align: center;padding: 1px 18px;font-size: 0.8em;">
                签到
            </a>
        </div>
    </div>
    <div class="mem-cen-nav2">
        <div class="weui-row">
            <a class="weui-col-50">
                <div class="s-number">${member.point}</div>
                <span class="m-shouyi">积分</span>
            </a>
            <a href="${base}/mobile/recharge/index" class="weui-col-50">
                <div class="s-number">￥${member.balance}</div>
                <span class="m-shouyi">余额</span>
            </a>
        </div>
    </div>
    <div class="weui-tab__bd">
        <div id="order_runing" class="weui-tab__bd-item weui-tab__bd-item--active">
            <div class="weui-cells" style="margin-top:0;">
                <a class="weui-cell weui-cell_access" href="${base}/mobile/orders/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-order.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">我的订单</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
                <a class="weui-cell weui-cell_access" href="${base}/mobile/member/qrcode">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon_invite.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">我的邀请码</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
                <a class="weui-cell weui-cell_access" href="${base}/mobile/team/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-team.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">我的好友</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

                <a class="weui-cell weui-cell_access" href="${base}/mobile/coupon/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-quan.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">优惠券</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
                <a class="weui-cell weui-cell_access" href="${base}/mobile/receiver/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-address.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">地址管理</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

                <a class="weui-cell weui-cell_access" href="${base}/mobile/recharge/index">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/recharge.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">我要充值</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
                <a class="weui-cell weui-cell_access" href="${base}/mobile/recharge/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/recharge_list.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">充值记录</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

                <a class="weui-cell weui-cell_access" href="${base}/mobile/point/list">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/btn-jifen_03.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">积分记录</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

                <a class="weui-cell weui-cell_access" href="${base}/mobile/member/info">
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-message.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">完善资料</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>

                <c:if test="${setting.memberWelfareUrl!=null && setting.memberWelfareUrl!=''}">
                    <a href="${setting.memberWelfareUrl}" class="weui-cell weui-cell_access">
                </c:if>
                <c:if test="${setting.memberWelfareUrl==null || setting.memberWelfareUrl==''}">
                    <a class="weui-cell weui-cell_access">
                </c:if>
                    <div class="weui-cell__hd"><img src="${respath}/mobile/images/icon-ware.png" alt="" style="width:30px;margin-right:5px;display:block"></div>
                    <div class="weui-cell__bd">
                        <p class="f_s_9">会员福利政策</p>
                    </div>
                    <div class="weui-cell__ft"></div>
                </a>
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
                    <i class="icon iconfont" style="color: #ff4442;">&#xe6f6;</i>
                    <div class="jfFooterName" style="color: #ff4442;">
                        我的
                    </div>
                </a>
            </div>

        </div>
    </div>

    <script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="${respath}/mobile/js/swiper.min.js"></script>
    <script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
    <script type="text/javascript" src="${respath}/mobile/js/fastclick.js"></script>
    <script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
    <script type="text/javascript">
        $(function() {

        });
    </script>
    </body>

</html>
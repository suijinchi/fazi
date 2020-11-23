<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
    <title>订单管理-详情</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
</head>

<body class="gray-bg">

    <input type="hidden" name="shippingMethod" id="shippingMethod" value="${order.shippingMethod}"/>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>
                        订单详情
                        <small class="m-l-sm"></small>
                    </h5>
                </div>
                <div class="ibox-content">

                    <div>
                        <div class="alert alert-warning">
                            <h5>当前订单状态：
                                <c:if test="${order.ordersStatus==0}">未确认</c:if>
                                <c:if test="${order.ordersStatus==1}">已确认</c:if>
                                <c:if test="${order.ordersStatus==2}">已完成</c:if>
                                <c:if test="${order.ordersStatus==3}">已取消</c:if>
                                <c:if test="${order.ordersStatus==4}">申请退款</c:if>
                                <c:if test="${order.ordersStatus==5}">退款中</c:if>
                                <c:if test="${order.ordersStatus==6}">已退款</c:if>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <button href="#flagform" data-toggle="modal" onclick="setFlaginfoOrder('${order.id}','${order.flagname}','${order.flaginfo}')" class="btn btn-white btn-sm" title="标记">标记</button>
                            </h5>
                            <c:if test="${(order.flaginfo!=null&&order.flaginfo!='')||(order.flagicon!=null&&order.flagicon!='')}">
                                <div class="m-1-xl m-b-xs">
                                    <p>
                                        标记 :
                                        <i class="${order.flagicon}"></i>
                                        <span class="m-l-xs">${order.flaginfo}</span>
                                    </p>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div>
                        <div class="alert alert-warning">
                            <h5>
                                客服确认状态：
                                <c:if test="${order.serviceConfirmStatus==0}">未确认</c:if>
                                <c:if test="${order.serviceConfirmStatus==1}">已确认</c:if>
                                <c:if test="${order.serviceConfirmStatus==0}">
                                    <button data-toggle="modal" onclick="serviceConfirm()" class="btn btn-white btn-sm" title="确认">确认</button>
                                </c:if>
                            </h5>
                        </div>
                    </div>

                    <div class="tabs-container">

                        <ul class="nav nav-tabs">
                            <li class="active"><a data-toggle="tab" href="#tab-product" aria-expanded="true"> 商品信息</a></li>
                            <li class=""><a data-toggle="tab" href="#tab-info" aria-expanded="false"> 订单信息</a></li>
                            <li class=""><a data-toggle="tab" href="#tab-member" aria-expanded="false"> 客户信息</a></li>
                            <li class=""><a data-toggle="tab" href="#tab-pay" aria-expanded="false"> 支付信息</a></li>
                            <c:if test="${refundList!=null&&refundList.size()>0}">
                                <li class=""><a data-toggle="tab" href="#tab-refund" aria-expanded="false"> 退款信息</a></li>
                            </c:if>
                        </ul>

                        <div class="tab-content">

                            <div id="tab-product" class="tab-pane  active">

                                <div class="panel-body">
                                    <div class="ibox-content">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>商品编号</th>
                                                <th>商品名称</th>
                                                <th>商品单价</th>
                                                <th>商品数量</th>
                                                <th>商品规格</th>
                                                <th>生日牌</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:if test="${productMapList!=null}">
                                                <c:forEach items="${productMapList}" var="data">
                                                    <tr>
                                                        <td>${data.sn}</td>
                                                        <td>${data.name}</td>
                                                        <td>${data.price}</td>
                                                        <td>${data.quantity}</td>
                                                        <td>${data.spec}</td>
                                                        <td>${data.birthdayCard}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <div class="panel-body">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>配送方式：</label>
                                            <c:if test="${shippingMethod==0}">到店自提</c:if>
                                            <c:if test="${shippingMethod==1}">商家配送</c:if>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>物流状态：</label>
                                            <c:if test="${shippingStatus==0 && shippingMethod==1}">未发货</c:if>
                                            <c:if test="${shippingStatus==1 && shippingMethod==1}">已发货</c:if>
                                            <c:if test="${shippingStatus==2 && shippingMethod==1}">已确认收货</c:if>
                                            <c:if test="${shippingStatus==0 && shippingMethod==0}">未提货</c:if>
                                            <c:if test="${shippingStatus==1 && shippingMethod==0}">已提货</c:if>
                                            <c:if test="${shippingStatus==2 && shippingMethod==0}">已确认收货</c:if>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>收货人：</label> ${order.name}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>收货人电话：</label> ${order.mobile}
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订货人：</label> ${order.orderer}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订货人电话：</label> ${order.ordererMobile}
                                        </div>
                                    </div>

                                    <c:if test="${shippingMethod==1}">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>地区：</label> ${area}
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>详细地址：</label> ${address}
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>配送费：</label> ￥${order.shippingFee}
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>配送时间：</label> ${order.shippingTime}
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>发货方式：</label> ${order.expressCompany}
                                            </div>
                                        </div>
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>配送单号：</label> ${order.expressSn}
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${shippingMethod==0}">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>自提时间：</label> ${order.pickUpTime}
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${shippingMethod==0}">
                                        <div class="col-sm-6">
                                            <div class="form-group">
                                                <label>自提地点：</label> ${order.pickUpAddress}
                                            </div>
                                        </div>
                                    </c:if>
                                </div>

                            </div>


                            <div id="tab-info" class="tab-pane">
                                <div class="panel-body">
                                    <div class="col-sm-12">
                                        <div class="form-group">

                                            <!-- 已支付未发货 -->
                                            <c:if test="${payStatus==1&&ordersStatus==1}">
                                                <c:if test="${shippingStatus==0}">
                                                    <a data-toggle="modal" class="btn btn-w-m btn-info" href="#shipping-form">发货</a>
                                                </c:if>
                                            </c:if>

                                            <%--<c:if test="${(order.ordersStatus ==1||order.ordersStatus==2) && order.payStatus == 1 && (order.shippingStatus == 0 || order.shippingStatus == 1 || order.shippingStatus == 2) }">--%>
                                            <c:if test="${ (order.ordersStatus ==1||order.ordersStatus ==4) && order.payStatus == 1}">
                                                <a onclick="f_refund()" type="button" class="btn btn-w-m btn-info">退款</a>
                                                <c:if test="${order.ordersStatus==4}">
                                                    <a type="button" class="btn btn-w-m btn-info" onclick="rejectRefund();">驳回</a>
                                                    <span class="help-block m-b-none" style="color: #ec4758"><i class="fa fa-info-circle"></i> 申请退款原因：${order.refundReason}</span>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${payStatus==1&&ordersStatus==4&&shippingStatus==0&&refundList!=null&&refundList.size()!=0}">
                                                <span class="help-block m-b-none" style="color: #ec4758"><i class="fa fa-info-circle"></i> 微信退款接口通知可能稍有延迟，请耐心等待</span>
                                            </c:if>

                                            <!--已确认未支付-->
                                            <c:if test="${ordersStatus==1&&payStatus==0}">
                                                <a data-toggle="modal" class="btn btn-w-m btn-info" href="#setAmount">调整支付金额</a>
                                            </c:if>

                                            <!-- 已支付已收货 -->
                                            <%--
                                            <c:if test="${payStatus==1}">
                                                <c:if test="${shippingStatus==2}">
                                                    <c:if test="${ordersStatus==1}">
                                                        <a onclick="f_complete()" type="button"
                                                           class="btn btn-w-m btn-info">完成</a>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                            --%>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订单编号：</label>${sn}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>创建日期：</label>${createDate}
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订单总金额：</label>￥${order.sumAmount}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订单支付金额：</label>￥${amount}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>调整金额：</label>￥${order.offsetAmount}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>积分支付额：</label>${order.pointPay}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>积分抵扣金额：</label>￥${order.pointOffset}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>余额支付金额：</label>￥${order.balancePay}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>第三方支付金额：</label>￥${order.thirdPay}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>卡券抵扣金额：</label>￥${order.couponPay}
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>会员折扣：</label>￥${order.discountPay}
                                        </div>
                                    </div>

                                    <c:if test="${order.pointReward>0}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>奖励积分：</label> ${order.pointReward} (确认收货后发放)
                                        </div>
                                    </div>
                                    </c:if>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>订单状态：</label>
                                            <c:if test="${order.ordersStatus==0}">未确认</c:if>
                                            <c:if test="${order.ordersStatus==1}">已确认</c:if>
                                            <c:if test="${order.ordersStatus==2}">已完成</c:if>
                                            <c:if test="${order.ordersStatus==3}">已取消</c:if>
                                            <c:if test="${order.ordersStatus==4}">申请退款</c:if>
                                            <c:if test="${order.ordersStatus==5}">退款中</c:if>
                                            <c:if test="${order.ordersStatus==6}">已退款</c:if>
                                        </div>
                                    </div>

                                    <c:if test="${order.completeDate!=null}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>完成时间：</label>
                                            <fmt:formatDate value="${order.completeDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </div>
                                    </div>
                                    </c:if>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>支付状态：</label>
                                            <c:if test="${payStatus==0}">未支付</c:if>
                                            <c:if test="${payStatus==1}">已支付</c:if>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>支付时间：</label>
                                            <fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>发货状态：</label>
                                            <c:if test="${order.shippingStatus==0}">未发货</c:if>
                                            <c:if test="${order.shippingStatus==1}">已发货</c:if>
                                        </div>
                                    </div>

                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label>买家留言：</label> ${memo}
                                        </div>
                                    </div>
                                </div>
                            </div>



                            <div id="tab-pay" class="tab-pane">

                                <div class="panel-body">
                                    <div class="ibox-content">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th>支付时间</th>
                                                <th>支付单号</th>
                                                <th>商户单号</th>
                                                <th>支付金额</th>
                                                <th>支付方式</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <c:if test="${payment!=null}">
                                                <tr>
                                                    <td><fmt:formatDate value="${payment.payDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                    <td>${payment.sn}</td>
                                                    <td>${payment.paySn}</td>
                                                    <td>￥${payment.amount}</td>
                                                    <td>微信支付</td>
                                                </tr>
                                                </c:if>
                                                <c:if test="${order.balancePay>0}">
                                                    <tr>
                                                        <td>-</td>
                                                        <td>-</td>
                                                        <td>-</td>
                                                        <td>￥${order.balancePay}</td>
                                                        <td>余额支付</td>
                                                    </tr>
                                                </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                            </div>

                            <div id="tab-member" class="tab-pane">
                                <div class="panel-body">
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>粉丝昵称：</label> ${member.nickname}
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>wechatID：</label> ${member.openid}
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>粉丝性别：</label>
                                            <c:if test="${member.gender==0}">女</c:if>
                                            <c:if test="${member.gender==1}">男</c:if>
                                            <c:if test="${member.gender==2}">未知</c:if>
                                        </div>
                                    </div>
                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>会员姓名：</label>
                                            <c:if test="${member.name!=null&&member.name!=''}">${member.name}</c:if>
                                            <c:if test="${member.name==null&&member.name==''}">-</c:if>
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>会员等级：</label> ${memberRank.name}
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>手机号码：</label>
                                            <c:if test="${member.mobile!=null&&member.mobile!=''}">${member.mobile}</c:if>
                                            <c:if test="${member.mobile==null&&member.mobile==''}">-</c:if>
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>会员余额：</label>${member.balance}
                                        </div>
                                    </div>

                                    <div class="col-sm-12">
                                        <div class="form-group">
                                            <label>会员积分：</label>${member.point}
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <c:if test="${refundList!=null&&refundList.size()>0}">
                                <div id="tab-refund" class="tab-pane">
                                    <div class="panel-body">
                                        <div class="ibox-content">
                                            <table class="table table-bordered">
                                                <thead>
                                                <tr>
                                                    <th>退款单号</th>
                                                    <th>退款时间</th>
                                                    <th>余额退款</th>
                                                    <th>微信支付退款</th>
                                                    <th>状态</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:if test="${refundList!=null}">
                                                    <c:forEach items="${refundList}" var="data">
                                                        <tr>
                                                            <td>${data.sn}</td>
                                                            <td>
                                                                <fmt:formatDate value="${data.createDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                                            </td>
                                                            <td>￥${data.balancePayRefund}</td>
                                                            <td>￥${data.thirdPayRefund}</td>
                                                            <td>
                                                                <c:if test="${data.status==1}">退款中</c:if>
                                                                <c:if test="${data.status==2}">退款已到账</c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </c:if>


                        </div>
                    </div>
                    <div class="m-t">
                        <%--<a href="${base}/admin/orders/list?orderStatus=${orderStatus}&payStatus=${payStatus}&sendStatus=${sendStatus}&shippingMethod=${shippingMethod}" target="_self" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;返回</a>--%>
                            <a href="${base}/admin/orders/list" target="_self" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;返回</a>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div id="flagform" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group">
                            <label>标记：</label>
                            <div id="flag">
                                <label class="radio-inline">
                                    <input type="radio" name="flagname" value="1" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-danger" style="position: relative;top: 0;left: 5px;"></i>
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="flagname" value="2" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-warning" style="position: relative;top: 0;left: 5px;"></i>
                                </label>
<%--

                                <label class="radio-inline">
                                    <input type="radio" name="flagname" value="3" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-primary" style="position: relative;top: 0;left: 5px;"></i>
                                </label>
--%>

                                <label class="radio-inline">
                                    <input type="radio" name="flagname" value="4" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-info" style="position: relative;top: 0;left: 5px;"></i>
                                </label>

                                <label class="radio-inline">
                                    <input type="radio" name="flagname" value="5" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-success" style="position: relative;top: 0;left: 5px;"></i>
                                </label>

                                <label class="radio-inline" style="display: none">
                                    <input id="deleteIcon" type="radio" name="flagname" value="0" style="position: relative;top: 0;">
                                    <i class="fa fa-flag fa-lg text-muted " style="position: relative;top: 0;left: 5px;"></i>
                                </label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label>标记信息：</label>
                            <input type="" id="flaginfo" name="flaginfo" placeholder="请填写标记信息" class="form-control">
                        </div>

                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" onclick="confirmFlaginfo()" class="btn btn-primary">确定</button>
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="confirmFlaginfo('1')" class="btn btn-white" data-dismiss="modal">删除</button>
                </div>

            </div>
        </div>
    </div>

    <%--<div id="modal-form" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group">
                            <label>快递方式：</label> <input type="" id="expressCompany" name="expressCompany" placeholder="请输入快递方式"
                                class="form-control">
                        </div>
                        <div class="form-group">
                            <label>快递单号：</label> <input type="" id="expressSn" name="expressSn" placeholder="请输入快递单号"
                                class="form-control">
                        </div>
                        <div>
                            <button class="btn btn-sm btn-primary pull-right m-t-n-xs"
                                type="submit" onclick="f_confirmExpress();">
                                <strong>确认发货</strong>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>--%>

    <div id="shipping-form" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">

                        <div class="form-group">
                            <label>发货方式：</label>
                            <div class="radio i-checks">
                                <label>
                                    <input name="shippingType" shippingMethod="1" type="radio" <c:if test="${order.shippingMethod==1}">checked</c:if> value="法滋物流"> <i></i> 法滋物流
                                </label>
                                <label>
                                    <input name="shippingType" shippingMethod="1" type="radio" value="打车配送"> <i></i> 打车配送
                                </label>
                                <label>
                                    <input name="shippingType" shippingMethod="0" type="radio" <c:if test="${order.shippingMethod==0}">checked</c:if> value="客户自提"> <i></i> 客户自提
                                </label>
                            </div>
                        </div>

                        <c:if test="${order.shippingMethod==0}">
                            <input type="" id="expressCompany" name="expressCompany" value="客户自提" placeholder="请输入快递方式" class="form-control" style="display: none">
                        </c:if>
                        <c:if test="${order.shippingMethod==1}">
                            <input type="" id="expressCompany" name="expressCompany" value="打车配送" placeholder="请输入快递方式" class="form-control" style="display: none">
                        </c:if>

                        <div id="expressSnDiv" class="form-group" <c:if test="${order.shippingMethod==0}">style="display: none"</c:if>>
                            <label>快递单号：</label>
                            <input type="" id="expressSn" name="expressSn" placeholder="请输入快递单号" class="form-control">
                        </div>

                        <div>
                            <button class="btn btn-sm btn-primary pull-right m-t-n-xs" type="submit" onclick="f_confirmShipping();">
                                <strong>确认发货</strong>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- 设置支付金额 -->
    <div id="setAmount" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group">
                            <p><span style="color: red;">提示：</span>请输入调整后金额</p>
                        </div>
                        <div class="form-group">
                            <label>订单总金额(商品+邮费)：</label>
                            <span>${order.sumAmount}</span>
                        </div>
                        <div class="form-group">
                            <label>订单支付金额：</label>
                            <span>${order.amount}<c:if test="${order.balancePay>0}">(余额已支付￥${order.balancePay})</c:if></span>
                        </div>
                        <div class="form-group">
                            <label>调整后金额：</label>
                            <input type="" id="amount" value="" name="amount" placeholder="请输入调整后金额" class="form-control">
                        </div>
                        <div>
                            <button class="btn btn-sm btn-primary pull-right m-t-n-xs" type="submit" onclick="setAmount()">
                                <strong>确认调整</strong>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>


        var setAmountFlag = false;
        function setAmount() {
            var amount = $("#amount").val();
            if (!isPrice(Math.abs(amount))) {
                layer.msg("请输入正确的支付金额");
                setAmountFlag = false;
                return;
            }
            $.ajax({
                type: "post",
                url: "${base}/admin/orders/set_amount?orderId=${orderId}",
                dataType: "json",
                data: {
                    amount: amount
                },
                success: function (data) {
                    if (data.type === "success") {
                        layer.msg(data.content, {icon: 1, time: 1000}, function () {
                            window.location.reload();
                        });
                    } else {
                        setAmountFlag = false;
                        layer.alert(data.content);
                    }
                },
                error: function () {
                    layer.alert("系统异常");
                }
            });
        }


        var oid = "";
        function setFlaginfoOrder(id,flagname,flaginfo){
            oid = id;
            $("#flag input[value='"+flagname+"']").prop("checked",true);
            $("#flaginfo").val(flaginfo);
        }
        function confirmFlaginfo(isDelete){
            if(isDelete=="1"){
                $("input[name='flagname']").prop("checked",false);
                $("#deleteIcon").prop("checked",true);
                $("#flaginfo").val("");
            }
            var flagname = $("input[name='flagname']:checked").val();
            var flagicon = $("input[name='flagname']:checked").next().attr("class");
            var flaginfo = $("#flaginfo").val();
            $.ajax({
                type:"post",
                dataType:"json",
                data:{
                    flagname:flagname,
                    flaginfo:flaginfo,
                    flagicon:flagicon,
                    orderId:oid
                },
                url:"change_flaginfo",
                success:function(data){
                    if(data.type=="success"){
                        window.location.reload();
                    }
                }
            });
        }


        $(function () {

            $(".full-height-scroll").slimScroll({
                height: "100%"
            });

            laydate.render({
                elem: '#startDate',
                type: 'datetime'
            });

            laydate.render({
                elem: '#endDate',
                type: 'datetime'
            });

            $("input:radio[name='shippingType']").on('ifChecked', function(event){
                var st = $(this).attr("shippingMethod");
                var shippingType = $(this).val();
                $("#expressCompany").val(shippingType);
                if(st=="0"){
                    $("#expressSnDiv").hide();
                }else{
                    $("#expressSnDiv").show();
                }
                $("#shippingMethod").val(st);
            });

            $(".i-checks").iCheck({
                checkboxClass: "icheckbox_square-green",
                radioClass: "iradio_square-green"
            });

        });

        var submitRefund = false;
        function f_refund(){
            //如果用户已经确认收货，用户获得的积分可能无法收回）！
            layer.confirm('确认进行退款操作吗？', function (index) {
                if(submitRefund){
                    return;
                }
                submitRefund = true;
                $.ajax({
                    type: "post",
                    url: "${base}/admin/orders/refund?orderId=${orderId}",
                    dataType: "json",
                    data: {},
                    success: function (data) {
                        if (data.type === "success") {
                            layer.alert(data.content,function(){
                                window.location.reload();
                            });
                        }else{
                            submitRefund = false;
                            layer.alert(data.content);
                        }
                    },error:function(){
                        submitRefund = false;
                        layer.alert("系统异常");
                    }
                });
            });
        }

        //发货
        function f_confirmShipping() {
            var sm = $("#shippingMethod").val();
            if(sm=="0"){//自提
                layer.confirm('确认客户提货？', function (index) {
                    $.ajax({
                        type: "post",
                        url: "${base}/admin/orders/confirm",
                        dataType: "json",
                        data: {
                            orderId:"${order.id}",
                            shippingMethod:sm
                        },
                        success: function (data) {
                            if (data.type === "success") {
                                layer.alert(data.content,function(){
                                    window.location.reload();
                                });
                            }else{
                                layer.alert(data.content);
                            }
                        },error:function(){
                            layer.alert("系统异常");
                        }
                    });
                });
            }else{//发货
                var expressCompany = $("#expressCompany").val();
                var expressSn = $("#expressSn").val();
                var shippingMethod = $("#shippingMethod").val();
                layer.confirm('确认发货？', function (index) {
                    $.ajax({
                        type: "post",
                        url: "${base}/admin/orders/confirm",
                        dataType: "json",
                        data: {
                            orderId:"${orderId}",
                            expressCompany:expressCompany,
                            expressSn:expressSn,
                            shippingMethod:shippingMethod
                        },
                        success: function (data) {
                            if (data.type === "success") {
                                layer.alert(data.content,function(){
                                    window.location.reload();
                                });
                            }else{
                                layer.alert(data.content);
                            }
                        },error:function(){
                            layer.alert("系统异常");
                        }
                    });
                });
            }

        }

        //完成
        function f_complete() {
            layer.confirm('确认订单已完成吗？', function (index) {
                $.ajax({
                    type: "post",
                    url: "${base}/admin/orders/complete?orderId=${orderId}",
                    dataType: "json",
                    data: $("#modal-form").serialize(),
                    success: function (data) {
                        if (data.type === "success") {
                            layer.alert(data.content,function(){
                                window.location.reload();
                            });
                        }else{
                            layer.alert(data.content);
                        }
                    },error:function(){
                        layer.alert("系统异常");
                    }
                });
            });
        }

        //申请退款驳回
        function rejectRefund(){
            layer.confirm('确认驳回？驳回后订单状态撤回为已确认', {
                btn: ['确认','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    dataType:"json",
                    data:{
                        id:"${order.id}"
                    },
                    url:"${base}/admin/orders/rejectRefund",
                    success:function (data) {
                        if(data.type=="success"){
                            layer.msg(data.content, {icon: 1},function(){
                                window.location.reload();
                            });
                        }else{
                            layer.alert(data.content, {icon: 0});
                        }
                    },error:function(){
                        layer.alert("系统异常");
                    }
                });
            }, function(){

            });
        }

        function serviceConfirm(){
            $.ajax({
                type:"post",
                dataType:"json",
                data:{
                    id:"${order.id}"
                },
                url:"${base}/admin/orders/serviceConfirm",
                success:function (data) {
                    if(data.type=="success"){
                        layer.msg(data.content, {icon: 1,time:1000},function(){
                            window.location.reload();
                        });
                    }else{
                        layer.alert(data.content, {icon: 0});
                    }
                },error:function(){
                    layer.alert("系统异常");
                }
            });
        }
    </script>

</body>
</html>
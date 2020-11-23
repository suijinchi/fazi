<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>确认订单</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge">

		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/every.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/order_agree.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css?ver=${ver}" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/address.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/newaddress.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/quan.css?ver=${ver}">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery.lSelect.js?ver=${ver}"></script>
		<script type="text/javascript" src="${respath}/mobile/js/valid.js?ver=${ver}"></script>
		<style type="text/css">
			.weui-switch-cp__box:before,
			.weui-switch:before {
				width: 27px!important;
				height: 24px!important;
			}

			.weui-switch-cp__box:after,
			.weui-switch:after {
				width: 27px!important;
				height: 24px!important;
			}

			.weui-switch,
			.weui-switch-cp__box {
				position: relative;
				width: 52px!important;
				height: 26px!important;
			}

			.cart-pro-list li {
				margin-bottom: 0;
				border-bottom: 1px solid #d1d1d1;
			}

			.cart-pro-box {
				height: auto!important;
			}

			.cart-pro-box img {
				display: block;
				float: left;
				width: 91px!important;
			}

			.cart-pro-box span {
				margin-top: 5px;
			}

			.cart-pro-box .cart-pro-title {
				padding-left: 107px!important;
			}

			.weui-check__label:active,
			.weui-cell_access:active {
				background-color: #fff!important;
			}

			.weui-cells {
				font-size: 0.98em!important;
			}

			.weui-picker-modal .picker-items {
				font-size: 1em!important;
			}

			.pstime {
				width: 100%;
				height: 20px;
				line-height: 20px;
				color: #ff4442;
				text-align: center;
				font-size: 0.7em;
				font-weight: normal;
				line-height: 4.4rem!important;
			}

			.toolbar .toolbar-inner {
				height: 2.8rem;
				display: -webkit-box;
				display: -ms-flexbox;
				display: flex;
				text-align: center;
			}

			.toolbar .title {
				position: absolute;
				display: block;
				width: 100%;
				padding: 0;
				font-size: .85rem;
				font-weight: normal;
				line-height: 2.4rem!important;
				color: #3d4145;
				text-align: center;
				white-space: nowrap;
			}

            .box-park-quan .current{
                background: url(${respath}/mobile/images/right.png) right top no-repeat;
                background-size: 25px auto;
            }
			.cuc{
				text-align: center;
				margin: 20px;
			}
			.cuc input{
				width: 40%;
				height: 30px;
				border: 0px;
				color: #fff;
				background-color: #ff4442;
				border-radius: 3px;
			}
		</style>

	</head>
	<body>

		<section class="min-width">

			<!-- 商品价格 -->
			<input type="hidden" type="productPrice" name="productPrice" value="${finalProductPrice}" id="productPrice"/>

			<!--商品最大可抵扣积分-->
			<input type="hidden" type="maxOffsetPoint" name="maxOffsetPoint" value="${maxOffsetPoint}" id="maxOffsetPoint"/>
			<!--最大抵扣余额，实际为用户余额-->
			<input type="hidden" type="maxOffsetBalance" name="maxOffsetBalance" value="${member.balance}" id="maxOffsetBalance"/>

			<!--积分抵扣比例-->
			<input type="hidden" name="pointRatio" value="${setting.pointRatio}" id="pointRatio"/>

			<!--用户积分-->
			<input type="hidden" name="point" value="${member.point}" id="point"/>
			<!--用户余额-->
			<input type="hidden" name="balance" value="${member.balance}" id="balance"/>

			<!--存储当前选择的优惠券是否与积分同享-->
			<input type="hidden" name="isWithPointShare" value="1" id="isWithPointShare"/>

			<form id="orderForm">

			<!--发货方式 0自提 1快递-->
			<input type="hidden" name="shippingMethod" value="1" id="shippingMethod"/>
			<!-- 实际抵扣积分 -->
			<input type="hidden" name="offsetPoint" value="0" id="offsetPoint"/>
			<!-- 实际积分抵扣金额 -->
			<input type="hidden" name="offsetPointAmount" value="0" id="offsetPointAmount"/>
			<!-- 实际抵扣余额 -->
			<input type="hidden" name="offsetBalance" value="0" id="offsetBalance"/>
			<!-- 实际配送费用 -->
			<input type="hidden" name="shippingFee" value="${setting.shippingFee}" id="shippingFee"/>
			<!-- 微信支付需要支付,也就是剩余需要支付的金额 -->
			<input type="hidden" name="thirdAmount" value="0" id="thirdAmount"/>
			<!-- 收货人id -->
			<c:if test="${receiver!=null}">
				<input type="hidden" name="receiverId" value="${receiver.id}" id="receiverId"/>
			</c:if>
			<c:if test="${receiver==null}">
				<input type="hidden" name="receiverId" value="" id="receiverId"/>
			</c:if>

            <!--优惠券id-->
            <input type="hidden" name="couponId" value="" id="couponId"/>
            <input type="hidden" name="offsetCouponAmount" value="0" id="offsetCouponAmount"/>

			<!--<div class="toolbar">
				<div class="toolbar-inner"><a href="javascript:;" class="picker-button close-picker">完成</a>
					<h2 class="pstime">我们将在配送阶段的200分钟内确保送达</h2>
					<h1 class="title">请选择配送时间</h1>
				</div>
			</div>-->
			<!--<div class="toolbar"><div class="toolbar-inner"><a href="javascript:;" class="picker-button close-picker">完成</a><h2 class="pstime">我们将在配送阶段的200分钟内确保送达</h2><h1 class="title">请选择配送时间</h1></div></div>-->
			<!--订单确认-->
			<div id="main" class="btn-order-agree" style="display: block;">
				<div class="box_distribution">
					<ul>
						<li class="title">
							配送方式
						</li>
						<li class="shangjia cur">
							<a href="#">商家配送</a>
						</li>
						<li class="ziti">
							<a href="#">到店自提</a>
						</li>
					</ul>
				</div>

				<c:if test="${receiver==null}">
                    <!--填写收货地址-->
                    <div id="address">
                        <a class="btn-go-addressLists">
                            <div onclick="addressLists()">
                                <i class="i1">
                                    <I class="icon iconfont">&#xe664;</I>
                                </i>
                                <span id="add_address">请填写收货地址</span>
                                <i class="i2 rt"><img src="${respath}/mobile/images/Store-entry_arrrow.png" alt=""  style="display: inline-block;"/></i>
                            </div>
                            <img src="${respath}/mobile/images/address_line_02.png" width="100%" />
                        </a>
                    </div>
					<div id="address-finish" class="zt_finish"></div>
				</c:if>

				<c:if test="${receiver!=null}">
				<div id="address"></div>
				<!--显示收货地址-->
				<div id="address-finish" class="zt_finish">
					<a class="btn-go-addressLists">
						<div class="addressContent" onclick="addressLists()">
							<i class="i1 lt">
								<I class="icon iconfont">&#xe664;</I></i>
							<div class="box-userAddress lt">
								<div class="u-name">
									${receiver.consignee}&nbsp;${receiver.contactNumber}
								</div>
								<div class="u-address">
									${receiver.areaName}${receiver.address}
								</div>
							</div>
							<i class="i2 rt" style="display: inline-block;"><img src="${respath}/mobile/images/Store-entry_arrrow.png" alt=""  style="display: inline-block;"/></i>
						</div>
						<img src="${respath}/mobile/images/address_line_02.png" width="100%" />
					</a>
				</div>
				</c:if>

				<!--不在配送范围-->
				<!--
				<div id="box-no-peisong">
					<div class="box-no-peisong bg-white">
						<div class="no-left">
							<i class="icon iconfont">&#xe6bb;</i>
						</div>
						<div class="no-right">
							你的商品不在当前可配送范围， 请选择就近自提点进行下单。
						</div>
					</div>
					<img src="images/address_line_02.png" width="100%" />
				</div>
				-->
				<div class="shopcartBox">
					<ul class="cart-pro-list" id="cart-object-list">


						<c:forEach items="${cartItemList}" var="cartItem">
						<li class="cart-object-goods-item scene_area">
							<div class="cart-pro-box">
								<a>
									<img src="${cartItem.showUrl}" class="fz-img">
								</a>
								<div class="cart-pro-title">
									<a>
										<h3>
											${cartItem.name}
										</h3>
										<h2 style="font-weight: normal;">
											<span>${cartItem.subname}</span>
										</h2>
									</a>
									<c:if test="${cartItem.specName!=null&&cartItem.specName!=''}">
										<span>规格：${cartItem.specName}</span>
									</c:if>
									<c:if test="${cartItem.specName==null||cartItem.specName==''}">
										<span>&nbsp;</span>
									</c:if>
									<span class="cart-price" data-amount="${cartItem.price}">
										￥${cartItem.price}
										<font style="color: #666">×${cartItem.quantity}</font>
									</span>
								</div>

							</div>
						</li>
						</c:forEach>

					</ul>
				</div>
				<div class="weui-panel" style="font-size: 0.95em;">
					<div class="weui-panel__bd">
						<div class="weui-media-box weui-media-box_small-appmsg">
							<div class="weui-cells">
								<!--<div id="btn-pstime">-->
								<%--<a class="weui-cell weui-cell_access" id="btn-pstime" href="javascript:;">
									<div class="weui-cell__bd weui-cell_primary">
										<p>配送时间</p>
									</div>
									<span class="weui-cell__ft"><input type="text" id='pstime' name="shippingTime" placeholder="请选择"/></span>
								</a>--%>

								<a class="weui-cell weui-cell_access" id="btn-psdate" href="javascript:;">
									<div class="weui-cell__bd weui-cell_primary">
										<p>配送日期</p>
									</div>
									<span class="weui-cell__ft">
										<input type="text" readonly id="psdate" placeholder="请选择"/>
										<input type="hidden" id="psdates" name="psdate" placeholder="请选择"/>
									</span>
									<%--<span class="weui-cell__ft">
										<select id="psdate" name="psdate">
											<option value="">请选择</option>
										</select>
									</span>--%>
								</a>
								<a class="weui-cell weui-cell_access" id="btn-pstime" href="javascript:;">
									<div class="weui-cell__bd weui-cell_primary">
										<p>配送时间</p>
									</div>
									<span class="weui-cell__ft" id="pstimeSpan">
										<input readonly type="text" id='pstime' name="pstime" placeholder="请选择"/>
									</span>
									<%--<span class="weui-cell__ft">
										<select id="pstime" name="pstime">
											<option value="">请选择</option>
										</select>
									</span>--%>
								</a>


								<a class="weui-cell weui-cell_access" id="btn-pickUpDate" href="javascript:;" class="btn-ziti-time">
									<div class="weui-cell__bd weui-cell_primary">
										<p>自提日期</p>
									</div>
									<span class="weui-cell__ft">
										<input type="text" readonly id='pickUpDate' placeholder="请选择"/>
										<input type="hidden" id='pickUpDates' name="pickUpDate" placeholder="请选择"/>
									</span>
								</a>
								<a class="weui-cell weui-cell_access" id="btn-pickUpTime" href="javascript:;" class="btn-ziti-time">
									<div class="weui-cell__bd weui-cell_primary">
										<p>自提时间</p>
									</div>
									<span class="weui-cell__ft" id="pickUpTimeSpan">
										<input readonly type="text" id="pickUpTime" name="pickUpTime" placeholder="请选择"/>
									</span>
								</a>

								<a class="weui-cell weui-cell_access" id="btn-ztplace" href="javascript:;" class="btn-ziti-place">
									<div class="weui-cell__bd weui-cell_primary">
										<p>自提点</p>
									</div>
									<span class="weui-cell__ft"><input type="text" id='ztPlace' name="pickUpAddress" placeholder="请选择"/></span>
								</a>

								<a class="weui-cell weui-cell_access" id="btn-ztname" href="javascript:;" class="btn-ziti-place">
									<div class="weui-cell__bd weui-cell_primary">
										<p>姓名</p>
									</div>
									<input type="text" name="name" id="name" value="" placeholder="请输入自提人姓名" style="border: 0px;text-align: right" onfocus="this.style.color='#333'" />
								</a>

								<a class="weui-cell weui-cell_access" id="btn-ztmobile" href="javascript:;" class="btn-ziti-place">
									<div class="weui-cell__bd weui-cell_primary">
										<p>联系电话</p>
									</div>
									<input type="tel" name="mobile" id="mobile" value="" placeholder="请输入自提人联系电话" style="border: 0px;text-align: right" onfocus="this.style.color='#333'" />
								</a>

								<c:if test="${couponList!=null&&couponList.size()>0}">
								<a class="weui-cell weui-cell_access" id="youhuiquan">
									<div class="weui-cell__bd weui-cell_primary">
										<p>优惠券</p>
									</div>
									<span id="selectCoupon" class="weui-cell__ft">选择</span>
								</a>
								</c:if>
								<%--
								<a class="weui-cell weui-cell_access" id="daijinquan">
									<div class="weui-cell__bd weui-cell_primary">
										<p>代金券</p>
									</div>
									<span class="weui-cell__ft">无可用</span>
								</a>
								--%>
							</div>
						</div>
					</div>
				</div>
				<div class="order-agree-small-appmsg">
					支付方式<span style="font-size: 12px">(商品最多抵扣${maxOffsetPoint}积分)</span>
				</div>
				<div class="weui-panel" style="font-size: 0.95em;margin-top: 0;">
					<div class="weui-panel__bd">
						<div class="weui-media-box weui-media-box_small-appmsg">
							<div class="weui-cells">

								<a class="weui-cell weui-cell_access" href="javascript:;">
									<div class="weui-cell__bd weui-cell_primary">
										<span style="font-size: 12px;float: left;">积分抵扣(${member.point}积分)</span>
										<span id="offsetPointDisp" style="font-size: 12px;color: red;display: none;">已抵扣￥0.00元</span>
										<label for="switchPoint" class="weui-switch-cp" style="float: right">
											<input id="switchPoint" class="weui-switch-cp__input" type="checkbox">
											<div class="weui-switch-cp__box"></div>
										</label>
									</div>
								</a>

								<a class="weui-cell weui-cell_access" href="javascript:;">
									<div class="weui-cell__bd weui-cell_primary">
										<span style="font-size: 12px;float: left;">余额支付(￥${member.balance})</span>
                                        <span id="offsetBalanceDisp" style="font-size: 12px;color: red;display: none;">已抵扣￥0.00元</span>
										<label for="switchBalance" class="weui-switch-cp" style="float: right">
											<input id="switchBalance" class="weui-switch-cp__input" type="checkbox">
											<div class="weui-switch-cp__box"></div>
										</label>
									</div>
								</a>

								<div class="weui-cell weui-cell_access" style="padding-top: 1px;padding-bottom: 0;">
									<div class="weui-cell__bd weui-cell_primary">
										<div class="weui-cells weui-cells_checkbox">
											<label class="weui-cell weui-check__label" for="switchWechat" style="padding: 10px 0;">
												<div class="weui-cell__bd">
													<p style="background: url(${respath}/mobile/images/weichat.png) left center no-repeat;background-size: 18px auto;padding-left: 22px;">微信支付</p>
												</div>
												<div class="weui-cell__hd">
													<input type="checkbox" class="weui-check" name="switchWechat" readonly id="switchWechat" checked="checked">
													<i class="weui-icon-checked"></i>
												</div>
											</label>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="gd"></div>
				<div class="box-order-fapiao">
					<ul>
						<li>
							<label class="fl">商品合计：</label>
							<label class="fr color_9"><span id="productPriceBottom">￥${productPrice}</span></label>
							<div class="clear">

							</div>
						</li>
						<li>
							<label class="fl">积分抵扣<span id="offsetPointBottom"></span></label>
							<label class="fr color_9">-<span id="offsetPointAmountBottom">0.00</span></label>
							<div class="clear"></div>
						</li>
						<li>
							<label class="fl">余额抵扣</label>
							<label class="fr color_9">-<span id="offsetBalanceBottom">0.00</span></label>
							<div class="clear"></div>
						</li>
						<li>
							<label class="fl">优惠券抵扣</label>
							<label class="fr color_9">-<span id="coupponOffsetBottom">0.00</span></label>
							<div class="clear">

							</div>
						</li>

						<c:if test="${memberRankDiscount>0}">
						<li>
							<label class="fl">会员等级抵扣</label>
							<label class="fr color_9">-<span id="memberRankDiscount">${memberRankDiscount}</span></label>
							<div class="clear"></div>
						</li>
						</c:if>

						<li>
							<label class="fl">配送费：</label>
							<label class="fr color_9">+<span id="shippingFeeBottom">0.00</span></label>
							<div class="clear">

							</div>
						</li>
					</ul>
				</div>

				<div class="box-order-fapiao">
					<ul>
						<li>
							<input type="text" class="ly" name="memo" id="" value="" placeholder="选填：买家留言（50字以内）" />
						</li>
					</ul>
				</div>
				<div class="gd" style="height: 80px;"></div>
				<!--总计-->
				<div class="fz-shop-sum" style="bottom: 0;">
					<div class="shopSum">
						<div class="shopNumber" >
							总计：<span id="orderSum">￥0.00</span>
						</div>
						<div class="shopYunfei">
							含配送费 ￥<span id="shippingFeeDisp">0.00</span>
						</div>
					</div>
					<a id="settle" class="rightXiadan">
						去结算
					</a>
				</div>
			</div>

				<!--地址列表-->
			<div id="ad-mainBox" style="display: none;">
				<div id="addressList">
<%--
					<div class="adre">
						<p class="p1">李伟<span>13843894138</span></p>
						<p class="p2">山东省青岛市市北区敦化路利群宇恒大厦21楼</p>
						<ul>
							<li class="lt">
								<a class="a1 fl"><img class="hide" src="${respath}/mobile/images/Shopping-Cart_Check.png" /></a>
								<span class="fl">设为默认地址</span>
							</li>
							<li class="rt">
								<a href="#" class="a2">
									<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe615;</i> 删除
								</a>
							</li>
							<li class="rt l2">
								<a href="">
									<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe677;</i> 编辑
								</a>
							</li>
						</ul>
					</div>
--%>
				</div>
				<div id="footer" style="">
					<a id="addAddress" style="color: #fff;">
						<i class="icon iconfont">&#xe614;</i> 新增管理地址
					</a>
				</div>
			</div>

			</form>

			<!--新增地址-->
			<div id="na-mainBox" style="display: none;">
				<div id="main" style="background: #fff;">
					<div id="header">
					<form id="dataForm" method="post">
						<ul id="dimension">
							<li>
								<ul>
									<li class="l1">订货人</li>
									<li class="l2">
										<input type="text" name="orderer" id="orderer" value="" placeholder="订货人（选填）" onfocus="this.style.color='#333'" />
									</li>
									<div class="clear"></div>
								</ul>
							</li>
							<li>
								<ul>
									<li class="l1">手机号</li>
									<li class="l2">
										<input type="tel" name="ordererNumber" id="ordererNumber" value="" placeholder="订货人联系电话（选填）" onfocus="this.style.color='#333'" />
									</li>
									<div class="clear"></div>
								</ul>
							</li>
							<li>
								<ul>
									<li class="l1">收货人</li>
									<li class="l2">
										<input type="text" name="consignee" id="consignee" value="" placeholder="收货人姓名" onfocus="this.style.color='#333'" />
									</li>
									<div class="clear"></div>
								</ul>
							</li>
							<li>
								<ul>
									<li class="l1">联系电话</li>
									<li class="l2">
										<input type="tel" name="contactNumber" id="contactNumber" value="" placeholder="收货人联系电话" onfocus="this.style.color='#333'" />
									</li>
									<div class="clear"></div>
								</ul>
							</li>
							<li class="box_location">
								<ul>
									<li class="l1">选择地区</li>
									<li class="l2" style="float: left;width: 70%;">
										<div class="cl">
											<input type="hidden" id="areaId" name="areaId" treePath=",1357," value="1369" placeholder="请选择"/>
										</div>
									</li>
									<div class="clear"></div>
								</ul>
							</li>
							<li>
								<ul>
									<li class="l1">详细地址</li>
									<li class="l2">
										<input type="text" name="newAddress" id="newAddress" value="" placeholder="街道门牌信息" onfocus="this.style.color='#333'" />
									</li>
									<div class="clear"></div>
								</ul>
							</li>
						</ul>
						<a id="baocun" onclick="addReceiver()" style="background: #fff;display: block;" >
							<button type="button" class="btn">保存</button>
						</a>
						<a id="baocun" onclick="cancelAddReceiver()" style="background: #fff;display: block;" >
							<button type="button" class="btn">取消</button>
						</a>

					</form>
					</div>
				</div>
			</div>

			<!--修改地址-->
			<div id="edit-mainBox" style="display: none;">
				<div id="main" style="background: #fff;">
					<div id="header">
						<form action="${base}/mobile/receiver/addsubmit" id="thisForm" method="post">
							<ul id="dimension">
								<li>
									<ul>
										<li class="l1">订货人</li>
										<li class="l2">
											<input type="text" name="orderer" id="editOrderer" value="" placeholder="订货人（选填）" onfocus="this.style.color='#333'" />
										</li>
										<div class="clear"></div>
									</ul>
								</li>

								<li>
									<ul>
										<li class="l1">联系电话</li>
										<li class="l2">
											<input type="number" name="ordererNumber" id="editOrdererNumber" value="" placeholder="订货人联系电话（选填）" onfocus="this.style.color='#333'" />
										</li>
										<div class="clear"></div>
									</ul>
								</li>


								<li>
									<ul>
										<li class="l1">收货人</li>
										<li class="l2">
											<input type="hidden" name="editReceiverId" id="editReceiverId">
											<input type="text" name="consignee" id="editConsignee" value="" placeholder="收货人姓名" onfocus="this.style.color='#333'" />
										</li>
										<div class="clear"></div>
									</ul>
								</li>
								<li>
									<ul>
										<li class="l1">联系电话</li>
										<li class="l2"><input type="tel" name="contactNumber" id="editContactNumber" value="" placeholder="收货人联系电话" onfocus="this.style.color='#333'" /></li>
										<div class="clear"></div>
									</ul>
								</li>
								<li>
									<ul>
										<li class="l1">选择地区</li>
										<li class="" style="float: left;width: 70%;">
											<div class="cl" >
												<input type="hidden" id="editAreaId" name="areaId" treePath="" value="" placeholder="请选择"/>
											</div>
										</li>
										<div class="clear"></div>
									</ul>
								</li>
								<li>
									<ul>
										<li class="l1">详细地址</li>
										<li class="l2">
											<input type="text" name="newAddress" id="editNewAddress" value="" placeholder="街道门牌信息" onfocus="this.style.color='#333'" />
										</li>
										<div class="clear"></div>
									</ul>
								</li>
								<%--<li>--%>
								<%--<ul>--%>
								<%--<li class="l1">邮政编码</li>--%>
								<%--<li class="l2"><input type="text" name="zipCode" id="editZipCode" value="" placeholder="邮政编码" onfocus="this.style.color='#333'" /></li>--%>
								<%--<div class="clear"></div>--%>
								<%--</ul>--%>
								<%--</li>--%>
							</ul>
						</form>
						<a id="baocun" onclick="updateReceiver()"  style="background: #fff;display: block;">
							<button class="btn" >更新</button>
						</a>
					</div>
				</div>
			</div>

			<c:if test="${couponList!=null&&couponList.size()>0}">
			<!-- 选择优惠券 -->
			<div id="couponDiv" style="width: 100%;max-width: 460px;margin: 0 auto;display: none;">
				<div id="conponContent" class="box-park-quan">
                    <c:forEach items="${couponList}" var="coupon">
                    <div class="quan-blocks">
                        <div id="coupon${coupon.id}" class="item-quan-block" onclick="selectCoupon('${coupon.id}',${coupon.cutMoney},'${coupon.isWithPointShare}')">
                            <div class="left">
                                <h3>
                                    <span id="">￥</span>${coupon.cutMoney}
                                </h3>
                                <div class="quan-mq">
                                    <c:if test="${coupon.useType==1}">无使用门槛</c:if>
                                    <c:if test="${coupon.useType==2}">满${coupon.byFull}元可用</c:if>
                                </div>
								<c:if test="${coupon.isWithPointShare==0}">
									<span>不可与积分同享</span>
								</c:if>
                            </div>
                            <div class="right">
                                <div class="q-img"></div>
                                <div class="q-message">
                                    <h4>${coupon.name}</h4>
                                    <div class="quan-p">
                                        ${coupon.subname}
                                    </div>
                                    <div class="quan-time">
                                        <fmt:formatDate value="${coupon.validStartDate}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                        至
                                        <fmt:formatDate value="${coupon.validEndDate}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                    </div>
                                </div>
                            </div>
                        </div>
					</div>
                    </c:forEach>
					<div id="cucBtn" class="cuc">
						<input type="button" id="cancelUseCoupon" value="不使用优惠券">
					</div>
				</div>
			</div>
			</c:if>

		</section>

		<script type="text/javascript">

            Date.prototype.format = function(fmt)
            {
                var o = {
                    "M+" : this.getMonth()+1,                 //月份
                    "d+" : this.getDate(),                    //日
                    "H+" : this.getHours(),                   //小时
                    "m+" : this.getMinutes(),                 //分
                    "s+" : this.getSeconds(),                 //秒
                    "q+" : Math.floor((this.getMonth()+3)/3), //季度
                    "S"  : this.getMilliseconds()             //毫秒
                };
                if(/(y+)/.test(fmt))
                    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
                for(var k in o)
                    if(new RegExp("("+ k +")").test(fmt))
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                return fmt;
            }

            //选择优惠券
            function selectCoupon(id,cutMoney,isWithPointShare){
				var offsetPoint = $("#offsetPoint").val()
                if(isWithPointShare=='0'&&offsetPoint>0){
                    $.toast("您已选择积分抵扣，该优惠券不可与积分同享","text");
                    return;
				}
				$("#isWithPointShare").val(isWithPointShare);

                $("body").css("background","");
                $("#couponDiv").hide();
                $("#main").show();

                //清除使用余额和积分
                clearCal();
                //设置样式及使用金额
                $("#couponDiv .q-message").removeClass("current");
                var cid = $("#couponId").val();
                if(cid==id){//取消选中
                    $("#offsetCouponAmount").val("0.00");
                    $("#coupponOffsetBottom").html("0.00");
                    $("#selectCoupon").html("不使用优惠券");
                    $("#couponId").val("");
                }else{//选中
                    $("#coupon"+id).find(".q-message").addClass("current");
                    //设置使用优惠券抵扣金额
                    cutMoney = cutMoney/1;
                    var thirdAmount = $("#thirdAmount").val()/1;
                    if(cutMoney>thirdAmount){
                        $("#offsetCouponAmount").val(thirdAmount);
                        $("#coupponOffsetBottom").html(thirdAmount.toFixed(2));
                    }else{
                        $("#offsetCouponAmount").val(cutMoney);
                        $("#coupponOffsetBottom").html(cutMoney.toFixed(2));
                    }
                    $("#couponId").val(id);
                    $("#selectCoupon").html("优惠"+(cutMoney/1).toFixed(2)+"元");
                }
                //计算剩余支付金额
                calSurplus();
            }
            $("#cucBtn").click(function () {
                $("body").css("background","");
                $("#couponDiv").hide();
                $("#main").show();
                $("#isWithPointShare").val(1);
                $("#couponDiv .q-message").removeClass("current");
				$("#offsetCouponAmount").val("0.00");
				$("#coupponOffsetBottom").html("0.00");
				$("#selectCoupon").html("不使用优惠券");
                $("#couponId").val("");
                clearCal();
                calSurplus();
                return;
            });

            var isSettle = false;
			$(function() {
			    $("#selectCoupon").click(function(){
					$("#couponDiv").show();
					$("#main").hide();
					$("body").css("background","#ececec");
                    /*$.ajax({
                        url:"${base}/mobile/orders/getCartCoupon",
                        traditional:true,
                        type:"POST",
                        dataType:"json",
                        success:function(data){
                            var dataList = "";
                            $.each(data.listData,function(i,n){
                                dataList+=
                                    '<div class="quan-blocks">'+
                                    '	<div class="item-quan-block">'+
                                    '		<div class="left">'+
                                    '			<h3><span id="">￥</span>'+n.cutMoney+'</h3>'+
                                    '			<div class="quan-mq">'+n.useType+'</div>'+
                                    '		</div>'+
                                    '		<div class="right">'+
                                    '			<div class="q-img"></div>'+
                                    '			<div class="q-message">'+
                                    '				<h4>'+n.name+'</h4>'+
                                    '				<div class="quan-p">'+n.subname+'</div>'+
                                    '				<div class="quan-time">'+n.validStartDate+"至"+n.validEndDate+'</div>'+
                                    '			</div>'+
                                    '		</div>'+
                                    '	</div>'+
                                    '</div>';
                            });
                            $("#couponContent").html(dataList);
                        }
                    });*/
				});


				// 自提选项不显示
				$("#zitiMain").css("display", "none");
				$("#btn-zttime").hide();
                $("#btn-pickUpDate").hide();
                $("#btn-pickUpTime").hide();
				$("#btn-ztplace").hide();
				$("#btn-ztname").hide();
				$("#btn-ztmobile").hide();
				//$("#address-finish").css("display", "none");
				//商品图片限制大小
				var pImg = $(".fz-img").width();
				$(".fz-img").height(pImg);

                //配送和自提显示隐藏
                $(".shangjia").click(function() {
                    $("#shippingMethod").val(1);
                    $(this).addClass("cur");
                    $(this).siblings(".ziti").removeClass("cur");
                    $("#zitiMain").hide();

                    $("#address-finish").show();
                    $("#address").show();

                    $("#btn-psdate").show();
                    $("#btn-pstime").show();
                    $("#btn-pickUpDate").hide();
                    $("#btn-pickUpTime").hide();
                    $("#btn-ztplace").hide();
                    $("#btn-ztname").hide();
                    $("#btn-ztmobile").hide();
                    clearCal();
                    calSurplus();
                })
                $(".ziti").click(function() {
                    $("#shippingMethod").val(0);
                    $(this).addClass("cur");
                    $(this).siblings(".shangjia").removeClass("cur");
                    $("#address-finish").hide();
                    $("#address").hide();
                    $("#btn-psdate").hide();
                    $("#btn-pstime").hide();
                    $("#btn-pickUpDate").show();
                    $("#btn-pickUpTime").show();
                    $("#btn-ztplace").show();
                    $("#btn-ztname").show();
                    $("#btn-ztmobile").show();
                    clearCal();
                    calSurplus();
                });

                //使用积分按钮,优先使用积分
				$("#switchPoint").click(function(){
				    var isWithPointShare = $("#isWithPointShare").val();
				    if(isWithPointShare=="0"){
                        $.toast("您已选择使用的优惠券不可与积分同享","text");
                        return false;
					}

                    calSurplus();
                    var checked = $(this).is(":checked");
                    //剩余金额
                    var thirdAmount = $("#thirdAmount").val()/1;
                    if(thirdAmount==0&&checked){
                        $.alert("还需支付金额为￥0元");
                        return false;
					}
				    //积分最大抵扣数量
                    var maxOffsetPoint = $("#maxOffsetPoint").val()/1;
                    //用户实际积分
                    var point = $("#point").val()/1;
                    var beyondFlag = false;
                    if(point>maxOffsetPoint){
						point = maxOffsetPoint;
                        beyondFlag = true;
					}
                    //积分比例
                    var pointRatio = $("#pointRatio").val()/1;
                    //配送费用
                    var shippingFee = $("#shippingFee").val()/1;
                    //隐藏显示
                    $("#offsetPointDisp").html("已抵扣￥0.00元").hide();
                    //抵扣积分清零
                    $("#offsetPoint").val(0);
                    //积分抵扣金额清0
                    $("#offsetPointAmount").val(0);
					//底部使用积分
                    $("#offsetPointBottom").html("(使用"+0+"积分)").hide();
                    //底部使用金额
                    $("#offsetPointAmountBottom").html("0.00");
				    if(checked){//选中
						var amount = parseInt(point/pointRatio);
						if(amount==0){//积分不足的情况
							if(beyondFlag){
                                $.alert("商品最大可抵扣积分为："+maxOffsetPoint+"积分，"+pointRatio+"积分可抵扣1元");
                                return false;
							}else{
                                $.alert("积分不足，"+pointRatio+"积分可抵扣1元");
                                return false;
							}
						}
						if(amount>=thirdAmount){//商品价格等于可以抵扣的商品金额
							amount = parseInt(thirdAmount);
						}
						if(amount==0){
                            $.alert("剩余"+thirdAmount+"未支付，"+pointRatio+"积分可抵扣1元，抵扣积分每满1元可抵扣");
                            return false;
						}
                        point = amount*pointRatio;
                        $("#offsetPoint").val(point);
                        $("#offsetPointBottom").html("(使用"+point+"积分)").show();
                        $("#offsetPointAmount").val(amount.toFixed(2));
                        $("#offsetPointAmountBottom").html(amount.toFixed(2));
                        $("#offsetPointDisp").html("已抵扣￥"+amount.toFixed(2)+"元").show();
					}else{
                        $("#switchWechat").prop("checked",true);
					}
                    calSurplus();
				});
                //使用余额按钮
                $("#switchBalance").click(function(){
                    calSurplus();
                    var checked = $(this).is(":checked");
                    var thirdAmount = $("#thirdAmount").val()/1;
                    if(thirdAmount==0&&checked){
                        $.alert("还需支付金额为￥0元");
                        return false;
                    }
                    //用户实际余额
                    var balance = $("#balance").val()/1;
                    //余额抵扣金额清0
                    $("#offsetBalance").val(0);
                    $("#offsetBalanceBottom").html("0.00");
                    //隐藏显示
                    $("#offsetBalanceDisp").html("已抵扣￥0.00元").hide();
                    if(checked){//选中
                        if(balance==0){//余额不足的情况
                            return false;
                        }
						var amount = 0;
						if(balance>=thirdAmount){
                            amount = thirdAmount;
                        }
                        if(balance<thirdAmount){
                            amount = balance;
						}
                        $("#offsetBalance").val(amount.toFixed(2));
						$("#offsetBalanceBottom").html(amount.toFixed(2));
                        $("#offsetBalanceDisp").html("已抵扣￥"+amount.toFixed(2)+"元").show();
                    }
                    calSurplus();
                });
                $("#switchWechat").click(function(){
					return false;
				});

                //清除选择的优惠券、积分、余额
                function clearCal(){
                    //积分
                    $("#offsetPointDisp").html("已抵扣￥0.00元").hide();
                    $("#offsetPoint").val(0);
                    $("#offsetPointAmount").val(0);
                    $("#offsetPointBottom").html("(使用"+0+"积分)").hide();
                    $("#offsetPointAmountBottom").html("0.00");
                    $("#switchPoint").prop("checked",false);

                    //余额
                    $("#offsetBalance").val(0);
                    $("#offsetBalanceBottom").html("0.00");
                    $("#offsetBalanceDisp").html("已抵扣￥0.00元").hide();
                    $("#switchBalance").prop("checked",false);

                    $("#switchWechat").prop("checked",true);
                }
                window.clearCal = clearCal;

				//计算还需要支付的金额
                function calSurplus(){
                    var shippingMethod = $("#shippingMethod").val();
                    if(shippingMethod=="0"){//自提
						$("#shippingFee").val(0);
                        $("#shippingFeeDisp").html("0.00");
                        $("#shippingFeeBottom").html("0.00");
					}else if(shippingMethod=="1"){//发货
						var shippingFee = $("#shippingFee").val()/1;
                        $("#shippingFee").val(shippingFee);
                        $("#shippingFeeDisp").html(shippingFee.toFixed(2));
                        $("#shippingFeeBottom").html(shippingFee.toFixed(2));
					}
                    //商品价格
                    var productPrice = $("#productPrice").val()/1;
                    //配送费用
                    var shippingFee = $("#shippingFee").val()/1;
                    //积分抵扣金额
                    var offsetPointAmount = $("#offsetPointAmount").val()/1;
                    //余额抵扣金额
                    var offsetBalance = $("#offsetBalance").val()/1;
                    //优惠券抵扣金额
                    var offsetCouponAmount = $("#offsetCouponAmount").val()/1;
                    //剩余需要金额
					var surplus = Math.abs(productPrice + shippingFee - offsetPointAmount - offsetBalance-offsetCouponAmount).toFixed(2);
					$("#thirdAmount").val(surplus);
					$("#orderSum").html("￥"+surplus);
					if(surplus==0){
                        $("#switchWechat").prop("checked",false);
					}else{
                        $("#switchWechat").prop("checked",true);
					}
				}
				window.calSurplus = calSurplus;
				//计算剩余需要支付的金额
                calSurplus();

/*
                //配送时间
                $.ajax({
                    url:"getPsDate",
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        var displayDate = data.displayDate;
                        var date = data.date;
                        var options = "";
                        for(var i=0;i<displayDate.length;i++){
                            options+="<option av='"+date[i]+"' value='"+displayDate[i]+"'>"+displayDate[i]+"</option>"
						}
						$("#psdate").append(options);

                        $("#psdate").change(function(){
							var av = $(this).children("option").filter("option:selected").attr("av");
                            $.ajax({
                                url:"getPsTimeByPsDate",
                                type:"post",
                                dataType:"json",
                                data:{
                                    date:av
                                },
                                success:function(data){
                                    var opts = '<option value="">请选择</option>';
                                    var time = data.time;
                                    for(var i=0;i<time.length;i++){
                                        opts+="<option value='"+time[i]+"'>"+time[i]+"</option>"
									}
									$("#pstime").html(opts);
                                }
                            });

						});

                    }
                });
*/




				//配送日期时间
                $.ajax({
                    url:"getPsDate",
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        $("#psdate").picker({
                            title: "请选择配送日期",
                            toolbarTemplate: '<div class="toolbar"><div class="toolbar-inner"><a href="javascript:;" class="picker-button close-picker">完成</a><h2 class="pstime">我们将在配送阶段的200分钟内确保送达</h2><h1 class="title">请选择配送日期</h1></div></div>',
                            cols: [{
                                textAlign: 'center',
                                values: data.displayDate,
                                displayValues:data.displayDate,
                                selfValues:data.date
                                //如果你希望显示文案和实际值不同，可以在这里加一个displayValues: [.....]
                            }],
                            onClose:function(e){
                                console.log(e.value[0]);
                                var date = e.cols[0].selfValues[e.cols[0].activeIndex];
                                $("#psdates").val(date);
                                console.log(date);
								if(date=="闪电配送"){

                                    $.modal({
                                        title: "提示",
                                        text: "选择闪电配送请联系拨打客服电话：${setting.csTel}确认配送费用",
                                        buttons: [
                                            { text: "取消拨打", className: "default", onClick: function(){ } },
                                            { text: "拨打电话", onClick: function(){
                                                	window.location = "tel:${setting.csTel}";
												}
                                            }
                                        ]
                                    });
                                    $("#btn-pstime").hide();

								}else{
                                    $("#btn-pstime").show();
                                    $.ajax({
                                        url:"getPsTimeByPsDate",
                                        type:"post",
                                        dataType:"json",
                                        data:{
                                            date:date
                                        },
                                        success:function(data){
                                            var time = data.time;

                                            <jsp:useBean id="now" class="java.util.Date" scope="page"/>
                                            var cd = '<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />';
                                            var cd2 = new Date().format("yyyy-MM-dd HH:mm:ss");
											if(cd2>cd){
                                                cd = cd2;
                                            }
                                            if(date=='2019-05-12'&&cd>'2019-05-11 23:00:00'){
                                                time = ["14:00-17:00", "16:30-19:30"];
											}
											if(date=='2019-05-12'&&cd>'2019-05-12 12:30:00'){
                                                time = ["16:30-19:30"];
                                            }

                                            $("#pstimeSpan").html("<input type='text' id='pstime' name='pstime' placeholder='请选择'/>");
                                            //配送时间
                                            $("#pstime").picker({
                                                //						title: "请选择配送时间",
                                                toolbarTemplate: '<div class="toolbar"><div class="toolbar-inner"><a href="javascript:;" class="picker-button close-picker">完成</a><h2 class="pstime">我们将在配送阶段的200分钟内确保送达</h2><h1 class="title">请选择配送时间</h1></div></div>',
                                                cols: [{
                                                    textAlign: 'center',
                                                    values: time
                                                }]
                                            });

                                        }
                                    });
								}

                            }
                        });
                    }
				});

				//自提点
				var pua = eval(${pickUpAddress});
				$("#ztPlace").picker({
					title: "请选择自提点",
					cols: [{
						textAlign: 'center',
						values: pua
					}]
				});

                //自提日期时间
                $.ajax({
                    url:"getPickUpDate",
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        $("#pickUpDate").picker({
                            title: "请选择自提日期",
                            cols: [{
                                textAlign: 'center',
                                values: data.displayDate,
                                displayValues:data.displayDate,
								selfValues:data.date
                            }],
                            onClose:function(e){
                                console.log(e.value[0]);
                                var date = e.cols[0].selfValues[e.cols[0].activeIndex];
                                $("#pickUpDates").val(date);
                                console.log(date);
                                $.ajax({
                                    url:"getPickUpTimeByPickUpDate",
                                    type:"post",
                                    dataType:"json",
                                    data:{
                                        date:e.cols[0].selfValues[e.cols[0].activeIndex]
                                    },
                                    success:function(data){
										var time = data.time;
                                        $("#pickUpTimeSpan").html("<input type='text' id='pickUpTime' name='pickUpTime' placeholder='请选择'/>");

                                        var cd = '<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" />';
                                        var cd2 = new Date().format("yyyy-MM-dd HH:mm:ss");
                                        if(cd2>cd){
                                            cd = cd2;
                                        }
                                        if(date=='2019-05-12'&&cd>'2019-05-11 23:00:00'){
                                            time = ["14:00-17:00", "16:30-19:30"];
                                        }
                                        if(date=='2019-05-12'&&cd>'2019-05-12 12:30:00'){
                                            time = ["16:30-19:30"];
                                        }

                                        //配送时间
                                        $("#pickUpTime").picker({
                                            //						title: "请选择配送时间",
                                            cols: [{
                                                textAlign: 'center',
                                                values: time
                                            }]
                                        });
                                    }
                                });
                            }
                        });
                    }
                });

				//去结算
				$("#settle").click(function(){
				    if(isSettle){
				        return;
					}
                    isSettle = true;
				    var shippingMethod = $("#shippingMethod").val();
				    if(shippingMethod==0){//自提
						var pickUpDate = $("#pickUpDate").val();
						if(!pickUpDate){
						    $.toast("请选择自提日期","text");
                            isSettle = false;
						    return;
						}
                        var pickUpTime = $("#pickUpTime").val();
                        if(!pickUpTime){
                            $.toast("请选择自提时间","text");
                            isSettle = false;
                            return;
                        }
                        var pickUpAddress = $("#ztPlace").val();
                        if(!pickUpAddress){
                            $.toast("请选择自提点","text");
                            isSettle = false;
                            return;
                        }

                        var name = $("#name").val();
                        if(!name){
                            $.toast("请填写自提人姓名","text");
                            isSettle = false;
                            return;
                        }
                        var mobile = $("#mobile").val();
                        if(!mobile){
                            $.toast("请填写自提人联系电话","text");
                            isSettle = false;
                            return;
                        }
                        if(!isTel(mobile)){
                            $.toast("请填写正确的联系电话","text");
                            isSettle = false;
                            return;
						}

                    }else if(shippingMethod==1){//发货

						var psdate = $("#psdate").val();
						var pstime = $("#pstime").val();
						var receiverId = $("#receiverId").val();
                        if(!receiverId){
                            $.toast("请填写收货地址","text");
                            isSettle = false;
                            return;
                        }

                        if(!psdate){
                            $.toast("请选择配送日期","text");
                            isSettle = false;
                            return;
                        }
                        if(!pstime){
                            $.toast("请选择配送时间","text");
                            isSettle = false;
                            return;
                        }

					}
                    $.confirm({
                        title: '提示',
                        text: '确认提交订单？',
                        onOK: function () {
							$.ajax({
								url:"create",
								type:"post",
								dataType:"json",
								data:$("#orderForm").serialize(),
								success:function(data){
								    if(data.type=="success"){
                                        $.toast(data.content,function(){
                                            if(data.data.thirdPay==0){
                                                window.location.replace("${base}/mobile/orders/list");
                                            }else if(data.data.thirdPay==1){
                                                wechatPay(data.data.orderId);
                                            }
                                        });
									}else{
								        $.toast(data.content,"forbidden");
                                        isSettle = false;
									}
								},error:function(){
								    isSettle = false;
								    $.toast("系统错误","text");
								}
							});
                        },
                        onCancel: function () {
                            isSettle = false;
                        }
                    });

				    var isSubmit = false;
                    function wechatPay(orderId) {
                        if(isSubmit){
                            return;
						}
                        isSubmit = true;
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
                                            $.toast("订单未支付","text",function(){
                                                window.location.replace("${base}/mobile/orders/list");
                                            });
                                        }
                                    });
                                }else{
                                    $.toast(data.content,"text",function(){
                                        window.location.replace("${base}/mobile/orders/list");
                                    });
                                }
                            },
                            error : function() {
                                isSubmit = false;
                                alert("系统异常，请重新操作");
                            },
                        });
                    }
				});

			})

		</script>


		<script>

            $("#addAddress").click(function(){
                $("#na-mainBox").css("display","block");
                $(".btn-order-agree").css("display","none");
                $("#ad-mainBox").css("display","none");
                $("#edit-mainBox").css("display","none");
                clean();
            });
           	function cancelAddReceiver(){
                $("#na-mainBox").css("display","none");
                $(".btn-order-agree").css("display","none");
                $("#ad-mainBox").css("display","block");
                $("#edit-mainBox").css("display","none");

                clean();

			};
			function isDefault(id) {
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/isDefault",
					dataType:"json",
					async:false,
					data:{
						id:id
					},
					success:function(data){
						if(data.type=="success"){
						    $.toast("设置成功","text");
						}
					}
				});
				receiver();
			}
			function clean() {
				area();
				$("#consignee").val("");
				$("#contactNumber").val("");
				$("#newAddress").val("");
				$("#zipCode").val("");
			}
			function area() {
				//初始化地区选择控件
				jQuery("#areaId").lSelect({
					url: "${base}/mobile/area/list",
					className:"item_select city_select fl new_select"
				});
			}
			function editArea() {
				//初始化地区选择控件
				jQuery("#editAreaId").lSelect({
					url: "${base}/mobile/area/list",
					className:"item_select city_select fl new_select"
				});
			}
			//显示地址
			function addressLists() {
				receiver();
				$("#na-mainBox").css("display","none");
				$(".btn-order-agree").css("display","none");
				$("#ad-mainBox").css("display","block");
				$("#edit-mainBox").css("display","none");
			}
			//查询地址
			function receiver() {
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/receiverList",
					dataType:"json",
					async:false,
					data:{},
					success:function(data){
						var dataList = "";
						for(var i =0;i<data.length;i++){
							var sr = "";
							if (data[i].isdefault == 1) {
								sr = '<a class="a1" style="float: left" onclick="isDefault('+data[i].id+')"><img class="show" src="${respath}/mobile/images/Shopping-Cart_Check.png" /></a><span style="float: left">设为默认地址</span></li>'
							} else {
								sr = '<a class="a1" style="float: left" onclick="isDefault('+data[i].id+')"><img class="hide" src="${respath}/mobile/images/Shopping-Cart_unCheck.png" /></a><span style="float: left">设为默认地址</span></li>'
							}
							dataList +=
								'<div class="adre">'+
								'	<a onclick="choose('+data[i].id+')">'+
								'		<p class="p1">'+data[i].receiver+'<span>'+data[i].mobile+'</span><label for="" class="fr" style="margin-right: 5px;color: #FF0000;">点击选择</label></p>'+
								'		<p class="p2">'+data[i].areaname+''+data[i].address+'</p>'+
								'		<ul>'+
								'			<li class="lt">'+sr+
								'			<li class="rt">'+
								'				<a onclick="deleteReceiver('+data[i].id+')" class="a2">'+
								'					<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe615;</i> 删除'+
								'				</a>'+
								'			</li>'+
								'			<li class="rt l2">'+
								'				<a onclick="edit('+data[i].id+')">'+
								'					<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe677;</i> 编辑'+
								'				</a>'+
								'			</li>'+
								'		</ul>'+
								'	</a>'+
								'</div>'
						}
						$("#addressList").html(dataList);
                        $(window).scrollTop(0);
					}
				});
			}

			//选择地址
            function choose(id) {
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/receiver/chooseReceiver",
                    dataType:"json",
                    async:false,
                    data:{
                        id:id
                    },
                    success:function(data){
                        var dataList =
                            '<a class="btn-go-addressLists" onclick="addressLists()">'+
                            '	<div class="addressContent">'+
                            '		<i class="i1 lt"><i class="icon iconfont">&#xe664;</i></i>'+
                            '		<div class="box-userAddress lt">'+
                            '			<div class="u-name">'+
                            '				'+data.consignee+'    '+data.contactNumber+
                            '			</div>'+
                            '			<div class="u-address">'+
                            '				'+data.areaName+''+data.address+
                            '			</div>'+
                            '		</div>'+
                            '		<i class="i2 rt" style="display: inline-block;line-height:80px;margin-top:35px"><img src="${respath}/mobile/images/Store-entry_arrrow.png" alt="" /></i>'+
                            '	</div>'+
                            '	<img src="${respath}/mobile/images/address_line_02.png" width="100%" />'+
                            '</a>'
						$("#address").remove();
                        $("#address-finish").html(dataList).show();
                        $("#receiverId").val(data.id);
                    }
                });
                $("#na-mainBox").css("display","none");
                $(".btn-order-agree").css("display","block");
                $("#ad-mainBox").css("display","none");
                $("#edit-mainBox").css("display","none");
            }

			//添加地址
			var addFlag = false;
			function addReceiver() {

                //联系电话
                var ordererNumber = $("#ordererNumber").val()?$("#ordererNumber").val():"";
                if(ordererNumber&&!isTel(ordererNumber)&&!isPhone(ordererNumber)){
                    $.toast("订货人联系电话不正确！","text");
                    return;
                }

				//姓名不能为空
				var consignee = $("#consignee").val();
				if(consignee==null||consignee==""){
                    $.toast("收货人姓名不能为空！","text");
					return;
				}
				//联系电话
				var contactNumber = $("#contactNumber").val();
				if(contactNumber==null||contactNumber==""){
                    $.toast("收货人联系电话不能为空！","text");
					return;
				}
				if(!isTel(contactNumber)&&!isPhone(contactNumber)){
                    $.toast("收货人联系电话不正确！","text");
                    return;
				}
				//详细地址
				var newAddress = $("#newAddress").val();
				if(newAddress==null||newAddress==""){
                    $.toast("收货详细地址不能为空！","text");
					return;
				}
				if(addFlag){
				    return;
				}
                addFlag = true;
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/addReceiver",
					dataType:"json",
					async:false,
					data:$("#dataForm").serialize(),
					success:function(data){
						if(data.type=="success"){
                            $.toast(data.content,"text",function(){
								addressLists();
							})
						} else {
                            $.toast(data.content,"text", function(){

							})
                            addFlag = false;
						}
					},error:function(){
                        addFlag = false;
					}
				});

			}

			//更新地址
			function updateReceiver() {
				//姓名不能为空
				var consignee = $("#editConsignee").val();
				if(consignee==null||consignee==""){
					$.toast("收货人姓名不能为空！", "text");
					return;
				}
				//联系电话
				var contactNumber = $("#editContactNumber").val();
				if(contactNumber==null||contactNumber==""){
                    $.toast("收货人联系电话不能为空！", "text");
					return;
				}
				//详细地址
				var newAddress = $("#editNewAddress").val();
				if(newAddress==null||newAddress==""){
                    $.toast("收货详细地址不能为空！", "text");
					return;
				}
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/updateReceiver",
					dataType:"json",
					async:false,
					data:$("#thisForm").serialize(),
					success:function(data){
						if(data.type=="success"){
							$.toast(data.content,"text",function(){
                                addressLists();
							});
						} else {
                            $.toast(data.content,"text");
						}
					}
				});
			}

			//删除地址
			function deleteReceiver(id) {
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/deleteReceiver",
					dataType:"json",
					async:false,
					data:{
						id:id
					},
					success:function(data){

					}
				});
				receiver();
			}

			//编辑地址
			function edit(id) {
				$.ajax({
					type:"post",
					url:"${base}/mobile/receiver/editReceiver",
					dataType:"json",
					async:false,
					data:{
						id:id
					},
					success:function(data){
						$("#editReceiverId").val(id);
						$("#editConsignee").val(data.receiver.consignee);
						$("#editContactNumber").val(data.receiver.contactNumber);
						$("#editOrderer").val(data.receiver.orderer);
						$("#editOrdererNumber").val(data.receiver.ordererNumber);
						$("#editNewAddress").val(data.receiver.address);
						$("#editZipCode").val(data.receiver.zipCode);
						$("#editAreaId").attr("treePath",data.treePath);
                        $("#editAreaId").val(data.receiver.areaId);
                        editArea();
					}
				});
				$("#na-mainBox").css("display","none");
				$(".btn-order-agree").css("display","none");
				$("#ad-mainBox").css("display","none");
				$("#edit-mainBox").css("display","block");
			}
            $("#newAddress,#editNewAddress").blur(function(){
                $(window).scrollTop(0)
            });
		</script>


	</body>

</html>
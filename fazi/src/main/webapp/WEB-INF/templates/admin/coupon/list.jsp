<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
	<title>优惠券</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	<meta name="keywords" content="">
	<meta name="description" content="">
	<style type="text/css">
		.order-list table tr td {
			border-top: none;
			border: 1px solid #e7eaec;
			padding: 5px 10px;
			vertical-align: middle;
		}
	</style>
</head>

<body class="gray-bg">

	<div class="wrapper wrapper-content  animated fadeInRight">

		<form role="form" class="form-inline" method="get" action="${base}/admin/coupon/list">
			<input type="hidden" name="couponTypeId" value="${couponTypeId}">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">

                        <span class="text-muted small pull-right  m-l m-r">
                            <a title="返回" onclick="window.history.go(-1);"  style="font-size: 14px">
                                <i class="fa fa-arrow-left"></i>
                            </a>
                        </span>
						<span class="text-muted small pull-right">共有数据：${page.total} 条</span>

						<div class="form-inline m-b-md">

							<div class="form-group">
								<select id="scene" class="form-control" name="scene">
									<option value="">全部来源</option>
									<option <c:if test="${scene==0}"> selected </c:if>value="0">自助领取</option>
									<option <c:if test="${scene==1}"> selected </c:if>value="1">推荐好友</option>
									<option <c:if test="${scene==2}"> selected </c:if>value="2">后台赠送</option>
								</select>
							</div>

							<div class="form-group">
								<input type="text" id="nickname" class="form-control" name="nickname" value="${nickname}" placeholder="请输入会员昵称">
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							</div>
						</div>

						<div class="clients-list">
							<div class="order-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>序号</th>
											<th>会员昵称</th>
											<th>头像</th>
											<th>姓名</th>
											<th>手机号</th>
											<th>领取时间</th>
											<th>使用时间</th>
											<th>领取来源</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>
												${status.index+1}
											</td>
											<td>
												${data.nickname}
											</td>
											<td>
												<img width="40" src="${data.avatarUrl}">
											</td>
											<td>
												${data.memberName}
											</td>
											<td>
												${data.mobile}
											</td>
											<td>
												<fmt:formatDate value="${data.createDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
											</td>
											<td>
												<c:if test="${data.status==0}">-</c:if>
												<c:if test="${data.status==1}"><fmt:formatDate value="${data.useDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></c:if>
											</td>
											<th>
												<c:if test="${data.scene==0}">自助领取</c:if>
												<c:if test="${data.scene==1}">推荐会员领取</c:if>
												<c:if test="${data.scene==2}">后台赠送(${data.sysAdminName}&nbsp;-&nbsp;<fmt:formatDate value="${data.couponTaskDate}" pattern="yyyy-MM-dd HH:mm"/>)</c:if>
												<c:if test="${data.scene==3}">签到</c:if>
											</th>
											<th>
												<c:if test="${data.status==0}">未使用</c:if>
												<c:if test="${data.status==1}">已使用</c:if>
											</th>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
							<%@ include file="/WEB-INF/templates/admin/include/page.jsp" %>
						</div>

					</div>
				</div>
			</div>
		</form>
	</div>

	<script>

		$(function() {

			$(".full-height-scroll").slimScroll({
				height: "100%"
			});

			laydate.render({
				elem: '#startDate' //指定元素
					,
				type: 'datetime'
			});

			laydate.render({
				elem: '#endDate' //指定元素
					,
				type: 'datetime'
			});

			$("thead input:checkbox").each(function() {
				if ($(this).attr("checked") === true) {
					$(this).parents('table').children("tbody input[type='checkbox']");
				}
			});

			//订单状态
			$("#ordersStatus").val("${ordersStatus}");

			//支付状态
			$("#payStatus").val("${payStatus}");

			//发送状态
			$("#sendStatus").val("${sendStatus}");

            //配送方式
            $("#shippingMethod").val("${shippingMethod}");

		});

		//详情
		function detail(orderId) {
		    var orderStatus = $("#orderStatus").val();
			var payStatus = $("#payStatus").val();
			var sendStatus = $("#sendStatus").val();
			var shippingMethod = $("#shippingMethod").val();
            window.location = "${base}/admin/orders/detail?orderId=" + orderId + "&orderStatus=" + orderStatus + "&payStatus=" + payStatus + "&sendStatus=" + sendStatus + "&shippingMethod=" +shippingMethod;
        }

		//查询
		function f_query(){
			document.forms[0].method = "post";
			document.forms[0].target = "_self";
			document.forms[0].action = "${base}/admin/orders/list";
			document.forms[0].submit();
		}

		//导出
		function f_exportXls(){
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var memberNickName = $('#memberNickName').val();
			var orderNumb = $('#orderNumb').val();
			var orderStatus = $('#orderStatus').val();
			var payStatus = $('#payStatus').val();
			var sendStatus = $('#sendStatus').val();
			if ((startDate===null || startDate==='' || startDate==='undefined') && (endDate===null || endDate==='' || endDate==='undefined') &&
					(memberNickName===null || memberNickName==='' || memberNickName==='undefined') && (orderNumb===null || orderNumb==='' || orderNumb==='undefined') &&
					(orderStatus===null || orderStatus==='' || orderStatus==='undefined') && (payStatus===null || payStatus==='' || payStatus==='undefined') &&
					(sendStatus===null || sendStatus==='' || sendStatus==='undefined')) {
				layer.msg("请选择筛选条件后导出", {icon: 0});
				return false;
			}
			document.forms[0].method = "post";
			document.forms[0].target = "_blank";
			document.forms[0].action = "${base}/admin/orders/export";
			document.forms[0].submit();
		}

	</script>

</body>

</html>
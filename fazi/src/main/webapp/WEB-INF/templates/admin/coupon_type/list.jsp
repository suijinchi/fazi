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

		<form role="form" class="form-inline" method="get" action="${base}/admin/coupon_type/list">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">

						<span class="text-muted small pull-right">共有数据：${page.total} 条</span>

						<div class="form-inline m-b-md">

							<div class="form-group">
								<select id="type" class="form-control" name="type">
									<option value="">全部类型</option>
									<option value="1">代金券</option>
								</select>
							</div>

							<div class="form-group">
								<input type="text" id="name" class="form-control" name="name" value="${name}" placeholder="请输入优惠券标题">
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							</div>

						</div>

						<div class="form-inline m-t-md">

							<div class="form-group">
								<button type="button" class="btn btn-danger" onclick="data_del()"><i class="icon iconfont">&#xe606;</i>删除</button>
							</div>
							<div class="form-group">
								<a href="${base}/admin/coupon_type/add">
									<button type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加优惠券</button>
								</a>
							</div>
						</div>

						<div class="clients-list">
							<div class="order-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th><input type="checkbox" value="" name="" id="box"></th>
											<th>序号</th>
											<th>id</th>
											<th>优惠券标题</th>
											<th>优惠券类型</th>
											<th>有效时间</th>
											<th>状态</th>
											<th>库存</th>
											<th>已领取</th>
											<th>是否与积分同享</th>
											<th>创建日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>
												<input type="checkbox" value="${data.id}" name="ids" class="boxs">
											</td>
											<td>
												${status.index+1}
											</td>
											<td>
												${data.id}
											</td>
											<td>
												${data.name}
											</td>
											<td>
												<c:if test="${data.type==1}">代金券</c:if>
												<c:if test="${data.type==2}">折扣券</c:if>
												<c:if test="${data.type==3}">礼品券</c:if>
											</td>
											<td>
												<c:if test="${data.validDateType==1}">
                                                    <fmt:formatDate value="${data.validStartDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                                    至
                                                    <fmt:formatDate value="${data.validEndDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                                </c:if>
												<c:if test="${data.validDateType==2}">领取后
                                                    <c:if test="${data.validGetDay==0}">当天</c:if>
                                                    <c:if test="${data.validGetDay>0}">${data.validGetDay}天</c:if>
                                                    生效
                                                    ${data.validDays}
                                                    天有效
                                                </c:if>
											</td>
											<td>
                                                <c:if test="${data.validDateType==1}">
                                                    <c:choose>
                                                        <c:when test="${data.validEndDate < now }">已过期</c:when>
                                                        <c:when test="${data.getNum==0}">待投放</c:when>
                                                        <c:when test="${data.getNum==1}">已投放</c:when>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${data.validDateType==2}">
                                                    <c:if test="${data.getNum==0}">待投放</c:if>
                                                    <c:if test="${data.getNum>=1}">已投放</c:if>
                                                </c:if>
											</td>
											<td>
												${data.stock}
												&nbsp;
												<%--<a title="修改库存" data-toggle="modal" data-target="#ajaxModal" ><i class="icon iconfont">&#xe683;</i></a>--%>
											</td>
											<td>
												${data.getNum}
											</td>
											<td>
												<c:if test="${data.isWithPointShare==0}">否</c:if>
												<c:if test="${data.isWithPointShare==1}">是</c:if>
											</td>
											<td>
												${data.createDate}
											</td>
											<td>
												<a title="编辑" class="btn btn-primary" href="edit?id=${data.id}"><i class="icon iconfont">&#xe683;</i></a>
                                                <a title="查看详情" class="btn btn-primary" href="detail?id=${data.id}"><i class="fa fa-file-text-o"></i></a>
                                                <a title="领取详情" class="btn btn-primary" href="${base}/admin/coupon/list?couponTypeId=${data.id}"><i class="fa fa-bar-chart"></i></a>
											</td>
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

<%--

		<div class="modal inmodal fade" id="ajaxModal"  tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
			<div class="modal-body "></div>
			<form class="form-horizontal form-validate form-modal" method="post"
				  action="/vshop/Ajax/CouponAjax/UpStock/4965" novalidate="novalidate">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">×</button>
							<h4 class="modal-title">库存</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<div class="col-sm-7">
									<label class="radio-inline"><input type="radio" value="1" name="stock_num" checked="">增加库存</label>
									<label class="radio-inline"><input type="radio" value="-1" name="stock_num">减少库存</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"></label>
								<div class="col-sm-5">
									<div class="input-group">
										<input type="text" class="form-control" data-rule-positiveinteger="true" data-rule-required="true" value="" name="store" aria-required="true">
										<span class="input-group-addon">份</span>
									</div>
									<span class="help-inlne m-t-xs pull-left">库存不能少于1</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" data-loading-text="保存中...">保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</form>
		</div>

--%>





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

        //批量删除
        function data_del() {
            var $ids = $("input[name='ids']:checked");
            if($ids.length===0){
                layer.msg("请选择商品");
                return;
            }
            layer.confirm('确认要删除吗？用户已领取的券不影响使用', function(index) {
                $.ajax({
                    type: 'POST',
                    url: '${base}/admin/coupon_type/delete',
                    dataType: 'json',
                    data:$ids.serialize(),
                    success: function(data) {
                        if(data.type==="success"){
                            layer.msg(data.content, {
                                icon: 1,
                                time: 2000
                            }, function(){
                                location.reload();
                            });
                        }else{
                            layer.msg(data.content, {icon: 0});
                        }
                    }
                });
            });
        }

        $("#box").change(function() {
            if ($("#box").is(":checked")) {
                $(".boxs").prop("checked", true);
            } else {
                $(".boxs").prop("checked", false);
            }
        });

	</script>

</body>

</html>
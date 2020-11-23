<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	<title>订单管理-列表</title>
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
		<form role="form" class="form-inline" method="post" action="${base}/admin/orders/list">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">

						<span class="text-muted small pull-right">共有数据：${page.total} 条</span>

						<div class="form-inline m-b-md">
							<div>
								<div class="form-group">
									<input type="text" id="startDate" name="orderBegDt" value="${orderBegDt}" class="form-control" placeholder="请输入开始日期">
								</div>
								<div class="form-group">
									<input type="text" id="endDate" name="orderEndDt" value="${orderEndDt}" class="form-control" placeholder="请输入结束日期">
								</div>
								<div class="form-group">
									<input type="text" id="memberNickName" class="form-control" name="memberNickName" value="${memberNickName}" placeholder="请输入会员昵称">
								</div>
								<div class="form-group">
									<input type="text" id="orderNumb" class="form-control" name="orderNumb" value="${orderNumb}" placeholder="请输入订单编号">
								</div>

							</div>

							<div style="margin: 20px 0 ;">
								<div class="form-group">
									<label style="color: #999;font-weight: normal;">是否显示已退款订单：</label>
									<select id="notShowRefund" class="form-control" name="notShowRefund">
										<option <c:if test="${notShowRefund==1}">selected</c:if> value="1">不显示</option>
										<option <c:if test="${notShowRefund==0}">selected</c:if> value="0">显示</option>
									</select>
								</div>

								<div class="form-group">
									<input type="text" id="ordererMobile" name="ordererMobile" value="${ordererMobile}" class="form-control" placeholder="请输入订货人电话">
								</div>
								<div class="form-group">
									<input type="text" id="mobile" class="form-control" name="mobile" value="${mobile}" placeholder="请输入收货人电话">
								</div>
								<div class="form-group">
									<input type="text" id="productName" class="form-control" name="productName" value="${productName}" placeholder="请输入商品名称">
								</div>
							</div>

							<div style="margin: 20px 0 ;">

								<div class="form-group">
									<select id="ordersStatus" class="form-control" name="ordersStatus">
										<option value="">订单状态选择</option>
										<option value="0">未确认</option>
										<option value="1">已确认</option>
										<option value="2">已完成</option>
										<option value="3">已取消</option>
										<option value="4">申请退款（维权）</option>
										<option value="5">退款中（微信延迟到账）</option>
										<option value="6">已退款</option>
									</select>
								</div>

								<div class="form-group">
									<select id="payStatus" class="form-control" name="payStatus">
										<option value="">支付状态选择</option>
										<option value="0">未支付</option>
										<option value="1">已支付</option>
									</select>
								</div>

								<div class="form-group">
									<select id="shippingMethod" class="form-control" name="shippingMethod">
										<option value="">配送方式选择</option>
										<option value="0">到店自提</option>
										<option value="1">商家配送</option>
									</select>
								</div>

								<div class="form-group">
									<select id="sendStatus" class="form-control" name="sendStatus">
										<option value="">物流状态选择</option>
										<option value="0">未发货</option>
										<option value="1">已发货</option>
										<option value="2">已确认收货</option>
									</select>
								</div>

								<div class="form-group">
									<select id="serviceConfirmStatus" class="form-control" name="serviceConfirmStatus">
										<option value="">客服确认状态</option>
										<option value="0">未确认</option>
										<option value="1">已确认</option>
									</select>
								</div>

								<div class="form-group">
									<button type="submit" class="btn btn btn-primary" onclick="f_query();"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
								</div>
								<div class="form-group">
									<button class="btn btn-success " type="button" onclick="f_exportXls();">
										<i class="icon iconfont">&#xe616;</i>&nbsp;&nbsp;<span class="bold">导出excel</span>
									</button>
								</div>
							</div>
						</div>

						<div class="clients-list">
							<div class="order-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>
												<div class="check-box">
													<input type="checkbox" id="checkbox">
													<label>&nbsp;</label>
												</div>
											</th>
											<%--<th>序号</th>--%>
											<th>订单编号</th>
											<th>下单时间</th>
											<th>客户</th>
											<th>实收款</th>
											<%--<th>配送方式</th>--%>
											<th>产品名称</th>
                                            <th>配送/自提时间</th>
                                            <th>客服是否确认</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${page.content}" var="data" varStatus="status" >
										<tr>
											<td>
												<input type="checkbox" value="${data.id}" name="ids">
											</td>
											<%--<td>
												${status.index+1}
											</td>--%>
											<td>
												${data.sn}
											</td>
											<td>
												${data.createDate}
											</td>

											<c:if test="${data.memberName!=null&&data.memberName!=''}">
												<td title="${data.name}">
													${data.name}
												</td>
											</c:if>
											<c:if test="${data.memberName==null||data.memberName==''}">
												<td title="${data.nickname}">
													${data.shortNickName}
												</td>
											</c:if>

											<td>
												￥${data.amount}&nbsp;<br>[含运费:${data.shippingFee}]
											</td>
											<%--<td>--%>
												<%--<c:if test="${data.shippingMethod==0}">【到店自提】</c:if>--%>
												<%--<c:if test="${data.shippingMethod==1}">【商家配送】</c:if>--%>
											<%--</td>--%>
											<td title="${data.ordersItemName}">
												${data.shortOrdersItemName}
											</td>
                                            <td>
                                                <c:if test="${data.shippingMethod==0}">
                                                    ${data.pickUpTime}
                                                </c:if>
                                                <c:if test="${data.shippingMethod==1}">
                                                    ${data.shippingTime}
                                                </c:if>
                                            </td>

											<td>
												<c:if test="${data.serviceConfirmStatus==0}"><span class="label label-danger">未确认</span></c:if>
												<c:if test="${data.serviceConfirmStatus==1}"><span class="label label-primary">已确认</span></c:if>
											</td>
											<td>
												<c:if test="${data.ordersStatus==0}">未确认</c:if>
												<c:if test="${data.ordersStatus==1}">已确认</c:if>
												<c:if test="${data.ordersStatus==2}">已完成</c:if>
												<c:if test="${data.ordersStatus==3}">已取消</c:if>
												<c:if test="${data.ordersStatus==4}">申请退款</c:if>
												<c:if test="${data.ordersStatus==5}">退款中</c:if>
												<c:if test="${data.ordersStatus==6}">已退款</c:if>
												,
												<c:if test="${data.payStatus==0}">未支付</c:if>
												<c:if test="${data.payStatus==1}">已支付</c:if>
												,
												<c:if test="${data.shippingStatus==0}">未发货</c:if>
												<c:if test="${data.shippingStatus==1}">已发货</c:if>
												<c:if test="${data.shippingStatus==2}">已确认收货</c:if>
											</td>
											<td>
												<a class="btn btn-primary btn-xs" onclick="detail(${data.id})"><i class="icon iconfont">&#xe6ad;</i>订单详情</a>

												<c:if test="${data.flagicon==null||data.flagicon==''}">
													<a href="#flagform" onclick="setFlaginfoOrder('${data.id}','${data.flagname}','${data.flaginfo}')" data-toggle="modal" ><i class="fa text-muted fa-lg fa-flag"></i></a>
												</c:if>
												<c:if test="${data.flagicon!=null&&data.flagicon!=''}">
													<a href="#flagform" onclick="setFlaginfoOrder('${data.id}','${data.flagname}','${data.flaginfo}')" data-toggle="modal" ><i title="${data.flaginfo}" class="${data.flagicon}"></i></a>
												</c:if>

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

						<div id="expressSnDiv" class="form-group" <c:if test="${order.shippingMethod==0}">style="display: none"</c:if>>
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


	<script>

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

            $("#serviceConfirmStatus").val("${serviceConfirmStatus}");

            //配送方式
            $("#shippingMethod").val("${shippingMethod}");

            $("#checkbox").click(function(){
                var checked = $(this).prop("checked");
                $("input[name='ids']").prop("checked",checked);
            });

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
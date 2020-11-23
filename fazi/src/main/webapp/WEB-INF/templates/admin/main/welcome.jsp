<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>主页</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link href="${respath}/admin/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
	<link href="${respath}/admin/css/plugins/morris/morris-0.4.3.min.css" rel="stylesheet">
	<link href="${respath}/admin/js/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.js"></script>
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.spline.js"></script>
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.pie.js"></script>
	<script src="${respath}/admin/js/plugins/flot/jquery.flot.symbol.js"></script>
	<script src="${respath}/admin/js/demo/peity-demo.min.js"></script>
	<script src="${respath}/admin/js/plugins/peity/jquery.peity.min.js"></script>
	<script src="${respath}/admin/lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script src="${respath}/admin/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="${respath}/admin/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row">
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-success pull-right">本月</span>
					<h5>收入</h5>
				</div>
				<div class="ibox-content">
					<h1 class="no-margins">${allAmount}</h1>
					<div class="stat-percent font-bold text-success">
						${amount}
						<!-- <i class="fa fa-bolt"></i> -->
					</div>
					<small>全部收入</small>
				</div>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-info pull-right">本月</span>
					<h5>订单</h5>
				</div>
				<a href="${base}/admin/orders/list">
					<div class="ibox-content">
						<h1 class="no-margins">${orderAllCounts}</h1>
						<div class="stat-percent font-bold text-info">
							${orderCounts}
							<!-- <i class="fa fa-level-up"></i> -->
						</div>
						<small>全部订单</small>
					</div>
				</a>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-primary pull-right">本月</span>
					<h5>商品数量</h5>
				</div>
				<a href="${base}/admin/product/list">
					<div class="ibox-content">
						<h1 class="no-margins">${allProductCounts}</h1>
						<div class="stat-percent font-bold text-navy">
							${productCounts}
							<!-- <i class="fa fa-level-up"></i> -->
						</div>
						<small>全部商品</small>
					</div>
				</a>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-danger pull-right">本月</span>
					<h5>活跃用户</h5>
				</div>
				<a href="${base}/admin/member/list">
					<div class="ibox-content">
						<h1 class="no-margins">${memberAllCounts}</h1>
						<div class="stat-percent font-bold text-danger">
							${memberCounts}
							<!-- <i class="fa fa-level-down"></i> -->
						</div>
						<small>全部用户</small>
					</div>
				</a>
			</div>
		</div>
	</div>
	<div class="row">

		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-danger pull-right">全部</span>
					<h5>待发货</h5>
				</div>
				<a href="${base}/admin/orders/list?payStatus=1&sendStatus=0&ordersStatus=1">
					<div class="ibox-content">
						<h1 class="no-margins">${unshippingOrders}</h1>
						<div class="stat-percent font-bold text-danger"></div>
						<small>待发货订单</small>
					</div>
				</a>
			</div>
		</div>

		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-danger pull-right">今日</span>
					<h5>今日新单</h5>
				</div>
				<a href="${base}/admin/orders/list?payStatus=1&orderBegDt=${orderBegDt}&orderEndDt=${orderEndDt}">
					<div class="ibox-content">
						<h1 class="no-margins">${todayOrders}</h1>
						<div class="stat-percent font-bold text-danger"></div>
						<small>新订单</small>
					</div>
				</a>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-danger pull-right">今日</span>
					<h5>自提订单</h5>
				</div>
				<a href="${base}/admin/orders/list?shippingMethod=0&payStatus=1&orderBegDt=${orderBegDt}&orderEndDt=${orderEndDt}">
					<div class="ibox-content">
						<h1 class="no-margins">${todayPickUpOrders}</h1>
						<div class="stat-percent font-bold text-danger"></div>
						<small>自提订单</small>
					</div>
				</a>
			</div>
		</div>

		<div class="col-sm-3">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<span class="label label-danger pull-right">全部</span>
					<h5>维权订单</h5>
				</div>
				<a href="${base}/admin/orders/list?ordersStatus=4&payStatus=1">
					<div class="ibox-content">
						<h1 class="no-margins">${todayRefundOrders}</h1>
						<div class="stat-percent font-bold text-danger"></div>
						<small>待处理维权订单</small>
					</div>
				</a>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>订单</h5>
					<div class="pull-right">
						<div class="btn-group">
							<button type="button" onclick="month()" class="btn btn-xs btn-white">月</button>
							<button type="button" onclick="year()" class="btn btn-xs btn-white">年</button>
						</div>
					</div>
				</div>
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<div class="flot-chart">
								<div class="flot-chart-content" id="orderChart"></div>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>消息</h5>
					<!-- <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div> -->
				</div>
				<div class="ibox-content ibox-heading">
					<h3><i class="fa fa-envelope-o"></i> 新消息</h3>
					<small><i class="fa fa-tim"></i> 您有${orderListSize}条新订单</small>
				</div>
				<div class="ibox-content" id="orderList">

					<c:forEach items="${orderList }" var="order" varStatus="s">
						<a href="${base}/admin/orders/list?payStatus=1&ordersStatus=1&sendStatus=0">
							<div id="o${s.index }" class="feed-activity-list omsg" style="display:none;">
								<div class="feed-element">
									<div>
										<small class="pull-right text-navy">${order.timeAgo }</small>
										<strong>${order.name }</strong>
										<div>付款成功，点击查看详情！</div>
										<small class="text-muted"><fmt:formatDate value="${order.payDate }" pattern="MM-dd HH:mm:ss"/></small>
									</div>
								</div>
							</div>
						</a>
					</c:forEach>

				</div>
			</div>
		</div>
		<div class="col-sm-8">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>商品销售排行榜</h5>
					<!-- <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div> -->
				</div>
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<table class="table table-hover margin bottom">
								<thead>
								<tr>
									<th class="text-center">序号</th>
									<th>名称</th>
									<th class="text-center">销售量</th>
									<th class="text-center">销售额</th>
								</tr>
								</thead>
								<tbody>

								<c:if test="${productSale!=null}">
									<c:forEach items="${productSale}" var="data" varStatus="status" >
										<tr>
											<td class="text-center">${status.index+1}</td>
											<td>${data.productName}
												</small>
											</td>
											<td class="text-center small">${data.quantity}</td>
											<td class="text-center"><span class="label label-primary">&yen;${data.amount}</span>
											</td>
										</tr>
									</c:forEach>
								</c:if>

								</tbody>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>会员增长月度趋势分析图</h5>
					<!-- <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div> -->
				</div>
				<div class="ibox-content">
					<div class="flot-chart">
						<div class="flot-chart-content" id="userChart"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>收益交易额年度趋势分析图</h5>
					<!-- <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div> -->
				</div>
				<div class="ibox-content">
					<div class="flot-chart">
						<div class="flot-chart-content" id="shouyiChart"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="js/jquery.min.js?v=2.1.4"></script>
	<script src="js/bootstrap.min.js?v=3.3.6"></script>
	<script src="js/plugins/flot/jquery.flot.js"></script>
	<script src="js/plugins/flot/jquery.flot.tooltip.min.js"></script>
	<script src="js/plugins/flot/jquery.flot.spline.js"></script>
	<script src="js/plugins/flot/jquery.flot.resize.js"></script>
	<script src="js/plugins/flot/jquery.flot.pie.js"></script>
	<script src="js/plugins/flot/jquery.flot.symbol.js"></script>
	<script src="js/plugins/peity/jquery.peity.min.js"></script>
	<script src="js/demo/peity-demo.min.js"></script>
	<script src="js/content.min.js?v=1.0.0"></script>
	<script src="js/plugins/jquery-ui/jquery-ui.min.js"></script>
	<script src="js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script type="text/javascript" src="lib/hcharts/Highcharts/5.0.6/js/highcharts.js"></script>
	<script>

        var currentIndex = 1;
        //消息切换
        function ordersSend() {
            var pageSize = 2;
            var orderListSize = "${orderListSize}"/1;
            var sumPage = orderListSize/pageSize+(orderListSize%pageSize===0?0:1);
            if(currentIndex>sumPage){
                currentIndex = 1;
            }
            var startIndex = currentIndex*pageSize-pageSize;
            var endIndex = currentIndex*pageSize;
            $(".omsg").hide();
            for(var i=startIndex;i<endIndex;i++){
                $("#o"+i).fadeIn();
            }
            currentIndex++;
        }

        //当年每月订单量统计
        function year() {
            var chart = $('#orderChart').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                legend: {
                    enabled: false,
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 150,
                    y: 100,
                    floating: true,
                    borderWidth: 1,
                },
                exporting: { //导出
                    enabled: true
                },
                xAxis: {
                    categories: [
                        <c:forEach items="${orderCountForMonth.monthList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                },
                yAxis: {
                    title: {
                        text: '订单数'
                    }
                },
                tooltip: {
                    shared: true,
                    valueSuffix: ' 个'
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    areaspline: {
                        fillOpacity: 0.5
                    }
                },
                series: [{
                    name: '订单数',
                    color: '#1ab394',
                    data: [
                        <c:forEach items="${orderCountForMonth.countList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                }]
            });
        }

        //当月每日订单量统计
        function month() {
            var chart = $('#orderChart').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                legend: {
                    enabled: false,
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 150,
                    y: 100,
                    floating: true,
                    borderWidth: 1
                },
                exporting: { //导出
                    enabled: true
                },
                xAxis: {
                    categories: [
                        <c:forEach items="${orderCountForDay.dayList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                },
                yAxis: {
                    title: {
                        text: '订单数'
                    }
                },
                tooltip: {
                    shared: true,
                    valueSuffix: ' 个'
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    areaspline: {
                        fillOpacity: 0.5
                    }
                },
                series: [{
                    name: '订单数',
                    color: '#1ab394',
                    data: [
                        <c:forEach items="${orderCountForDay.countList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                }]
            });
        }

        $(function() {

            ordersSend();
            //循环展示当前订单
            setInterval("ordersSend()",5000);

            //当月每日订单量统计
            var chart = $('#orderChart').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: ''
                },
                legend: {
                    enabled: false,
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 150,
                    y: 100,
                    floating: true,
                    borderWidth: 1
                },
                exporting: { //导出
                    enabled: true
                },
                xAxis: {
                    categories: [
                        <c:forEach items="${orderCountForDay.dayList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                },
                yAxis: {
                    title: {
                        text: '订单数'
                    }
                },
                tooltip: {
                    shared: true,
                    valueSuffix: ' 个'
                },
                credits: {
                    enabled: false
                },
                plotOptions: {
                    areaspline: {
                        fillOpacity: 0.5
                    }
                },
                series: [{
                    name: '订单数',
                    color: '#1ab394',
                    data: [
                        <c:forEach items="${orderCountForDay.countList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                }]
            });
            //会员统计
            var chart = $('#userChart').highcharts({
                chart: {
                    type: 'column'
                },
                credits: { //版权
                    enabled: false
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                exporting: { //导出
                    enabled: true
                },
                xAxis: {
                    title: {
                        text: '日期（1-30号）'
                    },
                    categories: [
                        <c:forEach items="${memberCountForDay.dayList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                },
                yAxis: {
                    min: 0,
                    //          			showEmpty: false,
                    title: {
                        text: '人数 (个)'
                    }
                },
                tooltip: {
                    shared: true,
                    useHTML: true
                },
                legend: {
                    enabled: false,
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 150,
                    y: 100,
                    floating: true,
                    borderWidth: 1
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '会员个数',
                    color: '#23c6c8',
                    data: [
                        <c:forEach items="${memberCountForDay.countList}" var="data" varStatus="status" >
                        ${data},
                        </c:forEach>
                    ]
                }]
            });
            //收益统计
            var chart = $('#shouyiChart').highcharts({
                credits: { //版权
                    enabled: false
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                exporting: { //导出
                    enabled: true
                },
                xAxis: {
                    title: {
                        text: '（1-12月份）'
                    },
                    categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
                    crosshair: {
                        width: 1,
                        color: 'gray',
                        dashStyle: 'shortdot'
                    } //十字星线
                },
                yAxis: {
                    min: 0,
                    //          			showEmpty: false,
                    title: {
                        text: '收益(元)'
                    },
                    crosshair: {
                        width: 1,
                        color: 'gray',
                        dashStyle: 'shortdot'
                    } //十字星线
                },
                tooltip: {
                    shared: true,
                    split: false,
                    useHTML: true
                },
                legend: {
                    enabled: true,
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 80,
                    y: 0,
                    floating: true,
                    borderWidth: 0.5
                },
                plotOptions: {
                    column: {
                        pointPadding: 0,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: '累计收益',
                    color: '#1c84c6',
                    data: [
                        ${orderAmountForMonth.month1},
                        ${orderAmountForMonth.month2},
                        ${orderAmountForMonth.month3},
                        ${orderAmountForMonth.month4},
                        ${orderAmountForMonth.month5},
                        ${orderAmountForMonth.month6},
                        ${orderAmountForMonth.month7},
                        ${orderAmountForMonth.month8},
                        ${orderAmountForMonth.month9},
                        ${orderAmountForMonth.month10},
                        ${orderAmountForMonth.month11},
                        ${orderAmountForMonth.month12}
                    ]
                }]
            });
        })
	</script>
</body>
</html>
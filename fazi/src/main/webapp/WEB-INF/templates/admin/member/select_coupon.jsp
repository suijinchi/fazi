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

	<form role="form" class="form-inline" method="get" action="${base}/admin/member/select_coupon">
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

						<div class="form-group">
							<button type="button" id="confirmSend" class="btn btn btn-primary"> <i class="icon iconfont">&#xe627;</i> 确认发放</button>
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
									<th>序号</th>
									<th>id</th>
									<th>优惠券标题</th>
									<th>优惠券类型</th>
									<th>有效时间</th>
									<th>状态</th>
									<th>库存</th>
									<th>已领取</th>
									<th>创建日期</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${page.content}" var="data" varStatus="status" >
									<tr>
                                        <td>
                                            <input type="checkbox" value="${data.id}" name="ids">
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
												${data.createDate}
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

<script>

    $(function() {

        $("#checkbox").click(function(){
            var checked = $(this).prop("checked");
            $("input[name='ids']").prop("checked",checked);
        });

		var memberCount = parent.selectMemberIds.length;
        $("#confirmSend").click(function(){
            var selectCountIds = [];
            $("input[name='ids']:checked").each(function(){
                var ids = $(this).val();
                selectCountIds.push(ids);
            });
            if(selectCountIds.length==0){
                layer.msg("请选择优惠券");
                return ;
			}
            layer.confirm('确认给'+memberCount+'个会员发放'+selectCountIds.length+'张优惠券？', {
                btn: ['是','否'] //按钮
            }, function(){
				parent.selectCouponIds = selectCountIds;
				parent.sendCountToMemeber();
            }, function(){

            });

        });

    });
</script>

</body>

</html>
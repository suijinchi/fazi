<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>优惠券</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet" charset="utf-8">
		<link href="${respath}/admin/css/plugins/iCheck/custom.css" rel="stylesheet" charset="utf-8">

		<link href="${respath}/admin/css/style.min862f.css?v=${ver}" rel="stylesheet" charset="utf-8">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet" charset="utf-8">
        <link href="${respath}/admin/css/plugins/steps/jquery.steps.css?id=0" rel="stylesheet" charset="utf-8">

	</head>

	<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="ibox-content" style="height: auto;overflow: hidden;">
				<h1>券面信息</h1>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<label>优惠券标题 *</label>
							<input id="name" name="name" readonly value="${couponType.name}" type="text" class="form-control required">
						</div>
						<div class="form-group">
							<label>优惠券副标题 *</label>
							<input id="subname" name="subname" readonly value="${couponType.subname}" type="text" class="form-control required">
						</div>
						<div class="form-group i-checks">
							<label>优惠券类型 *</label>
							<div><label><input type="radio" checked="" readonly value="1" name="type" style="display: inline-block"><i></i> 代金券</label></div>
						</div>

						<div class="form-group">
							<label>减免金额 *</label>
							<input id="cutMoney" name="cutMoney" readonly value="${couponType.cutMoney}" type="text" class="form-control required">
						</div>

						<div class="form-group i-checks">
							<label>使用条件 *</label>
							<div><label><input type="radio" <c:if test="${couponType.useType==1}">checked</c:if> disabled value="1" name="useType" style="display: inline-block"><i></i> 不限制</label></div>
							<div style="height: 35px;line-height: 35px">
								<label style="float: left;"><input type="radio" <c:if test="${couponType.useType==2}">checked</c:if> disabled value="2" name="useType" style="display: inline-block"><i></i> 满&nbsp;&nbsp;&nbsp;</label>
								<div class="input-group m-b" style="float: left;width: 70%">
									<input type="text" id="byFull" name="byFull" readonly value="${couponType.byFull}" class="form-control">
									<span class="input-group-addon">元</span>
								</div>
							</div>
						</div>

						<div class="form-group i-checks">
							<label>是否显示 *</label>
							<div>
								<c:if test="${couponType.isShow==0}">
									<label><input type="radio" checked value="0" name="isShow" style="display: inline-block"><i></i> 否</label>
								</c:if>
								<c:if test="${couponType.isShow==1}">
									<label><input type="radio" checked value="1" name="isShow" style="display: inline-block"><i></i> 是</label>
								</c:if>
							</div>
						</div>

						<div class="form-group i-checks">
							<label>是否与积分同享 *</label>
							<div>
								<c:if test="${couponType.isWithPointShare==0}">
									<label><input type="radio" value="0" name="isWithPointShare" style="display: inline-block"><i></i> 否</label>
								</c:if>
								<c:if test="${couponType.isWithPointShare==1}">
									<label><input type="radio" checked value="1" name="isWithPointShare" style="display: inline-block"><i></i> 是</label>
								</c:if>
							</div>
						</div>

						<div class="form-group i-checks">
							<label>有效期 *</label>
							<div style="height: 35px;line-height: 35px;margin-bottom: 7px">
								<label style="float: left"><input type="radio" <c:if test="${couponType.validDateType==1}">checked</c:if> disabled value="1" name="validDateType" style="display: inline-block"><i></i> 固定日期</label>
								<div class="col-sm-9">
									<div class="row">
										<div class="col-md-6">
											<input type="text" id="validStartDate" name="validStartDate" value="<fmt:formatDate value="${couponType.validStartDate}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly placeholder="开始时间" class="form-control">
										</div>
										<div class="col-md-6">
											<input type="text" id="validEndDate" name="validEndDate" value="<fmt:formatDate value="${couponType.validEndDate}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly placeholder="结束时间" class="form-control">
										</div>
									</div>
								</div>
							</div>

							<div style="height: 35px;line-height: 35px;margin-bottom: 7px">
								<label style="float: left;"><input type="radio" <c:if test="${couponType.validDateType==2}">checked</c:if> disabled value="2" name="validDateType" readonly style="display: inline-block"><i></i> 领取后&nbsp;</label>
								<div class="input-group m-b" style="float: left;width: 30%">
									<input type="text" id="validGetDay" name="validGetDay" value="${couponType.validGetDay}" readonly class="form-control">
									<span class="input-group-addon">天</span>
								</div>
								<span style="float: left">&nbsp;生效，有效天数&nbsp;</span>
								<div class="input-group m-b" style="float: left;width: 30%">
									<input type="text" id="validDays" name="validDays" value="${couponType.validDays}" readonly class="form-control">
									<span class="input-group-addon">天</span>
								</div>
							</div>
						</div>

					</div>

				</div>

				<h1>基本规则</h1>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<label>库存 *</label>
							<input id="stock" name="stock" type="text" value="${couponType.stock}" class="form-control required">
						</div>
					</div>

					<div class="col-sm-12">
						<div class="form-group">
							<label>每人限领 *</label>
							<input id="getLimit" name="getLimit" type="text" readonly value="${couponType.getLimit}" class="form-control required">
						</div>
					</div>

					<div class="col-sm-12">
						<div class="form-group">
							<label>使用说明 *</label>
							<textarea name="useMemo" class="form-control" readonly rows="3">${couponType.useMemo}</textarea>
						</div>
					</div>

					<div class="col-sm-12">
						<div class="form-group">
							<label>客服电话 *</label>
							<input id="contact" name="contact" type="text" value="${couponType.contact}" class="form-control required">
						</div>
					</div>

					<div class="col-sm-12">
						<div class="form-group i-checks">
							<label>可使用范围 *</label>
							<div>
								<label><input type="radio" <c:if test="${couponType.useScope==1}">checked</c:if> value="1" name="useScope" disabled style="display: inline-block"><i></i> 全部商品</label>
								<label><input type="radio" <c:if test="${couponType.useScope==2}">checked</c:if> value="2" name="useScope" disabled style="display: inline-block"><i></i> 指定商品</label>
							</div>
							<div id="useScopeDiv" style="height: 35px;line-height: 35px;<c:if test="${couponType.useScope==1}">display: none</c:if>">

								<div id="content" class="m-t" style="min-height:100px;">
									<c:forEach items="${couponTypeProductList}" var="ctp">
										<div class="input-group m-b m-r" style="float: left;width: 40%">
											<input type="text" value="${ctp.productName}" readonly class="form-control">
											<input type="hidden" name="ctp" value="${ctp.productId}" readonly class="form-control">
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
	</div>


	<script src="${respath}/admin/js/jquery.min.js"></script>
	<script src="${respath}/admin/js/bootstrap.min.js"></script>
	<script src="${respath}/admin/js/content.min.js"></script>
	<script src="${respath}/admin/js/plugins/staps/jquery.steps.min.js"></script>

	<script src="${respath}/admin/js/plugins/validate/jquery.validate.min.js?"></script>
	<script src="${respath}/admin/js/plugins/validate/messages_zh.min.js?"></script>

	<script src="${respath}/admin/lib/layer/2.4/layer.js"></script>
	<script src="${respath}/admin/js/layer-yw.js"></script>
	<script src="${respath}/admin/lib/laydate/laydate.js"></script>
	<script src="${respath}/admin/js/plugins/iCheck/icheck.min.js"></script>
    <script src="${respath}/admin/js/plugins/validate/validate-methods.js?v=${ver}"></script>

	<script src="${respath}/admin/js/plugins/suggest/bootstrap-suggest.min.js?v=${ver}"></script>

	<script>


	</script>

	</body>

</html>
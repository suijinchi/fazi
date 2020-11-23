<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>优惠券添加</title>
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
        <style>
            #content{
                min-height: 200px;
				width: 100%;
                float: left;
                overflow: scroll;
                border: 1px solid #bbb;
                padding: 10px;
            }
        </style>
	</head>

	<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

		<div class="row">
			<div class="col-sm-12">
				<div class="ibox">
					<div class="ibox-title">
						<h5>添加优惠券</h5>
					</div>
					<div class="ibox-content" style="height: auto;overflow: hidden;">
						<form id="form" class="wizard-big">
                            <input name="id" type="hidden" value="${couponType.id}" />
                            <h1>券面信息</h1>
							<fieldset>
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
												<label><input type="radio" <c:if test="${couponType.isShow==0}">checked</c:if> value="0" name="isShow" style="display: inline-block"><i></i> 否</label>
												<label><input type="radio" <c:if test="${couponType.isShow==1}">checked</c:if> value="1" name="isShow" style="display: inline-block"><i></i> 是</label>
											</div>
										</div>

										<div class="form-group i-checks">
											<label>是否与积分同享 *</label>
											<div>
												<label><input type="radio" disabled <c:if test="${couponType.isWithPointShare==0}">checked</c:if> value="0" name="isWithPointShare" style="display: inline-block"><i></i> 否</label>
												<label><input type="radio" disabled <c:if test="${couponType.isWithPointShare==1}">checked</c:if> value="1" name="isWithPointShare" style="display: inline-block"><i></i> 是</label>
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
							</fieldset>

							<h1>基本规则</h1>
							<fieldset>
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

												<div class="input-group">
													<input id="selectProduct" type="text" class="form-control" id="testNoBtn">
													<div class="input-group-btn">
														<button type="button" class="btn btn-white dropdown-toggle" data-toggle="dropdown">
															<span class="caret"></span>
														</button>
														<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
													</div>
												</div>

												<div id="content" class="m-t" style="min-height:100px;float: left;width: 100%">
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
							</fieldset>
<%--

							<h1>警告</h1>
							<fieldset>
								<div class="text-center" style="margin-top: 120px">
									<h2>你是火星人 :-)</h2>
								</div>
							</fieldset>
--%>

						</form>
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

		function deleteCtp(id){
		    $("#ctp"+id).remove();
		}

        $(document).ready(function () {
            var submitFlag = false;
            $("#form").steps({
                bodyTag: "fieldset", onStepChanging: function (event, currentIndex, newIndex) {
                    if (currentIndex > newIndex) {
                        return true
                    }
                    if (newIndex === 3 && Number($("#age").val()) < 18) {
                        return false
                    }
                    var form = $(this);
                    if (currentIndex < newIndex) {
                        $(".body:eq(" + newIndex + ") label.error", form).remove();
                        $(".body:eq(" + newIndex + ") .error", form).removeClass("error")
                    }
                    form.validate().settings.ignore = ":disabled,:hidden";
                    return form.valid()
                }, onStepChanged: function (event, currentIndex, priorIndex) {
                    if (currentIndex === 2 && Number($("#age").val()) >= 18) {
                        $(this).steps("next")
                    }
                    if (currentIndex === 2 && priorIndex === 3) {
                        $(this).steps("previous")
                    }
                }, onFinishing: function (event, currentIndex) {
                    var form = $(this);
                    form.validate().settings.ignore = ":disabled";
                    return form.valid()
                }, onFinished: function (event, currentIndex) {
                    if(submitFlag){
                        return;
                    }
                    submitFlag = true;
                    $.ajax({
                        type:"post",
                        url:"update",
                        data:$("#form").serialize(),
                        dataType:"json",
                        success:function(data){
                            if(data.type=="success"){
                                layer.msg(data.content,function(){
                                    window.location.href="${base}/admin/coupon_type/list";
								});
                            }else if(data.type=="error"){
                                layer.msg(data.content);
                                submitFlag = false;
                            }
                        },
                        error:function(){
                            alert("系统异常");
                            submitFlag = false;
                        }
                    });
                }
            }).validate({
                errorPlacement: function (error, element) {
                    // element.before(error)
                    element.closest(".form-group").children("label").after(error)
                }, rules: {
                    cutMoney:{
                        required:true,
                        isPrice:true
                    },

                    useType:{
                        required:true
                    },
                    <c:if test="${useType==2}">
                    byFull:{
                        required:true,
                        isPrice:true
                    },
                    </c:if>

                    validDateType:{
                        required:true
                    },
                    <c:if test="${validDateType==1}">
                    validStartDate:{
                        required:true
                    },
                    validEndDate:{
                        required:true
                    },
                    </c:if>
                    <c:if test="${validDateType==2}">
                    validGetDay:{
                        required:true,
                        digits:true,
                        min:0
                    },
                    validDays:{
                        required:true,
                        digits:true,
                        min:0
                    }
                    </c:if>


                    stock:{
                        required:true,
                        digits:true,
						min:0
					},
                    getLimit:{
                        required:true,
                        digits:true,
                        min:1
					},
                    useMemo:{
                        required:true,
                        maxlength:500
					},
                    contact:{
                        required:true,
                        isTel:true
					},
					useScope:{
                        required:true
					}
                }
            });

            $(document).ready(function () {
                //$(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green",})
            });


            $("[name='useType']").click(function(){
                var useType = $(this).val();
                if(useType=="1"){
                    $("#byFull").prop("disabled",true).rules("remove");
                }else{
                    $("#byFull").prop("disabled",false).val("").rules("add", {
                        required:true,
                        isPrice:true
                    });
                }
            });

            $("[name='useScope']").click(function(){
                var useScope = $(this).val();
                if(useScope=="1"){
					$("#useScopeDiv").hide();
                }else{
                    $("#useScopeDiv").show();
                }
            });


            $("#selectProduct").bsSuggest({
				url:"findProduct?keyword=",
                dataType:"json",
                indexId: 0,             //data.value 的第几个数据，作为input输入框的内容
                indexKey: 1,            //data.value 的第几个数据，作为input输入框的内容
                allowNoKeyword: false,  //是否允许无关键字时请求数据。为 false 则无输入时不执行过滤请求
                multiWord: true,        //以分隔符号分割的多关键字支持
                separator: ",",         //多关键字支持时的分隔符，默认为空格
                getDataMethod: "url",   //获取数据的方式，总是从 URL 获取
                showHeader: true,       //显示多个字段的表头
                autoDropup: true,       //自动判断菜单向上展开
                effectiveFieldsAlias:{
				    Id: "商品id", Keyword: "商品名称", Price:"价格"
				},
				processData:function (e, keyword, data) {
					var json = e.value;
                    var data = {value: []};
                    if (!json || !json.length) {
                        return false;
                    }
                    len = json.length;
                    for (index = 0; index < len; index++) {
                        data.value.push({
                            "Id": json[index].id,
                            "Keyword": json[index].name,
                            "Price": json[index].priceRange,
							/*"ShowUrl":json[index].showUrl*/
                        });
                    }
                    return data;
                }
            }).on('onSetSelectValue', function (e, k, data) {
                var dom = $("#content").find("input[value='"+k.id+"']");
                if(dom.length>0){
                    return;
				}
                var html =
                '<div id="ctp'+k.id+'" class="input-group m-b m-r" style="float: left;width: 40%">'+
                '    <input type="text" value="'+k.key+'" readonly class="form-control">'+
				'    <input type="hidden" name="ctp" value="'+k.id+'" class="form-control">'+
                '    <span class="input-group-addon" onclick="deleteCtp('+k.id+')" style="cursor: pointer">删除</span>'+
                '</div>';
                $("#content").append(html);
            });

            $("#form").on("click","[name='validDateType']",function(){
                var validDateType = $(this).val();
                if(validDateType=="1"){
                    $("#validStartDate").prop("disabled",false).rules("add",{
                        required:true,
                        messages: {
                            required: "请选择开始时间"
                        }
                    });
                    $("#validEndDate").prop("disabled",false).rules("add",{
                        required:true,
                        messages: {
                            required: "请选择结束时间"
                        }
                    });
                    $("#validGetDay").prop("disabled",true).rules("remove");
                    $("#validDays").prop("disabled",true).rules("remove");
                }else{
                    $("#validStartDate").prop("disabled",true).rules("remove");
                    $("#validEndDate").prop("disabled",true).rules("remove");
                    $("#validGetDay").prop("disabled",false).rules("add",{
                        required:true,
                        digits:true,
                        min:0,
                        messages: {
                            required: "请填写领取后多少天生效"
                        }
                    });
                    $("#validDays").prop("disabled",false).rules("add",{
                        required:true,
                        digits:true,
                        min:0,
                        messages: {
                            required: "请填写有效天数"
                        }
                    });
                }
            })

            laydate.render({
                elem: '#validStartDate'
                ,type: 'datetime'
            });
            laydate.render({
                elem: '#validEndDate'
                ,type: 'datetime'
            });

        });
	</script>

	</body>

</html>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>商品管理-添加</title>
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
		<link href="${respath}/admin/css/style.min862f.css" rel="stylesheet">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
		<link href="${respath}/admin/lib/icheck/icheck.css" rel="stylesheet">
		<link href="${respath}/admin/css/plugins/iCheck/custom.css" rel="stylesheet">
		<link href="${respath}/admin/css/public.css" rel="stylesheet">

		<link href="${respath}/admin/css/specification.css" rel="stylesheet">
		<link href="${respath}/admin/css/goods.css" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="${respath}/admin/css/liandong.css">

		<script src="${respath}/admin/js/jquery.min.js"></script>
		<script src="${respath}/admin/js/bootstrap.min.js"></script>
		<script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script src="${respath}/admin/js/jquery.form.js"></script>	
		<script src="${respath}/admin/js/plugins/validate/jquery.validate.min.js?"></script>
		<script src="${respath}/admin/js/plugins/validate/messages_zh.min.js?"></script>
		<script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
		<script src="${respath}/admin/lib/laydate/laydate.js"></script>
		<script src="${respath}/admin/js/plugins/iCheck/icheck.min.js"></script>
		<script src="${respath}/admin/editor/kindeditor.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<%--<script type="text/javascript" src="${respath}/admin/js/liandong.js"></script>--%>

		<style type="text/css">
			.product-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
			.div_contentlist ul li{
				float: left;
			}
			.tab-content{
				overflow-x:hidden ;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<a href="${base}/admin/product/list" class="m-b-md">
				<button class="btn btn-primary"><i class="icon iconfont">&#xe605;</i>返回商品列表</button>
			</a>
			<div class="row">
				<div class="col-sm-12">
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>添加商品<small class="m-l-sm"></small></h5>
						</div>
						<div class="ibox-content">						 	
							<form action="save" class="form-horizontal" id="dataForm" name="dataForm" method="post" onsubmit="return false">
								<div class="tabs-container">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="product-add.html#tab-4"><i class="icon iconfont">&#xe69c;</i>基本信息</a></li>
										<li class=""><a data-toggle="tab" href="product-add.html#tab-5"><i class="icon iconfont">&#xe658;</i>商品介绍</a></li>
										<li class=""><a data-toggle="tab" href="product-add.html#tab-6"><i class="icon iconfont">&#xe69e;</i>商品方位图</a></li>
									</ul>
									<div class="tab-content" style="margin-top: 20px">
										<div id="tab-4" class="tab-pane active">

											<div class="form-group">
												<label class="col-sm-2 control-label">类型：</label>
												<div class="col-sm-4">
													<select id="type" class="form-control m-b" name="type">
														<option value="0" selected>普通商品</option>
														<option value="1">企业专区</option>
													</select>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">销售时间：</label>
												<div class="col-sm-8">
													<input id="sellDate" name="sellDate" class="form-control" type="text" >
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>商品名称：</label>
												<div class="col-sm-8">
													<input id="name" name="name" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>简称：</label>
												<div class="col-sm-8">
													<input id="subName" name="subName" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">商品分类: </label>
												<div class="col-sm-8">
													<select class="form-control" name="productCategoryId" size="1">
														<c:forEach items="${productCategoryList}" var="productCategory">
															<option value="${productCategory.id}">${productCategory.name}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>展示图：</label>
												<div class="col-sm-8">													
													<input class="form-control" type="text" name="showUrl" id="showUrl" readonly="readonly" style="display: inline-block;width: 200px;line-height: 34px;height: 34px;float: left;">
													<label for="file">
														<a class="btn btn-primary upload-btn"><i class="icon iconfont">&#xe692;</i> 选择图片</a>
													</label>
													<input type="file" name="file" id="file" onchange="uploadImage(this);" style="display: none;">
												</div>
											</div>
											<div id="imgDiv" class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-8">
													
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">长方形展示图：</label>
												<div class="col-sm-8">
													<input class="form-control" type="text" name="longShowUrl" id="longShowUrl" readonly="readonly" style="display: inline-block;width: 200px;line-height: 34px;height: 34px;float: left;">
													<label for="file1">
														<a class="btn btn-primary upload-btn"><i class="icon iconfont">&#xe692;</i> 选择图片</a>
													</label>

												</div>
											</div>
											<div id="imgDiv1" class="form-group">
												<label class="col-sm-2 control-label"></label>
												<div class="col-sm-8">

												</div>
											</div>


											<div class="form-group">
												<label class="col-sm-2 control-label">简介：</label>
												<div class="col-sm-8">
													<input id="introduce" name="introduce" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">排序：</label>
												<div class="col-sm-8">
													<input id="orders" name="orders" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">备注：</label>
												<div class="col-sm-8">
													<input id="memo" name="memo" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">标签：</label>
												<div class="col-sm-8">													
													<div class="checkbox i-checks">
														<c:forEach items="${tagList}" var="tag">
															<label><input type="checkbox" value="${tag.id}" name="tagIds">&nbsp;${tag.name}</label>
													   	</c:forEach>													
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>成分：</label>
												<div class="col-sm-8">
													<div class="checkbox i-checks">
														<c:forEach items="${componentList}" var="component">
															<label><input type="checkbox" value="${component.id}" name="componentIds">&nbsp;${component.name}</label>
														</c:forEach>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">设置：</label>
												<div class="col-sm-8">
													<div class="checkbox i-checks">
														<label>
															<input type="hidden" id="isMarketable" name="isMarketable" value="0">
															<input id="isMarketableCheck" value="1" type="checkbox"> <i></i> 上架</label>
														<label>
															<input type="hidden" id="isTop" name="isTop" value="0">
															<input id="isTopCheck" value="1" type="checkbox"> <i></i> 置顶</label>
													</div>
												</div>
											</div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">参考甜度：</label>
                                                <div class="col-sm-4">
                                                    <select class="form-control m-b" name="sweetness">
                                                        <option value="0">请选择甜度</option>
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                        <option value="4">4</option>
                                                        <option value="5">5</option>
                                                    </select>
                                                </div>
                                            </div>

											<div class="form-group">
												<label class="col-sm-2 control-label">是否有生日牌：</label>
												<div class="col-sm-8">
													<div class="checkbox i-checks">
														<label>
															<input id="isBirthdayCard1" value="1" name="isBirthdayCard" type="radio"> <i></i> 是
														</label>
														<label>
															<input id="isBirthdayCard0" value="0" checked name="isBirthdayCard" type="radio"> <i></i> 否
														</label>
													</div>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">商品规格：</label>
												<div class="col-sm-9">
													<div class="checkbox i-checks" id="guigeBox">
														<label id="sin">
															<input type="radio" value="1" name="optionsRadios" checked="checked" onchange="testRadios()">
															<i></i> 统一规格
														</label>
														<label id="mul">
															<input type="radio" value="2" name="optionsRadios" onchange="testRadios()">
															<i></i> 多规格
														</label>
													</div>
													<div style="height: 20px">

													</div>
													<div class="clear">

													</div>
													<div class="box-sep" id="box-sep" style="display: none;">
														<div class="panel panel-primary">
															<div class="panel-body">
																<div id="navtab1" style="width: 100%;">
																	<div tabid="tabItem3">
																		<div id="Div1">
																			<div position="center">
																				<div style="padding: 5px 8px;" class="div_content">
																					<!--<div class="div_title"><span>产品规格</span></div>-->
																					<div class="div_contentlist">
																						<c:forEach items="${specNameList}" var="specName" varStatus="status">
																						<ul class="Father_Title js_show_div">
																							<li specNameId="${specName.id}" class="fl">${specName.name}</li>
																							<%--<li class="fl"><a href="javascript:;" class="m-l text-info js_specifica_edit fl">编辑</a></li>--%>
																							<div class="clear">

																							</div>
																						</ul>
																						<ul class="Father_Item${status.index}">
																							<c:forEach items="${specValueList}" var="specValue">
																								<c:if test="${specValue.specNameId == specName.id}">
																								<li class="li_width">
																									<label>
																										<input id="${specValue.id}" type="checkbox" class="chcBox_Width" value="${specValue.value}" />${specValue.value}<span class="li_empty"></span>
																									</label>
																								</li>
																								</c:if>
																							</c:forEach>
																							<div class="clear">

																							</div>
																						</ul>
																						<br/>
																						</c:forEach>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>
																</div>

															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="form-group" id="box-sep-2" style="display: none;">
												<label class="col-sm-2 control-label">价格&库存：</label>
												<div class="col-sm-9">
													<div style="font-size:14px;line-height:30px;font-weight: bold;">批量填充：</div>
													<div>
														<input type="text" id="batch1" placeholder="价格" class="form-control" style="width: 13%;float: left;margin-right:1%;">
														<input type="text" id="batch2" placeholder="库存" class="form-control" style="width: 13%;float: left;margin-right:1%;">
														<input type="text" id="batch3" placeholder="用餐人数" class="form-control" style="width: 13%;float: left;margin-right:1%;">
														<input type="text" id="batch4" placeholder="餐具数" class="form-control" style="width: 13%;float: left;margin-right:1%;">
														<input type="text" id="batch5" placeholder="尺寸" class="form-control" style="width: 14%;float: left;margin-right:1%;">
														<input type="text" id="batch6" placeholder="积分最大抵扣额" class="form-control" style="width: 18%;float: left;margin-right:1%;">
														<input id="batchConfirm" type="button" value="确认" class="btn btn-primary">
														<div style="clear:both;"></div>
													</div>
													<div style="font-size:14px;line-height:30px;font-weight: bold;">请选择规格：</div>
													<div class="div_contentlist2">
														<ul>
															<li>
																<div id="createTable" style="width: 100%;overflow: auto;"></div>
															</li>
														</ul>
													</div>

												</div>
											</div>


											<div class="form-group single" style="display: block">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>售价：</label>
												<div class="col-sm-8">
													<input id="price" name="price" class="form-control" type="text">
												</div>
											</div>
											<div class="form-group single" style="display: block">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>库存：</label>
												<div class="col-sm-8">
													<input id="stock" name="stock" class="form-control" type="text">
												</div>
											</div>

											<div class="form-group single" style="display: block">
												<label class="col-sm-2 control-label"><i class="icon iconfont icon-need">&#xe657;</i>最大抵扣使用积分：</label>
												<div class="col-sm-8">
													<input id="maxOffsetPoint" name="maxOffsetPoint" class="form-control" type="text">
												</div>
											</div>

										</div>
										<div id="tab-5" class="tab-pane">
											<div>
												<input type="hidden" id="content" name="content" value="" style="display:none" />
												<textarea id="editor" style="width:1500px;height:300px;"></textarea>
											</div>											
										</div>
										<div id="tab-6" class="tab-pane">
											<div>
												<button class="btn btn-primary" id="addImage" type="button">添加图片</button>
											</div>
											<table class="table table-striped table-hover table-bordered table-condensed">
												<thead>
													<tr class="text-c">
														<th>文件</th>
														<th>排序</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody id="tt">
											
												</tbody>
											</table>										
										</div>
										
										<div style="margin-top:30px;">
											<div class="col-sm-8 col-sm-offset-3">
												<button class="btn btn-primary" id="submit" onclick="save()">提交</button>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<div id="forms">
			
			</div>

			<form id="dataForm111">
				<input type="file" name="file" id="file1" onchange="uploadImage1(this);" style="display: none;">
			</form>
			
		</div>


		<div class="modal inmodal fade" id="batchSet" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">批量设置</h4>
					</div>
					<div class="modal-body" style="background-color: #fff">
						<div class="js_set_specif_list">

							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【价格：】</strong></label>
									<span id="priceBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>
								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>

							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【库存：】 </strong></label>

									<span id="stockBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>

								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>

							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【用餐人数：】 </strong></label>

									<span id="shareNumBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>

								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>


							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【餐具数：】 </strong></label>

									<span id="tablewareBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>

								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>


							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【尺寸：】 </strong></label>

									<span id="sizeBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>

								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>



							<div class="form-group" style="height: 20px">
								<div class="col-sm-12">
									<label class="checkbox-inline"><strong>【积分最大抵扣额：】 </strong></label>

									<span id="maxOffsetPointBatch">
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
										<label class="checkbox-inline" >
											<input name="" type="checkbox" value="">同月球图案相同
										</label>
									</span>

								</div>
							</div>
							<div class="line line-dashed line-lg pull-in"></div>


						</div>
					</div>
					<div class="modal-footer">
						<button id="singleBatchConfirm" type="button" class="btn btn-primary" data-dismiss="modal">确认</button>
						<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div>


		<script>

            function uploadImage1(){
                var options = {
                    url : '${base}/admin/common/upload?fileType=image',
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        console.log(data);
                        if (data.type === "success") {
                            $('#longShowUrl').val(data.data.absolute);
                            $("#imgDiv1").find("div").html("<img style='margin-right:5px' src='"+data.data.absolute+"' width='100px' />");
                        }
                    },
                    error : function(data) {
                        console.log(data);
                    }
                };
                $("#dataForm111").ajaxSubmit(options);
            }

            function save() {

                var name = $('#name').val();
                if (name === null || name === undefined || name === '') {
                    layer.msg("请填写商品名称", {icon: 0});
                    return;
				}
                var subName = $('#subName').val();
                if (subName === null || subName === undefined || subName === '') {
                    layer.msg("请填写商品简称", {icon: 0});
                    return;
				}

				var showUrl = $("#showUrl").val();
                if(!showUrl){
                    layer.msg("请上传展示图", {icon: 0});
                    return;
				}

                /*var $tagIds = $('input[name=tagIds]:checked');
                if ($tagIds.length === 0) {
                    layer.msg("请选择商品标签", {icon: 0});
                    return;
				}*/
                var $componentIds = $('input[name=componentIds]:checked');
                if ($componentIds.length === 0) {
                    layer.msg("请选择商品成分", {icon: 0});
                    return;
                }
/*

                var sweetness = $("#sweetness").val();
                if(!sweetness||isNaN(sweetness)||sweetness<=0||sweetness>5){
                    layer.msg("请填写参考甜度 数字1-5", {icon: 0});
                    return;
                }
*/

				var optionsRadios = $('input[name=optionsRadios]:checked').val();
                var flag = true;

                if (optionsRadios === "1") {
                    var price = $('#price').val();
					if (price === null || price === undefined || price === '') {
                        layer.msg("请填写商品价格", {icon: 0});
                        return;
					}
                    var stock = $('#stock').val();
                    if (stock === null || stock === undefined || stock === '') {
                        layer.msg("请填写商品库存", {icon: 0});
                        return;
                    }

                    var maxOffsetPoint = $('#maxOffsetPoint').val();
                    if (maxOffsetPoint === null || maxOffsetPoint === undefined || maxOffsetPoint === '') {
                        layer.msg("请填写最大抵扣积分", {icon: 0});
                        return;
                    }

				} else if (optionsRadios === "2") {
                    var $tr = $('.tr');
                    if ($tr.length === 0) {
                        layer.msg("请选择商品规格", {icon: 0});
                        return;
					}
                    var products = [];
                    $.each($tr, function (i, n){
                        var specImg = $(n).children('td[name=specImg]').children('input').val();
                        var price = $(n).children('td[name=price]').children('input').val();
                        var stock = $(n).children('td[name=stock]').children('input').val();
                        /*var sn = $(n).children('td[name=sn]').children('input').val();*/
                        var people = $(n).children('td[name=people]').children('input').val();
                        var tableware = $(n).children('td[name=tableware]').children('input').val();
                        var size = $(n).children('td[name=size]').children('input').val();
                        var maxPoint = $(n).children('td[name=maxPoint]').children('input').val();
                        var spec_ids = $(n).children('td[name=spec_ids]').children('input').val();
                        var spec_values = $(n).children('td[name=spec_values]').children('input').val();
                        var isDisabled = $(n).children('td[name=isDisabled]').children('input').val();
                        if (isDisabled == 0) {
                            if (specImg === null || specImg === undefined || specImg === '') {
                                layer.msg("请上传商品规格图", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (price === null || price === undefined || price === '') {
                                layer.msg("请填写商品价格", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (stock === null || stock === undefined || stock === '') {
                                layer.msg("请填写商品库存", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (people === null || people === undefined || people === '') {
                                layer.msg("请填写商品用餐人数", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (tableware === null || tableware === undefined || tableware === '') {
                                layer.msg("请填写商品餐具数", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (size === null || size === undefined || size === '') {
                                layer.msg("请填写商品尺寸", {icon: 0});
                                flag = false;
                                return;
                            }
                            if (maxPoint === null || maxPoint === undefined || maxPoint === '') {
                                layer.msg("请填写商品积分最大抵扣额", {icon: 0});
                                flag = false;
                                return;
                            }
                        }
                        var index = {
                            specImg: specImg,
                            price: price,
                            stock: stock,
                            /* sn: sn,*/
                            people: people,
                            tableware: tableware,
                            size: size,
                            maxPoint: maxPoint,
                            spec_ids: spec_ids,
                            spec_values: spec_values,
                            isDisabled: isDisabled
                        };
                        products.push(index);
                    });
                    var jsonString = JSON.stringify(products);
				}

                if ($("#isMarketableCheck").is(':checked')) {
                    $("#isMarketable").val("1");
                }
                if ($("#isTopCheck").is(':checked')) {
                    $("#isTop").val("1");
                }
                $("#content").val(editor.html());

                if (flag) {
                    $.ajax({
                        type:"post",
                        url:"${base}/admin/product/save",
                        dataType:"json",
                        data:$("#dataForm").serialize() + "&jsonString=" + encodeURIComponent(jsonString),
                        beforeSend:function(){
                            $('#submit').prop("disabled",true);
                        },
                        success:function(data){
                            if(data.type==="success"){
                                layer.msg(data.content, {icon: 1,time: 2000}, function(){
                                    window.location.href="${base}/admin/product/list";
                                });

                            }else{
                                layer.msg(data.content, {icon: 0});
                                $('#submit').prop("disabled",false);
                            }
                        }
                    });
				}

            }
		
			function uploadImage(){
				var options = {
					url : '${base}/admin/common/upload?fileType=image',
					type : 'post',
					dataType : 'json',
					success : function(data) {
						console.log(data);
						if (data.type === "success") {
							$('#showUrl').val(data.data.absolute);
							$("#imgDiv").find("div").html("<img style='margin-right:5px' src='"+data.data.absolute+"' width='100px' />");
						}
					},
					error : function(data) {
						console.log(data);
					}
				};
				$("#dataForm").ajaxSubmit(options);
			}	
			
			function upload(urlInput,formTag){
				var options = {
					url : '${base}/admin/common/upload?fileType=image',
					type : 'post',
					dataType : 'json',
					success : function(data) {
						console.log(data);
						if (data.type === "success") {
							$(urlInput).val(data.data.absolute);
							//$("#imgDiv").find("div").html("<img style='margin-right:5px' src='"+data.data.absolute+"' width='100px' />");
						}
					},
					error : function(data) {
						console.log(data);
					}
				};
				$(formTag).ajaxSubmit(options);
			}

            function uploadSpecImg(urlInput,img,formTag){
                var options = {
                    url : '${base}/admin/common/upload?fileType=image',
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        console.log(data);
                        if (data.type === "success") {
                            $(urlInput).val(data.data.absolute);
                            $(img).attr("src", data.data.absolute);
                        }
                    },
                    error : function(data) {
                        console.log(data);
                    }
                };
                $(formTag).ajaxSubmit(options);
            }

            function removeImage(dom) {
 				console.log("removeImage");
 				$(dom).parent().parent().remove();
			}

			//设置商品规格禁用启用
			function setDisabled(dom) {
				var $isDisabled = $(dom).parent().children('input');
				var isDisabled = $isDisabled.val();
				if (isDisabled == 0) {
                    $isDisabled.val(1);
                    $(dom).html('&#xe694;');
                    $(dom).attr('title', '启用');
                    var $input = $(dom).parent().parent().children('td');
                    $.each($input, function (i, n) {
						$(n).children('input').attr('disabled', 'disabled');
                    });
				} else if (isDisabled == 1) {
                    $isDisabled.val(0);
                    $(dom).html('&#xe693;');
                    $(dom).attr('title', '禁用');
                    var $input = $(dom).parent().parent().children('td');
                    $.each($input, function (i, n) {
                        $(n).children('input').removeAttr('disabled', 'disabled');
                    });
				}
            }

			$(function() {

                //统一规格和多规格的切换
                $("#guigeBox input:radio[name='optionsRadios']").on('ifChecked', function(event) {
                    if ($(this).val() == '1') {
                        $("#box-sep").hide();
                        $("#box-sep-2").hide();
                        $('.single').css("display", "block");
                    } else {
                        $("#box-sep").show();
                        $("#box-sep-2").show();
                        $('.single').css("display", "none");
                    }
                });

                //批量添加值
                $('#batchConfirm').click(function () {
					var price = $('#batch1').val();
                    var stock = $('#batch2').val();
                    var people = $('#batch3').val();
                    var tableware = $('#batch4').val();
                    var size = $('#batch5').val();
                    var maxPoint = $('#batch6').val();
                    var $price = $('input[name=price11]');
                    $.each($price, function (i, n) {
						$(n).val(price);
                    });
                    var $stock = $('input[name=stock11]');
                    $.each($stock, function (i, n) {
                        $(n).val(stock);
                    });
                    var $people = $('input[name=people11]');
                    $.each($people, function (i, n) {
                        $(n).val(people);
                    });
                    var $tableware = $('input[name=tableware11]');
                    $.each($tableware, function (i, n) {
                        $(n).val(tableware);
                    });
                    var $size = $('input[name=size11]');
                    $.each($size, function (i, n) {
                        $(n).val(size);
                    });
                    var $maxPoint = $('input[name=maxPoint11]');
                    $.each($maxPoint, function (i, n) {
                        $(n).val(maxPoint);
                    });
                    $('#batch1').val('');
                    $('#batch2').val('');
                    $('#batch3').val('');
                    $('#batch4').val('');
                    $('#batch5').val('');
                    $('#batch6').val('');
                });

                //点击编辑
                $(".js_specifica_edit").click(function() {
                    $(this).hide();
                    $(this).parents(".js_show_div").hide();
                    $(this).parents(".js_show_div").next("ul").hide();
                    $(this).parents(".js_show_div").next("ul").next().next(".js_enter_div").show();
                })

                //隐藏编辑规格
                $(".js_enter_div").hide();

                // 点击取消
                $(".js_specvals_val_cancel").click(function() {
                    $(this).parents(".js_enter_div").prev().prev("ul").prev(".js_show_div").show();
                    $(this).parents(".js_enter_div").prev().prev("ul").prev(".js_show_div").children().children(".js_specifica_edit").show();
                    $(this).parents(".js_enter_div").prev().prev("ul").show(); //显示编辑
                    $(this).parents(".js_enter_div").hide();
                });

                //输入规格后，点击添加
                $(".js_add_speval").click(function() {
                    var a = $(this).parent().prev(".col-sm-5").children(".js_input_outer").children(".js_custom_input");
                    var b = a.val();
                    var html = '<span class="label label-default bg-light dker specvals-show js_specvals_show js_specvals_del">' + b + '<i class="icon iconfont" style="font-size: 12px;">&#xe693;</i></span>'
                    $(this).parents(".js_enter_div").find(".specList").append(html);
                    $(".js_custom_input").val('');
                })

                //选择规格，表格显示记录
                $("#guigeBox input:radio[name='optionsRadios']").on('ifChecked', function(event) {})

                //删除规格
                $(document).delegate(".js_specvals_del", "click", function() {
                    $(this).hide();
                });

				var index = 0;
				$("#addImage").click(function () {
					var i = index++;
					var html  = "<tr>                                                                                                      									" +
								"	<td>																																	" +							
								"		<div class='row'>																													" +
								"			<div class='col-sm-6' id='productImageDiv'>																						" +
								"				<input id='productImages"+i+"' name='productImages' class='form-control' type='text' readonly='readonly' width='200px' value=''>	" +
								"			</div>																															" +
								"			<div class='col-sm-6'>																											" +
								"				<label class='btn btn-primary' for='inp-file"+i+"'><i class='icon iconfont'>&#xe692;</i> 选择文件</label>			" +
								"			</div>																															" +
								"		</div>																																" +
								"	</td>																																	" +
								"	<td>																																	" +
								"		<input id='imgOrders' name='imgOrders' class='form-control' type='text'>																" +
								"	</td>																																	" +
								"	<td>																																	" +
								"		<button type='button' onclick='removeImage(this)' class='btn btn-white btn-sm'>删除 </button>											" +
								"	</td>																																	" +
								"</tr>																																		";
					$("#tt").append(html);
					var form = "<form id='fileForm"+i+"'><input name='file' type='file' id='inp-file"+i+"' onchange=\"upload('#productImages"+i+"','#fileForm"+i+"')\" style='display: none;'></form>";
					$("#forms").append(form);
				});
			
				$(".i-checks").iCheck({
					checkboxClass: "icheckbox_square-green",
					radioClass: "iradio_square-green"
				});
				
				$(".full-height-scroll").slimScroll({
					height: "100%"
				});
				
				KindEditor.ready(function(K) {
	                window.editor = K.create('#editor',{
		                uploadJson : '${base}/admin/common/upload1',//服务器端的地址  
		                allowImageUpload  : true//运行上传图片
	                });
	        	});
				
			});

            $(function () {
                //SKU信息
                $(".div_contentlist label").bind("change", function () {
                    step.Creat_Table();
                });
                var step = {
                    //SKU信息组合
                    Creat_Table: function () {
                        step.hebingFunction();
                        var SKUObj = $(".Father_Title");
                        //var skuCount = SKUObj.length;//
                        var arrayTile = new Array();//标题组数
                        var arrayInfor = new Array();//盛放每组选中的CheckBox值的对象
						var arraySpecId = new Array();//盛放每组选中的CheckBox的ID
                        var arrayColumn = new Array();//指定列，用来合并哪些列
                        var bCheck = true;//是否全选
                        var columnIndex = 0;
                        $.each(SKUObj, function (i, item){
                            var itemName = "Father_Item" + i;
                            var len = $("." + itemName + " input[type=checkbox]:checked").length;
                            if(len>0){
                                arrayColumn.push(columnIndex);
                                columnIndex++;
                                arrayTile.push(SKUObj.find("li:first-child").eq(i).html().replace("：", ""));

                                //选中的CHeckBox取值
                                var order = new Array();
                                $("." + itemName + " input[type=checkbox]:checked").each(function (){
                                    order.push($(this).val());
                                });
                                arrayInfor.push(order);
                                if (order.join() == ""){
                                    //bCheck = false;
                                }
                                //选中的checkbox取ID
                                var specId = new Array();
                                $("." + itemName + " input[type=checkbox]:checked").each(function (){
                                    specId.push($(this).attr("id"));
                                });
                                arraySpecId.push(specId);
                            }
                            //var skuValue = SKUObj.find("li").eq(index).html();
                        });
                        //开始创建Table表
                        if (bCheck == true) {
                            var RowsCount = 0;
                            $("#createTable").html("");
                            var table = $("<table id=\"process\" border=\"1\" cellpadding=\"1\" cellspacing=\"0\" style=\"width:100%;padding:5px;border:1px solid #ccc;\"></table>");
                            table.appendTo($("#createTable"));
                            var thead = $("<thead></thead>");
                            thead.appendTo(table);
                            var trHead = $("<tr></tr>");
                            trHead.appendTo(thead);
                            //创建表头
                            $.each(arrayTile, function (index, item) {
                                var td = $("<th>" + item + "</th>");
                                td.appendTo(trHead);
                            });
                            var itemColumHead = $("<th style=\"width:80px;\">规格图片(320*160)<span class=\"maroon\">*</span></th><th style=\"width:150px;\">价格<span class=\"maroon\">*</span></th><th style=\"width:150px;\">库存<span class=\"maroon\">*</span></th><!--<th style=\"width:150px;\">编码</th>--><th style=\"width:150px;\">用餐人数<span class=\"maroon\">*</span></th><th style=\"width:150px;\">餐具数<span class=\"maroon\">*</span></th> <th style=\"width:150px;\">尺寸<span class=\"maroon\">*</span><br>（cm*cm）</th> <th style=\"width:150px;\">积分最大抵扣额<span class=\"maroon\">*</span></th><th style=\"width:50px;\">操作</th>");
                            itemColumHead.appendTo(trHead);
                            //var itemColumHead2 = $("<td >商家编码</td><td >商品条形码</td>");
                            //itemColumHead2.appendTo(trHead);
                            var tbody = $("<tbody></tbody>");
                            tbody.appendTo(table);
                            ////生成组合
                            var zuheDate = step.doExchange(arrayInfor);
                            var specZuheDate = step.doExchange(arraySpecId);
                            if (zuheDate.length > 0) {
                                //创建行
                                $.each(zuheDate, function (index, item) {
                                    var td_array = zuheDate[index].split(",");
                                    var specValues = specZuheDate[index].split(",");
                                    var spec_value_array = zuheDate[index];
                                    var spec_array = specZuheDate[index];
                                    var tr = $("<tr class='tr'></tr>");
                                    tr.appendTo(tbody);
                                    $.each(td_array, function (i, values) {
                                        var td = $("<td id='specValue"+(specValues[i])+"'>" + values + "</td>");
                                        td.appendTo(tr);
                                    });
                                    var td0 = $('<td name="specImg"><input id="specInput'+ index +'" type="hidden" value=""><label for="spec-file'+ index +'"><img id="specImg'+ index +'" style="width: 60px;height: 60px;" src="${respath}/admin/images/add-icon.png"></label></td>');
                                    td0.appendTo(tr);
                                    var td1 = $("<td name='price'><input name=\"price11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td1.appendTo(tr);
                                    var td2 = $("<td name='stock'><input name=\"stock11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td2.appendTo(tr);
                                    /*var td3 = $("<td name='sn'><input name=\"sn11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td3.appendTo(tr);*/
                                    var td4 = $("<td name='people'><input name=\"people11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td4.appendTo(tr);
                                    var td5 = $("<td name='tableware'><input name=\"tableware11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td5.appendTo(tr);
                                    var td6 = $("<td name='size'><input name=\"size11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td6.appendTo(tr);
                                    var td7 = $("<td name='maxPoint'><input name=\"maxPoint11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td7.appendTo(tr);
                                    var td8= $("<td name='spec_ids' style=\"display: none\"><input name=\"spec11\" class=\"l-text form-control\" type=\"text\" value=\""+ spec_array +"\"></td>");
                                    td8.appendTo(tr);
                                    var td9= $("<td name='spec_values' style=\"display: none\"><input name=\"spec11\" class=\"l-text form-control\" type=\"text\" value=\""+ spec_value_array +"\"></td>");
                                    td9.appendTo(tr);

                                    var td_op= $("<td name='isDisabled'><input name='isDisabled11' type='hidden' value='0' /> <i onclick='batchSet(this,\""+specZuheDate[index]+"\")' data-toggle='modal' data-target='#batchSet' title='批量设置' style='cursor: pointer' class='icon iconfont'>&#xe64b;</i> <i onclick='setDisabled(this)' title='禁用' style='cursor: pointer' class='icon iconfont'>&#xe693;</i></td>");
                                    td_op.appendTo(tr);

                                    var form = "<form id='imgForm"+index+"'><input name='file' type='file' id='spec-file"+index+"' onchange=\"uploadSpecImg('#specInput"+index+"','#specImg"+index+"','#imgForm"+index+"')\" style='display: none;'></form>";
                                    $("#forms").append(form);
                                });
                            }
                            //结束创建Table表
                            arrayColumn.pop();//删除数组中最后一项
                            //合并单元格
                            $(table).mergeCell({
                                // 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
                                cols: arrayColumn
                            });
                        } else{
                            //未全选中,清除表格
                            document.getElementById('createTable').innerHTML="";
                        }
                    },//合并行
                    hebingFunction: function () {
                        $.fn.mergeCell = function (options) {
                            return this.each(function () {
                                var cols = options.cols;
                                for (var i = cols.length - 1; cols[i] != undefined; i--) {
                                    // fixbug console调试
                                    // console.debug(cols[i]);
                                    mergeCell($(this), cols[i]);
                                }
                                dispose($(this));
                            });
                        };
                        function mergeCell($table, colIndex) {
                            $table.data('col-content', ''); // 存放单元格内容
                            $table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
                            $table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的), 默认一个"空"的jquery对象
                            $table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数, 用于最后一行做特殊处理时进行判断之用
                            // 进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
                            $('tbody tr', $table).each(function (index) {
                                // td:eq中的colIndex即列索引
                                var $td = $('td:eq(' + colIndex + ')', this);
                                // 取出单元格的当前内容
                                var currentContent = $td.html();
                                // 第一次时走此分支
                                if ($table.data('col-content') == '') {
                                    $table.data('col-content', currentContent);
                                    $table.data('col-td', $td);
                                } else {
                                    // 上一行与当前行内容相同
                                    if ($table.data('col-content') == currentContent) {
                                        // 上一行与当前行内容相同则col-rowspan累加, 保存新值
                                        var rowspan = $table.data('col-rowspan') + 1;
                                        $table.data('col-rowspan', rowspan);
                                        // 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
                                        $td.hide();
                                        // 最后一行的情况比较特殊一点
                                        // 比如最后2行 td中的内容是一样的, 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
                                        if (++index == $table.data('trNum'))
                                            $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                                    } else { // 上一行与当前行内容不同
                                        // col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
                                        if ($table.data('col-rowspan') != 1) {
                                            $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                                        }
                                        // 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
                                        $table.data('col-td', $td);
                                        $table.data('col-content', $td.html());
                                        $table.data('col-rowspan', 1);
                                    }
                                }
                            });
                        }
                        // 同样是个private函数 清理内存之用
                        function dispose($table) {
                            $table.removeData();
                        }
                    },
                    //组合数组
                    doExchange: function (doubleArrays) {
                        var len = doubleArrays.length;
                        if (len >= 2) {
                            var arr1 = doubleArrays[0];
                            var arr2 = doubleArrays[1];
                            var len1 = doubleArrays[0].length;
                            var len2 = doubleArrays[1].length;
                            var newlen = len1 * len2;
                            var temp = new Array(newlen);
                            var index = 0;
                            for (var i = 0; i < len1; i++) {
                                for (var j = 0; j < len2; j++) {
                                    temp[index] = arr1[i] + "," + arr2[j];
                                    index++;
                                }
                            }
                            var newArray = new Array(len - 1);
                            newArray[0] = temp;
                            if (len > 2) {
                                var _count = 1;
                                for (var i = 2; i < len; i++) {
                                    newArray[_count] = doubleArrays[i];
                                    _count++;
                                }
                            }
                            //console.log(newArray);
                            return step.doExchange(newArray);
                        }
                        else {
                            return doubleArrays[0];
                        }
                    }
                }
                return step;
            })

            laydate.render({
                elem: '#sellDate'
                ,type: 'datetime'
            });

            var price,stock,shareNum,tableware,size,maxOffsetPoint;
            var priceCheck,stockCheck,shareNumCheck,tablewareCheck,sizeCheck,maxOffsetPointCheck;
            var specValueArray = "";
            function batchSet(dom,specValueArray){
                specValueArray = specValueArray.split(",");
                var $tr = $(dom).parent().parent();

                price = $tr.find("input[name^='price']").val();
                stock = $tr.find("input[name^='stock']").val();
                shareNum = $tr.find("input[name^='people']").val();
                tableware = $tr.find("input[name^='tableware']").val();
                size = $tr.find("input[name^='size']").val();
                maxOffsetPoint = $tr.find("input[name^='maxPoint']").val();

                $("#priceBatch").html("");
                $("#stockBatch").html("");
                $("#shareNumBatch").html("");
                $("#tablewareBatch").html("");
                $("#sizeBatch").html("");
                $("#maxOffsetPointBatch").html("");


                var html = "";
                var $item = $("ul[class^='Father_Item']");
                var index = 0;
                $item.each(function(i,n){
                    var len = $(this).find("input:checked").length;
                    if(len>0){
                        var specName = $(this).prev("ul").find("li").html();
                        var specNameId = $(this).prev("ul").find("li").attr("specNameId");
                        html+=
                            '<label class="checkbox-inline" >'+
                            '    <input specValueId="'+specValueArray[index++]+'" specNameId="'+specNameId+'" name="" type="checkbox" value="">同'+specName+
                            '相同</label>';
                    }
                });

                $("#priceBatch").html(html);
                $("#stockBatch").html(html);
                $("#shareNumBatch").html(html);
                $("#tablewareBatch").html(html);
                $("#sizeBatch").html(html);
                $("#maxOffsetPointBatch").html(html);

                if(!price){
                    $("#priceBatch label").css("color","#999").css("cursor","not-allowed");
                    $("#priceBatch label input").prop("disabled",true);
                }

                if(!stock){
                    $("#stockBatch label ").css("color","#999").css("cursor","not-allowed");
                    $("#stockBatch label input").prop("disabled",true);
                }

                if(!shareNum){
                    $("#shareNumBatch label ").css("color","#999").css("cursor","not-allowed");
                    $("#shareNumBatch label input").prop("disabled",true);
                }

                if(!tableware){
                    $("#tablewareBatch label ").css("color","#999").css("cursor","not-allowed");
                    $("#tablewareBatch label input").prop("disabled",true);
                }

                if(!size){
                    $("#sizeBatch label ").css("color","#999").css("cursor","not-allowed");
                    $("#sizeBatch label input").prop("disabled",true);
                }

                if(!maxOffsetPoint){
                    $("#maxOffsetPointBatch label ").css("color","#999").css("cursor","not-allowed");
                    $("#maxOffsetPointBatch label input").prop("disabled",true);
                }
            }

            function isUndefined(val){
                if(val==undefined||val==""){
                    return true;
                }
                return false;
            }

            $("#singleBatchConfirm").click(function(){

                {//价格

                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#priceBatch label input:checked");
                    if($inputs.length>0){
						$inputs.each(function (i, n) {
							var specValueId = $(this).attr("specValueId");
							specValues.push(specValueId);
						});
						$("#process tr.tr").each(function () {
							var flag = true;
							var $this = $(this);
							$.each(specValues, function (i, n) {
								if ($this.find("td[id='specValue" + n + "']").length==0) {
									flag = false;
								}
							});
							if (flag) {
								if (!isUndefined(price)) {
									$this.find("td[name='price'] input").val(price);
								}
							}
						});
                    }
                }

                {
                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#stockBatch label input:checked");
                    $inputs.each(function(i,n){
                        var specValueId = $(this).attr("specValueId");
                        specValues.push(specValueId);
                    });
                    if($inputs.length>0) {
                        $("#process tr.tr").each(function () {
                            var flag = true;
                            var $this = $(this);
                            $.each(specValues, function (i, n) {
                                if ($this.find("td[id='specValue" + n + "']").length == 0) {
                                    flag = false;
                                }
                            });
                            if (flag) {
                                if (!isUndefined(stock)) {
                                    $this.find("td[name='stock'] input").val(stock);
                                }
                            }
                        });
                    }
                }


                {
                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#shareNumBatch label input:checked");
                    $inputs.each(function(i,n){
                        var specValueId = $(this).attr("specValueId");
                        specValues.push(specValueId);
                    });
                    if($inputs.length>0) {
                        $("#process tr.tr").each(function () {
                            var flag = true;
                            var $this = $(this);
                            $.each(specValues, function (i, n) {
                                if ($this.find("td[id='specValue" + n + "']").length == 0) {
                                    flag = false;
                                }
                            });
                            if (flag) {
                                if (!isUndefined(shareNum)) {
                                    $this.find("td[name='people'] input").val(shareNum);
                                }
                            }
                        });
                    }
                }


                {
                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#tablewareBatch label input:checked");
                    $inputs.each(function(i,n){
                        var specValueId = $(this).attr("specValueId");
                        specValues.push(specValueId);
                    });
                    if($inputs.length>0) {
                        $("#process tr.tr").each(function () {
                            var flag = true;
                            var $this = $(this);
                            $.each(specValues, function (i, n) {
                                if ($this.find("td[id='specValue" + n + "']").length == 0) {
                                    flag = false;
                                }
                            });
                            if (flag) {
                                if (!isUndefined(tableware)) {
                                    $this.find("td[name='tableware'] input").val(tableware);
                                }
                            }
                        });
                    }
                }


                {
                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#sizeBatch label input:checked");
                    $inputs.each(function(i,n){
                        var specValueId = $(this).attr("specValueId");
                        specValues.push(specValueId);
                    });
                    if($inputs.length>0) {
                        $("#process tr.tr").each(function () {
                            var flag = true;
                            var $this = $(this);
                            $.each(specValues, function (i, n) {
                                if ($this.find("td[id='specValue" + n + "']").length == 0) {
                                    flag = false;
                                }
                            });
                            if (flag) {
                                if (!isUndefined(size)) {
                                    $this.find("td[name='size'] input").val(size);
                                }
                            }
                        });
                    }
                }


                {
                    var flag = true;
                    var specValues = [];
                    var $inputs = $("#maxOffsetPointBatch label input:checked");
                    $inputs.each(function(i,n){
                        var specValueId = $(this).attr("specValueId");
                        specValues.push(specValueId);
                    });
                    if($inputs.length>0) {
                        $("#process tr.tr").each(function () {
                            var flag = true;
                            var $this = $(this);
                            $.each(specValues, function (i, n) {
                                if ($this.find("td[id='specValue" + n + "']").length == 0) {
                                    flag = false;
                                }
                            });
                            if (flag) {
                                if (!isUndefined(maxOffsetPoint)) {
                                    $this.find("td[name='maxPoint'] input").val(maxOffsetPoint);
                                }
                            }
                        });
                    }
                }
            });

		</script>
	</body>






</html>
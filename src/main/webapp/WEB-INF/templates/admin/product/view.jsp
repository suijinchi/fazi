<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>商品管理-查看</title>
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
		<script src="${respath}/admin/js/plugins/validate/jquery.validate.min.js?v"></script>
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
		<style type="text/css">
			.product-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">

			<div class="row">
				<div class="col-sm-12">
					<a href="${base}/admin/product/list" class="m-b-md">
						<button class="btn btn-primary"><i class="icon iconfont">&#xe605;</i>返回商品列表</button>
					</a>
					<div class="ibox float-e-margins">
						<div class="ibox-title">
							<h5>商品详情<small class="m-l-sm"></small></h5>
						</div>
						<div class="ibox-content">						 	
							<form action="save" class="form-horizontal" id="dataForm" name="dataForm" method="post" onsubmit="return false">
								<div class="tabs-container">
									<ul class="nav nav-tabs">
										<li class="active"><a data-toggle="tab" href="product-add.html#tab-4"><i class="icon iconfont">&#xe69c;</i>基本信息</a></li>
										<li class=""><a data-toggle="tab" href="product-add.html#tab-5"><i class="icon iconfont">&#xe658;</i>商品介绍</a></li>
										<li class=""><a data-toggle="tab" href="product-add.html#tab-6"><i class="icon iconfont">&#xe69e;</i>商品方位图</a></li>
										<%--<li class=""><a data-toggle="tab" href="product-add.html#tab-7"><i class="icon iconfont">&#xe6b5;</i>商品评价</a></li>--%>
									</ul>
									<div class="tab-content" style="margin-top: 20px">
										<div id="tab-4" class="tab-pane active">
											<div class="form-group">
												<label class="col-sm-2 control-label">商品名称：</label>
												<div class="col-sm-9">
													<input id="name" name="name" value="${product.name }" class="form-control" type="text" readonly="readonly">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">简称：</label>
												<div class="col-sm-9">
													<input id="subName" name="subName" value="${product.subname }" class="form-control" type="text" readonly="readonly">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">商品分类：</label>
												<div class="col-sm-9">
													<input id="productCategoryName" name="productCategoryIdName" value="${productCategory.name }" class="form-control" type="text" readonly="readonly">
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">展示图：</label>
												<div class="col-sm-9">
													<img style="margin-right:5px" src="${product.showUrl }" width="100px"/>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">长方形展示图：</label>
												<div class="col-sm-9">
													<img style="margin-right:5px" src="${product.longShowUrl }" width="100px"/>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">简介：</label>
												<div class="col-sm-9">
													<input id="memo" name="memo" value="${product.memo} " class="form-control" type="text" readonly="readonly">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">排序：</label>
												<div class="col-sm-9">
													<input id="orders" name="orders" value="${product.orders }" class="form-control" type="text" readonly="readonly">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">备注：</label>
												<div class="col-sm-9">
													<input id="introduce" name="introduce" value="${product.introduce }" class="form-control" type="text" readonly="readonly">
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">标签：</label>
												<div class="col-sm-9">
													<div class="checkbox i-checks">
														<c:forEach items="${tagList}" var="tag">
															<label><input disabled="disabled" type="checkbox" <c:forEach items="${productTagList }" var="productTag"><c:if test="${productTag.tagId==tag.id }">checked="checked"</c:if></c:forEach> value="${tag.id}" name="tagIds">&nbsp;${tag.name}</label>
														</c:forEach>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">成分：</label>
												<div class="col-sm-8">
													<div class="checkbox i-checks">
														<c:forEach items="${componentList}" var="component">
															<label><input disabled="disabled" type="checkbox" <c:forEach items="${productComponentList }" var="productComponent"><c:if test="${productComponent.componentId==component.id }">checked="checked"</c:if></c:forEach> value="${component.id}" name="componentIds">&nbsp;${component.name}</label>
														</c:forEach>
													</div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">设置：</label>
												<div class="col-sm-9">
													<div class="checkbox i-checks">
														<label>
															<input type="hidden" id="isMarketable" name="isMarketable" value="0">
															<input disabled="disabled" id="isMarketableCheck" <c:if test="${product.isMarketable==1 }">checked="checked"</c:if> value="1" type="checkbox"> <i></i> 上架</label>
														<label>
															<input type="hidden" id="isTop" name="isTop" value="0">
															<input disabled="disabled" id="isTopCheck" <c:if test="${product.isTop==1 }">checked="checked"</c:if> value="1" type="checkbox"> <i></i> 置顶</label>
													</div>
												</div>
											</div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">参考甜度：</label>
                                                <div class="col-sm-8">
                                                    <input id="sweetness" name="sweetness" disabled value="${product.sweetness}" class="form-control" type="text">
                                                </div>
                                            </div>

											<div class="form-group">
												<label class="col-sm-2 control-label">是否有生日牌：</label>
												<div class="col-sm-8">
													<div class="checkbox i-checks">
														<label>
															<input disabled id="isBirthdayCard1" <c:if test="${product.isBirthdayCard==1}"> checked </c:if> value="1" name="isBirthdayCard" type="radio"> <i></i> 是
														</label>
														<label>
															<input disabled id="isBirthdayCard0" <c:if test="${product.isBirthdayCard==0}"> checked </c:if> value="0" name="isBirthdayCard" type="radio"> <i></i> 否
														</label>
													</div>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">商品规格：</label>
												<div class="col-sm-9">
													<div class="checkbox i-checks" id="guigeBox">
														<label id="sin">
															<input disabled="disabled" type="radio" value="1" name="optionsRadios" <c:if test="${product.spec == 0}">checked="checked"</c:if> onchange="testRadios()">
															<i></i> 统一规格
														</label>
														<label id="mul">
															<input disabled="disabled" type="radio" value="2" name="optionsRadios" <c:if test="${product.spec == 1}">checked="checked"</c:if> onchange="testRadios()">
															<i></i> 多规格
														</label>
													</div>
													<div style="height: 20px">

													</div>
													<div class="clear">

													</div>
													<!--规格-->
													<c:if test="${product.spec == 1}">
														<div class="box-sep" id="box-sep">
															<div class="panel panel-primary">
																<div class="panel-body">
																	<div id="navtab1" style="width: 100%;">
																		<div tabid="tabItem3">
																			<div id="Div1">
																				<div position="center">
																					<div style="padding: 5px 8px;" class="div_content">
																						<!--<div class="div_title"><span>产品规格</span></div>-->
																						<div class="div_contentlist">
																							<c:forEach var="specName" items="${specNameList}" varStatus="status">
																								<ul class="Father_Title js_show_div">
																									<li class="fl">${specName.name}</li>
																									<div class="clear">

																									</div>
																								</ul>
																								<ul class="Father_Item${status.index}">
																									<c:forEach var="specValue" items="${specValueList}">
																										<c:if test="${specValue.specNameId == specName.id}">
																											<li class="li_width">
																												<label>
																													<input id="${specValue.id}" disabled="disabled" type="checkbox" <c:forEach items="${productStockSpecNameValueList}" var="data"><c:if test="${data.specValueId == specValue.id}">checked="checked"</c:if></c:forEach> class="chcBox_Width" value="${specValue.value}" />${specValue.value}<span class="li_empty" contentEditable="true"></span></label>
																											</li>
																										</c:if>
																									</c:forEach>
																									<div class="clear">

																									</div>
																								</ul>
																								<br/>
																							</c:forEach>
																						</div>
																						<div class="div_contentlist2">
																							<ul>
																								<li>
																									<div id="createTable" style="width: 100%;overflow: auto;"></div>
																								</li>
																							</ul>
																						</div>
																					</div>
																				</div>
																			</div>
																		</div>
																	</div>

																</div>
															</div>
														</div>
													</c:if>
												</div>
											</div>

											<c:if test="${product.spec == 0}">
												
												<div class="form-group single" style="display: block">
													<label class="col-sm-2 control-label">售价：</label>
													<div class="col-sm-8">
														<input id="price" name="price" class="form-control" value="${productStock.price }" type="text" readonly="readonly">
													</div>
												</div>

												<div class="form-group single" style="display: block">
													<label class="col-sm-2 control-label">库存：</label>
													<div class="col-sm-8">
														<input id="stock" name="stock" class="form-control" value="${productStock.stock }" type="text" readonly="readonly">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label">销量：</label>
													<div class="col-sm-9">
														<input id="soldOut" name="soldOut" value="${productStock.soldOut }" class="form-control" type="text" readonly="readonly">
													</div>
												</div>

											</c:if>

										</div>
										<div id="tab-5" class="tab-pane">
											<div>
												<input type="hidden" id="content" name="content" value="" style="display:none" />
												<textarea id="editor" style="width:1500px;height:300px;">${product.content }</textarea>
											</div>											
										</div>
										<div id="tab-6" class="tab-pane">											
											<table class="table table-striped table-hover table-bordered table-condensed">
												<thead>
													<tr class="text-c">
														<th>文件</th>
														<th>排序</th>
													</tr>
												</thead>
												<tbody id="tt">
												<c:forEach items="${productImageList}" var="data" varStatus="status" >
													<tr>                                                                                                      									
														<td>																																						
															<div class='row'>																													
																<div class='col-sm-6' id='productImageDiv'>																						
																	<input id='productImages' name='productImages' class='form-control' type='text' readonly='readonly' width='200px' value='${data.imgUrl}'>	
																</div>																														
																<div class='col-sm-6'>																										
																	<img style="margin-right:5px" src="${data.imgUrl }" width="100px"/>				
																</div>																														
															</div>																															
														</td>																																
														<td>																																
															<input id='imgOrders' name='imgOrders' class='form-control' type='text' readonly='readonly' value='${data.orders}'>													
														</td>																																																															
													</tr>	
												</c:forEach>															
												</tbody>
											</table>										
										</div>

										<%--<div id="tab-7" class="tab-pane">
											<div class="panel-body">
												<div class="ibox-content">
													<table class="table table-bordered">
														<thead>
															<tr>
																<th>序号</th>
																<th>评价人昵称</th>
																<th>评价时间</th>
																<th>评价内容</th>
																<th>评价图片</th>
																<th>操作</th>
															</tr>
														</thead>
														<tbody>
															<c:if test="${assessList!=null}">
																<c:forEach items="${assessList}" var="data" varStatus="status" >
																	<tr>
																		<td>${status.index+1}</td>
																		<td>${data.assess.nickname}</td>
																		<td><fmt:formatDate value="${data.assess.createDate}" pattern="yy-MM-dd HH:mm:ss"/></td>
																		<td>${data.assess.content}</td>
																		<td>
																			<c:forEach items="${data.imgList}" var="img">
																				<img src="${img}" style="width:50px;height:50px">
																			</c:forEach>
																		</td>
																		<td>
																			<a onclick="deleteAssess(this,${data.assess.id})" class="btn btn-white btn-sm"><i class="icon iconfont">&#xe634;</i> 删除 </a>
																		</td>
																	</tr>
																</c:forEach>
															</c:if>												
														</tbody>
													</table>
												</div>
											</div>										
										</div>--%>

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script>
		
			//时间范围
			$(function() {
				
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
			
			function deleteAssess(obj, id) {
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						url: '${base}/admin/assess/delete',
						data:{"id":id},
						type: 'POST',
						dataType: 'json',
						success: function(data) {
							$(obj).parents("tr").remove();
							layer.msg('已删除!', {
								icon: 1,
								time: 1000
							});
						},
						error: function(data) {
							console.log(data.msg);
						}
					});
				});
			}

            function show() {
                var products = ${productJson};
                var $tr = $('.tr');
                $.each($tr, function (i, n) {
                    var spec_values = $(n).children('td[name=spec_values]').children('input').val();
                    $.each(products, function (i, j) {
                        if (j.specName === spec_values) {
                            $(n).children('td[name=specImg]').children('input').val(j.imgUrl).attr("readonly", "readonly");
                            $(n).children('td[name=specImg]').children('label').children('img').attr('src', j.imgUrl);
                            $(n).children('td[name=price]').children('input').val(j.price).attr("readonly", "readonly");
                            $(n).children('td[name=stock]').children('input').val(j.stock).attr("readonly", "readonly");
                            $(n).children('td[name=people]').children('input').val(j.shareNum).attr("readonly", "readonly");
                            $(n).children('td[name=tableware]').children('input').val(j.tableware).attr("readonly", "readonly");
                            $(n).children('td[name=size]').children('input').val(j.size).attr("readonly", "readonly");
                            $(n).children('td[name=maxPoint]').children('input').val(j.maxOffsetPoint).attr("readonly", "readonly");
                            $(n).children('td[name=soldOut]').children('input').val(j.soldOut).attr("readonly", "readonly");
                        }

                    });
                });
            }

            $(function () {

                //SKU信息
                $(".div_contentlist label").bind("change", function () {
                    step.Creat_Table();
                    show();
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
                            var itemColumHead = $("<th style=\"width:80px;\">规格图片</th><th style=\"width:150px;\">价格</th><th style=\"width:150px;\">库存</th><th style=\"width:150px;\">销量</th><!--<th style=\"width:150px;\">编码</th>--><th style=\"width:150px;\">用餐人数</th><th style=\"width:150px;\">餐具数</th> <th style=\"width:150px;\">尺寸<br>（cm*cm）</th> <th style=\"width:150px;\">积分最大抵扣额</th>");
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
                                    var spec_value_array = zuheDate[index];
                                    var spec_array = specZuheDate[index];
                                    var tr = $("<tr class='tr'></tr>");
                                    tr.appendTo(tbody);
                                    $.each(td_array, function (i, values) {
                                        var td = $("<td>" + values + "</td>");
                                        td.appendTo(tr);
                                    });
                                    var td0 = $('<td name="specImg"><input id="specInput'+ index +'" type="hidden" value=""><label for="spec-file'+ index +'"><img id="specImg'+ index +'" style="width: 60px;height: 60px;" src="${respath}/admin/images/add-icon.png"></label></td>');
                                    td0.appendTo(tr);
                                    var td1 = $("<td name='price'><input name=\"price11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td1.appendTo(tr);
                                    var td2 = $("<td name='stock'><input name=\"stock11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td2.appendTo(tr);
                                    var td3 = $("<td name='soldOut'><input name=\"stock11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td3.appendTo(tr);
                                    /*var td3 = $("<td name='sn'><input name=\"sn11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td3.appendTo(tr);*/
                                    var td4 = $("<td name='people'><input name=\"people11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td4.appendTo(tr);
                                    var td5 = $("<td name='tableware'><input name=\"tableware11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td5.appendTo(tr);
                                    var td6 = $("<td name='size'><input name=\"size11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td6.appendTo(tr);
                                    var td7 = $("<td name='maxPoint'><input name=\"size11\" class=\"l-text form-control\" type=\"text\" value=\"\"></td>");
                                    td7.appendTo(tr);
                                    var td8= $("<td name='spec_ids' style=\"display: none\"><input name=\"spec11\" class=\"l-text form-control\" type=\"text\" value=\""+ spec_array +"\"></td>");
                                    td8.appendTo(tr);
                                    var td9= $("<td name='spec_values' style=\"display: none\"><input name=\"spec11\" class=\"l-text form-control\" type=\"text\" value=\""+ spec_value_array +"\"></td>");
                                    td9.appendTo(tr);

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
                };

                step.Creat_Table();

                show();

            })

		</script>
	</body>

</html>
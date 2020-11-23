<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>平台商品</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="shortcut icon" href="favicon.ico">
		<link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
		<link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
		<link href="${respath}/admin/css/style.min862f.css" rel="stylesheet">
		<link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
		<link href="${respath}/admin/lib/icheck/icheck.css" rel="stylesheet">
		<link href="${respath}/admin/css/public.css" rel="stylesheet">
		<script src="${respath}/admin/js/jquery.min.js"></script>
		<script src="${respath}/admin/js/bootstrap.min.js"></script>
		<script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
		<script src="${respath}/admin/lib/laydate/laydate.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/webuploader/0.1.5/webuploader.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.config.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="${respath}/admin/lib/My97DatePicker/4.8/WdatePicker.js"></script>
		<script type="text/javascript" src="${respath}/admin/js/clipboard.min.js"></script>
		<%--<style type="text/css">
			.product-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>--%>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" action="${base}/admin/product/list" method="get">
				<div class="row">
					<div class="ibox">
						<div class="ibox-content">

							<span class="text-muted small pull-right">共有数据： ${page.total} 条</span>
						
								<div class="form-group">
									<input type="text" id="startTime" class="form-control" name="startTime" value="${startTime}" placeholder="请输入开始日期">
								</div>

								<div class="form-group">
									<input type="text" placeholder="请输入结束日期" id="endTime" name="endTime" value="${endTime}" class="form-control">
								</div>

								<div class="form-group">
									<input type="text" id="name" class="form-control" name="name" value="${name}" placeholder="请输入商品名称">
								</div>

								<div class="form-group">
									<select class="form-control" name="productCategoryId" size="1">
										<option value="">商品分类选择</option>
										<c:forEach items="${productCategoryList}" var="productCategory">
											<option <c:if test="${productCategoryId==productCategory.id}">selected="selected"</c:if> value="${productCategory.id}">${productCategory.name}</option>
										</c:forEach>
									</select>
								</div>

								<div class="form-group">
									<select class="form-control" name="productCategoryId" size="1">
										<option value="">请选择类型</option>
										<option <c:if test="${type==0}">selected="selected"</c:if> value="0">普通商品</option>
										<option <c:if test="${type==1}">selected="selected"</c:if> value="0">企业专区</option>
									</select>
								</div>

								<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
	
								<div class="form-inline m-t-md">
									<div class="form-group">
										<button type="button" class="btn btn-danger" onclick="product_del()"><i class="icon iconfont">&#xe606;</i>删除</button>
									</div>
									<div class="form-group">
										<button type="button" class="btn btn btn-primary" onclick="product_up()"> <i class="icon iconfont">&#xe650;</i> 上架</button>
									</div>
									<div class="form-group">
										<button type="button" class="btn btn btn-default" onclick="product_down()"> <i class="icon iconfont">&#xe651;</i> 下架</button>
									</div>
									<div class="form-group">
										<a href="${base}/admin/product/add">
											<button type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加商品</button>
										</a>
									</div>
								</div>
							
								<div class="product-list m-t-md">
									<table class="table table-striped table-hover table-bordered table-condensed">
										<thead>
											<tr class="text-c">
												<th style="width: 50px">
													<input type="checkbox" value="" name="" id="box">
												</th>
												<th>序号</th>
												<th>商品名称</th>
												<th>类型</th>
												<th>商品分类</th>
												<th>规格类型</th>

                                                <th>价格</th>
                                                <th>总库存</th>
                                                <th>总销量</th>

												<%--<th>价格</th>--%>
												<%--<th>会员价</th>--%>
												<%--<th>积分价</th>--%>
												<%--<th>库存</th>--%>
												<%--<th>销量</th>--%>
												<th>上架状态</th>
												<th>创建时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${page.content}" var="data" varStatus="status" >
											<tr>
												<td>
													<input type="checkbox" value="${data.id}" name="ids" class="boxs">
												</td>
												<td>${status.index+1}</td>
												<td>
													${data.name}
												</td>
												<td>
													<c:if test="${data.type==0}">普通商品</c:if>
													<c:if test="${data.type==1}">企业专区</c:if>
												</td>
												<td>
													${data.productCategoryName}
												</td>
												<td>
													<c:if test="${data.spec == 0}">统一规格</c:if>
													<c:if test="${data.spec == 1}">多规格</c:if>
												</td>

                                                <td>
                                                    <c:if test="${data.lowPrice!=data.highPrice}">
                                                        ￥${data.lowPrice}-￥${data.highPrice}
                                                    </c:if>
                                                    <c:if test="${data.lowPrice==data.highPrice}">
                                                        ￥${data.lowPrice}
                                                    </c:if>

													<a onclick="editPriceStock('${data.id}','${data.spec}')" style="float: right">
														<i class="icon iconfont">&#xe677;</i>
													</a>

                                                </td>
                                                <td>
                                                    ${data.sumStock}
													<a onclick="editPriceStock('${data.id}','${data.spec}')" style="float: right">
														<i class="icon iconfont">&#xe677;</i>
													</a>
                                                </td>
                                                <td>
                                                    ${data.sumSoldOut}
                                                </td>


												<%--<td>
													${data.price}
												</td>
												<td>
													${data.memberPrice}
												</td>
												<td>
													${data.point}
												</td>
												<td>
													${data.stock}
												</td>											
												<td>
													${data.soldOut}
												</td>--%>
												<c:if test="${data.isMarketable==1}">
												    <%--<td><span onclick="updateMarketable('${data.id}','${data.isMarketable}')" style="cursor: pointer" class="label label-primary">已上架</span></td>--%>
													<td>
														<i onclick="updateMarketable('${data.id}','${data.isMarketable}')" class="icon iconfont" style="color: #1ab394;cursor: pointer">&#xe694;</i>
													</td>
												</c:if>
												<c:if test="${data.isMarketable==0}">
												   <%--<td><span onclick="updateMarketable('${data.id}','${data.isMarketable}')" style="cursor: pointer" class="label label-default">已下架</span></td>--%>
													<td>
														<i onclick="updateMarketable('${data.id}','${data.isMarketable}')" class="icon iconfont" style="color: #ed5565;cursor: pointer">&#xe693;</i>
													</td>
												</c:if>
												<td>
													<fmt:formatDate value="${data.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>
													<a href="${base}/admin/product/view?id=${data.id}" class="btn btn-white btn-xs" title="查看"><i class="icon iconfont">&#xe633;</i></a>
													<a href="${base}/admin/product/edit?id=${data.id}" class="btn btn-white btn-xs" title="编辑"><i class="icon iconfont">&#xe677;</i></a>

													<a data-clipboard-target="#plink${data.lowPriceSkuId}" class="cpl btn btn-white btn-xs" title="复制链接"><i class="icon iconfont">&#xe617;</i></a>
													<input id="plink${data.lowPriceSkuId}" value="${setting.domain}/mobile/product/detail?skuId=${data.lowPriceSkuId}" type="text" style="position: absolute;top: -999px;left: -999px">

												</td>
											</tr>		
											</c:forEach>
											<script>
                                                var clipboard = new ClipboardJS('.cpl');
                                                clipboard.on('success', function(e) {
                                                    layer.msg("已复制到粘贴板");
                                                });
											</script>
										</tbody>
									</table>
								</div>
							<%@ include file="/WEB-INF/templates/admin/include/page.jsp" %>				
						</div>
					</div>
				</div>
			</form>
		</div>

		<script>
			//时间范围
			$(function() {

				$(".full-height-scroll").slimScroll({
					height: "100%"
				});

				laydate.render({
					elem: '#startTime' //指定元素
						,
					type: 'datetime'
				});

				laydate.render({
					elem: '#endTime' //指定元素
						,
					type: 'datetime'
				});

				$("#box").change(function() {
				    if ($("#box").is(":checked")) {
                        $(".boxs").prop("checked", true);
					} else {
                        $(".boxs").prop("checked", false);
					}
				});

			});

			function copyLink(skuId) {
			    var link = "${setting.domain}/mobile/product/detail?skuId="+skuId;

            }

			function updateMarketable(id,s){
			    if(s=='0'){
                    $.ajax({
                        type: 'POST',
                        url: '${base}/admin/product/productUp',
                        dataType: 'json',
                        data:{
                            ids:id
                        },
                        success: function(data) {
                            if(data.type==="success"){
                                layer.msg(data.content, {
                                    icon: 1,
                                    time: 2000
                                }, function(){
                                    location.reload();
                                });
                            }else{
                                layer.alert(data.content,function(){
                                    window.location.reload();
                                });
                            }
                        }
                    });
				}else{
                    $.ajax({
                        type: 'POST',
                        url: '${base}/admin/product/productDown',
                        dataType: 'json',
                        data:{
                            ids:id
                        },
                        success: function(data) {
                            if(data.type==="success"){
                                layer.msg(data.content, {
                                    icon: 1,
                                    time: 2000
                                }, function(){
                                    location.reload();
                                });
                            }else{
                                layer.alert(data.content,function(){
                                    window.location.reload();
                                });
                            }
                        }
                    });
				}
			}

			function editPriceStock(id,type){

			    var h = '';
			    var w = '1050';
			    if(type=='0'){
					h = 500;
					w = 770;
				}

                if (h == null || h == '') {
                    h=($(window).height() - 50);
                };
                var ly = layer.open({
                    type: 2,
                    area: [w+'px', h +'px'],
                    fix: false, //不固定
                    maxmin: true,
                    shade:0.4,
                    title: "修改价格和库存",
                    content: "${base}/admin/product/stock?id="+id
                });
                // layer.full(ly);
			}

			//批量上架
			function product_up() {
				var $ids = $("input[name='ids']:checked");
				if($ids.length===0){
					layer.msg("请选择商品");
					return;
				}
				layer.confirm('确认要上架吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/product/productUp',
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
								layer.alert(data.content,function(){
									window.location.reload();
								});
							}
						}
					});
				});
			}
			
			//批量下架
			function product_down() {
				var $ids = $("input[name='ids']:checked");
				if($ids.length===0){
					layer.msg("请选择商品");
					return;
				}
				layer.confirm('确认要下架吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/product/productDown',
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
								layer.alert(data.content,function(){
									window.location.reload();
								});
							}
						}
					});
				});
			}

			//批量删除
			function product_del() {
				var $ids = $("input[name='ids']:checked");
				if($ids.length===0){
					layer.msg("请选择商品");
					return;
				}
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						type: 'POST',
						url: '${base}/admin/product/delete',
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

		</script>
	</body>

</html>
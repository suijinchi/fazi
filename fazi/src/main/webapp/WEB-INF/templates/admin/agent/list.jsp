<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
	</head>
	
	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" action="${base}/admin/agent/list" method="get">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">

						<span class="text-muted small pull-right">共有数据：<i class="fa fa-clock-o"></i> ${page.total}条</span>
							<%--
							<div class="form-group">
								<input type="text" id="startDate" name="startDate" value="${startDate}" placeholder="开始日期" class="form-control" >
							</div>
							<div class="form-group">
								<input type="text" id="endDate" name="endDate" value="${endDate}" placeholder="结束日期" class="form-control">
							</div>
							<div class="form-group">
								<input type="text" id="keyword" class="form-control" name="keyword" value="${keyword}" placeholder="请输入搜索条件">
							</div>
							--%>
							<div class="form-group">
								<input type="text" id="name" class="form-control" name="name" value="${name}" placeholder="请输入分销商名称">
							</div>
							<div class="form-group">
								<input type="tel" id="adminMobile" class="form-control" name="adminMobile" value="${adminMobile}" placeholder="请输入分销商手机号">
							</div>

							<button type="submit" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>

						<div class="form-inline m-t-md">
							<button onclick="layer_show('添加','${base}/admin/agent/add','800','')" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe614;</i> 添加</button>
						</div>

						<div class="product-list m-t-md">
							<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>
												<div class="check-box">
													<input type="checkbox" id="checkbox">
													<label for="">&nbsp;</label>
												</div>
											</th>
											<th>序号</th>
											<th>创建时间</th>
											<th>分销商名称</th>
											<th>分销商负责人</th>
											<th>分销商手机号</th>
											<th>客户数</th>
											<th>成交金额（￥）</th>
											<th>充值二维码</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${page.content}" var="data" varStatus="status" >
											<tr class="text-c">
												<td><input type="checkbox" name="ids"></td>
												<td>${status.index+1}</td>
												<td><fmt:formatDate value="${data.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>${data.name}</td>
												<td>${data.adminName}</td>
												<td>${data.adminMobile}</td>
												<td>${data.memberCount}</td>
												<td>${data.memberAmount}</td>
												<td>
													<a onclick="openQrcode('${data.id}')"><i class="icon iconfont">&#xe680;</i></a>
												</td>
												<td>
													<button type="button" class="btn btn-primary" onclick="layer_show('编辑','${base}/admin/agent/edit?id=${data.id}','800','')"><i class="icon iconfont">&#xe683;</i> 编辑</button>
												</td>
											</tr>
						                </c:forEach>
									</tbody>
								</table>
							<%@include file="/WEB-INF/templates/admin/include/page.jsp"%>
						</div>

					</div>
				</div>
			</div>
			</form>
		</div>

		<div id="qrcode" style="display: none">
			<div style="text-align: center;margin: 10px"></div>
			<img src="" width="300" height="300">
			<p style="text-align: center;padding: 10px;width: 100%;
    height: auto;
    word-wrap:break-word;
    word-break:break-all;
    overflow: hidden;"></p>
		</div>

		<script>
			//时间范围
			$(function() {
				$(".full-height-scroll").slimScroll({
					height: "100%"
				})
				laydate.render({
					elem: '#startDate',
					type: 'datetime'
				});
				laydate.render({
					elem: '#endTime',
					type: 'datetime'
				});

                $("#checkbox").click(function(){
                    var checked = $(this).prop("checked");
                    $("input[name='ids']").prop("checked",checked);
                });

			});

            function downloadImage(src) {
                var $a = document.createElement('a');
                $a.setAttribute("href", src);
                $a.setAttribute("download", "");

                var evObj = document.createEvent('MouseEvents');
                evObj.initMouseEvent( 'click', true, true, window, 0, 0, 0, 0, 0, false, false, true, false, 0, null);
                $a.dispatchEvent(evObj);
            }

            function openQrcode(id){
                $("#qrcode img").attr("src","");
                $("#qrcode div").html("");
                $("#qrcode p").html("");
                $("#qrcode a").remove();
                $.ajax({
                    type:"post",
                    data:{
                        id:id
                    },
                    url:"${base}/admin/agent/recharge_qrcode",
                    dataType:"json",
                    success:function(data){
                        if(data.type=="success"){
                            $("#qrcode div").html("分销商名称："+data.data.name);
                            $("#qrcode p").html(data.data.link);
                            $("#qrcode img").attr("src",data.data.path);
                            $("#qrcode").append('<a style="text-align: center;width: 100%;display: inline-block" onclick="downloadImage(\''+data.data.path+'\')">点击下载二维码</a>');
                            layer.open({
                                type: 1,
                                title: false,
                                closeBtn: 0,
                                area: ['300px', ''],
                                shadeClose: true,
                                content: $("#qrcode").html()
                            });
                        }
                    }
                });
            }

			/*删除*/
			function del(obj, id) {
				layer.confirm('确认要删除吗？', function(index) {
					$.ajax({
						url: 'delete',
						data:{"ids":id},
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
							
						},
					});
				});
			}
		</script>
	</body>

</html>
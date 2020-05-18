<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<script src="${respath}/admin/js/valid.js?v=${ver}"></script>

		<script src="${respath}/admin/jquery.ui/jquery-ui.min.js?v=${ver}"></script>
		<link href="${respath}/admin/jquery.ui/jquery-ui.min.css?v=${ver}" rel="stylesheet">

		<title>会员管理</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<style type="text/css">
			.member-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
		</style>
	</head>

	<body class="gray-bg">
		<div class="wrapper wrapper-content  animated fadeInRight">
			<form role="form" class="form-inline" method="post" action="${base}/admin/member/list">
			<div class="row">
				<div class="ibox">
					<div class="ibox-content">
						<div>
							<span class="text-muted small pull-right">共有数据：${page.total} 条</span>
							<div class="form-group">
								<input type="text" id="startTime" class="form-control" name="begRq" value="${begRq}" placeholder="请输入开始日期">
							</div>
							<div class="form-group">
								<input type="text" placeholder="请输入结束日期" id="endTime" name="endRq" value="${endRq}" class="form-control">
							</div>

							<div class="form-group">
								<input type="text" id="nickname" class="form-control" name="nickname" value="" placeholder="请输入会员昵称">
							</div>

							<div class="form-group">
								<input type="text" id="mobile" class="form-control" name="mobile" value="${mobile}" placeholder="请输入会员手机号">
							</div>

						</div>

						<div style="margin-top: 10px">

							生日范围：
							<div class="form-group">
								<select class="form-control" name="monthStart" id="monthStart"></select>
								<select class="form-control" name="dayStart" id="dayStart"></select>
								-
								<select class="form-control" name="monthEnd" id="monthEnd"></select>
								<select class="form-control" name="dayEnd" id="dayEnd"></select>
							</div>

						</div>
						<div style="margin-top: 10px">

							历史充值金额：
							<div class="form-group">
								<input type="number" width="80px" id="historyBalanceLow" class="form-control" name="historyBalanceLow" value="${historyBalanceLow}" placeholder="请输入最小金额">
								-
								<input type="number" width="80px" id="historyBalanceHigh" class="form-control" name="historyBalanceHigh" value="${historyBalanceHigh}" placeholder="请输入最大金额">
							</div>

						<div style="margin-top: 10px">

							<div class="form-group">
								<input type="hidden" id="productId" class="form-control" name="productId" value="${product.id}">
								<input type="text" id="productName" class="form-control" name="productName" value="${product.name}" placeholder="请输入商品名称">
							</div>

							<div class="form-group">
								<select class="form-control" name="memberRankId" id="memberRankId">
									<option value="">会员身份选择</option>
									<c:forEach items="${memberRankList}" var="mr">
										<option value="${mr.id}" <c:if test="${mr.id == memberRankId}"> selected </c:if> >${mr.name}</option>
									</c:forEach>
								</select>
							</div>

							<div class="form-group">
								<select class="form-control" name="status" id="status">
									<option selected="selected" value="">会员状态选择</option>
									<option value="0">正常</option>
									<option value="1">冻结</option>
								</select>
							</div>

							<button type="button" class="btn btn btn-primary" onclick="f_query();"> <i class="icon iconfont">&#xe607;</i> 搜索</button>
							<button type="button" class="btn btn btn-primary" onclick="f_exportXls();"> <i class="icon iconfont">&#xe616;</i> 导出</button>

                            <c:forEach items="${sysMenuList}" var="sysMenu">
                            <c:if test="${sysMenu.perms=='admin:member:select_coupon'}">
                                <button type="button" class="btn btn btn-primary" onclick="sendCoupon();"> <i class="icon iconfont">&#xe607;</i> 发放优惠券</button>
                            </c:if>
                            </c:forEach>

						</div>

						<div class="clients-list">
							<div class="member-list table-responsive">
								<table class="table table-striped table-hover table-bordered table-condensed">
									<thead>
										<tr class="text-c">
											<th>
												<div class="check-box">
													<input type="checkbox" id="checkbox">
													<label>&nbsp;</label>
												</div>
											</th>
											<th>ID</th>
											<th>昵称</th>
											<th>性别</th>
											<th>头像</th>
											<th>身份</th>
											<th>积分</th>
											<th>余额(元)</th>
											<th>历史充值(元)</th>
											<th>姓名</th>
											<th>手机号</th>
											<th>生日</th>
											<th>创建时间</th>
											<th>推荐人</th>
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
											<td>
												${data.id}
											</td>
											<td title="${data.nickname}">
												<a data-toggle="tab" href="#contact-1" class="client-link">${data.shortNickName}</a>
											</td>
											<td>
												<a data-toggle="tab" href="#contact-1" class="client-link"><c:if test="${data.gender==1}">男</c:if><c:if test="${data.gender!=1}">女</c:if></a>
											</td>
											<td>
												<img src="${data.avatarUrl}" width="35" />
											</td>
											<td>
												${data.memberRankName}
											</td>
											<td>
												${data.point}
											</td>
											<td>
												${data.balance}
											</td>
											<td>
												${data.historyBalance}
											</td>
											<td>
												${data.name}
											<td>
												${data.mobile}
											</td>
											<td>
												${data.birthday}
											</td>
											<td>
												${data.cjrq}
											</td>

											<td>
												<c:if test="${data.recommendId!=null}">
													<div title="${data.recommendNickname}" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;width: 120px;">昵称:${data.recommendNickname}</div>
													<div>id:${data.recommendId}</div>
												</c:if>
												<c:if test="${data.recommendId==null}">
													-
												</c:if>
											</td>

											<td class="td-status">
												<c:if test="${data.status==0}">
													<span class="label label-primary">正常</span>
												</c:if>
												<c:if test="${data.status==1}">
													<span class="label label-default">冻结</span>
												</c:if>												
											</td>
											<td class="td-manage">
												<a class="btn btn-white btn-xs" href="#pointForm" data-toggle="modal" onclick="pointForm('${data.id}')" title="调整积分"><i class="icon iconfont">&#xe683;</i> </a>
												<a class="btn btn-white btn-xs" href="#memberForm" data-toggle="modal" onclick="editMember('${data.id}','${data.name}','${data.mobile}','${data.birthday}')" title="修改信息"><i class="icon iconfont">&#xe608;</i> </a>
                                                <a class="btn btn-white btn-xs" onclick="layer_show('积分记录','${base}/admin/member/point_list?memberId=${data.id}',800,'')" title="积分记录"><i class="icon iconfont">&#xe67c;</i> </a>
												<c:if test="${data.status==1}">
													<a class="btn btn-white btn-xs" onclick="admin_start(this,${data.id})" title="启用"><i class="icon iconfont">&#xe635;</i></a>
												</c:if>
												<c:if test="${data.status==0}">
													<a class="btn btn-white btn-xs" onclick="admin_stop(this,${data.id})" title="禁用"><i class="icon iconfont">&#xe621;</i></a>
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
			</div>
			</form>
		</div>


		<div id="memberForm" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<input type="hidden" id="memberId" name="memberId">
							<div class="form-group">
								<label>姓名：</label>
								<input type="text" id="memberName" name="memberName" placeholder="请填写姓名" class="form-control">
							</div>
							<div class="form-group">
								<label>手机号：</label>
								<input type="number" id="memberMobile" name="memberMobile" placeholder="请填写手机号" class="form-control">
							</div>
							<div class="form-group">
								<label>生日：</label>
								<input type="text" id="memberBirthday" name="memberBirthday" placeholder="请选择生日" class="form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" onclick="updateMember()" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<div id="pointForm" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<div class="row">
							<input type="hidden" id="pointMemberId" name="pointMemberId">
							<div class="form-group">
								<label>调整积分：</label>
								<input type="number" id="point" name="point" placeholder="负数代表扣减，正数代表增加" class="form-control">
							</div>
							<div class="form-group">
								<label>调整原因：</label>
								<input type="text" id="pointMemo" name="pointMemo" placeholder="请填写调整原因" class="form-control">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" onclick="changePoint()" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>


		<script>

            laydate.render({
                elem: '#memberBirthday',
                type: 'date'
            });
			function editMember(id,memberName,memberMobile,memberBirthday){
                $("#memberId").val(id);
                $("#memberName").val(memberName);
                $("#memberMobile").val(memberMobile);
                $("#memberBirthday").val(memberBirthday);
			}
			function updateMember(){
                var memberId = $("#memberId").val();
                var memberName = $("#memberName").val();
                var memberMobile = $("#memberMobile").val();
                var memberBirthday = $("#memberBirthday").val();
			    if(!memberMobile){
					if(isPhone(memberMobile)){
					    layer.msg("请输入正确的手机号");
					}
				}
                $.ajax({
                    type:"post",
                    url:"${base}/admin/member/update_member",
                    dataType:"json",
                    data:{
                        memberId:memberId,
                        memberName:memberName,
                        memberMobile:memberMobile,
                        memberBirthday:memberBirthday,
                    },
                    success:function(data){
                        if(data.type === "success"){
                            window.location.reload();
                        }else{
                            layer.alert(data.content);
                        }
                    }
                });
			}

			function pointForm(id){
                $("#pointMemberId").val(id);
			}
			function changePoint(id){
                var point = $("#point").val();
                var pointMemberId = $("#pointMemberId").val();
                var pointMemo = $("#pointMemo").val();

				if(!isInteger(point)){
					layer.msg("请输入整数！");
					return false;
				}
				point = point/1;
				if(point==0){
					layer.msg("调整积分不能等于0！");
					return false;
				}
				var tips = "";
				if(point<0){
					tips = "确认扣减该会员"+(Math.abs(point))+"积分";
				}else if(point>0){
					tips = "确认增加该会员"+(Math.abs(point))+"积分?";
				}
				layer.close(confirm);
				var confirm = layer.confirm(tips, {
					btn: ['是','否'] //按钮
				}, function(){
                    layer.closeAll();
					$.ajax({
						type:"post",
						url:"${base}/admin/member/change_point",
						dataType:"json",
						data:{
							memberId:pointMemberId,
							point:point,
							memo:pointMemo
						},
						success:function(data){
							if(data.type === "success"){
								layer.alert(data.content,function(){
									window.location.reload();
								});
							}else{
								layer.alert(data.content);
							}
						}
					});
				}, function(){

				});
			}

			$(function() {

				$(".full-height-scroll").slimScroll({
					height: "100%"
				});

				laydate.render({
					elem: '#startTime',
					type: 'datetime'
				});

				laydate.render({
					elem: '#endTime',
					type: 'datetime'
				});

				$("thead input:checkbox").each(function() {
					if ($(this).attr("checked") === true) {
						//$(this).parents('table').children("tbody input[type='checkbox'] ")
					}
				});

				//设置手机号或者名称
				$("#nickname").val("${nickname}");

				//设置身份
				$("#type").val("${type}");

				//设置状态
				$("#status").val("${status}");

			});

			//停用
			function admin_stop(obj, id) {
				layer.confirm('确认要禁用吗？', function(index) {
					//此处请求后台程序，下方是成功后的前台处理……
					$.ajax({
						type:"post",
						url:"${base}/admin/member/stop?id="+id,
						dataType:"json",
						data:$("#dataForm").serialize(),
						success:function(data){
							if(data.type === "success"){
								$(obj).parents("tr").find(".td-manage").append('<a class="btn btn-white btn-xs" onclick="admin_start(this,'+id+')" title="启用"><i class="icon iconfont">&#xe635;</i></a>');
								$(obj).parents("tr").find(".td-status").html('<span class="label label-default">冻结</span>');
								$(obj).remove();
								layer.msg('已禁用!', {
									icon: 5,
									time: 1000
								});
							}
						}
					});
				});
			}

			//启用
			function admin_start(obj, id) {
				layer.confirm('确认要启用吗？', function(index) {
					//此处请求后台程序，下方是成功后的前台处理……
					$.ajax({
						type:"post",
						url:"${base}/admin/member/start?id="+id,
						dataType:"json",
						data:$("#dataForm").serialize(),
						success:function(data){
							if(data.type === "success"){
								$(obj).parents("tr").find(".td-manage").append('<a class="btn btn-white btn-xs" onclick="admin_stop(this,'+id+')" title="禁用"><i class="icon iconfont">&#xe621;</i></a>');
								$(obj).parents("tr").find(".td-status").html('<span class="label label-primary">正常</span>');
								$(obj).remove();
								layer.msg('已启用!', {
									icon: 6,
									time: 1000
								});
							}
						}
					});
				});
			}

			//查询
			function f_query(){

			    var monthStart = $("#monthStart").val();
                var monthEnd = $("#monthEnd").val();
                var dayStart = $("#dayStart").val();
                var dayEnd = $("#dayEnd").val();
				if(monthStart||monthEnd||dayStart||dayEnd){
				    if(!monthStart){
				        layer.msg("请选择生日开始月份");
						return;
					}
                    if(!monthEnd){
                        layer.msg("请选择生日结束月份");
                        return;
                    }
                    if(!dayStart){
                        layer.msg("请选择生日开始日期");
                        return;
                    }
                    if(!dayEnd){
                        layer.msg("请选择生日开始日期");
                        return;
                    }
				}

                document.forms[0].target = "_self";
				document.forms[0].action = "${base}/admin/member/list";
				document.forms[0].submit();
			}

			//导出
			function f_exportXls(){
                var monthStart = $("#monthStart").val();
                var monthEnd = $("#monthEnd").val();
                var dayStart = $("#dayStart").val();
                var dayEnd = $("#dayEnd").val();
                if(monthStart||monthEnd||dayStart||dayEnd){
                    if(!monthStart){
                        layer.msg("请选择生日开始月份");
                        return;
                    }
                    if(!monthEnd){
                        layer.msg("请选择生日结束月份");
                        return;
                    }
                    if(!dayStart){
                        layer.msg("请选择生日开始日期");
                        return;
                    }
                    if(!dayEnd){
                        layer.msg("请选择生日开始日期");
                        return;
                    }
                }
                document.forms[0].method = "post";
				document.forms[0].target = "_blank";
				document.forms[0].action = "${base}/admin/member/exportXls";
				document.forms[0].submit();
			}

			$("#checkbox").click(function(){
			    var checked = $(this).prop("checked");
				$("input[name='ids']").prop("checked",checked);
			});

			jQuery.extend({
				birthday:function(monthDom,dayDom,m,d){
				    d = d/1;
                    function isLeapYear(year) {  return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);  }//闰年能被4整除且不能被100整除，或能被400整除
				    var select = "";
                    $(monthDom).html("<option value=''>请选择月份</option>");
				    for(var i=0;i<12;i++){
                        var st = "";
                        if((i+1)==m){
                            st = "selected";
                        }
                        $(monthDom).append("<option "+st+" value='"+(i+1)+"'>"+(i+1)+"月</option>");
					}
					if(!m){
				        m = 1;
					}
					$(monthDom).change(function(){
					    var month = $(this).val();
					    var day = 0;
					    var month31 = [1,3,5,7,8,10,12];
					    if($.inArray(month/1,month31)>0){
                            day = 31;
						}else if(month==2){
							if(isLeapYear(new Date().getFullYear())){
                            	day = 29;
							}else{
                            	day = 28;
							}
						}else{
					        day = 30;
						}
                        $(dayDom).html("<option value=''>请选择日期</option>");
                        for(var j=0;j<day;j++){
                            var st = "";
                            if((j+1)==d){
                                st = "selected";
							}
                            $(dayDom).append("<option "+st+" value='"+(j+1)+"'>"+(j+1)+"日</option>");
                        }
					});
                    $(monthDom).find("option[value="+(m/1)+"]").select().trigger("change");

				}
			});

			$.birthday("#monthStart","#dayStart","${monthStart}","${dayStart}");
            $.birthday("#monthEnd","#dayEnd","${monthEnd}","${dayEnd}");

            <c:forEach items="${sysMenuList}" var="sysMenu">
            <c:if test="${sysMenu.perms=='admin:member:select_coupon'}">
			var selectMemberIds = [];
			var selectCouponIds = [];
            function sendCoupon(){
                selectMemberIds = [];
                var $ids = $("input[name='ids']:checked");
                $ids.each(function(){
                    var ids = $(this).val();
                    selectMemberIds.push(ids);
				});
                if(selectMemberIds.length==0){
                    layer.msg("请选择会员");
                    return ;
				}
                selectCouponIds = [];
                layer_show("选择优惠券","select_coupon",850,'');
            }

            function sendCountToMemeber(){
				var load = layer.load();
                $.ajax({
					type:"post",
					dataType:"json",
                    traditional:true,
					url:"${base}/admin/member/send_coupon",
					data:{
                        memberIds:selectMemberIds,
                        couponTypeIds:selectCouponIds
					},
					success:function(data){
						if(data.type=="success"){
						    layer.alert(data.content,function(){
						        window.location.reload();
							});
						}else{
                            layer.alert(data.content);
                        }
					}
				});

			}

            </c:if>
            </c:forEach>

            $("#productName").autocomplete({
                source:function(request,response){
                    var productName = $("#productName").val();
                    $.ajax({
                        type:"POST",
                        url:"${base}/admin/member/product",
                        dataType : "json",
                        cache : false,
                        data : {
                            productName:productName
                        },
                        success : function(json) {
                            var data = eval (json);//json数组
                            response($.map(data,function(item){
                                return {
                                    label:item.productName,//下拉框显示值
                                    value:item.productName,//选中后，填充到input框的值
                                    id:item.productId//选中后，填充到id里面的值
                                }

                            }));
                        }
                    });
                },
                delay: 100,//延迟100ms便于输入
                select : function(event, ui) {
                    $("#productId").val(ui.item.id);//取出在return里面放入到item中的属性
                },
                scroll:true,
                pagingMore:true,
                max:5000
            });

		</script>

	</body>

</html>
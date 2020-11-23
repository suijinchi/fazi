<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>编辑地址</title>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/every.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/newaddress.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery.lSelect.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/valid.js?id=0"></script>
	</head>

	<body>
		<div id="na-mainBox">
			<div id="main" style="background: #fff;">
				<div id="header">
					<form action="${base}/mobile/receiver/addsubmit" id="thisForm" method="post">
					<ul id="dimension">
						<li>
							<ul>
								<li class="l1">订货人</li>
								<li class="l2">
									<input type="hidden" name="receiverId" value="${receiver.id}">
									<input type="hidden" name="editReceiverId" value="${receiver.id}">
									<input type="text" name="orderer" id="orderer" value="${receiver.orderer}" placeholder="订货人（选填）" onfocus="this.style.color='#333'" />
								</li>
								<div class="clear"></div>
							</ul>
						</li>
						<li>
							<ul>
								<li class="l1">联系电话</li>
								<li class="l2">
									<input type="number" name="ordererNumber" id="ordererNumber" value="${receiver.ordererNumber}" placeholder="订货人联系电话（选填）" onfocus="this.style.color='#333'" />
								</li>
								<div class="clear"></div>
							</ul>
						</li>
						<li>
							<ul>
								<li class="l1">收货人</li>
								<li class="l2">
									<input type="text" name="consignee" id="consignee" value="${receiver.consignee}" placeholder="收货人姓名" onfocus="this.style.color='#333'" />
								</li>
								<div class="clear"></div>
							</ul>
						</li>
						<li>
							<ul>
								<li class="l1">联系电话</li>
								<li class="l2">
									<input type="tel" name="contactNumber" id="contactNumber" value="${receiver.contactNumber}" placeholder="收货人联系电话" onfocus="this.style.color='#333'" />
								</li>
								<div class="clear"></div>
							</ul>
						</li>
						<li class="box_location">
							<ul>
								<li class="l1">选择地区</li>
								<li class="l2" style="float: left;width: 70%;">
									<div class="cl">
										<input type="hidden" id="areaId" name="areaId" treePath="${treePath}" value="${receiver.areaId}" placeholder="请选择"/>
									</div>
								</li>
								<div class="clear"></div>
							</ul>
						</li>
						<li>
							<ul>
								<li class="l1">详细地址</li>
								<li class="l2">
									<input type="text" name="newAddress" id="newAddress" value="${receiver.address}" placeholder="街道门牌信息" onfocus="this.style.color='#333'" />
								</li>
								<div class="clear"></div>
							</ul>
						</li>
					</ul>
					<a id="baocun" style="background: #fff;display: block;" onclick="updateReceiver()">
						<button type="button" class="btn">保存</button>
					</a>
					</form>
				</div>
			</div>
		</div>
	</body>

	<script>

		$(function () {
			area();
        });

        function area() {
            //初始化地区选择控件
            jQuery("#areaId").lSelect({
                url: "${base}/mobile/area/list",
                className:"item_select city_select fl new_select"
            });
        }

        //更新地址
        function updateReceiver() {

            var ordererNumber = $("#ordererNumber").val();
            if(ordererNumber){
                if(!isPhone(ordererNumber)&&!isTel(ordererNumber)){
                    $.toast("订货人联系电话不正确！","text");
                    return;
                }
            }

            //姓名不能为空
            var consignee = $("#consignee").val();
            if(consignee==null||consignee==""){
                $.toast("收货人姓名不能为空！", "text");
                return;
            }
            //联系电话
            var contactNumber = $("#contactNumber").val();
            if(contactNumber==null||contactNumber==""){
                $.toast("收货人联系电话不能为空！", "text");
                return;
            }
            if(!isPhone(contactNumber)&&!isTel(ordererNumber)){
                $.toast("收货人联系电话不正确！","text");
                return;
            }

            //详细地址
            var newAddress = $("#newAddress").val();
            if(newAddress==null||newAddress==""){
                $.toast("收货详细地址不能为空！", "text");
                return;
            }
            $.ajax({
                type:"post",
                url:"${base}/mobile/receiver/updateReceiver",
                dataType:"json",
                async:false,
                data:$("#thisForm").serialize(),
                success:function(data){
                    if(data.type=="success"){
                        $.toast(data.content,"text",function(){
                            window.location.replace("${base}/mobile/receiver/list");
                        });
                    } else {
                        $.toast(data.content,"text");
                    }
                }
            });
        }

	</script>


</html>
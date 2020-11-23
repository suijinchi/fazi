<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>地址管理</title>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/every.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/address.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery.lSelect.js"></script>
	</head>

	<body>
		<div id="main">
			<c:forEach var="receiver" items="${receivers}">
			<div class="adre">
				<p class="p1">${receiver.consignee}<span>${receiver.contactNumber}</span></p>
				<p class="p2">${receiver.areaName}${receiver.address}</p>
				<ul>
					<li class="lt">
						<c:if test="${receiver.isDefault == 0}">
							<a class="a1 fl" onclick="isDefault(${receiver.id})"><img class="hide" src="${respath}/mobile/images/Shopping-Cart_unCheck.png" /></a>
						</c:if>
						<c:if test="${receiver.isDefault == 1}">
							<a class="a1 fl"><img class="hide" src="${respath}/mobile/images/Shopping-Cart_Check.png" /></a>
						</c:if>
						<span class="fl">设为默认地址</span>
					</li>
					<li class="rt">
						<a onclick="deleteReceiver(${receiver.id})" class="a2">
							<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe615;</i> 删除
						</a>
					</li>
					<li class="rt l2">
						<a href="${base}/mobile/receiver/edit?id=${receiver.id}">
							<i class="icon iconfont" style="color: #888;font-size: 18px;vertical-align: middle;">&#xe677;</i> 编辑
						</a>
					</li>
				</ul>
			</div>
			</c:forEach>

			<div id="" style="width: 100%;background: none;padding-top: 20px">
				<a href="${base}/mobile/receiver/add" style="display: block;background:#ff7d84;width: 80%;margin: 0 auto;font-size: 14px;color: #fff;text-align: center;padding: 10px 0;border-radius: 6px;">
					<i class="icon iconfont">&#xe614;</i> 新增管理地址
				</a>
			</div>

		</div>

		<script>
			var c = window.screen.availHeight;
			console.log(c);
			$('#main').css('height', c);

            function isDefault(id) {
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/receiver/isDefault",
                    dataType:"json",
                    async:false,
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.type === "success"){
                            $.toast("设置成功","text");
                            window.location.replace('${base}/mobile/receiver/list');
                        }
                    }
                });
            }

            //删除地址
            function deleteReceiver(id) {
                $.ajax({
                    type:"post",
                    url:"${base}/mobile/receiver/deleteReceiver",
                    dataType:"json",
                    async:false,
                    data:{
                        id:id
                    },
                    success:function(data){

                    }
                });
                window.location.replace('${base}/mobile/receiver/list');
            }

		</script>
	</body>

</html>
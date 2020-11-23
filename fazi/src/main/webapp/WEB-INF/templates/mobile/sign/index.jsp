<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>每日签到</title>
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<link href="${base}/resources/mobile/css/reset.css" rel="stylesheet" type="text/css">
		<link href="${base}/resources/mobile/css/iconfont.css" rel="stylesheet" type="text/css">
		<link href="${base}/resources/mobile/css/public.css" rel="stylesheet" type="text/css">
		<link href="${base}/resources/mobile/css/weui.min.css" rel="stylesheet" type="text/css">
		<link href="${base}/resources/mobile/css/jquery-weui.css" rel="stylesheet" type="text/css">
		<link href="${base}/resources/mobile/css/main1208.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
		<style type="text/css">
			.picker-calendar-day.picker-calendar-day-selected span {
				background: #5ac5d5;
				color: #fff;
			}
			
			.picker-calendar-week-days {
				height: 1.4rem;
				background: #f7f7f8;
				display: -webkit-box;
				display: -ms-flexbox;
				display: -webkit-flex;
				display: flex;
				font-size: 11px;
				box-sizing: border-box;
				position: relative;
			}
			
			.toolbar {
				line-height: 2;
			}
			
			.picker-calendar-week-days+ .picker-calendar-months {
				height: 13.9rem;
			}
		</style>
	</head>

	<body style="background: #ededed;">
		<div class="sign-main">
			<div class="sign-top">
				<div class="weui-flex">
					<div class="weui-flex__item">
						<a href="">
							<span class="number">${member.point}</span>
							<div class="text">可用积分</div>
						</a>
					</div>
					<div class="weui-flex__item">
						<a href="javascript:;">
							<c:if test="${sign}">
								<div class="btn-sign" style="background-color: #bbb">
									今日已签到
								</div>
							</c:if>
							<c:if test="${!sign}">
								<div class="btn-sign" id="sign">
									签到
								</div>
							</c:if>
						</a>
					</div>
					<div class="weui-flex__item">
						<a href="">
							<span class="number">${member.signSerialTimes}</span>
							<div class="text">连续签到</div>
						</a>
					</div>
				</div>
			</div>

			<div class="sign-main">
				<%--
				<div class="weui-flex box-navigation" style="text-align: center;">
					<a class="weui-flex__item" >
						<i class="icon iconfont">&#xe6aa;</i>
						<div >
							兑换礼品
						</div>
					</a>
					<a class="weui-flex__item" href="javascript:alert('暂未开放');">
						<i class="icon iconfont">&#xe6ac;</i>
						<div class="">
							积分抽奖
						</div>
					</a>
					<a class="weui-flex__item" href="list">
						<i class="icon iconfont">&#xe6ad;</i>
						<div class="">
							积分明细
						</div>
					</a>
					<a class="weui-flex__item" id="btn-rules">
						<i class="icon iconfont">&#xe6ab;</i>
						<div class="">
							签到规则
						</div>
					</a>
				</div>
				--%>
				<div class="weui-cell" style="padding-top: 0;">
					<div class="weui-cell__hd"><label for="date" class="weui-label"></label></div>
					<div class="weui-cell__bd">
						<input class="weui-input" id="date" type="text" style="display: none;" value="${date}">
					</div>
				</div>
				<div id="inline-calendar" style="width: 90%;margin: 4% auto;padding-bottom: 50px;">
                    <div style="position: absolute;width: 90%;height: 50%;z-index: 999"></div>
                </div>
			</div>

		</div>

		<div class="jffooterNav">
			<div class="weui-flex">
				<div class="weui-flex__item active">
					<a href="${base}/mobile/index">
						<i class="icon iconfont">&#xe6f5;</i>
						<div class="jfFooterName" style="color: #666;">
							首页
						</div>
					</a>
				</div>
				<div class="weui-flex__item active">
					<a href="${base}/mobile/product_category/list">
						<i class="icon iconfont">&#xe6f2;</i>
						<div class="jfFooterName" style="color: #666;">
							分类
						</div>
					</a>
				</div>
				<div class="weui-flex__item active">
					<a href="${base}/mobile/cart/list">
						<i class="icon iconfont">&#xe6c2;</i>
						<div class="jfFooterName" style="color: #666;">
							购物车
						</div>
					</a>
				</div>
				<div class="weui-flex__item">
					<a href="${base}/mobile/member/index">
						<i class="icon iconfont" style="color: #ff4442;">&#xe6f6;</i>
						<div class="jfFooterName" style="color: #ff4442;">
							我的
						</div>
					</a>
				</div>

			</div>
		</div>
		
	</body>
	<script type="text/javascript" src="${base}/resources/mobile/js/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${base}/resources/mobile/js/jquery-weui.js"></script>
	<script type="text/javascript" src="${respath}/mobile/js/common.js?ver=${ver}"></script>
	<script type="text/javascript">

		$(function(){
			
			function getSignList(month){
				$.ajax({
		            type: "post",
		            url: "date",
		            dataType:"json",
		            data:{
		            	month:month
		            },
		            success: function (data) {
		            	var date = data.data.date;
		            	var signList = data.data.signList;
                        var array = [];
                        if(signList){
                            array = new Array(signList.length);
                            for(var i =0;i<array.length;i++){
                                array[i] = signList[i].date;
                            }
                        }
                        // array = ['2018-11-20','2018-11-22','2018-11-30'];
		            	$("#date").calendar({
		        			container: "#inline-calendar",
		        			inputReadOnly: true,
		        			multiple: true,
		        			value: array, //默认选中
		        			dayNamesShort: ['日', '一', '二', '三', '四', '五', '六'],
		        			onDayClick: function(p, values, displayValues) {
		        			    return false;
		        				// p.removeClass(".picker-calendar-day-selected");
		        			}
		        		});

		            }
				});
			}
			
			var month = 0;
			getSignList(month);
			
			/* //下一个月
			$(".picker-calendar-next-month").on("click",function(){
				month=month+1;
				getSignList(month);
			});
			//前一个月
			$(".picker-calendar-prev-month").on("click",function(){
				month=month-1;
				getSignList(month);
			}); */
            $.wxShare({base:"${base}","title":"签到"});
		});

		$('#sign').click(function(){
			$.ajax({
                type: "post",
                url: "sign",
                dataType:"json",
                success: function (data) {
                    if(data.type=="success"){
                        $.toast(data.content);
                        setTimeout(function (){location.reload()},1000);
					}else{
                        $.toast(data.content,"text");
					}

                }
			});
		})
		
	</script>

</html>
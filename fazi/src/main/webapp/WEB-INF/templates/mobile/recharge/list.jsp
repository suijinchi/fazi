<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>充值记录</title>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="ie=edge">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css?ver=${ver}">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/reset.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/longyangshe.css?ver=${ver}">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/myself.css?ver=${ver}">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/fastclick.js"></script>
        <script type="text/javascript" src="${respath}/mobile/js/valid.js"></script>
		<style type="text/css">
			img{
				display: inline-block;
			}
		</style>
	</head>

	<body>

	<div id="consumption"></div>

	<form id="searchForm">
		<input type="hidden" id="pageNo" name="pageNo" value="1" />
	</form>

	<!-- 上滑 加载数据 -->
	<div id="loading" class="weui-loadmore">
		<i class="weui-loading"></i>
		<span class="weui-loadmore__tips">正在加载</span>
	</div>
	<div id="nodata" class="weui-loadmore weui-loadmore_line" style="display: none">
		<span class="weui-loadmore__tips">暂无数据</span>
	</div>
	<div id="nomore" class="weui-loadmore weui-loadmore_line weui-loadmore_dot" style="display: none">
		<span class="weui-loadmore__tips"></span>
	</div>


	<script type="text/javascript">
        $(function() {

            var loading = false;  //状态标记
            $(document.body).infinite().on("infinite", function() {
                if(loading)
                    return;
                loading = true;
                setTimeout(function() {
                    var pageNo = $("#pageNo").val()/1+1;
                    loadData(pageNo);
                }, 400);   //模拟延迟
            });
            function loadData(pageNo){
                $.ajax({
                    url:"${base}/mobile/recharge/listData",
                    data:{
                        pageNo:pageNo
                    },
                    type:"POST",
                    dataType:"json",
                    success:function(data){
                        if(data.totalPages==0){
                            $("#loading").hide();
                            $("#nomore").hide();
                            $("#nodata").show();
                            return;
                        }
                        var dataList = "";
                        $.each(data.listData,function(i,n){
							var ga = "";
							if(n.giveAmount/1>0){
                                ga = "(赠送："+n.giveAmount+")";
							}
                            dataList+=
                        	'<div class="weui-panel_access item-consumption">'+
                            '    <div class="weui-panel__bd">'+
                            '    	<a href="javascript:void(0);" class="weui-media-box_appmsg" style="padding: 5px;color: #666;">'+
                            '    		<div class="weui-media-box__hd">'+
                            '    			<img class="weui-media-box__thumb" src="${respath}/mobile/images/cons_chongzhi_10.png" style="width: 80%;margin-top: 6px;">'+
                            '    		</div>'+
                            '    		<div class="weui-media-box__bd">'+
                            '					<h4 class="weui-media-box__title">充值'+n.amount+'元'+ga+'</h4>'+
                            '					<p class="weui-media-box__desc">'+n.payDate+'</p>'+
                            '				</div>'+
                            '				<div class="weui-media-box__hd" style="text-align: center;width: 70px;">'+
                            '				<span class="color-danger">+'+n.sumAmount+'</span>'+
                            '			</div>'+
                            '   	</a>'+
                            '   </div>'+
                            '</div>';
                        });

                        $("#consumption").append(dataList);
                        $("#pageNo").val(data.pageNo);
                        if(data.pageNo==data.totalPages){
                            $("#loading").hide();
                            $("#nomore").show();
                            $(document.body).destroyInfinite();
                        }else{
                            loading = false;
                        }
                    }
                });
            }
            loadData(1);

        })
	</script>
	</body>


</html>
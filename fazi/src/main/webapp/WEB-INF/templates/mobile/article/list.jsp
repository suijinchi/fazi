<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>${articleCategory.name}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<%@ include file="/WEB-INF/templates/mobile/include/common.jsp" %>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js?ver=${ver}"></script>
		<link href="${respath}/mobile/css/reset.css?ver=${ver}" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/shequn.css">
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<style type="text/css">
			.weui-cells:after {
				bottom: 0;
				border-bottom: 0!important;
			}

			p {
				margin-bottom: 0!important;
			}

			.weui-panel:after {
				bottom: 0;
				border-bottom: 0!important;
			}

			.swiper-pagination-bullet-active {
				opacity: 1;
				background: #fff;
			}

			.art_title {
				width: 100%;
				display: block;
				height: 30px;
				line-height: 30px;
				text-overflow: ellipsis;
				white-space: nowrap;
			}

			.tieLists {
				width: 100%;
				background: #fff;
			}

			.tieLists .itemTie {
				padding: 3% 3% 1% 3%;
				display: block;
				border-bottom: 1px solid #cbcbcb;
				color: #878787;
			}

			.tieLists .itemTie .userPhoto {
				width: 44px;
				border-radius: 100%;
				margin-left: 8%;
				height: 44px;
			}

			.tieLists .itemTie .userName {
				color: #424242;
				font-size: 14px;
				font-weight: normal;
				line-height: 24px;
			}

			.tieLists .itemTie .tTitle {
				font-size: 1.3em;
				color: #000;
				margin-bottom: 5px;
			}

			.tieLists .itemTie .tDescription {
				font-size: 0.9em;
				color: #444343;
				line-height: 20px;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;
			}

			.tState {
				height: 36px;
				line-height: 36px;
				font-size: 0.9em;
				text-align: right;
			}

			.tz-title {
				font-size: 0.95em;
				color: #3f3f3f;
				height: 30px;
				line-height: 30px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				font-weight: bold;
			}

			.tDescription-2 {
				line-height: 17px;
				height: 34px;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;
				font-size: 0.9em;
				color: #444343;
			}

			.weui-uploader__file {
				height: 6em!important;
			}
		</style>
	</head>

	<body>
		<div id="content" class="tieLists"></div>

		<form id="searchForm">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<input type="hidden" id="articleCategoryId" name="articleCategoryId" value="${articleCategoryId}" />
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

		<%--<div style="height: 60px;"></div>--%>

		<script type="text/javascript">

            $(function () {

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
                        url:"${base}/mobile/article/listData",
                        data:{
                            pageNo:pageNo,
							articleCategoryId:$("#articleCategoryId").val(),
							aid:[<c:if test="${aid!=null&&not empty aid}"><c:forEach items="${aid}" var="a">"${a}",</c:forEach></c:if>]
                        },
                        traditional:true,
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
								dataList+=
								'<a class="itemTie" href="${base}/mobile/article/detail?id='+n.id+'">'+
									'	<div class="weui-row">'+
									'	<div class="weui-col-33">'+
									'		<div class="weui-flex__item weui-uploader__file" style="background-image:url('+n.showImageUrl+');position: relative;margin-bottom: 0;width: 100%;height: 8em;">'+
									'		</div>'+
									'	</div>'+
									'	<div class="weui-col-66">'+
									'		<div class="tz-top">'+
									'			<div class="weui-flex">'+
									'				<div class="weui-flex__item tz-title">'+n.title+'</div>'+
									'			</div>'+
									'		</div>'+
									'		<div class="tDescription-2">'+n.subTitle+'</div>'+
									'		<div class="tState">'+n.createDate+'</div>'+
									'	</div>'+
									'</div>'+
								'</a>';
							});
                            $("#content").append(dataList);
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
            });

		</script>

	</body>


</html>
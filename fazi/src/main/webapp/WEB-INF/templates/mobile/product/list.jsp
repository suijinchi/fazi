<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<title>商品列表</title>
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
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
		<link rel="stylesheet" type="text/css" href="${respath}/mobile/layer/skin/default/layer.css">
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
		<script type="text/javascript" src="${respath}/mobile/layer/layer.js"></script>
		<style type="text/css">
			.box-order-category ul li a {}
			.box-order-category ul li {
				position: relative;
			}
			.box-order-category ul li:after {
				position: absolute;
				right: 0;
				top: 15px;
				content: '';
				display: block;
				height: 10px;
				border-right: 1px solid #ccc;
			}
			.box-order-category ul li:last-child:after {
				border-right: 0;
			}
			.box-order-category ul li a i {
				vertical-align: top;
				font-size: 16px;
				color: #ff4442;
			}
			.box-order-category ul li a.cur {
				color: #ff4442;
				border-bottom: 0;
			}
			a.add-cart-buttn {
				background: #ff7d84;
				width: 66%;
				height: 44px;
				font-size: 14px;
				color: #fff;
				text-align: center;
				display: block;
				line-height: 44px;
				margin-left: 15%;
				margin-top: 28px;
				margin-bottom: 16px;
				padding: 0 3%;
				border-radius: 6px;
			}
			.details-options, .spec-title {
				font-size: 0.8em;
			}
			.box-order-category ul li {
				position: relative;
				width: 25%!important;
			}
		</style>
	</head>

	<body style="background: #efefef;">
		<div class="box-order-category">
			<ul>
				<li>
					<a id="zongheSort" class="cur">综合</a>
				</li>
				<li>
					<a id="salesSort">销量
						<i class="icon iconfont asc" style="display: none">&#xe652;</i>
						<i class="icon iconfont desc" style="display: none">&#xe653;</i>
					</a>
				</li>
				<li>
					<a id="xinpinSort">新品</a>
				</li>
				<li class="search_price">
					<a id="priceSort">价格
						<i class="icon iconfont asc" style="display: none">&#xe652;</i>
						<i class="icon iconfont desc" style="display: none">&#xe653;</i>
					</a>
				</li>
			</ul>
		</div>
		<div class="productHot" style="border-radius: 10px;overflow: hidden;padding-top: 50px;">
			<div class="producthotLists haowuBox recommend-list">
				<ul id="content"></ul>
				<div class="clear"></div>
			</div>
		</div>
		<!--弹出层-->
		<div class="box-select-main" id="box-select-main" style="display: none;">
			<div class="details-suspension-content" style="padding-top: 20px;padding-bottom: 10px;">
				<p class="details-price" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">¥298.00</p>
				<ul class="details-options" hidden="" style="display: block; transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
					<li class="details-options-size"><i></i>28x28cm</li>
					<li class="details-options-unmber"><i></i>适合15-20人分享</li>
					<li class="details-options-laid"><i></i>含20套餐具</li>
					<li class="details-options-time"><i></i>生日帽</li>
				</ul>
				<img src="${respath}/mobile/images/gg_03.jpg" alt="" class="size-img" hidden="" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);width:100%;margin-bottom: 10px;">
				<div class="extra-box" hidden="" style="display: none;">
					<p class="spec-title">商品规格</p>
					<ul class="details-suspension-size-extra">
					</ul>
				</div>
				<div class="normal-box" hidden="" style="display: block; transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
					<p class="spec-title">商品规格</p>
					<ul class="details-suspension-size">
						<li class="active">1.0磅</li>
						<li>2.0磅</li>
						<li>3.0磅</li>
						<li>4.0磅</li>
						<li>5.0磅</li>
						<li>5.0磅</li>
						<li>其他</li>
					</ul>
				</div>
				<a href="" class="add-cart-buttn join-cart" name="add-cart-button_799" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">加入购物车<span>(¥298.00)</span></a>
			</div>
		</div>

		<form id="searchForm">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<input type="hidden" id="name" name="name" value="${name}" />
			<input type="hidden" id="tagId" name="tagId" value="${tagId}" />
			<input type="hidden" id="productCategoryId" name="productCategoryId" value="${productCategoryId}" />
			<input type="hidden" id="sort" name="sort" value="" />
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

                $("#zongheSort").click(function(){
                    clearSort(this);
                    $("#sort").val("zongheSort");
                    loadData(1);
				});
                $("#salesSort").click(function(){
                    var asc = $("#salesSort i.asc").is(":hidden");
                    var desc = $("#salesSort i.desc").is(":hidden");
                    clearSort(this);
                    if(asc&&desc){
						$("#sort").val("salesAsc");
                        $("#salesSort i.asc").show();
                        $("#salesSort i.desc").hide();
					}
					if(asc&&!desc){
                        $("#sort").val("salesAsc");
                        $("#salesSort i.asc").show();
                        $("#salesSort i.desc").hide();
					}
                    if(!asc&&desc){
                        $("#sort").val("salesDesc");
                        $("#salesSort i.asc").hide();
                        $("#salesSort i.desc").show();
                    }
                    loadData(1);
                });
                $("#xinpinSort").click(function(){
                    clearSort(this);
                    $("#sort").val("xinpinSort");
                    loadData(1);
                });
                $("#priceSort").click(function(){
                    var asc = $("#priceSort i.asc").is(":hidden");
                    var desc = $("#priceSort i.desc").is(":hidden");
                    clearSort(this);
                    if(asc&&desc){
                        $("#sort").val("priceAsc");
                        $("#priceSort i.asc").show();
                        $("#priceSort i.desc").hide();
                    }
                    if(asc&&!desc){
                        $("#sort").val("priceAsc");
                        $("#priceSort i.asc").show();
                        $("#priceSort i.desc").hide();
                    }
                    if(!asc&&desc){
                        $("#sort").val("priceDesc");
                        $("#priceSort i.asc").hide();
                        $("#priceSort i.desc").show();
                    }
                    loadData(1);
                });

                function clearSort(dom,sort){
                    $(".box-order-category a").removeClass("cur");
                    $(dom).addClass("cur");
                    $(".asc").hide();
                    $(".desc").hide();
                    $("#pageNo").val(1);
                    $("#sort").val("");
                    $("#content").html("");
                    $("#loading").show();
                    $("#nomore").hide();
                    $("#nodata").hide();
				}

                /*//筛选
                $(".asc,.desc").hide();
                $(".box-order-category li a").click(function() {
                    $(this).addClass("cur");
                    $(this).parent("li").siblings("li").children().removeClass("cur");
                    if (!$(".search_price a").hasClass("cur")) {
                        $(".asc,.desc").hide();
                    }
                })
                var flag;
                $(".search_price a").click(function() {
                    if (flag) {
                        $(".asc").hide();
                        $(".desc").show();
                        flag = false;
                    } else {
                        $(".asc").show();
                        $(".desc").hide();
                        flag = true;
                    }
                })*/



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
                        url:"${base}/mobile/product/listData",
                        data:{
                            pageNo:pageNo,
							name:$("#name").val(),
							productCategoryId:$("#productCategoryId").val(),
							sort:$("#sort").val(),
							tagId:$("#tagId").val()
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

							    var sn = n.productStock.specName;
							    if(sn){
							       sn = "/"+sn;
								}else{
							        sn = "";
								}

								dataList+=
									'<li>'+
									'	<a href="${base}/mobile/product/detail?skuId='+n.productStock.id+'">'+
									'		<img src="'+n.showUrl+'" alt="'+n.name+'" class="fz-img">'+
									'		<h4>'+n.name+'</h4>'+
									'		<p>￥'+n.price+sn+'</p>'+
									'	</a>'+
                                    '	<a href="${base}/mobile/product/detail?skuId='+n.productStock.id+'">'+
									'	<div class="recommend-cart">'+
									'		<i class="icon iconfont">&#xe6c2;</i>'+
									'	</div>'+
                                    '	</a>'+
									'</li>';
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

                            var pImg = $(".fz-img").width();
                            $(".fz-img").height(pImg);

                        }
                    });
                }
                $("#zongheSort").click();
            });

		</script>

	</body>


</html>
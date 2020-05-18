<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>商品管理-修改</title>
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
		<script type="text/javascript" src="${respath}/admin/js/liandong.js"></script>

		<style type="text/css">
			.product-list table tr td {
				border-top: none;
				border: 1px solid #e7eaec;
				padding: 5px 10px;
				vertical-align: middle;
			}
			.div_contentlist ul li{
				float: left;
			}
		</style>
	</head>

	<body class="gray-bg">

        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">

                        <form action="save" class="form-horizontal" id="dataForm" name="dataForm" method="post" onsubmit="return false">
                            <input id="id" name="id" value="${product.id }" class="form-control" type="hidden">
                            <div class="tabs-container">
                                <div class="tab-content" style="margin-top: 20px">

                                    <div>

                                        <div class="panel panel-primary">


                                            <div class="panel-body">
                                                <div id="navtab1" style="width: 100%;">
                                                    <div title="商品规格" tabid="tabItem3">
                                                        <div id="Div1">
                                                            <div position="center">
                                                                <div style="padding: 5px 8px;" class="div_content">
                                                                    <!--<div class="div_title"><span>产品规格</span></div>-->
                                                                   <div class="div_contentlist" style="display: none">
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
                                                                                                <input id="${specValue.id}" type="checkbox" <c:forEach items="${productStockSpecNameValueList}" var="data"><c:if test="${data.specValueId == specValue.id}">checked="checked" disabled="disabled"</c:if></c:forEach> class="chcBox_Width" value="${specValue.value}" />${specValue.value}<span class="li_empty" contentEditable="true"></span></label>
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

                                    <div style="margin-top:30px;">
                                        <div class="col-sm-8 col-sm-offset-3">
                                            <button class="btn btn-primary" id="submit" onclick="save()">提交</button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
			
        <form id="dataForm111">
            <input type="file" name="file" id="file1" onchange="uploadImage1(this);" style="display: none;">
        </form>
			
		<script>

            function save() {

                var $tr = $('.tr');
                if ($tr.length === 0) {
                    layer.msg("请选择商品规格", {icon: 0});
                    return;
                }
                var flag = true;
                var products = [];
                $.each($tr, function (i, n){
                    var specImg = $(n).children('td[name=specImg]').children('input').val();
                    var price = $(n).children('td[name=price]').children('input').val();
                    var stock = $(n).children('td[name=stock]').children('input').val();
                    /*var sn = $(n).children('td[name=sn]').children('input').val();*/
                    var people = $(n).children('td[name=people]').children('input').val();
                    var tableware = $(n).children('td[name=tableware]').children('input').val();
                    var size = $(n).children('td[name=size]').children('input').val();
                    var maxPoint = $(n).children('td[name=maxPoint]').children('input').val();
                    var spec_ids = $(n).children('td[name=spec_ids]').children('input').val();
                    var spec_values = $(n).children('td[name=spec_values]').children('input').val();
                    if (specImg === null || specImg === undefined || specImg === '') {
                        layer.msg("请上传商品规格图", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (price === null || price === undefined || price === '') {
                        layer.msg("请填写商品价格", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (stock === null || stock === undefined || stock === '') {
                        layer.msg("请填写商品库存", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (people === null || people === undefined || people === '') {
                        layer.msg("请填写商品用餐人数", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (tableware === null || tableware === undefined || tableware === '') {
                        layer.msg("请填写商品餐具数", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (size === null || size === undefined || size === '') {
                        layer.msg("请填写商品尺寸", {icon: 0});
                        flag = false;
                        return;
                    }
                    if (maxPoint === null || maxPoint === undefined || maxPoint === '') {
                        layer.msg("请填写商品积分最大抵扣额", {icon: 0});
                        flag = false;
                        return;
                    }
                    var index = {
                        specImg: specImg,
                        price: price,
                        stock: stock,
                        people: people,
                        tableware: tableware,
                        size: size,
                        maxPoint: maxPoint,
                        spec_ids: spec_ids,
                        spec_values: spec_values
                    };
                    products.push(index);
                });
                var jsonString = JSON.stringify(products);

                if (flag) {
                    $.ajax({
                        type:"post",
                        url:"${base}/admin/product/update_stock",
                        dataType:"json",
                        data:$("#dataForm").serialize() + "&jsonString=" + jsonString,
                        beforeSend:function(){
                            $('#submit').prop("disabled",true);
                        },
                        success:function(data){
                            if(data.type==="success"){
                                layer.msg(data.content, {icon: 1,time: 2000}, function(){
                                    parent.location.reload();
                                });

                            }else{
                                layer.msg(data.content, {icon: 0});
                                $('#submit').prop("disabled",false);
                            }
                        }
                    });
                }

            }
		
			$(function() {

                //点击编辑
                $(".js_specifica_edit").click(function() {
                    $(this).hide();
                    $(this).parents(".js_show_div").hide();
                    $(this).parents(".js_show_div").next("ul").hide();
                    $(this).parents(".js_show_div").next("ul").next().next(".js_enter_div").show();
                })

                //隐藏编辑规格
                $(".js_enter_div").hide();

                // 点击取消
                $(".js_specvals_val_cancel").click(function() {
                    $(this).parents(".js_enter_div").prev().prev("ul").prev(".js_show_div").show();
                    $(this).parents(".js_enter_div").prev().prev("ul").prev(".js_show_div").children().children(".js_specifica_edit").show();
                    $(this).parents(".js_enter_div").prev().prev("ul").show(); //显示编辑
                    $(this).parents(".js_enter_div").hide();
                })

                //输入规格后，点击添加
                $(".js_add_speval").click(function() {
                    var a = $(this).parent().prev(".col-sm-5").children(".js_input_outer").children(".js_custom_input");
                    var b = a.val();
                    var html = '<span class="label label-default bg-light dker specvals-show js_specvals_show js_specvals_del">' + b + '<i class="icon iconfont" style="font-size: 12px;">&#xe693;</i></span>'
                    $(this).parents(".js_enter_div").find(".specList").append(html);
                    $(".js_custom_input").val('');
                })

                //选择规格，表格显示记录
                $("#guigeBox input:radio[name='optionsRadios']").on('ifChecked', function(event) {})

                //删除规格
                $(document).delegate(".js_specvals_del", "click", function() {
                    $(this).hide();
                });

				var index = 0;
				$("#addImage").click(function () {
					var i = index++;
					var html  = "<tr>                                                                                                      									" +
								"	<td>																																	" +							
								"		<div class='row'>																													" +
								"			<div class='col-sm-6' id='productImageDiv'>																						" +
								"				<input id='productImages"+i+"' name='productImages' class='form-control' type='text' readonly='readonly' width='200px' value=''>	" +
								"			</div>																															" +
								"			<div class='col-sm-6'>																											" +
								"				<label class='btn btn-primary' for='inp-file"+i+"'><i class='icon iconfont'>&#xe692;</i> 选择文件</label>			" +
								"			</div>																															" +
								"		</div>																																" +
								"	</td>																																	" +
								"	<td>																																	" +
								"		<input id='imgOrders' name='imgOrders' class='form-control' type='text'>																" +
								"	</td>																																	" +
								"	<td>																																	" +
								"		<button type='button' onclick='removeImage(this)' class='btn btn-white btn-sm'>删除 </button>											" +
								"	</td>																																	" +
								"</tr>																																		";
					$("#tt").append(html);
					var form = "<form id='fileForm"+i+"'><input name='file' type='file' id='inp-file"+i+"' onchange=\"upload('#productImages"+i+"','#fileForm"+i+"')\" style='display: none;'></form>";
					$("#forms").append(form);
				});
			
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

            function show() {


				var products = ${productJson};

                var $tr = $('.tr');
                $.each($tr, function (i, n) {
                    var spec_values = $(n).children('td[name=spec_values]').children('input').val();
                    $.each(products, function (i, j) {
                        if (j.specName === spec_values) {
                            $(n).children('td[name=specImg]').children('input').val(j.imgUrl);
                            $(n).children('td[name=specImg]').children('label').children('img').attr('src', j.imgUrl);
                            $(n).children('td[name=price]').children('input').val(j.price);
                            $(n).children('td[name=stock]').children('input').val(j.stock);
                            $(n).children('td[name=people]').children('input').val(j.shareNum);
                            $(n).children('td[name=tableware]').children('input').val(j.tableware);
                            $(n).children('td[name=size]').children('input').val(j.size);
                            $(n).children('td[name=maxPoint]').children('input').val(j.maxOffsetPoint);
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
                            var itemColumHead = $("<th style=\"width:80px;\">规格图片(320*160)<span class=\"maroon\">*</span></th><th style=\"width:150px;\">价格<span class=\"maroon\">*</span></th><th style=\"width:150px;\">库存<span class=\"maroon\">*</span></th><!--<th style=\"width:150px;\">编码</th>--><th style=\"width:150px;\">用餐人数<span class=\"maroon\">*</span></th><th style=\"width:150px;\">餐具数<span class=\"maroon\">*</span></th> <th style=\"width:150px;\">尺寸<span class=\"maroon\">*</span><br>（cm*cm）</th> <th style=\"width:150px;\">积分最大抵扣额<span class=\"maroon\">*</span></th>");
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
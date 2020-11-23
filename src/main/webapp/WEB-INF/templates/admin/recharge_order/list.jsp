<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <title>充值明细管理-列表</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${respath}/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="${respath}/admin/css/animate.min.css" rel="stylesheet">
    <link href="${respath}/admin/css/iconfont.css" rel="stylesheet">
    <link href="${respath}/admin/css/style.min862f.css" rel="stylesheet">
    <link href="${respath}/admin/lib/laydate/need/laydate.css" rel="stylesheet">
    <link href="${respath}/admin/css/public.css" rel="stylesheet">
    <script src="${respath}/admin/js/jquery.min.js"></script>
    <script src="${respath}/admin/js/bootstrap.min.js"></script>
    <script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
    <script src="${respath}/admin/lib/laydate/laydate.js"></script>
    <style type="text/css">
        .productCategory-list table tr td {
            border-top: none;
            border: 1px solid #e7eaec;
            padding: 5px 10px;
            vertical-align: middle;
        }
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <form role="form" class="form-inline" action="${base}/admin/recharge_order/list" method="get">
        <div class="row">
            <div class="ibox">
                <div class="ibox-content">
                    <span class="text-muted small pull-right">共有数据：${page.total} 条</span>
                    <div class="form-group">
                        <select class="form-control" name="type" id="type">
                            <option selected="selected" value="">请选择充值类型</option>
                            <option <c:if test="${type==0}">selected</c:if> value="0">平台充值</option>
                            <option <c:if test="${type==1}">selected</c:if> value="1">分销商充值</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <select class="form-control" name="agentId" id="agentId">
                            <option value="">请选择分销商</option>
                            <c:forEach items="${agentList}" var="agent">
                                <option <c:if test="${agent.id==agentId}">selected</c:if> value="${agent.id}">${agent.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" id="startDate" class="form-control" name="startDate" value="${startDate}" placeholder="请输入开始日期">
                    </div>
                    <div class="form-group">
                        <input type="text" id="endDate" name="endDate" value="${endDate}" class="form-control" placeholder="请输入结束日期" >
                    </div>
                    <div class="form-group">
                        <input type="text" id="nickname" class="form-control" name="nickname" value="${nickname}" placeholder="请输入会员昵称">
                    </div>
                    <%--
                    <div class="form-group">
                        <select class="form-control" name="type" id="type">
                            <option selected="selected" value="">会员身份选择</option>
                            <option <c:if test="${type==0}">selected</c:if> value="0">普通会员</option>
                            <option <c:if test="${type==1}">selected</c:if> value="1">经销商</option>
                        </select>
                    </div>
                    --%>

                    <button onclick="query()" type="button" class="btn btn btn-primary"> <i class="icon iconfont">&#xe607;</i> 搜索</button>

                    <button type="submit" class="btn btn btn-primary" onclick="importExcel()"> <i class="icon iconfont">&#xe607;</i> 导出excel</button>

                    <div class="productCategory-list m-t-md">
                        <table class="table table-striped table-hover table-bordered">
                            <thead>
                            <tr class="text-c">
                               <%-- <th style="width: 50px">
                                    <input type="checkbox" value="2" name="" id="box">
                                </th>--%>
                                <th>序号</th>

                                <th>昵称</th>
                                <th>头像</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>当前余额</th>

                                <th>充值单号</th>
                                <th>充值金额</th>
                                <th>赠送金额</th>
                                <th>充值时间</th>
                                <th>到账时间</th>
                                <th>充值类型</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${page.content}" var="data" varStatus="status" >
                                <tr>
                                    <%--<td>
                                        <input type="checkbox" value="${data.id}" name="ids" class="boxs">
                                    </td>--%>
                                    <td>${status.index+1}</td>
                                    <td>${data.nickname}</td>
                                    <td>
                                        <img src="${data.avatarUrl}" width="50">
                                    </td>
                                    <td>${data.name}</td>
                                    <td>${data.mobile}</td>
                                    <%--
                                    <td>
                                        <c:if test="${data.memberType==0}" >普通会员</c:if>
                                        <c:if test="${data.memberType==1}" >经销商</c:if>
                                    </td>
                                    --%>
                                    <td>${data.balance}</td>

                                    <td>${data.paySn}</td>
                                    <td>
                                        ${data.amount}
                                    </td>
                                    <td>
                                        ${data.giveAmount}
                                    </td>
                                    <td><fmt:formatDate value="${data.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${data.payDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <c:if test="${data.type==0}">平台充值</c:if>
                                        <c:if test="${data.type==1}">分销商充值(${data.agentName})</c:if>
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
    </form>
</div>

<script>

    $(function() {

        $("#box").change(function() {
            if ($("#box").is(":checked")) {
                $(".boxs").prop("checked", true);
            } else {
                $(".boxs").prop("checked", false);
            }
        });

        $(".full-height-scroll").slimScroll({
            height: "100%"
        });

        $("thead input:checkbox").each(function() {
            if ($(this).attr("checked") === true) {
                alert("1");
                //$(this).parents('table').children("tbody input[type='checkbox'] ")
            }
        });
        laydate.render({
            elem: '#startDate',
            type: 'datetime'
        });

        laydate.render({
            elem: '#endDate',
            type: 'datetime'
        });
    });

    function adver_add(title, url, w, h) {
        layer_show(title, url, w, h);
    }

    function admin_del() {
        var $ids = $("input[name='ids']:checked");
        if($ids.length===0){
            layer.msg("请选择充值项");
            return;
        }
        layer.confirm('确认要删除吗？', function(index) {
            $.ajax({
                type: 'POST',
                url: '${base}/admin/tag/delete',
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

    function query(){
        document.forms[0].method = "get";
        document.forms[0].target = "_self";
        document.forms[0].action = "${base}/admin/recharge_order/list";
        document.forms[0].submit();
    }

    //导出
    function importExcel(){
        document.forms[0].method = "post";
        document.forms[0].target = "_blank";
        document.forms[0].action = "${base}/admin/recharge_order/exportExcel";
        document.forms[0].submit();
    }

</script>
</body>

</html>
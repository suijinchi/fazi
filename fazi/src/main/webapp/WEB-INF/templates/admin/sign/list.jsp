<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
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
    <form role="form" class="form-inline" method="get" action="${base}/admin/sign/list">
        <div class="row">
            <div class="ibox">
                <div class="ibox-content">
                    <div>
                        <span class="text-muted small pull-right">共有数据：${page.total} 条</span>
                        <div class="form-group">
                            <input type="text" id="date" class="form-control" name="date" value="${date}" placeholder="请输入日期">
                        </div>
                        <button type="submit" class="btn btn btn-primary">
                            <i class="icon iconfont">&#xe607;</i> 搜索
                        </button>
                    </div>

                    <div class="clients-list">
                        <div class="member-list table-responsive">
                            <table class="table table-striped table-hover table-bordered table-condensed">
                                <thead>
                                <tr class="text-c">
                                    <th>
                                        <div class="check-box">
                                            <input type="checkbox" id="selectAll">
                                            <label>&nbsp;</label>
                                        </div>
                                    </th>
                                    <th>昵称</th>
                                    <th>性别</th>
                                    <th>头像</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>今日签到时间</th>
                                    <th>连续签到次数</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${page.content}" var="data" varStatus="status">
                                    <tr>
                                        <td>
                                            <input type="checkbox" value="${data.id}" name="ids">
                                        </td>
                                        <td title="${data.nickname}">
                                            <a data-toggle="tab" href="#contact-1" class="client-link">${data.nickname}</a>
                                        </td>
                                        <td>
                                            <a data-toggle="tab" href="#contact-1" class="client-link">
                                                <c:if test="${data.gender==1}">男</c:if>
                                                <c:if test="${data.gender!=1}">女</c:if>
                                            </a>
                                        </td>
                                        <td>
                                            <img src="${data.avatarUrl}" width="35"/>
                                        </td>
                                        <td>
                                                ${data.name}
                                                <c:if test="${data.name==null||data.name==''}">-</c:if>
                                        <td>
                                                ${data.mobile}
                                                <c:if test="${data.mobile==null||data.mobile==''}">-</c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${data.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${data.signSerialTimes}
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
    </form>
</div>


<script>
    $(function () {
        laydate.render({
            elem: '#date' //指定元素
        });
        $("#selectAll").change(function() {
            if ($("#selectAll").is(":checked")) {
                $("input[name='ids']").prop("checked", true);
            } else {
                $("input[name='ids']").prop("checked", false);
            }
        });
    });

</script>

</body>

</html>
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <title>法滋蛋糕 后台管理系统</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%-- <%@ include file="/WEB-INF/templates/admin/include/common.jsp" %> --%>

    <link href="${respath}/admin/css/bootstrap.min.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/animate.min.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/font-awesome.min93e3.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/iconfont.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/style.min862f.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/lib/laydate/need/laydate.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/lib/icheck/icheck.css" rel="stylesheet">
    <link href="${respath}/admin/css/public.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/plugins/iCheck/custom.css?v=${ver}" rel="stylesheet">
    <link href="${respath}/admin/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

    <script src="${respath}/admin/js/jquery.min.js?v=${ver}"></script>
    <script src="${respath}/admin/js/bootstrap.min.js?v=${ver}"></script>
    <script src="${respath}/admin/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${respath}/admin/js/plugins/slimscroll/jquery.slimscroll.min.js?v=${ver}"></script>
    <script type="text/javascript" src="${respath}/admin/lib/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="${respath}/admin/js/layer-yw.js"></script>
    <script src="${respath}/admin/js/plugins/layer/layer.min.js"></script>
    <script src="${respath}/admin/js/hplus.min.js?v=4.1.0"></script>
    <script src="${respath}/admin/js/contabs.min.js?id=00"></script>
    <script src="${respath}/admin/js/plugins/pace/pace.min.js"></script>
    <script src="${respath}/admin/js/base64.js?v=${ver}"></script>

    <script type="text/javascript">
        /*密码修改*/
        function edit_password(title, url, w, h) {
            layer_show(title, url, w, h);
        }
    </script>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="" src="${respath}/admin/images/ld-logo.png" width="80px"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
									<span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${admin.username }</strong></span>
									<span class="text-muted text-xs block">${admin.name }<b class="caret"></b></span>
									</span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="javascript:"
                                   onclick="layer_show('修改密码','${base}/admin/main/editPassword','800','400')">修改密码</a>
                            </li>
                            <%--<li><a href="${base}/admin/logout">安全退出</a></li>--%>
                        </ul>
                    </div>
                    <div class="logo-element">法兹蛋糕
                    </div>
                </li>

                <c:forEach items="${sysMenuList}" var="sysMenu">
                    <c:if test="${sysMenu.grade==0 && sysMenu.isShow=='1'}">
                        <li>
                            <a href="#">
                                <i class="icon iconfont">${sysMenu.icon}</i>
                                <span class="nav-label">
                                        ${sysMenu.name}
                                </span>
                                <span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level">
                                <c:forEach items="${sysMenuList }" var="sm">
                                    <c:if test="${sm.grade==1 && sm.parentId==sysMenu.id && sm.isShow=='1'}">
                                        <li>
                                            <a class="J_menuItem" href="${base}${sm.url}">
                                                    ${sm.name}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <!--background: #2f4050;-->
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0;">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i
                        class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post"
                          action="http://www.zi-han.net/theme/hplus/search_results.html">
                        <!-- <div class="form-group">
                            <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                        </div> -->
                    </form>
                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i> <span class="label label-primary">
                            ${shopCount+editShopCount+unshippingMemberCount+unshippingPlatCount+uncompleteMemberCount+uncompletePlatCount+productCount}
                        </span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">

                            <c:forEach items="${sysMenuList }" var="sm">

                                <c:if test="${sm.grade==1 && sm.isShow=='1' && sm.id==15}">

                                    <li>
                                        <a class="J_menuItem"
                                           href="${base }/admin/orders/member?orderStatus=1&payStatus=1&sendStatus=0">
                                            <div>
                                                <i class="fa fa-truck"></i> 会员礼包待发货订单
                                                <span class="pull-right text-muted small">${unshippingMemberCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                    <li>
                                        <a class="J_menuItem"
                                           href="${base}/admin/orders/member?orderStatus=1&payStatus=1&sendStatus=2">
                                            <div>
                                                <i class="fa fa-gift"></i> 会员礼包待完成订单
                                                <span class="pull-right text-muted small">${uncompleteMemberCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                </c:if>

                                <c:if test="${sm.grade==1 && sm.isShow=='1' && sm.id==16}">

                                    <li>
                                        <a class="J_menuItem"
                                           href="${base }/admin/orders/plat?orderStatus=1&payStatus=1&sendStatus=0">
                                            <div>
                                                <i class="fa fa-truck"></i> 平台商品待发货订单
                                                <span class="pull-right text-muted small">${unshippingPlatCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                    <li>
                                        <a class="J_menuItem"
                                           href="${base}/admin/orders/plat?orderStatus=1&payStatus=1&sendStatus=2">
                                            <div>
                                                <i class="fa fa-gift"></i> 平台商品待完成订单
                                                <span class="pull-right text-muted small">${uncompletePlatCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                </c:if>

                                <c:if test="${sm.grade==1 && sm.isShow=='1' && sm.id==29}">

                                    <li>
                                        <a class="J_menuItem" href="${base }/admin/shop/wait">
                                            <div>
                                                <i class="fa fa-eye"></i> 待审核店铺
                                                <span class="pull-right text-muted small">${shopCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                </c:if>

                                <c:if test="${sm.grade==1 && sm.isShow=='1' && sm.id==30}">

                                    <li>
                                        <a class="J_menuItem" href="${base }/admin/shop/review?status=0">
                                            <div>
                                                <i class="fa fa-edit"></i> 待审核店铺修改
                                                <span class="pull-right text-muted small">${editShopCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                </c:if>

                                <c:if test="${sm.grade==1 && sm.isShow=='1' && sm.id==18}">

                                    <li>
                                        <a class="J_menuItem" href="${base}/admin/product/shopList?status=0">
                                            <div>
                                                <i class="fa fa-envelope fa-fw"></i> 待审核商品
                                                <span class="pull-right text-muted small">${productCount }个</span>
                                            </div>
                                        </a>
                                    </li>
                                    <li class="divider"></li>

                                </c:if>

                            </c:forEach>

                            <%--<li>
                                <a class="J_menuItem" href="${base }/admin/orders/member?orderStatus=1&payStatus=1&sendStatus=0">
                                    <div>
                                        <i class="fa fa-truck"></i> 会员礼包待发货订单
                                        <span class="pull-right text-muted small">${unshippingMemberCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="J_menuItem" href="${base }/admin/orders/plat?orderStatus=1&payStatus=1&sendStatus=0">
                                    <div>
                                        <i class="fa fa-truck"></i> 平台商品待发货订单
                                        <span class="pull-right text-muted small">${unshippingPlatCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>

                            <li>
                                <a class="J_menuItem" href="${base}/admin/orders/member?orderStatus=1&payStatus=1&sendStatus=2">
                                    <div>
                                        <i class="fa fa-gift"></i> 会员礼包待完成订单
                                        <span class="pull-right text-muted small">${uncompleteMemberCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a class="J_menuItem" href="${base}/admin/orders/plat?orderStatus=1&payStatus=1&sendStatus=2">
                                    <div>
                                        <i class="fa fa-gift"></i> 平台商品待完成订单
                                        <span class="pull-right text-muted small">${uncompletePlatCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>

                            <li>
                                <a class="J_menuItem" href="${base }/admin/shop/wait">
                                    <div>
                                        <i class="fa fa-eye"></i> 待审核店铺
                                        <span class="pull-right text-muted small">${shopCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>

                            <li>
                                <a class="J_menuItem" href="${base }/admin/shop/review?status=0">
                                    <div>
                                        <i class="fa fa-edit"></i> 待审核店铺修改
                                        <span class="pull-right text-muted small">${editShopCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>

                            <li>
                                <a class="J_menuItem" href="${base}/admin/product/shopList?status=0">
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 待审核商品
                                        <span class="pull-right text-muted small">${productCount }个</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>--%>

                            <!--
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="J_menuItem" href="notifications.html">
                                        <strong>查看所有 </strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>
                            </li>
                            -->
                        </ul>
                    </li>
                    <!--
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>
                    -->
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="${base}/admin/main/index" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${base}/admin/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i>
                退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${base}/admin/main/welcome"
                    frameborder="0" data-id="" seamless>

            </iframe>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">

            <ul class="nav nav-tabs navs-3">

                <li class="active">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-2">
                    通知
                </a>
                </li>
                <li><a data-toggle="tab" href="#tab-3">
                    项目进度
                </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox"
                                           id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox"
                                           id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
									<span>
                        固定宽度
                    </span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox"
                                           id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
									<span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                        </div>
                        <div class="setings-item blue-skin nb">
									<span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
									<span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                        </div>
                    </div>
                </div>
                <div id="tab-2" class="tab-pane">

                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 最新通知</h3>
                        <small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
                    </div>

                    <div>

                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">

                                    据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">
                                </div>
                                <div class="media-body">
                                    HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    写的好！与您分享
                                    <br>
                                    <small class="text-muted">昨天 1:10</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">
                                </div>

                                <div class="media-body">
                                    国外极限小子的炼成！这还是亲生的吗！！
                                    <br>
                                    <small class="text-muted">昨天 8:37</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">
                                </div>
                                <div class="media-body">

                                    一只流浪狗被收留后，为了减轻主人的负担，坚持自己觅食，甚至......有些东西，可能她比我们更懂。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">
                                </div>
                                <div class="media-body">
                                    这哥们的新视频又来了，创意杠杠滴，帅炸了！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    最近在补追此剧，特别喜欢这段表白。
                                    <br>
                                    <small class="text-muted">昨天 1:10</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="">
                                </div>
                                <div class="media-body">
                                    我发起了一个投票 【你认为下午大盘会翻红吗？】
                                    <br>
                                    <small class="text-muted">星期一 8:37</small>
                                </div>
                            </a>
                        </div>
                    </div>

                </div>
                <div id="tab-3" class="tab-pane">

                    <div class="sidebar-title">
                        <h3><i class="fa fa-cube"></i> 最新任务</h3>
                        <small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>
                    </div>

                    <ul class="sidebar-list">
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>市场调研</h4> 按要求接收教材；

                                <div class="small">已完成： 22%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                </div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>可行性报告研究报上级批准 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 48%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 48%;" class="progress-bar"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>立项阶段</h4> 东风商用车公司 采购综合综合查询分析系统项目进度阶段性报告武汉斯迪克科技有限公司

                                <div class="small">已完成： 14%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-primary pull-right">NEW</span>
                                <h4>设计阶段</h4>
                                <!--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                项目进度报告(Project Progress Report)
                                <div class="small">已完成： 22%</div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>拆迁阶段</h4> 科研项目研究进展报告 项目编号: 项目名称: 项目负责人:

                                <div class="small">已完成： 22%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                </div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>建设阶段 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 48%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 48%;" class="progress-bar"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>获证开盘</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 14%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                </div>
                            </a>
                        </li>

                    </ul>

                </div>
            </div>

        </div>
    </div>
    <!--右侧边栏结束-->
</div>
</body>
</html>
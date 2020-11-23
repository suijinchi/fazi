<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/mobile/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
	<head>
        <title>领取会员卡</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/iconfont.css" />
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/swiper.min.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/fzcake.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/weui.min.css">
        <link rel="stylesheet" type="text/css" href="${respath}/mobile/css/jquery-weui.css">
        <style type="text/css"></style>

    </head>

    <body>

        <c:if test="${member.mobile==null}">
        <div class="container">
            <div class="perfectInfosBox">
                <div class="item_perfect">
                    <i class="icon iconfont">&#xe6a8;</i>
                    <input id="name" type="text" class="weui-input inp_pf" placeholder="姓名" name="name" value="" />
                </div>
                <div class="item_perfect">
                    <i class="icon iconfont">&#xe6b2;</i>
                    <input id="mobile" type="tel" class="weui-input inp_pf" placeholder="输入手机号" name="mobile" value="" />
                </div>
                <div class="item_perfect">
                    <i class="icon iconfont">&#xe6b0;</i>
                    <input id="msgCode" type="tel" class="weui-input inp_pf" placeholder="短信验证码" name="msgCode" value="" style="width: 30%" />
                    <a id="sendMsgBtns" class="huoqu hq-01">获取验证码</a>
                   <%-- <a id="countDown" class="huoqu hq-02" style="border: 1px solid #ccc;color: #666;">60S</a>--%>
                </div>
                <div class="item_perfect">
                    <i class="icon iconfont">&#xe6f9;</i>
                    <input id="birthday" type="date" class="weui-input inp_pf" placeholder="请选择生日" name="birthday" value="" />
                </div>
            </div>
            <input id="submit" class="weui-btn weui-btn_primary" value="确定" type="button" style="width: 80%;margin: 6% auto;background: #ff7d84;font-size:1em;">
        </div>
        </c:if>

        <c:if test="${member.mobile!=null}">
            <div class="container">
                <div class="perfectInfosBox">
                    <div class="item_perfect">
                        <i class="icon iconfont">&#xe6a8;</i>
                        <input readonly id="name" type="text" class="weui-input inp_pf" placeholder="姓名" name="name" value="${member.name}" />
                    </div>
                    <div class="item_perfect">
                        <i class="icon iconfont">&#xe6b2;</i>
                        <input readonly id="mobile" type="tel" class="weui-input inp_pf" placeholder="输入手机号" name="mobile" value="${member.mobile}" />
                    </div>
                    <div class="item_perfect">
                        <i class="icon iconfont">&#xe6f9;</i>
                        <input readonly id="birthday" type="text" class="weui-input inp_pf" placeholder="请选择生日" name="birthday" value="${member.birthday}" />
                    </div>
                </div>
            </div>
        </c:if>

        <script type="text/javascript" src="${respath}/mobile/js/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="${respath}/mobile/js/swiper.min.js"></script>
        <script type="text/javascript" src="${respath}/mobile/js/jquery-weui.js"></script>
        <script type="text/javascript" src="${respath}/mobile/js/fastclick.js"></script>
        <script type="text/javascript" src="${respath}/mobile/Swiper/js/swiper.min.js"></script>
        <script type="text/javascript" src="${respath}/mobile/js/valid.js"></script>
        <script type="text/javascript">


            var curCount=60;
            var flag = true;
            $sendMsgBtns = $("#sendMsgBtns");
            $sendMsgBtns.click(function(){
                if(flag){
                    sendMsg();
                }
            });
            function sendMsg() {
                var mobile = $("[name='mobile']").val();
                if(!mobile){
                    $.toast("请填写手机号","text");
                    return;
                }
                jQuery.ajax({
                    type : "post",
                    url : "sendMsgCode",
                    data : {
                        mobile:mobile
                    },
                    dataType : "json",
                    beforeSend : function() {
                        flag = false;
                    },
                    success : function(data) {
                        if (data.type == "success") {
                            $.toast(data.content,"text");
                            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
                        } else if (data.type == "error") {
                            $.toast(data.content,"text");
                            flag = true;//启用按钮
                        }
                    },error:function(){
                        alert("系统异常");
                        flag = true;
                    }
                });
            };
            //按钮定时器
            function SetRemainTime() {
                if (curCount == 1) {
                    window.clearInterval(InterValObj);   //停止计时器
                    flag = true;;//启用按钮
                    $sendMsgBtns.html("获取验证码");
                    curCount = 60;
                } else {
                    curCount--;
                    $sendMsgBtns.html(curCount + "秒");
                }
            };

            $(function() {
                $("#birthday").val("${member.birthday}");
                $("#submit").click(function(){
                    var name = $("#name").val();
                    var mobile = $("#mobile").val();
                    var msgCode = $("#msgCode").val();
                    var birthday = $("#birthday").val();
                    if(!name){
                        $.toast("请填写姓名","text");
                        return;
                    }
                    if(!mobile){
                        $.toast("请填写手机号","text");
                        return;
                    }
                    if(!isPhone(mobile)){
                        $.toast("请填写正确的手机号","text");
                        return;
                    }
                    if(!birthday){
                        $.toast("请填写生日","text");
                        return;
                    }
                    $("#submit").prop("disabled",true);
                    $.ajax({
                        type:"post",
                        dataType:"json",
                        data:{
                            name:name,
                            mobile:mobile,
                            msgCode:msgCode,
                            birthday:birthday
                        },
                        url:"submitInfo",
                        success:function(data){
                            if(data.type=="success"){
                                $.toast(data.content,"text",function(){
                                    window.location.replace("${base}/mobile/member/index");
                                });
                            }else{
                                $.toast(data.content,"text");
                                $("#submit").prop("disabled",false);
                            }
                        },
                        error:function(){
                            $.toast("系统异常","text");
                            $("#submit").prop("disabled",false);
                        }
                    });
                });
            });
        </script>
    </body>

</html>
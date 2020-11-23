<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/templates/admin/include/taglib.jsp" %>
<!DOCTYPE html>
<html>

	<head>

		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<%@ include file="/WEB-INF/templates/admin/include/common.jsp" %>
		<script src="${respath}/admin/js/jquery.form.js"></script>	
		<title>系统设置</title>
		<meta name="keywords" content="">
		<meta name="description" content="">

		<script type="text/javascript">
		
			function uploadImage(formId,inputId){
				var options = {
					url : '${base}/admin/common/upload?fileType=image',
					type : 'post',
					dataType : 'json',
					success : function(data) {
						console.log(data);
						if (data.type == "success") {
							$('#'+inputId).val(data.data.absolute);
                            $(".imgDivppp").remove();
							$("#"+inputId).parent().append('<div class="imgDivppp"><img src="'+data.data.absolute+'" width="100"></div>');
						}
					},
					error : function(data) {
						console.log(data);
					}
				};
				$("#"+formId).ajaxSubmit(options);
			}	
		
			$(function () {
				
				var $submit = $("#submit");
				$("#dataForm").validate({
					rules:{
						msgUid:{
							required:true
						},
						msgKey:"required",
						ip:"required",
						domain:"required",
						cookieDomain:"required",
						cookiePath:"required",
						ipMaxMsgPerDay:{
							required:true,
							number:true,
							min:0
						},
						mobileMaxMsgPerDay:{
							required:true,
							number:true,
							min:0
						},
						msgExpireTime:{
							required:true,
							number:true,
							min:1,
							max:24*60
						},
						withdrawalCmsFee:{
							required:true,
							isFloat:true
						},
						withdrawSalesMin:{
							required:true,
							isFloat:true
						},
						withdrawalSalesFee:{
							required:true,
							isFloat:true
						},
						withdrawCmsMin:{
							required:true,
							isFloat:true
						},
                        shippingFee:{
							required:true,
							isFloat:true
						},
                        minRechargeAmount:{
							required:true,
							isFloat:true
						},
                        pointRatio:{
							required:true,
							number:true
						},
                        csTel:{
                            required:true
                        },
                        qrcodeImgUrl:{
                            required:true
                        },
                        recommendCouponId:{
                            digits:true
						},
                        registeCouponId:{
                            digits:true
						},
						signPoint:{
                            required:true,
                            digits:true
                        },
					},
					onkeyup:false,
					focusCleanup:true,
					success:"valid",
					submitHandler:function(form){
						$.ajax({
							type:"post",
							url:"${base}/admin/setting/update",
							dataType:"json",
							data:$("#dataForm").serialize(),
							beforeSend:function(){
								$submit.prop("disabled",true);
							},
							success:function(data){
								if(data.type==="success"){
									layer.msg(data.content, {icon: 1,time: 2000}, function(){
										location.reload();
									});

								}else{
									layer.msg(data.content, {icon: 0});
									$submit.prop("disabled",false);
								}
							}
						});
						return false;
					}
				});
				
			});
			
			
			
		</script>
		
	</head>

	<body>
		
		<div class="row">
			<form action="save" class="form-horizontal" id="dataForm" method="post" onsubmit="return false">
		        
		        <div class="wrapper wrapper-content animated fadeInRight">
		            <div class="tabs-container">
		                <ul class="nav nav-tabs">

							<li class="active">
								<a data-toggle="tab" href="#tab-base" aria-expanded="true"> 基本设置</a>
							</li>

							<li>
								<a data-toggle="tab" href="#tab-popup" aria-expanded="true"> 弹窗设置</a>
							</li>

							<li>
								<a data-toggle="tab" href="#tab-msg" aria-expanded="false"> 短信设置</a>
							</li>

		                    <li class="" style="display:none">
		                        <a data-toggle="tab" href="#tab-system" aria-expanded="false"> 系统设置</a>
		                    </li>

		                </ul>
		                <div class="ibox-content">
			                <div class="tab-content ">

								<div id="tab-base" class="tab-pane active">

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;签到积分：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="签到积分" value="${setting.signPoint}" id="signPoint" name="signPoint" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;签到满30天送券id：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="签到满30天送券id" value="${setting.signCouponId}" id="signCouponId" name="signCouponId" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;推荐会员获得的券类型id：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="老会员推荐获得的优惠券id" value="${setting.recommendCouponId}" id="recommendCouponId" name="recommendCouponId" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;推荐会员注册获得的积分：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="推荐注册获得的积分" value="${setting.registePoint}" id="registePoint" name="registePoint" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;会员首页进入商城获得的券类型id：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="员首页进入商城获得的优惠券类型id" value="${setting.registeCouponId}" id="registeCouponId" name="registeCouponId" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;评论商品赠送的积分：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="评论商品赠送的积分" value="${setting.assessPoint}" id="assessPoint" name="assessPoint" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">&nbsp;会员福利链接地址：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="会员福利链接地址" value="${setting.memberWelfareUrl}" id="memberWelfareUrl" name="memberWelfareUrl" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;积分比例：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="积分比例,抵扣1元需要多少积分" value="${setting.pointRatio}" id="pointRatio" name="pointRatio" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;最小充值金额：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="最小充值金额" value="${setting.minRechargeAmount}" id="minRechargeAmount" name="minRechargeAmount" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;客服电话：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="客服电话" value="${setting.csTel}" id="csTel" name="csTel" class="form-control">
										</div>
									</div>

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;二维码界面banner：</label>
                                        <div class="col-sm-9">
                                            <input type="text" placeholder="二维码界面banner" value="${setting.qrcodeImgUrl}" id="qrcodeImgUrl" name="qrcodeImgUrl" class="form-control" style="display: inline-block;width: 500px;line-height: 34px;height: 34px;float: left;">
                                            &nbsp;&nbsp;
                                            <label for="qrcodeImgUrlFile">
                                                <a class="btn btn-primary upload-btn"><i class="icon iconfont">&#xe692;</i> 选择图片</a>
                                            </label>
                                            <div>尺寸：750*375</div>
                                            <c:if test="${setting.qrcodeImgUrl!=null&&setting.qrcodeImgUrl!=''}">
                                                <div class="imgDivppp"><img src="${setting.qrcodeImgUrl}" width="100"></div>
                                            </c:if>
                                        </div>
                                    </div>

								</div>

								<div id="tab-popup" class="tab-pane">
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;是否开启弹窗：</label>
										<div class="col-sm-8">
											<div class="radio radio-info radio-inline">
												<input type="radio" id="popupSwitch0" name="popupSwitch" value="1" <c:if test="${setting.popupSwitch==1}">checked="checked"</c:if>>
												<label for="popupSwitch0">是</label>
											</div>
											<div class="radio radio-info radio-inline">
												<input type="radio" id="popupSwitch1" name="popupSwitch" value="0" <c:if test="${setting.popupSwitch==0}">checked="checked"</c:if>>
												<label for="popupSwitch1">否</label>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;弹窗标题：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="弹窗标题" value="${setting.popupTitle}" id="popupTitle" name="popupTitle" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;弹窗按钮：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="弹窗按钮" value="${setting.popupBtnText}" id="popupBtnText" name="popupBtnText" class="form-control">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;弹窗链接地址：</label>
										<div class="col-sm-9">
											<input type="text" placeholder="弹窗链接地址" value="${setting.popupLink}" id="popupLink" name="popupLink" class="form-control">

										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;弹窗内容：</label>
										<div class="col-sm-9">
											<input type="text" placeholder="弹窗内容" value="${setting.popupContent}" id="popupContent" name="popupContent" class="form-control">
											<div>最多输入50个字</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;客服二维码：</label>
										<div class="col-sm-9">
											<input type="text" placeholder="弹窗图片" value="${setting.popupImgUrl}" id="popupImgUrl" name="popupImgUrl" class="form-control" style="display: inline-block;width: 500px;line-height: 34px;height: 34px;float: left;">
											&nbsp;&nbsp;
											<label for="popupFile">
												<a class="btn btn-primary upload-btn"><i class="icon iconfont">&#xe692;</i> 选择图片</a>
											</label>
											<div>尺寸：750*415</div>
											<c:if test="${setting.popupImgUrl!=null&&setting.popupImgUrl!=''}">
												<div class="imgDivppp"><img src="${setting.popupImgUrl}" width="100"></div>
											</c:if>
										</div>
									</div>

								</div>

								<div id="tab-msg" class="tab-pane">
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;短信id：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="短信id" value="${setting.msgUid}" id="msgUid" name="msgUid" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;短信key：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="短信key" value="${setting.msgKey}" id="msgKey" name="msgKey" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;短信签名：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="短信签名" value="${setting.msgSign}" id="msgSign" name="msgSign" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;注册短信内容：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="注册短信内容" value="${setting.msgContent}" id="msgContent" name="msgContent" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;是否发送短息：</label>
										<div class="col-sm-8">

											<div class="radio radio-info radio-inline">
												<input type="radio" id="s1" name="isSendMsg" value="true" <c:if test="${setting.isSendMsg=='true'}">checked="checked"</c:if> >
												<label for="s1">是</label>
											</div>
											<div class="radio radio-info radio-inline">
												<input type="radio" id="s2" name="isSendMsg" value="false" <c:if test="${setting.isSendMsg=='false'}">checked="checked"</c:if>>
												<label for="s2">否</label>
											</div>

										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;ip每天发送量：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="ip每天发送量" value="${setting.ipMaxMsgPerDay}" id="ipMaxMsgPerDay" name="ipMaxMsgPerDay" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;手机号每天发送量：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="手机号每天发送量" value="${setting.mobileMaxMsgPerDay}" id="mobileMaxMsgPerDay" name="mobileMaxMsgPerDay" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;短信发送间隔(秒)：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="短信发送间隔(秒)" value="${setting.msgIntervalTime}" id="msgIntervalTime" name="msgIntervalTime" class="form-control">
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;短信有效时间(分)：</label>
										<div class="col-sm-8">
											<input type="text" placeholder="短信有效时间(分)" value="${setting.msgExpireTime}" id="msgExpireTime" name="msgExpireTime" class="form-control">
										</div>
									</div>
								</div>

			                    <div id="tab-system" class="tab-pane">

									<div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;ip：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="ip" id="ip" name="ip" value="${setting.ip}" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;域名：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="域名" value="${setting.domain}" id="domain" name="domain" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;cookie域名：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="cookie域名" value="${setting.cookieDomain}" id="cookieDomain" name="cookieDomain" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;cookie存储路径：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="cookie存储路径" value="${setting.cookiePath}" id="cookiePath" name="cookiePath" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;图片上传扩展名：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="图片上传扩展名" value="${setting.uploadImageExtension}" id="uploadImageExtension" name="uploadImageExtension" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;图片上传路径：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="图片上传路径" value="${setting.uploadImagePath}" id="uploadImagePath" name="uploadImagePath" class="form-control">
			                            </div>
			                        </div>	
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;文件上传扩展名：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="文件上传扩展名" value="${setting.uploadDocExtension}" id="uploadDocExtension" name="uploadDocExtension" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;文件上传路径：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="文件上传路径" value="${setting.uploadDocPath}" id="uploadDocPath" name="uploadDocPath" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;文件下载路径：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="文件下载路径" value="${setting.downloadPath}" id="downloadPath" name="downloadPath" class="form-control">
			                            </div>
			                        </div>		
			                         <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;视频上传扩展名：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="视频上传扩展名" value="${setting.uploadMediaExtension}" id="uploadMediaExtension" name="uploadMediaExtension" class="form-control">
			                            </div>
			                        </div>
			                        <div class="form-group">
			                            <label class="col-sm-3 control-label"><i class="icon iconfont icon-need">&#xe657;</i>&nbsp;视频上传路径：</label>
			                            <div class="col-sm-8">
			                                <input type="text" placeholder="视频上传路径" value="${setting.uploadMediaPath}" id="uploadMediaPath" name="uploadMediaPath" class="form-control">
			                            </div>
			                        </div>
			                    </div>
			                    
			                </div>
			            </div>
		            </div>
		            
		            <div class="form-group">
	                    <div class="col-sm-offset-3 col-sm-8">
	                        <button class="btn btn-sm btn-white" id="submit" type="submit">保存</button>
	                    </div>
	                </div>
		            
		        </div>
			</form>			
	    </div>
	    
	    <form id="popupFileForm">
	    	<input type="file" name="file" id="popupFile" onchange="uploadImage('popupFileForm','popupImgUrl');" style="display: none;">
	    </form>
	    <form id="qrcodeImgUrlForm">
	    	<input type="file" name="file" id="qrcodeImgUrlFile" onchange="uploadImage('qrcodeImgUrlForm','qrcodeImgUrl');" style="display: none;">
	    </form>

	</body>

</html>
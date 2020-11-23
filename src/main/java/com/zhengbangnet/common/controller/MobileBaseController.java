package com.zhengbangnet.common.controller;

import com.zhengbangnet.common.controller.BaseController;
import com.zhengbangnet.common.lang.Message;
import org.weixin4j.Weixin;

import javax.annotation.Resource;

public abstract class MobileBaseController extends BaseController{
	
	/**
	 * 错误视图
	 */
	protected static final String RESULT_TIPS = "/mobile/common/tips";
	
	/**
	 * 错误视图
	 */
	protected static final String ERROR_VIEW = "/error/404";

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("操作错误");

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");
	
	/**
	 * openid 缓存前缀
	 */
	public static final String OPENID_PREFIX = "OPENID_PREFIX";
	
	/**
	 * 微信授权详细信息 缓存前缀
	 */
	public static final String OAUTH2USER_PREFIX = "OAUTH2USER_PREFIX";
	
	/**
	 * 当前登录用户
	 */
	public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

	@Resource(name="weixin")
	protected Weixin weixin;

}

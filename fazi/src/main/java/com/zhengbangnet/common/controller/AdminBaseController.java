package com.zhengbangnet.common.controller;

import com.zhengbangnet.common.lang.Message;

public abstract class AdminBaseController extends BaseController{
	
	/**
	 * 错误视图
	 */
	protected static final String ERROR_VIEW = "/error/404";

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("操作错误");

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");
}

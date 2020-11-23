package com.zhengbangnet.modules.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.OrdersItem;

public interface OrdersItemService extends BaseService<OrdersItem,Long> {

	/**
	 * 根据订单ID查询订单中商品详情
	 * @author suijinchi
	 * @param
	 * @return
	 */
	List<OrdersItem> getByOrdersId(Long orderId);

	/**
	 * 根据订单ID查询
	 * @param orderId
	 * @return
	 */
	List<Map<String,Object>> findByOrdersId(Long orderId);
}
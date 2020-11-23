package com.zhengbangnet.modules.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.OrdersItem;
import com.zhengbangnet.modules.service.OrdersItemService;
import com.zhengbangnet.modules.mapper.OrdersItemMapper;

@Service("ordersItemServiceImpl")
@Transactional(readOnly=true)
public class OrdersItemServiceImpl extends BaseServiceImpl<OrdersItem,Long> implements OrdersItemService{

	@Resource(name="ordersItemMapper")
	private OrdersItemMapper ordersItemMapper;

	@Resource(name="ordersItemMapper")
	public void setBaseDao(BaseMapper<OrdersItem,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/**
	 * 根据订单ID查询订单中商品详情
	 * @author suijinchi
	 * @param
	 * @return
	 */
	@Override
	public List<OrdersItem> getByOrdersId(Long orderId) {
		return ordersItemMapper.getByOrdersId(orderId);
	}

	/**
	 * 根据订单ID查询
	 * @param orderId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findByOrdersId(Long orderId) {
		return ordersItemMapper.findByOrdersId(orderId);
	}

}
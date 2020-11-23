package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.OrdersItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * OrdersItemMapper数据库操作接口类
 * 
 **/

@Repository("ordersItemMapper")
public interface OrdersItemMapper extends BaseMapper<OrdersItem,Long>{

	/**
	 * 根据订单ID查询订单中商品详情
	 * @author suijinchi
	 * @param orderId
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
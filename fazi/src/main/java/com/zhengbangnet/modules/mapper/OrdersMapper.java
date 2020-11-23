package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Orders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * OrdersMapper数据库操作接口类
 * 
 **/

@Repository("ordersMapper")
public interface OrdersMapper extends BaseMapper<Orders,Long>{

	/**
	 * 根据条件分页查询数据-店铺销售 
	 * @param params
	 * @return
	 * add by hawkbird date 2018-03-28
	 */
	public List<Map<String,Object>> findShopSalePageByParams(Map<String, Object> params);
	/**
	 * 根据条件查询数量-店铺销售
	 * @param params
	 * @return
	 * add by hawkbird date 2018-03-28
	 */
	public Long getShopSaleCountByParams(Map<String, Object> params);

	/**
	 * 根据参数查询-店铺销售
	 * @param params
	 * @return
	 * add by hawkbird date 2018-03-28
	 */
	List<HashMap<String,Object>> findShopSaleListByParams(Map<String, Object> params);
	
	/**
	 * 手机端按页查询当前用户订单
	 * add by hawkbird date 2018-03-29
	 */
	List<Map<String,Object>> findOnePageOrdersByParams(Map<String,Object> params);
	
	/**
	 * 根据订单号查询订单
	 * @author suijinchi
	 * @param sn
	 * @return
	 */
	public Orders getBySn(String sn);
	
	/**
	 * 查询未领取有效特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	int unTakedSpecialOrdersCnt(long memberId);
	
	/**
	 * 查询已领取特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId》0,address not is null 或者去空格后不为空)
	 */
	int takedSpecialOrdersCnt(long memberId);
	
	/**
	 * 查询未领取特殊订单ID(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	long queryUntakedSpecialOrdersId(long memberId);
	
	/**
	 * 删除作废的特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-13
	 */
	int delInvalidSpecialOrders(long memberId);
	
	/**
	 * 查询最新的平台订单 10条 
	 * @author hawkbird date 2018-04-13
	 */
	List<Map<String,Object>> queryCurrentPlatOrders();
	
	/**
	 * 根据条件查询已支付订单数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	public Long findOrdersByParams(Map<String, Object> params);
	
	/**
	 * 根据条件查询收入
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	public BigDecimal findAmountByParams(Map<String, Object> params);
	
	/**
	 * 查询店铺销售量排行榜
	 * @author suijinchi
	 * @return
	 */
	public List<Map<String, Object>> findShopSale();
	
	/**
	 * 获取最新已支付未发货的订单
	 * @author suijinchi
	 * @return
	 */
	public List<Orders> findNearestOrders();
	
	/**
	 * 查询店铺销售提现总金额
	 * @author suijinchi
	 * @param map
	 * @return
	 */
	public BigDecimal findShopSaleAmountByParams(Map<String, Object> map);

	/**
	 * 根据订单状态查询订单数量
	 * @param params
	 * @return
	 */
    int countByOrdersStatus(Map<String, Object> params);

    List<HashMap<String,Object>> findListByIds(@Param("ids") Long[] ids);
}
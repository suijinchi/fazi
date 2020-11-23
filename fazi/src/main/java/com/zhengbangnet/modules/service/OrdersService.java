package com.zhengbangnet.modules.service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.Orders;

public interface OrdersService extends BaseService<Orders,Long> {
	/**
	 * 分页查询店铺销售
	 * add by hawkbird date 2018-03-28
	 */
	Page<Map<String, Object>> findShopSalePageByParams(Map<String, Object> params);
	/**
	 * 获取数量
	 * @param params
	 * add by hawkbird date 2018-03-28
	 */
	Long getShopSaleCountByParams(Map<String, Object> params);
	/**
	 * 查询店铺销售
	 * add by hawkbird date 2018-03-28
	 */
	List<HashMap<String,Object>> findShopSaleListByParams(Map<String, Object> params);
	
	/**
	 * 手机端按页查询当前用户订单
	 * add by hawkbird date 2018-03-29
	 */
	List<Map<String,Object>> findOnePageOrdersByParams(Map<String,Object> params);
	
	/**
	 * 单个商品购买生成订单
	 * @author suijinchi
	 * @param id
	 * @param productId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	Orders createOrder(Integer counts, BigDecimal price, Long memberId, Long productId, String productSpec, Integer shippingMethod, Long receiverId, String memo);
	
	/**
	 * 根据订单号查询订单
	 * @author suijinchi
	 * @param sn
	 * @return
	 */
	Orders getBySn(String sn);
	
	/**
	 * 创建平台商品订单
	 * @author suijinchi
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 */
	Orders createPlatOrder(Long memberId, Integer shippingMethod, Long receiverId, String memo);
	
	/**
	 * 积分购买时创建平台商品订单
	 * @author suijinchi
	 * @param id
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	Orders createPlatPointOrder(Long id, Integer shippingMethod, Long receiverId, String memo);
	
	/**
	 * 创建店铺商品订单
	 * @author suijinchi
	 * @param shopId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 */
	Orders createShopOrder(Long memberId, Long shopId, Integer shippingMethod, Long receiverId, String memo);
	
	/**
	 * 单个商品积分购买生成订单
	 * @author suijinchi
	 * @param counts
	 * @param point
	 * @param id
	 * @param productId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	Orders createPointOrder(Integer counts, Integer point, Long id, Long productId, String productSpec, Integer shippingMethod,
			Long receiverId, String memo);
	
	/**
	 * 查询未领取有效特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	int unTakedSpecialOrdersCnt(long memberId);
	
	/**
	 * 查询已领取特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId》0,address not is null 或者去空格后不为空)
	 * @author hawkbird date 2018-04-12
	 */
	int takedSpecialOrdersCnt(long memberId);
	
	/**
	 * 查询未领取特殊订单ID(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	Long queryUntakedSpecialOrdersId(long memberId);
	
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
	 * 会员订单，完成订单操作
	 * @param orderId
	 */
	void memberComplete(Long orderId);
	
	/**
	 * 店铺订单，完成订单操作
	 * @param orderId
	 */
	void shopComplete(long id);

	/**
	 * 支付普通订单
	 * @param ordersIds
	 * @param thirdSn
	 * @param payDate
	 */
	void payOrder(Long[] ordersIds, String thirdSn, Date payDate);
	
	/**
	 * 根据条件查询已支付订单数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	Long findOrdersByParams(Map<String, Object> params);
	
	/**
	 * 批量取消
	 */
	void batchCancel();
	
	/**
	 * 批量确认收货
	 */
	void batchConfirmReceive();
	
	/**
	 * 批量确认完成
	 */
	void batchComplete();
	
	/**
	 * 根据条件查询收入
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	BigDecimal findAmountByParams(Map<String, Object> params);
	
	/**
	 * 查询各个月份的收益
	 * @return
	 */
	Map<String,BigDecimal> findAmountForMonth();
	
	/**
	 * 查询店铺销量排行榜
	 * @author suijinchi
	 * @return
	 */
	List<Map<String, Object>> findShopSale();
	
	/**
	 * 当月每日订单量统计
	 * @author suijinchi
	 * @return
	 */
	Map<String,Object> findCountForDay();
	
	/**
	 * 当年每月订单量统计
	 * @author suijinchi
	 * @return
	 */
	Map<String,Object> findCountForMonth();
	
	/**
	 * 获取最新已支付未发货的订单（不大于10条）
	 * @author suijinchi
	 * @return
	 */
	List<Orders> findNearestOrders();
	
	/**
	 * 查询店铺销售提现总金额
	 * @author suijinchi
	 * @param map
	 * @return
	 */
	BigDecimal findShopSaleAmountByParams(Map<String, Object> map);

	/**
	 * 根据订单状态查询订单数量
	 * @param params
	 * @return
	 */
    int countByOrdersStatus(Map<String, Object> params);

	/**
	 * 创建订单
	 * @param shippingMethod 发货方式0自提 1快递
	 * @param offsetPoint 抵扣积分
	 * @param offsetPointAmount 积分抵扣金额
	 * @param offsetBalance 余额抵扣金额
	 * @param couponId 优惠券id
	 * @param offsetCouponAmount 优惠券抵扣金额
	 * @param shippingFee 配送费
	 * @param thirdAmount 第三方支付金额
	 * @param pickUpTime 自提时间
	 * @param pickUpAddress 自提点
	 * @param name 自提姓名
	 * @param mobile 自提手机号
	 * @param shippingTime 发货时间
	 * @param receiverId 收货信息
	 * @param memo 用户备注
	 */
    Orders payByThird(Member member, Integer shippingMethod,
				  Integer offsetPoint, BigDecimal offsetPointAmount, BigDecimal offsetBalance, Long couponId, BigDecimal offsetCouponAmount,
				  BigDecimal shippingFee, BigDecimal thirdAmount,
				  String pickUpTime, String pickUpAddress, String name,String mobile,
					  String shippingTime, Long receiverId,Date msgDate,String memo);

	/**
	 * 支付订单
	 * @param shippingMethod 发货方式0自提 1快递
	 * @param offsetPoint 抵扣积分
	 * @param offsetPointAmount 积分抵扣金额
	 * @param offsetBalance 余额抵扣金额
	 * @param couponId 优惠券id
	 * @param offsetCouponAmount 优惠券抵扣金额
	 * @param shippingFee 配送费
	 * @param thirdAmount 第三方支付金额
	 * @param pickUpTime 自提时间
	 * @param pickUpAddress 自提点
	 * @param name 自提姓名
	 * @param mobile 自提手机号
	 * @param shippingTime 发货时间
	 * @param receiverId 收货信息
	 * @param memo 用户备注
	 */
    Orders payByBalance(Member member,Integer shippingMethod,
						Integer offsetPoint, BigDecimal offsetPointAmount, BigDecimal offsetBalance, Long couponId, BigDecimal offsetCouponAmount,
						BigDecimal shippingFee, BigDecimal thirdAmount,
						String pickUpTime, String pickUpAddress,String name,String mobile, String shippingTime, Long receiverId,Date msgDate,String memo);

	/**
	 * 取消订单
	 * @param orderId
	 */
	void cancel(Long orderId);

    /**
     * 完成订单
     * @param orderId
     */
	void complete(Long orderId);

	/**
	 * 确认收货
     * 更新商品销量 库存放在此操作中
	 * @param orderId
	 */
	void confirmReceive(Long orderId);

	/**
	 * 退款
	 * @param orderId
	 */
    void refund(Long orderId,Long adminId);

	/**
	 * 申请退款
	 * @param orderId
	 * @param reason
	 */
	void applyRefund(Long orderId, String reason);

    List<HashMap<String,Object>> findListByIds(Long[] ids);
}
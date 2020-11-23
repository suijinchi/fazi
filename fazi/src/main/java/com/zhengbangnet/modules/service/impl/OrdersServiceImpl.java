package com.zhengbangnet.modules.service.impl;

import com.zhengbangnet.Config;
import com.zhengbangnet.common.lang.Setting;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.common.utils.*;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.mapper.*;
import com.zhengbangnet.modules.service.CouponService;
import com.zhengbangnet.modules.service.CouponTypeService;
import com.zhengbangnet.modules.service.OrdersService;
import com.zhengbangnet.modules.service.ProductStockService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.Configuration;
import org.weixin4j.WXTemplateMsg;
import org.weixin4j.Weixin;
import org.weixin4j.WeixinException;
import org.weixin4j.pay.SignUtil;
import org.weixin4j.pay.refund.WxRefund;
import org.weixin4j.pay.refund.WxRefundResult;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service("ordersServiceImpl")
@Transactional(readOnly=false)
public class OrdersServiceImpl extends BaseServiceImpl<Orders,Long> implements OrdersService{
	
	@Resource(name="memberMapper")
	private MemberMapper memberMapper;

	@Resource(name="memberRankMapper")
	private MemberRankMapper memberRankMapper;

	@Resource(name="ordersMapper")
	private OrdersMapper ordersMapper;
	
	@Resource(name = "receiverMapper")
	private ReceiverMapper receiverMapper;

	@Resource(name = "couponMapper")
	private CouponMapper couponMapper;

	@Resource(name = "couponTypeMapper")
	private CouponTypeMapper couponTypeMapper;

	@Resource(name = "couponTypeServiceImpl")
	private CouponTypeService couponTypeService;

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "productMapper")
	private ProductMapper productMapper;

	@Resource(name = "productStockMapper")
	private ProductStockMapper productStockMapper;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;

	@Resource(name = "ordersItemMapper")
	private OrdersItemMapper ordersItemMapper;
	
	@Resource(name = "cartItemMapper")
	private CartItemMapper cartItemMapper;
	
	@Resource(name="areaMapper")
	private AreaMapper areaMapper;
	
	@Resource(name = "cartMapper")
	private CartMapper cartMapper;
	
	@Resource(name = "pointRecordMapper")
	private PointRecordMapper pointRecordMapper;

	@Resource(name = "balanceRecordMapper")
	private BalanceRecordMapper balanceRecordMapper;

	@Resource(name = "paymentMapper")
	private PaymentMapper paymentMapper;

	@Resource(name = "refundMapper")
	private RefundMapper refundMapper;

	@Resource(name = "weixin")
	private Weixin weixin;
	
	@Resource(name="ordersMapper")
	public void setBaseDao(BaseMapper<Orders,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	/* 
	 * 分页查询-店铺销售
	 * add by hawkbird date 2018-03-28
	 */
	@Override
	public Page<Map<String, Object>> findShopSalePageByParams(Map<String, Object> params) {
		Pageable pageable = (Pageable)params.get("pageable");
		Long count = ordersMapper.getCountByParams(params);
		List<Map<String, Object>> list = ordersMapper.findShopSalePageByParams(params);
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(list,count, pageable);
		return page;
	}

	/* 
	 * 获取记录数
	 * add by hawkbird date 2018-03-28
	 */
	@Override
	public Long getShopSaleCountByParams(Map<String, Object> params) {
		return  ordersMapper.getShopSaleCountByParams(params);
	}

	/* 
	 * 依照参数查询店铺销售
	 * add by hawkbird date 2018-03-28
	 */
	@Override
	public List<HashMap<String, Object>> findShopSaleListByParams(Map<String, Object> params) {
		return ordersMapper.findShopSaleListByParams(params);
	}

	/* 
	 * 手机端按页查询当前用户订单
	 * add by hawkbird date 2018-03-29
	 */
	@Override
	public List<Map<String, Object>> findOnePageOrdersByParams(Map<String, Object> params) {
		return ordersMapper.findOnePageOrdersByParams(params);
	}

	/**
	 * 单个商品购买生产订单
	 * @param
	 * @param productId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	@Override
	public Orders createOrder(Integer counts, BigDecimal price, Long memberId, Long productId, String productSpec, Integer shippingMethod, Long receiverId, String memo) {
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersNumb = SnUtils.getChargeOrderSn();
		//商品信息
		Product product = productMapper.getByPrimaryKey(productId);
		//插入订单
		Orders orders = new Orders();
		orders.setSn(ordersNumb);			
		orders.setAmount(price.multiply(new BigDecimal(counts)));						
		orders.setOrdersStatus(1);						
		orders.setPayStatus(0);							
		orders.setShippingStatus(0);	
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);	
		orders.setPoint(0);
		orders.setPayType(0);
		orders.setCreateDate(new Date());			
		orders.setModifyDate(new Date());		
		orders.setMemberId(memberId);				
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
			orders.setAreaId(receiver.getAreaId());				
			orders.setName(receiver.getConsignee());		
			orders.setMobile(receiver.getContactNumber());		
		}	
		ordersMapper.insert(orders);
		//插入订单明细
		OrdersItem item = new OrdersItem();
		item.setProductId(product.getId());			
		item.setName(product.getName());				
		item.setOrdersId(orders.getId());				
		item.setThumbnail(product.getShowUrl());		
		item.setPrice(price);
		item.setPoint(product.getPoint());
		item.setQuantity(counts);		
		item.setProductSpec(productSpec);
		item.setSn(product.getSn());					
		item.setCreateDate(new Date());				
		item.setModifyDate(new Date());					
		ordersItemMapper.insert(item);
		//商品减去库存
//		product.setSoldOut(product.getSoldOut()+counts);
		product.setStock(product.getStock()-counts);
		productMapper.updateByPrimaryKeySelective(product);
		return orders;
	}

	/**
	 * 根据订单号获取订单
	 * @author suijinchi
	 * @param sn
	 * @return
	 */
	@Override
	public Orders getBySn(String sn) {
		return ordersMapper.getBySn(sn);
	}
	
	/**
	 * 积分购买时创建平台商品订单
	 * @author suijinchi
	 * @param
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	@Override
	public Orders createPlatPointOrder(Long memberId, Integer shippingMethod, Long receiverId, String memo) {
	//创建订单
		//获取会员信息
		Member member = memberMapper.getByPrimaryKey(memberId);
		//获取会员购物车信息
		Cart cart = cartMapper.getByMemberId(memberId);
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersNumb = SnUtils.getChargeOrderSn();
		//获取购物车详情列表
		List<CartItem> cartItemList = cartItemMapper.getByCartId(cart.getId());
		//获取购物车中平台商品总积分
		Integer platPoint = 0;
		for (int i=0;i<cartItemList.size();i++) {
			CartItem cartItem = cartItemList.get(i);
			Product product = productMapper.getByPrimaryKey(cartItem.getProductId());
			//从购物车明细中获取商品数量
			Integer quantity = cartItem.getQuantity();
			platPoint = platPoint + product.getPoint()*quantity;
		}				
		//生成订单
		Orders orders = new Orders();
		orders.setSn(ordersNumb);		
		BigDecimal amount = SettingUtils.get().getShippingFee();
		orders.setAmount(amount);						
		orders.setOrdersStatus(1);						
		orders.setPayStatus(0);							
		orders.setShippingStatus(0);
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);
		orders.setCreateDate(new Date());			
		orders.setModifyDate(new Date());		
		orders.setMemberId(memberId);
		orders.setPoint(platPoint);
		orders.setPayType(1);
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
			orders.setAreaId(receiver.getAreaId());				
			orders.setName(receiver.getConsignee());		
			orders.setMobile(receiver.getContactNumber());		
		}	
		ordersMapper.insert(orders);
		//插入订单明细
		for (int i=0;i<cartItemList.size();i++) {
			CartItem cartItem = cartItemList.get(i);
			Product product = productMapper.getByPrimaryKey(cartItem.getProductId());
			OrdersItem item = new OrdersItem();
			item.setProductId(product.getId());			
			item.setName(product.getName());				
			item.setOrdersId(orders.getId());				
			item.setThumbnail(product.getShowUrl());
			item.setPoint(product.getPoint());
			item.setProductSpec(cartItem.getProductSpec());
			if (member.getType() == 0) {
				item.setPrice(product.getPrice());
			} else {
				item.setPrice(product.getMemberPrice());
			}
			//从购物车明细中获取商品数量
			Integer quantity = cartItem.getQuantity();
			item.setQuantity(quantity);						
			item.setSn(product.getSn());					
			item.setCreateDate(new Date());				
			item.setModifyDate(new Date());					
			ordersItemMapper.insert(item);
			//减去商品库存
			product.setStock(product.getStock() - quantity);
//			product.setSoldOut(product.getSoldOut() + quantity);
			productMapper.updateByPrimaryKeySelective(product);
			//购物车中移除商品
			cartItemMapper.deleteByPrimaryKey(cartItem.getId());
		}
		
		/*
		//需要支付10元邮费，在支付回调时减去会员积分
		member.setPoint(member.getPoint()-platPoint);
		memberMapper.updateByPrimaryKeySelective(member);
		*/
		return orders;
	}

//	@Override
//	public Orders createPlatPointOrder(Long memberId, Integer shippingMethod, Long receiverId, String memo) {
//	//创建订单
//		//获取会员信息
//		Member member = memberMapper.getByPrimaryKey(memberId);
//		//获取会员购物车信息
//		Cart cart = cartMapper.getByMemberId(memberId);
//		//获取地址信息
//		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
//		//获取订单编号
//		String ordersNumb = SnUtils.getChargeOrderSn();
//		//获取购物车中平台的商品信息
//		List<Product> list = productMapper.getCartPlatProductByMemberId(memberId);
//		//获取购物车中平台商品总积分
//		Integer platPoint = 0;
//		for (int i=0;i<list.size();i++) {
//			
//			Product product = list.get(i);
//			//从购物车明细中获取商品数量
//			Map<String, Object> params1 = new HashMap<String, Object>();
//			params1.put("cartId", cart.getId());
//			params1.put("productId", product.getId());
//			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(params1);
//			Integer quantity = cartItem.getQuantity();
//			platPoint = platPoint + product.getPoint()*quantity;
//		}				
//		//生成订单
//		Orders orders = new Orders();
//		orders.setSn(ordersNumb);		
//		BigDecimal amount = new BigDecimal(10);
//		orders.setAmount(amount);						
//		orders.setOrdersStatus(1);						
//		orders.setPayStatus(0);							
//		orders.setShippingStatus(0);				
//		orders.setShippingMethod(shippingMethod);		
//		orders.setType(1);							
//		orders.setCreateDate(new Date());			
//		orders.setModifyDate(new Date());		
//		orders.setMemberId(memberId);
//		orders.setPoint(platPoint);
//		orders.setPayType(1);
//		if (receiver != null && shippingMethod==1) {
//			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
//			orders.setAreaId(receiver.getAreaId());				
//			orders.setName(receiver.getConsignee());		
//			orders.setMobile(receiver.getContactNumber());		
//		}	
//		ordersMapper.insert(orders);
//		//插入订单明细
//		for (int i=0;i<list.size();i++) {
//			Product product = list.get(i);
//			OrdersItem item = new OrdersItem();
//			item.setProductId(product.getId());			
//			item.setName(product.getName());				
//			item.setOrdersId(orders.getId());				
//			item.setThumbnail(product.getShowUrl());
//			item.setPoint(product.getPoint());
//			if (member.getType() == 0) {
//				item.setPrice(product.getPrice());
//			} else {
//				item.setPrice(product.getMemberPrice());
//			}
//			//从购物车明细中获取商品数量
//			Map<String, Object> params2 = new HashMap<String, Object>();
//			params2.put("cartId", cart.getId());
//			params2.put("productId", product.getId());
//			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(params2);
//			Integer quantity = cartItem.getQuantity();
//			item.setQuantity(quantity);						
//			item.setSn(product.getSn());					
//			item.setCreateDate(new Date());				
//			item.setModifyDate(new Date());					
//			ordersItemMapper.insert(item);
//			//减去商品库存
//			product.setStock(product.getStock() - quantity);
//			product.setSoldOut(product.getSoldOut() + quantity);
//			productMapper.updateByPrimaryKeySelective(product);
//			//购物车中移除商品
//			cartItemMapper.deleteByCartIdAndProductId(params2);
//		}
//		//减去会员积分
//		member.setPoint(member.getPoint()-platPoint);
//		memberMapper.updateByPrimaryKeySelective(member);
//		return orders;
//	}
	
	/**
	 * 创建平台商品订单
	 * @author suijinchi
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 */
	@Override
	public Orders createPlatOrder(Long memberId, Integer shippingMethod, Long receiverId, String memo) {
	//创建订单
		//获取会员信息
		Member member = memberMapper.getByPrimaryKey(memberId);
		//获取会员购物车信息
		Cart cart = cartMapper.getByMemberId(memberId);
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersNumb = SnUtils.getChargeOrderSn();
		//获取购物车详情列表
		List<CartItem> cartItemList = cartItemMapper.getByCartId(cart.getId());
		//获取购物车中平台商品总金额
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0;i<cartItemList.size();i++) {
			CartItem cartItem = cartItemList.get(i);
			Product product = productMapper.getByPrimaryKey(cartItem.getProductId());
			//从购物车明细中获取商品数量
			Integer quantity = cartItem.getQuantity();
			BigDecimal temp = new BigDecimal(quantity);
			BigDecimal productAmount = BigDecimal.ZERO;
			if (member.getType() == 0) {
				productAmount = product.getPrice().multiply(temp);
			} else {
				productAmount = product.getMemberPrice().multiply(temp);
			}
			amount = amount.add(productAmount);
		}				
		//生成订单
		Orders orders = new Orders();
		orders.setSn(ordersNumb);								
		orders.setAmount(amount);						
		orders.setOrdersStatus(1);						
		orders.setPayStatus(0);							
		orders.setShippingStatus(0);
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);
		orders.setCreateDate(new Date());			
		orders.setModifyDate(new Date());		
		orders.setMemberId(memberId);
		orders.setPoint(0);
		orders.setPayType(0);
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
			orders.setAreaId(receiver.getAreaId());				
			orders.setName(receiver.getConsignee());		
			orders.setMobile(receiver.getContactNumber());		
		}	
		ordersMapper.insert(orders);
		//插入订单明细
		for (int i=0;i<cartItemList.size();i++) {
			CartItem cartItem = cartItemList.get(i);
			Product product = productMapper.getByPrimaryKey(cartItem.getProductId());
			OrdersItem item = new OrdersItem();
			item.setProductId(product.getId());			
			item.setName(product.getName());				
			item.setOrdersId(orders.getId());				
			item.setThumbnail(product.getShowUrl());
			item.setPoint(product.getPoint());
			item.setProductSpec(cartItem.getProductSpec());
			if (member.getType() == 0) {
				item.setPrice(product.getPrice());
			} else {
				item.setPrice(product.getMemberPrice());
			}
			//从购物车明细中获取商品数量
			Integer quantity = cartItem.getQuantity();
			item.setQuantity(quantity);						
			item.setSn(product.getSn());					
			item.setCreateDate(new Date());				
			item.setModifyDate(new Date());					
			ordersItemMapper.insert(item);
			//减去商品库存
			product.setStock(product.getStock() - quantity);
//			product.setSoldOut(product.getSoldOut() + quantity);
			productMapper.updateByPrimaryKeySelective(product);
			//购物车中移除商品
			cartItemMapper.deleteByPrimaryKey(cartItem.getId());
		}
		return orders;
	}

//	@Override
//	public Orders createPlatOrder(Long memberId, Integer shippingMethod, Long receiverId, String memo) {
//	//创建订单
//		//获取会员信息
//		Member member = memberMapper.getByPrimaryKey(memberId);
//		//获取会员购物车信息
//		Cart cart = cartMapper.getByMemberId(memberId);
//		//获取地址信息
//		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
//		//获取订单编号
//		String ordersNumb = SnUtils.getChargeOrderSn();
//		//获取购物车中平台的商品信息
//		List<Product> list = productMapper.getCartPlatProductByMemberId(memberId);
//		//获取购物车中平台商品总金额
//		BigDecimal amount = BigDecimal.ZERO;
//		for (int i=0;i<list.size();i++) {
//			Product product = list.get(i);
//			//从购物车明细中获取商品数量
//			Map<String, Object> params1 = new HashMap<String, Object>();
//			params1.put("cartId", cart.getId());
//			params1.put("productId", product.getId());
//			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(params1);
//			Integer quantity = cartItem.getQuantity();
//			BigDecimal temp = new BigDecimal(quantity);
//			BigDecimal productAmount = BigDecimal.ZERO;
//			if (member.getType() == 0) {
//				productAmount = product.getPrice().multiply(temp);
//			} else {
//				productAmount = product.getMemberPrice().multiply(temp);
//			}
//			amount = amount.add(productAmount);
//		}				
//		//生成订单
//		Orders orders = new Orders();
//		orders.setSn(ordersNumb);								
//		orders.setAmount(amount);						
//		orders.setOrdersStatus(1);						
//		orders.setPayStatus(0);							
//		orders.setShippingStatus(0);				
//		orders.setShippingMethod(shippingMethod);		
//		orders.setType(1);							
//		orders.setCreateDate(new Date());			
//		orders.setModifyDate(new Date());		
//		orders.setMemberId(memberId);
//		orders.setPoint(0);
//		orders.setPayType(0);
//		if (receiver != null && shippingMethod==1) {
//			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
//			orders.setAreaId(receiver.getAreaId());				
//			orders.setName(receiver.getConsignee());		
//			orders.setMobile(receiver.getContactNumber());		
//		}	
//		ordersMapper.insert(orders);
//		//插入订单明细
//		for (int i=0;i<list.size();i++) {
//			Product product = list.get(i);
//			OrdersItem item = new OrdersItem();
//			item.setProductId(product.getId());			
//			item.setName(product.getName());				
//			item.setOrdersId(orders.getId());				
//			item.setThumbnail(product.getShowUrl());
//			item.setPoint(product.getPoint());
//			if (member.getType() == 0) {
//				item.setPrice(product.getPrice());
//			} else {
//				item.setPrice(product.getMemberPrice());
//			}
//			//从购物车明细中获取商品数量
//			Map<String, Object> params2 = new HashMap<String, Object>();
//			params2.put("cartId", cart.getId());
//			params2.put("productId", product.getId());
//			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(params2);
//			Integer quantity = cartItem.getQuantity();
//			item.setQuantity(quantity);						
//			item.setSn(product.getSn());					
//			item.setCreateDate(new Date());				
//			item.setModifyDate(new Date());					
//			ordersItemMapper.insert(item);
//			//减去商品库存
//			product.setStock(product.getStock() - quantity);
//			product.setSoldOut(product.getSoldOut() + quantity);
//			productMapper.updateByPrimaryKeySelective(product);
//			//购物车中移除商品
//			cartItemMapper.deleteByCartIdAndProductId(params2);
//		}
//		return orders;
//	}

	/**
	 * 创建店铺商品订单
	 * @author suijinchi
	 * @param shopId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 */
	@Override
	public Orders createShopOrder(Long memberId, Long shopId, Integer shippingMethod, Long receiverId, String memo) {
	//创建订单
		//获取会员购物车信息
		Cart cart = cartMapper.getByMemberId(memberId);
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersNumb = SnUtils.getChargeOrderSn();
		//获取购物车中此店铺的商品信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("shopId", shopId);
		List<Product> list = null;
		//获取购物车中平台商品总金额
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0;i<list.size();i++) {
			Product product = list.get(i);
			//从购物车明细中获取商品数量
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("cartId", cart.getId());
			map1.put("productId", product.getId());
			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(map1);
			Integer quantity = cartItem.getQuantity();
			BigDecimal temp = new BigDecimal(quantity);
			BigDecimal productAmount = product.getPrice().multiply(temp);			
			amount = amount.add(productAmount);
		}		
		//生成订单
		Orders orders = new Orders();
		orders.setSn(ordersNumb);								
		orders.setAmount(amount);						
		orders.setOrdersStatus(1);						
		orders.setPayStatus(0);							
		orders.setShippingStatus(0);
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);
		orders.setCreateDate(new Date());			
		orders.setModifyDate(new Date());		
		orders.setMemberId(memberId);
		orders.setPoint(0);
		orders.setPayType(0);
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
			orders.setAreaId(receiver.getAreaId());				
			orders.setName(receiver.getConsignee());		
			orders.setMobile(receiver.getContactNumber());		
		}	
		ordersMapper.insert(orders);
		//插入订单明细
		for (int i=0;i<list.size();i++) {
			Product product = list.get(i);
			OrdersItem item = new OrdersItem();
			item.setProductId(product.getId());			
			item.setName(product.getName());				
			item.setOrdersId(orders.getId());				
			item.setThumbnail(product.getShowUrl());		
			item.setPrice(product.getPrice());
			item.setPoint(product.getPoint());
			//从购物车明细中获取商品数量
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("cartId", cart.getId());
			map2.put("productId", product.getId());
			CartItem cartItem = cartItemMapper.getQuantityByCartIdAndProductId(map2);
			Integer quantity = cartItem.getQuantity();
			item.setQuantity(quantity);					
			item.setSn(product.getSn());					
			item.setCreateDate(new Date());				
			item.setModifyDate(new Date());					
			ordersItemMapper.insert(item);
			//减去商品库存
			product.setStock(product.getStock() - quantity);
//			product.setSoldOut(product.getSoldOut() + quantity);
			productMapper.updateByPrimaryKeySelective(product);
			//购物车中移除商品
			cartItemMapper.deleteByCartIdAndProductId(map2);		
		}	
		return orders;
	}

	/**
	 * 单个平台商品积分购买生成订单
	 * @author suijinchi
	 * @param counts
	 * @param point
	 * @param
	 * @param productId
	 * @param shippingMethod
	 * @param receiverId
	 * @param memo
	 * @return
	 */
	@Override   
	public Orders createPointOrder(Integer counts, Integer point, Long memberId, Long productId, String productSpec, Integer shippingMethod,Long receiverId, String memo) {
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersNumb = SnUtils.getChargeOrderSn();
		//商品信息
		Product product = productMapper.getByPrimaryKey(productId);
		//插入订单
		Orders orders = new Orders();
		orders.setSn(ordersNumb);
		BigDecimal amount = SettingUtils.get().getShippingFee();
		orders.setAmount(amount);	
		orders.setPoint(point*counts);
		orders.setOrdersStatus(1);						
		orders.setPayStatus(0);							
		orders.setShippingStatus(0);
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);
		orders.setCreateDate(new Date());			
		orders.setModifyDate(new Date());		
		orders.setMemberId(memberId);	
		orders.setPayType(1);
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());		
			orders.setAreaId(receiver.getAreaId());				
			orders.setName(receiver.getConsignee());		
			orders.setMobile(receiver.getContactNumber());		
		}	
		ordersMapper.insert(orders);
		//插入订单明细
		OrdersItem item = new OrdersItem();
		item.setProductId(product.getId());			
		item.setName(product.getName());				
		item.setOrdersId(orders.getId());				
		item.setThumbnail(product.getShowUrl());		
		item.setPrice(product.getPrice());		
		item.setPoint(point);
		item.setQuantity(counts);		
		item.setProductSpec(productSpec);
		item.setSn(product.getSn());					
		item.setCreateDate(new Date());				
		item.setModifyDate(new Date());					
		ordersItemMapper.insert(item);
		
		//扣减库存
		product.setStock(product.getStock()-counts);
		productMapper.updateByPrimaryKeySelective(product);
		
		/*
		//商品减去库存
		//支付成功后增加销量
		product.setSoldOut(product.getSoldOut()+counts);
		product.setStock(product.getStock()-counts);
		productMapper.updateByPrimaryKeySelective(product);
		
		//由于需要支付10元邮费，积分放在支付回调时扣减
		Member member = memberMapper.getByPrimaryKey(memberId);
		member.setPoint(member.getPoint()-point*counts);
		memberMapper.updateByPrimaryKeySelective(member);
		*/
		return orders;
	}
	
	/**
	 * 查询有效特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	@Override
	public int unTakedSpecialOrdersCnt(long memberId) {
		return ordersMapper.unTakedSpecialOrdersCnt(memberId);
	}

	/**
	 * 查询已领取特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId》0,address not is null 或者去空格后不为空)
	 * @author hawkbird date 2018-04-12
	 */
	@Override
	public int takedSpecialOrdersCnt(long memberId) {
		return ordersMapper.takedSpecialOrdersCnt(memberId);
	}

	/**
	 * 查询未领取特殊订单ID(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-12
	 */
	@Override
	public Long queryUntakedSpecialOrdersId(long memberId) {
		return ordersMapper.queryUntakedSpecialOrdersId(memberId);
	}

	/**
	 * 删除作废的特殊订单(type=0,会员订单;payStatus=1,payType=0,已支付且微信支付即非积分支付;地址为空,areaId=0,address is null 或者去空格后为空)
	 * @author hawkbird date 2018-04-13
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
	public int delInvalidSpecialOrders(long memberId) {
		return ordersMapper.delInvalidSpecialOrders(memberId);
	}

	/**
	 * 查询最新的平台订单 10条 
	 * @author hawkbird date 2018-04-13
	 */
	@Override
	public List<Map<String, Object>> queryCurrentPlatOrders() {
		return ordersMapper.queryCurrentPlatOrders();
	}

	@Override
	@Transactional
	public void memberComplete(Long orderId) {
		
		Orders orders = ordersMapper.getByPrimaryKey(orderId);
		orders.setOrdersStatus(2);
		orders.setModifyDate(new Date());
		orders.setCompleteDate(new Date());
		ordersMapper.updateByPrimaryKeySelective(orders);
		
	}

	@Override
	@Transactional
	public void shopComplete(long id) {
//		Setting setting = SettingUtils.get();
		Orders orders = ordersMapper.getByPrimaryKey(id);
		orders.setOrdersStatus(2);
		orders.setCompleteDate(new Date());
		ordersMapper.updateByPrimaryKeySelective(orders);
	}

	/**
	 * 根据条件查询已支付订单数量
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	@Override
	public Long findOrdersByParams(Map<String, Object> params) {

		return ordersMapper.findOrdersByParams(params);
	}	

	@Override
	public void payOrder(Long[] ordersIds, String thirdSn, Date payDate) {
		for(Long id:ordersIds){
			if(id==null){
				continue;
			}
			//更新订单通知
			Orders orders = ordersMapper.getByPrimaryKey(id);
			if(orders==null){
				continue;
			}
			orders.setThirdPay(orders.getWxPayRecord());
			orders.setPayDate(payDate);
			orders.setPayStatus(1);
			ordersMapper.updateByPrimaryKeySelective(orders);

			//支付成功模板通知
			this.sendSuccesMsg(orders);
		}
	}


	/**
	 * 根据条件查询收入
	 * @author suijinchi
	 * @param params
	 * @return
	 */
	@Override
	public BigDecimal findAmountByParams(Map<String, Object> params) {
		return ordersMapper.findAmountByParams(params);
	}

	/**
	 * 查询各个月份收益
	 * @author suijinchi
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> findAmountForMonth() {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,BigDecimal> parms = new HashMap<String,BigDecimal>();
		Integer year = DateUtils.getDateYear(DateUtils.getDate());
		String key = "month";
		for (int month = 1; month < 13; month ++) {
			Date startDate = DateUtils.getDate(year, month, 1);
			Date endDate = DateUtils2.getDateEndTime(DateUtils.getLastDayOfMonth(year, month));
			map.put("startTime", DateUtils.dateToString(startDate,"yyyy-MM-dd HH:mm:ss"));
			map.put("endTime", DateUtils.dateToString(endDate,"yyyy-MM-dd HH:mm:ss"));
			map.put("type", "01");
			BigDecimal amount = ordersMapper.findAmountByParams(map);
			if (amount == null) {
				amount = BigDecimal.ZERO;
			}
			map.clear();
			parms.put(key + month, amount);
		}
		return parms;
	}

	/**
	 * 查询店铺销售量排行榜
	 * @author suijinchi
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findShopSale() {
		
		List<Map<String, Object>> list = ordersMapper.findShopSale();
		if (list != null) {
			if (list.size() > 6) {
				list = list.subList(0, 6); 
			}
			return list;
		}
		return null;
	}

	/**
	 * 当月每日订单量统计
	 * @author suijinchi
	 * @return
	 */
	@Override
	public Map<String, Object> findCountForDay() {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		List<Long> countList = new ArrayList<Long>();
		List<Integer> dayList = new ArrayList<Integer>();
		Integer year = DateUtils.getDateYear(DateUtils.getDate());
		Integer month = DateUtils.getDateMonth(DateUtils.getDate());
		Integer days = DateUtils.getDayOfMonth(DateUtils.dateToString(new Date(),"yyyyMM"));
		for (int day = 1; day <= days; day ++) {
			String startTime = DateUtils.dateToString(DateUtils.getDate(year, month, day, 0, 0, 0), "yyyy-MM-dd HH:mm:ss");
			String endTime = DateUtils.dateToString(DateUtils.getDate(year, month, day, 23, 59, 59), "yyyy-MM-dd HH:mm:ss");
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			Long count = ordersMapper.findOrdersByParams(map);
			if (count == null) {
				count = 0L;
			}
			map.clear();
			dayList.add(day);
			countList.add(count);
		}
		params.put("dayList", dayList);
		params.put("countList", countList);
		return params;
	}

	/**
	 * 当年每月订单量统计
	 * @author suijinchi
	 * @return
	 */
	@Override
	public Map<String, Object> findCountForMonth() {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		List<Long> countList = new ArrayList<Long>();
		List<Integer> monthList = new ArrayList<Integer>();
		Integer year = DateUtils.getDateYear(DateUtils.getDate());
		for (int month = 1; month < 13; month ++) {
			Date startDate = DateUtils.getDate(year, month, 1);
			Date endDate = DateUtils2.getDateEndTime(DateUtils.getLastDayOfMonth(year, month));
			map.put("startTime", DateUtils.dateToString(startDate,"yyyy-MM-dd HH:mm:ss"));
			map.put("endTime", DateUtils.dateToString(endDate,"yyyy-MM-dd HH:mm:ss"));
			Long count = ordersMapper.findOrdersByParams(map);
			if (count == null) {
				count = 0L;
			}
			map.clear();
			monthList.add(month);
			countList.add(count);
		}
		params.put("monthList", monthList);
		params.put("countList", countList);
		return params;
	}

	/**
	 * 获取最新已支付未发货的订单（不大于10条）
	 * @author suijinchi
	 * @return
	 */
	@Override
	public List<Orders> findNearestOrders() {
		List<Orders> orderList = ordersMapper.findNearestOrders();
		if (orderList != null) {
//			if (orderList.size() > 10) {
//				orderList = orderList.subList(0, 10);
//			}
			return orderList;
 		}
		return null;
	}

	/**
	 * 查询店铺销售提现总金额
	 * @author suijinchi
	 * @param map
	 * @return
	 */
	@Override
	public BigDecimal findShopSaleAmountByParams(Map<String, Object> map) {
		BigDecimal amount = ordersMapper.findShopSaleAmountByParams(map);
		return amount==null?BigDecimal.ZERO:amount;
	}

    @Override
    public int countByOrdersStatus(Map<String, Object> params) {
        return ordersMapper.countByOrdersStatus(params);
    }


	@Override
	@Transactional
	public Orders payByThird(Member member,Integer shippingMethod, Integer offsetPoint, BigDecimal offsetPointAmount, BigDecimal offsetBalance,Long couponId, BigDecimal offsetCouponAmount, BigDecimal shippingFee, BigDecimal thirdAmount, String pickUpTime, String pickUpAddress,String name,String mobile, String shippingTime, Long receiverId,Date msgDate,String memo) {
//		member = memberMapper.getByPrimaryKey(member.getId());
		Orders orders = this.createOrder(member,shippingMethod,
				offsetPoint,offsetPointAmount,offsetBalance,couponId,offsetCouponAmount,
				shippingFee,thirdAmount,
				pickUpTime,pickUpAddress,name,mobile,
				shippingTime,receiverId,msgDate,memo);
		orders.setPayStatus(0);
		orders.setPayDate(new Date());
		orders.setPayType(0);//微信支付
		ordersMapper.updateByPrimaryKeySelective(orders);
		return orders;
	}

	@Override
	@Transactional
	public Orders payByBalance(Member member,Integer shippingMethod,
							   Integer offsetPoint, BigDecimal offsetPointAmount, BigDecimal offsetBalance, Long couponId,BigDecimal offsetCouponAmount,
							   BigDecimal shippingFee, BigDecimal thirdAmount,
							   String pickUpTime, String pickUpAddress,String name,String mobile, String shippingTime, Long receiverId,Date msgDate,String memo) {
//		member = memberMapper.getByPrimaryKey(member.getId());
		Orders orders = this.createOrder(member,shippingMethod,
				offsetPoint,offsetPointAmount,offsetBalance,couponId,offsetCouponAmount,
				shippingFee,thirdAmount,
				pickUpTime,pickUpAddress,name,mobile,
				shippingTime,receiverId,msgDate,memo);
		orders.setPayStatus(1);
		orders.setPayDate(new Date());
		orders.setPayType(1);//余额支付
		ordersMapper.updateByPrimaryKeySelective(orders);

		//支付成功模板通知
        this.sendSuccesMsg(orders);

		return orders;
	}

    private void sendSuccesMsg(Orders orders) {
		try{
			Long memberId = orders.getMemberId();
			Member member = memberMapper.getByPrimaryKey(memberId);
			List<Map<String, Object>> orderItems = ordersItemMapper.findByOrdersId(orders.getId());

			/**
			 编号OPENTM207128229
			 标题支付成功通知
			 行业IT科技 - 互联网|电子商务
			 使用人数423
			 最后修改时间2015-07-29 16:35:02
			 详细内容
			 {{first.DATA}}
			 订单商品：{{keyword1.DATA}}
			 订单编号：{{keyword2.DATA}}
			 支付金额：{{keyword3.DATA}}
			 支付时间：{{keyword4.DATA}}
			 {{remark.DATA}}
			 内容示例
			 尊敬的客户，您的订单已支付成功
			 订单商品：测试商品
			 订单编号：01500001
			 支付金额：150元
			 支付时间：2014年10月21日13：00
			 感谢您的光临
			 */

			String productName = "";
			for(int i=0;i<orderItems.size();i++){
				Map<String,Object> item = orderItems.get(i);
				productName = productName+item.get("name");
				if(StringUtils.isNotBlank((String)item.get("productSpec"))){
					productName = productName + "【"+item.get("productSpec")+"】";
				}
				if(i!=(orderItems.size()-1)){
					productName+="、";
				}
			}
        
            WXTemplateMsg msg = new WXTemplateMsg();
            msg.setTouser(member.getOpenid());
            msg.setTemplate_id(Config.getProperty("paySuccessMsgId"));
            msg.addItem("first", "尊敬的客户，您的订单已支付成功");
            msg.addItem("keyword1", productName,"#173177");//
            msg.addItem("keyword2", orders.getSn(),"#173177");
            msg.addItem("keyword3", orders.getAmount().toString()+"元","#173177");
            msg.addItem("keyword4", DateUtils.dateToString(orders.getPayDate(),"yyyy-MM-dd HH:mm:ss"),"#173177");
            msg.addItem("remark","","#173177");
            msg.setUrl(SettingUtils.get().getDomain()+"/mobile/wechat/index?action=/mobile/orders/list");
            weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
            weixin.sendTemplateMsg(msg);
        }catch(Exception e){
            logger.error("会员支付成功通知模板异常",e);
        }
    }

    private Orders createOrder(Member member,Integer shippingMethod,
							   Integer offsetPoint, BigDecimal offsetPointAmount, BigDecimal offsetBalance, Long couponId,BigDecimal offsetCouponAmount,
							   BigDecimal shippingFee, BigDecimal thirdAmount,
							   String pickUpTime, String pickUpAddress,String pickUpName,String pickUpMobile,
							   String shippingTime, Long receiverId,Date msgDate,String memo){

		member = memberMapper.getByPrimaryKey(member.getId());
		//获取会员购物车信息
		Cart cart = cartMapper.getByMemberId(member.getId());
		//获取地址信息
		Receiver receiver = receiverMapper.getByPrimaryKey(receiverId);
		//获取订单编号
		String ordersSn = SnUtils.getOrdersSn(member.getId());

		CartItem c = new CartItem();
		c.setCartId(cart.getId());
		List<Map<String, Object>> cartItemList = cartItemMapper.getListByCartItem(c);

		//检测数据是否有问题
		Integer maxOffsetPoint = 0;
		BigDecimal productPrice = BigDecimal.ZERO;
		for(Map<String,Object> map:cartItemList){
			Integer mop = (Integer)map.get("maxOffsetPoint");
			BigDecimal price = (BigDecimal)map.get("price");
			Integer quantity = (Integer)map.get("quantity");
			maxOffsetPoint+=mop*quantity;
			productPrice = productPrice.add(price.multiply(new BigDecimal(quantity)));
		}

		Setting setting = SettingUtils.get();
		if(shippingMethod==0){
			if(StringUtils.isBlank(pickUpAddress)){
				throw new RuntimeException("自提点为空");
			}
			if(StringUtils.isBlank(pickUpTime)){
				throw new RuntimeException("自提时间为空");
			}
		}else if(shippingMethod==1){
			if(StringUtils.isBlank(shippingTime)){
				throw new RuntimeException("配送时间为空");
			}
			if(receiver==null){
				throw new RuntimeException("收货地址为空");
			}
		}

        if(couponId!=null) {
            Coupon coupon = couponMapper.getByPrimaryKey(couponId);
            if (coupon == null || coupon.getMemberId().longValue() != member.getId().longValue()) {
                throw new RuntimeException("选择的优惠券不存在");
            }
            if (coupon.getCutMoney().compareTo(offsetCouponAmount) < 0) {
                throw new RuntimeException("优惠券抵扣金额错误");
            }
            Boolean check = couponService.checkCoupon(couponId);
            if (!check) {
                throw new RuntimeException("优惠券使用错误");
            }
        }

		if(offsetPoint>member.getPoint()){
			throw new RuntimeException("积分余额不足");
		}
		if(offsetPoint>maxOffsetPoint){
			throw new RuntimeException("抵扣积分不可大于商品的最大抵扣数量");
		}
		if(new BigDecimal(offsetPoint/setting.getPointRatio()).compareTo(offsetPointAmount)!=0){
			throw new RuntimeException("积分抵扣金额错误");
		}
		if(offsetBalance.compareTo(member.getBalance())>0){
			throw new RuntimeException("抵扣余额不可大于用户余额");
		}

		//会员等级优惠
		MemberRank memberRank = memberRankMapper.getByPrimaryKey(member.getMemberRankId());
		BigDecimal sumDiscount = productPrice.multiply(BigDecimal.ONE.subtract(memberRank.getScale())).setScale(2,BigDecimal.ROUND_HALF_UP);

		BigDecimal sumAmount = productPrice.add(shippingFee);

		if(offsetPointAmount.add(offsetBalance).add(thirdAmount).add(offsetCouponAmount).add(sumDiscount).compareTo(sumAmount)!=0){
			throw new RuntimeException("金额计算错误，请刷新页面后重新下单");
		}

		//人民币支付金额=商品金额+配送费-积分抵扣-优惠券抵扣-会员折扣
		BigDecimal rmbAmount = sumAmount.subtract(offsetPointAmount).subtract(offsetCouponAmount).subtract(sumDiscount);

		//生成订单
		Orders orders = new Orders();
		orders.setCreateDate(new Date());
		orders.setModifyDate(new Date());
		orders.setSn(ordersSn);
		orders.setAmount(rmbAmount);
		orders.setSumAmount(sumAmount);
		orders.setThirdPay(BigDecimal.ZERO);
		orders.setOrdersStatus(1);//已确认
		orders.setPayStatus(0);//未支付
		orders.setShippingStatus(0);
		orders.setAssessStatus(0);
		orders.setShippingMethod(shippingMethod);
		orders.setMemberId(member.getId());
		orders.setPoint(0);//订单积分额
        orders.setPointReward(0);//订单奖励积分
//		orders.setPayType(1);
		orders.setBalancePay(offsetBalance);
		orders.setPointPay(offsetPoint);
		orders.setCouponPay(BigDecimal.ZERO);
		orders.setOffsetAmount(BigDecimal.ZERO);
		orders.setPointOffset(offsetPointAmount);
		orders.setShippingFee(shippingFee);
		orders.setShippingTime(shippingTime);
		orders.setPickUpTime(pickUpTime);
		orders.setPickUpAddress(pickUpAddress);
		orders.setCouponPay(offsetCouponAmount);
		orders.setMemo(memo);
		orders.setShippingMsgDate(msgDate);
		orders.setIsShippingMsg(0);
		orders.setServiceConfirmStatus(0);
		orders.setDiscountPay(sumDiscount);
		if (receiver != null && shippingMethod==1) {
			orders.setAddress(receiver.getAreaName()+receiver.getAddress());
			orders.setAreaId(receiver.getAreaId());
			orders.setName(receiver.getConsignee());
			orders.setMobile(receiver.getContactNumber());
			orders.setOrderer(receiver.getOrderer());
			orders.setOrdererMobile(receiver.getOrdererNumber());
		}else{
			orders.setName(pickUpName);
			orders.setMobile(pickUpMobile);
		}
		ordersMapper.insertSelective(orders);

		//更新优惠券使用状态等信息
		Coupon tc = new Coupon();
		tc.setId(couponId);
		tc.setStatus(1);
		tc.setUseDate(new Date());
		tc.setModifyDate(new Date());
		tc.setOrdersId(orders.getId());
		couponMapper.updateByPrimaryKeySelective(tc);

		//获取购物车中平台商品总积分
		for(Map<String,Object> map:cartItemList){

			Long id = (Long)map.get("id");
			Long productStockId = (Long)map.get("productStockId");
			Long productId = (Long)map.get("productId");
			Integer mop = (Integer)map.get("maxOffsetPoint");
			BigDecimal price = (BigDecimal)map.get("price");
			Integer quantity = (Integer)map.get("quantity");
			String name = (String)map.get("name");
			String specName = (String)map.get("specName");
			String birthdayCard = (String)map.get("birthdayCard");

			productPrice = productPrice.add(price);

			ProductStock productStock = productStockMapper.getByPrimaryKey(productStockId);
			Product product = productMapper.getByPrimaryKey(productStock.getProductId());

			OrdersItem item = new OrdersItem();
			item.setProductId(productId);
			item.setName(name);
			item.setOrdersId(orders.getId());
			item.setThumbnail(product.getShowUrl());
			item.setPoint(0);
			item.setProductSpec(specName==null?"":specName);
			item.setPrice(price);
			//从购物车明细中获取商品数量
			item.setQuantity(quantity);
			item.setSn(productStock.getSn());
			item.setCreateDate(new Date());
			item.setModifyDate(new Date());
			item.setSubname(product.getSubname());
			item.setProductStockId(productStock.getId());
			item.setBirthdayCard(birthdayCard);
			ordersItemMapper.insert(item);
			//减去商品库存
//			productStock.setStock(productStock.getStock() - quantity);
//			productStockMapper.updateByPrimaryKeySelective(productStock);
			productStockService.updateStock(productStock.getId(),productStock.getStock(),productStock.getSoldOut(),-quantity);
			//购物车中移除商品
			cartItemMapper.deleteByPrimaryKey(id);
		}
		cartMapper.deleteByPrimaryKey(cart.getId());

		if(orders.getPointPay()>0){
			//扣减积分
			member.setPoint(member.getPoint()-orders.getPointPay());
			memberMapper.updateByPrimaryKeySelective(member);
			//积分记录
			PointRecord pr = new PointRecord();
			pr.setCreateDate(new Date());
			pr.setModifyDate(new Date());
			pr.setMemberId(member.getId());
			pr.setMemo("购买商品，单号："+orders.getSn()+"，消费积分");
			pr.setPoint(-orders.getPointPay());
			pr.setSurplusAmount(member.getPoint());
			pointRecordMapper.insert(pr);
		}
		if(orders.getBalancePay().compareTo(BigDecimal.ZERO)>0){
			//扣减余额
			member.setBalance(member.getBalance().subtract(orders.getBalancePay()));
			memberMapper.updateByPrimaryKeySelective(member);
			//余额扣减记录

			BalanceRecord br = new BalanceRecord();
			br.setCreateDate(new Date());
			br.setModifyDate(new Date());
			br.setMemberId(member.getId());
			br.setMemo("购买商品，单号："+orders.getSn()+"，消费余额");
			br.setBalance(BigDecimal.ZERO.subtract(orders.getBalancePay()));
			balanceRecordMapper.insert(br);
		}
		return orders;
	}




    /**
     * 完成订单
     */
    @Override
    public void complete(Long ordersId) {
        Orders orders = new Orders();
        orders.setId(ordersId);
        orders.setCompleteDate(new Date());
        orders.setOrdersStatus(2);
        ordersMapper.updateByPrimaryKeySelective(orders);
    }

    @Override
    public void cancel(Long orderId) {

        //设置订单取消
        Orders order = ordersMapper.getByPrimaryKey(orderId);
        order.setOrdersStatus(3);
        order.setCancelDate(new Date());
        ordersMapper.updateByPrimaryKeySelective(order);

        //增加商品库存
        List<OrdersItem> ordersItemList = ordersItemMapper.getByOrdersId(orderId);
        for (int i = 0; i < ordersItemList.size(); i ++) {
            OrdersItem ordersItem = ordersItemList.get(i);
            ProductStock productStock = productStockService.getByPrimaryKey(ordersItem.getProductStockId());
            productStockService.updateStock(productStock.getId(), productStock.getStock(), productStock.getSoldOut(), ordersItem.getQuantity());
            Product product = productMapper.getByPrimaryKey(ordersItem.getProductId());
            product.setStock(product.getStock() + ordersItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }

        //退回积分
        Member member = memberMapper.getByPrimaryKey(order.getMemberId());
        if(order.getPointPay()>0){
            member.setPoint(member.getPoint()+order.getPointPay());
            memberMapper.updateByPrimaryKeySelective(member);
            //积分记录
            PointRecord pr = new PointRecord();
            pr.setCreateDate(new Date());
            pr.setModifyDate(new Date());
            pr.setMemberId(member.getId());
            pr.setMemo("取消订单，单号："+order.getSn()+"，退回积分");
            pr.setPoint(order.getPointPay());
			pr.setSurplusAmount(member.getPoint());
            pointRecordMapper.insert(pr);
        }
        //退回余额
        if(order.getBalancePay().compareTo(BigDecimal.ZERO)>0){
            member.setBalance(member.getBalance().add(order.getBalancePay()));
            memberMapper.updateByPrimaryKeySelective(member);
            BalanceRecord br = new BalanceRecord();
            br.setCreateDate(new Date());
            br.setModifyDate(new Date());
            br.setMemberId(member.getId());
            br.setMemo("取消订单，单号："+order.getSn()+"，退回余额");
            br.setBalance(order.getBalancePay());
            balanceRecordMapper.insert(br);
        }
        //退回使用的优惠券
        couponMapper.returnCouponByOrdersId(orderId);
    }

	@Override
	public void confirmReceive(Long orderId) {
		//订单状态
		Orders order = ordersMapper.getByPrimaryKey(orderId);

        Integer point = order.getThirdPay().intValue()+order.getBalancePay().intValue();
		order.setConfirmReceiveDate(new Date());
		order.setCompleteDate(new Date());
		order.setOrdersStatus(2);
		order.setShippingStatus(2);
		order.setPointReward(point);
		ordersMapper.updateByPrimaryKeySelective(order);

		//更新积分
		Member member = memberMapper.getByPrimaryKey(order.getMemberId());
		member.setPoint(member.getPoint()+point);
		member.setHistoryPoint(member.getHistoryPoint()+point);
		MemberRank mr = memberRankMapper.getByPoint(member.getHistoryPoint());
		MemberRank mr2 = memberRankMapper.getByPrimaryKey(member.getMemberRankId());
		if(mr!=null&&mr.getLowAmount().compareTo(mr2.getLowAmount())>0){
			member.setMemberRankId(mr.getId());
		}
		memberMapper.updateByPrimaryKeySelective(member);

		//明细
		PointRecord pr = new PointRecord();
		pr.setCreateDate(new Date());
		pr.setModifyDate(new Date());
		pr.setMemberId(order.getMemberId());
		pr.setMemo("购物消费，获得积分："+point);
		pr.setPoint(point);
		pr.setSurplusAmount(member.getPoint());
		pointRecordMapper.insert(pr);

		//增加商品销量
		List<OrdersItem> ordersItemList = ordersItemMapper.getByOrdersId(orderId);
		for (int i = 0; i < ordersItemList.size(); i ++) {
			OrdersItem ordersItem = ordersItemList.get(i);
			ProductStock productStock = productStockMapper.getByPrimaryKey(ordersItem.getProductStockId());
			if(productStock!=null){
				productStock.setSoldOut(productStock.getSoldOut() + 1);
				productStockMapper.updateByPrimaryKeySelective(productStock);
				Product product = productMapper.getByPrimaryKey(ordersItem.getProductId());
				if(product!=null){
					product.setSoldOut(product.getSoldOut() + 1);
					productMapper.updateByPrimaryKeySelective(product);
				}
			}
		}


		Long recommendId = member.getRecommendId();
		if(recommendId!=null){
			Member recommend = memberMapper.getByPrimaryKey(recommendId);
			if(recommend!=null){

				//检测设置推荐人获得优惠券,首单有奖励
				Map<String,Object> params = new HashMap<String,Object>();

				params.put("sourceMemberId",member.getId());
				params.put("memberId",recommend.getId());
				Long couponCount = couponMapper.getCountByParams(params);
				if(couponCount>0){
					return;
				}

				params.clear();
				params.put("memberId",recommend.getId());
				params.put("payStatus",1);
				params.put("ordersStatus",1);
				Long count1 = ordersMapper.getCountByParams(params);

				params.clear();
				params.put("memberId",recommend.getId());
				params.put("payStatus",1);
				params.put("ordersStatus",2);
				Long count2 = ordersMapper.getCountByParams(params);
				if((count1+count2)>0){
					Long couponTypeId = SettingUtils.get().getRecommendCouponId();
					if(couponTypeId!=null){
						CouponType couponType = couponTypeMapper.getByPrimaryKey(couponTypeId);
						if(couponType!=null&&couponType.getStock()>0){

							//更改库存和获取数量
							couponType.setStock(couponType.getStock()-1);
							couponType.setGetNum(couponType.getGetNum()+1);
							couponTypeMapper.updateByPrimaryKeySelective(couponType);

							Coupon coupon = new Coupon();
							BeanUtils.copyProperties(couponType,coupon);
							if(couponType.getValidDateType()==2){
								Integer validGetDay = couponType.getValidGetDay();
								Integer validDays = couponType.getValidDays();
								Date sd = DateUtils.getSomeDaysBeforeAfter(new Date(), validGetDay);
								Date ed = DateUtils.getSomeDaysBeforeAfter(sd, validDays);
								coupon.setValidStartDate(sd);
								coupon.setValidEndDate(ed);
							}
							coupon.setSourceMemberId(member.getId());
							coupon.setCreateDate(new Date());
							coupon.setModifyDate(new Date());
							coupon.setStatus(0);
							coupon.setScene(1);
							coupon.setMemberId(recommendId);
							coupon.setCouponTypeId(couponTypeId);
							couponMapper.insertSelective(coupon);

							/*
							编号 OPENTM402085801
							标题 推荐成功通知
							行业IT科技 - 互联网|电子商务
							使用人数1785
							最后修改时间2016-04-13 10:32:11

							详细内容
							{{first.DATA}}
							推荐人：{{keyword1.DATA}}
							被推荐人：{{keyword2.DATA}}
							{{remark.DATA}}

							内容示例
							天天有机生活商城新会员注册成功。
							推荐人：小红
							被推荐人：小明
							感谢加入天天有机生活商城。
							*/

							String name = member.getName();
							if(StringUtils.isBlank(name)){
								name = member.getNickname();
							}

							String recommendName = recommend.getName();
							if(StringUtils.isBlank(recommendName)){
								recommendName = recommend.getNickname();
							}

							try{
								WXTemplateMsg msg = new WXTemplateMsg();
								msg.setTouser(recommend.getOpenid());
								msg.setTemplate_id(Config.getProperty("recommendMsgId"));
								msg.addItem("first", "推荐会员购买商品成功~");
								msg.addItem("keyword1", recommendName,"#173177");//
								msg.addItem("keyword2", name,"#173177");
								msg.addItem("remark","您推荐的会员"+name+"成功购买商品，您获得一张优惠券","#173177");
								msg.setUrl(SettingUtils.get().getDomain()+"/mobile/wechat/index?action=/mobile/coupon/list");
								weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
								weixin.sendTemplateMsg(msg);
							}catch(Exception e){
								logger.error("推荐会员购买商品通知模板异常",e);
							}

						}
					}
				}
			}
		}

	}

    @Override
    public void refund(Long orderId,Long adminId) {

        Orders order = ordersMapper.getByPrimaryKey(orderId);
        Member member = memberMapper.getByPrimaryKey(order.getMemberId());
/*
        //积分收回
        Integer returnPoint = 0;
        if(order.getPointReward()>member.getPoint()){
            returnPoint = member.getPoint();
        }else{
            returnPoint = order.getPointReward();
        }
        member.setPoint(member.getPoint()-returnPoint);
        memberMapper.updateByPrimaryKeySelective(member);
        PointRecord pr = new PointRecord();
        pr.setCreateDate(new Date());
        pr.setModifyDate(new Date());
        pr.setMemberId(member.getId());
        pr.setMemo("订单退款，单号："+order.getSn()+"，收回奖励积分");
        pr.setPoint(returnPoint);
        pointRecordMapper.insert(pr);
*/

		Refund refund = new Refund();
		refund.setCreateDate(new Date());
		refund.setModifyDate(new Date());
		refund.setAdminId(adminId);
		refund.setMemberId(order.getMemberId());
		refund.setOrdersId(order.getId());
		refund.setPointPayRefund(order.getPointPay());
		refund.setBalancePayRefund(order.getBalancePay());

		//微信支付延迟更新
        if(order.getThirdPay().compareTo(BigDecimal.ZERO)>0){

			Payment payment = paymentMapper.getBySuccess(order.getId(),0);
			String refundSn = "R"+payment.getSn()+"_"+order.getSn();
			refund.setSn(refundSn);
			refund.setStatus(Refund.REFUNDING);
			refund.setThirdPayRefund(order.getThirdPay());

			String appid = Configuration.getOAuthAppId();
			String mchid = Configuration.getOAuthMchid();
			String certPath = Configuration.getCertPath();
			String certSecret = Configuration.getCertSecret();
			String mchkey = Configuration.getPartnerKey();

			WxRefund wxr = new WxRefund();
			wxr.setAppid(appid);
			wxr.setMch_id(mchid);
			wxr.setNonce_str(RandomUtils.getRandomStringByLength(32));
			wxr.setOut_refund_no(refund.getSn());
			wxr.setOut_trade_no(payment.getSn());
//			wxr.setRefund_account(refund_account);
			wxr.setRefund_desc("");
			wxr.setRefund_fee(order.getThirdPay().multiply(new BigDecimal(100)).intValue());
//			wxr.setRefund_fee_type(refund_fee_type);
			wxr.setSign_type("MD5");
			wxr.setTotal_fee(order.getThirdPay().multiply(new BigDecimal(100)).intValue());
			wxr.setTransaction_id(payment.getPaySn());
			wxr.setNotify_url(SettingUtils.get().getDomain()+"/wx/refund/notify");
//			wxr.setNotify_url("http://diwsnp.natappfree.cc/denise/wx/refund/notify");
			wxr.setSign(SignUtil.getSign(wxr.toMap(), mchkey));
			try {
				WxRefundResult payRefund = weixin.payRefund(wxr, mchid, certPath, certSecret);
				logger.info("申请退款接口返回结果："+payRefund.toString());
				String return_code = payRefund.getReturn_code();
				String result_code = payRefund.getResult_code();
				//返回状态码
				//业务结果
				if("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code)){
					/*
					SUCCESS/FAIL
					SUCCESS退款申请接收成功，结果通过退款查询接口查询
					FAIL 提交业务失败
					*/
					String refund_id = payRefund.getRefund_id();
					/*
					Refund r = new Refund();
					r.setId(refundId);
					r.setStatus("2");//退款中
					r.setRefundSn(refund_id);;
					refundMapper.updateByPrimaryKeySelective(r);
					*/
				}else{
					throw new RuntimeException("return_msg:"+payRefund.getReturn_msg()+",err_code:"+payRefund.getErr_code()+",err_code_des:"+payRefund.getErr_code_des());
				}
			} catch (WeixinException e) {
				logger.warn("微信支付申请退款异常：",e);
				throw new RuntimeException("微信支付申请退款异常");
			}
			order.setOrdersStatus(5);
        }else{
			refund.setStatus(Refund.REFUNED);
			refund.setThirdPayRefund(order.getThirdPay());
			refund.setSn("R"+order.getSn());
			order.setOrdersStatus(6);//退款成功
		}
		refundMapper.insert(refund);

		//积分退
		if(order.getPointPay()>0){
			member.setPoint(member.getPoint()+order.getPointPay());
			memberMapper.updateByPrimaryKeySelective(member);
			//积分记录
			PointRecord pr = new PointRecord();
			pr.setCreateDate(new Date());
			pr.setModifyDate(new Date());
			pr.setMemberId(member.getId());
			pr.setMemo("订单退款，单号："+order.getSn()+"，退回支付积分");
			pr.setPoint(order.getPointPay());
			pr.setSurplusAmount(member.getPoint());
			pointRecordMapper.insert(pr);
		}

		//余额退
		if(order.getBalancePay().compareTo(BigDecimal.ZERO)>0){
			member.setBalance(member.getBalance().add(order.getBalancePay()));
			memberMapper.updateByPrimaryKeySelective(member);
			BalanceRecord br = new BalanceRecord();
			br.setCreateDate(new Date());
			br.setModifyDate(new Date());
			br.setMemberId(member.getId());
			br.setMemo("订单退款，单号："+order.getSn()+"，退回支付余额");
			br.setBalance(order.getBalancePay());
			balanceRecordMapper.insert(br);
		}
		//退回使用的优惠券
		couponMapper.returnCouponByOrdersId(orderId);

		order.setModifyDate(new Date());
		ordersMapper.updateByPrimaryKey(order);
    }

    /**
     * 批量确认收货
     */
    @Override
    public void batchConfirmReceive() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 1);
        params.put("sendStatus", 1);
        params.put("orderStatus", 1);
        params.put("confirmReceiveMinute", 3*24*60);//发货时间3天后自动确认收货
        List<HashMap<String, Object>> list = ordersMapper.findListByParams(params);
        for(Map<String,Object> order:list){
            Long id = (Long)order.get("id");
            this.confirmReceive(id);
        }
    }

    /**
     * 批量完成
     */
    @Override
    public void batchComplete() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 1);
        params.put("sendStatus", 2);
        params.put("orderStatus", 1);
        params.put("completeMinute", 1*24*60);//确认收货时间7天后自动完成
        List<HashMap<String, Object>> list = ordersMapper.findListByParams(params);
        for(Map<String,Object> order:list){
            //更新订单状态
            Long id = (Long)order.get("id");
            this.complete(id);
        }
    }

    /**
     * 批量取消
     */
    @Override
    public void batchCancel() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 0);
        params.put("orderStatus", 1);
        params.put("expireMinute", 60);//过期时间单位分钟
        List<HashMap<String, Object>> list = ordersMapper.findListByParams(params);
        //更新订单状态
        for(Map<String,Object> order:list) {
            Long id = (Long) order.get("id");
            this.cancel(id);
        }
    }

	//申请退款
	@Override
	public void applyRefund(Long orderId, String reason) {
		Orders order = ordersMapper.getByPrimaryKey(orderId);
		//更新订单状态
		Orders orders = new Orders();
		orders.setModifyDate(new Date());
		orders.setId(orderId);
		orders.setOrdersStatus(4);//申请图款中
		orders.setId(order.getId());
		orders.setRefundReason(reason);
		ordersMapper.updateByPrimaryKeySelective(orders);
	}

	@Override
	public List<HashMap<String, Object>> findListByIds(Long[] ids) {
		return ordersMapper.findListByIds(ids);
	}

}
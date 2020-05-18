package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 
 * PaymentMapper数据库操作接口类
 * 
 **/

@Repository("paymentMapper")
public interface PaymentMapper extends BaseMapper<Payment,Long>{

	/**
	 * 通过单号查询
	 * @param sn
	 * @return
	 */
	Payment getBySn(String sn);

    Payment getBySuccess(@Param("ordersId") Long ordersId, @Param("type") Integer type);

}
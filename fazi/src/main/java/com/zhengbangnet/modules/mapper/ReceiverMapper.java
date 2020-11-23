package com.zhengbangnet.modules.mapper;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Receiver;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * ReceiverMapper数据库操作接口类
 * 
 **/

@Repository("receiverMapper")
public interface ReceiverMapper extends BaseMapper<Receiver, Long> {

	/**
	 * 依据会员ID查询收货人地址信息 add by hawkbird date 2018-04-04
	 */
	List<Map<String, Object>> queryAddressByMemberId(long memberId);
	
	/**
	 * 依据会员ID 更改默认地址为空
	 */
	int updateNoDefaultByMemberId(long memberId);

	/**
	 * 根据会员id查询默认地址
	 * @param memberId
	 * @return
	 */
    Receiver getDefault(Long memberId);
}
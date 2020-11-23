package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.BirthdayCard;
import org.springframework.stereotype.Repository;

/**
 * 
 * BirthdayCardMapper数据库操作接口类
 * 
 **/

@Repository("birthdayCardMapper")
public interface BirthdayCardMapper extends BaseMapper<BirthdayCard,Long>{



}
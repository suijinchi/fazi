package com.zhengbangnet.modules.mapper;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.MessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 
 * MessageLogMapper数据库操作接口类
 * 
 **/

@Repository("messageLogMapper")
public interface MessageLogMapper extends BaseMapper<MessageLog,Long>{

    /**
     * 通过手机号查找今天的短信日志
     * @param mobile 手机号
     * @return 短信日志
     */
    public MessageLog getByMobile(@Param("mobile")String mobile, @Param("type")Integer type);

    /**
     * 根据ip查询今天的短信量
     * @param ip ip地址
     * @return 短信量
     */
    public Long findCountByIp(@Param("ip")String ip,@Param("date")Date date);

    /**
     * 根据手机号查询今天的短信量
     * @param mobile
     * @param date
     * @return
     */
    public Long findCountByMobile(@Param("mobile")String mobile,@Param("date")Date date);

}
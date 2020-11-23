package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.MessageLog;

public interface MessageLogService extends BaseService<MessageLog,Long> {

    /**
     *
     * 通过手机号查找今天的短信日志
     * @param mobile 手机号
     * @param type
     * @return 短信日志
     */
    public MessageLog getByMobile(String mobile,Integer type);

    /**
     * 根据ip查询今天的短信量
     * @param ip ip地址
     * @return 短信量
     */
    public Long findCountByIp(String ip);

    /**
     * 根据手机号查询今天的短信量
     * @param ip ip地址
     * @return 短信量
     */
    public Long findCountByMobile(String mobile);

}
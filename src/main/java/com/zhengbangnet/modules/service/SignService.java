package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Sign;

import java.util.List;
import java.util.Map;

public interface SignService extends BaseService<Sign,Long> {


    List<Map<String,Object>> findDateByMember(Map<String, Object> params);

    void sign(Long memberId);
}
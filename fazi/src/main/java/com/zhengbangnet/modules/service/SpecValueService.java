package com.zhengbangnet.modules.service;
import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.SpecValue;

import java.util.List;

public interface SpecValueService extends BaseService<SpecValue,Long> {

    List<SpecValue> getBySpecNameId(Long specNameId);

    void save(Long[] valueIds, String[] valueNames, Long specNameId);

    void update(Long[] valueIds, String[] valueNames, Long specNameId);

    void deleteBySpecNameId(Long specNameId);
}
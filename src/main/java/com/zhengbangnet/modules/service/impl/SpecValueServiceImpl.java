package com.zhengbangnet.modules.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.common.service.impl.BaseServiceImpl;
import com.zhengbangnet.modules.entity.SpecValue;
import com.zhengbangnet.modules.service.SpecValueService;
import com.zhengbangnet.modules.mapper.SpecValueMapper;

import java.util.Date;
import java.util.List;

@Service("specValueServiceImpl")
@Transactional(readOnly=false)
public class SpecValueServiceImpl extends BaseServiceImpl<SpecValue,Long> implements SpecValueService{

	@Resource(name="specValueMapper")
	private SpecValueMapper specValueMapper;

	@Resource(name="specValueMapper")
	public void setBaseDao(BaseMapper<SpecValue,Long> baseMapper) {
		super.setBaseDao(baseMapper);
	}

	@Override
	public List<SpecValue> getBySpecNameId(Long specNameId) {
		return specValueMapper.getBySpecNameId(specNameId);
	}

	@Override
	public void save(Long[] valueIds, String[] valueNames, Long specNameId) {
		for(int i = 0; i < valueNames.length; i ++){
			SpecValue specValue = new SpecValue();
			specValue.setCreateDate(new Date());
			specValue.setModifyDate(new Date());
			specValue.setSpecNameId(specNameId);
			specValue.setValue(valueNames[i]);
			specValueMapper.insert(specValue);
		}
	}

	@Override
	public void update(Long[] valueIds, String[] valueNames, Long specNameId) {
		for(int i = 0; i < valueIds.length; i ++){
			Long valueId = valueIds[i];
			SpecValue specValue = new SpecValue();
			if (valueId == 0) {
				specValue.setCreateDate(new Date());
				specValue.setModifyDate(new Date());
				specValue.setSpecNameId(specNameId);
				specValue.setValue(valueNames[i]);
				specValueMapper.insert(specValue);
			} else {
				specValue.setId(valueId);
				specValue.setModifyDate(new Date());
				specValue.setValue(valueNames[i]);
				specValue.setSpecNameId(specNameId);
				specValueMapper.updateByPrimaryKey(specValue);
			}
		}
	}

	@Override
	public void deleteBySpecNameId(Long specNameId) {
		specValueMapper.deleteBySpecNameId(specNameId);
	}

}
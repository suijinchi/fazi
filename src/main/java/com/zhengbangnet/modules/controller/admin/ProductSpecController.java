package com.zhengbangnet.modules.controller.admin;

import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.modules.entity.ProductStockSpecNameValue;
import com.zhengbangnet.modules.entity.SpecName;
import com.zhengbangnet.modules.entity.SpecValue;
import com.zhengbangnet.modules.service.ProductStockSpecNameValueService;
import com.zhengbangnet.modules.service.SpecNameService;
import com.zhengbangnet.modules.service.SpecValueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("adminProductSpecController")
@RequestMapping("/admin/productSpec")
public class ProductSpecController extends AdminBaseController {

	@Resource(name = "specNameServiceImpl")
	private SpecNameService specNameService;

	@Resource(name = "specValueServiceImpl")
	private SpecValueService specValueService;

	@Resource(name = "productStockSpecNameValueServiceImpl")
	private ProductStockSpecNameValueService productStockSpecNameValueService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageable", pageable);
		Page<Map<String, Object>> page = specNameService.findPageByParams(params);
		for (int i = 0; i < page.getContent().size(); i ++) {
			Map<String, Object> data = page.getContent().get(i);
			Long specNameId = (Long) data.get("id");
			List<SpecValue> specValueList = specValueService.getBySpecNameId(specNameId);
			String specValues = "";
			if (specValueList != null && specValueList.size() > 0) {
				for (int j = 0; j < specValueList.size(); j ++) {
					specValues += specValueList.get(j).getValue() + " ";
				}
			}
			data.put("specValues", specValues);
		}
		model.addAttribute("page", page);
		return "/admin/product_spec/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/admin/product_spec/add";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
    Message save(String name, String[] valueNames, Long[] valueIds) {
		SpecName specName = new SpecName();
		specName.setCreateDate(new Date());
		specName.setModifyDate(new Date());
		specName.setName(name);
		specNameService.insert(specName);
		Long specNameId = specName.getId();
		if(valueNames != null && valueIds != null){
			specValueService.save(valueIds, valueNames, specNameId);
		}
		return Message.success("添加成功");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		SpecName specName = specNameService.getByPrimaryKey(id);
		model.addAttribute("specName", specName);
		List<SpecValue> specValueList = specValueService.getBySpecNameId(id);
		model.addAttribute("specValueList", specValueList);
		return "/admin/product_spec/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
    Message update(Long specNameId, String name, String[] valueNames, Long[] valueIds) {
		SpecName specName = new SpecName();
		specName.setId(specNameId);
		specName.setModifyDate(new Date());
		specName.setName(name);
		specNameService.updateByPrimaryKey(specName);
		if(valueNames != null && valueIds != null){
			specValueService.update(valueIds, valueNames, specNameId);
		}
		return Message.success("修改成功");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
    Message delete(Long specNameId) {

		List<ProductStockSpecNameValue> productStockSpecNameValueList = productStockSpecNameValueService.getBySpecNameId(specNameId);
		if (productStockSpecNameValueList.size() > 0) {
			return Message.error("规格下面存在商品");
		}
		specNameService.deleteByPrimaryKey(specNameId);
		specValueService.deleteBySpecNameId(specNameId);
		return Message.success("删除规格成功");
	}

	@RequestMapping(value = "/deleteValue", method = RequestMethod.POST)
	public @ResponseBody
    Message deleteValue(Long specValueId) {

		List<ProductStockSpecNameValue> productStockSpecNameValueList = productStockSpecNameValueService.getBySpecValueId(specValueId);
		if (productStockSpecNameValueList.size() > 0) {
			return Message.error("规格值下面存在商品");
		}
		specValueService.deleteByPrimaryKey(specValueId);
		return Message.success("删除规格值成功");
	}

}
package com.zhengbangnet.modules.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.zhengbangnet.common.annotation.SysLog;
import com.zhengbangnet.common.controller.AdminBaseController;
import com.zhengbangnet.common.lang.Message;
import com.zhengbangnet.common.page.Page;
import com.zhengbangnet.common.page.Pageable;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.common.utils.SettingUtils;
import com.zhengbangnet.modules.entity.*;
import com.zhengbangnet.modules.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Controller("adminProductController")
@RequestMapping("/admin/product")
public class ProductController extends AdminBaseController {

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "productStockServiceImpl")
	private ProductStockService productStockService;
	
	@Resource(name = "productTagServiceImpl")
	private ProductTagService productTagService;
	
	@Resource(name = "productImageServiceImpl")
	private ProductImageService productImageService;

	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "specNameServiceImpl")
	private SpecNameService specNameService;

	@Resource(name = "specValueServiceImpl")
	private SpecValueService specValueService;

	@Resource(name = "productComponentServiceImpl")
	private ProductComponentService productComponentService;

	@Resource(name = "componentServiceImpl")
	private ComponentService componentService;

	@Resource(name = "tagServiceImpl")
	private TagService tagService;

	@Resource(name = "productStockSpecNameValueServiceImpl")
	private ProductStockSpecNameValueService productStockSpecNameValueService;

	/**
	 * 列表 create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品管理",method="查询商品列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, Model model, String startTime, String endTime, String name, Long productCategoryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("name", name);
		params.put("pageable", pageable);
		params.put("productCategoryId", productCategoryId);
		Page< Map<String, Object>> page = productService.findPageByParams(params);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("name", name);
		model.addAttribute("page", page);
		model.addAttribute("productCategoryId", productCategoryId);
		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("setting", SettingUtils.get());
		return "/admin/product/list";
	}

	/**
	 * 添加页面 create by suijinchi on 2018-06-22
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {

		List<Tag> tagList = tagService.findByType(0);
		model.addAttribute("tagList", tagList);

		List<Component> componentList = componentService.findAll();
		model.addAttribute("componentList", componentList);

		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		model.addAttribute("productCategoryList", productCategoryList);

		List<SpecName> specNameList = specNameService.findAll();
		model.addAttribute("specNameList", specNameList);

		List<SpecValue> specValueList = specValueService.findAll();
		model.addAttribute("specValueList", specValueList);

		return "/admin/product/add";
	}

	/**
	 * 保存 modify by suijinchi on 2018-07-12
	 */
	@SysLog(module="商品管理",method="添加商品")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Message save(String sellDate,Integer type,String longShowUrl, String jsonString, Integer optionsRadios,Integer maxOffsetPoint,
									  Long productCategoryId, String memo, Integer stock, String sn, String subName, String name, String showUrl,
									  java.math.BigDecimal price, String content, String introduce, Integer isMarketable, Integer orders, Integer isTop,
									  Long[] tagIds, Long[] componentIds, String productImages[],Integer imgOrders[],Integer isBirthdayCard,Integer sweetness) {
		Product product = new Product();
		product.setType(type);
		product.setCreateDate(new Date());
		product.setModifyDate(new Date());
		product.setName(name);
		product.setSubname(subName);
		product.setShowUrl(showUrl);
		product.setLongShowUrl(longShowUrl);
		product.setContent(content);
		product.setSweetness(sweetness);
		product.setIntroduce(introduce);
		if (optionsRadios == 1) {
			product.setSpec(0);
		} else if (optionsRadios == 2) {
			product.setSpec(1);
		}
		product.setProductCategoryId(productCategoryId);
		product.setIsMarketable(isMarketable);
 		product.setOrders(orders);
		product.setIsTop(isTop);
 		product.setMemo(memo);
 		product.setIsBirthdayCard(isBirthdayCard);
		if(StringUtils.isNotBlank(sellDate)){
			product.setSellDate(DateUtils.stringToDate(sellDate,"yyyy-MM-dd HH:mm:ss"));
		}

		productService.insertSelective(product);
		Long id = product.getId();
		if(tagIds!=null&&tagIds.length>0){
			productTagService.save(tagIds,id);
		}
		if(componentIds!=null&&componentIds.length>0){
			productComponentService.save(componentIds,id);
		}
		if(imgOrders!=null && productImages!=null){
			productImageService.save(productImages, imgOrders, id);
		}

		if (optionsRadios == 1) {
			ProductStock productStock = new ProductStock();
			productStock.setCreateDate(new Date());
			productStock.setModifyDate(new Date());
			productStock.setName(name);
            productStock.setStock(stock);
            productStock.setPrice(price);
			productStock.setSoldOut(0);
			productStock.setMaxOffsetPoint(maxOffsetPoint);
			productStock.setSn(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS"));
			productStock.setProductId(id);
			productStock.setIsVoid(0);
			productStock.setIsDisabled(0);
			productStockService.insertSelective(productStock);
		} else if (optionsRadios == 2) {
			List<Object> list = JSONArray.parseArray(jsonString);
			if (list.size() > 0) {
				for (int i=0; i < list.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					ProductStock productStock = new ProductStock();
					productStock.setCreateDate(new Date());
					productStock.setModifyDate(new Date());
					productStock.setName(name);
					productStock.setProductId(id);
					productStock.setSoldOut(0);
					productStock.setSn(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS") + i);
					String priceValue = (String) map.get("price");
					if (StringUtils.isNotBlank(priceValue)) {
						productStock.setPrice(new BigDecimal(priceValue));
					}
					String specImg = (String) map.get("specImg");
					if (StringUtils.isNotBlank(specImg)) {
						productStock.setImgUrl((String) map.get("specImg"));
					}
					String stockValue = (String) map.get("stock");
					if (StringUtils.isNotBlank(stockValue)) {
						productStock.setStock(Integer.parseInt((String) map.get("stock")));
					}
					String people = (String) map.get("people");
					if (StringUtils.isNotBlank(people)) {
						productStock.setShareNum((String) map.get("people"));
					}
					String tableware = (String) map.get("tableware");
					if (StringUtils.isNotBlank(tableware)) {
						productStock.setTableware((String) map.get("tableware"));
					}
					String size = (String) map.get("size");
					if (StringUtils.isNotBlank(size)) {
						productStock.setSize((String) map.get("size"));
					}
					String maxPoint = (String) map.get("maxPoint");
					if (StringUtils.isNotBlank(maxPoint)) {
						productStock.setMaxOffsetPoint(Integer.parseInt((String) map.get("maxPoint")));
					}
					productStock.setIsDisabled(Integer.parseInt((String) map.get("isDisabled")));
					String specName = (String) map.get("spec_values");
					productStock.setSpecName(specName.replace(",", " "));
					productStock.setIsVoid(0);

					productStockService.insertSelective(productStock);

					Long productStockId = productStock.getId();
					String specValueIdsStr = (String) map.get("spec_ids");
					String[] specValueIds = specValueIdsStr.split(",");
					for (int j = 0; j < specValueIds.length; j ++) {
						SpecValue specValue = specValueService.getByPrimaryKey(Long.parseLong(specValueIds[j]));
						ProductStockSpecNameValue productStockSpecNameValue = new ProductStockSpecNameValue();
						productStockSpecNameValue.setProductStockId(productStockId);
						productStockSpecNameValue.setSpecNameId(specValue.getSpecNameId());
						productStockSpecNameValue.setSpecValueId(specValue.getId());
						productStockSpecNameValueService.insert(productStockSpecNameValue);
					}
				}
			}
		}

		return Message.success("添加成功");
	}
	
	/**
	 * 查看 create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品管理",method="查询商品详情")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, Model model) {

		Product product = productService.getByPrimaryKey(id);
		model.addAttribute("product", product);

		List<Component> componentList = componentService.findAll();
		model.addAttribute("componentList", componentList);

		List<Tag> tagList = tagService.findByType(0);
		model.addAttribute("tagList", tagList);

		List<ProductImage> productImageList = productImageService.getByProductId(id);
		model.addAttribute("productImageList", productImageList);

		List<ProductComponent> productComponentList = productComponentService.getByProductId(id);
		model.addAttribute("productComponentList", productComponentList);

		List<ProductTag> productTagList = productTagService.getByProductId(id);
		model.addAttribute("productTagList", productTagList);

		ProductCategory productCategory = productCategoryService.getByPrimaryKey(product.getProductCategoryId());
		model.addAttribute("productCategory", productCategory);

		List<SpecName> specNameList = specNameService.findAll();
		model.addAttribute("specNameList", specNameList);

		List<SpecValue> specValueList = specValueService.findAll();
		model.addAttribute("specValueList", specValueList);

		if (product.getSpec() == 0) {
			ProductStock productStock = productStockService.getByProductId(id);
			model.addAttribute("productStock", productStock);
		}

		if (product.getSpec() == 1) {
			List<ProductStockSpecNameValue> productStockSpecNameValueList = productStockSpecNameValueService.getByProductId(id);
			model.addAttribute("productStockSpecNameValueList", productStockSpecNameValueList);

			List<ProductStock> productStockList = new ArrayList<>();
			List<ProductStock> productStockList_s = productStockService.findByProductId(id);
			for (int i = 0; i < productStockList_s.size(); i ++) {
				ProductStock productStock = productStockList_s.get(i);
				String specName = productStock.getSpecName().replace(" ", ",");
				productStock.setSpecName(specName);
				productStockList.add(productStock);
			}
			Gson gson = new Gson();
			String productJson = gson.toJson(productStockList);
			model.addAttribute("productJson", productJson);
		}

		return "/admin/product/view";
	}

	/**
	 * 编辑 create by suijinchi on 2018-06-22
	 */	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {

		Product product = productService.getByPrimaryKey(id);
		model.addAttribute("product", product);

		List<Component> componentList = componentService.findAll();
		model.addAttribute("componentList", componentList);

		List<Tag> tagList = tagService.findByType(0);
		model.addAttribute("tagList", tagList);

		List<ProductImage> productImageList = productImageService.getByProductId(id);
		model.addAttribute("productImageList", productImageList);

		List<ProductComponent> productComponentList = productComponentService.getByProductId(id);
		model.addAttribute("productComponentList", productComponentList);

		List<ProductTag> productTagList = productTagService.getByProductId(id);
		model.addAttribute("productTagList", productTagList);

		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		model.addAttribute("productCategoryList", productCategoryList);

		List<SpecName> specNameList = specNameService.findAll();
		model.addAttribute("specNameList", specNameList);

		List<SpecValue> specValueList = specValueService.findAll();
		model.addAttribute("specValueList", specValueList);

		if (product.getSpec() == 0) {
			List<ProductStock> productStockList = productStockService.findByProductId(id);
			model.addAttribute("productStock", productStockList.get(0));
			Gson gson = new Gson();
			model.addAttribute("productJson", gson.toJson(new ArrayList<>()));
		}

        if (product.getSpec() == 1) {
            List<ProductStockSpecNameValue> productStockSpecNameValueList = productStockSpecNameValueService.getByProductId(id);
            model.addAttribute("productStockSpecNameValueList", productStockSpecNameValueList);

			List<ProductStock> productStockList = new ArrayList<>();
			List<ProductStock> productStockList_s = productStockService.findByProductId(id);
			for (int i = 0; i < productStockList_s.size(); i ++) {
				ProductStock productStock = productStockList_s.get(i);
				String specName = productStock.getSpecName().replace(" ", ",");
				productStock.setSpecName(specName);
				productStockList.add(productStock);
			}
			Gson gson = new Gson();
			String productJson = gson.toJson(productStockList);
			model.addAttribute("productJson", productJson);
		}

		return "/admin/product/edit";
	}

	/**
	 * 更新编辑 create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品管理",method="更新商品")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Message update(String sellDate,String longShowUrl,String jsonString, Integer optionsRadios, String subName, Long productCategoryId,
										String memo, Integer stock, Long id, String sn, String name, String showUrl,
										java.math.BigDecimal price, java.math.BigDecimal memberPrice, Integer point, java.math.BigDecimal marketPrice,
										String content, String introduce, Integer isMarketable, Integer orders, Integer isTop,
										Long[] tagIds, Long[] componentIds, String productImages[], Integer imgOrders[],Integer isBirthdayCard,
										Integer sweetness,Integer maxOffsetPoint) {
		Product product = productService.getByPrimaryKey(id);
		if (product == null) {
			return Message.error("编辑商品不存在");
		}
		product.setModifyDate(new Date());
		product.setName(name);
		product.setSweetness(sweetness);
		product.setSubname(subName);
		product.setShowUrl(showUrl);
		product.setLongShowUrl(longShowUrl);
		product.setContent(content);
		product.setIntroduce(introduce);
		product.setProductCategoryId(productCategoryId);
		product.setIsMarketable(isMarketable);
		product.setOrders(orders);
		product.setIsTop(isTop);
		product.setIsBirthdayCard(isBirthdayCard);
		product.setMemo(memo);
		if(StringUtils.isNotBlank(sellDate)){
			product.setSellDate(DateUtils.stringToDate(sellDate,"yyyy-MM-dd HH:mm:ss"));
		}

		productService.updateByPrimaryKeySelective(product);
		productTagService.deleteByProductId(id);
		if(tagIds!=null&&tagIds.length>0){
			productTagService.save(tagIds,id);
		}
		productComponentService.deleteByProductId(id);
		if(componentIds!=null&&componentIds.length>0){
			productComponentService.save(componentIds,id);
		}
		productImageService.deleteByProductId(id);
		if(imgOrders!=null && productImages!=null){
			productImageService.save(productImages, imgOrders, id);
		}
		Integer isSpec = 0;
		if (optionsRadios == 1) {
			isSpec = 0;
		} else if (optionsRadios == 2) {
			isSpec = 1;
		}
		if (isSpec.equals(product.getSpec())) {
			if (product.getSpec() == 0) {
				List<ProductStock> productStockList = productStockService.findByProductId(id);
				ProductStock productStock = productStockList.get(0);
				productStock.setModifyDate(new Date());
				productStock.setName(name);
				productStock.setStock(stock);
				productStock.setPrice(price);
				productStock.setMaxOffsetPoint(maxOffsetPoint);
				productStockService.updateByPrimaryKeySelective(productStock);
			} else if (product.getSpec() == 1) {
				productStockService.updateVoidStatus(id);
				List<Object> list = JSONArray.parseArray(jsonString);
				if (list.size() > 0) {
					for (int i=0; i < list.size(); i++) {
						Map<String, Object> map = (Map<String, Object>) list.get(i);
						String specName = (String) map.get("spec_values");
						specName = specName.replace(",", " ");
						ProductStock productStock = productStockService.getBySpecNameAndProductId(specName, id);
						if (productStock == null) {
							productStock = new ProductStock();
							productStock.setCreateDate(new Date());
							productStock.setModifyDate(new Date());
							productStock.setName(name);
							productStock.setProductId(id);
							productStock.setSoldOut(0);
							productStock.setSn(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS") + i);
							String priceValue = (String) map.get("price");
							if (StringUtils.isNotBlank(priceValue)) {
								productStock.setPrice(new BigDecimal(priceValue));
							}
							String specImg = (String) map.get("specImg");
							if (StringUtils.isNotBlank(specImg)) {
								productStock.setImgUrl((String) map.get("specImg"));
							}
							String stockValue = (String) map.get("stock");
							if (StringUtils.isNotBlank(stockValue)) {
								productStock.setStock(Integer.parseInt((String) map.get("stock")));
							}
							String people = (String) map.get("people");
							if (StringUtils.isNotBlank(people)) {
								productStock.setShareNum((String) map.get("people"));
							}
							String tableware = (String) map.get("tableware");
							if (StringUtils.isNotBlank(tableware)) {
								productStock.setTableware((String) map.get("tableware"));
							}
							String size = (String) map.get("size");
							if (StringUtils.isNotBlank(size)) {
								productStock.setSize((String) map.get("size"));
							}
							String maxPoint = (String) map.get("maxPoint");
							if (StringUtils.isNotBlank(maxPoint)) {
								productStock.setMaxOffsetPoint(Integer.parseInt((String) map.get("maxPoint")));
							}
							productStock.setIsDisabled(Integer.parseInt((String) map.get("isDisabled")));
							productStock.setSpecName(specName);
							productStock.setIsVoid(0);
							productStockService.insertSelective(productStock);

							Long productStockId = productStock.getId();
							String specValueIdsStr = (String) map.get("spec_ids");
							String[] specValueIds = specValueIdsStr.split(",");
							for (int j = 0; j < specValueIds.length; j ++) {
								SpecValue specValue = specValueService.getByPrimaryKey(Long.parseLong(specValueIds[j]));
								ProductStockSpecNameValue productStockSpecNameValue = new ProductStockSpecNameValue();
								productStockSpecNameValue.setProductStockId(productStockId);
								productStockSpecNameValue.setSpecNameId(specValue.getSpecNameId());
								productStockSpecNameValue.setSpecValueId(specValue.getId());
								productStockSpecNameValueService.insert(productStockSpecNameValue);
							}
						} else {
							productStock.setModifyDate(new Date());
							productStock.setName(name);
							String priceValue = (String) map.get("price");
							if (StringUtils.isNotBlank(priceValue)) {
								productStock.setPrice(new BigDecimal(priceValue));
							}
							String specImg = (String) map.get("specImg");
							if (StringUtils.isNotBlank(specImg)) {
								productStock.setImgUrl((String) map.get("specImg"));
							}
							String stockValue = (String) map.get("stock");
							if (StringUtils.isNotBlank(stockValue)) {
								productStock.setStock(Integer.parseInt((String) map.get("stock")));
							}
							String people = (String) map.get("people");
							if (StringUtils.isNotBlank(people)) {
								productStock.setShareNum((String) map.get("people"));
							}
							String tableware = (String) map.get("tableware");
							if (StringUtils.isNotBlank(tableware)) {
								productStock.setTableware((String) map.get("tableware"));
							}
							String size = (String) map.get("size");
							if (StringUtils.isNotBlank(size)) {
								productStock.setSize((String) map.get("size"));
							}
							String maxPoint = (String) map.get("maxPoint");
							if (StringUtils.isNotBlank(maxPoint)) {
								productStock.setMaxOffsetPoint(Integer.parseInt((String) map.get("maxPoint")));
							}
							productStock.setIsDisabled(Integer.parseInt((String) map.get("isDisabled")));
							productStock.setIsVoid(0);
							productStockService.updateByPrimaryKeySelective(productStock);

							Long productStockId = productStock.getId();
							productStockSpecNameValueService.deleteByProductStockId(productStockId);
							String specValueIdsStr = (String) map.get("spec_ids");
							String[] specValueIds = specValueIdsStr.split(",");
							for (int j = 0; j < specValueIds.length; j ++) {
								SpecValue specValue = specValueService.getByPrimaryKey(Long.parseLong(specValueIds[j]));
								ProductStockSpecNameValue productStockSpecNameValue = new ProductStockSpecNameValue();
								productStockSpecNameValue.setProductStockId(productStockId);
								productStockSpecNameValue.setSpecNameId(specValue.getSpecNameId());
								productStockSpecNameValue.setSpecValueId(specValue.getId());
								productStockSpecNameValueService.insert(productStockSpecNameValue);
							}
						}
					}
				}
			}
		} else {
			productStockService.updateVoidStatus(id);
			if (optionsRadios == 1) {
				ProductStock productStock = new ProductStock();
				productStock.setCreateDate(new Date());
				productStock.setModifyDate(new Date());
				productStock.setName(name);
				productStock.setStock(stock);
				productStock.setPrice(price);
				productStock.setSoldOut(0);
				productStock.setMaxOffsetPoint(maxOffsetPoint);
				productStock.setSn(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS"));
				productStock.setProductId(id);
				productStock.setIsVoid(0);
				productStock.setIsDisabled(0);
				productStockService.insertSelective(productStock);
			} else if (optionsRadios == 2) {
				List<Object> list = JSONArray.parseArray(jsonString);
				if (list.size() > 0) {
					for (int i=0; i < list.size(); i++) {
						Map<String, Object> map = (Map<String, Object>) list.get(i);
						ProductStock productStock = new ProductStock();
						productStock.setCreateDate(new Date());
						productStock.setModifyDate(new Date());
						productStock.setName(name);
						productStock.setProductId(id);
						productStock.setSoldOut(0);
						productStock.setSn(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS") + i);
						String priceValue = (String) map.get("price");
						if (StringUtils.isNotBlank(priceValue)) {
							productStock.setPrice(new BigDecimal(priceValue));
						}
						String specImg = (String) map.get("specImg");
						if (StringUtils.isNotBlank(specImg)) {
							productStock.setImgUrl((String) map.get("specImg"));
						}
						String stockValue = (String) map.get("stock");
						if (StringUtils.isNotBlank(stockValue)) {
							productStock.setStock(Integer.parseInt((String) map.get("stock")));
						}
						String people = (String) map.get("people");
						if (StringUtils.isNotBlank(people)) {
							productStock.setShareNum((String) map.get("people"));
						}
						String tableware = (String) map.get("tableware");
						if (StringUtils.isNotBlank(tableware)) {
							productStock.setTableware((String) map.get("tableware"));
						}
						String size = (String) map.get("size");
						if (StringUtils.isNotBlank(size)) {
							productStock.setSize((String) map.get("size"));
						}
						String maxPoint = (String) map.get("maxPoint");
						if (StringUtils.isNotBlank(maxPoint)) {
							productStock.setMaxOffsetPoint(Integer.parseInt((String) map.get("maxPoint")));
						}
						productStock.setIsDisabled(Integer.parseInt((String) map.get("isDisabled")));
						String specName = (String) map.get("spec_values");
						productStock.setSpecName(specName.replace(",", " "));
						productStock.setIsVoid(0);
						productStockService.insertSelective(productStock);

						Long productStockId = productStock.getId();
						String specValueIdsStr = (String) map.get("spec_ids");
						String[] specValueIds = specValueIdsStr.split(",");
						for (int j = 0; j < specValueIds.length; j ++) {
							SpecValue specValue = specValueService.getByPrimaryKey(Long.parseLong(specValueIds[j]));
							ProductStockSpecNameValue productStockSpecNameValue = new ProductStockSpecNameValue();
							productStockSpecNameValue.setProductStockId(productStockId);
							productStockSpecNameValue.setSpecNameId(specValue.getSpecNameId());
							productStockSpecNameValue.setSpecValueId(specValue.getId());
							productStockSpecNameValueService.insert(productStockSpecNameValue);
						}
					}
				}
			}
		}
		product.setSpec(isSpec);
		productService.updateByPrimaryKeySelective(product);

		return Message.success("修改成功");
	}
	
	/**
	 * 上架 create by suijinchi on 2018-06-22
	 * @param ids
	 * @return
	 */
	@SysLog(module="商品管理",method="上架商品")
	@RequestMapping(value = "/productUp", method = RequestMethod.POST)
	public @ResponseBody Message productUp(Long[] ids) {
		if (ids != null && ids.length > 0) {
			productService.productUpAndDown(ids,1);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	/**
	 * 下架 create by suijinchi on 2018-06-22
	 * @param ids
	 * @return
	 */
	@SysLog(module="商品管理",method="下架商品")
	@RequestMapping(value = "/productDown", method = RequestMethod.POST)
	public @ResponseBody Message productDown(Long[] ids) {
		if (ids != null && ids.length > 0) {
			productService.productUpAndDown(ids,0);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}

	/**
	 * 删除(含批量删除) create by suijinchi on 2018-06-22
	 */
	@SysLog(module="商品管理",method="删除商品")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null && ids.length > 0) {
			productService.deleteByPrimaryKeys(ids);
			for(int i=0; i<ids.length; i++){
				productTagService.deleteByProductId(ids[i]);
				productImageService.deleteByProductId(ids[i]);
				productComponentService.deleteByProductId(ids[i]);
				productStockService.deleteByProductId(ids[i]);
			}		
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}







	@RequestMapping(value = "/stock", method = RequestMethod.GET)
	public String stock(Long id,Model model) {
		Product product = productService.getByPrimaryKey(id);
		model.addAttribute("product", product);

		List<Component> componentList = componentService.findAll();
		model.addAttribute("componentList", componentList);

		List<Tag> tagList = tagService.findByType(0);
		model.addAttribute("tagList", tagList);

		List<ProductImage> productImageList = productImageService.getByProductId(id);
		model.addAttribute("productImageList", productImageList);

		List<ProductComponent> productComponentList = productComponentService.getByProductId(id);
		model.addAttribute("productComponentList", productComponentList);

		List<ProductTag> productTagList = productTagService.getByProductId(id);
		model.addAttribute("productTagList", productTagList);

		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		model.addAttribute("productCategoryList", productCategoryList);

		List<SpecName> specNameList = specNameService.findAll();
		model.addAttribute("specNameList", specNameList);

		List<SpecValue> specValueList = specValueService.findAll();
		model.addAttribute("specValueList", specValueList);

		if (product.getSpec() == 0) {
			List<ProductStock> productStockList = productStockService.findByProductId(id);
			model.addAttribute("productStock", productStockList.get(0));
			Gson gson = new Gson();
			model.addAttribute("productJson", gson.toJson(new ArrayList<>()));
		}

		if (product.getSpec() == 1) {
			List<ProductStockSpecNameValue> productStockSpecNameValueList = productStockSpecNameValueService.getByProductId(id);
			model.addAttribute("productStockSpecNameValueList", productStockSpecNameValueList);

			List<ProductStock> productStockList = new ArrayList<>();
			List<ProductStock> productStockList_s = productStockService.findByProductId(id);
			for (int i = 0; i < productStockList_s.size(); i ++) {
				ProductStock productStock = productStockList_s.get(i);
				String specName = productStock.getSpecName().replace(" ", ",");
				productStock.setSpecName(specName);
				productStockList.add(productStock);
			}
			Gson gson = new Gson();
			String productJson = gson.toJson(productStockList);
			model.addAttribute("productJson", productJson);
		}
		if(product.getSpec()==0){
			return "/admin/product/stock_nospec";
		}
		return "/admin/product/stock_spec";
	}


	@SysLog(module="商品管理",method="更新商品库存")
	@RequestMapping(value = "/update_stock", method = RequestMethod.POST)
	public @ResponseBody Message update_stock(String jsonString,Long id,Integer stock,BigDecimal price,Integer maxOffsetPoint) {

		Product product = productService.getByPrimaryKey(id);
		if (product == null) {
			return Message.error("编辑商品不存在");
		}
		product.setModifyDate(new Date());
		productService.updateByPrimaryKeySelective(product);
		if (product.getSpec() == 0) {
			List<ProductStock> productStockList = productStockService.findByProductId(id);
			ProductStock productStock = productStockList.get(0);
			productStock.setModifyDate(new Date());
			productStock.setStock(stock);
			productStock.setPrice(price);
			productStock.setMaxOffsetPoint(maxOffsetPoint);
			productStockService.updateByPrimaryKeySelective(productStock);
		} else if (product.getSpec() == 1) {
			List<Object> list = JSONArray.parseArray(jsonString);
			if (list.size() > 0) {
				for (int i=0; i < list.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) list.get(i);
					String specName = (String) map.get("spec_values");
					specName = specName.replace(",", " ");
					ProductStock productStock = productStockService.getBySpecNameAndProductId(specName, id);
					productStock.setModifyDate(new Date());
					productStock.setPrice(new BigDecimal((String) map.get("price")));
					productStock.setImgUrl((String) map.get("specImg"));
					productStock.setStock(Integer.parseInt((String) map.get("stock")));
					productStock.setShareNum((String) map.get("people"));
					productStock.setTableware((String) map.get("tableware"));
					productStock.setSize((String) map.get("size"));
					productStock.setMaxOffsetPoint(Integer.parseInt((String) map.get("maxPoint")));
					productStockService.updateByPrimaryKeySelective(productStock);

					Long productStockId = productStock.getId();
					productStockSpecNameValueService.deleteByProductStockId(productStockId);
					String specValueIdsStr = (String) map.get("spec_ids");
					String[] specValueIds = specValueIdsStr.split(",");
					for (int j = 0; j < specValueIds.length; j ++) {
						SpecValue specValue = specValueService.getByPrimaryKey(Long.parseLong(specValueIds[j]));
						ProductStockSpecNameValue productStockSpecNameValue = new ProductStockSpecNameValue();
						productStockSpecNameValue.setProductStockId(productStockId);
						productStockSpecNameValue.setSpecNameId(specValue.getSpecNameId());
						productStockSpecNameValue.setSpecValueId(specValue.getId());
						productStockSpecNameValueService.insert(productStockSpecNameValue);
					}
				}
			}
		}
		return Message.success("修改成功");
	}



}
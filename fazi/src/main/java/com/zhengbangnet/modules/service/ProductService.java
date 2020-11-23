package com.zhengbangnet.modules.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhengbangnet.common.service.BaseService;
import com.zhengbangnet.modules.entity.Product;

public interface ProductService extends BaseService<Product,Long> {

	/**
	 * 批量上下架商品
	 * @param ids
	 * @param i
	 */
	void productUpAndDown(Long[] ids, Integer i);

	/**
	 * 根据商品编号查询商品
	 * @param sn
	 * @return
	 */
	Product getBySn(String sn);

	/**
	 * 根据商品分类查询商品
	 * @param
	 * @return
	 */
	List<Map<String, Object>> getByProductCategoryId(Long productCategoryId);

	/**
	 * 根据条件查询商品数量
	 * @param params
	 * @return
	 */
    Long findCountsByParams(Map<String, Object> params);

	/**
	 * 查询商品销售额排行
	 * @return
	 */
	List<Map<String, Object>> findSaleSort();

	/**
	 * 查询商品规格及相应的规格值
	 * @param productId
	 * @return
	 */
    List<Map<String,Object>> findSpecNameAndValue(Long productId);

	List<Map<String,Object>> findSpecNameAndValue(Long productId, Long[] specValues);

    /**
     * 查询规格商品
     * @param productId
     * @return
     */
    List<HashMap<String,Object>> findSpecProduct(Long productId);

}
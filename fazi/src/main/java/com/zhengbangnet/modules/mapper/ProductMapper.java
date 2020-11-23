package com.zhengbangnet.modules.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zhengbangnet.common.mapper.BaseMapper;
import com.zhengbangnet.modules.entity.Product;

/**
 * 
 * ProductMapper数据库操作接口类
 * 
 **/

@Repository("productMapper")
public interface ProductMapper extends BaseMapper<Product,Long>{

	/**
	 * 上架商品
	 * @param id
	 */
	void updateProductUp(Long id);

	/**
	 * 下架商品
	 * @param id
	 */
	void updateProductDown(Long id);

	/**
	 * 根据编号查询
	 * @param sn
	 * @return
	 */
	Product getBySn(String sn);

	/**
	 * 根据商品分类查询商品
	 * @param productCategoryId
	 * @return
	 */
    List<Map<String,Object>> getByProductCategoryId(Long productCategoryId);

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
    List<Map<String,Object>> findSaleSort();

	/**
	 * 根据商品id查询商品规格属性名称
	 * @param productId
	 * @return
	 */
    List<Map<String,Object>> findSpecName(Long productId);
	/**
	 * 根据属性名称查询商品规格值
	 * @param productId
	 * @param specNameId
	 * @return
	 */
	List<Map<String,Object>> findSpecValue(@Param("productId") Long productId,@Param("specNameId") Long specNameId);

	/**
	 * 根据商品id、规格属性id、已选择规格值id筛选相应的商品，再把相应的规格值显示出来
	 * @param productId
	 * @param specNameId
	 * @param specValues
	 * @return
	 */
	List<Long> findSpecValueId(@Param("productId")Long productId,@Param("specNameId") Long specNameId,@Param("specValues") Long[] specValues);
}
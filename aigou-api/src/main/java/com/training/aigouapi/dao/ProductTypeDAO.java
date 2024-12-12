package com.training.aigouapi.dao;

import com.training.aigouapi.entity.ProductType;

import java.util.List;

/**
 * 商品类型表数据操作接口
 *
 * @author ethan
 */
public interface ProductTypeDAO {
    /**
     * 查询商品类型分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 商品类型列表
     */
    List<ProductType> selectLimit(int start, int limit);

    /**
     * 查询商品类型总数
     *
     * @return 商品类型总数
     */
    long selectCount();

    /**
     * 查询所有商品类型
     *
     * @return 商品类型列表
     */
    List<ProductType> selectAll();

    /**
     * 按id查询商品类型
     *
     * @param id 商品类型ID
     * @return 商品类型对象，如果不存在则返回null
     */
    ProductType selectId(String id);

    /**
     * 插入商品类型
     *
     * @param productType 商品类型对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(ProductType productType);

    /**
     * 更新商品类型
     *
     * @param productType 商品类型对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(ProductType productType);

    /**
     * 删除商品类型
     *
     * @param id 商品类型ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);
}

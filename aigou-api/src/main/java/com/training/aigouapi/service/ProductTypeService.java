package com.training.aigouapi.service;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ProductType;

import java.util.List;

/**
 * 商品类型表业务逻辑接口
 *
 * @author ethan
 */
public interface ProductTypeService {
    /**
     * 分页查询商品类型信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<ProductType> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有商品类型
     *
     * @return 商品类型列表
     */
    List<ProductType> findAll();

    /**
     * 根据ID查询商品类型
     *
     * @param id 商品类型ID
     * @return 商品类型对象
     */
    ProductType findId(String id);

    /**
     * 删除商品类型
     *
     * @param id 商品类型ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String id);

    /**
     * 更新商品类型信息
     *
     * @param productType 商品类型对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(ProductType productType);

    /**
     * 添加新商品类型
     *
     * @param productType 商品类型对象
     * @return 是否添加成功，true表示成功，false表示失败
     */
    boolean save(ProductType productType);
}

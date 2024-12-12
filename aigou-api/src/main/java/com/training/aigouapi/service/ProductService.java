package com.training.aigouapi.service;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.Product;

import java.util.List;

/**
 * 商品表业务逻辑接口
 *
 * @author ethan
 */
public interface ProductService {
    /**
     * 分页查询商品信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<Product> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有商品
     *
     * @return 商品列表
     */
    List<Product> findAll();

    /**
     * 根据商品ID查询商品
     *
     * @param productId 商品ID
     * @return 商品对象
     */
    Product findId(String productId);

    /**
     * 插入新商品
     *
     * @param product 商品对象
     * @return 是否插入成功，true表示成功，false表示失败
     */
    boolean save(Product product);

    /**
     * 删除商品
     *
     * @param productId 商品ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String productId);

    /**
     * 更新商品信息
     *
     * @param product 商品对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(Product product);
}

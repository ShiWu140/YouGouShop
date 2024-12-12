package com.training.aigouapi.service;

import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ShopCartProduct;

import java.util.List;

/**
 * 购物车商品表业务逻辑接口
 *
 * @author ethan
 */
public interface ShopCartProductService {
    /**
     * 分页查询购物车商品信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<ShopCartProduct> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有购物车商品信息
     *
     * @return 购物车商品信息列表
     */
    List<ShopCartProduct> findAll();

    /**
     * 根据ID查询购物车商品信息
     *
     * @param id 购物车商品ID
     * @return 购物车商品对象
     */
    ShopCartProduct findId(String id);

    /**
     * 删除购物车商品信息
     *
     * @param id 购物车商品ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String id);

    /**
     * 更新购物车商品信息
     *
     * @param shopCartProduct 购物车商品对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(ShopCartProduct shopCartProduct);

    /**
     * 添加新的购物车商品信息
     *
     * @param shopCartProduct 购物车商品对象
     * @return 是否添加成功，true表示成功，false表示失败
     */
    boolean save(ShopCartProduct shopCartProduct);
}

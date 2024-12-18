package com.training.aigouapi.service;

import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.ShopCart;

import java.util.List;

/**
 * 购物车业务逻辑接口
 *
 * @author ethan
 */
public interface ShopCartService {
    /**
     * 分页查询购物车记录信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<ShopCart> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有购物车记录
     *
     * @return 购物车记录列表
     */
    List<ShopCart> findAll();

    /**
     * 根据购物车ID查询购物车记录
     *
     * @param cartId 购物车记录ID
     * @return 购物车记录对象
     */
    ShopCart findId(String cartId);

    /**
     * 添加新的购物车记录
     *
     * @param shopCart 新的购物车记录对象
     * @return 是否添加成功，true表示成功，false表示失败
     */
    boolean save(ShopCart shopCart);

    /**
     * 删除购物车记录
     *
     * @param cartId 购物车记录ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String cartId);

    /**
     * 更新购物车记录
     *
     * @param shopCart 需要更新的购物车记录对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(ShopCart shopCart);
}

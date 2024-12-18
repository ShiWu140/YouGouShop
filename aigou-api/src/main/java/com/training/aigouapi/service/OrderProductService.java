package com.training.aigouapi.service;

import com.training.aigouapi.entity.OrderProduct;
import com.training.aigouapi.common.PageEntity;

import java.util.List;

/**
 * 订单商品表业务逻辑接口
 *
 * @author ethan
 */
public interface OrderProductService {
    /**
     * 查询商品分页信息
     *
     * @param current  当前页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageEntity<OrderProduct> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有商品
     *
     * @return 商品列表
     */
    List<OrderProduct> findAll();

    /**
     * 根据商品ID查询商品
     *
     * @param id 商品ID
     * @return 商品对象
     */
    OrderProduct findId(String id);

    /**
     * 根据商品ID删除商品
     *
     * @param id 商品ID
     * @return 删除结果
     */
    boolean remove(String id);

    /**
     * 更新商品信息
     *
     * @param orderProduct 商品对象
     * @return 更新结果
     */
    boolean update(OrderProduct orderProduct);

    /**
     * 插入商品
     *
     * @param orderProduct 商品对象
     * @return 插入结果
     */
    boolean save(OrderProduct orderProduct);
}

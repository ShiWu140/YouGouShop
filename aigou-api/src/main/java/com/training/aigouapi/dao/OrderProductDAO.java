package com.training.aigouapi.dao;

import com.training.aigouapi.entity.OrderProduct;

import java.util.List;

/**
 * 订单商品表数据操作接口
 *
 * @author ethan
 */
public interface OrderProductDAO {
    /**
     * 查询订单商品分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 订单商品列表
     */
    List<OrderProduct> selectLimit(int start, int limit);

    /**
     * 查询订单商品总数
     *
     * @return 订单商品总数
     */
    long selectCount();

    /**
     * 查询所有订单商品信息
     *
     * @return 订单商品列表
     */
    List<OrderProduct> selectAll();

    /**
     * 按订单商品id查询商品
     *
     * @param id 订单商品ID
     * @return 订单商品对象，如果不存在则返回null
     */
    OrderProduct selectId(String id);

    /**
     * 插入订单商品信息
     *
     * @param orderProduct 订单商品对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(OrderProduct orderProduct);

    /**
     * 更新订单商品信息
     *
     * @param orderProduct 订单商品对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(OrderProduct orderProduct);

    /**
     * 删除订单商品信息
     *
     * @param id 订单商品ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);
}

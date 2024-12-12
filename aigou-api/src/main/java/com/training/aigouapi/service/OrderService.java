package com.training.aigouapi.service;

import com.training.aigouapi.entity.Order;
import com.training.aigouapi.entity.PageEntity;

import java.util.List;

/**
 * 订单表业务逻辑接口
 *
 * @author ethan
 */
public interface OrderService {
    /**
     * 分页查询订单信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<Order> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有订单
     *
     * @return 订单列表
     */
    List<Order> findAll();

    /**
     * 根据订单ID查询订单
     *
     * @param orderId 订单ID
     * @return 订单对象
     */
    Order findId(String orderId);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String orderId);

    /**
     * 更新订单信息
     *
     * @param order 订单对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(Order order);

    /**
     * 添加新订单
     *
     * @param order 订单对象
     * @return 是否添加成功，true表示成功，false表示失败
     */
    boolean save(Order order);
}

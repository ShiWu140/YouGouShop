package com.training.aigouapi.service.impl;


import com.training.aigouapi.mapper.OrderMapper;
import com.training.aigouapi.entity.Order;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    /**
     * 调用dao层对象
     */
    @Resource
    private OrderMapper orderMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<Order> findPage(Integer current, Integer pageSize) {
        //分页查询数据
        List<Order> orders = orderMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<Order> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(orderMapper.selectCount());
        pageEntity.setRecords(orders);
        return pageEntity;
    }

    /**
     * 查询所有订单信息
     *
     * @return 订单信息列表
     */
    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }

    /**
     * 根据订单id查询订单信息
     *
     * @param orderId
     * @return 订单信息
     */
    @Override
    public Order findId(String orderId) {
        return orderMapper.selectId(orderId);
    }

    /**
     * 添加订单信息
     *
     * @param order
     * @return 是否添加成功 true 成功 false 失败
     */
    @Override
    public boolean save(Order order) {
        return orderMapper.insert(order);
    }

    /**
     * 删除订单信息
     *
     * @param orderId
     * @return 是否删除成功 true 成功 false 失败
     */
    @Override
    public boolean remove(String orderId) {
        return orderMapper.delete(orderId);
    }

    /**
     * 更新订单信息
     *
     * @param order
     * @return 是否更新成功 true 成功 false 失败
     */
    @Override
    public boolean update(Order order) {
        return orderMapper.update(order);
    }
}
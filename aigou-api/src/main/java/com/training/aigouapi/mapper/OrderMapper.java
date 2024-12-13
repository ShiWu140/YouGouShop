package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface OrderMapper {
    /**
     * 查询订单分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 订单列表
     */
    List<Order> selectLimit(int start, int limit);

    /**
     * 查询订单总数
     *
     * @return 订单总数
     */
    long selectCount();

    /**
     * 查询所有订单
     *
     * @return 订单列表
     */
    List<Order> selectAll();

    /**
     * 根据订单id查询订单
     *
     * @param orderId 订单ID
     * @return 订单对象，如果不存在则返回null
     */
    Order selectId(String orderId);

    /**
     * 插入新订单
     *
     * @param order 订单对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(Order order);

    /**
     * 删除订单
     *
     * @param orderId 订单ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String orderId);

    /**
     * 更新订单
     *
     * @param order 订单对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(Order order);
}

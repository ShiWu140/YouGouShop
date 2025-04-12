package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.entity.request.OrderDetailDTO;
import com.training.aigoushopapi.entity.request.OrderRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IOrderService extends IService<Order> {

    List<OrderDetailDTO> getOrderDetailsByUserId(String userId);

    /**
     * 统计指定时间范围内的订单数量
     */
    long countOrdersByDate(LocalDateTime start, LocalDateTime end);

    /**
     * 计算指定时间范围内的营收金额
     */
    BigDecimal getRevenueByDate(LocalDateTime start, LocalDateTime end);

    /**
     * 统计待处理订单数量
     */
    long countPendingOrders();

    /**
     * 统计紧急订单数量
     */
    long countUrgentOrders();

    /**
     * 统计待发货订单数量
     */
    long countToBeShippedOrders();

    /**
     * 获取订单状态统计数据
     * @return 包含各状态订单数量的Map
     */
    Map<String, Long> getOrderStatusStats();
}

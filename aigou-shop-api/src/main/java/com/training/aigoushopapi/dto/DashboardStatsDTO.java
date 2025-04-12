package com.training.aigoushopapi.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    // 今日订单统计
    private OrderStats todayOrders;
    // 今日营收统计
    private RevenueStats todayRevenue;
    // 待处理订单统计
    private PendingStats pendingOrders;
    // 待发货统计
    private ShippingStats toBeShipped;

    @Data
    public static class OrderStats {
        private Integer count;        // 订单数量
        private Double increase;      // 增长率
    }

    @Data
    public static class RevenueStats {
        private Double amount;        // 营收金额
        private Double increase;      // 增长率
    }

    @Data
    public static class PendingStats {
        private Integer count;        // 待处理订单数量
        private Integer urgentCount;  // 紧急订单数量
    }

    @Data
    public static class ShippingStats {
        private Integer count;        // 待发货数量
    }
} 
package com.training.aigoushopapi.service.impl;

import com.training.aigoushopapi.dto.DashboardStatsDTO;
import com.training.aigoushopapi.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        // 1. 获取今日订单统计
        stats.setTodayOrders(getTodayOrderStats());
        
        // 2. 获取今日营收统计
        stats.setTodayRevenue(getTodayRevenueStats());
        
        // 3. 获取待处理订单统计
        stats.setPendingOrders(getPendingOrderStats());
        
        // 4. 获取待发货统计
        stats.setToBeShipped(getToBeShippedStats());
        
        return stats;
    }

    private DashboardStatsDTO.OrderStats getTodayOrderStats() {
        DashboardStatsDTO.OrderStats stats = new DashboardStatsDTO.OrderStats();
        
        String sql = """
            SELECT 
                COUNT(*) as today_count,
                COALESCE(
                    (SELECT COUNT(*) FROM s_order 
                     WHERE DATE(create_time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)
                    ), 0
                ) as yesterday_count
            FROM s_order 
            WHERE DATE(create_time) = CURDATE()
        """;
        
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        
        Long todayCount = (Long) result.get("today_count");
        Long yesterdayCount = (Long) result.get("yesterday_count");
        
        stats.setCount(todayCount.intValue());
        
        // 计算增长率
        if (yesterdayCount > 0) {
            double increase = ((todayCount - yesterdayCount) * 100.0) / yesterdayCount;
            stats.setIncrease(Math.round(increase * 10.0) / 10.0); // 保留一位小数
        } else {
            stats.setIncrease(100.0); // 如果昨天是0，则增长率为100%
        }
        
        return stats;
    }

    private DashboardStatsDTO.RevenueStats getTodayRevenueStats() {
        DashboardStatsDTO.RevenueStats stats = new DashboardStatsDTO.RevenueStats();
        
        String sql = """
            SELECT 
                COALESCE(SUM(p.price * op.product_num), 0) as today_revenue,
                COALESCE(
                    (SELECT SUM(p2.price * op2.product_num) 
                     FROM s_order o2 
                     JOIN s_order_product op2 ON o2.id = op2.order_id
                     JOIN s_product p2 ON p2.id = op2.product_id
                     WHERE DATE(o2.create_time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)
                    ), 0
                ) as yesterday_revenue
            FROM s_order o
            JOIN s_order_product op ON o.id = op.order_id
            JOIN s_product p ON p.id = op.product_id
            WHERE DATE(o.create_time) = CURDATE()
        """;
        
        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        
        Double todayRevenue = ((Number) result.get("today_revenue")).doubleValue();
        Double yesterdayRevenue = ((Number) result.get("yesterday_revenue")).doubleValue();
        
        stats.setAmount(Math.round(todayRevenue * 100.0) / 100.0); // 保留两位小数
        
        // 计算增长率
        if (yesterdayRevenue > 0) {
            double increase = ((todayRevenue - yesterdayRevenue) * 100.0) / yesterdayRevenue;
            stats.setIncrease(Math.round(increase * 10.0) / 10.0); // 保留一位小数
        } else {
            stats.setIncrease(100.0);
        }
        
        return stats;
    }

    private DashboardStatsDTO.PendingStats getPendingOrderStats() {
        DashboardStatsDTO.PendingStats stats = new DashboardStatsDTO.PendingStats();
        
        String sql = "SELECT COUNT(*) as pending_count FROM s_order WHERE state = 0";
        Integer pendingCount = jdbcTemplate.queryForObject(sql, Integer.class);
        
        // 获取紧急订单数量（创建时间超过24小时的待处理订单）
        String urgentSql = """
            SELECT COUNT(*) as urgent_count 
            FROM s_order 
            WHERE state = 0 
            AND create_time <= DATE_SUB(NOW(), INTERVAL 24 HOUR)
        """;
        Integer urgentCount = jdbcTemplate.queryForObject(urgentSql, Integer.class);
        
        stats.setCount(pendingCount);
        stats.setUrgentCount(urgentCount);
        
        return stats;
    }

    private DashboardStatsDTO.ShippingStats getToBeShippedStats() {
        DashboardStatsDTO.ShippingStats stats = new DashboardStatsDTO.ShippingStats();
        
        // 假设state=1表示已支付待发货
        String sql = "SELECT COUNT(*) as to_be_shipped FROM s_order WHERE state = 1";
        Integer toBeShipped = jdbcTemplate.queryForObject(sql, Integer.class);
        
        stats.setCount(toBeShipped);
        
        return stats;
    }
} 
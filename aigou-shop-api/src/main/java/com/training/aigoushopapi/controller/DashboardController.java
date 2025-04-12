package com.training.aigoushopapi.controller;

import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.service.IOrderProductService;
import com.training.aigoushopapi.service.IOrderService;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/dashboard")
@ResponseResult
public class DashboardController {

    @Resource
    private IOrderService orderService;
    
    @Resource
    private IOrderProductService orderProductService;
    
    @Resource
    private IProductService productService;

    @GetMapping("/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> result = new HashMap<>();
        
        // 获取今天的订单数据
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime yesterday = today.minusDays(1);
        
        // 今日订单数
        long todayOrderCount = orderService.countOrdersByDate(today, LocalDateTime.now());
        long yesterdayOrderCount = orderService.countOrdersByDate(yesterday, today);
        double orderIncrease = yesterdayOrderCount == 0 ? 100 : 
            ((double)(todayOrderCount - yesterdayOrderCount) / yesterdayOrderCount) * 100;
        
        // 今日营收
        BigDecimal todayRevenue = orderService.getRevenueByDate(today, LocalDateTime.now());
        BigDecimal yesterdayRevenue = orderService.getRevenueByDate(yesterday, today);
        double revenueIncrease = yesterdayRevenue.compareTo(BigDecimal.ZERO) == 0 ? 100 :
            todayRevenue.subtract(yesterdayRevenue).divide(yesterdayRevenue, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100")).doubleValue();
        
        // 待处理订单
        long pendingOrders = orderService.countPendingOrders();
        long urgentOrders = orderService.countUrgentOrders();
        
        // 需要发货的订单
        long toBeShipped = orderService.countToBeShippedOrders();
        
        // 最近7天的营收数据
        List<Map<String, Object>> revenueData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 6; i >= 0; i--) {
            LocalDateTime start = today.minusDays(i);
            LocalDateTime end = start.plusDays(1);
            BigDecimal revenue = orderService.getRevenueByDate(start, end);
            
            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", start.format(formatter));
            dayData.put("revenue", revenue);
            revenueData.add(dayData);
        }
        
        // 订单状态统计
        Map<String, Long> orderStatusStats = orderService.getOrderStatusStats();
        
        // 组装返回数据
        result.put("todayOrders", new HashMap<String, Object>() {{
            put("count", todayOrderCount);
            put("increase", orderIncrease);
        }});
        
        result.put("todayRevenue", new HashMap<String, Object>() {{
            put("amount", todayRevenue);
            put("increase", revenueIncrease);
        }});
        
        result.put("pendingOrders", new HashMap<String, Object>() {{
            put("count", pendingOrders);
            put("urgentCount", urgentOrders);
        }});
        
        result.put("toBeShipped", new HashMap<String, Object>() {{
            put("count", toBeShipped);
        }});
        
        result.put("revenueData", revenueData);
        result.put("orderStatusStats", orderStatusStats);
        
        return result;
    }
} 
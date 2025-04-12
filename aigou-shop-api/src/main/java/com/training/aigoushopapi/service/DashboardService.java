package com.training.aigoushopapi.service;

import com.training.aigoushopapi.dto.DashboardStatsDTO;

public interface DashboardService {
    /**
     * 获取仪表盘统计数据
     * @return 统计数据DTO
     */
    DashboardStatsDTO getDashboardStats();
} 
package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.OrderProduct;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IOrderProductService extends IService<OrderProduct> {
    /**
     * 根据订单ID获取订单商品列表
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    List<OrderProduct> getByOrderId(String orderId);
}

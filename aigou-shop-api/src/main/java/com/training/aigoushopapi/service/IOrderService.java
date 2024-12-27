package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.entity.request.OrderDetailDTO;

import java.util.List;

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
}

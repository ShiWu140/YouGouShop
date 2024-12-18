package com.training.aigoushopapi.service.impl;

import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.mapper.OrderMapper;
import com.training.aigoushopapi.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}

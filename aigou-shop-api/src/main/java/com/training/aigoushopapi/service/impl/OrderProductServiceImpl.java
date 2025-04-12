package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.mapper.OrderProductMapper;
import com.training.aigoushopapi.service.IOrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct> implements IOrderProductService {

    @Override
    public List<OrderProduct> getByOrderId(String orderId) {
        LambdaQueryWrapper<OrderProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderProduct::getOrderId, orderId);
        return this.list(wrapper);
    }
}

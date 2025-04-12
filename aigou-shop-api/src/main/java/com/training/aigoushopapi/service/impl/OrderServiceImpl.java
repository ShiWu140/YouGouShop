package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.request.OrderDetailDTO;
import com.training.aigoushopapi.mapper.OrderMapper;
import com.training.aigoushopapi.mapper.OrderProductMapper;
import com.training.aigoushopapi.mapper.ProductMapper;
import com.training.aigoushopapi.service.IOrderProductService;
import com.training.aigoushopapi.service.IOrderService;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderProductMapper orderProductMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private IOrderProductService orderProductService;

    @Resource
    private IProductService productService;

    @Override
    public List<OrderDetailDTO> getOrderDetailsByUserId(String userId) {
        // 获取用户的所有订单
        List<Order> orders = orderMapper.selectList(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId));
        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for userId: " + userId);
        }

        // 获取所有订单的ID
        List<String> orderIds = orders.stream()
                .map(Order::getId)
                .collect(Collectors.toList());

        // 查询多个订单的订单商品
        List<OrderProduct> orderProducts = orderProductMapper.selectList(
                new QueryWrapper<OrderProduct>().lambda().in(OrderProduct::getOrderId, orderIds)
        );

        // 查询商品信息并封装
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for (Order order : orders) {
            List<OrderProduct> orderProductList = orderProducts.stream()
                    .filter(op -> op.getOrderId().equals(order.getId()))
                    .toList();

            // 查询商品信息并封装
            List<OrderDetailDTO.OrderProductDTO> productDTOList = new ArrayList<>();
            for (OrderProduct orderProduct : orderProductList) {
                Product product = productMapper.selectById(orderProduct.getProductId());
                OrderDetailDTO.OrderProductDTO productDTO = new OrderDetailDTO.OrderProductDTO();
                productDTO.setProductId(product.getId());
                productDTO.setProductNum(orderProduct.getProductNum());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductImage(product.getProductImage());
                productDTO.setPrice(product.getPrice());
                productDTO.setProductType(product.getProductType());
                productDTO.setProductDesc(product.getProductDesc());
                productDTO.setProductBrand(product.getProductBrand());

                productDTOList.add(productDTO);
            }

            // 封装返回结果
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setOrderId(order.getId());
            orderDetailDTO.setCreateTime(order.getCreateTime());
            orderDetailDTO.setProducts(productDTOList);
            orderDetailDTO.setState(order.getState());
            orderDetailDTO.setDeliveryStatus(order.getDeliveryStatus());

            orderDetailDTOList.add(orderDetailDTO);
        }

        return orderDetailDTOList;
    }

    @Override
    public long countOrdersByDate(LocalDateTime start, LocalDateTime end) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Order::getCreateTime, start, end);
        return this.count(wrapper);
    }

    @Override
    public BigDecimal getRevenueByDate(LocalDateTime start, LocalDateTime end) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(Order::getCreateTime, start, end)
                // 只统计已支付的订单
               .eq(Order::getState, 1);

        List<Order> orders = this.list(wrapper);
        return orders.stream()
            .map(order -> {
                List<OrderProduct> orderProducts = orderProductService.getByOrderId(order.getId());
                return orderProducts.stream()
                    .map(op -> {
                        Product product = productService.getById(op.getProductId());
                        return BigDecimal.valueOf(product.getPrice()).multiply(new BigDecimal(op.getProductNum()));
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public long countPendingOrders() {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        // 未支付的订单
        wrapper.eq(Order::getState, 0);
        return this.count(wrapper);
    }

    @Override
    public long countUrgentOrders() {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        // 未支付的订单
        wrapper.eq(Order::getState, 0)
                // 超过24小时的订单
               .lt(Order::getCreateTime, LocalDateTime.now().minusHours(24));
        return this.count(wrapper);
    }

    @Override
    public long countToBeShippedOrders() {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        // 已支付的订单
        wrapper.eq(Order::getState, 1)
                // 未发货的订单
               .eq(Order::getDeliveryStatus, 0);
        return this.count(wrapper);
    }
}

package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.request.OrderDetailDTO;
import com.training.aigoushopapi.mapper.OrderMapper;
import com.training.aigoushopapi.mapper.OrderProductMapper;
import com.training.aigoushopapi.mapper.ProductMapper;
import com.training.aigoushopapi.service.IOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
                    .collect(Collectors.toList());

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

            orderDetailDTOList.add(orderDetailDTO);
        }

        return orderDetailDTOList;
    }
}

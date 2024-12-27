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

    public OrderDetailDTO getOrderDetailsByUserId(String userId) {
        // 获取用户的订单
        List<Order> orders = orderMapper.selectList(new QueryWrapper<Order>().lambda().eq(Order::getUserId, userId));
        if (orders.isEmpty()) {
            return null;
        }

        // 假设只取第一个订单
        Order order = orders.get(0);

        // 查询订单商品
        List<OrderProduct> orderProducts = orderProductMapper.selectList(
                new QueryWrapper<OrderProduct>().eq("order_id", order.getId())
        );

        // 查询商品信息并封装
        List<OrderDetailDTO.OrderProductDTO> productDTOList = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
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

        return orderDetailDTO;
    }
}

package com.training.aigoushopapi.entity.request;
import lombok.Data;

import java.util.List;

/**
 * @author 十五
 * 提交订单的参数对象
 */
@Data
public class OrderRequest {
    private String userId;
    private String receivingAddress;
    private List<ProductRequest> products;
}

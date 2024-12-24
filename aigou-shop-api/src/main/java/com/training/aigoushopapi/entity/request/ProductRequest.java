package com.training.aigoushopapi.entity.request;
import lombok.Data;

/**
 * @author 十五
 * 提交商品的订单参数对象
 */
@Data
public class ProductRequest{
    private String productId;
    private Integer productNum;
}

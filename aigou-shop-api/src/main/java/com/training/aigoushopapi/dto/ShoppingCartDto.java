package com.training.aigoushopapi.dto;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private String userId;
    private String productId;
    private Integer quantity;
}

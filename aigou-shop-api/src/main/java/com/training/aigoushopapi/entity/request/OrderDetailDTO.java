package com.training.aigoushopapi.entity.request;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 十五
 */
@Setter
@Getter
@Data
public class OrderDetailDTO {
    private String orderId;
    private LocalDateTime createTime;
    private List<OrderProductDTO> products;

    @Setter
    @Getter
    @Data
    public static class OrderProductDTO {
        private String productId;
        private int productNum;
        private String productName;
        private String productImage;
        private double price;
        private String productType;
        private String productDesc;
        private String productBrand;

    }
}

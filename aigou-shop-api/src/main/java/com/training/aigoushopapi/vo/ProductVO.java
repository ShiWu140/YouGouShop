package com.training.aigoushopapi.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1;
    /**
     * 商品ID
     */
    private String id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品类型
     */
    private String productType;

    /**
     * 商品详情
     */
    private String productDesc;

    /**
     * 商品创建时间
     */
    private Timestamp createTime;

    /**
     * 商品品牌
     */
    private String productBrand;

    /**
     * 销售数量
     */
    private Integer salesNum;
}

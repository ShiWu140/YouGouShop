package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 商品实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class Product {
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
}

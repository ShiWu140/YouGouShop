package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Getter
@Setter
@TableName("s_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 商品图片
     */
    @TableField("product_image")
    private String productImage;

    /**
     * 价格
     */
    @TableField("price")
    private Double price;

    /**
     * 所属分类
     */
    @TableField("product_type")
    private String productType;

    /**
     * 商品详情
     */
    @TableField("product_desc")
    private String productDesc;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 商品品牌
     */
    @TableField("product_brand")
    private String productBrand;
}

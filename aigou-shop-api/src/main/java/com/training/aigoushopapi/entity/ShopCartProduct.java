package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author shiwu
 * @since 2024-12-18
 */
@Getter
@Setter
@TableName("s_shop_cart_product")
public class ShopCartProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 购物车id
     */
    @TableField("shop_cart_id")
    private String shopCartId;

    /**
     * 商品id
     */
    @TableField("product_id")
    private String productId;

    /**
     * 商品数量
     */
    @TableField("product_num")
    private Integer productNum;
}

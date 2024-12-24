package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author shiwu
 * @since 2024-12-18
 */
@Getter
@Setter
@ToString
@TableName("s_shop_cart")
public class ShopCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 购物车id
     */
    @TableField("cart_id")
    private String cartId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 前端商品信息{/shoppingCart}
     */
    @TableField(exist = false)
    private String productImage;
    @TableField(exist = false)
    private double productPrice;
    @TableField(exist = false)
    private int productNum;

}

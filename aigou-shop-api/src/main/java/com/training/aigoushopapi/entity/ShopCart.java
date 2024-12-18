package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("s_shop_cart")
public class ShopCart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
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
}

package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车产品实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class ShopCartProduct {
    /**
     * 购物车产品项ID
     */
    private String id;

    /**
     * 购物车ID
     */
    private String shopCartId;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 产品数量
     */
    private Integer productNum;
}

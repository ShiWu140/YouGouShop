package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品类型实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class ProductType {
    /**
     * 商品类型ID
     */
    private String id;

    /**
     * 商品类型名称
     */
    private String productTypeName;

    /**
     * 商品类型描述
     */
    private String productTypeDesc;

    /**
     * 商品类型图标
     */
    private String productTypeIcon;
}

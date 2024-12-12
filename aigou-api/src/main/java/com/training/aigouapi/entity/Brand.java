package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 品牌实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class Brand {
    /**
     * 品牌主键id
     */
    private String id;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 品牌类型
     */
    private String brandType;
    /**
     * 品牌图片
     */
    private String brandImg;
}

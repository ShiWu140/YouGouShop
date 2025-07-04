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
@TableName("s_product_type")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 分类名称
     */
    @TableField("product_type_name")
    private String productTypeName;

    /**
     * 分类描述
     */
    @TableField("product_type_desc")
    private String productTypeDesc;

    /**
     * 分类图标
     */
    @TableField("product_type_icon")
    private String productTypeIcon;
}

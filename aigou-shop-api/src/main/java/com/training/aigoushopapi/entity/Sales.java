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
@TableName("s_sales")
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("product_id")
    private String productId;

    @TableField("sales_num")
    private Integer salesNum;
}

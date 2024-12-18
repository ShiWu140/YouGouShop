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
@TableName("s_sales")
public class Sales implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @TableField("product_id")
    private String productId;

    @TableField("sales_num")
    private Integer salesNum;
}

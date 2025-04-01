package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author shiwu
 * @since 2024-12-18
 */
@Getter
@Setter
@TableName("s_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 收货地址Id
     */
    @TableField("receiving_address")
    private String receivingAddress;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 订单支付状态
     */
    @TableField("state")
    private Integer state;

    /**
     * 发货状态 0未发货 1已发货 2已收货 3已完成
     */
    @TableField("delivery_status")
    private Integer deliveryStatus;
}

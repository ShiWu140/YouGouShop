package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
@TableName("s_receiving_address")
public class ReceivingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 收货地址
     */
    @TableField("receiving_address")
    private String receivingAddress;

    /**
     * 收件人
     */
    @TableField("receiving_person")
    private String receivingPerson;

    /**
     * 手机号
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 账号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 默认收货地址
     */
    @TableField("is_default")
    private Integer isDefault;
}

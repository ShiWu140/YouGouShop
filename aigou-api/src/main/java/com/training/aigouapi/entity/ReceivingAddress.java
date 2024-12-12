package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收货地址实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class ReceivingAddress {
    /**
     * 收货地址ID
     */
    private String id;

    /**
     * 收货地址
     */
    private String receivingAddress;

    /**
     * 收货人
     */
    private String receivingPerson;

    /**
     * 手机号
     */
    private String mobilePhone;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否默认地址
     */
    private Integer isDefault;
}

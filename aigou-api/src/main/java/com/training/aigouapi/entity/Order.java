package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 订单实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class Order {
    /**
     * 订单ID
     */
    private String id;
    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 收货地址
     */
    private String receivingAddress;
    /**
     * 用户ID
     */
    private String userId;
}

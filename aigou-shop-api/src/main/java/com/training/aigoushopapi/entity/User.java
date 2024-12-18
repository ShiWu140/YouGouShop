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
@TableName("s_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("user_pwd")
    private String userPwd;

    /**
     * 用户类型：0、后台用户；1、前台用户
     */
    @TableField("user_type")
    private Integer userType;
}

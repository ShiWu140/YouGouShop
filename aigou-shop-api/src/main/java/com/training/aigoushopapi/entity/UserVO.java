package com.training.aigoushopapi.entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 十五
 */
@Setter
@Getter
@Data
public class UserVO {
    private String userId;
    private String username;
    //jwt令牌
    private String token;

}

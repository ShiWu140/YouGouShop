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
@TableName("s_brand")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private String id;

    /**
     * 名称
     */
    @TableField("brand_name")
    private String brandName;

    /**
     * 所属分类
     */
    @TableField("brand_type")
    private String brandType;

    /**
     * 品牌图片
     */
    @TableField("brand_img")
    private String brandImg;
}

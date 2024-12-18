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
@TableName("s_carousel_figure")
public class CarouselFigure implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 地址
     */
    @TableField("url")
    private String url;

    /**
     * 序号
     */
    @TableField("sequence_num")
    private Integer sequenceNum;
}

package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 轮播图实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class CarouselFigure {
    /**
     * 轮播图主键id
     */
    private String id;
    /**
     * 轮播图图片地址
     */
    private String url;
    /**
     * 轮播图序号
     */
    private Integer sequenceNum;
}

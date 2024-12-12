package com.training.aigouapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页数据实体类
 *
 * @param <T> 泛型类型，表示分页数据中的元素类型
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class PageEntity<T> {
    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 页行数
     */
    private Integer pageSize;

    /**
     * 总行数
     */
    private Long total;

    /**
     * 集合
     */
    private List<T> records;
}

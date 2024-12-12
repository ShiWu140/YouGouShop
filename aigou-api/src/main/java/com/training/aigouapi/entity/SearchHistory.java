package com.training.aigouapi.entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索历史实体类
 *
 * @author ethan
 */
@Data // 自动生成 getter 和 setter 方法，以及 toString、equals 和 hashCode 方法
@AllArgsConstructor // 生成全参构造函数
@NoArgsConstructor // 生成无参构造函数
public class SearchHistory {
    /**
     * 搜索历史ID
     */
    private String id;

    /**
     * 搜索词
     */
    private String searchWords;

    /**
     * 次数
     */
    private Integer num;

    /**
     * 搜索时间
     */
    private Timestamp searchTime;
}

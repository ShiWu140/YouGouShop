package com.training.aigoushopapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("s_search_history")
public class SearchHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * PK
     */
    @TableId("id")
    private String id;

    /**
     * 搜索词
     */
    @TableField("search_words")
    private String searchWords;

    /**
     * 次数
     */
    @TableField("num")
    private Integer num;

    /**
     * 搜索时间
     */
    @TableField("search_time")
    private LocalDateTime searchTime;
}

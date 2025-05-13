package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.SearchHistory;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface ISearchHistoryService extends IService<SearchHistory> {
    /**
     * 更新或添加搜索关键词
     *
     * @param keyword 搜索关键词
     * @return 操作是否成功
     */
    boolean updateOrAddKeyword(String keyword);
}

package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.SearchHistory;
import com.training.aigoushopapi.mapper.SearchHistoryMapper;
import com.training.aigoushopapi.service.ISearchHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class SearchHistoryServiceImpl extends ServiceImpl<SearchHistoryMapper, SearchHistory> implements ISearchHistoryService {

    @Override
    public boolean updateOrAddKeyword(String keyword) {
        // 查询是否存在该关键词
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("search_words", keyword);
        SearchHistory existingHistory = this.getOne(queryWrapper);

        if (existingHistory != null) {
            // 如果存在，更新搜索次数和搜索时间
            existingHistory.setNum(existingHistory.getNum() + 1);
            existingHistory.setSearchTime(LocalDateTime.now());
            return this.updateById(existingHistory);
        } else {
            // 如果不存在，创建新的搜索记录
            SearchHistory newHistory = new SearchHistory();
            newHistory.setSearchWords(keyword);
            newHistory.setNum(1);
            newHistory.setSearchTime(LocalDateTime.now());
            return this.save(newHistory);
        }
    }
}

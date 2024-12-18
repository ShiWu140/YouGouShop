package com.training.aigouapi.service.impl;


import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.SearchHistory;
import com.training.aigouapi.mapper.SearchHistoryMapper;
import com.training.aigouapi.service.SearchHistoryService;
import com.training.aigouapi.util.IDUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 搜索历史实现类
 *
 * @author 十五
 */
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    // 实例化dao层对象,方便调用dao层方法
    @Resource
    private SearchHistoryMapper searchHistoryMapper;

    /**
     * 查询分页方法
     *
     * @param current  起始页
     * @param pageSize 总数
     * @return
     */
    @Override
    public PageEntity<SearchHistory> findPage(Integer current, Integer pageSize) {
        //查询总数
        Long total = searchHistoryMapper.selectCount();
        //分页查询数据
        List<SearchHistory> searchHistories = searchHistoryMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<SearchHistory> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(total);
        pageEntity.setRecords(searchHistories);
        return pageEntity;
    }

    @Override
    public List<SearchHistory> findAll() {
        return searchHistoryMapper.selectAll();
    }

    @Override
    public SearchHistory findId(String id) {
        return searchHistoryMapper.selectId(id);
    }

    @Override
    public boolean save(SearchHistory searchHistory) {
        // 使用 ID 生成工具类生成新的 ID
        searchHistory.setId(IDUtils.get());
        return searchHistoryMapper.insert(searchHistory);
    }

    @Override
    public boolean update(SearchHistory searchHistory) {
        return searchHistoryMapper.update(searchHistory);
    }

    @Override
    public boolean remove(String id) {
        return searchHistoryMapper.delete(id);
    }
}

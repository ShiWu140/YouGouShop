package com.training.aigouapi.service.impl;


import com.training.aigouapi.dao.SearchHistoryDAO;
import com.training.aigouapi.dao.impl.SearchHistoryDAOImpl;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.SearchHistory;
import com.training.aigouapi.service.SearchHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    // 实例化dao层对象,方便调用dao层方法
    private SearchHistoryDAO searchHistoryDAO = new SearchHistoryDAOImpl();

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
        long total = searchHistoryDAO.selectCount();
        //分页查询数据
        List<SearchHistory> searchHistories = searchHistoryDAO.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<SearchHistory> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(total);
        pageEntity.setRecords(searchHistories);
        return pageEntity;
    }

    @Override
    public List<SearchHistory> findAll() {
        return searchHistoryDAO.selectAll();
    }

    @Override
    public SearchHistory findId(String id) {
        return searchHistoryDAO.selectId(id);
    }

    @Override
    public boolean save(SearchHistory searchHistory) {
        return searchHistoryDAO.insert(searchHistory);
    }

    @Override
    public boolean update(SearchHistory searchHistory) {
        return searchHistoryDAO.update(searchHistory);
    }

    @Override
    public boolean remove(String id) {
        return searchHistoryDAO.delete(id);
    }
}

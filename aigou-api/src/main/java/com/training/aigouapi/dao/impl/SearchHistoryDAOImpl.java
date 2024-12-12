package com.training.aigouapi.dao.impl;

import com.training.aigouapi.dao.SearchHistoryDAO;
import com.training.aigouapi.entity.SearchHistory;
import com.training.aigouapi.util.JDBCUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SearchHistoryDAOImpl implements SearchHistoryDAO {
    /**
     * 查询分页方法
     *
     * @param start 起始页
     * @param limit 返回行数
     * @return
     */
    @Override
    public List<SearchHistory> selectLimit(int start, int limit) {
        return JDBCUtils.executeQuery(SearchHistory.class, "select * from s_search_history limit ?, ?", start, limit);
    }

    @Override
    public long selectCount() {
        return JDBCUtils.executeCount("select count(*) from s_search_history");
    }
    /**
     * 查询总数
     *
     * @return count
     */


    /**
     * 查询所有
     *
     * @return SearchHistory集合，里面有所有SearchHistory对象
     */
    @Override
    public List<SearchHistory> selectAll() {
        return JDBCUtils.executeQuery(SearchHistory.class, "select * from s_search_history");
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    @Override
    public SearchHistory selectId(String id) {
        List<SearchHistory> searchHistories = JDBCUtils.executeQuery(SearchHistory.class, "select * from s_search_history where id = ?", id);
        if (searchHistories.isEmpty()) {
            return null;
        }
        return searchHistories.get(0);
    }

    /**
     * 插入信息
     *
     * @param searchHistory
     * @return
     */
    @Override
    public boolean insert(SearchHistory searchHistory) {
        int rows = JDBCUtils.executeUpdate("insert into s_search_history(id,search_words,num,search_time) values(?,?,?,?)",
                searchHistory.getId(),
                searchHistory.getSearchWords(),
                searchHistory.getNum(),
                // 插入当前时间，其他业务不对时间进行修改
                Timestamp.valueOf(LocalDateTime.now()));
        return rows > 0;
    }

    /**
     * 按id更新信息
     *
     * @param searchHistory
     * @return
     */
    @Override
    public boolean update(SearchHistory searchHistory) {
        int rows = JDBCUtils.executeUpdate("update s_search_history set search_words=?,num=? where id =?",
                searchHistory.getSearchWords(), searchHistory.getNum(), searchHistory.getId());
        return rows > 0;

    }

    /**
     * 按id删除信息
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        int rows = JDBCUtils.executeUpdate("delete from s_search_history where id = ?", id);
        return rows > 0;
    }
}

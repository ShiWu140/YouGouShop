package com.training.aigouapi.mapper;

import com.training.aigouapi.entity.SearchHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 搜索历史表数据操作接口
 *
 * @author ethan
 */
@Mapper
public interface SearchHistoryMapper {
    /**
     * 查询搜索历史分页信息
     *
     * @param start 起始页，必须大于等于0
     * @param limit 返回行数，必须大于0
     * @return 搜索历史列表
     */
    List<SearchHistory> selectLimit(int start, int limit);

    /**
     * 查询搜索历史总数
     *
     * @return 搜索历史总数
     */
    long selectCount();

    /**
     * 查询所有搜索历史
     *
     * @return 搜索历史列表
     */
    List<SearchHistory> selectAll();

    /**
     * 根据id查询搜索历史
     *
     * @param id 搜索历史ID
     * @return 搜索历史对象，如果不存在则返回null
     */
    SearchHistory selectId(String id);

    /**
     * 插入新的搜索历史
     *
     * @param searchHistory 搜索历史对象
     * @return 是否插入成功 true 成功 false 失败
     */
    boolean insert(SearchHistory searchHistory);

    /**
     * 更新搜索历史
     *
     * @param searchHistory 搜索历史对象
     * @return 是否更新成功 true 成功 false 失败
     */
    boolean update(SearchHistory searchHistory);

    /**
     * 删除搜索历史
     *
     * @param id 搜索历史ID
     * @return 是否删除成功 true 成功 false 失败
     */
    boolean delete(String id);
}

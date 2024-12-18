package com.training.aigouapi.service;

import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.entity.SearchHistory;

import java.util.List;

/**
 * 搜索历史记录服务接口
 *
 * @author ethan
 */
public interface SearchHistoryService {
    /**
     * 分页查询搜索历史记录信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 分页结果
     */
    PageEntity<SearchHistory> findPage(Integer current, Integer pageSize);

    /**
     * 查询所有搜索历史记录
     *
     * @return 搜索历史记录列表
     */
    List<SearchHistory> findAll();

    /**
     * 根据用户ID查询搜索历史记录
     *
     * @param id 用户ID
     * @return 搜索历史记录对象
     */
    SearchHistory findId(String id);

    /**
     * 插入新的搜索历史记录
     *
     * @param searchHistory 搜索历史记录对象
     * @return 是否插入成功，true表示成功，false表示失败
     */
    boolean save(SearchHistory searchHistory);

    /**
     * 更新搜索历史记录信息
     *
     * @param searchHistory 搜索历史记录对象
     * @return 是否更新成功，true表示成功，false表示失败
     */
    boolean update(SearchHistory searchHistory);

    /**
     * 删除搜索历史记录
     *
     * @param id 搜索历史记录ID
     * @return 是否删除成功，true表示成功，false表示失败
     */
    boolean remove(String id);
}

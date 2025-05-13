package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.SearchHistory;
import com.training.aigoushopapi.service.ISearchHistoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 搜索历史
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/searchHistory")
public class SearchHistoryController {
    @Resource
    private ISearchHistoryService searchHistoryService;

    /**
     * 分页查询 搜索历史信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 搜索历史信息数据的分页对象
     */
    @GetMapping("/page")
    public Page<SearchHistory> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<SearchHistory> page = new Page<>(current, size);
        QueryWrapper<SearchHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("search_time");
        return searchHistoryService.page(page, queryWrapper);
    }


    /**
     * 查询所有 搜索历史信息
     *
     * @return 所有 搜索历史信息的列表
     */
    @GetMapping("/all")
    public List<SearchHistory> getAll() {
        return searchHistoryService.list();
    }

    /**
     * 按ID查询 搜索历史信息
     *
     * @param id 搜索历史信息ID
     * @return 搜索历史信息对象或错误信息
     */
    @GetMapping("/{id}")
    public SearchHistory getId(@PathVariable Long id) {
        return searchHistoryService.getById(id);
    }

    /**
     * 添加 搜索历史信息
     *
     * @param searchHistory 搜索历史信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody SearchHistory searchHistory) {
        return searchHistoryService.save(searchHistory);
    }

    /**
     * 更新 搜索历史信息
     *
     * @param searchHistory 搜索历史信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody SearchHistory searchHistory) {
        return searchHistoryService.updateById(searchHistory);
    }

    /**
     * 删除 搜索历史信息
     *
     * @param searchHistory 搜索历史信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody SearchHistory searchHistory) {
        return searchHistoryService.removeById(searchHistory.getId());
    }

    /**
     * 更新或添加搜索关键词
     *
     * @param keyword 搜索关键词
     * @return 成功或失败信息
     */
    @PostMapping("/updateKeyword")
    public boolean updateKeyword(@RequestParam String keyword) {
        return searchHistoryService.updateOrAddKeyword(keyword);
    }
}

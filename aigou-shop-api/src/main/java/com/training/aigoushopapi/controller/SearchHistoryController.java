package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.SearchHistory;
import com.training.aigoushopapi.service.ISearchHistoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索历史
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/searchHistory")
public class SearchHistoryController {
    @Resource
    private ISearchHistoryService searchHistoryService;

    /**
     * 分页查询 搜索历史信息信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 搜索历史信息数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<SearchHistory> page = new Page<>(current, size);
        return Result.success(searchHistoryService.page(page));
    }

    /**
     * 查询所有 搜索历史信息
     *
     * @return 所有 搜索历史信息的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(searchHistoryService.list());
    }

    /**
     * 按ID查询 搜索历史信息信息
     *
     * @param id 搜索历史信息ID
     * @return 搜索历史信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        SearchHistory searchHistory = searchHistoryService.getById(id);
        if (searchHistory != null) {
            return Result.success(searchHistory);
        } else {
            return Result.error("没有找到ID为 " + id + " 的搜索历史信息！");
        }
    }

    /**
     * 添加 搜索历史信息
     *
     * @param searchHistory 搜索历史信息对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody SearchHistory searchHistory) {
        boolean rs = searchHistoryService.save(searchHistory);
        if (rs) {
            return Result.success(searchHistory);
        } else {
            return Result.error("添加 搜索历史信息失败！");
        }
    }

    /**
     * 更新 搜索历史信息信息
     *
     * @param searchHistory 搜索历史信息对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody SearchHistory searchHistory) {
        boolean rs = searchHistoryService.updateById(searchHistory);
        if (rs) {
            return Result.success(searchHistory);
        } else {
            return Result.error("编辑ID为 " + searchHistory.getId() + " 的搜索历史信息失败！");
        }
    }

    /**
     * 删除 搜索历史信息
     *
     * @param id 搜索历史信息ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = searchHistoryService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的搜索历史信息成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的搜索历史信息失败！");
        }
    }

}

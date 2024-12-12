package com.training.aigouapi.controller;

import com.training.aigouapi.entity.ApiResponse;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.SearchHistory;
import com.training.aigouapi.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 搜索历史控制器
 */
@RestController
@RequestMapping("/searchHistory")
@CrossOrigin
public class SearchHistoryController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * 分页查询搜索历史记录
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 包含分页搜索历史记录的响应实体
     */
    @GetMapping("/page/{current}/{pageSize}")
    public ResponseEntity<PageEntity<SearchHistory>> page(@PathVariable(value = "current") Integer current, @PathVariable(value = "pageSize") Integer pageSize) {
        PageEntity<SearchHistory> searchHistoryPageEntity = searchHistoryService.findPage(current, pageSize);
        return ResponseEntity.ok(searchHistoryPageEntity);
    }

    /**
     * 根据ID查询搜索历史记录
     *
     * @param id 搜索历史记录ID
     * @return 查询到的搜索历史记录或错误响应实体
     */
    @GetMapping("/queryId")
    public ResponseEntity<SearchHistory> findById(@RequestParam String id) {
        Optional<SearchHistory> searchHistory = Optional.ofNullable(searchHistoryService.findId(id));
        return searchHistory.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * 查询所有搜索历史记录
     *
     * @return 所有搜索历史记录列表的响应实体
     */
    @GetMapping("/queryAll")
    public ResponseEntity<List<SearchHistory>> findAll() {
        List<SearchHistory> searchHistories = searchHistoryService.findAll();
        return ResponseEntity.ok(searchHistories);
    }

    /**
     * 添加搜索历史记录
     *
     * @param searchHistory 搜索历史记录对象
     * @return 添加结果的响应实体
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> register(SearchHistory searchHistory) {
        if (searchHistoryService.findId(searchHistory.getId()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 已经存在"));
        }
        boolean rs = searchHistoryService.save(searchHistory);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "SearchHistory 添加成功", searchHistory.getId()));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 添加失败"));
        }
    }

    /**
     * 修改搜索历史记录
     *
     * @param searchHistory 搜索历史记录对象
     * @return 更新结果的响应实体
     */
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> update(SearchHistory searchHistory) {
        boolean rs = searchHistoryService.update(searchHistory);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "SearchHistory 更新成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 更新失败"));
        }
    }

    /**
     * 删除搜索历史记录
     *
     * @param id 搜索历史记录ID
     * @return 删除结果的响应实体
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        boolean rs = searchHistoryService.remove(id);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "SearchHistory 删除成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 删除失败"));
        }
    }
}

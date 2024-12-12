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

@RestController
@RequestMapping("/searchHistory")
@CrossOrigin
public class SearchHistoryController {

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * 分页查询用户信息
     *
     * @param current  当前页码
     * @param pagesize 每页显示数量
     * @return 包含分页用户信息的响应实体
     */
    @PostMapping("/page")
    public ResponseEntity<PageEntity<SearchHistory>> page(@RequestParam Integer current, @RequestParam Integer pagesize) {
        PageEntity<SearchHistory> searchHistoryPageEntity = searchHistoryService.findPage(current, pagesize);
        return ResponseEntity.ok(searchHistoryPageEntity);
    }


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

    @PostMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> update(SearchHistory searchHistory) {
        boolean rs = searchHistoryService.update(searchHistory);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "SearchHistory 更新成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 更新失败"));
        }
    }


    @PostMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        boolean rs = searchHistoryService.remove(id);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "SearchHistory 删除成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "SearchHistory 删除失败"));
        }
    }


    @PostMapping("/queryId")
    public ResponseEntity<SearchHistory> findById(@RequestParam String id) {
        Optional<SearchHistory> searchHistory = Optional.ofNullable(searchHistoryService.findId(id));
        return searchHistory.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/queryAll")
    public ResponseEntity<List<SearchHistory>> findAll() {
        List<SearchHistory> searchHistories = searchHistoryService.findAll();
        return ResponseEntity.ok(searchHistories);
    }
}

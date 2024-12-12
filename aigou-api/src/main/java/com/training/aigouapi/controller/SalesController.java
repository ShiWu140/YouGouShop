package com.training.aigouapi.controller;

import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.Sales;
import com.training.aigouapi.service.SalesService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销量
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/12 12:32
 */
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Resource
    private SalesService salesService;

    /**
     * 分页方法
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页后的销售记录
     */
    @GetMapping
    public ResponseEntity<PageEntity<Sales>> page(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(salesService.findPage(page, size));
    }

    /**
     * 查询所有销售记录
     *
     * @return 所有销售记录的列表
     */
    @GetMapping("/all")
    public ResponseEntity<List<Sales>> getAll() {
        return ResponseEntity.ok(salesService.findAll());
    }

    /**
     * 根据销售记录 id 查询销售记录
     *
     * @param id 销售记录 id
     * @return 对应的销售记录，如果不存在则返回 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sales> getId(@PathVariable String id) {
        Sales sales = salesService.findId(id);
        if (sales != null) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 搜索销售记录
     *
     * @param words 搜索关键词
     * @return 符合条件的销售记录列表
     */
//    @GetMapping("/search")
//    public ResponseEntity<List<Sales>> search(@RequestParam String words) {
//        // 假设有一个 search 方法在 SalesService 中
//        List<Sales> salesList = salesService.search(words);
//        return ResponseEntity.ok(salesList);
//    }

    /**
     * 添加销售记录
     *
     * @param sales 要添加的销售记录
     * @return 添加成功的销售记录
     */
    @PostMapping
    public ResponseEntity<Sales> add(@RequestBody Sales sales) {
        boolean success = salesService.save(sales);
        if (success) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新销售记录
     *
     * @param sales 要更新的销售记录
     * @return 更新后的销售记录
     */
    @PutMapping
    public ResponseEntity<Sales> modify(@RequestBody Sales sales) {
        boolean success = salesService.update(sales);
        if (success) {
            return ResponseEntity.ok(sales);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除销售记录
     *
     * @param id 销售记录 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean success = salesService.remove(id);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

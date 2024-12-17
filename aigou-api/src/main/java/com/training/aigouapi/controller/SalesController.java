package com.training.aigouapi.controller;

import com.training.aigouapi.common.Result;
import com.training.aigouapi.entity.Sales;
import com.training.aigouapi.service.SalesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 销量
 *
 * @author ethan
 * @version 1.0
 * @since 2024/12/12 12:32
 */
@CrossOrigin
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
    public Result page(@RequestParam Integer page, @RequestParam Integer size) {
        return Result.success(salesService.findPage(page, size));
    }

    /**
     * 查询所有销售记录
     *
     * @return 所有销售记录的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(salesService.findAll());
    }

    /**
     * 根据销售记录 id 查询销售记录
     *
     * @param id 销售记录 id
     * @return 对应的销售记录，如果不存在则返回错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable String id) {
        Sales sales = salesService.findId(id);
        if (sales != null) {
            return Result.success(sales);
        } else {
            return Result.error("没有找到{" + id + "}！");
        }
    }

    /**
     * 添加销售记录
     *
     * @param sales 要添加的销售记录
     * @return 添加成功的销售记录
     */
    @PostMapping
    public Result add(@RequestBody Sales sales) {
        boolean success = salesService.save(sales);
        if (success) {
            return Result.success(sales);
        } else {
            return Result.error("添加{" + sales.getId() + "}失败！");
        }
    }

    /**
     * 更新销售记录
     *
     * @param sales 要更新的销售记录
     * @return 更新后的销售记录
     */
    @PutMapping
    public Result modify(@RequestBody Sales sales) {
        boolean success = salesService.update(sales);
        if (success) {
            return Result.success(sales);
        } else {
            return Result.error("编辑{" + sales.getId() + "}失败！");
        }
    }

    /**
     * 删除销售记录
     *
     * @param id 销售记录 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        boolean success = salesService.remove(id);
        if (success) {
            return Result.success("删除{" + id + "}成功！");
        } else {
            return Result.error("删除{" + id + "}失败！");
        }
    }
}

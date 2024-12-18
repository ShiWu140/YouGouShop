package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.Sales;
import com.training.aigoushopapi.service.ISalesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 销售
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Resource
    private ISalesService salesService;

    /**
     * 分页查询 销售信息信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 销售信息数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Sales> page = new Page<>(current, size);
        return Result.success(salesService.page(page));
    }

    /**
     * 查询所有 销售信息
     *
     * @return 所有 销售信息的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(salesService.list());
    }

    /**
     * 按ID查询 销售信息信息
     *
     * @param id 销售信息ID
     * @return 销售信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        Sales sales = salesService.getById(id);
        if (sales != null) {
            return Result.success(sales);
        } else {
            return Result.error("没有找到ID为 " + id + " 的销售信息！");
        }
    }

    /**
     * 添加 销售信息
     *
     * @param sales 销售信息对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody Sales sales) {
        boolean rs = salesService.save(sales);
        if (rs) {
            return Result.success(sales);
        } else {
            return Result.error("添加 销售信息失败！");
        }
    }

    /**
     * 更新 销售信息信息
     *
     * @param sales 销售信息对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody Sales sales) {
        boolean rs = salesService.updateById(sales);
        if (rs) {
            return Result.success(sales);
        } else {
            return Result.error("编辑ID为 " + sales.getId() + " 的销售信息失败！");
        }
    }

    /**
     * 删除 销售信息
     *
     * @param id 销售信息ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = salesService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的销售信息成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的销售信息失败！");
        }
    }

}

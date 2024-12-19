package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Sales;
import com.training.aigoushopapi.service.ISalesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/sales")
public class SalesController {
    @Resource
    private ISalesService salesService;

    /**
     * 分页查询 销售信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 销售信息数据的分页对象
     */
    @GetMapping("/page")
    public Page<Sales> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Sales> page = new Page<>(current, size);
        return salesService.page(page);
    }

    /**
     * 查询所有 销售信息
     *
     * @return 所有 销售信息的列表
     */
    @GetMapping("/all")
    public List<Sales> getAll() {
        return salesService.list();
    }

    /**
     * 按ID查询 销售信息
     *
     * @param id 销售信息ID
     * @return 销售信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Sales getId(@PathVariable Long id) {
        return salesService.getById(id);
    }

    /**
     * 添加 销售信息
     *
     * @param sales 销售信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Sales sales) {
        return salesService.save(sales);
    }

    /**
     * 更新 销售信息
     *
     * @param sales 销售信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody Sales sales) {
        return salesService.updateById(sales);
    }

    /**
     * 删除 销售信息
     *
     * @param sales 销售信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody Sales sales) {
        return salesService.removeById(sales.getId());
    }
}

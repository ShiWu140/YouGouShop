package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.service.IOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private IOrderService orderService;

    /**
     * 分页查询订单信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含订单数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Order> page = new Page<>(current, size);
        return Result.success(orderService.page(page));
    }

    /**
     * 查询所有订单
     *
     * @return 所有订单的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(orderService.list());
    }

    /**
     * 按ID查询订单信息
     *
     * @param id 订单ID
     * @return 订单对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        Order order = orderService.getById(id);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("没有找到ID为 " + id + " 的订单！");
        }
    }

    /**
     * 添加订单
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody Order order) {
        boolean rs = orderService.save(order);
        if (rs) {
            return Result.success(order);
        } else {
            return Result.error("添加订单失败！");
        }
    }

    /**
     * 更新订单信息
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody Order order) {
        boolean rs = orderService.updateById(order);
        if (rs) {
            return Result.success(order);
        } else {
            return Result.error("编辑ID为 " + order.getId() + " 的订单失败！");
        }
    }

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = orderService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的订单成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的订单失败！");
        }
    }

}

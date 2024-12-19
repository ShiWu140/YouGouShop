package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Order;
import com.training.aigoushopapi.service.IOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/order")
@ResponseResult
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
    @GetMapping("/page")
    public Page<Order> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Order> page = new Page<>(current, size);
        return orderService.page(page);
    }

    /**
     * 查询所有订单
     *
     * @return 所有订单的列表
     */
    @GetMapping("/all")
    public List<Order> getAll() {
        return orderService.list();
    }

    /**
     * 按ID查询订单信息
     *
     * @param id 订单ID
     * @return 订单对象或错误信息
     */
    @GetMapping("/{id}")
    public Order getId(@PathVariable Long id) {
        return orderService.getById(id);
    }

    /**
     * 添加订单
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody Order order) {
        return orderService.save(order);
    }

    /**
     * 更新订单信息
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody Order order) {
        return orderService.updateById(order);
    }

    /**
     * 删除订单
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody Order order) {
        return orderService.removeById(order.getId());
    }
}

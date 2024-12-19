package com.training.aigouapi.controller;


import com.training.aigouapi.entity.Order;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 分页方法
     *
     * @param page 当前页码
     * @param size  每页大小
     * @return 包含订单数据的分页对象             
     */
    @GetMapping
    public ResponseEntity<PageEntity<Order>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<Order> orders = orderService.findPage(page, size);
        return ResponseEntity.ok(orders);
    }
    /**
     * 查询所有订单
     *
     * @return 所有订单的列表
     */
    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    /**
     * 按ID查询订单信息
     *
     * @param id 订单ID
     * @return 订单对象或错误信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Order order = orderService.findId(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 添加订单
     *
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Order order) {
        if (order == null || order.getId() == null || order.getCreateTime() == null || order.getReceivingAddress() == null || order.getUserId() == null) {
            return ResponseEntity.badRequest().body("订单信息不完整");
        }
        if (orderService.findId(order.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同订单 ID");
        }
        boolean rs = orderService.save(order);
        if (rs) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新订单信息
     *
     * @param id    订单ID
     * @param order 订单对象
     * @return 成功或失败信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Order order) {
        if (order == null || id == null || order.getCreateTime() == null || order.getReceivingAddress() == null || order.getUserId() == null) {
            return ResponseEntity.badRequest().body("订单信息不完整");
        }
        boolean rs = orderService.update(order);
        if (rs) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().body("更新订单失败");
        }
    }

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = orderService.remove(id);
        if (rs) {
            return ResponseEntity.ok("订单删除成功 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除订单失败");
        }
    }
}
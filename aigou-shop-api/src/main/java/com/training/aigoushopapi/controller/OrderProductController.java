package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.service.IOrderProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 订单商品
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/orderProduct")
public class OrderProductController {
    @Resource
    private IOrderProductService orderProductService;

    /**
     * 分页查询订单商品信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含订单商品数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<OrderProduct> page = new Page<>(current, size);
        return Result.success(orderProductService.page(page));
    }

    /**
     * 查询所有订单商品
     *
     * @return 所有订单商品的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(orderProductService.list());
    }

    /**
     * 按ID查询订单商品信息
     *
     * @param id 订单商品ID
     * @return 订单商品对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        OrderProduct orderProduct = orderProductService.getById(id);
        if (orderProduct != null) {
            return Result.success(orderProduct);
        } else {
            return Result.error("没有找到ID为 " + id + " 的订单商品！");
        }
    }

    /**
     * 添加订单商品
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody OrderProduct orderProduct) {
        boolean rs = orderProductService.save(orderProduct);
        if (rs) {
            return Result.success(orderProduct);
        } else {
            return Result.error("添加订单商品失败！");
        }
    }

    /**
     * 更新订单商品信息
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody OrderProduct orderProduct) {
        boolean rs = orderProductService.updateById(orderProduct);
        if (rs) {
            return Result.success(orderProduct);
        } else {
            return Result.error("编辑ID为 " + orderProduct.getId() + " 的订单商品失败！");
        }
    }

    /**
     * 删除订单商品
     *
     * @param id 订单商品ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = orderProductService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的订单商品成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的订单商品失败！");
        }
    }
}

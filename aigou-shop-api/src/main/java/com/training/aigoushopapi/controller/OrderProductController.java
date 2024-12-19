package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.OrderProduct;
import com.training.aigoushopapi.service.IOrderProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单商品
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/orderProduct")
@ResponseResult
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
    @GetMapping("/page")
    public Page<OrderProduct> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<OrderProduct> page = new Page<>(current, size);
        return orderProductService.page(page);
    }

    /**
     * 查询所有订单商品
     *
     * @return 所有订单商品的列表
     */
    @GetMapping("/all")
    public List<OrderProduct> getAll() {
        return orderProductService.list();
    }

    /**
     * 按ID查询订单商品信息
     *
     * @param id 订单商品ID
     * @return 订单商品对象或错误信息
     */
    @GetMapping("/{id}")
    public OrderProduct getId(@PathVariable Long id) {
        return orderProductService.getById(id);
    }

    /**
     * 添加订单商品
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody OrderProduct orderProduct) {
        return orderProductService.save(orderProduct);
    }

    /**
     * 更新订单商品信息
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody OrderProduct orderProduct) {
        return orderProductService.updateById(orderProduct);
    }

    /**
     * 删除订单商品
     *
     * @param orderProduct 订单商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody OrderProduct orderProduct) {
        return orderProductService.removeById(orderProduct.getId());
    }
}

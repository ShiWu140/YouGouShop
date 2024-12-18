package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.ShopCart;
import com.training.aigoushopapi.service.IShopCartService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {
    @Resource
    private IShopCartService shopCartService;

    /**
     * 分页查询 购物车信息信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 购物车信息数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ShopCart> page = new Page<>(current, size);
        return Result.success(shopCartService.page(page));
    }

    /**
     * 查询所有 购物车信息
     *
     * @return 所有 购物车信息的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(shopCartService.list());
    }

    /**
     * 按ID查询 购物车信息信息
     *
     * @param id 购物车信息ID
     * @return 购物车信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        ShopCart shopCart = shopCartService.getById(id);
        if (shopCart != null) {
            return Result.success(shopCart);
        } else {
            return Result.error("没有找到ID为 " + id + " 的购物车信息！");
        }
    }

    /**
     * 添加 购物车信息
     *
     * @param shopCart 购物车信息对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody ShopCart shopCart) {
        boolean rs = shopCartService.save(shopCart);
        if (rs) {
            return Result.success(shopCart);
        } else {
            return Result.error("添加 购物车信息失败！");
        }
    }

    /**
     * 更新 购物车信息信息
     *
     * @param shopCart 购物车信息对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody ShopCart shopCart) {
        boolean rs = shopCartService.updateById(shopCart);
        if (rs) {
            return Result.success(shopCart);
        } else {
            return Result.error("编辑ID为 " + shopCart.getId() + " 的购物车信息失败！");
        }
    }

    /**
     * 删除 购物车信息
     *
     * @param id 购物车信息ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = shopCartService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的购物车信息成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的购物车信息失败！");
        }
    }
}

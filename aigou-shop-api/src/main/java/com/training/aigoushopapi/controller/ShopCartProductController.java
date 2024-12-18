package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.ShopCartProduct;
import com.training.aigoushopapi.service.IShopCartProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车商品
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/shopCartProduct")
public class ShopCartProductController {
    @Resource
    private IShopCartProductService shopCartProductService;

    /**
     * 分页查询 购物车商品信息信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 购物车商品信息数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ShopCartProduct> page = new Page<>(current, size);
        return Result.success(shopCartProductService.page(page));
    }

    /**
     * 查询所有 购物车商品信息
     *
     * @return 所有 购物车商品信息的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(shopCartProductService.list());
    }

    /**
     * 按ID查询 购物车商品信息信息
     *
     * @param id 购物车商品信息ID
     * @return 购物车商品信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        ShopCartProduct shopCartProduct = shopCartProductService.getById(id);
        if (shopCartProduct != null) {
            return Result.success(shopCartProduct);
        } else {
            return Result.error("没有找到ID为 " + id + " 的购物车商品信息！");
        }
    }

    /**
     * 添加 购物车商品信息
     *
     * @param shopCartProduct 购物车商品信息对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody ShopCartProduct shopCartProduct) {
        boolean rs = shopCartProductService.save(shopCartProduct);
        if (rs) {
            return Result.success(shopCartProduct);
        } else {
            return Result.error("添加 购物车商品信息失败！");
        }
    }

    /**
     * 更新 购物车商品信息信息
     *
     * @param shopCartProduct 购物车商品信息对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody ShopCartProduct shopCartProduct) {
        boolean rs = shopCartProductService.updateById(shopCartProduct);
        if (rs) {
            return Result.success(shopCartProduct);
        } else {
            return Result.error("编辑ID为 " + shopCartProduct.getId() + " 的购物车商品信息失败！");
        }
    }

    /**
     * 删除 购物车商品信息
     *
     * @param id 购物车商品信息ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = shopCartProductService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的购物车商品信息成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的购物车商品信息失败！");
        }
    }
}

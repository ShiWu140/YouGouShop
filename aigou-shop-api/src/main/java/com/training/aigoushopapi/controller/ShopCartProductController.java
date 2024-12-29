package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.ShopCartProduct;
import com.training.aigoushopapi.service.IShopCartProductService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车商品
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/shopCartProduct")
public class ShopCartProductController {
    @Resource
    private IShopCartProductService shopCartProductService;

    /**
     * 分页查询 购物车商品信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 购物车商品信息数据的分页对象
     */
    @GetMapping("/page")
    public Page<ShopCartProduct> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ShopCartProduct> page = new Page<>(current, size);
        return shopCartProductService.page(page);
    }

    /**
     * 查询所有 购物车商品信息
     *
     * @return 所有 购物车商品信息的列表
     */
    @GetMapping("/all")
    public List<ShopCartProduct> getAll() {
        return shopCartProductService.list();
    }

    /**
     * 按ID查询 购物车商品信息
     *
     * @param id 购物车商品信息ID
     * @return 购物车商品信息对象或错误信息
     */
    @GetMapping("/{id}")
    public ShopCartProduct getId(@PathVariable Long id) {
        return shopCartProductService.getById(id);
    }

    /**
     * 添加 购物车商品信息
     *
     * @param shopCartProduct 购物车商品信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody ShopCartProduct shopCartProduct) {
        return shopCartProductService.save(shopCartProduct);
    }

    /**
     * 更新 购物车商品信息
     *
     * @param shopCartProduct 购物车商品信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody ShopCartProduct shopCartProduct) {
        return shopCartProductService.updateById(shopCartProduct);
    }

    /**
     * 删除 购物车商品信息
     *
     * @param shopCartProduct 购物车商品信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody ShopCartProduct shopCartProduct) {
        return shopCartProductService.removeById(shopCartProduct.getId());
    }

    /**
     * 跟新商品数量
     *
     * @param cartId     购物车id
     * @param productId  商品id
     * @param productNum 商品数量
     * @return true false
     */
    @GetMapping("/updateProductNum")
    public boolean updateProductNum(@RequestParam String cartId, @RequestParam String productId, @RequestParam Integer productNum) {
        return shopCartProductService.update(new UpdateWrapper<ShopCartProduct>().lambda()
                .eq(ShopCartProduct::getShopCartId, cartId)
                .eq(ShopCartProduct::getProductId, productId)
                .set(ShopCartProduct::getProductNum, productNum));
    }
}

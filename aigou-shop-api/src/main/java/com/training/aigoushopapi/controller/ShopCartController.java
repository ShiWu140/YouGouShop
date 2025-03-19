package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.dto.ShoppingCartDto;
import com.training.aigoushopapi.entity.ShopCart;
import com.training.aigoushopapi.service.IShopCartProductService;
import com.training.aigoushopapi.service.IShopCartService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/shopCart")
public class ShopCartController {
    @Resource
    private IShopCartService shopCartService;
    @Resource
    private IShopCartProductService shopCartProductService;

    /**
     * 分页查询 购物车信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 购物车信息数据的分页对象
     */
    @GetMapping("/page")
    public Page<ShopCart> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ShopCart> page = new Page<>(current, size);
        return shopCartService.page(page);
    }

    /**
     * 查询所有 购物车信息
     *
     * @return 所有 购物车信息的列表
     */
    @GetMapping("/all")
    public List<ShopCart> getAll() {
        return shopCartService.list();
    }

    /**
     * 按ID查询 购物车信息
     *
     * @param id 购物车信息ID
     * @return 购物车信息对象或错误信息
     */
    @GetMapping("/{id}")
    public ShopCart getId(@PathVariable Long id) {
        return shopCartService.getById(id);
    }

    /**
     * 添加 购物车信息
     *
     * @param shopCart 购物车信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody ShopCart shopCart) {
        return shopCartService.save(shopCart);
    }

    /**
     * 更新 购物车信息
     *
     * @param shopCart 购物车信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody ShopCart shopCart) {
        return shopCartService.updateById(shopCart);
    }

    /**
     * 删除 购物车信息
     *
     * @param shopCart 购物车信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody ShopCart shopCart) {
        return shopCartService.removeById(shopCart.getId());
    }

    /**
     * 按用户ID查询购物车信息
     *
     * @param userId 用户ID
     * @return 购物车信息对象或错误信息
     */
    @GetMapping("/getProductsByUserId")
    public List<ShopCart> getProductsByUserId(@RequestParam Long userId) {
        return shopCartService.getProductsByUserId(userId);
    }

    /**
     * 根据购物车id从购物车中删除指定商品
     *
     * @param cartId    购物车id
     * @param productId 商品id
     * @return true false
     */
    @DeleteMapping("/deleteProductFromCart")
    public boolean deleteProductFromCart(@RequestParam String cartId, @RequestParam String productId) {
        return shopCartService.deleteProductFromCart(cartId, productId);
    }
    /**
     * 根据商品id添加到用户购物车中
     */
    @PostMapping("/addProductFromCart")
    public boolean addToCart(@RequestBody ShoppingCartDto cartDto) {
        return shopCartService.addProductToCart(cartDto.getUserId(), cartDto.getProductId(), cartDto.getQuantity());
    }

}

package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.entity.ShopCart;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IShopCartService extends IService<ShopCart> {
    /**
     * 根据用户ID查询对应购物车列表信息{/shoppingCart}
     * 1.根据用户id查询购物车id（user_id）
     * 2.根据购物车id查询购物车商品id信息 (cart_id === shop_cart_id)
     * 3.根据商品id查询商品信息 (product_id ===  id )
     */
    List<ShopCart> getProductsByUserId(Long userId);

}

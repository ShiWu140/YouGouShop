package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.ShopCart;
import com.training.aigoushopapi.mapper.ShopCartMapper;
import com.training.aigoushopapi.service.IShopCartService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements IShopCartService {

    @Resource
    ShopCartMapper shopCartMapper;

    /**
     * 根据用户ID查询对应购物车列表信息{/shoppingCart}
     * 1.根据用户id查询购物车id（user_id）
     * 2.根据购物车id查询购物车商品id信息 (cart_id === shop_cart_id)
     * 3.根据商品id查询商品信息 (product_id ===  id )
     *
     * @param userId 用户id
     */
    @Override
    public List<ShopCart> getProductsByUserId(Long userId) {
        return shopCartMapper.getProductsByUserId(userId);
    }

    /**
     * 根据购物车id删除购物车商品信息{/shoppingCart/delete}
     *
     * @param cartId    购物车ID
     * @param productId 商品ID
     */
    @Override
    public boolean deleteProductFromCart(String cartId, String productId) {
        return shopCartMapper.deleteProductFromCart(cartId, productId);
    }
}

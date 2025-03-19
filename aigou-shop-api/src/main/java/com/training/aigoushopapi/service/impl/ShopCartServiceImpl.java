package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.entity.ShopCart;
import com.training.aigoushopapi.entity.ShopCartProduct;
import com.training.aigoushopapi.mapper.ShopCartMapper;
import com.training.aigoushopapi.mapper.ShopCartProductMapper;
import com.training.aigoushopapi.service.IShopCartService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    @Autowired
    private ShopCartProductMapper shopCartProductMapper;

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

    @Override
    public boolean addProductToCart(String userId, String productId, Integer quantity) {
        // 1. 查询用户是否已有购物车
        ShopCart cart = shopCartMapper.selectOne(new QueryWrapper<ShopCart>().eq("user_id", userId));
        String cartId;

        if (cart == null) {
            // 2. 没有购物车则创建购物车
            cartId = UUID.randomUUID().toString();
            cart = new ShopCart();
            cart.setId(UUID.randomUUID().toString());
            cart.setCartId(cartId);
            cart.setUserId(userId);
            shopCartMapper.insert(cart);
        } else {
            cartId = cart.getCartId();
        }

        // 3. 查询购物车中是否已有该商品
        ShopCartProduct existingProduct = shopCartProductMapper.findByCartIdAndProductId(cartId, productId);
        if (existingProduct != null) {
            // 4. 有则更新数量
            existingProduct.setProductNum(existingProduct.getProductNum() + quantity);
            return shopCartProductMapper.updateProductNum(existingProduct) > 0;
        } else {
            // 5. 无则插入新记录
            ShopCartProduct newProduct = new ShopCartProduct();
            newProduct.setId(UUID.randomUUID().toString());
            newProduct.setShopCartId(cartId);
            newProduct.setProductId(productId);
            newProduct.setProductNum(quantity);
            return shopCartProductMapper.insert(newProduct) > 0;
        }
    }

}

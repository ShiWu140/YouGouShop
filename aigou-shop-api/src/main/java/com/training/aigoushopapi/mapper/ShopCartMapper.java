package com.training.aigoushopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.training.aigoushopapi.entity.ShopCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Mapper
public interface ShopCartMapper extends BaseMapper<ShopCart> {
    /**
     * 根据用户id获取购物车信息
     *
     * @param userId 用户id
     * @return 购物车信息
     */
    List<ShopCart> getProductsByUserId(Long userId);
    /**
     * 从购物车中删除商品
     *
     * @param cartId    购物车id
     * @param productId 商品id
     * @return true false
     */
    boolean deleteProductFromCart(String cartId, String productId);
}

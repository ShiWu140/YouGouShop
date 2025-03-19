package com.training.aigoushopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.training.aigoushopapi.entity.ShopCartProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface ShopCartProductMapper extends BaseMapper<ShopCartProduct> {

    ShopCartProduct findByCartIdAndProductId(@Param("cartId") String cartId, @Param("productId") String productId);
    int updateProductNum(ShopCartProduct existingProduct);
}

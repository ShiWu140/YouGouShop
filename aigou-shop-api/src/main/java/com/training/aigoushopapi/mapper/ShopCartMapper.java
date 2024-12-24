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
    List<ShopCart> getProductsByUserId(Long userId);

}

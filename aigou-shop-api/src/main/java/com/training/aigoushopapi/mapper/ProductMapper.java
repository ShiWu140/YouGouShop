package com.training.aigoushopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.training.aigoushopapi.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}

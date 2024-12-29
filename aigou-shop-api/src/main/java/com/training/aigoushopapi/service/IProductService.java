package com.training.aigoushopapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.training.aigoushopapi.dto.ProductDto;
import com.training.aigoushopapi.entity.Product;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
public interface IProductService extends IService<Product> {
    Page pageH(ProductDto productDto);
}

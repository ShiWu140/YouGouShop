package com.training.aigouapi.service;

import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.entity.ProductType;

import java.util.List;

/**
 * 商品类型表业务逻辑接口
 *
 * @author ethan
 */
public interface ProductTypeService {

    PageBean page(Integer page, Integer size, String words);

    List<ProductType> getAll();

    ProductType getId(String id);

    void update(ProductType productType);

    void delete(List<String> ids);

    void save(ProductType productType);
}

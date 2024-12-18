package com.training.aigouapi.service;

import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.entity.Product;

import java.util.List;

/**
 * 商品表业务逻辑接口
 *
 * @author ethan
 */
public interface ProductService {

    PageBean page(Integer page, Integer size ,String words);

    Product getId(String id);

    List<Product> getAll();

    void update(Product product);

    void delete(List<String> ids);

    void save(Product product);
}

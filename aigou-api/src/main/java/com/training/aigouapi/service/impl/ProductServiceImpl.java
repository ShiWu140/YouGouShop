package com.training.aigouapi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.entity.Product;
import com.training.aigouapi.mapper.ProductMapper;
import com.training.aigouapi.service.ProductService;
import com.training.aigouapi.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageBean page(Integer page, Integer size, String words) {
        PageHelper.startPage(page, size);
        Page<Product> list = (Page<Product>) productMapper.list(words);
        PageBean p = new PageBean(list.getTotal(), list.getResult());
        return p;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Product getId(String id) {
        return productMapper.getId(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<Product> getAll() {
        return productMapper.list(null);
    }

    /**
     * 修改
     *
     * @param product
     */
    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    /**
     * 删除
     *
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        productMapper.delete(ids);
    }

    /**
     * 添加
     *
     * @param product
     */
    @Override
    public void save(Product product) {
        product.setId(IDUtils.get());
        product.setCreateTime(LocalDateTime.now());
        productMapper.save(product);
    }
}

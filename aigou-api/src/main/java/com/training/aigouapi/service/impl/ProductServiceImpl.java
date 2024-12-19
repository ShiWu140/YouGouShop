package com.training.aigouapi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.Product;
import com.training.aigouapi.entity.ProductType;
import com.training.aigouapi.mapper.BrandMapper;
import com.training.aigouapi.mapper.ProductMapper;
import com.training.aigouapi.mapper.ProductTypeMapper;
import com.training.aigouapi.service.ProductService;
import com.training.aigouapi.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private BrandMapper brandMapper;

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
        List<Product> collect = list.getResult().stream().map(item -> {
            ProductType productType = productTypeMapper.getId(item.getProductType());
            Brand brand = brandMapper.selectId(item.getProductBrand());
            if (productType != null) {
                item.setProductType(productType.getProductTypeName());
            }
            if (brand != null) {
                item.setProductBrand(brand.getBrandName());
            }
            return item;
        }).collect(Collectors.toList());

        PageBean p = new PageBean(list.getTotal(), collect);
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
        return productMapper.selectAll();
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

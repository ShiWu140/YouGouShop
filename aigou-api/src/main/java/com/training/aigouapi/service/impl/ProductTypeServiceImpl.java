package com.training.aigouapi.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.entity.ProductType;
import com.training.aigouapi.mapper.ProductTypeMapper;
import com.training.aigouapi.service.ProductTypeService;
import com.training.aigouapi.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;

    /**
     * 分页
     * @param page
     * @param size
     * @param words
     * @return
     */
    @Override
    public PageBean page(Integer page, Integer size, String words) {
        PageHelper.startPage(page,size);
        Page<ProductType> list=(Page<ProductType>) productTypeMapper.page(words);
        PageBean pageBean=new PageBean(list.getTotal(),list.getResult());
        return pageBean;
    }

    /**
     * 查全部
     * @return
     */
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public ProductType getId(String id) {
        return productTypeMapper.getId(id);
    }

    /**
     * 修改
     * @param productType
     */
    @Override
    public void update(ProductType productType) {
        productTypeMapper.update(productType);
    }

    /**
     * 删除
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        productTypeMapper.delete(ids);
    }

    /**
     * 添加
     * @param productType
     */
    @Override
    public void save(ProductType productType) {
        productType.setId(IDUtils.get());
        productTypeMapper.save(productType);
    }
}

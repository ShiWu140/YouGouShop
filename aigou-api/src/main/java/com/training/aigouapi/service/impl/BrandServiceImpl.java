package com.training.aigouapi.service.impl;

import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.mapper.BrandMapper;
import com.training.aigouapi.service.BrandService;
import com.training.aigouapi.util.IDUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ethan
 * @version 1.0
 * @since 2024/12/11 21:09
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public PageEntity<Brand> findPage(Integer current, Integer pageSize) {
        //分页查询品牌数据
        List<Brand> brands = brandMapper.selectLimit((current - 1) * pageSize, pageSize);
        PageEntity<Brand> pageEntity = new PageEntity<>();
        pageEntity.setCurrent(current);
        pageEntity.setPageSize(pageSize);
        pageEntity.setTotal(brandMapper.selectCount());
        pageEntity.setRecords(brands);
        return pageEntity;
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findId(String id) {
        return brandMapper.selectId(id);
    }

    @Override
    public boolean save(Brand brand) {
        brand.setId(IDUtils.get());
        return brandMapper.insert(brand);
    }

    @Override
    public boolean remove(String id) {
        return brandMapper.delete(id);
    }

    @Override
    public boolean update(Brand brand) {
        return brandMapper.update(brand);
    }
}

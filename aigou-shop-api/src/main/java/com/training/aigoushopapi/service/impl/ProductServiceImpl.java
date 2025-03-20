package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.dto.ProductDto;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.Sales;
import com.training.aigoushopapi.mapper.ProductMapper;
import com.training.aigoushopapi.service.IProductService;
import com.training.aigoushopapi.service.ISalesService;
import com.training.aigoushopapi.vo.ProductVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shiwu
 * @since 2024-12-18
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ISalesService salesService;

    @Autowired
    private ProductMapper productDao;

    @Override
    public List<Product> getSameTypeProducts(String productId) {
        // 首先根据商品ID获取商品对象
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return Collections.emptyList();
        }
        System.out.println(product);
        // 获取商品的分类ID
        String productTypeId = product.getProductType();
        System.out.println(productTypeId);
        // 根据分类ID查询同一个分类下的所有商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        // 随机查询
        queryWrapper.orderByAsc("RAND()");
        queryWrapper.eq("product_type", productTypeId);
        List<Product> sameTypeProducts = productMapper.selectList(queryWrapper);
        System.out.println(sameTypeProducts);
        // 查询每个商品的销量并设置到商品对象中
        for (Product p : sameTypeProducts) {
            LambdaQueryWrapper<Sales> salesQueryWrapper = new LambdaQueryWrapper<>();
            salesQueryWrapper.eq(Sales::getProductId, p.getId());
            Sales sales = salesService.getOne(salesQueryWrapper);
            if (sales != null) {
                p.setSalasNum(sales.getSalesNum());
            }
        }
        System.out.println(sameTypeProducts);
        return sameTypeProducts;
    }


    @Override
    public Product getDetailByProductId(String id) {
        // 根据商品ID获取商品对象
        Product product = productMapper.selectById(id);
        if (product == null) {
            return null;
        }

        // 根据商品ID查询销量
        LambdaQueryWrapper<Sales> salesQueryWrapper = new LambdaQueryWrapper<>();
        salesQueryWrapper.eq(Sales::getProductId, id);
        Sales sales = salesService.getOne(salesQueryWrapper);

        // 设置销量到商品对象中
        if (sales != null) {
            product.setSalasNum(sales.getSalesNum());
        } else {
            // 如果没有对应的销量记录，设置为0
            product.setSalasNum(0);
        }
        return product;
    }

    @Override
    public Page pageH(ProductDto productDto) {
        Page<ProductVO> page = new Page<>();

        Integer p = productDto.getPage();
        Integer size = productDto.getSize();
        productDto.setPage((p - 1) * size);

        String[] brands = productDto.getBrands();

        if (brands.length == 0) {
            brands = null;
            productDto.setBrands(brands);
        }

        List<Product> list = productDao.pageH(productDto);
        List<ProductVO> collect = list.stream().map(item -> {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(item, productVO);

            LambdaQueryWrapper<Sales> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Sales::getProductId, item.getId());
            Sales one = salesService.getOne(lqw);

            if (one != null) {
                //拿到商品数量
                Integer salesNum = one.getSalesNum();
                productVO.setSalesNum(salesNum);
            } else {
                //没有对应的数量,设置为0
                productVO.setSalesNum(0);
            }
            return productVO;
        }).collect(Collectors.toList());

        page.setRecords(collect);
        page.setTotal(collect.size());
        return page;
    }

}

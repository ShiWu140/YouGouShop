package com.training.aigoushopapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.training.aigoushopapi.dto.ProductDto;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.Sales;
import com.training.aigoushopapi.mapper.ProductMapper;
import com.training.aigoushopapi.service.IProductService;
import com.training.aigoushopapi.service.ISalesService;
import com.training.aigoushopapi.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private ISalesService salesService;

    @Autowired
    private ProductMapper productDao;

    @Override
    public Page pageH(ProductDto productDto) {
        Page<ProductVO> page = new Page<>();

        Integer p = productDto.getPage();
        Integer size = productDto.getSize();
        productDto.setPage((p - 1) * size);

        String[] brands = productDto.getBrands();

        if(brands.length==0){
            brands=null;
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

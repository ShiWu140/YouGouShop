package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.service.IProductService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品表
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Resource
    private IProductService productService;

    /**
     * 分页查询商品信息
     * @param page
     * @param size
     * @param name
     * @param brands
     * @param minP
     * @param maxP
     * @return
     */
    @GetMapping("/page")
    public Page<Product> page(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 String name,
                                 String[] brands,
                                 Double minP,
                                 Double maxP){
        log.info("页码{},每页记录数{},搜索{},品牌{},最低价格{},最高价格{}",page,size,name,brands,minP,maxP);
        Page<Product> pageInfo=new Page<>(page,size);
        LambdaQueryWrapper<Product> lqw=new LambdaQueryWrapper<>();
        lqw.le(maxP!=null,Product::getPrice,maxP);
        lqw.ge(minP!=null,Product::getPrice,minP);
        lqw.like(name!=null,Product::getProductName,name);
        lqw.in(brands!=null,Product::getProductBrand,brands);
        lqw.orderByAsc(Product::getPrice);
        productService.page(pageInfo,lqw);
        long count = productService.count(lqw);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    /**
     * 查询所有商品
     *
     * @return 所有商品的列表
     */
    @GetMapping
    public List<Product> getAll() {
        return productService.list();
    }

    /**
     * 按ID查询商品信息
     *
     * @param id 商品ID
     * @return 商品对象或错误信息
     */
    @GetMapping("/{id}")
    public Product getId(@PathVariable String id) {
        return productService.getById(id);
    }

    /**
     * 添加商品
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping
    public boolean add(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 更新商品信息
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PutMapping
    public boolean modify(@RequestBody Product product) {
        return productService.updateById(product);
    }

    /**
     * 删除商品
     *
     * @param id 商品对象
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        return productService.removeById(id);
    }
}

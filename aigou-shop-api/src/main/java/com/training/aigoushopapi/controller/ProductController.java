package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.dto.ProductDto;
import com.training.aigoushopapi.entity.Product;
import com.training.aigoushopapi.entity.ProductType;
import com.training.aigoushopapi.entity.Sales;
import com.training.aigoushopapi.entity.request.CategoryProductRequest;
import com.training.aigoushopapi.service.IProductService;
import com.training.aigoushopapi.service.IProductTypeService;
import com.training.aigoushopapi.service.ISalesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @Resource
    private ISalesService salesService;
    @Resource
    private IProductTypeService productTypeService;


    /**
     * 分页
     *
     * @param productDto productDto
     * @return page
     */
    @PostMapping("/pageH")
    public Page pageH(@RequestBody ProductDto productDto) {
        Page page = productService.pageH(productDto);
        return page;
    }

    @GetMapping("/page")
    public Page<Product> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<Product> page = new Page<>(current, size);
        return productService.page(page);
    }

    /**
     * 查询所有商品
     *
     * @return 所有商品的列表
     */
    @GetMapping("/all")
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
    @PostMapping("/add")
    public boolean add(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 更新商品信息
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody Product product) {
        return productService.updateById(product);
    }

    /**
     * 删除商品
     *
     * @param product 商品对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody Product product) {
        return productService.removeById(product.getId());
    }

    /**
     * 销量榜
     *
     * @return 返回最畅销的产品列表
     */
    @GetMapping("/getProductSalesList")
    public List<Product> getSalesList() {
        List<Product> productList = new ArrayList<>();
        Page<Sales> page = new Page<>(1, 6);
        QueryWrapper<Sales> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sales_num");
        List<Sales> salesList = salesService.page(page, queryWrapper).getRecords();
        salesList.forEach(sales -> productList.add(productService.getById(sales.getProductId())));
        return productList;
    }

    /**
     * 所有分类及产品列表
     *
     * @return 匹配给定分类ID的产品列表
     */
    @GetMapping("/allCategoryProduct")
    public List<CategoryProductRequest> getClassify() {
        List<CategoryProductRequest> allCategoryProduct = new ArrayList<>();
        List<ProductType> productTypeList = productTypeService.list();
        for (ProductType productType : productTypeList) {
            String id = productType.getId();
            String productTypeName = productType.getProductTypeName();
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_type", id);
            queryWrapper.orderByAsc("RAND()");
            queryWrapper.last("LIMIT 10");
            List<Product> productList = productService.list(queryWrapper);
            if (productList.isEmpty()) {
                continue;
            }
            allCategoryProduct.add(new CategoryProductRequest(id, productTypeName, productList));
        }
        return allCategoryProduct;
    }

    /**
     * 新品数据列表
     *
     * @return 首页需要展示的新品
     */
    @GetMapping("/newProduct")
    public List<Product> getNewProduct() {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("LIMIT 6");
        return productService.list(queryWrapper);

    }
}

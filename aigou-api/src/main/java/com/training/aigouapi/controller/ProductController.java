package com.training.aigouapi.controller;

import com.aliyun.core.annotation.Path;
import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.common.Result;
import com.training.aigouapi.entity.Product;
import com.training.aigouapi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1")Integer page,
                       @RequestParam(defaultValue = "1")Integer size ,
                       String words){
        log.info("页码:{},每页记录数:{},words:{}",page,size,words);
        PageBean pageBean=productService.page(page,size,words);
        return Result.success(pageBean);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable String id){
        log.info("要查询的id:{}",id);
        Product product=productService.getId(id);
        return Result.success(product);
    }

    /**
     * 查询全部
     * @return
     */
    @GetMapping("/all")
    public Result getAll(){
        List<Product> list=productService.getAll();
        return Result.success(list);
    }

    /**
     * 搜索
     * @param words
     * @return
     */
    @GetMapping("/search")
    public Result search(String words){
        log.info("搜索条件:{}",words);
        PageBean page = productService.page(1, 100, words);
        return Result.success(page);
    }

    /**
     * 修改
     * @param product
     * @return
     */
    @PutMapping
    public Result modify(@RequestBody Product product){
        log.info("修改为:{}",product);
        productService.update(product);
        return Result.success();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") List<String> ids){
        log.info("要删除的ids:{}",ids);
        productService.delete(ids);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Product product){
        log.info("新增产品:{}",product);
        productService.save(product);
        return Result.success();
    }
}

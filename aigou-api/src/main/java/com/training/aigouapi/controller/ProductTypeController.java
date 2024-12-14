package com.training.aigouapi.controller;

import com.training.aigouapi.common.PageBean;
import com.training.aigouapi.common.Result;
import com.training.aigouapi.entity.ProductType;
import com.training.aigouapi.service.ProductService;
import com.training.aigouapi.service.ProductTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    /**
     * 分页
     * @param page
     * @param size
     * @param words
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer size,
                       String words){
        log.info("商品类型分页信息:{},{},{}",page,size,words);
        PageBean pageBean=productTypeService.page(page,size,words);
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
        ProductType productType=productTypeService.getId(id);
        return Result.success(productType);
    }

    /**
     * 查询全部
     * @return
     */
    @GetMapping("/all")
    public Result getAll(){
        List<ProductType> list=productTypeService.getAll();
        return Result.success(list);
    }

    /**
     * 修改
     * @param productType
     * @return
     */
    @PutMapping
    public Result modify(@RequestBody ProductType productType){
        log.info("修改的值:{}",productType);
        productTypeService.update(productType);
        return Result.success();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") List<String> ids){
        log.info("删除的值:{}",ids);
        productTypeService.delete(ids);
        return Result.success();
    }

    /**
     * 添加
     * @param productType
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ProductType productType){
        log.info("要添加的商品类别:{}",productType);
        productTypeService.save(productType);
        return Result.success();
    }

    /**
     * 搜索
     * @param words
     * @return
     */
    @GetMapping("/search")
    public Result search(String words){
        PageBean page = productTypeService.page(1, 100, words);
        return Result.success(page);
    }
}

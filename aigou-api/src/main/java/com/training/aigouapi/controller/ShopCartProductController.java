package com.training.aigouapi.controller;

import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.ShopCart;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.ShopCartProduct;
import com.training.aigouapi.service.ShopCartProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;

/**
 * 购物车商品控制器，用于处理与购物车商品相关的HTTP请求
 */
@CrossOrigin    // 允许跨域请求
@RestController // 表示这是一个控制器类，用于处理HTTP请求, 并将返回的数据转换为JSON格式
@RequestMapping("/shopCartProduct")// 表示该控制器处理的所有请求的URL路径都会以"/shopCart"开头
public class ShopCartProductController {

    // 注入收货地址服务
    @Resource
    private ShopCartProductService shopCartProductService;

    /**
     * 分页查询购物车商品
     */
    @GetMapping("/{current}/{pageSize}")
    public ResponseEntity<PageEntity<ShopCartProduct>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<ShopCartProduct> pageEntity = shopCartProductService.findPage(page, size);
        return ResponseEntity.ok(pageEntity);
    }

    /**
     * 查询所有购物车商品
     *
     * @return ResponseEntity<List<ShopCart>> 返回所有收货地址的列表
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ShopCartProduct>> findAll() {
        List<ShopCartProduct> shopCartProducts = shopCartProductService.findAll();
        return ResponseEntity.ok(shopCartProducts);
    }

    /**
     * 根据购物车商品id查询
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ShopCartProduct shopCartProduct = shopCartProductService.findId(id);
        if (shopCartProduct != null) {
            return ResponseEntity.ok(shopCartProduct);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 添加新收购物车商品
     */
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ShopCartProduct shopCartProduct) {
        if (shopCartProduct == null || shopCartProduct.getId() == null || shopCartProduct.getShopCartId() == null || shopCartProduct.getProductId() == null){
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        if (shopCartProductService.findId(shopCartProduct.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同品牌 ID");
        }
        boolean rs = shopCartProductService.save(shopCartProduct);
        if (rs) {
            return ResponseEntity.ok(shopCartProduct);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新购物车商品
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ShopCartProduct shopCartProduct) {
        if (shopCartProduct == null || id== null || shopCartProduct.getShopCartId() == null || shopCartProduct.getProductId() == null) {
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        boolean rs = shopCartProductService.update(shopCartProduct);
        if (rs) {
            return ResponseEntity.ok(shopCartProduct);
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
    /**
     * 删除购物车商品
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = shopCartProductService.remove(id);
        if (rs) {
            return ResponseEntity.ok("已删除 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除品牌失败");
        }
    }
}

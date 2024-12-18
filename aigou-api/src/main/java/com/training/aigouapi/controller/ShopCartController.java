package com.training.aigouapi.controller;

import com.training.aigouapi.entity.ShopCart;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.ShopCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 购物车控制器，用于处理与购物车相关的HTTP请求
 */
@CrossOrigin    // 允许跨域请求
@RestController // 表示这是一个控制器类，用于处理HTTP请求, 并将返回的数据转换为JSON格式
@RequestMapping("/shopCart")// 表示该控制器处理的所有请求的URL路径都会以"/shopCart"开头
public class ShopCartController {

    // 注入收货地址服务
    @Resource
    private ShopCartService shopCartService;

    /**
     * 分页查询购物车
     */
    @GetMapping
    public ResponseEntity<PageEntity<ShopCart>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<ShopCart> pageEntity = shopCartService.findPage(page, size);
        return ResponseEntity.ok(pageEntity);
    }

    /**
     * 查询所有购物车信息
     *
     * @return ResponseEntity<List<ShopCart>> 返回所有收货地址的列表
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ShopCart>> findAll() {
        List<ShopCart> shopCartes = shopCartService.findAll();
        return ResponseEntity.ok(shopCartes);
    }

    /**
     * 根据购物车id查询
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ShopCart shopCart = shopCartService.findId(id);
        if (shopCart != null) {
            return ResponseEntity.ok(shopCart);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 添加新收购物车
     */
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ShopCart shopCart) {
        if (shopCart == null || shopCart.getId() == null || shopCart.getCartId() == null || shopCart.getUserId() == null){
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        if (shopCartService.findId(shopCart.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同品牌 ID");
        }
        boolean rs = shopCartService.save(shopCart);
        if (rs) {
            return ResponseEntity.ok(shopCart);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新购物车
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ShopCart shopCart) {
        if (shopCart == null || id == null || shopCart.getCartId() == null || shopCart.getUserId() == null) {
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        boolean rs = shopCartService.update(shopCart);
        if (rs) {
            return ResponseEntity.ok(shopCart);
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
    /**
     * 删除购物车
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = shopCartService.remove(id);
        if (rs) {
            return ResponseEntity.ok("已删除 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除品牌失败");
        }
    }
}

package com.training.aigouapi.controller;

import com.training.aigouapi.entity.Brand;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌
 *
 * @author ethan
 */
@CrossOrigin
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Resource
    private BrandService brandService;

    /**
     * 分页查询品牌信息
     *
     * @param page  当前页码
     * @param size 每页大小
     * @return 包含品牌数据的分页对象
     */
    @GetMapping
    public ResponseEntity<PageEntity<Brand>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<Brand> brands = brandService.findPage(page, size);
        return ResponseEntity.ok(brands);
    }

    /**
     * 查询所有品牌
     *
     * @return 所有品牌的列表
     */
    @GetMapping("/all")
    public ResponseEntity<List<Brand>> findAll() {
        List<Brand> brands = brandService.findAll();
        return ResponseEntity.ok(brands);
    }

    /**
     * 按ID查询品牌信息
     *
     * @param id 品牌ID
     * @return 品牌对象或错误信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Brand brand = brandService.findId(id);
        if (brand != null) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 搜索品牌信息
     *
     * @param words 关键字
     * @return 符合条件的品牌列表
     */
//    @GetMapping("/search")
//    public ResponseEntity<List<Brand>> search(@RequestParam String words) {
//        List<Brand> brands = brandService.search(words);
//        return ResponseEntity.ok(brands);
//    }

    /**
     * 添加品牌
     *
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Brand brand) {
        if (brand == null || brand.getId() == null || brand.getBrandName() == null || brand.getBrandType() == null || brand.getBrandImg() == null) {
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        if (brandService.findId(brand.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同品牌 ID");
        }
        boolean rs = brandService.save(brand);
        if (rs) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新品牌信息
     *
     * @param id    品牌ID
     * @param brand 品牌对象
     * @return 成功或失败信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Brand brand) {
        if (brand == null || id == null || brand.getBrandName() == null || brand.getBrandType() == null || brand.getBrandImg() == null) {
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        boolean rs = brandService.update(brand);
        if (rs) {
            return ResponseEntity.ok(brand);
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }

    /**
     * 删除品牌
     *
     * @param id 品牌ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = brandService.remove(id);
        if (rs) {
            return ResponseEntity.ok("已删除 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除品牌失败");
        }
    }
}

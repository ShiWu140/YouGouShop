package com.training.aigouapi.controller;

import com.training.aigouapi.entity.ReceivingAddress;
import com.training.aigouapi.common.PageEntity;
import com.training.aigouapi.service.ReceivingAddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * 收货地址控制器，处理收货地址相关的请求
 * @RequestMapping("/receivingAddress")表示该控制器处理的所有请求的URL路径都会以"/receivingAddress"开头
 * @CrossOrigin                        允许跨域请求
 * @RestController                     表示这是一个控制器类，用于处理HTTP请求, 并将返回的数据转换为JSON格式
 */
@CrossOrigin    // 允许跨域请求
@RestController // 表示这是一个控制器类，用于处理HTTP请求, 并将返回的数据转换为JSON格式
@RequestMapping("/receivingAddress")// 表示该控制器处理的所有请求的URL路径都会以"/receivingAddress"开头
public class ReceivingAddressController {

    // 注入收货地址服务
    @Resource
    private ReceivingAddressService receivingAddressService;

    /**
     * 分页查询收货地址
     * @param page 当前页码,size 每页大小
     * @return ResponseEntity<PageEntity<ReceivingAddress>> 返回包含收货地址数据的分页对象
     */
    @GetMapping
    public ResponseEntity<PageEntity<ReceivingAddress>> findPage(@RequestParam int page, @RequestParam int size) {
        PageEntity<ReceivingAddress> pageEntity = receivingAddressService.findPage(page, size);
        return ResponseEntity.ok(pageEntity);
    }

    /**
     * 查询所有收货地址
     *
     * @return ResponseEntity<List<ReceivingAddress>> 返回所有收货地址的列表
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ReceivingAddress>> findAll() {
        List<ReceivingAddress> receivingAddresses = receivingAddressService.findAll();
        return ResponseEntity.ok(receivingAddresses);
    }

    /**
     * 根据收货地址id查询收货地址
     *
     * @param id 收货地址id
     *
     * @return ResponseEntity<ReceivingAddress> 返回查询到的收货地址对象或错误信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ReceivingAddress receivingAddress = receivingAddressService.findId(id);
        if (receivingAddress != null) {
            return ResponseEntity.ok(receivingAddress);
        } else {
            return ResponseEntity.badRequest().body("没有找到");
        }
    }

    /**
     * 添加新收货地址
     */
    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody ReceivingAddress receivingAddress) {
        if (receivingAddress == null || receivingAddress.getId() == null || receivingAddress.getReceivingAddress() == null || receivingAddress.getReceivingPerson() == null || receivingAddress.getMobilePhone() == null || receivingAddress.getUserId() == null){
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        if (receivingAddressService.findId(receivingAddress.getId()) != null) {
            return ResponseEntity.badRequest().body("存在有相同品牌 ID");
        }
        boolean rs = receivingAddressService.save(receivingAddress);
        if (rs) {
            return ResponseEntity.ok(receivingAddress);
        } else {
            return ResponseEntity.badRequest().body("保存失败");
        }
    }

    /**
     * 更新收货地址
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ReceivingAddress receivingAddress) {
        if (receivingAddress == null || id== null || receivingAddress.getReceivingAddress() == null || receivingAddress.getReceivingPerson() == null || receivingAddress.getMobilePhone() == null || receivingAddress.getUserId() == null) {
            return ResponseEntity.badRequest().body("品牌信息不完整");
        }
        boolean rs = receivingAddressService.update(receivingAddress);
        if (rs) {
            return ResponseEntity.ok(receivingAddress);
        } else {
            return ResponseEntity.badRequest().body("更新失败");
        }
    }
    /**
     * 删除收货地址
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        boolean rs = receivingAddressService.remove(id);
        if (rs) {
            return ResponseEntity.ok("已删除 " + id);
        } else {
            return ResponseEntity.badRequest().body("删除品牌失败");
        }
    }
}

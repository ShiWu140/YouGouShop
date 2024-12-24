package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.ReceivingAddress;
import com.training.aigoushopapi.service.IReceivingAddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/receivingAddress")
public class ReceivingAddressController {
    @Resource
    private IReceivingAddressService receivingAddressService;

    /**
     * 分页查询 收货地址信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 收货地址数据的分页对象
     */
    @GetMapping("/page")
    public Page<ReceivingAddress> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ReceivingAddress> page = new Page<>(current, size);
        return receivingAddressService.page(page);
    }

    /**
     * 查询所有 收货地址
     *
     * @return 所有 收货地址的列表
     */
    @GetMapping("/all")
    public List<ReceivingAddress> getAll() {
        return receivingAddressService.list();
    }

    /**
     * 按ID查询 收货地址信息
     *
     * @param id 收货地址ID
     * @return 收货地址对象或错误信息
     */
    @GetMapping("/{id}")
    public ReceivingAddress getId(@PathVariable Long id) {
        return receivingAddressService.getById(id);
    }

    /**
     * 添加 收货地址
     *
     * @param receivingAddress 收货地址对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody ReceivingAddress receivingAddress) {
        return receivingAddressService.save(receivingAddress);
    }

    /**
     * 更新 收货地址信息
     *
     * @param receivingAddress 收货地址对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody ReceivingAddress receivingAddress) {
        return receivingAddressService.updateById(receivingAddress);
    }

    /**
     * 删除 收货地址
     *
     * @param receivingAddress 收货地址对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody ReceivingAddress receivingAddress) {
        return receivingAddressService.removeById(receivingAddress.getId());
    }

    /**
     * 根据用户ID获取收货地址
     */
    @GetMapping("/getReceivingAddressByUserId")
    public List<ReceivingAddress> getReceivingAddressByUserId(Long userId) {
        return receivingAddressService.getReceivingAddressByUserId(userId);
    }
}

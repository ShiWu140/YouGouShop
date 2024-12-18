package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.ReceivingAddress;
import com.training.aigoushopapi.service.IReceivingAddressService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 收货地址
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/receivingAddress")
public class ReceivingAddressController {
    @Resource
    private IReceivingAddressService receivingAddressService;

    /**
     * 分页查询 收获地址信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 收获地址数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<ReceivingAddress> page = new Page<>(current, size);
        return Result.success(receivingAddressService.page(page));
    }

    /**
     * 查询所有 收获地址
     *
     * @return 所有 收获地址的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(receivingAddressService.list());
    }

    /**
     * 按ID查询 收获地址信息
     *
     * @param id 收获地址ID
     * @return 收获地址对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        ReceivingAddress receivingAddress = receivingAddressService.getById(id);
        if (receivingAddress != null) {
            return Result.success(receivingAddress);
        } else {
            return Result.error("没有找到ID为 " + id + " 的收获地址！");
        }
    }

    /**
     * 添加 收获地址
     *
     * @param receivingAddress 收获地址对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody ReceivingAddress receivingAddress) {
        boolean rs = receivingAddressService.save(receivingAddress);
        if (rs) {
            return Result.success(receivingAddress);
        } else {
            return Result.error("添加 收获地址失败！");
        }
    }

    /**
     * 更新 收获地址信息
     *
     * @param receivingAddress 收获地址对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody ReceivingAddress receivingAddress) {
        boolean rs = receivingAddressService.updateById(receivingAddress);
        if (rs) {
            return Result.success(receivingAddress);
        } else {
            return Result.error("编辑ID为 " + receivingAddress.getId() + " 的收获地址失败！");
        }
    }

    /**
     * 删除 收获地址
     *
     * @param id 收获地址ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = receivingAddressService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的收获地址成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的收获地址失败！");
        }
    }
}

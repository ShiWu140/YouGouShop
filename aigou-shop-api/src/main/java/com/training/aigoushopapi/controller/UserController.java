package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.common.Result;
import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.service.IUserService;
import com.training.aigoushopapi.util.MD5Utils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户
 * 前端控制器
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 分页查询 用户信息信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 用户信息数据的分页对象
     */
    @GetMapping
    public Result page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<User> page = new Page<>(current, size);
        return Result.success(userService.page(page));
    }

    /**
     * 查询所有 用户信息
     *
     * @return 所有 用户信息的列表
     */
    @GetMapping("/all")
    public Result getAll() {
        return Result.success(userService.list());
    }

    /**
     * 按ID查询 用户信息信息
     *
     * @param id 用户信息ID
     * @return 用户信息对象或错误信息
     */
    @GetMapping("/{id}")
    public Result getId(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("没有找到ID为 " + id + " 的用户信息！");
        }
    }

    /**
     * 添加 用户信息
     *
     * @param user 用户信息对象
     * @return 成功或失败信息
     */
    @PostMapping
    public Result add(@RequestBody User user) {
        user.setUserPwd(MD5Utils.md5(user.getUserPwd()));
        boolean rs = userService.save(user);
        if (rs) {
            return Result.success(user);
        } else {
            return Result.error("添加 用户信息失败！");
        }
    }

    /**
     * 更新 用户信息信息
     *
     * @param user 用户信息对象
     * @return 成功或失败信息
     */
    @PutMapping
    public Result modify(@RequestBody User user) {
        boolean rs = userService.updateById(user);
        if (rs) {
            return Result.success(user);
        } else {
            return Result.error("编辑ID为 " + user.getUserId() + " 的用户信息失败！");
        }
    }

    /**
     * 删除 用户信息
     *
     * @param id 用户信息ID
     * @return 成功或失败信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean rs = userService.removeById(id);
        if (rs) {
            return Result.success("删除ID为 " + id + " 的用户信息成功！");
        } else {
            return Result.error("删除ID为 " + id + " 的用户信息失败！");
        }
    }

    /**
     * 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户名或密码错误！");
        }
    }

}

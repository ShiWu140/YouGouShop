package com.training.aigoushopapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.training.aigoushopapi.annotation.ResponseResult;
import com.training.aigoushopapi.entity.User;
import com.training.aigoushopapi.entity.UserVO;
import com.training.aigoushopapi.service.IUserService;
import com.training.aigoushopapi.util.MD5Utils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 * @author shiwu
 * @since 2024-12-18
 */
@RestController
@ResponseResult
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 分页查询 用户信息
     *
     * @param current 当前页码
     * @param size    每页大小
     * @return 包含 用户信息数据的分页对象
     */
    @GetMapping("/page")
    public Page<User> page(@RequestParam Integer current, @RequestParam Integer size) {
        Page<User> page = new Page<>(current, size);
        return userService.page(page);
    }

    /**
     * 查询所有 用户信息
     *
     * @return 所有 用户信息的列表
     */
    @GetMapping("/all")
    public List<User> getAll() {
        return userService.list();
    }

    /**
     * 按ID查询 用户信息
     *
     * @param id 用户信息ID
     * @return 用户信息对象或错误信息
     */
    @GetMapping("/{id}")
    public User getId(@PathVariable Long id) {
        return userService.getById(id);
    }

    /**
     * 添加 用户信息
     *
     * @param user 用户信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/add")
    public boolean add(@RequestBody User user) {
        user.setUserPwd(MD5Utils.md5(user.getUserPwd()));
        return userService.save(user);
    }

    /**
     * 更新 用户信息
     *
     * @param user 用户信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/modify")
    public boolean modify(@RequestBody User user) {
        return userService.updateById(user);
    }

    /**
     * 删除 用户信息
     *
     * @param user 用户信息对象
     * @return 成功或失败信息
     */
    @PostMapping("/delete")
    public boolean delete(@RequestBody User user) {
        return userService.removeById(user.getUserId());
    }

    /**
     * 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return Result
     */
    @PostMapping("/login")
    public UserVO login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }
}

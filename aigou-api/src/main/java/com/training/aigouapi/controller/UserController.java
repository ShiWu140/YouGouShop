package com.training.aigouapi.controller;

import com.training.aigouapi.entity.ApiResponse;
import com.training.aigouapi.entity.PageEntity;
import com.training.aigouapi.entity.User;
import com.training.aigouapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 分页查询用户信息
     *
     * @param current  当前页码
     * @param pageSize 每页显示数量
     * @return 包含分页用户信息的响应实体
     */
    @GetMapping("/page/{current}/{pageSize}")
    public ResponseEntity<PageEntity<User>> page(@PathVariable(value = "current") Integer current, @PathVariable(value = "pageSize") Integer pageSize) {
        PageEntity<User> userPageEntity = userService.findPage(current, pageSize);
        return ResponseEntity.ok(userPageEntity);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param id 用户ID
     * @return 查询到的用户信息或错误响应实体
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable(value = "id") String id) {
        Optional<User> user = Optional.ofNullable(userService.findId(id));
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    /**
     * 查询所有用户信息
     *
     * @return 所有用户信息列表的响应实体
     */
    @GetMapping("/queryAll")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * search 搜索
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> search(@RequestParam String keyword) {
//        List<User> users = userService.search(keyword);
//        return ResponseEntity.ok(users);
        return null;
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return 更新结果的响应实体
     */
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> update(User user) {
        boolean rs = userService.update(user);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "User 更新成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User 更新失败"));
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果的响应实体
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String userId) {
        boolean rs = userService.remove(userId);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "User 删除成功"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User 删除失败"));
        }
    }

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果的响应实体
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> register(User user) {
        if (userService.findId(user.getUserId()) != null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User 已经存在"));
        }
        boolean rs = userService.save(user);
        if (rs) {
            return ResponseEntity.ok(new ApiResponse<>(200, "User 注册成功", user.getUserId()));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User 注册失败"));
        }
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param userPwd  用户密码
     * @return 登录成功的用户信息或错误响应实体
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestParam String userName, @RequestParam String userPwd) {
        User user = userService.login(userName, userPwd);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>(200, "success"));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

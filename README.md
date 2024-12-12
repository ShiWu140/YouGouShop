旧代码地址：https://gitee.com/ethanzhao2001/aigou

运行环境：JDK 17

# 命名规范

1. 小驼峰命名
2. 多写注释
3. **不要**随意改动接口定义
4. **主要修改controller**，修改之前dao.impl，service.impl不规范的地方
5. 后端接口修改好后，记得同步**修改前端请求**
6. 部分页面需要添加**搜索功能**
7. Controller 方法名及接口定义。**item为资源名**，如product.

   | 功能   | 方法     | 路径                               | Controller 方法名 |
   |------|--------|----------------------------------|----------------|
   | 分页   | GET    | `/items?page={page}&size={size}` | `page`         |
   | 查询   | GET    | `/items/{id}`                    | `getID`        |
   | 查询全部 | GET    | `/items`                         | `getAll`       |
   | 搜索   | GET    | `/items/search?words={words}`    | `search`       |
   | 修改   | PUT    | `/items`                         | `modify`       |
   | 删除   | DELETE | `/items/{id}`                    | `delete`       |
   | 添加   | POST   | `/items`                         | `add`          |
   | 登录   | POST   | `/auth/login`                    | `login`        |

8. 修改方法
    1. entity不变
    2. dao
        1. @Repository
    3. service
        1. @Service
        2. @Resource
    4. controller
        1. @CrossOrigin
        2. @RestController
        3. @RequestMapping
        4. @Resource

# 施工进度

## 商品

- [ ] 商品管理
- [ ] 商品类型管理
- [ ] 商品品牌管理
- [ ] 商品销量管理

## 购物车 李科

- [ ] 购物车管理
- [ ] 购物车商品管理
- [ ] 收货地址

## 订单 王熙

- [ ] 订单管理
- [ ] 订单商品管理

## 系统 杜杰龙

- [ ] 用户管理
- [ ] 轮播图管理
- [ ] 搜索历史管理


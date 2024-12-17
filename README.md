旧代码地址：https://gitee.com/ethanzhao2001/aigou
# 环境

JDK 17

Springboot 3.4.0


# 后台

## 命名规范

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
   | 查询   | GET    | `/items/{id}`                    | `getId`        |
   | 查询全部 | GET    | `/items/all`                     | `getAll`       |
   | 搜索   | GET    | `/items/search?words={words}`    | `search`       |
   | 修改   | PUT    | `/items`                         | `modify`       |
   | 删除   | DELETE | `/is/{id}`                       | `delete`       |
   | 添加   | POST   | `/itemtems`                      | `add`          |
   | 登录   | POST   | `/auth/login`                    | `login`        |

8. 修改方法
    1. entity不变
    2. dao -> mapper
    3. service
        1. @Service
        2. @Resource
    4. controller
        1. @CrossOrigin
        2. @RestController
        3. @RequestMapping
        4. @Resource

## 🚧施工进度

### 商品

- [x] 商品管理
- [x] 商品类型管理
- [x] 商品品牌管理
- [x] 商品销量管理

### 购物车 李科

- [ ] 购物车管理
- [ ] 购物车商品管理
- [ ] 收货地址

### 订单 王熙

- [ ] 订单管理
- [ ] 订单商品管理

### 系统 杜杰龙

- [x] 用户管理
- [x] 轮播图管理
- [x] 搜索历史管理

# 前台

## 命名规范



## 🚧施工进度

### 赵映添

- [ ] goodsDetail.html

- [ ] index.html


### 杜杰龙

- [ ] shoppingCart.html


### 李科

- [ ] login.html

- [ ] register.html

- [ ] deliverAddress.html


### 王熙

- [ ] buyerTrade.html


### 熊诗怡

- [ ] classify.html

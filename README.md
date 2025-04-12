# Aigou SpringBoot Project

## 项目介绍
Aigou是一个基于SpringBoot3和Vue的电商商城系统，采用前后端分离架构，包含后台管理系统和前台商城系统。

## 技术栈

### 后端技术
- JDK 17
- SpringBoot 3.4.0
- Spring Security
- MyBatis
- Redis
- MySQL
- Maven

### 前端技术
- Vue2 + Element UI (后台管理)
- Vue3 + Element Plus (前台商城)

## 项目结构
- aigou-shop-api: 后端接口服务
- aigou-web: 后台管理系统
- aigou-shop: 前台商城系统

## 运行说明

### 后端接口服务
- 端口：8080
- 环境要求：JDK 17
- 启动方式：直接运行主类或使用Maven命令

### 后台管理系统
- 端口：8081
- 技术栈：Vue2 + Element UI
- 启动步骤：
```bash
cd aigou-web
npm install
npm run serve
```

### 前台商城系统
- 端口：8091
- 技术栈：Vue3 + Element Plus
- 启动步骤：
```bash
cd aigou-shop
npm install
npm run dev
```

## 主要功能
- 商品管理
- 订单管理
- 会员管理
- 权限管理
- 系统设置
- 数据统计

## 开发环境
- IDE推荐：IntelliJ IDEA
- 数据库：MySQL
- 缓存：Redis
- 包管理：Maven

## 注意事项
1. 确保已安装所需环境（JDK 17、Node.js、MySQL、Redis）
2. 按照顺序启动服务：先启动后端，再启动前端
3. 首次运行需要初始化数据库

## 相关截图

## 贡献指南
欢迎提交Issue和Pull Request

## 许可证
本项目采用开源协议

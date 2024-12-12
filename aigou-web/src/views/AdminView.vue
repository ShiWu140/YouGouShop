<script>
export default {
  data() {
    return {
      username: "",
      isCollapse: false
    }
  },
  mounted() {
    if (localStorage.getItem('user') === null) {
      this.$router.push('/login');
    }
    const user = JSON.parse(localStorage.getItem('user'));
    if (user.user_name.length > 8) {
      this.username = user.user_name.substring(0, 8) + '...';
    } else {
      this.username = user.user_name;
    }
  },
  methods: {
    handleCommand(command) {
      if (command === 'logout') {
        this.$confirm('确认退出登录?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          localStorage.removeItem('user');
          this.$router.push('/login');
        }).catch(() => {
          this.$message.info('已取消退出');
        });
      }
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<template>
  <div class="admin">
    <el-container>
      <el-header>
        <div class="title">
          爱购后台管理
        </div>
        <div class="user">
          <el-avatar icon="el-icon-user-solid"></el-avatar>
          &emsp;
          <el-dropdown @command="handleCommand">
                    <span class="el-dropdown-link">
                        用户：{{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside width="auto">
          <div class="collapse-button">
            <el-button :icon="isCollapse ? 'el-icon-d-arrow-right' : 'el-icon-d-arrow-left'" circle
                       @click="isCollapse = !isCollapse">
            </el-button>
          </div>
          <el-menu :collapse="isCollapse" :router=true class="el-menu-vertical" default-active="/admin"
                   @close="handleClose"
                   @open="handleOpen">

            <el-menu-item index="/admin">
              <i class="el-icon-house"></i>
              <span slot="title">首页</span>
            </el-menu-item>

            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-goods"></i>
                <span>商品管理</span>
              </template>
              <el-menu-item index="/admin/product">商品管理</el-menu-item>
              <el-menu-item index="/admin/product-type">商品类型管理</el-menu-item>
              <el-menu-item index="/admin/brand">商品品牌管理</el-menu-item>
              <el-menu-item index="/admin/sales">商品销量管理</el-menu-item>
            </el-submenu>

            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-shopping-cart-full"></i>
                <span>购物管理</span>
              </template>
              <el-menu-item index="/admin/shop-cart">购物车管理</el-menu-item>
              <el-menu-item index="/admin/shop-cart-product">购物车商品管理</el-menu-item>
              <el-menu-item index="/admin/receiving-address">收货地址</el-menu-item>
            </el-submenu>

            <el-submenu index="4">
              <template slot="title">
                <i class="el-icon-tickets"></i>
                <span>订单管理</span>
              </template>
              <el-menu-item index="/admin/order">订单管理</el-menu-item>
              <el-menu-item index="/admin/order-product">订单商品管理</el-menu-item>
            </el-submenu>

            <el-submenu index="5">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>系统管理</span>
              </template>
              <el-menu-item index="/admin/user">用户管理</el-menu-item>
              <el-menu-item index="/admin/carousel-figure">轮播图管理</el-menu-item>
              <el-menu-item index="/admin/search-history">搜索历史管理</el-menu-item>
            </el-submenu>

          </el-menu>
        </el-aside>
        <el-main>
          <!--            子页面-->
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style>
.admin {
  height: 100vh;
  width: 100%;
}

.el-container {
  height: 100%;
}

.el-header {
  display: flex;
  background-color: #5A9CF8;
  padding: 0;
}

.el-aside {
  background-color: #fff;
  border-right: solid 1px #e6e6e6;
}

.el-main {
  background-color: #F4F4F5;
}

.title {
  width: 250px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 25px;
  font-weight: bold;
  color: #F4F4F5;
}

.user {
  width: 250px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-left: auto;
}

.el-dropdown-link {
  cursor: pointer;
  color: #fff;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 250px;
  min-height: 400px;
}

.el-menu {
  border-right: 0;
}

.collapse-button {
  text-align: center;
  margin: 10px 0 10px 0;
}

.add-button {
  margin: 0 20px 20px 20px;
}

.pagination {
  height: 60px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 修改分页按钮的背景颜色 */
.custom-pagination .el-pager li,
.custom-pagination .btn-prev,
.custom-pagination .btn-next {
  background-color: #fff !important; /* 设置背景色为白色 */
}

/* 如果需要修改悬停时的颜色，可以添加如下样式 */
.custom-pagination .el-pager li:hover,
.custom-pagination .btn-prev:hover,
.custom-pagination .btn-next:hover {
  background-color: #f0f0f0 !important; /* 设置悬停时的背景色 */
}

/* 如果还需要修改当前选中页码的背景颜色，可以添加如下样式 */
.custom-pagination .el-pager li.active {
  background-color: #409eff !important; /* 设置当前页码的背景色 */
  color: #fff; /* 设置当前页码的文字颜色 */
}

</style>
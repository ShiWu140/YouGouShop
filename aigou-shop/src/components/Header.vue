<script setup>
import {useRoute} from 'vue-router';
import {onMounted, ref} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

let isHomeRoute = ref(false);
let isLogin = ref(false);
const userName = localStorage.getItem("userName")
const route = useRoute();
// 退出登录
const logout = async () => {
  // 清除 localStorage 中的 token 和 userId
  localStorage.removeItem('token');
  localStorage.removeItem('userId');
  localStorage.removeItem('rememberMe')
  ElMessage.info('已退出登录')
  // 跳转到首页，并清除 query 参数
  await router.replace({path: '/'});
};
onMounted(() => {
  isHomeRoute.value = (route.path === "/");
  isLogin.value = (localStorage.getItem("token") != null);
});
</script>

<template>
  <div class="top" id="top">
    <div class="top-bar">
      <div class="w1230 top-main">
        <router-link to="/" class="toIndex" v-if="!isHomeRoute">
          <i class="fa fa-home"></i>
          <span>爱购网首页</span>
        </router-link>

        <div class="w1230 top-bar-main" v-if="!isLogin">
          <ul>
            <li>
              <router-link to="/login" class="login">登录</router-link>
            </li>
            <li>
              <router-link to="/register" class="register">注册</router-link>
            </li>
            <li>
              <router-link to="/buyerTrade"><i class="fa fa-file-text-o"></i>我的订单</router-link>
            </li>
            <li>
              <router-link to="/shoppingCart"><i class="fa fa-shopping-cart"></i>购物车</router-link>
            </li>
          </ul>
        </div>
        <div class="account" v-if="isLogin">
          <span>用户：{{ userName }}<i class="fa fa-caret-down"></i></span>
          <div class="manage">
            <ul>
              <li>
                <router-link to="/deliverAddress">收货地址</router-link>
              </li>
              <li>
                <router-link to="/shoppingCart">购物车</router-link>
              </li>
              <li>
                <router-link to="/buyerTrade">我的订单</router-link>
              </li>
              <li>
                <router-link to="/" @click.prevent="logout">退出</router-link>
              </li>
            </ul>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>

</style>

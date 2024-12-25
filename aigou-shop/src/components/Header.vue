<script setup>

</script>

<template>
  <div class="top" id="top">
    <!--头部bar-->
    <div class="top-bar">
      <div class="w1230 top-bar-main">
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
    </div>
  </div>
</template>

<style scoped>

</style>
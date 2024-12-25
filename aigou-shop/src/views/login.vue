<script setup>
import Footer from "@/components/Footer.vue";
import {ref} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";

const userName = ref('');
const password = ref('');
const code = ref('');
const rememberMe = ref(false);
const router = useRouter();

const login = async () => {
  if (!userName.value || !password.value) {
    alert('请输入用户名和密码');
    return;
  }
  const url = `/user/login?userName=${encodeURIComponent(userName.value)}&password=${encodeURIComponent(password.value)}`;
  console.log('请求地址:', url);
  try {
    const response = await axios.post(url);
    const {code, msg, data} = response.data;
    if (code === 1) {
      // 登录成功，保存 token 和 userId
      localStorage.setItem('token', data.token);
      localStorage.setItem('userId', data.userId);
      if (rememberMe.value) {
        localStorage.setItem('rememberMe', 'true');
      }
      ElMessage.info('登录成功')
      // 跳转到首页
      await router.push('/');
    } else {
      ElMessage.error(`登录失败: ${msg}`);
    }
  } catch (error) {
    console.error('登录接口调用失败', error);
    ElMessage.warning('登录失败，请稍后重试');
  }
};
</script>

<template>
  <!--登录/注册的头部-->
  <div class="lr-top w1230">
    <a href="index.html"><img src="@/assets/img/logo.png" width="150px" height="60px"/></a>
    <div class="top-link">
      <a href="#" class="top-link1"></a>
      <a href="#" class="top-link2"></a>
      <a href="#" class="top-link3"></a>
    </div>
  </div>
  <!--登录/注册的中部-->
  <div class="lr-main">
    <div class="w1230">
      <div class="reg-div">
        <h3 class="login-title">登录爱购</h3>
        <p class="go-reg">还没有爱购账号？
          <router-link to="/register">在此注册</router-link>
        </p>
        <form @submit.prevent="login">
          <p><span class="icon-account"></span><input type="text" v-model="userName" placeholder="请输入已注册的账号"/>
          </p>
          <p><span class="icon-pwd"></span><input type="password" v-model="password" placeholder="请输入密码"/></p>
          <p class="clear-float"><input type="text" v-model="code" placeholder="验证码" class="code"/><img
              src="@/assets/img/code.jpg" width="110" height="42" class="code-img"/></p>
          <p><input type="checkbox" v-model="rememberMe" class="chk-login"/><span>7天免登录</span>
          </p>
          <button type="submit" class="sum-btn">登录</button>
        </form>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
/* 添加样式 */
</style>

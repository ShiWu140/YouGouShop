<script setup>
import Footer from "@/components/Footer.vue";
import {ref} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";

// 表单数据
const userName = ref('');
const userPwd = ref('');
const confirmPwd = ref('');
const code = ref('');
const router = useRouter();

// 表单提交逻辑
const register = async () => {
  // 简单的表单验证
  if (!userName.value || !userPwd.value || !confirmPwd.value) {
    alert('请填写完整信息');
    return;
  }
  if (userPwd.value !== confirmPwd.value) {
    alert('两次输入的密码不一致');
    return;
  }
  // 请求数据
  const data = {
    // 后端自动注入
    userId: '',
    userName: userName.value,
    userPwd: userPwd.value,
    // 默认普通用户
    userType: 0,
  };

  try {
    const response = await axios.post('/user/add', data);
    console.log('请求数据:', response.data)
    if (response.data.code === 1) {
      ElMessage.success('注册成功')
      await router.push('/login'); // 注册成功后跳转到登录页面
    } else {
      ElMessage.error('注册失败,请重试')
    }
  } catch (error) {
    console.error('注册接口调用失败:', error);
    ElMessage.warning('注册接口调用失败', error)
  }
};
</script>

<template>
  <!-- 登录/注册的头部 -->
  <div class="lr-top w1230">
    <router-link to="/"><img src="@/assets/img/logo.png" width="150px" height="60px"/></router-link>
    <div class="top-link">
      <a href="#" class="top-link1"></a>
      <a href="#" class="top-link2"></a>
      <a href="#" class="top-link3"></a>
    </div>
  </div>
  <!-- 登录/注册的中部 -->
  <div class="lr-main">
    <div class="w1230">
      <div class="reg-div">
        <h3 class="login-title">注册爱购</h3>
        <p class="go-reg">
          若有账号？
          <router-link to="/login">在此登录</router-link>
        </p>
        <form @submit.prevent="register">
          <p><input v-model="userName" type="text" placeholder="请输入注册的账号"/></p>
          <p><input v-model="userPwd" type="password" placeholder="请输入密码"/></p>
          <p><input v-model="confirmPwd" type="password" placeholder="请确认密码"/></p>
          <p class="clear-float">
            <input v-model="code" type="text" placeholder="验证码" class="code"/>
            <img src="@/assets/img/code.jpg" width="110" height="42" class="code-img"/>
          </p>
          <input type="submit" value="同意并注册协议" class="sum-btn"/>
          <p class="pro"><a href="#">《爱购用户协议》</a></p>
        </form>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
.reg-div input {
  width: 305px;
  padding-left: 10px;
}

.pro {
  margin-top: 10px;
}

.pro a {
  color: #B41E23;
}
</style>

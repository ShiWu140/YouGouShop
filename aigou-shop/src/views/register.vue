<script setup>
import Footer from "@/components/Footer.vue";
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import {register as registerApi} from '@/api/user';
import SIdentify from "@/components/Sidentify.vue";

// 表单数据
const userName = ref('');
const userPwd = ref('');
const confirmPwd = ref('');
const code = ref('');
const router = useRouter();

// 验证码相关
let identifyCode = ref('') //图形验证码
let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz') //验证码出现的数字和字母

// 生成随机数
const randomNum = (min, max) => {
  max = max + 1
  return Math.floor(Math.random() * (max - min) + min)
}

// 随机生成验证码字符串
const makeCode = (o, l) => {
  for (let i = 0; i < l; i++) {
    identifyCode.value += o[randomNum(0, o.length)]
  }
}

// 更新验证码
const refreshCode = () => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
}

// 表单提交逻辑
const register = async () => {
  // 简单的表单验证
  if (!userName.value || !userPwd.value || !confirmPwd.value) {
    ElMessage.warning('请填写完整信息');
    return;
  }
  if (userPwd.value !== confirmPwd.value) {
    ElMessage.warning('两次输入的密码不一致');
    return;
  }
  if (!code.value) {
    ElMessage.warning('请输入验证码');
    return;
  }
  if (code.value.toLowerCase() !== identifyCode.value.toLowerCase()) {
    ElMessage.warning('验证码错误');
    refreshCode();
    return;
  }

  try {
    const response = await registerApi({
      userName: userName.value,
      userPwd: userPwd.value
    });

    if (response.code === 1) {
      ElMessage.success('注册成功')
      await router.push('/login'); // 注册成功后跳转到登录页面
    } else {
      ElMessage.error('注册失败,请重试')
    }
  } catch (error) {
    ElMessage.warning('注册失败，请稍后重试');
  }
};

// 组件挂载时生成验证码
onMounted(() => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
})
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
        <h3 class="login-title">注册优购</h3>
        <p class="go-reg">
          若有账号？
          <router-link to="/login">在此登录</router-link>
        </p>
        <form @submit.prevent="register">
          <p><input v-model="userName" type="text" placeholder="请输入注册的账号"/></p>
          <p><input v-model="userPwd" type="password" placeholder="请输入密码"/></p>
          <p><input v-model="confirmPwd" type="password" placeholder="请确认密码"/></p>
          <div class="verify-code-container">
            <input v-model="code" type="text" placeholder="验证码" class="code"/>
            <SIdentify :identifyCode="identifyCode" @click="refreshCode" class="code-img"/>
          </div>
          <input type="submit" value="同意并注册协议" class="sum-btn"/>
          <p class="pro"><a href="#">《优购用户协议》</a></p>
        </form>
      </div>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
.reg-div input:not(.code) {
  width: 305px;
  padding-left: 10px;
}

.pro {
  margin-top: 10px;
}

.pro a {
  color: #B41E23;
}

.verify-code-container {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.code {
  width: 180px;
  height: 42px;
  padding: 0 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.code-img {
  cursor: pointer;
  margin-left: 10px;
  height: 42px;
}

.sum-btn {
  width: 305px !important;
}
</style>

<script setup>
import Footer from "@/components/Footer.vue";
import {onMounted, ref} from 'vue';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {ElMessage} from "element-plus";
import {login as loginApi} from '@/api/user';
import SIdentify  from "@/components/Sidentify.vue";

const userName = ref('');
const password = ref('');
const code = ref('');
const rememberMe = ref(false);
const router = useRouter();

let sidentifyMode = ref('') //输入框验证码
let identifyCode = ref('') //图形验证码
let identifyCodes = ref('1234567890abcdefjhijklinopqrsduvwxyz') //验证码出现的数字和字母

const login = async () => {
  if (!userName.value || !password.value) {
    ElMessage.warning('请输入用户名和密码');
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
    const response = await loginApi(userName.value, password.value);
    const {statusCodeValue, body} = response;

    if (statusCodeValue === 200) {
      // 登录成功，保存 token 和 userId
      localStorage.setItem('token', body.token);
      localStorage.setItem('userId', body.userId);
      localStorage.setItem("userName", body.username);
      if (rememberMe.value) {
        localStorage.setItem('rememberMe', 'true');
      }
      ElMessage.success(`登录成功！`)
      // 检查是否有重定向路径
      const redirectPath = router.currentRoute.value.query.redirect || '/';
      // 跳转到目标路径或首页
      await router.push(redirectPath);
    } else {
      ElMessage.error(`登录失败!`);
    }
  } catch (error) {
    ElMessage.warning('登录失败，请稍后重试');
  }
};
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

onMounted(() => {
  identifyCode.value = ''
  makeCode(identifyCodes.value, 4)
})
</script>

<template>
  <!--登录/注册的头部-->
  <div class="lr-top w1230">
    <router-link to="/"><img src="@/assets/img/logo.png" width="199" height="80"/></router-link>
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
        <h3 class="login-title">登录优购</h3>
        <p class="go-reg">还没有优购账号？
          <router-link to="/register">在此注册</router-link>
        </p>
        <form @submit.prevent="login">
          <p><span class="icon-account"></span><input type="text" v-model="userName" placeholder="请输入已注册的账号"/>
          </p>
          <p><span class="icon-pwd"></span><input type="password" v-model="password" placeholder="请输入密码"/></p>
          <p class="clear-float">
            <input type="text" v-model="code" placeholder="验证码" class="code"/>
            <SIdentify :identifyCode="identifyCode" @click="refreshCode" class="code-img"/>
          </p>
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
.code-img {
  cursor: pointer;
  margin-left: 10px;
  vertical-align: middle;
}
.code {
  width: 110px;
  height: 42px;
  padding: 0 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>

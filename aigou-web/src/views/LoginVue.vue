<script>
export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'change'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ]
      }
    };
  },
  mounted() {
    if (localStorage.getItem("token")) {
      this.$router.push({path: '/admin'})
    }
  },
  methods: {
    login() {
      console.log('login:' + this.$qs.stringify(this.form))
      this.$http.post('/user/login', this.$qs.stringify(this.form)).then(resp => {
        console.log("resp.data"+resp.data)
        if (resp.data) {
          // 保存JWT令牌
          localStorage.setItem("userId", resp.data.body.userId)
          localStorage.setItem("username", resp.data.body.username)
          localStorage.setItem("token", resp.data.body.token)
          // 登录成功后跳转管理页面
          this.$router.push({path: '/admin'})
        } else {
          this.$alert('登录失败，未返回有效数据', {
            confirmButtonText: '确定', type: 'warning'
          });
        }
      }).catch(error => {
        // 处理HTTP错误
        if (error.response) {
          this.$alert('登录失败，账号或密码错误', {
            confirmButtonText: '确定', type: 'warning'
          });
        } else if (error.request) {
          // 请求已发出，但没有收到响应
          this.$alert('登录失败，未收到服务器响应', {
            confirmButtonText: '确定', type: 'warning'
          });
        } else {
          // 在设置请求时发生了一些事情，触发了错误
          this.$alert('登录失败', {
            confirmButtonText: '确定', type: 'warning'
          });
        }
      });
    }
  }

}
</script>

<template>
  <div class="login">
    <div class="background">
      <el-card class="box-card">
        <el-row>
          <el-col :span="12">
            <el-image :src="require('@/assets/login.jpg')"
                      fit="cover"
                      style="width: 400px;height: 400px">
            </el-image>
          </el-col>
          <el-col :span="12">
            <h1>优购商城后台管理系统</h1>
            <el-form ref="form" :model="form" :rules="rules" style="padding: 30px">
              <el-form-item label="用户名" prop="username">
                <el-input v-model.trim="form.username" placeholder="请输入内容" @keyup.enter.native="login"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model.trim="form.password" placeholder="请输入密码" show-password
                          @keyup.enter.native="login"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button style="float: right;" type="primary" @click="login">登录</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-card>
    </div>
  </div>
</template>

<style>
.login {
  height: 100vh;
  background-image: url("../assets/login.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}

.background {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  backdrop-filter: blur(100px);
}

.box-card {
  width: 800px;
  height: 400px;
  border-radius: 20px;
  padding: 0;
}

h1 {
  text-align: center;
  margin-bottom: 0;
  margin-top: 30px;
}

.el-card__body {
  padding: 0;
}
</style>

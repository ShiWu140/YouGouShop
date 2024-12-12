<script>
export default {
  data() {
    return {
      form: {
        user_name: '',
        user_pwd: ''
      },
      rules: {
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'change'}
        ],
        user_pwd: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ]
      }
    };
  },
  mounted() {
    if (localStorage.getItem('user') != null) {
      this.$router.push('/admin');
    }
  },
  methods: {
    login() {
      console.log('login:' + this.$qs.stringify(this.form))
      this.$http.post('/user?method=login', this.$qs.stringify(this.form)).then(resp => {
        console.log(resp.data)
        if (resp.data.msg === 'success') {
          //登录成功后跳转管理页面
          localStorage.setItem('user', JSON.stringify(resp.data.data));
          this.$router.push({path: '/admin'})
        } else {
          this.$alert(resp.data.data, '登录失败', {
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
            <h1>爱购网后台管理系统</h1>
            <el-form ref="form" :model="form" :rules="rules" style="padding: 30px">
              <el-form-item label="用户名" prop="user_name">
                <el-input v-model.trim="form.user_name" placeholder="请输入内容" @keyup.enter.native="login"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="user_pwd">
                <el-input v-model.trim="form.user_pwd" placeholder="请输入密码" show-password
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
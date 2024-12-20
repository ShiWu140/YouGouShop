<script>
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: {
        userId: '',
        userName: '',
        userPwd: '',
        userType: '',
      },
      changePasswordVisible: false,
      ruleForm: {
        pass: '',
        checkPass: ''
      },
      rules: {
        pass: [
          {validator: validatePass, trigger: 'change'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'change'}
        ]
      },
    };
  },
  methods: {
    logout() {
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
    },
  },
  mounted() {
  },
};
</script>

<template>
  <div class="dashboard">
    <div class="welcome-container">
      <span class="welcome-message">🎉欢迎用户🎉<br>{{ user.userName }}</span>
      <div class="button-group">
        <el-button type="primary">修改密码</el-button>
        <el-button type="danger" @click="logout">退出登录</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="changePasswordVisible" title="修改密码">
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="auto" status-icon>
        <el-form-item label="新密码" prop="pass">
          <el-input v-model.trim="ruleForm.pass" autocomplete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model.trim="ruleForm.checkPass" autocomplete="off" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="changePasswordVisible = false">取 消</el-button>
        <el-button type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.dashboard {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.welcome-message {
  font-size: 50px;
  font-weight: bolder;
  display: block;
  text-align: center;
  color: #000;
}

.button-group {
  display: flex;
  justify-content: center;
  gap: 50px;
  margin-top: 50px;
}
</style>




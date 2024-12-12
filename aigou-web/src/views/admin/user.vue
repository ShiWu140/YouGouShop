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
      users: [],
      user: {
        user_id: '',
        user_name: '',
        user_pwd: '',
        user_type: '',
      },
      userFormVisible: false,
      UserFormVisible: false,
      changePasswordVisible: false,
      tableHeight: window.innerHeight - 220,
      operate: '',
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      // 确认密码
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
      userRules: {
        user_id: [
          {required: true, message: '请输入用户ID', trigger: 'change'}
        ],
        user_name: [
          {required: true, message: '请输入用户名', trigger: 'change'}
        ],
        user_pwd: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ],
        user_type: [
          {required: true, message: '请选择用户类型', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateUser(user) {
      this.$http.post("/user?method=" + this.operate + "&" + this.$qs.stringify(user)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadUsers(this.current)
          this.userFormVisible = false;
          this.changePasswordVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleClose() {
      this.user.user_id = '';
      this.user.user_name = '';
      this.user.user_pwd = '';
      this.user.user_type = '';
      this.ruleForm.pass = '';
      this.ruleForm.checkPass = '';
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.user = JSON.parse(JSON.stringify(row));
      this.userFormVisible = true;
    },
    handleChangePassword(index, row) {
      this.operate = 'update';
      this.user = JSON.parse(JSON.stringify(row));
      this.changePasswordVisible = true;
    },
    openChangePassword() {
      this.user.user_pwd = '';
      this.ruleForm.checkPass = '';
    },
    changePassword(user) {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.user.user_pwd = this.ruleForm.pass;
          this.operateUser(user);
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleRegistUser() {
      this.operate = 'register';
      this.userFormVisible = true;
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateUser(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    loadUsers(current) {
      this.current = current
      this.$http.post('/user?method=page&current=' + this.current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.users = res.data.data.records;
              this.total = res.data.data.total;
              if (this.users.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadUsers(this.current);
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadUsers(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadUsers(this.current);
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
  },
  mounted() {
    this.loadUsers(this.current);
    // 窗口大小变化事件的监听器
    window.addEventListener('resize', this.calculateTableHeight);
  },
  beforeDestroy() {
    // 组件销毁前移除窗口大小变化事件的监听器
    window.removeEventListener('resize', this.calculateTableHeight);
  },
}
</script>

<template>
  <div style="height: 100%">
    <div style="position: relative;">
      <h1 style="position: absolute;margin-top: 0">用户管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="handleRegistUser()">注册用户</el-button>
      </div>
    </div>

    <el-dialog :visible.sync="userFormVisible" title="用户" @close="handleClose">
      <el-form :model="user" :rules="userRules" label-width="auto">
        <el-form-item label="用户ID" prop="user_id">
          <el-input v-model.trim="user.user_id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="user_name">
          <el-input v-model.trim="user.user_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="this.operate==='register'" label="密码" prop="user_pwd">
          <el-input v-model.trim="user.user_pwd" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="user_type">
          <el-select v-model="user.user_type" placeholder="请选择用户类型">
            <el-option :value="0" label="后台用户"></el-option>
            <el-option :value="1" label="前台用户"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="userFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateUser(user)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="changePasswordVisible" title="修改密码" @close="handleClose" @open="openChangePassword">
      <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="auto" status-icon>
        <el-form-item label="密码" prop="pass">
          <el-input v-model.trim="ruleForm.pass" autocomplete="off" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input v-model.trim="ruleForm.checkPass" autocomplete="off" type="password"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="changePasswordVisible = false">取 消</el-button>
        <el-button type="primary" @click="changePassword(user)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="users"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          fixed
          label="ID"
          min-width="100px"
          prop="user_id">
      </el-table-column>
      <el-table-column
          label="用户名"
          min-width="100px"
          prop="user_name">
      </el-table-column>
      <el-table-column
          label="用户类型"
          min-width="100px"
          prop="user_type">
        <template slot-scope="scope">
          <span v-if="scope.row.user_type === 0">后台用户</span>
          <span v-else-if="scope.row.user_type === 1">前台用户</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="250px">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              type="primary"
              @click="handleChangePassword(scope.$index, scope.row)">修改密码
          </el-button>
          <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <!--    分页组件-->
      <el-pagination
          :page-size="pageSize"
          :page-sizes="[5,10,20,50,100]"
          :total="total"
          :current-page="current"
          background
          layout="total, sizes, prev, pager, next, jumper"
          class="custom-pagination"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>
</template>

<style>

</style>
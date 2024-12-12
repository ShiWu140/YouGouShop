<script>
export default {
  data() {
    return {
      receivingAddresss: [],
      receivingAddress: {
        id: '',
        receiving_address: '',
        receiving_person: '',
        mobile_phone: '',
        user_id: '',
        is_default: ''
      },
      receivingAddressFormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        receiving_address: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        receiving_person: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        mobile_phone: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        user_id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        is_default: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateReceivingAddress(receivingAddress) {
      this.$http.post("/receivingAddress?method=" + this.operate + "&" + this.$qs.stringify(receivingAddress)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadReceivingAddress();
          this.receivingAddressFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'remove';
        this.operateReceivingAddress(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.receivingAddress = JSON.parse(JSON.stringify(row));
      this.receivingAddressFormVisible = true;
    },
    addFrom() {
      this.operate = 'save';
      this.receivingAddress = {
        id: '',
        receiving_address: '',
        receiving_person: '',
        mobile_phone: '',
        user_id: '',
        is_default: ''
      };
      this.receivingAddressFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadReceivingAddress() {
      this.$http.post("/receivingAddress?method=page&current=" + this.current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.receivingAddresss = res.data.data.records;
              this.total = res.data.data.total;
              if (this.receivingAddresss.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadReceivingAddress(this.current);
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadReceivingAddress(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadReceivingAddress(this.current);
    },
  },
  mounted() {
    this.loadReceivingAddress();
    // 窗口大小变化事件的监听器
    window.addEventListener('resize', this.calculateTableHeight);
  },
  beforeDestroy() {
    // 组件销毁前移除窗口大小变化事件的监听器
    window.removeEventListener('resize', this.calculateTableHeight);
  }
}
</script>

<template>
  <div style="height: 100%">
    <div style="position: relative;">
      <h1 style="position: absolute;margin-top: 0">收货地址管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">新增收货地址</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="receivingAddressFormVisible" title="收货地址">
      <el-form :model="receivingAddress" label-width="auto" :rules="rules" ref="receivingAddressForm">
        <el-form-item label="ID" prop="id">
          <el-input v-model.trim="receivingAddress.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货地址" prop="receiving_address">
          <el-input v-model.trim="receivingAddress.receiving_address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收件人" prop="receiving_person">
          <el-input v-model.trim="receivingAddress.receiving_person" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile_phone">
          <el-input v-model.trim="receivingAddress.mobile_phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="user_id">
          <el-input v-model.trim="receivingAddress.user_id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="默认收货地址" prop="is_default">
          <el-select v-model="receivingAddress.is_default" placeholder="是否默认收货地址">
            <el-option :value="-1" label="否"></el-option>
            <el-option :value="1" label="是"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="receivingAddressFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateReceivingAddress(receivingAddress)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="receivingAddresss"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          fixed
          label="ID"
          min-width="100px"
          prop="id">
      </el-table-column>
      <el-table-column
          label="收货地址"
          min-width="100px"
          prop="receiving_address">
      </el-table-column>
      <el-table-column
          label="收件人"
          min-width="100px"
          prop="receiving_person">
      </el-table-column>
      <el-table-column
          label="手机号"
          min-width="100px"
          prop="mobile_phone">
      </el-table-column>
      <el-table-column
          label="用户 ID"
          min-width="100px"
          prop="user_id">
      </el-table-column>
      <el-table-column
          label="默认收货地址"
          min-width="100px"
          prop="is_default">
        <template slot-scope="scope">
          <span v-if="scope.row.is_default === -1">否</span>
          <span v-else-if="scope.row.is_default === 1">是</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="150px">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
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




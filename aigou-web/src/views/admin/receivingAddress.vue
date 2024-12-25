<script>
export default {
  data() {
    return {
      receivingAddresss: [],
      receivingAddress: {
        id: '',
        receivingAddress: '',
        receivingPerson: '',
        mobilePhone: '',
        userId: '',
        isDefault: ''
      },
      // 表单和表格
      FormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        receivingAddress: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        receivingPerson: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        mobilePhone: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        userId: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        isDefault: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateReceivingAddress(receivingAddress) {
      this.$http.post("/receivingAddress/" + this.operate, receivingAddress).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadData();
          this.FormVisible = false;
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
        this.operate = 'delete';
        this.operateReceivingAddress(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.receivingAddress = JSON.parse(JSON.stringify(row));
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.receivingAddress = {
        id: '',
        receivingAddress: '',
        receivingPerson: '',
        mobilePhone: '',
        userId: '',
        isDefault: ''
      };
      this.FormVisible = true;
    },
    loadData() {
      this.$http.get("/receivingAddress/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              console.log(res.data.data)
              this.receivingAddresss = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.receivingAddresss.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadData();
              }
            }
          })
    },
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
    <el-dialog :visible.sync="FormVisible" title="收货地址">
      <el-form :model="receivingAddress" label-width="auto" :rules="rules" ref="receivingAddressForm">
        <el-form-item label="收货地址" prop="receivingAddress">
          <el-input v-model.trim="receivingAddress.receivingAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收件人" prop="receivingPerson">
          <el-input v-model.trim="receivingAddress.receivingPerson" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobilePhone">
          <el-input v-model.trim="receivingAddress.mobilePhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="账号" prop="userId">
          <el-input v-model.trim="receivingAddress.userId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="默认收货地址" prop="isDefault">
          <el-select v-model="receivingAddress.isDefault" placeholder="是否默认收货地址">
            <el-option :value="-1" label="否"></el-option>
            <el-option :value="1" label="是"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateReceivingAddress(receivingAddress)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="receivingAddresss"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="收货地址 ID"
          min-width="100px"
          prop="id">
      </el-table-column>
      <el-table-column
          label="收货地址"
          min-width="100px"
          prop="receivingAddress">
      </el-table-column>
      <el-table-column
          label="收件人"
          min-width="100px"
          prop="receivingPerson">
      </el-table-column>
      <el-table-column
          label="手机号"
          min-width="100px"
          prop="mobilePhone">
      </el-table-column>
      <el-table-column
          label="用户 ID"
          min-width="100px"
          prop="userId">
      </el-table-column>
      <el-table-column
          label="默认收货地址"
          min-width="100px"
          prop="isDefault">
        <template slot-scope="scope">
          <span v-if="scope.row.isDefault === -1">否</span>
          <span v-else-if="scope.row.isDefault === 1">是</span>
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




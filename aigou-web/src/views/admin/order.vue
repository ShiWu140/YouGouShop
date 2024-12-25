<script>
export default {
  data() {
    return {
      orders: [],
      order: {
        id: '',
        createTime: '',
        receivingAddress: '',
        userId: '',
      },
      orderFormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        createTime: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        receivingAddress: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        userId: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateOrder(order) {
      this.$http.post("/order/" + this.operate, order).then((response) => {
        if (response.status === 200) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadData();
          this.orderFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data
          });
        }
      })
    },
    handleClose() {
      this.order.id = '';
      this.order.createTime = '';
      this.order.receivingAddress = '';
      this.order.userId = '';
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateOrder(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.order = JSON.parse(JSON.stringify(row));
      this.orderFormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.order = {
        id: '',
        receivingAddress: '',
        userId: '',
      };
      this.orderFormVisible = true;
    },
    loadData() {
      ;
      this.$http.get("/order/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              console.log(res.data.data)
              this.orders = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.orders.length === 0 && this.current > 1) {
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
      <h1 style="position: absolute;margin-top: 0">订单管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加订单</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="orderFormVisible" title="订单" @close="handleClose">
      <el-form :model="order" label-width="auto" :rules="rules" ref="orderForm">
        <el-form-item label="收货地址" prop="receivingAddress">
          <el-input v-model.trim="order.receivingAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model.trim="order.userId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateOrder(order)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="orders"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="创建时间"
          min-width="100px"
          :formatter="formatCreateTime"
          prop="createTime">
      </el-table-column>
      <el-table-column
          label="收货地址"
          min-width="100px"
          prop="receivingAddress">
      </el-table-column>
      <el-table-column
          label="用户 ID"
          min-width="100px"
          prop="userId">
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

<style>

</style>

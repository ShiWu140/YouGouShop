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
        id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
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
      const {createTime, ...newOrder} = order;
      if (this.operate === 'save') {
        this.$http.post("/order", newOrder).then((response) => {
          if (response.data.msg === 'success') {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            this.loadOrder(this.current)
            this.orderFormVisible = false;
          } else {
            this.$message({
              type: 'error',
              message: response.data.data
            });
          }
        })
      }
      if (this.operate === 'update') {
        this.$http.put("/order/" + newOrder.id, newOrder).then((response) => {
          if (response.data.msg === 'success') {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            this.loadOrder(this.current)
            this.orderFormVisible = false;
          } else {
            this.$message({
              type: 'error',
              message: response.data.data
            });
          }
        })
      }
      if (this.operate === 'remove') {
        this.$http.delete("/order/" + newOrder.id).then((response) => {
          if (response.data.msg === 'success') {
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
            this.loadOrder(this.current)
            this.orderFormVisible = false;
          } else {
            this.$message({
              type: 'error',
              message: response.data.data
            });
          }
        })
      }
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'remove'
        this.operateOrder(row)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.order = JSON.parse(JSON.stringify(row));
      this.orderFormVisible = true;
    },
    addFrom() {
      this.operate = 'save'
      this.order = {
        id: '',
        receivingAddress: '',
        userId: '',
      };
      this.orderFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadOrder() {
      this.$http.get("/order?page=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.orders = res.data.data.records;
              this.total = res.data.data.total;
              if (this.orders.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadOrder(this.current);
               }
            }
          })
    },
    // 格式化时间
    formatCreateTime(row, column, cellValue) {
      // 假设 cellValue 是时间戳（毫秒）
      const date = new Date(cellValue);
      return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadOrder(this.current);
    }
    ,
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadOrder(this.current);
    },
  },
  mounted() {
    this.loadOrder(this.current);
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
      <h1 style="position: absolute;margin-top: 0">订单管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加订单</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="orderFormVisible" title="订单">
      <el-form :model="order" label-width="auto" :rules="rules" ref="orderForm">
        <el-form-item label="订单 ID" prop="id">
          <el-input v-model.trim="order.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货地址 ID" prop="receivingAddress">
          <el-input v-model.trim="order.receivingAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户 ID" prop="userId">
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
          fixed
          label="ID"
          min-width="100px"
          prop="id">
      </el-table-column>
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
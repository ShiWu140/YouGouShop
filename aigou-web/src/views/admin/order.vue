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
        state: '',
        deliveryStatus: ''
      },
      // 表单和表格
      FormVisible: false,
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
    // 获取支付状态文本
    getPaymentStatusText(state) {
      switch (state) {
        case 0:
          return '未支付';
        case 1:
          return '已支付';
        default:
          return '未知状态';
      }
    },
    // 获取发货状态文本
    getDeliveryStatusText(status) {
      switch (status) {
        case 0:
          return '未发货';
        case 1:
          return '已发货';
        case 2:
          return '已收货';
        case 3:
          return '已完成';
        default:
          return '未知状态';
      }
    },
    // 获取状态标签类型
    getStatusType(status) {
      switch (status) {
        case 0:
          return 'warning';
        case 1:
          return 'success';
        case 2:
        case 3:
          return 'info';
        default:
          return 'info';
      }
    },
    // 发货操作
    handleDelivery(row) {
      this.$confirm('确认发货？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post('/order/updateDeliveryStatus', null, {
          params: {
            orderId: row.id,
            deliveryStatus: 1
          }
        }).then(response => {
          if (response.data) {
            this.$message({
              type: 'success',
              message: '发货成功！'
            });
            this.loadData();
          } else {
            this.$message.error('发货失败');
          }
        }).catch(() => {
          this.$message.error('发货失败');
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消发货'
        });
      });
    },
    operateOrder(order) {
      this.$http.post("/order/" + this.operate, order).then((response) => {
        if (response.status === 200) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadData();
          this.FormVisible = false;
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
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.order = {
        id: '',
        receivingAddress: '',
        userId: '',
      };
      this.FormVisible = true;
    },
    loadData() {
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
    <el-dialog :visible.sync="FormVisible" title="订单" @close="handleClose">
      <el-form :model="order" label-width="auto" :rules="rules" ref="orderForm">
        <el-form-item label="收货地址" prop="receivingAddress">
          <el-input v-model.trim="order.receivingAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model.trim="order.userId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateOrder(order)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="orders"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="订单号"
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
          label="支付状态"
          min-width="100px">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.state)">
            {{ getPaymentStatusText(scope.row.state) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          label="发货状态"
          min-width="100px">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.deliveryStatus)">
            {{ getDeliveryStatusText(scope.row.deliveryStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="250px">
        <template #default="scope">
          <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
              v-if="scope.row.state === 1 && scope.row.deliveryStatus === 0"
              size="mini"
              type="success"
              @click="handleDelivery(scope.row)">发货
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

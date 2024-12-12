<script>
export default {
  data() {
    return {
      orderProducts: [],
      orderProduct: {
        id: '',
        order_id: '',
        product_id: '',
        product_num: '',
      },
      orderProductFormVisible: false,
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
        order_id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        product_id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        product_num: [
          {required: true, message: '必填项', trigger: 'change'},
          {type: 'number', message: '必须为数字值', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateOrderProduct(orderProduct) {
      this.$http.post("/orderProduct?method=" + this.operate + "&" + this.$qs.stringify(orderProduct)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadOrderProduct(this.current);
          this.orderProductFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该订单商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateOrderProduct(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.orderProduct = JSON.parse(JSON.stringify(row));
      this.orderProductFormVisible = true;
    },
    addFrom() {
      this.operate = 'save';
      this.orderProduct = {
        id: '',
        order_id: '',
        product_id: '',
        product_num: '',
      };
      this.orderProductFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadOrderProduct(current) {
      current = this.current;
      this.$http.post("/orderProduct?method=page&current=" + current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.orderProducts = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.orderProducts.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadOrderProduct(this.current);
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadOrderProduct(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadOrderProduct(this.current);
    },
  },
  mounted() {
    this.loadOrderProduct(this.current);
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
      <h1 style="position: absolute;margin-top: 0">订单商品管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加订单商品</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="orderProductFormVisible" title="订单商品">
      <el-form :model="orderProduct" label-width="auto" :rules="rules" ref="orderProductForm">
        <el-form-item label="ID" prop="id">
          <el-input v-model.trim="orderProduct.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="订单 ID" prop="order_id">
          <el-input v-model.trim="orderProduct.order_id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品 ID" prop="product_id">
          <el-input v-model.trim="orderProduct.product_id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="product_num">
          <el-input v-model.trim.number="orderProduct.product_num" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="orderProductFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateOrderProduct(orderProduct)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="orderProducts"
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
          label="订单 ID"
          min-width="100px"
          prop="order_id">
      </el-table-column>
      <el-table-column
          label="商品 ID"
          min-width="100px"
          prop="product_id">
      </el-table-column>
      <el-table-column
          label="商品数量"
          min-width="100px"
          prop="product_num">
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




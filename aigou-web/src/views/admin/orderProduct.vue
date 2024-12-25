<script>
export default {
  data() {
    return {
      orderProducts: [],
      orderProduct: {
        id: '',
        orderId: '',
        productId: '',
        productNum: '',
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
        orderId: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productId: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productNum: [
          {required: true, message: '必填项', trigger: 'change'},
          {type: 'number', message: '必须为数字值', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateOrderProduct(orderProduct) {
      this.$http.post("/orderProduct/" + this.operate, orderProduct).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadData();
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
      this.operate = 'modify';
      this.orderProduct = JSON.parse(JSON.stringify(row));
      this.orderProductFormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.orderProduct = {
        id: '',
        orderId: '',
        productId: '',
        productNum: '',
      };
      this.orderProductFormVisible = true;
    },
    loadData() {
      ;
      this.$http.get("/orderProduct/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              console.log(res.data.data)
              this.orderProducts = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.orderProducts.length === 0 && this.current > 1) {
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
      <h1 style="position: absolute;margin-top: 0">订单商品管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加订单商品</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="orderProductFormVisible" title="订单商品">
      <el-form :model="orderProduct" label-width="auto" :rules="rules" ref="orderProductForm">
        <el-form-item label="订单 ID" prop="orderId">
          <el-input v-model.trim="orderProduct.orderId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品 ID" prop="productId">
          <el-input v-model.trim="orderProduct.productId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="productNum">
          <el-input v-model.trim.number="orderProduct.productNum" autocomplete="off"></el-input>
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
          label="订单 ID"
          min-width="100px"
          prop="orderId">
      </el-table-column>
      <el-table-column
          label="商品 ID"
          min-width="100px"
          prop="productId">
      </el-table-column>
      <el-table-column
          label="商品数量"
          min-width="100px"
          prop="productNum">
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




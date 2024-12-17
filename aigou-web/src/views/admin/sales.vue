<script>
export default {
  data() {
    return {
      saless: [],
      sales: {
        id: '',
        productId: '',
        salesNum: '',
      },
      salesFormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        productId: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        salesNum: [
          {required: true, message: '必填项', trigger: 'change'},
          {type: 'number', message: '必须为数字值', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    handleAdd() {
      this.operate = 'save';
      this.sales = {
        id: '',
        productId: '',
        salesNum: '',
      };
      this.salesFormVisible = true;
    },
    handleDelete(index, row) {
      this.operate = 'remove';
      this.sales = JSON.parse(JSON.stringify(row));
      this.operateApi();
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.sales = JSON.parse(JSON.stringify(row));
      this.salesFormVisible = true;
    },
    loadSales() {
      this.$http.get("/sales?page=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.saless = res.data.data.records;
              this.total = res.data.data.total;
              if (this.saless.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadSales(this.current);
              }
            }
          })
    },
    operateApi() {
      console.log('提交的数据', this.sales);
      if (this.operate === 'save') {
        this.$refs.salesForm.validate(valid => {
          if (valid) {
            // 表单验证通过，提交数据
            this.$http.post("/sales", this.sales)
                .then(res => {
                  if (res.data.code === 1) {
                    this.$message({
                      message: '添加成功',
                      type: 'success'
                    });
                    this.salesFormVisible = false;
                    this.loadSales();
                  } else {
                    this.$message({
                      message: '添加失败',
                      type: 'error'
                    });
                  }
                })
          } else {
            this.$message({
              message: '请正确填写内容！',
              type: 'warning'
            });
          }
        })
      }
      if (this.operate === 'update') {
        this.$refs.salesForm.validate(valid => {
          if (valid) {
            // 表单验证通过，提交数据
            this.$http.put("/sales", this.sales)
                .then(res => {
                  if (res.data.code === 1) {
                    this.$message({
                      message: '修改成功',
                      type: 'success'
                    });
                    this.salesFormVisible = false;
                    this.loadSales();
                  } else {
                    this.$message({
                      message: '修改失败',
                      type: 'error'
                    });
                  }
                })
          } else {
            this.$message({
              message: '请正确填写内容！',
              type: 'warning'
            });
          }
        })
      }
      if (this.operate === 'remove') {
        this.$http.delete("/sales/" + this.sales.id)
            .then(res => {
              if (res.data.code === 1) {
                this.$message({
                  message: '删除成功',
                  type: 'success'
                });
                this.salesFormVisible = false;
                this.loadSales();
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'error'
                });
              }
            })
      }
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },

    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadSales(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadSales(this.current);
    },
  },
  mounted() {
    this.loadSales();
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
      <h1 style="position: absolute;margin-top: 0">商品销量管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="handleAdd()">添加销量记录</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="salesFormVisible" title="添加销量记录">
      <el-form :model="sales" label-width="auto" :rules="rules" ref="salesForm">
        <!--        <el-form-item label="销量 ID" prop="id">-->
        <!--          <el-input v-model.trim="sales.id" :disabled="operate === 'update'" autocomplete="off"></el-input>-->
        <!--        </el-form-item>-->
        <el-form-item label="商品" prop="productId">
          <el-input v-model.trim="sales.productId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="售出数量" prop="salesNum">
          <el-input v-model.trim.number="sales.salesNum" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="salesFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateApi(sales)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="saless"
        :height="tableHeight"
        border
        style="width: 100%;">
      <!--      <el-table-column-->
      <!--          fixed-->
      <!--          label="ID"-->
      <!--          min-width="100px"-->
      <!--          prop="id">-->
      <!--      </el-table-column>-->
      <el-table-column
          label="商品"
          min-width="100px"
          prop="productId">
      </el-table-column>
      <el-table-column
          label="售出数量"
          min-width="100px"
          prop="salesNum">
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

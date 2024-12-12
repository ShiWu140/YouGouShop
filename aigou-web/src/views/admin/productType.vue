<script>
export default {
  data() {
    return {
      productTypes: [],
      productType: {
        id: '',
        product_type_name: '',
        product_type_desc: '',
        product_type_icon: '',
      },
      productTypeFormVisible: false,
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
        product_type_name: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        product_type_icon: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        product_type_desc: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateProductType(productType) {
      this.$http.post("/productType?method=" + this.operate + "&" + this.$qs.stringify(productType)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadProductType();
          this.productTypeFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该商品类型, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateProductType(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.productType = JSON.parse(JSON.stringify(row));
      this.productTypeFormVisible = true;
    },
    addFrom() {
      this.operate = 'save';
      this.productType = {
        id: '',
        product_type_name: '',
        product_type_desc: '',
        product_type_icon: '',
      };
      this.productTypeFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadProductType() {
      this.$http.post("/productType?method=page&current=" + this.current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.productTypes = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.productTypes.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadProductType();
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadProductType(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadProductType(this.current);
    },
  },
  mounted() {
    this.loadProductType();
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
      <h1 style="position: absolute;margin-top: 0">商品类型管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加类型</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="productTypeFormVisible" title="添加类型">
      <el-form :model="productType" label-width="auto" :rules="rules" ref="productType">
        <el-form-item label="类型 ID" prop="id">
          <el-input v-model.trim="productType.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类名称" prop="product_type_name">
          <el-input v-model.trim="productType.product_type_name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类描述" prop="product_type_desc">
          <el-input v-model.trim="productType.product_type_desc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类图标" prop="product_type_icon">
          <el-input v-model.trim="productType.product_type_icon" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="productTypeFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateProductType(productType)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="productTypes"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          fixed
          label="分类 ID"
          min-width="100px"
          prop="id">
      </el-table-column>
      <el-table-column
          label="分类名称"
          min-width="100px"
          prop="product_type_name">
      </el-table-column>
      <el-table-column
          label="分类描述"
          min-width="100px"
          prop="product_type_desc">
      </el-table-column>
      <el-table-column
          label="分类图标"
          min-width="100px"
          prop="product_type_icon">
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




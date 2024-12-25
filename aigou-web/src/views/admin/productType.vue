<script>
export default {
  data() {
    return {
      productTypes: [],
      productType: {
        id: '',
        productTypeName: '',
        productTypeDesc: '',
        productTypeIcon: '',
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
        productTypeName: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productTypeIcon: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productTypeDesc: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateProductType(productType) {
      this.$http.post("/productType/" + this.operate, productType).then((response) => {
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
      this.operate = 'modify';
      this.productType = JSON.parse(JSON.stringify(row));
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.productType = {
        id: '',
        productTypeName: '',
        productTypeDesc: '',
        productTypeIcon: '',
      };
      this.FormVisible = true;
    },
    loadData() {

      this.$http.get("/productType/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.productTypes = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.productTypes.length === 0 && this.current > 1) {
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
      <h1 style="position: absolute;margin-top: 0">商品类型管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加类型</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="FormVisible" title="添加类型">
      <el-form :model="productType" label-width="auto" :rules="rules" ref="productType">
        <el-form-item label="分类名称" prop="productTypeName">
          <el-input v-model.trim="productType.productTypeName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类描述" prop="productTypeDesc">
          <el-input v-model.trim="productType.productTypeDesc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分类图标" prop="productTypeIcon">
          <el-input v-model.trim="productType.productTypeIcon" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateProductType(productType)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="productTypes"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="分类名称"
          min-width="100px"
          prop="productTypeName">
      </el-table-column>
      <el-table-column
          label="分类描述"
          min-width="100px"
          prop="productTypeDesc">
      </el-table-column>
      <el-table-column
          label="分类图标"
          min-width="100px"
          prop="productTypeIcon">
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




<script>
export default {
  data() {
    return {
      brands: [],
      brand: {
        id: '',
        brandName: '',
        brandImg: '',
        brandType: '',
      },
      brandFormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //图片url地址
      imageUrl: '',
      //表单验证
      rules: {
        brandName: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        brandImg: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        brandType: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateBrand(brand) {
      // 调试输出提交的数据
      console.log('提交的数据', brand);
      this.$http.post("/brand/" + this.operate, brand).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadBrand();
          this.brandFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该品牌, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateBrand(row);  // 添加这一行以确保调用删除操作
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.brand = JSON.parse(JSON.stringify(row));
      this.imageUrl = this.brand.brandImg;
      this.brandFormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.brand = {
        id: '',
        brandName: '',
        brandImg: '',
        brandType: '',
      };
      // 清空图片 URL
      this.imageUrl = '';
      this.brandFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadBrand(current) {
      current = this.current
      this.$http.get("/brand/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.brands = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.brands.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadBrand();
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadBrand(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadBrand(this.current);
    },
    //图片上传方法
    handleAvatarSuccess(res, file) {
      console.log('upload', res.data)
      this.imageUrl = res.data
      this.brand.brandImg = res.data;
    }
  },
  mounted() {
    this.loadBrand();
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
      <h1 style="position: absolute;margin-top: 0">商品品牌管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加品牌</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="brandFormVisible" title="添加品牌">
      <el-form :model="brand" label-width="auto" :rules="rules" ref="brandForm">
        <el-form-item label="品牌名称" prop="brandName">
          <el-input v-model.trim="brand.brandName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="品牌图片" prop="brandImg">
          <el-input v-model.trim="this.imageUrl" autocomplete="off"></el-input>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8080/upload"
              accept=".jpg,.png"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="所属分类" prop="brandType">
          <el-input v-model.trim="brand.brandType" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="brandFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateBrand(brand)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="brands"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="品牌名称"
          min-width="100px"
          prop="brandName">
      </el-table-column>

      <el-table-column label="品牌图片" min-width="100px" prop="brandImg">
        <!-- 图片的显示 -->
        <template slot-scope="scope">
          <el-image
              :src="scope.row.brandImg"
              alt="品牌图片"
              min-width="100px"
              style="height: 100px"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
          label="所属分类"
          min-width="100px"
          prop="brandType">
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>


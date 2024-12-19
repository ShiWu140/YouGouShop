<script>
export default {
  name: "UploadView",
  data() {
    return {
      products: [],
      product: {
        id: '',
        productName: '',
        productImage: '',
        price: '',
        productType: '',
        productDesc: '',
        productBrand: '',
        createTime: '',
      },
      brands: [],
      productTypes: [],
      productFormVisible: false,
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
        id: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productName: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productImage: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        price: [
          {required: true, message: '必填项', trigger: 'change'},
          {type: 'number', message: '必须为数字值', trigger: 'change'}
        ],
        productType: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productDesc: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        productBrand: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateProduct(product) {
      // // 将 createTime 转换为时间戳（毫秒）
      // if (product.createTime instanceof Date) {
      //   product.createTime = product.createTime.getTime();
      // }
      // 调试输出提交的数据
      console.log('提交的数据', product);
      this.$refs.productForm.validate(valid => {
        if (valid) {
          //验证成功
          /*更新------------------------------------------*/

          if (this.operate == 'update') {
            console.log("发送更新请求update")
            this.$http.put("/product", product).then((res) => {
              console.log(res.data.code)
              this.loadProduct()
              this.productFormVisible = false;
            })
          }
          /*添加------------------------------------------*/
          if (this.operate == 'save') {
            console.log("发送添加请求insert")
            this.$http.post("/product", product).then((res) => {
              console.log(res.data.code)
              if (res.data.code == 1) {
                this.loadProduct()
                this.productFormVisible = false;
                this.$message({
                  type: 'success',
                  message: '操作成功!'
                });
              } else {
                this.$message({
                  type: 'error',
                  message: res.data.msg
                });
              }
            })
          }
        } else {
          this.$message({
            message: '请正确填写内容！',
            type: 'warning'
          });
        }
      });
      /*删除------------------------------------------*/
      if (this.operate == 'remove') {
        console.log("发送删除请求delete")
        this.$http.delete("/product/" + product.id).then((res) => {
          console.log(res.data.code)
          if (res.data.code == 1) {
            this.loadProduct()
            this.productFormVisible = false;
            this.$message({
              type: 'success',
              message: '操作成功!'
            });
          } else {
            this.$message({
              type: 'error',
              message: res.data.msg
            });
          }
        })
      }
      /*this.$http.post("/product?method=" + this.operate + "&" + this.$qs.stringify(product)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadProduct();
          this.productFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })*/
    },
    //删除
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'remove';
        this.operateProduct(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //编辑
    handleEdit(index, row) {
      this.operate = 'update';
      this.product = JSON.parse(JSON.stringify(row));
      this.imageUrl = this.product.productImage;
      this.productFormVisible = true;
    },
    //添加
    addFrom() {
      this.operate = 'save';
      this.product = {
        id: '',
        productName: '',
        productImage: '',
        price: '',
        productType: '',
        productDesc: '',
        productBrand: '',
        createTime: '',
      };
      // 清空图片 URL
      this.imageUrl = '';
      this.productFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadProduct() {
      this.$http.get("/product?page=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            //console.log(res.data);
            if (res.data.msg === "success") {
              this.products = res.data.data.rows;
              this.total = res.data.data.total;
              if (this.products.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadProduct(this.current);
              }
            }
          }),
          this.loadBrandsAndTypes();
    },
    loadBrandsAndTypes() {
      this.$http.get("/brand/all")
          .then(res => {
            //console.log("响应brand品牌"+res.data.data);
            if (res.data.msg === "success") {
              this.brands = res.data.data;
              console.log("响应brand品牌",this.brands);
            }
          })

      this.$http.get("/productType/all")
          .then(res => {
            //console.log("响应type分类",res.data.data);
            if (res.data.msg === "success") {
              this.productTypes = res.data.data;
              console.log("响应brand分类",this.productTypes);
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
      this.loadProduct(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadProduct(this.current);
    },
    //图片上传方法
    handleAvatarSuccess(res, file) {
      console.log('upload', res)
      this.imageUrl = res
      this.product.productImage = res;
    }
  },
  mounted() {
    this.loadProduct();
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
      <h1 style="position: absolute;margin-top: 0">商品管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">上架商品</el-button>
      </div>
    </div>
    <!--    新增-->
    <el-dialog :visible.sync="productFormVisible" title="上架商品">
      <el-form :model="product" label-width="auto" :rules="rules" ref="productForm">
        <!--        <el-form-item label="商品 ID" prop="id">
                  <el-input v-model.trim="product.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
                </el-form-item>-->
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model.trim="product.productName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="productImage">
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
        <el-form-item label="商品详情" prop="productDesc">
          <el-input v-model.trim="product.productDesc" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model.trim.number="product.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属分类" prop="productType">
          <el-select
              v-model.trim="product.productType"
              placeholder="请选择分类"
          >
            <el-option v-for="(prot,index) in productTypes" :key="index" :label="prot.productTypeName" :value="prot.id" />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="所属分类" prop="productType">
                  <el-input v-model.trim="product.productType" autocomplete="off"></el-input>
                </el-form-item>-->
        <el-form-item label="商品品牌" prop="productBrand">
<!--          <el-input v-model.trim="product.productBrand" autocomplete="off"></el-input>-->
          <el-select
              v-model.trim="product.productBrand"
              placeholder="请选择品牌"
          >
            <el-option v-for="(b,index) in brands" :key="index" :label="b.brandName" :value="b.id" />
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="创建时间">-->
        <!--          <el-date-picker type="datetime" placeholder="选择时间" v-model="product.createTime"></el-date-picker>-->
        <!--          &lt;!&ndash;          <el-input v-model.trim="" autocomplete="off"></el-input>&ndash;&gt;-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="productFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateProduct(product)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="products"
        :height="tableHeight"
        border
        style="width: 100%;">
      <!--      <el-table-column
                fixed
                label="商品 ID"
                min-width="100px"
                prop="id">
            </el-table-column>-->
      <el-table-column
          label="商品名称"
          min-width="100px"
          prop="productName">
      </el-table-column>
      <el-table-column label="商品图片" min-width="100px" prop="product_image">
        <!-- 图片的显示 -->
        <template slot-scope="scope">
          <el-image
              :src="scope.row.productImage"
              alt="商品图片"
              style="height: 100px"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
          label="商品详情"
          min-width="100px"
          prop="productDesc">
      </el-table-column>
      <el-table-column
          label="价格"
          min-width="100px"
          prop="price">
      </el-table-column>
      <el-table-column
          label="所属分类"
          min-width="100px"
          prop="productType">
      </el-table-column>
      <el-table-column
          label="商品品牌"
          min-width="100px"
          prop="productBrand">
      </el-table-column>
      <el-table-column
          label="创建时间"
          min-width="100px"
          :formatter="formatCreateTime"
          prop="createTime">
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

.auto-image {
  width: 100%;
  height: auto;
  object-fit: contain; /* 保持图片比例，避免图片被拉伸 */
}
</style>


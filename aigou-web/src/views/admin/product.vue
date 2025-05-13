<script>
export default {
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
      // 表单和表格
      FormVisible: false,
      // 分页属性
      total: 0,
      pageSize: 5,
      current: 1,
      operate: '',
      imageUrl: '', // 商品主图
      descImageUrls: [], // 商品介绍图片列表
      //表单验证
      rules: {
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
      console.log('提交的数据', product);
      this.$http.post("/product/" + this.operate, product).then((response) => {
        if (response) {
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
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateProduct(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.product = JSON.parse(JSON.stringify(row));
      this.imageUrl = this.product.productImage;
      this.descImageUrls = this.product.productDesc ? this.product.productDesc.split(',') : [];
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
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
      this.imageUrl = '';
      this.descImageUrls = [];
      this.FormVisible = true;
    },
    loadData() {
      this.$http.get("/product/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.products = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.products.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadData();
              }
            }
          })
    },
    //图片上传方法
    handleAvatarSuccess(res, file) {
      console.log('upload', res)
      this.imageUrl = res
      this.product.productImage = res
    },
    // 商品介绍图片上传成功
    handleDescImageSuccess(res, file) {
      if (res) {
        this.descImageUrls.push(res);
        this.product.productDesc = this.descImageUrls.join(',');
      }
    },
    // 商品介绍图片移除
    handleDescImageRemove(file) {
      const index = this.descImageUrls.indexOf(file.url);
      if (index !== -1) {
        this.descImageUrls.splice(index, 1);
        this.product.productDesc = this.descImageUrls.join(',');
      }
    },
    // 上传前检查
    beforeDescImageUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt5M = file.size / 1024 / 1024 < 5;

      if (!isImage) {
        this.$message.error('只能上传图片文件！');
        return false;
      }
      if (!isLt5M) {
        this.$message.error('图片大小不能超过 5MB！');
        return false;
      }
      return true;
    }
  },
  mounted() {
    this.loadTypes()
    this.loadBrands();
  },
  computed: {
    uploadHeaders() {
      const token = localStorage.getItem('token');
      return {
        Authorization: `${token}`,
      };
    }
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
    <el-dialog :visible.sync="FormVisible" title="上架商品">
      <el-form :model="product" label-width="auto" :rules="rules" ref="productForm">
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model.trim="product.productName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品图片" prop="productImage">
          <el-input v-model.trim="this.imageUrl" autocomplete="off"></el-input>
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8088/upload/fileupload"
              accept=".jpg,.png"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :headers="uploadHeaders">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品详情" prop="productDesc">
          <el-upload
            class="desc-image-uploader"
            action="http://localhost:8088/upload/fileupload"
            accept=".jpg,.png"
            list-type="picture-card"
            :on-success="handleDescImageSuccess"
            :on-remove="handleDescImageRemove"
            :before-upload="beforeDescImageUpload"
            :headers="uploadHeaders"
            :file-list="descImageUrls.map(url => ({url}))"
            multiple>
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input v-model.trim.number="product.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属分类" prop="productType">
          <el-select v-model="product.productType" placeholder="请选择分类">
            <el-option
                v-for="type in typeList"
                :key="type.id"
                :label="type.productTypeName"
                :value="type.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品品牌" prop="productBrand">
          <el-select v-model="product.productBrand" placeholder="请选择品牌">
            <el-option
                v-for="brand in brandList"
                :key="brand.id"
                :label="brand.brandName"
                :value="brand.id">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateProduct(product)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="products"
        :height="tableHeight"
        :fit="true"
        style="width: 100%;">
      <el-table-column
          label="商品名称"
          min-width="100px"
          prop="productName">
      </el-table-column>
      <el-table-column label="商品图片" min-width="100px" prop="productImage">
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
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.productDesc" placement="top">
            <div class="desc-cell">
              {{ scope.row.productDesc }}
            </div>
          </el-tooltip>
        </template>
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
        <template slot-scope="scope">
          {{ getTypeName(scope.row.productType) }}
        </template>
      </el-table-column>
      <el-table-column
          label="商品品牌"
          min-width="100px"
          prop="productBrand">
        <template slot-scope="scope">
          {{ getBrandName(scope.row.productBrand) }}
        </template>
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

.desc-image-uploader {
  margin-top: 10px;
}

.desc-image-uploader .el-upload--picture-card {
  width: 120px;
  height: 120px;
  line-height: 120px;
}

.desc-image-uploader .el-upload-list--picture-card .el-upload-list__item {
  width: 120px;
  height: 120px;
}

.el-upload__tip {
  color: #666;
  font-size: 12px;
  margin-top: 8px;
}

.desc-cell {
  max-height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  cursor: pointer;
}
</style>


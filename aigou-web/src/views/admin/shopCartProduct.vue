<script>
export default {
  data() {
    return {
      shopCarts: [],
      shopCart: {
        id: '',
        shopCartId: '',
        productId: '',
        productNum: '',
      },
      // 表单和表格
      FormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        shopCartId: [
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
    operateShopCartProduct(shopCart) {
      this.$http.post("/shopCartProduct/" + this.operate, shopCart).then((response) => {
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
      this.$confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateShopCartProduct(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.shopCart = JSON.parse(JSON.stringify(row));
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.shopCart = {
        id: '',
        shopCartId: '',
        productId: '',
        productNum: '',
      };
      this.FormVisible = true;
    },
    loadData() {
      this.$http.get("/shopCartProduct/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              console.log(res.data.data)
              this.shopCarts = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.shopCarts.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadData();
              }
            }
          })
    },
  },
  mounted() {
    this.loadProducts()
  }
}
</script>

<template>
  <div style="height: 100%">
    <div style="position: relative;">
      <h1 style="position: absolute;margin-top: 0">购物车商品管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">新增购物车商品</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="FormVisible" title="购物车商品">
      <el-form :model="shopCart" label-width="auto" :rules="rules" ref="shopCartForm">
        <el-form-item label="购物车 ID" prop="shopCartId">
          <el-input v-model.trim="shopCart.shopCartId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品" prop="productId">
          <el-select v-model="shopCart.productId" placeholder="请选择商品">
            <el-option
                v-for="type in productList"
                :key="type.id"
                :label="type.productName"
                :value="type.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品数量" prop="productNum">
          <el-input v-model.trim.number="shopCart.productNum" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateShopCartProduct(shopCart)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="shopCarts"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="购物车 ID"
          min-width="100px"
          prop="shopCartId">
      </el-table-column>
      <el-table-column
          label="商品"
          min-width="100px"
          prop="productId">
        <template slot-scope="scope">
          {{ getProductName(scope.row.productId) }}
        </template>
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




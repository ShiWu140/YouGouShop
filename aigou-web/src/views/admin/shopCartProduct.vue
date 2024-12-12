<script>
export default {
  data() {
    return {
      shopCarts: [],
      shopCart: {
        id: '',
        shop_cart_id: '',
        product_id: '',
        product_num: '',
      },
      shopCartFormVisible: false,
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
        shop_cart_id: [
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
    operateShopCartProduct(shopCart) {
      this.$http.post("/shopCartProduct?method=" + this.operate + "&" + this.$qs.stringify(shopCart)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadShopCart();
          this.shopCartFormVisible = false;
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
      this.operate = 'update';
      this.shopCart = JSON.parse(JSON.stringify(row));
      this.shopCartFormVisible = true;
    },
    addFrom() {
      this.operate = 'save';
      this.shopCart = {
        id: '',
        shop_cart_id: '',
        product_id: '',
        product_num: '',
      };
      this.shopCartFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadShopCart() {
      this.$http.post("/shopCartProduct?method=page&current=" + this.current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.shopCarts = res.data.data.records;
              this.total = res.data.data.total;
              if (this.shopCarts.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadShopCart(this.current);
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadShopCart(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadShopCart(this.current);
    },
  },
  mounted() {
    this.loadShopCart();
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
      <h1 style="position: absolute;margin-top: 0">购物车商品管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">新增购物车商品</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="shopCartFormVisible" title="购物车商品">
      <el-form :model="shopCart" label-width="auto" :rules="rules" ref="shopCartForm">
        <el-form-item label="ID" prop="id">
          <el-input v-model.trim="shopCart.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="购物车 ID" prop="shop_cart_id">
          <el-input v-model.trim="shopCart.shop_cart_id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品 ID" prop="product_id">
          <el-input v-model.trim="shopCart.product_id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="product_num">
          <el-input v-model.trim.number="shopCart.product_num" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="shopCartFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateShopCartProduct(shopCart)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="shopCarts"
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
          label="购物车 ID"
          min-width="100px"
          prop="shop_cart_id">
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




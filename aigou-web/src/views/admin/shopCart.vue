<script>
export default {
  data() {
    return {
      shopCarts: [],
      shopCart: {
        id: '',
        cartId: '',
        userId: '',
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
        cartId: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        userId: [
          {required: true, message: '必填项', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateShopCart(shopCart) {
      this.$http.post("/shopCart/" + this.operate, shopCart).then((response) => {
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
        this.operateShopCart(row);
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
        cartId: '',
        userId: '',
      };
      this.FormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadData() {

      this.$http.get("/shopCart/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.shopCarts = res.data.data.records;
              this.total = res.data.data.total;
              if (this.shopCarts.length === 0 && this.current > 1) {
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
      <h1 style="position: absolute;margin-top: 0">购物车管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">新增购物车</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="FormVisible" title="购物车">
      <el-form :model="shopCart" label-width="auto" :rules="rules" ref="shopCart">
        <el-form-item label="购物车 ID" prop="cartId">
          <el-input v-model.trim="shopCart.cartId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户 ID" prop="userId">
          <el-input v-model.trim="shopCart.userId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateShopCart(shopCart)">确 定</el-button>
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
          prop="cartId">
      </el-table-column>
      <el-table-column
          label="用户 ID"
          min-width="100px"
          prop="userId">
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

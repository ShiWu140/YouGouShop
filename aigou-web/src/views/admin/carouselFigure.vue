<script>
export default {
  data() {
    return {
      carouselFigures: [],
      carouselFigure: {
        id: '',
        url: '',
        sequence_num: '',
      },
      carouselFigureFormVisible: false,
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
        id: [{
          required: true, message: '必填项', trigger: 'change'
        }],
        url: [{
          required: true, message: '必填项', trigger: 'change'
        }],
        sequence_num: [{
          required: true, message: '必填项', trigger: 'change'
        }, {
          type: 'number', message: '必须为数字值', trigger: 'change'
        }]
      }
    }
  },
  methods: {
    operateCarouselFigure(carouselFigure) {
      // 调试输出提交的数据
      console.log('提交的数据', carouselFigure);
      this.$http.post("/carouselFigure?method=" + this.operate + "&" + this.$qs.stringify(carouselFigure)).then((response) => {
        if (response.data.msg === 'success') {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadCarouselFigure(this.current);
          this.carouselFigureFormVisible = false;
        } else {
          this.$message({
            type: 'error',
            message: response.data.data
          });
        }
      })
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该轮播图, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'remove';
        this.operateCarouselFigure(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'update';
      this.carouselFigure = JSON.parse(JSON.stringify(row));
      this.imageUrl = this.carouselFigure.url;
      this.carouselFigureFormVisible = true;
    },
    addFrom() {
      this.operate = 'save';
      this.carouselFigure = {
        id: '',
        url: '',
        sequence_num: '',
      };
      // 清空图片 URL
      this.imageUrl = '';
      this.carouselFigureFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadCarouselFigure(current) {
      current = this.current;
      this.$http.post("/carouselFigure?method=page&current=" + current + '&pagesize=' + this.pageSize)
          .then(res => {
            console.log(res.data);
            if (res.data.msg === "success") {
              this.carouselFigures = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.carouselFigures.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadCarouselFigure(this.current);
              }
            }
          })
    },
    handleSizeChange(val) {
      this.pageSize = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadCarouselFigure(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadCarouselFigure(this.current);
    },
    //图片上传方法
    handleAvatarSuccess(res, file) {
      console.log('upload', res.data)
      this.imageUrl = res.data
      this.carouselFigure.url = res.data;
    }
  },
  mounted() {
    this.loadCarouselFigure(this.current);
    // 窗口大小变化事件的监听器
    window.addEventListener('resize', this.calculateTableHeight);
  },
  beforeDestroy() {
    // 组件销毁前移除窗口大小变化事件的监听器
    window.removeEventListener('resize', this.calculateTableHeight);
  },
}
</script>

<template>
  <div style="height: 100%">
    <div style="position: relative;">
      <h1 style="position: absolute;margin-top: 0">轮播图管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加轮播图</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="carouselFigureFormVisible" title="轮播图">
      <el-form :model="carouselFigure" label-width="auto" :rules="rules" ref="carouselFigure">
        <el-form-item label="ID" prop="id">
          <el-input v-model.trim="carouselFigure.id" :disabled="operate === 'update'" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="轮播图" prop="url">
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
        <el-form-item label="轮播图序号" prop="sequence_num">
          <el-input v-model.trim.number="carouselFigure.sequence_num" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="carouselFigureFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateCarouselFigure(carouselFigure)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="carouselFigures"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          fixed
          label="ID"
          min-width="100px"
          prop="id">
      </el-table-column>
      <el-table-column label="轮播图" min-width="100px" prop="url">
        <!-- 图片的显示 -->
        <template slot-scope="scope">
          <el-image
              :src="scope.row.url"
              alt="轮播图"
              style="height: 100px"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column
          label="轮播图序号"
          min-width="100px"
          prop="sequence_num">
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




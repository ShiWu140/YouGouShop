<script>
export default {
  data() {
    return {
      carouselFigures: [],
      carouselFigure: {
        id: '',
        url: '',
        sequenceNum: '',
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
        url: [{
          required: true, message: '必填项', trigger: 'change'
        }],
        sequenceNum: [{
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
      this.$http.post("/carouselFigure/" + this.operate, carouselFigure).then((response) => {
        if (response.status === 200) {
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
      }).catch((error) => {
        console.error('There was an error!', error);
        this.$message({
          type: 'error',
          message: '请求失败，请重试!'
        });
      });
    }
    ,
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该轮播图, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateCarouselFigure(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.carouselFigure = JSON.parse(JSON.stringify(row));
      this.imageUrl = this.carouselFigure.url;
      this.FormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.carouselFigure = {
        id: '',
        url: '',
        sequenceNum: '',
      };
      // 清空图片 URL
      this.imageUrl = '';
      this.FormVisible = true;
    },
    loadData() {
      this.$http.get("/carouselFigure/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              console.log(res.data.data)
              this.carouselFigures = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.carouselFigures.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadData();
              }
            }
          })
    },
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
    <el-dialog :visible.sync="FormVisible" title="轮播图">
      <el-form :model="carouselFigure" label-width="auto" :rules="rules" ref="carouselFigure">
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
        <el-form-item label="轮播图序号" prop="sequenceNum">
          <el-input v-model.trim.number="carouselFigure.sequenceNum" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="FormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateCarouselFigure(carouselFigure)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="carouselFigures"
        :height="tableHeight"
        border
        style="width: 100%;">
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
          prop="sequenceNum">
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




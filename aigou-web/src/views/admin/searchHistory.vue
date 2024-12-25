<script>
export default {
  data() {
    return {
      searchHistories: [],
      searchHistory: {
        id: '',
        searchWords: '',
        num: '',
        searchTime: ''
      },
      searchHistoryFormVisible: false,
      tableHeight: window.innerHeight - 220,
      // 分页属性
      total: 0,
      pageSize: 10,
      current: 1,
      operate: '',
      //表单验证
      rules: {
        searchWords: [
          {required: true, message: '必填项', trigger: 'change'}
        ],
        num: [
          {required: true, message: '必填项', trigger: 'change'},
          {type: 'number', message: '必须为数字值', trigger: 'change'}
        ]
      }
    }
  },
  methods: {
    operateSearchHistory(searchHistory) {
      const {create_time, ...newSearchHistory} = searchHistory;
      this.$http.post("/searchHistory/" + this.operate, newSearchHistory).then((response) => {
        if (response.status === 200) {
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
          this.loadSearchHistory(this.current);
          this.searchHistoryFormVisible = false;
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
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该搜索历史, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.operate = 'delete';
        this.operateSearchHistory(row);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.operate = 'modify';
      this.searchHistory = JSON.parse(JSON.stringify(row));
      this.searchHistoryFormVisible = true;
    },
    addFrom() {
      this.operate = 'add';
      this.searchHistory = {
        id: '',
        searchWords: '',
        num: '',
        searchTime: ''
      };
      this.searchHistoryFormVisible = true;
    },
    calculateTableHeight() {
      // 动态计算表格高度，
      this.tableHeight = window.innerHeight - 220;
    },
    loadSearchHistory(current) {
      current = this.current;
      this.$http.get("/searchHistory/page?current=" + this.current + '&size=' + this.pageSize)
          .then(res => {
            if (res.data) {
              this.searchHistories = res.data.data.records;
              this.total = res.data.data.total;
              // 如果当前页没有数据且不是第一页，则跳转到上一页
              if (this.searchHistories.length === 0 && this.current > 1) {
                this.current -= 1;
                this.loadBrand();
              }
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
      this.loadSearchHistory(this.current);
    },
    handleCurrentChange(val) {
      this.current = val;
      console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
      this.loadSearchHistory(this.current);
    },
  },
  mounted() {
    this.loadSearchHistory(this.current);
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
      <h1 style="position: absolute;margin-top: 0">搜索历史管理</h1>
      <div style="text-align: right;">
        <el-button class="add-button" round type="primary" @click="addFrom()">添加搜索历史</el-button>
      </div>
    </div>
    <el-dialog :visible.sync="searchHistoryFormVisible" title="搜索历史">
      <el-form :model="searchHistory" label-width="auto" :rules="rules" ref="searchHistory">
        <el-form-item label="搜索关键词" prop="searchWords">
          <el-input v-model.trim="searchHistory.searchWords" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="搜索次数" prop="num">
          <el-input v-model.trim.number="searchHistory.num" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="searchHistoryFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="operateSearchHistory(searchHistory)">确 定</el-button>
      </div>
    </el-dialog>
    <el-table
        :data="searchHistories"
        :height="tableHeight"
        border
        style="width: 100%;">
      <el-table-column
          label="搜索关键词"
          min-width="100px"
          prop="searchWords">
      </el-table-column>
      <el-table-column
          label="搜索次数"
          min-width="100px"
          prop="num">
      </el-table-column>
      <el-table-column
          label="搜索时间"
          :formatter="formatCreateTime"
          min-width="100px"
          prop="searchTime">
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
          :current-page="current"
          :total="total"
          :page-sizes="[10,20,50,100]"
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




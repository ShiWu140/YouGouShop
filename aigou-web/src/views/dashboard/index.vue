<template>
  <div class="dashboard-container">
    <!-- 顶部数据卡片 -->
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="card-header">
            <span>今日订单</span>
          </div>
          <div class="card-content">
            <div class="number">142</div>
            <div class="compare">较昨日 +12.5%</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="card-header">
            <span>今日营收</span>
          </div>
          <div class="card-content">
            <div class="number">¥9,856</div>
            <div class="compare">较昨日 +18.3%</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="card-header">
            <span>待处理订单</span>
          </div>
          <div class="card-content">
            <div class="number">8</div>
            <div class="status">2笔立即处理</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="data-card">
          <div class="card-header">
            <span>需要发货</span>
          </div>
          <div class="card-content">
            <div class="number">5</div>
            <div class="status warning">需尽快处理</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <div class="card-header">
            <span>近日营收趋势</span>
            <div class="date-tabs">
              <el-radio-group v-model="dateType" size="small">
                <el-radio-button label="day">日</el-radio-button>
                <el-radio-button label="week">周</el-radio-button>
                <el-radio-button label="month">月</el-radio-button>
              </el-radio-group>
            </div>
          </div>
          <div ref="revenueChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-header">
            <span>订单状态统计</span>
          </div>
          <div ref="orderStatusChart" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近订单列表 -->
    <el-card shadow="hover" style="margin-top: 20px;">
      <div class="card-header" style="margin-bottom: 20px;">
        <span>最近订单</span>
        <el-button type="text" style="float: right;">查看全部</el-button>
      </div>
      <el-table :data="recentOrders" style="width: 100%">
        <el-table-column prop="orderId" label="订单号" width="180"></el-table-column>
        <el-table-column prop="product" label="商品" width="180"></el-table-column>
        <el-table-column prop="price" label="价格" width="100"></el-table-column>
        <el-table-column prop="time" label="时间" width="180"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="销量" width="100"></el-table-column>
        <el-table-column prop="amount" label="销售额" width="120"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'Dashboard',
  data() {
    return {
      dateType: 'day',
      revenueChart: null,
      orderStatusChart: null,
      recentOrders: [
        {
          orderId: 'ORD-2023071501',
          product: 'B05',
          price: '¥158',
          time: '12:45',
          status: '已完成',
          quantity: '86份',
          amount: '¥2,580'
        },
        {
          orderId: 'ORD-2023071502',
          product: 'B05',
          price: '¥158',
          time: '12:45',
          status: '制作中',
          quantity: '72份',
          amount: '¥1,728'
        },
        {
          orderId: 'ORD-2023071503',
          product: 'C06',
          price: '¥342',
          time: '13:10',
          status: '已取消',
          quantity: '65份',
          amount: '¥2,275'
        }
      ]
    }
  },
  mounted() {
    this.initRevenueChart()
    this.initOrderStatusChart()
  },
  methods: {
    initRevenueChart() {
      this.revenueChart = echarts.init(this.$refs.revenueChart)
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['7天前', '6天前', '5天前', '4天前', '3天前', '昨天', '今天']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [5000, 5800, 6000, 7200, 8000, 7500, 9000],
          type: 'line',
          areaStyle: {
            opacity: 0.2
          },
          smooth: true
        }]
      }
      this.revenueChart.setOption(option)
    },
    initOrderStatusChart() {
      this.orderStatusChart = echarts.init(this.$refs.orderStatusChart)
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          right: 10,
          top: 'center'
        },
        series: [
          {
            name: '订单状态',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 60, name: '已完成' },
              { value: 15, name: '进行中' },
              { value: 20, name: '待处理' },
              { value: 5, name: '已取消' }
            ]
          }
        ]
      }
      this.orderStatusChart.setOption(option)
    },
    getStatusType(status) {
      const statusMap = {
        '已完成': 'success',
        '制作中': 'warning',
        '已取消': 'danger',
        '待处理': 'info'
      }
      return statusMap[status]
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.data-card {
  height: 120px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.card-content {
  text-align: center;
}

.number {
  font-size: 24px;
  font-weight: bold;
  margin: 10px 0;
}

.compare {
  font-size: 12px;
  color: #67C23A;
}

.status {
  font-size: 12px;
  color: #909399;
}

.status.warning {
  color: #E6A23C;
}

.date-tabs {
  margin-right: 20px;
}
</style> 
<script>
import * as echarts from 'echarts'
import axios from 'axios'

export default {
  name: 'Dashboard',
  data() {
    return {
      loading: false,
      tableLoading: false,
      userName: localStorage.getItem('userName'),
      dateType: 'day',
      revenueChart: null,
      orderStatusChart: null,
      statsTimer: null,
      themeColors: {
        success: '#67C23A',    // 绿色 - 已完成/成功
        warning: '#E6A23C',    // 橙色 - 进行中/警告
        primary: '#409EFF',    // 蓝色 - 待处理
        danger: '#F56C6C',     // 红色 - 已取消/错误
      },
      cardData: [],
      recentOrders: []
    }
  },
  mounted() {
    this.fetchDashboardStats()
    this.initRevenueChart()
    this.initOrderStatusChart()
    
    // 监听窗口大小变化，重绘图表
    window.addEventListener('resize', this.handleResize)
    
    // 设置定时刷新
    this.statsTimer = setInterval(this.fetchDashboardStats, 60000) // 每分钟刷新一次
  },
  beforeDestroy() {
    // 清理定时器和事件监听
    if (this.statsTimer) {
      clearInterval(this.statsTimer)
    }
    // 移除事件监听
    window.removeEventListener('resize', this.handleResize)
    // 销毁图表实例
    if (this.revenueChart) {
      this.revenueChart.dispose()
    }
    if (this.orderStatusChart) {
      this.orderStatusChart.dispose()
    }
  },
  methods: {
    handleResize() {
      if (this.revenueChart) {
        this.revenueChart.resize()
      }
      if (this.orderStatusChart) {
        this.orderStatusChart.resize()
      }
    },
    initRevenueChart() {
      this.revenueChart = echarts.init(this.$refs.revenueChart)
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: '{b}<br/>{a}: ¥{c}'
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
          type: 'value',
          axisLabel: {
            formatter: '¥{value}'
          }
        },
        series: [{
          name: '营收',
          data: [5000, 5800, 6000, 7200, 8000, 7500, 9000],
          type: 'line',
          areaStyle: {
            opacity: 0.2
          },
          smooth: true,
          itemStyle: {
            color: this.themeColors.primary
          },
          areaStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: this.themeColors.primary },
              { offset: 1, color: '#fff' }
            ])
          }
        }]
      }
      this.revenueChart.setOption(option)
    },
    initOrderStatusChart() {
      this.orderStatusChart = echarts.init(this.$refs.orderStatusChart)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} ({d}%)'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '订单状态',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 28,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { 
                value: 0, 
                name: '已完成',
                itemStyle: { 
                  color: this.themeColors.success,
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                }
              },
              { 
                value: 0, 
                name: '进行中',
                itemStyle: { 
                  color: this.themeColors.warning,
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                }
              },
              { 
                value: 0, 
                name: '待处理',
                itemStyle: { 
                  color: this.themeColors.primary,
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                }
              },
              { 
                value: 0, 
                name: '已取消',
                itemStyle: { 
                  color: this.themeColors.danger,
                  borderRadius: 10,
                  borderColor: '#fff',
                  borderWidth: 2
                }
              }
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
    },
    async fetchDashboardStats() {
      try {
        this.loading = true
        const response = await axios.get('/dashboard/stats')
        const data = response.data.data || response.data // 兼容两种返回格式
        
        // 更新卡片数据
        this.cardData = [
          {
            title: '今日订单',
            number: data.todayOrders.count,
            compare: `较昨日 ${data.todayOrders.increase > 0 ? '+' : ''}${data.todayOrders.increase}%`,
            trend: data.todayOrders.increase >= 0 ? 'up' : 'down',
            type: 'success'
          },
          {
            title: '今日营收',
            number: `¥${data.todayRevenue.amount.toLocaleString()}`,
            compare: `较昨日 ${data.todayRevenue.increase > 0 ? '+' : ''}${data.todayRevenue.increase}%`,
            trend: data.todayRevenue.increase >= 0 ? 'up' : 'down',
            type: 'primary'
          },
          {
            title: '待处理订单',
            number: data.pendingOrders.count,
            statusText: `${data.pendingOrders.urgentCount}笔立即处理`,
            type: 'warning'
          },
          {
            title: '需要发货',
            number: data.toBeShipped.count,
            statusText: '需尽快处理',
            status: 'warning',
            type: 'danger'
          }
        ]

        // 更新营收趋势图数据
        if (this.revenueChart) {
          const option = {
            tooltip: {
              trigger: 'axis',
              formatter: '{b}<br/>{a}: ¥{c}'
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
              data: data.revenueData.map(item => item.date)
            },
            yAxis: {
              type: 'value',
              axisLabel: {
                formatter: '¥{value}'
              }
            },
            series: [{
              name: '营收',
              data: data.revenueData.map(item => item.revenue),
              type: 'line',
              areaStyle: {
                opacity: 0.2
              },
              smooth: true,
              itemStyle: {
                color: this.themeColors.primary
              },
              areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: this.themeColors.primary },
                  { offset: 1, color: '#fff' }
                ])
              }
            }]
          }
          this.revenueChart.setOption(option)
        }

        // 更新订单状态统计图数据
        if (this.orderStatusChart) {
          const orderStatusData = data.orderStatusStats
          const option = {
            series: [{
              data: [
                { 
                  value: orderStatusData['已完成'] || 0, 
                  name: '已完成',
                  itemStyle: { 
                    color: this.themeColors.success,
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                  }
                },
                { 
                  value: orderStatusData['进行中'] || 0, 
                  name: '进行中',
                  itemStyle: { 
                    color: this.themeColors.warning,
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                  }
                },
                { 
                  value: orderStatusData['待处理'] || 0, 
                  name: '待处理',
                  itemStyle: { 
                    color: this.themeColors.primary,
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                  }
                },
                { 
                  value: orderStatusData['已取消'] || 0, 
                  name: '已取消',
                  itemStyle: { 
                    color: this.themeColors.danger,
                    borderRadius: 10,
                    borderColor: '#fff',
                    borderWidth: 2
                  }
                }
              ]
            }]
          }
          this.orderStatusChart.setOption(option)
        }
      } catch (error) {
        console.error('获取仪表盘数据失败:', error)
        this.$message.error('获取数据失败，请稍后重试')
      } finally {
        this.loading = false
      }
    },
    handleDateTypeChange(type) {
      // TODO: 根据选择的日期类型获取对应的数据
      this.initRevenueChart()
    },
    viewAllOrders() {
      this.$router.push('/admin/order')
    }
  }
}
</script>

<template>
  <div class="dashboard-container">
    <!-- 顶部数据卡片 -->
    <el-row :gutter="20" v-loading="loading">
      <el-col :span="6" v-for="(item, index) in cardData" :key="index">
        <el-card shadow="hover" :class="['data-card', item.type]">
          <div class="card-title">{{ item.title }}</div>
          <div class="card-content">
            <div class="number">{{ item.number }}</div>
            <div :class="['compare', item.trend]" v-if="item.compare">{{ item.compare }}</div>
            <div :class="['status', item.status]" v-if="item.statusText">{{ item.statusText }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <div class="section-header">
            <span class="section-title">近日营收趋势</span>
            <div class="date-tabs">
              <el-radio-group v-model="dateType" size="small" @change="handleDateTypeChange">
                <el-radio-button label="day">日</el-radio-button>
                <el-radio-button label="week">周</el-radio-button>
                <el-radio-button label="month">月</el-radio-button>
              </el-radio-group>
            </div>
          </div>
          <div ref="revenueChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="section-header">
            <span class="section-title">订单状态统计</span>
          </div>
          <div ref="orderStatusChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近订单列表 -->
    <el-card shadow="hover" style="margin-top: 20px;">
      <div class="section-header">
        <span class="section-title">最近订单</span>
        <el-button type="text" @click="viewAllOrders">查看全部</el-button>
    </div>
      <el-table :data="recentOrders" style="width: 100%" v-loading="tableLoading">
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

<style scoped>
.dashboard-container {
  padding: 20px;
}

.data-card {
  height: 120px;
  background: #fff;
  border-radius: 4px;
  transition: all 0.3s;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
  border-top: 3px solid transparent;
}

.data-card:hover {
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  transform: translateY(-2px);
}

.data-card.success {
  border-top-color: #67C23A;
}

.data-card.warning {
  border-top-color: #E6A23C;
}

.data-card.primary {
  border-top-color: #409EFF;
}

.data-card.danger {
  border-top-color: #F56C6C;
}

.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
  text-align: center;
}

.card-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.number {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin: 8px 0;
  line-height: 1;
}

.compare {
  font-size: 12px;
  color: #67C23A;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
}

.compare.up::before {
  content: "↑";
}

.compare.down {
  color: #F56C6C;
}

.compare.down::before {
  content: "↓";
}

.status {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  text-align: center;
}

.status.warning {
  color: #E6A23C;
}

.section-header {
  padding: 15px 20px;
  border-bottom: 1px solid #ebeef5;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-title {
  font-size: 16px;
  color: #303133;
  font-weight: 500;
}

.date-tabs {
  margin-right: 20px;
}

.chart-container {
  padding: 20px;
  height: 350px;
}
</style>




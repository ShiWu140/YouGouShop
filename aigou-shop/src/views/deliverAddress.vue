<script>
import { deliverAddressApi } from '@/api/deliverAddress';

export default {
  data() {
    return {
      loading: false,
      ReceivingAddress: {
        id: '',
        receivingAddress: '',
        receivingPerson: '',
        mobilePhone: '',
        userId: '',
        isDefault: 0
      },
      ReceivingAddresses: [],
      rules: {
        receivingAddress: [
          {required: true, message: '请输入详细地址', trigger: 'blur'},
          {min: 5, max: 100, message: '地址长度在5到100个字符之间', trigger: 'blur'}
        ],
        receivingPerson: [
          {required: true, message: '请输入收货人姓名', trigger: 'blur'},
          {min: 2, max: 20, message: '姓名长度在2到20个字符之间', trigger: 'blur'}
        ],
        mobilePhone: [
          {required: true, message: '请输入手机号码', trigger: 'blur'},
          {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    // 加载收货地址
    async loadingReceivingAddress() {
      const userId = localStorage.getItem('userId');
      console.log('当前用户ID:', userId);
      
      if (!userId) {
        console.log('用户未登录');
        this.$message.warning('请先登录');
        return;
      }

      this.loading = true;
      console.log('开始请求收货地址数据...');
      try {
        const addresses = await deliverAddressApi.getReceivingAddresses(userId);
        console.log('获取收货地址成功，数据:', addresses);
        this.ReceivingAddresses = addresses;
      } catch (error) {
        console.error('获取收货地址请求失败:', error);
        console.error('错误详情:', {
          message: error.message,
          response: error.response,
          request: error.request,
          config: error.config
        });
        this.$message.error('获取收货地址失败，请稍后重试');
      } finally {
        this.loading = false;
      }
    },
    // 验证表单
    validateForm() {
      return new Promise((resolve, reject) => {
        if (!this.ReceivingAddress.receivingAddress) {
          this.$message.error('请输入详细地址');
          reject(new Error('请输入详细地址'));
        }
        if (!this.ReceivingAddress.receivingPerson) {
          this.$message.error('请输入收货人姓名');
          reject(new Error('请输入收货人姓名'));
        }
        if (!this.ReceivingAddress.mobilePhone) {
          this.$message.error('请输入手机号码');
          reject(new Error('请输入手机号码'));
        }
        if (!/^1[3-9]\d{9}$/.test(this.ReceivingAddress.mobilePhone)) {
          this.$message.error('请输入正确的手机号码');
          reject(new Error('请输入正确的手机号码'));
        }
        resolve();
      });
    },
    // 添加或修改地址
    async addAddress(flag) {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          this.$message.error('请先登录');
          return;
        }

        await this.validateForm();
        this.ReceivingAddress.userId = userId;

        if (flag) {
          await deliverAddressApi.modifyReceivingAddress(this.ReceivingAddress);
          this.$message.success('修改成功');
        } else {
          this.ReceivingAddress.isDefault = 0;
          await deliverAddressApi.addReceivingAddress(this.ReceivingAddress);
          this.$message.success('添加成功');
        }

        this.resetForm();
        this.loadingReceivingAddress();
      } catch (error) {
        console.error('操作失败:', error);
        this.$message.error(error.message || '操作失败');
      }
    },
    // 删除地址
    async deleteAddress(id) {
      try {
        await this.$confirm('确认删除该地址吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        });
        
        await deliverAddressApi.deleteReceivingAddress(id);
        this.$message.success('删除成功');
        this.resetForm();
        this.loadingReceivingAddress();
      } catch (error) {
        if (error !== 'cancel') {
          console.error('删除失败:', error);
          this.$message.error('删除失败，请稍后重试');
        }
      }
    },
    // 更新地址
    updateAddress(item) {
      this.ReceivingAddress = {
        id: item.id,
        receivingAddress: item.receivingAddress,
        receivingPerson: item.receivingPerson,
        mobilePhone: item.mobilePhone,
        userId: item.userId,
        isDefault: item.isDefault
      };
    },
    // 重置表单
    resetForm() {
      this.ReceivingAddress = {
        id: '',
        receivingAddress: '',
        receivingPerson: '',
        mobilePhone: '',
        userId: '',
        isDefault: 0
      };
    },

  },
  mounted() {
    this.loadingReceivingAddress();
  }
}

</script>
<template>
  <Header/>
  <div>
    <div class="w1230">
      <img src="@/assets/img/logo.png" width="100" height="40" class="logo"/>
      <span class="cart">收货地址</span>
    </div>
    <!--编辑收货地址-->
    <div class="w1230">
      <h3 class="add-title">新增收货地址</h3>
      <div class="add-address">
        <el-form :model="ReceivingAddress" :rules="rules" ref="addressForm" label-width="100px">
          <el-form-item label="详细地址" prop="receivingAddress">
            <el-input
                type="textarea"
                v-model="ReceivingAddress.receivingAddress"
                placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息"
                :rows="3"
            ></el-input>
          </el-form-item>
          <el-form-item label="收货人姓名" prop="receivingPerson">
            <el-input
                v-model="ReceivingAddress.receivingPerson"
                placeholder="请输入收货人姓名"
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop="mobilePhone">
            <el-input
                v-model="ReceivingAddress.mobilePhone"
                placeholder="请输入收货人手机号"
            ></el-input>
          </el-form-item>
          <el-form-item label="默认地址">
            <el-switch
                v-model="ReceivingAddress.isDefault"
                :active-value="1"
                :inactive-value="0"
                active-text="是"
                inactive-text="否"
            ></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="addAddress(ReceivingAddress.id)">保存</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <!--收货地址列表-->
    <div class="add-list w1230">
      <el-table 
        v-loading="loading"
        :data="ReceivingAddresses" 
        border 
        style="width: 80%"
      >
        <el-table-column prop="receivingPerson" label="收货人" width="80"/>
        <el-table-column prop="receivingAddress" label="收货地址"/>
        <el-table-column prop="mobilePhone" label="手机号" width="150"/>
        <el-table-column label="默认地址" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isDefault === 1 ? 'success' : 'info'">
              {{ scope.row.isDefault === 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <el-button type="primary" link @click="updateAddress(scope.row)">修改</el-button>
            <el-button type="danger" link @click="deleteAddress(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
  <Footer/>
</template>

<style scoped>
@import "@/assets/css/deliverAddress.css";
</style>

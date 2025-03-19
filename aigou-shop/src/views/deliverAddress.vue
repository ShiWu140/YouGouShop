<script>

export default {
  data() {
    return {
      ReceivingAddress: {
        id: '',
        receivingAddress: '',
        receivingPerson: '',
        mobilePhone: '',
        userId: '',
        isDefault: ''
      },
      ReceivingAddresses: []

    }

  },
  methods: {
    loadingReceivingAddress() {
      this.$http.get("/receivingAddress/all").then((response) => {
        console.log('查询数据', response.data)
        if (response.data.code == 1) {
          this.ReceivingAddresses = response.data.data;
          console.log(this.ReceivingAddresses)
          /*if (response.data.data.length == 0) {
            this.msg = "当前没有数据,请添加后重试"
          } else {
            this.msg = ''
          }*/
        } else {

        }
      })
    },
    addAddress(flag) {
      if (flag != '') {
        //说明是修改
        this.$http.post("/receivingAddress/modify", this.ReceivingAddress).then((response) => {
          console.log("xxx")
          if (response.data.code == 1) {
            console.log('修改成功', response.data)
            this.ReceivingAddress.id = '';
            this.ReceivingAddress.receivingAddress = '';
            this.ReceivingAddress.receivingPerson = '';
            this.ReceivingAddress.mobilePhone = '';
            this.ReceivingAddress.isDefault = '';
            this.loadingReceivingAddress();
          }
        })
      } else {
        //新增
        this.ReceivingAddress.isDefault = 0
        this.$http.post("/receivingAddress/add", this.ReceivingAddress).then((response) => {
          if (response.data.code == 1) {
            console.log('添加成功', response.data)
            this.ReceivingAddress.receivingAddress = '';
            this.ReceivingAddress.receivingPerson = '';
            this.ReceivingAddress.mobilePhone = '';
            this.ReceivingAddress.isDefault = '';
            this.loadingReceivingAddress();
          }
        })
      }

    },
    deleteAddress(id) {
      this.ReceivingAddress.id = id
      this.$http.post("/receivingAddress/delete", this.ReceivingAddress).then((response) => {
        if (response.data.code == 1) {
          console.log('删除成功', response.data)
          this.ReceivingAddress.receivingAddress = '';
          this.ReceivingAddress.receivingPerson = '';
          this.ReceivingAddress.mobilePhone = '';
          this.ReceivingAddress.isDefault = '';
          this.loadingReceivingAddress();
        }
      })
    },
    updateAddress(item) {
      this.ReceivingAddress.id = item.id
      this.ReceivingAddress.receivingAddress = item.receivingAddress
      this.ReceivingAddress.receivingPerson = item.receivingPerson
      this.ReceivingAddress.mobilePhone = item.mobilePhone
    },
    /*statusAddress(status){
      this.ReceivingAddress.isDefault = status
    }*/
  },
  mounted() {
    this.loadingReceivingAddress();
  }
}

</script>
<template>
  <Header />
  <div>
    <div class="w1230">
      <img src="@/assets/img/logo.png" width="100" height="40" class="logo" />
      <span class="cart">收货地址</span>
    </div>
    <!--编辑收货地址-->
    <div class="w1230">
      <h3 class="add-title">新增收货地址</h3>
      <div class="add-address">
        <form action="#" method="post">
          <p>
            <label>详细地址：</label>
            <textarea placeholder="请输入详细地址信息，如道路、门牌号、小区、楼栋号、单元等信息" class="add-detail"
                      v-model="ReceivingAddress.receivingAddress"></textarea>
          </p>
          <p>
            <label>收货人姓名：</label>
            <input v-model="ReceivingAddress.receivingPerson" type="text" class="add-name"
                   placeholder="请输入收货人姓名" />
          </p>
          <p>
            <label>手机号码：</label>
            <input v-model="ReceivingAddress.mobilePhone" type="text" class="add-tel"
                   placeholder="请输入收货人手机号" />
          </p>
          <p class="chk-address">
            <input type="checkbox" v-model="ReceivingAddress.isDefault" />
            <span>设置为默认收货地址</span>
          </p>
          <input @click="addAddress(ReceivingAddress.id)" type="button" value="保存" class="save-btn" />
        </form>
      </div>
    </div>
    <!--收货地址列表-->
    <div class="add-list w1230">
<!--      <p class="message"><i class="fa fa-exclamation-circle"></i>只能保存5个地址</p>-->
      <el-table :data="ReceivingAddresses" border style="width: 80%">
        <el-table-column prop="receivingPerson" label="收货人" width="80" />
        <el-table-column prop="receivingAddress" label="收货地址" />
        <el-table-column prop="mobilePhone" label="手机号" width="150" />
        <el-table-column label="操作" width="180">
          <template #default="scope">
            <a href="#" @click.prevent="updateAddress(scope.row)">修改</a> |
            <a href="#" @click.prevent="deleteAddress(scope.row.id)">删除</a>
          </template>
        </el-table-column>
        <el-table-column label="默认" width="140">
          <template #default="scope">
            <span v-if="scope.row.isDefault == 0">
              <a href="#" @click.prevent="statusAddress(scope.row)">设为默认地址</a>
            </span>
            <span v-if="scope.row.isDefault == 1">默认收货地址</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <Footer />
  </div>
</template>

<style scoped>
@import "@/assets/css/deliverAddress.css";
</style>

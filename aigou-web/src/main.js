import Vue from 'vue';
import ElementUI, {Message} from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import router from './router';
import axios from "axios";
import qs from 'qs';

Vue.config.productionTip = false;
Vue.prototype.$qs = qs;
Vue.prototype.$http = axios;
Vue.prototype.$http.defaults.baseURL = 'http://localhost:8080';
// Vue.prototype.$http.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//请求拦截器
Vue.prototype.$http.interceptors.request.use(
    req => {
        //读取localstoge中的token
        let token = localStorage.getItem("token");
        if (token) {
            //把localStorage的token放在Authorization里
            req.headers.Authorization = token;
        }
        return req;
    },
    err => {
        Message.error('请求配置错误');
        return Promise.reject(err);
    }
);

//响应拦截器
Vue.prototype.$http.interceptors.response.use(
    resp => {
        return resp;
    },
    err => {
        if (err.response) {
            switch (err.response.status) {
                case 401:
                    // 清除本地存储的token和用户信息
                    localStorage.removeItem('token');
                    localStorage.removeItem('userId');
                    localStorage.removeItem('username');
                    // 显示错误消息
                    Message.error({
                        message: '登录已过期，请重新登录',
                        duration: 2000,
                        onClose: () => {
                            // 跳转到登录页
                            router.push('/login');
                        }
                    });
                    break;
                case 403:
                    Message.error({
                        message: '没有权限访问该资源',
                        duration: 2000
                    });
                    break;
                case 404:
                    Message.error({
                        message: '请求的资源不存在',
                        duration: 2000
                    });
                    break;
                case 500:
                    Message.error({
                        message: '服务器错误，请稍后重试',
                        duration: 2000
                    });
                    break;
                default:
                    Message.error({
                        message: '请求失败，请稍后重试',
                        duration: 2000
                    });
            }
        } else if (err.request) {
            Message.error({
                message: '网络错误，请检查网络连接',
                duration: 2000
            });
        } else {
            Message.error({
                message: '请求配置错误',
                duration: 2000
            });
        }
        return Promise.reject(err);
    }
);
Vue.mixin({
    data() {
        return {
            //图片url地址
            imageUrl: '',
            typeList: [],
            brandList: [],
            productList: [],
            tableHeight: window.innerHeight - 220,
        }
    },
    //公共方法
    methods: {
        // 格式化时间
        formatCreateTime(row, column, cellValue) {
            // 假设 cellValue 是时间戳（毫秒）
            const date = new Date(cellValue);
            return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
        },
        calculateTableHeight() {
            // 动态计算表格高度，
            this.tableHeight = window.innerHeight - 220;
        },
        handleSizeChange(val) {
            this.pageSize = val;
            console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
            this.loadData(this.current);
        },
        handleCurrentChange(val) {
            this.current = val;
            console.log("分页大小：" + this.pageSize + "、当前页" + this.current);
            this.loadData(this.current);
        },
        loadData() {
        },
        loadTypes() {
            this.$http.get("/productType/all")
                .then(response => {
                    if (response.data.msg === "success") {
                        this.typeList = response.data.data;
                    }
                })
                .catch(error => {
                    console.error("获取分类数据失败", error);
                });
        },
        getTypeName(typeId) {
            const type = this.typeList.find(type => type.id === typeId);
            return type ? type.productTypeName : '';
        },
        loadBrands() {
            this.$http.get("/brand/all")
                .then(response => {
                    if (response.data.msg === "success") {
                        this.brandList = response.data.data;
                    }
                })
                .catch(error => {
                    console.error("获取品牌数据失败", error);
                });
        },
        getBrandName(brandId) {
            const brand = this.brandList.find(brand => brand.id === brandId);
            return brand ? brand.brandName : '';
        },
        loadProducts() {
            this.$http.get("/product/all")
                .then(res => {
                    if (res.data.msg === "success") {
                        this.productList = res.data.data;
                    }
                })
                .catch(error => {
                console.error("获取商品数据失败", error);
            });
        },
        getProductName(productId){
            const product = this.productList.find(product => product.id === productId);
            return product ? product.productName : '';
        }
    },
    mounted() {
        this.loadData();
        window.addEventListener('resize', this.calculateTableHeight);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.calculateTableHeight);
    },
});
Vue.use(ElementUI);
new Vue({
    router, render: function (h) {
        return h(App)
    }
}).$mount('#app')

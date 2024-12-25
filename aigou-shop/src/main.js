import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus, {ElMessage} from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 创建 Vue 应用
const app = createApp(App)
    .use(router)
    .use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// 配置生产提示
app.config.productionTip = false;
// 挂载全局属性
app.config.globalProperties.$http = axios;
app.config.globalProperties.$http.defaults.baseURL = 'http://127.0.0.1:8080';

//请求拦截器
app.config.globalProperties.$http.interceptors.request.use(
    req => {
        //读取localstoge中的token
        let token = localStorage.getItem("token");
        console.log("拦截请求数据--->" + token);
        if (token) {
            //把localStorage的token放在Authorization里
            req.headers.Authorization = token;
        }
        return req;
    },
    err => {
        console.log("失败信息" + err);
    }
);

//响应拦截器
app.config.globalProperties.$http.interceptors.response.use(
    resp => {
        console.log('响应数据拦截--->', resp)
        if (resp.data.data.code === 0) {
            ElMessage.error({message: '没有登录，请重新登录'})
            location.href = '/login'
        }
        return resp;
    },
    err => {
        console.log("失败信息" + err);
    }
);


app.mount('#app')

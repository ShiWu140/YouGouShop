import {createApp, onMounted} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus, {ElMessage} from 'element-plus'
import 'element-plus/dist/index.css'
import axios from "axios";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import Header from '@/components/Header.vue'
import Footer from '@/components/Footer.vue'


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
app.config.globalProperties.$http.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;

//请求拦截器
app.config.globalProperties.$http.interceptors.request.use(req => {
        //读取localstoge中的token
        let token = localStorage.getItem("token");
        // console.log("拦截请求数据--->" + token);
        if (token) {
            // 解码 JWT 的 payload 部分
            const payload = JSON.parse(atob(token.split('.')[1]));
            // 当前时间（秒）
            const currentTime = Math.floor(Date.now() / 1000);
            if (payload.exp && payload.exp < currentTime) {
                // Token 已过期
                localStorage.removeItem('token');
                router.push('/login'); // 跳转到登录页
                return Promise.reject(new Error('Token 已过期，请重新登录'));
            }
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
        // console.log('响应数据拦截--->', resp)
        if (resp.data.message === "login failed") {
            ElMessage.error({message: '没有登录，请重新登录'})
            location.href = '/login'
        }
        return resp;
    },
    err => {
        console.log("失败信息" + err);
    }
);

app.component('Header', Header)
app.component('Footer', Footer)
app.mount('#app')

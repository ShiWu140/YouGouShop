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
Vue.prototype.$http.defaults.baseURL = 'http://127.0.0.1:8080';
// Vue.prototype.$http.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';
//请求拦截器
Vue.prototype.$http.interceptors.request.use(
    req => {
        //读取localstoge中的token
        let token = localStorage.getItem("token");
        console.log("拦截请求token--->" + token);
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
Vue.prototype.$http.interceptors.response.use(
    resp => {
        console.log('response拦截', resp)
        if (resp.data.data.code === 0) {
            Message.error({message: '没有登录，请重新登录'})
            location.href = '/login'
        }
        return resp;
    },
    err => {
        console.log("失败信息" + err);
    }
);
Vue.use(ElementUI);
new Vue({
    router, render: function (h) {
        return h(App)
    }
}).$mount('#app')

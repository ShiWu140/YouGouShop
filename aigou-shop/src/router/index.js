import {createRouter, createWebHistory} from 'vue-router';
import {ElMessage} from "element-plus";

import IndexView from '@/views/index.vue';
import LoginView from '@/views/login.vue';
import RegisterView from '@/views/register.vue';
import ShoppingCartView from '@/views/shoppingCart.vue';
import BuyerTradeView from '@/views/buyerTrade.vue';
import ClassifyView from '@/views/classify.vue';
import GoodsDetailView from '@/views/goodsDetail.vue';
import DeliverAddressView from '@/views/deliverAddress.vue';
import payment from "@/views/payment.vue";
const routes = [
    {
        path: '/',
        name: 'index',
        meta: {noRequireAuth: true},
        component: IndexView,
    },
    {
        path: '/login',
        name: 'login',
        meta: {noRequireAuth: true},
        component: LoginView,
    },
    {
        path: '/register',
        name: 'register',
        meta: {noRequireAuth: true},
        component: RegisterView,
    },
    {
        path: '/shoppingCart',
        name: 'shoppingCart',
        meta: {noRequireAuth: false},
        component: ShoppingCartView,
    },
    {
        path: '/buyerTrade',
        name: 'buyerTrade',
        meta: {noRequireAuth: false},
        component: BuyerTradeView,
    },
    {
        path: '/classify',
        name: 'classify',
        meta: {noRequireAuth: false},
        component: ClassifyView,
    },
    {
        path: '/goodsDetail',
        name: 'goodsDetail',
        meta: {noRequireAuth: false},
        component: GoodsDetailView,
    },
    {
        path: '/deliverAddress',
        name: 'deliverAddress',
        meta: {noRequireAuth: false},
        component: DeliverAddressView,
    },
    {
        path: '/payment',
        name: 'Payment',
        meta: {noRequireAuth: false},
        component: payment,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    // 检查用户是否已登录
    const isAuthenticated = () => {
        const user = localStorage.getItem("userId");
        const token = localStorage.getItem("token");
        const userName = localStorage.getItem("userName")
        return user && token && userName;
    };

    // 如果路由不需要认证，直接放行
    if (to.meta.noRequireAuth) {
        return next();
    }

    // 如果用户已登录，放行
    if (isAuthenticated()) {
        return next();
    } else {
        // 如果用户未登录，提示用户登录并重定向到登录页面
        ElMessage.warning('请先登录');
        next({
            path: '/login',
            // 保存目标路径
            query: {redirect: to.fullPath}
        });
    }
});

export default router;

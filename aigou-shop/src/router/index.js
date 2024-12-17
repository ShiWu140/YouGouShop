import {createRouter, createWebHistory} from 'vue-router'

import IndexView from '@/views/index.vue'
import LoginView from '@/views/login.vue'
import registerView from '@/views/register.vue'
import shoppingCartView from '@/views/shoppingCart.vue'
import buyerTrade from "@/views/buyerTrade.vue";
import classify from "@/views/classify.vue";
import goodsDetail from "@/views/goodsDetail.vue";
import deliverAddress from "@/views/deliverAddress.vue";

const routes = [
    {
        path: '/',
        name: 'index',
        meta: {
            noRequireAuth: true
        },
        component: IndexView,
    },
    {
        path: '/login',
        name: 'login',
        meta: {
            noRequireAuth: true
        },
        component: LoginView,
    },
    {
        path: '/register',
        name: 'register',
        meta: {
            noRequireAuth: true
        },
        component: registerView,
    },
    {
        path: '/shoppingCart',
        name: 'shoppingCart',
        meta: {
            noRequireAuth: true
        },
        component: shoppingCartView,
    },
    {
        path: '/buyerTrade',
        name: 'buyerTrade',
        meta: {
            noRequireAuth: true
        },
        component: buyerTrade,
    },
    {
        path: '/classify',
        name: 'classify',
        meta: {
            noRequireAuth: true
        },
        component: classify,
    },
    {
        path: '/goodsDetail',
        name: 'goodsDetail',
        meta: {
            noRequireAuth: true
        },
        component: goodsDetail,
    },
    {
        path: '/deliverAddress',
        name: 'deliverAddress',
        meta: {
            noRequireAuth: true
        },
        component: deliverAddress,
    }

]

const router = new createRouter({
    history: createWebHistory(),
    routes: routes,
})

router.beforeEach((to,from,next) => {
    const user = localStorage.getItem("user");
    //路由不需要认证
    if (to.meta.noRequireAuth) {
        next()
    } else {
        // 判断是否有登录缓存
        if (user) {
            next()
        } else {
            next({
                path: '/'
            })
        }
    }
})

export default router

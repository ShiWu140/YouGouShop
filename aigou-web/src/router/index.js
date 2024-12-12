import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexView from '@/views/HomeView.vue'
import LoginView from "@/views/LoginVue.vue";
import AdminView from "@/views/AdminView.vue";
import adminProduct from "@/views/admin/product.vue"
// 首页
import adminHome from "@/views/admin/home.vue"
// 商品
import adminProductType from "@/views/admin/productType.vue"
import adminBrand from "@/views/admin/brand.vue"
import adminSales from "@/views/admin/sales.vue"
// 购物车
import adminShopCart from "@/views/admin/shopCart.vue"
import adminShopCartProduct from "@/views/admin/shopCartProduct.vue"
import adminReceivingAddress from "@/views/admin/receivingAddress.vue"
// 订单
import adminOrder from "@/views/admin/order.vue"
import adminOrderProduct from "@/views/admin/orderProduct.vue"
// 系统
import adminUser from "@/views/admin/user.vue"
import adminCarouselFigure from "@/views/admin/carouselFigure.vue"
import adminSearchHistory from "@/views/admin/searchHistory.vue"


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        component: IndexView
    }, {
        path: '/login',
        name: 'Login',
        component: LoginView
    }, {
        path: '/admin',
        name: 'AdminView',
        component: AdminView,
        children: [
            {
                path: '/admin',
                name: 'Home',
                component: adminHome
            },
            // 商品
            {
                path: '/admin/product',
                name: 'Product',
                component: adminProduct
            },
            {
                path: '/admin/product-type',
                name: 'ProductType',
                component: adminProductType
            },
            {
                path: '/admin/brand',
                name: 'Brand',
                component: adminBrand
            },
            {
                path: '/admin/sales',
                name: 'Sales',
                component: adminSales
            },
            // 购物车
            {
                path: '/admin/shop-cart',
                name: 'ShopCart',
                component: adminShopCart
            },
            {
                path: '/admin/shop-cart-product',
                name: 'ShopCartProduct',
                component: adminShopCartProduct
            },
            {
                path: '/admin/receiving-address',
                name: 'ReceivingAddress',
                component: adminReceivingAddress
            },
            // 订单
            {
                path: '/admin/order',
                name: 'Order',
                component: adminOrder
            },
            {
                path: '/admin/order-product',
                name: 'OrderProduct',
                component: adminOrderProduct
            },
            // 系统
            {
                path: '/admin/user',
                name: 'User',
                component: adminUser
            },
            {
                path: '/admin/carousel-figure',
                name: 'CarouselFigure',
                component: adminCarouselFigure
            },
            {
                path: '/admin/search-history',
                name: 'SearchHistory',
                component: adminSearchHistory
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router

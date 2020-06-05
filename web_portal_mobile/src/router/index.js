import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);
//引入一级组件
import Dashboard from "../views/dashboard/Dashboard";
//懒加载
const Home = () => import('./../views/home/Home.vue');
const Category = () => import('./../views/category/Category.vue');
const Cart = () => import('./../views/cart/Cart.vue');
const Mine = () => import('./../views/mine/Mine.vue');
const Order = () => import('./../views/order/Order');
const MyAddress = () => import('./../views/order/children/MyAddress');
const AddAddress = () => import('./../views/order/children/children/AddAddress');
const EditAddress = () => import('./../views/order/children/children/EditAddress');
const Login = () => import('./../views/login/Login');
const UserCenter = () => import('./../views/mine/children/UserCenter');
const MyCartToOrder = () => import('./../views/order/children/MyCartToOrder');

export default new Router({
    mode: 'history',
    routes: [
        {path: '/', redirect: '/dashboard/home'},
        {
            path: '/dashboard',
            name: 'Dashboard',
            component: Dashboard,
            children: [
                {path: 'home', name: 'Home', component: Home, meta: {keepAlive: true}},
                {path: 'category', name: 'Category', component: Category, meta: {keepAlive: true}},
                {path: 'cart', name: 'Cart', component: Cart},
                {
                    path: 'mine',
                    name: 'Mine',
                    component: Mine,
                    children: [
                        {path: 'userCenter', name: 'UserCenter', component: UserCenter},
                    ]
                },
                {path: 'login', name: 'Login', component: Login,}
            ]
        },
        {
            path: '/confirmOrder',
            name: 'order',
            component: Order,
            children: [
                {
                    path: 'myAddress',
                    name: 'MyAddress',
                    component: MyAddress,
                    children: [
                        {path: 'addAddress', name: 'AddAddress', component: AddAddress},
                        {path: 'editAddress', name: 'EditAddress', component: EditAddress},
                    ]
                },
                {path: 'myCartToOrder', name: 'MyCartToOrder', component: MyCartToOrder}
            ]
        },

    ]
})
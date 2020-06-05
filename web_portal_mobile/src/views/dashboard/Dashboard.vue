<template>
    <div id="dashboard">
        <van-tabbar v-model="active" active-color="#1afa29">
            <van-tabbar-item replace to="/dashboard/home">
                <span>首页</span>
                <template #icon="props" >
                    <img :src="props.active ? home_icon.inactive: home_icon.active "/>
                </template>
            </van-tabbar-item>
            <van-tabbar-item replace to="/dashboard/category" active-color="#1afa29">
                <span>分类</span>
                <template #icon="props">
                    <img :src="props.active ?  category_icon.inactive:  category_icon.active "/>
                </template>
            </van-tabbar-item>
            <van-tabbar-item replace to="/dashboard/cart" :info="goodsNum>0 ? goodsNum : ''" active-color="#1afa29">
                <span>购物车</span>
                <template #icon="props">
                    <img :src="props.active ? cart_icon.inactive : cart_icon.active "/>
                </template>
            </van-tabbar-item>
            <van-tabbar-item replace to="/dashboard/mine" active-color="#1afa29">
                <span>我的</span>
                <template #icon="props">
                    <img :src="props.active ? mine_icon.inactive : mine_icon.active "/>
                </template>
            </van-tabbar-item>
        </van-tabbar>
        <keep-alive>
            <router-view v-if="$route.meta.keepAlive"/>
        </keep-alive>
        <router-view v-if="!$route.meta.keepAlive"/>
    </div>
</template>

<script>
    import {mapMutations,mapState} from 'vuex'
    export default {
        name: "Dashboard",
        data() {
            return {
                active: Number( sessionStorage.getItem("tabBarActiveIndex"))||0,
                home_icon: {
                    active: require('@/images/tabbar/home.svg'),
                    inactive:require('@/images/tabbar/checkHome.svg') ,
                },
                category_icon: {
                    active: require('@/images/tabbar/category.svg'),
                    inactive: require('@/images/tabbar/checkCategory.svg'),
                },
                cart_icon: {
                    active: require('@/images/tabbar/cart.svg'),
                    inactive: require('@/images/tabbar/checkCart.svg'),
                },
                mine_icon: {
                    active:require( '@/images/tabbar/mine.svg'),
                    inactive:require( '@/images/tabbar/checkMine.svg'),
                },
            };
        },
        computed:{
            ...mapState(["shopCart"]),
            goodsNum(){
                if(this.shopCart){
                    let num = 0;
                    //取出所有的values
                    Object.values(this.shopCart).forEach((goods,index)=>{
                        num +=Number(goods.num);
                    })
                    return num;
                }else{
                    return 0;
                }
            }
        },
        methods:{
            ...mapMutations(["INIT_SHOP_CART",]),
            getInitShopCartToRedis(){
                let userInfo = this.StorageUtil.Local.getAndTime('userInfo');
                let vm = this;
                vm.axios.get(vm.API.CART_FINDCARTLIST_URL+"?username="+userInfo.username).then(res=>{
                    console.log(res);
                    let arr = res.data;
                    let shopCart = {};
                    arr.forEach((item,index)=>{
                        shopCart[item.id] ={
                            'num': item.num,
                            'id': item.id,
                            'name': item.name,
                            'small_price': item.smallImage,
                            'price': item.price,
                            "checked": item.checked,
                        }
                    })
                    console.log(shopCart);
                    this.StorageUtil.Local.set("shopCart",shopCart);
                    //获取购物车数据
                    this.INIT_SHOP_CART();
                })
            }
        },
        mounted(){
            this.getInitShopCartToRedis()
        },
        watch:{
            active(value){
                let tabBarActiveIndex = value > 0 ? value : 0;
                //缓存本地
                sessionStorage.setItem('tabBarActiveIndex',tabBarActiveIndex);
            }
        }
    }
</script>

<style scoped>
    #dashboard {
        width: 100%;
        height: 100%;
        background-color: #f5f5f5;
    }
</style>
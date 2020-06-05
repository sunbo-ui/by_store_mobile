<template>
    <div id="mine">
        <van-nav-bar
                title="我的"
                :fixed="true"
                :border="false"
        />
        <!--用户-->
        <van-cell-group style="margin-top: 2.3rem;">
            <van-cell
                    :center="true"
                    style="background-color: #2eba5a;color: #fff"
                    @click="$router.push('/dashboard/mine/userCenter')"
                    is-link>
                <template #title>
                    <div class="personMsg">
                        <img src="./images/logo.png" alt="">
                        <div class="personInfo">
                            <span>{{user.username}}</span>
                            <span>手机号:{{user.phone}}</span>
                        </div>
                    </div>
                </template>
            </van-cell>
        </van-cell-group>

        <van-cell-group>
            <van-cell icon="label" title="我的订单" value="查看所有的订单" is-link/>
            <van-grid>
                <van-grid-item
                        v-for="(order,index) in orderData"
                        :icon="order.icon"
                        :key="index"
                        :dot="order.dot"
                        :text="order.title"/>

            </van-grid>
        </van-cell-group>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell icon="gold-coin" title="我的优惠券" value="1张" is-link/>
            <van-cell icon="todo-list" title="我的收货地址" is-link/>
        </van-cell-group>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell icon="phone" title="联系客服" value="客服时间 07:00-22:00" is-link/>
            <van-cell icon="comment" title="意见反馈" is-link/>
        </van-cell-group>

        <transition mode="out-in" name="router-slider">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import Login from "../login/Login";

    export default {
        name: "Mine",
        computed:{
            ...mapState(['userInfo']),
        },
        data() {
            return {
                orderData: [
                    {icon: 'cart-circle-o', title: '待支付',dot:false},
                    {icon: 'gift-o', title: '待收货',dot:false},
                    {icon: 'smile-comment-o', title: '待评价',dot:false},
                    {icon: 'cash-back-record', title: '售后/退款',dot:false},
                ],
                user:{},
            }
        },
        methods:{
            onLoadUserInfo(){
                let vm = this;
                vm.axios.get(vm.API.MINE_USERINFO_URL,{params:{username:this.userInfo.username}}).then(res=>{
                    vm.user = res.data;
                })
            },
            onLoadOrderInfo(){
                let vm = this;
                vm.axios.get(vm.API.MINE_ORDER_URL,{params:{username:this.userInfo.username}}).then(res=>{
                    if(res.data){
                        this.orderData[0].dot=true
                    }
                })
            }
        },
        created() {
            this.onLoadUserInfo();
            this.onLoadOrderInfo()
        }
    }
</script>

<style scoped>
    #mine {
        width: 100%;
        height: 100%;
        background-color: #f5f5f5;
    }

    .van-nav-bar {
        background-color: #2eba5a;
    }

    #mine >>> .van-nav-bar__title {
        color: #fff;
        font-size: 1rem;
    }

    .personMsg {
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .personMsg > img {
        width: 4rem;
        height: 4rem;
        border: 0.15rem solid #fff;
        border-radius: 50%;
    }

    .personInfo {
        display: flex;
        flex-direction: column;
        margin-left: 0.8rem;
    }

    .van-cell__left-icon {
        color: orange;
        font-size: 1.2rem;
    }
    .router-slider-enter-active, .router-slider-leave-active {
        transition: all 0.5s;
    }
    .router-slider-enter,.router-slider-leave-active{
        transform: translate3d(2rem,0,0);
        opacity: 0;
    }
</style>
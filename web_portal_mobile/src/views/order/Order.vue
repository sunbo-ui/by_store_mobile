<template>
    <div id="order">
        <van-nav-bar
                title="填写订单"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <!-- 联系人卡片 -->
        <van-contact-card
                :type="address_type"
                :name="address_name"
                :tel="address_tel"
                add-text="选择收货地址"
                @click="chooseAddress"
                style="margin-top: 3rem;"
        ></van-contact-card>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell is-link title="送达时间" :value="arriveDate" @click="showDate"/>
            <van-cell :value="`共${totalNum}件`" is-link :center="true" @click="onOrder">
                <!-- 使用 title 插槽来自定义标题 -->
                <template #title>
                    <img v-if="index<3" v-for="(cart,index) in cartList" :key="index" :src="cart.small_price"
                         style="width: 3rem;" alt="">
                </template>
            </van-cell>
        </van-cell-group>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell title="支付方式" :value="status[paymentType]" @click="onPayShow"></van-cell>
        </van-cell-group>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell title="备注">
                <input type="text" :value="this.order.buyer_message" placeholder="选填,备注您的特殊需求!">
            </van-cell>
        </van-cell-group>

        <van-cell-group style="margin-top: 0.6rem;">
            <van-cell title="商品金额" :value="`￥${totalPrice.toFixed(2)}`"/>
            <van-cell title="配送费" value="￥0.00"/>
        </van-cell-group>

        <van-submit-bar
                :price="totalPrice*100"
                label="实付"
                button-text="提交订单"
                @submit="onSubmit"
        />


        <van-popup v-model="dateShow" round position="bottom">
            <van-datetime-picker
                    v-model="currentDate"
                    type="datetime"
                    :min-date="minDate"
                    :max-date="maxDate"
                    @cancel="onDateCancel"
                    @confirm="onDateConfirm"
            />
        </van-popup>

        <van-popup v-model="payShow" round position="bottom">
            <van-cell @click="onPay('1')" center value="微信"/>
            <van-cell @click="onPay('2')" center value="货到付款"/>
            <van-cell @click="onPay('3')" center value="B悦钱包"/>
        </van-popup>

        <transition mode="out-in" name="router-slider">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import {mapState} from 'vuex';
    import PubSub from 'pubsub-js';
    import {Toast} from 'vant';
    import moment from 'moment'

    export default {
        name: "Order",
        data() {
            return {
                chosenAddressId: '1',
                cartList: [],
                address_type: 'add',
                address_id: null,
                address_name: null,
                address:null,
                address_tel: null,
                dateShow: false,
                minDate: new Date(),
                maxDate: new Date(2021, 10, 1),
                currentDate: new Date(),
                arriveDate: "请选择送达时间",
                payShow: false,
                paymentType:'3',
                status:{
                    '1':"微信",
                    '2':"货到付款",
                    '3':"B悦钱包",
                },
                order:{
                    paymentType:1,
                    receiverAreaName:'',
                    receiverMobile:'',
                    receiver:'',
                    sourceType:'',
                    userId:'',
                    buyer_message:'',
                    buyer_rate:0,
                },
            };
        },
        computed: {
            ...mapState(['userInfo', 'shopCart']),
            totalPrice() {
                let totalPrice = 0;
                Object.values(this.shopCart).forEach(goods => {
                    if (goods.checked) {
                        totalPrice += goods.price * goods.num;
                    }
                })
                return totalPrice;
            },
            totalNum() {
                let totalNum = 0;
                Object.values(this.shopCart).forEach(goods => {
                    if (goods.checked) {
                        totalNum += Number(goods.num);
                    }
                })
                return totalNum;
            }
        },
        methods: {
            onPayShow(){
                this.payShow = true;
            },
            onPay(param){
                this.paymentType = param;
                this.payShow = false;
            },
            onOrder() {
                this.$router.push("/confirmOrder/myCartToOrder")
            },
            onClickLeft() {
                this.$router.back()
            },
            chooseAddress() {
                this.$router.push("/confirmOrder/myAddress")
            },
            onSubmit() {
                if(this.address_id===null){
                    Toast({
                        message:"请先选择地址哦~",
                        duration:800,
                    })
                    return;
                }
                this.order.receiverAreaName = this.address//地址
                this.order.receiverMobile = this.address_tel;//电话
                this.order.receiver = this.address_name;//联系人
                this.order.sourceType = '1';
                this.order.userId = this.userInfo.username;
                this.order.paymentType = this.paymentType;

                let vm = this;
                vm.axios.post(vm.API.ORDER_ADD_URL,this.order).then(res=>{
                    if(res.data.success){
                        if(this.order.paymentType == 1){
                            //微信支付
                        }else{
                            //货到付款
                            this.$router.push("/dashboard/mine")
                        }
                    }
                })
            },
            showDate() {
                this.dateShow = true
            },
            onDateConfirm(value) {
                this.dateShow = false
                this.arriveDate = moment(value).format('YYYY-MM-DD hh:mm')
            },
            onDateCancel() {
                this.dateShow = false
            },
            onLoadCart() {
                let vm = this;
                Object.values(this.shopCart).forEach(goods => {
                    if (goods.checked) {
                        vm.cartList.push(goods)
                    }
                })
            }
        },
        mounted() {
            PubSub.subscribe('userAddress', (msg, item) => {
                if (msg === 'userAddress') {
                    console.log(item);
                    this.address_id = item.id;
                    this.address_name = item.name;
                    this.address_type = "edit"
                    this.address_tel = item.tel;
                    this.address = item.address;
                }
            });
            this.onLoadCart();
        },
        beforeDestroy() {
            PubSub.unsubscribe('userAddress')
        }
    }
</script>

<style scoped>
    #order {
        width: 100%;
        height: 100%;
        background-color: #f5f5f5;
    }

    .router-slider-enter-active, .router-slider-leave-active {
        transition: all 0.5s;
    }

    .router-slider-enter, .router-slider-leave-active {
        transform: translate3d(2rem, 0, 0);
        opacity: 0;
    }
</style>
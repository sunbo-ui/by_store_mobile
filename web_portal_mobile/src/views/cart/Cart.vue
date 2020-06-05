<template>
    <div id="cart">
        <div class="content">
            <div id="cartHeader">
                <span class="headerContent">购物车</span>
                <span @click="removeAllToCart" style="color: red;position: absolute;top: 35%;right: 10px">清空购物车</span>
            </div>
            <van-cell style="margin-top: 0.14rem;" v-for="(goods , index) in shopCart" :key="index">
                <div style="display: flex">
                    <div style="flex: 1;display: flex;justify-items: center;align-items: center">
                        <van-checkbox @click="selectCart(goods.id)" style="margin-right: 1rem;" v-model="goods.checked"
                                      checked-color="#07c160"></van-checkbox>
                    </div>
                    <div style="flex: 5;">
                        <img :src="goods.small_price" width="80" height="80" alt="">
                    </div>
                    <div style="flex:10;position: relative;margin-left: 0.5rem;">
                        <span style="color: #303133;font-size: 0.8rem;">{{goods.name}} </span>
                        <div>
                            <span style=" position: absolute;bottom: 0;left:0;color: red;">{{goods.price |moneyFormat}}</span>
                            <van-stepper @minus="removeToCart(goods.id,goods.num)"
                                         min="0"
                                         @plus="addNum(goods.id,goods.name,goods.small_price,goods.price)"
                                         style="position: absolute;bottom: 0;right:0;" v-model="goods.num"/>
                        </div>
                    </div>
                </div>
            </van-cell>
            <div class="addToOrder">
                <div style="width: 100%;height: 100%;position: relative">
                    <van-checkbox v-model="isSelectedAll" @click="selectAllCart()"
                                  style="position: absolute;top: 15px;left: 10px" checked-color="#07c160">全选
                    </van-checkbox>
                    <div style="position: absolute;top: 13px;left:100px">
                        <span>合计:</span>
                        <span style="color: red">{{totalPrice | moneyFormat}}</span>
                    </div>
                    <div @click="goOrder()" style="position: absolute;top: 10px;right:10px;border-radius: 50px;color:#fff;padding:2px 10px 2px 10px;height: 30px;background-color: red;"
                         type="danger">
                        <span>去结算(</span>
                        <span>{{totalNum}}</span>
                        <span>)</span>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import {Dialog} from 'vant';
    import { Toast } from 'vant';

    export default {
        name: "Cart",
        data() {
            return {
                num: 1,
            };
        },
        computed: {
            ...mapState(['shopCart','userInfo']),
            isSelectedAll: {
                get: function () {
                    if (this.totalNum === 0) {
                        return false;
                    }
                    if (Object.values(this.shopCart).every(item => item.checked)) {
                        //全部选中
                        return true;
                    } else {
                        return false;
                    }
                },
                set: function (val) {

                }
            },
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
            ...mapMutations(['REMOVE_ALL_CART','REDUCE_CART', 'ADD_GOODS', 'SELECT_CHECKED_CART', 'SELECT_CHECKED_ALL_CART']),
            addNum(goodsId, goodsName, smallImage, goodsPrice) {
                if(this.userInfo){
                    //取出state存到redis
                    let vm = this;
                    let obj = {
                        id:goodsId,
                        name:goodsName,
                        smallImage:smallImage,
                        price:goodsPrice,
                    }
                    vm.axios.post(vm.API.CART_ADDGOODSTOCARTLIST_URL+"?username="+this.userInfo.username,obj).then(res=>{
                        if(res.data.success){
                            Toast({
                                message:res.data.message,
                                duration:800
                            })
                        }else {
                            Toast({
                                message:res.data.message,
                                duration:800
                            })
                        }
                    })

                    //需要同步到state
                    this.ADD_GOODS({
                        goodsId,
                        goodsName,
                        smallImage,
                        goodsPrice,
                    })
                }
            },
            goOrder(){
                if(this.totalNum>0){
                    this.$router.push('/confirmOrder');
                }else{
                    Toast("还没有商品哦,多去逛逛八")
                }
            },
            removeToCart(goodsId, goodsNum) {
                goodsNum = goodsNum-1;
                if (goodsNum >= 1) {
                    this.REDUCE_CART({goodsId});
                    //改变数量
                    let vm = this;
                    vm.axios.get(vm.API.CART_UPDATENUM_URL+"?username="+this.userInfo.username+"&num="+goodsNum+"&goodsId="+goodsId).then(res=>{
                        if(res.data.success){
                            Toast(res.data.message);
                        }
                    })
                } else if (goodsNum === 0) {
                    Dialog.confirm({
                        title: '温馨提示',
                        message: '确定删除该商品吗?',
                    }).then(() => {
                        this.REDUCE_CART({goodsId});
                        //删除单个商品
                        let vm = this;
                        vm.axios.get(vm.API.CART_REMOVE_URL+"?username="+this.userInfo.username+"&goodsId="+goodsId).then(res=>{
                            if(res.data.success){
                                Toast(res.data.message);
                            }
                        })
                    }).catch(() => {
                        // on cancel
                        location.reload()
                    });
                }

            },
            selectCart(goodsId) {
                this.SELECT_CHECKED_CART({goodsId});
                //服务器同步
                let vm = this;
                vm.axios.get(vm.API.CART_SELECTSINGE_URL+"?username="+this.userInfo.username+"&goodsId="+goodsId).then(res=>{
                    if(res.data.success){
                        Toast({
                            message:res.data.message,
                            duration:800
                        })
                    }else {
                        Toast({
                            message:res.data.message,
                            duration:800
                        })
                    }
                })
            },
            selectAllCart() {
                this.SELECT_CHECKED_ALL_CART();
                //服务器同步
                let vm = this;
                vm.axios.get(vm.API.CART_SELECTALL_URL+"?username="+this.userInfo.username).then(res=>{
                    if(res.data.success){
                        Toast({
                            message:res.data.message,
                            duration:800
                        })
                    }else {
                        Toast({
                            message:res.data.message,
                            duration:800
                        })
                    }
                })
            },
            removeAllToCart() {
                Dialog.confirm({
                    title: '温馨提示',
                    message: '确定清空购物车吗?',
                }).then(() => {
                    this.REMOVE_ALL_CART();
                    //清空redis当中的购物车
                    let vm = this;
                    vm.axios.get(vm.API.CART_REMOVEALL_URL+"?username="+this.userInfo.username).then(res=>{
                        if(res.data.success){
                            Toast(res.data.message);
                        }
                    })
                }).catch(() => {
                    // on cancel
                   // location.reload()
                });
            },
        },
    }
</script>

<style scoped>
    #cartHeader {
        width: 100%;
        height: 50px;
        background-color: #fff;
        margin-bottom: 0.5rem;
        position: relative;

    }

    #cart {
        width: 100%;
        height: 100%;
        padding-bottom: 50px;
        background-color: #f5f5f5;
    }

    .content {
        height: 100%;
        width: 100%;
        position: relative;

    }

    .addToOrder {
        width: 100%;
        height: 50px;
        background-color: #fff;
        position: absolute;
        bottom: 0;
        left: 0;

    }

    .headerContent {
        position: absolute;
        top: 15%;
        left: 50%;
        transform: translate(-50%);
        font-size: 1rem;
    }
</style>
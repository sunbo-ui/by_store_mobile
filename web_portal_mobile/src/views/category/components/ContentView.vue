<template>
    <div id="contentView" v-if="showList">
        <van-cell v-for="(item,index) in rightCategoryList" :key="index">
            <div style="display: flex">
                <div style="flex: 1">
                    <img :src="item.smallPic" width="80" height="80" alt="">
                </div>
                <div style="flex: 3;position: relative;margin-left: 0.3rem;">
                    <span style="color: #303133;font-size: 0.8rem;">{{item.goodsName }}</span>
                    <div style="color: #C0C4CC;font-size: 0.4rem;">{{item.caption}}</div>
                    <div style="margin-top: 0.8rem;">
                        <span style="color: red">{{item.price |moneyFormat}}</span>
                        <button class="addCartBtn" @click="addToCart(item.id,item.goodsName,item.smallPic,item.price)">
                            <img style="margin-top: 5px;" src="./images/cart.svg" width="60%" height="60%" alt="">
                        </button>
                    </div>
                </div>
            </div>
        </van-cell>
    </div>
    <div v-else>
        <div style="position: absolute;left: 50%;top: 40%;">
            现没有该类商品
        </div>
    </div>
</template>

<script>
    import {Toast} from 'vant';
    import {mapState, mapMutations} from 'vuex'

    export default {
        name: "ContentView",
        props: {
            rightCategoryList: Array,
        },
        data() {
            return {
                showList: true,
            };
        },
        computed: {
            ...mapState(["userInfo"])
        },
        methods: {
            ...mapMutations(['ADD_GOODS']),
            addToCart(goodsId,goodsName,smallImage,goodsPrice){
                if(this.userInfo.username){
                    //取出state存到redis
                    let vm = this;
                    let obj = {
                        id:goodsId,
                        name:goodsName,
                        smallImage:smallImage,
                        price:goodsPrice,
                        num:1,
                        checked:true
                    }
                    vm.axios.post(vm.API.CART_ADDGOODSTOCARTLIST_URL+"?username="+this.userInfo.username,obj).then(res=>{
                        if(res.data.success){
                            /*存入本地*/
                            this.ADD_GOODS({
                                goodsId,
                                goodsName,
                                smallImage,
                                goodsPrice,
                            })
                            Toast.success({
                                message:"添加到购物车成功!",
                                duration:800
                            })
                        }else{
                            Toast({
                                message:"添加失败!错误码:100",
                                duration:800
                            })
                        }
                    })
                }else{
                    this.$router.push("/dashboard/login")
                }
            }
        },
        filters: {
            titleLength(val) {
                if (val.length <= 7) {
                    return val;
                } else {
                    val = val.substr(0, 15) + "...";
                    return val;
                }
            },
        },
        created() {
        },
        watch: {
            rightCategoryList(value) {
                if (value.length <= 0) {
                    this.showList = false;
                } else {
                    this.showList = true;
                }
            }
        }
    }
</script>

<style scoped>
    #contentView {
        width: 100%;
        height: 100%;
        overflow-x: hidden;
        overflow-y: auto;
    }

    .addCartBtn {
        display: inline-block;
        width: 1.5rem;
        height: 1.5rem;
        border-radius: 50%;
        position: absolute;
        bottom: 0.4rem;
        right: 0.1rem;
        background-color: #3BA068;
    }
</style>
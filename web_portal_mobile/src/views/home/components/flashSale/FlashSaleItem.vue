<template>
    <div class="itemWrapper">
        <div :key="index" v-for="(item,index) in flash_list"
             style="width: 6rem;display:inline-block;margin-right: 1rem">
            <img style="width: 100%;height:8rem" :src="item.smallPic" alt="">
            <div class="title">{{item.goodsName |titleLength}}</div>
            <div style="position: relative">
                <span class="newPrice">{{item.price|moneyFormat }}</span>
                <button class="addCartBtn" @click="addToCart(item.id,item.goodsName,item.smallPic,item.price)">
                    <img style="margin-top: 5px;" src="./image/cart.svg" width="60%" height="60%" alt="">
                </button>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState, mapMutations} from 'vuex'
    import {Toast} from 'vant';

    export default {
        name: "FlashSaleItem",
        props: {
            flash_list: Array,
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
                if (val.length <= 4) {
                    return val;
                } else {
                    val = val.substr(0, 4) + "...";
                    ;
                    return val;
                }
            },
        }
    }
</script>

<style scoped>
    .itemWrapper {
        white-space: nowrap;
        overflow-y: hidden;
        overflow-x: scroll;
        overflow-scrolling: touch;
    }

    /*隐藏滚动条*/
    .itemWrapper::-webkit-scrollbar {
        display: none;
    }

    .addCartBtn {
        display: inline-block;
        width: 1.6rem;
        height: 1.6rem;
        border-radius: 50%;
        position: absolute;
        top: -0.5rem;
        right: -0.2rem;
        background-color: #3BA068;
    }

    .title {
        margin-top: 10px;
        color: #333333;
        font-size: 1rem;
        margin-left: 10px;
    }

    .oldPrice {
        margin-top: 5px;
        margin-left: 8px;
        color: #c6c8ca;
        font-size: 0.6rem;
    }

    .newPrice {
        margin-top: 5px;
        margin-left: 8px;
        color: red;
        font-size: 0.7rem;
    }
</style>
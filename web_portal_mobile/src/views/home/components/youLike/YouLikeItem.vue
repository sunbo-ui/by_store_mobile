<template>
    <div class="itemWapper">
        <div :key="index" v-for="(item,index) in youLike_list" class="itemContent">
            <van-image  style="width: 100%;height: 180px"  :src="item.smallPic">
                <template v-slot:loading>
                    <van-loading type="spinner" size="20"/>
                </template>
            </van-image>
            <div>
                <span class="title">{{item.goodsName |titleLength}}</span>
                <div style="position: relative">
                    <span class="newPrice">{{item.price|moneyFormat }}</span>
                    <button class="addCartBtn" @click="addToCart(item.id,item.goodsName,item.smallPic,item.price)">
                        <img style="margin-top: 5px;" src="./image/cart.svg" width="60%" height="60%" alt="">
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { Toast } from 'vant';
    import {mapState,mapMutations} from 'vuex'
    export default {
        name: "YouLikeItem",
        props:{
            youLike_list:Array,
        },
        computed:{
          ...mapState(["userInfo"])
        },
        methods:{
            ...mapMutations([ 'ADD_GOODS']),
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
        filters:{
            titleLength(val){
                if(val.length<=7){
                    return val;
                }else {
                    val = val.substr(0,17)+"...";
                    return val;
                }
            },
        }
    }
</script>

<style scoped>
    .itemWapper{
        width: 100%;
        height: 100%;
        padding: 1rem;

    }
    .itemContent{
        width: 45%;
        padding: 0.5rem;
        margin: 5px 10px 5px 5px;
        display: inline-block;
        background-color: #fff;
    }
    .title {
        height: 2.5rem;
        width: 100%;
        overflow: hidden;
        display: inline-block;
        margin-top: 0.3rem ;
        color: #333333;
        font-size: 1rem;
        margin-left: 10px;
    }

    .newPrice {
        margin-left: 1px;
        color: red;
        font-size: 0.7rem;
    }

    .addCartBtn {
        display: inline-block;
        width: 1.5rem;
        height:1.5rem;
        border-radius: 50%;
        position: absolute;
        top: -0.1rem;
        right: -0.1rem;
        background-color: #3BA068;
    }
</style>
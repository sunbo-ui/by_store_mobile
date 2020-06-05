<template>
    <div id="myCartToOrder">
        <van-nav-bar
                title="我的订单商品"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
      <div style="margin-top: 3rem;">
          <van-cell style="margin-top: 0.3rem;" v-for="(goods , index) in shopCart" :key="index">
              <div style="display: flex">
                  <div style="flex: 5;">
                      <img :src="goods.small_price" width="80" height="80" alt="">
                  </div>
                  <div style="flex:10;position: relative;margin-left: 0.5rem;">
                      <span style="color: #303133;font-size: 0.8rem;">{{goods.name}} </span>
                      <div>
                          <span style=" position: absolute;bottom: 0;left:0;color: red;">{{goods.price |moneyFormat}}</span>
                          <span>{{goods.num}}</span>
                      </div>
                  </div>
              </div>
          </van-cell>
      </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'

    export default {
        name: "MyCartToOrder",
        computed:{
            ...mapState(['shopCart']),
        },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
        }
    }
</script>

<style scoped>
    #myCartToOrder {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: #f5f5f5;
        z-index: 9999;
    }
</style>
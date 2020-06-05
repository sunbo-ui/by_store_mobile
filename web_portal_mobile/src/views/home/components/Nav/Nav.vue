<template>
    <div id="nav">
        <div :key="index" v-if="index<8"
             v-for="(nav,index) in nav_list"
             style="margin-top: 1.4rem;margin-left: 2rem;
             float:left;width:2.7rem;height: 2.7rem;text-align: center">
            <van-image style="width:2.7rem;height: 2.7rem;" :src="nav.picPath">
                <template v-slot:loading>
                    <van-loading type="spinner" size="20"/>
                </template>
            </van-image>
            <div style="font-size: 0.5rem;">{{nav.name|titleLength}}</div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Nav",
        props: {
            nav_list: Array,
        },
        filters:{
            titleLength(val){
                if(val.length<=4){
                    return val;
                }else {
                    val = val.substr(0,2)+"..";;
                    return val;
                }
            },
        }
    }
</script>

<style scoped>
    #nav {
        width: 100%;
        background-color: #fff;
        height: 9.5rem;
    }

    #nav >>> .van-image__img {
        border-radius: 50%
    }
</style>
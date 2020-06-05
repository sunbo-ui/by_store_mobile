<template>
    <div id="app">
        <transition mode="out-in" name="router-slider">
            <router-view/>
        </transition>
    </div>
</template>
<script>
    import {mapActions} from 'vuex'

    export default {
        methods:{
            ...mapActions(["reqUserInfo"])
        },
        created(){
            //初始化userinfo
            this.reqUserInfo()
        },
    }
</script>
<style lang="less">
#app{
    width: 100%;
    height: 100%;
}
.router-slider-enter-active, .router-slider-leave-active {
    transition: all 0.5s;
}
.router-slider-enter,.router-slider-leave-active{
    transform: translate3d(2rem,0,0);
    opacity: 0;
}
</style>

<template>
    <div id="userCenter">
        <van-nav-bar
                title="用户中心"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <div class="logout">
            <button @click="logout">退出登录</button>
        </div>
    </div>
</template>

<script>
    import {mapState,mapMutations} from 'vuex'
    import { Toast } from 'vant';
    export default {
        name: "UserCenter",
        computed: {
            ...mapState(['userInfo']),
        },
        methods: {
            ...mapMutations(["DELETE_USER_INFO","REMOVE_ALL_CART"]),
            onClickLeft() {
                this.$router.back()
            },
            logout() {
                let vm = this;
                vm.axios.get(vm.API.LOGGOUT_URL + "?token=" +this.userInfo.token).then(res => {
                    if(res.data.code == 10001){
                        //用户已退出
                        Toast({
                            message:"用户已退出",
                            duration:1000
                        })
                        //清空userInfo
                        vm.DELETE_USER_INFO();
                        //清空购物车
                        vm.REMOVE_ALL_CART();
                        vm.$router.push("/dashboard/home")
                    }
                })
            }
        }
    }
</script>

<style scoped>
    #userCenter {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 200;
        background-color: #f5f5f5;
    }

    .logout {
        height: 2.5rem;
        margin-top: 4rem;
        width: 100%;
        display: flex;
        justify-items: center;
        align-items: center;
    }

    .logout button {
        width: 100%;
        height: 100%;
        background-color: #fff;

    }

    #userCenter >>> .van-nav-bar__title {
        color: #000;
    }
</style>
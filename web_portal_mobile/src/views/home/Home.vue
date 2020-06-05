<template>
    <div id="home">
        <div v-if="!showLoading">
            <Header/>
            <Sowing :sowing_list="sowing_list"/>
            <Nav :nav_list="nav_list"/>
            <FlashSale :flash_list="flash_list"/>
            <YouLike :youLike_list="youLike_list"/>
            <MarkPage v-if="showBackStatus" :scrollToTop="scrollToTop"/>
        </div>
        <van-loading v-else type="spinner" color="#75a342"
                     style="position: absolute;left: 50%;top: 50%;transform: translate(-50%)">
            正在加载中....
        </van-loading>

    </div>
</template>

<script>
    import Header from "./components/header/Header";
    import Sowing from "./components/sowing/Sowing";
    import Nav from "./components/Nav/Nav";
    import FlashSale from "./components/flashSale/FlashSale";
    import YouLike from "./components/youLike/YouLike";
    import MarkPage from "./components/markPage/MarkPage";
    import {showBack} from "../../plugins/global";
    import jq from 'jquery'

    //引入通知插件
    import { Toast } from 'vant';
    //引入vuex
    import {mapMutations} from 'vuex'

    export default {
        name: "Home",
        components: {MarkPage, YouLike, FlashSale, Nav, Sowing, Header},

        data() {
            return {
                sowing_list: [],
                showLoading: true,
                nav_list: [],
                flash_list: [],
                youLike_list: [],
                //是否显示
                showBackStatus: false,
            };
        },
        methods: {
            ...mapMutations(["ADD_GOODS"]),
             getContentList() {
                let vm = this;
                 vm.axios.post(vm.API.CONTENT_FINDBYCATEGORYID_URL + "?categoryId=1").then(res => {
                    vm.sowing_list = res.data;
                })
                 vm.axios.get(vm.API.HOME_NAV_FINDALL_URL).then(res => {
                    vm.nav_list = res.data;
                })
                 vm.axios.get(vm.API.HOME_FLASHSALE_URL + "?param=0").then(res => {
                    console.log(res);
                    vm.flash_list = res.data;
                })
                 vm.axios.get(vm.API.HOME_FLASHSALE_URL + "?param=1").then(res => {
                    console.log(res);
                    vm.youLike_list = res.data;
                })
                //显示回顶
                showBack((status) => {
                    this.showBackStatus = status;
                })

            },

            scrollToTop() {
                jq('html , body').animate({scrollTop: "0px"}, 400);
            }
        },
        created() {
            this.getContentList();
            this.scrollToTop();
        },
        mounted() {
            this.showLoading = false
        }
    }
</script>

<style scoped>
    #home {
        width: 100%;
        height: 100%;
        background-color: #f5f5f5;
    }
</style>
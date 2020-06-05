<template>
    <div id="category">
        <Header/>
        <div class="content" v-if="!showLoading">
            <div class="leftCategory">
                <van-sidebar v-model="activeKey" @change="onChange">
                    <van-sidebar-item :data="item.itemCatId" v-for="(item,index) in leftCategoryList"
                                      style="font-size:0.7rem" :title="item.name"/>
                </van-sidebar>
            </div>
            <div class="rightCategory">
                <ContentView :rightCategoryList="rightCategoryList"/>
            </div>
        </div>
        <van-loading v-else type="spinner" color="#75a342"
                     style="position: absolute;left: 50%;top: 50%;transform: translate(-50%)">
            正在加载中....
        </van-loading>
    </div>
</template>

<script>
    import Header from "./components/Header";
    import ContentView from "./components/ContentView";
    import BScroll from 'better-scroll'
    import jq from 'jquery'
    //引入通知插件
    import PubSub from "pubsub-js"
    import {Toast} from 'vant';
    //引入vuex
    import {mapMutations} from 'vuex'

    export default {
        name: "Category",
        components: {ContentView, Header},
        data() {
            return {
                showLoading: true,
                activeKey: 0,
                leftCategoryList: [],
                rightCategoryList: [],
                leftScroll: null,
            };
        },
        methods: {
            ...mapMutations(["ADD_GOODS"]),
            onChange(index) {
                let el = jq(".van-sidebar-item")[index];
                let vm = this;
                vm.axios.get(vm.API.CHANGE_CATEGORY_URL, {params: {categoryId: el.getAttribute("data")}}).then(res => {
                    vm.rightCategoryList = res.data;
                })
                this.leftScroll.scrollToElement(el, 300);
            },
            async initData() {
                let vm = this;
                await vm.axios.get(vm.API.HOME_NAV_FINDALL_URL).then(res => {
                    vm.leftCategoryList = res.data;
                });
                await vm.axios.get(vm.API.CHANGE_CATEGORY_URL, {params: {categoryId: 1}}).then(res => {
                    console.log(res);
                    vm.rightCategoryList = res.data;
                });
                /**
                 * 一个事件循环就是一个Tick
                 * 数据下来   对界面进行改动
                 * 在Dom更新时做一些初始化组件或者动画
                 */
                this.showLoading = false;
                this.$nextTick(() => {
                    this.leftScroll = new BScroll(".leftCategory", {
                        probeType: 3,
                        click: true,
                        scrollY: true,
                        tap: true,
                        mouseWheel: true
                    });
                })
            }
        },
        created() {
            this.initData();
        },
        mounted() {
            this.showLoading = false
        },
    }
</script>

<style scoped>
    #category {
        width: 100%;
        height: 100%;
        background-color: #f5f5f5;
        padding-bottom: 50px;
    }

    .content {
        display: flex;
        width: 100%;
        height: 100%;
        padding-bottom: 50px;

    }

    .leftCategory {
        flex: 1;
        overflow: hidden;
        padding-bottom: 50px;

    }

    .rightCategory {
        flex: 3;
        height: 100%;
    }

    .van-sidebar {
        width: 100%
    }
</style>
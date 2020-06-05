<template>
    <div id="addAddress">
        <van-nav-bar
                title="我的地址"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <van-address-edit
                :area-list="areaList"
                show-set-default
                :search-result="searchResult"
                style="margin-top: 3rem;"
                @save="onSave"
                @change-detail="onChangeDetail"
        />
    </div>
</template>

<script>
    import {Toast} from 'vant';
    import areaList from './../../../../config/area'
    import {mapState} from 'vuex'
    import PubSub from 'pubsub-js'
    export default {
        name: "AddAddress",
        data() {
            return {
                areaList: areaList,
                searchResult: [],
            };
        },
        computed:{
          ...mapState(['userInfo']),
        },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
            onSave(data) {
                let obj = {
                    contact: data.name,
                    provinceId: data.province,
                    cityId: data.city,
                    townId: data.county,
                    mobile: data.tel,
                    address: data.addressDetail,
                    isDefault: data.isDefault,
                    alias: data.areaCode,
                }
                if (obj.isDefault === true) {
                    obj.isDefault = "1"
                } else {
                    obj.isDefault = "0"
                }
                let vm = this
                vm.axios.post(vm.API.ADDRESS_ADD_URL+"?username="+this.userInfo.username,obj).then(res=>{
                    if(res.data.success){
                        Toast({
                            message:res.data.message,
                            duration:800,
                        })
                        vm.$router.back()
                        PubSub.publish("addAddressSuccess");
                    }else{
                        Toast({
                            message:res.data.message,
                            duration:800,
                        })
                    }
                })
            },
            onChangeDetail(val) {
                if (val) {
                    this.searchResult = [
                        {
                            name: '黄龙万科中心',
                            address: '杭州市西湖区',
                        },
                    ];
                } else {
                    this.searchResult = [];
                }
            },
        },
        beforeDestroy() {
            PubSub.unsubscribe('addAddressSuccess')
        }
    }
</script>

<style scoped>
    #addAddress {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: #f5f5f5;
        z-index: 9999;
    }
</style>
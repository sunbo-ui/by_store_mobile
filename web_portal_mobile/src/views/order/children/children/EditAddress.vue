<template>
    <div id="editAddress">
        <van-nav-bar
                title="我的地址"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <van-address-edit
                :area-list="areaList"
                show-delete
                :address-info="addressInfo"
                show-set-default
                style="margin-top: 3rem;"
                @save="onSave"
                @delete="onDelete"
                @change-detail="onChangeDetail"
        />
    </div>
</template>

<script>
    import {Toast} from 'vant';
    import areaList from './../../../../config/area'
    import {mapState} from 'vuex'

    export default {
        name: "EditAddress",
        data() {
            return {
                addressInfo: {},
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
                    id:this.$route.query.id,
                    userId:this.userInfo.username,
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
                vm.axios.post(vm.API.ADDRESS_UPDATE_URL,obj).then(res=>{
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
            onDelete() {
                let vm = this;
                vm.axios.get(vm.API.ADDRESS_REMOVE_URL+"?id="+this.$route.query.id).then(res=>{
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
            loadAddress(id) {
                let vm = this;
                vm.axios.get(vm.API.ADDRESS_FINDONE_URL + "?id=" + id).then(res => {
                    console.log(res);
                    let obj = {
                        id:res.data.id,
                        name: res.data.contact,
                        province: res.data.provinceId,
                        city: res.data.cityId,
                        county: res.data.townId,
                        tel: res.data.mobile,
                        addressDetail: res.data.address,
                        isDefault: res.data.isDefault,
                        areaCode: res.data.alias,
                    }
                    if (res.data.isDefault === '1') {
                        obj.isDefault = true
                    } else {
                        obj.isDefault = false
                    }
                    vm.addressInfo = obj;
                })
            }
        },
        created() {
            let id = this.$route.query.id;
            this.loadAddress(id);
        }
    }
</script>

<style scoped>
    #editAddress {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        background-color: #f5f5f5;
        z-index: 9999;
    }
</style>
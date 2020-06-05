<template>
    <div id="myAddress">
        <van-nav-bar
                title="我的地址"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <van-address-list
                v-model="chosenAddressId"
                @select="onChangeDefault"
                :list="list"
                @add="onAdd"
                @edit="onEdit"
                default-tag-text="默认"
                style="margin-top: 3rem;"
        />


        <transition mode="out-in" name="router-slider">
            <router-view/>
        </transition>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import PubSub from 'pubsub-js'
    import {Toast} from 'vant';
    export default {
        name: "MyAddress",
        data() {
            return {
                chosenAddressId: '1',
                list: [],

            };
        },
        computed: {
            ...mapState(['userInfo'])
        },
        methods: {
            onClickLeft() {
                this.$router.back()
            },
            onAdd() {
                this.$router.push('/confirmOrder/myAddress/addAddress')
            },
            onEdit(data) {
                this.$router.push({
                    path: '/confirmOrder/myAddress/editAddress',
                    query: {
                        id: data.id
                    }
                })
            },
            onLoadAddress() {
                let vm = this;
                vm.axios(vm.API.ADDRESSS_LOAD_URL + "?username=" + this.userInfo.username).then(res => {
                    let arr = res.data;
                    let newArr = [];
                    arr.forEach((item, index) => {
                        let obj = {
                            id: item.id,
                            name: item.contact,
                            tel: item.mobile,
                            address:item.provinceId+item.cityId+item.townId+ item.address,
                            isDefault: true,
                        }
                        if (item.isDefault === '1') {
                            vm.chosenAddressId = item.id,
                                obj.isDefault = true
                        } else {
                            obj.isDefault = false
                        }
                        newArr[index] = obj;
                    })
                    vm.list = newArr;
                })
            },
            onChangeDefault(data){
                let vm = this
                vm.axios.get(vm.API.ADDRESS_UPDATEDEFAULT_URL+"?id="+data.id+"&username="+this.userInfo.username).then(res=>{
                    if(res.data.success){
                        Toast({
                            message:res.data.message,
                            duration:800,
                        })
                        PubSub.publish("userAddress",data);
                        this.$router.back()
                    }else{
                        Toast({
                            message:res.data.message,
                            duration:800,
                        })
                    }
                })
            }
        },
        created() {
            this.onLoadAddress();
        },
        mounted() {
            PubSub.subscribe('addAddressSuccess',(msg)=>{
                if(msg ==='addAddressSuccess'){
                    this.onLoadAddress();
                }
            })
        },
        beforeDestroy() {
            PubSub.unsubscribe('addAddressSuccess')
        }
    }
</script>

<style scoped>
    #myAddress {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 200;
        background-color: #f5f5f5;
    }

    .router-slider-enter-active, .router-slider-leave-active {
        transition: all 0.5s;
    }

    .router-slider-enter, .router-slider-leave-active {
        transform: translate3d(2rem, 0, 0);
        opacity: 0;
    }
</style>
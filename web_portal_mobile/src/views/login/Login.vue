<template>
    <div id="login">
        <van-nav-bar
                title="登录"
                left-text="返回"
                :fixed=true
                left-arrow
                @click-left="onClickLeft"
        ></van-nav-bar>
        <div class="border">
            <div class="yuan_fu">
                <div class="yuan"></div>
            </div>
            <div>
                <van-form @submit="onSubmit">
                    <van-field
                            style="margin-top: 50px;"
                            v-model="username"
                            name="username"
                            label="用户名"
                            placeholder="用户名"
                            :rules="[{ required: true, message: '请填写用户名' }]"
                    />
                    <van-field
                            v-model="password"
                            type="password"
                            name="password"
                            label="密码"
                            placeholder="密码"
                            :rules="[{ required: true, message: '请填写密码' }]"
                    />
                    <van-button style="margin-top: 20px;" round block type="primary" native-type="submit">
                        登录
                    </van-button>
                </van-form>
                <van-button style="margin-top: 10px;margin-bottom: 10px;" round block type="warning">
                    注册
                </van-button>
            </div>
        </div>
    </div>
</template>

<script>
    import {Toast} from 'vant';
    import qs from "qs";
    import {mapActions} from 'vuex'
    export default {
        name: "Login",
        data() {
            return {
                username: '',
                password: '',
                userInfo: {},
            };
        },
        methods: {
            ...mapActions(['syncUserInfo']),
            onSubmit(value) {
                let vm = this;
               // console.log(value);
                vm.axios.post(vm.API.LOGIN_URL, qs.stringify(value),
                    {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
                ).then(res => {
                    console.log(res);
                    if (res.data.status == 200) {
                        this.userInfo.username = res.data.result;
                        this.userInfo.token = res.headers.authorization;
                        this.syncUserInfo(this.userInfo);
                        Toast({
                            message:"登录成功",
                            duration:1000
                        });
                        this.$router.back();
                    } else {
                        Toast({
                            message:"登录失败, 用户名或密码错误",
                            duration:1000
                        });
                    }
                });
            },
            onClickLeft() {
                this.$router.back();
            }
        },
    }
</script>

<style scoped>
    #login {
        background-color: #f5f5f5;
        width: 100%;
        height: 100%;

    }

    #login .border {
        background: #fff;
        width: 90%;
        border-radius: 4px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        position: relative;
        position: absolute;
        top: 50%;
        left:50%;
        transform: translateY(-50%) translateX(-50%);
        display: flex;
        align-items: center;
        justify-content: center;
    }

    #login .yuan {
        width: 100px;
        height: 100px;
        border-radius: 50%;
        background: url("./images/logo.png");
        background-size: contain;
        position: absolute;
        left: 0px;
        top: 6px;
        box-shadow: 0 2px 35px 0 #ffffff;
    }

    #login .yuan_fu {
        width: 110px;
        height: 110px;
        border-radius: 50%;
        background: #fff;
        position: absolute;
        border: 2px solid gainsboro;
        left: 50%;
        top: -25%;
        transform: translate(-50%);
        box-shadow: 0 2px 35px 0 #ffffff;
        z-index: 9999;
    }

    .van-field {
        height: 3rem;
    }
</style>
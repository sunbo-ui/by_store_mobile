<template>
    <swiper id="sowing" :options="swiperOptions">
        <swiper-slide v-for="(item,index) in sowing_list" :key="index">
            <img :src="item.pic" alt="">
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
</template>

<script>
    import {Swiper, SwiperSlide, directive} from 'vue-awesome-swiper'
    import 'swiper/css/swiper.css'

    export default {
        name: "Sowing",
        components: {
            Swiper,
            SwiperSlide
        },
        props:{
            sowing_list:Array,
        },
        data() {
            return {
                swiperOptions: {
                    //组件不会通过nextTick来实例化  就能第一时间拿到swiper,否则需要等到数据加载下来才能拿到
                    notNextTick: true,
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                    loop: true,
                    autoplay: {
                        delay: 1000,
                    },
                    slideToClickedSlide: true,
                    speed: 600,
                    on: {
                        //滑动后回调函数
                        slideChangeTransitionEnd() {
                        }
                    }
                }
            }
        },
    }
</script>

<style scoped>
    #sowing {
        width: 100%;
        height: 13rem;
    }
    #sowing img {
        width: 100%;
        height: 100%;
    }
    /*
        拿到父类直接做穿透修改样式
    */
    #sowing >>> .swiper-pagination-bullet-active{
        background-color: #1AFA29 ;
    }
</style>
<template>
    <div id="homeHeader">
        <div style="flex: 1;position: relative;">
            <img src="./image/position.svg"
                 style="height: 0.8rem;width: 0.8rem;position: absolute;top:25px;left: 10px;transform: translateY(-50%)"
                 alt="">
            <section style="position: absolute;top: 25px;left: 40px;transform: translateY(-50%)">
                <span class="address">{{city ||"湖南长沙"}}</span>
                <img src="./image/bottom.svg" style="height: 1rem;width: 1rem;margin-left: 3px;" alt="">
            </section>
        </div>
        <div style="flex: 1">
            <button style="background-color: #f2f2f2;height: 35px;width: 200px;display: block;border-radius: 50px;position:relative;margin-top: 8px;margin-right: 5px;">
                <img src="./image/search.svg" style="height: 1rem;width: 1rem;position: absolute;left: 20px;top:8px"
                     alt="">
                <span style="position: absolute;left: 50px;top:8px;color: #999;font-size: 13px;">搜索你想要的东西趴</span>
            </button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data() {
            return {
                longitude: 0,//经度
                latitude: 0,//纬度
                city: '',
            }
        },
        mounted(){
            this.getLocation();
        },
        methods: {
            getLocation(){
                let geolocation = new qq.maps.Geolocation("JVCBZ-M7DRS-QHGOX-6TWOK-TKSXO-5ZFC7", "myapp");
                geolocation.getLocation(this.showPosition, this.showError);
            },
            showPosition(position){
                console.log(position);
                this.longitude = position.lng;
                this.latitude = position.lat;
                this.city = position.city;
            },
            showError(position){
                console.log("定位失败");
                this.getLocation();
            }
        }
    }
</script>

<style scoped>
    #homeHeader {
        width: 100%;
        height: 3rem;
        background-color: #fff;
        display: flex;
    }

    #homeHeader .address {
        font-size: 0.8rem;
    }
</style>
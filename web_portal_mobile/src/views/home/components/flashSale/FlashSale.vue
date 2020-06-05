<template>
    <div id="flashSale">
        <div class="header">
            <div class="flashTitle">
                <span style="margin-left: 10px;font-weight: bold;font-size: 1.2rem;">限时抢购</span>
            </div>
            <span class="flashTime">07:00场</span>
            <span class="flashTimeEnd">已结束</span>
            <span class="more">更多</span>
        </div>
        <div class="item">
            <FlashSaleItem :flash_list="flash_list"/>
        </div>
    </div>
</template>

<script>
    import FlashSaleItem from "./FlashSaleItem";
    export default {
        name: "FlashSale",
        props:{
            flash_list:Array,
        },
        components: {FlashSaleItem}
    }
</script>

<style scoped>
    #flashSale {
        width: 100%;
        height: 17rem;
        background-color: #fff;
        margin-top: 1rem;
    }
    .header{
        width: 100%;
        height: 70px;
        padding: 20px 10px 10px 10px;
    }
    .flashTitle{
        border-left: 5px solid #3BA068;
        display: inline-block;
    }
    .flashTime{
        margin-left: 10px;
        font-size: 0.8rem;
        color: red;
    }
    .flashTimeEnd{
        border: 1px solid #cccccc;
        margin-left: 20px;
        font-size: 0.9rem;
        padding: 5px;
        border-radius: 10px ;
        color:#c6c8ca;
    }
    .more{
        float: right;
        margin-right: 5px;
        color: #3cb963;
    }
</style>
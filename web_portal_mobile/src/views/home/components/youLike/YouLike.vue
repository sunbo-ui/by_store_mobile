<template>
    <div class="youLike">
        <div style="text-align: center;position: relative;margin-top: 1rem;">
            <div class="line leftLine"></div>
            <div  style="display: inline-block;font-size: 1.2rem;">猜你喜欢</div>
            <div class="line rightLine"></div>
        </div>
        <div>
            <YouLikeItem :youLike_list="youLike_list"/>
        </div>
    </div>
</template>

<script>
    import YouLikeItem from "./YouLikeItem";
    export default {
        name: "YouLike",
        props:{
            youLike_list:Array,
        },
        components: {YouLikeItem}
    }
</script>

<style scoped>
    .youLike{
        width: 100%;
        height: 100%;
        padding-bottom: 50px;
    }
    .line{
        border-bottom: 1px solid #ccc;
        display: inline-block;
        width: 30%;
    }
    .leftLine{
        position:absolute;
        top:50%;
        left: 5%;
    }
    .rightLine{
        position:absolute;
        top:50%;
        right:5%;
    }
</style>
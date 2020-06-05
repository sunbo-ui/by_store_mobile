<template>
    <div id="categoryHeader">
        <div>
            <button class="btnCategory">
                <img src="./images/search.svg" class="categoryImage"
                     alt="">
                <span style="position: absolute;left: 50px;top:8px;color: #999;font-size: 13px;">搜索你想要的东西趴</span>
            </button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Header"
    }
</script>

<style scoped>
    #categoryHeader {
        width: 100%;
        height: 3rem;
        background-color: #fff;
        padding: 0.5rem 1rem 0.2rem 1rem;
    }

    .btnCategory {
        background-color: #f2f2f2;
        height: 35px;
        width: 100%;
        border-radius: 50px;
        position: relative;
    }

    .categoryImage {
        height: 1rem;
        width: 1rem;
        position: absolute;
        left: 20px;
        top: 8px
    }
</style>
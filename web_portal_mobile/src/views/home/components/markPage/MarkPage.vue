<template>
    <div>
        <div class="markPage" @click="scrollToTop" >
            <img style="width: 100%;height: 100%;" src="./image/goback.svg" alt="">
        </div>
    </div>
</template>

<script>
    export default {
        name: "MarkPage",
        props: {
            scrollToTop: Function
        }
    }
</script>

<style scoped>
    .markPage {
        width: 3rem;
        height: 3rem;
        border-radius: 50%;
        background-color: lightskyblue;
        position: fixed;
        bottom: 4rem;
        right: 1rem;
        padding: 10px;
    }
</style>
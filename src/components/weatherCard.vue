<template>
    <div id="card">
        <search/>
        <weather/>
    </div>
</template>

<script>
    import search from './search.vue';
    import weather from './weather.vue';
    export default {
        components : {search, weather},       
    }
</script>

<style scoped>
    #card {
        background: linear-gradient(to bottom, #00ddb9, #58598c);
        width: 550px;
        height: 650px;
        border-radius: 20px;
        margin: auto;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
    }
</style>
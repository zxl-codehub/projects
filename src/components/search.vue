<template>
    <div id="search">
        <input type="text" placeholder="请输入城市名称" id="search-input" v-model="place" @keyup.enter="getWeather">
        <button id="btn" @click="getWeather">查询</button>
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        data : function() {
            return {
                place : '',
            }
        },
        methods : {
            getWeather : function() {
                let place = this.place.trim(' ');
                if (place === '') return;
                axios.get('/api/v3/geocode/geo?address='+place+'&key=8a4ad6956675b4ac576653624eac4554')
                .then(response => {
                    this.place = '';
                    if (response.data.geocodes === undefined) {
                        this.$root.$emit('searchToWeather', false);
                        return;
                    }
                    let placeObj = response.data.geocodes[0];
                    this.$root.$emit('searchToWeather', true, placeObj.adcode);
                })
                .catch(error => {
                    console.log(error);
                });
            }
        },
        mounted : function() {
            axios.get('/api/v3/ip?key=8a4ad6956675b4ac576653624eac4554')
            .then(response => {
                this.place = Array.isArray(response.data.city) ? '北京' : response.data.city;
                this.getWeather();
            })
            .catch(error => {
                console.log(error);
            });
        }
    }
</script>

<style scoped>
    #search {
        position: absolute;
        left: 30px;
        top: 40px;
    }
    #search-input {
        width: 400px;
        height: 50px;
        border-radius: 20px;
        font-size: 25px;
        margin-right: 30px;
        padding-left: 10px;
        background-color: transparent;
        border: 3px skyblue solid;
        transition: background 2s, box-shadow 2s, color 2s;
        color: #999;
    }
    #search-input:focus {
        background-color: white;
        border: 3px transparent solid;
        transition: background 2s, box-shadow 2s, color 2s;
        box-shadow: 0px 0px 20px 10px white;
        outline: none;
        color: black;
    }
    #btn {
        position: absolute;
        bottom: 3px;
        font-size: 15px;
        width: 50px;
        height: 50px;
        border-radius: 25px;
        cursor: pointer;
        background-color: transparent;
        border: 3px gold solid;
        color: #777;
        transition: background 2s;
        font-weight: bold;
    }
    #btn:hover {
        transition: background 0.5s, box-shadow 0.5s;
        background-color: gold;
        color: white;
        box-shadow: 0px 0px 20px 10px gold;
    }
    #btn:active {
        box-shadow: 0px 0px 20px 10px red;
        transition: background 0.2s, box-shadow 0.2s;
        background-color: red;
        color: white;
    }
</style>
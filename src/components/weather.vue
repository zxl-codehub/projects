<template>
    <div id="weather" ref="weather" @selectstart.prevent >
        <div id="bar-mes" v-show="!isExist">对不起，您输入的城市名不存在</div>
        <div id="content" v-show="isExist">
            <div id="main-info">
                <div id="weather-msg">{{weather}}</div>
                <div id="temp">{{temp}}℃</div>
                <div id="city">{{city}}</div>
            </div>
            <div id="side-info">
                <div id="shidu">
                    {{shidu}}%
                    <div id="font-shidu">湿度</div>
                </div>
                <div id="blank"></div>
                <div id="wind-power">
                    {{windpower}}m/s
                    <div id="font-wind-power">风力</div>
                </div>
            </div>
            <div id="update-time">最后一次更新时间：{{lastUpdateTime}}</div>
        </div>
        
    </div>
</template>

<script>
    import axios from 'axios';
    export default {
        data : function() {
            return {
                isExist : false,
                weather : '',
                temp : '',
                city : '',
                shidu : '',
                windpower : '',
                lastUpdateTime : ''
            }
        },
        mounted : function() {
            this.$root.$on('searchToWeather', this.getWeather);
        },
        methods : {
            getWeather : function(isExist, adcode) {
                this.isExist = isExist;
                if (!isExist) return;
                axios.get('/api/v3/weather/weatherInfo?city='+adcode+'&key=8a4ad6956675b4ac576653624eac4554')
                .then(response => {
                    let weatherObj = response.data.lives[0];
                    this.weather = weatherObj.weather;
                    this.temp = weatherObj.temperature;
                    this.city = weatherObj.city;
                    this.shidu = weatherObj.humidity;
                    this.windpower = weatherObj.windpower;
                    this.lastUpdateTime = weatherObj.reporttime;
                })
                .catch(error => {
                    console.log(error);
                });
            }
        }
    }
</script>

<style scoped>
    #bar-mes {
        position: absolute;
        top: 100px;
        right: 100px;
    }
    #weather {
        color: white;
        cursor: default;
    }
    #main-info {
        text-align: center;
        position: absolute;
        left: 160px;
        top: 120px;
    }
    #weather-msg {
        font-size: 90px;
        margin-bottom: 30px;
    }
    #temp {
        font-size: 100px;
    }
    #city {
        font-size: 50px;
    }
    #side-info {
        position: absolute;
        bottom: 70px;
    }
    #shidu {
        font-size: 40px;
        display: inline-block;
        padding-left: 30px;
    }
    #font-shidu {
        font-size: 20px;
    }
    #blank {
        display: inline-block;
        width: 270px;
    }
    #wind-power {
        font-size: 40px;
        display: inline-block;
        
    }
    #font-wind-power {
        font-size: 20px;
    }
    #update-time {
        font-size: 20px;
        position: absolute;
        right: 30px;
        bottom: 20px;
    }
</style>
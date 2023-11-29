import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false;

new Vue({
  el : '#root',
  render : function(createElement) {
    return createElement(App);
  }
});
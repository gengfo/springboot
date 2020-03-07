import Vue from 'vue'
import App from './App'
import router from '@/router/index.js'
import axios from '@/api/index.js'
import store from '@/store/index.js'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
/* 国际化语言包 */
import vueI18n from 'vue-i18n'
/* 事件总线 */
import VueBus from 'vue-bus'
/* CSS动画库 */
import 'animate.css'
import 'woah.css'
/* 粒子库 */
import VueParticles from 'vue-particles'
/* 工具类 */
import utils from '@/util/index.js'

Vue.use(VueParticles)
Vue.use(iView)
Vue.use(vueI18n)
Vue.use(VueBus)

Vue.config.productionTip = false
// 将API方法绑定到全局,在其他vue组件中可以使用this.$axios调用
Vue.prototype.$axios = axios
Vue.prototype.$store = store
Vue.prototype.$utils = utils

/* eslint-disable */
new Vue({
  el: '#app',
  router,
  render: h => h(App)
})

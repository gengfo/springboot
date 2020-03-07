import Vue from 'vue'
import Vuex from 'vuex'
import getters from '@/store/getters.js'
import userStore from '@/store/modules/user-store.js'
import routerStore from '@/store/modules/router-store.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    userStore,
    routerStore
  },
  getters
})

export default store

const getters = {
  // 获取用户TOKEN
  getToken (state) {
    return state.userStore.token
  },
  getRoutes (state) {
    return state.routerStore.routes
  }
}

export default getters

const routerStore = {
  state: {
    routes: []
  },
  mutations: {
    setRoutes (state, routes) {
      state.routes = routes
    }
  },
  actions: {
    initRoutes (context, token) {
      return new Promise((resolve, reject) => {
      })
    }
  }
}

export default routerStore

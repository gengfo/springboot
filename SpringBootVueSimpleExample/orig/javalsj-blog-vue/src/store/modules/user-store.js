// 初始化从localStorage拉取用户信息
const JWT_TOKEN_KEY = 'JWT_TOKEN'

function getToken () {
  return localStorage.getItem(JWT_TOKEN_KEY)
}

const userStore = {
  state: {
    token: getToken()
  },
  mutations: {
    // 设置TOKEN
    setToken (state, token) {
      localStorage.setItem(JWT_TOKEN_KEY, token)
      state.token = token
    },
    // 清除TOKEN信息
    clearToken (state) {
      localStorage.removeItem(JWT_TOKEN_KEY)
      state.token = ''
    }
  }
}

export default userStore

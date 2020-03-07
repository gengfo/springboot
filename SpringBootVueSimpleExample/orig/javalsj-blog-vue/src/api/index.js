import axios from 'axios'
import store from '@/store/index.js'
import router from '@/router/index.js'
import httpStatuCodes from '@/util/modules/http-status-code-util.js'
import { Message } from 'iview'

axios.defaults.baseURL = process.env.SERVER_API_BASE_URL
axios.defaults.timeout = 5000

/* http request 拦截器 */
axios.interceptors.request.use(
  config => {
    let jwtToken = store.getters.getToken
    if (jwtToken !== '') {
      // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Authorization = `${jwtToken}`
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

/* http response 拦截器 */
axios.interceptors.response.use(
  response => {
    switch (response.data.code) {
      // 返回200，将响应数据返回
      case httpStatuCodes.CODE_200:
        break
      // 返回401，清除token信息并跳转到登录页面
      case httpStatuCodes.CODE_401:
        store.commit('clearToken')
        let loginPath = ''
        if (router.currentRoute.fullPath.startsWith('/admin')) {
          loginPath = '/admin/login'
        } else {
          loginPath = '/login'
        }
        router.replace({
          path: loginPath,
          query: {
            redirect: router.currentRoute.fullPath
          }
        })
        break
      // 返回其他响应码直接抛异常
      default:
        Message.error(response.data.message)
        break
    }
    return response.data
  },
  error => {
    // 返回接口返回的错误信息
    Message.error('网络出现异常：' + error.message)
    return Promise.reject(error.data)
  }
)

export default axios

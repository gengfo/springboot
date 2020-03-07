import Vue from 'vue'
import iView from 'iview'
import Router from 'vue-router'
import homeRoutes from '@/router/modules/home-routes.js'
import adminRoutes from '@/router/modules/admin-routes.js'
import store from '@/store/index.js'
import utils from '@/util/index.js'

Vue.use(Router)

/* 使用前端路由，当切换到新路由时，想要页面滚到顶部，或者是保持原先的滚动位置 */
const scrollBehavior = (to, from, savedPosition) => {
  if (savedPosition) {
    return savedPosition
  } else {
    return { x: 0, y: 0 }
  }
}

/* 注册路由 */
const routes = [
  // 前台面板路由
  ...homeRoutes,
  // 管理面板路由
  ...adminRoutes,
  // 当页面地址和上面任一地址不匹配，则跳转到404(该路由放最后)
  { path: '*', redirect: '/404' }
]

/* 创建路由对象 */
const router = new Router({
  // 启用history模式，vue-router 默认是hash模式，即使用url的hash来模拟一个完整的url，于是当url改变时，页面不会重新加载，并且url中会有#
  mode: 'history',
  scrollBehavior,
  routes
})

/* 利用vue-router提供的钩子函数beforeEach()对路由进行权限判断 */
router.beforeEach((to, from, next) => {
  iView.LoadingBar.start()
  if (!to.matched.some(record => record.meta.requiresAuth)) {
    // 判断该路由是否需要登录权限，无需权限验证直接跳转
    next()
    return
  }
  // JWT 用户权限校验，判断 TOKEN 是否在 localStorage 当中
  let jwtToken = store.getters.getToken
  if (utils.StringUtil.isNotEmpty(jwtToken)) {
    // 有登录权限且已登陆则直接跳转（通过vuex state获取当前的token是否存在）
    next()
    return
  }
  // 无登录权限判断路由是管理平台还是主页平台
  if (to.fullPath.startsWith('/admin')) {
    // 管理平台路由直接跳转至管理登录页面
    next({
      path: '/admin/login',
      // 将跳转的路由path作为参数，登录成功后跳转到该路由
      query: {
        redirect: to.fullPath
      }
    })
  } else {
    // 主页平台路由直接跳转至主页登录页面
    next({
      path: '/login',
      query: {
        redirect: to.fullPath
      }
    })
  }
})

router.afterEach((to) => {
  iView.LoadingBar.finish()
  window.scrollTo(0, 0)
})

export default router

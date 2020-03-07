const BlogHome = resolve => require(['@/components/views/home/BlogHome.vue'], resolve)
const BlogIndex = resolve => require(['@/components/views/home/index/BlogIndex.vue'], resolve)

// 前台面板路由
export default [
  {
    path: '/',
    component: BlogHome,
    children: [
      { path: '', name: '博客主页入口', redirect: 'index' },
      { path: 'index', name: '博客主页', component: BlogIndex }
    ]
  }
]

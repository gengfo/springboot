const BlogAdminIndex = resolve => require(['@/components/views/admin/index/BlogAdminIndex.vue'], resolve)
const BlogAdminLogin = resolve => require(['@/components/views/admin/login/BlogAdminLogin.vue'], resolve)
const BlogAdmin = resolve => require(['@/components/views/admin/BlogAdmin.vue'], resolve)

// 管理面板路由
export default [
  {
    path: '/admin',
    component: BlogAdmin,
    children: [
      { path: '', name: '管理平台入口', redirect: 'index' },
      { path: 'login', name: '管理平台登录页', component: BlogAdminLogin },
      { path: 'index', name: '管理平台主页', component: BlogAdminIndex, meta: { requiresAuth: true, title: '管理平台主页', icon: 'el-icon-menu' } }
    ]
  }
]

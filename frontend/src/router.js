import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/job/list'
  },
  {
    path: '/job/list',
    name: 'JobList',
    component: () => import('./components/JobList.vue'),
    meta: { title: '岗位列表' }
  },
  {
    path: '/job/add',
    name: 'JobAdd',
    component: () => import('./components/JobAdd.vue'),
    meta: { title: '添加岗位' }
  },
  {
    path: '/job/edit/:id',
    name: 'JobEdit',
    component: () => import('./components/JobEdit.vue'),
    meta: { title: '编辑岗位' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 设置页面标题
router.beforeEach((to, from, next) => {
  document.title = to.meta.title || 'IT岗位求职记录系统'
  next()
})

export default router
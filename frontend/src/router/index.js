import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/dashboard',
    component: () => import('../views/Layout.vue'),
    children: [
      {
        path: '/user',
        name: 'UserManagement',
        component: () => import('../views/application/UserManagement.vue')
      },
      {
        path: '/goods',
        name: 'GoodsManagement',
        component: () => import('../views/application/GoodsManagement.vue')
      },
      {
        path: '/category',
        name: 'CategoryManagement',
        component: () => import('../views/application/CategoryManagement.vue')
      },
      {
        path: '/order',
        name: 'OrderManagement',
        component: () => import('../views/application/OrderManagement.vue')
      },
      {
        path: '/config',
        name: 'SystemConfig',
        component: () => import('../views/system/SystemConfig.vue')
      },
      {
        path: '/system-user',
        name: 'SystemUser',
        component: () => import('../views/system/SystemUser.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 暂时注释掉登录验证
// router.beforeEach((to, from, next) => {
//   const token = localStorage.getItem('token')
//   
//   if (to.path === '/login') {
//     if (token) {
//       next('/dashboard')
//     } else {
//       next()
//     }
//   } else {
//     if (token) {
//       next()
//     } else {
//       next('/login')
//     }
//   }
// })

export default router
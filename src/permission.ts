import router from './router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { Message } from 'element-ui'
import { Route } from 'vue-router'
import { UserModule } from '@/store/modules/user'
import Cookies from 'js-cookie'

NProgress.configure({ 'showSpinner': false })

router.beforeEach(async (to: Route, _: Route, next: any) => {
  NProgress.start()
  // if (Cookies.get('token')) {
  //   next()
  // } else {
  //   if (!to.meta || !to.meta.notNeedAuth) {
  //     next('/login')
  //   } else {
  //     next()
  //   }
  // }
  const isAuthenticated = !!Cookies.get('token')
  
  // 如果用户未登录
  if (!isAuthenticated) {
    // 如果目标路由需要认证，则重定向到登录页
    if (to.meta && !to.meta.notNeedAuth) {
      // 避免重复导航到登录页
      if (to.path !== '/login') {
        next({ path: '/login', query: { redirect: to.fullPath } })
      } else {
        next() // 已经在登录页，继续
      }
    } else {
      next() // 无需认证的路由，继续
    }
  } else {
    // 如果用户已登录，但访问登录页，则重定向到首页
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      next() // 已认证，继续访问目标路由
    }
  }
})


router.afterEach((to: Route) => {
  NProgress.done()
  if (to.meta && to.meta.title) {
    document.title = to.meta.title
  }
})

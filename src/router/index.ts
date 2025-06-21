import Vue, { h } from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Layout from "@/layout/index.vue";


Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path:'/login',
    component: () => import('@/views/login/index.vue'),
    meta: { title : '商户登录', hidden: true ,notNeedAuth: true }
  },
  // {
  //   path:'/',
  //   redirect: '/login',
  // },
  // {
  //   path:'/dashboard',
  //   component: Layout,
  //   // redirect: "/dashboard",
  //   // component: () =>import("@/views/dashboardtemp/index.vue"),
  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    children:
    [
      {
        path:'dashboard',
        name:'Dashboard',
        component: () => import("@/views/dashboard/index.vue"), 
        meta: {
            title: "工作台",
            icon: "dashboard",
            affix: true
          }
      },
      {
        path: "/statistics",
        component: () => import(/* webpackChunkName: "shopTable" */ "@/views/statistics/index.vue"),
          meta: {
            title: "数据统计",
            icon: "icon-statistics"
          }
      },
      {
        path: "/order",
        component: () => import(/* webpackChunkName: "shopTable" */ "@/views/orderDetails/index.vue"),
        meta: {
          title: "订单管理",
          icon: "icon-order"
        }
      },
      {
        path:'/employee',
        name:'Employee',
        component: () => import('@/views/employee/index.vue'),
        meta: { title: '员工管理' , icon: "icon-employee" }
      },
      {
        path:'/employee/add',
        name:'AddEmployee',
        component: () => import('@/views/employee/addEmployee.vue'),
        meta: { title: '添加员工',hidden: true }
      },
      {
        path:'/dish',
        name:'Dish',
        component: () => import('@/views/dish/index.vue'),
        meta: {
            title: "菜品管理",
            icon: "el-icon-dish"
          }
      },
      {
          path: "/dish/add",
          component: () =>
            import(/* webpackChunkName: "shopTable" */ "@/views/dish/addDishtype.vue"),
          meta: {
            title: "添加菜品",
            hidden: true
          }
        },
      {
        path:'/inform',
        name:'Inform',
        component: () => import('@/views/inform/index.vue')
      },
      {
        path:'/category',
        name:'Category',
        component:() => import('@/views/category/index.vue'),
        meta:{title: '分类管理', icon:'icon-category'}
      },
      {
        path:'/setmeal',
        name:'Setmeal',
        component: () => import('@/views/setmeal/index.vue'),
        meta:{title: '套餐管理', icon: 'icon-combo'}
      },
      {
        path: '/setmeal/add',
        name: 'AddSetmeal',
        component: ()=> import("@/views/setmeal/addSetmeal.vue"),
        meta: {
          title: '新增套餐',
          hidden: true
        }
      }

    ]
  },
  {
    path:'/404',
    component: () => import('@/views/404.vue'),
    meta: { title: "苍穹外卖", hidden: true, notNeedAuth: true }
  },
  {
    path: '*',
    redirect:'/login'
  }
]

const router = new VueRouter({
  routes,
  linkActiveClass: 'custom-active',
})

export default router

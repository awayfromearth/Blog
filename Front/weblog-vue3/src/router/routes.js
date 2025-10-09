import Index from "@/pages/frontend/index.vue"
import Login from "@/pages/admin/Login.vue"
import AdminIndex from "@/pages/admin/index.vue"

const routes = [
    {
        path: "/", // 路由地址
        component: Index, // 对应组件
        meta: { // meta 信息
            title: "Weblog 首页" // 页面标题
        }
    },
    {
        path: "/login",
        component: Login,
        meta: {
            title: "Weblog 登录页"
        }
    },
    {
        path: "/admin/index", // 后台首页
        component: AdminIndex,
        meta: {
            title: "Admin 后台首页"
        }
    }
]

export default routes
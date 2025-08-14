import Index from "@/pages/frontend/index.vue"
import Login from "@/pages/admin/Login.vue"
import { createRouter, createWebHashHistory } from "vue-router"

const routes = [
    {
        path: "/",
        component: Index,
        meta: {
            title: "Weblog 首页"
        }
    },
    {
        path: "/login",
        component: Login,
        meta: {
            title: "Weblog 登录页"
        }
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
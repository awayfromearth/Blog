import Index from "@/pages/frontend/index.vue"
import { createRouter, createWebHashHistory } from "vue-router"

const routes = [
    {
        path: "/",
        component: Index,
        meta: {
            title: "Weblog 首页"
        }
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
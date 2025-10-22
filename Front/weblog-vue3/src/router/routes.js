import Index from "@/pages/frontend/index.vue"
import Login from "@/pages/admin/Login.vue"
import AdminIndex from "@/pages/admin/index.vue"
import Admin from "@/layouts/admin/Admin.vue"

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
        path: "/admin",
        component: Admin,
        children: [
            {
                path: "/admin/index",
                component: AdminIndex,
                meta: {
                    title: "仪表盘"
                }
            },
            {
                path: "/admin/article/list",
                component: () => import("@/pages/admin/ArticleList.vue"),
                meta: {
                    title: "文章管理"
                }
            },
            {
                path: "/admin/category/list",
                component: () => import("@/pages/admin/CategoryList.vue"),
                meta: {
                    title: "类别管理"
                }
            },
            {
                path: "/admin/tag/list",
                component: () => import("@/pages/admin/TagList.vue"),
                meta: {
                    title: "标签管理"
                }
            },
            {
                path: "/admin/blog/setting",
                component: () => import("@/pages/admin/BlogSetting.vue"),
                meta: {
                    title: "博客设置"
                }
            }
        ]
    }
]

export default routes
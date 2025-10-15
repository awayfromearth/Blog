import router from "."

import { getToken } from "@/utils/cookie"
import { showMessage } from "@/utils/message"
import { showPageLoading, hidePageLoading } from "@/utils/nprogress"

router.beforeEach((to, from, next) => {
  showPageLoading()
  // 若用户想访问后台（以 /admin 为前缀的路由）
  // 未登录，则强制跳转登录页
  let token = getToken()
  if (!token && to.path.startsWith("/admin")) {
    showMessage("请先登录", "warning")
    next({ path: "/login" })
  } else if (token && to.path ==="/login") {
    showMessage("请勿重复登录", "warning")
    next({ path: "/admin/index" })
  }
  else {
    next()
  }
})

router.afterEach((to, from) => {
  document.title = (to.meta.title || "") + " - Weblog"
  hidePageLoading()
})
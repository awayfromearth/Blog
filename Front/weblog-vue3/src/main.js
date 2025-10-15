import { createApp } from 'vue'
import { installIcons } from "@/utils/iconsInstaller"

import router from "./router/index"
import App from './App.vue'

import "animate.css"
import "@/assets/styles/main.css"
import "@/router/permission"
import "nprogress/nprogress.css"

const app = createApp(App)

installIcons(app)
app.use(router)
app.mount('#app')

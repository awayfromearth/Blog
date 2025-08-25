import "animate.css"
import "@/styles/main.css"

import { createApp } from 'vue'
import { install } from "@/utils/elementIconInstaller.js"
import App from '@/App.vue'
import router from '@/router'

const app = createApp(App)

install(app)
app.use(router)
app.mount('#app')

// 导入 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 注册图标
export function installIcons(app) {
  for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
}
import { defineConfig } from 'vite'
import { fileURLToPath ,URL } from "node:url"
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)) // 对应当前文件所在目录下的src目录的绝对文件路径
    }
  }
})

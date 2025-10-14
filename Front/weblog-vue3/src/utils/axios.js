import axios from "axios"

import { getToken } from "./cookie"
import { showMessage } from "@/utils/message.js"

// 创建 Axios 实例
const instance = axios.create({
  baseURL: "/api", // 你的 API 基础 URL
  timeout: 7000, // 请求超时时间
})

// 添加请求拦截器
instance.interceptors.request.use(config => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, error => {
  return Promise.reject(error)
})

// 添加响应拦截器
instance.interceptors.response.use(response => {
  return response.data
}, error => {
  let errorMessage = error.response.data.message || "请求失败"
  showMessage(errorMessage, "error")
  return Promise.reject(error)
})

// 暴露出去
export default instance
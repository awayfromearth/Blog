import axios from "@/utils/axios"

/**
 * 登录接口
 * @param username 用户名
 * @param password 密码
 * @returns { Promise<axios.AxiosResponse<any>> }
 */
export function login(username, password) {
  return axios.post("/login", { username, password })
}

/**
 * 获取当前登录用户信息
 * @returns { Promise<axios.AxiosResponse<any>> }
 */
export function getUserInfo() {
  return axios.get("admin/user/info")
}

/**
 * 修改密码
 * @param data 用户名和新密码
 * @returns { Promise<axios.AxiosResponse<any>> }
 */
export function updatePassword(data) {
  return axios.post("/admin/password/update", data)
}
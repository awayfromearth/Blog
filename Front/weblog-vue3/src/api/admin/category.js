import axios from "@/utils/axios"

/**
 * 添加分类
 * @param data 分类
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function addCategory(data) {
  return axios.post("/admin/category/add", data)
}
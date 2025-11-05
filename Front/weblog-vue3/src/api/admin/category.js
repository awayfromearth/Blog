import axios from "@/utils/axios"

/**
 * 添加分类
 * @param data 分类
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function addCategory(data) {
  return axios.post("/admin/category/add", data)
}

/**
 * 分页查询分类接口
 * @param data 页码、每页数据量、模糊查询的名称、时间范围等参数
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function getCategoryPageList(data) {
  return axios.post("/admin/category/list", data)
}

/**
 * 删除分类接口
 * @param id 分类 id
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function deleteCategory(id) {
  return axios({
    url: "/admin/category/delete",
    method: "DELETE",
    params: {
      id
    }
  })
}
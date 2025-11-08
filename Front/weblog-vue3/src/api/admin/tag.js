import axios from "@/utils/axios.js"

/**
 * 新增标签请求
 * @param data 标签名称数组
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function addTags(data) {
    return axios.post("/admin/tag/add", data)
}
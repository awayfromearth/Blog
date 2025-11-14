import axios from "@/utils/axios.js"

/**
 * 新增标签请求
 * @param data 标签名称数组
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function addTags(data) {
    return axios.post("/admin/tag/add", data)
}

/**
 * 分页查询标签
 * @param data 分页参数及查询参数
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function getTagPageList(data) {
    return axios.post("admin/tag/list", data)
}
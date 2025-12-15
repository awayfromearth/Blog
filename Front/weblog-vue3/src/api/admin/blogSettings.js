import axios from "@/utils/axios"

/**
 * 获取博客设置详情请求
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function getBlogSettingDetail() {
    return axios.get("/admin/blog/settings/detail")
}

/**
 * 更新博客设置接口
 * @param data 新博客设置信息
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function updateBlogSettings(data) {
    return axios.post("/admin/blog/settings/update", data)
}
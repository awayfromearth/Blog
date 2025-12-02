import axios from "@/utils/axios"

/**
 * 获取博客设置详情请求
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function getBlogSettingDetail() {
    return axios.get("/admin/blog/settings/detail")
}
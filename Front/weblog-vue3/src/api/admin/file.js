import axios from "@/utils/axios"

/**
 * 文件上传接口
 * @param form 文件表单数据
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export function uploadFile(form) {
    return axios.post("/admin/file/upload", form)
}
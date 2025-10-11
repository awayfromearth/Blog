export function showMessage(message = "提示内容", type = "success", customClass = "") {
    return ElMessage({
        type,
        message,
        customClass
    })
}
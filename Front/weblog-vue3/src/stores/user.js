import { defineStore } from "pinia"
import { ref } from "vue"
import { getUserInfo } from "@/api/admin/user.js"
import { removeToken } from "@/utils/cookie"

export const useUserStore = defineStore("user", () => {
    const userInfo = ref({})

    async function setUserInfo() {
        try {
            const { success, data } = await getUserInfo()
            if (success) {
                userInfo.value = data
            }
        } catch(e) {
            console.log(e)
        }
    }

    function logout() {
        removeToken()
        userInfo.value = {}
    }

    return { userInfo, setUserInfo, logout }
}, {
    persist: true
})
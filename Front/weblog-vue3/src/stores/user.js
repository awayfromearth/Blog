import { defineStore } from "pinia"
import { ref } from "vue"
import { getUserInfo } from "@/api/admin/user.js"

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

    return { userInfo, setUserInfo }
}, {
    persist: true
})
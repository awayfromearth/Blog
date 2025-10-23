import { useCookies } from "@vueuse/integrations/useCookies"

const TOKEN_KEY = "Authorization"
const TAB_LIST_LEY = "tabList"

const cookie = useCookies()

export function getToken() {
    return cookie.get(TOKEN_KEY)
}

export function setToken(token) {
    return cookie.set(TOKEN_KEY, token)
}

export function removeToken() {
    return cookie.remove(TOKEN_KEY)
}

export function getTabList() {
    return cookie.get(TAB_LIST_LEY)
}

export function setTabList(tabList) {
    return cookie.set(TAB_LIST_LEY, tabList)
}
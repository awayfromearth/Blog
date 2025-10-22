import { defineStore } from "pinia"
import { ref } from "vue"

export const useMenuStore = defineStore("menu", () => {
  const isMenuCollapsed = ref(false)

  function toggleMenuCollapsed() {
    isMenuCollapsed.value = !isMenuCollapsed.value
  }

  return { isMenuCollapsed, toggleMenuCollapsed }
})
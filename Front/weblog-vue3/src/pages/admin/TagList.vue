<script setup>
import { Search, RefreshRight } from "@element-plus/icons-vue"
import {
  ref,
  reactive
} from "vue"
import { addTags, getTagPageList } from "@/api/admin/tag"
import { showMessage } from "@/utils/message"
import { showModel } from "@/utils/model"
import { nanoid } from "nanoid"

import FormDialog from "@/components/FormDialog.vue"
import moment from "moment"

const form = reactive({
  name: ""
})

const formDialogRef = ref(null)

const inputtedTags = ref([])

function openDialog() {
  formDialogRef.value.open()
}

async function onSubmit() {
  if (inputtedTags.value.length === 0) {
    formDialogRef.value.close()
  } else {
    formDialogRef.value.switchLoading()
    const tags = inputtedTags.value.map(i => i.name)
    try {
      const { success, message } = await addTags({ tags })
      if (success) {
        showMessage("添加成功")
        formDialogRef.value.close()
        form.name = ""
        await getTableData()
      } else {
        showMessage(message, "error")
      }
      formDialogRef.value.switchLoading()
      formDialogRef.value.close()
      inputtedTags.value = []
    } catch(e) {
      console.log(e)
      formDialogRef.value.switchLoading()
    }
  }
}

const shortcuts = [
  {
    text: "最近一周",
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
      return [start, end]
    },
  },
  {
    text: '最近一个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
      return [start, end]
    },
  },
  {
    text: '最近三个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
      return [start, end]
    },
  }
]

const pickedDate = ref(null)
const searchTagName = ref("")
const current = ref(1)
const total = ref(0)
const size = ref(10)
const tableData = ref([])
const isTableLoading = ref(false)

const isInputShow = ref(false)

async function getTableData(p = current.value) {
  current.value = p
  isTableLoading.value = true
  try {
    let startDate = ""
    let endDate = ""
    if (pickedDate.value) {
      startDate = moment(pickedDate.value[0]).format("YYYY-MM-DD HH:mm:ss")
      endDate = moment(pickedDate.value[1]).format("YYYY-MM-DD HH:mm:ss")
    }
    const { data, current: currentPage, success, size: pageSize, total: totalCount } = await getTagPageList({
      current: current.value,
      size: size.value,
      name: searchTagName.value,
      startDate,
      endDate,
    })
    if (success) {
      tableData.value = data
      current.value = currentPage
      size.value = pageSize
      total.value = totalCount
    }
  } catch(e) {
    console.log(e)
  } finally {
    isTableLoading.value = false
  }
}

getTableData()

function handleSizeChange(chosenSize) {
  size.value = chosenSize
  getTableData(1)
}

function resetQueryParams() {
  searchTagName.value = ""
  pickedDate.value = null
}

function showDeleteCategoryConfirmModal(r) {
  showModel('是否确定要删除该标签？').then(async () => {
    try {
      // const { success, message } = await deleteTag(r.id)
      // if (success) {
      //   showMessage('删除成功')
      //   getTableData()
      // } else {
      //   showMessage(message, "error")
      // }
    } catch(e) {
      console.log(e)
    }
  }).catch(() => {
    console.log('取消了')
  })
}

function addTag() {
  const value = form.name.trim()
  if (value) {
    inputtedTags.value.push({
      id: nanoid(),
      name: value
    })
  }
  isInputShow.value = false

  form.name = ""
}

function removeTag(i) {
  inputtedTags.value.splice(i, 1)
}
</script>

<template>
  <div>
    <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
    <el-card shadow="never" class="mb-5">
      <!-- flex 布局，内容垂直居中 -->
      <div class="flex items-center">
        <el-text>标签名称</el-text>
        <div class="ml-3 w-52 mr-5"><el-input placeholder="请输入（模糊查询）" v-model="searchTagName" /></div>

        <el-text>创建日期</el-text>
        <div class="ml-3 w-30 mr-5">
          <!-- 日期选择组件（区间选择） -->
          <el-date-picker :shortcuts="shortcuts" v-model="pickedDate" type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" size="default" />
        </div>

        <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData(1)">查询</el-button>
        <el-button class="ml-3" :icon="RefreshRight" @click="resetQueryParams">重置</el-button>
      </div>
    </el-card>

    <el-card shadow="never">
      <!-- 新增按钮 -->
      <div class="mb-5">
        <el-button type="primary" @click="openDialog">
          <el-icon class="mr-1">
            <Plus />
          </el-icon>
          新增
        </el-button>
      </div>

      <!-- 分页列表 -->
      <el-table v-loading="isTableLoading" :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="name" label="分类名称" width="180">
          <template #default="scope">
            <el-tag class="ml-2" type="success">{{ scope.row.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" >
          <template #default="scope">
            <el-button type="danger" size="small" @click="showDeleteCategoryConfirmModal(scope.row)">
              <el-icon class="mr-1">
                <Delete />
              </el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="mt-10 flex justify-center">
        <el-pagination
            layout="total, sizes, prev, pager, next, jumper"
            :page-sizes="[10, 20, 50]"
            :small="false"
            :background="true"
            :total="total"
            v-model:current-page="current"
            v-model:page-size="size"
            @size-change="handleSizeChange"
            @current-change="getTableData"
        />
      </div>

    </el-card>

    <FormDialog ref="formDialogRef" title="添加文章标签" width="40%" @submit="onSubmit">
      <el-form>
        <el-form-item class="align-middle">
          <el-tag v-for="(t, i) in inputtedTags" :key="t.id" class="mx-1" closable :disable-transitions="false" @close="removeTag(i)">
            {{ t.name }}
          </el-tag>
          <span class="w-20">
            <el-input v-if="isInputShow" class="ml-1 w-20" size="small" v-model="form.name" @keyup.enter="addTag" />
            <el-button v-else class="button-new-tag ml-1" size="small" @click="isInputShow = true">
              + 新增标签
            </el-button>
          </span>
        </el-form-item>
      </el-form>
    </FormDialog>
  </div>
</template>
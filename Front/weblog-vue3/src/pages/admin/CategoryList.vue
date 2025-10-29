<script setup>
import { Search, RefreshRight } from "@element-plus/icons-vue"
</script>

<template>
  <div>
    <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
    <el-card shadow="never" class="mb-5">
      <!-- flex 布局，内容垂直居中 -->
      <div class="flex items-center">
        <el-text>分类名称</el-text>
        <div class="ml-3 w-52 mr-5"><el-input placeholder="请输入（模糊查询）" /></div>

        <el-text>创建日期</el-text>
        <div class="ml-3 w-30 mr-5">
          <!-- 日期选择组件（区间选择） -->
          <el-date-picker type="daterange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" size="default" />
        </div>

        <el-button type="primary" class="ml-3" :icon="Search">查询</el-button>
        <el-button class="ml-3" :icon="RefreshRight">重置</el-button>
      </div>
    </el-card>

    <el-card shadow="never">
      <!-- 新增按钮 -->
      <div class="mb-5">
        <el-button type="primary">
          <el-icon class="mr-1">
            <Plus />
          </el-icon>
          新增</el-button>
      </div>

      <!-- 分页列表 -->
      <el-table :data="[]" border stripe style="width: 100%">
        <el-table-column prop="name" label="分类名称" width="180" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" >
          <template #default="scope">
            <el-button type="danger" size="small">
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
            :total="50" />
      </div>

    </el-card>
  </div>
</template>
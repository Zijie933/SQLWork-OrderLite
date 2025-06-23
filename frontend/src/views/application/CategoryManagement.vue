<template>
  <div class="category-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>商品种类列表</span>
          <el-button type="primary" @click="handleAdd">添加种类</el-button>
        </div>
      </template>
      
      <el-table :data="categoryList" style="width: 100%">
        <el-table-column prop="name" label="种类名称" />
        <el-table-column prop="displayOrder" label="显示顺序" width="120" />
        <el-table-column prop="showStatus" label="是否显示" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.showStatus ? 'success' : 'info'">
              {{ scope.row.showStatus ? '显示' : '隐藏' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              :type="scope.row.showStatus ? 'warning' : 'success'"
              size="small"
              @click="handleDisplayStatusChange(scope.row)"
            >
              {{ scope.row.showStatus ? '隐藏' : '显示' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑种类对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="种类名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="displayOrder">
          <el-input-number v-model="form.displayOrder" :min="0" />
        </el-form-item>
        <el-form-item label="是否显示" prop="isDisplay">
          <el-switch v-model="form.showStatus" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

// 表格数据
const categoryList = ref([])

// 对话框数据
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)
const form = ref({
  id: '',
  name: '',
  displayOrder: 0,
  showStatus: true
})

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入种类名称', trigger: 'blur' }],
  displayOrder: [{ required: true, message: '请输入显示顺序', trigger: 'blur' }]
}

// 获取种类列表
const getCategoryList = async () => {
  try {
    const response = await axios.get('/api/goods-categories')
    categoryList.value = response.data
  } catch (error) {
    ElMessage.error('获取种类列表失败')
  }
}

// 添加种类
const handleAdd = () => {
  dialogTitle.value = '添加种类'
  form.value = {
    name: '',
    displayOrder: 0,
    isDisplay: true
  }
  dialogVisible.value = true
}

// 编辑种类
const handleEdit = (row) => {
  dialogTitle.value = '编辑种类'
  form.value = { ...row }
  dialogVisible.value = true
}

// 修改显示状态
const handleDisplayStatusChange = async (row) => {
  try {
    await axios.put(`/api/goods-categories/${row.name}/show-status`, {
      showStatus: !row.showStatus
    })
    ElMessage.success('状态修改成功')
    getCategoryList()
  } catch (error) {
    ElMessage.error('状态修改失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogTitle.value === '编辑种类') {
          const oldName = form.value.id || form.value.name;
          await axios.put(`/api/goods-categories/${oldName}`, {
            name: form.value.name,
            displayOrder: form.value.displayOrder,
            showStatus: form.value.showStatus
          })
        } else {
          await axios.post('/api/goods-categories', form.value)
        }
        ElMessage.success('保存成功')
        dialogVisible.value = false
        getCategoryList()
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }
  })
}

onMounted(() => {
  getCategoryList()
})
</script>

<style scoped>
.category-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
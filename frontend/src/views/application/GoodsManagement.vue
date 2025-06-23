<template>
  <div class="goods-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>商品列表</span>
          <el-button type="primary" @click="handleAdd">添加商品</el-button>
        </div>
      </template>
      
      <el-table :data="goodsList" style="width: 100%">
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="categoryName" label="种类" />
        <el-table-column prop="displayOrder" label="显示顺序" width="100" />
        <el-table-column prop="price" label="价格">
          <template #default="scope">
            ¥{{ scope.row.price.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column prop="isSell" label="是否上架" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.isSell ? 'success' : 'info'">
              {{ scope.row.isSell ? '已上架' : '已下架' }}
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
              :type="scope.row.isSell ? 'warning' : 'success'"
              size="small"
              @click="handleSellStatusChange(scope.row)"
            >
              {{ scope.row.isSell ? '下架' : '上架' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑商品对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="商品种类" prop="goodsCategoryName">
          <el-select v-model="form.goodsCategoryName" placeholder="请选择商品种类">
            <el-option
              v-for="item in categories"
              :key="item.name"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="显示顺序" prop="displayOrder">
          <el-input-number v-model="form.displayOrder" :min="0" />
        </el-form-item>
        <el-form-item label="价格" prop="defaultPrice">
          <el-input-number
            v-model="form.defaultPrice"
            :precision="2"
            :step="0.1"
            :min="0"
          />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            rows="3"
          />
        </el-form-item>
        <el-form-item label="是否上架" prop="isSell">
          <el-switch v-model="form.isSell" />
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
const goodsList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const categories = ref([])

// 对话框数据
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)
const form = ref({
  id: '',
  name: '',
  goodsCategoryName: '',
  displayOrder: 0,
  price: 0,
  description: '',
  isSell: true
})

// 表单验证规则
const rules = {
  name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
  goodsCategoryName: [{ required: true, message: '请选择商品种类', trigger: 'change' }],
  displayOrder: [{ required: true, message: '请输入显示顺序', trigger: 'blur' }],
  defaultPrice: [{ required: true, message: '请输入价格', trigger: 'blur' }]
}

// 获取商品列表
const getGoodsList = async () => {
  try {
    const response = await axios.get('/api/goods', {
      params: {
        page: currentPage.value - 1,
        size: pageSize.value
      }
    })
    goodsList.value = response.data.content
    total.value = response.data.totalElements
  } catch (error) {
    ElMessage.error('获取商品列表失败')
  }
}

// 获取商品种类列表
const getCategories = async () => {
  try {
    const response = await axios.get('/api/goods-categories')
    categories.value = response.data
  } catch (error) {
    ElMessage.error('获取商品种类失败')
  }
}

// 添加商品
const handleAdd = () => {
  dialogTitle.value = '添加商品'
  form.value = {
    name: '',
    goodsCategoryName: '',
    displayOrder: 0,
    defaultPrice: 0,
    description: '',
    isSell: true
  }
  dialogVisible.value = true
}

// 编辑商品
const handleEdit = (row) => {
  dialogTitle.value = '编辑商品'
  form.value = { ...row }
  dialogVisible.value = true
}

// 修改上架状态
const handleSellStatusChange = async (row) => {
  try {
    await axios.put(`/api/goods/${row.id}/sell-status`, {
      isSell: !row.isSell
    })
    ElMessage.success('状态修改成功')
    getGoodsList()
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
        if (form.value.id) {
          await axios.put(`/api/goods/${form.value.id}`, form.value)
        } else {
          await axios.post('/api/goods', form.value)
        }
        ElMessage.success('保存成功')
        dialogVisible.value = false
        getGoodsList()
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }
  })
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getGoodsList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getGoodsList()
}

onMounted(() => {
  getGoodsList()
  getCategories()
})
</script>

<style scoped>
.goods-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
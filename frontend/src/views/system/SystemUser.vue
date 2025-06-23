<template>
  <div class="system-user">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>系统用户列表</span>
          <el-button type="primary" @click="handleAdd">添加用户</el-button>
        </div>
      </template>
      
      <el-table :data="userList" style="width: 100%">
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="roleName" label="角色" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(scope.row)"
            >
              编辑
            </el-button>
            <el-button
              type="warning"
              size="small"
              @click="handleResetPassword(scope.row)"
            >
              重置密码
            </el-button>
            <el-button
              :type="scope.row.status === 1 ? 'danger' : 'success'"
              size="small"
              @click="handleStatusChange(scope.row)"
            >
              {{ scope.row.status === 1 ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑用户对话框 -->
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
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色">
            <el-option
              v-for="role in roleList"
              :key="role.id"
              :label="role.name"
              :value="role.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="!form.id"
          label="密码"
          prop="password"
        >
          <el-input
            v-model="form.password"
            type="password"
            show-password
          />
        </el-form-item>
        <el-form-item
          v-if="!form.id"
          label="确认密码"
          prop="confirmPassword"
        >
          <el-input
            v-model="form.confirmPassword"
            type="password"
            show-password
          />
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

    <!-- 重置密码对话框 -->
    <el-dialog
      v-model="resetPasswordVisible"
      title="重置密码"
      width="500px"
    >
      <el-form
        ref="resetPasswordFormRef"
        :model="resetPasswordForm"
        :rules="resetPasswordRules"
        label-width="100px"
      >
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            v-model="resetPasswordForm.newPassword"
            type="password"
            show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="resetPasswordForm.confirmPassword"
            type="password"
            show-password
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="resetPasswordVisible = false">取消</el-button>
          <el-button type="primary" @click="handleResetPasswordSubmit">
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
const userList = ref([])
const roleList = ref([])

// 对话框数据
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)
const form = ref({
  id: '',
  username: '',
  roleId: '',
  password: '',
  confirmPassword: ''
})

// 重置密码对话框数据
const resetPasswordVisible = ref(false)
const resetPasswordFormRef = ref(null)
const resetPasswordForm = ref({
  userId: '',
  newPassword: '',
  confirmPassword: ''
})

// 验证密码一致性
const validateConfirmPassword = (rule, value, callback) => {
  if (value !== form.value.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

// 验证重置密码一致性
const validateResetConfirmPassword = (rule, value, callback) => {
  if (value !== resetPasswordForm.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

// 表单验证规则
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  roleId: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

// 重置密码表单验证规则
const resetPasswordRules = {
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateResetConfirmPassword, trigger: 'blur' }
  ]
}

// 获取用户列表
const getUserList = async () => {
  try {
    const response = await axios.get('/api/system-users')
    userList.value = response.data
  } catch (error) {
    ElMessage.error('获取用户列表失败')
  }
}

// 获取角色列表
const getRoleList = async () => {
  try {
    const response = await axios.get('/api/system-roles')
    roleList.value = response.data
  } catch (error) {
    ElMessage.error('获取角色列表失败')
  }
}

// 添加用户
const handleAdd = () => {
  dialogTitle.value = '添加用户'
  form.value = {
    username: '',
    roleId: '',
    password: '',
    confirmPassword: ''
  }
  dialogVisible.value = true
}

// 编辑用户
const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  form.value = {
    id: row.id,
    username: row.username,
    roleId: row.roleId
  }
  dialogVisible.value = true
}

// 重置密码
const handleResetPassword = (row) => {
  resetPasswordForm.value = {
    userId: row.id,
    newPassword: '',
    confirmPassword: ''
  }
  resetPasswordVisible.value = true
}

// 修改状态
const handleStatusChange = async (row) => {
  try {
    await axios.put(`/api/system-users/${row.id}/status`, {
      status: row.status === 1 ? 0 : 1
    })
    ElMessage.success('状态修改成功')
    getUserList()
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
          await axios.put(`/api/system-users/${form.value.id}`, form.value)
        } else {
          await axios.post('/api/system-users', form.value)
        }
        ElMessage.success('保存成功')
        dialogVisible.value = false
        getUserList()
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }
  })
}

// 提交重置密码
const handleResetPasswordSubmit = async () => {
  if (!resetPasswordFormRef.value) return

  await resetPasswordFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await axios.put(`/api/system-users/${resetPasswordForm.value.userId}/password`, {
          newPassword: resetPasswordForm.value.newPassword
        })
        ElMessage.success('密码重置成功')
        resetPasswordVisible.value = false
      } catch (error) {
        ElMessage.error('密码重置失败')
      }
    }
  })
}

onMounted(() => {
  getUserList()
  getRoleList()
})
</script>

<style scoped>
.system-user {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
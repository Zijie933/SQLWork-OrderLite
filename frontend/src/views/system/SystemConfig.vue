<template>
  <div class="system-config">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>系统配置</span>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        class="config-form"
      >
        <el-form-item label="店铺名称" prop="shopName">
          <el-input v-model="form.shopName" placeholder="请输入店铺名称" />
        </el-form-item>

        <el-form-item label="营业状态" prop="isOpen">
          <el-switch
            v-model="form.isOpen"
            :active-text="form.isOpen ? '营业中' : '休息中'"
          />
        </el-form-item>

        <el-form-item label="营业公告" prop="announcement">
          <el-input
            v-model="form.announcement"
            type="textarea"
            :rows="4"
            placeholder="请输入营业公告"
          />
        </el-form-item>

        <el-form-item label="营业时间" prop="businessHours">
          <el-time-picker
            v-model="form.openTime"
            format="HH:mm"
            placeholder="开始时间"
          />
          <span class="time-separator">至</span>
          <el-time-picker
            v-model="form.closeTime"
            format="HH:mm"
            placeholder="结束时间"
          />
        </el-form-item>

        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="店铺地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入店铺地址" />
        </el-form-item>

        <el-form-item label="配送范围(km)" prop="deliveryRadius">
          <el-input-number
            v-model="form.deliveryRadius"
            :min="0"
            :max="20"
            :precision="1"
            :step="0.5"
          />
        </el-form-item>

        <el-form-item label="起送金额" prop="minOrderAmount">
          <el-input-number
            v-model="form.minOrderAmount"
            :min="0"
            :precision="2"
            :step="1"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存配置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const formRef = ref(null)
const form = ref({
  shopName: '',
  isOpen: true,
  announcement: '',
  openTime: null,
  closeTime: null,
  contactPhone: '',
  address: '',
  deliveryRadius: 3,
  minOrderAmount: 0
})

const rules = {
  shopName: [
    { required: true, message: '请输入店铺名称', trigger: 'blur' }
  ],
  announcement: [
    { required: true, message: '请输入营业公告', trigger: 'blur' }
  ],
  contactPhone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入店铺地址', trigger: 'blur' }
  ]
}

// 获取系统配置
const getSystemConfig = async () => {
  try {
    const response = await axios.get('/api/system/config')
    const config = response.data
    form.value = {
      ...config,
      openTime: config.openTime ? new Date(`2000-01-01T${config.openTime}`) : null,
      closeTime: config.closeTime ? new Date(`2000-01-01T${config.closeTime}`) : null
    }
  } catch (error) {
    ElMessage.error('获取系统配置失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const submitData = {
          ...form.value,
          openTime: form.value.openTime ? form.value.openTime.toTimeString().slice(0, 5) : null,
          closeTime: form.value.closeTime ? form.value.closeTime.toTimeString().slice(0, 5) : null
        }
        await axios.put('/api/system/config', submitData)
        ElMessage.success('保存成功')
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }
  })
}

onMounted(() => {
  getSystemConfig()
})
</script>

<style scoped>
.system-config {
  padding: 20px;
}

.config-form {
  max-width: 600px;
  margin: 0 auto;
}

.time-separator {
  margin: 0 10px;
}

.el-time-picker {
  width: 180px;
}
</style>
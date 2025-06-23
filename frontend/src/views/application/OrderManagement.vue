<template>
  <div class="order-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>订单列表</span>
          <el-form :inline="true" :model="searchForm" class="search-form">
            <el-form-item label="订单状态">
              <el-select v-model="searchForm.status" placeholder="全部状态">
                <el-option label="全部" value="" />
                <el-option label="待支付" value="PENDING_PAYMENT" />
                <el-option label="待取餐" value="PENDING_PICKUP" />
                <el-option label="已完成" value="COMPLETED" />
                <el-option label="已取消" value="CANCELLED" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearch">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </template>
      
      <el-table :data="orderList" style="width: 100%">
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="status" label="订单状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="pickupType" label="取餐方式" width="100">
          <template #default="scope">
            {{ scope.row.pickupType === 'DINE_IN' ? '堂食' : '外带' }}
          </template>
        </el-table-column>
        <el-table-column prop="address" label="收货地址" show-overflow-tooltip />
        <el-table-column label="商品信息" show-overflow-tooltip>
          <template #default="scope">
            <el-popover
              placement="top-start"
              :width="300"
              trigger="hover"
            >
              <template #default>
                <div class="goods-list">
                  <div v-for="item in scope.row.orderItems" :key="item.id" class="goods-item">
                    <span>{{ item.goodsName }}</span>
                    <span>x{{ item.quantity }}</span>
                    <span>¥{{ item.price.toFixed(2) }}</span>
                  </div>
                </div>
              </template>
              <template #reference>
                <span>{{ getGoodsSummary(scope.row.orderItems) }}</span>
              </template>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="totalQuantity" label="商品总数" width="100" />
        <el-table-column label="总价格" width="100">
          <template #default="scope">
            ¥{{ scope.row.totalAmount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="支付金额" width="100">
          <template #default="scope">
            ¥{{ scope.row.payAmount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="paymentNo" label="支付单号" width="180" show-overflow-tooltip />
        <el-table-column prop="createTime" label="下单时间" width="180" />
        <el-table-column prop="payTime" label="支付时间" width="180" />
        <el-table-column prop="completeTime" label="完成时间" width="180" />
        <el-table-column prop="customerPhone" label="用户联系电话" width="120" />
        <el-table-column prop="pickupPerson" label="取餐人" width="100" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="scope">
            <el-button
              type="primary"
              size="small"
              @click="handleViewDetail(scope.row)"
            >
              查看详情
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

    <!-- 订单详情对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="订单详情"
      width="800px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="订单号">{{ currentOrder.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="getStatusType(currentOrder.status)">
            {{ getStatusText(currentOrder.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="取餐方式">
          {{ currentOrder.pickupType === 'DINE_IN' ? '堂食' : '外带' }}
        </el-descriptions-item>
        <el-descriptions-item label="收货地址">{{ currentOrder.address }}</el-descriptions-item>
        <el-descriptions-item label="取餐人">{{ currentOrder.pickupPerson }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ currentOrder.customerPhone }}</el-descriptions-item>
        <el-descriptions-item label="下单时间">{{ currentOrder.createTime }}</el-descriptions-item>
        <el-descriptions-item label="支付时间">{{ currentOrder.payTime }}</el-descriptions-item>
        <el-descriptions-item label="完成时间">{{ currentOrder.completeTime }}</el-descriptions-item>
        <el-descriptions-item label="支付单号">{{ currentOrder.paymentNo }}</el-descriptions-item>
      </el-descriptions>

      <el-divider>商品信息</el-divider>
      
      <el-table :data="currentOrder.orderItems" border>
        <el-table-column prop="goodsName" label="商品名称" />
        <el-table-column prop="quantity" label="数量" width="100" />
        <el-table-column label="单价" width="120">
          <template #default="scope">
            ¥{{ scope.row.price.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="小计" width="120">
          <template #default="scope">
            ¥{{ (scope.row.price * scope.row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
      </el-table>

      <div class="order-summary">
        <p>商品总数：{{ currentOrder.totalQuantity }} 件</p>
        <p>总价格：¥{{ currentOrder.totalAmount?.toFixed(2) }}</p>
        <p>支付金额：¥{{ currentOrder.payAmount?.toFixed(2) }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import axios from 'axios'

// 搜索表单
const searchForm = ref({
  status: ''
})

// 表格数据
const orderList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 对话框数据
const dialogVisible = ref(false)
const currentOrder = ref({})

// 获取订单列表
const getOrderList = async () => {
  try {
    const response = await axios.get('/api/orders', {
      params: {
        page: currentPage.value - 1,
        size: pageSize.value,
        status: searchForm.value.status
      }
    })
    orderList.value = response.data.content
    total.value = response.data.totalElements
  } catch (error) {
    ElMessage.error('获取订单列表失败')
  }
}

// 查看订单详情
const handleViewDetail = (row) => {
  currentOrder.value = row
  dialogVisible.value = true
}

// 搜索
const handleSearch = () => {
  currentPage.value = 1
  getOrderList()
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  getOrderList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  getOrderList()
}

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    'PENDING_PAYMENT': 'warning',
    'PENDING_PICKUP': 'primary',
    'COMPLETED': 'success',
    'CANCELLED': 'info'
  }
  return statusMap[status] || 'info'
}

// 获取状态文本
const getStatusText = (status) => {
  const statusMap = {
    'PENDING_PAYMENT': '待支付',
    'PENDING_PICKUP': '待取餐',
    'COMPLETED': '已完成',
    'CANCELLED': '已取消'
  }
  return statusMap[status] || status
}

// 获取商品概要
const getGoodsSummary = (items) => {
  if (!items || items.length === 0) return ''
  const first = items[0]
  return items.length > 1
    ? `${first.goodsName} 等${items.length}件商品`
    : first.goodsName
}

onMounted(() => {
  getOrderList()
})
</script>

<style scoped>
.order-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 0;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.goods-list {
  max-height: 200px;
  overflow-y: auto;
}

.goods-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.order-summary {
  margin-top: 20px;
  text-align: right;
}

.order-summary p {
  margin: 5px 0;
}
</style>
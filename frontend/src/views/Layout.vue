<template>
  <el-container class="layout-container">
    <el-aside width="200px" class="aside">
      <div class="logo-container">
        <img src="../assets/images/shop-logo.jpg" alt="店铺logo" class="shop-logo" />
      </div>
      <el-menu
        :default-active="route.path"
        class="el-menu-vertical"
        :router="true"
        background-color="#b35900"
        text-color="#ffe0b3"
        active-text-color="#ffd280"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Management /></el-icon>
            <span>应用管理</span>
          </template>
          <el-menu-item index="/user">
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </el-menu-item>
          <el-menu-item index="/goods">
            <el-icon><Goods /></el-icon>
            <span>商品管理</span>
          </el-menu-item>
          <el-menu-item index="/category">
            <el-icon><Files /></el-icon>
            <span>商品种类</span>
          </el-menu-item>
          <el-menu-item index="/order">
            <el-icon><List /></el-icon>
            <span>订单管理</span>
          </el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/config">
            <el-icon><Tools /></el-icon>
            <span>系统配置</span>
          </el-menu-item>
          <el-menu-item index="/system-user">
            <el-icon><UserFilled /></el-icon>
            <span>系统用户</span>
          </el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="height: 60px;" class="header">
        <div class="header-content">
          <div></div>
          <el-dropdown>
            <span class="el-dropdown-link">
              <el-avatar :size="32" :src="userAvatar" />
              {{ userName }}
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
  Management,
  User,
  Goods,
  Files,
  List,
  Setting,
  Tools,
  UserFilled
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userAvatar = ref('')
const userName = ref('管理员')

const handleLogout = () => {
  // 实现登出逻辑
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.aside {
  background-color: #b35900;
}

.logo-container {
  background-color: #804000;
  padding: 16px;
  text-align: center;
}

.shop-logo {
  width: 120px;
  height: 120px;
  border-radius: 4px;
}

.header {
  background-color: white;
  border-bottom: 1px solid #e6e6e6;
}

.header-content {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
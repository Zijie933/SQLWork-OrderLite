import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router'
import './style.css'
import App from './App.vue'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 配置axios
import axios from 'axios'
axios.defaults.withCredentials = true

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    console.log('发送请求:', config.url, config.data)
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

app.use(ElementPlus)
app.use(router)
app.mount('#app')

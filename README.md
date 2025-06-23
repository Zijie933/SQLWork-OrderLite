# SQLWork-OrderLite

这是一个面向数据库实验项目的订单管理系统，采用前后端分离架构。

## 技术栈

### 后端

- Java 17
- Spring Boot 3.1.1
- Spring Web
- Spring Data JPA
- MySQL
- Lombok

### 前端

- Vue 3
- Vite
- Vue Router
- Pinia
- Element Plus
- Axios

## 项目结构

```
.
├── backend     // 后端 Spring Boot 项目
│   └── src/main
│       ├── java/com/restaurant/backend
│       │   ├── config      // Spring Boot 配置
│       │   ├── controller  // 控制器层
│       │   ├── entity      // 实体类
│       │   ├── repository  // 数据访问层
│       │   └── service     // 服务层
│       └── resources
│           └── application.yml // 配置文件
└── frontend    // 前端 Vue.js 项目
    └── src
        ├── assets      // 静态资源
        ├── components  // 可复用组件
        ├── router      // 路由配置
        ├── views       // 页面视图
        ├── App.vue     // 根组件
        └── main.js     // 入口文件
```

## 如何开始

### 后端

1.  使用 IDE（如 IntelliJ IDEA）打开 `backend` 目录。
2.  配置 `src/main/resources/application.properties` 中的数据库连接信息。
3.  运行 Maven 命令 `mvn spring-boot:run` 或启动主应用程序类。

### 前端

1.  进入 `frontend` 目录：

    ```bash
    cd frontend
    ```

2.  安装依赖：

    ```bash
    npm install
    ```

3.  启动开发服务器：

    ```bash
    npm run dev
    ```

## 可用脚本 (前端)

- `npm run dev`: 启动开发服务器
- `npm run build`: 构建生产版本
- `npm run preview`: 预览生产版本
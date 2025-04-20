# 股票交易系统后端服务

[![Java](https://img.shields.io/badge/Java-17-blue.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.6-brightgreen.svg)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange.svg)]()
[![Redis](https://img.shields.io/badge/Redis-7.0-red.svg)]()

## 项目概述 📈
本系统是为股票交易平台提供核心业务逻辑的后端服务：
- **用户服务**：注册/登录
- **行情服务**：实时股票数据推送与历史数据查询
- **模拟交易服务**：委托下单处理

## 技术栈 🛠️
| 类别     | 技术组件                                     |
| -------- | -------------------------------------------- |
| 核心框架 | Spring Boot 3.1.6                            |
| 数据层   | MyBatis-Plus 3.5.3.2 / MySQL 8.0 / Redis 7.0 |
| 接口文档 | Knife4j 4.3.0 (增强版Swagger)                |
| 实时推送 | WebSocket                                    |
| 监控     | Prometheus                                   |
| 部署     | Docker / Docker Compose / Nginx 反向代理     |

## 系统架构 🏗️
![系统架构图]()

## 功能特性 ✨
### 用户模块 👤
- JWT令牌认证

### 行情模块 📊
- 实时行情推送（WebSocket）
- 历史K线数据查询

### 交易模块 💹
- 限价单/市价单处理
- 交易流水

## 快速开始 🚀
```bash
# 克隆项目
git clone https://github.com/yourname/web-backend.git
```

## 环境配置 ⚙️
1. 创建MySQL数据库：
```sql
CREATE DATABASE stock_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改配置文件 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/stock_db
    username: root
    password: yourpassword
    
  redis:
    host: localhost
    port: 6379
```

## API文档 📖
访问在线接口文档：`http://localhost:8080/doc.html`

![Swagger截图](src/main/resources/static/api-demo.png)

## 部署指南 🐳
```bash
# 启动全套依赖服务
docker-compose -f src/main/resources/docker-compose/mysql/docker-compose.yml up -d
docker-compose -f src/main/resources/docker-compose/redis/docker-compose.yml up -d
```

## 许可证 📄
[MIT License](LICENSE)

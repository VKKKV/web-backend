
### **快速解决方案：Dockerfile 定制化**
```dockerfile
# 使用官方 Nginx Alpine 版本（轻量且高效）
FROM nginx:1.25-alpine

# 删除默认配置文件（避免启动时默认加载）
RUN rm /etc/nginx/conf.d/default.conf

# 将宿主机本地的 Nginx 自定义配置复制到容器中
COPY ./reverse-proxy.conf /etc/nginx/conf.d/reverse-proxy.conf

# （可选）暴露 80 和 443 端口
EXPOSE 80 443
```

---

### **详细配置说明**

#### **1. 自定义反向代理配置文件（`reverse-proxy.conf`）**
在 Dockerfile 同目录下创建 `reverse-proxy.conf`，内容如下：
```nginx
server {
    listen 80;
  
    # 关键配置：代理到宿主机本地的服务（按系统调整目标地址）
    location / {
        proxy_pass http://host.docker.internal:8080;  # ❗适配不同操作系统的宿主机访问方式
      
        # 传递关键请求头信息
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
      
        # 优化代理性能
        proxy_connect_timeout 60s;
        proxy_read_timeout 60s;
        proxy_send_timeout 60s;
    }

    # （可选）添加其他反向代理逻辑，如静态文件缓存等
}
```

---

#### **2. **构建与运行容器**

**Step 1 - 构建镜像**：
```bash
docker build -t custom-nginx-reverse-proxy .
```

**Step 2 - 运行容器**（按宿主机的操作系统调整网络配置）：

- **在 Linux 上**（需要手动处理 `host.docker.internal` 解析）：
  ```bash
  # 方法1：通过 --add-host 手动绑定宿主机 IP（替换 172.17.0.1 为实际宿主机的 docker0 网关 IP）
  docker run -d --name nginx-proxy \
    -p 80:80 \
    --add-host=host.docker.internal:172.17.0.1 \
    custom-nginx-reverse-proxy

  # 方法2：直接替换 proxy_pass 为宿主机的真实 IP（如 192.168.1.100）
  ```

---

#### **3. **验证代理效果
```bash
# 访问宿主机 80 端口的 Nginx（预期会被转发到本地 8080 服务）
curl http://localhost

# 测试容器内部到宿主机的连通性
docker exec -it nginx-proxy ping host.docker.internal
```

---

### **关键优化与注意事项**
#### **1. 跨操作系统网络差异处理**
| 系统         | 宿主机访问方式                          | 配置示例                          |
|--------------|----------------------------------------|----------------------------------|
| **macOS/Windows** | 直接使用 `host.docker.internal`          | `proxy_pass http://host.docker.internal:8080` |
| **Linux**        | Docker Engine ≥20.10 支持自动解析          | 无需额外配置（新版支持）         |
| **低版本 Linux** | 需设置 `--add-host` 或直接使用宿主机 IP   | `proxy_pass http://172.17.0.1:8080` |

#### **2. 网络模式选择**
- **默认模式（bridge）**：推荐使用，端口映射隔离灵活。
- **Host 模式**：若选择 `--network=host`，此时容器直接共享宿主机网络，可直接用 `127.0.0.1`：
  ```bash
  docker run -d --network=host custom-nginx-reverse-proxy
  ```
  但需避免端口冲突（如宿主机的 Nginx 已占用了 80）。

---

### **完整配置文件示例**
如果需要在 Dockerfile 中完全替代默认配置（避免冲突），可以替换整个 `nginx.conf`： 
```dockerfile
COPY ./nginx.conf /etc/nginx/nginx.conf
```

其中 `nginx.conf` 的模板可参考官方文档定制，核心片段示例如下：
```nginx
events {
    worker_connections 1024;
}

http {
    # 主配置：直接引入子配置文件
    include /etc/nginx/conf.d/*.conf;
}
```

---

### **总结**
- **核心逻辑**：通过 Dockerfile 将自定义配置固化到镜像中，确保代理逻辑一致性。
- **网络核心点**：根据宿主机操作系统调整目标地址（`host.docker.internal` 或实际 IP）。
- **扩展性**：可叠加多组反向代理规则、负载均衡策略（`upstream`）到配置文件中。

如需针对特定环境（如 Kubernetes）优化或添加 SSL 支持，可进一步补充需求说明！

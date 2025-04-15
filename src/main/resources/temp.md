
交易页面增加买入成本，响应式计算属性



1.2 研究现状  

1.2.1 国外研究现状及其发展  
1.2.2 国内研究现状及其发展  
1.3 研究意义  
- 填补中小投资者专业化工具缺失的空白
1.4 研究目标与内容  
- 目标：构建支持实时行情、模拟交易的一体化平台。  
- 内容：架构设计。  
1.5 论文的组织安排  
- 章节间逻辑关系及内容概述。  

2 关键技术介绍  
2.1 前后端分离架构  
2.2 typescript 
2.3 Spring Boot 与 Java 生态
2.4 Vue3
2.5 数据库技术选型  
2.6 本章小结  

3 需求分析与系统设计  
3.1 整体需求分析  
- 用户需求：实时行情推送、高效交易执行、平台监控。  
3.2 功能模块划分  
3.2.1 行情模块  
- 实时数据获取（WebSocket）、视图展示（K线图）。  
3.2.2 交易模块  
- 买入/卖出指令处理、订单历史。  
3.2.3 用户模块
3.4 系统架构设计  
- 分层架构（数据层、服务层、前端层）。  
3.5 数据库设计  
- 核心表设计（用户表、交易记录表、行情表）
3.6 本章小结  

4 股票交易Web平台的具体实现  
4.1 实现环境
4.2 整体实现概述
4.3 用户模块实现
4.3 行情模块实现 
4.4 交易模块实现
4.5 本章小结

5 结论  

致谢  
参考文献  

---

token stock-api:
a7098d30ae48d4bc8029243d68163971-c-app

---


实时行情架构
推送机制：
```java
// WebSocket 服务端消息广播示例
@ServerEndpoint("/market/{stockCode}")
public class MarketEndpoint {
    @OnOpen
    public void onOpen(Session session, 
                      @PathParam("stockCode") String code) {
        RedisSubscriber.subscribe(code, session);
    }
}
```

事务控制
```sql
-- 资金账户表设计冗余字段
CREATE TABLE account (
    id BIGINT PRIMARY KEY,
    balance DECIMAL(18,2),
);
```

4. 系统核心模块实现
账户模块
集成 Spring Security 与 JWT 构建鉴权体系：生成 Token 时加入设备指纹识别，防范凭证盗用。

前端图表
在 Vue 3 中集成 KLineChart 组件：

```typescript
// 绘制K线图
const renderChart = (data: KLineData[]) => {
    const chart = new KLineChart({ 
        container: "#chart", 
        theme: "dark" 
    });
    chart.applyNewData(data);
}
...
```


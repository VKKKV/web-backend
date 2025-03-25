CREATE TABLE `users`
(
    `user_id`       INT PRIMARY KEY AUTO_INCREMENT,
    `username`      VARCHAR(50) UNIQUE NOT NULL COMMENT '用户登录名',
    `password_hash` VARCHAR(255)       NOT NULL COMMENT 'BCrypt加密密码',
    `email`         VARCHAR(100) UNIQUE COMMENT '用户邮箱',
    `phone`         VARCHAR(20) COMMENT '国际格式: +国家码 号码',
    `created_at`    TIMESTAMP                   DEFAULT (CURRENT_TIMESTAMP),
    `updated_at`    TIMESTAMP                   DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP(),
    `is_deleted`    TINYINT            NOT NULL DEFAULT 0 COMMENT '逻辑删除标识：0-未删除，1-已删除'
);

-- add logic delete
# ALTER TABLE `users`
#     ADD COLUMN `is_deleted` TINYINT NOT NULL DEFAULT 0
#     COMMENT '逻辑删除标识：0-未删除，1-已删除'
# AFTER `updated_at`;


CREATE TABLE `stocks`


(
    `stock_id`   INT PRIMARY KEY AUTO_INCREMENT,
    `stock_code` VARCHAR(20) UNIQUE NOT NULL COMMENT '股票唯一代码',
    `stock_name` VARCHAR(100)       NOT NULL COMMENT '股票全称',
    `market`     VARCHAR(50) COMMENT '所属市场: SH/SZ等'
);

CREATE TABLE `transactions`
(
    `transaction_id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '交易流水号',
    `user_id`        INT            NOT NULL,
    `stock_id`       INT            NOT NULL,
    `action_type`    ENUM('BUY', 'SELL', 'CANCEL') NOT NULL COMMENT '交易类型',
    `price`          DECIMAL(15, 4) NOT NULL COMMENT '精确到4位小数',
    `quantity`       INT            NOT NULL COMMENT '交易数量(股)',
    `status`         ENUM('PENDING','EXECUTED','FAILED') DEFAULT 'PENDING',
    `created_at`     TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) COMMENT '订单创建时间',
    `executed_at`    TIMESTAMP COMMENT '订单执行时间'
);

CREATE TABLE `market_data`
(
    `data_id`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `stock_id`  INT            NOT NULL,
    `price`     DECIMAL(15, 4) NOT NULL,
    `volume`    BIGINT         NOT NULL COMMENT '成交量(股)',
    `timestamp` TIMESTAMP      NOT NULL COMMENT '精确到毫秒'
);

CREATE TABLE `trading_strategies`
(
    `strategy_id`       INT PRIMARY KEY AUTO_INCREMENT,
    `user_id`           INT          NOT NULL,
    `name`              VARCHAR(100) NOT NULL COMMENT '策略名称',
    `trigger_condition` JSON         NOT NULL COMMENT 'JSON格式策略条件',
    `status`            ENUM('ACTIVE','PAUSED','ARCHIVED') DEFAULT 'ACTIVE',
    `created_at`        TIMESTAMP DEFAULT (CURRENT_TIMESTAMP),
    `updated_at`        TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `security_events`
(
    `event_id`   BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`    INT COMMENT '可为空表示系统事件',
    `event_type` ENUM('LOGIN','LOGOUT','TRADE','STRATEGY_CREATE','PASSWORD_CHANGE','SYSTEM_ALERT'),
    `ip_address` VARCHAR(45) COMMENT '支持IPv6',
    `details`    JSON COMMENT '事件详情JSON',
    `created_at` TIMESTAMP DEFAULT (CURRENT_TIMESTAMP)
);

CREATE UNIQUE INDEX `uidx_login_account` ON `users` (`username`, `email`);

CREATE UNIQUE INDEX `uidx_stock_code` ON `stocks` (`stock_code`);

CREATE INDEX `idx_code_market` ON `stocks` (`stock_code`, `market`);

CREATE INDEX `idx_user_orders` ON `transactions` (`user_id`, `status`);

CREATE INDEX `idx_stock_transactions` ON `transactions` (`stock_id`, `created_at`);

CREATE UNIQUE INDEX `uidx_stock_timestamp` ON `market_data` (`stock_id`, `timestamp`);

CREATE INDEX `idx_market_timeline` ON `market_data` (`timestamp`);

CREATE INDEX `idx_user_strategies` ON `trading_strategies` (`user_id`, `status`);

CREATE INDEX `idx_user_activities` ON `security_events` (`user_id`, `created_at`);

CREATE INDEX `idx_security_timeline` ON `security_events` (`created_at`);

ALTER TABLE `transactions`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `transactions`
    ADD FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`);

ALTER TABLE `market_data`
    ADD FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`);

ALTER TABLE `trading_strategies`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `security_events`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);


ALTER TABLE `transactions`
    ADD COLUMN `order_type` ENUM('MARKET', 'LIMIT') NOT NULL DEFAULT 'LIMIT' COMMENT '委托类型' AFTER `action_type`;
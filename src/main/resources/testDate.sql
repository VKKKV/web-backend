INSERT INTO `users` (`username`, `password_hash`, `email`, `phone`, `created_at`, `updated_at`)
VALUES ('user1', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1', 'user1@example.com', '+1 1234567890',
        NOW(), NOW()),
       ('user2', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z2', 'user2@example.com', '+1 1234567891',
        NOW(), NOW()),
       ('user3', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z3', 'user3@example.com', '+1 1234567892',
        NOW(), NOW()),
       ('user4', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z4', 'user4@example.com', '+1 1234567893',
        NOW(), NOW()),
       ('user5', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z5', 'user5@example.com', '+1 1234567894',
        NOW(), NOW()),
       ('user6', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z6', 'user6@example.com', '+1 1234567895',
        NOW(), NOW()),
       ('user7', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z7', 'user7@example.com', '+1 1234567896',
        NOW(), NOW()),
       ('user8', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z8', 'user8@example.com', '+1 1234567897',
        NOW(), NOW()),
       ('user9', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z9', 'user9@example.com', '+1 1234567898',
        NOW(), NOW()),
       ('user10', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z0', 'user10@example.com', '+1 1234567899',
        NOW(), NOW()),
       ('user11', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1', 'user11@example.com', '+1 1234567800',
        NOW(), NOW()),
       ('user12', '$2y$10$EIXZ1Z1Z1Z1Z1Z1Z1Z1Z1Oe1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z2', 'user12@example.com', '+1 1234567801',
        NOW(), NOW());

INSERT INTO `stocks` (`stock_code`, `stock_name`, `market`)
VALUES ('600000', '浦发银行', 'SH'),
       ('600004', '白云机场', 'SH'),
       ('600006', '东风汽车', 'SH'),
       ('600007', '中国国贸', 'SH'),
       ('600008', '首创股份', 'SH'),
       ('600009', '上海机场', 'SH'),
       ('600010', '包钢股份', 'SH'),
       ('600011', '华能国际', 'SH'),
       ('600012', '皖通科技', 'SH'),
       ('600015', '华夏银行', 'SH'),
       ('600016', '民生银行', 'SH'),
       ('600017', '日照港', 'SH'),
       ('600018', '上港集团', 'SH'),
       ('600019', '宝钢股份', 'SH'),
       ('600020', '中原证券', 'SH'),
       ('600021', '上海电力', 'SH'),
       ('600022', '山东钢铁', 'SH'),
       ('600023', '浦东建设', 'SH'),
       ('600025', '华能水电', 'SH'),
       ('600026', '中远海控', 'SH'),
       ('600027', '华电国际', 'SH'),
       ('600028', '中国石化', 'SH'),
       ('600029', '南方航空', 'SH'),
       ('600030', '中信证券', 'SH'),
       ('600031', '三一重工', 'SH'),
       ('600032', '金龙鱼', 'SH'),
       ('600033', '福建水泥', 'SH'),
       ('600035', '楚天科技', 'SH'),
       ('600036', '招商银行', 'SH'),
       ('600037', '歌尔股份', 'SH'),
       ('600038', '中直股份', 'SH'),
       ('600039', '四川长虹', 'SH'),
       ('600048', '保利地产', 'SH'),
       ('600050', '中国联通', 'SH'),
       ('600051', '宁波联合', 'SH'),
       ('600052', '中航资本', 'SH'),
       ('600053', '九鼎投资', 'SH'),
       ('600054', '黄山旅游', 'SH'),
       ('600055', '万东医疗', 'SH'),
       ('600056', '中国医药', 'SH'),
       ('600057', '五粮液', 'SH'),
       ('600058', '五矿发展', 'SH'),
       ('600059', '古井贡酒', 'SH'),
       ('600060', '海信电器', 'SH'),
       ('600061', '国投资本', 'SH'),
       ('600062', '华润万东', 'SH'),
       ('600063', '中兴通讯', 'SH'),
       ('600064', '南京熊猫', 'SH'),
       ('600065', '中国人寿', 'SH'),
       ('600066', '宇通客车', 'SH'),
       ('600067', '冠城大通', 'SH'),
       ('600068', '葛洲坝', 'SH'),
       ('600069', '银鸽投资', 'SH'),
       ('600070', '浙江世宝', 'SH'),
       ('600071', '凤凰传媒', 'SH'),
       ('600072', '中船防务', 'SH'),
       ('600073', '上海梅林', 'SH'),
       ('600074', '保利文化', 'SH'),
       ('600075', '新疆天业', 'SH'),
       ('600076', '华电能源', 'SH'),
       ('600077', '中信建投', 'SH'),
       ('600078', '中信证券', 'SH'),
       ('600079', '中国电建', 'SH'),
       ('600080', '东风汽车', 'SH'),
       ('600081', '东华软件', 'SH'),
       ('600082', '海泰发展', 'SH'),
       ('600083', '博信股份', 'SH'),
       ('600084', '中葡股份', 'SH'),
       ('600085', '同仁堂', 'SH'),
       ('600086', '东方金钰', 'SH'),
       ('600087', '长城汽车', 'SH'),
       ('600088', '中视传媒', 'SH'),
       ('600089', '特变电工', 'SH'),
       ('600090', '天健集团', 'SH'),
       ('600091', '长江电力', 'SH'),
       ('600092', '中天科技', 'SH'),
       ('600093', '易见股份', 'SH'),
       ('600094', '大名城', 'SH'),
       ('600095', '哈投股份', 'SH'),
       ('600096', '云天化', 'SH');



INSERT INTO `market_data` (`stock_id`, `price`, `volume`, `timestamp`)
VALUES (1, 100.1234, 1000, '2023-10-01 10:00:00.000'),
       (1, 101.5678, 1500, '2023-10-01 10:01:00.000'),
       (1, 102.3456, 2000, '2023-10-01 10:02:00.000'),
       (2, 200.9876, 500, '2023-10-01 10:00:00.000'),
       (2, 201.2345, 800, '2023-10-01 10:01:00.000'),
       (2, 202.4567, 1200, '2023-10-01 10:02:00.000'),
       (3, 300.1234, 3000, '2023-10-01 10:00:00.000'),
       (3, 301.5678, 2500, '2023-10-01 10:01:00.000'),
       (3, 302.3456, 4000, '2023-10-01 10:02:00.000'),
       (4, 400.9876, 600, '2023-10-01 10:00:00.000'),
       (4, 401.2345, 900, '2023-10-01 10:01:00.000'),
       (4, 402.4567, 1100, '2023-10-01 10:02:00.000'),
       (5, 500.1234, 700, '2023-10-01 10:00:00.000'),
       (5, 501.5678, 1300, '2023-10-01 10:01:00.000'),
       (5, 502.3456, 1600, '2023-10-01 10:02:00.000'),
       (6, 600.9876, 400, '2023-10-01 10:00:00.000'),
       (6, 601.2345, 500, '2023-10-01 10:01:00.000'),
       (6, 602.4567, 700, '2023-10-01 10:02:00.000'),
       (7, 700.1234, 800, '2023-10-01 10:00:00.000'),
       (7, 701.5678, 900, '2023-10-01 10:01:00.000'),
       (7, 702.3456, 1000, '2023-10-01 10:02:00.000'),
       (8, 800.9876, 300, '2023-10-01 10:00:00.000'),
       (8, 801.2345, 400, '2023-10-01 10:01:00.000'),
       (8, 802.4567, 500, '2023-10-01 10:02:00.000'),
       (9, 900.1234, 2000, '2023-10-01 10:00:00.000'),
       (9, 901.5678, 2500, '2023-10-01 10:01:00.000'),
       (9, 902.3456, 3000, '2023-10-01 10:02:00.000'),
       (10, 1000.9876, 1500, '2023-10-01 10:00:00.000'),
       (10, 1001.2345, 1800, '2023-10-01 10:01:00.000'),
       (10, 1002.4567, 2200, '2023-10-01 10:02:00.000');


INSERT INTO `transactions` (`user_id`, `stock_id`, `action_type`, `price`, `quantity`, `status`, `created_at`, `executed_at`)
VALUES
    (1, 1, 'BUY', 100.2500, 10, 'EXECUTED', NOW(), NOW()),
    (1, 2, 'SELL', 150.5000, 5, 'EXECUTED', NOW(), NOW()),
    (2, 1, 'BUY', 99.7500, 20, 'PENDING', NOW(), NULL),
    (2, 2, 'CANCEL', 200.0000, 15, 'FAILED', NOW(), NULL),
    (3, 3, 'BUY', 250.0000, 8, 'EXECUTED', NOW(), NOW()),
    (3, 3, 'SELL', 300.0000, 12, 'PENDING', NOW(), NULL),
    (4, 4, 'BUY', 75.5000, 25, 'EXECUTED', NOW(), NOW()),
    (4, 4, 'SELL', 80.0000, 30, 'FAILED', NOW(), NULL),
    (5, 5, 'BUY', 120.0000, 50, 'EXECUTED', NOW(), NOW()),
    (5, 5, 'SELL', 130.0000, 40, 'PENDING', NOW(), NULL),
    (6, 6, 'BUY', 90.0000, 15, 'EXECUTED', NOW(), NOW()),
    (6, 6, 'CANCEL', 110.0000, 10, 'FAILED', NOW(), NULL),
    (7, 7, 'BUY', 95.5000, 22, 'EXECUTED', NOW(), NOW()),
    (7, 7, 'SELL', 85.0000, 18, 'PENDING', NOW(), NULL),
    (8, 8, 'BUY', 200.0000, 5, 'EXECUTED', NOW(), NOW()),
    (8, 8, 'SELL', 210.0000, 3, 'FAILED', NOW(), NULL),
    (9, 9, 'BUY', 300.0000, 7, 'EXECUTED', NOW(), NOW()),
    (9, 9, 'SELL', 320.0000, 9, 'PENDING', NOW(), NULL),
    (10, 10, 'BUY', 400.0000, 2, 'EXECUTED', NOW(), NOW()),
    (10, 10, 'CANCEL', 410.0000, 1, 'FAILED', NOW(), NULL);

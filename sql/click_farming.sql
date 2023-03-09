/*
 Navicat Premium Data Transfer

 Source Server         : Local
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : click_farming

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 09/03/2023 16:21:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customer_commission_config
-- ----------------------------
DROP TABLE IF EXISTS `customer_commission_config`;
CREATE TABLE `customer_commission_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `config_list` json DEFAULT NULL,
  `task_type` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of customer_commission_config
-- ----------------------------
BEGIN;
INSERT INTO `customer_commission_config` (`id`, `create_at`, `update_at`, `config_list`, `task_type`) VALUES (1, '2023-02-17 17:18:27.976286', '2023-02-17 17:18:27.985159', '[{\"endPrice\": 100, \"levelOne\": 6, \"levelTwo\": 7, \"levelThree\": 8, \"startPrice\": 0, \"showServiceFee\": 3, \"commissionPrice\": 4, \"stuffServiceFee\": 4, \"instantServiceFee\": 3, \"stuffCommissionPrice\": 5}, {\"endPrice\": 200, \"levelOne\": 7, \"levelTwo\": 8, \"levelThree\": 9, \"startPrice\": 100, \"showServiceFee\": 3, \"commissionPrice\": 2, \"stuffServiceFee\": 5, \"instantServiceFee\": 4, \"stuffCommissionPrice\": 6}, {\"endPrice\": 400, \"levelOne\": 6, \"levelTwo\": 7, \"levelThree\": 0, \"startPrice\": 200, \"showServiceFee\": 3, \"commissionPrice\": 2, \"stuffServiceFee\": 4, \"instantServiceFee\": 2, \"stuffCommissionPrice\": 5}]', 0);
INSERT INTO `customer_commission_config` (`id`, `create_at`, `update_at`, `config_list`, `task_type`) VALUES (2, '2023-02-17 17:18:17.538345', '2023-02-17 17:18:17.538345', '[{\"endPrice\": 100, \"levelOne\": 6, \"levelTwo\": 7, \"levelThree\": 8, \"startPrice\": 0, \"showServiceFee\": 3, \"commissionPrice\": 2, \"stuffServiceFee\": 4, \"instantServiceFee\": 1, \"stuffCommissionPrice\": 5}]', 1);
COMMIT;

-- ----------------------------
-- Table structure for fund_change_record
-- ----------------------------
DROP TABLE IF EXISTS `fund_change_record`;
CREATE TABLE `fund_change_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `order_no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `task_no` varchar(255) DEFAULT NULL,
  `trade_type` smallint DEFAULT NULL,
  `transaction_no` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_type` smallint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `operated_user` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of fund_change_record
-- ----------------------------
BEGIN;
INSERT INTO `fund_change_record` (`id`, `create_at`, `update_at`, `amount`, `balance`, `order_no`, `remark`, `task_no`, `trade_type`, `transaction_no`, `user_id`, `user_type`, `username`, `operated_user`, `shop_name`) VALUES (10, '2023-02-15 15:02:20.455658', '2023-02-15 15:02:20.455658', 1.00, 3.00, '', '', '', 9, '16764445404484081', 1, 2, '业务员1', 'admin', NULL);
INSERT INTO `fund_change_record` (`id`, `create_at`, `update_at`, `amount`, `balance`, `order_no`, `remark`, `task_no`, `trade_type`, `transaction_no`, `user_id`, `user_type`, `username`, `operated_user`, `shop_name`) VALUES (11, '2023-02-15 16:24:03.407617', '2023-02-15 16:24:03.407617', -1.00, 4.00, '', '', '', 8, '16764494434067979', 1, 2, '业务员1', 'admin', NULL);
INSERT INTO `fund_change_record` (`id`, `create_at`, `update_at`, `amount`, `balance`, `order_no`, `remark`, `task_no`, `trade_type`, `transaction_no`, `user_id`, `user_type`, `username`, `operated_user`, `shop_name`) VALUES (12, '2023-02-15 16:24:56.278753', '2023-02-15 16:24:56.278753', 1.00, 3.00, '', '', '', 9, '16764494962788886', 1, 2, '业务员1', 'admin', NULL);
INSERT INTO `fund_change_record` (`id`, `create_at`, `update_at`, `amount`, `balance`, `order_no`, `remark`, `task_no`, `trade_type`, `transaction_no`, `user_id`, `user_type`, `username`, `operated_user`, `shop_name`) VALUES (13, '2023-02-15 16:25:33.580437', '2023-02-15 16:25:33.580437', 1.00, 4.00, '', '', '', 9, '16764495335804216', 1, 2, '业务员1', 'admin', NULL);
INSERT INTO `fund_change_record` (`id`, `create_at`, `update_at`, `amount`, `balance`, `order_no`, `remark`, `task_no`, `trade_type`, `transaction_no`, `user_id`, `user_type`, `username`, `operated_user`, `shop_name`) VALUES (14, '2023-02-15 17:10:06.902932', '2023-02-15 17:10:06.902932', 1.00, 5.00, '', '加1元', '', 9, '16764522068922397', 1, 2, '业务员1', 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `commission_rate` decimal(38,2) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `froze_balance` decimal(38,2) DEFAULT NULL,
  `insurance` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `principal_type` smallint DEFAULT NULL,
  `publish_task_enabled` bit(1) NOT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sales_man_id` bigint DEFAULT NULL,
  `state` smallint DEFAULT NULL,
  `taobao_enabled` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `wechat` varchar(255) DEFAULT NULL,
  `shop_limit` int NOT NULL,
  `enabled` bit(1) NOT NULL,
  `default_withdraw_password` bit(1) NOT NULL,
  `withdraw_password` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `main_category` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of merchant
-- ----------------------------
BEGIN;
INSERT INTO `merchant` (`id`, `create_at`, `update_at`, `balance`, `commission_rate`, `contact`, `froze_balance`, `insurance`, `password`, `principal_type`, `publish_task_enabled`, `qq`, `remark`, `sales_man_id`, `state`, `taobao_enabled`, `username`, `wechat`, `shop_limit`, `enabled`, `default_withdraw_password`, `withdraw_password`, `city`, `contact_number`, `main_category`, `province`) VALUES (4, '2023-03-08 23:10:28.270573', '2023-03-08 23:10:28.270573', 0.00, 1.00, '', 0.00, b'0', '$2a$10$6HLHjybUcfc406dvewlLGuuYxTjOEfCU/jJ2O07o5.frXk6Tdp9TO', 0, b'0', '111', '', 1, 0, b'1', 'Uhr28yjqAJsF1bS4RN0GPO6EojeLMHMimBdjxBe4KC+pQs/ww997vq5bgC3j+sWUMDmZyJyLQYrShdY7VZZ5AV4pT/NKGV7eaipkkFkbqsiXYrPxN2qWj1v2Y0YevMX6/9mI+xxUSCaD8pgz+fGjCyiSqcJl87+a0vzdsKv46F0=', 'qqq', 0, b'0', b'1', '$2a$10$YN9qpLQvEIswR1y1yjVqJeBNqLvT0XwDbWC/u92pBoGEnPbaFokAO', '', '', '', '');
INSERT INTO `merchant` (`id`, `create_at`, `update_at`, `balance`, `commission_rate`, `contact`, `froze_balance`, `insurance`, `password`, `principal_type`, `publish_task_enabled`, `qq`, `remark`, `sales_man_id`, `state`, `taobao_enabled`, `username`, `wechat`, `shop_limit`, `enabled`, `default_withdraw_password`, `withdraw_password`, `city`, `contact_number`, `main_category`, `province`) VALUES (5, '2023-03-08 23:11:07.354928', '2023-03-08 23:11:07.354928', 0.00, 1.00, '', 0.00, b'0', '$2a$10$7eA.Yb398u8581.u1HDOX.tkTiM5PZigwmJexsIFOOzr8L.KjZfd2', 0, b'0', '111', '', 1, 0, b'1', 'BXbyMJaGR/Vy5v1pP11ptLBKS94WsO3rJHa8C28YmBZc3riaVqEgG2VMn5vFA7OKyfKr85zPqdqPlOKn09qex0LqMbQW0K6ff8I3jsUz1MfkqBhTLg2rik7D4ivLxO1gi685Kg6FCeGFqd78fxXdbIlvQo+gOimggYn3hqATiQ0=', 'qqq', 0, b'0', b'1', '$2a$10$x1CJBIQsIY4yA5FXpX1VCe3hfLbrRjeFF3yTCW48cehYtytx8qFPO', '', '', '', '');
INSERT INTO `merchant` (`id`, `create_at`, `update_at`, `balance`, `commission_rate`, `contact`, `froze_balance`, `insurance`, `password`, `principal_type`, `publish_task_enabled`, `qq`, `remark`, `sales_man_id`, `state`, `taobao_enabled`, `username`, `wechat`, `shop_limit`, `enabled`, `default_withdraw_password`, `withdraw_password`, `city`, `contact_number`, `main_category`, `province`) VALUES (6, '2023-03-08 23:14:05.751564', '2023-03-08 23:14:05.751564', 0.00, 1.00, '', 0.00, b'0', '$2a$10$0uS1gJzyc2gHUR9kOaSafezUMJiBmqlb4y.FcYhIP5ESKF3OihOmC', 0, b'0', '111', '', 1, 0, b'1', '商家1', 'qqq', 0, b'0', b'1', '$2a$10$YJ8gOq2ucUAiBAs4ckHo8.qPhBZ5Z0Nrv1J.KH8cLFYykWwwcdrjS', '', '', '', '');
INSERT INTO `merchant` (`id`, `create_at`, `update_at`, `balance`, `commission_rate`, `contact`, `froze_balance`, `insurance`, `password`, `principal_type`, `publish_task_enabled`, `qq`, `remark`, `sales_man_id`, `state`, `taobao_enabled`, `username`, `wechat`, `shop_limit`, `enabled`, `default_withdraw_password`, `withdraw_password`, `city`, `contact_number`, `main_category`, `province`) VALUES (7, '2023-03-08 23:15:03.392529', '2023-03-08 23:15:03.392529', 0.00, 1.00, '', 0.00, b'0', '$2a$10$.pS0zSXI8gcXtwUhwWhVJOiPZja3K2M0p.jv.S1c1MmqtRQzbhD8m', 0, b'0', '111', '', 1, 0, b'1', '15555555555', 'qqq', 0, b'0', b'1', '$2a$10$LxlqbshNNOiwThCnVuFLpOTAPaSMphoOPPWLRMq25cdPPVgF5s/9W', '', '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for merchant_shop
-- ----------------------------
DROP TABLE IF EXISTS `merchant_shop`;
CREATE TABLE `merchant_shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `delivery_name` varchar(255) DEFAULT NULL,
  `delivery_phone` varchar(255) DEFAULT NULL,
  `flag_remark` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `main_category` varchar(255) DEFAULT NULL,
  `merchant_id` bigint NOT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `repay_days` int NOT NULL,
  `shop_flag` varchar(255) DEFAULT NULL,
  `shop_link` varchar(255) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `shop_type` smallint DEFAULT NULL,
  `state` smallint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh7n2eca6xnlj2ah4ndvybpwux` (`merchant_id`),
  CONSTRAINT `FKh7n2eca6xnlj2ah4ndvybpwux` FOREIGN KEY (`merchant_id`) REFERENCES `merchant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of merchant_shop
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for merchant_top_up_record
-- ----------------------------
DROP TABLE IF EXISTS `merchant_top_up_record`;
CREATE TABLE `merchant_top_up_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `bank_card_id` bigint NOT NULL,
  `merchant_id` bigint NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `state` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of merchant_top_up_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sales_man
-- ----------------------------
DROP TABLE IF EXISTS `sales_man`;
CREATE TABLE `sales_man` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT NULL,
  `business_rate` decimal(38,2) DEFAULT NULL,
  `customer_rate` decimal(38,2) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `froze_balance` decimal(38,2) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `share_code` varchar(255) DEFAULT NULL,
  `share_permission` smallint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `business_commission_config` json DEFAULT NULL,
  `customer_commission_config` json DEFAULT NULL,
  `sub_site` bit(1) DEFAULT NULL,
  `sub_site_id` bigint DEFAULT NULL,
  `belongs_to_id` bigint DEFAULT NULL,
  `belongs_to_user` varchar(255) DEFAULT NULL,
  `sales_man_type` smallint DEFAULT NULL,
  `default_withdraw_password` bit(1) NOT NULL,
  `withdraw_password` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDXeip0vhjlm9w5tikd01bmpcmyd` (`share_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_man
-- ----------------------------
BEGIN;
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (1, '2023-02-14 18:24:46.854777', '2023-03-03 23:01:02.868174', 6.00, 1.00, 2.00, b'1', 0.00, '$2a$10$ioWbHJrwW/6Mt4jzC119K.LN/SCt2lLMIzvS6EejJAuYzwSLTsDuC', 'DOFGNM', 2, '业务员1', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'1', 4, NULL, NULL, 3, b'1', '$2a$10$XN9cSG78BIxKjeFO2f1yUe9ODYeGL3fT2WQMkMuoZqk5tYsssK1h.', NULL);
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (2, '2023-02-14 18:25:17.249537', '2023-02-23 23:38:05.345917', 0.00, 2.00, 3.00, b'0', 0.00, '$2a$10$h8cC1p3KM.1K51dFKDBa6uBhXdrOMA1iQEESjsriEnHoZGEsXfhrS', '0QZR2M', 1, '业务员2', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'0', NULL, NULL, NULL, 1, b'1', '$2a$10$.sUy2xFohlLm9oCoulDAVe3NmLi1MSywtbo3MjZXGLtv4sUK4hgWK', NULL);
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (3, '2023-02-14 18:55:40.904239', '2023-02-23 23:38:06.831500', 0.00, 1.00, 2.00, b'0', 0.00, '$2a$10$H1nga0H6tdJ3tIo7aQFhhuB1XlGtO0CrVWCr0dwqw9fcSahkJlE1a', 'WVQWU4', 0, '业务3', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'0', NULL, NULL, NULL, 1, b'1', '$2a$10$d/Wiyw1lYjCHv089m4nzxu.MKfTP.3.82H/k.2cleiLYCB1yYEFIK', NULL);
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (4, '2023-02-17 17:24:47.053401', '2023-02-23 23:38:08.532539', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$Z8pp8hlFOmLhCQTJHxBu1OgA9mdccLejyVcENCZckMAMyIxgX3SIq', '3DHNDI', 1, 'freffe', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'0', NULL, NULL, NULL, 1, b'1', '$2a$10$ZiLanLfY6YyjgSdO005Inerabq.7VbKmHpuWppoLhyMyEDyaS8TLa', NULL);
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (5, '2023-03-01 15:41:14.040631', '2023-03-01 23:21:01.972804', 0.00, 0.00, 0.00, b'1', 0.00, '$2a$10$4C4lu1Vq4CUijBMATRHV5O2APnQMtvUrAwGpAxAX3WggPj457NR.6', '', 0, 'e4r4rr4', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$qQOapyWFI1uwsJxe6xHUPemqwvnk9NS2GpzJDNvZZ9nhbWl2Qncdy', '11');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (6, '2023-03-01 15:51:56.808632', '2023-03-02 00:31:17.828022', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$4g.PrWZba/fDmLQV2GftoeuNIXoGUmsTs7.bxwutpdksVbhQ/4IiC', '', 0, '7y7y7y', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$dA8YF7ODQLqGOlQozvAthesap6rWpIDf.6Jsi8y3rSLqpUuS06XH6', ' 443');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (7, '2023-03-01 15:53:53.780952', '2023-03-01 15:53:53.780952', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$xN1juIDQSwhP0.pdLFTRM.eUPJtORwXOWZ4hLbolP0J74at/iXp6a', '', 0, 'yyrer9', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$n51WqWK8E.TPDEYG46kmEekYUj574bggon.ArAi.Jw1f7sDOjabj2', '');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (8, '2023-03-01 16:25:57.992755', '2023-03-01 16:25:57.992755', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$r.V3X2cobhDKWLmT3sTMPOBwthQz7P8hxLoq1mVtPQr9uNTnGs6YS', '', 0, 'o0ffkef', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$/o3Pbhh61K8IhMnfb29fpOrQlqOYSBLxZ.Gxu33h7w/Ui8DqqKk8.', '');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (9, '2023-03-01 16:26:51.814013', '2023-03-01 16:26:51.814013', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$xOOagTEV.rPF/qZ3FzIwPOUFfZu0WHq/fOoPL8lZWwRrQDz0LbukS', '', 0, 'nnef12', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$H9skYeLcGoTnck.JcwG5duJTdw7zWke3LH8MuTTXMYEqIa/bgcSXe', '');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (10, '2023-03-01 23:57:44.618614', '2023-03-01 23:58:25.796817', 0.00, 0.00, 0.00, b'1', 0.00, '$2a$10$PhNYGD83CXVvawF7Z4KOpOQ0zr19GEHAJ4eONXBlHc0iEKd5nW5/O', '', 0, 'as2323', '[]', '[]', b'0', 4, NULL, '业务员1', 0, b'1', '$2a$10$Qq.7qnJhc4ql6kkAVg3J0Oy0d2a48WfCoXw7CEYKmhns.Zyk4lwEK', '44');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (11, '2023-03-04 23:43:34.480232', '2023-03-04 23:47:57.183411', 0.00, 0.00, 0.00, b'1', 0.00, '$2a$10$.aOTFCs2/VPhn7eIUVxMhuIPEJWzj9wjy2r1oMxwwv6EIqGTHL/oW', '7I94BM', 2, 'test业务员', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'1', 5, NULL, NULL, 0, b'1', '$2a$10$A98vPF1Cj5obfiY8W/GiZ.HWym7q.KJCW8GDiy6y2vY7tHtqc.MPy', '');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (12, '2023-03-04 23:49:48.882988', '2023-03-04 23:49:55.419325', 0.00, 0.00, 0.00, b'0', 0.00, '$2a$10$/He3t/HANOf2kp/PldYe0Ousaw85sXNomG4mj8sTMlkm1yJ8pDx0W', 'XNFDGG', 2, '业务员test123', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', '[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]', b'1', 6, NULL, NULL, 0, b'1', '$2a$10$aOX5AjBG4RNgHXJtf6NSsed.a2ozM2PlgBOpmfAs5xlO4b6LJSS/m', '');
INSERT INTO `sales_man` (`id`, `create_at`, `update_at`, `balance`, `business_rate`, `customer_rate`, `enabled`, `froze_balance`, `password`, `share_code`, `share_permission`, `username`, `business_commission_config`, `customer_commission_config`, `sub_site`, `sub_site_id`, `belongs_to_id`, `belongs_to_user`, `sales_man_type`, `default_withdraw_password`, `withdraw_password`, `remark`) VALUES (13, '2023-03-04 23:59:10.582629', '2023-03-04 23:59:15.278431', 0.00, 0.00, 0.00, b'1', 0.00, '$2a$10$PpDD3sSDhMaYqYhQWAQgK.JpXtWhWK66RZaVQWDtzd6W/d9JvnwHa', '', 0, 'xxx111', '[]', '[]', b'0', 6, NULL, '业务员test123', 0, b'1', '$2a$10$Bc.qzj/Byo6TMvCiim.CJOCq0Il.d21EtpOz9C8G8DfmSBldM8HHe', '');
COMMIT;

-- ----------------------------
-- Table structure for sales_man_custom_price
-- ----------------------------
DROP TABLE IF EXISTS `sales_man_custom_price`;
CREATE TABLE `sales_man_custom_price` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `config_list` json DEFAULT NULL,
  `sub_site_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_man_custom_price
-- ----------------------------
BEGIN;
INSERT INTO `sales_man_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (1, '2023-03-04 23:47:57.243427', '2023-03-04 23:47:57.243427', '[{\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}]', 5, '通用');
INSERT INTO `sales_man_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (2, '2023-03-04 23:49:55.457604', '2023-03-04 23:49:55.457604', '[{\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}, {\"amount\": 0.5, \"percent\": false}]', 6, '通用');
INSERT INTO `sales_man_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (3, '2023-03-05 00:24:52.611304', '2023-03-05 00:24:52.611304', '[]', 6, 'xxx111');
INSERT INTO `sales_man_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (4, '2023-03-05 01:41:00.023062', '2023-03-05 01:41:00.023062', '[]', 4, 'e4r4rr4');
COMMIT;

-- ----------------------------
-- Table structure for sales_man_merchant_custom_price
-- ----------------------------
DROP TABLE IF EXISTS `sales_man_merchant_custom_price`;
CREATE TABLE `sales_man_merchant_custom_price` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `config_list` json DEFAULT NULL,
  `sub_site_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_man_merchant_custom_price
-- ----------------------------
BEGIN;
INSERT INTO `sales_man_merchant_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (1, '2023-03-04 23:47:57.191502', '2023-03-04 23:47:57.238440', '[{\"taskType\": \"手机淘宝任务\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝淘口令\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝无截图版\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝黑搜模式\", \"customPriceList\": []}, {\"taskType\": \"手淘搜索精准打标签\", \"customPriceList\": []}, {\"taskType\": \"手淘隔天任务\", \"customPriceList\": []}, {\"taskType\": \"手淘首页猜你喜欢\", \"customPriceList\": []}, {\"taskType\": \"手淘预览单\", \"customPriceList\": []}, {\"taskType\": \"手机京东\", \"customPriceList\": []}, {\"taskType\": \"京喜\", \"customPriceList\": []}, {\"taskType\": \"手机拼多多\", \"customPriceList\": []}, {\"taskType\": \"抖音小店任务\", \"customPriceList\": []}, {\"taskType\": \"洋淘\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝入口任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝直播任务\", \"customPriceList\": []}, {\"taskType\": \"拼拼多多二维码任务\", \"customPriceList\": []}, {\"taskType\": \"拼多多链接任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝二维码任务\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝淘口令裂变\", \"customPriceList\": []}, {\"taskType\": \"手淘聚划算\", \"customPriceList\": []}, {\"taskType\": \"手机阿里巴巴任务\", \"customPriceList\": []}, {\"taskType\": \"拼多多批发模式\", \"customPriceList\": []}, {\"taskType\": \"拼多多商家版任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝特价版\", \"customPriceList\": []}]', 5, '通用');
INSERT INTO `sales_man_merchant_custom_price` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `username`) VALUES (2, '2023-03-04 23:49:55.428106', '2023-03-04 23:49:55.452571', '[{\"taskType\": \"手机淘宝任务\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝淘口令\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝无截图版\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝黑搜模式\", \"customPriceList\": []}, {\"taskType\": \"手淘搜索精准打标签\", \"customPriceList\": []}, {\"taskType\": \"手淘隔天任务\", \"customPriceList\": []}, {\"taskType\": \"手淘首页猜你喜欢\", \"customPriceList\": []}, {\"taskType\": \"手淘预览单\", \"customPriceList\": []}, {\"taskType\": \"手机京东\", \"customPriceList\": []}, {\"taskType\": \"京喜\", \"customPriceList\": []}, {\"taskType\": \"手机拼多多\", \"customPriceList\": []}, {\"taskType\": \"抖音小店任务\", \"customPriceList\": []}, {\"taskType\": \"洋淘\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝入口任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝直播任务\", \"customPriceList\": []}, {\"taskType\": \"拼拼多多二维码任务\", \"customPriceList\": []}, {\"taskType\": \"拼多多链接任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝二维码任务\", \"customPriceList\": []}, {\"taskType\": \"手机淘宝淘口令裂变\", \"customPriceList\": []}, {\"taskType\": \"手淘聚划算\", \"customPriceList\": []}, {\"taskType\": \"手机阿里巴巴任务\", \"customPriceList\": []}, {\"taskType\": \"拼多多批发模式\", \"customPriceList\": []}, {\"taskType\": \"拼多多商家版任务\", \"customPriceList\": []}, {\"taskType\": \"淘宝特价版\", \"customPriceList\": []}]', 6, '通用');
COMMIT;

-- ----------------------------
-- Table structure for sales_man_sub_site
-- ----------------------------
DROP TABLE IF EXISTS `sales_man_sub_site`;
CREATE TABLE `sales_man_sub_site` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `principal_type` smallint DEFAULT NULL,
  `site_name` varchar(255) DEFAULT NULL,
  `sub_site_config` json DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_man_sub_site
-- ----------------------------
BEGIN;
INSERT INTO `sales_man_sub_site` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `principal_type`, `site_name`, `sub_site_config`) VALUES (4, '2023-02-17 18:35:38.155899', '2023-02-19 17:15:37.098298', '', '中国银行(1212)', 2, '业务员1', '{\"logo\": \"https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/VFN1QK2GXG1676798088102.jpg\", \"domain\": \"1\", \"serviceQQ\": \"4\", \"enableSales\": true, \"merchantRate\": 5, \"merchantTheme\": \"蓝色\", \"additionalRate\": 6, \"goodCommentRate\": 7, \"loginBackground\": \"https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/PTKHUMXLCX1676798076155.jpg\", \"highestSalesRate\": 8, \"merchantLoginPass\": \"3\", \"serviceWechatList\": [\"https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/8N22H7LILD1676798100911.jpg\", \"https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/2EHPICDBWB1676798105612.jpg\", \"https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/UQ8PX4RNIL1676798111994.jpg\"], \"highestFixedAmount\": 9, \"merchantInviteLink\": \"2\", \"principalReturnFee\": 11, \"principalReturnRate\": 10, \"enabledWithServiceSplit\": true, \"principalServiceFeeRate\": 12, \"principalServiceFeeType\": \"默认设置(0元)\", \"enableMerchantCommission\": true}');
INSERT INTO `sales_man_sub_site` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `principal_type`, `site_name`, `sub_site_config`) VALUES (5, '2023-03-04 23:47:57.166901', '2023-03-04 23:47:57.166901', '', '', 0, 'test业务员', NULL);
INSERT INTO `sales_man_sub_site` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `principal_type`, `site_name`, `sub_site_config`) VALUES (6, '2023-03-04 23:49:55.407699', '2023-03-05 00:56:43.273102', '', '', 0, '业务员test123', '{\"logo\": \"\", \"domain\": \"\", \"serviceQQ\": \"\", \"enableSales\": true, \"merchantRate\": 0, \"merchantTheme\": \"默认\", \"additionalRate\": 0, \"goodCommentRate\": 0, \"loginBackground\": \"\", \"highestSalesRate\": 0, \"merchantLoginPass\": \"\", \"serviceWechatList\": [], \"highestFixedAmount\": 0, \"merchantInviteLink\": \"\", \"principalReturnFee\": 0, \"principalReturnRate\": 0, \"enabledWithServiceSplit\": false, \"principalServiceFeeRate\": 0, \"principalServiceFeeType\": \"默认设置(0元)\", \"enableMerchantCommission\": false}');
COMMIT;

-- ----------------------------
-- Table structure for sales_man_sub_site_config
-- ----------------------------
DROP TABLE IF EXISTS `sales_man_sub_site_config`;
CREATE TABLE `sales_man_sub_site_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `config_list` json DEFAULT NULL,
  `sub_site_id` bigint NOT NULL,
  `task_type` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sales_man_sub_site_config
-- ----------------------------
BEGIN;
INSERT INTO `sales_man_sub_site_config` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `task_type`) VALUES (1, '2023-02-17 23:02:57.942938', '2023-02-17 23:04:07.676938', '[3, 4, 2]', 4, 0);
INSERT INTO `sales_man_sub_site_config` (`id`, `create_at`, `update_at`, `config_list`, `sub_site_id`, `task_type`) VALUES (2, '2023-02-17 23:02:57.978947', '2023-02-17 23:04:15.282466', '[9]', 4, 1);
COMMIT;

-- ----------------------------
-- Table structure for system_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `system_bank_card`;
CREATE TABLE `system_bank_card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_type` smallint DEFAULT NULL,
  `is_default` bit(1) NOT NULL,
  `issue_bank` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `receive_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_bank_card
-- ----------------------------
BEGIN;
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (12, '2023-02-16 14:54:12.000000', '2023-02-19 17:41:09.502155', '21323232', '微信', 2, b'0', '', '12', 'https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/AL9NQJVBFX1676799665814.jpg');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (15, '2023-02-16 15:51:17.000000', '2023-02-16 18:33:43.723252', '1212', '中国银行', 0, b'0', '', '12', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (16, '2023-02-16 15:53:06.000000', '2023-02-19 17:41:26.272897', '544545', '支付宝', 1, b'1', '', '2334223', 'https://click-farming-oss.oss-cn-hangzhou.aliyuncs.com/ILIK97PD931676799683606.jpg');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (17, '2023-02-16 15:55:15.921993', '2023-02-16 15:55:15.921993', '212332', '微信', 2, b'0', '', '323232', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (18, '2023-02-16 16:00:15.627163', '2023-02-16 16:00:15.627163', '4334', '支付宝', 1, b'0', '', '434334', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (19, '2023-02-16 16:06:03.611723', '2023-02-16 16:06:03.611723', '12323', '中国建设银行', 0, b'0', '', '323232', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (20, '2023-02-16 16:06:24.630091', '2023-02-16 16:06:24.630091', '2343t54', '中国建设银行', 0, b'0', '', '4543', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (21, '2023-02-16 16:07:03.718192', '2023-02-16 16:07:03.718192', '433434', '支付宝', 1, b'0', '', '4353434', '');
INSERT INTO `system_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_name`, `bank_type`, `is_default`, `issue_bank`, `real_name`, `receive_code`) VALUES (22, '2023-02-16 16:09:06.412767', '2023-02-16 16:09:06.412767', '2344334', '支付宝', 1, b'0', '', '34342', '');
COMMIT;

-- ----------------------------
-- Table structure for system_bank_item
-- ----------------------------
DROP TABLE IF EXISTS `system_bank_item`;
CREATE TABLE `system_bank_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_bank_item
-- ----------------------------
BEGIN;
INSERT INTO `system_bank_item` (`id`, `create_at`, `update_at`, `bank_name`) VALUES (1, '2023-02-15 19:27:22.064056', '2023-02-15 19:27:22.064056', '中国银行');
INSERT INTO `system_bank_item` (`id`, `create_at`, `update_at`, `bank_name`) VALUES (2, '2023-02-15 19:29:00.108608', '2023-02-15 19:29:00.108608', '中国建设银行');
INSERT INTO `system_bank_item` (`id`, `create_at`, `update_at`, `bank_name`) VALUES (3, '2023-02-15 19:30:04.182727', '2023-02-15 19:30:04.182727', '中国农业银行');
INSERT INTO `system_bank_item` (`id`, `create_at`, `update_at`, `bank_name`) VALUES (4, '2023-02-16 14:55:24.094740', '2023-02-16 14:55:24.094740', '香港银行');
COMMIT;

-- ----------------------------
-- Table structure for system_config_task_additional_price
-- ----------------------------
DROP TABLE IF EXISTS `system_config_task_additional_price`;
CREATE TABLE `system_config_task_additional_price` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `config_list` json DEFAULT NULL,
  `task_type` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_config_task_additional_price
-- ----------------------------
BEGIN;
INSERT INTO `system_config_task_additional_price` (`id`, `create_at`, `update_at`, `config_list`, `task_type`) VALUES (2, '2023-02-23 14:57:23.060069', '2023-02-23 14:57:23.060069', '[{\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 1}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 2}]', 0);
INSERT INTO `system_config_task_additional_price` (`id`, `create_at`, `update_at`, `config_list`, `task_type`) VALUES (3, '2023-02-23 14:57:48.444389', '2023-02-23 14:57:48.444389', '[{\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 3}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 4}]', 1);
INSERT INTO `system_config_task_additional_price` (`id`, `create_at`, `update_at`, `config_list`, `task_type`) VALUES (4, '2023-02-23 14:58:10.171977', '2023-02-23 14:58:10.171977', '[{\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 1}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 0}, {\"sort\": 0, \"enabeld\": false, \"isDefault\": false, \"commission\": 0, \"serviceFee\": 2}]', 2);
COMMIT;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `parent_id` bigint NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (1, '2023-02-09 05:10:17.150563', '2023-02-09 05:10:17.150582', b'1', 0, '资金管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (2, '2023-02-09 05:10:17.152013', '2023-02-09 05:10:17.152023', b'1', 0, '试客管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (3, '2023-02-09 05:10:17.152900', '2023-02-09 05:10:17.152911', b'1', 0, '商家管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (4, '2023-02-09 05:10:17.154190', '2023-02-09 05:10:17.154207', b'1', 0, '业务团队', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (5, '2023-02-09 05:10:17.155458', '2023-02-09 05:10:17.155471', b'1', 0, '任务管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (6, '2023-02-09 05:10:17.156214', '2023-02-09 05:10:17.156224', b'1', 0, '订单管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (7, '2023-02-09 05:10:17.157164', '2023-02-09 05:10:17.157175', b'1', 0, '统计报表', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (8, '2023-02-09 05:10:17.158005', '2023-02-09 05:10:17.158020', b'1', 0, '文章管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (9, '2023-02-09 05:10:17.158825', '2023-02-09 05:10:17.158834', b'1', 0, '申诉管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (10, '2023-02-09 05:10:17.159684', '2023-02-09 05:10:17.159693', b'1', 0, '活动管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (11, '2023-02-09 05:10:17.160469', '2023-02-09 05:10:17.160479', b'1', 0, '日志管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (12, '2023-02-09 05:10:17.161192', '2023-02-09 05:10:17.161201', b'1', 0, '基础管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (13, '2023-02-09 05:10:17.161992', '2023-02-09 05:10:17.162004', b'1', 0, '权限管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (14, '2023-02-09 05:10:17.163050', '2023-02-09 05:10:17.163061', b'1', 0, '站内信管理', NULL);
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (15, '2023-02-09 05:19:37.000000', '2023-02-09 05:19:39.000000', b'1', 1, '账户变动记录', 'AccountChangeRecord');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (16, '2023-02-09 05:20:38.000000', '2023-02-09 05:20:34.000000', b'1', 1, '账户充值', 'AccountRecharge');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (17, '2023-02-09 05:21:08.000000', '2023-02-09 05:21:06.000000', b'1', 1, '账户提现', 'AccountWithdrawal');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (18, '2023-02-09 05:29:43.000000', '2023-02-09 05:29:45.000000', b'1', 1, '账户任务', 'AccountTasks');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (19, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '试客审核', 'SkAudit');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (20, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '试客管理', 'SkManagement');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (21, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '身份证审核', 'SkIDCardAudit');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (22, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '帐号审核', 'SkAccountAudit');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (23, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '账号管理', 'SkAccountManagement');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (24, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 2, '试客资金', 'SkFunds');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (25, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 3, '商家列表', 'MerchantList');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (26, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 3, '商家店铺', 'MerchantShop');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (27, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 3, '商家资金', 'MerchantFunds');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (28, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 3, '点数充值记录', 'MerchantPointsRechargeRecord');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (29, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 3, '银行卡管理', 'MerchantBankCardManage');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (30, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 4, '业务员管理', 'ClerkManage');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (31, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 4, '业务员资金', 'ClerkFunds');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (32, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 4, '业务员审核', 'ClerkReview');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (33, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '所有任务', 'TaskAll');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (34, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '待审核任务', 'PendingTasks');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (35, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '进行中任务', 'TaskProgress');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (36, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '已完成任务', 'TaskCompleted');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (37, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '已终止任务', 'TerminatedTask');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (38, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 5, '审核未过任务', 'ReviewPendingTasks');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (39, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '所有订单', 'AllOrders');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (40, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '待接单订单', 'PendingOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (41, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '待操作订单', 'PendingOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (42, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '金额异常订单', 'OrderAbnormalAmount');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (43, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '驳回中订单', 'RejectOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (44, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '确认发货订单', 'ConfirmShippingOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (45, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '待评价订单', 'PendingEvaluationOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (46, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '待确认订单', 'PendingOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (47, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '待商家返款订单', 'WaitingMerchantRefundOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (48, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '已完成订单', 'CompletedOrder');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (49, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 6, '超时订单取消', 'OverTimeOrderCancellation');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (50, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '账号提现统计', 'AccountWithdrawalStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (51, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '商家充值统计', 'BusinessRechargeStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (52, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '充值提现汇总', 'SummaryDepositWithdrawal');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (53, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '帐户余额统计', 'AccountBalanceStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (54, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '提现冻结统计', 'WithdrawalFreezeStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (55, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '商家日报表', 'MerchantDailyReport');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (56, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '业务员日报表', 'SalesmanDailyReport');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (57, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '订单利润统计', 'OrderProfitStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (58, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '标签统计', 'LabelStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (59, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '数据统计报表', 'StatisticsReport');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (60, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '每日完成统计', 'StatisticsCompletedDaily');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (61, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '物流单号报表', 'LogisticsOrderNumberReport');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (62, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '增值服务统计', 'ValueAddedServiceStatistics');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (63, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 7, '增值服务记录', 'ValueAddedServiceRecords');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (64, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 8, '分类管理', 'CategoryManagement');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (65, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 8, '公告管理', 'AnnouncementManagement');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (66, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 9, '所有申诉', 'AllComplaints');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (67, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 9, '申诉中', 'Complaint');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (68, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 9, '申诉成功', 'SuccessfulAppeal');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (69, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 9, '申诉失败', 'AppealFailed');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (70, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 10, '红包活动', 'RedEnvelopeActivity');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (71, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 11, '登录日志', 'LoginLog');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (72, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 11, 'API日志', 'APILog');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (73, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 11, '系统资金操作日志', 'SystemFundOperation Log');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (74, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '网站配置', 'WebsiteConfiguration');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (75, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '增值服务', 'ValueAddedServices');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (76, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '示例配置', 'SampleConfiguration');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (77, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '任务时间', 'TaskTime');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (78, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '计划任务', 'ScheduledTasks');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (79, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '试客佣金', 'SkCommission');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (80, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '任务附加费', 'TaskSurcharge');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (81, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '好评任务费', 'PraiseTaskFee');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (82, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '任务标签', 'TaskLabel');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (83, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '短信配置', 'SMSConfiguration');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (84, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '银行卡', 'BankCard');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (85, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '物流方式', 'ShippingMethods');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (86, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '修改密码', 'ChangePassword');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (87, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '照妖镜验号配置', 'ZyjVerify');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (88, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '小爱验号配置', 'XaVerify');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (89, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '畅行验号配置', 'CxVerify');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (90, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '商返设置', 'TradeReturn');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (91, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 12, '企业支付宝', 'EnterpriseAlipay');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (92, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 13, '角色管理', 'RoleManage');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (93, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:48.000000', b'1', 13, '用户管理', 'UserManage');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (94, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:47.000000', b'1', 14, '站内信信息', 'ShowMessage');
INSERT INTO `system_menu` (`id`, `create_at`, `update_at`, `enabled`, `parent_id`, `permission`, `path`) VALUES (95, '2023-02-09 05:29:47.000000', '2023-02-09 05:29:47.000000', b'1', 14, '发起站内信', 'LaunchMessage');
COMMIT;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_role
-- ----------------------------
BEGIN;
INSERT INTO `system_role` (`id`, `create_at`, `update_at`, `role_name`, `enabled`) VALUES (8, '2023-02-12 17:21:04.825568', '2023-02-12 18:18:35.259875', '角色1', b'1');
INSERT INTO `system_role` (`id`, `create_at`, `update_at`, `role_name`, `enabled`) VALUES (9, '2023-02-12 17:21:11.123214', '2023-02-12 18:10:23.323722', '角色2', b'1');
INSERT INTO `system_role` (`id`, `create_at`, `update_at`, `role_name`, `enabled`) VALUES (10, '2023-02-12 17:21:15.527420', '2023-02-12 18:18:34.540792', '角色3', b'1');
COMMIT;

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKgy6porohks3gmindlykgsglyt` (`menu_id`),
  CONSTRAINT `FKgy6porohks3gmindlykgsglyt` FOREIGN KEY (`menu_id`) REFERENCES `system_menu` (`id`),
  CONSTRAINT `FKmi8x9d42tccm7lwo7uejwy3y2` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 1);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 2);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 15);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 16);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 17);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 18);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 19);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 20);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 21);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 22);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 23);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (8, 24);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (9, 71);
INSERT INTO `system_role_menu` (`role_id`, `menu_id`) VALUES (9, 73);
COMMIT;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `fund_operate_password` varchar(255) DEFAULT NULL,
  `transfer_password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_user
-- ----------------------------
BEGIN;
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (1, '2023-02-12 03:20:30.000000', '2023-02-12 18:47:42.397621', b'1', '$2a$10$/lf9AVp2W9uvv7VHpWBHvu8E1L127cQpMH4seGb.skOQs.0D/8ZSK', 'admin', '$2a$10$/lf9AVp2W9uvv7VHpWBHvu8E1L127cQpMH4seGb.skOQs.0D/8ZSK', '$2a$10$/lf9AVp2W9uvv7VHpWBHvu8E1L127cQpMH4seGb.skOQs.0D/8ZSK');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (12, '2023-02-12 18:16:22.054831', '2023-02-12 18:18:16.296558', b'0', '$2a$10$rRoFqPpjwWE8dbOD2/FT/.SlAzjbraLrOkjz/WWHtm8t39MQsxF4a', 'admin7', '$2a$10$iPTcqHp2YBcxXt6fOMEGr..CJp/S9t6HjWDP7/RPLfcamkrHZvGT6', '$2a$10$xBII7Cofx0fPjZlpmInJHOqkgcYs8PckvGCpwHVrx/7V1rVwq8qPW');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (13, '2023-02-12 18:27:36.532768', '2023-02-12 18:27:36.532768', b'0', '$2a$10$5ukOudYMEP.8z0LAEFx9OOryLUeOFVJr6Pffm1th7PSin2elRyOoq', 'admintest', '$2a$10$lBVZp8vZjC3i/Srl5eifHehjABEldnbNheDiXsz6VWx6UwfhT31RG', '$2a$10$8Gu8ke5AYC1NNm8UnRruFe6o9YtWP8mlZIelYFc7UgcIHgLM.mSOq');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (14, '2023-02-12 18:51:42.222737', '2023-02-12 22:36:08.979243', b'0', '$2a$10$hC4zY60U2CVlkfCO/uj24ulnK6gFN53Hrl7/ABWSmL4dwx0wEfMmu', '123', '$2a$10$a6H0WFF4AeSgr3NkiYv.Vut.Fiv9F0A33vqTs.QBvrS12pHYm6vAy', '$2a$10$x/q/TOuPFBHRBIrhnJv4.uJUZuNKzN8EZTuGipBFaK18J.F1opm6e');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (15, '2023-02-12 18:52:05.552804', '2023-02-12 21:41:10.485048', b'0', '$2a$10$rL..SBn4klmGHvUHfYETbuFSYQv3d8FbNEpuoYsUAb/CRG5Wpjewe', '`123232', '$2a$10$IWZnagEd1MHznEza42KlH.PaJiNbGxWBJuVxuimslIduJ1VPvQgga', '$2a$10$kpqj3r.wJtwnbldXdMbLj.xIg3DnldKFGoaSQmknw55pCZw.7t0PC');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (16, '2023-02-12 20:21:55.072833', '2023-02-12 21:40:48.447332', b'0', '$2a$10$gkcaTnEUZhhR4O4XrpD/BePKtnjisNNybXf5Jhy34CwFjXaKkm.NK', '123ffffff', '$2a$10$nrZ.WX32Lp5wF7W6qfLk1ezgji26aLm2JuSVc1fJLyjqF.72Hq0Tu', '$2a$10$mCNBv1XSDk4Pd6mjItydqeN57jBBwdImtLmC8IyN00LJhP5NeIpR.');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (17, '2023-02-12 21:43:14.226560', '2023-02-12 22:35:47.120273', b'1', '$2a$10$hC4zY60U2CVlkfCO/uj24ulnK6gFN53Hrl7/ABWSmL4dwx0wEfMmu', '123321', '$2a$10$0PL5hABh3aZJ5N46JJiEDu7Mf5RKQlC1.Nb/HUiLFznrJapS7OtkC', '$2a$10$XwZWugKmcU8nrp4q3wJ83.WKcINaaOAkXRLxvTyJr8bmMH51Y688e');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (18, '2023-02-12 22:39:16.662167', '2023-02-12 22:40:41.457771', b'0', '$2a$10$J9kIMRl4VspG9ooyepQwcuoIeC8AP4GtF10NfoI4YFMErJT8DEsOO', '老k123', '$2a$10$9ZpKkXFKe8dRAj0G2s7GzuElU3Y7.fzXSHf6EMYgIAJkHT1zQl2bK', '$2a$10$ivvNoUrFNf7CKXuW8dJ3a.8S04C4UBoHzXrblkn7ftPzMiAD6jCcS');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (19, '2023-02-12 22:42:14.134439', '2023-02-12 22:44:14.235415', b'0', '$2a$10$33IZEdlEDPq0sQHAlrfw1.gVuo7FQkgtCQWaMAIN/cEoDMF5jvWPC', '谢总牛逼', '$2a$10$M9q9JF.bFzA9NGPq16zUnuaf5gQqu7c2hFH0YWcRFT82CjCTbnJ1m', '$2a$10$YD1Ue1twLfDSY8P1/jPyb.BHyWhW0gJSCH9doOdMNm/AwicfzZA0m');
INSERT INTO `system_user` (`id`, `create_at`, `update_at`, `enabled`, `password`, `username`, `fund_operate_password`, `transfer_password`) VALUES (20, '2023-02-14 17:32:35.759295', '2023-02-14 17:32:45.221481', b'1', '$2a$10$gVtwtqg1iBsiB3DBTBCFEe2hRAfV3TELRtXO7JtQdZw0GMobGsi4y', 'cewcwe123', '$2a$10$IoVVv9iZZe0Hl7wSHFX8pOZkNyrP.HSBPNF7uGE36ru0bZ/T0EYyC', '$2a$10$QMEw3VnLvzrxApkkOA8eS.2K719CXkf8htmiL2gJZmEyl9tS5Fw9G');
COMMIT;

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKnp61n3syn415rmbwvhnw87u3a` (`role_id`),
  CONSTRAINT `FKkc6ik04bm9v9kldgbt3kkgfac` FOREIGN KEY (`user_id`) REFERENCES `system_user` (`id`),
  CONSTRAINT `FKnp61n3syn415rmbwvhnw87u3a` FOREIGN KEY (`role_id`) REFERENCES `system_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
BEGIN;
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (14, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (15, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (16, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (17, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (19, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (20, 8);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (14, 9);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (15, 9);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (17, 9);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (19, 9);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (20, 9);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (17, 10);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (19, 10);
INSERT INTO `system_user_role` (`user_id`, `role_id`) VALUES (20, 10);
COMMIT;

-- ----------------------------
-- Table structure for user_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `user_bank_card`;
CREATE TABLE `user_bank_card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `update_at` datetime(6) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `bank_type` varchar(255) DEFAULT NULL,
  `issue_bank` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `state` smallint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_type` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_bank_card
-- ----------------------------
BEGIN;
INSERT INTO `user_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_type`, `issue_bank`, `real_name`, `state`, `user_id`, `user_type`) VALUES (1, '2023-03-02 21:02:52.073620', '2023-03-02 21:27:22.481523', '22222222222234', '1', '2222', '233', 1, 1, 2);
INSERT INTO `user_bank_card` (`id`, `create_at`, `update_at`, `account`, `bank_type`, `issue_bank`, `real_name`, `state`, `user_id`, `user_type`) VALUES (4, '2023-03-02 21:27:16.956171', '2023-03-02 21:27:25.757288', '3333333333333', '3', '22', '44', 1, 1, 2);
COMMIT;

-- ----------------------------
-- Table structure for user_withdraw_request
-- ----------------------------
DROP TABLE IF EXISTS `user_withdraw_request`;
CREATE TABLE `user_withdraw_request` (
  `id` bigint NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `state` smallint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `user_type` smallint DEFAULT NULL,
  `bank_card_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_withdraw_request
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

-- 登录帐号test 密码test

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '客户',
  `bill_type` smallint(4) NOT NULL DEFAULT '0' COMMENT '0.未结 1.结部分 2.已结',
  `bill_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '欠款金额',
  `pay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已付款',
  `bill_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '立据时间',
  `remark` varchar(256) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户欠款';

-- ----------------------------
-- Records of bill
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(64) NOT NULL COMMENT '商品名',
  `goods_type` smallint(4) NOT NULL COMMENT '0.粉类 1.浆类 2.乳液类 3.沙子 4.小器具 5.其他',
  `sale_type` smallint(4) NOT NULL DEFAULT '1' COMMENT '0.转卖 1.自己生产 2.生产原料,非卖品',
  `stock_count` int(11) NOT NULL DEFAULT '0' COMMENT '库存量',
  `unit_type` smallint(6) NOT NULL COMMENT '0.袋 1.桶 2.瓶 3.个 4.公斤',
  `weight` decimal(11,2) DEFAULT NULL COMMENT '每份的重量',
  `weight_type` smallint(4) DEFAULT NULL COMMENT '0.克 1.千克 2.吨',
  `original_price` decimal(11,2) NOT NULL COMMENT '进价/成本价',
  `wholesale_price` decimal(11,2) DEFAULT NULL COMMENT '批发价',
  `retail_price` decimal(11,2) DEFAULT NULL COMMENT '零售价',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '0.失效 1.有效',
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `goodsName` (`goods_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '真石漆008', '2', '1', '86', '1', '60.00', '1', '2.00', '3.00', '3.50', '0');
INSERT INTO `goods` VALUES ('4', '真石漆007', '0', '1', '110', '0', '1.00', '1', '1.00', '1.00', '1.00', '0');
INSERT INTO `goods` VALUES ('5', '天能水泥', '0', '0', '100', '0', '25.00', '1', '14.00', '17.00', '20.00', '0');
INSERT INTO `goods` VALUES ('6', '层山水泥', '0', '0', '56', '0', '25.00', '1', '14.00', '17.00', '20.00', '0');
INSERT INTO `goods` VALUES ('7', '其它水泥', '0', '1', '100', '0', '25.00', '1', '12.00', '14.00', '16.00', '1');
INSERT INTO `goods` VALUES ('8', '内墙水泥', '0', '1', '100', '0', '22.00', '1', '9.10', '11.00', '12.00', '1');
INSERT INTO `goods` VALUES ('9', '外墙腻子', '0', '1', '100', '0', '25.00', '1', '13.60', '26.00', '28.00', '1');
INSERT INTO `goods` VALUES ('10', '高光水泥', '0', '1', '100', '0', '15.00', '1', '7.30', '9.00', '12.00', '1');
INSERT INTO `goods` VALUES ('11', '环保腻子粉', '0', '1', '100', '0', '12.50', '1', '5.90', '8.50', '10.00', '1');
INSERT INTO `goods` VALUES ('12', '环保腻子粉25', '0', '1', '100', '0', '25.00', '1', '11.50', '17.00', '20.00', '1');
INSERT INTO `goods` VALUES ('13', '钢化', '0', '1', '100', '0', '12.50', '1', '6.10', '8.50', '10.00', '1');
INSERT INTO `goods` VALUES ('14', '粉刷石膏', '0', '1', '100', '0', '15.00', '1', '8.60', '12.00', '15.00', '1');
INSERT INTO `goods` VALUES ('15', '嵌缝膏', '0', '1', '100', '0', '10.00', '1', '6.80', '10.00', '15.00', '1');
INSERT INTO `goods` VALUES ('16', '滑石粉', '0', '0', '100', '0', '30.00', '1', '7.20', '10.00', '12.00', '1');
INSERT INTO `goods` VALUES ('17', '胶水(二801)', '5', '1', '100', '0', '12.50', '1', '5.00', '8.00', '10.00', '1');
INSERT INTO `goods` VALUES ('18', '胶水(10kg)', '5', '1', '100', '0', '10.00', '1', '3.70', '5.00', '6.00', '1');
INSERT INTO `goods` VALUES ('19', '胶水(8kg)', '5', '1', '100', '0', '8.00', '1', '3.20', '4.50', '6.00', '1');
INSERT INTO `goods` VALUES ('20', '胶水(黑801)', '5', '1', '100', '0', '12.50', '1', '9.00', '12.00', '15.00', '1');
INSERT INTO `goods` VALUES ('21', '胶水(黑901)', '5', '1', '100', '0', '12.50', '1', '12.00', '20.00', '20.00', '1');
INSERT INTO `goods` VALUES ('22', '木乳㬵(7.5)', '2', '0', '100', '1', '7.50', '1', '30.00', '32.00', '35.00', '1');
INSERT INTO `goods` VALUES ('23', '木乳㬵(3)', '1', '0', '100', '1', '3.00', '1', '15.00', '18.00', '18.00', '1');
INSERT INTO `goods` VALUES ('24', '木乳㬵(1)', '1', '0', '90', '1', '1.00', '1', '5.00', '8.00', '8.00', '1');
INSERT INTO `goods` VALUES ('25', '接缝纸', '5', '0', '100', '3', '0.50', '1', '5.00', '8.00', '10.00', '1');
INSERT INTO `goods` VALUES ('26', '网带(10mm)', '5', '0', '100', '3', '0.20', '1', '6.00', '8.00', '10.00', '1');
INSERT INTO `goods` VALUES ('27', '网带(5mm)', '5', '0', '100', '3', '0.10', '1', '4.00', '7.00', '10.00', '1');
INSERT INTO `goods` VALUES ('28', '美纹纸5mm', '5', '0', '100', '3', '0.10', '1', '1.21', '2.00', '2.00', '1');
INSERT INTO `goods` VALUES ('29', '美纹纸3mm', '5', '0', '100', '3', '0.10', '1', '0.91', '1.50', '1.50', '1');
INSERT INTO `goods` VALUES ('30', '美纹纸2mm', '5', '0', '100', '3', '0.10', '1', '0.62', '0.80', '1.00', '1');
INSERT INTO `goods` VALUES ('31', '美纹纸真石漆用2mm', '5', '0', '100', '3', '0.01', '1', '1.50', '1.80', '2.00', '1');
INSERT INTO `goods` VALUES ('32', '美纹纸真石漆用1.5mm', '5', '0', '100', '3', '0.01', '1', '1.00', '1.20', '1.50', '1');
INSERT INTO `goods` VALUES ('33', '美纹纸真石漆用1mm', '5', '0', '100', '3', '0.01', '1', '0.80', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('34', '阴角80g', '5', '0', '100', '3', '0.08', '1', '0.34', '0.50', '0.80', '1');
INSERT INTO `goods` VALUES ('35', '阴角120g', '5', '0', '100', '3', '0.12', '1', '0.80', '1.00', '1.20', '1');
INSERT INTO `goods` VALUES ('36', '阳角80g', '5', '0', '100', '3', '0.08', '1', '0.34', '0.50', '0.80', '1');
INSERT INTO `goods` VALUES ('37', '阳角120g', '5', '0', '100', '3', '0.12', '1', '0.80', '1.00', '1.20', '1');
INSERT INTO `goods` VALUES ('38', '保护膜110cm', '5', '0', '100', '3', '0.05', '1', '4.00', '6.00', '8.00', '1');
INSERT INTO `goods` VALUES ('39', '保护膜550cm', '5', '0', '100', '3', '0.03', '1', '3.50', '5.00', '6.00', '1');
INSERT INTO `goods` VALUES ('40', '刷子8寸', '4', '0', '100', '3', '0.01', '1', '3.00', '5.00', '5.00', '1');
INSERT INTO `goods` VALUES ('41', '刷子4寸', '4', '0', '100', '3', '0.01', '1', '1.40', '3.00', '3.00', '1');
INSERT INTO `goods` VALUES ('42', '刷子3寸', '4', '0', '100', '3', '0.01', '1', '1.00', '2.00', '2.00', '1');
INSERT INTO `goods` VALUES ('43', '刷子2寸', '4', '0', '100', '3', '0.01', '1', '0.80', '1.50', '2.00', '1');
INSERT INTO `goods` VALUES ('44', '刷子1寸', '4', '0', '90', '3', '0.01', '1', '0.50', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('45', '砂纸好(360)', '5', '0', '100', '3', '0.01', '1', '0.70', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('46', '砂纸好(240)', '5', '0', '100', '3', '0.01', '1', '0.70', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('47', '砂纸好(180)', '5', '0', '100', '3', '0.01', '1', '0.70', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('48', '水砂纸(240)', '5', '0', '50', '3', '0.01', '1', '0.30', '0.50', '0.50', '1');
INSERT INTO `goods` VALUES ('49', '水砂纸(180)', '5', '0', '100', '3', '0.01', '1', '0.30', '0.50', '0.50', '1');
INSERT INTO `goods` VALUES ('50', '砂纸架', '4', '0', '100', '3', '0.20', '1', '1.00', '3.00', '3.00', '1');
INSERT INTO `goods` VALUES ('51', '大抹(双柄60)', '4', '0', '100', '3', '0.60', '1', '6.50', '10.00', '10.00', '1');
INSERT INTO `goods` VALUES ('52', '小抹', '4', '0', '100', '3', '0.10', '1', '1.50', '0.00', '2.00', '1');
INSERT INTO `goods` VALUES ('53', '腻刀4寸', '4', '0', '100', '3', '0.20', '1', '0.80', '0.00', '1.00', '1');
INSERT INTO `goods` VALUES ('54', '腻刀3寸', '4', '0', '100', '3', '0.15', '1', '0.60', '0.00', '1.00', '1');
INSERT INTO `goods` VALUES ('55', '腻刀2寸', '4', '0', '100', '3', '0.10', '1', '0.60', '0.00', '1.00', '1');
INSERT INTO `goods` VALUES ('56', '腻刀1寸', '4', '0', '100', '3', '0.10', '1', '0.50', '0.00', '1.00', '1');
INSERT INTO `goods` VALUES ('57', '塑料刮', '4', '0', '100', '3', '0.05', '1', '0.65', '1.00', '1.00', '1');
INSERT INTO `goods` VALUES ('58', '铲刀(30mm)', '4', '0', '100', '3', '0.20', '1', '4.00', '5.00', '8.00', '1');
INSERT INTO `goods` VALUES ('59', '铲刀(15mm)', '4', '0', '100', '3', '0.20', '1', '4.00', '5.00', '5.00', '1');
INSERT INTO `goods` VALUES ('60', '内漆201500', '2', '1', '100', '1', '20.00', '1', '36.50', '50.00', '80.00', '1');
INSERT INTO `goods` VALUES ('61', '内漆202600', '2', '1', '100', '1', '20.00', '1', '73.00', '100.00', '120.00', '1');
INSERT INTO `goods` VALUES ('62', '内漆203600', '2', '1', '100', '1', '20.00', '1', '96.00', '120.00', '180.00', '1');
INSERT INTO `goods` VALUES ('63', '内漆205400', '2', '1', '100', '1', '20.00', '1', '150.00', '180.00', '260.00', '1');
INSERT INTO `goods` VALUES ('64', '净味5400', '2', '1', '100', '1', '20.00', '1', '160.00', '280.00', '380.00', '1');
INSERT INTO `goods` VALUES ('65', '内漆高光漆', '2', '1', '100', '1', '19.00', '1', '180.00', '280.00', '400.00', '1');
INSERT INTO `goods` VALUES ('66', '净味五合一', '2', '1', '100', '1', '20.00', '1', '180.00', '280.00', '400.00', '1');
INSERT INTO `goods` VALUES ('68', '外墙102400', '2', '1', '100', '1', '20.00', '1', '65.00', '85.00', '120.00', '1');
INSERT INTO `goods` VALUES ('69', '外墙10300', '2', '1', '100', '1', '20.00', '1', '67.00', '105.00', '125.00', '1');
INSERT INTO `goods` VALUES ('70', '外墙103600', '2', '1', '100', '1', '20.00', '1', '83.00', '125.00', '180.00', '1');
INSERT INTO `goods` VALUES ('71', '外墙105400', '2', '1', '100', '1', '20.00', '1', '118.00', '180.00', '260.00', '1');
INSERT INTO `goods` VALUES ('72', '外墙弹性106600', '2', '1', '100', '1', '20.00', '1', '160.00', '220.00', '320.00', '1');
INSERT INTO `goods` VALUES ('73', '外墙高光110400', '2', '1', '100', '1', '19.00', '1', '230.00', '380.00', '600.00', '1');
INSERT INTO `goods` VALUES ('74', '防水(5kg)', '2', '1', '100', '1', '5.00', '1', '51.00', '80.00', '100.00', '1');
INSERT INTO `goods` VALUES ('75', '防水(10kg)', '2', '1', '100', '1', '10.00', '1', '120.00', '160.00', '200.00', '1');
INSERT INTO `goods` VALUES ('76', '防水(20kg)', '2', '1', '100', '1', '20.00', '1', '200.00', '280.00', '400.00', '1');
INSERT INTO `goods` VALUES ('77', '涂料400600', '2', '1', '100', '1', '20.00', '1', '30.00', '50.00', '60.00', '1');
INSERT INTO `goods` VALUES ('78', '罩光漆', '2', '1', '100', '1', '17.50', '1', '130.00', '180.00', '280.00', '1');
INSERT INTO `goods` VALUES ('79', '铁桶', '4', '0', '100', '3', '1.00', '1', '11.50', '12.00', '13.00', '1');
INSERT INTO `goods` VALUES ('80', '大红9815', '1', '0', '90', '2', '1.00', '1', '260.00', '320.00', '360.00', '1');
INSERT INTO `goods` VALUES ('81', '大红9815（小瓶）', '1', '1', '100', '2', '0.05', '1', '18.00', '20.00', '22.00', '1');
INSERT INTO `goods` VALUES ('82', '滚筒（无死角）差', '4', '0', '100', '3', '0.05', '1', '1.40', '2.50', '3.00', '1');

-- ----------------------------
-- Table structure for order_base
-- ----------------------------
DROP TABLE IF EXISTS `order_base`;
CREATE TABLE `order_base` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL COMMENT '顾客id',
  `order_type` smallint(4) NOT NULL COMMENT '0.未配送 1.配送中 2.配送完成',
  `delivery_type` smallint(4) DEFAULT NULL COMMENT '0.客户上门 1.自己取送 2.其他人取送',
  `delivery_address` varchar(128) DEFAULT NULL COMMENT '配送地址',
  `delivery_user_id` int(11) DEFAULT NULL COMMENT '配送者',
  `delivery_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '配送费',
  `pay_type` smallint(4) NOT NULL COMMENT '0.未付款 1.付部分 2.已付款',
  `pay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已付款金额',
  `unpay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未付款金额',
  `total_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '各个商品总价',
  `order_time` timestamp NULL DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_base
-- ----------------------------
INSERT INTO `order_base` VALUES ('8', '2', '2', '0', '', null, '0.00', '0', '0.00', '0.00', '10.00', '2018-05-27 13:55:17', '', 'xuchen', '2018-05-27 13:55:46', null, '2018-05-27 13:58:57');
INSERT INTO `order_base` VALUES ('9', '5', '0', '0', '', null, '0.00', '0', '0.00', '0.00', '13.00', '2018-05-27 13:58:59', '', 'xuchen', '2018-05-27 13:59:07', null, '2018-05-27 13:59:34');
INSERT INTO `order_base` VALUES ('10', '2', '0', '0', '', null, '0.00', '0', '0.00', '0.00', '102.00', '2018-05-27 13:59:38', '', 'xuchen', '2018-05-27 13:59:44', null, '2018-06-02 23:37:44');
INSERT INTO `order_base` VALUES ('11', '2', '0', '0', '', null, '0.00', '0', '0.00', '0.00', '285.00', '2018-06-02 09:38:47', '', 'xuchen', '2018-06-02 09:38:56', null, '2018-06-02 23:39:11');
INSERT INTO `order_base` VALUES ('12', '7', '0', '0', '', null, '0.00', '0', '0.00', '0.00', '3564.00', '2018-06-02 23:29:17', '', 'xuchen', '2018-06-02 23:29:23', null, '2018-06-02 23:39:27');

-- ----------------------------
-- Table structure for order_goods
-- ----------------------------
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `order_goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  `goods_sale_money` decimal(11,2) NOT NULL COMMENT '实际出售单价',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_goods_id`),
  UNIQUE KEY `goodsAndOrder` (`order_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_goods
-- ----------------------------
INSERT INTO `order_goods` VALUES ('104', '8', '1', '1', '1.00', 'xuchen', '2018-05-27 13:56:58', null, null);
INSERT INTO `order_goods` VALUES ('105', '8', '7', '3', '1.00', 'xuchen', '2018-05-27 13:57:07', null, '2018-05-27 13:58:48');
INSERT INTO `order_goods` VALUES ('106', '8', '6', '2', '1.00', 'xuchen', '2018-05-27 13:58:42', null, null);
INSERT INTO `order_goods` VALUES ('107', '8', '8', '4', '1.00', 'xuchen', '2018-05-27 13:58:57', null, null);
INSERT INTO `order_goods` VALUES ('108', '9', '1', '1', '1.00', 'xuchen', '2018-05-27 13:59:17', null, null);
INSERT INTO `order_goods` VALUES ('109', '9', '6', '3', '1.00', 'xuchen', '2018-05-27 13:59:23', null, null);
INSERT INTO `order_goods` VALUES ('110', '9', '7', '4', '1.00', 'xuchen', '2018-05-27 13:59:28', null, null);
INSERT INTO `order_goods` VALUES ('111', '9', '10', '5', '1.00', 'xuchen', '2018-05-27 13:59:34', null, null);
INSERT INTO `order_goods` VALUES ('112', '10', '8', '1', '1.00', 'xuchen', '2018-05-27 13:59:56', null, null);
INSERT INTO `order_goods` VALUES ('113', '10', '1', '1', '1.00', 'xuchen', '2018-05-27 13:59:59', null, null);
INSERT INTO `order_goods` VALUES ('114', '12', '1', '5', '4.00', 'xuchen', '2018-06-02 23:29:30', null, null);
INSERT INTO `order_goods` VALUES ('115', '12', '6', '44', '1.00', 'xuchen', '2018-06-02 23:29:37', null, null);
INSERT INTO `order_goods` VALUES ('116', '10', '24', '10', '10.00', 'xuchen', '2018-06-02 23:37:44', null, null);
INSERT INTO `order_goods` VALUES ('117', '11', '1', '9', '5.00', 'xuchen', '2018-06-02 23:38:56', null, null);
INSERT INTO `order_goods` VALUES ('118', '11', '44', '10', '4.00', 'xuchen', '2018-06-02 23:39:03', null, null);
INSERT INTO `order_goods` VALUES ('119', '11', '48', '50', '4.00', 'xuchen', '2018-06-02 23:39:11', null, null);
INSERT INTO `order_goods` VALUES ('120', '12', '80', '10', '350.00', 'xuchen', '2018-06-02 23:39:27', null, null);

-- ----------------------------
-- Table structure for purchase_base
-- ----------------------------
DROP TABLE IF EXISTS `purchase_base`;
CREATE TABLE `purchase_base` (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `supplier_id` int(11) NOT NULL COMMENT '供货商',
  `pay_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0.未付款 1.付部分 2.已付款',
  `total_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应付金额',
  `delivery_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  `pay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已付款',
  `unpay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未付款',
  `purchase_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '进货时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '付款时间',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`purchase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='进货表';

-- ----------------------------
-- Records of purchase_base
-- ----------------------------
INSERT INTO `purchase_base` VALUES ('3', '20', '1', '0.00', '11.00', '11.00', '111.00', '2018-06-02 09:41:54', '2018-06-02 09:41:54', '', '2018-06-02 09:42:08');

-- ----------------------------
-- Table structure for purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `purchase_detail`;
CREATE TABLE `purchase_detail` (
  `purchase_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `purchase_base_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL COMMENT 'goodsId',
  `goods_count` int(11) NOT NULL DEFAULT '0' COMMENT '进货量',
  PRIMARY KEY (`purchase_detail_id`),
  UNIQUE KEY `baseIdAndGoodsId` (`purchase_base_id`,`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchase_detail
-- ----------------------------

-- ----------------------------
-- Table structure for return_base
-- ----------------------------
DROP TABLE IF EXISTS `return_base`;
CREATE TABLE `return_base` (
  `return_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL COMMENT '顾客id',
  `return_address` varchar(128) DEFAULT NULL COMMENT '取回地址',
  `order_type` smallint(4) unsigned zerofill NOT NULL COMMENT '0.未取货 1.取货中 2.取货完成',
  `delivery_type` smallint(4) NOT NULL DEFAULT '1' COMMENT '0.客户上门 1.自己取送 2.其他人取送',
  `return_user_id` int(11) DEFAULT NULL COMMENT '取货者',
  `delivery_price` decimal(11,2) DEFAULT '0.00' COMMENT '取货费',
  `pay_type` smallint(4) NOT NULL COMMENT '0.未付款 1.付部分 2.已付款',
  `total_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应退总额',
  `pay_money` decimal(11,2) DEFAULT '0.00' COMMENT '已退金额',
  `unpay_money` decimal(11,2) DEFAULT '0.00' COMMENT '尚未退金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `return_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '退单时间',
  `create_user` varchar(16) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`return_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of return_base
-- ----------------------------

-- ----------------------------
-- Table structure for return_goods
-- ----------------------------
DROP TABLE IF EXISTS `return_goods`;
CREATE TABLE `return_goods` (
  `return_goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  `goods_real_money` decimal(11,4) NOT NULL COMMENT '实际退回单价',
  `remark` varchar(128) DEFAULT NULL COMMENT '商品描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`return_goods_id`),
  UNIQUE KEY `returnAndGoods` (`order_id`,`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of return_goods
-- ----------------------------

-- ----------------------------
-- Table structure for statistics_goods_month
-- ----------------------------
DROP TABLE IF EXISTS `statistics_goods_month`;
CREATE TABLE `statistics_goods_month` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month_time` int(11) NOT NULL COMMENT 'yyyymm',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `sale_count` int(11) NOT NULL COMMENT '该月销售次数',
  `sale_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '该月销售总额',
  `original_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总成本价',
  `gain_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总利润',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of statistics_goods_month
-- ----------------------------
INSERT INTO `statistics_goods_month` VALUES ('1', '201805', '1', '3', '3.00', '2.00', '1.00');
INSERT INTO `statistics_goods_month` VALUES ('2', '201805', '6', '5', '5.00', '84.00', '-79.00');
INSERT INTO `statistics_goods_month` VALUES ('3', '201805', '7', '7', '7.00', '84.00', '-77.00');
INSERT INTO `statistics_goods_month` VALUES ('4', '201805', '8', '5', '5.00', '72.80', '-67.80');
INSERT INTO `statistics_goods_month` VALUES ('5', '201805', '10', '5', '5.00', '73.00', '-68.00');
INSERT INTO `statistics_goods_month` VALUES ('6', '201805', '24', '10', '100.00', '120.00', '-20.00');
INSERT INTO `statistics_goods_month` VALUES ('7', '201806', '1', '14', '65.00', '2.00', '63.00');
INSERT INTO `statistics_goods_month` VALUES ('8', '201806', '6', '44', '44.00', '84.00', '-40.00');
INSERT INTO `statistics_goods_month` VALUES ('9', '201806', '44', '10', '40.00', '22.00', '18.00');
INSERT INTO `statistics_goods_month` VALUES ('10', '201806', '48', '50', '200.00', '14.40', '185.60');
INSERT INTO `statistics_goods_month` VALUES ('11', '201806', '80', '10', '3500.00', '20800.00', '-17300.00');

-- ----------------------------
-- Table structure for statistics_user_month
-- ----------------------------
DROP TABLE IF EXISTS `statistics_user_month`;
CREATE TABLE `statistics_user_month` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month_time` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_cost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总消费',
  `total_original` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总成本',
  `total_gain` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总利润',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户每月消费';

-- ----------------------------
-- Records of statistics_user_month
-- ----------------------------
INSERT INTO `statistics_user_month` VALUES ('1', '201805', '2', '112.00', '163.50', '-51.50');
INSERT INTO `statistics_user_month` VALUES ('2', '201805', '5', '13.00', '128.50', '-115.50');
INSERT INTO `statistics_user_month` VALUES ('3', '201806', '2', '285.00', '38.00', '247.00');
INSERT INTO `statistics_user_month` VALUES ('4', '201806', '7', '3564.00', '3226.00', '338.00');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(64) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(64) DEFAULT NULL COMMENT '授权',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型   0：目录   1：菜单',
  `img` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', '', '0', '&#xe614;', '99', '2018-03-30 11:24:01', '2018-04-14 11:03:42');
INSERT INTO `sys_menu` VALUES ('2', '1', '用户管理', '/sysUser', 'sysUser', '1', null, '10', '2018-03-30 11:24:28', '2018-04-10 17:07:33');
INSERT INTO `sys_menu` VALUES ('3', '1', '菜单管理', '/sysMenu', 'sysMenu', '1', null, '99', '2018-03-30 11:24:28', '2018-04-10 17:07:38');
INSERT INTO `sys_menu` VALUES ('4', '0', '商品数据', null, null, '0', '&#xe698;', '8', '2018-03-30 11:25:26', '2018-03-30 11:25:35');
INSERT INTO `sys_menu` VALUES ('5', '4', '商品', '/goods', 'goods', '1', '', '1', '2018-03-30 11:25:58', '2018-04-18 23:01:42');
INSERT INTO `sys_menu` VALUES ('6', '1', '权限管理', '/sysRole', 'sysRole', '1', null, '5', '2018-04-03 10:03:28', '2018-05-13 20:28:02');
INSERT INTO `sys_menu` VALUES ('7', '0', '用户数据', null, null, '0', '&#xe612;', '50', '2018-04-14 10:39:39', '2018-04-15 15:13:58');
INSERT INTO `sys_menu` VALUES ('8', '7', '用户', '/user', 'user', '1', null, '99', '2018-04-14 10:40:15', null);
INSERT INTO `sys_menu` VALUES ('11', '0', '订单管理', null, null, '0', '&#xe60a;', '40', '2018-04-18 22:42:02', null);
INSERT INTO `sys_menu` VALUES ('12', '11', '销售单', '/order', 'order', '1', null, '50', '2018-04-18 22:42:42', '2018-05-31 19:25:15');
INSERT INTO `sys_menu` VALUES ('13', '11', '进货单', '/purchaseBase', 'purchaseBase', '1', null, '20', '2018-05-31 19:25:52', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_sign` varchar(64) DEFAULT NULL COMMENT '角色标识',
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `user_id_create` int(11) DEFAULT NULL COMMENT '创建用户id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'superUser', '超级管理员', '1', '2018-04-13 18:50:57');
INSERT INTO `sys_role` VALUES ('2', '管理员', 'manager', '管理员', '1', '2018-04-23 20:35:22');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `status` int(4) NOT NULL DEFAULT '0' COMMENT '0无权限 1有权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '3', '1');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '6', '1');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '7', '1');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '8', '1');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '11', '1');
INSERT INTO `sys_role_menu` VALUES ('12', '1', '12', '1');
INSERT INTO `sys_role_menu` VALUES ('13', '2', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('14', '2', '2', '0');
INSERT INTO `sys_role_menu` VALUES ('15', '2', '3', '0');
INSERT INTO `sys_role_menu` VALUES ('16', '2', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('17', '2', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('18', '2', '6', '0');
INSERT INTO `sys_role_menu` VALUES ('19', '2', '7', '1');
INSERT INTO `sys_role_menu` VALUES ('20', '2', '8', '1');
INSERT INTO `sys_role_menu` VALUES ('23', '2', '11', '1');
INSERT INTO `sys_role_menu` VALUES ('24', '2', '12', '1');
INSERT INTO `sys_role_menu` VALUES ('25', '1', '13', '1');
INSERT INTO `sys_role_menu` VALUES ('26', '2', '13', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` int(11) DEFAULT '1' COMMENT '0.失效  1.正常',
  `user_desc` varchar(64) DEFAULT NULL COMMENT '用户描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` int(11) NOT NULL COMMENT '创建用户的id',
  `login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '上次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'xuchen', 'e698a95d5f57f7017e198d0b4868340d', '1', '管理员', '2018-04-13 18:47:49', '0', '2018-06-02 23:35:03');
INSERT INTO `sys_user` VALUES ('3', 'test', '72e1242b855fb038212135e0ad348842', '1', '测试用户', '2018-05-06 14:36:40', '1', '2018-06-03 01:15:42');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '3', '1');

-- ----------------------------
-- Table structure for type_enum
-- ----------------------------
DROP TABLE IF EXISTS `type_enum`;
CREATE TABLE `type_enum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` smallint(4) NOT NULL COMMENT '1.商品类型',
  `type_id` smallint(4) NOT NULL COMMENT '枚举id',
  `type_name` varchar(16) NOT NULL COMMENT '枚举值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of type_enum
-- ----------------------------
INSERT INTO `type_enum` VALUES ('1', '1', '0', '粉类');
INSERT INTO `type_enum` VALUES ('2', '1', '1', '浆类');
INSERT INTO `type_enum` VALUES ('3', '1', '2', '乳液类');
INSERT INTO `type_enum` VALUES ('4', '1', '3', '沙类');
INSERT INTO `type_enum` VALUES ('5', '1', '4', '器具类');
INSERT INTO `type_enum` VALUES ('6', '1', '5', '其他类别');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '姓名',
  `user_img` varchar(255) DEFAULT NULL,
  `user_type` smallint(4) NOT NULL COMMENT '0.自家 1.门店 2.涂料工 3.工厂 4.散客 5.供应商 6.配送者 7.其他',
  `shop_name` varchar(32) DEFAULT NULL COMMENT '门店名',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `address_two` varchar(128) DEFAULT NULL COMMENT '地址2',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `phone_two` varchar(16) DEFAULT NULL COMMENT '电话2',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '0.失效 1.有效',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `userName` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '周玉', null, '0', '1 ', '万有商业城', '', 'a ', '', '1', '', '1', '2038-01-01 00:00:01', 'xuchen', '2018-05-23 17:08:06');
INSERT INTO `user` VALUES ('2', '孙显', 'http://img.zhenyang.work/userTest/2.jpg', '1', '随便', '灌云某处', null, '112234456', '', '0', '', '1', '2018-04-23 20:41:35', 'xuchen', '2018-05-20 11:32:05');
INSERT INTO `user` VALUES ('3', '供应商A', null, '6', '临沂', '临沂某处', null, '11', '', '0', '', '1', '2018-04-23 20:42:41', 'xuchen', '2018-05-20 11:32:07');
INSERT INTO `user` VALUES ('4', '门店用户', 'http://img.zhenyang.work/userTest/4.jpg', '6', '门店A', 'address', null, '123', '', '0', '', '1', '2018-04-26 09:56:48', 'xuchen', '2018-05-14 16:42:27');
INSERT INTO `user` VALUES ('5', '11', null, '2', '11', '1', null, '11', '', '1', '', '1', '2018-04-29 12:27:09', 'xuchen', '2018-05-08 22:17:28');
INSERT INTO `user` VALUES ('6', '测试', null, '0', '1', '2', null, '3', '', '1', '4', 'xuchen', '2018-05-07 20:00:14', 'xuchen', '2018-05-08 22:17:43');
INSERT INTO `user` VALUES ('7', '2', 'http://img.zhenyang.work/userTest/7.jpg', '1', '2', '3', null, '4', '', '0', '5', 'xuchen', '2018-05-07 20:02:37', 'xuchen', '2018-05-20 11:32:09');
INSERT INTO `user` VALUES ('8', '112', 'http://img.zhenyang.work/userTest/8.jpg', '0', '3', '2', null, '3', '', '1', '1', 'xuchen', '2018-05-07 20:10:41', 'xuchen', '2018-05-14 16:42:27');
INSERT INTO `user` VALUES ('9', '4', 'http://img.zhenyang.work/userTest/9.jpg', '0', '1', '2', null, '3', '', '1', '44', 'xuchen', '2018-05-07 20:17:49', 'xuchen', '2018-05-14 16:42:54');
INSERT INTO `user` VALUES ('10', '111', 'http://img.zhenyang.work/userTest/10.jpg', '0', '22', '33', null, '44', '', '1', '5', 'xuchen', '2018-05-07 20:33:49', 'xuchen', '2018-05-14 16:42:54');
INSERT INTO `user` VALUES ('11', '332', null, '0', '121', '33', null, '22', '', '1', '11', 'xuchen', '2018-05-07 20:34:35', 'xuchen', '2018-05-08 21:21:19');
INSERT INTO `user` VALUES ('12', '4123', null, '0', '1', '2', '', '3', '', '1', '', 'xuchen', '2018-05-08 20:00:24', 'xuchen', '2018-05-08 21:21:07');
INSERT INTO `user` VALUES ('13', '12', null, '0', '1', '2', '2', '1', '', '1', '', 'xuchen', '2018-05-09 15:08:28', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('15', 'asda', null, '0', '13', '123', '', '123', '', '1', '', 'xuchen', '2018-05-09 15:08:41', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('16', 'z', null, '0', 'z', 'z', '', '11', '', '1', '', 'xuchen', '2018-05-09 15:09:34', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('17', 'aa', null, '0', 'z', 'z', '', '1', '', '1', '', 'xuchen', '2018-05-09 15:09:45', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('19', '111122', null, '1', null, null, null, null, null, '1', null, '1', '2018-05-12 17:11:48', '1', '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('20', '供应A', null, '5', 'qwer', 'qwer', 'qwer', '1', '', '1', '', 'xuchen', '2018-05-31 19:26:49', null, '0000-00-00 00:00:00');

-- ----------------------------
-- Procedure structure for update_month
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_month`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `update_month`()
BEGIN

	CALL update_month_goods_sale();
	CALL update_month_user_cost();
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for update_month_goods_sale
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_month_goods_sale`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `update_month_goods_sale`()
BEGIN
	-- 按月更新所有goods销售数据
	/*DECLARE thisMonthFirstDay VARCHAR(20);
	SET thisMonthFirstDay = DATE_ADD(curdate(),interval -day(curdate())+1 day);*/

	TRUNCATE TABLE statistics_goods_month;

	INSERT INTO statistics_goods_month(month_time,goods_id,sale_count,sale_money)
	SELECT month_time,goods_id,SUM(goods_count),SUM(goods_total_sale) FROM(
		SELECT o.goods_id,SUM(o.goods_count) AS goods_count,SUM(o.goods_count)*o.goods_sale_money AS goods_total_sale,DATE_FORMAT(b.order_time, '%Y%m') AS month_time
			FROM order_base b
			INNER JOIN order_goods o ON b.order_id = o.order_id
		GROUP BY DATE_FORMAT(b.order_time, '%Y%m'),o.goods_id,o.goods_sale_money) temp
	GROUP BY month_time,goods_id;

	UPDATE statistics_goods_month s
	INNER JOIN goods g ON s.goods_id = g.goods_id
	SET s.original_money = s.sale_count*g.original_price,
			s.gain_money = s.sale_money - s.original_money;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for update_month_user_cost
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_month_user_cost`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `update_month_user_cost`()
BEGIN
	/*按月更新用户当月所有消费额、成本和利润
			ps:只依据商品和商品当前价格，和账单无关*/

	TRUNCATE TABLE statistics_user_month;

	INSERT INTO statistics_user_month(month_time,user_id,total_cost,total_original)
		SELECT month_time,customer_id,SUM(cost),SUM(original)
			FROM(SELECT b.customer_id,o.goods_count,o.goods_count*o.goods_sale_money AS cost,
			o.goods_count*g.original_price AS original,DATE_FORMAT(b.order_time, '%Y%m') AS month_time
			FROM order_base b
			INNER JOIN order_goods o ON b.order_id = o.order_id
			INNER JOIN goods g ON o.goods_id = g.goods_id) temp GROUP BY month_time,customer_id;

	UPDATE statistics_user_month SET total_gain = total_cost - total_original;

END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for USP_update_goods_month
-- ----------------------------
DROP EVENT IF EXISTS `USP_update_goods_month`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `USP_update_goods_month` ON SCHEDULE EVERY 1 WEEK STARTS '2018-05-28 00:01:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL update_month()
;;
DELIMITER ;

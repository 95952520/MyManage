/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : boot_manage

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-23 20:48:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for debt
-- ----------------------------
DROP TABLE IF EXISTS `debt`;
CREATE TABLE `debt` (
  `debt_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '客户',
  `order_id` int(11) DEFAULT NULL COMMENT '对应订单id',
  `debt_type` smallint(4) NOT NULL DEFAULT '0' COMMENT '0.未结 1.结部分 2.已结',
  `debt_money` decimal(11,0) NOT NULL DEFAULT '0' COMMENT '欠款金额',
  `pay_money` decimal(11,0) NOT NULL DEFAULT '0' COMMENT '已付款',
  `remark` varchar(256) DEFAULT NULL,
  `debt_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '欠款时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` int(4) DEFAULT NULL,
  `is_delete` smallint(4) DEFAULT NULL,
  PRIMARY KEY (`debt_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of debt
-- ----------------------------

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(64) NOT NULL COMMENT '商品名',
  `goods_type` smallint(4) NOT NULL COMMENT '0.粉类 1.浆类 2.乳液类 3.沙子 4.小器具 5.其他',
  `is_product` smallint(4) NOT NULL DEFAULT '0' COMMENT '0.转卖 1.自己生产',
  `stock_count` int(11) NOT NULL DEFAULT '0' COMMENT '库存量',
  `unit_type` smallint(6) NOT NULL COMMENT '0.袋 1.桶 2.瓶 3.个 4.公斤',
  `weight` decimal(11,2) DEFAULT NULL COMMENT '每份的重量',
  `weight_type` smallint(4) DEFAULT NULL COMMENT '0.克 1.千克 2.吨',
  `original_price` decimal(11,2) NOT NULL COMMENT '进价/成本价',
  `freight` decimal(11,2) DEFAULT '0.00' COMMENT '运费',
  `wholesale_price` decimal(11,2) DEFAULT NULL COMMENT '批发价',
  `retail_price` decimal(11,2) DEFAULT NULL COMMENT '零售价',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '腻子粉', '0', '1', '99', '0', '10.00', '1', '8.00', '0.50', '10.00', '12.00');

-- ----------------------------
-- Table structure for order_base
-- ----------------------------
DROP TABLE IF EXISTS `order_base`;
CREATE TABLE `order_base` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL COMMENT '顾客id',
  `order_type` smallint(4) NOT NULL COMMENT '0.未配送 1.配送中 2.配送完成',
  `delivery_type` smallint(4) DEFAULT NULL COMMENT '0.上门取货 1.自己配送 2.找人配送',
  `delivery_address` varchar(128) DEFAULT NULL COMMENT '配送地址',
  `delivery_user_id` int(11) DEFAULT NULL COMMENT '配送者',
  `delivery_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '配送费',
  `pay_type` smallint(4) NOT NULL COMMENT '0.未付款 1.付部分 2.已付款',
  `pay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已付款金额',
  `unpay_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未付款金额',
  `total_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '各个商品总价',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_base
-- ----------------------------

-- ----------------------------
-- Table structure for order_goods
-- ----------------------------
DROP TABLE IF EXISTS `order_goods`;
CREATE TABLE `order_goods` (
  `order_goods_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `goods_count` int(11) NOT NULL COMMENT '商品数量',
  `goods_sale_money` decimal(11,4) NOT NULL COMMENT '出售单价',
  `create_user` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_goods_id`),
  UNIQUE KEY `goodsAndOrder` (`order_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_goods
-- ----------------------------

-- ----------------------------
-- Table structure for return_base
-- ----------------------------
DROP TABLE IF EXISTS `return_base`;
CREATE TABLE `return_base` (
  `return_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL COMMENT '顾客id',
  `return_address` varchar(128) DEFAULT NULL COMMENT '取回地址',
  `total_price` decimal(11,2) NOT NULL COMMENT '退回价格',
  `order_type` smallint(4) NOT NULL COMMENT '0.未取货 1.取货中 2.取货完成',
  `delivery_type` smallint(4) DEFAULT NULL COMMENT '0.上门取退货 1.自己送到厂 2.其他人送',
  `return_user_id` int(11) DEFAULT NULL COMMENT '取货者',
  `delivery_price` decimal(11,2) DEFAULT NULL COMMENT '取货费',
  `pay_type` smallint(4) NOT NULL COMMENT '0.未付款 1.付部分 2.已付款',
  `pay_money` decimal(11,2) DEFAULT NULL COMMENT '已退回金额',
  `unpay_money` decimal(11,2) DEFAULT NULL COMMENT '未退回金额',
  `create_user` int(11) NOT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` smallint(4) NOT NULL DEFAULT '0',
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
  `goods_discount` decimal(2,2) DEFAULT NULL COMMENT '商品几层新',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`return_goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of return_goods
-- ----------------------------

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `stock_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存表',
  `stock_name` varchar(64) NOT NULL,
  `stock_type` smallint(4) NOT NULL COMMENT '0.粉类 1.浆类 2.乳液类 3.沙子 4.小器具 5.其他',
  `unit_type` smallint(4) NOT NULL COMMENT '0.袋 1.桶 2.瓶 3.个 4.公斤',
  `weight` decimal(11,2) DEFAULT NULL COMMENT '每份的重量',
  `weight_type` smallint(4) DEFAULT NULL COMMENT '0.克 1.千克 2.吨',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存量',
  `supplier` int(11) DEFAULT NULL COMMENT '供应商',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(32) NOT NULL,
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1', '钛白粉', '0', '0', '25.00', '1', '50', '3', '2018-04-23 20:43:06', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '', '', '0', '&#xe614;', '99', '2018-03-30 11:24:01', '2018-04-14 11:03:42');
INSERT INTO `sys_menu` VALUES ('2', '1', '用户管理', '/sysUser', 'sysUser', '1', null, '10', '2018-03-30 11:24:28', '2018-04-10 17:07:33');
INSERT INTO `sys_menu` VALUES ('3', '1', '菜单管理', '/sysMenu', 'sysMenu', '1', null, '99', '2018-03-30 11:24:28', '2018-04-10 17:07:38');
INSERT INTO `sys_menu` VALUES ('4', '0', '商品数据', null, null, '0', '&#xe698;', '8', '2018-03-30 11:25:26', '2018-03-30 11:25:35');
INSERT INTO `sys_menu` VALUES ('5', '4', '商品', '/goods', 'goods', '1', '', '1', '2018-03-30 11:25:58', '2018-04-18 23:01:42');
INSERT INTO `sys_menu` VALUES ('6', '1', '菜单权限', '/sysRole', null, '1', null, '5', '2018-04-03 10:03:28', '2018-04-08 15:40:55');
INSERT INTO `sys_menu` VALUES ('7', '0', '用户数据', null, null, '0', '&#xe612;', '50', '2018-04-14 10:39:39', '2018-04-15 15:13:58');
INSERT INTO `sys_menu` VALUES ('8', '7', '用户', '/user', 'user', '1', null, '99', '2018-04-14 10:40:15', null);
INSERT INTO `sys_menu` VALUES ('9', '0', '原料相关', null, null, '0', '&#xe61d;', '30', '2018-04-15 15:13:52', '2018-04-18 21:15:17');
INSERT INTO `sys_menu` VALUES ('10', '9', '原料库存', '/stock', 'stock', '1', null, '20', '2018-04-15 15:14:29', '2018-04-18 23:01:18');
INSERT INTO `sys_menu` VALUES ('11', '0', '订单管理', null, null, '0', '&#xe60a;', '40', '2018-04-18 22:42:02', null);
INSERT INTO `sys_menu` VALUES ('12', '11', '订单', '/order', 'order', '1', null, '50', '2018-04-18 22:42:42', null);

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
INSERT INTO `sys_role_menu` VALUES ('9', '1', '9', '1');
INSERT INTO `sys_role_menu` VALUES ('10', '1', '10', '1');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '11', '1');
INSERT INTO `sys_role_menu` VALUES ('12', '1', '12', '1');
INSERT INTO `sys_role_menu` VALUES ('13', '2', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('14', '2', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('15', '2', '3', '0');
INSERT INTO `sys_role_menu` VALUES ('16', '2', '4', '1');
INSERT INTO `sys_role_menu` VALUES ('17', '2', '5', '1');
INSERT INTO `sys_role_menu` VALUES ('18', '2', '6', '0');
INSERT INTO `sys_role_menu` VALUES ('19', '2', '7', '1');
INSERT INTO `sys_role_menu` VALUES ('20', '2', '8', '1');
INSERT INTO `sys_role_menu` VALUES ('21', '2', '9', '1');
INSERT INTO `sys_role_menu` VALUES ('22', '2', '10', '1');
INSERT INTO `sys_role_menu` VALUES ('23', '2', '11', '1');
INSERT INTO `sys_role_menu` VALUES ('24', '2', '12', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '3a3c6ca24f6e565c26100b01b5c3fc91', '1', '管理员', '2018-04-13 18:47:49', '0', '2018-04-23 20:33:48');
INSERT INTO `sys_user` VALUES ('2', 'tongjun', 'fd9eb39b0e9d3700837fc71b8c5c70b1', '1', '同军', '2018-04-23 20:37:09', '1', '0000-00-00 00:00:00');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '姓名',
  `user_type` smallint(4) NOT NULL COMMENT '0.自家 1.门店 2.涂料工 3.工厂 4.散客 5.供应商 6.配送者 7.其他',
  `shop_name` varchar(32) DEFAULT NULL COMMENT '门店名',
  `address` varchar(128) DEFAULT NULL COMMENT '地址',
  `address_two` varchar(128) DEFAULT NULL COMMENT '地址2',
  `address_three` varchar(128) DEFAULT NULL COMMENT '地址3',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `phone_two` varchar(16) DEFAULT NULL COMMENT '电话2',
  `phone_three` varchar(16) DEFAULT NULL COMMENT '电话3',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(32) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_user` varchar(32) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `userName` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '周玉', '0', '大江涂料厂', '万有商业城', '墙拐国', '', '13338991239', '', '', '', '1', '2018-04-23 20:40:26', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('2', '孙显', '1', '随便', '灌云某处', '', '', '112234456', '', '', '', '1', '2018-04-23 20:41:35', null, '0000-00-00 00:00:00');
INSERT INTO `user` VALUES ('3', '供应商A', '5', '临沂', '临沂某处', '', '', '987654321', '', '', '', '1', '2018-04-23 20:42:41', null, '0000-00-00 00:00:00');

/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : aspen

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 18/08/2020 00:09:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menuId` bigint(20) NOT NULL AUTO_INCREMENT,
  `menuName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parentId` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级菜单id',
  `sort` int(5) NOT NULL DEFAULT 999 COMMENT '菜单排序',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '菜单图标',
  `hidden` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否显示  0-不显示  1-显示',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件地址 /user',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径 例:(@/views/system/user/center)/(Layout)',
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`menuId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '系统中心', 0, 1, 'system', 1, '/system', 'Layout', 1594565423, 1594565423, 0);
INSERT INTO `menu` VALUES (2, '用户管理', 1, 2, 'user', 1, 'users', 'system/user/index', 1594565423, 1594565423, 0);
INSERT INTO `menu` VALUES (3, '菜单管理', 1, 3, 'caidan', 1, 'menus', 'system/menu/index', 1594565423, 1594565423, 0);
INSERT INTO `menu` VALUES (4, '角色管理', 1, 4, 'role', 1, 'roles', 'system/role/index', 1594565423, 1594565423, 0);
INSERT INTO `menu` VALUES (5, '商品中心', 0, 5, 'item', 1, '/item', 'Layout', 1584867546, 1597679143, 0);
INSERT INTO `menu` VALUES (6, '商品列表', 5, 6, 'caidan', 1, 'list', 'item/list/index', 1584867546, 1597676158, 0);
INSERT INTO `menu` VALUES (8, '测试1', 5, 999, 'fabu', 1, '345345', '435345', 1597580882, 1597679476, 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `roleCode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色code',
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系统管理员', 'admin', 1584867546, 1584867546, 0);
INSERT INTO `role` VALUES (2, '商品管理员', 'item', 1596879534, 1597566776, 0);

-- ----------------------------
-- Table structure for rolemenu
-- ----------------------------
DROP TABLE IF EXISTS `rolemenu`;
CREATE TABLE `rolemenu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `menuId` bigint(20) NOT NULL COMMENT '菜单id',
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uqe_roleId_menuId`(`roleId`, `menuId`) USING BTREE,
  INDEX `idx_roleId`(`roleId`) USING BTREE,
  INDEX `idx_menuId`(`menuId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rolemenu
-- ----------------------------
INSERT INTO `rolemenu` VALUES (1, 1, 1, 1584867546, 1584867546, 0);
INSERT INTO `rolemenu` VALUES (2, 1, 2, 1594565423, 1594565423, 0);
INSERT INTO `rolemenu` VALUES (3, 1, 3, 1584867546, 1584867546, 0);
INSERT INTO `rolemenu` VALUES (4, 1, 4, 1584867546, 1584867546, 0);
INSERT INTO `rolemenu` VALUES (42, 2, 4, 1597564917, 1597564917, 0);
INSERT INTO `rolemenu` VALUES (44, 2, 1, 1597565601, 1597565601, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `usernick` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户头像',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否有效(0:有效1:无效)',
  `created` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `updated` bigint(20) NOT NULL DEFAULT 0 COMMENT '最后一次修改时间',
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `unq_uname`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'ygz', 'Aspen', '123456', '', '18829017849', 0, 1584867546, 1597479728);
INSERT INTO `user` VALUES (8, '123456', '123456', '123456', '1', '12345678', 0, 1596863583, 1597505393);

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL COMMENT '角色id',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `created` bigint(20) NOT NULL,
  `updated` bigint(20) NOT NULL,
  `isDeleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uqe_userId_roleId`(`userId`, `roleId`) USING BTREE,
  INDEX `idx_roleId`(`roleId`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES (1, 1, 1, 1584867546, 1597478835, 0);
INSERT INTO `userrole` VALUES (21, 2, 8, 1597505393, 1597505393, 0);

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : atm

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 18/11/2020 17:02:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '123');

-- ----------------------------
-- Table structure for detail
-- ----------------------------
DROP TABLE IF EXISTS `detail`;
CREATE TABLE `detail`  (
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of detail
-- ----------------------------
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:44:28', '123456?123??80?', '??');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:46:23', '123456向123转账10元', '转账');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:51:23', '存款100元', '存款');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:51:31', '存款200元', '存款');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:51:40', '123456向123转账100元', '转账');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 15:55:25', '存款100元', '存款');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 16:18:21', '存款3元', '存款');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 16:18:36', '存款5元', '存款');
INSERT INTO `detail` VALUES ('123456', '2020-11-18 16:54:55', '存款1元', '存款');

-- ----------------------------
-- Table structure for init
-- ----------------------------
DROP TABLE IF EXISTS `init`;
CREATE TABLE `init`  (
  `money` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of init
-- ----------------------------
INSERT INTO `init` VALUES (101001);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` int(11) NULL DEFAULT NULL,
  `id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('123456', '123456', 107, 1);
INSERT INTO `user` VALUES ('123', '123', 210, 2);

SET FOREIGN_KEY_CHECKS = 1;

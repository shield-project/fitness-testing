/*
 Navicat Premium Data Transfer

 Source Server         : xinerzi
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : 119.27.167.18:3306
 Source Schema         : fitness-testing

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 17/08/2018 10:22:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for testing_question
-- ----------------------------
DROP TABLE IF EXISTS `testing_question`;
CREATE TABLE `testing_question` (
  `question`    varchar(100) CHARACTER SET utf8
  COLLATE utf8_general_ci    NOT NULL,
  `id`          int(11)      NOT NULL AUTO_INCREMENT,
  `somato_type` varchar(20) CHARACTER SET utf8
  COLLATE utf8_general_ci    NOT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 68
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

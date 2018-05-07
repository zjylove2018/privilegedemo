/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : privilegedemo

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-28 19:21:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `itcast_user`
-- ----------------------------
DROP TABLE IF EXISTS `itcast_user`;
CREATE TABLE `itcast_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `loginName` varchar(32) default NULL,
  `name` varchar(32) default NULL,
  `password` varchar(32) default NULL,
  `email` varchar(64) default NULL,
  `phoneNumber` varchar(16) default NULL,
  `description` varchar(255) default NULL,
  `gender` int(11) default NULL,
  `departmentId` bigint(20) default NULL,
  `savePath` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK4ADEC00F49BC32E` (`departmentId`),
  CONSTRAINT `FK4ADEC00F49BC32E` FOREIGN KEY (`departmentId`) REFERENCES `itcast_department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of itcast_user
-- ----------------------------
INSERT INTO `itcast_user` VALUES ('5', 'admin', 'Admin', '81dc9bdb52d04dc20036dbd8313ed055', null, null, null, null, null, '/2014/05/15/121d9154-1fb3-4a21-abb3-18af0a50663f.jpg');

-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: dmcs
-- ------------------------------------------------------
-- Server version	5.7.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `device`
--
--


DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `devimage` varchar(255) DEFAULT NULL COMMENT '设备图片',
  `devid` varchar(45) DEFAULT NULL COMMENT '设备编号',
  `name` varchar(45) DEFAULT NULL COMMENT '设备名称',
  `type` varchar(45) DEFAULT NULL COMMENT '设备类型',
  `parameters` varchar(45) DEFAULT NULL COMMENT '主要参数',
  `vendor` varchar(45) DEFAULT NULL COMMENT '产商',
  `guranteeFrom` datetime DEFAULT NULL COMMENT '保修起始日期',
  `owner` bigint(20) DEFAULT NULL COMMENT '所有者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_parameter`
--

DROP TABLE IF EXISTS `device_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_parameter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deviceId` bigint(20) DEFAULT NULL COMMENT '设备id',
  `name` varchar(45) DEFAULT NULL COMMENT '设备参数名称',
  `description` varchar(45) DEFAULT NULL COMMENT '参数描述',
  `type` int(11) DEFAULT NULL COMMENT '参数类型',
  `value` bigint(20) DEFAULT NULL COMMENT '参数值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组id',
  `name` varchar(45) NOT NULL COMMENT '组名',
  `description` varchar(45) DEFAULT NULL COMMENT '组描述',
  `owner` bigint(20) NOT NULL COMMENT '组所有者',
  `type` int(11) NOT NULL COMMENT '组类型',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_device_mapping`
--

DROP TABLE IF EXISTS `group_device_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_device_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `groupId` bigint(20) NOT NULL COMMENT '组id',
  `deviceId` bigint(20) NOT NULL COMMENT '设备id',
  `type` int(11) NOT NULL COMMENT '关联类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_user_mapping`
--

DROP TABLE IF EXISTS `group_user_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_user_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主建',
  `groupId` bigint(20) NOT NULL COMMENT '组id',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `type` int(11) NOT NULL COMMENT '关联类型',
  `isAdmin` tinyint(4) NOT NULL COMMENT '是否组管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL COMMENT '描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trace`
--

DROP TABLE IF EXISTS `trace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'traceid',
  `userId` bigint(20) DEFAULT NULL COMMENT '操作用户名',
  `ip` varchar(45) DEFAULT NULL COMMENT '操作地址',
  `method` varchar(45) DEFAULT NULL COMMENT '操作方法',
  `parameter` varchar(45) DEFAULT NULL COMMENT '操作参数',
  `description` varchar(45) DEFAULT NULL COMMENT '操作描述',
  `userName` varchar(45) DEFAULT NULL COMMENT '用户名',
  `createTime` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` varchar(45) NOT NULL COMMENT '用户系统号',
  `username` varchar(45) NOT NULL COMMENT '登陆用户名',
  `currentAuthority` varchar(45) NOT NULL COMMENT '用户身份',
  `password` varchar(45) NOT NULL COMMENT '密码',
  `usersex` varchar(45) NOT NULL COMMENT '性别',
  `realname` varchar(45) DEFAULT NULL COMMENT '真实姓名',
  `alias` varchar(45) DEFAULT NULL,
  `userIdnumber` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '个人照片',
  `userEmail` varchar(45) NOT NULL COMMENT '电邮',
  `userEmail_1` varchar(45) DEFAULT NULL COMMENT'备用邮箱',
  `userTelephone` varchar(45) NOT NULL COMMENT '手机',
  `userTelephone_1` varchar(255) DEFAULT NULL COMMENT '备用手机',
  `userworkPlace` varchar(255) DEFAULT NULL COMMENT '地址',
  `userWeixin` varchar(45) DEFAULT NULL COMMENT '微信',
  `userQq` varchar(45) DEFAULT NULL COMMENT 'Qq',
  `regtime` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `techdocument`
--
DROP TABLE IF EXISTS  `tech_document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tech_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(45) NOT NULL COMMENT '标题',
  `image_address` varchar(255) NOT NULL COMMENT '图片地址',
  `description`  varchar(45) NOT NULL COMMENT '文件描述',
  `document_address` varchar(255) NOT NULL  COMMENT '文件存储地址',
  `identityNumber` int NOT NULL COMMENT '识别号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `image`
--
DROP TABLE IF EXISTS  `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `image`(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `image_address`  varchar(255) NOT NULL COMMENT '图片地址',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table admin_group_zero
--
DROP TABLE IF EXISTS `admin_group_zero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_group_zero`(
  `userid` varchar(45) NOT NULL COMMENT '用户系统号/用户系统号',
  `authorityNumber` int NOT NULL COMMENT '用户权限',
  PRIMARY KEY (`userid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `user_device_dashboard`
--
DROP TABLE IF EXISTS `user_device_dashboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_device_dashboard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `deviceId` bigint(20) NOT NULL COMMENT '设备id',
  `userid` varchar(45) NOT NULL COMMENT '用户id',
  `type` varchar(45) NOT NULL COMMENT '控件类型',
  `position_x` int(11) DEFAULT NULL COMMENT '显示x位置',
  `position_y` int(11) DEFAULT NULL COMMENT '显示y坐标位置',
  `range` int(11) DEFAULT NULL COMMENT '量程',
  `measurement` varchar(45) DEFAULT NULL COMMENT '单位',
  `node` varchar(45) DEFAULT NULL COMMENT '节点',
  `width` int(11) DEFAULT NULL COMMENT 'widget的宽',
  `heighth` int(11) DEFAULT NULL COMMENT 'widget的高',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role_mapping`
--

DROP TABLE IF EXISTS `user_role_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户id',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*******************************************************/
--
-- Table structure for table `avatar_history`
--
DROP TABLE IF EXISTS `avatar_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
Create Table `avatar_history` (
  `avatarId` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45) DEFAULT NULL COMMENT '用户号',
  `avatar` VARCHAR (255) NOT NULL COMMENT '图片地址',
  `selectFlag` VARCHAR(25) NOT NULL COMMENT 'false/true',
  PRIMARY KEY(`avatarId`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `user_information`
--
DROP TABLE IF EXISTS `user_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_information` (
  `userId` VARCHAR(45) NOT NULL COMMENT '主键',
  `currentAuthority` VARCHAR(45) NOT NULL COMMENT '身份',
  `avatar` VARCHAR(255) NOT NULL COMMENT '头像',
  `userName` VARCHAR(45) NOT NULL COMMENT '用户名',
  `realName` VARCHAR(45) DEFAULT NULL COMMENT '真实姓名',
  `passWord` VARCHAR(45) NOT NULL COMMENT '密码',
  `userSex` VARCHAR(45) DEFAULT NULL COMMENT '性别',
  `userIdNumber` VARCHAR(45) DEFAULT NULL COMMENT '身份证号',
  `userEmail` VARCHAR(45) NOT NULL COMMENT '邮箱',
  `emailCheckedFlag` VARCHAR(25) NOT NULL COMMENT '是否注册',
  `userWorkPlace` VARCHAR(45) DEFAULT NULL COMMENT '工作地点',
  `userTelephone` VARCHAR(25) NOT NULL COMMENT '电话',
  `userTelephone_1` VARCHAR(25) DEFAULT NULL COMMENT '备用电话',
  `userWeixin` VARCHAR(45) DEFAULT NULL COMMENT '微信',
  `userQq` VARCHAR(25) DEFAULT NULL COMMENT 'QQ',
  `regtime` Datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY(`userId`),
  UNIQUE KEY(`userName`),
  UNIQUE KEY(`userEmail`),
  UNIQUE KEY(`userTelephone`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_address`
--
DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_address` (/**用户地址信息**/
  `addressId` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `userId` VARCHAR(45)  DEFAULT NULL COMMENT '用户号',
  `name` VARCHAR (45) DEFAULT NULL COMMENT '收件人名字',
  `title` VARCHAR (45) DEFAULT NULL COMMENT '称呼',
  `country` VARCHAR(45) DEFAULT NULL COMMENT '国家',
  `proviance` VARCHAR(45) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(45) DEFAULT NULL COMMENT '城市',
  `area` VARCHAR(45) DEFAULT NULL COMMENT '地区',
  `place` VARCHAR(45) DEFAULT NULL COMMENT '地点',
  `mobilePhone` VARCHAR(25) DEFAULT NULL COMMENT '移动电话',
  `fixedPhone` VARCHAR(25) DEFAULT NULL COMMENT '固定电话',
  `email` VARCHAR(45) DEFAULT NULL COMMENT '邮件',
  PRIMARY KEY (`addressId`),
  FOREIGN KEY(`userId`) references user_information(`userId`) ON DELETE cascade
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_friend`
--
DROP TABLE IF EXISTS `user_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_friend` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userSelf` VARCHAR(45) DEFAULT NULL COMMENT '自身用户编号',
  `userFriend` VARCHAR(45) DEFAULT NULL COMMENT '朋友编号',
  PRIMARY KEY(id),
  FOREIGN KEY(`userSelf`) references user_information(`userId`) ON DELETE cascade ,
  FOREIGN KEY(`userFriend`) references user_information(`userId`) ON DELETE cascade
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login_log`
--
DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
Create TABLE `login_log`(
  `logId` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` VARCHAR(45) DEFAULT NULL COMMENT '用户编号',
  `loginIP` VARCHAR(255) DEFAULT NULL COMMENT '登录IP',
  `loginTime` Datetime DEFAULT NULL COMMENT '登录时间',
  `loginOutTime` Datetime DEFAULT NULL COMMENT '登出时间',
  `loginOutWay` VARCHAR(45) DEFAULT NULL COMMENT '登入方式',
  PRIMARY KEY(`logId`),
  FOREIGN KEY(`userId`) references user_information(`userId`) ON DELETE cascade
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/**************************************************************************/

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-23 22:07:11

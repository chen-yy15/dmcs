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
  `avatarid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(45) DEFAULT NULL COMMENT '用户号',
  `avatar` VARCHAR (255) NOT NULL COMMENT '图片地址',
  `selectflag` VARCHAR(25) NOT NULL COMMENT 'false/true',
  PRIMARY KEY(`avatarid`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `user_information`
--
DROP TABLE IF EXISTS `user_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_information` (
  `userid` VARCHAR(45) NOT NULL COMMENT '主键',
  `currentAuthority` VARCHAR(45) NOT NULL COMMENT '身份',
  `avatar` VARCHAR(255) NOT NULL COMMENT '头像',
  `username` VARCHAR(45) NOT NULL COMMENT '用户名',
  `realname` VARCHAR(45) DEFAULT NULL COMMENT '真实姓名',
  `password` VARCHAR(45) NOT NULL COMMENT '密码',
  `usersex` VARCHAR(45) DEFAULT NULL COMMENT '性别',
  `useridnumber` VARCHAR(45) DEFAULT NULL COMMENT '身份证号',
  `useremail` VARCHAR(45) NOT NULL COMMENT '邮箱',
  `emailcheckedflag` VARCHAR(25) NOT NULL COMMENT '是否注册',
  `userworkplace` VARCHAR(45) DEFAULT NULL COMMENT '工作地点',
  `usertelephone` VARCHAR(25) NOT NULL COMMENT '电话',
  `usertelephone_1` VARCHAR(25) DEFAULT NULL COMMENT '备用电话',
  `userweixin` VARCHAR(45) DEFAULT NULL COMMENT '微信',
  `userqq` VARCHAR(25) DEFAULT NULL COMMENT 'QQ',
  `regtime` Datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY(`userid`),
  UNIQUE KEY(`username`),
  UNIQUE KEY(`useremail`),
  UNIQUE KEY(`usertelephone`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_address`
--
DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_address` (/**用户地址信息**/
  `addressid` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `userid` VARCHAR(45)  DEFAULT NULL COMMENT '用户号',
  `name` VARCHAR (45) DEFAULT NULL COMMENT '收件人名字',
  `title` VARCHAR (45) DEFAULT NULL COMMENT '称呼',
  `country` VARCHAR(45) DEFAULT NULL COMMENT '国家',
  `proviance` VARCHAR(45) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(45) DEFAULT NULL COMMENT '城市',
  `area` VARCHAR(45) DEFAULT NULL COMMENT '地区',
  `place` VARCHAR(45) DEFAULT NULL COMMENT '地点',
  `mobilephone` VARCHAR(25) DEFAULT NULL COMMENT '移动电话',
  `fixedphone` VARCHAR(25) DEFAULT NULL COMMENT '固定电话',
  `email` VARCHAR(45) DEFAULT NULL COMMENT '邮件',
  PRIMARY KEY (`addressId`),
  FOREIGN KEY(`userid`) references user_information(`userid`) ON DELETE cascade
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
  `userself` VARCHAR(45) DEFAULT NULL COMMENT '自身用户编号',
  `userfriend` VARCHAR(45) DEFAULT NULL COMMENT '朋友编号',
  PRIMARY KEY(id),
  FOREIGN KEY(`userself`) references user_information(`userid`) ON DELETE cascade ,
  FOREIGN KEY(`userfriend`) references user_information(`userid`) ON DELETE cascade
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `login_log`
--
DROP TABLE IF EXISTS `login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
Create TABLE `login_log`(
  `logid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` VARCHAR(45) DEFAULT NULL COMMENT '用户编号',
  `loginip` VARCHAR(255) DEFAULT NULL COMMENT '登录IP',
  `logintime` Datetime DEFAULT NULL COMMENT '登录时间',
  `loginouttime` Datetime DEFAULT NULL COMMENT '登出时间',
  `loginoutway` VARCHAR(45) DEFAULT NULL COMMENT '登入方式',
  PRIMARY KEY(`logId`),
  FOREIGN KEY(`userid`) references user_information(`userid`) ON DELETE cascade
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table file_info
--
DROP TABLE IF EXISTS `file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_info`(
  `fileid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `filename` VARCHAR(45) COMMENT '文件名',
  `suffixname` VARCHAR(45) COMMENT '后缀名',
  `filetype` VARCHAR(25) COMMENT '文件类型',
  `filedescription` VARCHAR(255) COMMENT '文件描述内容',
  `filesrc` VARCHAR(255) COMMENT '文件目录',
  `insertTime` DATETIME COMMENT '插入时间',
  `insertUser` VARCHAR(45) NOT NULL COMMENT '插入用户名',
  PRIMARY KEY (`fileid`),
  FOREIGN KEY (`insertUser`) REFERENCES user_information(`userid`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table file_window_module
--
DROP TABLE IF EXISTS `file_window_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_window_module`(
  `createid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `moduleid` INT COMMENT '对应模块编号',
  `windowid` INT COMMENT '对应窗口',
  `orderid` INT COMMENT '排序情况',
  `filename` VARCHAR(255) DEFAULT NULL COMMENT '文件名',
  `filesrc` VARCHAR(255) DEFAULT NULL COMMENT '文件地址',
  `imagename` VARCHAR(255) DEFAULT NULL COMMENT '图片名',
  `imagesrc` VARCHAR(255) DEFAULT NULL COMMENT '图片地址',
  `fileimagedescrip` VARCHAR(255) DEFAULT NULL COMMENT '图片与文件关系描述',
  `viewed` VARCHAR(25) COMMENT '可视性判断',
  `insertUser` VARCHAR(45) NOT NULL COMMENT '插入用户名',
  PRIMARY KEY(`createid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table sysOperation_log
--
DROP TABLE IF EXISTS `sysOperation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sysOperation_log`(
  `logid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` VARCHAR(45) COMMENT '用户编号',
  `fileid` BIGINT(20) COMMENT '文件编号,其中公告采用特殊编号',
  `filefullname` VARCHAR(255) COMMENT '文件内容，公告则直接显示公告内容',
  `operationtime` DATETIME COMMENT '操作时间',
  `opDesc` VARCHAR(255) COMMENT '操作描述',
  PRIMARY KEY(`logid`),
  FOREIGN KEY(`userid`) REFERENCES user_information(`userid`) ON DELETE CASCADE
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
  `auth1` VARCHAR(20) DEFAULT 'false' COMMENT '权限1',
  `auth2` VARCHAR(20) DEFAULT 'false' COMMENT '权限2',
  `auth3` VARCHAR(20) DEFAULT 'false' COMMENT '权限3',
  `auth4` VARCHAR(20) DEFAULT 'false' COMMENT '权限4',
  `auth5` VARCHAR(20) DEFAULT 'false' COMMENT '权限5',
  `auth6` VARCHAR(20) DEFAULT 'false' COMMENT '权限6',
  `auth7` VARCHAR(20) DEFAULT 'false' COMMENT '权限7',
  `auth8` VARCHAR(20) DEFAULT 'false' COMMENT '权限8',
  `auth9` VARCHAR(20) DEFAULT 'false' COMMENT '权限9',
  `auth10` VARCHAR(20) DEFAULT 'false' COMMENT '权限10',
  PRIMARY KEY (`userid`),
  FOREIGN KEY(`userid`) references user_information(`userid`) ON DELETE cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table web_information
--
DROP TABLE IF EXISTS `web_information`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `web_information`(
  `infid` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inftxt` VARCHAR(255) COMMENT '公告内容',
  `viewed` VARCHAR(25) COMMENT '可视性设置false/true',
  `linksrc` VARCHAR(255) COMMENT '链接文章',
  `inserUser` VARCHAR(45) COMMENT '插入用户',
  `insertTime` DATETIME COMMENT '插入时间',
  `outTime` DATETIME COMMENT '过期时间',
  PRIMARY KEY(`infid`),
  FOREIGN KEY(`inserUser`) REFERENCES user_information(`userid`) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for enum_name
--
DROP TABLE IF EXISTS `enum_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enum_name`(
  `nameid` INT NOT NULL COMMENT '编号',
  `bandid` INT NOT NULL COMMENT '绑定编号',
  `moduleType` VARCHAR(25) NOT NULL COMMENT '模块类型',
  `namedetail` VARCHAR(45) COMMENT '内容详情',
  PRIMARY KEY(`nameid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for
--
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

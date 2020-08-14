/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.31 : Database - blade_taobao
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blade_taobao` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `blade_taobao`;

/*Table structure for table `blade_admin_account` */

DROP TABLE IF EXISTS `blade_admin_account`;

CREATE TABLE `blade_admin_account` (
  `id` varchar(64) NOT NULL COMMENT '暂定UUID',
  `account` varchar(254) NOT NULL COMMENT '平台帐号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台帐号表';

/*Data for the table `blade_admin_account` */

/*Table structure for table `blade_order` */

DROP TABLE IF EXISTS `blade_order`;

CREATE TABLE `blade_order` (
  `id` varchar(64) NOT NULL COMMENT '订单ID 暂定同步淘宝下拉',
  `status` int(4) NOT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

/*Data for the table `blade_order` */

/*Table structure for table `blade_rate` */

DROP TABLE IF EXISTS `blade_rate`;

CREATE TABLE `blade_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '无意义自增ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `rate` decimal(5,2) NOT NULL COMMENT '比率',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比率表';

/*Data for the table `blade_rate` */

/*Table structure for table `blade_store_user_middle` */

DROP TABLE IF EXISTS `blade_store_user_middle`;

CREATE TABLE `blade_store_user_middle` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '无意义自增Id',
  `user_id` varchar(64) NOT NULL COMMENT '工作人员Id',
  `store_id` varchar(64) NOT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺关联表';

/*Data for the table `blade_store_user_middle` */

/*Table structure for table `blade_user` */

DROP TABLE IF EXISTS `blade_user`;

CREATE TABLE `blade_user` (
  `id` varchar(64) NOT NULL COMMENT 'UUID64位对应用户基础表ID',
  `phone` varchar(32) NOT NULL COMMENT '账号对应基础信息手机号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `role` int(4) NOT NULL COMMENT '角色-管理员-1 商家 2 工作人员 3',
  `status` int(4) NOT NULL COMMENT '审核状态 1为不通过 -1 管理员 0待审 2 通过',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台用户表';

/*Data for the table `blade_user` */

/*Table structure for table `blade_user_bash` */

DROP TABLE IF EXISTS `blade_user_bash`;

CREATE TABLE `blade_user_bash` (
  `id` varchar(64) NOT NULL COMMENT 'UUID64位用户ID',
  `user_name` varchar(32) NOT NULL COMMENT '用户姓名',
  `user_sex` int(4) NOT NULL COMMENT '性别',
  `user_age` int(4) NOT NULL COMMENT '年龄',
  `education` varchar(32) NOT NULL COMMENT '学历',
  `school` varchar(64) NOT NULL COMMENT '毕业院校',
  `work_year` int(4) NOT NULL COMMENT '工作年限',
  `resume_url` varchar(254) NOT NULL COMMENT '简历URL',
  `address` varchar(254) NOT NULL COMMENT '居住地址',
  `phone` varchar(32) NOT NULL COMMENT '手机号对应登录账号',
  `alipay` varchar(64) NOT NULL COMMENT '支付宝账号用于后期提现',
  `individual_resume` varchar(602) NOT NULL COMMENT '个人简介',
  `identity_image_front` varchar(254) NOT NULL COMMENT '身份证正面',
  `identity_image_verso` varchar(254) NOT NULL COMMENT '身份证反面',
  `education_image` varchar(254) NOT NULL COMMENT '学历照片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台工作人员基础信息表';

/*Data for the table `blade_user_bash` */

/*Table structure for table `blade_user_check` */

DROP TABLE IF EXISTS `blade_user_check`;

CREATE TABLE `blade_user_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '无意义自增主键',
  `user_id` varchar(64) NOT NULL COMMENT '实际用户id',
  `check_opinion` varchar(254) NOT NULL COMMENT '审核意见',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发起时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='审核意见表';

/*Data for the table `blade_user_check` */

/*Table structure for table `blade_user_store` */

DROP TABLE IF EXISTS `blade_user_store`;

CREATE TABLE `blade_user_store` (
  `id` varchar(64) NOT NULL COMMENT 'UUID64位对应userId',
  `store_name` varchar(64) NOT NULL COMMENT '店铺名称',
  `store_human` varchar(32) NOT NULL COMMENT '店铺负责人',
  `phone` varchar(32) NOT NULL COMMENT '手机号',
  `address` varchar(64) NOT NULL COMMENT '地址',
  `longitude` varchar(32) NOT NULL COMMENT '经度',
  `latitude` varchar(32) NOT NULL COMMENT '维度',
  `q_r_code` varchar(254) NOT NULL COMMENT '二维码',
  `image` varchar(254) NOT NULL COMMENT '图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺详细表';

/*Data for the table `blade_user_store` */

/*Table structure for table `blade_wallet` */

DROP TABLE IF EXISTS `blade_wallet`;

CREATE TABLE `blade_wallet` (
  `id` varchar(64) NOT NULL COMMENT 'UUID64对应用户id',
  `money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '钱',
  `history_money_all` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '历史总钱',
  `conversion_money_all` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '提现总钱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户钱包';

/*Data for the table `blade_wallet` */

/*Table structure for table `blade_wallet_history` */

DROP TABLE IF EXISTS `blade_wallet_history`;

CREATE TABLE `blade_wallet_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增无意义ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `money_change` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '变更金额',
  `reason` int(4) NOT NULL COMMENT '什么原因改变暂定 0 分成加钱 1 提现',
  `then_money` decimal(11,2) NOT NULL COMMENT '扣完钱后多少钱',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '扣钱时间',
  `status` int(4) NOT NULL COMMENT '该钱状态是否成功之类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钱变动历史表';

/*Data for the table `blade_wallet_history` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

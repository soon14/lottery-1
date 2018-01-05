/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.20 : Database - kenwork
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kenwork` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `kenwork`;

/*Table structure for table `of_user` */

DROP TABLE IF EXISTS `of_user`;

CREATE TABLE `of_user` (
  `USER_ID` int(100) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `USERNAME` varchar(255) DEFAULT NULL COMMENT '登陆名',
  `PASSWORD` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `NAME` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `ROLE_ID` varchar(100) DEFAULT NULL COMMENT '角色编号',
  `Phone` varchar(100) DEFAULT NULL COMMENT '联系方式',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `of_user` */

insert  into `of_user`(`USER_ID`,`USERNAME`,`PASSWORD`,`NAME`,`ROLE_ID`,`Phone`,`CREATE_TIME`) values (1,'admin','admin','管理员','1','18612186708','2015-05-26 11:27:05'),(2,'ken123','123','ken','6','18612186708','2015-05-31 10:35:34');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

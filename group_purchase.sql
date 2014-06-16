-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: group_purchase
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu1

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `crypted_password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'hunter','admin','347212291@qq.com','$2a$10$eGXeO2byqd/JSFMZlS0rEuCXjas7SnAWYu/j/MgwcReRJiDPQg8O.','admin','2014-05-15 13:16:40','2014-05-15 13:16:40');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `good_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` text CHARACTER SET latin1,
  `writer` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `note` mediumtext,
  `service` mediumtext,
  `avatar` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `integration` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,1,100,'集合了所有法式的常见美味','本店提供免费Wifi，同时每购买一份培根芦笋卷就赠送一个法式小礼物','本店有着最优质的服务，最舒心的体验。这里有专业的厨师为大家烹饪各种美味','14052515012115201615877.jpg',0,100,'2014-05-16 13:25:22','2014-05-25 07:01:21',' 法式菜肴'),(2,2,100,'沙县鱼丸成品洁白如玉，软嫩如腐，极易消化。很满足学生口味','赠送饮料','本店有免费wifi','14053012513915201615879.jpg',0,100,'2014-05-30 04:51:39','2014-05-30 04:51:39','沙县鱼丸'),(3,3,100,'味道很好，韩味十足。','赠送小吃','本地有免费wifi','14053014164015201614171.jpg',0,100,'2014-05-30 06:16:40','2014-05-30 06:16:40','盖饭'),(4,4,100,'味道香辣，地道美食','赠送饮料','本地有免费wifi','14053014384615201613839.jpg',0,100,'2014-05-30 06:38:46','2014-05-30 06:38:46','水煮肉片'),(5,5,10,'相当美味且可口','买一送一','本地有wifi','14053014435215201614384.jpg',0,10,'2014-05-30 06:43:52','2014-05-30 06:43:52','蛋挞'),(6,6,20,'特别好吃，很有味道','买一送一','本店有wifi','14053014494915201616087.jpg',0,20,'2014-05-30 06:49:49','2014-05-30 06:49:49','饺子'),(7,7,50,'肉多有口感，味美多兹。。香甜舒滑','免费赠送饮料','本地有wifi，提供免费水','14053117025315201615011.jpg',0,50,'2014-05-31 09:02:53','2014-05-31 09:02:53','京酱肉丝'),(8,8,30,'面香扑鼻，口味润滑。富有弹性','本店赠送可以免费抽奖，若抽中，则可免费进食。','本店有免费wifi网络，可提供饮水','14053117140715201613834.jpg',0,30,'2014-05-31 09:14:07','2014-05-31 09:14:07','牛肉面'),(9,9,500,'特别地道的火锅料理。正宗的特色火锅。肉多味美。香辣可口','可以抽奖，有获得免费饮食的机会。','本店有wifi，免费提供饮料','14053117312315201614993.jpg',0,500,'2014-05-31 09:31:23','2014-05-31 09:31:23','陈阿婆特色火锅'),(10,10,100,'特色川菜，很有韵味。香辣正宗','赠送饮水。。','本店有免费wifi，服务周到','14053117383615201616384.jpg',0,100,'2014-05-31 09:38:36','2014-05-31 09:38:36','渝乡特色川菜'),(11,11,20,'地道风味的刀削面系列，特别美味','可参加抽奖，有机会免单','本地有免费wifi，更有免费水赠送','14060211143615201617879.jpg',0,20,'2014-06-02 03:14:36','2014-06-02 03:14:36','晋北刀削面');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schema_migrations`
--

DROP TABLE IF EXISTS `schema_migrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_migrations` (
  `version` varchar(255) NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_migrations`
--

LOCK TABLES `schema_migrations` WRITE;
/*!40000 ALTER TABLE `schema_migrations` DISABLE KEYS */;
INSERT INTO `schema_migrations` VALUES ('1'),('2'),('20140505085104'),('20140505134114'),('3'),('4'),('5'),('6'),('7'),('8'),('9');
/*!40000 ALTER TABLE `schema_migrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopowners`
--

DROP TABLE IF EXISTS `shopowners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopowners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `income` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopowners`
--

LOCK TABLES `shopowners` WRITE;
/*!40000 ALTER TABLE `shopowners` DISABLE KEYS */;
INSERT INTO `shopowners` VALUES (1,'15201615877','123456','han',NULL,'2014-05-16 06:31:29','2014-05-16 06:31:29'),(2,'15201615879','654321','han',NULL,'2014-05-16 06:32:00','2014-05-16 06:32:00'),(3,'15201615877','123456','han',0,'2014-05-16 06:52:38','2014-05-16 06:52:38'),(4,'15201615877','654321','han',0,'2014-05-16 07:08:33','2014-05-16 07:08:33'),(5,'15201615879','123456','程源',0,'2014-05-30 02:08:25','2014-05-30 02:08:25'),(6,'15201614171','123456','陈铮',0,'2014-05-30 06:13:34','2014-05-30 06:13:34'),(7,'15201614560','123456','梁其亮',0,'2014-05-30 06:17:14','2014-05-30 06:17:14'),(8,'15201613839','123456','陈飞鱼',0,'2014-05-30 06:34:47','2014-05-30 06:34:47'),(9,'15201614384','123456','陈雅胜',0,'2014-05-30 06:39:36','2014-05-30 06:39:36'),(10,'15201616087','123456','石思齐',0,'2014-05-30 06:45:50','2014-05-30 06:45:50'),(11,'15201617877','123456','刘青青',0,'2014-05-30 06:50:12','2014-05-30 06:50:12'),(12,'15201615011','123456','全君臣',0,'2014-05-31 08:57:34','2014-05-31 08:57:34'),(13,'15201613834','123456','东海',0,'2014-05-31 09:03:52','2014-05-31 09:03:52'),(14,'15201614993','123456','董海景',0,'2014-05-31 09:18:06','2014-05-31 09:18:06'),(15,'15201616384','123456','张玲玲',0,'2014-05-31 09:34:20','2014-05-31 09:34:20'),(16,'15201617879','123456','望住并',0,'2014-06-02 03:08:58','2014-06-02 03:08:58');
/*!40000 ALTER TABLE `shopowners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shops`
--

DROP TABLE IF EXISTS `shops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `shopowner_id` int(11) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  `lon` varchar(255) DEFAULT NULL,
  `shop_tel` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shops`
--

LOCK TABLES `shops` WRITE;
/*!40000 ALTER TABLE `shops` DISABLE KEYS */;
INSERT INTO `shops` VALUES (1,'金榜园','北京理工大学金榜园餐厅',1,'39.96308','116.319731','1234567890',5,'西餐','14052820135215201615877.jpg','金榜园是理工校内的一个好餐厅','2014-05-16 12:51:14','2014-05-28 12:13:52'),(2,'沙县小吃','民大西路入口处向东走100米',5,'39.962644','116.323316','1234567890',5,'小吃快餐','14053012411015201615879.jpg','这里是专门为理工学子提供餐饮的小吃，味美价廉哦！','2014-05-30 04:41:10','2014-05-30 04:41:10'),(3,'韩式快餐','民大西路入口处向南600米',6,'39.961662','116.324025','123456',5,'日韩料理','14053014151415201614171.jpg','一家很好的韩式料理店，很正宗。。','2014-05-30 06:15:14','2014-05-30 06:15:14'),(4,'成都美食','魏公村小区14号楼东部',8,'39.960743','116.325274','123456',5,'火锅','14053014373615201613839.JPG','很地道的成都味道','2014-05-30 06:37:36','2014-05-30 06:37:36'),(5,'美心小厨','人大当代商城向南100米',9,'39.971789','116.329613','123456',5,'甜点','14053014430115201614384.jpg','很有情味的甜品店','2014-05-30 06:43:01','2014-05-30 06:43:01'),(6,'红毛饺子魏公村店','民大东门对面向北100米',10,'39.959837','116.330664','123456',5,'小吃快餐','14053014484515201616087.jpg','正宗饺子，香甜可口味美价廉','2014-05-30 06:48:45','2014-05-30 06:48:45'),(7,'东北特味','北京外国语大学西门对面',12,'39.967168','116.314261','1234567',5,'小吃快餐','14053117002715201615011.jpg','正宗东北特色小吃，香甜味美。','2014-05-31 09:00:27','2014-05-31 09:00:27'),(8,'大碗拉面','三义庙公交站向东100米',13,'39.970541','116.319808','123456',5,'自助餐','14053117071315201613834.jpg','很地道的面食，有风味也有韵味','2014-05-31 09:07:13','2014-05-31 09:07:13'),(9,'陈阿婆苏州街店','北外西门北侧200米',14,'39.967092','116.314306','123242',5,'火锅','14053117253115201614993.jpg','很有历史的一家连锁火锅店，辣椒味十足且地道。','2014-05-31 09:25:31','2014-05-31 09:25:31'),(10,'俞乡川味','魏公村路西口向南100米',15,'39.962762','116.315298','123456',5,'火锅','14053117371415201616384.jpg','地道风味火锅，香辣逼人。','2014-05-31 09:37:14','2014-05-31 09:37:14'),(11,'晋北面馆','苏州街地铁口向南100米',16,'39.980535','116.313281','123456',5,'小吃快餐','14060211114515201617879.jpg','特定晋北风味的面食。','2014-06-02 03:11:45','2014-06-02 03:11:45');
/*!40000 ALTER TABLE `shops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `integration` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'15201615877','123456h','10214d60-c853-4191-b70f-b8467fb87e29','2014-05-28 11:45:16','2014-06-09 03:29:26',0),(2,'hunter','15201615877','123456','ad7a1813-b40a-4b5d-b23d-fc6279acfd4b','2014-05-28 11:48:12','2014-05-28 11:49:01',100),(3,'hunter','15201615879','123456','8ea60655-a1e7-498c-a02d-ade90e490c72','2014-06-10 03:06:21','2014-06-10 03:20:58',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-12  1:33:24

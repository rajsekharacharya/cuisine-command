CREATE DATABASE  IF NOT EXISTS `resturent` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `resturent`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: resturent
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `addons`
--

DROP TABLE IF EXISTS `addons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addons`
--

LOCK TABLES `addons` WRITE;
/*!40000 ALTER TABLE `addons` DISABLE KEYS */;
/*!40000 ALTER TABLE `addons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'admin','2025-08-09 11:38:13.325000','admin','2025-08-09 11:38:13.325000','Chicken momo is a steamed or fried dumpling filled with seasoned minced chicken, popular in Tibetan, Nepalese, and Himalayan cuisines.','uploads/Category/1ad8e3e1-a852-48a8-ae38-d98ac95b856f.jpg','Chicken Momo',_binary ''),(2,'admin','2025-08-09 11:39:14.807000','admin','2025-08-09 11:39:14.807000','Fish momo is a Himalayan-style dumpling stuffed with spiced minced fish, served steamed or fried for a flavorful seafood twist.','uploads/Category/8335d912-8cdc-44e0-821d-aa4d0a7d163c.webp','Fish Momo',_binary ''),(3,'admin','2025-08-09 11:39:56.552000','admin','2025-08-09 11:39:56.552000','Veggie momo is a soft dumpling filled with a mix of fresh, seasoned vegetables, offering a light and flavorful vegetarian delight.','uploads/Category/4f2d24c5-6ad5-4488-bdbb-ea3b69e4901d.webp','Veggie Momo',_binary ''),(4,'admin','2025-08-09 11:40:42.222000','admin','2025-08-09 11:40:42.222000','Cheese momo is a soft dumpling filled with gooey, melted cheese, delivering a creamy and savory flavor in every bite.','uploads/Category/eb39434c-f65e-4c79-8590-3c6b50620a07.webp','Cheese Momo',_binary ''),(5,'admin','2025-08-09 11:41:54.523000','admin','2025-08-09 11:41:54.523000','Crispy momo is a fried dumpling with a crunchy golden shell and a flavorful filling, offering a perfect mix of texture and taste.','uploads/Category/d4920518-1474-4bb1-a15c-05be87664723.webp','Crispy Momo',_binary ''),(6,'admin','2025-08-09 11:42:21.901000','admin','2025-08-09 11:42:21.901000','Fusion momo is a creative twist on traditional dumplings, blending diverse flavors and ingredients from different cuisines into one unique bite.','uploads/Category/ce6c0f6c-a4c7-44bd-b1f6-c66c47a25d6c.webp','Fusion Momo',_binary ''),(7,'admin','2025-08-09 11:42:45.963000','admin','2025-08-09 11:42:45.963000','মনের মোমো হলো এক ধরনের কল্পনার মোমো, যা স্বাদের পাশাপাশি ভালোবাসা ও অনুভূতির বিশেষ ছোঁয়া বহন করে।','uploads/Category/0c450eef-f320-4d8f-abbf-5063186e13cd.webp','মনের মোমো',_binary ''),(8,'admin','2025-08-09 11:43:11.294000','admin','2025-08-09 11:43:11.294000','মোমোচিত্তের মিষ্টি মোমো হলো মিষ্টি পুরে ভরা নরম মোমো, যা প্রতিটি কামড়ে এনে দেয় আনন্দ আর মিষ্টি স্মৃতির স্বাদ।','uploads/Category/37ef9681-6d46-46c7-90e2-cb558502c943.webp','মোমোচিত্তের মিষ্টি মোমো',_binary ''),(9,'admin','2025-08-09 11:43:38.639000','admin','2025-08-09 11:43:38.639000','Sizzler momo is a hot and smoky serving of dumplings, often paired with spicy sauces and veggies on a sizzling platter for an extra burst of flavor and aroma.','uploads/Category/dc67cc59-c51d-4791-b07b-ddb4fc9ffddd.webp','Sizzler Momo',_binary ''),(10,'admin','2025-08-09 11:44:37.959000','admin','2025-08-09 11:44:37.959000','Pizza cravings is the irresistible desire for a cheesy, flavorful pizza loaded with toppings and baked to perfection.','uploads/Category/3fc94b57-048a-42b0-aceb-43bb94c6cf69.jpeg','Pizza Cravings',_binary ''),(11,'admin','2025-08-09 11:45:35.890000','admin','2025-08-09 11:45:35.890000','Pasta Paradise is a delightful haven of perfectly cooked pasta tossed in rich, flavorful sauces for a truly satisfying meal.','uploads/Category/50dfee0d-c489-454f-af9e-fdf178a864a8.png','Pasta Paradise',_binary ''),(12,'admin','2025-08-09 11:46:16.540000','admin','2025-08-09 11:46:16.540000','Burger Bliss is the ultimate joy of biting into a juicy, flavorful burger layered with fresh toppings and savory sauces.','uploads/Category/e0ef24a4-f483-4eeb-8f9c-ac1b6dd1bf48.jpeg','Burger Bliss',_binary ''),(13,'admin','2025-08-09 11:47:08.857000','admin','2025-08-09 11:47:08.857000','Sandwich Sensation is the mouthwatering delight of fresh bread stacked with tasty fillings, crisp veggies, and flavorful spreads.','uploads/Category/b04d41e5-ebf5-4128-86a4-9a683242033b.jpg','Sandwich Sensation ',_binary ''),(14,'admin','2025-08-09 11:47:59.451000','admin','2025-08-09 11:47:59.451000','The Chai Canvas is a warm, aromatic cup of tea brewed to perfection, painting moments with comfort and rich flavors.','uploads/Category/606f3150-1457-41cd-b999-7e0508555439.jpg','The Chai Canvas ',_binary ''),(15,'admin','2025-08-09 11:48:49.751000','admin','2025-08-09 11:48:49.751000','The Coffee Affair is a passionate indulgence in the rich aroma and bold flavors of freshly brewed coffee.','uploads/Category/0aa3dea9-a96f-4ebd-965a-a99b709ef0ae.jpeg','The Coffee Affair',_binary ''),(16,'admin','2025-08-09 11:49:23.763000','admin','2025-08-09 11:49:23.763000','ChatGPT said:\nShakes & Smiles is the joyful blend of creamy, flavorful shakes that bring a smile with every sip.','uploads/Category/b87a6348-303f-419c-bed5-20c024379780.jpeg','Shakes & Smiles ',_binary ''),(17,'admin','2025-08-09 11:50:38.424000','admin','2025-08-09 11:50:38.424000','Divine Dessert is a heavenly treat that indulges your sweet tooth with rich flavors and irresistible textures.','uploads/Category/6e862188-97c5-4d48-a5fd-4c81fa5ec9a9.jpeg','Divine Dessert',_binary ''),(18,'admin','2025-08-09 11:51:31.454000','admin','2025-08-09 11:51:31.454000','Mocktails Mania is the vibrant celebration of colorful, flavorful, and refreshing non-alcoholic drinks for every occasion.','uploads/Category/492c1413-7ae9-445c-bbc3-15ecea5a360f.png','Mocktails Mania',_binary ''),(19,'admin','2025-08-09 11:53:25.410000','admin','2025-08-09 11:53:25.410000','Lunch Box is a wholesome combo meal packed with a variety of dishes, offering balanced flavors and nutrition in one convenient serving.','uploads/Category/6c897299-c029-423b-8781-0088cf15c4d5.jpg','Lunch Box',_binary '');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `addons` bit(1) NOT NULL,
  `combo` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dietary` enum('NON_VEG','VEG','VEGAN') DEFAULT NULL,
  `gst_type` tinyint DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `short_code` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `variation` bit(1) NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2n9w8d0dp4bsfra9dcg0046l4` (`category_id`),
  CONSTRAINT `FK2n9w8d0dp4bsfra9dcg0046l4` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `item_chk_1` CHECK ((`gst_type` between 0 and 4))
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'admin','2025-08-09 13:31:01.718000','admin','2025-08-09 13:31:01.718000',_binary '\0',_binary '\0','','NON_VEG',2,'Classic Chicken Momo',NULL,'CCM',_binary '',_binary '',1),(2,'admin','2025-08-09 13:32:03.168000','admin','2025-08-09 13:32:03.168000',_binary '\0',_binary '\0','','NON_VEG',2,'Dhania Chicken Momo',NULL,'DCM',_binary '',_binary '',1),(3,'admin','2025-08-09 13:32:52.388000','admin','2025-08-09 13:32:52.388000',_binary '\0',_binary '\0','','NON_VEG',2,'Darjeeling Chicken Momo ',NULL,'DJCM',_binary '',_binary '',1),(4,'admin','2025-08-09 13:33:35.132000','admin','2025-08-09 13:33:35.132000',_binary '\0',_binary '\0','','NON_VEG',2,'Gondhoraj Chicken Momo',NULL,'GCM',_binary '',_binary '',1),(5,'admin','2025-08-09 13:34:09.787000','admin','2025-08-09 13:34:09.787000',_binary '\0',_binary '\0','','NON_VEG',2,'Aachari Chicken Momo',NULL,'ACM',_binary '',_binary '',1),(6,'admin','2025-08-09 13:34:49.640000','admin','2025-08-09 13:34:49.640000',_binary '\0',_binary '\0','','NON_VEG',2,'Butter Garlic Chicken Momo',NULL,'BGCM',_binary '',_binary '',1),(7,'admin','2025-08-09 13:35:24.761000','admin','2025-08-09 13:35:24.761000',_binary '\0',_binary '\0','','NON_VEG',2,'Chill Garlic Chicken Momo',NULL,'CGCM',_binary '',_binary '',1),(8,'admin','2025-08-09 13:35:55.925000','admin','2025-08-09 13:35:55.925000',_binary '\0',_binary '\0','','NON_VEG',2,'Peri Peri Chicken Momo',NULL,'PPCM',_binary '',_binary '',1),(9,'admin','2025-08-09 13:36:26.454000','admin','2025-08-09 13:36:26.454000',_binary '\0',_binary '\0','','NON_VEG',2,'Schezwan Chicken Momo',NULL,'SCM',_binary '',_binary '',1),(10,'admin','2025-08-09 13:36:57.632000','admin','2025-08-09 13:36:57.632000',_binary '\0',_binary '\0','','NON_VEG',2,'Kolhapuri Chicken Momo',NULL,'KCM',_binary '',_binary '',1),(11,'admin','2025-08-09 13:37:26.531000','admin','2025-08-09 13:37:26.531000',_binary '\0',_binary '\0','','NON_VEG',2,'Capsicum Chicken Momo',NULL,'CCM',_binary '',_binary '',1),(12,'admin','2025-08-09 13:37:54.011000','admin','2025-08-09 13:37:54.011000',_binary '\0',_binary '\0','','NON_VEG',2,'Pudina Chicken Momo ',NULL,'',_binary '',_binary '',1),(13,'admin','2025-08-09 13:39:23.348000','admin','2025-08-09 13:39:23.348000',_binary '\0',_binary '\0','','NON_VEG',2,'Pure Vetki Momo',NULL,'PVM',_binary '',_binary '',2),(14,'admin','2025-08-09 13:40:03.555000','admin','2025-08-09 13:42:42.514000',_binary '\0',_binary '\0','','NON_VEG',2,'Schezwan Vetki Momo',NULL,'SVM',_binary '',_binary '',2),(15,'admin','2025-08-09 13:40:40.233000','admin','2025-08-09 13:40:40.233000',_binary '\0',_binary '\0','','NON_VEG',2,'Butter Garlic Prawn Momo',NULL,'BGPM',_binary '',_binary '',2),(16,'admin','2025-08-09 13:42:06.862000','admin','2025-08-09 13:42:06.862000',_binary '\0',_binary '\0','','VEG',2,'Classic Veg Momo',NULL,'CVM',_binary '',_binary '',3),(17,'admin','2025-08-09 13:43:40.615000','admin','2025-08-09 13:43:40.615000',_binary '\0',_binary '\0','','VEG',2,'Premium Veg Momo',NULL,'PVM',_binary '',_binary '',3),(18,'admin','2025-08-09 13:44:43.099000','admin','2025-08-09 13:44:43.099000',_binary '\0',_binary '\0','','VEG',2,'Classic Paneer Momo',NULL,'CPM',_binary '',_binary '',3),(19,'admin','2025-08-09 13:45:31.194000','admin','2025-08-09 13:45:31.194000',_binary '\0',_binary '\0','','VEG',2,'Premium Paneer Momo',NULL,'PPM',_binary '',_binary '',3),(20,'admin','2025-08-09 13:46:12.877000','admin','2025-08-09 13:46:12.877000',_binary '\0',_binary '\0','','VEG',2,'Butter Garlic Paneer Momo ',NULL,'BGPM',_binary '',_binary '',3),(21,'admin','2025-08-09 13:46:52.953000','admin','2025-08-09 13:46:52.953000',_binary '\0',_binary '\0','','VEG',2,'Schezwan Paneer Momo',NULL,'SPM',_binary '',_binary '',3),(22,'admin','2025-08-09 13:47:35.649000','admin','2025-08-09 13:47:35.649000',_binary '\0',_binary '\0','','VEG',2,'Corn and Paneer Momo',NULL,'CPM',_binary '',_binary '',3),(23,'admin','2025-08-09 13:48:13.115000','admin','2025-08-09 13:48:13.115000',_binary '\0',_binary '\0','','VEG',2,'Aachari Paneer Momo',NULL,'APM',_binary '',_binary '',3),(24,'admin','2025-08-09 13:49:49.426000','admin','2025-08-09 13:51:00.879000',_binary '\0',_binary '\0','','NON_VEG',2,'Cheesy Chicken Momo',NULL,'CCM',_binary '',_binary '',4),(25,'admin','2025-08-09 13:50:34.287000','admin','2025-08-09 13:51:05.594000',_binary '\0',_binary '\0','','NON_VEG',2,'Premium Cheesy Chicken Momo',NULL,'PCCM',_binary '',_binary '',4),(26,'admin','2025-08-09 13:51:46.996000','admin','2025-08-09 13:51:46.996000',_binary '\0',_binary '\0','','VEG',2,'Molten Chicken Cheese Momo',NULL,'MCCM',_binary '',_binary '',4),(27,'admin','2025-08-09 13:52:48.196000','admin','2025-08-09 13:52:48.196000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Burst Momo',NULL,'CCBM',_binary '',_binary '',4),(28,'admin','2025-08-09 14:02:04.957000','admin','2025-08-09 14:02:04.957000',_binary '\0',_binary '\0','','VEG',2,'Corn and Cheese Momo',NULL,'CCM',_binary '',_binary '',4),(29,'admin','2025-08-09 14:02:38.361000','admin','2025-08-09 14:02:38.361000',_binary '\0',_binary '\0','','VEG',2,'Premium Corn & Cheese Momo',NULL,'PCCM',_binary '',_binary '',3),(30,'admin','2025-08-09 14:03:17.861000','admin','2025-08-09 14:03:17.861000',_binary '\0',_binary '\0','','VEG',2,'Molten Cheese & Corn Momo',NULL,'MCCM',_binary '',_binary '',4),(31,'admin','2025-08-09 14:04:17.112000','admin','2025-08-09 14:04:17.112000',_binary '\0',_binary '\0','','VEG',2,'Corn Cheese Burst Momo',NULL,'',_binary '',_binary '',4),(32,'admin','2025-08-09 14:04:54.235000','admin','2025-08-09 14:04:54.235000',_binary '\0',_binary '\0','','VEG',2,'Cheesy Paneer Momo',NULL,'CPM',_binary '',_binary '',3),(33,'admin','2025-08-09 14:05:29.694000','admin','2025-08-09 14:05:29.694000',_binary '\0',_binary '\0','','VEG',2,'Molten Cheese Paneer Momo ',NULL,'MCPM',_binary '',_binary '',4),(34,'admin','2025-08-09 14:09:02.332000','admin','2025-08-09 14:09:02.332000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Kurkure Momo',169.00,'',_binary '',_binary '\0',5),(35,'admin','2025-08-09 14:09:22.555000','admin','2025-08-09 14:09:22.555000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Kurkure Momo',189.00,'',_binary '',_binary '\0',5),(36,'admin','2025-08-09 14:09:40.832000','admin','2025-08-09 14:09:40.832000',_binary '\0',_binary '\0','','VEG',2,'Veg Kurkure Momo',139.00,'',_binary '',_binary '\0',5),(37,'admin','2025-08-09 14:10:17.455000','admin','2025-08-09 14:10:17.455000',_binary '\0',_binary '\0','','VEG',2,'Paneer Kurkure Momo',159.00,'',_binary '',_binary '\0',5),(38,'admin','2025-08-09 14:10:36.066000','admin','2025-08-09 14:10:36.066000',_binary '\0',_binary '\0','','VEG',2,'Corn and Cheese Kurkure Momo',159.00,'',_binary '',_binary '\0',5),(39,'admin','2025-08-09 14:11:56.324000','admin','2025-08-09 14:12:10.353000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Pizza Momo',178.00,'',_binary '',_binary '\0',6),(40,'admin','2025-08-09 14:12:21.897000','admin','2025-08-09 14:12:21.897000',_binary '\0',_binary '\0','','NON_VEG',2,'Kullad Chicken Pizza Momo',199.00,'',_binary '',_binary '\0',6),(41,'admin','2025-08-09 14:12:35.718000','admin','2025-08-09 14:12:35.718000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Afghani Momo',169.00,'',_binary '',_binary '\0',6),(42,'admin','2025-08-09 14:12:50.438000','admin','2025-08-09 14:12:50.438000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Malai Momo',169.00,'',_binary '',_binary '\0',5),(43,'admin','2025-08-09 14:13:35.675000','admin','2025-08-09 14:13:35.675000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Malai Momo',169.00,'',_binary '',_binary '\0',6),(44,'admin','2025-08-09 14:13:54.941000','admin','2025-08-09 14:13:54.941000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Tandoori Momo',169.00,'',_binary '',_binary '\0',6),(45,'admin','2025-08-09 14:14:07.991000','admin','2025-08-09 14:14:07.991000',_binary '\0',_binary '\0','','NON_VEG',2,'Italian Macaroni Chicken Momo',179.00,'',_binary '',_binary '\0',6),(46,'admin','2025-08-09 14:14:20.500000','admin','2025-08-09 14:14:20.500000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Malai Momo',199.00,'',_binary '',_binary '\0',6),(47,'admin','2025-08-09 14:14:40.724000','admin','2025-08-09 14:14:40.724000',_binary '\0',_binary '\0','','NON_VEG',2,'Fish Afghani Momo',199.00,'',_binary '',_binary '\0',6),(48,'admin','2025-08-09 14:15:00.035000','admin','2025-08-09 14:15:00.035000',_binary '\0',_binary '\0','','NON_VEG',2,'Darjeeling Kothey Momo',159.00,'',_binary '',_binary '\0',6),(49,'admin','2025-08-09 14:15:16.891000','admin','2025-08-09 14:15:16.891000',_binary '\0',_binary '\0','','NON_VEG',2,'Thai Sweet Chill Chicken Momo',179.00,'',_binary '',_binary '\0',6),(50,'admin','2025-08-09 14:15:33.812000','admin','2025-08-09 14:15:33.812000',_binary '\0',_binary '\0','','NON_VEG',2,'Spicy Dragon Chicken Momo',179.00,'',_binary '',_binary '\0',6),(51,'admin','2025-08-09 14:15:47.987000','admin','2025-08-09 14:15:47.987000',_binary '\0',_binary '\0','','NON_VEG',2,'Hot Garlic Chicken Momo',179.00,'',_binary '',_binary '\0',6),(52,'admin','2025-08-09 14:16:05.710000','admin','2025-08-09 14:16:05.710000',_binary '\0',_binary '\0','','VEG',2,'Veg Pizza Momo',169.00,'',_binary '',_binary '\0',6),(53,'admin','2025-08-09 14:16:24.868000','admin','2025-08-09 14:16:24.868000',_binary '\0',_binary '\0','','VEG',2,'Veg Afghani Momo',159.00,'',_binary '',_binary '\0',6),(54,'admin','2025-08-09 14:16:36.487000','admin','2025-08-09 14:16:36.487000',_binary '\0',_binary '\0','','VEG',2,'Veg Malai Momo',159.00,'',_binary '',_binary '\0',6),(55,'admin','2025-08-09 14:16:47.247000','admin','2025-08-09 14:16:47.247000',_binary '\0',_binary '\0','','VEG',2,'Italian Macaroni Veg Momo',169.00,'',_binary '',_binary '\0',6),(56,'admin','2025-08-09 14:17:01.254000','admin','2025-08-09 14:17:01.254000',_binary '\0',_binary '\0','','VEG',2,'Corn Cheese Malai Momo',169.00,'',_binary '',_binary '\0',6),(57,'admin','2025-08-09 14:17:26.856000','admin','2025-08-09 14:21:41.255000',_binary '\0',_binary '\0','','VEG',2,'Chocolate',NULL,'',_binary '',_binary '',8),(58,'admin','2025-08-09 14:17:41.091000','admin','2025-08-09 14:50:48.152000',_binary '\0',_binary '\0','','NON_VEG',2,'মনের মোমো',NULL,'',_binary '',_binary '',7),(59,'admin','2025-08-09 14:22:02.952000','admin','2025-08-09 14:22:02.952000',_binary '\0',_binary '\0','','VEG',2,'Strawberry',NULL,'',_binary '',_binary '',8),(60,'admin','2025-08-09 14:22:24.315000','admin','2025-08-09 14:22:24.315000',_binary '\0',_binary '\0','','VEG',2,'Mango',NULL,'',_binary '',_binary '',8),(61,'admin','2025-08-09 14:22:47.978000','admin','2025-08-09 14:22:47.978000',_binary '\0',_binary '\0','','VEG',2,'Orange',NULL,'',_binary '',_binary '',8),(62,'admin','2025-08-09 14:23:17.699000','admin','2025-08-09 14:23:17.699000',_binary '\0',_binary '\0','','VEG',2,'Vanilla',NULL,'',_binary '',_binary '',8),(63,'admin','2025-08-09 14:23:36.471000','admin','2025-08-09 14:23:36.471000',_binary '\0',_binary '\0','','NON_VEG',2,'Prawn Sizzler Momo',219.00,'',_binary '',_binary '\0',9),(64,'admin','2025-08-09 14:23:47.941000','admin','2025-08-09 14:23:47.941000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Sizzler Momo',219.00,'',_binary '',_binary '\0',9),(65,'admin','2025-08-09 14:24:00.510000','admin','2025-08-09 14:24:00.510000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Sizzler Momo',229.00,'',_binary '',_binary '\0',9),(66,'admin','2025-08-09 14:24:11.079000','admin','2025-08-09 14:24:11.079000',_binary '\0',_binary '\0','','VEG',2,'Veggie Sizzler Momo',199.00,'',_binary '',_binary '\0',9),(67,'admin','2025-08-09 14:24:21.357000','admin','2025-08-09 14:24:21.357000',_binary '\0',_binary '\0','','VEG',2,'Paneer Sizzler Momo',199.00,'',_binary '',_binary '\0',9),(68,'admin','2025-08-09 14:25:26.557000','admin','2025-08-09 14:25:39.366000',_binary '\0',_binary '\0','','NON_VEG',2,'Classic Chicken Pizza',149.00,'',_binary '',_binary '\0',10),(69,'admin','2025-08-09 14:25:57.482000','admin','2025-08-09 14:25:57.482000',_binary '\0',_binary '\0','','NON_VEG',2,'Loaded Chicken Pizza',169.00,'',_binary '',_binary '\0',10),(70,'admin','2025-08-09 14:26:14.134000','admin','2025-08-09 14:26:14.134000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Barbeque Pizza',189.00,'',_binary '',_binary '\0',10),(71,'admin','2025-08-09 14:26:25.915000','admin','2025-08-09 14:26:25.915000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Sausage Pizza',169.00,'',_binary '',_binary '\0',10),(72,'admin','2025-08-09 14:26:40.025000','admin','2025-08-09 14:26:40.025000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken with Olive Pizza',179.00,'',_binary '',_binary '\0',10),(73,'admin','2025-08-09 14:26:49.677000','admin','2025-08-09 14:26:49.677000',_binary '\0',_binary '\0','','VEG',2,'Margherita Pizza',109.00,'',_binary '',_binary '\0',10),(74,'admin','2025-08-09 14:27:03.621000','admin','2025-08-09 14:27:03.621000',_binary '\0',_binary '\0','','VEG',2,'Classic Veg Pizza',109.00,'',_binary '',_binary '\0',10),(75,'admin','2025-08-09 14:27:14.128000','admin','2025-08-09 14:27:14.128000',_binary '\0',_binary '\0','','VEG',2,'Veg Loaded Pizza',129.00,'',_binary '',_binary '\0',10),(76,'admin','2025-08-09 14:27:25.789000','admin','2025-08-09 14:27:25.789000',_binary '\0',_binary '\0','','VEG',2,'Corn and Cheese Pizza',149.00,'',_binary '',_binary '\0',10),(77,'admin','2025-08-09 14:27:35.116000','admin','2025-08-09 14:27:35.116000',_binary '\0',_binary '\0','','VEG',2,'Paneer Cheese Pizza',149.00,'',_binary '',_binary '\0',10),(78,'admin','2025-08-09 14:32:02.362000','admin','2025-08-09 14:32:02.362000',_binary '\0',_binary '\0','','VEG',2,'White Sauce Pasta (VEG)',129.00,'',_binary '',_binary '\0',11),(79,'admin','2025-08-09 14:32:22.876000','admin','2025-08-09 14:32:22.876000',_binary '\0',_binary '\0','','NON_VEG',2,'White Sauce Pasta(NON VEG)',159.00,'',_binary '',_binary '\0',11),(80,'admin','2025-08-09 14:32:42.400000','admin','2025-08-09 14:32:42.400000',_binary '\0',_binary '\0','','VEG',2,'Red Sauce Pasta (VEG)',129.00,'',_binary '',_binary '\0',11),(81,'admin','2025-08-09 14:33:25.463000','admin','2025-08-09 14:33:25.463000',_binary '\0',_binary '\0','','NON_VEG',2,'Red Sauce Pasta (NON VEG)',159.00,'',_binary '',_binary '\0',11),(82,'admin','2025-08-09 14:33:40.648000','admin','2025-08-09 14:33:40.648000',_binary '\0',_binary '\0','','VEG',2,'Mixed Sauce Pasta(VEG)',149.00,'',_binary '',_binary '\0',11),(83,'admin','2025-08-09 14:34:02.612000','admin','2025-08-09 14:34:02.612000',_binary '\0',_binary '\0','','NON_VEG',2,'Mixed Sauce Pasta(NON VEG)',179.00,'',_binary '',_binary '\0',11),(84,'admin','2025-08-09 14:34:16.979000','admin','2025-08-09 14:34:16.979000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Moburg',89.00,'',_binary '',_binary '\0',12),(85,'admin','2025-08-09 14:34:31.938000','admin','2025-08-09 14:34:31.938000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Moburg',109.00,'',_binary '',_binary '\0',12),(86,'admin','2025-08-09 14:34:52.120000','admin','2025-08-09 14:34:52.120000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Burger ',119.00,'',_binary '',_binary '\0',12),(87,'admin','2025-08-09 14:35:04.645000','admin','2025-08-09 14:35:04.645000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Burger ',139.00,'',_binary '',_binary '\0',12),(88,'admin','2025-08-09 14:35:15.344000','admin','2025-08-09 14:35:15.344000',_binary '\0',_binary '\0','','VEG',2,'Veg Moburg',79.00,'',_binary '',_binary '\0',12),(89,'admin','2025-08-09 14:35:24.179000','admin','2025-08-09 14:35:24.179000',_binary '\0',_binary '\0','','VEG',2,'Veg Cheese Moburg',99.00,'',_binary '',_binary '\0',12),(90,'admin','2025-08-09 14:35:34.290000','admin','2025-08-09 14:35:34.290000',_binary '\0',_binary '\0','','VEG',2,'Veg Burger ',99.00,'',_binary '',_binary '\0',12),(91,'admin','2025-08-09 14:35:46.744000','admin','2025-08-09 14:35:46.744000',_binary '\0',_binary '\0','','VEG',2,'Veg Cheese Burger ',129.00,'',_binary '',_binary '\0',12),(92,'admin','2025-08-09 14:36:15.257000','admin','2025-08-09 14:36:15.257000',_binary '\0',_binary '\0','','NON_VEG',2,'Classic Chicken Sandwich',109.00,'',_binary '',_binary '\0',13),(93,'admin','2025-08-09 14:36:25.953000','admin','2025-08-09 14:36:25.953000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Mayo Sandwich ',119.00,'',_binary '',_binary '\0',13),(94,'admin','2025-08-09 14:36:37.857000','admin','2025-08-09 14:36:37.857000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Cheese Sandwich ',139.00,'',_binary '',_binary '\0',13),(95,'admin','2025-08-09 14:36:55.749000','admin','2025-08-09 14:36:55.749000',_binary '\0',_binary '\0','','NON_VEG',2,'Chicken Club Sandwich ',149.00,'',_binary '',_binary '\0',13),(96,'admin','2025-08-09 14:38:12.840000','admin','2025-08-09 14:38:12.840000',_binary '\0',_binary '\0','','NON_VEG',2,'Classic Egg Sandwich ',89.00,'',_binary '',_binary '\0',13),(97,'admin','2025-08-09 14:38:21.950000','admin','2025-08-09 14:38:36.924000',_binary '\0',_binary '\0','','NON_VEG',2,'Egg Mayo Sandwich ',109.00,'',_binary '',_binary '\0',13),(98,'admin','2025-08-09 14:38:50.171000','admin','2025-08-09 14:38:50.171000',_binary '\0',_binary '\0','','VEG',2,'Veg Mayo Sandwich',99.00,'',_binary '',_binary '\0',13),(99,'admin','2025-08-09 14:39:03.110000','admin','2025-08-09 14:39:03.110000',_binary '\0',_binary '\0','','VEG',2,'Veg Cheese Sandwich',119.00,'',_binary '',_binary '\0',13),(100,'admin','2025-08-09 14:39:15.080000','admin','2025-08-09 14:39:15.080000',_binary '\0',_binary '\0','','VEG',2,'Veg Club Sandwich',139.00,'',_binary '',_binary '\0',13),(101,'admin','2025-08-09 14:39:27.242000','admin','2025-08-09 14:39:27.242000',_binary '\0',_binary '\0','','VEG',2,'Classic Paneer Sandwich ',129.00,'',_binary '',_binary '\0',13),(102,'admin','2025-08-09 14:39:36.784000','admin','2025-08-09 14:39:36.784000',_binary '\0',_binary '\0','','VEG',2,'Paneer Cheese Sandwich ',149.00,'',_binary '',_binary '\0',13),(103,'admin','2025-08-09 14:39:52.883000','admin','2025-08-09 14:39:52.883000',_binary '\0',_binary '\0','','VEG',2,'Black Tea',20.00,'',_binary '',_binary '\0',14),(104,'admin','2025-08-09 14:40:02.510000','admin','2025-08-09 14:40:02.510000',_binary '\0',_binary '\0','','VEG',2,'Green Tea',50.00,'',_binary '',_binary '\0',14),(105,'admin','2025-08-09 14:40:14.368000','admin','2025-08-09 14:40:14.368000',_binary '\0',_binary '\0','','VEG',2,'Ginger Elaichi Tea',30.00,'',_binary '',_binary '\0',14),(106,'admin','2025-08-09 14:40:28.084000','admin','2025-08-09 14:40:28.084000',_binary '\0',_binary '\0','','VEG',2,'Keshar Elaichi Tea',60.00,'',_binary '',_binary '\0',14),(107,'admin','2025-08-09 14:40:37.989000','admin','2025-08-09 14:40:37.989000',_binary '\0',_binary '\0','','VEG',2,'Masala Tea',40.00,'',_binary '',_binary '\0',14),(108,'admin','2025-08-09 14:40:48.552000','admin','2025-08-09 14:40:48.552000',_binary '\0',_binary '\0','','VEG',2,'Chocolate Tea',60.00,'',_binary '',_binary '\0',14),(109,'admin','2025-08-09 14:40:57.791000','admin','2025-08-09 14:40:57.791000',_binary '\0',_binary '\0','','VEG',2,'Malai Tea',50.00,'',_binary '',_binary '\0',14),(110,'admin','2025-08-09 14:41:08.560000','admin','2025-08-09 14:41:08.560000',_binary '\0',_binary '\0','','VEG',2,'Black Coffee  ',50.00,'',_binary '',_binary '\0',15),(111,'admin','2025-08-09 14:41:17.905000','admin','2025-08-09 14:41:17.905000',_binary '\0',_binary '\0','','VEG',2,'Hot Coffee',50.00,'',_binary '',_binary '\0',15),(112,'admin','2025-08-09 14:41:27.462000','admin','2025-08-09 14:41:27.462000',_binary '\0',_binary '\0','','VEG',2,'Premium Hot Coffee',60.00,'',_binary '',_binary '\0',15),(113,'admin','2025-08-09 14:41:38.645000','admin','2025-08-09 14:41:38.645000',_binary '\0',_binary '\0','','VEG',2,'Cold Coffee with Cream ',109.00,'',_binary '',_binary '\0',15),(114,'admin','2025-08-09 14:41:48.264000','admin','2025-08-09 14:41:48.264000',_binary '\0',_binary '\0','','VEG',2,'Cold Coffee with Ice-Cream',129.00,'',_binary '',_binary '\0',15),(115,'admin','2025-08-09 14:42:03.490000','admin','2025-08-09 14:42:03.490000',_binary '\0',_binary '\0','','VEG',2,'Chocolate Milkshake ',119.00,'',_binary '',_binary '\0',16),(116,'admin','2025-08-09 14:42:13.600000','admin','2025-08-09 14:42:13.600000',_binary '\0',_binary '\0','','VEG',2,'Strawberry Milkshake',119.00,'',_binary '',_binary '\0',16),(117,'admin','2025-08-09 14:42:24.754000','admin','2025-08-09 14:42:24.754000',_binary '\0',_binary '\0','','VEG',2,'Oreo Milkshake',139.00,'',_binary '',_binary '\0',16),(118,'admin','2025-08-09 14:42:35.323000','admin','2025-08-09 14:42:35.323000',_binary '\0',_binary '\0','','VEG',2,'Kitkat Milkshake',139.00,'',_binary '',_binary '\0',16),(119,'admin','2025-08-09 14:42:44.214000','admin','2025-08-09 14:42:44.214000',_binary '\0',_binary '\0','','VEG',2,'Mango Milkshake',139.00,'',_binary '',_binary '\0',16),(120,'admin','2025-08-09 14:42:58.103000','admin','2025-08-09 14:42:58.103000',_binary '\0',_binary '\0','','VEG',2,'Sizzler Brownie with Hot Chocolate and Ice-Cream ',149.00,'',_binary '',_binary '\0',17),(121,'admin','2025-08-09 14:43:20.341000','admin','2025-08-09 14:43:20.341000',_binary '\0',_binary '\0','','VEG',2,'Chocolate Momo with Ice-Cream Sizzler ',179.00,'',_binary '',_binary '\0',17),(122,'admin','2025-08-09 14:43:30.745000','admin','2025-08-09 14:43:30.745000',_binary '\0',_binary '\0','','VEG',2,'Plain Coke ',39.00,'',_binary '',_binary '\0',18),(123,'admin','2025-08-09 14:43:40.600000','admin','2025-08-09 14:43:40.600000',_binary '\0',_binary '\0','','VEG',2,'Masala Coke',69.00,'',_binary '',_binary '\0',18),(124,'admin','2025-08-09 14:43:51.512000','admin','2025-08-09 14:43:51.512000',_binary '\0',_binary '\0','','VEG',2,'Blue Sea',99.00,'',_binary '',_binary '\0',18),(125,'admin','2025-08-09 14:44:04.256000','admin','2025-08-09 14:44:04.256000',_binary '\0',_binary '\0','','VEG',2,'Virgin Mojito ',99.00,'',_binary '',_binary '\0',18),(126,'admin','2025-08-09 14:44:18.915000','admin','2025-08-09 14:44:18.915000',_binary '\0',_binary '\0','','VEG',2,'Mint Mojito ',99.00,'',_binary '',_binary '\0',18),(127,'admin','2025-08-09 14:44:31.598000','admin','2025-08-09 14:44:31.598000',_binary '\0',_binary '\0','','VEG',2,'Cucumber Mojito ',99.00,'',_binary '',_binary '\0',18),(128,'admin','2025-08-09 14:44:40.759000','admin','2025-08-09 14:44:40.759000',_binary '\0',_binary '\0','','VEG',2,'Pineapple Mojito ',99.00,'',_binary '',_binary '\0',18),(129,'admin','2025-08-09 14:44:51.128000','admin','2025-08-09 14:44:51.128000',_binary '\0',_binary '\0','','VEG',2,'Watermelon Crush ',99.00,'',_binary '',_binary '\0',18),(130,'admin','2025-08-09 14:44:59.607000','admin','2025-08-09 14:44:59.607000',_binary '\0',_binary '\0','','VEG',2,'Tangy Orange ',99.00,'',_binary '',_binary '\0',18),(131,'admin','2025-08-09 14:45:08.794000','admin','2025-08-09 14:45:08.794000',_binary '\0',_binary '\0','','VEG',2,'Sweetest Strawberry ',99.00,'',_binary '',_binary '\0',18),(132,'admin','2025-08-09 14:48:19.497000','admin','2025-08-09 14:48:19.497000',_binary '\0',_binary '','','VEG',2,'মনের মোমো (5 pc) + Veg Moburg (1 pc) + 200 ml Cola (1 pc)',129.00,'',_binary '',_binary '\0',19),(133,'admin','2025-08-09 14:49:16.012000','admin','2025-08-09 14:49:16.012000',_binary '\0',_binary '','','VEG',2,'Dhania Chicken Momo (4 pc) + মনের মোমো- Fried (4 pc) + 200 ml Cola (1 pc)',149.00,'',_binary '',_binary '\0',19),(134,'admin','2025-08-09 14:50:22.959000','admin','2025-08-09 14:50:22.959000',_binary '\0',_binary '','','NON_VEG',2,'Butter Garlic Chicken Momo (4 pc) + Chicken Moburg (1 pc) + মনের মোমো- Pan-Fried (4 pc) + 200 ml Cola (1 pc)',199.00,'',_binary '',_binary '\0',19);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_addons`
--

DROP TABLE IF EXISTS `item_addons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_addons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `status` bit(1) NOT NULL,
  `item_id` int DEFAULT NULL,
  `item_variation_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2ch670yw8la98t10w2lrlhnmg` (`item_id`),
  KEY `FK229s6nav4kr4f6kwet1l6e56h` (`item_variation_id`),
  CONSTRAINT `FK229s6nav4kr4f6kwet1l6e56h` FOREIGN KEY (`item_variation_id`) REFERENCES `item_variations` (`id`),
  CONSTRAINT `FK2ch670yw8la98t10w2lrlhnmg` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_addons`
--

LOCK TABLES `item_addons` WRITE;
/*!40000 ALTER TABLE `item_addons` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_addons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_combo_items`
--

DROP TABLE IF EXISTS `item_combo_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_combo_items` (
  `combo_item_id` int NOT NULL,
  `included_item_id` int NOT NULL,
  KEY `FK8st1nqq6n7jfmvqjgghh534hf` (`included_item_id`),
  KEY `FKohdxgb65j4dqw96w9bs5am2nk` (`combo_item_id`),
  CONSTRAINT `FK8st1nqq6n7jfmvqjgghh534hf` FOREIGN KEY (`included_item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKohdxgb65j4dqw96w9bs5am2nk` FOREIGN KEY (`combo_item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_combo_items`
--

LOCK TABLES `item_combo_items` WRITE;
/*!40000 ALTER TABLE `item_combo_items` DISABLE KEYS */;
INSERT INTO `item_combo_items` VALUES (132,58),(132,88),(132,122),(133,2),(133,58),(133,122),(134,6),(134,58),(134,84),(134,122);
/*!40000 ALTER TABLE `item_combo_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_order_types`
--

DROP TABLE IF EXISTS `item_order_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_order_types` (
  `item_id` int NOT NULL,
  `order_type` enum('DELIVERY','DINE_IN','PICKUP') DEFAULT NULL,
  KEY `FKrivnd1heisl2ier9acfp539wf` (`item_id`),
  CONSTRAINT `FKrivnd1heisl2ier9acfp539wf` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_order_types`
--

LOCK TABLES `item_order_types` WRITE;
/*!40000 ALTER TABLE `item_order_types` DISABLE KEYS */;
INSERT INTO `item_order_types` VALUES (1,'DINE_IN'),(1,'PICKUP'),(1,'DELIVERY'),(2,'DINE_IN'),(2,'PICKUP'),(2,'DELIVERY'),(3,'DINE_IN'),(3,'PICKUP'),(3,'DELIVERY'),(4,'DINE_IN'),(4,'PICKUP'),(4,'DELIVERY'),(5,'DINE_IN'),(5,'PICKUP'),(5,'DELIVERY'),(6,'DINE_IN'),(6,'PICKUP'),(6,'DELIVERY'),(7,'DINE_IN'),(7,'PICKUP'),(7,'DELIVERY'),(8,'DINE_IN'),(8,'PICKUP'),(8,'DELIVERY'),(9,'DINE_IN'),(9,'PICKUP'),(9,'DELIVERY'),(10,'DINE_IN'),(11,'DINE_IN'),(11,'PICKUP'),(11,'DELIVERY'),(12,'DINE_IN'),(12,'PICKUP'),(12,'DELIVERY'),(13,'DINE_IN'),(13,'PICKUP'),(13,'DELIVERY'),(15,'DINE_IN'),(15,'PICKUP'),(16,'DINE_IN'),(16,'DELIVERY'),(14,'DINE_IN'),(14,'PICKUP'),(14,'DELIVERY'),(17,'DINE_IN'),(17,'PICKUP'),(17,'DELIVERY'),(18,'DINE_IN'),(18,'PICKUP'),(18,'DELIVERY'),(19,'DINE_IN'),(19,'PICKUP'),(19,'DELIVERY'),(20,'DINE_IN'),(20,'PICKUP'),(20,'DELIVERY'),(21,'DINE_IN'),(21,'PICKUP'),(21,'DELIVERY'),(22,'DINE_IN'),(22,'PICKUP'),(22,'DELIVERY'),(23,'DINE_IN'),(23,'PICKUP'),(23,'DELIVERY'),(24,'DINE_IN'),(24,'PICKUP'),(24,'DELIVERY'),(25,'DINE_IN'),(25,'PICKUP'),(25,'DELIVERY'),(26,'DINE_IN'),(26,'PICKUP'),(26,'DELIVERY'),(27,'DINE_IN'),(27,'PICKUP'),(27,'DELIVERY'),(28,'DINE_IN'),(28,'PICKUP'),(28,'DELIVERY'),(29,'DINE_IN'),(29,'PICKUP'),(29,'DELIVERY'),(30,'DINE_IN'),(30,'PICKUP'),(30,'DELIVERY'),(31,'DINE_IN'),(31,'PICKUP'),(31,'DELIVERY'),(32,'DINE_IN'),(32,'DELIVERY'),(33,'DINE_IN'),(33,'PICKUP'),(33,'DELIVERY'),(34,'DINE_IN'),(34,'PICKUP'),(34,'DELIVERY'),(35,'DINE_IN'),(35,'PICKUP'),(35,'DELIVERY'),(36,'DINE_IN'),(36,'PICKUP'),(36,'DELIVERY'),(37,'DINE_IN'),(37,'PICKUP'),(37,'DELIVERY'),(38,'DINE_IN'),(38,'PICKUP'),(38,'DELIVERY'),(39,'DINE_IN'),(39,'PICKUP'),(39,'DELIVERY'),(40,'DINE_IN'),(40,'PICKUP'),(40,'DELIVERY'),(41,'DINE_IN'),(41,'PICKUP'),(41,'DELIVERY'),(42,'DINE_IN'),(42,'PICKUP'),(42,'DELIVERY'),(43,'DINE_IN'),(43,'PICKUP'),(43,'DELIVERY'),(44,'DINE_IN'),(44,'PICKUP'),(44,'DELIVERY'),(45,'DINE_IN'),(45,'PICKUP'),(45,'DELIVERY'),(46,'DINE_IN'),(46,'PICKUP'),(46,'DELIVERY'),(47,'DINE_IN'),(47,'PICKUP'),(47,'DELIVERY'),(48,'DINE_IN'),(48,'PICKUP'),(48,'DELIVERY'),(49,'DINE_IN'),(49,'PICKUP'),(49,'DELIVERY'),(50,'DINE_IN'),(50,'PICKUP'),(50,'DELIVERY'),(51,'DINE_IN'),(51,'PICKUP'),(51,'DELIVERY'),(52,'DINE_IN'),(52,'PICKUP'),(52,'DELIVERY'),(53,'DINE_IN'),(53,'PICKUP'),(53,'DELIVERY'),(54,'DINE_IN'),(54,'PICKUP'),(54,'DELIVERY'),(55,'DINE_IN'),(55,'PICKUP'),(55,'DELIVERY'),(56,'DINE_IN'),(56,'PICKUP'),(56,'DELIVERY'),(57,'DINE_IN'),(57,'PICKUP'),(57,'DELIVERY'),(59,'DINE_IN'),(59,'PICKUP'),(59,'DELIVERY'),(60,'DINE_IN'),(60,'PICKUP'),(60,'DELIVERY'),(61,'DINE_IN'),(61,'PICKUP'),(61,'DELIVERY'),(62,'DINE_IN'),(62,'PICKUP'),(62,'DELIVERY'),(63,'DINE_IN'),(63,'PICKUP'),(63,'DELIVERY'),(64,'DINE_IN'),(64,'PICKUP'),(64,'DELIVERY'),(65,'DINE_IN'),(65,'PICKUP'),(65,'DELIVERY'),(66,'DINE_IN'),(66,'PICKUP'),(66,'DELIVERY'),(67,'DINE_IN'),(67,'PICKUP'),(67,'DELIVERY'),(68,'DINE_IN'),(68,'PICKUP'),(68,'DELIVERY'),(69,'DINE_IN'),(69,'PICKUP'),(69,'DELIVERY'),(70,'DINE_IN'),(70,'PICKUP'),(70,'DELIVERY'),(71,'DINE_IN'),(71,'PICKUP'),(71,'DELIVERY'),(72,'DINE_IN'),(72,'PICKUP'),(72,'DELIVERY'),(73,'DINE_IN'),(73,'PICKUP'),(73,'DELIVERY'),(74,'DINE_IN'),(74,'PICKUP'),(74,'DELIVERY'),(75,'DINE_IN'),(75,'PICKUP'),(75,'DELIVERY'),(76,'DINE_IN'),(76,'PICKUP'),(76,'DELIVERY'),(77,'DINE_IN'),(77,'PICKUP'),(77,'DELIVERY'),(78,'DINE_IN'),(78,'PICKUP'),(78,'DELIVERY'),(79,'DINE_IN'),(79,'PICKUP'),(79,'DELIVERY'),(80,'DINE_IN'),(80,'PICKUP'),(80,'DELIVERY'),(81,'DINE_IN'),(81,'PICKUP'),(81,'DELIVERY'),(82,'DINE_IN'),(82,'PICKUP'),(82,'DELIVERY'),(83,'DINE_IN'),(83,'PICKUP'),(83,'DELIVERY'),(84,'DINE_IN'),(84,'PICKUP'),(84,'DELIVERY'),(85,'DINE_IN'),(85,'PICKUP'),(85,'DELIVERY'),(86,'DINE_IN'),(86,'PICKUP'),(86,'DELIVERY'),(87,'DINE_IN'),(87,'PICKUP'),(87,'DELIVERY'),(88,'DINE_IN'),(88,'PICKUP'),(88,'DELIVERY'),(89,'DINE_IN'),(89,'PICKUP'),(89,'DELIVERY'),(90,'DINE_IN'),(90,'PICKUP'),(90,'DELIVERY'),(91,'DINE_IN'),(91,'PICKUP'),(91,'DELIVERY'),(92,'DINE_IN'),(92,'PICKUP'),(92,'DELIVERY'),(93,'DINE_IN'),(93,'PICKUP'),(93,'DELIVERY'),(94,'DINE_IN'),(94,'PICKUP'),(94,'DELIVERY'),(95,'DINE_IN'),(95,'PICKUP'),(95,'DELIVERY'),(96,'DINE_IN'),(96,'PICKUP'),(96,'DELIVERY'),(97,'DINE_IN'),(97,'PICKUP'),(97,'DELIVERY'),(98,'DINE_IN'),(98,'PICKUP'),(98,'DELIVERY'),(99,'DINE_IN'),(99,'PICKUP'),(99,'DELIVERY'),(100,'DINE_IN'),(100,'PICKUP'),(100,'DELIVERY'),(101,'DINE_IN'),(101,'PICKUP'),(101,'DELIVERY'),(102,'DINE_IN'),(102,'PICKUP'),(102,'DELIVERY'),(103,'DINE_IN'),(103,'PICKUP'),(103,'DELIVERY'),(104,'DINE_IN'),(104,'PICKUP'),(104,'DELIVERY'),(105,'DINE_IN'),(105,'PICKUP'),(105,'DELIVERY'),(106,'DINE_IN'),(106,'PICKUP'),(106,'DELIVERY'),(107,'DINE_IN'),(107,'PICKUP'),(107,'DELIVERY'),(108,'DINE_IN'),(108,'PICKUP'),(108,'DELIVERY'),(109,'DINE_IN'),(109,'PICKUP'),(109,'DELIVERY'),(110,'DINE_IN'),(110,'PICKUP'),(110,'DELIVERY'),(111,'DINE_IN'),(111,'PICKUP'),(111,'DELIVERY'),(112,'DINE_IN'),(112,'PICKUP'),(112,'DELIVERY'),(113,'DINE_IN'),(113,'PICKUP'),(113,'DELIVERY'),(114,'DINE_IN'),(114,'PICKUP'),(114,'DELIVERY'),(115,'DINE_IN'),(115,'PICKUP'),(115,'DELIVERY'),(116,'DINE_IN'),(116,'PICKUP'),(116,'DELIVERY'),(117,'DINE_IN'),(117,'PICKUP'),(117,'DELIVERY'),(118,'DINE_IN'),(118,'PICKUP'),(118,'DELIVERY'),(119,'DINE_IN'),(119,'PICKUP'),(119,'DELIVERY'),(120,'DINE_IN'),(120,'PICKUP'),(120,'DELIVERY'),(121,'DINE_IN'),(121,'PICKUP'),(121,'DELIVERY'),(122,'DINE_IN'),(122,'PICKUP'),(122,'DELIVERY'),(123,'DINE_IN'),(123,'PICKUP'),(123,'DELIVERY'),(124,'DINE_IN'),(124,'PICKUP'),(124,'DELIVERY'),(125,'DINE_IN'),(125,'PICKUP'),(125,'DELIVERY'),(126,'DINE_IN'),(126,'PICKUP'),(126,'DELIVERY'),(127,'DINE_IN'),(127,'PICKUP'),(127,'DELIVERY'),(128,'DINE_IN'),(128,'PICKUP'),(128,'DELIVERY'),(129,'DINE_IN'),(129,'PICKUP'),(129,'DELIVERY'),(130,'DINE_IN'),(130,'PICKUP'),(130,'DELIVERY'),(131,'DINE_IN'),(131,'PICKUP'),(131,'DELIVERY'),(132,'DINE_IN'),(132,'PICKUP'),(132,'DELIVERY'),(133,'DINE_IN'),(133,'PICKUP'),(133,'DELIVERY'),(134,'DINE_IN'),(134,'PICKUP'),(134,'DELIVERY'),(58,'DINE_IN'),(58,'PICKUP'),(58,'DELIVERY');
/*!40000 ALTER TABLE `item_order_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_variations`
--

DROP TABLE IF EXISTS `item_variations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_variations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `addons` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `status` bit(1) NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3xk4ep8sxagvjtrdyvfra3j14` (`item_id`),
  CONSTRAINT `FK3xk4ep8sxagvjtrdyvfra3j14` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_variations`
--

LOCK TABLES `item_variations` WRITE;
/*!40000 ALTER TABLE `item_variations` DISABLE KEYS */;
INSERT INTO `item_variations` VALUES (1,'admin','2025-08-09 13:31:01.777000','admin','2025-08-09 13:31:01.777000',_binary '\0','Steam Momo',99.00,_binary '',1),(2,'admin','2025-08-09 13:31:01.791000','admin','2025-08-09 13:31:01.791000',_binary '\0','Fried Momo',199.00,_binary '',1),(3,'admin','2025-08-09 13:31:01.796000','admin','2025-08-09 13:31:01.796000',_binary '\0','Pan Fried Momo',149.00,_binary '',1),(4,'admin','2025-08-09 13:32:03.174000','admin','2025-08-09 13:32:03.174000',_binary '\0','Steam Momo',99.00,_binary '',2),(5,'admin','2025-08-09 13:32:03.180000','admin','2025-08-09 13:32:03.180000',_binary '\0','Fried Momo',199.00,_binary '',2),(6,'admin','2025-08-09 13:32:03.184000','admin','2025-08-09 13:32:03.184000',_binary '\0','Pan Fried Momo',149.00,_binary '',2),(7,'admin','2025-08-09 13:32:52.392000','admin','2025-08-09 13:32:52.392000',_binary '\0','Steam Momo',99.00,_binary '',3),(8,'admin','2025-08-09 13:32:52.397000','admin','2025-08-09 13:32:52.397000',_binary '\0','Fried Momo',119.00,_binary '',3),(9,'admin','2025-08-09 13:32:52.400000','admin','2025-08-09 13:32:52.400000',_binary '\0','Pan Fried Momo',149.00,_binary '',3),(10,'admin','2025-08-09 13:33:35.137000','admin','2025-08-09 13:33:35.137000',_binary '\0','Steam Momo',99.00,_binary '',4),(11,'admin','2025-08-09 13:33:35.141000','admin','2025-08-09 13:33:35.141000',_binary '\0','Fried Momo',119.00,_binary '',4),(12,'admin','2025-08-09 13:33:35.146000','admin','2025-08-09 13:33:35.146000',_binary '\0','Pan Fried Momo',149.00,_binary '',4),(13,'admin','2025-08-09 13:34:09.792000','admin','2025-08-09 13:34:09.792000',_binary '\0','Steam Momo',99.00,_binary '',5),(14,'admin','2025-08-09 13:34:09.796000','admin','2025-08-09 13:34:09.796000',_binary '\0','Fried Momo',119.00,_binary '',5),(15,'admin','2025-08-09 13:34:09.801000','admin','2025-08-09 13:34:09.801000',_binary '\0','Pan Fried Momo',149.00,_binary '',5),(16,'admin','2025-08-09 13:34:49.645000','admin','2025-08-09 13:34:49.645000',_binary '\0','Steam Momo',99.00,_binary '',6),(17,'admin','2025-08-09 13:34:49.653000','admin','2025-08-09 13:34:49.653000',_binary '\0','Fried Momo',119.00,_binary '',6),(18,'admin','2025-08-09 13:34:49.663000','admin','2025-08-09 13:34:49.663000',_binary '\0','Pan Fried Momo',149.00,_binary '',6),(19,'admin','2025-08-09 13:35:24.766000','admin','2025-08-09 13:35:24.766000',_binary '\0','Steam Momo',99.00,_binary '',7),(20,'admin','2025-08-09 13:35:24.769000','admin','2025-08-09 13:35:24.769000',_binary '\0','Fried Momo',119.00,_binary '',7),(21,'admin','2025-08-09 13:35:24.773000','admin','2025-08-09 13:35:24.773000',_binary '\0','Pan Fried Momo',149.00,_binary '',7),(22,'admin','2025-08-09 13:35:55.930000','admin','2025-08-09 13:35:55.930000',_binary '\0','Steam Momo',99.00,_binary '',8),(23,'admin','2025-08-09 13:35:55.935000','admin','2025-08-09 13:35:55.935000',_binary '\0','Fried Momo',119.00,_binary '',8),(24,'admin','2025-08-09 13:35:55.939000','admin','2025-08-09 13:35:55.939000',_binary '\0','Pan Fried Momo',149.00,_binary '',8),(25,'admin','2025-08-09 13:36:26.458000','admin','2025-08-09 13:36:26.458000',_binary '\0','Steam Momo',99.00,_binary '',9),(26,'admin','2025-08-09 13:36:26.460000','admin','2025-08-09 13:36:26.460000',_binary '\0','Fried Momo',119.00,_binary '',9),(27,'admin','2025-08-09 13:36:26.464000','admin','2025-08-09 13:36:26.464000',_binary '\0','Pan Fried Momo',149.00,_binary '',9),(28,'admin','2025-08-09 13:36:57.637000','admin','2025-08-09 13:36:57.637000',_binary '\0','Steam Momo',99.00,_binary '',10),(29,'admin','2025-08-09 13:36:57.641000','admin','2025-08-09 13:36:57.641000',_binary '\0','Fried Momo',119.00,_binary '',10),(30,'admin','2025-08-09 13:36:57.645000','admin','2025-08-09 13:36:57.645000',_binary '\0','Pan Fried Momo',149.00,_binary '',10),(31,'admin','2025-08-09 13:37:26.535000','admin','2025-08-09 13:37:26.535000',_binary '\0','Steam Momo',99.00,_binary '',11),(32,'admin','2025-08-09 13:37:26.539000','admin','2025-08-09 13:37:26.539000',_binary '\0','Fried Momo',119.00,_binary '',11),(33,'admin','2025-08-09 13:37:26.542000','admin','2025-08-09 13:37:26.542000',_binary '\0','Pan Fried Momo',149.00,_binary '',11),(34,'admin','2025-08-09 13:37:54.015000','admin','2025-08-09 13:37:54.015000',_binary '\0','Steam Momo',99.00,_binary '',12),(35,'admin','2025-08-09 13:37:54.018000','admin','2025-08-09 13:37:54.018000',_binary '\0','Fried Momo',119.00,_binary '',12),(36,'admin','2025-08-09 13:37:54.022000','admin','2025-08-09 13:37:54.022000',_binary '\0','Pan Fried Momo',149.00,_binary '',12),(37,'admin','2025-08-09 13:39:23.353000','admin','2025-08-09 13:39:23.353000',_binary '\0','Steam Momo',139.00,_binary '',13),(38,'admin','2025-08-09 13:39:23.357000','admin','2025-08-09 13:39:23.357000',_binary '\0','Fried Momo',159.00,_binary '',13),(39,'admin','2025-08-09 13:39:23.360000','admin','2025-08-09 13:39:23.360000',_binary '\0','Pan Fried Momo',189.00,_binary '',13),(40,'admin','2025-08-09 13:40:03.559000','admin','2025-08-09 13:42:42.515000',_binary '\0','Steam Momo',149.00,_binary '',14),(41,'admin','2025-08-09 13:40:03.563000','admin','2025-08-09 13:42:42.515000',_binary '\0','Fried Momo',169.00,_binary '',14),(42,'admin','2025-08-09 13:40:03.566000','admin','2025-08-09 13:42:42.515000',_binary '\0','Pan Fried Momo',199.00,_binary '',14),(43,'admin','2025-08-09 13:40:40.238000','admin','2025-08-09 13:40:40.238000',_binary '\0','Steam Momo',139.00,_binary '',15),(44,'admin','2025-08-09 13:40:40.242000','admin','2025-08-09 13:40:40.242000',_binary '\0','Fried Momo',159.00,_binary '',15),(45,'admin','2025-08-09 13:40:40.245000','admin','2025-08-09 13:40:40.245000',_binary '\0','Pan Fried Momo',189.00,_binary '',15),(46,'admin','2025-08-09 13:42:06.867000','admin','2025-08-09 13:42:06.867000',_binary '\0','Steam Momo',79.00,_binary '',16),(47,'admin','2025-08-09 13:42:06.870000','admin','2025-08-09 13:42:06.870000',_binary '\0','Fried Momo',99.00,_binary '',16),(48,'admin','2025-08-09 13:42:06.874000','admin','2025-08-09 13:42:06.874000',_binary '\0','Pan Fried Momo',129.00,_binary '',16),(49,'admin','2025-08-09 13:43:40.618000','admin','2025-08-09 13:43:40.618000',_binary '\0','Steam Momo',99.00,_binary '',17),(50,'admin','2025-08-09 13:43:40.622000','admin','2025-08-09 13:43:40.622000',_binary '\0','Fried Momo',119.00,_binary '',17),(51,'admin','2025-08-09 13:43:40.626000','admin','2025-08-09 13:43:40.626000',_binary '\0','Pan Fried Momo',149.00,_binary '',17),(52,'admin','2025-08-09 13:44:43.103000','admin','2025-08-09 13:44:43.103000',_binary '\0','Steam Momo',119.00,_binary '',18),(53,'admin','2025-08-09 13:44:43.107000','admin','2025-08-09 13:44:43.107000',_binary '\0','Fried Momo',139.00,_binary '',18),(54,'admin','2025-08-09 13:44:43.110000','admin','2025-08-09 13:44:43.110000',_binary '\0','Pan Fried Momo',169.00,_binary '',18),(55,'admin','2025-08-09 13:45:31.198000','admin','2025-08-09 13:45:31.198000',_binary '\0','Steam Momo',139.00,_binary '',19),(56,'admin','2025-08-09 13:45:31.202000','admin','2025-08-09 13:45:31.202000',_binary '\0','Fried Momo',159.00,_binary '',19),(57,'admin','2025-08-09 13:45:31.205000','admin','2025-08-09 13:45:31.205000',_binary '\0','Pan Fried Momo',189.00,_binary '',19),(58,'admin','2025-08-09 13:46:12.882000','admin','2025-08-09 13:46:12.882000',_binary '\0','Steam Momo',129.00,_binary '',20),(59,'admin','2025-08-09 13:46:12.886000','admin','2025-08-09 13:46:12.886000',_binary '\0','Fried Momo',149.00,_binary '',20),(60,'admin','2025-08-09 13:46:12.890000','admin','2025-08-09 13:46:12.890000',_binary '\0','Pan Fried Momo',179.00,_binary '',20),(61,'admin','2025-08-09 13:46:52.959000','admin','2025-08-09 13:46:52.959000',_binary '\0','Steam Momo',129.00,_binary '',21),(62,'admin','2025-08-09 13:46:52.963000','admin','2025-08-09 13:46:52.963000',_binary '\0','Fried Momo',149.00,_binary '',21),(63,'admin','2025-08-09 13:46:52.966000','admin','2025-08-09 13:46:52.966000',_binary '\0','Pan Fried Momo',179.00,_binary '',21),(64,'admin','2025-08-09 13:47:35.653000','admin','2025-08-09 13:47:35.653000',_binary '\0','Steam Momo',129.00,_binary '',22),(65,'admin','2025-08-09 13:47:35.657000','admin','2025-08-09 13:47:35.657000',_binary '\0','Fried Momo',149.00,_binary '',22),(66,'admin','2025-08-09 13:47:35.660000','admin','2025-08-09 13:47:35.660000',_binary '\0','Pan Fried Momo',179.00,_binary '',22),(67,'admin','2025-08-09 13:48:13.119000','admin','2025-08-09 13:48:13.119000',_binary '\0','Steam Momo',139.00,_binary '',23),(68,'admin','2025-08-09 13:48:13.122000','admin','2025-08-09 13:48:13.122000',_binary '\0','Fried Momo',159.00,_binary '',23),(69,'admin','2025-08-09 13:48:13.125000','admin','2025-08-09 13:48:13.125000',_binary '\0','Pan Fried Momo',189.00,_binary '',23),(70,'admin','2025-08-09 13:49:49.430000','admin','2025-08-09 13:49:49.430000',_binary '\0','Steam Momo',139.00,_binary '',24),(71,'admin','2025-08-09 13:49:49.434000','admin','2025-08-09 13:49:49.434000',_binary '\0','Fried Momo',159.00,_binary '',24),(72,'admin','2025-08-09 13:49:49.437000','admin','2025-08-09 13:49:49.437000',_binary '\0','Pan Fried Momo',189.00,_binary '',24),(73,'admin','2025-08-09 13:50:34.291000','admin','2025-08-09 13:50:34.291000',_binary '\0','Steam Momo',149.00,_binary '',25),(74,'admin','2025-08-09 13:50:34.294000','admin','2025-08-09 13:50:34.294000',_binary '\0','Fried Momo',169.00,_binary '',25),(75,'admin','2025-08-09 13:50:34.298000','admin','2025-08-09 13:50:34.298000',_binary '\0','Pan Fried Momo',199.00,_binary '',25),(76,'admin','2025-08-09 13:51:47.000000','admin','2025-08-09 13:51:47.000000',_binary '\0','Steam Momo',159.00,_binary '',26),(77,'admin','2025-08-09 13:51:47.005000','admin','2025-08-09 13:51:47.005000',_binary '\0','Fried Momo',179.00,_binary '',26),(78,'admin','2025-08-09 13:51:47.008000','admin','2025-08-09 13:51:47.008000',_binary '\0','Pan Fried Momo',209.00,_binary '',26),(79,'admin','2025-08-09 13:52:48.201000','admin','2025-08-09 13:52:48.201000',_binary '\0','Steam Momo',169.00,_binary '',27),(80,'admin','2025-08-09 13:52:48.204000','admin','2025-08-09 13:52:48.204000',_binary '\0','Fried Momo',189.00,_binary '',27),(81,'admin','2025-08-09 13:52:48.208000','admin','2025-08-09 13:52:48.208000',_binary '\0','Pan Fried Momo',219.00,_binary '',27),(82,'admin','2025-08-09 14:02:04.962000','admin','2025-08-09 14:02:04.962000',_binary '\0','Steam Momo',119.00,_binary '',28),(83,'admin','2025-08-09 14:02:04.966000','admin','2025-08-09 14:02:04.966000',_binary '\0','Fried Momo',139.00,_binary '',28),(84,'admin','2025-08-09 14:02:04.970000','admin','2025-08-09 14:02:04.970000',_binary '\0','Pan Fried Momo',169.00,_binary '',28),(85,'admin','2025-08-09 14:02:38.365000','admin','2025-08-09 14:02:38.365000',_binary '\0','Steam Momo',139.00,_binary '',29),(86,'admin','2025-08-09 14:02:38.368000','admin','2025-08-09 14:02:38.368000',_binary '\0','Fried Momo',159.00,_binary '',29),(87,'admin','2025-08-09 14:02:38.371000','admin','2025-08-09 14:02:38.371000',_binary '\0','Pan Fried Momo',189.00,_binary '',29),(88,'admin','2025-08-09 14:03:17.865000','admin','2025-08-09 14:03:17.865000',_binary '\0','Steam Momo',149.00,_binary '',30),(89,'admin','2025-08-09 14:03:17.868000','admin','2025-08-09 14:03:17.868000',_binary '\0','Fried Momo',169.00,_binary '',30),(90,'admin','2025-08-09 14:03:17.870000','admin','2025-08-09 14:03:17.870000',_binary '\0','Pan Fried Momo',199.00,_binary '',30),(91,'admin','2025-08-09 14:04:17.117000','admin','2025-08-09 14:04:17.117000',_binary '\0','Steam Momo',149.00,_binary '',31),(92,'admin','2025-08-09 14:04:17.120000','admin','2025-08-09 14:04:17.120000',_binary '\0','Fried Momo',169.00,_binary '',31),(93,'admin','2025-08-09 14:04:17.125000','admin','2025-08-09 14:04:17.125000',_binary '\0','Pan Fried Momo',199.00,_binary '',31),(94,'admin','2025-08-09 14:04:54.239000','admin','2025-08-09 14:04:54.239000',_binary '\0','Steam Momo',119.00,_binary '',32),(95,'admin','2025-08-09 14:04:54.243000','admin','2025-08-09 14:04:54.243000',_binary '\0','Fried Momo',139.00,_binary '',32),(96,'admin','2025-08-09 14:04:54.247000','admin','2025-08-09 14:04:54.247000',_binary '\0','Pan Fried Momo',169.00,_binary '',32),(97,'admin','2025-08-09 14:05:29.699000','admin','2025-08-09 14:05:29.699000',_binary '\0','Steam Momo',149.00,_binary '',33),(98,'admin','2025-08-09 14:05:29.703000','admin','2025-08-09 14:05:29.703000',_binary '\0','Fried Momo',169.00,_binary '',33),(99,'admin','2025-08-09 14:05:29.707000','admin','2025-08-09 14:05:29.707000',_binary '\0','Pan Fried Momo',199.00,_binary '',33),(100,'admin','2025-08-09 14:19:34.411000','admin','2025-08-09 14:19:34.411000',_binary '\0','Steam Momo',69.00,_binary '',58),(101,'admin','2025-08-09 14:19:34.421000','admin','2025-08-09 14:19:34.421000',_binary '\0','Fried Momo',89.00,_binary '',58),(102,'admin','2025-08-09 14:19:34.424000','admin','2025-08-09 14:19:34.424000',_binary '\0','Pan Fried Momo',109.00,_binary '',58),(103,'admin','2025-08-09 14:21:41.243000','admin','2025-08-09 14:21:41.243000',_binary '\0','Steam Momo',129.00,_binary '',57),(104,'admin','2025-08-09 14:21:41.246000','admin','2025-08-09 14:21:41.246000',_binary '\0','Fried Momo',149.00,_binary '',57),(105,'admin','2025-08-09 14:22:02.956000','admin','2025-08-09 14:22:02.956000',_binary '\0','Steam Momo',129.00,_binary '',59),(106,'admin','2025-08-09 14:22:02.959000','admin','2025-08-09 14:22:02.959000',_binary '\0','Fried Momo',149.00,_binary '',59),(107,'admin','2025-08-09 14:22:24.319000','admin','2025-08-09 14:22:24.319000',_binary '\0','Steam Momo',129.00,_binary '',60),(108,'admin','2025-08-09 14:22:24.322000','admin','2025-08-09 14:22:24.322000',_binary '\0','Fried Momo',149.00,_binary '',60),(109,'admin','2025-08-09 14:22:47.982000','admin','2025-08-09 14:22:47.982000',_binary '\0','Steam Momo',129.00,_binary '',61),(110,'admin','2025-08-09 14:22:47.985000','admin','2025-08-09 14:22:47.985000',_binary '\0','Fried Momo',149.00,_binary '',61),(111,'admin','2025-08-09 14:23:17.702000','admin','2025-08-09 14:23:17.702000',_binary '\0','Steam Momo',129.00,_binary '',62),(112,'admin','2025-08-09 14:23:17.705000','admin','2025-08-09 14:23:17.705000',_binary '\0','Fried Momo',149.00,_binary '',62);
/*!40000 ALTER TABLE `item_variations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_history`
--

DROP TABLE IF EXISTS `login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `device_info` varchar(255) DEFAULT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `login_time` datetime(6) DEFAULT NULL,
  `logout_time` datetime(6) DEFAULT NULL,
  `status` enum('FAILED','SUCCESS') DEFAULT NULL,
  `token` varchar(1024) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20v0mimmdegh2afs39uixlxpm` (`user_id`),
  CONSTRAINT `FK20v0mimmdegh2afs39uixlxpm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_history`
--

LOCK TABLES `login_history` WRITE;
/*!40000 ALTER TABLE `login_history` DISABLE KEYS */;
INSERT INTO `login_history` VALUES (1,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 11:30:54.213540',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDcxOTI1NCwiZXhwIjoxNzU0ODA1NjU0LCJBdXRob3JpdGllcyI6IkFETUlOIiwiaXNzdWVyIjoiYml0ZS1saW5lIiwiYXVkaWVuY2UiOiJiaXRlLWxpbmUgdXNlcnMiLCJ0eXBlIjoiYWNjZXNzIn0.t3NyIxh1uSNQfRNGrZRIeU9E8Bf-pcIGiR9FFlm_wTk',1),(2,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 13:38:03.489317',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJ2ZXIiLCJpYXQiOjE3NTQ3MjY4ODMsImV4cCI6MTc1NDgxMzI4MywiQXV0aG9yaXRpZXMiOiJTRVJWRVIiLCJpc3N1ZXIiOiJiaXRlLWxpbmUiLCJhdWRpZW5jZSI6ImJpdGUtbGluZSB1c2VycyIsInR5cGUiOiJhY2Nlc3MifQ.pjh8l29mAZffQH611ENnit30s9t7LSW536JhMiGhIfI',4),(3,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 13:38:42.448685',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDcyNjkyMiwiZXhwIjoxNzU0ODEzMzIyLCJBdXRob3JpdGllcyI6IkFETUlOIiwiaXNzdWVyIjoiYml0ZS1saW5lIiwiYXVkaWVuY2UiOiJiaXRlLWxpbmUgdXNlcnMiLCJ0eXBlIjoiYWNjZXNzIn0.hvMv-g5tBy6XzVPArzB-iaw5HgofiP8jzosaA2t4Yho',1),(4,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 14:05:35.478568',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJ2ZXIiLCJpYXQiOjE3NTQ3Mjg1MzUsImV4cCI6MTc1NDgxNDkzNSwiQXV0aG9yaXRpZXMiOiJTRVJWRVIiLCJpc3N1ZXIiOiJiaXRlLWxpbmUiLCJhdWRpZW5jZSI6ImJpdGUtbGluZSB1c2VycyIsInR5cGUiOiJhY2Nlc3MifQ.XnTVU9LyO5HekP4CfYmt53Twew-GFr1JG8Mlwrl_YzM',4),(5,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 14:06:07.117227',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDcyODU2NywiZXhwIjoxNzU0ODE0OTY3LCJBdXRob3JpdGllcyI6IkFETUlOIiwiaXNzdWVyIjoiYml0ZS1saW5lIiwiYXVkaWVuY2UiOiJiaXRlLWxpbmUgdXNlcnMiLCJ0eXBlIjoiYWNjZXNzIn0.3kg8vpywsNcwMNTUvGokgF2vl5IUaBpNk0I1v9STByo',1),(6,'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36','0:0:0:0:0:0:0:1',_binary '','2025-08-09 14:51:22.444990',NULL,'SUCCESS','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJ2ZXIiLCJpYXQiOjE3NTQ3MzEyODIsImV4cCI6MTc1NDgxNzY4MiwiQXV0aG9yaXRpZXMiOiJTRVJWRVIiLCJpc3N1ZXIiOiJiaXRlLWxpbmUiLCJhdWRpZW5jZSI6ImJpdGUtbGluZSB1c2VycyIsInR5cGUiOiJhY2Nlc3MifQ.1PYQjoDfo0kOw6JWnQTPw3NV-nut_17ALCibbHNVyl4',4);
/*!40000 ALTER TABLE `login_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item_combo_items`
--

DROP TABLE IF EXISTS `order_item_combo_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item_combo_items` (
  `order_item_id` int NOT NULL,
  `included_item_id` int NOT NULL,
  KEY `FKqpkv9cbgj535oo4hfepp76e78` (`included_item_id`),
  KEY `FK5kj37sw62u4vlmb6tbs07vgbg` (`order_item_id`),
  CONSTRAINT `FK5kj37sw62u4vlmb6tbs07vgbg` FOREIGN KEY (`order_item_id`) REFERENCES `pos_order_item` (`id`),
  CONSTRAINT `FKqpkv9cbgj535oo4hfepp76e78` FOREIGN KEY (`included_item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_combo_items`
--

LOCK TABLES `order_item_combo_items` WRITE;
/*!40000 ALTER TABLE `order_item_combo_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item_combo_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos_order`
--

DROP TABLE IF EXISTS `pos_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pos_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `container_charge` decimal(38,2) DEFAULT NULL,
  `delivery_charge` decimal(38,2) DEFAULT NULL,
  `discount_amount` decimal(38,2) DEFAULT NULL,
  `discount_percent` decimal(38,2) DEFAULT NULL,
  `final_total` decimal(38,2) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_time` time(6) DEFAULT NULL,
  `order_type` enum('DELIVERY','DINE_IN','PICKUP') NOT NULL,
  `payment_method` enum('CARD','CASH','OTHER','UPI') DEFAULT NULL,
  `round_off` decimal(38,2) DEFAULT NULL,
  `status` enum('CANCELLED','COMPLETED','HELD') NOT NULL,
  `subtotal` decimal(38,2) DEFAULT NULL,
  `table_number` varchar(255) DEFAULT NULL,
  `tax_amount` decimal(38,2) DEFAULT NULL,
  `tax_percent` decimal(38,2) DEFAULT NULL,
  `total_quantity` decimal(38,2) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26a15hteud58smq9l88mowndx` (`customer_id`),
  CONSTRAINT `FK26a15hteud58smq9l88mowndx` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_order`
--

LOCK TABLES `pos_order` WRITE;
/*!40000 ALTER TABLE `pos_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos_order_item`
--

DROP TABLE IF EXISTS `pos_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pos_order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  `item_id` int NOT NULL,
  `order_id` int NOT NULL,
  `variation_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgb677t9ds32ds7y2ehdtxv6ee` (`item_id`),
  KEY `FKd8qo7wcw5lxu67yshpersa8gl` (`order_id`),
  KEY `FKqi3t2wd2sku7cl5pmtsqfoqnt` (`variation_id`),
  CONSTRAINT `FKd8qo7wcw5lxu67yshpersa8gl` FOREIGN KEY (`order_id`) REFERENCES `pos_order` (`id`),
  CONSTRAINT `FKgb677t9ds32ds7y2ehdtxv6ee` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKqi3t2wd2sku7cl5pmtsqfoqnt` FOREIGN KEY (`variation_id`) REFERENCES `item_variations` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_order_item`
--

LOCK TABLES `pos_order_item` WRITE;
/*!40000 ALTER TABLE `pos_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pos_order_item_addons`
--

DROP TABLE IF EXISTS `pos_order_item_addons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pos_order_item_addons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` bit(1) NOT NULL,
  `item_addon_id` int DEFAULT NULL,
  `item_variation_id` int DEFAULT NULL,
  `order_item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg99q85c9y7mo7bj4twf87xyhj` (`item_addon_id`),
  KEY `FKhfq6pynxvpemp17i2o7oxth39` (`item_variation_id`),
  KEY `FKwo19r0ulvg4ad35swxab8qcp` (`order_item_id`),
  CONSTRAINT `FKg99q85c9y7mo7bj4twf87xyhj` FOREIGN KEY (`item_addon_id`) REFERENCES `item_addons` (`id`),
  CONSTRAINT `FKhfq6pynxvpemp17i2o7oxth39` FOREIGN KEY (`item_variation_id`) REFERENCES `item_variations` (`id`),
  CONSTRAINT `FKwo19r0ulvg4ad35swxab8qcp` FOREIGN KEY (`order_item_id`) REFERENCES `pos_order_item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pos_order_item_addons`
--

LOCK TABLES `pos_order_item_addons` WRITE;
/*!40000 ALTER TABLE `pos_order_item_addons` DISABLE KEYS */;
/*!40000 ALTER TABLE `pos_order_item_addons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `cuisine` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'SYSTEM','2025-08-09 11:29:25.313000','SYSTEM','2025-08-09 11:29:25.313000','70/1, Sastri Narendranath Ganguly Rd, near Betore Heights, Betore, Santragachi, Ichapur, Howrah, West Bengal 711104',NULL,'9876543210','Multi-Cuisine','A place for delicious food',NULL,'Momo Chitte - Ramrajatala',_binary '');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `quantity` decimal(38,2) NOT NULL,
  `status` bit(1) NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf3gqo6e3aged60kygtbmtsd8s` (`item_id`),
  CONSTRAINT `FKf3gqo6e3aged60kygtbmtsd8s` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'admin','2025-08-09 13:31:01.832000','admin','2025-08-09 13:31:01.832000',0.00,_binary '',1),(2,'admin','2025-08-09 13:32:03.196000','admin','2025-08-09 13:32:03.196000',0.00,_binary '',2),(3,'admin','2025-08-09 13:32:52.409000','admin','2025-08-09 13:32:52.409000',0.00,_binary '',3),(4,'admin','2025-08-09 13:33:35.157000','admin','2025-08-09 13:33:35.157000',0.00,_binary '',4),(5,'admin','2025-08-09 13:34:09.811000','admin','2025-08-09 13:34:09.811000',0.00,_binary '',5),(6,'admin','2025-08-09 13:34:49.680000','admin','2025-08-09 13:34:49.680000',0.00,_binary '',6),(7,'admin','2025-08-09 13:35:24.782000','admin','2025-08-09 13:35:24.782000',0.00,_binary '',7),(8,'admin','2025-08-09 13:35:55.946000','admin','2025-08-09 13:35:55.946000',0.00,_binary '',8),(9,'admin','2025-08-09 13:36:26.473000','admin','2025-08-09 13:36:26.473000',0.00,_binary '',9),(10,'admin','2025-08-09 13:36:57.654000','admin','2025-08-09 13:36:57.654000',0.00,_binary '',10),(11,'admin','2025-08-09 13:37:26.551000','admin','2025-08-09 13:37:26.551000',0.00,_binary '',11),(12,'admin','2025-08-09 13:37:54.030000','admin','2025-08-09 13:37:54.030000',0.00,_binary '',12),(13,'admin','2025-08-09 13:39:23.368000','admin','2025-08-09 13:39:23.368000',0.00,_binary '',13),(14,'admin','2025-08-09 13:40:03.576000','admin','2025-08-09 13:40:03.576000',0.00,_binary '',14),(15,'admin','2025-08-09 13:40:40.253000','admin','2025-08-09 13:40:40.253000',0.00,_binary '',15),(16,'admin','2025-08-09 13:42:06.881000','admin','2025-08-09 13:42:06.881000',0.00,_binary '',16),(17,'admin','2025-08-09 13:43:40.636000','admin','2025-08-09 13:43:40.636000',0.00,_binary '',17),(18,'admin','2025-08-09 13:44:43.116000','admin','2025-08-09 13:44:43.116000',0.00,_binary '',18),(19,'admin','2025-08-09 13:45:31.212000','admin','2025-08-09 13:45:31.212000',0.00,_binary '',19),(20,'admin','2025-08-09 13:46:12.898000','admin','2025-08-09 13:46:12.898000',0.00,_binary '',20),(21,'admin','2025-08-09 13:46:52.975000','admin','2025-08-09 13:46:52.975000',0.00,_binary '',21),(22,'admin','2025-08-09 13:47:35.669000','admin','2025-08-09 13:47:35.669000',0.00,_binary '',22),(23,'admin','2025-08-09 13:48:13.132000','admin','2025-08-09 13:48:13.132000',0.00,_binary '',23),(24,'admin','2025-08-09 13:49:49.445000','admin','2025-08-09 13:49:49.445000',0.00,_binary '',24),(25,'admin','2025-08-09 13:50:34.306000','admin','2025-08-09 13:50:34.306000',0.00,_binary '',25),(26,'admin','2025-08-09 13:51:47.016000','admin','2025-08-09 13:51:47.016000',0.00,_binary '',26),(27,'admin','2025-08-09 13:52:48.216000','admin','2025-08-09 13:52:48.216000',0.00,_binary '',27),(28,'admin','2025-08-09 14:02:04.977000','admin','2025-08-09 14:02:04.977000',0.00,_binary '',28),(29,'admin','2025-08-09 14:02:38.377000','admin','2025-08-09 14:02:38.377000',0.00,_binary '',29),(30,'admin','2025-08-09 14:03:17.877000','admin','2025-08-09 14:03:17.877000',0.00,_binary '',30),(31,'admin','2025-08-09 14:04:17.133000','admin','2025-08-09 14:04:17.133000',0.00,_binary '',31),(32,'admin','2025-08-09 14:04:54.255000','admin','2025-08-09 14:04:54.255000',0.00,_binary '',32),(33,'admin','2025-08-09 14:05:29.715000','admin','2025-08-09 14:05:29.715000',0.00,_binary '',33),(34,'admin','2025-08-09 14:09:02.341000','admin','2025-08-09 14:09:02.341000',0.00,_binary '',34),(35,'admin','2025-08-09 14:09:22.562000','admin','2025-08-09 14:09:22.562000',0.00,_binary '',35),(36,'admin','2025-08-09 14:09:40.838000','admin','2025-08-09 14:09:40.838000',0.00,_binary '',36),(37,'admin','2025-08-09 14:10:17.461000','admin','2025-08-09 14:10:17.461000',0.00,_binary '',37),(38,'admin','2025-08-09 14:10:36.072000','admin','2025-08-09 14:10:36.072000',0.00,_binary '',38),(39,'admin','2025-08-09 14:11:56.334000','admin','2025-08-09 14:11:56.334000',0.00,_binary '',39),(40,'admin','2025-08-09 14:12:21.903000','admin','2025-08-09 14:12:21.903000',0.00,_binary '',40),(41,'admin','2025-08-09 14:12:35.725000','admin','2025-08-09 14:12:35.725000',0.00,_binary '',41),(42,'admin','2025-08-09 14:12:50.444000','admin','2025-08-09 14:12:50.444000',0.00,_binary '',42),(43,'admin','2025-08-09 14:13:35.683000','admin','2025-08-09 14:13:35.683000',0.00,_binary '',43),(44,'admin','2025-08-09 14:13:54.992000','admin','2025-08-09 14:13:54.992000',0.00,_binary '',44),(45,'admin','2025-08-09 14:14:08.000000','admin','2025-08-09 14:14:08.000000',0.00,_binary '',45),(46,'admin','2025-08-09 14:14:20.506000','admin','2025-08-09 14:14:20.506000',0.00,_binary '',46),(47,'admin','2025-08-09 14:14:40.732000','admin','2025-08-09 14:14:40.732000',0.00,_binary '',47),(48,'admin','2025-08-09 14:15:00.043000','admin','2025-08-09 14:15:00.043000',0.00,_binary '',48),(49,'admin','2025-08-09 14:15:16.901000','admin','2025-08-09 14:15:16.901000',0.00,_binary '',49),(50,'admin','2025-08-09 14:15:33.818000','admin','2025-08-09 14:15:33.818000',0.00,_binary '',50),(51,'admin','2025-08-09 14:15:47.994000','admin','2025-08-09 14:15:47.994000',0.00,_binary '',51),(52,'admin','2025-08-09 14:16:05.719000','admin','2025-08-09 14:16:05.719000',0.00,_binary '',52),(53,'admin','2025-08-09 14:16:24.875000','admin','2025-08-09 14:16:24.875000',0.00,_binary '',53),(54,'admin','2025-08-09 14:16:36.493000','admin','2025-08-09 14:16:36.493000',0.00,_binary '',54),(55,'admin','2025-08-09 14:16:47.254000','admin','2025-08-09 14:16:47.254000',0.00,_binary '',55),(56,'admin','2025-08-09 14:17:01.262000','admin','2025-08-09 14:17:01.262000',0.00,_binary '',56),(57,'admin','2025-08-09 14:17:26.862000','admin','2025-08-09 14:17:26.862000',0.00,_binary '',57),(58,'admin','2025-08-09 14:17:41.098000','admin','2025-08-09 14:17:41.098000',0.00,_binary '',58),(59,'admin','2025-08-09 14:22:02.967000','admin','2025-08-09 14:22:02.967000',0.00,_binary '',59),(60,'admin','2025-08-09 14:22:24.327000','admin','2025-08-09 14:22:24.327000',0.00,_binary '',60),(61,'admin','2025-08-09 14:22:47.992000','admin','2025-08-09 14:22:47.992000',0.00,_binary '',61),(62,'admin','2025-08-09 14:23:17.713000','admin','2025-08-09 14:23:17.713000',0.00,_binary '',62),(63,'admin','2025-08-09 14:23:36.479000','admin','2025-08-09 14:23:36.479000',0.00,_binary '',63),(64,'admin','2025-08-09 14:23:47.949000','admin','2025-08-09 14:23:47.949000',0.00,_binary '',64),(65,'admin','2025-08-09 14:24:00.519000','admin','2025-08-09 14:24:00.519000',0.00,_binary '',65),(66,'admin','2025-08-09 14:24:11.086000','admin','2025-08-09 14:24:11.086000',0.00,_binary '',66),(67,'admin','2025-08-09 14:24:21.366000','admin','2025-08-09 14:24:21.366000',0.00,_binary '',67),(68,'admin','2025-08-09 14:25:26.565000','admin','2025-08-09 14:25:26.565000',0.00,_binary '',68),(69,'admin','2025-08-09 14:25:57.489000','admin','2025-08-09 14:25:57.489000',0.00,_binary '',69),(70,'admin','2025-08-09 14:26:14.145000','admin','2025-08-09 14:26:14.145000',0.00,_binary '',70),(71,'admin','2025-08-09 14:26:25.922000','admin','2025-08-09 14:26:25.922000',0.00,_binary '',71),(72,'admin','2025-08-09 14:26:40.033000','admin','2025-08-09 14:26:40.033000',0.00,_binary '',72),(73,'admin','2025-08-09 14:26:49.685000','admin','2025-08-09 14:26:49.685000',0.00,_binary '',73),(74,'admin','2025-08-09 14:27:03.628000','admin','2025-08-09 14:27:03.628000',0.00,_binary '',74),(75,'admin','2025-08-09 14:27:14.149000','admin','2025-08-09 14:27:14.149000',0.00,_binary '',75),(76,'admin','2025-08-09 14:27:25.796000','admin','2025-08-09 14:27:25.796000',0.00,_binary '',76),(77,'admin','2025-08-09 14:27:35.122000','admin','2025-08-09 14:27:35.122000',0.00,_binary '',77),(78,'admin','2025-08-09 14:32:02.369000','admin','2025-08-09 14:32:02.369000',0.00,_binary '',78),(79,'admin','2025-08-09 14:32:22.884000','admin','2025-08-09 14:32:22.884000',0.00,_binary '',79),(80,'admin','2025-08-09 14:32:42.406000','admin','2025-08-09 14:32:42.406000',0.00,_binary '',80),(81,'admin','2025-08-09 14:33:25.471000','admin','2025-08-09 14:33:25.471000',0.00,_binary '',81),(82,'admin','2025-08-09 14:33:40.656000','admin','2025-08-09 14:33:40.656000',0.00,_binary '',82),(83,'admin','2025-08-09 14:34:02.619000','admin','2025-08-09 14:34:02.619000',0.00,_binary '',83),(84,'admin','2025-08-09 14:34:16.988000','admin','2025-08-09 14:34:16.988000',0.00,_binary '',84),(85,'admin','2025-08-09 14:34:31.946000','admin','2025-08-09 14:34:31.946000',0.00,_binary '',85),(86,'admin','2025-08-09 14:34:52.128000','admin','2025-08-09 14:34:52.128000',0.00,_binary '',86),(87,'admin','2025-08-09 14:35:04.652000','admin','2025-08-09 14:35:04.652000',0.00,_binary '',87),(88,'admin','2025-08-09 14:35:15.350000','admin','2025-08-09 14:35:15.350000',0.00,_binary '',88),(89,'admin','2025-08-09 14:35:24.186000','admin','2025-08-09 14:35:24.186000',0.00,_binary '',89),(90,'admin','2025-08-09 14:35:34.296000','admin','2025-08-09 14:35:34.296000',0.00,_binary '',90),(91,'admin','2025-08-09 14:35:46.751000','admin','2025-08-09 14:35:46.751000',0.00,_binary '',91),(92,'admin','2025-08-09 14:36:15.268000','admin','2025-08-09 14:36:15.268000',0.00,_binary '',92),(93,'admin','2025-08-09 14:36:25.960000','admin','2025-08-09 14:36:25.960000',0.00,_binary '',93),(94,'admin','2025-08-09 14:36:37.865000','admin','2025-08-09 14:36:37.865000',0.00,_binary '',94),(95,'admin','2025-08-09 14:36:55.755000','admin','2025-08-09 14:36:55.755000',0.00,_binary '',95),(96,'admin','2025-08-09 14:38:12.846000','admin','2025-08-09 14:38:12.846000',0.00,_binary '',96),(97,'admin','2025-08-09 14:38:21.958000','admin','2025-08-09 14:38:21.958000',0.00,_binary '',97),(98,'admin','2025-08-09 14:38:50.180000','admin','2025-08-09 14:38:50.180000',0.00,_binary '',98),(99,'admin','2025-08-09 14:39:03.117000','admin','2025-08-09 14:39:03.117000',0.00,_binary '',99),(100,'admin','2025-08-09 14:39:15.087000','admin','2025-08-09 14:39:15.087000',0.00,_binary '',100),(101,'admin','2025-08-09 14:39:27.248000','admin','2025-08-09 14:39:27.248000',0.00,_binary '',101),(102,'admin','2025-08-09 14:39:36.792000','admin','2025-08-09 14:39:36.792000',0.00,_binary '',102),(103,'admin','2025-08-09 14:39:52.890000','admin','2025-08-09 14:39:52.890000',0.00,_binary '',103),(104,'admin','2025-08-09 14:40:02.517000','admin','2025-08-09 14:40:02.517000',0.00,_binary '',104),(105,'admin','2025-08-09 14:40:14.375000','admin','2025-08-09 14:40:14.375000',0.00,_binary '',105),(106,'admin','2025-08-09 14:40:28.089000','admin','2025-08-09 14:40:28.089000',0.00,_binary '',106),(107,'admin','2025-08-09 14:40:37.999000','admin','2025-08-09 14:40:37.999000',0.00,_binary '',107),(108,'admin','2025-08-09 14:40:48.558000','admin','2025-08-09 14:40:48.558000',0.00,_binary '',108),(109,'admin','2025-08-09 14:40:57.799000','admin','2025-08-09 14:40:57.799000',0.00,_binary '',109),(110,'admin','2025-08-09 14:41:08.566000','admin','2025-08-09 14:41:08.566000',0.00,_binary '',110),(111,'admin','2025-08-09 14:41:17.913000','admin','2025-08-09 14:41:17.913000',0.00,_binary '',111),(112,'admin','2025-08-09 14:41:27.468000','admin','2025-08-09 14:41:27.468000',0.00,_binary '',112),(113,'admin','2025-08-09 14:41:38.651000','admin','2025-08-09 14:41:38.651000',0.00,_binary '',113),(114,'admin','2025-08-09 14:41:48.271000','admin','2025-08-09 14:41:48.271000',0.00,_binary '',114),(115,'admin','2025-08-09 14:42:03.496000','admin','2025-08-09 14:42:03.496000',0.00,_binary '',115),(116,'admin','2025-08-09 14:42:13.607000','admin','2025-08-09 14:42:13.607000',0.00,_binary '',116),(117,'admin','2025-08-09 14:42:24.761000','admin','2025-08-09 14:42:24.761000',0.00,_binary '',117),(118,'admin','2025-08-09 14:42:35.329000','admin','2025-08-09 14:42:35.329000',0.00,_binary '',118),(119,'admin','2025-08-09 14:42:44.221000','admin','2025-08-09 14:42:44.221000',0.00,_binary '',119),(120,'admin','2025-08-09 14:42:58.110000','admin','2025-08-09 14:42:58.110000',0.00,_binary '',120),(121,'admin','2025-08-09 14:43:20.350000','admin','2025-08-09 14:43:20.350000',0.00,_binary '',121),(122,'admin','2025-08-09 14:43:30.752000','admin','2025-08-09 14:43:30.752000',0.00,_binary '',122),(123,'admin','2025-08-09 14:43:40.609000','admin','2025-08-09 14:43:40.609000',0.00,_binary '',123),(124,'admin','2025-08-09 14:43:51.519000','admin','2025-08-09 14:43:51.519000',0.00,_binary '',124),(125,'admin','2025-08-09 14:44:04.261000','admin','2025-08-09 14:44:04.261000',0.00,_binary '',125),(126,'admin','2025-08-09 14:44:18.923000','admin','2025-08-09 14:44:18.923000',0.00,_binary '',126),(127,'admin','2025-08-09 14:44:31.604000','admin','2025-08-09 14:44:31.604000',0.00,_binary '',127),(128,'admin','2025-08-09 14:44:40.767000','admin','2025-08-09 14:44:40.767000',0.00,_binary '',128),(129,'admin','2025-08-09 14:44:51.134000','admin','2025-08-09 14:44:51.134000',0.00,_binary '',129),(130,'admin','2025-08-09 14:44:59.614000','admin','2025-08-09 14:44:59.614000',0.00,_binary '',130),(131,'admin','2025-08-09 14:45:08.801000','admin','2025-08-09 14:45:08.801000',0.00,_binary '',131),(132,'admin','2025-08-09 14:48:19.507000','admin','2025-08-09 14:48:19.507000',0.00,_binary '',132),(133,'admin','2025-08-09 14:49:16.018000','admin','2025-08-09 14:49:16.018000',0.00,_binary '',133),(134,'admin','2025-08-09 14:50:22.966000','admin','2025-08-09 14:50:22.966000',0.00,_binary '',134);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_verification`
--

DROP TABLE IF EXISTS `user_verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_verification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK7qvcou7abf0yo2wkqwsb14lgs` (`token`),
  UNIQUE KEY `UKha4odgfncscp0wbex975lioc5` (`user_id`),
  CONSTRAINT `FKtj9xd3vagu4sh7xk3ep9c2wx6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_verification`
--

LOCK TABLES `user_verification` WRITE;
/*!40000 ALTER TABLE `user_verification` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_verification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `employee_id` varchar(100) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `refresh_token` varchar(1024) DEFAULT NULL,
  `role` enum('ADMIN','CHEF','CUSTOMER','MANAGER','SERVER') DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `verified` bit(1) NOT NULL,
  `restaurant_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK63cf888pmqtt5tipcne79xsbm` (`mobile`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKcn7awwo916vg0my6knhmkn03j` (`restaurant_id`),
  CONSTRAINT `FKcn7awwo916vg0my6knhmkn03j` FOREIGN KEY (`restaurant_id`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'SYSTEM','2025-08-09 11:29:25.451000','anonymousUser','2025-08-09 14:06:07.095000',_binary '',_binary '',_binary '',_binary '\0','admin2663@example.com',NULL,_binary '',NULL,'9502758397','Admin','$2a$10$YI.Iwdahw1RjCwTXOb5BM.xMUBXElm2ok1z/10lGTr34gQOzq/xbW','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1NDcyODU2NywiZXhwIjoxNzU1MzMzMzY3LCJ0eXBlIjoicmVmcmVzaCJ9.zaRQlwTRnJFFnvThpCDN4naJQiNbOpA3d66pouBibzk','ADMIN','admin',_binary '',1),(2,'SYSTEM','2025-08-09 11:29:25.563000','SYSTEM','2025-08-09 11:29:25.563000',_binary '',_binary '',_binary '',_binary '\0','manager5151@example.com',NULL,_binary '',NULL,'9962421841','Manager','$2a$10$UGg2ObM8WlDT3R0Rn18vyOAbHpjbVlOGe1Bd5ID9hCwsmiAtMmKQ.',NULL,'MANAGER','manager',_binary '',1),(3,'SYSTEM','2025-08-09 11:29:25.648000','SYSTEM','2025-08-09 11:29:25.648000',_binary '',_binary '',_binary '',_binary '\0','chef3723@example.com',NULL,_binary '',NULL,'9872147136','Chef','$2a$10$nxao.ZYdWUW1v5wynzEJK.z2PFh1duUnM/q77PGKE952092m09dyi',NULL,'CHEF','chef',_binary '',1),(4,'SYSTEM','2025-08-09 11:29:25.733000','anonymousUser','2025-08-09 14:51:22.419000',_binary '',_binary '',_binary '',_binary '\0','server4974@example.com',NULL,_binary '',NULL,'9977942805','Server','$2a$10$/KxTSlAjn3guVGfk9yoNAOwQz6vb.K7MK2yVvk50qPn36TiH5QHO6','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJ2ZXIiLCJpYXQiOjE3NTQ3MzEyODIsImV4cCI6MTc1NTMzNjA4MiwidHlwZSI6InJlZnJlc2gifQ.c2vGo57tZe0oyeL05zsWTP7VbCbsuhSBmuqZhcf1rew','SERVER','server',_binary '',1),(5,'SYSTEM','2025-08-09 11:29:25.818000','SYSTEM','2025-08-09 11:29:25.818000',_binary '',_binary '',_binary '',_binary '\0','customer9591@example.com',NULL,_binary '',NULL,'9105807435','Customer','$2a$10$fwtNdvcHuBXQmFdRUqeRLOK18qJd4lO7t77D7wGIcdCpOTyGPNBhe',NULL,'CUSTOMER','customer',_binary '',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variation`
--

DROP TABLE IF EXISTS `variation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variation`
--

LOCK TABLES `variation` WRITE;
/*!40000 ALTER TABLE `variation` DISABLE KEYS */;
INSERT INTO `variation` VALUES (1,'admin','2025-08-09 13:28:34.780000','admin','2025-08-09 13:28:34.780000','Steam Momo','Steam Momo',_binary ''),(2,'admin','2025-08-09 13:28:42.309000','admin','2025-08-09 13:28:42.309000','Fried Momo','Fried Momo',_binary ''),(3,'admin','2025-08-09 13:29:02.352000','admin','2025-08-09 13:29:02.352000','Pan Fried Momo','Pan Fried Momo',_binary ''),(4,'admin','2025-08-09 14:25:00.790000','admin','2025-08-09 14:25:00.790000','','VEG',_binary ''),(5,'admin','2025-08-09 14:25:09.494000','admin','2025-08-09 14:25:09.494000','','NON VEG',_binary '');
/*!40000 ALTER TABLE `variation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'resturent'
--

--
-- Dumping routines for database 'resturent'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-09 14:53:31

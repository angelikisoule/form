CREATE DATABASE  IF NOT EXISTS `mobile_ladylike` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mobile_ladylike`;
-- MySQL dump 10.13  Distrib 5.5.43, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mobile_ladylike
-- ------------------------------------------------------
-- Server version	5.5.43-0ubuntu0.12.04.1

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
-- Temporary table structure for view `advertorial`
--

DROP TABLE IF EXISTS `advertorial`;
/*!50001 DROP VIEW IF EXISTS `advertorial`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `advertorial` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `supertitle` tinyint NOT NULL,
  `leadText` tinyint NOT NULL,
  `teaserTitle` tinyint NOT NULL,
  `teaserSupertitle` tinyint NOT NULL,
  `teaserLeadText` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `relatedPictures` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `discriminator` varchar(11) NOT NULL,
  `eceArticleId` varchar(45) NOT NULL,
  `articleType` int(11) NOT NULL,
  `articleState` int(11) NOT NULL,
  `title` longtext NOT NULL,
  `supertitle` longtext,
  `leadText` longtext,
  `teaserTitle` longtext,
  `teaserSupertitle` longtext,
  `teaserLeadText` longtext,
  `alternate` longtext,
  `body` longtext,
  `embeddedCode` longtext,
  `credits` varchar(255) DEFAULT NULL,
  `caption` varchar(255) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `link` longtext,
  `videoType` int(11) DEFAULT NULL,
  `videoId` varchar(45) DEFAULT NULL,
  `datePublished` datetime DEFAULT NULL,
  `dateLastUpdated` datetime DEFAULT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Article_EceArticleId` (`eceArticleId`),
  KEY `INDEX_eceArticleId` (`eceArticleId`),
  KEY `INDEX_Multiple` (`datePublished`,`articleState`,`articleType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleAuthor`
--

DROP TABLE IF EXISTS `articleAuthor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleAuthor` (
  `articleId` bigint(20) NOT NULL,
  `authorId` bigint(20) NOT NULL,
  KEY `FK_Authors_Article_idx` (`articleId`),
  KEY `FK_Authors_Author_idx` (`authorId`),
  CONSTRAINT `FK_Authors_Article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Authors_Author` FOREIGN KEY (`authorId`) REFERENCES `author` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleAuthor`
--

LOCK TABLES `articleAuthor` WRITE;
/*!40000 ALTER TABLE `articleAuthor` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleAuthor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleCategory`
--

DROP TABLE IF EXISTS `articleCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleCategory` (
  `articleId` bigint(20) NOT NULL,
  `categoryId` bigint(20) NOT NULL,
  `categoryOrder` int(11) NOT NULL,
  KEY `FK_Categories_Article_idx` (`articleId`),
  KEY `FK_Categories_Category_idx` (`categoryId`),
  CONSTRAINT `FK_Categories_Article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Categories_Category` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleCategory`
--

LOCK TABLES `articleCategory` WRITE;
/*!40000 ALTER TABLE `articleCategory` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleRelatedArticle`
--

DROP TABLE IF EXISTS `articleRelatedArticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleRelatedArticle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `relatedArticleId` bigint(20) NOT NULL,
  `enclosureComment` int(11) DEFAULT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Article_Related_Enclosure` (`relatedArticleId`,`articleId`,`enclosureComment`),
  KEY `FK_Related_Article_idx` (`articleId`),
  KEY `FK_Related_Related_Article_idx` (`relatedArticleId`),
  CONSTRAINT `FK_Related_Article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Related_Related_Article` FOREIGN KEY (`relatedArticleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleRelatedArticle`
--

LOCK TABLES `articleRelatedArticle` WRITE;
/*!40000 ALTER TABLE `articleRelatedArticle` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleRelatedArticle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articleTag`
--

DROP TABLE IF EXISTS `articleTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articleTag` (
  `articleId` bigint(20) NOT NULL,
  `tagId` bigint(20) NOT NULL,
  KEY `FK_Tag_Article_idx` (`articleId`),
  KEY `FK_Tag_Tag_idx` (`tagId`),
  CONSTRAINT `FK_Tag_Article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Tag_Tag` FOREIGN KEY (`tagId`) REFERENCES `tag` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articleTag`
--

LOCK TABLES `articleTag` WRITE;
/*!40000 ALTER TABLE `articleTag` DISABLE KEYS */;
/*!40000 ALTER TABLE `articleTag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sectionUniqueName` varchar(45) NOT NULL,
  `groupName` varchar(45) DEFAULT NULL,
  `publication` bigint(20) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_SectionUniqueName_GroupName_Publication` (`sectionUniqueName`,`groupName`,`publication`),
  KEY `FK_Publication_idx` (`publication`),
  CONSTRAINT `FK_Publication` FOREIGN KEY (`publication`) REFERENCES `publication` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES 
(1,'ladylike','ece_frontpage',NULL,1,'2015-06-02 14:25:38'),
(2,'Άρθρα','articles',NULL,1,'2015-06-02 14:25:38'),
(3,'Newsletter','newsletter',NULL,1,'2015-06-02 15:25:38'),
(4,'Videos','videos',NULL,1,'2015-06-02 15:26:28'),
(5,'Εβδομαδιαία Περιοδικά','weekly_mag',NULL,1,'2015-05-28 09:47:51'),
(6,'Τηλεοπτικά περιοδικά','tv_magazines',NULL,1,'2015-05-28 09:48:51'),
(7,'Free Press','weekly_press',NULL,1,'2015-05-28 09:49:51'),
(8,'Ένθετα Εφημερίδων','entheta_efimeridon',NULL,1,'2015-05-28 09:50:51'),
(9,'Γυναικεία Περιοδικά','women_magazines',NULL,1,'2015-05-28 09:51:51'),
(10,'Fitness και Ευεξία','fitness_kai_eveksia',NULL,1,'2015-05-28 09:52:51'),
(11,'Κυριακάτικες','sunday_papers',NULL,1,'2015-05-28 09:53:51');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feed` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `params` varchar(255) NOT NULL,
  `profile` varchar(45) DEFAULT NULL,
  `category` bigint(20) DEFAULT NULL,
  `feedFlag` int(11) NOT NULL,
  `feedStatus` int(11) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Url_Params` (`url`,`params`),
  KEY `FK_Feed_Category_idx` (`category`),
  CONSTRAINT `FK_Feed_Category` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES 
(1,'http://content.24media.gr/develop/pool/','?section=6327&profile=bGFkeWxpa2UyNE1A&types=news,advertorial,photostory&view=generic%2Fv2%2Fpool-atom','bGFkeWxpa2UyNE1A',3,2,0,'2015-06-02 15:28:03'),
(2,'http://content.24media.gr/develop/articles/','?sections=5688&profile=bGFkeWxpa2UyNE1A&types=news,advertorial,photostory&view=generic%2Fv2%2Flatest-atom','bGFkeWxpa2UyNE1A',1,1,0,'2015-06-02 14:28:03'),
(3,'http://content.24media.gr/develop/pool/','?section=5688&profile=bGFkeWxpa2UyNE1A&types=news,advertorial,photostory&view=generic%2Fv2%2Fpool-atom','bGFkeWxpa2UyNE1A',1,2,0,'2015-05-28 09:54:18'),
(4,'http://content.24media.gr/develop/pool/','?section=5885&profile=bGFkeWxpa2UyNE1A&areas=main&groups=main1,main2&types=news,advertorial,photostory&view=generic%2Fv2%2Fpool-atom','bGFkeWxpa2UyNE1A',2,2,0,'2015-06-01 09:52:37'),
(5,'http://content.24media.gr/develop/articles/','?sections=6827&profile=bGFkeWxpa2UyNE1A&types=multipletypevideo&view=generic%2Fv2%2Flatest-atom','bGFkeWxpa2UyNE1A',4,1,0,'2015-06-02 14:27:03'),
(6,'http://news247.gr/newspapers/weekly_mag/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,5,0,0,'2015-06-01 09:46:49'),
(7,'http://news247.gr/newspapers/tv_magazines/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,6,0,0,'2015-06-01 09:46:49'),
(8,'http://news247.gr/newspapers/weekly_press/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,7,0,0,'2015-06-01 09:47:49'),
(9,'http://news247.gr/newspapers/entheta_efimeridon/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,8,0,0,'2015-06-01 09:48:49'),
(10,'http://news247.gr/newspapers/women_magazines/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,9,0,0,'2015-06-01 09:49:49'),
(11,'http://news247.gr/newspapers/fitness_kai_eveksia/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,10,0,0,'2015-06-01 09:50:49'),
(12,'http://news247.gr/newspapers/sunday_papers/','?widget=rssfeed&view=feed&contentId=38291&rsspass=getfeed',NULL,11,0,0,'2015-06-01 09:51:49');
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `newspaper`
--

DROP TABLE IF EXISTS `newspaper`;
/*!50001 DROP VIEW IF EXISTS `newspaper`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `newspaper` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `link` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `photostory`
--

DROP TABLE IF EXISTS `photostory`;
/*!50001 DROP VIEW IF EXISTS `photostory`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `photostory` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `supertitle` tinyint NOT NULL,
  `leadText` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `relatedPictures` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!50001 DROP VIEW IF EXISTS `picture`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `picture` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `credits` tinyint NOT NULL,
  `caption` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `product`
--

DROP TABLE IF EXISTS `product`;
/*!50001 DROP VIEW IF EXISTS `product`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `product` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `supertitle` tinyint NOT NULL,
  `leadText` tinyint NOT NULL,
  `teaserTitle` tinyint NOT NULL,
  `teaserSupertitle` tinyint NOT NULL,
  `teaserLeadText` tinyint NOT NULL,
  `body` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `price` tinyint NOT NULL,
  `link` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `relatedStories` tinyint NOT NULL,
  `relatedPictures` tinyint NOT NULL,
  `relatedVideos` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `publication`
--

DROP TABLE IF EXISTS `publication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publication` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publication`
--

LOCK TABLES `publication` WRITE;
/*!40000 ALTER TABLE `publication` DISABLE KEYS */;
INSERT INTO `publication` VALUES (1,'ladylike','2015-05-28 09:49:42');
/*!40000 ALTER TABLE `publication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Administrator','2015-03-14 18:55:28'),(2,'Manager','2015-03-14 18:57:27'),(3,'Editor','2015-03-14 18:58:20');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `section` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `publication` bigint(20) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Name_Publication` (`name`,`publication`),
  KEY `FK_Section_Publication_idx` (`publication`),
  CONSTRAINT `FK_Section_Publication` FOREIGN KEY (`publication`) REFERENCES `publication` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sectionArticle`
--

DROP TABLE IF EXISTS `sectionArticle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sectionArticle` (
  `sectionId` bigint(20) NOT NULL,
  `articleId` bigint(20) NOT NULL,
  `articleOrder` int(11) NOT NULL,
  KEY `FK_Sections_Section_idx` (`sectionId`),
  KEY `FK_Sections_Article_idx` (`articleId`),
  CONSTRAINT `FK_Sections_Article` FOREIGN KEY (`articleId`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Sections_Section` FOREIGN KEY (`sectionId`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sectionArticle`
--

LOCK TABLES `sectionArticle` WRITE;
/*!40000 ALTER TABLE `sectionArticle` DISABLE KEYS */;
/*!40000 ALTER TABLE `sectionArticle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `story`
--

DROP TABLE IF EXISTS `story`;
/*!50001 DROP VIEW IF EXISTS `story`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `story` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `supertitle` tinyint NOT NULL,
  `leadText` tinyint NOT NULL,
  `teaserTitle` tinyint NOT NULL,
  `teaserSupertitle` tinyint NOT NULL,
  `teaserLeadText` tinyint NOT NULL,
  `body` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `price` tinyint NOT NULL,
  `link` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `relatedStories` tinyint NOT NULL,
  `relatedPictures` tinyint NOT NULL,
  `relatedVideos` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `displayName` varchar(100) NOT NULL,
  `publication` bigint(20) NOT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Name_DisplayName_Publication` (`name`,`displayName`,`publication`),
  KEY `FK_Tag_Publication_idx` (`publication`),
  CONSTRAINT `FK_Tag_Publication` FOREIGN KEY (`publication`) REFERENCES `publication` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `role` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_Username` (`username`),
  UNIQUE KEY `UNIQUE_Email` (`email`),
  KEY `FK_User_Role_idx` (`role`),
  CONSTRAINT `FK_User_Role` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'blixabargeld','$2a$10$DDIvJtBM/KqoA.TI.fdQJ.JzZuIVfeUQLHiQorlj/EviFIZNmT02e','nikolaos.i.papadopoulos@gmail.com',1,'','2015-03-14 18:56:51'),(2,'asoule','$2a$10$rPfm2PY3MF.0RJtPhiQzaOShlbDCo2AeQq3LFcL1kPuj08mjUnxM6','asoule@24media.gr',1,'','2015-05-28 10:06:21'),(3,'tasos','$2a$10$eTxAO/gaF1R21JA8RlUXdeQHAc.PX32.xT4TXCssl7iwLorl9f3v.','tk@24media.gr',1,'','2015-05-28 10:07:20');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `video`
--

DROP TABLE IF EXISTS `video`;
/*!50001 DROP VIEW IF EXISTS `video`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `video` (
  `id` tinyint NOT NULL,
  `eceArticleId` tinyint NOT NULL,
  `articleState` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `supertitle` tinyint NOT NULL,
  `leadText` tinyint NOT NULL,
  `videoType` tinyint NOT NULL,
  `videoId` tinyint NOT NULL,
  `embeddedCode` tinyint NOT NULL,
  `alternate` tinyint NOT NULL,
  `authors` tinyint NOT NULL,
  `categories` tinyint NOT NULL,
  `relatedPictures` tinyint NOT NULL,
  `dateLastUpdated` tinyint NOT NULL,
  `datePublished` tinyint NOT NULL,
  `dateUpdated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `advertorial`
--

/*!50001 DROP TABLE IF EXISTS `advertorial`*/;
/*!50001 DROP VIEW IF EXISTS `advertorial`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `advertorial` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`supertitle` AS `supertitle`,`article`.`leadText` AS `leadText`,`article`.`teaserTitle` AS `teaserTitle`,`article`.`teaserSupertitle` AS `teaserSupertitle`,`article`.`teaserLeadText` AS `teaserLeadText`,`article`.`alternate` AS `alternate`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'PICTURE')))),NULL) AS `relatedPictures`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'ADVERTORIAL') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `newspaper`
--

/*!50001 DROP TABLE IF EXISTS `newspaper`*/;
/*!50001 DROP VIEW IF EXISTS `newspaper`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `newspaper` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`alternate` AS `alternate`,`article`.`link` AS `link`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'NEWSPAPER') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `photostory`
--

/*!50001 DROP TABLE IF EXISTS `photostory`*/;
/*!50001 DROP VIEW IF EXISTS `photostory`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `photostory` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`supertitle` AS `supertitle`,`article`.`leadText` AS `leadText`,`article`.`alternate` AS `alternate`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'PICTURE')))),NULL) AS `relatedPictures`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'PHOTOSTORY') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `picture`
--

/*!50001 DROP TABLE IF EXISTS `picture`*/;
/*!50001 DROP VIEW IF EXISTS `picture`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `picture` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`alternate` AS `alternate`,`article`.`credits` AS `credits`,`article`.`caption` AS `caption`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'PICTURE') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `product`
--

/*!50001 DROP TABLE IF EXISTS `product`*/;
/*!50001 DROP VIEW IF EXISTS `product`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `product` AS select `story`.`id` AS `id`,`story`.`eceArticleId` AS `eceArticleId`,`story`.`articleState` AS `articleState`,`story`.`title` AS `title`,`story`.`supertitle` AS `supertitle`,`story`.`leadText` AS `leadText`,`story`.`teaserTitle` AS `teaserTitle`,`story`.`teaserSupertitle` AS `teaserSupertitle`,`story`.`teaserLeadText` AS `teaserLeadText`,`story`.`body` AS `body`,`story`.`alternate` AS `alternate`,`story`.`price` AS `price`,`story`.`link` AS `link`,`story`.`authors` AS `authors`,`story`.`categories` AS `categories`,`story`.`relatedStories` AS `relatedStories`,`story`.`relatedPictures` AS `relatedPictures`,`story`.`relatedVideos` AS `relatedVideos`,`story`.`dateLastUpdated` AS `dateLastUpdated`,`story`.`datePublished` AS `datePublished`,`story`.`dateUpdated` AS `dateUpdated` from `story` where ((`story`.`price` is not null) or (`story`.`link` is not null)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `story`
--

/*!50001 DROP TABLE IF EXISTS `story`*/;
/*!50001 DROP VIEW IF EXISTS `story`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `story` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`supertitle` AS `supertitle`,`article`.`leadText` AS `leadText`,`article`.`teaserTitle` AS `teaserTitle`,`article`.`teaserSupertitle` AS `teaserSupertitle`,`article`.`teaserLeadText` AS `teaserLeadText`,`article`.`body` AS `body`,`article`.`alternate` AS `alternate`,`article`.`price` AS `price`,`article`.`link` AS `link`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'STORY')))),NULL) AS `relatedStories`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'PICTURE')))),NULL) AS `relatedPictures`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'VIDEO')))),NULL) AS `relatedVideos`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'STORY') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `video`
--

/*!50001 DROP TABLE IF EXISTS `video`*/;
/*!50001 DROP VIEW IF EXISTS `video`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `video` AS select `article`.`id` AS `id`,`article`.`eceArticleId` AS `eceArticleId`,`article`.`articleState` AS `articleState`,`article`.`title` AS `title`,`article`.`supertitle` AS `supertitle`,`article`.`leadText` AS `leadText`,`article`.`videoType` AS `videoType`,`article`.`videoId` AS `videoId`,`article`.`embeddedCode` AS `embeddedCode`,`article`.`alternate` AS `alternate`,ifnull(coalesce((select group_concat(`author`.`name` separator ' | ') from (`articleAuthor` left join `author` on((`articleAuthor`.`authorId` = `author`.`id`))) where (`articleAuthor`.`articleId` = `article`.`id`))),NULL) AS `authors`,ifnull(coalesce((select group_concat(`category`.`sectionUniqueName` separator ' | ') from (`articleCategory` left join `category` on((`articleCategory`.`categoryId` = `category`.`id`))) where (`articleCategory`.`articleId` = `article`.`id`))),NULL) AS `categories`,ifnull(coalesce((select group_concat(`T`.`eceArticleId` separator ' | ') from (`articleRelatedArticle` left join `article` `T` on((`articleRelatedArticle`.`relatedArticleId` = `T`.`id`))) where ((`articleRelatedArticle`.`articleId` = `article`.`id`) and (`T`.`discriminator` = 'PICTURE')))),NULL) AS `relatedPictures`,`article`.`dateLastUpdated` AS `dateLastUpdated`,`article`.`datePublished` AS `datePublished`,`article`.`dateUpdated` AS `dateUpdated` from `article` where (`article`.`discriminator` = 'VIDEO') group by `article`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-02 17:28:47

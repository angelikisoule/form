CREATE DATABASE  IF NOT EXISTS `form` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `form`;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` longtext NOT NULL,
  `datePublished` datetime DEFAULT NULL,
  `dateLastUpdated` datetime DEFAULT NULL,
  `dateUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
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
INSERT INTO `user` VALUES (1,'asoule','$2a$10$rPfm2PY3MF.0RJtPhiQzaOShlbDCo2AeQq3LFcL1kPuj08mjUnxM6','angelikisoule@gmail.com',1,'','2016-03-30 10:06:21');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;




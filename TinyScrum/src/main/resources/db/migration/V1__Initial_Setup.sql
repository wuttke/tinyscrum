-- MySQL dump 10.13  Distrib 5.5.18, for Win64 (x86)
--
-- Host: localhost    Database: tinyscrum
-- ------------------------------------------------------
-- Server version	5.5.18

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(4000) NOT NULL,
  `create_date_time` datetime NOT NULL,
  `user_name` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `comment_type` varchar(30) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_upload`
--

DROP TABLE IF EXISTS `file_upload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_upload` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `binary_data` mediumblob,
  `comment_type` varchar(30) DEFAULT NULL,
  `create_date_time` datetime NOT NULL,
  `file_name` varchar(500) DEFAULT NULL,
  `file_size` bigint(20) NOT NULL,
  `mime_type` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iteration`
--

DROP TABLE IF EXISTS `iteration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iteration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `duration_days` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `version` int(11) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  `project` bigint(20) NOT NULL,
  `team` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8904EEDD1DC08454` (`project`),
  KEY `FK8904EEDD48F619D8` (`team`),
  CONSTRAINT `FK8904EEDD1DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK8904EEDD48F619D8` FOREIGN KEY (`team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `name` varchar(200) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `story_estimate_unit` varchar(30) NOT NULL,
  `task_estimate_unit` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_feature`
--

DROP TABLE IF EXISTS `project_feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_feature` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36F643D01DC08454` (`project`),
  CONSTRAINT `FK36F643D01DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project_release`
--

DROP TABLE IF EXISTS `project_release`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_release` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `planned_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB254F0E11DC08454` (`project`),
  CONSTRAINT `FKB254F0E11DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scrum_user`
--

DROP TABLE IF EXISTS `scrum_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scrum_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `email` varchar(300) DEFAULT NULL,
  `full_name` varchar(300) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `estimate` double DEFAULT NULL,
  `name` varchar(800) NOT NULL,
  `status` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  `story` bigint(20) NOT NULL,
  `tester` varchar(100) DEFAULT NULL,
  `developer1` varchar(100) DEFAULT NULL,
  `developer2` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3635851DC08454` (`project`),
  KEY `FK363585347BAA81` (`story`),
  CONSTRAINT `FK3635851DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK363585347BAA81` FOREIGN KEY (`story`) REFERENCES `user_story` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `name` varchar(300) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team_member`
--

DROP TABLE IF EXISTS `team_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) DEFAULT NULL,
  `scrum_user` bigint(20) NOT NULL,
  `team` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA29B52BC48F619D8` (`team`),
  KEY `FKA29B52BC7FEA9977` (`scrum_user`),
  CONSTRAINT `FKA29B52BC48F619D8` FOREIGN KEY (`team`) REFERENCES `team` (`id`),
  CONSTRAINT `FKA29B52BC7FEA9977` FOREIGN KEY (`scrum_user`) REFERENCES `scrum_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_story`
--

DROP TABLE IF EXISTS `user_story`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_story` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(2000) DEFAULT NULL,
  `owner` varchar(100) DEFAULT NULL,
  `title` varchar(400) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `iteration` bigint(20) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  `sequence_number` int(11) NOT NULL,
  `project_feature` bigint(20) DEFAULT NULL,
  `project_release` bigint(20) DEFAULT NULL,
  `estimate` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK735303812C66CB5C` (`iteration`),
  KEY `FK735303811DC08454` (`project`),
  KEY `FK73530381A441AF2D` (`project_release`),
  KEY `FK73530381AD84550B` (`project_feature`),
  CONSTRAINT `FK735303811DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK735303812C66CB5C` FOREIGN KEY (`iteration`) REFERENCES `iteration` (`id`),
  CONSTRAINT `FK73530381A441AF2D` FOREIGN KEY (`project_release`) REFERENCES `project_release` (`id`),
  CONSTRAINT `FK73530381AD84550B` FOREIGN KEY (`project_feature`) REFERENCES `project_feature` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-26  7:22:27

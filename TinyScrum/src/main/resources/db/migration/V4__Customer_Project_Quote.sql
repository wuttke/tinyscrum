DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `project` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24217FDE1DC08454` (`project`),
  CONSTRAINT `FK24217FDE1DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_project`
--

DROP TABLE IF EXISTS `customer_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `customer` bigint(20) NOT NULL,
  `project` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK73AAB3B81DC08454` (`project`),
  KEY `FK73AAB3B859A0D5DA` (`customer`),
  CONSTRAINT `FK73AAB3B81DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK73AAB3B859A0D5DA` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `quote`
--

DROP TABLE IF EXISTS `quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quote` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(2000) DEFAULT NULL,
  `delivery_date` date DEFAULT NULL,
  `external_order_number` varchar(80) DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `order_number` varchar(80) DEFAULT NULL,
  `quote_number` varchar(80) DEFAULT NULL,
  `status` varchar(80) DEFAULT NULL,
  `title` varchar(400) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `customer` bigint(20) DEFAULT NULL,
  `project` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK66F3E7C1DC08454` (`project`),
  KEY `FK66F3E7C59A0D5DA` (`customer`),
  CONSTRAINT `FK66F3E7C1DC08454` FOREIGN KEY (`project`) REFERENCES `project` (`id`),
  CONSTRAINT `FK66F3E7C59A0D5DA` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

alter table user_story add `current_due_date` date DEFAULT NULL;
alter table user_story add `latest_due_date` date DEFAULT NULL;
alter table user_story add `priority` varchar(40) DEFAULT NULL;
alter table user_story add `customer` bigint(20) DEFAULT NULL;
alter table user_story add `customer_project` bigint(20) DEFAULT NULL;
alter table user_story add `quote` bigint(20) DEFAULT NULL;

alter table user_story add KEY `FK7353038137A2CD35` (`customer_project`);
alter table user_story add KEY `FK73530381D5878B9A` (`quote`);
alter table user_story add KEY `FK7353038159A0D5DA` (`customer`);
alter table user_story add CONSTRAINT `FK7353038137A2CD35` FOREIGN KEY (`customer_project`) REFERENCES `customer_project` (`id`);
alter table user_story add CONSTRAINT `FK7353038159A0D5DA` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`);
alter table user_story add CONSTRAINT `FK73530381D5878B9A` FOREIGN KEY (`quote`) REFERENCES `quote` (`id`);
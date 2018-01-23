CREATE DATABASE  IF NOT EXISTS `climate_collector` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `climate_collector`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: climate_collector
-- ------------------------------------------------------
-- Server version	5.7.20

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
-- Table structure for table `weather_collection_records`
--

DROP TABLE IF EXISTS `weather_collection_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weather_collection_records` (
  `weather_collection_records_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Record UID',
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `postal_code` varchar(50) DEFAULT NULL,
  `latitude` float DEFAULT NULL,
  `longitude` float DEFAULT NULL,
  `elevation_ft` int(11) DEFAULT NULL,
  `observer` varchar(255) DEFAULT NULL,
  `station_id` varchar(50) DEFAULT NULL,
  `observation_time` datetime DEFAULT NULL,
  `weather_str` varchar(255) DEFAULT NULL,
  `temp_f` float DEFAULT NULL,
  `relative_humidity_percent` int(11) DEFAULT NULL,
  `wind_str` varchar(255) DEFAULT NULL,
  `wind_dir_str` varchar(50) DEFAULT NULL,
  `wind_deg` int(11) DEFAULT NULL,
  `wind_mph` float DEFAULT NULL,
  `wind_gust_mph` float DEFAULT NULL,
  `pressure_mb` int(11) DEFAULT NULL,
  `pressure_in` float DEFAULT NULL,
  `pressure_trend` varchar(50) DEFAULT NULL,
  `dewpoint_deg_f` int(11) DEFAULT NULL,
  `wind_chill_deg_f` int(11) DEFAULT NULL,
  `heat_index_deg_f` int(11) DEFAULT NULL,
  `feels_like_deg_f` int(11) DEFAULT NULL,
  `visibility_mi` float DEFAULT NULL,
  `solar_rad` int(11) DEFAULT NULL,
  `uv_rad` float DEFAULT NULL,
  `percip_one_hour_in` float DEFAULT NULL,
  `percip_day_in` float DEFAULT NULL,
  `icon_str` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`weather_collection_records_Id`),
  UNIQUE KEY `weather_collection_records_Id_UNIQUE` (`weather_collection_records_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-22 23:49:27

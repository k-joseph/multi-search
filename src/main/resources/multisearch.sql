-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 06, 2015 at 03:22 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

--
-- Database: `multisearch`
--
CREATE DATABASE IF NOT EXISTS `multisearch` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `multisearch`;


-- --------------------------------------------------------

--
-- Table structure for table `multisearch_project`
--

CREATE TABLE IF NOT EXISTS `multisearch_project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) NOT NULL,
  `description` text NULL,
  `uuid` varchar(55) NOT NULL,
  `sql_query` text NOT NULL,
  `field_names` text NOT NULL,
  `fields_exist_in_schema` tinyint(1) NOT NULL,
  `db_name` varchar(35) NOT NULL,
  `db_user` varchar(35) NULL,
  `db_user_password` varchar(55) NULL,
  `server_name` varchar(35) NULL,
  `dbms` varchar(8) NULL,
  `port_number` varchar(8) NULL,
  PRIMARY KEY (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `multisearch_project`
--

INSERT INTO `multisearch_project` (`project_id`, `name`, `description`, `uuid`, `sql_query`, `field_names`, `fields_exist_in_schema`, `db_name`, `db_user`, `db_user_password`, `server_name`, `dbms`, `port_number`) VALUES
(1, 'Chart Search', NULL, '66f0081c-93e9-4c54-ad73-caf34b104a9c', 'SELECT name AS cc_name, filter_query AS cc_filter_query, description AS cc_description FROM chartsearch_categories', 'cc_name, cc_filter_query, cc_description', 1, 'multisearch', NULL, NULL, NULL, NULL, NULL);


--
-- Table structure for table `multisearch_categories`
--

CREATE TABLE IF NOT EXISTS `multisearch_categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `filter_query` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `uuid` char(38) NOT NULL,
  PRIMARY KEY (category_id)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `multisearch_categories`
--

INSERT INTO `multisearch_categories` (`category_id`, `name`, `filter_query`, `description`, `uuid`) VALUES
(1, 'Test', 'concept_class_name:Test', 'Category item for filtering Test', '3918-2910-4245-84f4-a9f38648ac60'),
(2, 'Procedure', 'concept_class_name:Procedure', 'Category item for filtering Procedure', '66b0e726-a280-4d42-ada3-42bc111f68d6'),
(3, 'Drug', 'concept_class_name:Drug', 'Category item for filtering Drug', '35cdafb5-5f6b-4c79-89e4-7226bea70ba9'),
(4, 'Diagnosis', 'concept_class_name:Diagnosis', 'Category item for filtering Diagnosis', 'a458cbba-fb5d-4e5f-b0da-bfab122860a8'),
(5, 'Finding', 'concept_class_name:Finding', 'Category item for filtering Finding', 'fc29ada4-0e00-4450-905c-e2982a242df2'),
(6, 'Anatomy', 'concept_class_name:Anatomy', 'Category item for filtering Anatomy', '290c0ddd-3aad-4718-addf-3d1d33f7ae5e'),
(7, 'Question', 'concept_class_name:Question', 'Category item for filtering Question', '0717136a-5c5f-4d68-b099-cbf1ad820363'),
(8, 'LabSet', 'concept_class_name:LabSet', 'Category item for filtering LabSet', 'e68bb6ff-8b11-48e0-b259-703ffbdf4123'),
(9, 'MedSet', 'concept_class_name:MedSet', 'Category item for filtering MedSet', 'd90cb961-42c3-4dd8-93f1-d9d3bff09866'),
(10, 'ConvSet', 'concept_class_name:ConvSet', 'Category item for filtering ConvSet', 'c7cd56a5-62a3-4da5-a2ec-7b2390b31c7b'),
(11, 'Misc', 'concept_class_name:Misc', 'Category item for filtering Misc', '9159716d-120b-48c7-9d83-a954430b4362'),
(12, 'Symptom', 'concept_class_name:Symptom', 'Category item for filtering Symptom', '113f71f4-e1c0-4548-97df-1fca64f4b011'),
(13, 'Symptom/Finding', 'concept_class_name:"Symptom/Finding"', 'Category item for filtering Symptom/Finding', 'b5611ebc-4ceb-40ed-8142-61d42967b87f'),
(14, 'Specimen', 'concept_class_name:Specimen', 'Category item for filtering Specimen', '4d280790-c856-4ebb-974b-4b1bd0684302'),
(15, 'Misc Order', 'concept_class_name:"Misc Order"', 'Category item for filtering Misc Order', '952ae740-b9d4-49ed-856c-500bc8e2a381'),
(16, 'Frequency', 'concept_class_name:Frequency', 'Category item for filtering Frequency', '21ac0fec-947f-4a76-aeee-2e17d9d0cb13');

-- --------------------------------------------------------

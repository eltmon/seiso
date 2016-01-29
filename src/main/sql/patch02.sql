-- Run this after running patch01.sql

DROP TABLE IF EXISTS `annotated_health_status`; 

CREATE TABLE `annotated_health_status` (
  
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`details` varchar(250) DEFAULT NULL,
	`health_status_id` tinyint(3) unsigned,

PRIMARY KEY (`id`), CONSTRAINT `ahs_health_status_id` 
 FOREIGN KEY (`health_status_id`) REFERENCES `health_status`(`id`))
 ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
 
ALTER TABLE `node` DROP FOREIGN KEY `node_health_status_id`;

ALTER TABLE `node` DROP COLUMN `health_status_id`;

ALTER TABLE `node` ADD COLUMN `annotated_health_status_id` int(10) unsigned DEFAULT NULL;

ALTER TABLE `node` ADD CONSTRAINT `node_health_status_id` FOREIGN KEY (`annotated_health_status_id`) REFERENCES `annotated_health_status` (`id`);
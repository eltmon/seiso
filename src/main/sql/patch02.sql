-- Run this after running patch01.sql
alter table node add column annotated_health_status_id varchar(80) after machine_id;

DROP TABLE IF EXISTS `annotated_health_status`; 

CREATE TABLE `annotated_health_status` (
  
	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
	`details` varchar(250) DEFAULT NULL,

`health_status_id` tinyint(3),
PRIMARY KEY (`id`),
 CONSTRAINT `annotated_health_status_health_status_id` FOREIGN KEY (`health_status_id`) REFERENCES `health_status (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


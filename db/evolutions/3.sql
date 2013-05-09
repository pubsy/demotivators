# --- !Ups
 
ALTER TABLE Demotivator DROP COLUMN domain;

CREATE TABLE `Domain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

ALTER TABLE Demotivator ADD COLUMN domain_id BIGINT(20);
ALTER TABLE Demotivator ADD KEY `domain_fk` (`domain_id`);
ALTER TABLE Demotivator ADD CONSTRAINT `domain_fk` FOREIGN KEY (`domain_id`) REFERENCES `Domain` (`id`);
 
# --- !Downs
 
ALTER TABLE Demotivator ADD COLUMN domain VARCHAR(32) NOT NULL;
DROP TABLE `Domain`;

ALTER TABLE Demotivator DROP FOREIGN KEY domain_fk;
ALTER TABLE Demotivator DROP COLUMN domain_id;
# --- !Ups

CREATE TABLE `Comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `date` DATETIME NOT NULL,
  `demotivator_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


ALTER TABLE Comment ADD KEY `demotivator_fk` (`demotivator_id`);
ALTER TABLE Comment ADD KEY `user_fk` (`user_id`);
ALTER TABLE Comment ADD CONSTRAINT `demotivator_fk` FOREIGN KEY (`demotivator_id`) REFERENCES `Demotivator` (`id`);
ALTER TABLE Comment ADD CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);
 
# --- !Downs

DROP TABLE `Comment`;
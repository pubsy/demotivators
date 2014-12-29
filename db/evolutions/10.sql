# --- !Ups

ALTER TABLE Users ADD COLUMN loginName VARCHAR(255) NOT NULL; 

UPDATE Users SET loginName = email;

ALTER TABLE Users ADD CONSTRAINT unique_loginname 
UNIQUE (loginName);

# --- !Downs
ALTER TABLE Users DROP INDEX unique_loginname;

ALTER TABLE Users DROP COLUMN loginName;
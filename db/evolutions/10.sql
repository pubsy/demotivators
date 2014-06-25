# --- !Ups

ALTER TABLE User ADD COLUMN loginName VARCHAR(255) NOT NULL DEFAULT ''; 

ALTER TABLE User ADD CONSTRAINT unique_loginname 
UNIQUE (loginName);

UPDATE User SET loginName = email;

# --- !Downs
ALTER TABLE User DROP INDEX unique_loginname;

ALTER TABLE User DROP COLUMN loginName;
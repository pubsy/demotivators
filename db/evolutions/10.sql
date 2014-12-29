# --- !Ups

ALTER TABLE User ADD COLUMN loginName VARCHAR(255) NOT NULL; 

UPDATE User SET loginName = email;

ALTER TABLE User ADD CONSTRAINT unique_loginname 
UNIQUE (loginName);

# --- !Downs
ALTER TABLE User DROP INDEX unique_loginname;

ALTER TABLE User DROP COLUMN loginName;
# --- !Ups
 
ALTER TABLE Demotivator ADD COLUMN text VARCHAR(255) DEFAULT NULL; 
 
# --- !Downs
 
ALTER TABLE Demotivator DROP COLUMN text;
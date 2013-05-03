# --- !Ups
 
ALTER TABLE Demotivator ADD COLUMN domain VARCHAR(32) NOT NULL;
 
# --- !Downs
 
ALTER TABLE User DROP COLUMN domain;
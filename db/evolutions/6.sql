# --- !Ups

ALTER TABLE Demotivator ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT false;
 
# --- !Downs

ALTER TABLE Demotivator DROP COLUMN deleted;
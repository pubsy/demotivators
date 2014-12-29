# --- !Ups
 
ALTER TABLE User ADD COLUMN activated BOOLEAN NOT NULL DEFAULT false; 
 
# --- !Downs
 
ALTER TABLE User DROP COLUMN activated;
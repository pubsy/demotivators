# --- !Ups
 
ALTER TABLE User ADD CONSTRAINT unique_displayname 
UNIQUE (displayName);

ALTER TABLE User ADD CONSTRAINT unique_email 
UNIQUE (email);
 
# --- !Downs
 
ALTER TABLE User DROP INDEX unique_displayname;

ALTER TABLE User DROP INDEX unique_email;
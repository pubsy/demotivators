# --- !Ups
 
ALTER TABLE User ADD COLUMN encryption VARCHAR(6) NOT NULL DEFAULT 'BCRYPT';
 
# --- !Downs
 
ALTER TABLE User DROP COLUMN encryption;
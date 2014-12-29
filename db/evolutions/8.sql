# --- !Ups
 
ALTER TABLE Users ADD COLUMN encryption VARCHAR(6) NOT NULL DEFAULT 'BCRYPT';
 
# --- !Downs
 
ALTER TABLE Users DROP COLUMN encryption;
# --- !Ups
 
ALTER TABLE Users ADD COLUMN activated BOOLEAN NOT NULL DEFAULT false; 
 
# --- !Downs
 
ALTER TABLE Users DROP COLUMN activated;
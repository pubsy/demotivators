# --- !Ups
 
ALTER TABLE Users ADD CONSTRAINT unique_displayname 
UNIQUE (displayName);

ALTER TABLE Users ADD CONSTRAINT unique_email 
UNIQUE (email);
 
# --- !Downs
 
ALTER TABLE Users DROP INDEX unique_displayname;

ALTER TABLE Users DROP INDEX unique_email;
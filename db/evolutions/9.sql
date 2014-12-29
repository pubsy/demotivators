# --- !Ups

ALTER TABLE Users MODIFY date timestamp not null default now();

# --- !Downs

ALTER TABLE Users MODIFY date DATETIME DEFAULT NULL;
# --- !Ups

ALTER TABLE User MODIFY date timestamp not null default now();

# --- !Downs

ALTER TABLE User MODIFY date DATETIME DEFAULT NULL;
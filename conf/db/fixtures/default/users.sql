# --- !Ups

CREATE TABLE users
(
    username VARCHAR(255) PRIMARY KEY NOT NULL,
    email VARCHAR(320) NOT NULL,
    password TEXT NOT NULL
);
CREATE UNIQUE INDEX users_email_uindex ON users (email);
CREATE UNIQUE INDEX users_username_uindex ON users (username);

# --- !Downs

DROP TABLE users;
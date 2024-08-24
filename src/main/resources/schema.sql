-- Elimina la base de datos si ya existe y la vuelve a crear
DROP DATABASE IF EXISTS flashcards;
CREATE DATABASE IF NOT EXISTS flashcards DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE flashcards;

-- Estructura de la tabla users
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
   id int NOT NULL AUTO_INCREMENT,
   username varchar(20) UNIQUE NOT NULL,
   email varchar(60) UNIQUE NOT NULL,
   password varchar(250) NOT NULL,
   register_date datetime,
   is_enabled BIT(1),
   PRIMARY KEY (id)
);

-- Estructura de la tabla collections
DROP TABLE IF EXISTS collections;
CREATE TABLE IF NOT EXISTS collections (
   id int NOT NULL AUTO_INCREMENT,
   title varchar(30) NOT NULL,
   description varchar(60) DEFAULT NULL,
   created_at datetime,
   is_public BIT(1),
   user_id int NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_users
       FOREIGN KEY (user_id)
       REFERENCES users (id)
       ON DELETE CASCADE
);

-- Estructura de la tabla cards
DROP TABLE IF EXISTS cards;

CREATE TABLE IF NOT EXISTS cards (
   id int NOT NULL AUTO_INCREMENT,
   front varchar(255) NOT NULL,
   backside varchar(255) DEFAULT NULL,
   created_at datetime,
   collection_id int NOT NULL,
   user_id int NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_collections
       FOREIGN KEY (collection_id)
       REFERENCES collections (id),
   CONSTRAINT fk_users_card
       FOREIGN KEY (user_id)
       REFERENCES users (id)
       ON DELETE CASCADE
);

-- Estructura de la tabla likes

DROP TABLE IF EXISTS likes;
CREATE TABLE IF NOT EXISTS likes (
   user_id INT NOT NULL,
   card_id INT NOT NULL,
   PRIMARY KEY (user_id, card_id),
   CONSTRAINT fk_user_card_user
       FOREIGN KEY (user_id)
       REFERENCES users (id)
       ON DELETE CASCADE,
   CONSTRAINT fk_user_card_card
       FOREIGN KEY (card_id)
       REFERENCES cards (id)
       ON DELETE CASCADE
);
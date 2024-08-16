DROP SCHEMA IF EXISTS flashcards;

CREATE SCHEMA IF NOT EXISTS flashcards DEFAULT CHARACTER SET utf8mb4;
USE flashcards;

-- Table structure for table users

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

DROP TABLE IF EXISTS cards;
CREATE TABLE IF NOT EXISTS cards (
   `id` int NOT NULL AUTO_INCREMENT,
   `front` varchar(255) NOT NULL,
   `backside` varchar(255) DEFAULT NULL,
   `created_at` datetime,
   `is_favourite` BIT(1),
   `collection_id` int NOT NULL,
   `user_id` int NOT NULL,
   PRIMARY KEY (id),
   CONSTRAINT fk_collections
       FOREIGN KEY (collection_id)
       REFERENCES collections (id),
   CONSTRAINT fk_users_card
          FOREIGN KEY (user_id)
          REFERENCES users (id)
       ON DELETE CASCADE
);
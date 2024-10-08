-- Elimina la base de datos si ya existe y la vuelve a crear
-- DROP DATABASE IF EXISTS flashcards;
 CREATE DATABASE IF NOT EXISTS flashcards DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
 USE flashcards;

-- Estructura de la tabla users
-- DROP TABLE IF EXISTS users;
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
-- DROP TABLE IF EXISTS collections;
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
-- DROP TABLE IF EXISTS cards;
CREATE TABLE IF NOT EXISTS cards (
   id int NOT NULL AUTO_INCREMENT,
   front varchar(255) NOT NULL,
   backside varchar(255) DEFAULT NULL,
   created_at datetime,
   is_favourite BIT(1),
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

-- Table hashtags
-- DROP TABLE IF EXISTS hashtags;
CREATE TABLE IF NOT EXISTS hashtags (
   hashtag varchar(50) NOT NULL,
   card_id int NOT NULL,
   PRIMARY KEY (hashtag, card_id),
   CONSTRAINT fk_cards_hashtag
       FOREIGN KEY (card_id)
       REFERENCES cards (id)
       ON DELETE CASCADE
);

-- Drop the user_collections table if it exists
-- DROP TABLE IF EXISTS user_collections;

-- Create the user_collections table
CREATE TABLE IF NOT EXISTS user_collections (
   user_id INT NOT NULL,
   collection_id INT NOT NULL,
   PRIMARY KEY (user_id, collection_id),
   CONSTRAINT fk_user_collections_user
       FOREIGN KEY (user_id)
       REFERENCES users (id)
       ON DELETE CASCADE,
   CONSTRAINT fk_user_collections_collection
       FOREIGN KEY (collection_id)
       REFERENCES collections (id)
       ON DELETE CASCADE
);


-- Insertar usuarios de prueba en la tabla `users`
INSERT INTO users (username, email, password, register_date, is_enabled)
VALUES
('testuser', 'testuser@example.com', 'securepassword', NOW(), 1),
('user1', 'user1@example.com', 'password_hash_1', NOW(), 1),
('user2', 'user2@example.com', 'password_hash_2', NOW(), 1);

-- Insertar colecciones de prueba en la tabla `collections`
INSERT INTO collections (title, description, created_at, is_public, user_id)
VALUES
('Animales Curiosos', 'Colección de datos curiosos sobre animales', NOW(), 1, 1),
('Curiosidades Históricas', 'Colección de datos curiosos sobre la historia', NOW(), 0, 1),
('My Collection', 'A description of my collection', NOW(), 1, 2);

-- Insertar 100 datos curiosos en la tabla `cards`
INSERT INTO cards (front, backside, created_at, is_favourite, collection_id, user_id)
VALUES
('¿Sabías que el corazón de un camarón está en su cabeza?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las abejas pueden reconocer rostros humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la miel nunca se echa a perder?', 'Curiosidad alimentaria', NOW(), 0, 1, 1),
('¿Sabías que los cocodrilos no pueden sacar la lengua?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los pulpos tienen tres corazones?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las cucarachas pueden vivir varias semanas sin cabeza?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el ketchup se utilizaba como medicina en el siglo XIX?', 'Curiosidad histórica', NOW(), 0, 1, 1),
('¿Sabías que el ojo humano puede distinguir hasta 10 millones de colores?', 'Curiosidad humana', NOW(), 0, 1, 1),
('¿Sabías que la Tierra no es perfectamente redonda, sino que es un geoide?', 'Curiosidad geográfica', NOW(), 0, 1, 1),
('¿Sabías que las bananas son bayas, pero las fresas no?', 'Curiosidad alimentaria', NOW(), 0, 1, 1),
('¿Sabías que los humanos comparten el 50% de su ADN con los plátanos?', 'Curiosidad científica', NOW(), 0, 1, 1),
('¿Sabías que los caracoles pueden dormir hasta tres años seguidos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las tortugas pueden respirar a través de su trasero?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los flamencos son blancos y se vuelven rosados por su dieta?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las vacas tienen mejores amigos y se estresan cuando se separan?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el cuerpo humano tiene suficiente hierro para hacer un clavo de 7.5 cm?', 'Curiosidad humana', NOW(), 0, 1, 1),
('¿Sabías que las hormigas no tienen pulmones?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los koalas tienen huellas dactilares muy parecidas a las de los humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la Gran Muralla China no es visible desde el espacio?', 'Curiosidad geográfica', NOW(), 0, 1, 1),
('¿Sabías que las arañas no pueden volar, pero pueden "volar" usando seda?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los tiburones han existido por más tiempo que los árboles?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el cerebro humano es más activo durante la noche que durante el día?', 'Curiosidad humana', NOW(), 0, 1, 1),
('¿Sabías que las mariposas tienen sensores de sabor en los pies?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las cebras son negras con rayas blancas, y no al revés?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los delfines tienen nombres individuales y se llaman entre ellos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las estrellas de mar no tienen cerebro?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las jirafas tienen la misma cantidad de huesos en el cuello que los humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los humanos y las vacas tienen aproximadamente el mismo número de dientes?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las cabras tienen acentos diferentes según la región en la que viven?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el agua puede hervir y congelarse al mismo tiempo bajo ciertas condiciones?', 'Curiosidad científica', NOW(), 0, 1, 1),
('¿Sabías que los ojos de un avestruz son más grandes que su cerebro?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las ranas pueden vomitar su estómago para limpiarlo?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que un día en Venus dura más que un año en Venus?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los árboles de secuoya pueden vivir más de 2,000 años?', 'Curiosidad geográfica', NOW(), 0, 1, 1),
('¿Sabías que los búhos no pueden mover sus ojos, pero pueden girar la cabeza hasta 270 grados?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la lengua azul del lagarto de lengua azul está diseñada para asustar a los depredadores?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los elefantes son los únicos mamíferos que no pueden saltar?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las mariposas tienen sangre verde?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las hormigas pueden levantar hasta 50 veces su propio peso?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el cerebro humano contiene más conexiones sinápticas que estrellas en la Vía Láctea?', 'Curiosidad humana', NOW(), 0, 1, 1),
('¿Sabías que la posición de las orejas de un caballo puede decirnos su estado de ánimo?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el cerebro humano se encoge con la edad?', 'Curiosidad humana', NOW(), 0, 1, 1),
('¿Sabías que la distancia entre la Tierra y la Luna es tal que podríamos alinear todos los planetas del sistema solar en ella?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los pingüinos tienen rodillas?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que un copo de nieve puede tardar hasta una hora en llegar al suelo?', 'Curiosidad meteorológica', NOW(), 0, 1, 1),
('¿Sabías que las arañas macho suelen tejer una red antes de cortejar a una hembra?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los rayos viajan a 300,000 km por segundo?', 'Curiosidad meteorológica', NOW(), 0, 1, 1),
('¿Sabías que la longitud total del ADN en un ser humano es suficiente para ir al sol y volver 100 veces?', 'Curiosidad científica', NOW(), 0, 1, 1),
('¿Sabías que el Sol constituye el 99.86% de la masa del sistema solar?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los gatos tienen más huesos en el cuerpo que los humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las abejas no pueden volar cuando hace frío?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que un camello puede beber 200 litros de agua en 3 minutos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los pulpos tienen sangre azul debido a la presencia de cobre en su hemoglobina?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los peces no pueden cerrar los ojos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el caracol más rápido del mundo viaja a una velocidad de 0.03 millas por hora?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la Vía Láctea tiene más de 100 mil millones de estrellas?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los mosquitos tienen dientes?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la piel de un oso polar es negra bajo su pelaje blanco?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las ovejas pueden reconocer rostros humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los perezosos pueden mantener la respiración durante 40 minutos bajo el agua?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los flamencos nacen grises y no rosados?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el tiburón martillo tiene visión binocular?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las jirafas pueden limpiar sus orejas con su lengua de 50 cm?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las estrellas fugaces son en realidad meteoritos entrando en la atmósfera de la Tierra?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los tiburones nunca dejan de nadar?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los caballos pueden dormir tanto de pie como tumbados?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las mariposas pueden saborear con sus pies?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el cerebro de una avestruz es más pequeño que su ojo?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las hormigas son las criaturas más longevas de su tamaño, viviendo hasta 30 años?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el planeta más caliente de nuestro sistema solar no es el más cercano al Sol?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que los caballitos de mar son monógamos y se quedan con su pareja de por vida?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las avispas se vuelven más agresivas a finales del verano?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la lengua de un oso hormiguero puede medir más de 60 cm?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los gusanos pueden regenerar partes de su cuerpo si se cortan?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la estrella más cercana a la Tierra después del Sol es Proxima Centauri?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que el animal nacional de Escocia es el unicornio?', 'Curiosidad histórica', NOW(), 0, 1, 1),
('¿Sabías que los murciélagos siempre giran a la izquierda cuando salen de una cueva?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las ranas son capaces de cambiar de sexo en un entorno monosexual?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las estrellas de mar tienen ojos en los extremos de sus brazos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los elefantes tienen un saludo especial con la trompa?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las vacas pueden detectar olores a más de 8 kilómetros de distancia?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los tiburones tienen electrorreceptores para detectar campos eléctricos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los peces de colores pierden su color si se mantienen en la oscuridad por demasiado tiempo?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las medusas tienen un 95% de agua en su cuerpo?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los gatos tienen más huesos en su cuerpo que los humanos?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que el 10% de los huesos de un gato están en su cola?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las ranas no beben agua con la boca, sino que la absorben a través de la piel?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las tortugas pueden respirar a través de su cloaca?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los cangrejos pueden regenerar sus pinzas si las pierden?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las tortugas pueden vivir sin comida durante más de un año?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las avispas se vuelven más agresivas a finales del verano?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que los koalas pueden dormir hasta 22 horas al día?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las estrellas fugaces no son estrellas, sino fragmentos de cometas o asteroides?', 'Curiosidad astronómica', NOW(), 0, 1, 1),
('¿Sabías que la mayoría de los elefantes pesan menos que la lengua de una ballena azul?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que las hormigas pueden sobrevivir bajo el agua por 24 horas?', 'Curiosidad animal', NOW(), 0, 1, 1),
('¿Sabías que la primera computadora ocupaba una habitación entera?', 'Curiosidad tecnológica', NOW(), 0, 1, 1);

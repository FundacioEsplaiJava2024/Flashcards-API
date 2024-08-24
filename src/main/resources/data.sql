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
('Curiosidades Históricas', 'Colección de datos curiosos sobre la historia', NOW(), 1, 1),
('My Collection', 'A description of my collection', NOW(), 1, 1);

-- Insertar 100 datos curiosos en la tabla `cards`
INSERT INTO cards (front, backside, created_at, collection_id, user_id)
VALUES  
('¿Sabías que el corazón de un camarón está en su cabeza?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las abejas pueden reconocer rostros humanos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que la miel nunca se echa a perder?', 'Curiosidad alimentaria', NOW(), 1, 1),
('¿Sabías que los cocodrilos no pueden sacar la lengua?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los pulpos tienen tres corazones?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las cucarachas pueden vivir varias semanas sin cabeza?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el ketchup se utilizaba como medicina en el siglo XIX?', 'Curiosidad histórica', NOW(), 1, 1),
('¿Sabías que el ojo humano puede distinguir hasta 10 millones de colores?', 'Curiosidad humana', NOW(), 1, 1),
('¿Sabías que la Tierra no es perfectamente redonda, sino que es un geoide?', 'Curiosidad geográfica', NOW(), 1, 1),
('¿Sabías que las bananas son bayas, pero las fresas no?', 'Curiosidad alimentaria', NOW(), 1, 1),
('¿Sabías que los humanos comparten el 50% de su ADN con los plátanos?', 'Curiosidad científica', NOW(), 1, 1),
('¿Sabías que los caracoles pueden dormir hasta tres años seguidos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las tortugas pueden respirar a través de su trasero?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los flamencos son blancos y se vuelven rosados por su dieta?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las vacas tienen mejores amigos y se estresan cuando se separan?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el cuerpo humano tiene suficiente hierro para hacer un clavo de 7.5 cm?', 'Curiosidad humana', NOW(), 1, 1),
('¿Sabías que las hormigas no tienen pulmones?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los koalas tienen huellas dactilares muy parecidas a las de los humanos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que la Gran Muralla China no es visible desde el espacio?', 'Curiosidad geográfica', NOW(), 1, 1),
('¿Sabías que las arañas no pueden volar, pero pueden "volar" usando seda?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los tiburones han existido por más tiempo que los árboles?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el cerebro humano es más activo durante la noche que durante el día?', 'Curiosidad humana', NOW(), 1, 1),
('¿Sabías que las mariposas tienen sensores de sabor en los pies?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las cebras son negras con rayas blancas, y no al revés?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los delfines tienen nombres individuales y se llaman entre ellos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las estrellas de mar no tienen cerebro?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las jirafas tienen la misma cantidad de huesos en el cuello que los humanos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los humanos y las vacas tienen aproximadamente el mismo número de dientes?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las cabras tienen acentos diferentes según la región en la que viven?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el agua puede hervir y congelarse al mismo tiempo bajo ciertas condiciones?', 'Curiosidad científica', NOW(), 1, 1),
('¿Sabías que los ojos de un avestruz son más grandes que su cerebro?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las ranas pueden vomitar su estómago para limpiarlo?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que un día en Venus dura más que un año en Venus?', 'Curiosidad astronómica', NOW(), 1, 1),
('¿Sabías que los árboles de secuoya pueden vivir más de 2,000 años?', 'Curiosidad geográfica', NOW(), 1, 1),
('¿Sabías que los búhos no pueden mover sus ojos, pero pueden girar la cabeza hasta 270 grados?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que la lengua azul del lagarto de lengua azul está diseñada para asustar a los depredadores?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los elefantes son los únicos mamíferos que no pueden saltar?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las mariposas tienen sangre verde?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las hormigas pueden levantar hasta 50 veces su propio peso?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el cerebro humano contiene más conexiones sinápticas que estrellas en la Vía Láctea?', 'Curiosidad humana', NOW(), 1, 1),
('¿Sabías que la posición de las orejas de un caballo puede decirnos su estado de ánimo?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que el cerebro humano se encoge con la edad?', 'Curiosidad humana', NOW(), 1, 1),
('¿Sabías que la distancia entre la Tierra y la Luna es tal que podríamos alinear todos los planetas del sistema solar en ella?', 'Curiosidad astronómica', NOW(), 1, 1),
('¿Sabías que los pingüinos tienen rodillas?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que un copo de nieve puede tardar hasta una hora en llegar al suelo?', 'Curiosidad meteorológica', NOW(), 1, 1),
('¿Sabías que las arañas macho suelen tejer una red antes de cortejar a una hembra?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los rayos viajan a 300,000 km por segundo?', 'Curiosidad meteorológica', NOW(), 1, 1),
('¿Sabías que la longitud total del ADN en un ser humano es suficiente para ir al sol y volver 100 veces?', 'Curiosidad científica', NOW(), 1, 1),
('¿Sabías que el Sol constituye el 99.86% de la masa del sistema solar?', 'Curiosidad astronómica', NOW(), 1, 1),
('¿Sabías que los gatos tienen más huesos en el cuerpo que los humanos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que las abejas no pueden volar cuando hace frío?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que un camello puede beber 200 litros de agua en 3 minutos?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los pulpos tienen sangre azul debido a la presencia de cobre en su hemoglobina?', 'Curiosidad animal', NOW(), 1, 1),
('¿Sabías que los peces no pueden cerrar los ojos?', 'Curiosidad animal', NOW(), 1, 1)

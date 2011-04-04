INSERT INTO author  (id, name, surname) values (1, "Ray","Bradbury");
INSERT INTO author  (id, name, surname) values (2, "Isaac","Asimov");
INSERT INTO author  (id, name, surname) values (3, "Dan","Simmons");

INSERT INTO book  (Author_id, id, title, description) values (3,1, "Ilion","jedna kniha");
INSERT INTO book  (Author_id, id, title, description) values (1,2, "Pampeliskove vino","dalsi kniha");


INSERT INTO user  (name, surname, id, email, password) values ("adam","cincura", 1,"cincura@atlas.cz","heslo");
INSERT INTO user  (name, surname, id, email, password) values ("tomas","cerevka", 2,"tomas@cerevka.cz","hesloheslo");


INSERT INTO role  (id, name) values (1, "admin");
INSERT INTO role  (id, name) values (2, "user");


INSERT INTO usersrole  (User_id, Role_id) values (1, 1);
INSERT INTO usersrole  (User_id, Role_id) values (2, 2);


INSERT INTO  shelf (belongs, id, name) values (2, 1,"prvni police");
INSERT INTO  shelf (belongs, id, name) values (2, 2,"druha police");


INSERT INTO  copy (Book_id, id, note, published) values (1, 1,"moje prvni knizka",CURDATE());
INSERT INTO  copy (Book_id, id, note, published) values (2, 2,"moje druha knizka",CURDATE());


INSERT INTO  bookinshelf (Shelf_id, Copy_id) values (1, 1);
INSERT INTO  bookinshelf (Shelf_id, Copy_id) values (1, 2);


INSERT INTO  friendship (authorized, User_1_id, User_2_id) values (1, 1, 2);


INSERT INTO  borrow (limit_date, do, od, id, User_id, Copy_id) values (CURDATE(), CURDATE(),CURDATE(),1,1,2);

INSERT INTO  reservation (status, datum, id, User_id, Copy_id) values ("zadana", CURDATE(), 1,1,1);
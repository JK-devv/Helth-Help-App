-- insert into users
INSERT INTO users (name, password, role) VALUES ('doctor', 'guatemala', 'DOCTOR');
INSERT INTO users (name, password, role) VALUES ('manager', 'nicaragua', 'MANAGER');

-- insert into categories
INSERT INTO categories (name) VALUES ('medical');
INSERT INTO categories (name) VALUES ('food');

-- insert into items
INSERT INTO items (name, count, category_id) VALUES ('cough syrup', 3, 1);
INSERT INTO items (name, count, category_id) VALUES ('antiseptic', 7, 1);
INSERT INTO items (name, count, category_id) VALUES ('oral rinse', 3, 1);
INSERT INTO items (name, count, category_id) VALUES ('ointment', 7, 1);
INSERT INTO items (name, count, category_id) VALUES ('tablets', 11, 1);
INSERT INTO items (name, count, category_id) VALUES ('blood pressure', 9, 1);
INSERT INTO items (name, count, category_id) VALUES ('pulse oximeter', 2, 1);
INSERT INTO items (name, count, category_id) VALUES ('insulin  pump', 9, 1);
INSERT INTO items (name, count, category_id) VALUES ('stethoscope', 9, 1);

INSERT INTO items (name, count, category_id) VALUES ('apple pie', 3, 2);
INSERT INTO items (name, count, category_id) VALUES ('lemon tart', 7, 2);
INSERT INTO items (name, count, category_id) VALUES ('cheeseburger', 3, 2);
INSERT INTO items (name, count, category_id) VALUES ('green salad', 7, 2);
INSERT INTO items (name, count, category_id) VALUES ('lasagne', 11, 2);
INSERT INTO items (name, count, category_id) VALUES ('pizza', 9, 2);
INSERT INTO items (name, count, category_id) VALUES ('sandwich', 2, 2);
INSERT INTO items (name, count, category_id) VALUES ('soup', 9, 2);
INSERT INTO items (name, count, category_id) VALUES ('chicken', 9, 2);

-- insert into patients
INSERT INTO patients (name, phone) VALUES ('Anna Ivanovna','112-16-87');
INSERT INTO patients (name, phone) VALUES ('Xuan Cox','369-72-85');
INSERT INTO patients (name, phone) VALUES ('Asher Edwards','768-48-52');
INSERT INTO patients (name, phone) VALUES ('Fabian Gray','220-45-98');
INSERT INTO patients (name, phone) VALUES ('Alana Carter','767-45-23');
INSERT INTO patients (name, phone) VALUES ('Quenby Scott','032-04-91');
INSERT INTO patients (name, phone) VALUES ('Coraline Lee','205-45-27');
INSERT INTO patients (name, phone) VALUES ('Brisa Perry','825-62-82');
INSERT INTO patients (name, phone) VALUES ('Lewis Reed','541-71-03');
INSERT INTO patients (name, phone) VALUES ('Gerald Young','980-17-95');

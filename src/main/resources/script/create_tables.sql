CREATE TABLE users (
                        id serial PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(255)
);

CREATE TABLE categories (
                       id    serial PRIMARY KEY,
                       name  VARCHAR(255) UNIQUE
);

CREATE TABLE items (
                        id    serial PRIMARY KEY,
                        name  VARCHAR(255) UNIQUE,
                        count integer,
                        category_id integer,
                        FOREIGN KEY (category_id) REFERENCES categories
);


CREATE TABLE patients (
                       id    serial PRIMARY KEY,
                       name  VARCHAR(255),
                       phone VARCHAR(255) UNIQUE
);
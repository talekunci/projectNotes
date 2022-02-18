CREATE TABLE users(
    id serial PRIMARY key,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(72) NOT NULL
);

CREATE TABLE notes(
    uuid VARCHAR(36) PRIMARY key,
    name VARCHAR(100) NOT NULL,
    body VARCHAR(10000),
    access VARCHAR(10) NOT NULL,
    user_id INTEGER NOT NULL,

    CONSTRAINT user_id_fk FOREIGN key (user_id) REFERENCES users(id)
);

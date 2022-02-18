CREATE TABLE users(
    id serial PRIMARY key,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(72) NOT NULL
);

CREATE TABLE notes(
    uuid VARCHAR(36) PRIMARY key,
    name VARCHAR(100) NOT NULL,
    body VARCHAR(10000),
    access VARCHAR(10) NOT NULL
);

CREATE TABLE users_notes(
    user_id INTEGER NOT NULL,
    note_uuid VARCHAR(36) NOT NULL,

    CONSTRAINT user_id_fk FOREIGN key (user_id) REFERENCES users(id),
    CONSTRAINT note_uuid_fk FOREIGN key (note_uuid) REFERENCES notes(uuid)
);

DROP TABLE IF EXISTS authentication_token CASCADE;
DROP TABLE IF EXISTS phone CASCADE;
DROP TABLE IF EXISTS user CASCADE;
DROP TABLE IF EXISTS user_phones CASCADE;

CREATE TABLE user (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_at TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE phone (
    id SERIAL PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    city_code VARCHAR(255) NOT NULL,
    country_code VARCHAR(255) NOT NULL,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE authentication_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id UUID UNIQUE,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE user_phones (
    user_id UUID NOT NULL,
    phones_id SERIAL NOT NULL,
    PRIMARY KEY (user_id, phones_id),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (phones_id) REFERENCES Phone(id)
);
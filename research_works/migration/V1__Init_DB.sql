CREATE TABLE user
(
    id   int8 NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id   int8 NOT NULL,
    name varchar(255),
    PRIMARY KEY (id)
);
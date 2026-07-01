CREATE TABLE cliente
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(255) NOT NULL,
    apellido  VARCHAR(255) NOT NULL,
    telefono  varchar(20)      NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);
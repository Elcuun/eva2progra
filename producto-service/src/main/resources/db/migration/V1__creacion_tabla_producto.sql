CREATE TABLE producto
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    marca    VARCHAR(255) NOT NULL,
    modelo  VARCHAR(255) NOT NULL,
    anio  BIGINT       NOT NULL,
    color  VARCHAR(255) NOT NULL,
    precio  BIGINT       NOT NULL,
    estado VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)

);
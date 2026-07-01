CREATE DATABASE IF NOT EXISTS bd_pedidos;

USE bd_pedidos;

CREATE TABLE IF NOT EXISTS pedidos (
    idpedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    fechapedido DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    id_cliente BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL
);

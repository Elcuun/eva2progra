CREATE TABLE boletas (

                         id_boleta BIGINT PRIMARY KEY AUTO_INCREMENT,

                         fecha DATE NOT NULL,

                         total DECIMAL(10,2) NOT NULL,

                         metodo_pago VARCHAR(40) NOT NULL,

                         id_pedido BIGINT NOT NULL

);
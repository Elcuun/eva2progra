CREATE TABLE envios (

                        id_envio BIGINT PRIMARY KEY AUTO_INCREMENT,

                        direccion_entrega VARCHAR(150) NOT NULL,

                        fecha_envio DATE NOT NULL,

                        estado VARCHAR(40) NOT NULL,

                        id_pedido BIGINT NOT NULL

);
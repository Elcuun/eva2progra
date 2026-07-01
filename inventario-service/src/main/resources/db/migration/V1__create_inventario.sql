CREATE TABLE inventario (

                            id_Inventario BIGINT PRIMARY KEY AUTO_INCREMENT,

                            id_Producto BIGINT NOT NULL,

                            id_Sucursal BIGINT NOT NULL,

                            stock INT NOT NULL,

                            fecha_Ingreso date NOT NULL

);
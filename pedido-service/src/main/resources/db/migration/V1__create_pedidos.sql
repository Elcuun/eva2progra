CREATE TABLE pedidos
(

    id_Pedido    BIGINT PRIMARY KEY AUTO_INCREMENT,

    fecha_Pedido DATE        NOT NULL,

    estado      VARCHAR(20) NOT NULL,

    id_Cliente   BIGINT      NOT NULL,

    id_Empleado  BIGINT      NOT NULL

);

CREATE TABLE detalle_pedido
(

    id_detalle  BIGINT PRIMARY KEY AUTO_INCREMENT,

    id_Pedido   BIGINT         NOT NULL,

    id_Producto BIGINT         NOT NULL,

    cantidad   INT            NOT NULL,

    subtotal   DECIMAL(10, 2) NOT NULL

);
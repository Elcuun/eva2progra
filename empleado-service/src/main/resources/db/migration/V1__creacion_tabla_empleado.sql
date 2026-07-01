CREATE TABLE empleado (

     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     nombre VARCHAR(100) NOT NULL,
     apellido VARCHAR(100) NOT NULL,
     cargo VARCHAR(100) NOT NULL,
     telefono VARCHAR(20) NOT NULL,
     email VARCHAR(150) UNIQUE NOT NULL,
     password VARCHAR(150) NOT NULL,
     sucursal BIGINT NOT NULL,
     UNIQUE (email)

);
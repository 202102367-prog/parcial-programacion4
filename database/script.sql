-- Ejecutar esta instruccion en pgAdmin, conectado a postgres y fuera de una transaccion.
CREATE DATABASE parcial_citas;
-- Conectarse despues a parcial_citas.
-- Spring Boot genera las seis tablas desde las entidades JPA al iniciar:
-- cliente, empleado, servicio, cita, detalle_cita y horario_empleado.
-- No ejecutar CREATE DATABASE otra vez si ya existe.

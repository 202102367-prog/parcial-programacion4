-- Ejecutar conectado a parcial_citas, despues de completar las pruebas.
SELECT * FROM cliente;
SELECT * FROM empleado;
SELECT * FROM servicio;
SELECT * FROM horario_empleado;
SELECT * FROM cita;
SELECT * FROM detalle_cita;
SELECT c.id, c.fecha, c.hora, c.estado, cl.nombre AS cliente,
       e.nombre AS empleado, c.total,
       SUM(d.subtotal) AS total_detalles
FROM cita c JOIN cliente cl ON cl.id=c.cliente_id
JOIN empleado e ON e.id=c.empleado_id
JOIN detalle_cita d ON d.cita_id=c.id
GROUP BY c.id,cl.nombre,e.nombre ORDER BY c.id;

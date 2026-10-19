# Parte conceptual — Programación 4

1. **@Entity, @Repository, @Service y @RestController.**
   - `@Entity` identifica una clase persistente que JPA relaciona con una tabla. Por ejemplo, Cliente representa la tabla cliente y su id es la clave primaria.
   - `@Repository` identifica la capa de acceso a datos. ClienteRepository extiende JpaRepository y permite guardar, consultar y eliminar clientes; Spring Data crea su implementación.
   - `@Service` identifica la capa de negocio. CitaService comprueba la disponibilidad del empleado y calcula subtotales y total.
   - `@RestController` recibe solicitudes HTTP y devuelve datos, normalmente JSON. CitaController publica las rutas /api/citas y delega el trabajo al servicio.

2. **GET, POST, PUT y DELETE.**
   - GET consulta información sin modificarla: GET /api/clientes/1 obtiene el cliente 1.
   - POST crea un recurso: POST /api/clientes con nombre, correo y telefono crea un cliente y responde 201.
   - PUT actualiza un recurso: PUT /api/clientes/1 reemplaza sus datos. PUT /api/citas/1/atender cambia su estado a ATENDIDA.
   - DELETE elimina un recurso: DELETE /api/clientes/1 elimina un cliente sin citas relacionadas y responde 204.

3. **Ventaja de la capa Service.** Separa las reglas de negocio de las solicitudes HTTP, evita duplicarlas y permite reutilizarlas y probarlas. En este proyecto, crear y actualizar citas pasan por la misma validación y cálculo en CitaService; el Controller solo recibe datos y delega. @Transactional mantiene los cambios de una cita y sus detalles dentro de una transacción.

4. **Consumo desde Node.js y papel de Axios.** Express publica GET /resumen-citas. Su manejador usa Axios para enviar GET http://localhost:8080/api/citas y espera la respuesta con async/await. Axios es el cliente HTTP que recibe el JSON; Node cuenta los estados y suma los totales de las citas ATENDIDAS. Express devuelve el resumen procesado. Si Spring Boot no está disponible, responde 502.

5. **@OneToMany y @ManyToOne.** Representan los dos lados de una relación de uno a muchos. Cliente tiene una lista de citas con @OneToMany(mappedBy="cliente"); cada Cita tiene un cliente con @ManyToOne. En PostgreSQL, cita.cliente_id es una clave foránea hacia cliente.id. De la misma forma, un empleado tiene citas y horarios; una cita tiene varios detalles, y cada DetalleCita apunta a una Cita y a un Servicio mediante claves foráneas. mappedBy indica qué atributo del otro lado administra la relación.

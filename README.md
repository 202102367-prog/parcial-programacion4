# Parcial práctico: gestión de citas y servicios

## Ejecutar en Windows

Requisitos: Java **17 o superior**, PostgreSQL iniciado, Node.js **LTS con npm**, Git para consultar el historial. Puertos libres: 8080, 3000 y PostgreSQL en 5432.

1. Extraer **todo** el ZIP a una carpeta local. No ejecutar dentro del ZIP.
2. En pgAdmin, conectarse al servidor PostgreSQL. Abrir Query Tool sobre la base `postgres` y ejecutar `database/script.sql` para crear `parcial_citas`. Si ya existe, omitir la creación. No borrar otras bases.
3. Abrir `INICIAR.bat`. Introducir usuario PostgreSQL (Enter usa `postgres`) y contraseña. El JAR ya compilado inicia Spring Boot. La primera ejecución instala las dependencias Node con `npm ci`, por lo que requiere internet.
4. Esperar el mensaje `Listo`. Dejar abiertas las ventanas del backend y Node.
5. Abrir `EVIDENCIAS.bat`. Ejecuta solicitudes reales y hace una pausa por captura. No envía ni crea capturas automáticamente. Los registros permanecen en PostgreSQL.
6. Cuando lo indique, abrir Query Tool en **parcial_citas**, ejecutar las consultas de `database/evidencia.sql` una por una y capturar los resultados.
7. Revisar `CONCEPTUAL.md`: contiene las cinco respuestas de la parte conceptual.

Si falla la conexión: comprobar servidor, usuario, contraseña y que exista `parcial_citas`. Si un puerto está ocupado, cerrar la ejecución anterior antes de volver a iniciar. Los errores aparecen en las ventanas de los servicios.

## Ejecutar por separado o desde el código fuente

### Backend
Desde `backend`, configurar las variables de entorno y ejecutar:

```bat
set DB_URL=jdbc:postgresql://localhost:5432/parcial_citas
set DB_USER=postgres
set DB_PASSWORD=TU_CONTRASENA
java -jar citas.jar
```

Para compilar desde el código, usar `mvnw.cmd clean package` en Windows (`./mvnw clean package` en Linux/macOS), con JDK 17+ e internet. Ejecutar el resultado con `java -jar target/citas-0.0.1-SNAPSHOT.jar`. Alternativa: `mvnw.cmd spring-boot:run`.

Origen: Spring Initializr, Maven, Java 17, Spring Web, Spring Data JPA, PostgreSQL Driver y Validation. Spring Boot 4 usa el starter Web MVC para Spring Web. Las tablas y claves foráneas se generan desde las entidades con `spring.jpa.hibernate.ddl-auto=update`.

### Node.js
En otra terminal:

```bat
cd node-service
npm ci
npm start
```

Consultar `http://localhost:3000/resumen-citas`. La API consumida puede configurarse con `SPRING_API_URL`; por defecto es `http://localhost:8080`. El puerto Node puede configurarse con `NODE_PORT`.

## Rutas CRUD

| Recurso | Ruta base | Operaciones |
|---|---|---|
| Clientes | /api/clientes | POST, GET, GET /{id}, PUT /{id}, DELETE /{id} |
| Empleados | /api/empleados | POST, GET, GET /{id}, PUT /{id}, DELETE /{id} |
| Servicios | /api/servicios | POST, GET, GET /{id}, PUT /{id}, DELETE /{id} |
| Horarios | /api/horarios | POST, GET, GET /{id}, PUT /{id}, DELETE /{id} |
| Citas | /api/citas | POST, GET, GET /{id}, PUT /{id}, DELETE /{id} |

DetalleCita se crea, consulta, actualiza y elimina mediante los detalles de la cita. PUT /api/citas/{id} reemplaza sus detalles; DELETE elimina la cita y sus detalles. No se permite eliminar clientes, empleados o servicios que todavía tengan referencias.

### Crear o actualizar una cita

```json
{
  "fecha": "2026-10-26",
  "hora": "09:00:00",
  "clienteId": 1,
  "empleadoId": 1,
  "detalles": [
    {"servicioId": 1, "cantidad": 2},
    {"servicioId": 2, "cantidad": 1}
  ]
}
```

Los IDs deben corresponder a registros existentes. En POST, estado omitido equivale a PENDIENTE. También se admite ATENDIDA o CANCELADA. Para cancelar, enviar PUT con los mismos datos y `"estado":"CANCELADA"`. Subtotal y total son calculados en Service; no se reciben del cliente. Precio 15.50 por dos unidades más precio 20 por una unidad produce 51.00.

Una cita activa es una cita cuyo estado es distinto de CANCELADA. El empleado no puede tener otra cita activa en la misma fecha y hora. La regla se comprueba tanto al crear como al actualizar o atender. Se bloquea la fila del empleado durante la transacción para evitar reservas simultáneas. La regla exige igualdad de fecha y hora; duración y horarios se almacenan según el modelo pedido.

### Endpoints especiales

- GET /api/citas/pendientes
- GET /api/citas/cliente/{id}
- GET /api/citas/empleado/{id}
- PUT /api/citas/{id}/atender (sin cuerpo)
- GET http://localhost:3000/resumen-citas

Ingreso total = suma de totales de citas ATENDIDAS. Pendientes y canceladas no generan ingreso. Después de una ejecución de evidencias en una base vacía: totalCitas 3, pendientes 1, atendidas 1, canceladas 1, ingresoTotal 51. Repetir las evidencias agrega nuevos registros y el resumen aumenta.

## Evidencias solicitadas

El archivo EVIDENCIAS.bat guía los POST, GET, PUT, DELETE, cuatro endpoints especiales, conflicto HTTP 409, resumen Node, consultas PostgreSQL e historial real de commits. Las capturas deben tomarse en la máquina donde se ejecuta. Revisar el código y las respuestas conceptuales para poder explicar cada parte y realizar la modificación que solicite el docente.

## Git y GitHub

La carpeta `.git` incluye el historial de preparación del proyecto, con autor identificado como `Preparacion del parcial`; no representa trabajo previo del estudiante. Para verlo, abrir una terminal en la carpeta raíz:

```bash
git log --oneline --reverse
```

Para subirlo a tu cuenta, crear un repositorio **vacío** en GitHub y ejecutar (reemplazar la URL):

```bash
git branch -M main
git remote add origin https://github.com/TU_USUARIO/parcial-programacion4.git
git push -u origin main
```

El ZIP incluye el proyecto y los commits locales. La publicación en GitHub y las capturas se hacen desde tu cuenta y tu equipo. No guardar contraseñas en el repositorio.

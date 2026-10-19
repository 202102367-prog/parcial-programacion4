$ErrorActionPreference = 'Stop'
$api = 'http://localhost:8080'
function Solicitud($metodo, $ruta, $datos = $null) {
    $p = @{Method=$metodo; Uri="$api$ruta"; UseBasicParsing=$true}
    if ($null -ne $datos) { $p.Body = [System.Text.Encoding]::UTF8.GetBytes(($datos | ConvertTo-Json -Depth 10)); $p.ContentType='application/json; charset=utf-8' }
    Write-Host "`n$metodo $ruta" -ForegroundColor Cyan
    $r = Invoke-WebRequest @p
    Write-Host "HTTP $($r.StatusCode)"
    if ($r.Content) { Write-Host (($r.Content | ConvertFrom-Json) | ConvertTo-Json -Depth 10) }
    if ($r.Content) { return ($r.Content | ConvertFrom-Json) }
}
function Captura($texto) { Write-Host "`n$texto" -ForegroundColor Yellow; Read-Host 'Toma la captura y pulsa Enter' | Out-Null }
try {
    Write-Host 'PRUEBAS DEL PARCIAL - PROGRAMACION 4' -ForegroundColor Green
    Write-Host 'Crea registros nuevos, sin borrar los que ya existen. Se puede repetir.'
    $cliente = Solicitud POST '/api/clientes' @{nombre='Cesia Henríquez';correo='cesia@example.com';telefono='70001234'}
    $empleado = Solicitud POST '/api/empleados' @{nombre='Ana López';especialidad='Servicios generales';activo=$true}
    $s1 = Solicitud POST '/api/servicios' @{nombre='Servicio A';duracionMinutos=30;precio=15.50}
    $s2 = Solicitud POST '/api/servicios' @{nombre='Servicio B';duracionMinutos=45;precio=20}
    $horario = Solicitud POST '/api/horarios' @{diaSemana='MONDAY';horaInicio='08:00:00';horaFin='17:00:00';empleadoId=$empleado.id}
    Captura 'EVIDENCIA 1: altas de cliente, empleado, servicios y horario (POST).'
    $datos = @{fecha='2026-10-26';hora='09:00:00';clienteId=$cliente.id;empleadoId=$empleado.id;detalles=@(@{servicioId=$s1.id;cantidad=2},@{servicioId=$s2.id;cantidad=1})}
    $cita = Solicitud POST '/api/citas' $datos
    if ($cita.total -ne 51) { throw 'El total esperado es 51.00' }
    Captura 'EVIDENCIA 2: cita con varios servicios. Total automatico: 2 x 15.50 + 20 = 51.00.'
    Write-Host "`nPOST /api/citas - duplicado intencional" -ForegroundColor Cyan
    try {
        Invoke-WebRequest -UseBasicParsing -Method POST -Uri "$api/api/citas" -ContentType 'application/json' -Body ($datos | ConvertTo-Json -Depth 10) | Out-Null
        throw 'La cita duplicada fue aceptada incorrectamente.'
    } catch {
        if (-not $_.Exception.Response -or [int]$_.Exception.Response.StatusCode -ne 409) { throw }
        Write-Host 'HTTP 409 - cita rechazada por disponibilidad' -ForegroundColor Green
        Write-Host $_.ErrorDetails.Message
    }
    Captura 'EVIDENCIA 3: regla de negocio, empleado ocupado en la misma fecha y hora.'
    $null = Solicitud GET '/api/citas/pendientes'
    Captura 'EVIDENCIA 4: GET citas pendientes.'
    $null = Solicitud GET "/api/citas/cliente/$($cliente.id)"
    Captura 'EVIDENCIA 5: GET citas de un cliente.'
    $null = Solicitud GET "/api/citas/empleado/$($empleado.id)"
    Captura 'EVIDENCIA 6: GET citas de un empleado.'
    $null = Solicitud PUT "/api/citas/$($cita.id)/atender"
    Captura 'EVIDENCIA 7: PUT atender, estado ATENDIDA.'
    $datos.hora='10:00:00'; $pendiente = Solicitud POST '/api/citas' $datos
    $datos.hora='11:00:00'; $datos.estado='CANCELADA'; $cancelada=Solicitud POST '/api/citas' $datos
    $temporal = Solicitud POST '/api/clientes' @{nombre='Cliente temporal';correo='temporal@example.com';telefono='70000000'}
    $null = Solicitud PUT "/api/clientes/$($temporal.id)" @{nombre='Cliente actualizado';correo='temporal@example.com';telefono='71111111'}
    $null = Solicitud GET "/api/clientes/$($temporal.id)"
    $null = Solicitud DELETE "/api/clientes/$($temporal.id)"
    Captura 'EVIDENCIA 8: PUT, GET y DELETE de cliente temporal. DELETE devuelve HTTP 204.'
    Write-Host "`nGET http://localhost:3000/resumen-citas" -ForegroundColor Cyan
    $resumen=Invoke-RestMethod 'http://localhost:3000/resumen-citas'
    $resumen | ConvertTo-Json | Write-Host
    Captura 'EVIDENCIA 9: resumen procesado con Express y Axios. En una base vacia: 3 citas, 1 pendiente, 1 atendida, 1 cancelada, ingreso 51.'
    Write-Host 'Ejecuta database/evidencia.sql en pgAdmin y captura los registros de las seis tablas.'
    Captura 'EVIDENCIA 10: PostgreSQL y registros de prueba.'
    if (Get-Command git -ErrorAction SilentlyContinue) {
        git -C $PSScriptRoot log --oneline --reverse
    } else { Write-Host 'Instala Git para mostrar el historial real incluido en la carpeta .git.' }
    Captura 'EVIDENCIA 11: historial de commits.'
    Write-Host 'Pruebas completadas.' -ForegroundColor Green
} catch { Write-Host "ERROR: $_" -ForegroundColor Red; exit 1 }

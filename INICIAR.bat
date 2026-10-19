@echo off
chcp 65001 >nul
cd /d "%~dp0"
where java >nul 2>nul || (echo Instala Java 17 o superior y vuelve a intentar. & pause & exit /b 1)
where node >nul 2>nul || (echo Instala Node.js LTS y vuelve a intentar. & pause & exit /b 1)
where npm >nul 2>nul || (echo No se encontro npm. Reinstala Node.js. & pause & exit /b 1)
set "DB_URL=jdbc:postgresql://localhost:5432/parcial_citas"
set "DB_USER=postgres"
set /p "DB_USER=Usuario PostgreSQL [Enter = postgres]: "
for /f "usebackq delims=" %%P in (`powershell -NoProfile -Command "$s=Read-Host 'Contrasena PostgreSQL' -AsSecureString; [System.Net.NetworkCredential]::new('', $s).Password"`) do set "DB_PASSWORD=%%P"
echo La base parcial_citas debe existir. Si no existe, ejecuta database\script.sql en pgAdmin.
if not exist "node-service\node_modules\express" (
    pushd node-service
    call npm ci --no-audit --no-fund
    if errorlevel 1 (popd & pause & exit /b 1)
    popd
)
start "Backend Spring Boot" cmd /k call "%~dp0backend\EJECUTAR.bat"
start "Node Express Axios" cmd /k call "%~dp0node-service\EJECUTAR.bat"
echo Esperando al backend...
powershell -NoProfile -Command "$ok=$false; for($i=0;$i -lt 90;$i++){try{Invoke-RestMethod 'http://localhost:8080/api/clientes' -TimeoutSec 2 ^| Out-Null; $ok=$true; break}catch{Start-Sleep -Seconds 2}}; if(-not $ok){Write-Host 'No inicio. Revisa el error en la ventana del backend.'; exit 1}"
if errorlevel 1 (pause & exit /b 1)
echo Listo. Ejecuta EVIDENCIAS.bat para probar y tomar capturas.
pause

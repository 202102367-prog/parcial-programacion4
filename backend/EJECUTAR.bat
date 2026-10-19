@echo off
cd /d "%~dp0"
if exist "citas.jar" (
 java -jar citas.jar
) else (
 call mvnw.cmd spring-boot:run
)

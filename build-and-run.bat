@echo off
echo Compilando microservicio...
cd atm-microservice
call mvn clean package -DskipTests

echo Construyendo imagen Docker...
cd ..  REM
docker build -t atm-microservice .

echo Ejecutando microservicio en http://localhost:8080
docker run -p 8080:8080 atm-microservice

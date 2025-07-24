#!/bin/bash

echo "Compilando microservicio..."
cd atm-microservice || exit
mvn clean package -DskipTests

echo "Construyendo imagen Docker..."
cd .. || exit
docker build -t atm-microservice .

echo "Ejecutando microservicio en http://localhost:8080"
docker run -p 8080:8080 atm-microservice

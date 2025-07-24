# 🏧 Producto ATM – Challenge JAVA

Aplicación de consola (CLI) que simula un cajero automático. Permite realizar operaciones básicas como login, consulta de saldo, extracción y depósito, consumiendo un microservicio desarrollado con Java y Spring Boot.

## 🧰 Tecnologías utilizadas

### 🖥️ Aplicación de consola (cliente)
- **Java 17** – Versión LTS moderna
- **Spring Framework** (spring-web) – Para consumir APIs REST desde la consola
- **Jackson**, **Hibernate Validator**, **Lombok** – Utilidades para serialización, validación y reducción de código repetitivo
- **Maven** – Gestión de dependencias y construcción del proyecto

### 🔧 Microservicio
- **Spring Boot 3.2.5** – Framework principal del microservicio
- **Java 17**
- **Spring Web** – Exposición de endpoints REST
- **Spring Data JPA** – Acceso a la base de datos con JPA/Hibernate
- **H2** – Base de datos en memoria para desarrollo y pruebas
- **Docker** – Containerización del microservicio
- **Maven** – Gestión de dependencias y construcción

## 📁 Estructura del proyecto

```
producto-atm/
├── atm-console/          → Aplicación CLI que consume el microservicio
├── atm-microservice/     → Microservicio REST con endpoints de operaciones bancarias
├── build-and-run.sh      → Script de construcción y ejecución (Linux/macOS)
├── build-and-run.bat     → Script de construcción y ejecución (Windows)
├── run.sh               → Script para ejecutar CLI (Linux/macOS)
└── run.bat              → Script para ejecutar CLI (Windows)
```

## 🚀 Instalación y ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- Docker (opcional, pero recomendado)

### 1️⃣ Ejecutar el microservicio

#### Opción A: Usando Docker (recomendado)

**Linux/macOS:**
```bash
# Hacer ejecutable el script
chmod +x build-and-run.sh

# Ejecutar
./build-and-run.sh
```

**Windows:**
```batch
# Ejecutar directamente
build-and-run.bat
```

El microservicio estará disponible en `http://localhost:8080`

#### Opción B: Sin Docker

```bash
cd atm-microservice
mvn spring-boot:run
```

### 2️⃣ Ejecutar la aplicación de consola

**⚠️ Importante:** Asegurate que el microservicio esté corriendo antes de ejecutar la consola.

**Linux/macOS:**
```bash
# Hacer ejecutable el script
chmod +x run.sh

# Ejemplos de uso
./run.sh login 1111-2222-3333
./run.sh saldo 1111-2222-3333 CBU-0001
./run.sh extraer 1111-2222-3333 CBU-0001 1000
./run.sh depositar 1111-2222-3333 CBU-0001 500
```

**Windows:**
```batch
# Ejemplos de uso
run.bat login 1111-2222-3333
run.bat saldo 1111-2222-3333 CBU-0001
run.bat extraer 1111-2222-3333 CBU-0001 1000
run.bat depositar 1111-2222-3333 CBU-0001 500
```


## 🏗️ Decisiones de diseño

### Arquitectura
- **Separación en módulos**: Microservicio independiente y cliente CLI separados para facilitar mantenimiento y escalabilidad
- **Comunicación REST**: Uso de `RestTemplate` para comunicación HTTP entre consola y API
- **Base de datos en memoria**: H2 con carga inicial vía `data.sql` para simplificar el setup

### Validaciones implementadas
- **Login**: Verifica existencia y estado activo de la tarjeta
- **Extracción**: Valida saldo suficiente para evitar saldo negativo
- **Depósito**: Permite acreditación en cualquier cuenta válida
- **Consulta de saldo**: Exige que la cuenta esté vinculada a la tarjeta

### Cumplimiento de requisitos no funcionales

#### Criticidad del servicio
- Manejo robusto de errores con códigos HTTP apropiados
- Validaciones de datos antes de procesar transacciones
- Mensajes de error claros para facilitar el diagnóstico

#### Trazabilidad de transacciones
- Logging detallado de todas las operaciones en consola
- Estructura de datos que permite auditoría de saldos
- Timestamp implícito en logs para seguimiento temporal

## ⚠️ Limitaciones y concesiones

### Por limitaciones de tiempo
- **Testing limitado**: No se implementaron tests unitarios ni de integración comprehensivos
- **Validaciones básicas**: Validaciones de entrada mínimas en la aplicación CLI
- **Logs básicos**: No se implementó sistema de logs persistentes ni trazabilidad avanzada


## ⏱️ Tiempo de desarrollo

| Actividad | Horas estimadas |
|-----------|----------------|
| Análisis y diseño | 2h |
| Desarrollo microservicio | 6h |
| Desarrollo CLI | 4h |
| Scripts y Docker | 1h |
| **Total aproximado** | **13h** |

## 📋 API Endpoints

El microservicio expone los siguientes endpoints:

- `POST /api/login` - Autenticación de tarjeta
- `GET /api/saldo/{tarjeta}/{cbu}` - Consulta de saldo
- `POST /api/extraer` - Extracción de dinero
- `POST /api/depositar` - Depósito de dinero

### 📘 Documentación Swagger

La documentación de la API está disponible automáticamente en:

👉 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

Proporcionada mediante **SpringDoc OpenAPI** (`springdoc-openapi-starter-webmvc-ui`)

## 🔗 Repositorio

**URL pública**: https://github.com/MatiasSilvaIslas/producto-atm/tree/master

---

*Desarrollado como parte del challenge técnico Java - Sistema ATM*
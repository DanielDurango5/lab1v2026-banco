[![CI/CD Pipeline](https://github.com/DanielDurango5/lab1v2026-banco/actions/workflows/build.yml/badge.svg)](https://github.com/DanielDurango5/lab1v2026-banco/actions/workflows/build.yml)
[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=coverage)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Maintainability issues](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=software_quality_maintainability_issues)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Reliability issues](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=software_quality_reliability_issues)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Security issues](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=software_quality_security_issues)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DanielDurango5_lab1v2026-banco&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=DanielDurango5_lab1v2026-banco)

# Lab 1v2026 - Banco

API REST de un banco sencillo desarrollada con Spring Boot, con las siguientes operaciones:

* Crear un cliente
* Consultar todos los clientes
* Consultar un cliente por ID
* Realizar una transferencia entre cuentas
* Consultar las transacciones de una cuenta

Incluye integración continua con GitHub Actions.

### Tecnologías

* Java 17
* Spring Boot (Web, Data JPA, Validation)
* MySQL
* MapStruct y Lombok
* JUnit 5 y Mockito
* GitHub Actions

### Endpoints

| Método | Ruta                                | Descripción                              |
|--------|-------------------------------------|------------------------------------------|
| GET    | `/api/customers`                    | Lista todos los clientes                 |
| GET    | `/api/customers/{id}`               | Obtiene un cliente por su ID             |
| POST   | `/api/customers`                    | Crea un cliente                          |
| POST   | `/api/transactions`                 | Transfiere dinero entre dos cuentas      |
| GET    | `/api/transactions/{accountNumber}` | Lista las transacciones de una cuenta    |

### Estructura de carpetas

* `Backend/lab1v20206/src/main`: código principal de la API (`controller`, `DTO`, `entity`, `mapper`, `repository`, `service`)
* `Backend/lab1v20206/src/test`: pruebas unitarias
* `Frontend`: interfaz de la aplicación
* `.github/workflows`: pipeline de CI/CD

### Cómo ejecutarlo

Requiere Java 17 y MySQL en `localhost:3306` con una base de datos llamada `lab1v2026` creada previamente. La conexión se configura en `src/main/resources/application.properties` (las tablas se generan automáticamente con Hibernate).

```shell
cd Backend/lab1v20206
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8088`.

### Cómo ejecutar las pruebas

```shell
cd Backend/lab1v20206
./mvnw clean test
```

### Pipeline de CI/CD

El workflow `.github/workflows/build.yml` se ejecuta en cada push a `main` y a las ramas `feature/**`, en los pull requests hacia `main` y de forma manual. Actualmente ejecuta las pruebas unitarias con `mvn -B test`.
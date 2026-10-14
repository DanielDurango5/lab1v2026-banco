# ==========================================
# Etapa 1: Construcción de la aplicación
# ==========================================
FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar el código fuente (el .dockerignore excluye target, .idea, etc.)
COPY . .

# Compilar y empaquetar la aplicación (los tests ya corren en el pipeline)
RUN mvn -B clean package -DskipTests

# ==========================================
# Etapa 2: Ejecución de la aplicación
# ==========================================
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiar el JAR generado; el comodín evita depender del nombre exacto.
# No toma el .jar.original porque el patrón termina en ".jar".
COPY --from=build /app/target/*.jar app.jar

# Puerto de la aplicación (server.port=8088)
EXPOSE 8088

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
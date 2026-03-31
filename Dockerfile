# Stage 1: Build the JAR usando Maven Wrapper
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiamos Maven Wrapper y configuraciones
COPY mvnw .
COPY .mvn .mvn

# Copiamos pom.xml y código fuente
COPY pom.xml .
COPY src ./src

# Construimos el JAR
RUN chmod +x mvnw && ./mvnw clean package

# Stage 2: Run the app
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copiamos el JAR compilado
COPY --from=build /app/target/Biblioteca-0.0.1.jar app_biblioteca.jar

# Puerto que Railway asigna
EXPOSE 8080

# Inicia la app
ENTRYPOINT ["java", "-jar", "app_biblioteca.jar"]
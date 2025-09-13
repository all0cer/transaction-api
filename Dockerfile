# Stage 1: build
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

# Copia os arquivos de configuração e o pom.xml
COPY pom.xml .
COPY src ./src

# Build da aplicação e criação do JAR
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia o jar gerado do stage de build
COPY --from=build /app/target/api-transacao-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Define variáveis de ambiente (opcional, podem ser sobrescritas)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Comando para rodar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

FROM debian:bullseye-slim AS builder

# Instalar o OpenJDK 22
RUN apt-get update && apt-get install -y openjdk-22-jdk

# Definir o diretório de trabalho
WORKDIR /app

# Compilar a aplicação Java
COPY . /app
RUN ./mvnw clean install

# Nova etapa para criar a imagem final
FROM openjdk:22-jdk

# Copiar o arquivo JAR da aplicação para o contêiner
COPY --from=builder /app/target/SensorCenter-0.0.1-SNAPSHOT.jar /app/sensorcenter.jar

# Expor a porta que a aplicação usa
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/sensorcenter.jar"]

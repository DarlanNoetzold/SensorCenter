# Etapa de construção do JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean install

# Usar uma imagem baseada no Debian com OpenJDK para compilar e executar a aplicação Java
FROM openjdk:22-jdk

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR da aplicação para o contêiner gerado na etapa de build
COPY --from=build /app/target/SensorCenter-0.0.1-SNAPSHOT.jar /app/sensorcenter.jar

# Expor a porta que a aplicação usa
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/sensorcenter.jar"]

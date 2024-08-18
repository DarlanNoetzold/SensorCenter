# Usar uma imagem baseada no Debian para compilar o projeto Java
FROM openjdk:22-jdk AS maven_build

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o código fonte para o contêiner
COPY . /app

# Dar permissão de execução ao script mvnw
RUN chmod +x ./mvnw

# Compilar o projeto Java
RUN ./mvnw clean install -DskipTests

# Nova etapa para criar a imagem final
FROM openjdk:22-jdk

# Copiar o arquivo JAR da aplicação para o contêiner
COPY --from=maven_build /app/target/SensorCenter-0.0.1-SNAPSHOT.jar /app/sensorcenter.jar

# Expor a porta que a aplicação usa
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/sensorcenter.jar"]
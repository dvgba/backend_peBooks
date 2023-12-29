# Primeira fase: Construção
FROM maven:3.8.4-openjdk-17 AS build

# Copiar apenas os arquivos necessários para a construção
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

# Definir o diretório de trabalho
WORKDIR /usr/src/app

# Executar a construção do projeto
RUN mvn clean package

# Segunda fase: Execução
FROM openjdk:17-jdk-slim

# Expor a porta em que a aplicação está ouvindo
EXPOSE 8080

# Copiar o arquivo JAR construído na primeira fase
COPY --from=build /usr/src/app/target/pebooks-0.0.1-SNAPSHOT.jar app.jar

# Definir o comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

#TODO COntinuar a Instalação do docker e renderizar
# ======================
# Stage 1: Build (Compilação)
# ======================
FROM maven:3.9.7-eclipse-temurin-17 AS build

WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar as dependências (cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Agora copia o código fonte e gera o jar
COPY src ./src
RUN mvn package -DskipTests

# ======================
# Stage 2: Runtime (Execução)
# ======================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Configuração de fuso horário (Importante para o seu Scheduler de renovação)
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Se você NÃO tiver o agente APM ou o entrypoint.sh ainda,
# comente as linhas abaixo para o build não falhar.
# COPY --from=build /app/lib/elastic-apm-agent-1.54.0.jar /app/lib/elastic-apm-agent.jar
# COPY --from=build /app/entrypoint.sh /app/entrypoint.sh
# RUN chmod +x /app/entrypoint.sh

# Copia o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar /app/app.jar

# Expõe a porta que definimos no application.properties
EXPOSE 8080

# Comando para rodar a aplicação
# Se usar entrypoint.sh: ENTRYPOINT [ "/app/entrypoint.sh" ]
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
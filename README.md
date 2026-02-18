# 🎬 Streaming Subscription Service

Este projeto é um microserviço robusto para gestão de assinaturas de streaming, desenvolvido com **Java 17** e **Spring Boot 3**. A solução utiliza **Arquitetura Hexagonal** para garantir o desacoplamento total entre a lógica de negócio e as tecnologias externas (MongoDB, Kafka e APIs).

---

## 🛠️ Infraestrutura (Docker Compose)

Para facilitar o setup, toda a infraestrutura necessária está contida no arquivo `docker-compose.yml`. O projeto utiliza MongoDB para persistência e Kafka para mensageria.

```yaml
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    container_name: streaming-mongo
    ports:
      - "27017:27017"
    networks:
      - streaming-net

  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
    networks:
      - streaming-net

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    networks:
      - streaming-net

networks:
  streaming-net:
    driver: bridge
⚙️ Configurações da Aplicação (Properties)
O sistema está configurado para rodar na porta 8087 (ajustada para evitar conflitos) com o context-path /api.

Properties
spring.application.name=subscription-service
server.port=8087
server.servlet.context-path=/api

# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/streaming_db

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.group-id=streaming-group
spring.kafka.consumer.properties.spring.json.trusted.packages=br.com.streaming.*

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
📖 Documentação da API (Swagger/OpenAPI)
A API é auto-documentada. Ao rodar o projeto, você pode testar todos os endpoints de forma visual.

🔗 Link de Acesso: http://localhost:8087/api/swagger-ui/index.html

Principais Endpoints:
Criar Assinatura: POST /v1/subscriptions

Cancelar Assinatura: PATCH /v1/subscriptions/{id}/cancel

🧠 Lógica de Domínio (Resiliência de Pagamento)
O coração do projeto implementa a regra de 3 tentativas de falha antes da suspensão automática:

Java
public void recordPaymentFailure() {
    this.renewalAttempts++;
    if (this.renewalAttempts >= 3) {
        this.status = SubscriptionStatus.SUSPENDED;
        // O sistema dispara um evento Kafka após esta mudança de estado
    }
}
🏗️ Estrutura Hexagonal
O projeto está organizado para garantir que o "Core" seja independente:

core.domain: Entidades puras e regras de negócio.

ports.inbound: Interfaces de entrada (Use Cases).

ports.outbound: Interfaces de saída (Repository, Kafka).

infrastructure.adapters: Implementações reais (MongoDB, Rest, Kafka).

🚀 Como Executar
Opção A: Execução Local (Maven)
Subir Infraestrutura: docker-compose up -d

Rodar Aplicação: ./mvnw spring-boot:run

Opção B: Execução via Docker (Dockerfile)
Gerar a imagem da aplicação:

Bash
docker build -t streaming-app .
Rodar o container mapeando a porta correta:

Bash
docker run -p 8087:8087 --network=host streaming-app
👨‍💻 Autor
David Oliveira - Software Engineer

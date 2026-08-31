# Ammazon Enterprise E-Commerce Platform

## 🏢 Overview

Ammazon is a **production-grade, cloud-native enterprise Java microservices platform** designed to serve millions of users. This project demonstrates best practices in:

- **Java 21** with Virtual Threads (Project Loom)
- **Spring Boot 3.2** & **Spring Cloud** latest versions
- **Microservices Architecture** with independent services
- **Reactive Programming** with Project Reactor & WebFlux
- **Event-Driven Architecture** with Apache Kafka
- **Distributed Transactions** using Saga Pattern
- **Container Orchestration** with Kubernetes & Docker
- **Observability** with Prometheus, Grafana, Jaeger
- **Security** with Spring Security, JWT, OAuth2
- **Resilience** with Resilience4j patterns

## 🏗️ Architecture

### Microservices

1. **API Gateway** - Entry point, authentication, routing, rate limiting
2. **Config Server** - Centralized configuration management
3. **Eureka Server** - Service discovery and registration
4. **Auth Service** - JWT token generation, OAuth2, authentication
5. **User Service** - User profile management, registration
6. **Product Service** - Product catalog and management
7. **Inventory Service** - Stock management with eventual consistency
8. **Cart Service** - Shopping cart operations (reactive)
9. **Order Service** - Order processing with saga pattern
10. **Payment Service** - Payment processing with idempotency
11. **Notification Service** - Email, SMS, push notifications
12. **Search Service** - Elasticsearch integration for product search
13. **Analytics Service** - Real-time analytics with Kafka Streams
14. **Shipping Service** - Shipping management and tracking

## 🛠️ Technology Stack

### Core
- Java 21
- Spring Boot 3.2
- Spring Cloud 2023.0
- Maven 3.9+

### Data
- PostgreSQL 15+ (transactional)
- MongoDB 6.0+ (document store)
- Redis 7.0+ (caching & sessions)
- Elasticsearch 8.0+ (search)

### Messaging & Events
- Apache Kafka 3.5+
- Zookeeper (Kafka coordination)

### Container & Orchestration
- Docker & Docker Compose
- Kubernetes 1.27+
- Helm 3.0+

### Observability
- Prometheus (metrics)
- Grafana (visualization)
- OpenTelemetry (tracing)
- Jaeger (distributed tracing)
- Logback & SLF4J (logging)
- ELK Stack (log aggregation)

### Security
- Spring Security 6.2
- JWT (jsonwebtoken 0.12)
- OAuth2
- Vault (secrets management)

### Resilience
- Resilience4j 2.1.0
- Spring Retry

## 📁 Project Structure

```
ammazon-enterprise-ecommerce/
├── ammazon-commons/                 # Shared constants, enums
├── ammazon-shared-libs/             # Common utilities, DTOs, exceptions
├── ammazon-config-server/           # Spring Cloud Config Server
├── ammazon-eureka-server/           # Service Discovery (Eureka)
├── ammazon-api-gateway/             # Spring Cloud Gateway
├── ammazon-auth-service/            # Authentication & Authorization
├── ammazon-user-service/            # User Management
├── ammazon-product-service/         # Product Catalog
├── ammazon-inventory-service/       # Inventory Management
├── ammazon-cart-service/            # Shopping Cart (Reactive)
├── ammazon-order-service/           # Order Processing
├── ammazon-payment-service/         # Payment Processing
├── ammazon-notification-service/    # Notifications
├── ammazon-search-service/          # Search with Elasticsearch
├── ammazon-analytics-service/       # Real-time Analytics
├── ammazon-shipping-service/        # Shipping Management
├── docker-compose.yml               # Local development environment
├── k8s/                             # Kubernetes manifests
├── helm/                            # Helm charts
├── docs/                            # Architecture & API documentation
└── README.md                        # This file
```

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose
- Java 21 JDK
- Maven 3.9+
- Kubernetes cluster (for deployment)
- Git

### Local Development Setup

```bash
# Clone repository
git clone https://github.com/myllm135795-eng/Ammazon-enterprise-ecommerce-platform.git
cd Ammazon-enterprise-ecommerce-platform

# Start infrastructure (Kafka, PostgreSQL, MongoDB, Redis, etc.)
docker-compose up -d

# Build all modules
mvn clean install -DskipTests

# Start individual services
cd ammazon-eureka-server && mvn spring-boot:run
cd ammazon-config-server && mvn spring-boot:run
cd ammazon-api-gateway && mvn spring-boot:run
# ... start other services
```

### Access Points

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Eureka Dashboard**: http://localhost:8761
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)
- **Kafka UI**: http://localhost:8080/kafka-ui
- **Elasticsearch**: http://localhost:9200

## 📊 Architecture Diagrams

### System Context (C4 Model)
```
┌─────────────────────────────────────────────────────────┐
│                     Clients                              │
│  (Web Browser, Mobile App, Desktop Client)              │
└──────────────────┬──────────────────────────────────────┘
                   │ HTTPS
┌──────────────────▼──────────────────────────────────────┐
│              API Gateway                                 │
│  (Spring Cloud Gateway)                                  │
│  - Auth, Rate Limiting, Routing                          │
└──────────────────┬──────────────────────────────────────┘
                   │
     ┌─────────────┼─────────────┐
     │             │             │
     ▼             ▼             ▼
  ┌──────┐    ┌──────┐    ┌──────────┐
  │Auth  │    │User  │    │Product   │
  │Svc   │    │Svc   │    │Service   │
  └──────┘    └──────┘    └──────────┘
     │             │             │
     └─────────────┼─────────────┘
                   │
     ┌─────────────┴─────────────┐
     │                           │
     ▼                           ▼
  ┌──────────┐            ┌────────────┐
  │PostgreSQL│            │  MongoDB   │
  │  (User)  │            │  (Products)│
  └──────────┘            └────────────┘
     │
     ▼
  ┌──────────┐
  │  Kafka   │
  │  Events  │
  └──────────┘
```

## 🔐 Security

### Authentication & Authorization
- JWT-based authentication
- OAuth2 support
- Role-Based Access Control (RBAC)
- API Keys for service-to-service communication
- Vault integration for secrets

### API Security
- HTTPS/TLS encryption
- CORS configuration
- CSRF protection
- Secure headers
- Rate limiting

## 📈 Observability

### Metrics
- JVM metrics (memory, GC, threads)
- HTTP request metrics
- Database query metrics
- Kafka producer/consumer metrics
- Custom business metrics

### Tracing
- OpenTelemetry instrumentation
- Distributed tracing with Jaeger
- Correlation IDs across services

### Logging
- Structured JSON logging
- Correlation IDs (MDC)
- ELK Stack for centralized logging
- Log levels by environment

## 🧪 Testing

### Test Coverage
- Unit tests with JUnit 5 & Mockito
- Integration tests with Testcontainers
- Contract tests with Spring Cloud Contract
- API tests with REST Assured
- Performance tests with JMH

```bash
# Run all tests
mvn test

# Run integration tests
mvn verify -Pintegration-tests

# Generate coverage report
mvn jacoco:report
```

## 🐳 Docker & Kubernetes

### Docker Compose (Local Development)
```bash
docker-compose up -d
docker-compose ps
docker-compose down
```

### Kubernetes Deployment
```bash
# Using Helm
helm install ammazon ./helm/ammazon-platform -n production

# Using kubectl
kubectl apply -f k8s/

# Check status
kubectl get pods -n production
kubectl get svc -n production
```

## 📚 Documentation

- [Architecture Guide](./docs/ARCHITECTURE.md)
- [API Documentation](./docs/API.md)
- [Deployment Guide](./docs/DEPLOYMENT.md)
- [Database Schema](./docs/DATABASE.md)
- [Kafka Topics](./docs/KAFKA.md)
- [Development Guide](./docs/DEVELOPMENT.md)
- [Common Interview Questions](./docs/FAQ.md)

## 🔄 CI/CD Pipeline

GitHub Actions workflow:
1. **Build**: Compile and package Maven artifacts
2. **Test**: Run unit and integration tests
3. **Code Quality**: SonarQube analysis
4. **Docker Build**: Build and push Docker images
5. **Deploy**: Deploy to Kubernetes

## 💡 Key Design Decisions

### Why Java 21?
- Virtual Threads (Project Loom) for scalability
- Record classes for DTOs
- Sealed classes for domain models
- Pattern matching for clean code
- Better GC and performance

### Why Microservices?
- Independent scaling and deployment
- Technology diversity per service
- Team autonomy
- Fault isolation
- Database per service pattern

### Why Kafka?
- Event-driven architecture
- Asynchronous communication
- Event replay capability
- Exactly-once semantics
- Real-time analytics

### Why Kubernetes?
- Container orchestration
- Self-healing
- Auto-scaling
- Rolling updates
- Service discovery

## 🎯 Performance Optimization

- Virtual Threads for high concurrency
- Reactive programming (Project Reactor)
- Connection pooling (HikariCP)
- Caching strategies (Redis, Caffeine)
- Compression (gzip)
- Async processing
- Database indexing
- Query optimization

## 🚦 Resilience Patterns

- **Circuit Breaker**: Prevent cascading failures
- **Retry**: Automatic retry with backoff
- **Rate Limiter**: Control request flow
- **Bulkhead**: Isolate resources
- **Time Limiter**: Prevent hanging requests
- **Fallback**: Graceful degradation

## 📋 Deployment Checklist

- [ ] All services containerized with Dockerfile
- [ ] Kubernetes manifests created and tested
- [ ] Secrets stored in Vault
- [ ] Monitoring configured (Prometheus, Grafana)
- [ ] Logging aggregation enabled (ELK)
- [ ] Tracing configured (Jaeger)
- [ ] Database migrations applied
- [ ] Load testing completed
- [ ] Security scanning passed (SonarQube)
- [ ] Documentation updated

## 🤝 Contributing

This is an educational project demonstrating enterprise Java patterns. Contributions are welcome!

1. Fork the repository
2. Create a feature branch
3. Follow the code style guide
4. Write tests for new features
5. Submit a pull request

## 📝 License

MIT License - See LICENSE file

## 👨‍💼 Author

Built as a comprehensive guide for enterprise Java architecture and microservices design.

## 🔗 Resources

- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Microservices Patterns](https://microservices.io/)
- [Kubernetes Documentation](https://kubernetes.io/docs/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [OpenTelemetry](https://opentelemetry.io/)

---

**Last Updated**: 2024
**Status**: Active Development
**Java Version**: 21+
**Spring Boot**: 3.2+
**Spring Cloud**: 2023.0+
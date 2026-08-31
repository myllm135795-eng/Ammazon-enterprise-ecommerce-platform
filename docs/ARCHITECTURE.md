# Architecture Documentation

## System Architecture Overview

Ammazon is built following microservices architecture principles with the following key characteristics:

### 1. Service Independence
- Each microservice is independently deployable
- Services have their own databases (Database per Service pattern)
- Loose coupling between services
- Each service has single responsibility

### 2. Communication Patterns

#### Synchronous Communication
- REST APIs via HTTP/2
- OpenFeign for service-to-service calls
- WebClient for reactive calls
- gRPC for high-performance communication

#### Asynchronous Communication
- Apache Kafka for event streaming
- Event-driven architecture
- Publish-Subscribe pattern
- Event replay capability

### 3. Data Management

#### Database Strategy
- **PostgreSQL**: Transactional data (Users, Orders, Payments)
- **MongoDB**: Document-oriented data (Products, Reviews)
- **Redis**: Caching, Sessions, Real-time data
- **Elasticsearch**: Full-text search and analytics

#### Consistency Patterns
- Immediate consistency for critical operations
- Eventual consistency for distributed transactions
- Saga pattern for multi-service transactions

### 4. API Gateway Pattern

Spring Cloud Gateway serves as the single entry point:
- Authentication and Authorization
- JWT validation
- Rate limiting
- Request routing
- CORS handling
- Request/Response logging
- Circuit breaking

### 5. Service Discovery

Eureka-based service discovery:
- Automatic service registration
- Client-side load balancing
- Health checking
- Dynamic routing

### 6. Configuration Management

Spring Cloud Config Server:
- Centralized configuration
- Environment-specific configurations
- Runtime configuration updates
- Audit trail of changes

## Microservices Deep Dive

### Authentication Service

**Responsibilities**:
- User registration and login
- JWT token generation
- Token validation
- Refresh token management
- OAuth2 integration
- Password management

**Technologies**:
- Spring Security
- JWT (io.jsonwebtoken)
- OAuth2 Client
- Bcrypt for password encryption

**Database**: PostgreSQL (user credentials, tokens)
**Caching**: Redis (token blacklist, sessions)

### User Service

**Responsibilities**:
- User profile management
- User preferences
- Address management
- User deactivation/deletion
- Audit logging

**Technologies**:
- Spring Data JPA
- PostgreSQL
- Kafka for events

**Events Published**:
- UserRegisteredEvent
- UserProfileUpdatedEvent
- UserDeletedEvent

### Product Service

**Responsibilities**:
- Product catalog management
- Product information
- Product categories
- Pricing and discounts
- Product reviews and ratings

**Technologies**:
- Spring Data MongoDB
- Elasticsearch for search
- Redis for caching

**Database**: MongoDB (product data, reviews)
**Cache**: Redis (frequently accessed products)
**Search**: Elasticsearch (full-text search)

### Inventory Service

**Responsibilities**:
- Stock management
- Stock updates
- Low stock alerts
- Reservation management
- Warehouse management

**Technologies**:
- Spring Data JPA
- PostgreSQL
- Kafka Streams for real-time processing

**Consistency**: Eventual consistency using Saga pattern

### Cart Service

**Responsibilities**:
- Shopping cart operations
- Add/Remove items
- Cart persistence
- Cart expiration

**Technologies**:
- Spring WebFlux (Reactive)
- Redis (cart storage)
- Project Reactor

**Performance**: Optimized for high concurrency with reactive streams

### Order Service

**Responsibilities**:
- Order creation
- Order status tracking
- Order history
- Order cancellation
- Refund processing

**Technologies**:
- Spring Data JPA
- PostgreSQL
- Kafka
- Saga pattern for distributed transactions

**Saga Pattern**: Orchestration-based saga for multi-service order processing

### Payment Service

**Responsibilities**:
- Payment processing
- Payment method management
- Transaction recording
- Payment status tracking
- Refund handling
- Idempotent payment processing

**Technologies**:
- Spring Data JPA
- PostgreSQL
- Payment Gateway Integration (Stripe/PayPal)
- Idempotency patterns

**Key Patterns**:
- Idempotent operations
- Saga pattern integration
- Webhook handling

### Notification Service

**Responsibilities**:
- Email notifications
- SMS notifications
- Push notifications
- Notification templates
- Notification preferences

**Technologies**:
- Kafka Consumer
- Spring Mail
- Twilio for SMS
- Firebase for Push
- Redis for rate limiting

### Search Service

**Responsibilities**:
- Full-text search
- Faceted search
- Auto-complete
- Search analytics
- Search indexing

**Technologies**:
- Elasticsearch
- Spring Data Elasticsearch
- Kafka for real-time indexing

### Analytics Service

**Responsibilities**:
- Real-time analytics
- User behavior tracking
- Sales analytics
- Performance metrics
- Business intelligence

**Technologies**:
- Kafka Streams
- Elasticsearch
- Grafana
- Custom metrics

### Shipping Service

**Responsibilities**:
- Shipping calculations
- Carrier integration
- Tracking information
- Delivery scheduling
- Shipping status updates

**Technologies**:
- Spring Data JPA
- PostgreSQL
- Carrier APIs integration
- Kafka events

## Data Flow Examples

### Order Creation Flow

```
1. Client -> API Gateway (Auth + Rate Limit)
2. API Gateway -> Order Service
3. Order Service -> Payment Service (Sync: WebClient)
4. Payment Service -> Payment Gateway (External API)
5. Order Service publishes: OrderCreatedEvent -> Kafka
6. Inventory Service consumes OrderCreatedEvent
7. Inventory Service reserves stock
8. Notification Service sends confirmation email
9. Shipping Service receives shipping request
```

### Search Flow

```
1. Client -> API Gateway
2. API Gateway -> Search Service
3. Search Service queries Elasticsearch
4. Response returned with facets and results
```

### Real-time Analytics Flow

```
1. Events published to Kafka from various services
2. Analytics Service consumes from Kafka
3. Kafka Streams processes events
4. Results stored in Elasticsearch
5. Grafana visualizes metrics
```

## Resilience Patterns

### Circuit Breaker
- Prevents cascading failures
- Auto-recovery
- Fallback methods

### Retry Mechanism
- Exponential backoff
- Maximum retry attempts
- Jitter to prevent thundering herd

### Rate Limiting
- Token bucket algorithm
- Per-user and global limits
- API Gateway level enforcement

### Bulkhead Pattern
- Thread pool isolation
- Resource protection
- Prevents resource exhaustion

### Timeout
- Request timeouts
- Default responses
- Prevents hanging requests

## Security Architecture

### Authentication Flow
```
1. User credentials -> Auth Service
2. Auth Service validates credentials
3. Auth Service generates JWT token
4. Client stores JWT token
5. Client includes JWT in Authorization header
6. API Gateway validates JWT
7. Request forwarded to service
```

### Authorization
- Role-Based Access Control (RBAC)
- Resource-level permissions
- Method-level security with @PreAuthorize

### Secrets Management
- Vault for sensitive data
- Environment variables for configuration
- No hardcoded secrets

## Observability Architecture

### Metrics
- JVM metrics (Micrometer)
- Application metrics
- Custom business metrics
- Prometheus scraping
- Grafana dashboards

### Tracing
- OpenTelemetry instrumentation
- Distributed tracing with Jaeger
- Correlation IDs across services
- Request context propagation

### Logging
- Structured JSON logging
- Correlation IDs (MDC)
- ELK Stack for aggregation
- Log levels by environment

## Scalability Considerations

### Horizontal Scaling
- Stateless services
- Load balancing
- Session persistence (Redis)
- Database connection pooling

### Vertical Scaling
- Virtual Threads for concurrency
- Reactive programming
- JVM tuning
- Memory optimization

### Caching Strategy
- Redis distributed cache
- Cache-aside pattern
- Write-through pattern
- Caffeine local cache
- Cache invalidation

## Technology Rationale

### Why Spring Boot?
- Rapid development
- Auto-configuration
- Embedded server
- Production-ready
- Mature ecosystem

### Why Kafka?
- Event streaming
- Durability
- Scalability
- Event replay
- Exactly-once semantics

### Why Kubernetes?
- Container orchestration
- Self-healing
- Auto-scaling
- Rolling updates
- Service discovery

### Why Virtual Threads?
- High concurrency
- Reduced thread overhead
- Simpler code
- Better resource utilization
- Project Loom

## Deployment Architecture

### Local Development
- Docker Compose
- All services in containers
- Volumes for data persistence

### Cloud Deployment
- Kubernetes cluster
- Helm for package management
- ConfigMaps for configuration
- Secrets for sensitive data
- Persistent Volumes for data
- Ingress for external access

## Next Steps

1. Review service-specific documentation
2. Set up local development environment
3. Understand Kafka topic structure
4. Review security implementation
5. Study distributed transaction patterns
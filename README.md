# ShopSphere E-commerce Platform

A production-grade e-commerce platform built with microservices architecture using Spring Boot and MongoDB.

## Architecture

The platform is built using a microservices architecture with the following services:

1. **API Gateway**: Routes requests to appropriate microservices
2. **Service Registry**: Manages service discovery and registration
3. **Config Server**: Centralized configuration management
4. **Product Service**: Manages product catalog and inventory
5. **Order Service**: Handles order processing and management
6. **User Service**: Manages user accounts and authentication
7. **Cart Service**: Handles shopping cart operations
8. **Payment Service**: Processes payments and transactions
9. **Inventory Service**: Manages product inventory
10. **Review Service**: Handles product reviews and ratings
11. **Notification Service**: Manages notifications and alerts
12. **Analytics Service**: Handles analytics and reporting
13. **Security Service**: Manages security and authentication

## Technology Stack

- **Backend**: Spring Boot 3.2.3
- **Database**: MongoDB
- **Search Engine**: Elasticsearch
- **Caching**: Redis
- **Service Discovery**: Netflix Eureka
- **Configuration Management**: Spring Cloud Config
- **API Documentation**: OpenAPI (Swagger)
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- JDK 17
- Maven
- Docker
- Docker Compose
- MongoDB
- Elasticsearch
- Redis

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/yourusername/shopsphere.git
cd shopsphere
```

2. Build the project:
```bash
mvn clean install
```

3. Start the services using Docker Compose:
```bash
docker-compose up -d
```

4. Access the services:
- API Gateway: http://localhost:8080
- Service Registry: http://localhost:8761
- Config Server: http://localhost:8888
- Product Service: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html

## API Documentation

The API documentation is available through Swagger UI at:
- Product Service: http://localhost:8081/swagger-ui.html

## Development

### Project Structure

```
shopsphere/
├── api-gateway/
├── service-registry/
├── config-server/
├── product-service/
├── order-service/
├── user-service/
├── cart-service/
├── payment-service/
├── inventory-service/
├── review-service/
├── notification-service/
├── analytics-service/
├── security-service/
└── common/
```

### Adding a New Service

1. Create a new module in the parent pom.xml
2. Add necessary dependencies
3. Create the service structure:
   - Controller
   - Service
   - Repository
   - Model
   - DTO
   - Mapper
4. Add configuration in application.yml
5. Create Dockerfile
6. Update docker-compose.yml

## Testing

Run tests using Maven:
```bash
mvn test
```

## Deployment

The application can be deployed using Docker Compose:
```bash
docker-compose up -d
```

## Monitoring

- Eureka Dashboard: http://localhost:8761
- Actuator Endpoints: http://localhost:8081/actuator

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
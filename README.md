# Price service (Shopping Platform)
Service calculate a given product's price based on implemented policies.

## Requirements
- Java 17 or higher
- Gradle
- Docker

## Building the Project
```bash
./gradlew clean build
```

## Running the Project

### Using Gradlew
```bash
./gradlew bootRun
```

### Using Docker
```bash
docker build -t price-service .
docker run -p 8080:8080 price-service
```

### Swagger ui
```
http://localhost:8080/swagger-ui/index.html
```

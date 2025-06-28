# Dropwizard Application

A sample Dropwizard application demonstrating REST API development with database integration.

## Features

- RESTful API endpoints
- Database integration with H2 and JDBI
- Health checks
- Metrics and monitoring
- Database migrations
- Unit tests

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Project Structure

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── api/           # Data models
│   │   ├── config/        # Configuration classes
│   │   ├── dao/           # Data Access Objects
│   │   ├── health/        # Health checks
│   │   ├── resources/     # REST resources
│   │   └── DropwizardApplication.java
│   └── resources/
│       └── migrations/    # Database migrations
├── test/
│   └── java/com/example/  # Unit tests
└── config.yml             # Application configuration
```

## Building the Application

```bash
mvn clean package
```

## Running the Application

1. **Run the application:**
   ```bash
   java -jar target/dropwizard-app-1.0.0.jar server config.yml
   ```

2. **Run database migrations:**
   ```bash
   java -jar target/dropwizard-app-1.0.0.jar db migrate config.yml
   ```

## API Endpoints

### Hello World
- **GET** `/hello-world` - Returns a greeting message
- **Query Parameters:**
  - `name` (optional) - Name to greet (defaults to "Stranger")

### Users
- **GET** `/users` - Get all users
- **GET** `/users/{id}` - Get user by ID
- **POST** `/users` - Create a new user
- **PUT** `/users/{id}` - Update an existing user
- **DELETE** `/users/{id}` - Delete a user

### Example User JSON
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

## Health Checks

- **GET** `/healthcheck` - Application health status

## Metrics

- **GET** `/metrics` - Application metrics (requires authentication)
- **GET** `/metrics/prometheus` - Prometheus format metrics

## Testing

Run the tests:
```bash
mvn test
```

## Configuration

The application uses `config.yml` for configuration. Key settings:

- `template`: Template string for greetings
- `defaultName`: Default name for greetings
- `database`: Database configuration (H2 in-memory by default)
- `logging`: Logging configuration

## Development

### Adding New Resources

1. Create a new resource class in `src/main/java/com/example/resources/`
2. Annotate with JAX-RS annotations
3. Register in `DropwizardApplication.run()`

### Adding New Models

1. Create model class in `src/main/java/com/example/api/`
2. Add JSON annotations for serialization
3. Add validation annotations if needed

### Database Operations

1. Create DAO interface in `src/main/java/com/example/dao/`
2. Use JDBI annotations for SQL operations
3. Create row mapper if needed

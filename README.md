# Flashcard API

Java Spring Boot REST API for managing flashcard decks and cards. This is the backend for the Kana Flashcards application.

## Technologies

- Java 21
- Spring Boot 3.1.2
- Spring Data JPA
- Spring Security with JWT
- Maven
- H2 Database (development)
- PostgreSQL (production)

## Features

- User registration and authentication (JWT)
- CRUD operations for decks
- CRUD operations for flashcards
- Flashcard management within decks
- Basic validation and error handling

## Project Structure

src/main/java/com/example/
├── controller/ # REST controllers
├── service/ # Business logic
├── repository/ # Data access layer
├── model/ # Entity classes
├── dto/ # Data Transfer Objects
└── util/security/ # Security utilities

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Setup and Run

1. Clone the repository:
   git clone https://github.com/ferytell/java-springboot-api.git
   cd java-springboot-api
2. Build the project:
   mvn clean install
3. Run the application:
   mvn spring-boot:run

The API will start on `http://localhost:8095`

## Configuration

Edit `src/main/resources/application.properties` for database and server settings.

Default settings:

- Server port: 8095
- Development database: H2 in-memory
- Production: PostgreSQL via environment variables

## Environment Variables

- `DB_URL` - Database connection URL
- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `SECRET_KEY` - JWT secret key
- `SERVER_PORT` - Server port (default 8095)

## API Base URL

http://localhost:8095/api

## Testing

Use the included `testing.http` file for manual API testing in VS Code.

## Build for Production

mvn clean package

This generates an executable JAR in the `target/` directory.

## Notes

this is Assignment for Full-Stack Application position ^\_^

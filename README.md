# Flashcard API

A Java-based Spring Boot API for managing flashcard decks and cards with Maven and H2 Database.

## Project Structure

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── SpringBootApiApplication.java     # Main application entry point
│   │   ├── controller/
│   │   │   ├── UserController.java           # Sample user endpoints
│   │   │   └── FlashcardController.java      # Flashcard deck and card endpoints
│   │   ├── service/
│   │   │   ├── UserService.java              # User business logic
│   │   │   └── DeckService.java              # Deck and card business logic
│   │   ├── repository/
│   │   │   ├── UserRepository.java           # User data access
│   │   │   ├── DeckRepository.java           # Deck data access
│   │   │   └── FlashcardRepository.java      # Flashcard data access
│   │   └── model/
│   │       ├── User.java                     # User entity
│   │       ├── Deck.java                     # Deck entity
│   │       └── Flashcard.java                # Flashcard entity
│   └── resources/
│       └── application.properties            # Application configuration
├── test/
└── pom.xml                                    # Maven configuration
```

## Prerequisites

- Java 21 or higher
- Maven 3.6.0 or higher

## Dependencies

- **Spring Boot 3.1.2**
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-validation
  - spring-boot-starter-test
- **H2 Database** (in-memory)
- **Jakarta EE Persistence API**

## Building the Project

```bash
mvn clean compile
```

## Running the Application

```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8095`

## API Endpoints

### Get All Decks

```
GET /api/decks
```

### Create Deck

```
POST /api/decks
Content-Type: application/json

{
  "title": "Biology",
  "description": "Cell basics"
}
```

### Get Deck by ID

```
GET /api/decks/{deckId}
```

### Update Deck

```
PUT /api/decks/{deckId}
Content-Type: application/json

{
  "title": "Biology",
  "description": "Cell structure"
}
```

### Delete Deck

```
DELETE /api/decks/{deckId}
```

### Get Cards in a Deck

```
GET /api/decks/{deckId}/cards
```

### Create a Flashcard

```
POST /api/decks/{deckId}/cards
Content-Type: application/json

{
  "question": "What is the powerhouse of the cell?",
  "answer": "Mitochondria",
  "category": "science"
}
```

## Database Access

H2 Console is available at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

## Building for Production

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory.

To run the packaged application:

```bash
java -jar target/springboot-api-1.0.0.jar
```

## Configuration

Application properties can be modified in `src/main/resources/application.properties`:

- `server.port` - API server port (default: 8095)
- `spring.jpa.hibernate.ddl-auto` - Database schema generation (default: update)
- `spring.datasource.url` - Database connection URL

## Notes

- This project uses an in-memory H2 database, suitable for development and testing
- Data is not persisted between application restarts
- For production, configure a persistent database in `application.properties`

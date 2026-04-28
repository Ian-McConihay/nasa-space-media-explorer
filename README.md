# NASA Space Media Explorer

**Author:** Ian McConihay  
**Course:** CST-452 Capstone Project  
**University:** Grand Canyon University  
**Repository:** [NASA Space Media Explorer](https://github.com/Ian-McConihay/nasa-space-media-explorer)

---

## Project Overview

NASA Space Media Explorer is a full-stack Spring Boot web application that aggregates scientific media and dataset information from multiple NASA public APIs. The application allows users to browse, search, organize, and annotate space-related content while enforcing role-based access control.

This project demonstrates enterprise-style software development practices, including external API integration, layered architecture, database persistence, authentication, authorization, unit testing, and responsive UI design.

---

## Project Background

This project was developed as part of the CST-451 and CST-452 Capstone sequence at Grand Canyon University.

The goal of the project was to design and build a complete web application that integrates external data sources, persists data locally, supports secure user interaction, and provides a clean user interface for exploring NASA media and scientific datasets.

NASA provides multiple public APIs, but the data is distributed across different services. This application centralizes selected NASA data sources into one searchable and organized system.

---

## Features

### Core Features

- User registration and login
- Role-based access control
- Paginated NASA item browsing
- Search and filtering by title, media type, and source API
- Item detail pages for images, videos, and datasets
- Responsive NASA-themed user interface

### User Features

- Create personal collections
- View saved collections
- Add items to collections
- Remove items from collections
- Add annotations to NASA items
- Delete annotations

### Admin Features

- Ingest APOD data
- Ingest NASA Image Library data
- Ingest OSDR dataset metadata
- View success and error feedback after ingestion actions

### System Features

- Custom error pages for 403, 404, and 500 errors
- Duplicate prevention during data ingestion
- MySQL database persistence
- Service-layer unit testing
- Manual test case validation

---

## Technology Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot |
| Web Framework | Spring MVC |
| Security | Spring Security |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Frontend | Thymeleaf, Bootstrap 5, CSS |
| Testing | JUnit 5, Mockito |
| External APIs | NASA APOD, NASA Image Library, NASA OSDR |
| Build Tool | Maven |
| Source Control | Git, GitHub |

---

## External APIs

The application integrates with the following NASA public APIs:

| API | Purpose |
|---|---|
| APOD | Retrieves Astronomy Picture of the Day content |
| NASA Image and Video Library | Retrieves NASA images and media based on search terms |
| OSDR | Retrieves Open Science Data Repository dataset metadata |

---

## Implementation Approach

The application follows a layered architecture pattern.

```text
User Browser
    |
    v
Controller Layer
    |
    v
Service Layer
    |
    v
Repository Layer
    |
    v
MySQL Database
```

External NASA API clients are used by the service layer to retrieve data, normalize it, prevent duplicate records, and persist selected items into the local database.

```text
NASA APIs
    |
    v
Integration Client Layer
    |
    v
NasaIngestService
    |
    v
ItemRepository
    |
    v
MySQL Database
```

---

## Architecture Overview

### Presentation Layer

The presentation layer uses Thymeleaf templates and Bootstrap styling to provide browser-based pages for users and administrators.

### Controller Layer

Controllers handle routing, request processing, and model preparation for UI pages and REST endpoints.

### Service Layer

The service layer contains business logic for users, items, collections, annotations, and NASA ingestion.

### Repository Layer

Repositories use Spring Data JPA to communicate with the MySQL database.

### Integration Layer

NASA API clients retrieve data from APOD, the Image Library, and OSDR.

---

## Major Application Modules

| Module | Description |
|---|---|
| Authentication | Handles login, registration, and user access |
| Items | Displays NASA media and dataset records |
| Collections | Allows users to organize saved items |
| Annotations | Allows users to add notes to items |
| Admin Ingestion | Allows admins to ingest external NASA data |
| Security | Enforces role-based permissions |
| Testing | Validates service logic and functional behavior |

---

## Role-Based Access Control

The application supports the following roles:

| Role | Permissions |
|---|---|
| ADMIN | Can ingest NASA data and access admin features |
| CONTRIBUTOR | Can browse items, manage collections, and create annotations |
| READONLY | Can browse and view content with limited modification rights |

Spring Security protects backend routes, while Thymeleaf security expressions hide unauthorized UI elements.

```html
<a class="nav-link"
   sec:authorize="hasRole('ADMIN')"
   th:href="@{/admin/ingest}">
    Admin
</a>
```

---

## Key Code Example

The item search service normalizes blank filters before passing them to the repository query.

```java
@Override
public Page<ItemEntity> searchItems(
        String title,
        String mediaType,
        NasaSourceApi sourceApi,
        Pageable pageable) {

    String titleFilter =
            (title == null || title.isBlank()) ? null : title;

    String mediaTypeFilter =
            (mediaType == null || mediaType.isBlank()) ? null : mediaType;

    return itemRepository.searchItems(
            titleFilter,
            mediaTypeFilter,
            sourceApi,
            pageable);
}
```

---

## Duplicate Prevention Example

The ingestion process prevents duplicate NASA records by checking the source API and NASA ID before saving.

```java
if (itemRepository.existsBySourceApiAndNasaId(
        NasaSourceApi.IMAGE,
        nasaId)) {
    continue;
}
```

---

## Screenshots

### Home Page

![Home Page](media/home.png)

### Items Page

![Items Page](media/items.png)

### Item Detail Page

![Item Detail Page](media/item-detail.png)

### Collections Page

![Collections Page](media/collections.png)

### Admin Ingest Page

![Admin Ingest Page](media/admin-ingest.png)

---

## User Guide

### Register an Account

1. Navigate to the registration page.
2. Enter a username, email, and password.
3. Submit the form.
4. Login using the new account.

### Browse NASA Items

1. Login to the application.
2. Select **Items** from the navigation bar.
3. Browse the paginated item grid.
4. Use the search bar or filters to narrow results.
5. Click **View Details** to open an item.

### Create a Collection

1. Navigate to **Collections**.
2. Enter a collection name and optional description.
3. Click **Create Collection**.
4. The new collection appears in the collections list.

### Add an Item to a Collection

1. Open an item detail page.
2. Select a collection from the dropdown.
3. Click **Add**.
4. Open the collection to confirm the item was added.

### Add an Annotation

1. Open an item detail page.
2. Enter a note in the annotation text box.
3. Click **Save Annotation**.
4. The annotation appears under the item.

---

## System Administration Guide

### Admin Login

Administrators can access the ingestion page from the navigation bar after login.

```text
/admin/ingest
```

### Admin Functions

Admins can ingest:

- APOD data
- NASA Image Library results
- OSDR dataset metadata

### Required Configuration

The application requires a MySQL database and NASA API key.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nasa_media_explorer
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

nasa.api.key=YOUR_NASA_API_KEY
```

### Maintenance Notes

- Monitor logs for API failures.
- Confirm database connectivity before running ingestion.
- Keep NASA API keys outside public commits.
- Validate imported records after ingestion.
- Use the test suite before final deployment or submission.

---

## How to Run the Application

### Prerequisites

- Java 17
- Maven
- MySQL
- Git
- NASA API key

### Clone the Repository

```bash
git clone https://github.com/Ian-McConihay/nasa-space-media-explorer.git
cd nasa-space-media-explorer
```

### Create the Database

```sql
CREATE DATABASE nasa_media_explorer;
```

### Configure Application Properties

Create or update the local application configuration.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nasa_media_explorer
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=validate

nasa.api.key=YOUR_NASA_API_KEY
```

### Run the Application

```bash
mvn spring-boot:run
```

### Open the Application

```text
http://localhost:8080
```

---

## Testing

Testing was completed through both manual functional testing and backend unit testing.

### Manual Testing

Manual test cases validated:

- Login and registration
- Duplicate user prevention
- NASA item browsing
- Search and filtering
- Item detail rendering
- Collection creation
- Add and remove item from collection
- Annotation creation and deletion
- Admin ingestion
- Role-based access control
- Error handling

### Unit Testing

Unit tests were implemented using JUnit 5 and Mockito.

| Test Class | Purpose |
|---|---|
| UserServiceImplTest | Tests user creation, duplicate validation, lookup, and deletion |
| ItemServiceImplTest | Tests item save, retrieval, deletion, search, and filter normalization |
| CollectionServiceImplTest | Tests collection creation, deletion, item addition, duplicate prevention, and removal |
| AnnotationServiceImplTest | Tests annotation save, retrieval, deletion, and exception handling |

### Run Tests

```bash
mvn test
```

---

## Test Results Summary

| Test Area | Result |
|---|---|
| Manual Functional Testing | Passed |
| Unit Testing | Passed |
| Authentication Testing | Passed |
| Collection Testing | Passed |
| Annotation Testing | Passed |
| NASA API Ingestion Testing | Passed |
| Role-Based Access Testing | Passed |
| Error Handling Testing | Passed |

---

## Project Status

| Category | Status |
|---|---|
| Core Features | Complete |
| UI | Complete |
| Backend Services | Complete |
| Database Integration | Complete |
| NASA API Integration | Complete |
| Unit Testing | Complete |
| Manual Testing | Complete |
| Documentation | Complete |

**Overall Project Completion:** 100%

---

## Challenges and Solutions

| Challenge | Solution |
|---|---|
| NASA APIs return different JSON structures | Created API-specific clients and mapped data into a normalized internal model |
| Duplicate records during ingestion | Added source API and NASA ID duplicate checks |
| Role-based UI visibility | Used Spring Security and Thymeleaf security expressions |
| Collection-item relationship complexity | Implemented a join entity with a composite key |
| Static resource issue after error handling changes | Restored Spring static resource mappings and verified CSS loading |

---

## Lessons Learned

- Layered architecture improves maintainability and testing.
- External API integration requires careful error handling and data normalization.
- Role-based security must be enforced in both backend routes and frontend visibility.
- Unit testing service logic helps validate business rules independently.
- Clear documentation and source control improve project quality and presentation readiness.

---

## Future Improvements

- Add advanced search filters
- Add API response caching
- Add user profile management
- Add export functionality for collections
- Add more detailed admin reporting
- Add deployment configuration for a cloud environment

---

## Demo

### Screencast Videos

- [Screencast Part 1](https://www.loom.com/share/ad005ab92b7241bb942d472fd8ba79a8)
- [Screencast Part 2](https://www.loom.com/share/d94706c63fed4ea8bdc8166561325aa9)

---

## Repository

- [GitHub Repository](https://github.com/Ian-McConihay/nasa-space-media-explorer)

---

## Author

**Ian McConihay**  
Software Developer  
Grand Canyon University  

---

## License

This project was developed for educational purposes as part of the CST-451 and CST-452 Capstone sequence at Grand Canyon University.```text
Controller → Service → Repository → Database

Development Approach

* Milestone 1–3: Planning, requirements, and design
* Milestone 4: Core development and implementation
* Milestone 5: Testing and validation
* Milestone 6: Final integration and polish

Design Patterns Used

* Service Layer Pattern
* Repository Pattern
* Dependency Injection
* DTO Pattern for API responses

⸻

System Architecture Diagram

[ User Browser ]
        ↓
[ Controller Layer ]
        ↓
[ Service Layer ]
        ↓
[ Repository Layer ]
        ↓
[ MySQL Database ]
        ↓
[ External APIs ]
  - APOD
  - Image Library
  - OSDR

⸻

Key Technical Highlights

NASA API Integration

* Multiple APIs normalized into a unified data model
* External responses mapped into internal entities

Duplicate Prevention

* Idempotent ingestion using:

existsBySourceApiAndNasaId(...)

Role-Based Security

* Backend enforcement via Spring Security
* UI enforcement using Thymeleaf sec:authorize

Collections System

* Many-to-many relationship via join entity
* Composite key (CollectionItemId)

Unit Testing

* Service layer tested with Mockito
* Covers logic, validation, and edge cases

⸻

User Guide

How to Use the Application

1. Register or login to your account
2. Navigate to the Items page to browse NASA content
3. Use search to filter results
4. Click an item to view details
5. Add annotations to save notes
6. Create collections to organize items
7. Add or remove items from collections

⸻

System Administration Guide

Admin Capabilities

* Access admin panel at /admin/ingest
* Trigger ingestion for:
    * APOD data
    * NASA Image Library
    * OSDR datasets

Configuration

* Database configured in application.properties
* NASA API key required

Maintenance

* Duplicate prevention handled automatically
* Monitor logs for API failures
* Validate database integrity periodically

⸻

Screenshots

Items Page

(Add screenshot here)

Item Detail

(Add screenshot here)

Collections

(Add screenshot here)

Admin Panel

(Add screenshot here)

⸻

Code Example

public Page<ItemEntity> searchItems(
        String title,
        String mediaType,
        NasaSourceApi sourceApi,
        Pageable pageable) {
    String titleFilter =
            (title == null || title.isBlank()) ? null : title;
    String mediaTypeFilter =
            (mediaType == null || mediaType.isBlank()) ? null : mediaType;
    return itemRepository.searchItems(
            titleFilter,
            mediaTypeFilter,
            sourceApi,
            pageable);
}

⸻

How to Run the Application

Prerequisites

* Java 17
* MySQL
* Maven

Steps

1. Clone the repository

git clone https://github.com/Ian-McConihay/nasa-space-media-explorer.git
cd nasa-space-media-explorer

2. Configure database

spring.datasource.url=jdbc:mysql://localhost:3306/nasa_media_explorer
spring.datasource.username=root
spring.datasource.password=root

3. Add NASA API key

nasa.api.key=YOUR_API_KEY

4. Run application

mvn spring-boot:run

5. Open browser

http://localhost:8080

⸻

Testing

Unit Testing

Implemented using:

* JUnit 5
* Mockito

Tested Components

* UserServiceImpl
* CollectionServiceImpl
* ItemServiceImpl
* AnnotationServiceImpl

Coverage Focus

* Business logic validation
* Exception handling
* Duplicate prevention
* Data integrity

⸻

Project Status

Project Completion: 100%
All planned features implemented and tested successfully.

⸻

Challenges and Solutions

Challenge: External API Inconsistency

Solution:

* DTO mapping and normalization layer

Challenge: Duplicate Data Ingestion

Solution:

* Idempotency checks before saving

Challenge: Relationship Complexity

Solution:

* Join entity with composite key

⸻

Future Improvements

* Advanced search filters
* API response caching
* User profile management
* UI/UX enhancements
* Data export features

⸻

Demo

Screencast Videos

* https://www.loom.com/share/ad005ab92b7241bb942d472fd8ba79a8
* https://www.loom.com/share/d94706c63fed4ea8bdc8166561325aa9

⸻

Author

Ian McConihay
Software Developer
Grand Canyon University

⸻

Repository

https://github.com/Ian-McConihay/nasa-space-media-explorer

⸻

License

This project is for educational purposes as part of a capstone project.

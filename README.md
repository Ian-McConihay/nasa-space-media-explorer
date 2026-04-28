# NASA Space Media Explorer
## Project Overview
NASA Space Media Explorer is a full-stack web application that aggregates scientific media and datasets from multiple NASA public APIs. The system allows users to browse, search, organize, and annotate space-related content while enforcing secure role-based access control.
This project demonstrates real-world software engineering practices, including API integration, layered architecture design, database persistence, authentication, and testing.
---
## Project Background
This project was developed as part of the CST-451 and CST-452 Capstone sequence at Grand Canyon University.
The objective was to design and implement a complete enterprise-style application that integrates external data sources, enforces security, and provides meaningful user interaction through a structured and scalable architecture.
---
## Features
### Core Features
- User registration and authentication
- Role-based access control (Admin, Contributor, Readonly)
- Browse NASA media (paginated)
- Search by title and filters
- View detailed item pages with images, videos, and datasets
### User Features
- Create and manage collections
- Add and remove items from collections
- Add and delete annotations
### Admin Features
- Ingest APOD (Astronomy Picture of the Day)
- Ingest NASA Image Library data
- Ingest OSDR datasets
### System Features
- Error handling (403, 404, 500)
- Duplicate prevention during ingestion
- Responsive NASA-themed UI
---
## Technology Stack
### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA (Hibernate)
### Frontend
- Thymeleaf
- Bootstrap 5
- Custom CSS
### Database
- MySQL
### Testing
- JUnit 5
- Mockito
### External APIs
- NASA APOD API
- NASA Image & Video Library API
- NASA OSDR API
---
## Implementation Approach
The application follows a layered architecture:
```text
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

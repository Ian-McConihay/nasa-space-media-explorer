# NASA Space Media Explorer

## Overview
NASA Space Media Explorer is a full-stack web application that aggregates scientific media and datasets from multiple NASA public APIs. The system allows users to browse, search, organize, and annotate space-related content while enforcing secure role-based access control.

This project demonstrates enterprise-level application design using a layered architecture, external API integration, and full CRUD functionality with authentication and authorization.

---

## Project Purpose
The goal of this project is to provide a centralized platform for exploring NASA media while demonstrating real-world software engineering practices, including:

- API integration and data ingestion
- Secure authentication and role-based authorization
- Scalable architecture design
- Database-driven application development
- Testing and validation of business logic

---

## Features

### Core Features
- User registration and authentication
- Role-based access control (Admin, Contributor, Readonly)
- Browse NASA media items (paginated)
- Search by title and filters
- View detailed item pages with media

### User Features
- Create and manage collections
- Add and remove items from collections
- Add and delete annotations on items

### Admin Features
- Ingest Astronomy Picture of the Day (APOD)
- Ingest NASA Image Library data
- Ingest Open Science Data Repository (OSDR) datasets

### System Features
- Error handling (403, 404, 500)
- Duplicate prevention during ingestion
- Responsive UI using Bootstrap
- Dark-themed NASA-inspired design

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
- Custom CSS (NASA-themed UI)

### Database
- MySQL

### Testing
- JUnit 5
- Mockito

### External APIs
- NASA APOD API
- NASA Image and Video Library API
- NASA Open Science Data Repository (OSDR)

---

## Architecture Overview

The application follows a layered architecture:
